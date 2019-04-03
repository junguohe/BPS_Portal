package com.erry.auth.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.hibernate.engine.spi.SessionImplementor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.erry.auth.dto.PriceDiffDTo;
import com.erry.auth.dto.PriceDowLoad;
import com.erry.auth.dto.PriceStrategyDetailDTO;
import com.erry.auth.model.Json;
import com.erry.auth.model.PriceStrategy;
import com.erry.auth.model.PriceStrategyDetail;
import com.erry.auth.repository.DealerInfoRepository;
import com.erry.auth.repository.MaterialInfoRepository;
import com.erry.auth.repository.PriceStrategyDetailRepository;
import com.erry.common.BusinessException;
import com.erry.util.DateUtil;
import com.erry.util.GuidIdUtil;
import com.erry.util.NumberUtil;
import com.erry.util.StringUtil;

@Service
@Transactional
public class PriceStrategyDetailServiceImpl {
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private PriceStrategyServiceImpl priceStrategyServiceImpl;

	@Autowired
	private PriceStrategyDetailRepository priceStrategyDetailRepository;
	

	// 价格查询（不含特价）
	// 价格查询
	public Map<Integer, List<PriceStrategyDetail>> findprice(int page,
			int size, final String materialcode, final String materialname,
			final String isAccuracy, final String prodid,
			final String dealername, final String startdate,
			final String enddate, final String isRelease, final String name,
			final String isMain) {
		Map<Integer, List<PriceStrategyDetail>> resultMap = new HashMap<Integer, List<PriceStrategyDetail>>();
		StringBuffer hql = new StringBuffer(
				" from PriceStrategyDetail psd where psd.active='1'  ");
		hql.append(" and psd.sid in (select ps.sid from PriceStrategy ps where ps.status='1') ");
		//
		if (!StringUtil.isEmpty(materialcode)) {
			hql.append(" and psd.mterialid in (select m.id from MaterialInfo m where m.materialcode like '%"
					+ materialcode + "%') ");
		}
		if (isAccuracy.equals("1")) {
			if (!StringUtil.isEmpty(materialname)) {
				hql.append(" and psd.mterialid in (select m.id from MaterialInfo m where m.materialname = '"
						+ materialname + "') ");
			}
		} else {
			if (!StringUtil.isEmpty(materialname)) {
				hql.append(" and psd.mterialid in (select m.id from MaterialInfo m where m.materialname like '%"
						+ materialname + "%') ");
			}
		}

		if (!StringUtil.isEmpty(prodid)) {
			hql.append(" and psd.mterialid in (select m.id from MaterialInfo m where m.prodid = '"
					+ prodid + "') ");
		}
//		if (!StringUtil.isEmpty(dealername)) {
//			hql.append(" and psd.mterialid in (select m.id from MaterialInfo m where m.prodid in (select pl.prodid from ProdLine pl where pl.prodid in (select cpl.prodid from CustProdLine cpl where cpl.custid in (select c.id from Customer c where c.custcode like '%"
//					+ dealername
//					+ "%' or c.custname like '%"
//					+ dealername
//					+ "%')) )");
//		}
		if (!StringUtil.isEmpty(startdate) && !StringUtil.isEmpty(enddate)) {
			hql.append(" and  psd.sid in (select ps.sid from PriceStrategy ps where ((ps.validfrom between '"
					+ startdate
					+ "' and '"
					+ enddate
					+ "') or (ps.validto between '"
					+ startdate
					+ "' and '"
					+ enddate
					+ "') or (ps.validfrom <= '"
					+ startdate
					+ "' and ps.validto >= '" + enddate + "')))");
		}
		if (!StringUtil.isEmpty(startdate) && StringUtil.isEmpty(enddate)) {
			hql.append(" and psd.sid in (select ps.sid from PriceStrategy ps where (ps.validto >= '" + startdate + "'))");
		}
		if (StringUtil.isEmpty(startdate) && !StringUtil.isEmpty(enddate)) {
			hql.append(" and psd.sid in (select ps.sid from PriceStrategy ps where (ps.validfrom <= '" + enddate + "'))");
		}
		if (!StringUtil.isEmpty(isRelease)) {
			hql.append(" and  psd.ispublic ='" + isRelease + "' ");
		}
		if (!StringUtil.isEmpty(isMain)) {
			hql.append(" and  psd.ismain ='" + isMain + "' ");
		}
		
		Query queryCount = entityManager.createQuery(
				" select count(*) " + hql.toString(), Long.class);
		Long totalCount = (Long) queryCount.getSingleResult();

		// 查询分页数据
		Query query = entityManager.createQuery(
				" select psd " + hql.toString(), PriceStrategyDetail.class);
		query.setFirstResult(page * size);
		query.setMaxResults(size);
		// 保存Map 总条数 分页后的集合
		resultMap.put(totalCount.intValue(), query.getResultList());
		return resultMap;

	}
	//下载模板时获取上期价格
	public List<PriceStrategyDetailDTO> getLastPrice(String billno) {
		StringBuffer sql = new StringBuffer(
				"  select d.ListPrice,m.Materialcode,m.MaterialName,m.Assembly,mp.prodname,d.LifeCycle from T_BPS_Price_Strategy s "
						+ " left join T_BPS_Price_Strategy_Detail d on s.SID = d.SID "
						+ " left join T_BPS_MaterialInfo m on m.MaterialID = d.MaterialID "
						+ " left join T_BPS_ProductLine mp on mp.prodid = m.prodid where (s.Code) = '"
						+ billno + "' ");
		Query query = entityManager

		.createNativeQuery(sql.toString());
		List<PriceStrategyDetailDTO> lists = new ArrayList<PriceStrategyDetailDTO>();
		List<Object[]> objArrList = query.getResultList();
		for (Object[] o : objArrList) {
			// java.math.BigDecimal.valueOf;
			PriceStrategyDetailDTO dto = new PriceStrategyDetailDTO();
			dto.setListprice(String.valueOf(o[0]));
			dto.setMaterialcode(String.valueOf(o[1]));
			dto.setMaterialname(String.valueOf(o[2]));
			dto.setAssembly(String.valueOf(o[3]));
			dto.setProdname(String.valueOf(o[4]));
			dto.setLifecycle(String.valueOf(o[5]));
			lists.add(dto);
		}
		// return query.getResultList();
		return lists;
	}
	//根据物料查找该物料的上期价格
	public List<PriceStrategyDetailDTO> getLastPriceByMaterial(String materialid,String publicdate) {
		StringBuffer sql = new StringBuffer(
				"  select top 1 d.ListPriceInc from T_BPS_Price_Strategy s "
						+ " left join T_BPS_Price_Strategy_Detail d on s.SID = d.SID "
						+ "where PublicDate <'"+publicdate+"' and d.MaterialID = '"+materialid+"' "
						+ "order by PublicDate desc ");
		Query query = entityManager.createNativeQuery(sql.toString());
		List<PriceStrategyDetailDTO> lists = new ArrayList<PriceStrategyDetailDTO>();
		List<BigDecimal> objArrList = query.getResultList();
		
		if(objArrList.size()>0){
			for (BigDecimal objArr : objArrList) {
				PriceStrategyDetailDTO dto = new PriceStrategyDetailDTO();
				if(objArr==null||objArr.equals("null")){
					dto.setListpriceinc("");
				}else{
					dto.setListpriceinc(StringUtil.fmtMicrometer(objArr.toString()));
				}
				
				lists.add(dto);
			}
		}
		return lists;
	}

	// 下载模板
	public void importFileDownload(HttpServletRequest request,
			HttpServletResponse response, String billno, String fname)
			throws Exception {
		List<PriceStrategyDetailDTO> strs = getLastPrice(billno);
		String path = request.getSession().getServletContext()
				.getRealPath("/download");
		InputStream in = new FileInputStream(path + "/imtemplate.xls");
		HSSFWorkbook wb = new HSSFWorkbook(in);
		for (int i = 0; i < strs.size(); i++) {
			HSSFRow row = wb.getSheet("Sheet1").createRow(i + 1);

			String mcode = strs.get(i).getMaterialcode();
			row.createCell(0).setCellValue(mcode.toString());

			String mname = strs.get(i).getMaterialname();
			row.createCell(1).setCellValue(mname.toString());

			String prodname = strs.get(i).getProdname();
			row.createCell(2).setCellValue(prodname.toString());

			String life = strs.get(i).getLifecycle();
			row.createCell(3).setCellValue(life.toString());
			
			String f = strs.get(i).getAssembly();
			row.createCell(6).setCellValue(f.toString());
			
			String cucc = strs.get(i).getCurrency();
			row.createCell(7).setCellValue(cucc);
			
			String sixBuf1 = "ROUND(L"+(i+2)+"*1.15,3)";
			row.createCell(8).setCellFormula(sixBuf1);
			
			String sixBuf2 = "ROUND(I"+(i+2)+"*1.16,3)";
			row.createCell(9).setCellFormula(sixBuf2);
			
			String sixBuf3 = "ROUND(M"+(i+2)+"*1.1,3)";
			row.createCell(10).setCellFormula(sixBuf3);
			
			String sixBuf4 = "ROUND(M"+(i+2)+"/1.16,3)";
			row.createCell(11).setCellFormula(sixBuf4);
			
			String sixBuf5 = "(N"+(i+2)+"-M"+(i+2)+")/M"+(i+2)+"";
			row.createCell(14).setCellFormula(sixBuf5);
			
			String listprice = strs.get(i).getListprice();
			if (!StringUtil.isEmpty(listprice)&&listprice!="null") {
				row.createCell(13).setCellValue(Double.parseDouble(listprice));
			} else {
				row.createCell(13).setCellValue(Double.valueOf(0));
			}

		}
		String file = new String(fname.getBytes("gb2312"), "ISO-8859-1")
				+ ".xls";
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=" + file
				+ " ");
		OutputStream out = response.getOutputStream();
		wb.write(out);
		out.close();
	}

	// 上传数据
	public List<PriceStrategyDetailDTO> readFile(MultipartFile file)
			throws IOException, InvalidFormatException {
		InputStream is = file.getInputStream();
		List<PriceStrategyDetailDTO> list = new ArrayList<PriceStrategyDetailDTO>();
		Workbook wbs = WorkbookFactory.create(is);
		Sheet sheet = wbs.getSheetAt(0);
		if (sheet.getRow(0).getPhysicalNumberOfCells()!=16){
			throw new BusinessException("请下载模板,上传正确格式的文件");
		}
		for (int j = 1; j <= sheet.getLastRowNum(); j++) {
			PriceStrategyDetailDTO dto = new PriceStrategyDetailDTO();
			Row row = sheet.getRow(j);
			String[] arr = new String[16];
			if (null != row) {
				int k = 0;
				for (k = 0; k < row.getLastCellNum(); k++) {
					String value = null;
					Cell cell = row.getCell(k);
					if (null != cell) {
						switch (cell.getCellType()) {
						
						case HSSFCell.CELL_TYPE_STRING:// 字符串
								value = cell.getRichStringCellValue().getString();
							break;
							
						case HSSFCell.CELL_TYPE_NUMERIC:// 数字
								if (HSSFDateUtil.isCellDateFormatted(cell)) {
									double d = cell.getNumericCellValue();
									Date date = HSSFDateUtil.getJavaDate(d);
									SimpleDateFormat dformat = new SimpleDateFormat(
											"yyyy/MM/dd HH:mm:ss");
									value = dformat.format(date);
								} else {
									NumberFormat nf = NumberFormat.getInstance();
									nf.setMaximumFractionDigits(9); 
									nf.setGroupingUsed(false);// true时的格式：1,234,567,890
									value = nf.format(cell.getNumericCellValue());
								}
							break;
							
						case HSSFCell.CELL_TYPE_BLANK:
							value = "";
							break;
							
						case HSSFCell.CELL_TYPE_FORMULA:
							try {  
								value = String.valueOf(cell.getStringCellValue());  
							} catch (IllegalStateException e) {  
								value = String.valueOf(cell.getNumericCellValue());  
							} 
							break;
							
						case HSSFCell.CELL_TYPE_BOOLEAN:// boolean型值
								value = String.valueOf(cell.getBooleanCellValue());
							break;
							
						case HSSFCell.CELL_TYPE_ERROR:
							value = String.valueOf(cell.getErrorCellValue());
							break;
							
						default:
							break;
						}
					}

					if (value != null)
						value = value.trim();
					arr[k] = value;
				}
			}
			if (StringUtil.isEmpty(arr[8]) || StringUtil.isEmpty(arr[9])
					|| StringUtil.isEmpty(arr[11]) || StringUtil.isEmpty(arr[12])
					) {
				throw new BusinessException("请将文件填写完整");
			}
			
			if(!StringUtil.isNumberString(arr[8])||!StringUtil.isNumberString(arr[9])||!StringUtil.isNumberString(arr[10])||!StringUtil.isNumberString(arr[11])||!StringUtil.isNumberString(arr[12])){
				throw new BusinessException("价格只能是数字类型！");
			}
			int number = 4;
//			if(arr[7]!=null&&arr[7].equals("USD")){
//				number=4;
//			}
			dto.setMaterialcode(arr[0]);
			dto.setMaterialname(arr[1]);
			dto.setProdname(arr[2]);
			dto.setLifecycle(arr[4]);
			if(!StringUtil.isEmpty(arr[5])){
				try {
					dto.setEol(DateUtil.format(new Date(arr[5]),DateUtil.PATTERN_DATE));
				} catch (Exception e) {
					throw new BusinessException("时间格式错误！请上传正确的时间(yyyy/MM/dd)");
				}
			}
			else{
				dto.setEol(null);
			}
			
			dto.setAssembly(arr[6]);
			dto.setCurrency(arr[7]);
			//根据币种判断截取几位尾数
			
			BigDecimal price1=NumberUtil.setScale(BigDecimal.valueOf(Double.valueOf(arr[9])),number);
			BigDecimal price2=NumberUtil.setScale(BigDecimal.valueOf(Double.valueOf(arr[12])),number);
			BigDecimal dealerprice=NumberUtil.setScale(BigDecimal.valueOf(Double.valueOf(arr[11])),number);
			BigDecimal listprice=NumberUtil.setScale(BigDecimal.valueOf(Double.valueOf(arr[8])),number);
			
			dto.setListprice(listprice.toString());
			dto.setListpriceinc(price1.toString());
			dto.setCustsp(arr[10]);
			dto.setDealerprice(dealerprice.toString());
			dto.setDealerpriceinc(price2.toString());
			if(StringUtil.isEmpty(arr[13])){
				dto.setLastprice("");
			}else{
				dto.setLastprice(arr[13]);
			}
			if(StringUtil.isEmpty(arr[14])){
				dto.setReduceper("");
			}else{
				dto.setReduceper(StringUtil.fmtMicrometer(arr[14]));
			}
			if (arr[15].equals("是")) {
				dto.setIspublic("1");
			} else {
				dto.setIspublic("0");
			}
			double price3=Double.parseDouble(price1.toString()) / Double.parseDouble(price2.toString());
			double price4=price3-1;
			
			if(arr[12].equals("0")){
				dto.setDealerprofit("0");
			}else{
				dto.setDealerprofit(StringUtil.fmtMicrometer(String.valueOf(price4)));
			}
			list.add(dto);
		}
		return list;
	}

	// 保存上传文件到数据库
	public Json savePrice(PriceStrategy price,List<PriceStrategyDetail> priceStrategyDetails) {
		String flag = "操作成功";
		Boolean result = true;
		Map sidMap = priceStrategyServiceImpl.savePrice(price);
		if (sidMap != null) {
			// priceStrategyDetailRepository.updatepricedetail(sidMap.get("oldsid").toString());//保存策略成功
			// 失效了一个策略 失效掉改策略的明细
			if (sidMap.get("oldsid") != null) {
				List<PriceStrategyDetail> lists = priceStrategyDetailRepository.getpricebysid(sidMap.get("oldsid").toString());
				for (int i = 0; i < lists.size(); i++) {
					PriceStrategyDetail p = lists.get(i);
					//0的判断
					p.setActive("0");
					if(!StringUtil.isEmpty(lists.get(i).getListprice())&&lists.get(i).getListprice()!="null"){
						
						String sjiachun = lists.get(i).getListprice();
						BigDecimal db = new BigDecimal(sjiachun);
						String ii = db.toPlainString();
						
						p.setListprice(ii);
					}
					if(!StringUtil.isEmpty(lists.get(i).getListpriceinc())&&lists.get(i).getListpriceinc()!="null"){
						
						String sjiachun = lists.get(i).getListpriceinc();
						BigDecimal db = new BigDecimal(sjiachun);
						String ii = db.toPlainString();
						
							p.setListpriceinc(ii);
					}
					if(!StringUtil.isEmpty(lists.get(i).getDealerprice())&&lists.get(i).getDealerprice()!="null"){
						
						String sjiachun = lists.get(i).getDealerprice();
						BigDecimal db = new BigDecimal(sjiachun);
						String ii = db.toPlainString();
						
							p.setDealerprice(ii);
					}
					if(!StringUtil.isEmpty(lists.get(i).getDealerpriceinc())&&lists.get(i).getDealerpriceinc()!="null"){
						
						String sjiachun = lists.get(i).getDealerpriceinc();
						BigDecimal db = new BigDecimal(sjiachun);
						String ii = db.toPlainString();
						
							p.setDealerpriceinc(ii);
					}
					
					if(!StringUtil.isEmpty(lists.get(i).getDealerprofit())&&lists.get(i).getDealerprofit()!="null"){
						
						String sjiachun = lists.get(i).getDealerprofit();
						BigDecimal db = new BigDecimal(sjiachun);
						String ii = db.toPlainString();
						
							p.setDealerprofit(ii);
					}
					
					if(!StringUtil.isEmpty(lists.get(i).getLastprice())&&lists.get(i).getLastprice()!="null"){
						
						String sjiachun = lists.get(i).getLastprice();
						BigDecimal db = new BigDecimal(sjiachun);
						String ii = db.toPlainString();
						
							p.setLastprice(ii);
					}
					if(!StringUtil.isEmpty(lists.get(i).getReduceper())&&lists.get(i).getReduceper()!="null"){
						
						String sjiachun = lists.get(i).getReduceper();
						BigDecimal db = new BigDecimal(sjiachun);
						String ii = db.toPlainString();
							p.setReduceper(ii);
					}
					
					if(!StringUtil.isEmpty(lists.get(i).getCustsp())&&lists.get(i).getCustsp()!="null"){
						
						String sjiachun = lists.get(i).getCustsp();
						BigDecimal db = new BigDecimal(sjiachun);
						String ii = db.toPlainString();
						
							p.setCustsp(ii);
					}
					entityManager.merge(p);
				}
			}
		}
		// 保存新策略的策略明细
		List<PriceStrategyDetail> list=new ArrayList<PriceStrategyDetail>();
		if (priceStrategyDetails.size() > 0) {
			
			for (int i = 0; i < priceStrategyDetails.size(); i++) {
				int number = 4;
				PriceStrategyDetail pr = new PriceStrategyDetail();
				pr.setSid(sidMap.get("newsid").toString());
				pr.setId(GuidIdUtil.getGuId());
				
				pr.setMaterialcode(priceStrategyDetails.get(i).getMaterialcode());
				pr.setMaterialname(priceStrategyDetails.get(i).getMaterialname());
				pr.setAssembly(priceStrategyDetails.get(i).getAssembly());
				
				if(!StringUtil.isEmpty(priceStrategyDetails.get(i).getLifecycle())){
					pr.setLifecycle(priceStrategyDetails.get(i).getLifecycle());
				}
				
				pr.setIsmain("1");
				
				if(priceStrategyDetails.get(i).getListprice()!="null"&&!StringUtil.isEmpty(priceStrategyDetails.get(i).getListprice())){
					//pr.setListprice(priceStrategyDetails.get(i).getListprice());
					pr.setListprice(NumberUtil.setScale(BigDecimal.valueOf(Double.valueOf(getNumber(priceStrategyDetails.get(i).getListprice()))), number).toString());
				}
				if(priceStrategyDetails.get(i).getListpriceinc()!="null"&&!StringUtil.isEmpty(priceStrategyDetails.get(i).getListpriceinc())){
					pr.setListpriceinc(NumberUtil.setScale(BigDecimal.valueOf(Double.valueOf(getNumber(priceStrategyDetails.get(i).getListpriceinc()))), number).toString());
				}
				if(priceStrategyDetails.get(i).getDealerprice()!="null"&&!StringUtil.isEmpty(priceStrategyDetails.get(i).getDealerprice())){
					pr.setDealerprice(NumberUtil.setScale(BigDecimal.valueOf(Double.valueOf(getNumber(priceStrategyDetails.get(i).getDealerprice()))), number).toString());
				}
				if(priceStrategyDetails.get(i).getDealerpriceinc()!="null"&&!StringUtil.isEmpty(priceStrategyDetails.get(i).getDealerpriceinc())){
					pr.setDealerpriceinc(NumberUtil.setScale(BigDecimal.valueOf(Double.valueOf(getNumber(priceStrategyDetails.get(i).getDealerpriceinc()))), number).toString());
				}
				if(priceStrategyDetails.get(i).getDealerprofit()!="null"&&!StringUtil.isEmpty(priceStrategyDetails.get(i).getDealerprofit())){
					pr.setDealerprofit(NumberUtil.setScale(BigDecimal.valueOf(Double.valueOf(getNumber(priceStrategyDetails.get(i).getDealerprofit()))), number).toString());
				}
				if(priceStrategyDetails.get(i).getLastprice()!="null"&&!StringUtil.isEmpty(priceStrategyDetails.get(i).getLastprice())){
					pr.setLastprice(NumberUtil.setScale(BigDecimal.valueOf(Double.valueOf(getNumber(priceStrategyDetails.get(i).getLastprice()))), number).toString());
				}
				if(priceStrategyDetails.get(i).getCustsp()!="null"&&!StringUtil.isEmpty(priceStrategyDetails.get(i).getCustsp())){
					pr.setCustsp(NumberUtil.setScale(BigDecimal.valueOf(Double.valueOf(getNumber(priceStrategyDetails.get(i).getCustsp()))), number).toString());
				}
				if(priceStrategyDetails.get(i).getReduceper()!="null"&&!StringUtil.isEmpty(priceStrategyDetails.get(i).getReduceper())){
					pr.setReduceper(priceStrategyDetails.get(i).getReduceper());
				}
				if(!StringUtil.isEmpty(priceStrategyDetails.get(i).getIspublic())){
				pr.setIspublic(priceStrategyDetails.get(i).getIspublic());
				}
				if(!StringUtil.isEmpty(priceStrategyDetails.get(i).getCurrency())){
					pr.setCurrency(priceStrategyDetails.get(i).getCurrency());
				}
				if(priceStrategyDetails.get(i).getEol()!=null){
					pr.setEol(DateUtil.roundDate(priceStrategyDetails.get(i).getEol()));
				}
				pr.setActive("1");
				PriceStrategyDetail newpr = entityManager.merge(pr);
				list.add(newpr);
			}
			entityManager.flush();
			get9Id(sidMap.get("newsid").toString());
		}
		
		
		Json json = new Json();
		json.setData(list);
		json.setMsg(flag);
		json.setSuccess(result);
		return json;
	}
	public void get9Id(String sid) {

		SessionImplementor session = entityManager.unwrap(SessionImplementor.class);
		Connection conn = session.connection();
		String testPrint = null;
		try {
			conn.setAutoCommit(false);
			CallableStatement cstmt = conn.prepareCall("{ call [proc_PriceStrategyValid](?) }");
			cstmt.setString(1, sid);
			cstmt.execute();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

     }
	// 调价失算
	//dealername, period, pricea, priceb
	public List<PriceDiffDTo> PriceDiff(
			final String name, final String period, final String pricea,String priceb) {
		List<PriceDiffDTo> dtos = new ArrayList<PriceDiffDTo>();
		StringBuffer sql = new StringBuffer(
				" select temp.DealerName,SUM(temp.Diff) diff from ( "
						+ "select a.DealerName,a.MaterialCode,a.Qty,m.DealerPriceInc as mDealerPrceInc,n.DealerPriceInc,(n.DealerPriceInc-m.DealerPriceInc)*a.Qty Diff "
						+ "from [dbo].[T_BPS_Dealer_Upload_Inventory] a "
						+ "LEFT JOIN T_BPS_Upload_TaskInfo task ON task.TID = a.TID "
						+ "left join ( select * from dbo.T_BPS_Price_Strategy_Detail where SID in( "
						+ "select sid from  T_BPS_Price_Strategy where Status='1' and Active='1' and code='"+pricea+"') and Active='1') m on a.MMaterialId = m.MaterialID "
						+ "left join ( select * from dbo.T_BPS_Price_Strategy_Detail where SID in( "
						+ "select sid from  T_BPS_Price_Strategy where Status='1' and Active='1' and code='"+priceb+"') and Active='1') n on a.MMaterialId = n.MaterialID "
						+ "where a.Active = '1' AND task.Period='"+period+"' AND task.TaskConfirm='1' AND task.TaskContent='inventory' AND m.Currency = n.Currency "
						+ ") temp ");
		
		if (!StringUtil.isEmpty(name)) {
			sql.append(" where DealerName like '%"+name+"%'");
		}
		Query query = entityManager.createNativeQuery(sql.toString()+" group by temp.DealerName");
		
		List<Object[]> objArrList = query.getResultList();
		for (Object[] objArr : objArrList) {
			PriceDiffDTo dto = new PriceDiffDTo();
			dto.setDealername(String.valueOf(objArr[0]));
			BigDecimal diff = new BigDecimal(0);
			if(!StringUtil.isEmpty(String.valueOf(objArr[1]))&&!String.valueOf(objArr[1]).equals("null")){
				//if(!String.valueOf(objArr[1]).equals("OE-9")){
					
					String sjiachun = String.valueOf(objArr[1]);
					BigDecimal db = new BigDecimal(sjiachun);
					String ii = db.toPlainString();
					
					diff = NumberUtil.setScale(BigDecimal.valueOf(Double.valueOf(ii)),0);
			//	}
			}
			dto.setDiff(diff);
			dtos.add(dto);
		}
		return dtos;
	}
	/**
	 * 获取经销商的调价试算结果的明细
	 * @param dealerid
	 * @return
	 */
	public List<PriceDiffDTo> readerPriceDiffInfo(String dealername,String pricea,String priceb,String period) {
		List<PriceDiffDTo> dtos = new ArrayList<PriceDiffDTo>();
		StringBuffer sql = new StringBuffer(
			"select a.DealerName,a.MaterialCode,a.Qty,m.DealerPriceInc as mDealerPrce,n.DealerPriceInc,(n.DealerPriceInc-m.DealerPriceInc)*a.Qty Diff,a.MaterialName,m.Currency "
						+ "from [dbo].[T_BPS_Dealer_Upload_Inventory] a "
						+ "LEFT JOIN T_BPS_Upload_TaskInfo task ON task.TID = a.TID AND task.TaskConfirm='1' "
						+ "left join ( select * from dbo.T_BPS_Price_Strategy_Detail where SID in( "
						+ "select sid from  T_BPS_Price_Strategy where Status='1' and Active='1' and code='"+pricea+"') and Active='1') m on a.MMaterialId = m.MaterialID "
						+ "left join ( select * from dbo.T_BPS_Price_Strategy_Detail where SID in( "
						+ "select sid from  T_BPS_Price_Strategy where Status='1' and Active='1' and code='"+priceb+"') and Active='1') n on a.MMaterialId = n.MaterialID "
						+ "where a.Active = '1' and a.DealerName='"+dealername+"' AND task.Period='"+period+"' AND task.TaskContent='inventory' AND m.Currency = n.Currency " );
		
		Query query = entityManager.createNativeQuery(sql.toString());
		List<Object[]> objArrList = query.getResultList();
		for (Object[] objArr : objArrList) {
			PriceDiffDTo dto = new PriceDiffDTo();
			dto.setDealername(String.valueOf(objArr[0]));
			dto.setMaterialcode(String.valueOf(objArr[1]));
			dto.setQuantity(String.valueOf(objArr[2]));
			if(!StringUtil.isEmpty(String.valueOf(objArr[3]))&&!String.valueOf(objArr[3]).equals("null")){
				dto.setDealerpriceinca(NumberUtil.formatDecimal(new BigDecimal(String.valueOf(objArr[3]))));
			}
			if(!StringUtil.isEmpty(String.valueOf(objArr[4]))&&!String.valueOf(objArr[4]).equals("null")){
				dto.setDealerpriceincb(NumberUtil.formatDecimal(new BigDecimal(String.valueOf(objArr[4]))));
			}
			
			BigDecimal diff = new BigDecimal(0);
			if(!StringUtil.isEmpty(String.valueOf(objArr[5]))&&!String.valueOf(objArr[5]).equals("null")){
				//if(!String.valueOf(objArr[5]).equals("OE-9")){
					String sjiachun = String.valueOf(objArr[5]);
					BigDecimal db = new BigDecimal(sjiachun);
					String ii = db.toPlainString();
					
					diff = new BigDecimal(ii);
				//}
			}
			dto.setDiff(diff);
			dto.setMaterialname(String.valueOf(objArr[6]));
			dto.setCurrency(String.valueOf(objArr[7]));
			dtos.add(dto);
		}
		return dtos;
	}

	public List<PriceStrategyDetailDTO> mappingToDTO(
			List<PriceStrategyDetail> list) {
		List<PriceStrategyDetailDTO> lists = new ArrayList<PriceStrategyDetailDTO>();
		for (PriceStrategyDetail p : list) {
			PriceStrategyDetailDTO dto = new PriceStrategyDetailDTO(p);
			lists.add(dto);
		}
		return lists;
	}
	//标准价导出
	public List<PriceDowLoad> pricedownload(HttpServletRequest request,
			HttpServletResponse response, final String materialcode,
			final String materialname, final String isAccuracy, final String prodid,
			final String dealername, final String startdate, final String enddate,
			final String isRelease,final String name,final String isMain,final String filename) throws IOException {
		List<PriceDowLoad> list = new ArrayList<PriceDowLoad>();
		StringBuffer sql = new StringBuffer(
				"select mi.MaterialCode,mi.MaterialName,pl.ProdName,psd.DealerPriceInc,psd.DealerPrice,psd.IsMain,psd.LifeCycle,psd.Assembly,psd.IsPublic,ps.PublicDate,psd.ListPriceInc,psd.ListPrice,psd.LastPrice,psd.DealerProfit,ps.Code "
						+ "  from T_BPS_Price_Strategy_Detail psd "
						+ " left join T_BPS_Price_Strategy ps on ps.SID=psd.SID "
						+ " left join T_BPS_MaterialInfo mi on mi.MaterialID=psd.MaterialID "
						+ "  left join T_BPS_ProductLine pl on pl.ProdID = mi.ProdID where psd.active='1' and ps.active='1' and mi.active='1' and pl.active='1' and ps.status='1'");
		if (!StringUtil.isEmpty(materialcode)&&materialcode!=null) {
			sql.append(" and mi.materialcode like '%"
					+ materialcode + "%' ");
		}
		if (isAccuracy.equals("1")) {
			if (!StringUtil.isEmpty(materialname)&&materialname!=null) {
				sql.append(" and mi.materialname = '"
						+ materialname + "' ");
			}
		} else {
			if (!StringUtil.isEmpty(materialname)&&materialname!=null) {
				sql.append(" and mi.materialname like '%"
						+ materialname + "%' ");
			}
		}

		if (!StringUtil.isEmpty(prodid)&&prodid!=null) {
			sql.append(" and pl.prodid = '"
					+ prodid + "'");
		}
		if (!StringUtil.isEmpty(startdate) &&startdate!=null && !StringUtil.isEmpty(enddate)&&enddate!=null) {
			sql.append(" and ((ps.validfrom between '"+ DateUtil.parse(startdate)+ "' and '"+ DateUtil.parse(enddate)
					+ "') or (ps.validto between '"
					+ DateUtil.parse(startdate)
					+ "' and '"
					+ DateUtil.parse(enddate)
					+ "') or (ps.validfrom <= '"
					+ DateUtil.parse(startdate)
					+ "' and ps.validto >= '" + DateUtil.parse(enddate) + "'))");
		}
		if (!StringUtil.isEmpty(startdate)&&startdate!=null && StringUtil.isEmpty(enddate)) {
			sql.append(" and ps.validto >= '" + DateUtil.parse(startdate) + "'");
		}
		if (StringUtil.isEmpty(startdate) && !StringUtil.isEmpty(enddate) && enddate!=null) {
			sql.append(" and ps.validfrom <= '" + DateUtil.parse(enddate) + "'");
		}
		if (!StringUtil.isEmpty(isMain)&&isMain!=null) {
			sql.append(" and  psd.ismain ='" + isMain + "' ");
		}
		Query query = entityManager.createNativeQuery(sql.toString());
		List<Object[]> objArrList = query.getResultList();
		for (Object[] objArr : objArrList) {
			PriceDowLoad dto = new PriceDowLoad();
			dto.setMaterialcode(String.valueOf(objArr[0]));
			dto.setMaterialname(String.valueOf(objArr[1]));
			dto.setProdname(String.valueOf(objArr[2]));
			dto.setDealerpriceinc(String.valueOf(objArr[3]));
			dto.setDealerprice(String.valueOf(objArr[4]));
			dto.setIsmain(String.valueOf(objArr[5]));
			dto.setLifecycle(String.valueOf(objArr[6]));
			dto.setAssembly(String.valueOf(objArr[7]));
			dto.setIspublic(String.valueOf(objArr[8]));
			dto.setPublicdate(String.valueOf(objArr[9]));
			dto.setListpriceinc(String.valueOf(objArr[10]));
			dto.setListprice(String.valueOf(objArr[11]));
			dto.setLastprice(String.valueOf(objArr[12]));
			dto.setDealerprofit(String.valueOf(objArr[13]));
			dto.setCode(String.valueOf(objArr[14]));
			list.add(dto);
		}
		return list;
	}

//	(request, response, code, versionno,
//			 materialcode, materialname, isAccuracy, startdate,enddate,status,isMain, filename
	public List<PriceStrategyDetail> priceStrategydownload(HttpServletRequest request,
			HttpServletResponse response, String code, String versionno,
			String materialcode, String materialname, String isAccuracy,
			String startdate, String enddate, String status, String isMain,
			String filename) {
		Map<Integer, List<PriceStrategyDetail>> resultMap = new HashMap<Integer, List<PriceStrategyDetail>>();
		StringBuffer sql = new StringBuffer(" from PriceStrategyDetail d where d.sid in(select sid from PriceStrategy where 1=1 )");
		if (!StringUtil.isEmpty(versionno)&&!versionno.equals("null")) {
			sql.append(" and d.sid in (select s.sid from PriceStrategy s where s.versionno = '" + versionno + "') ");
		}
		if (!StringUtil.isEmpty(code)&&!code.equals("null")) {
			sql.append(" and d.sid in (select s.sid from PriceStrategy s where  s.code = '" + code + "') ");
		}
		if (!StringUtil.isEmpty(materialcode)&&!materialcode.equals("null")) {
			sql.append(" and d.mterialid in (select m.id from MaterialInfo m where m.materialcode like '%"+materialcode+"%') ");
		}
		if (isAccuracy.equals("1")) {
			if (!StringUtil.isEmpty(materialname)&&!materialname.equals("null")) {
				sql.append(" and d.mterialid in (select m.id from MaterialInfo m where m.materialname = '"+materialname+"') ");
			}
		} else {
			if (!StringUtil.isEmpty(materialname)&&!materialname.equals("null")) {
				sql.append(" and d.mterialid in (select m.id from MaterialInfo m where m.materialname like '%"+materialname+"%') ");
			}
		}

		if (!StringUtil.isEmpty(startdate)&&!startdate.equals("null")&& !StringUtil.isEmpty(enddate)&&!enddate.equals("null")) {
			sql.append(" and d.sid in(select s.sid from PriceStrategy s where s.publicdate  between '" + startdate + "' and '"
					+ enddate + "')");
		}
		if (!StringUtil.isEmpty(startdate)&&!startdate.equals("null") && (StringUtil.isEmpty(enddate)||enddate.equals("null"))) {
			sql.append(" and d.sid in(select s.sid from PriceStrategy s where s.publicdate >= '" + startdate + "')");
		}
		if ((StringUtil.isEmpty(startdate) || startdate.equals("null") ) && !StringUtil.isEmpty(enddate)&&!enddate.equals("null")) {
			sql.append(" and d.sid in(select s.sid from PriceStrategy s where s.publicdate <= '" + enddate + "')");
		}
		if (!StringUtil.isEmpty(status)&&!status.equals("null")) {
			sql.append(" and d.sid in(select s.sid from PriceStrategy s where s.status ='" + status + "')");
		}else{
			sql.append(" and d.sid in(select s.sid from PriceStrategy s where s.status ='1')");
		}
		if (!StringUtil.isEmpty(isMain)&&!isMain.equals("null")) {
			sql.append(" and d.ismain='" + isMain + "'");
		}
		Query queryCount = entityManager.createQuery(" select count(*) "
				+ sql.toString(), Long.class);
		Long totalCount = (Long) queryCount.getSingleResult();

		Query query = entityManager.createQuery(" select d " + sql.toString(), PriceStrategyDetail.class);

		return query.getResultList();

	}
	public List<PriceStrategyDetail> readUploadInfo(int i, Integer limit,
			String sid) {
		Map<Integer, List<PriceStrategyDetail>> resultMap = new HashMap<Integer, List<PriceStrategyDetail>>();
		StringBuffer hql = new StringBuffer(
				" from PriceStrategyDetail psd where psd.active='1'  ");
		 //hql.append(" and psd.sid in (select ps.sid from PriceStrategy ps where ps.status='1') ");
		if (!StringUtil.isEmpty(sid)) {
			hql.append(" and psd.sid='"+sid+"'");
		}
		Query query = entityManager.createQuery(" select psd " + hql.toString(), PriceStrategyDetail.class);
		return  query.getResultList();

	}
	public PriceStrategyDetail DeletePriceStrategyDetail(String id) {
		// TODO Auto-generated method stub
		PriceStrategyDetail pr = new PriceStrategyDetail();
		pr=priceStrategyDetailRepository.findById(id);
		if(pr.getId()!=null){
			pr.setActive("0");
			entityManager.merge(pr);
		}
		return pr;
	}
	
	public String getNumber(String num){
		String sjiachun =num.replace(",", "");
		   BigDecimal db = new BigDecimal(sjiachun);
		         String     ii = db.toPlainString();
		return ii;
	}
}
package com.erry.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileExistsException;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import com.erry.common.GlobalConstant;

public class FileUtil {
	
	private static final int BUFFER_SIZE = 16 * 1024;
	
	private static final String SIGN = "/";
	
	private static final SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat dateformatYYYYMM = new SimpleDateFormat("yyyyMM");
	private static final SimpleDateFormat dateformatYYYYMMddHHmmssSSS = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS");
	
	/**
	 * 文件上传到服务器
	 * @param file
	 * @param date
	 * @param entityName
	 * @return
	 * @throws IOException
	 */
	public static File fileUpload(MultipartFile file, Date date, String entityName) throws IOException {
		byte[] bytes = file.getBytes();
		if(bytes == null || bytes.length == 0 )return null;
		String finalTargetFolder;
//		finalTargetFolder = GlobalConstant.UPLOAD_FOLDER + "/" + dateformat.format(date);
		finalTargetFolder = GlobalConstant.getSystemConfig("file.upload.folder") + SIGN + dateformatYYYYMM.format(date);
//		finalTargetFolder = GlobalConstant.UPLOAD_FOLDER + "/" + entityName;
		File rootFolder = new File(finalTargetFolder);
		//文件夹不存在，则创建文件夹
		if(!rootFolder.exists())rootFolder.mkdirs();
		//生成文件名
		String[] nameSplit = file.getOriginalFilename().split("\\.");
		//System.out.println(nameSplit[nameSplit.length-1]);	
		File finalFile = new File(finalTargetFolder+SIGN
				+ entityName
				+ "_"
//				+ (new Date()).getTime()
				+ dateformatYYYYMMddHHmmssSSS.format(date)
				+ "."+nameSplit[nameSplit.length-1]);
		//写文件
		if(!file.isEmpty()){
			file.transferTo(finalFile);
		}
		return finalFile;
	}
	
	/** 
     * 下载 
     *  
     * @param request 
     * @param response 
     * @param storeName 
     * @param contentType 
     * @param realName 
     * @throws Exception 
     */  
    public static void download(HttpServletRequest request,  
            HttpServletResponse response, String storeName, String contentType,  
            String realName) throws Exception {  
        response.setContentType("text/html;charset=UTF-8");  
        request.setCharacterEncoding("UTF-8");  
        BufferedInputStream bis = null;  
        BufferedOutputStream bos = null;  

		String uploadFolder = GlobalConstant.getSystemConfig("file.upload.folder");
        String ctxPath = uploadFolder + SIGN;  
        String downLoadPath = ctxPath + storeName;  
  
        long fileLength = new File(downLoadPath).length();  
  
        response.setContentType(contentType);  
        response.setHeader("Content-disposition", "attachment; filename="  
                + new String(realName.getBytes("utf-8"), "ISO8859-1"));  
        response.setHeader("Content-Length", String.valueOf(fileLength));  
  
        bis = new BufferedInputStream(new FileInputStream(downLoadPath));  
        bos = new BufferedOutputStream(response.getOutputStream());  
        byte[] buff = new byte[2048];  
        int bytesRead;  
        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
            bos.write(buff, 0, bytesRead);  
        }  
        bis.close();  
        bos.close();  
    }  
    
    public static void fileMove(String src, String descDir){
    	if(src != null && !"".equals(src)){
			try {
				File descFile = new File(descDir);
				if(!descFile.exists())descFile.mkdirs();
				FileUtils.moveFileToDirectory(new File(src), descFile, true);
			} catch (FileExistsException e){
//				e.printStackTrace();
				new File(descDir).delete();
				fileMove(src, descDir);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    }
    
    /**
     * 获取指定目录下的一切文件子集
     * @param folder 需要检索的文件夹
     * @param includeChildren 是否需要收集子文件夹中的文件
     * @return
     */
    public static Set<File> getFileCollection(File folder, boolean includeChildren){
    	Set<File> fileSet = new HashSet<File>();
    	File[] files = folder.listFiles();
    	for(File eachFile: files){
    		if(eachFile.isFile()){
    			fileSet.add(eachFile);
    		}else if(eachFile.isDirectory()){
    			if(includeChildren){
    				fileSet.addAll(getFileCollection(eachFile, true));
    			}
    		}
    	}
    	return fileSet;
    }
    
    /**
     * 获取文件的后缀名
     * @param file
     * @return
     */
    public static String getExtension(File file) {
    	String fileName = file.getName();
    	return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
    
    
    // 复制文件
    public static void copyFile(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } finally {
            // 关闭流
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }
    
	/**
	 * 保存web上传的MultipartFile
	 * @param file
	 * @param path
	 */
	public static void saveFile(MultipartFile file, String path) {
		try {
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new BufferedInputStream(file.getInputStream(), BUFFER_SIZE);
				out = new BufferedOutputStream(new FileOutputStream(path), BUFFER_SIZE);
				
				byte[] buffer = new byte[BUFFER_SIZE];
				while (in.read(buffer) > 0) out.write(buffer);
			} finally {
				if (in != null) in.close();
				if (out != null) out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
	/**
	 * 创建目录
	 * @param folderPath
	 */
	public static void createFolder(String folderPath) {
		File file = new File(folderPath);
		if (!file.exists()) file.mkdirs();
	}

	/**
	 * 删除目录下所有文件
	 * @param directory
	 */
	public static void emptyFolder(String folderPath) {
		File directory = new File(folderPath);
		if (directory.exists()) {
			File[] entries = directory.listFiles();
			for (File file : entries) {
				file.delete();
			}
		}
	}

	/**
	 * 删除目录及下所有文件
	 * @param folderPath
	 */
	public static void deleteFolder(String folderPath) {
		File directory = new File(folderPath);
		if (directory.exists()) {
			emptyFolder(folderPath);
			directory.delete();
		}
	}

	/**
	 * 创建文件
	 * @param filepath
	 * @throws IOException
	 */
	public static void createFile(String filepath) throws IOException {
		File file = new File(filepath);
		if (!file.exists()) file.createNewFile();
	}

	/**
	 * 删除文件
	 * @param filepath
	 */
	public static void deleteFile(String filepath) {
		File file = new File(filepath);
		if (file.exists()) file.delete();
	}
    
	/**
	 * 文件重命名
	 * @param filepath
	 * @param destname
	 */
	public static void renamefile(File file, String destname) {
		if (file.exists()) {
			String fileParent = file.getParent();
			File desFile = new File(fileParent + File.separator + destname);
			file.renameTo(desFile);
		}
	}
    
}

package com.erry.auth.component;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.erry.common.GlobalConstant;
import com.erry.exception.ImageStoreException;

@Component
public class ImageStoreComponent {
	
	//can't pass test case ,so make change 
	@Autowired(required=false)
	ServletContext sc;
	
	private static final String[] format = {"GIF","PNG","JPEG", "BMP","WBMP"};
	
	private static final String SIGN="/";
	
	private static final SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd HHmmss");
	
	private String getTargetPath(String relatePath){
		String currentPath = sc.getRealPath(SIGN);
		File file = new File(currentPath);
		return file.getParent()+SIGN+relatePath;
	} 

	public static void getFile(byte[] bfile, String filePath,String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if(!dir.exists()&&dir.isDirectory()){//判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(filePath+"\\"+fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

	
	public File storeImage(MultipartFile file,Date date,String UserId) throws IOException{
		byte[] bs = file.getBytes();
		if(bs==null || bs.length==0 )
			return null;
		
		
		
		String finalTargetFolder = GlobalConstant.UPLOAD_FOLDER+"/"+UserId+"/"+dateformat.format(date);
		File rootFolder = new File(finalTargetFolder);
		if(!rootFolder.exists())rootFolder.mkdirs();
		
		ByteArrayInputStream in = new ByteArrayInputStream(bs);
		BufferedImage bi =  ImageIO.read(in);
		
		String[] nameSplit = file.getOriginalFilename().split("/.");
		File finalFile = new File(finalTargetFolder+SIGN+file.getName()+(new Date()).getTime()+(new Random(10)).nextLong()+"."+nameSplit[nameSplit.length-1]);
		
		try{
			boolean throwException = true;
			for(String str : format){
				if(ImageIO.write(bi, str,finalFile)){
					throwException = false;
					break;
				}
			}
			
			if(throwException){
				throw new ImageStoreException();
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw new ImageStoreException();
		}finally{
			in.close();
		}
		
		return finalFile;
		
	}
	
	public boolean deleteImage(String path){
		
		String realPath = getTargetPath(path);
		File file = new File(realPath);
		if(file.exists()){
			file.deleteOnExit();
			return true;
		}
	
		return false;
	}
	
	
	public static void main(String args[]){
		String file="Jellyfish.jpg.200";
		String[] nameSplit = file.split(".");
	//	System.out.println(nameSplit[0]);
	}
	
	
}

package com.erry.util;

import java.text.DecimalFormat;

import org.apache.poi.hssf.usermodel.HSSFCell;

public class WDWUtil {
	
    // @描述：是否是2003的excel，返回true是2003 
   public static boolean isExcel2003(String filePath)  {  
       return filePath.matches("^.+\\.(?i)(xls)$");  
   }  
 
    //@描述：是否是2007的excel，返回true是2007 
   public static boolean isExcel2007(String filePath)  {  
       return filePath.matches("^.+\\.(?i)(xlsx)$");  
   }
   
   
   //判断从Excel文件中解析出来数据的格式   
   public static String getCellValue(HSSFCell cell){   
       String value = null;   
       //简单的查检列类型   
       switch(cell.getCellType())   
       {   
           case HSSFCell.CELL_TYPE_STRING://字符串  
               value = cell.getRichStringCellValue().getString();   
               break;   
           case HSSFCell.CELL_TYPE_NUMERIC://数字  
               //long dd = (long)cell.getNumericCellValue();   
        	   String dd= new DecimalFormat("0.00").format(cell.getNumericCellValue());
               value = dd+"";   
               break;   
           case HSSFCell.CELL_TYPE_BLANK:   
               value = "";   
               break;      
           case HSSFCell.CELL_TYPE_FORMULA:   
               value = String.valueOf(cell.getCellFormula());   
               break;   
           case HSSFCell.CELL_TYPE_BOOLEAN://boolean型值  
               value = String.valueOf(cell.getBooleanCellValue());   
               break;   
           case HSSFCell.CELL_TYPE_ERROR:   
               value = String.valueOf(cell.getErrorCellValue());   
               break;   
           default:   
               break;   
       }   
       return value;   
   }  	   
}

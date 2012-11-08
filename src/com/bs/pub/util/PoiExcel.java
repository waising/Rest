package com.bs.pub.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * 
 * @ClassName 类名：PoiExcel
 * @Description 功能说明：
 *              <p>
 *              导出Excel流
 *              </p>
 *              *****************************************************************
 *              ******
 * @date 创建日期：Nov 26, 2010
 * @author 创建人：李中俊
 * @version 版本号：V1.0
 *          <p>
 *         **************************修订记录*************************************
 * 
 *          Nov 26, 2010 李中俊 创建该类功能。
 * 
 *         
 *          *********************************************************************
 *          *
 *          </p>
 */
public class PoiExcel {

	/**
	 * 创建表头
	 * 
	 * @param workbook
	 * @param sheet
	 * @param headNames
	 */
	private int createHeadCell(HSSFWorkbook workbook, HSSFSheet sheet,
			String[] headNames,int colWidths[]) {

		boolean b = (headNames != null && headNames.length > 0);
		if (b) {
			HSSFRow row = sheet.createRow(0);
			HSSFCell fcell = null;
			for (int i = 0; i < headNames.length; i++) {
				fcell = row.createCell(i);

				HSSFCellStyle cellStyle = workbook.createCellStyle(); // 建立新的cell样式
				HSSFFont font = workbook.createFont();
				font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				font.setFontHeightInPoints((short) 10);
				
				cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//居中
				cellStyle.setFont(font);//设置字体
				cellStyle.setBorderRight((short)1);//设边框大小
				cellStyle.setBorderLeft((short)1);
				cellStyle.setBorderBottom((short)1);
				cellStyle.setBorderTop((short)1);
				cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//设置背景颜色
				cellStyle.setFillForegroundColor(HSSFColor.GOLD.index);
				
				fcell.setCellStyle(cellStyle);
				fcell.setCellValue(headNames[i]);
				if(colWidths!=null && i<colWidths.length){
				sheet.setColumnWidth(i, 32*colWidths[i]);
				}
		//		sheet.autoSizeColumn((short)i);
			}
		}

		return b ? 1 : 0;
	}

	public InputStream getExcelFile(List<Map<String, Object>> data,
			String sheetName, String[] headNames) throws IOException {
		return getExcelFile(data, sheetName, headNames, null,null);
	}

	/**
	 * 根据数据集生成Excel，并返回Excel文件流
	 * @param data 数据集
	 * @param sheetName Excel中sheet单元名称
	 * @param headNames 列表头名称数组
	 * @param colKeys 列key,数据集根据该key进行按顺序取值
	 * @return
	 * @throws IOException
	 */
	public InputStream getExcelFile(List<Map<String, Object>> data,
			String sheetName, String[] headNames, String[] colKeys,int colWidths[])
			throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(sheetName);
		// 创建表头
		int startRow = createHeadCell(workbook, sheet, headNames,colWidths);
		
		HSSFCellStyle cellStyle = workbook.createCellStyle(); // 建立新的cell样式
		cellStyle.setBorderRight((short)1);
		cellStyle.setBorderLeft((short)1);
		cellStyle.setBorderBottom((short)1);
		cellStyle.setBorderTop((short)1);
		// 创建数据
		SetCellData(data, sheet,cellStyle,startRow, colKeys);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		workbook.write(baos);
		byte[] ba = baos.toByteArray();
		ByteArrayInputStream bais = new ByteArrayInputStream(ba);
		return bais;
	}

	private void SetCellData(List<Map<String, Object>> data, HSSFSheet sheet,
			HSSFCellStyle cellStyle,int startRow, String[] colkeys) {
		// 创建数据
		HSSFRow row = null;
		HSSFCell cell = null;
		int i = startRow;

		if (colkeys == null || colkeys.length == 0) {
			if(data!=null && data.size()>0){
				for (Map<String, Object> rowData : data) {
					row = sheet.createRow(i);
					Set<String> set = rowData.keySet();
					int j = 0;
					for (String colKey : set) {
						Object colValue = rowData.get(colKey);
						cell = row.createCell(j);
						cell.setCellStyle(cellStyle);
						// if (colValue instanceof String) {
						// cell.setCellValue((String) colValue);
						// }else if(colValue instanceof Integer){
						//					
						// }
						if (colValue != null) {
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							cell.setCellValue(colValue.toString());
						}
						//sheet.autoSizeColumn((short)j); 耗资源较大！
						j++;
					}
					i++;
				}
			}
		} else {
			if(data!=null && data.size()>0){
				for (Map<String, Object> rowData : data) {
					row = sheet.createRow(i);
					int j = 0;
					for (String key : colkeys) {
						Object colValue = rowData.get(key);
						cell = row.createCell(j);
						cell.setCellStyle(cellStyle);
						if (colValue != null) {
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							cell.setCellValue(colValue.toString());
						}
//						sheet.autoSizeColumn((short)j); 耗资源较大！
						j++;
					}
					i++;
				}
			}
		}
	}

	// 测试
	public static void main(String[] args) throws IOException {
		PoiExcel excel = new PoiExcel();
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

		LinkedHashMap<String, Object> e = new LinkedHashMap<String, Object>();
		e.put("username", "小李");
		e.put("address", "福州鼓楼");
		e.put("age", new Integer(20));
		e.put("tag", new Boolean(true));
		e.put("date", Tools.getDate());
		data.add(e);

		e = new LinkedHashMap<String, Object>();
		e.put("username", "小李2");
		e.put("address", "福州鼓楼");
		e.put("age", new Integer(22));
		e.put("tag", new Boolean(true));
		e.put("date", Tools.getDate());
		data.add(e);

		e = new LinkedHashMap<String, Object>();
		e.put("username", "小李3");
		e.put("address", "福州鼓楼");
		e.put("age", new Integer(18));
		e.put("tag", new Boolean(true));
		e.put("date", Tools.getDate());
		data.add(e);

		e = new LinkedHashMap<String, Object>();
		e.put("username", "小李4");
		e.put("address", "福州鼓楼");
		e.put("age", new Integer(22));
		e.put("tag", new Boolean(true));
		e.put("date", Tools.getDate());
		data.add(e);

		String[] headNames = { "用户名", "地址", "年龄", "状态", "日期" };
		String[] keys ={"username","address","age","tag","date"};
		int colWidths[] ={200,300,60,60,140};

		InputStream input = (excel.getExcelFile(data, "单位", headNames,keys,colWidths));

		File f = new File("c:\\excel.xls");
		if (f.exists())
			f.delete();
		f.createNewFile();
		FileOutputStream out = new FileOutputStream(f);
		HSSFWorkbook book = new HSSFWorkbook(input);
		book.write(out);
		out.flush();
		out.close();
	}
}

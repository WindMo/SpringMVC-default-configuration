package mo.springmvc.defaultconfiguration.util;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.List;

/**
 * @author WindShadow
 * @verion 2020/9/6.
 * 简单介绍
 * HSSFWorkbook:是操作Excel2003以前（包括2003）的版本，扩展名是.xls；（行数量有限，65535，超过则OOM）
 * XSSFWorkbook:是操作Excel2007后的版本，扩展名是.xlsx；(1048576行，16384列，可OOM)
 * SXSSFWorkbook:是操作Excel2007后的版本，扩展名是.xlsx；（行数数量可以很多，原理：持久化到硬盘，空间换时间）
 */

public abstract class BaseExcelUtil {

    /**
     * 数据导出到文件，生成 .xlsx
     * @param title 标题
     * @param sheetName sheet名称
     * @param dataList 数据列表
     * @param fileName 文件名
     * @param filePath 文件路径
     * @throws IOException
     */
    public static void exportExcelXlsx(String[] title, String sheetName, List<String[]> dataList, String fileName, String filePath) throws IOException {

        File checkPath = new File(filePath);
        if (!checkPath.exists()) {
            checkPath.mkdirs();
        }
        File file = new File(filePath + File.separator + fileName);
        exportExcelXlsx(title,sheetName,dataList,file);
    }

    /**
     * 数据导出到文件，生成 .xls
     * @param title 标题
     * @param sheetName sheet名称
     * @param dataList 数据列表
     * @param fileName 文件名
     * @param filePath 文件路径
     * @throws IOException
     */
    public static void exportExcelXls(String[] title, String sheetName, List<String[]> dataList, String fileName, String filePath) throws IOException {

        File checkPath = new File(filePath);
        if (!checkPath.exists()) {
            checkPath.mkdirs();
        }
        File file = new File(filePath + File.separator + fileName);
        exportExcelXls(title,sheetName,dataList,file);
    }

    /**
     * 数据导出到文件，生成 .xlsx
     * @param title 标题
     * @param sheetName sheet名称
     * @param dataList 数据列表
     * @param file
     * @throws IOException
     */
    public static void exportExcelXlsx(String[] title, String sheetName, List<String[]> dataList, File file) throws IOException {

        FileOutputStream outStream = new FileOutputStream(file);
        XSSFWorkbook workBook = createXSSFWorkbook(title, sheetName, dataList);
        workBook.write(outStream);
        outStream.flush();
        outStream.close();
    }

    /**
     * 数据导出到文件，生成 .xls
     * @param title 标题
     * @param sheetName sheet名称
     * @param dataList 数据列表
     * @param file
     * @throws IOException
     */
    public static void exportExcelXls(String[] title, String sheetName, List<String[]> dataList, File file) throws IOException {

        FileOutputStream outStream = new FileOutputStream(file);
        HSSFWorkbook hssfWorkbook =  createHSSFWorkbook(title, sheetName, dataList);
        hssfWorkbook.write(outStream);
        outStream.flush();
        outStream.close();
    }

    /**
     * 导出 .xlsx 到文件
     * @param xssfWorkbook
     * @param file
     */
    public static void exportXSSFWorkbookToFile(XSSFWorkbook xssfWorkbook, File file) throws IOException {

        FileOutputStream outStream = new FileOutputStream(file);
        xssfWorkbook.write(outStream);
        outStream.flush();
        outStream.close();
    }

    /**
     * 导出 .xls 到文件
     * @param hssfWorkbook
     * @param file
     */
    public static void exportHSSFWorkbookToFile(HSSFWorkbook hssfWorkbook, File file) throws IOException {

        FileOutputStream outStream = new FileOutputStream(file);
        hssfWorkbook.write(outStream);
        outStream.flush();
        outStream.close();
    }

    /**
     * XSSFWorkbook导入到输出流
     * @param xssfWorkbook
     * @param outputStream
     */
    public static void exportXSSFWorkbookToOutputStream(XSSFWorkbook xssfWorkbook, OutputStream outputStream) throws IOException {

        xssfWorkbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }


    /**
     * HSSFWorkbook导入到输出流
     * @param hssfWorkbook
     * @param outputStream
     */
    public static void exportHSSFWorkbookToOutputStream(HSSFWorkbook hssfWorkbook,OutputStream outputStream) throws IOException {

        hssfWorkbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 生成一个有一个sheet栏的excel表格，扩展名为 .xlsx
     * @param title 标题
     * @param sheetName sheet名称
     * @param dataList 数据列表
     * @return
     */
    public static XSSFWorkbook createXSSFWorkbook(String[] title, String sheetName, List<String[]> dataList) {
        XSSFWorkbook workBook = new XSSFWorkbook();
        XSSFSheet sheet = workBook.createSheet();
        workBook.setSheetName(0, sheetName);
        XSSFRow titleRow = sheet.createRow(0);

        int i;
        for(i = 0; i < title.length; ++i) {
            titleRow.createCell(i).setCellValue(title[i]);
        }

        for(i = 0; i < dataList.size(); ++i) {
            XSSFRow row = sheet.createRow(i + 1);
            String[] rowData = (String[])dataList.get(i);

            for(int j = 0; j < rowData.length; ++j) {
                row.createCell(j).setCellValue(rowData[j]);
            }
        }

        return workBook;
    }

    /**
     * 生成一个有一个sheet栏的excel表格，扩展名为 .xls
     * @param title 标题
     * @param sheetName sheet名称
     * @param dataList 数据列表
     * @return
     */
    public static HSSFWorkbook createHSSFWorkbook(String[] title, String sheetName, List<String[]> dataList) {

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(sheetName);
        HSSFRow titleRow = sheet.createRow(0);
        int i;
        for(i = 0; i < title.length; ++i) {
            titleRow.createCell(i).setCellValue(title[i]);
        }

        for(i = 0; i < dataList.size(); ++i) {
            HSSFRow row = sheet.createRow(i + 1);
            String[] rowData = (String[])dataList.get(i);

            for(int j = 0; j < rowData.length; ++j) {
                row.createCell(j).setCellValue(rowData[j]);
            }
        }

        return wb;
    }
}

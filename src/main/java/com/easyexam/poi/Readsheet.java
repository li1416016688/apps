package com.easyexam.poi;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

public class Readsheet {
    static XSSFRow row;

    public static void importExcel(String fileName){

        FileInputStream fis = null;
        XSSFWorkbook workbook = null;
        try {
            fis = new FileInputStream(
                    new File(fileName));
            workbook = new XSSFWorkbook(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            XSSFSheet spreadsheet = workbook.getSheetAt(i);
            Iterator<Row> rowIterator = spreadsheet.iterator();
            while (rowIterator.hasNext()) {
                row = (XSSFRow) rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getCellType()) {
//                        case Cell.CELL_TYPE_NUMERIC:
//                            System.out.print(
//                                    cell.getNumericCellValue() + " \t\t");
//                            break;
                        case Cell.CELL_TYPE_STRING:
                            System.out.print(
                                    cell.getStringCellValue() + " \t\t");
                            break;
                    }
                }
            }
            System.out.println();
        }

        try {
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)  {
        Readsheet.importExcel("C:\\Users\\Lei\\Desktop\\题目导入模板 (1).xlsx");
    }



}

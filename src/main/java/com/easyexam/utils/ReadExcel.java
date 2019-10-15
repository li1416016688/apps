/**
 * 该方法主要针对excel模板读取试题，四种方法针对性较强，需要对应的模板文件进行配合
 * 返回值int型为成功导入的题目数量
 * 当抛出空指针异常时，则对应错误代码2342
 */
package com.easyexam.utils;

import com.easyexam.common.CodeMsg;
import com.easyexam.entity.entity.QuesSingleChoose;
import com.easyexam.exection.MyException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadExcel {
    public List<QuesSingleChoose> readSingleChoose(String filePath) throws NullPointerException{
        XSSFSheet sheet = getXSSFWorkbook(filePath, "单选");
        int rowNum = sheet.getLastRowNum();
        QuesSingleChoose singleChoose = null;
        List list = new ArrayList<QuesSingleChoose>();  //最终要返回的list
        for(int i = 1; i <= rowNum; i++){
            singleChoose = new QuesSingleChoose();
            XSSFRow row = sheet.getRow(i);
            //先判断是否为空再放入
            singleChoose.setQuestion(getCellStringValue(row.getCell(0)));
            if (!isNullCell(row.getCell(1))) {
                singleChoose.setChooseA(getCellStringValue(row.getCell(1)));
            }
            if (!isNullCell(row.getCell(2))) {
                singleChoose.setChooseB(getCellStringValue(row.getCell(2)));
            }
            if (!isNullCell(row.getCell(3))) {
                singleChoose.setChooseC(getCellStringValue(row.getCell(3)));
            }
            if (!isNullCell(row.getCell(4))) {
                singleChoose.setChooseD(getCellStringValue(row.getCell(4)));
            }
            if (!isNullCell(row.getCell(5))) {
                singleChoose.setChooseE(getCellStringValue(row.getCell(5)));
            }
            if (!isNullCell(row.getCell(6))) {
                singleChoose.setChooseF(getCellStringValue(row.getCell(6)));
            }
            singleChoose.setAnswer(getCellStringValue(row.getCell(7)));
            //根据输入的学科名称获取放入的id
            int id = getSubjectId(filePath, getCellStringValue(row.getCell(8)));
            singleChoose.setSubjectId(id);

            singleChoose.setLevel(getCellIntegerValue(row.getCell(9)));
            singleChoose.setTag(getCellStringValue(row.getCell(10)));
            list.add(singleChoose);
        }
        return list;
    }

    /**
     * 根据文件路径获取Excel工具表
     * @param filePath  文件路径
     * @param sheetName sheet名称
     * @return
     */
    private XSSFSheet getXSSFWorkbook(String filePath,String sheetName){
        FileInputStream inputStream = null;
        XSSFWorkbook workbook = null;
        try {
            inputStream = new FileInputStream(filePath);
            workbook = new XSSFWorkbook(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        XSSFSheet sheet = workbook.getSheet(sheetName);
        return sheet;
    }

    /**
     * 根据科目名称从模板文件中获取对应的id
     * @param SubjectName  科目名称
     * @return
     */
    private int getSubjectId(String filePath, String SubjectName){
        XSSFSheet sheet = getXSSFWorkbook(filePath, "学科");
        Integer targetRow = null;   //科目所在的行的索引号
        for(int i = 0; i < sheet.getLastRowNum(); i++) {
            XSSFRow row = sheet.getRow(i);
            XSSFCell cell = row.getCell(0);
            if(cell.getStringCellValue().equals(SubjectName)){
                targetRow = i;
                break;
            }
        }
        //目标行检查
        if(targetRow == null){
            throw new MyException(2341,new CodeMsg().getSubjectIdNotFound());
        }

        //获取id
        XSSFRow row = sheet.getRow(targetRow);
        XSSFCell cell = row.getCell(1);
        int value = (int)cell.getNumericCellValue();
        return value;
    }

    /**
     * 判断单元格是否为空
     * @param cell
     * @return  空值返回true；非空返回false
     */
    private boolean isNullCell(Cell cell){
        if(cell == null){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 获取单元格的String值，非String值会强制转成String
     * @param cell 输入的单元格对象
     * @return
     */
    private String getCellStringValue(Cell cell){
        String returnValue = null;
        int cellType = cell.getCellType();
        if(cellType == cell.CELL_TYPE_NUMERIC){
            double value = cell.getNumericCellValue();
            returnValue = String.valueOf(value);
        }else if(cellType == cell.CELL_TYPE_STRING){
            String value = cell.getStringCellValue();
            returnValue = value;
        }
        return returnValue;
    }

    /**
     * 获取单元格的int值；double会强制转换成int；其他类型值会返回null
     * @param cell 输入的单元格对象
     * @return
     */
    private Integer getCellIntegerValue(Cell cell){
        Integer returnValue = null;
        int cellType = cell.getCellType();
        if(cellType == cell.CELL_TYPE_NUMERIC){
            double value = cell.getNumericCellValue();
            returnValue = (int) value;
        }else{
            return null;
        }
        return returnValue;
    }

    public static void main(String[] args) {
        ReadExcel readExcel = new ReadExcel();
        List<QuesSingleChoose> list = readExcel.readSingleChoose("C:\\Users\\kingi\\Desktop\\题目导入模板 - 副本.xlsx");
        System.out.println(list.size());
        for(QuesSingleChoose singleChoose : list){
            System.out.println(singleChoose);
        }
    }
}

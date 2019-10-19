/**
 * 该方法主要针对excel模板读取试题，四种方法针对性较强，需要对应的模板文件进行配合
 * 返回List集合，包含对应类型的题目，List长度为成功导出的题目数量
 * 当抛出SubjectNotFoundException时，对应代码2341，学科不存在
 * 当抛出FileNotFoundException时，对应代码2342，文件不存在
 * 当抛出NullPointerException时，则对应错误代码2343，题目关键信息为空
 * 当抛出SheetNotFoundException时，对应返回错误代码2344
 * 当抛出IOException时，对应代码500，服务器IO异常
 */
package com.easyexam.apps.utils;

import com.easyexam.apps.common.CodeMsg;
import com.easyexam.apps.common.JsonResult;
import com.easyexam.apps.entity.QuesJudge;
import com.easyexam.apps.entity.QuesMultipleChoose;
import com.easyexam.apps.entity.QuesQuestionsAnswers;
import com.easyexam.apps.entity.QuesSingleChoose;
import com.easyexam.apps.exection.MyException;
import com.easyexam.apps.exection.SheetNotFoundException;
import com.easyexam.apps.exection.SubjectNotFoundException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ReadExcel {
    //读取单选题
    public List<QuesSingleChoose> readSingleChoose(String filePath) throws NullPointerException, IOException, FileNotFoundException, SheetNotFoundException, SubjectNotFoundException {
        XSSFSheet sheet = getXSSFWorkbook(filePath, "单选");
        if(sheet == null){
            throw new SheetNotFoundException();
        }
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

    //读取多选题
    public List<QuesMultipleChoose> readMultipleChoose(String filePath) throws NullPointerException, IOException, FileNotFoundException, SheetNotFoundException, SubjectNotFoundException {
        XSSFSheet sheet = getXSSFWorkbook(filePath, "多选");
        if(sheet == null){
            throw new SheetNotFoundException();
        }
        int rowNum = sheet.getLastRowNum();
        QuesMultipleChoose multipleChoose = null;
        List list = new ArrayList<QuesSingleChoose>();  //最终要返回的list
        for(int i = 1; i <= rowNum; i++){
            multipleChoose = new QuesMultipleChoose();
            XSSFRow row = sheet.getRow(i);
            //先判断是否为空再放入
            multipleChoose.setQuestion(getCellStringValue(row.getCell(0)));
            if (!isNullCell(row.getCell(1))) {
                multipleChoose.setChooseA(getCellStringValue(row.getCell(1)));
            }
            if (!isNullCell(row.getCell(2))) {
                multipleChoose.setChooseB(getCellStringValue(row.getCell(2)));
            }
            if (!isNullCell(row.getCell(3))) {
                multipleChoose.setChooseC(getCellStringValue(row.getCell(3)));
            }
            if (!isNullCell(row.getCell(4))) {
                multipleChoose.setChooseD(getCellStringValue(row.getCell(4)));
            }
            if (!isNullCell(row.getCell(5))) {
                multipleChoose.setChooseE(getCellStringValue(row.getCell(5)));
            }
            if (!isNullCell(row.getCell(6))) {
                multipleChoose.setChooseF(getCellStringValue(row.getCell(6)));
            }
            //对数据格式进行处理
            String originalAnswer = getCellStringValue(row.getCell(7));
            String ProcessedAnswer = originalAnswer.replaceAll(",", "&");
            multipleChoose.setAnswer(ProcessedAnswer);
            //根据输入的学科名称获取放入的id
            int id = getSubjectId(filePath, getCellStringValue(row.getCell(8)));
            multipleChoose.setSubjectId(id);

            multipleChoose.setLevel(getCellIntegerValue(row.getCell(9)));
            multipleChoose.setTag(getCellStringValue(row.getCell(10)));
            list.add(multipleChoose);
        }
        return list;
    }

    //读取判断题
    public List<QuesJudge> readJudge(String filePath) throws NullPointerException, IOException, FileNotFoundException, SheetNotFoundException, SubjectNotFoundException {
        XSSFSheet sheet = getXSSFWorkbook(filePath, "判断");
        if(sheet == null){
            throw new SheetNotFoundException();
        }
        int rowNum = sheet.getLastRowNum();
        QuesJudge quesJudge = null;
        List list = new ArrayList<QuesJudge>();  //最终要返回的list
        for(int i = 1; i <= rowNum; i++){
            quesJudge = new QuesJudge();
            XSSFRow row = sheet.getRow(i);
            //先判断是否为空再放入
            quesJudge.setQuestion(getCellStringValue(row.getCell(0)));
            quesJudge.setAnswer(getCellBooleanValue(row.getCell(1))?1:0);   //如果为true存入1，否则存入0
            //根据输入的学科名称获取放入的id
            int id = getSubjectId(filePath, getCellStringValue(row.getCell(2)));
            quesJudge.setSubjectId(id);

            quesJudge.setLevel(getCellIntegerValue(row.getCell(3)));
            quesJudge.setTag(getCellStringValue(row.getCell(4)));
            list.add(quesJudge);
        }
        return list;
    }

    //读取判断题
    public List<QuesQuestionsAnswers> readQuestionsAnswers(String filePath) throws NullPointerException, IOException, FileNotFoundException, SheetNotFoundException, SubjectNotFoundException {
        XSSFSheet sheet = getXSSFWorkbook(filePath, "简答");
        if(sheet == null){
            throw new SheetNotFoundException();
        }
        int rowNum = sheet.getLastRowNum();
        QuesQuestionsAnswers questionsAnswers = null;
        List list = new ArrayList<QuesQuestionsAnswers>();  //最终要返回的list
        for(int i = 1; i <= rowNum; i++){
            questionsAnswers = new QuesQuestionsAnswers();
            XSSFRow row = sheet.getRow(i);
            //先判断是否为空再放入
            questionsAnswers.setQuestion(getCellStringValue(row.getCell(0)));
            questionsAnswers.setAnswer(getCellStringValue(row.getCell(1)));
            //根据输入的学科名称获取放入的id
            int id = getSubjectId(filePath, getCellStringValue(row.getCell(2)));
            questionsAnswers.setSubjectId(id);

            questionsAnswers.setLevel(getCellIntegerValue(row.getCell(3)));
            questionsAnswers.setTag(getCellStringValue(row.getCell(4)));
            list.add(questionsAnswers);
        }
        return list;
    }

    /**
     * 根据文件路径获取Excel工具表
     * @param filePath  文件路径
     * @param sheetName sheet名称
     * @return
     */
    private XSSFSheet getXSSFWorkbook(String filePath,String sheetName) throws IOException,FileNotFoundException{
        FileInputStream inputStream = new FileInputStream(filePath);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheet(sheetName);
        return sheet;
    }

    /**
     * 根据科目名称从模板文件中获取对应的id
     * @param SubjectName  科目名称
     * @return
     */
    private int getSubjectId(String filePath, String SubjectName) throws IOException,FileNotFoundException,SubjectNotFoundException{
        XSSFSheet sheet = getXSSFWorkbook(filePath, "学科");
        Integer targetRow = null;   //科目所在的行的索引号
        for(int i = 0; i <= sheet.getLastRowNum(); i++) {
            XSSFRow row = sheet.getRow(i);
            XSSFCell cell = row.getCell(0);
            if(cell.getStringCellValue().equals(SubjectName)){
                targetRow = i;
                break;
            }
        }
        //目标行检查
        if(targetRow == null){
            throw new SubjectNotFoundException();
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

    /**
     * 获取单元格内容（T或F）返回数据，
     * 如果是T则返回true，F返回false
     * 如果不为T或F则返回null
     * @param cell 输入的单元格对象
     * @return
     */
    private Boolean getCellBooleanValue(Cell cell){
        Boolean returnValue = null;
        String cellValue = cell.getStringCellValue();
        if("T".equals(cellValue)){
            return true;
        }else if("F".equals(cellValue)){
            return false;
        }else {
            return null;
        }
    }
}

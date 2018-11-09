package sample;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;




/**
 * Created by nguyennghi on 9/12/17.
 */
class XlsxFileAccess {
    private XSSFSheet Sheet;
    private FileInputStream fis;

    private static XSSFCellStyle createStyleForTitle(XSSFWorkbook workbook) {
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }


    XlsxFileAccess(String filename) {
        try {
            fis = new FileInputStream(new File(filename));
            XSSFWorkbook workBook = new XSSFWorkbook(fis);
            Sheet = workBook.getSheetAt(0);
        } catch (Exception e) {
            //
        }
    }

     ArrayList<ArrayList<String>> readData() {
        ArrayList<ArrayList<String>> content = new ArrayList<>();
        for (Row row : Sheet) { // Iterating over each column of Excel file
            //int b =0;
            ArrayList<String> lines = new ArrayList<>();
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        lines.add(cell.getStringCellValue());
                      //  b++;
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        lines.add(String.valueOf(cell.getNumericCellValue()));
                      //  b++;
                        break;
                    default:
                }
            }
            content.add(lines);
            //System.out.println(b);
        }
        return content;
    }


  static void writeDate(ArrayList<TestNode> content, String path, boolean middle)
    {
        XSSFWorkbook generalList = new XSSFWorkbook();
        XSSFSheet sheet = generalList.createSheet("Sheet1");
        Cell cell;
        Row row;
        XSSFCellStyle style = createStyleForTitle(generalList);
        row = sheet.createRow(0);
        cell = row.createCell(0);
        cell.setCellValue("MÃ MH");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue("NHÓM");
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellValue("TỔ");
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellValue("TÊN MÔN HỌC ");
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellValue("SS");
        cell.setCellStyle(style);
        cell = row.createCell(5);
        cell.setCellValue("THỨ");
        cell.setCellStyle(style);

        cell = row.createCell(6);
        cell.setCellValue("NGÀY");
        cell.setCellStyle(style);
        cell = row.createCell(7);
        cell.setCellValue("GIỜ");
        cell.setCellStyle(style);
        cell = row.createCell(8);
        cell.setCellValue("PHÒNG");
        cell.setCellStyle(style);
        cell = row.createCell(9);
        cell.setCellValue("THỜI LƯỢNG");
        cell.setCellStyle(style);

        XSSFWorkbook detailList = new XSSFWorkbook();
        XSSFSheet detailSheet = detailList.createSheet("Sheet1");
        Cell cell2;
        Row row2;
        XSSFCellStyle style2 = createStyleForTitle(detailList);
        row2 = detailSheet.createRow(0);
        cell2 = row2.createCell(0);
        cell2.setCellValue("MÃ SV");
        cell2.setCellStyle(style2);
        cell2 = row2.createCell(1);
        cell2.setCellValue("HỌ LÓT");
        cell2.setCellStyle(style2);
        cell2 = row2.createCell(2);
        cell2.setCellValue("TÊN");
        cell2.setCellStyle(style2);
        cell2 = row2.createCell(3);
        cell2.setCellValue("MÔN THI");
        cell2.setCellStyle(style2);
        cell2 = row2.createCell(4);
        cell2.setCellValue("MÃ MÔN THI");
        cell2.setCellStyle(style2);
        cell2 = row2.createCell(5);
        cell2.setCellValue("NGÀY");
        cell2.setCellStyle(style2);

        cell2 = row2.createCell(6);
        cell2.setCellValue("NHÓM");
        cell2.setCellStyle(style2);
        cell2 = row2.createCell(7);
        cell2.setCellValue("TỔ");
        cell2.setCellStyle(style2);
        cell2 = row2.createCell(8);
        cell2.setCellValue("GIỜ");
        cell2.setCellStyle(style2);
        cell2 = row2.createCell(9);
        cell2.setCellValue("PHÒNG");
        cell2.setCellStyle(style2);
        cell2 = row2.createCell(10);
        cell2.setCellValue("THỜI LƯƠNG");
        cell2.setCellStyle(style2);

        int generalCurrent = 1;
        int detailCurrent = 1;

        for(TestNode testNode: content)
        {

            Row general =sheet.createRow(generalCurrent);
            general.createCell(0).setCellValue(testNode.getSubjectCode());
            general.createCell(1).setCellValue(testNode.getGroup());
            general.createCell(2).setCellValue(testNode.getSubGroup());
            general.createCell(3).setCellValue(testNode.getSubjectName());
            general.createCell(4).setCellValue(testNode.getTotalNumber());
            general.createCell(5).setCellValue(testNode.getDateOfWeek());
            general.createCell(6).setCellValue(testNode.getDate());
            general.createCell(7).setCellValue(testNode.getTime());
            general.createCell(8).setCellValue(testNode.getRoom());
            general.createCell(9).setCellValue(testNode.getDuration());

            generalCurrent++;

            for(Student stu: testNode.getStudents())
            {
                Row detail = detailSheet.createRow(detailCurrent);
                detail.createCell(0).setCellValue(stu.getStudentCode());
                detail.createCell(1).setCellValue(stu.getLastName());
                detail.createCell(2).setCellValue(stu.getFirstName());
                detail.createCell(3).setCellValue(testNode.getSubjectName());
                detail.createCell(4).setCellValue(testNode.getSubjectCode());
                detail.createCell(5).setCellValue(testNode.getDate());
                detail.createCell(6).setCellValue(testNode.getGroup());
                detail.createCell(7).setCellValue(testNode.getSubGroup());
                detail.createCell(8).setCellValue(testNode.getTime());
                detail.createCell(9).setCellValue(testNode.getRoom());
                detail.createCell(10).setCellValue(testNode.getDuration());
                detailCurrent++;
            }
        }
        for(int i = 0; i< 10; i++)
            sheet.autoSizeColumn(i);
        for(int i = 0; i< 11; i++)
            detailSheet.autoSizeColumn(i);


    File exportSubjectList;

        File exportDetailList;
        if(middle) {
            exportSubjectList = new File(path + "/LichThiGiuaKyTongQuat.xlsx");
            exportDetailList = new File(path + "/LichThiGiuaKyChiTiet.xlsx");
        }
        else
        {
            exportSubjectList = new File(path + "/LichThiCuoiKyTongQuat.xlsx");
            exportDetailList = new File(path + "/LichThiCuoiKyChiTiet.xlsx");
        }
        try {
            FileOutputStream outFile1 = new FileOutputStream(exportSubjectList);
            generalList.write(outFile1);
            outFile1.close();
            generalList.close();
            FileOutputStream outFile2 = new FileOutputStream(exportDetailList);
            detailList.write(outFile2);
            outFile1.close();
            detailList.close();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    void close() {
        try {
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

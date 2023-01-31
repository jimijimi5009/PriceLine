package utility;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.util.LocaleUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelReader {

    public static List<Map<String, String>> getData(String excelFilePath, String sheetName)
            throws InvalidFormatException, IOException {
        Sheet sheet = getSheetByName(excelFilePath, sheetName);
        return readSheet(sheet);
    }

    public static List<Map<String, String>> getData(String excelFilePath, int sheetNumber)
            throws InvalidFormatException, IOException {
        Sheet sheet = getSheetByIndex(excelFilePath, sheetNumber);
        return readSheet(sheet);
    }

    public static void captureDataToExcel(String filePath,String sheetName,int rownum,
                                          int column,String data)
            throws EncryptedDocumentException, IOException, InterruptedException {


        try {

            if (!isFileClosed(filePath)) {
                System.out.println("Excel is open.");
                OsUtill.killAllProcesses("excel.exe");
            }

            FileInputStream fis = new FileInputStream(filePath);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheet(sheetName);

            //Getting age cell of first row from the sheet
            Cell cell= sheet.getRow(rownum).getCell(column);

            cell.setCellValue(data);
            FileOutputStream fos = new FileOutputStream(filePath);
            workbook.write(fos);
            fos.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void writeValueToCell(Object value, int rowIdx, int colIdx, Sheet sheet) {

        Row row = sheet.getRow(rowIdx);
        Cell cell;
        if (row == null) {
            cell = sheet.createRow(rowIdx).createCell(colIdx);
        } else {
            cell = row.getCell(colIdx);
            if (cell == null)
                cell = row.createCell(colIdx);
        }

        if (value == null)
            cell.setBlank();
        else if (value instanceof String)
            cell.setCellValue(value.toString());
        else if (value instanceof Integer)
            cell.setCellValue((Integer) value);
        else if (value instanceof Double)
            cell.setCellValue((Double) value);
        else if (value instanceof Date) {
            cell.setCellValue((Date) value);
            CellStyle style = sheet.getWorkbook().createCellStyle();
            style.setDataFormat(sheet.getWorkbook().getCreationHelper().createDataFormat().getFormat(("yyyy/m/d")));
            cell.setCellStyle(style);
        } else {
            cell.setCellValue("Unknown type");
        }
    }

    public static Map<String,String> getKeyValueData(String filelocation,String sheetName){

        Map<String,String> testData = new HashMap<String,String>();
        try {
            FileInputStream fileInputStream = new FileInputStream(filelocation);

            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheet(sheetName);
            int lastRowNumber = sheet.getLastRowNum();


            for (int i = 0; i < lastRowNumber; i++) {
                Row row = sheet.getRow(i);
                Cell keyCell= row.getCell(0);
                String key = keyCell.getStringCellValue().trim();
                Cell valueCell = row.getCell(1);
                String value = valueCell.getStringCellValue().trim();
                testData.put(key,value);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return testData;
    }

    public static List<String> getHeaderName(String fileLocation, String sheetName) throws IOException {
        List<String> requiredHeaders = new ArrayList<>();

        FileInputStream file = new FileInputStream(new File(fileLocation));
        Workbook workbook = new XSSFWorkbook(file);
        DataFormatter formatter = new DataFormatter();
        Sheet sheet = workbook.getSheet(sheetName);
        for (Cell cell : sheet.getRow(0)) {
            requiredHeaders.add(cell.getColumnIndex(), cell.getStringCellValue());
        }

        return requiredHeaders;
    }



    // helper method.....................
    private static Sheet getSheetByName(String excelFilePath, String sheetName) throws IOException, InvalidFormatException {
        Sheet sheet = getWorkBook(excelFilePath).getSheet(sheetName);
        return sheet;
    }

    private static Sheet getSheetByIndex(String excelFilePath, int sheetNumber) throws IOException, InvalidFormatException {
        Sheet sheet = getWorkBook(excelFilePath).getSheetAt(sheetNumber);
        return sheet;
    }

    private static Workbook getWorkBook(String excelFilePath) throws IOException, InvalidFormatException {
        return WorkbookFactory.create(new File(excelFilePath));
    }

    private static List<Map<String, String>> readSheet(Sheet sheet) {
        Row row;
        int totalRow = sheet.getPhysicalNumberOfRows();
        List<Map<String, String>> excelRows = new ArrayList<Map<String, String>>();
        int headerRowNumber = getHeaderRowNumber(sheet);
        if (headerRowNumber != -1) {
            int totalColumn = sheet.getRow(headerRowNumber).getLastCellNum();
            int setCurrentRow = 1;
            for (int currentRow = setCurrentRow; currentRow <= totalRow; currentRow++) {
                row = getRow(sheet, sheet.getFirstRowNum() + currentRow);
                LinkedHashMap<String, String> columnMapdata = new LinkedHashMap<String, String>();
                for (int currentColumn = 0; currentColumn < totalColumn; currentColumn++) {
                    columnMapdata.putAll(getCellValue(sheet, row, currentColumn));
                }
                excelRows.add(columnMapdata);
            }
        }
        return excelRows;
    }

    private static int getHeaderRowNumber(Sheet sheet) {
        Row row;
        int totalRow = sheet.getLastRowNum();
        for (int currentRow = 0; currentRow <= totalRow + 1; currentRow++) {
            row = getRow(sheet, currentRow);
            if (row != null) {
                int totalColumn = row.getLastCellNum();
                for (int currentColumn = 0; currentColumn < totalColumn; currentColumn++) {
                    Cell cell;
                    cell = row.getCell(currentColumn, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    if (cell.getCellType() == CellType.STRING) {
                        return row.getRowNum();

                    } else if (cell.getCellType() == CellType.NUMERIC) {
                        return row.getRowNum();

                    } else if (cell.getCellType() == CellType.BOOLEAN) {
                        return row.getRowNum();
                    } else if (cell.getCellType() == CellType.ERROR) {
                        return row.getRowNum();
                    }
                }
            }
        }
        return (-1);
    }

    private static Row getRow(Sheet sheet, int rowNumber) {
        return sheet.getRow(rowNumber);
    }

    private static LinkedHashMap<String, String> getCellValue(Sheet sheet, Row row, int currentColumn) {
        LinkedHashMap<String, String> columnMapdata = new LinkedHashMap<String, String>();
        Cell cell;
        if (row == null) {
            if (sheet.getRow(sheet.getFirstRowNum()).getCell(currentColumn, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
                    .getCellType() != CellType.BLANK) {
                String columnHeaderName = sheet.getRow(sheet.getFirstRowNum()).getCell(currentColumn)
                        .getStringCellValue();
                columnMapdata.put(columnHeaderName, "");
            }
        } else {
            cell = row.getCell(currentColumn, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            if (cell.getCellType() == CellType.STRING) {
                if (sheet.getRow(sheet.getFirstRowNum())
                        .getCell(cell.getColumnIndex(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
                        .getCellType() != CellType.BLANK) {
                    String columnHeaderName = sheet.getRow(sheet.getFirstRowNum()).getCell(cell.getColumnIndex())
                            .getStringCellValue();
                    columnMapdata.put(columnHeaderName, cell.getStringCellValue());
                }
            } else if (cell.getCellType() == CellType.NUMERIC) {
                if (sheet.getRow(sheet.getFirstRowNum())
                        .getCell(cell.getColumnIndex(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
                        .getCellType() != CellType.BLANK) {
                    String columnHeaderName = sheet.getRow(sheet.getFirstRowNum()).getCell(cell.getColumnIndex())
                            .getStringCellValue();
                    columnMapdata.put(columnHeaderName, NumberToTextConverter.toText(cell.getNumericCellValue()));
                }
            } else if (cell.getCellType() == CellType.BLANK) {
                if (sheet.getRow(sheet.getFirstRowNum())
                        .getCell(cell.getColumnIndex(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
                        .getCellType() != CellType.BLANK) {
                    String columnHeaderName = sheet.getRow(sheet.getFirstRowNum()).getCell(cell.getColumnIndex())
                            .getStringCellValue();
                    columnMapdata.put(columnHeaderName, "");
                }
            } else if (cell.getCellType() == CellType.BOOLEAN) {
                if (sheet.getRow(sheet.getFirstRowNum())
                        .getCell(cell.getColumnIndex(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
                        .getCellType() != CellType.BLANK) {
                    String columnHeaderName = sheet.getRow(sheet.getFirstRowNum()).getCell(cell.getColumnIndex())
                            .getStringCellValue();
                    columnMapdata.put(columnHeaderName, Boolean.toString(cell.getBooleanCellValue()));
                }
            } else if (cell.getCellType() == CellType.ERROR) {
                if (sheet.getRow(sheet.getFirstRowNum())
                        .getCell(cell.getColumnIndex(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
                        .getCellType() != CellType.BLANK) {
                    String columnHeaderName = sheet.getRow(sheet.getFirstRowNum()).getCell(cell.getColumnIndex())
                            .getStringCellValue();
                    columnMapdata.put(columnHeaderName, Byte.toString(cell.getErrorCellValue()));
                }
            }else if (cell.getCellType() == CellType.NUMERIC) {
                if (DateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    String columnHeaderName = sheet.getRow(sheet.getFirstRowNum()).getCell(cell.getColumnIndex())
                            .getStringCellValue();
                    columnMapdata.put(columnHeaderName, String.valueOf(cell.getDateCellValue()));
                }
            }
        }
        return columnMapdata;
    }

    public static int getRowCount(String filePath,String sheetName,int columnNum) throws InterruptedException, IOException {

        if (!isFileClosed(filePath)) {
            System.out.println("Excel is open.");
            OsUtill.killAllProcesses("excel.exe");        }

        FileInputStream fis = new FileInputStream(filePath);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheet(sheetName);
        int count = 0;
        for (Row row : sheet) {
            if (row.getCell(columnNum) != null) {
                count += 1;
            }
        }

      return count-1;
    }

    public static List<String> getDateData(String filePath, String sheetName, int columnNum) throws InterruptedException, IOException {


        List<String> dates = null;
        try {
            if (!isFileClosed(filePath)) {
                System.out.println("Excel is open.");
                OsUtill.killAllProcesses("excel.exe");
            }

            FileInputStream fis = new FileInputStream(filePath);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheet(sheetName);
            dates = new ArrayList<>();

            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                // For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch(cell.getCellType()) {
                        case STRING:
                            System.out.print(cell.getStringCellValue() + "\t\t");
                            break;
                        case NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell)) {

                                DataFormatter formatter = new DataFormatter();
                                FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

                                if (LocaleUtil.getUserLocale().equals(java.util.Locale.GERMANY)) {
                                    formatter.addFormat("m/d/yy", new java.text.SimpleDateFormat("dd.MM.yyyy"));
                                } else if (LocaleUtil.getUserLocale().equals(java.util.Locale.US)) {
                                    formatter.addFormat("m/d/yy", new java.text.SimpleDateFormat("MM/dd/yyyy"));
                                } else if (LocaleUtil.getUserLocale().equals(java.util.Locale.UK)) {
                                    formatter.addFormat("m/d/yy", new java.text.SimpleDateFormat("dd/MM/yyyy"));
                                }
                                String date = formatter.formatCellValue(cell,evaluator);
                                dates.add(date);
                            } else {
                                System.out.print(cell.getNumericCellValue() + "\t\t");
                            }
                            break;
                        case BOOLEAN:
                            System.out.print(cell.getBooleanCellValue() + "\t\t");
                            break;
                    }

                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return dates;
    }

    public static String arGetCellData(String filePath, String sheetName, int rownum, int colnum) throws IOException {


        FileInputStream fis = new FileInputStream(filePath);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheet(sheetName);
        XSSFRow row = sheet.getRow(rownum);
        XSSFCell cell = row.getCell(colnum);
        if (cell == null) { // use row.getCell(x, Row.CREATE_NULL_AS_BLANK) to avoid null cells
            //return true;
        }

        DataFormatter formatter = new DataFormatter();
        String data;
        try {
            data = formatter.formatCellValue(cell);
        } catch (Exception e) {
            data = "";
        }
        workbook.close();
        fis.close();
        return data;
    }

    public static boolean isFileClosed(String fileName) {
        File file = new File(fileName);
        return file.renameTo(file);
    }

    public static void arReadXLSXFile(String filePath) throws IOException {

            try {
                FileInputStream inputStr = new FileInputStream(filePath);
                XSSFWorkbook xssfWork = new XSSFWorkbook(inputStr) ;
                XSSFSheet sheet1 = xssfWork.getSheetAt(0);
                Iterator rowItr = sheet1.rowIterator();

                while ( rowItr.hasNext() ) {
                    XSSFRow row = (XSSFRow) rowItr.next();
                    System.out.println("ROW:-->");
                    Iterator cellItr = row.cellIterator();

                    while ( cellItr.hasNext() ) {
                        XSSFCell cell = (XSSFCell) cellItr.next();

                        if(cell==null){
                            System.out.println("diiim");
                        }
                       // System.out.println("CELL:-->"+cell.toString());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }




}



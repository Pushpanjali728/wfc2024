package com.webapp.apis.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.ss.usermodel.CellType;

import com.webapp.apis.masters.domain.Tutorial;



public class ExcelHelper {
  public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
  static String[] HEADERs = { "Id", "Title", "Description", "Published" };
  static String SHEET = "Tutorials";

  public static boolean hasExcelFormat(MultipartFile file) {

    if (!TYPE.equals(file.getContentType())) {
      return false;
    }

    return true;
  }

  public static ByteArrayInputStream tutorialsToExcel(List<Tutorial> tutorials) {

    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
      Sheet sheet = workbook.createSheet(SHEET);

      // Header
      Row headerRow = sheet.createRow(0);

      for (int col = 0; col < HEADERs.length; col++) {
        Cell cell = headerRow.createCell(col);
        cell.setCellValue(HEADERs[col]);
      }

      int rowIdx = 1;
      for (Tutorial tutorial : tutorials) {
        Row row = sheet.createRow(rowIdx++);

        row.createCell(0).setCellValue(tutorial.getId());
        row.createCell(1).setCellValue(tutorial.getTitle());
        row.createCell(2).setCellValue(tutorial.getDescription());
        row.createCell(3).setCellValue(tutorial.isPublished());
      }

      workbook.write(out);
      return new ByteArrayInputStream(out.toByteArray());
    } catch (IOException e) {
      throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
    }
  }

  public static List<Tutorial> excelToTutorials(InputStream is) {
    try {
      Workbook workbook = new XSSFWorkbook(is);

      Sheet sheet = workbook.getSheet(SHEET);
      Iterator<Row> rows = sheet.iterator();

      List<Tutorial> tutorials = new ArrayList<Tutorial>();

      int rowNumber = 0;
      while (rows.hasNext()) {
        Row currentRow = rows.next();

        // skip header
        if (rowNumber == 0) {
          rowNumber++;
          continue;
        }

        Iterator<Cell> cellsInRow = currentRow.iterator();

        Tutorial tutorial = new Tutorial();

        int cellIdx = 0;
        while (cellsInRow.hasNext()) {
          Cell currentCell = cellsInRow.next();

          switch (cellIdx) {
          case 0:
            tutorial.setId((long) currentCell.getNumericCellValue());
            break;

          case 1:
            tutorial.setTitle(currentCell.getStringCellValue());
            break;

          case 2:
            tutorial.setDescription(currentCell.getStringCellValue());
            break;

          case 3:
            tutorial.setPublished(currentCell.getStringCellValue());
            break;

          default:
            break;
          }

          cellIdx++;
        }

        tutorials.add(tutorial);
      }

      workbook.close();

      return tutorials;
    } catch (IOException e) {
      throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
    }
  }
  
  public static List<String> excelToTable(InputStream is) {
	    try {
	      Workbook workbook = new XSSFWorkbook(is);

	      Sheet sheet = workbook.getSheet(SHEET);
	      Iterator<Row> rows = sheet.iterator();

	      List<String> ls = new ArrayList<String>();

	      int rowNumber = 0;
	      while (rows.hasNext()) {
	        Row currentRow = rows.next();

	        // skip header
	       /* if (rowNumber == 0) {
	          rowNumber++;
	          continue;
	        }*/

	        Iterator<Cell> cellsInRow = currentRow.iterator();

	        String values = new String();

	        int cellIdx = 0;
	        while (cellsInRow.hasNext()) {
	          Cell currentCell = cellsInRow.next();
	          
	          CellType type = currentCell.getCellType();

	          if (type == CellType.STRING) {
                  System.out.printf("[%d, %d] = STRING; Value = %s%n",
                		  currentCell.getRowIndex(), currentCell.getColumnIndex(),
                		  currentCell.getRichStringCellValue().toString());
                  values += "'"+currentCell.getRichStringCellValue().toString() +"',";
              } else if (type == CellType.NUMERIC) {
                  System.out.printf("[%d, %d] = NUMERIC; Value = %f%n",
                		  currentCell.getRowIndex(), currentCell.getColumnIndex(),
                		  currentCell.getNumericCellValue());
                  values +=currentCell.getNumericCellValue() +",";
              } else if (type == CellType.BOOLEAN) {
                  System.out.printf("[%d, %d] = BOOLEAN; Value = %b%n",
                		  currentCell.getRowIndex(), currentCell.getColumnIndex(),
                		  currentCell.getBooleanCellValue());
                  values +=currentCell.getBooleanCellValue() +",";
              } else if (type == CellType.BLANK) {
                  System.out.printf("[%d, %d] = BLANK CELL%n",
                		  currentCell.getRowIndex(), currentCell.getColumnIndex());
              }
	          
	         

	          cellIdx++;
	        }

	        ls.add(values.substring(0, values.length()-1));
	      }

	      workbook.close();

	      return ls;
	    } catch (IOException e) {
	      throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
	    }
	  }
  
}

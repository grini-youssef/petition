package com.ensah.Petitions.Service.Export;

import com.ensah.Petitions.Entity.Signature;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Slf4j
public class SignatureExcelExport {

    private XSSFWorkbook workbook = new XSSFWorkbook() ;
    private XSSFSheet sheet;
    List<Signature> signatures ;

    public  SignatureExcelExport(List<Signature> signatures){
        this.signatures = signatures ;
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet(UUID.randomUUID().toString());

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "CIN", style);
        createCell(row, 1, "E-mail", style);
        createCell(row, 2, "Full Name", style);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines(String caseId) {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Signature signature : signatures) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, signature.getCin(), style);
            createCell(row, columnCount++, signature.getEmail(), style);
            createCell(row, columnCount++, signature.getFullName(), style);
        }
    }

    public void export(HttpServletResponse response,  String caseId) throws IOException {
        log.info("[StartExportingSignatures] exporting Signatures for case");
        writeHeaderLine();
        writeDataLines(caseId);
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();

    }
}

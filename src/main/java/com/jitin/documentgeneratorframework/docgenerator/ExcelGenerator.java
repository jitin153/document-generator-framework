package com.jitin.documentgeneratorframework.docgenerator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.jitin.documentgeneratorframework.exception.DocumentGeneratorException;
import com.jitin.documentgeneratorframework.util.DocumentGeneratorConstants;

public class ExcelGenerator implements DocumentGenerator {

	public byte[] getDocument(String processedtext, String watermark) {
		/*
		 * Since its a EXCEL file therefore we can't apply watermark on it. Watermark in
		 * only for PDF files.
		 */
		return getDocument(processedtext);
	}

	public byte[] getDocument(String processedText) {
		Document document = Jsoup.parse(processedText);
		Elements tables = document.getElementsByTag(DocumentGeneratorConstants.HTML_TABLE);
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(DocumentGeneratorConstants.EXCEL_SHEET);
		int rowCounter = 0;
		for (Element table : tables) {
			Elements rows = table.select(DocumentGeneratorConstants.HTML_TABLE_ROW);
			if (!rows.isEmpty()) {
				int i = 0;
				Elements tableHeders = rows.select(DocumentGeneratorConstants.HTML_TABLE_HEADER);
				if (!tableHeders.isEmpty()) {
					writeCells(sheet, tableHeders, rowCounter++);
					i = 1;
				}
				for (; i < rows.size(); i++) {
					Elements cells = rows.get(i).select(DocumentGeneratorConstants.HTML_TABLE_DATA);
					writeCells(sheet, cells, rowCounter++);
				}
			}
			sheet.createRow(rowCounter++);
		}
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			workbook.write(byteArrayOutputStream);
		} catch (Exception e) {
			System.out.println("Error occurred : " + e);
			throw new DocumentGeneratorException("Error while creating excel!");
		} finally {
			try {
				workbook.close();
			} catch (IOException e) {
				System.out.println("Error occurred : " + e);
			}
		}
		return byteArrayOutputStream.toByteArray();
	}

	private void writeCells(HSSFSheet sheet, Elements cells, int rowCounter) {
		Row excelRow = sheet.createRow(rowCounter);
		int cellNumber = 0;
		for (Element cell : cells) {
			Cell excelCell = excelRow.createCell(cellNumber++);
			excelCell.setCellValue(cell.text());
		}
	}

}

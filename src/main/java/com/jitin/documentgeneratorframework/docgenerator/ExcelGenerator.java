package com.jitin.documentgeneratorframework.docgenerator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jitin.documentgeneratorframework.exception.DocumentGeneratorException;
import com.jitin.documentgeneratorframework.model.DocumentRequest;
import com.jitin.documentgeneratorframework.util.DocumentGeneratorConstants;
import com.jitin.documentgeneratorframework.util.DocumentGeneratorHelper;

/**
 * 
 * @author Jitin Gangwar
 *
 */
public class ExcelGenerator implements DocumentGenerator {
	
	private static final Logger LOG = LoggerFactory.getLogger(ExcelGenerator.class);
	
	/** Generate & return the document as byte array.
	 * @param documentRequest
	 * @return byte[]
	 */
	public byte[] generateDocumentAndGetBytes(DocumentRequest request) {
		return getDocument(DocumentGeneratorHelper.getProcessedText(request));
	}
	
	/** Generate & write the document to the disk.
	 * @param documentRequest
	 */
	public void generateDocumentAndWriteToDisk(DocumentRequest request) {
		byte[] document = getDocument(DocumentGeneratorHelper.getProcessedText(request));
		DocumentGeneratorHelper.writeFile(request, document);
	}
	
	//public byte[] getDocument(String processedtext, String watermark) {
		/*
		 * Since its a EXCEL file therefore we can't apply watermark on it. Watermark in
		 * only for PDF files.
		 */
		//return getDocument(processedtext);
	//}

	private byte[] getDocument(String processedText) {
		Document document = Jsoup.parse(processedText);
		Elements tables = document.getElementsByTag(DocumentGeneratorConstants.HTML_TABLE);
		//HSSFWorkbook workbook = new HSSFWorkbook();
		Workbook workbook = new XSSFWorkbook();
		//HSSFSheet sheet = workbook.createSheet(DocumentGeneratorConstants.EXCEL_SHEET);
		Sheet sheet = workbook.createSheet(DocumentGeneratorConstants.EXCEL_SHEET);
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
			LOG.error("Error occurred: {}",e.getMessage());
			throw new DocumentGeneratorException("Error while creating excel!");
		} finally {
			try {
				workbook.close();
			} catch (IOException e) {
				LOG.error("Error occurred while closing the workbook: {}",e.getMessage());
			}
		}
		return byteArrayOutputStream.toByteArray();
	}

	//private void writeCells(HSSFSheet sheet, Elements cells, int rowCounter) {
	private void writeCells(Sheet sheet, Elements cells, int rowCounter) {
		Row excelRow = sheet.createRow(rowCounter);
		int cellNumber = 0;
		for (Element cell : cells) {
			Cell excelCell = excelRow.createCell(cellNumber++);
			excelCell.setCellValue(cell.text());
		}
	}
}

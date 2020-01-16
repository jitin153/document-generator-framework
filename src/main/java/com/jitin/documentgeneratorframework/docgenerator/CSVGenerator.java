package com.jitin.documentgeneratorframework.docgenerator;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.jitin.documentgeneratorframework.exception.DocumentGeneratorException;
import com.jitin.documentgeneratorframework.model.DocumentRequest;
import com.jitin.documentgeneratorframework.util.DocumentGeneratorConstants;

public class CSVGenerator implements DocumentGenerator {

	public byte[] getDocument(String processedtext, String watermark) {
		/*
		 * Since its a CSV file therefore we can't apply watermark on it.
		 */
		return getDocument(processedtext);
	}
	
	public byte[] getDocument(String processedText) {
		
		Document document = Jsoup.parse(processedText);
		Elements tables = document.getElementsByTag(DocumentGeneratorConstants.HTML_TABLE);
		Writer writer = null;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			writer = new BufferedWriter(new OutputStreamWriter(byteArrayOutputStream));
			for (Element table : tables) {
				Elements rows = table.select(DocumentGeneratorConstants.HTML_TABLE_ROW);
				if (!rows.isEmpty()) {
					int i = 0;
					Elements tableHeders = rows.select(DocumentGeneratorConstants.HTML_TABLE_HEADER);
					if (!tableHeders.isEmpty()) {
						writeData(writer, tableHeders);
						i = 1;
					}
					for (; i < rows.size(); i++) {
						Elements cells = rows.get(i).select(DocumentGeneratorConstants.HTML_TABLE_DATA);
						writeData(writer, cells);
					}
				}
				writer.write("\n");
			}
		} catch (Exception e) {
			System.out.println("Error occurred : " + e);
			throw new DocumentGeneratorException("Error while creating csv!");
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				System.out.println("Error occurred : " + e);
			}
		}
		return byteArrayOutputStream.toByteArray();
	}

	private void writeData(Writer writer, Elements cells) {
		try {
			for (Element cell : cells) {
				//writer.write(cell.text().concat(", ")); //--Text without double quotes.
				writer.write("\""+cell.text()+"\"".concat(", ")); //--Enclose text within double quotes.
			}
			writer.write("\n");
		} catch (Exception e) {
			System.out.println("Error occurred : " + e);
			throw new DocumentGeneratorException("Error while creating csv!");
		}
	}
}

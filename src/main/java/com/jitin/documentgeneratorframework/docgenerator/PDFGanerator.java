package com.jitin.documentgeneratorframework.docgenerator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.xhtmlrenderer.pdf.ITextRenderer;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.jitin.documentgeneratorframework.exception.DocumentGeneratorException;
import com.jitin.documentgeneratorframework.model.DocumentRequest;
import com.jitin.documentgeneratorframework.util.DocumentGeneratorConstants;

public class PDFGanerator implements DocumentGenerator {
	
	// --Call this method when you want to generate pdf with parsing XHTML content into XMLWorkerHelper.
	public byte[] getDocument(String processedtext) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		Document document = new Document();
		try {
			PdfWriter writer = PdfWriter.getInstance(document, byteArrayOutputStream);
			document.open();
			XMLWorkerHelper xMLWorkerHelper = XMLWorkerHelper.getInstance();
			InputStream inputStream = new ByteArrayInputStream(processedtext.getBytes(StandardCharsets.UTF_8));
			xMLWorkerHelper.parseXHtml(writer, document, inputStream, Charset.forName(DocumentGeneratorConstants.DEFAULT_ENCODING));
			document.close();
			return byteArrayOutputStream.toByteArray();
		} catch (Exception e) {
			System.out.println("Error occurred : "+e);
			throw new DocumentGeneratorException("Error while creating Pdf!");
		}
	}

	public byte[] getDocument(String processedtext, String watermark) {
		byte[] document = getDocument(processedtext);
		if (null!=watermark && watermark!="") {
			try {
				return applyWatermark(document, watermark);
			} catch (Exception e) {
				System.out.println("Error occurred : "+e);
				throw new DocumentGeneratorException("Error while creating Pdf!");
			}
		}
		return document;
	}
	
	/*public byte[] getDocument(DocumentRequest documentRequest) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		Document document = new Document();
		try {
			PdfWriter writer = PdfWriter.getInstance(document, byteArrayOutputStream);
			document.open();
			XMLWorkerHelper xMLWorkerHelper = XMLWorkerHelper.getInstance();
			InputStream inputStream = new ByteArrayInputStream(documentRequest.getProcessedText().getBytes(StandardCharsets.UTF_8));
			xMLWorkerHelper.parseXHtml(writer, document, inputStream, Charset.forName(DocumentGeneratorConstants.DEFAULT_ENCODING));
			document.close();
			if (null!=documentRequest.getWatermark() && documentRequest.getWatermark()!="") {
				return applyWatermark(byteArrayOutputStream.toByteArray(), documentRequest.getWatermark());
			} else {
				return byteArrayOutputStream.toByteArray();
			}
		} catch (Exception e) {
			System.out.println("Error occurred : "+e);
			throw new DocumentGeneratorException("Error while creating Pdf!");
		}
	}*/

	// --Call this method when you want to generate pdf without parsing XHTML content into XMLWorkerHelper.
	/*private byte[] generateDocumentFromProcessedText(DocRequest documentRequest) {
		ITextRenderer renderer = new ITextRenderer();
		renderer.setDocumentFromString(documentRequest.getProcessedText());
		renderer.getDocument();
		renderer.layout();
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			renderer.createPDF(byteArrayOutputStream);
			if (null!=documentRequest.getWatermark() && documentRequest.getWatermark()!="") {
				return applyWatermark(byteArrayOutputStream.toByteArray(), documentRequest.getWatermark());
			} else {
				return byteArrayOutputStream.toByteArray();
			}
		} catch (Exception e) {
			System.out.println("Error occurred : "+e);
			throw new DocumentGeneratorException("Error while creating Pdf!");
		}
	}*/

	private byte[] applyWatermark(byte[] source, String watermarkValue) throws IOException, DocumentException {
		PdfReader reader = new PdfReader(source);
		int totalPages = reader.getNumberOfPages();
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		PdfStamper stamper = new PdfStamper(reader, byteArrayOutputStream);
		Font font = new Font(FontFamily.HELVETICA, 80);
		Phrase phrase = new Phrase(watermarkValue, font);
		for (int i = 1; i <= totalPages; i++) {
			PdfContentByte over = stamper.getOverContent(i);
			over.saveState();
			PdfGState pdfGStae = new PdfGState();
			pdfGStae.setFillOpacity(0.1F);
			over.setGState(pdfGStae);
			ColumnText.showTextAligned(over, Element.ALIGN_CENTER, phrase, 297, 450, 45);
			over.restoreState();
		}
		stamper.close();
		reader.close();
		return byteArrayOutputStream.toByteArray();
	}

}

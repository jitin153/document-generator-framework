package com.jitin.documentgeneratorframework.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import com.jitin.documentgeneratorframework.docgenerator.DocumentGenerator;
import com.jitin.documentgeneratorframework.exception.DocumentGeneratorException;
import com.jitin.documentgeneratorframework.factory.DocumentFactory;
import com.jitin.documentgeneratorframework.factory.TemplateEngineFactory;
import com.jitin.documentgeneratorframework.model.DocumentRequest;
import com.jitin.documentgeneratorframework.model.DocumentType;
import com.jitin.documentgeneratorframework.templateprocessor.TemplateProcessor;

/**
 * 
 * @author Jitin Gangwar
 *
 */
public class DocumentGeneratorUtil {
	private DocumentGeneratorUtil() {

	}
	
	/**
	 * @param documentType
	 * @param processedtext
	 * @param watermark
	 * @return
	 */
	private static byte[] getDocument(DocumentType documentType, String processedtext, String watermark) {
		DocumentGenerator generateDocument = DocumentFactory.getInstance(documentType);
		if (null != processedtext && processedtext != "") {
			if (null != watermark && watermark != "") {
				return generateDocument.getDocument(processedtext, watermark);
			}
			return generateDocument.getDocument(processedtext);
		} else {
			throw new DocumentGeneratorException("Processed text cannot be null or blank.");
		}
	}
	
	/**
	 * Returns document as byte[]
	 * @param documentRequest
	 * @return byte[]
	 */
	public static byte[] getDocumentAsByteArray(DocumentRequest documentRequest) {
		if (null != documentRequest.getDocumentType()) {
			/*
			 * If processed text is already set is that case there is no need to process
			 * template again.
			 */
			if (null != documentRequest.getProcessedText() && documentRequest.getProcessedText() != "") {
				return getDocument(documentRequest.getDocumentType(), documentRequest.getProcessedText(),
						documentRequest.getWatermark());
			}
			/*
			 * If processed text not set in this case user has to provide the template
			 * engine to process the template.
			 */
			if (null != documentRequest.getTemplateEngine()) {
				TemplateProcessor templateProcessor = TemplateEngineFactory
						.getInstance(documentRequest.getTemplateEngine().getEngine());
				String processedText = templateProcessor.getProcessedText(documentRequest.getTemplateEngine());
				return getDocument(documentRequest.getDocumentType(), processedText, documentRequest.getWatermark());
			} else {
				throw new DocumentGeneratorException("TemplateEngine cannot be null.");
			}
		} else {
			throw new DocumentGeneratorException("DocumentType cannot be null.");
		}
	}

	/**
	 * Writes the file directly to the disk.
	 * @param outputDirectory
	 * @param fileName
	 * @param documentRequest
	 */
	public static void writeDocumentToDisk(String outputDirectory, String fileName, DocumentRequest documentRequest) {
		if (null != documentRequest) {
			if (null != fileName && fileName != "") {
				fileName = FileNameGenerator.generateFileName(fileName, documentRequest.getDocumentType());
			} else {
				fileName = FileNameGenerator.generateFileName(DocumentGeneratorConstants.FILE_NAME_PREFIX,
						documentRequest.getDocumentType());
			}
			if (null != outputDirectory && outputDirectory != "") {
				byte[] document = getDocumentAsByteArray(documentRequest);
				StringBuilder file = new StringBuilder(outputDirectory).append("/").append(fileName);
				try {
					Files.write(new File(file.toString()).toPath(), document);
				} catch (IOException e) {
					System.out.println("Error occurred : " + e);
				}
			} else {
				throw new DocumentGeneratorException("Output directory cannot be null or empty!");
			}
		} else {
			throw new DocumentGeneratorException("DocumentRequest cannot be null.");
		}
	}
}

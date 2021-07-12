package com.jitin.documentgeneratorframework.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jitin.documentgeneratorframework.exception.DocumentGeneratorException;
import com.jitin.documentgeneratorframework.factory.TemplateProcessorFactory;
import com.jitin.documentgeneratorframework.model.DocumentRequest;
import com.jitin.documentgeneratorframework.templateprocessor.TemplateProcessor;

/**
 * 
 * @author Jitin Gangwar
 *
 */
public class DocumentGeneratorHelper {

	private static final Logger LOG = LoggerFactory.getLogger(DocumentGeneratorHelper.class);

	private DocumentGeneratorHelper() {

	}

	public static void writeFile(DocumentRequest documentRequest, byte[] fileContent) {
		if (Objects.nonNull(documentRequest)) {
			String fileName = documentRequest.getFileName();
			if (StringUtils.isNotBlank(fileName)) {
				fileName = FileNameGenerator.generateFileName(fileName, documentRequest.getDocumentType());
			} else {
				fileName = FileNameGenerator.generateFileName(DocumentGeneratorConstants.FILE_NAME_PREFIX,
						documentRequest.getDocumentType());
			}
			if (StringUtils.isNotBlank(documentRequest.getOutputDirectory().toString())) {
				StringBuilder file = new StringBuilder(documentRequest.getOutputDirectory().toString()).append("/")
						.append(fileName);
				try {
					Files.write(new File(file.toString()).toPath(), fileContent);
				} catch (IOException e) {
					LOG.error("Error occurred: {}", e.getMessage());
				}
			} else {
				throw new DocumentGeneratorException("Output directory cannot be null or empty!");
			}
		} else {
			throw new DocumentGeneratorException("DocumentRequest cannot be null.");
		}
	}

	/*
	 * private static void validateRequest(DocumentRequest request) {
	 * if(Objects.isNull(request)) { throw new
	 * DocumentGeneratorException("Request object cannot be null."); }
	 * if(Objects.isNull(request.getTemplateEngine())) { throw new
	 * DocumentGeneratorException("Template engine cannot be null."); }
	 * if(Objects.isNull(request.getDocumentType())) { throw new
	 * DocumentGeneratorException("Document type cannot be null."); }
	 * if(StringUtils.isBlank(request.getVariableName())) { throw new
	 * DocumentGeneratorException("Variable name cannot be blank."); }
	 * if(Objects.isNull(request.getTemplateDirectory()) &&
	 * StringUtils.isBlank(request.getTemplateText())) { throw new
	 * DocumentGeneratorException("Template directory & template text both cannot be blank at the same time."
	 * ); } if(StringUtils.isBlank(request.getTemplateName())) { throw new
	 * DocumentGeneratorException("Template name cannot be blank."); }
	 * if(Objects.isNull(request.getData())) { throw new
	 * DocumentGeneratorException("Data cannot be blank."); } }
	 */
	public static String getProcessedText(DocumentRequest request) {
		if (Objects.isNull(request)) {
			throw new DocumentGeneratorException("Request object cannot be null.");
		}
		TemplateProcessor processor = TemplateProcessorFactory.getInstance(request.getTemplateEngine());
		return processor.getProcessedText(request);
	}

	/**
	 * @param documentType
	 * @param processedtext
	 * @param watermark
	 * @return
	 */
	/*
	 * private static byte[] getDocument(DocumentType documentType, String
	 * processedtext, String watermark) { DocumentGenerator generateDocument =
	 * DocumentFactory.getInstance(documentType); //if (null != processedtext &&
	 * processedtext != "") { if(StringUtils.isNotBlank(processedtext)) { //if (null
	 * != watermark && watermark != "") { if(StringUtils.isNotBlank(watermark)) {
	 * return generateDocument.getDocument(processedtext, watermark); } return
	 * generateDocument.getDocument(processedtext); } else { throw new
	 * DocumentGeneratorException("Processed text cannot be null or blank."); } }
	 */

	/**
	 * Returns document as byte[]
	 * 
	 * @param documentRequest
	 * @return byte[]
	 */
	/*
	 * public static byte[] getDocumentAsByteArray(DocumentRequest documentRequest)
	 * { if(Objects.nonNull(documentRequest) &&
	 * Objects.nonNull(documentRequest.getDocumentType())) { //if (null !=
	 * documentRequest.getDocumentType()) { /* If processed text is already set is
	 * that case there is no need to process template again.
	 */
	/*
	 * if(StringUtils.isNotBlank(documentRequest.getProcessedText())) { //if (null
	 * != documentRequest.getProcessedText() && documentRequest.getProcessedText()
	 * != "") { return getDocument(documentRequest.getDocumentType(),
	 * documentRequest.getProcessedText(), documentRequest.getWatermark()); }
	 */
	/*
	 * If processed text not set in this case user has to provide the template
	 * engine to process the template.
	 */
	/*
	 * if(Objects.nonNull(documentRequest.getTemplateEngine())) { //if (null !=
	 * documentRequest.getTemplateEngine()) { TemplateProcessor templateProcessor =
	 * TemplateEngineFactory
	 * .getInstance(documentRequest.getTemplateEngine().getEngine()); String
	 * processedText =
	 * templateProcessor.getProcessedText(documentRequest.getTemplateEngine());
	 * return getDocument(documentRequest.getDocumentType(), processedText,
	 * documentRequest.getWatermark()); } else { throw new
	 * DocumentGeneratorException("TemplateEngine cannot be null."); } } else {
	 * throw new DocumentGeneratorException("DocumentType cannot be null."); } }
	 */

	/**
	 * Writes the file directly to the disk.
	 * 
	 * @param outputDirectory
	 * @param fileName
	 * @param documentRequest
	 */
	/*
	 * public static void writeDocumentToDisk(String outputDirectory, String
	 * fileName, DocumentRequest documentRequest) {
	 * if(Objects.nonNull(documentRequest)) { //if (null != documentRequest) {
	 * if(StringUtils.isNotBlank(fileName)) { //if (null != fileName && fileName !=
	 * "") { fileName = FileNameGenerator.generateFileName(fileName,
	 * documentRequest.getDocumentType()); } else { fileName =
	 * FileNameGenerator.generateFileName(DocumentGeneratorConstants.
	 * FILE_NAME_PREFIX, documentRequest.getDocumentType()); }
	 * if(StringUtils.isNotBlank(outputDirectory)) { //if (null != outputDirectory
	 * && outputDirectory != "") { byte[] document =
	 * getDocumentAsByteArray(documentRequest); StringBuilder file = new
	 * StringBuilder(outputDirectory).append("/").append(fileName); try {
	 * Files.write(new File(file.toString()).toPath(), document); } catch
	 * (IOException e) { LOG.error("Error occurred: {}",e.getMessage()); } } else {
	 * throw new
	 * DocumentGeneratorException("Output directory cannot be null or empty!"); } }
	 * else { throw new
	 * DocumentGeneratorException("DocumentRequest cannot be null."); } }
	 */
}

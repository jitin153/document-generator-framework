package com.jitin.documentgeneratorframework;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jitin.documentgeneratorframework.docgenerator.DocumentGenerator;
import com.jitin.documentgeneratorframework.factory.DocumentGeneratorFactory;
import com.jitin.documentgeneratorframework.model.DocumentRequest;
import com.jitin.documentgeneratorframework.model.DocumentRequest.DocumentRequestBuilder;
import com.jitin.documentgeneratorframework.model.DocumentType;
import com.jitin.documentgeneratorframework.model.TemplateEngine;
import com.jitin.documentgeneratorframework.util.FileNameGenerator;

public class DocumentGeneratorTester {

	private static final Logger LOG = LoggerFactory.getLogger(DocumentGeneratorTester.class);

	public static void main(String[] args) {
		
		Address address = new Address(23, "Test street", "Test city", "Test state");
		Student student = new Student("Test College of Technology", 12, "Test Student", 89.7, Boolean.TRUE, address);
		
		DocumentType docType = DocumentType.PDF;

		// ---TESTING FREEMARKER TEMPLATE---
		
		//--PREPARING REQUEST
		DocumentRequest<Student> freemarkerRequest = new DocumentRequestBuilder<Student>(TemplateEngine.FREEMARKER,
				docType, "data").withData(student).withFileName("Report_Freemarker.xls")
						.withOutputDirectory(Paths.get(Constants.FILE_STORAGE_DIRECTORY))
						.withTemplateDirectory(Paths.get(Constants.FREEMARKER_TEMPLATE_DIRECTORY))
						.withTemplateName("studentReport.ftl").withWatermark("SAMPLE").build();
		
		DocumentGenerator freemarkerDocGenerator = DocumentGeneratorFactory
				.getInstance(freemarkerRequest.getDocumentType());
		/*
		 * USE CASE 1: Write file directly to the disk.
		 */
		freemarkerDocGenerator.generateDocumentAndWriteToDisk(freemarkerRequest);

		freemarkerRequest = new DocumentRequestBuilder<Student>(TemplateEngine.FREEMARKER, docType, "data")
				.withData(student).withTemplateDirectory(Paths.get(Constants.FREEMARKER_TEMPLATE_DIRECTORY))
				.withTemplateName("studentReport.ftl").withWatermark("SAMPLE").build();
		/*
		 * USE CASE 2: Get the file as byte array then write on disk.
		 */
		byte[] freemarkerFileContent1 = freemarkerDocGenerator.generateDocumentAndGetBytes(freemarkerRequest);
		writeFileFromByteArray(freemarkerRequest.getDocumentType(), "StudentResult_Freemarker", freemarkerFileContent1);

		/*
		 * Here we are passing string as template content while creating the object.
		 */
		String templateText = getTemplateText(
				Constants.FREEMARKER_TEMPLATE_DIRECTORY + "StudentReportTemplateText.txt");

		freemarkerRequest = new DocumentRequestBuilder<Student>(TemplateEngine.FREEMARKER, docType, "data")
				.withData(student).withFileName("Report_Freemarker")
				.withOutputDirectory(Paths.get(Constants.FILE_STORAGE_DIRECTORY)).withTemplateText(templateText)
				.withWatermark("SAMPLE").build();
		/*
		 * USE CASE 3: Write file directly to the disk.
		 */
		freemarkerDocGenerator.generateDocumentAndWriteToDisk(freemarkerRequest);

		freemarkerRequest = new DocumentRequestBuilder<Student>(TemplateEngine.FREEMARKER, docType, "data")
				.withData(student).withTemplateText(templateText).withWatermark("SAMPLE").build();
		/*
		 * USE CASE 4: Get the file as byte array then write on disk.
		 */
		byte[] freemarkerFileContent2 = freemarkerDocGenerator.generateDocumentAndGetBytes(freemarkerRequest);
		writeFileFromByteArray(freemarkerRequest.getDocumentType(), "StudentResult_Freemarker", freemarkerFileContent2);

		// ---TESTING VELOCITY TEMPLATE---

		//--PREPARING REQUEST
		DocumentRequest<Student> velocityRequest = new DocumentRequestBuilder<Student>(TemplateEngine.VELOCITY, docType,
				"data").withData(student).withFileName("Report_Velocity")
						.withOutputDirectory(Paths.get(Constants.FILE_STORAGE_DIRECTORY))
						.withTemplateDirectory(Paths.get(Constants.VELOCITY_TEMPLATE_DIRECTORY))
						.withTemplateName("studentReport.vm").withWatermark("SAMPLE").build();
		DocumentGenerator velocityDocGenerator = DocumentGeneratorFactory
				.getInstance(velocityRequest.getDocumentType());
		/*
		 * USE CASE 1: Write file directly to the disk.
		 */
		velocityDocGenerator.generateDocumentAndWriteToDisk(velocityRequest);

		velocityRequest = new DocumentRequestBuilder<Student>(TemplateEngine.VELOCITY, docType, "data")
				.withData(student).withTemplateDirectory(Paths.get(Constants.VELOCITY_TEMPLATE_DIRECTORY))
				.withTemplateName("studentReport.vm").withWatermark("SAMPLE").build();
		/*
		 * USE CASE 2: Get the file as byte array then write on disk.
		 */
		byte[] velocityFileContent1 = velocityDocGenerator.generateDocumentAndGetBytes(velocityRequest);
		writeFileFromByteArray(velocityRequest.getDocumentType(), "StudentResult_Velocity", velocityFileContent1);

		/*
		 * Here we are passing string as template content while creating the object.
		 */
		String velocityTemplateText = getTemplateText(
				Constants.VELOCITY_TEMPLATE_DIRECTORY + "StudentReportTemplateText.txt");

		velocityRequest = new DocumentRequestBuilder<Student>(TemplateEngine.VELOCITY, docType, "data")
				.withData(student).withFileName("Report_Velocity")
				.withOutputDirectory(Paths.get(Constants.FILE_STORAGE_DIRECTORY)).withTemplateText(velocityTemplateText)
				.withWatermark("SAMPLE").build();
		/*
		 * USE CASE 3: Write file directly to the disk.
		 */
		velocityDocGenerator.generateDocumentAndWriteToDisk(velocityRequest);

		velocityRequest = new DocumentRequestBuilder<Student>(TemplateEngine.VELOCITY, docType, "data")
				.withData(student).withTemplateText(velocityTemplateText).withWatermark("SAMPLE").build();
		/*
		 * USE CASE 4: Get the file as byte array then write on disk.
		 */
		byte[] velocityFreemarkerFileContent2 = velocityDocGenerator.generateDocumentAndGetBytes(velocityRequest);
		writeFileFromByteArray(velocityRequest.getDocumentType(), "StudentResult_Velocity",
				velocityFreemarkerFileContent2);

		// ---TESTING THYMELEAF TEMPLATE---
		
		//--PREPARING REQUEST
		DocumentRequest<Student> thymeleafRequest = new DocumentRequestBuilder<Student>(TemplateEngine.THYMELEAF,
				docType, "data").withData(student).withFileName("Report_Thymeleaf")
						.withOutputDirectory(Paths.get(Constants.FILE_STORAGE_DIRECTORY))
						.withTemplateDirectory(Paths.get(Constants.THYMELEAF_TEMPLATE_DIRECTORY))
						.withTemplateName("studentReport.html").withWatermark("SAMPLE").build();
		DocumentGenerator thymeleafDocGenerator = DocumentGeneratorFactory
				.getInstance(thymeleafRequest.getDocumentType());
		/*
		 * USE CASE 1: Write file directly to the disk.
		 */
		thymeleafDocGenerator.generateDocumentAndWriteToDisk(thymeleafRequest);

		thymeleafRequest = new DocumentRequestBuilder<Student>(TemplateEngine.THYMELEAF, docType, "data")
				.withData(student).withTemplateDirectory(Paths.get(Constants.THYMELEAF_TEMPLATE_DIRECTORY))
				.withTemplateName("studentReport.html").withWatermark("SAMPLE").build();
		/*
		 * USE CASE 2: Get the file as byte array then write on disk.
		 */
		byte[] thymeleafFileContent1 = thymeleafDocGenerator.generateDocumentAndGetBytes(thymeleafRequest);
		writeFileFromByteArray(thymeleafRequest.getDocumentType(), "StudentResult_Thymeleaf", thymeleafFileContent1);

		/*
		 * Here we are passing string as template content while creating the object.
		 */
		String thymeleafTemplateText = getTemplateText(
				Constants.THYMELEAF_TEMPLATE_DIRECTORY + "StudentReportTemplateText.txt");

		thymeleafRequest = new DocumentRequestBuilder<Student>(TemplateEngine.THYMELEAF, docType, "data")
				.withData(student).withFileName("Report_Thymeleaf")
				.withOutputDirectory(Paths.get(Constants.FILE_STORAGE_DIRECTORY))
				.withTemplateText(thymeleafTemplateText).withWatermark("SAMPLE").build();
		/*
		 * USE CASE 3: Write file directly to the disk.
		 */
		thymeleafDocGenerator.generateDocumentAndWriteToDisk(thymeleafRequest);

		thymeleafRequest = new DocumentRequestBuilder<Student>(TemplateEngine.THYMELEAF, docType, "data")
				.withData(student).withTemplateText(thymeleafTemplateText).withWatermark("SAMPLE").build();
		/*
		 * USE CASE 4: Get the file as byte array then write on disk.
		 */
		byte[] thymeleafFileContent2 = thymeleafDocGenerator.generateDocumentAndGetBytes(thymeleafRequest);
		writeFileFromByteArray(thymeleafRequest.getDocumentType(), "StudentResult_Thymeleaf", thymeleafFileContent2);
		LOG.info("File(s) created!");
	}
	
	/**
	 * Method to write file using byte array
	 * @param documentType
	 * @param filenameWithoutExtension
	 * @param fileContent
	 */
	private static void writeFileFromByteArray(DocumentType documentType, String filenameWithoutExtension,
			byte[] fileContent) {
		String filenameWithExtension = FileNameGenerator.generateFileName(filenameWithoutExtension, documentType);
		try {
			Files.write(new File(Constants.FILE_STORAGE_DIRECTORY + filenameWithExtension).toPath(), fileContent);
		} catch (IOException e) {
			LOG.error("Error occurred: {}",e.getMessage());
		}
	}
	
	/**
	 * Method to get template file content as string.
	 * @param path
	 * @return
	 */
	private static String getTemplateText(String path) {
		String templateText = "";
		try {
			templateText = new String(Files.readAllBytes(Paths.get(path)));
		} catch (IOException e) {
			LOG.error("Error occurred: {}",e.getMessage());
		}
		return templateText;
	}
}

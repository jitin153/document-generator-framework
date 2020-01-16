package com.jitin.documentgeneratorframework;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.jitin.documentgeneratorframework.model.DocumentRequest;
import com.jitin.documentgeneratorframework.model.DocumentRequest.DocumentRequestBuilder;
import com.jitin.documentgeneratorframework.model.DocumentType;
import com.jitin.documentgeneratorframework.model.Engine;
import com.jitin.documentgeneratorframework.model.TemplateEngine;
import com.jitin.documentgeneratorframework.model.TemplateEngine.TemplateEngineBuilder;
import com.jitin.documentgeneratorframework.util.DocumentGeneratorUtil;
import com.jitin.documentgeneratorframework.util.FileNameGenerator;

public class DocumentGeneratorTester {

	public static void main(String[] args) {

		Address addressDTO = new Address(23, "Test street", "Test city", "Test state");
		Student studentDTO = new Student("Test College of Technology", 12, "Test Student", 89.7, Boolean.TRUE,
				addressDTO);

		// ---TESTING FREEMARKER TEMPLATE---

		/*
		 * Here we are passing template directory and template while creating the object
		 * of TemplateEngine.
		 */
		TemplateEngine freemarkerTemplateEngine1 = new TemplateEngineBuilder(Engine.FREEMARKER, "data", studentDTO)
				.templateDirectory(Constants.FREEMARKER_TEMPLATE_DIRECTORY).templateName("StudentReport.ftl").build();
		DocumentRequest freemarkerRequest1 = new DocumentRequestBuilder(DocumentType.EXCEL).templateEngine(freemarkerTemplateEngine1)
				.watermark("SAMPLE").build();
		/*
		 * Write file directly to the disk.
		 */
		DocumentGeneratorUtil.writeDocumentToDisk(Constants.FILE_STORAGE_DIRECTORY, "StudentResult_Freemarker", freemarkerRequest1);
		/*
		 * Get the file as byte array then write on disk.
		 */
		byte[] freemarkerFileContent1 = DocumentGeneratorUtil.getDocumentAsByteArray(freemarkerRequest1);
		writeFileFromByteArray(freemarkerRequest1.getDocumentType(), "StudentResult_Freemarker", freemarkerFileContent1);

		/*
		 * Here we are passing string as template content while creating the object of
		 * TemplateEngine.
		 */
		String templateText = getTemplateText(
				Constants.FREEMARKER_TEMPLATE_DIRECTORY + "StudentReportTemplateText.txt");
		TemplateEngine freemarkerTemplateEngine2 = new TemplateEngineBuilder(Engine.FREEMARKER, "data", studentDTO)
				.templateText(templateText).build();
		DocumentRequest freemarkerRequest2 = new DocumentRequestBuilder(DocumentType.PDF).templateEngine(freemarkerTemplateEngine2)
				.watermark("SAMPLE").build();
		/*
		 * Write file directly to the disk.
		 */
		DocumentGeneratorUtil.writeDocumentToDisk(Constants.FILE_STORAGE_DIRECTORY, "StudentResult_Freemarker",
				freemarkerRequest2);
		/*
		 * Get the file as byte array then write on disk.
		 */
		byte[] freemarkerFileContent2 = DocumentGeneratorUtil.getDocumentAsByteArray(freemarkerRequest2);
		writeFileFromByteArray(freemarkerRequest1.getDocumentType(), "StudentResult_Freemarker", freemarkerFileContent2);
		

		// ---TESTING VELOCITY TEMPLATE---

		/*
		 * Here we are passing template directory and template while creating the object
		 * of TemplateEngine.
		 */
		TemplateEngine velocityTemplateEngine1 = new TemplateEngineBuilder(Engine.VELOCITY, "data", studentDTO)
				.templateDirectory(Constants.VELOCITY_TEMPLATE_DIRECTORY).templateName("StudentReport.vm").build();
		DocumentRequest velocityDocumentRequest1 = new DocumentRequestBuilder(DocumentType.EXCEL)
				.templateEngine(velocityTemplateEngine1).watermark("SAMPLE").build();
		/*
		 * Write file directly to the disk.
		 */
		DocumentGeneratorUtil.writeDocumentToDisk(Constants.FILE_STORAGE_DIRECTORY, "StudentResult_Velocity",
				velocityDocumentRequest1);
		/*
		 * Get the file as byte array then write on disk.
		 */
		byte[] velocityFileContent1 = DocumentGeneratorUtil.getDocumentAsByteArray(velocityDocumentRequest1);
		writeFileFromByteArray(velocityDocumentRequest1.getDocumentType(), "StudentResult_Velocity", velocityFileContent1);

		/*
		 * Here we are passing string as template content while creating the object of
		 * TemplateEngine.
		 */
		String velocityTemplateText = getTemplateText(
				Constants.VELOCITY_TEMPLATE_DIRECTORY + "StudentReportTemplateText.txt");
		TemplateEngine velocityTemplateEngine2 = new TemplateEngineBuilder(Engine.VELOCITY, "data", studentDTO)
				.templateText(velocityTemplateText).build();
		DocumentRequest velocityDocumentRequest2 = new DocumentRequestBuilder(DocumentType.PDF)
				.templateEngine(velocityTemplateEngine2).watermark("SAMPLE").build();
		/*
		 * Write file directly to the disk.
		 */
		DocumentGeneratorUtil.writeDocumentToDisk(Constants.FILE_STORAGE_DIRECTORY, "StudentResult_Velocity",
				velocityDocumentRequest2);
		/*
		 * Get the file as byte array then write on disk.
		 */
		byte[] velocityFileContent2 = DocumentGeneratorUtil.getDocumentAsByteArray(velocityDocumentRequest2);
		writeFileFromByteArray(velocityDocumentRequest1.getDocumentType(), "StudentResult_Velocity", velocityFileContent2);
		
		System.out.println("File(s) created!");
	}

	private static void writeFileFromByteArray(DocumentType documentType, String filenameWithoutExtension, byte[] fileContent) {
		String filenameWithExtension = FileNameGenerator.generateFileName(filenameWithoutExtension, documentType);
		try {
			Files.write(new File(Constants.FILE_STORAGE_DIRECTORY + filenameWithExtension).toPath(), fileContent);
		} catch (IOException e) {
			System.out.println("Error occurred : " + e);
		}
	}

	private static String getTemplateText(String path) {
		String templateText = "";
		try {
			templateText = new String(Files.readAllBytes(Paths.get(path)));
		} catch (IOException e) {
			System.out.println(e);
		}
		return templateText;
	}
}

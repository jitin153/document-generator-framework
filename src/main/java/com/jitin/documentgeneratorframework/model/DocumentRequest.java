package com.jitin.documentgeneratorframework.model;

import java.nio.file.Path;

public class DocumentRequest<T> {
	private TemplateEngine templateEngine;
	private Path templateDirectory;
	private String templateName;
	private String templateText;
	private Path outputDirectory;
	private String fileName;
	private String variableName;
	private T data;
	private DocumentType documentType;
	//private String processedHtml;
	private String watermark;
	//private TemplateProcessor templateProcessor;

	private DocumentRequest(TemplateEngine templateEngine, Path templateDirectory, String templateName,
			String templateText, Path outputDirectory, String fileName, String variableName, T data,
			DocumentType documentType, String watermark) {
		this.templateEngine = templateEngine;
		this.templateDirectory = templateDirectory;
		this.templateName = templateName;
		this.templateText = templateText;
		this.outputDirectory = outputDirectory;
		this.fileName = fileName;
		this.variableName = variableName;
		this.data = data;
		this.documentType = documentType;
		//this.processedHtml = processedHtml;
		this.watermark = watermark;
		//this.templateProcessor = templateProcessor;
	}

	public TemplateEngine getTemplateEngine() {
		return templateEngine;
	}

	public Path getTemplateDirectory() {
		return templateDirectory;
	}

	public String getTemplateName() {
		return templateName;
	}

	public String getTemplateText() {
		return templateText;
	}

	public Path getOutputDirectory() {
		return outputDirectory;
	}

	public String getFileName() {
		return fileName;
	}

	public String getVariableName() {
		return variableName;
	}

	public T getData() {
		return data;
	}

	public DocumentType getDocumentType() {
		return documentType;
	}

	public String getWatermark() {
		return watermark;
	}

	public static class DocumentRequestBuilder<T> {
		private TemplateEngine templateEngine;
		private Path templateDirectory;
		private String templateName;
		private String templateText;
		private Path outputDirectory;
		private String fileName;
		private String variableName;
		private T data;
		private DocumentType documentType;
		//private String processedHtml;
		private String watermark;
		//private TemplateProcessor templateProcessor;

		public DocumentRequestBuilder(TemplateEngine templateEngine, DocumentType documentType, String variableName) {
			this.templateEngine = templateEngine;
			this.documentType = documentType;
			this.variableName = variableName;
		}

		public DocumentRequestBuilder<T> withTemplateDirectory(Path templateDirectory) {
			this.templateDirectory = templateDirectory;
			return this;
		}

		public DocumentRequestBuilder<T> withTemplateName(String templateName) {
			this.templateName = templateName;
			return this;
		}

		public DocumentRequestBuilder<T> withOutputDirectory(Path outputDirectory) {
			this.outputDirectory = outputDirectory;
			return this;
		}

		public DocumentRequestBuilder<T> withFileName(String fileName) {
			this.fileName = fileName;
			return this;
		}

		/*
		 * public DocumentRequestBuilder<T> withVariableName(String variableName) {
		 * this.variableName = variableName; return this; }
		 */

		public DocumentRequestBuilder<T> withData(T data) {
			this.data = data;
			return this;
		}

		public DocumentRequestBuilder<T> withTemplateText(String templateText) {
			this.templateText = templateText;
			return this;
		}

		public DocumentRequestBuilder<T> withWatermark(String watermark) {
			this.watermark = watermark;
			return this;
		}

		public DocumentRequest<T> build() {
			return new DocumentRequest<T>(templateEngine, templateDirectory, templateName, templateText,
					outputDirectory, fileName, variableName, data, documentType, watermark);
		}
	}
}

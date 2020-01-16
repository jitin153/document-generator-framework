package com.jitin.documentgeneratorframework.model;

public class DocumentRequest {
	private DocumentType documentType;
	private TemplateEngine templateEngine;
	private String processedText;
	private String watermark;

	private DocumentRequest(DocumentType documentType, TemplateEngine templateEngine, String processedText,
			String watermark) {
		this.documentType = documentType;
		this.templateEngine = templateEngine;
		this.processedText = processedText;
		this.watermark = watermark;
	}

	public DocumentType getDocumentType() {
		return documentType;
	}

	public TemplateEngine getTemplateEngine() {
		return templateEngine;
	}

	public String getProcessedText() {
		return processedText;
	}

	public String getWatermark() {
		return watermark;
	}

	public static class DocumentRequestBuilder {
		private DocumentType documentType;
		private TemplateEngine templateEngine;
		private String processedText;
		private String watermark;
		
		public DocumentRequestBuilder(DocumentType documentType) {
			this.documentType = documentType;
		}

		public DocumentRequestBuilder templateEngine(TemplateEngine templateEngine) {
			this.templateEngine = templateEngine;
			return this;
		}

		public DocumentRequestBuilder processedText(String processedText) {
			this.processedText = processedText;
			return this;
		}

		public DocumentRequestBuilder watermark(String watermark) {
			this.watermark = watermark;
			return this;
		}
		public DocumentRequest build() {
			return new DocumentRequest(documentType,templateEngine,processedText,watermark);
		}
		
	}

}

package com.jitin.documentgeneratorframework.docgenerator;

import com.jitin.documentgeneratorframework.model.DocumentRequest;

public interface DocumentGenerator {
	//public byte[] getDocument(String processedtext);
	/*
	 * To generate the PDF with watermark.
	 */
	//public byte[] getDocument(String processedtext, String watermark);
	public byte[] generateDocumentAndGetBytes(DocumentRequest request);
	public void generateDocumentAndWriteToDisk(DocumentRequest request);
}

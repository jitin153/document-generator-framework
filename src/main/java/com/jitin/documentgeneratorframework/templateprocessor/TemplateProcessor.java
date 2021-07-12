package com.jitin.documentgeneratorframework.templateprocessor;

import com.jitin.documentgeneratorframework.model.DocumentRequest;

public interface TemplateProcessor {
	public String getProcessedText(DocumentRequest request);
}

package com.jitin.documentgeneratorframework.templateprocessor;

import com.jitin.documentgeneratorframework.model.TemplateEngine;

public interface TemplateProcessor {
	public String getProcessedText(TemplateEngine templateEngine);
}

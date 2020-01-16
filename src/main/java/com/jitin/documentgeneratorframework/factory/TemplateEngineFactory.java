package com.jitin.documentgeneratorframework.factory;

import com.jitin.documentgeneratorframework.exception.DocumentGeneratorException;
import com.jitin.documentgeneratorframework.model.Engine;
import com.jitin.documentgeneratorframework.templateprocessor.FreemarkerTemplateProcessor;
import com.jitin.documentgeneratorframework.templateprocessor.TemplateProcessor;
import com.jitin.documentgeneratorframework.templateprocessor.VelocityTemplateProcessor;

public class TemplateEngineFactory {
	public static TemplateProcessor getInstance(Engine templateEngine) {
		switch (templateEngine) {
		case FREEMARKER:
			return new FreemarkerTemplateProcessor();
		case VELOCITY:
			return new VelocityTemplateProcessor();
		default:
			throw new DocumentGeneratorException("Error while returning instance!");
		}
	}
}

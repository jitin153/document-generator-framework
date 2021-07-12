package com.jitin.documentgeneratorframework.factory;

import java.util.Objects;

import com.jitin.documentgeneratorframework.exception.DocumentGeneratorException;
import com.jitin.documentgeneratorframework.model.TemplateEngine;
import com.jitin.documentgeneratorframework.templateprocessor.FreemarkerTemplateProcessor;
import com.jitin.documentgeneratorframework.templateprocessor.TemplateProcessor;
import com.jitin.documentgeneratorframework.templateprocessor.ThymeleafTemplateProcessor;
import com.jitin.documentgeneratorframework.templateprocessor.VelocityTemplateProcessor;

public class TemplateProcessorFactory {

	private TemplateProcessorFactory() {
	}

	public static TemplateProcessor getInstance(TemplateEngine templateEngine) {
		if (Objects.nonNull(templateEngine)) {
			switch (templateEngine) {
			case FREEMARKER:
				return new FreemarkerTemplateProcessor();
			case VELOCITY:
				return new VelocityTemplateProcessor();
			case THYMELEAF:
				return new ThymeleafTemplateProcessor();
			default:
				throw new DocumentGeneratorException("Error while returning instance!");
			}
		} else {
			throw new DocumentGeneratorException("Template engine cannot be null.");
		}
	}
}

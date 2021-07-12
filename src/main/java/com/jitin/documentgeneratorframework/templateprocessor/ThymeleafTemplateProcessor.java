package com.jitin.documentgeneratorframework.templateprocessor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import com.jitin.documentgeneratorframework.exception.DocumentGeneratorException;
import com.jitin.documentgeneratorframework.model.DocumentRequest;

public class ThymeleafTemplateProcessor implements TemplateProcessor {

	private static final Logger LOG = LoggerFactory.getLogger(ThymeleafTemplateProcessor.class);
	
	public String getProcessedText(DocumentRequest request) {
		String templateText = "";
		if (StringUtils.isNotBlank(request.getTemplateText())) {
			templateText = request.getTemplateText();
		} else if (StringUtils.isNotBlank(request.getTemplateDirectory().toString())
				&& StringUtils.isNotBlank(request.getTemplateName())) {
			templateText = getTemplateAsString(request);
		} else {
			throw new DocumentGeneratorException("No template found for processing!");
		}
		StringTemplateResolver templateResolver = new StringTemplateResolver();
		// templateResolver.setOrder(1);
		templateResolver.setTemplateMode(TemplateMode.HTML);
		// templateResolver.setCacheable(false);

		org.thymeleaf.TemplateEngine engine = new org.thymeleaf.TemplateEngine();
		engine.setTemplateResolver(templateResolver);

		Context context = new Context();
		context.setVariable(request.getVariableName(), request.getData());

		return engine.process(templateText, context);
	}

	private String getTemplateAsString(DocumentRequest request) {
		String templateText = "";
		try {
			templateText = new String(
					Files.readAllBytes(Paths.get(request.getTemplateDirectory().toString() + "/" + request.getTemplateName())));
		} catch (IOException e) {
			LOG.error(e.getMessage());
		}
		return templateText;
	}
}

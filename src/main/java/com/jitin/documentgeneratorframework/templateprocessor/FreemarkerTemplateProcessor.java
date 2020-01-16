package com.jitin.documentgeneratorframework.templateprocessor;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.jitin.documentgeneratorframework.exception.DocumentGeneratorException;
import com.jitin.documentgeneratorframework.model.TemplateEngine;
import com.jitin.documentgeneratorframework.util.DocumentGeneratorConstants;

import freemarker.cache.StringTemplateLoader;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateNotFoundException;

public class FreemarkerTemplateProcessor implements TemplateProcessor {

	public String getProcessedText(TemplateEngine templateEngine) {
		Configuration configuration = new Configuration(Configuration.VERSION_2_3_26);
		configuration.setDefaultEncoding(DocumentGeneratorConstants.DEFAULT_ENCODING);
		configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		configuration.setLogTemplateExceptions(false);
		String templateName = DocumentGeneratorConstants.DEFAULT_TEMPLATE_NAME;
		if (null != templateEngine.getTemplateText() && templateEngine.getTemplateText() != "") {
			this.processTemplateFromString(configuration, templateName, templateEngine.getTemplateText());
		} else if (null != templateEngine.getTemplateDirectory() && templateEngine.getTemplateName() != "") {
			templateName = templateEngine.getTemplateName();
			this.processTemplateFromFile(configuration, templateEngine.getTemplateDirectory(), templateName);
		} else {
			throw new DocumentGeneratorException("Some required properties were null or empty!");
		}
		String processedText = "";
		Template template;
		try {
			template = configuration.getTemplate(templateName);
			processedText = processTemplate(template, templateEngine.getContext(), templateEngine.getData());
		} catch (TemplateNotFoundException e) {
			System.out.println("Error occurred : " + e);
		} catch (MalformedTemplateNameException e) {
			System.out.println("Error occurred : " + e);
		} catch (ParseException e) {
			System.out.println("Error occurred : " + e);
		} catch (IOException e) {
			System.out.println("Error occurred : " + e);
		}
		return processedText;
	}

	private void processTemplateFromString(Configuration configuration, String templateName, String templateText) {
		StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
		stringTemplateLoader.putTemplate(templateName, templateText);
		configuration.setTemplateLoader(stringTemplateLoader);
	}

	private void processTemplateFromFile(Configuration configuration, String templateDirectory, String templateName) {
		try {
			configuration.setDirectoryForTemplateLoading(new File(templateDirectory + "/"));
		} catch (IOException e) {
			System.out.println("Error occurred : " + e);
		}
	}

	private String processTemplate(Template template, String contextName, Object data) {
		String processedText = "";
		try {
			Map<String, Object> root = new HashMap<String, Object>();
			root.put(contextName, data);
			Writer writer = new StringWriter();
			try {
				template.process(root, writer);
				processedText = processTemplateIntoString(template, root);
			} catch (TemplateException e) {
				System.out.println("Error occurred : " + e);
			}
		} catch (IOException e) {
			System.out.println("Error occurred : " + e);
		}
		return processedText;
	}

	private static String processTemplateIntoString(Template template, Object model)
			throws IOException, TemplateException {
		StringWriter result = new StringWriter();
		template.process(model, result);
		return result.toString();
	}
}

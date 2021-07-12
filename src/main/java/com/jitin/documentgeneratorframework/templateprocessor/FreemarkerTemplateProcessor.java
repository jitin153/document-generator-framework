package com.jitin.documentgeneratorframework.templateprocessor;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jitin.documentgeneratorframework.exception.DocumentGeneratorException;
import com.jitin.documentgeneratorframework.model.DocumentRequest;
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

	private static final Logger LOG = LoggerFactory.getLogger(FreemarkerTemplateProcessor.class);

	public String getProcessedText(DocumentRequest request) {
		Configuration configuration = new Configuration(Configuration.VERSION_2_3_26);
		configuration.setDefaultEncoding(DocumentGeneratorConstants.DEFAULT_ENCODING);
		configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		configuration.setLogTemplateExceptions(false);
		String templateName = DocumentGeneratorConstants.DEFAULT_TEMPLATE_NAME;
		if (null != request.getTemplateText() && request.getTemplateText() != "") {
			this.processTemplateFromString(configuration, templateName, request.getTemplateText());
		} else if (null != request.getTemplateDirectory() && request.getTemplateName() != "") {
			templateName = request.getTemplateName();
			this.processTemplateFromFile(configuration, request.getTemplateDirectory().toString()+"/");
		} else {
			throw new DocumentGeneratorException("Some required properties were null or empty!");
		}
		String processedText = "";
		Template template;
		try {
			template = configuration.getTemplate(templateName);
			processedText = processTemplate(template, request.getVariableName(), request.getData());
		} catch (TemplateNotFoundException e) {
			LOG.error(e.getMessage());
		} catch (MalformedTemplateNameException e) {
			LOG.error(e.getMessage());
		} catch (ParseException e) {
			LOG.error(e.getMessage());
		} catch (IOException e) {
			LOG.error(e.getMessage());
		}
		return processedText;
	}

	private void processTemplateFromString(Configuration configuration, String templateName, String templateText) {
		StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
		stringTemplateLoader.putTemplate(templateName, templateText);
		configuration.setTemplateLoader(stringTemplateLoader);
	}

	private void processTemplateFromFile(Configuration configuration, String templateDirectory) {
		try {
			configuration.setDirectoryForTemplateLoading(new File(templateDirectory));
		} catch (IOException e) {
			LOG.error(e.getMessage());
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
				LOG.error(e.getMessage());
			}
		} catch (IOException e) {
			LOG.error(e.getMessage());
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

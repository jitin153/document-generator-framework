package com.jitin.documentgeneratorframework.templateprocessor;

import java.io.StringReader;
import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.RuntimeSingleton;
import org.apache.velocity.runtime.parser.ParseException;
import org.apache.velocity.runtime.parser.node.SimpleNode;

import com.jitin.documentgeneratorframework.exception.DocumentGeneratorException;
import com.jitin.documentgeneratorframework.model.TemplateEngine;
import com.jitin.documentgeneratorframework.util.DocumentGeneratorConstants;

public class VelocityTemplateProcessor implements TemplateProcessor {

	public String getProcessedText(TemplateEngine templateEngine) {
		Template template;
		if (null != templateEngine.getTemplateText()) {
			template = this.processTemplateFromString(templateEngine.getTemplateText());
		} else if (null != templateEngine.getTemplateDirectory() && templateEngine.getTemplateName() != "") {
			template = this.processTemplateFromFile(templateEngine.getTemplateDirectory(),
					templateEngine.getTemplateName());
		} else {
			throw new DocumentGeneratorException("Some required properties were null or empty!");
		}
		return this.processTemplate(template, templateEngine.getContext(), templateEngine.getData());
	}

	private Template processTemplateFromFile(String templateDirectory, String templateName) {
		VelocityEngine velocityEngine = new VelocityEngine();
		velocityEngine.init();
		StringBuilder velocityTemplate = new StringBuilder(templateDirectory).append(templateName);
		return velocityEngine.getTemplate(velocityTemplate.toString());
	}

	private Template processTemplateFromString(String templateText) {
		RuntimeServices runtimeServices = RuntimeSingleton.getRuntimeServices();
		StringReader stringReader = new StringReader(templateText);
		SimpleNode simpleNode;
		Template template = new Template();
		try {
			simpleNode = runtimeServices.parse(stringReader, DocumentGeneratorConstants.DEFAULT_TEMPLATE_NAME);
			template.setRuntimeServices(runtimeServices);
			template.setData(simpleNode);
			template.initDocument();
		} catch (ParseException e) {
			System.out.println("Error occurred : " + e);
		}
		return template;
	}

	private String processTemplate(Template template, String contextName, Object data) {
		VelocityContext velocityContext = new VelocityContext();
		velocityContext.put(contextName, data);

		StringWriter stringWriter = new StringWriter();
		template.merge(velocityContext, stringWriter);
		return stringWriter.toString();
	}
}

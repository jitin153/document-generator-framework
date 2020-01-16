package com.jitin.documentgeneratorframework.model;

public class TemplateEngine<T>{
	private Engine engine;
	private String templateDirectory;
	private String templateName;
	private String templateText;
	private String context;
	private T data;

	private TemplateEngine(Engine engine, String templateDirectory, String templateName, String templateText, String context, T data) {
		this.engine = engine;
		this.templateDirectory = templateDirectory;
		this.templateName = templateName;
		this.templateText = templateText;
		this.context = context;
		this.data = data;
	}

	public Engine getEngine() {
		return engine;
	}

	public String getTemplateDirectory() {
		return templateDirectory;
	}

	public String getTemplateName() {
		return templateName;
	}

	public String getTemplateText() {
		return templateText;
	}

	public String getContext() {
		return context;
	}

	public T getData() {
		return data;
	}

	public static class TemplateEngineBuilder<T>{
		private Engine engine;
		private String templateDirectory;
		private String templateName;
		private String templateText;
		private String context;
		private T data;
		
		public TemplateEngineBuilder(Engine engine, String context, T data){
			this.engine=engine;
			this.context=context;
			this.data = data;
		}
		
		public TemplateEngineBuilder templateDirectory(String templateDirectory) {
			this.templateDirectory = templateDirectory;
			return this;
		}

		public TemplateEngineBuilder templateName(String templateName) {
			this.templateName = templateName;
			return this;
		}

		public TemplateEngineBuilder templateText(String templateText) {
			this.templateText = templateText;
			return this;
		}
		
		public TemplateEngineBuilder data(T data) {
			this.data = data;
			return this;
		}

		public TemplateEngine build() {
			return new TemplateEngine(engine,templateDirectory,templateName,templateText,context,data);
		}
	}
}

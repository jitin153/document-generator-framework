package com.jitin.documentgeneratorframework.factory;

import com.jitin.documentgeneratorframework.docgenerator.CSVGenerator;
import com.jitin.documentgeneratorframework.docgenerator.DocumentGenerator;
import com.jitin.documentgeneratorframework.docgenerator.ExcelGenerator;
import com.jitin.documentgeneratorframework.docgenerator.PDFGanerator;
import com.jitin.documentgeneratorframework.exception.DocumentGeneratorException;
import com.jitin.documentgeneratorframework.model.DocumentType;

public class DocumentFactory {
	public static DocumentGenerator getInstance(DocumentType documentType) {
		switch (documentType) {
		case PDF:
			return new PDFGanerator();
		case EXCEL:
			return new ExcelGenerator();
		case CSV:
			return new CSVGenerator();
		default:
			throw new DocumentGeneratorException("Error while returning instance!");
		}
	}
}

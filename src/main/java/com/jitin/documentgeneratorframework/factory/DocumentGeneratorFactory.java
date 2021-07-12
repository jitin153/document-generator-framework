package com.jitin.documentgeneratorframework.factory;

import java.util.Objects;

import com.jitin.documentgeneratorframework.docgenerator.CSVGenerator;
import com.jitin.documentgeneratorframework.docgenerator.DocumentGenerator;
import com.jitin.documentgeneratorframework.docgenerator.ExcelGenerator;
import com.jitin.documentgeneratorframework.docgenerator.PDFGanerator;
import com.jitin.documentgeneratorframework.exception.DocumentGeneratorException;
import com.jitin.documentgeneratorframework.model.DocumentType;

public class DocumentGeneratorFactory {

	private DocumentGeneratorFactory() {
	}

	public static DocumentGenerator getInstance(DocumentType documentType) {
		if (Objects.nonNull(documentType)) {
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
		} else {
			throw new DocumentGeneratorException("Document type cannot be null.");
		}
	}
}

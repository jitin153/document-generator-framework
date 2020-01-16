package com.jitin.documentgeneratorframework.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.jitin.documentgeneratorframework.exception.DocumentGeneratorException;
import com.jitin.documentgeneratorframework.model.DocumentType;

public class FileNameGenerator {
	private FileNameGenerator() {
		
	}
	public static String generateFileName(String prefix, DocumentType documentType) {
		StringBuilder fileName = new StringBuilder(prefix).append("_")
				.append(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
		if(null != documentType) {
		switch (documentType) {
		case PDF:
			return fileName.append(".pdf").toString();
		case EXCEL:
			return fileName.append(".xls").toString();
		case CSV:
			return fileName.append(".csv").toString();
		default:
			throw new DocumentGeneratorException("Error while generating the file name!");
		}
		}else {
			throw new DocumentGeneratorException("DocumentType cannot be null.");
		}
	}
}

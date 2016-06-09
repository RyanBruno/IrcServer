package com.rbruno.irc.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class Utilities {

	public static List<String> read(String fileName) throws IOException {
		return Files.readAllLines(new File(fileName).toPath(), Charset.defaultCharset());
	}
	
	public static void makeFile(String fileName) throws IOException {
		InputStream inputStream = Utilities.class.getResourceAsStream("/" + fileName);
		Files.copy(inputStream, new File(fileName).toPath(), StandardCopyOption.REPLACE_EXISTING);
	}
	
}

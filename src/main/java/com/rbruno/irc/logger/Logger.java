package com.rbruno.irc.logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

public class Logger {

	/**
	 * Logs a message to the console and the log file.
	 * 
	 * @param message The message in which needs to be logged.
	 * @param level The level of the log.
	 */
	public static void log(String message, Level level) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd H:mm:ss ");
		String formattedDate = sdf.format(date);
		String formattedMessage = formattedDate + "[" + level.toString() + "] " + message;
		System.out.println(formattedMessage);
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("log.txt", true)));
			out.println(formattedMessage);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Logs a message with the console and the log file. Defaults to using the
	 * INFO level.
	 * 
	 * @param string The message in which needs to be logged.
	 */
	public static void log(String string) {
		log(string, Level.INFO);
	}

}

package com.nextgeninno.beanlogd.application;

import org.apache.log4j.spi.LoggingEvent;

import com.google.gson.Gson;

/**
 * Class to represent a log4j LoggingEvent. Serializable to a byte Json representation.
 * @author Joachim van der Herten
 *
 */
public class LogMessage {
	
	private String priority;
	private String logger;
	private String message;
	private long stamp;
	
	public LogMessage(String priority, String logger, String message, long timestamp) {
		this.priority = priority;
		this.logger = logger;
		this.message = message;
		this.stamp = timestamp;
	}
	
	public LogMessage(LoggingEvent arg0) {
		this(arg0.getLevel().toString(), arg0.getLoggerName(), arg0.getMessage().toString(), arg0.getTimeStamp());
	}

	public byte[] serialize() {
		return new Gson().toJson(this, LogMessage.class).getBytes();
	}
	
	public static LogMessage unserialize(byte[] data) {
		return new Gson().fromJson(new String(data), LogMessage.class);
	}

	public String getPriority() {
		return priority;
	}

	public String getLogger() {
		return logger;
	}

	public String getMessage() {
		return message;
	}

	public long getStamp() {
		return stamp;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this, LogMessage.class);
	}
	
	

}

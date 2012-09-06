package com.nextgeninno.beanlogd.appender;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

import com.nextgeninno.beanlogd.application.LogMessage;
import com.surftools.BeanstalkClientImpl.ClientImpl;

/**
 * Log4J appender class. 
 * Expects a connection string of the format <host>:<port>.
 * See README for details on how to integrate this appender into 
 * your existing projects to intercept the log4j message flow.
 * @author Joachim van der Herten
 *
 */
public class BeanStalkAppender extends AppenderSkeleton {

	protected String connection;
	
	private ClientImpl bean;

	public BeanStalkAppender() {
	}

	public String getConnection() {
		return connection;
	}

	public void setConnection(String connection) {
		this.connection = connection;
		connect(connection.split(":")[0], Integer.parseInt(connection.split(":")[1]));
	}
	
	private void connect(String host, int port) {
		bean = new ClientImpl(host, port);
		bean.useTube("logging");
	}

	@Override
	protected void append(LoggingEvent arg0) {
		bean.put(0, 0, 0, new LogMessage(arg0).serialize());
	}
	
	public void close() {
		bean.close();
	}
	
	public boolean requiresLayout() {
		return false;
	}

}

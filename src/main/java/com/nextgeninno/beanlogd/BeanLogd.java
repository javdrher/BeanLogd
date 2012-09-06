package com.nextgeninno.beanlogd;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.nextgeninno.beanlogd.application.Application;

/**
 * Remote logging system developed at Next Generation Innovation. 
 * Includes an appender for log4j to dump it's messages in beanstalkd (a small, flexible queue),
 * and an application to pull the messages back from the queue and store them in a RDBMS.
 * 
 * @author Joachim van der Herten
 * @url http://www.nextgeninno.com
 */
public class BeanLogd {
	
	private static final Logger log = Logger.getLogger(BeanLogd.class);

	/**
	 * Main class.
	 * @param args expects two arguments, a host and a port (endpoint of the beanstalkd)
	 */
	public static void main(String[] args) {
		DOMConfigurator.configure("log4j.xml");
		if(args.length != 2) {
			log.info("usage: java -jar logging.jar <host> <port>");
			log.info("with host and port representing the endpoint of the beanstalkd.");
			log.fatal("Argument mismatch. Exiting");
			System.exit(1);
		}
		try {
			Application app = new Application(args[0], Integer.parseInt(args[1]));
			app.run();
		} catch(Exception e) {
			log.fatal(e.getMessage());
			System.exit(1);
		}

	}

}

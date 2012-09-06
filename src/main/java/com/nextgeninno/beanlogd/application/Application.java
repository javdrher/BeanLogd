package com.nextgeninno.beanlogd.application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;


import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import com.surftools.BeanstalkClient.Job;
import com.surftools.BeanstalkClientImpl.ClientImpl;

/**
 * Main application
 * @author Joachim van der Herten
 */
public class Application {
	
	private static final Logger log = Logger.getLogger(Application.class);
	
	private ClientImpl bean;
	private BoneCP connectionPool;
	private ExecutorService pool;

	public Application(String host, int port) throws FileNotFoundException, SQLException, Exception {
		bean = new ClientImpl(host, port);
		bean.watch("logging");
		connectionPool = new BoneCP(new BoneCPConfig(new FileInputStream("bonecp-config.xml"), "config"));
		pool = Executors.newCachedThreadPool();
		log.info("BeanLogd started, intercepting messages in the beanstalkd logging tube on "+host+":"+port);
	}
	
	public void run() {
		Job job;
		while((job = bean.reserve(null)) != null) {
			LogMessage msg = LogMessage.unserialize(job.getData());
			log.debug("Received message: "+msg.toString());
			pool.submit(new StoreThread(msg, connectionPool.getAsyncConnection()));
			bean.delete(job.getJobId());
		}
	}

}

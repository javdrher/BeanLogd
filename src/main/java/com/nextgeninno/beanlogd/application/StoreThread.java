package com.nextgeninno.beanlogd.application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.concurrent.Future;

/**
 * Stores a LogMessage in a rdbms.
 * @author Joachim van der Herten
 *
 */
public class StoreThread implements Runnable {
	
	private LogMessage msg;
	private Future<Connection> con;
	
	public StoreThread(LogMessage msg, Future<Connection> con) {
		this.msg = msg;
		this.con = con;
	}

	public void run() {
		try {
			Connection cx = con.get();
			String sql = "insert into messages(level, logger, message, timestamp) values(?,?,?,?)";
			PreparedStatement stmt = cx.prepareStatement(sql);
			stmt.setString(1, msg.getPriority());
			stmt.setString(2, msg.getLogger());
			stmt.setString(3, msg.getMessage());
			stmt.setTimestamp(4, new Timestamp(msg.getStamp()));
			stmt.execute();
			cx.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

}

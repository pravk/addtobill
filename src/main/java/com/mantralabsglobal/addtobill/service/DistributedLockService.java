package com.mantralabsglobal.addtobill.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class DistributedLockService extends BaseService{

	private static final Logger logger = LoggerFactory.getLogger(DistributedLockService.class);

	@Autowired
	@Qualifier("lockDS")
	protected DataSource datasource;
	
	private static final String LOCK_SQL = "SELECT get_lock('%s', 60)";
	private static final String RELEASE_LOCK_SQL = "SELECT release_lock('%s')";
	
	private ThreadLocal<Connection> connection = new ThreadLocal<>();
	
	public boolean acquireLock(String userId)
	{
		int result =0;
		try {
			connection.set(datasource.getConnection());
			Connection conn = connection.get();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(String.format(LOCK_SQL,userId));
			if (rs.next()) {
				 result = rs.getInt(1);
			}
		} catch (SQLException e) {
			logger.error("Failed to acquire lock", e);
		}
		return result ==1;
	}
	
	public boolean releaseLock(String userId)
	{
		Connection conn = connection.get();
		int result =0;
		try {
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(String.format(RELEASE_LOCK_SQL,userId));
			if (rs.next()) {
				 result = rs.getInt(1);
			}
		} catch (SQLException e) {
			logger.error("Failed to release lock", e);
		}
		finally
		{
			try {
				conn.close();
			} catch (SQLException e) {
				logger.error("Failed to release lock", e);
			}
		}
		return result ==1;
	}
}

package com.jquery.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * Class for DataBase activities (Connecting, closing connection, resultset, prepare statements)
 * @author 
 *
 */
public class DBConnectionManager {

	private static final DemoLogger logger = DemoLogger
			.getLogger(DBConnectionManager.class);

	private static DataSource objDataSource;

	private Connection objDBConnection = null;

	static {
		try {
			Context context = new InitialContext();
			objDataSource = (DataSource) context
					.lookup("java:/comp/env/sampleDB");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(
					"Error occurred while getting the DataSource details.", e);
		}
	}

	/**
	 * Getting DB connection
	 * @return
	 * @throws Exception
	 */
	public Connection getConnection() throws Exception {
		logger.debug("Getting connection...");

		objDBConnection = objDataSource.getConnection();
		logger.info("getConnection :: " + objDBConnection);

		return objDBConnection;
	}
	
	/**
	 * Closing ResultSet, PreparedStatement....
	 * @param rsFile
	 * @param psFile
	 * @throws KonaLegacyException
	 */
	public void closingConnection(ResultSet rsFile, PreparedStatement psFile) throws Exception{
		try{
			logger.info("Comes in DB manager for closing Resultset and Prepared statement....");
			if (rsFile != null){
				rsFile.close();
				rsFile = null;
			}
			if (psFile != null){
				psFile.close();
				psFile = null;
			}
		}catch(Exception e){
			logger.error("Error in closing ResultSet  PreparedStatement connection:\n", e);
			throw e;
		}
		
	}
	

	/**
	 * Closing Database connection
	 * @param isProperReturn
	 * @throws Exception
	 */
	public void returnConnection(boolean isProperReturn) throws Exception {
		logger.debug("comes in returnConnection -> " + isProperReturn);
		if (isProperReturn) {
			logger.debug("isProperReturn:: " + isProperReturn);
			//this.objDBConnection.commit();
			this.objDBConnection.close();
			this.objDBConnection=null;
		} else {
			//this.objDBConnection.rollback();
			this.objDBConnection.close();
			this.objDBConnection=null;
		}
	}
	
	public static void main(String [] args)
	{
		DBConnectionManager obj = new DBConnectionManager();
		try {
			Connection ob =obj.getConnection();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

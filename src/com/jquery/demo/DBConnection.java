package com.jquery.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author shriram
 * 
 */
public class DBConnection {

	private static DemoLogger LOGGER = DemoLogger.getLogger(DBConnection.class);
	private DBConnectionManager objDBConnectionManager;

	/**
	 * Getting db connection
	 * 
	 * @return
	 * @throws Exception
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public DBConnection() throws Exception {
		objDBConnectionManager = new DBConnectionManager();
	}

	/**
	 * @param query
	 * @param con
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getDocumentData(String query) {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Connection connection = null;
		List<Map<String, Object>> documentList = new ArrayList<Map<String, Object>>();
		try {
			connection = objDBConnectionManager.getConnection();
			LOGGER.info("execute Query");
			preparedStatement = connection.prepareStatement(query);
			rs = preparedStatement.executeQuery(query);
			HashMap<String, Object> documentMap = new HashMap<String, Object>();
			while (rs != null && rs.next()) {
				documentMap.put(DocumentConstants.PARENT_ID,
						rs.getLong(DocumentConstants.PARENT_ID));
				documentMap.put(DocumentConstants.DOCUMENT_ID,
						rs.getLong(DocumentConstants.DOCUMENT_ID));
				documentMap.put(DocumentConstants.DESCRIPTION,
						rs.getString(DocumentConstants.DESCRIPTION));
				documentMap.put(DocumentConstants.DOCUMENT_NAME,
						rs.getString(DocumentConstants.DOCUMENT_NAME));
				documentMap.put(DocumentConstants.DOCUMENT_TYPE,
						rs.getString(DocumentConstants.DOCUMENT_TYPE));
				documentMap.put(DocumentConstants.IS_FOLDER,
						rs.getInt(DocumentConstants.IS_FOLDER));
				documentMap.put(DocumentConstants.SIZE,
						rs.getLong(DocumentConstants.SIZE));
				documentMap.put(DocumentConstants.VIEW_ALLOWED,
						rs.getString(DocumentConstants.VIEW_ALLOWED));
				documentList.add((Map<String, Object>) documentMap.clone());
				documentMap.clear();
			}

		} catch (Exception e) {
			LOGGER.error("SQL Excetpion", e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
					rs = null;
				} catch (SQLException e) {
					LOGGER.info("executeSelectQuery: clsoing resultset:"
							+ e.getMessage());
				}
			}
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					LOGGER.info(
							"getDocumentData: closing preparement statement:",
							e.getMessage());
				}
			}
			if (connection != null) {
				try {
					objDBConnectionManager.returnConnection(true);
					connection = null;
				} catch (Exception e) {
					LOGGER.info("executeSelectQuery: closing connection:"
							+ e.getMessage());
				}
			}
		}
		return documentList;
	}

	public void insertData(String TableName, Map<String, String> Data) {
		Statement statement = null;
		Connection connection = null;
		StringBuilder insertQuery = new StringBuilder("insert into ");
		insertQuery.append(TableName).append(" (");
		StringBuilder values = new StringBuilder (" values (");
		Set<String> columns = Data.keySet();
		for(String column : columns) {
			insertQuery.append(column).append(",");
			if(column.equals(DocumentConstants.DOCUMENT_ID) || column.equals(DocumentConstants.PARENT_ID))
			{
				values.append(Data.get(column));
			} else if (column.equals(DocumentConstants.IS_FOLDER)) {
				values.append(Data.get(column).equals("true") ? 1 : 0);
			} else {
				values.append("'").append(Data.get(column)).append("'");
			}
			values.append(",");
		}
		insertQuery = insertQuery.deleteCharAt(insertQuery.lastIndexOf(","));
		values = values.deleteCharAt(values.lastIndexOf(","));
		insertQuery.append(")").append(values).append(')');
		LOGGER.info(insertQuery);
		try{
			connection = objDBConnectionManager.getConnection();
			statement = connection.createStatement();
			statement.execute(insertQuery.toString());
		} catch (Exception e) {
			LOGGER.error(e);
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					LOGGER.info(
							"getDocumentData: closing preparement statement:",
							e.getMessage());
				}
			}
			if (connection != null) {
				try {
					objDBConnectionManager.returnConnection(true);
					connection = null;
				} catch (Exception e) {
					LOGGER.info("executeSelectQuery: closing connection:"
							+ e.getMessage());
				}
			}
		}
	}
}

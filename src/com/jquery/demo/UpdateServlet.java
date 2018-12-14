package com.jquery.demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Servlet implementation class UpdateServlet
 */
public class UpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static int counter = 99;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
//		response.setContentType("application/json");
	}

	private void deleteIDs(String sData) {
		// DBConnection dbConnection = null;
		// try {
		// dbConnection = new DBConnection();
		// dbConnection.delete("DocumentCenter1.DocumentDetail12",
		// sData.split(":"));
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
//		response.setContentType("application/json");
		String sJSONResponse =request.getParameter("jsonData");
		System.out.println("post data received"+sJSONResponse);
//		try {
//			handleJSONResponse(sJSONResponse);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		String sResponse =request.getParameter("deleteData");
		System.out.println("post data received"+sResponse);
//			deleteIDs(sResponse);
		// TODO Auto-generated method stub
	}

	private void handleJSONResponse(String sJSONResponse) throws Exception {
		DBConnection dbConnection = null;
		try {
			dbConnection = new DBConnection();
			JSONArray objJsonArray = new JSONArray(sJSONResponse);
			Map<String, String> Data = new HashMap<String, String>();
			for (int i =0 ; i<objJsonArray.length() ; i++) {
				JSONObject objData = objJsonArray.getJSONObject(i);
				Data.put(DocumentConstants.DOCUMENT_NAME, objData.getString("Document Name"));
				Data.put(DocumentConstants.DOCUMENT_TYPE, objData.getString("Doc Type"));
				Data.put(DocumentConstants.IS_FOLDER, objData.getString("Folder"));
				Data.put(DocumentConstants.PARENT_ID, objData.getString("ParentID"));
				Data.put(DocumentConstants.DOCUMENT_ID, String.valueOf(counter++));
				dbConnection.insertData("DocumentCenter1.DocumentDetail12", Data);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

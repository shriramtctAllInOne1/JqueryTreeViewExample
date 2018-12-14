package com.jquery.demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * @author shriram This is a controller which handles request,fetch data from
 *         db, create json data send back to view.
 * 
 */
public class ConnectDBServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static long DOC_ID = 0;
	private static DemoLogger LOGGER = DemoLogger
			.getLogger(ConnectDBServlet.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			LOGGER.info("doGet:");
			doProcess(request, response);
		} catch (SQLException e) {
			LOGGER.info("doGet:", e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			LOGGER.info("doPost:");
			doProcess(request, response);
		} catch (SQLException e) {
			LOGGER.info("doPost:", e.getMessage());
		}
	}

	/**
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException
	 */
	private void doProcess(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		JSONObject docDATA = new JSONObject();
		PrintWriter out = response.getWriter();
//		List<DocumentData> objList = fetchDataFromDB();
		
		DocumentData folderTechSpec = createFolder("Technical Specifications", "TechSpec", false, null);
		System.out.println("1----------------------------------");
		System.out.println(folderTechSpec.toString());
		System.out.println("----------------------------------");
		
		/*Create Tech Resp */
		DocumentData folderTechResp = createFolder("Technical Response", "TechResp", true, null);
		System.out.println("2----------------------------------");
		System.out.println(folderTechResp.toString());
		System.out.println("----------------------------------");

		/*Create Tech Resp */
		DocumentData folderMarketMaterial = createFolder("MarketMaterial", "MktMtrl", true, null);
		System.out.println("3----------------------------------");
		System.out.println(folderMarketMaterial.toString());
		System.out.println("----------------------------------");

		/*Create Tech Spec */
		DocumentData folderInvoice = createFolder("Invoices", "Invoice", false, null);
		System.out.println("4----------------------------------");
		System.out.println(folderInvoice.toString());
		System.out.println("----------------------------------");

		List<DocumentData> objList = new ArrayList<DocumentData>();
		objList.add(folderTechSpec);
		objList.add(folderTechResp);
		objList.add(folderMarketMaterial);
		objList.add(folderInvoice);
		
		LOGGER.info("doProcess:objList:" + objList);
		docDATA = fillJSON(objList);
		LOGGER.info(docDATA.toString());
		response.setContentType("application/json");
		out.println(docDATA.toString());
		out.close();
		objList = null;
	}

	/**
	 * @param docList
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public JSONObject fillJSON(List<DocumentData> docList) {
		LOGGER.info("fillJSON:");
		JSONObject docDATA = new JSONObject();
		JSONArray parentArray = new JSONArray();
		for (DocumentData currentDoc : docList) {
			LOGGER.info("fillJSON:" + currentDoc.toString());
			parentArray.add(createJSONfromDocumentData(currentDoc));
		}
		boolean isextjs = true;
		String sObjectName = isextjs == true ? "children" : "";
		docDATA.put(sObjectName, parentArray);
		return docDATA;
	}

	/**
	 * @param doc
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public JSONObject createJSONfromDocumentData(DocumentData doc) {
		LOGGER.info("createJSONfromDocumentData:");
		JSONObject jsonDocumentObject = new JSONObject();
		jsonDocumentObject.put("DocId", doc.getDocumentID());
		jsonDocumentObject.put("parentID", doc.getParentDocID());
		jsonDocumentObject.put("fileName", doc.getDocumentName());
		jsonDocumentObject.put("docType", doc.getDocumentType());
		jsonDocumentObject.put("fileSize", doc.getSize());
		if (doc.isFolder()) {
			jsonDocumentObject.put("iconCls", "fileName-folder");
			JSONArray jsonDocumentArrayObject = new JSONArray();
			for (DocumentData currentDoc : doc.getSubDocuments()) {
				jsonDocumentArrayObject
						.add(createJSONfromDocumentData(currentDoc));
			}
			jsonDocumentObject.put("children", jsonDocumentArrayObject);
		} else {
			jsonDocumentObject.put("iconCls", "fileName");
			jsonDocumentObject.put("leaf", true);
		}
		return jsonDocumentObject;
	}

	/**
	 * @param docID
	 * @param docName
	 * @param docType
	 * @param parentDocID
	 * @param isFolder
	 * @param fileSize
	 * @return
	 */
	public DocumentData createDocument(long docID, String docName,
			String docType, Long parentDocID, boolean isFolder, long fileSize) {
		LOGGER.info("createDocument:");
		DocumentData objDocumentData = new DocumentData();
		objDocumentData.setDocumentID(docID);
		objDocumentData.setParentDocID(parentDocID);
		objDocumentData.setDocumentName(docName);
		objDocumentData.setDocumentType(docType);
		objDocumentData.setSize(fileSize);
		objDocumentData.setFolder(isFolder);
		if (isFolder) {
			List<DocumentData> objList = new ArrayList<DocumentData>();
			objDocumentData.setSubDocuments(objList);
		}
		return objDocumentData;
	}

	/**
	 * @param documentList
	 * @param subDocument
	 * @return
	 */
	public boolean addDocumentsToFolder(List<DocumentData> documentList,
			DocumentData subDocument) {
		LOGGER.info("addDocumentsToFolder:");
		boolean isAdded = false;
		for (DocumentData parentDocument : documentList) {
			System.out.println(parentDocument.getDocumentID());
			System.out.println(subDocument.getParentDocID());
			if (parentDocument.getDocumentID() == subDocument.getParentDocID()) {
				parentDocument.getSubDocuments().add(subDocument);
				isAdded = true;
				break;
			} else if (parentDocument.getSubDocuments() != null) {
				isAdded = addDocumentsToFolder(
						parentDocument.getSubDocuments(), subDocument);
				if (isAdded) {
					break;
				}
			}
		}
		LOGGER.info("addDocumentsToFolder: is added");
		return isAdded;
	}

	/**
	 * @param docName
	 * @param docType
	 * @param hasSubFolder
	 * @param parentDocID
	 * @return
	 */
	public DocumentData createFolder(String docName, String docType,
			boolean hasSubFolder, Long parentDocID) {
		LOGGER.info("createFolder:");
		DocumentData docFolder = new DocumentData();
		docFolder.setDocumentID(DOC_ID++);
		docFolder.setParentDocID(parentDocID);
		docFolder.setDocumentName(docName);
		docFolder.setDocumentType(docType);
		docFolder.setFolder(true);
		List<DocumentData> objList = new ArrayList<DocumentData>();
		objList.add(createDocument(DOC_ID++, docName + "File" + DOC_ID,
				docType, docFolder.getDocumentID(), false, 13));
		if (hasSubFolder) {
			objList.add(createFolder(docName + "1", docType, false,
					docFolder.getDocumentID()));
		}
		docFolder.setSubDocuments(objList);
		return docFolder;
	}

	/**
	 * @return
	 */
	public List<DocumentData> fetchDataFromDB() {
		DBConnection dbConnection = null;
		List<DocumentData> documentDataList = new ArrayList<DocumentData>();
		DocumentData docmentData = null;
		Long lParentId=null;
		String query = "select * from DocumentCenter1.DocumentDetail12 order by Parent_ID";
		try {
			dbConnection = new DBConnection();
			List<Map<String,Object>> dbDocumentList = dbConnection.getDocumentData(query);
			for (Map<String, Object> docMapObj : dbDocumentList) {
				boolean bIsFolder;
				Integer sHasFolder = (Integer) (docMapObj.get(DocumentConstants.IS_FOLDER));
				if (sHasFolder == 0) {
					bIsFolder = false;
				} else {
					bIsFolder = true;
				}
				lParentId = (Long) docMapObj.get(DocumentConstants.PARENT_ID);
				if (lParentId == 0) {
					lParentId = null;
				}
				docmentData = createDocument((Long) docMapObj.get(DocumentConstants.DOCUMENT_ID),
						(String) docMapObj.get(DocumentConstants.DOCUMENT_NAME),
						(String) docMapObj.get(DocumentConstants.DESCRIPTION),(Long) lParentId, (boolean) bIsFolder,
						(Long) docMapObj.get(DocumentConstants.SIZE));
				if (lParentId == null) {
					LOGGER.info("adding document to list: "
							+ docmentData.toString());
					documentDataList.add(docmentData);
				} else {
					LOGGER.info("adding document to folder: "
							+ docmentData.toString());
					addDocumentsToFolder(documentDataList, docmentData);
				}
			}

		} catch (Exception e) {
			LOGGER.info("executeSelectQuery:", e);
		} 
		return documentDataList;
	}
}

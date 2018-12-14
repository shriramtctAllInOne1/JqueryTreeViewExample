/**
 * 
 */
package com.jquery.demo;

import java.util.List;

/**
 * @author shriram
 * 
 */
public class DocumentData {
	private long DocumentID;
	private Long ParentDocID;
	private String DocumentName;
	private boolean Folder;
	private String Description;
	private long Size;
	private String DocumentType;
	private String ViewAllowed;
	private List<DocumentData> SubDocuments;

	public DocumentData() {

	}

	/**
	 * @param documentID
	 * @param documentName
	 * @param folder
	 * @param description
	 * @param size
	 * @param documentType
	 * @param viewAllowed
	 * @param subDocuments
	 */
	public DocumentData(long documentID, String documentName, boolean folder,
			String description, long size, String documentType,
			String viewAllowed, List<DocumentData> subDocuments) {
		super();
		DocumentID = documentID;
		DocumentName = documentName;
		Folder = folder;
		Description = description;
		Size = size;
		DocumentType = documentType;
		ViewAllowed = viewAllowed;
		SubDocuments = subDocuments;
	}

	/**
	 * @return the documentID
	 */
	public long getDocumentID() {
		return DocumentID;
	}

	/**
	 * @param documentID
	 *            the documentID to set
	 */
	public void setDocumentID(long documentID) {
		DocumentID = documentID;
	}

	/**
	 * @return the parentDocID
	 */
	public Long getParentDocID() {
		return ParentDocID;
	}

	/**
	 * @param parentDocID the parentDocID to set
	 */
	public void setParentDocID(Long parentDocID) {
		ParentDocID = parentDocID;
	}

	/**
	 * @return the documentName
	 */
	public String getDocumentName() {
		return DocumentName;
	}

	/**
	 * @param documentName
	 *            the documentName to set
	 */
	public void setDocumentName(String documentName) {
		DocumentName = documentName;
	}

	/**
	 * @return the folder
	 */
	public boolean isFolder() {
		return Folder;
	}

	/**
	 * @param folder
	 *            the folder to set
	 */
	public void setFolder(boolean folder) {
		Folder = folder;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return Description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		Description = description;
	}

	/**
	 * @return the size
	 */
	public long getSize() {
		return Size;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public void setSize(long size) {
		Size = size;
	}

	/**
	 * @return the documentType
	 */
	public String getDocumentType() {
		return DocumentType;
	}

	/**
	 * @param documentType
	 *            the documentType to set
	 */
	public void setDocumentType(String documentType) {
		DocumentType = documentType;
	}

	/**
	 * @return the viewAllowed
	 */
	public String getViewAllowed() {
		return ViewAllowed;
	}

	/**
	 * @param viewAllowed
	 *            the viewAllowed to set
	 */
	public void setViewAllowed(String viewAllowed) {
		ViewAllowed = viewAllowed;
	}

	/**
	 * @return the subDocuments
	 */
	public List<DocumentData> getSubDocuments() {
		return SubDocuments;
	}

	/**
	 * @param subDocuments
	 *            the subDocuments to set
	 */
	public void setSubDocuments(List<DocumentData> subDocuments) {
		SubDocuments = subDocuments;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DocumentData [DocumentID=");
		builder.append(DocumentID);
		builder.append(", ParentDocID=");
		builder.append(ParentDocID);
		builder.append(", DocumentName=");
		builder.append(DocumentName);
		builder.append(", Folder=");
		builder.append(Folder);
		builder.append(", Description=");
		builder.append(Description);
		builder.append(", Size=");
		builder.append(Size);
		builder.append(", DocumentType=");
		builder.append(DocumentType);
		builder.append(", ViewAllowed=");
		builder.append(ViewAllowed);
		builder.append(", SubDocuments=");
		builder.append(SubDocuments);
		builder.append("]");
		return builder.toString();
	}
}

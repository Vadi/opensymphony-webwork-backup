/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.components;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.webwork.interceptor.ServletRequestAware;
import com.opensymphony.webwork.interceptor.ServletResponseAware;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionSupport;

/**
 * 
 * @author tm_jee
 * @version $Date$ $Id$
 */
public abstract class AbstractRichtexteditorConnector extends ActionSupport implements ServletRequestAware, ServletResponseAware {
	
	private static final Log _log = LogFactory.getLog(AbstractRichtexteditorConnector.class);

	public static String GET_FOLDERS = "getFolders";
	public static String GET_FOLDERS_AND_FILES = "getFoldersAndFiles";
	public static String CREATE_FOLDER = "createFolder";
	public static String FILE_UPLOAD = "fileUpload";
	
	protected HttpServletRequest _request;
	protected HttpServletResponse _response;
	
	protected java.io.File _newFile;
	protected String _newFileFileName;
	protected String _newFileContentType;
	
	protected String _type;
	protected String _command;
	protected String _currentFolder;
	protected String _serverPath = "/webwork/richtexteditor/data/";
	protected String _newFolderName;
	protected String _actualServerPath = "/com/opensymphony/webwork/static/richtexteditor/data/";
	
	
	public String browse() throws Exception {
		
		if ("GetFolders".equals(getCommand())) {
			_log.debug("Command "+getCommand()+" detected \n\t type="+getType()+"\n\t folderPath="+getCurrentFolder());
			
			ActionContext.getContext().put("__richtexteditorCommand", getCommand());
			ActionContext.getContext().put("__richtexteditorType", getType());
			ActionContext.getContext().put("__richtexteditorFolderPath", getCurrentFolder());
			ActionContext.getContext().put("__richtexteditorServerPath", calculateServerPath(getServerPath(), getCurrentFolder(), getType()));
			
			Folder[] folders = getFolders(getCurrentFolder(), getType());
			
			ActionContext.getContext().put("__richtexteditorGetFolders", folders);
			
			return GET_FOLDERS;
		}
		else if ("GetFoldersAndFiles".equals(getCommand())) {
			_log.debug("Command "+getCommand()+" detected \n\t type="+getType()+"\n\t folderPath="+getCurrentFolder());
			
			ActionContext.getContext().put("__richtexteditorCommand", getCommand());
			ActionContext.getContext().put("__richtexteditorType", getType());
			ActionContext.getContext().put("__richtexteditorFolderPath", getCurrentFolder());
			ActionContext.getContext().put("__richtexteditorServerPath", calculateServerPath(getServerPath(), getCurrentFolder(), getType()));
			
			FoldersAndFiles folderAndFiles = getFoldersAndFiles(getCurrentFolder(), getType());
			
			ActionContext.getContext().put("__richtexteditorGetFoldersAndFiles", folderAndFiles);
			
			return GET_FOLDERS_AND_FILES;
		}
		else if ("CreateFolder".equals(getCommand())) {
			_log.debug("Command "+getCommand()+" detected \n\t type="+getType()+"\n\t folderPath="+getCurrentFolder()+"\n\t newFolderName="+getNewFolderName());
			
			ActionContext.getContext().put("__richtexteditorCommand", getCommand());
			ActionContext.getContext().put("__richtexteditorType", getType());
			ActionContext.getContext().put("__richtexteditorFolderPath", getCurrentFolder());
			ActionContext.getContext().put("__richtexteditorServerPath", calculateServerPath(getServerPath(), getCurrentFolder(), getType()));
			
			CreateFolderResult createFolderResult = createFolder(getCurrentFolder(), getType(), getNewFolderName());
			
			ActionContext.getContext().put("__richtexteditorCreateFolder", createFolderResult);
			
			return CREATE_FOLDER;
		}
		else if ("FileUpload".equals(getCommand())) {
			_log.debug("Command "+getCommand()+" detected \n\t type="+getType()+"\n\t folderPath="+getCurrentFolder()+"\n\t newFileFileName="+getNewFileFileName()+"\n\t newFileContentType="+getNewFileContentType()+"\n\t newFile="+getNewFile());
			
			ActionContext.getContext().put("__richtexteditorCommand", getCommand());
			
			FileUploadResult fileUploadResult = fileUpload(getCurrentFolder(), getType(), getNewFileFileName(), getNewFileContentType(), getNewFile());
			
			ActionContext.getContext().put("__richtexteditorFileUpload", fileUploadResult);
			
			return FILE_UPLOAD;
		}
		else {
			_log.debug("Unknown Command "+getCommand()+" detected \n\t type="+getType()+"\n\t folderPath="+getCurrentFolder());
			
			unknownCommand(getCommand(), getCurrentFolder(), getType(), getNewFileFileName(), getNewFileContentType(), getNewFile());
			
			return ERROR;
		}
	}
	
	public String upload() throws Exception {
		_log.debug("Upload detected \n\t type="+getType()+"\n\t newFileFileName="+getNewFileFileName()+"\n\t newFileContentType="+getNewFileContentType()+"\n\t newFile="+getNewFile());
		
		FileUploadResult fileUploadResult = fileUpload("/", getType(), getNewFileFileName(), getNewFileContentType(), getNewFile());
		
		ActionContext.getContext().put("__richtexteditorFileUpload", fileUploadResult);
		
		return FILE_UPLOAD;
	}

	protected abstract String calculateServerPath(String serverPath, String folderPath, String type) throws Exception;
	protected abstract Folder[] getFolders(String virtualFolderPath, String type) throws Exception;
	protected abstract FoldersAndFiles getFoldersAndFiles(String virtualFolderPath, String type) throws Exception;
	protected abstract CreateFolderResult createFolder(String virtualFolderPath, String type, String newFolderName) throws Exception;
	protected abstract FileUploadResult fileUpload(String virtualFolderPath, String type, String filename, String contentType, java.io.File newFile) throws Exception;
	protected abstract void unknownCommand(String command, String virtualFolderPath, String type, String filename, String contentType, java.io.File newFile) throws Exception;
	
	
	// === FileUpload Details ======
	public java.io.File getNewFile() { return _newFile; }
	public void setNewFile(java.io.File newFile) { _newFile = newFile; }
	
	public String getNewFileFileName() { return _newFileFileName; }
	public void setNewFileFileName(String newFileFileName) { _newFileFileName = newFileFileName; }
	
	public String getNewFileContentType() { return _newFileContentType; }
	public void setNewFileContentType(String newFileContentType) { _newFileContentType = newFileContentType; }
	
	
	// === Browse / Upload details
	public String getCommand() { return _command; }
	public void setCommand(String command) { _command = command; }
	
	public String getType() { return _type; }
	public void setType(String type) { _type = type; }
	
	public String getCurrentFolder() { return _currentFolder; }
	public void setCurrentFolder(String currentFolder) { _currentFolder = currentFolder; }
	
	public String getNewFolderName() { return _newFolderName; }
	public void setNewFolderName(String newFolderName) { _newFolderName = newFolderName; }
	
	public String getServerPath() { return _serverPath; }
	public void setServerPath(String serverPath) { _serverPath = serverPath; }
	
	public String getActualServerPath() { return _actualServerPath; }
	public void setActualServerPath(String actualServerPath) { _actualServerPath = actualServerPath; }
	
	
	
	public void setServletRequest(HttpServletRequest request) {
		_request = request;
	}
	
	public void setServletResponse(HttpServletResponse response) {
		_response = response;
	}
	
	
	// ============================================================
	// === inner class ( Folder ) =================================
	// ============================================================
	public static class Folder implements Serializable {
		private String foldername;
		public Folder(String foldername) {
			assert(foldername != null);
			this.foldername = foldername;
		}
		public String getFoldername() { return this.foldername; }
	}
	
	
	// ============================================================
	// === inner class ( File ) ===================================
	// ============================================================
	
	public static class File implements Serializable {
		private String filename;
		private long sizeInKb;
		public File(String filename, long sizeInKb) {
			assert(filename != null);
			this.filename = filename;
			this.sizeInKb = sizeInKb;
		}
		public String getFilename() { return this.filename; }
		public long getSizeInKb() { return this.sizeInKb; }
	}
	
	// ============================================================
	// === inner class (FolderAndFiles) ===========================
	// ============================================================
	
	public static class FoldersAndFiles implements Serializable {
		private Folder[] folders;
		private File[] files;
		public FoldersAndFiles(Folder[] folders, File[] files) {
			this.folders = folders;
			this.files = files;
		}
		public Folder[] getFolders() { return this.folders; }
		public File[] getFiles() { return this.files; }
	}
	
	// ===========================================================
	// ==== inner class (CreateFolderResult) =====================
	// ===========================================================
	
	public static class CreateFolderResult implements Serializable {
		public static final CreateFolderResult NO_ERRORS = new CreateFolderResult("0");
		public static final CreateFolderResult FOLDER_ALREADY_EXISTS = new CreateFolderResult("101");
		public static final CreateFolderResult INVALID_FOLDER_NAME = new CreateFolderResult("102");
		public static final CreateFolderResult NO_PERMISSION = new CreateFolderResult("103");
		public static final CreateFolderResult UNKNOWN_ERROR = new CreateFolderResult("110");
		
		private String code;
		private CreateFolderResult(String code) {
			this.code = code;
		}
		public String getCode() { return this.code; }
		
		public static CreateFolderResult noErrors() { return NO_ERRORS; }
		public static CreateFolderResult folderAlreadyExists() { return FOLDER_ALREADY_EXISTS; }
		public static CreateFolderResult invalidFolderName() { return INVALID_FOLDER_NAME; }
		public static CreateFolderResult noPermission() { return NO_PERMISSION; }
		public static CreateFolderResult unknownError() { return UNKNOWN_ERROR; }
	}
	
	// =============================================================
	// === inner class (FileUploadResult) ==========================
	// =============================================================
	
	public static class FileUploadResult implements Serializable {
		private static final FileUploadResult UPLOAD_COMPLETED = new FileUploadResult("0");
		private static final FileUploadResult INVALID_FILE = new FileUploadResult("202");
		
		private String code;
		private String filename;
		private FileUploadResult(String code) {
			this(code, null);
		}
		private FileUploadResult(String code, String newFilename) {
			this.code = code;
			filename = newFilename;
		}
		public String getCode() { return code; }
		public String getFilename() { return filename; }
		
		public static FileUploadResult uploadComplete() { return UPLOAD_COMPLETED; }
		public static FileUploadResult invalidFile() { return INVALID_FILE; }
		public static FileUploadResult uploadCompleteWithFilenamChanged(String newFilename) {
			assert(newFilename != null);
			return new FileUploadResult("201", newFilename);
		}
	}
	
}

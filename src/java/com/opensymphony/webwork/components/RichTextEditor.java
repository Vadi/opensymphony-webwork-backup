/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.components;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.webwork.components.AbstractRichtexteditorConnector.CreateFolderResult;
import com.opensymphony.webwork.components.AbstractRichtexteditorConnector.FileUploadResult;
import com.opensymphony.webwork.components.AbstractRichtexteditorConnector.Folder;
import com.opensymphony.webwork.components.AbstractRichtexteditorConnector.FoldersAndFiles;
import com.opensymphony.xwork.util.OgnlValueStack;

/**
 * <!-- START SNIPPET: javadoc -->
 * 
 * Create a Rich Text Editor based on FCK editor (www.fckeditor.net). 
 * 
 * <!-- END SNIPPET: javadoc -->
 * 
 * <p/>
 * 
 * 
 * <pre>
 * <!-- START SNIPPET: example -->
 * 
 * &lt;ww:richtexteditor 
 *			toolbarCanCollapse="false"
 *			width="700"
 *			label="Description 1" 
 *			name="description1" 
 *			value="Some Content I keyed In In The Tag Itself"
 *			/&gt;
 * 
 * 
 * <!-- END SNIPPET: example -->
 * </pre>
 * 
 * 
 * <!-- START SNIPPET: serversidebrowsing -->
 * 
 * It is possible to have a rich text editor do server side browsing  
 * when for example the image button is clicked. To integrate this functionality with 
 * webwork, one need to defined the following action definition typically in xwork.xml
 * 
 * <pre>
 *   &lt;package name="richtexteditor-browse" extends="webwork-default" namespace="/webwork/richtexteditor/editor/filemanager/browser/default/connectors/jsp"&gt;
 *   	&lt;action name="connector" class="com.opensymphony.webwork.components.DefaultRichtexteditorConnector" method="browse">
 *   		&lt;result name="getFolders" type="richtexteditorGetFolders" /&gt;
 *   		&lt;result name="getFoldersAndFiles" type="richtexteditorGetFoldersAndFiles" /&gt;
 *   		&lt;result name="createFolder" type="richtexteditorCreateFolder" /&gt;
 *   		&lt;result name="fileUpload" type="richtexteditorFileUpload" /&gt;
 *   	&lt;/action&gt;
 *   &lt;/package&gt;
 * </pre>
 * 
 * By default whenever a browse command is triggered (eg. by clicking on the 'image' button and then
 * 'browse server' button, the url '/webwork/static/richtexteditor/editor/filemanager/browser/default/browser.html?&Type=Image&Connector=connectors/jsp/connector.action'.
 * The page browser.html which comes with FCK Editor will trigger the url 
 * '/webwork/richtexteditor/editor/filemanager/browser/default/connectors/jsp/connector.action' which will
 * caused the webwork's DefaultRichtexteditorConnector to be executed. The trigerring url could be
 * changed by altering the 'imageBrowseURL'. There 3 types of such related url, namely 'imageBrowseURL', 
 * 'linkBrowseURL' and 'flashBrowseURL'. It is recomended that the default one being used. One could change the
 * Connector parameter instead. For example
 * 
 * <pre>
 * /webwork/static/richtexteditor/editor/filemanager/browser/default/browser.html?&Type=Image&Connector=connectors/jsp/connector.action
 * </pre>
 * 
 * could be changed to 
 * 
 * <pre>
 * /webwork/static/richtexteditor/editor/filemanager/browser/default/browser.html?&Type=Image&Connector=myLittlePath/myConnector.action
 * </pre>
 * 
 * In this case the action will need to have a namespace of '/webwork/richtexteditor/editor/filemanager/browser/default/myLittlePath'
 * and action name of 'myConnector'
 * 
 * <p/>
 * 
 * By default the action method that needs to be defined in xwork.xml needs to be 'browse'. If this needs
 * to be something else say, myBrowse, the following could be used
 * 
 * <pre>
 *   public String myBrowse() {
 *       browse();
 *   }
 * </pre>
 * 
 * <!-- START SNIPPET: serversidebrowsing -->
 * 
 * <p/>
 * 
 * 
 * <!-- START SNIPPET: serversideuploading -->
 * 
 * It is possible for the richtexteditor to do server side uploading as well. For example when clicking
 * on the 'Image' button and then the 'Upload' tab and then selecting a file from client local
 * machine and the clicking 'Send it to the server'. To integrate this functionality with 
 * webwork, one need to defined the following action definition typically in xwork.xml
 * 
 * <pre>
 *   &lt;package name="richtexteditor-upload" extends="webwork-default" namespace="/webwork/richtexteditor/editor/filemanager/upload"&gt;
 *		&lt;action name="uploader" class="com.opensymphony.webwork.components.DefaultRichtexteditorConnector" method="upload"&gt;
 *			&lt;result name="richtexteditorFileUpload" /&gt;
 *		&lt;/action&gt;    
 *   &lt;/package&gt;
 * </pre>
 * 
 * By default whenever an upload command is triggered, a '/webwork/static/richtexteditor/editor/filemanager/upload/uploader.action?Type=Image'
 * will be issued. This could be changed by setting the imageUploadURL attribute of the tag. 
 * When this link is issued, the webwork action will get executed. There's 3 such related upload url
 * namely, 'imageUploadURL', 'linkUploadURL' and 'flashUploadURL'. It is recomended that the default 
 * one being used. However one could change the url, but need to include the Type parameter. For example
 * 
 * <pre>
 * /webwork/static/richtexteditor/editor/filemanager/upload/uploader.action?Type=Image
 * </pre>
 * 
 * could be changed to 
 * 
 * <pre>
 * /webwork/static/richtexteditor/editor/filemanager/upload/aDifferentUploader.action?Type=Image
 * </pre>
 * 
 * In this case the action will need to have a namespace of '/webwork/static/richtexteditor/editor/filemanager/upload'
 * and action name of 'aDifferentUploader'
 * 
 * By default the action method that needs to be defined in xwork.xml needs to be 'upload'. If this needs
 * to be something else say, myUpload, the following could be used
 * 
 * <pre>
 *   public String myUpload() {
 *       upload();
 *   }
 * </pre>
 * 
 * <!-- END SNIPPET: serversideuploading -->
 * 
 * 
 * <!-- START SNIPPET: richtexteditoraction -->
 * 
 * The webwork that handles the server-side browsing and uploading needs to extends from 
 * AbstractRichtexteditorConnector.
 * 
 * There are four abstract methods need to be implemented, namely 
 * 
 * <p/>
 * 
 * <pre>
 *  protected abstract String calculateServerPath(String serverPath, String folderPath, String type) throws Exception;
 *  protected abstract Folder[] getFolders(String virtualFolderPath, String type) throws Exception;
 *  protected abstract FoldersAndFiles getFoldersAndFiles(String virtualFolderPath, String type) throws Exception;
 *  protected abstract CreateFolderResult createFolder(String virtualFolderPath, String type, String newFolderName) throws Exception;
 *  protected abstract FileUploadResult fileUpload(String virtualFolderPath, String type, String filename, String contentType, java.io.File newFile) throws Exception;
 *  protected abstract void unknownCommand(String command, String virtualFolderPath, String type, String filename, String contentType, java.io.File newFile) throws Exception;
 * </pre>
 * 
 * <!-- END SNIPPET: richtexteditoraction -->
 * 
 * 
 * 
 * @author tm_jee
 * @version $Date$ $Id$
 * @see AbstractRichtexteditorConnector
 * 
 * @ww.tag name="richtexteditor" tld-body-content="JSP" tld-tag-class="com.opensymphony.webwork.views.jsp.ui.RichTextEditorTag"
 * description="Render a rich text editor element"
 */
public class RichTextEditor extends UIBean {

	final public static String TEMPLATE = "richtexteditor";
	
	private String checkBrowser;
	private String displayError;
	private String basePath = null;
	private String toolbarSet = null;
	private String width = null;
	private String height = null;
	private String customConfigurationsPath = null;
	private String editorAreaCSS = null;
	private String baseHref = null;
	private String skinPath = null;
	private String pluginsPath = null;
	private String fullPage = null;
	private String debug = null;	
	private String autoDetectLanguage = null;
	private String defaultLanguage = null;
	private String contentLangDirection = null;
	private String enableXHTML = null;
	private String enableSourceXHTML = null;
	private String fillEmptyBlocks = null;
	private String formatSource = null;
	private String formatOutput = null;
	private String formatIndentator = null;
	private String geckoUseSPAN = null;
	private String startupFocus = null;
	private String forcePasteAsPlainText = null;
	private String forceSimpleAmpersand = null;
	private String tabSpaces = null;
	private String useBROnCarriageReturn = null;
	private String toolbarStartExpanded = null;
	private String toolbarCanCollapse = null;
	private String fontColors = null;
	private String fontNames = null;
	private String fontSizes = null;
	private String fontFormats = null;
	private String stylesXmlPath = null;
	private String templatesXmlPath = null;
	private String linkBrowserURL = null;
	private String imageBrowserURL = null;
	private String flashBrowserURL = null;
	private String linkUploadURL = null;
	private String imageUploadURL = null;
	private String flashUploadURL = null;
	
	private String allowImageBrowse;
	private String allowLinkBrowse;
	private String allowFlashBrowse;
	private String allowImageUpload;
	private String allowLinkUpload;
	private String allowFlashUpload;
	private String linkUploadAllowedExtension;
	private String linkUploadDeniedExtension;
	private String imageUploadAllowedExtension;
	private String imageUploadDeniedExtension;
	private String flashUploadAllowedExtension;
	private String flashUploadDeniedExtension;
	private String smileyPath;
	private String smileyImages;
	
	
	public RichTextEditor(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
		super(stack, request, response);
	}

	protected String getDefaultTemplate() {
		return TEMPLATE;
	}
	
	protected void evaluateExtraParams() {
		super.evaluateExtraParams();
		
		// checkBrowser
		if (checkBrowser != null) {
			addParameter("checkBrowser", ((Boolean)findValue(checkBrowser, Boolean.class)).booleanValue()?"true":"false");
		}
		
		// displayError
		if (displayError != null) {
			addParameter("displayError", ((Boolean)findValue(displayError, Boolean.class)).booleanValue()?"true":"false");
		}
		
		// basePath
		if (basePath != null) {
			addParameter("basePath", findString(basePath));
		}
		
		// toolbarSet
		if (toolbarSet != null) {
			addParameter("toolbarSet", findString(toolbarSet));
		}
		
		// width
		if (width != null) {
			addParameter("width", findString(width));
		}
		
		// height
		if (height != null) {
			addParameter("height", findString(height));
		}
		
		// customConfigurationsPath
		if (customConfigurationsPath != null) {
			addParameter("customConfigurationsPath", findString(customConfigurationsPath));
		}
		
		
		// editorAreaCss
		if (editorAreaCSS != null) {
			addParameter("editorAreaCss", findString(editorAreaCSS));
		}
		
		// baseHref
		if (baseHref != null) {
			addParameter("baseHref", findString(baseHref));
		}
		
		// skinPath
		if (skinPath != null) {
			addParameter("skinPath", findString(skinPath));
		}
		
		// pluginsPath
		if (pluginsPath != null) {
			addParameter("pluginsPath", findString(pluginsPath));
		}
		
		// fullPage
		if (fullPage != null) {
			addParameter("fullPage", ((Boolean)findValue(fullPage, Boolean.class)).booleanValue() ?"true" : "false");
		}
		
		// debug
		if (debug != null) {
			addParameter("debug", ((Boolean)findValue(debug, Boolean.class)).booleanValue() ? "true" : "false");
		}
		
		// autoDetectLanguage
		if (autoDetectLanguage != null) {
			addParameter("autoDetectLanguage", ((Boolean)findValue(autoDetectLanguage, Boolean.class)).booleanValue() ? "true" : "false");
		}
		
		// defaultLanguage
		if (defaultLanguage != null) {
			addParameter("defaultLanguage", findString(defaultLanguage));
		}
		
		// contentLangDirection
		if (contentLangDirection != null) {
			addParameter("contentLangDirection", findString(contentLangDirection));
		}
		
		// enableXHTML
		if (enableXHTML != null) {
			addParameter("enableXHTML", ((Boolean)findValue(enableXHTML, Boolean.class)).booleanValue() ? "true" : "false");
		}
		
		// enableSourceXHTML
		if (enableSourceXHTML != null) {
			addParameter("enableSourceXHTML", ((Boolean)findValue(enableSourceXHTML, Boolean.class)).booleanValue() ? "true" : "false");
		}
		
		// fillEmptyBlocks
		if (fillEmptyBlocks != null) {
			addParameter("fillEmptyBlocks", ((Boolean)findValue(fillEmptyBlocks, Boolean.class)).booleanValue() ? "true" : "false");
		}
		
		// formatSource
		if (formatSource != null) {
			addParameter("formatSource", ((Boolean)findValue(formatSource, Boolean.class)).booleanValue() ? "true" : "false");
		}
		
		// formatOutput
		if (formatOutput != null) {
			addParameter("formatOutput", ((Boolean)findValue(formatOutput, Boolean.class)).booleanValue() ? "true" : "false");
		}
		
		// formatIndentator
		if (formatIndentator != null) {
			addParameter("formatIndentator", findString(formatIndentator));
		}
		
		// geckoUseSPAN
		if (geckoUseSPAN != null) {
			addParameter("geckoUseSPAN", ((Boolean)findValue(geckoUseSPAN, Boolean.class)).booleanValue()? "true" : "false");
		}
		
		// startupFocus
		if (startupFocus != null) {
			addParameter("startupFocus", ((Boolean)findValue(startupFocus, Boolean.class)).booleanValue()? "true" : "false");
		}
		
		// forcePasteAsPlainText
		if (forcePasteAsPlainText != null) {
			addParameter("forcePasteAsPlainText", ((Boolean)findValue(forcePasteAsPlainText, Boolean.class)).booleanValue()? "true" : "false");
		}
		
		// forceSimpleAmpersand
		if (forceSimpleAmpersand != null) {
			addParameter("forceSimpleAmpersand", ((Boolean)findValue(forceSimpleAmpersand, Boolean.class)).booleanValue()? "true" : "false");
		}
		
		// tabSpaces
		if (tabSpaces != null) {
			addParameter("tabSpaces", findString(tabSpaces));
		}
		
		// useBROnCarriageReturn
		if (useBROnCarriageReturn != null) {
			addParameter("useBROnCarriageReturn", ((Boolean)findValue(useBROnCarriageReturn, Boolean.class)).booleanValue()? "true" : "false");
		}
		
		
		// toolbarStartExpanded
		if (toolbarStartExpanded != null) {
			addParameter("toolbarStartExpanded", ((Boolean)findValue(toolbarStartExpanded, Boolean.class)).booleanValue()? "true" : "false");
		}
		
		// toolbarCanCollapse
		if (toolbarCanCollapse != null) {
			addParameter("toolbarCanCollapse", ((Boolean)findValue(toolbarCanCollapse, Boolean.class)).booleanValue()? "true" : "false");
		}
		
		// fontColors
		if (fontColors != null) {
			addParameter("fontColors", findString(fontColors));
		}
		
		// fontNames
		if (fontNames != null) {
			addParameter("fontNames", findString(fontNames));
		}
		
		// fontSizes
		if (fontSizes != null) {
			addParameter("fontSizes", findString(fontSizes));
		}
		
		// fontFormats
		if (fontFormats != null) {
			addParameter("fontFormats", findString(fontFormats));
		}
		
		// stylesXmlPath
		if (stylesXmlPath != null) {
			addParameter("stylesXmlPath", findString(stylesXmlPath));
		}
		
		// template
		if (templatesXmlPath != null) {
			addParameter("templatesXmlPath", findString(templatesXmlPath));
		}
		
		// linkBrowserURL
		if (linkBrowserURL != null) {
			addParameter("linkBrowserURL", findString(linkBrowserURL));
		}
		
		// imageBrowserURL
		if (imageBrowserURL != null) {
			addParameter("imageBrowserURL", findString(imageBrowserURL));
		}
		
		// flashBrowserURL 
		if (flashBrowserURL != null) {
			addParameter("flashBrowserURL", findString(flashBrowserURL));
		}
		
		// linkUploadURL
		if (linkUploadURL != null) {
			addParameter("linkUploadURL", findString(linkUploadURL));
		}
		
		// imageUploadURL
		if (imageUploadURL != null) {
			addParameter("imageUploadURL", findString(imageUploadURL));
		}
		
		// flashUploadURL
		if (flashUploadURL != null) {
			addParameter("flashUploadURL", findString(flashUploadURL));
		}
		
		// allowImageBrowse
		if (allowImageBrowse != null) {
			addParameter("allowImageBrowse", allowFlashBrowse);
		}
		
		// allowLinkBrowse
		if (allowLinkBrowse != null) {
			addParameter("allowLinkBrowse", allowLinkBrowse);
		}
		
		// allowFlashBrowse
		if (allowFlashBrowse != null) {
			addParameter("allowFlashBrowse", allowFlashBrowse);
		}
		
		// allowImageUpload
		if (allowImageUpload != null) {
			addParameter("allowImageUpload", allowImageUpload);
		}
		
		// allowLinkUpload
		if (allowLinkUpload != null) {
			addParameter("allowLinkUpload", allowLinkUpload);
		}
		
		// allowFlashUpload
		if (allowFlashUpload != null) {
			addParameter("allowFlashUpload", allowFlashUpload);
		}
		
		// linkUploadAllowedExtension
		if (linkUploadAllowedExtension != null) {
			addParameter("linkUploadAllowedExtension", linkUploadAllowedExtension);
		}
		
		// linkUploadDeniedExtension
		if (linkUploadDeniedExtension != null) {
			addParameter("linkUploadDeniedExtension", linkUploadDeniedExtension);
		}
		
		// imageUploadAllowedExtension
		if (imageUploadAllowedExtension != null) {
			addParameter("imageUploadAllowedExtension", imageUploadAllowedExtension);
		}
		
		// imageUploadDeniedExtension
		if (imageUploadDeniedExtension != null) {
			addParameter("imageUploadDeniedExtension", imageUploadDeniedExtension);
		}
		
		// flashUploadAllowedExtension
		if (flashUploadAllowedExtension != null) {
			addParameter("flashUploadAllowedExtension", flashUploadAllowedExtension);
		}
		
		// flashUploadDeniedExtension
		if (flashUploadDeniedExtension != null) {
			addParameter("flashUploadDeniedExtension", flashUploadDeniedExtension);
		}
		
		// smileyPath
		if (smileyPath != null) {
			addParameter("smileyPath", smileyPath);
		}
		
		// smileyImages
		if (smileyImages != null) {
			addParameter("smileyImages", smileyImages);
		}
	}
	
	
	
	



	public String getCheckBrowser() {
		return checkBrowser;
	}

	/**
     * @ww.tagattribute required="false" type="Boolean" default="true"
     * description="Whether the rich text editor should check for browser compatibility when rendering its toolbar"
     */
	public void setCheckBrowser(String checkBrowser) {
		this.checkBrowser = checkBrowser;
	}

	public String getDisplayError() {
		return displayError;
	}

	/**
     * @ww.tagattribute required="false" type="Boolean" default="true"
     * description="Whether should the rich text editor display error when it fails to render etc."
     */
	public void setDisplayError(String displayError) {
		this.displayError = displayError;
	}

	public String getAutoDetectLanguage() {
		return autoDetectLanguage;
	}

	/**
     * @ww.tagattribute required="false" type="Boolean" default="true"
     * description="Tells the editor to automatically detect the user language preferences to adapt its interface language. With Internet Explorer, the language configured in the Windows Control Panel is used. With Firefox, the browser language is used"
     */
	public void setAutoDetectLanguage(String autoDetectLanguage) {
		this.autoDetectLanguage = autoDetectLanguage;
	}

	public String getBaseHref() {
		return baseHref;
	}

	/**
     * @ww.tagattribute required="false" type="String" default=" "
     * desciprtion="Base URL used to resolve links (on images, links, styles, etc.). For example, if BaseHref is set to 'http://www.fredck.com', an image that points to "/images/Logo.gif" will be interpreted by the editor as "http://www.fredck.com/images/Logo.gif", without touching the "src" attribute of the image."
     */
	public void setBaseHref(String baseHref) {
		this.baseHref = baseHref;
	}

	public String getBasePath() {
		return basePath;
	}

	/**
	 * @ww.tagattribute required="false" type="String" default="/webwork/static/richtexteditor/"
	 * description="Set the dir where the FCKeditor files reside on the server"
	 */
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public String getContentLangDirection() {
		return contentLangDirection;
	}
	
	
	/**
	 * @ww.tagattribute required="false" type="String" default="ltr"
	 * description="Sets the direction of the editor area contents. Either ltr or rtl"
	 */
	public void setContentLangDirection(String contentLangDirection) {
		this.contentLangDirection = contentLangDirection;
	}

	public String getCustomConfigurationsPath() {
		return customConfigurationsPath;
	}

	/**
	 * @ww.tagattribute required="false" type="String" default=" "
	 * description="Set the path of a custom file that can override some configurations. It is recommended to use absolute paths (starting with /), like /myfckconfig.js."
	 */
	public void setCustomConfigurationsPath(String customConfigurationsPath) {
		this.customConfigurationsPath = customConfigurationsPath;
	}

	public String getDebug() {
		return debug;
	}

	/**
	 * @ww.tagattribute required="false" type="Boolean" default="false"
	 * description="Enables the debug window to be shown when calling the FCKDebug.Output() function."
	 */
	public void setDebug(String debug) {
		this.debug = debug;
	}

	public String getDefaultLanguage() {
		return defaultLanguage;
	}

	/**
	 * @ww.tagattribute required="false" type="String" default="en"
	 * description="Sets the default language used for the editor's interface localization. The default language is used when the AutoDetectLanguage options is disabled or when the user language is not available."
	 */
	public void setDefaultLanguage(String defaultLanguage) {
		this.defaultLanguage = defaultLanguage;
	}

	public String getEditorAreaCSS() {
		return editorAreaCSS;
	}

	/**
	 * @ww.tagattribute required="false" type="String" default="css/fck_editorarea.css"
	 * description="Set the CSS styles file to be used in the editing area. In this way you can point to a file that reflects your web site styles"
	 */
	public void setEditorAreaCSS(String editorAreaCSS) {
		this.editorAreaCSS = editorAreaCSS;
	}

	public String getEnableSourceXHTML() {
		return enableSourceXHTML;
	}

	/**
	 * @ww.tagattribute required="false" type="String" default="true"
	 * description="Tells the editor to process the HTML source to XHTML when switching from WYSIWYG to Source view"
	 */
	public void setEnableSourceXHTML(String enableSourceXHTML) {
		this.enableSourceXHTML = enableSourceXHTML;
	}

	public String getEnableXHTML() {
		return enableXHTML;
	}

	/**
	 * @ww.tagattribute required="false" type="String" default="true"
	 * description="Tells the editor to process the HTML source to XHTML on form post."
	 */
	public void setEnableXHTML(String enableXHTML) {
		this.enableXHTML = enableXHTML;
	}

	public String getFillEmptyBlocks() {
		return fillEmptyBlocks;
	}

	/**
	 * @ww.tagattribute required="false" type="String" default="true"
	 * description="Block elements (like P, DIV, H1, PRE, etc...) are forced to have content (a &amp;nbsp;).
	 * Empty blocks are "collapsed" by while browsing, so a empty &lt;p&gt;&lt;/p&gt; is not visible.
	 * While editing, the editor "expand" empty blocks so you can insert content inside then.
	 * Setting this option to "true" results useful to reflect the same output when browsing and editing."
	 */
	public void setFillEmptyBlocks(String fillEmptyBlocks) {
		this.fillEmptyBlocks = fillEmptyBlocks;
	}

	public String getFlashBrowserURL() {
		return flashBrowserURL;
	}

	/**
	 * @ww.tagattribute required="false" type="String" default="/webwork/static/richtexteditor/editor/filemanager/browser/default/browser.html?Type=Flash&Connector=connectors/jsp/connector.action"
	 * description="Sets the URL of the page called when the user clicks the 'Browse Server' button in the "Flash" dialog window.
	 * In this way, you can create your custom Flash Browser that is well integrated with your system."
	 */
	public void setFlashBrowserURL(String flashBrowserURL) {
		this.flashBrowserURL = flashBrowserURL;
	}

	public String getFlashUploadURL() {
		return flashUploadURL;
	}

	/**
	 * @ww.tagattribute required="false" type="string" default="/webwork/static/richtexteditor/editor/filemanager/upload/uploader.action?Type=Flash" 
	 * description="Sets the URL of the upload handler called when the user clicks the 'Send it to server' button in the "Flash" dialog window.<BR>
	 * In this way, you can create your custom Flash Uploader that is well integrated with your system."
	 */
	public void setFlashUploadURL(String flashUploadURL) {
		this.flashUploadURL = flashUploadURL;
	}

	public String getFontColors() {
		return fontColors;
	}

	/**
	 * @ww.tagattribute required="false" type="string" default="000000,993300,333300,003300,003366,000080,333399,333333,800000,FF6600,808000,808080,008080,0000FF,666699,808080,FF0000,FF9900,99CC00,339966,33CCCC,3366FF,800080,999999,FF00FF,FFCC00,FFFF00,00FF00,00FFFF,00CCFF,993366,C0C0C0,FF99CC,FFCC99,FFFF99,CCFFCC,CCFFFF,99CCFF,CC99FF,FFFFFF"
	 * description="Sets the colors that must be shown in the colors panels (in the toolbar)."
	 */
	public void setFontColors(String fontColors) {
		this.fontColors = fontColors;
	}

	public String getFontFormats() {
		return fontFormats;
	}

	/**
	 * @ww.tagattribute required="false" type="string" default="p;div;pre;address;h1;h2;h3;h4;h5;h6"
	 * description="Sets the list of formats to be shown in the "Format" toolbar command."
	 */
	public void setFontFormats(String fontFormats) {
		this.fontFormats = fontFormats;
	}

	public String getFontNames() {
		return fontNames;
	}

	/**
	 * @ww.tagattribute required="false" type="string" default="Arial;Comic Sans MS;Courier New;Tahoma;Times New Roman;Verdana"
	 * description="Sets the list of fonts to be shown in the "Font" toolbar command."
	 */
	public void setFontNames(String fontNames) {
		this.fontNames = fontNames;
	}

	public String getFontSizes() {
		return fontSizes;
	}

	/**
	 * @ww.tagattribute required="false" type="string" default="1/xx-small;2/x-small;3/small;4/medium;5/large;6/x-large;7/xx-large"
	 * description="Sets the list of font sizes to be shown in the "Size" toolbar command."
	 */
	public void setFontSizes(String fontSizes) {
		this.fontSizes = fontSizes;
	}

	public String getForcePasteAsPlainText() {
		return forcePasteAsPlainText;
	}

	/**
	 * @ww.tagattribute required="false" type="boolean" default="false"
	 * description="Converts the clipboard contents to pure text on pasting operations"
	 */
	public void setForcePasteAsPlainText(String forcePasteAsPlainText) {
		this.forcePasteAsPlainText = forcePasteAsPlainText;
	}

	public String getForceSimpleAmpersand() {
		return forceSimpleAmpersand;
	}

	/**
	 * @ww.tagattribute required="false" type="boolean" default="false"
	 * description="Forces the ampersands (&) on tags attributes to not be converted to '&amp;amp;'
	 * This conversion is a W3C requirement for XHTML, so it is recommended to leave this option to 'false'."
	 */
	public void setForceSimpleAmpersand(String forceSimpleAmpersand) {
		this.forceSimpleAmpersand = forceSimpleAmpersand;
	}

	public String getFormatIndentator() {
		return formatIndentator;
	}

	/**
	 * @ww.tagattribute required="false" type="boolean" default="'    '"
	 * description="Sets the characters to be used when indenting the HTML source when formatting it.
	 * Useful values are a sequence of spaces ('     ') or a tab char ('\t')."
	 */
	public void setFormatIndentator(String formatIndentator) {
		this.formatIndentator = formatIndentator;
	}

	public String getFormatOutput() {
		return formatOutput;
	}

	/**
	 * @ww.tagattribute required="false" type="boolean" default="true"
	 * description = "The output HTML generated by the editor will be processed and formatted."
	 */
	public void setFormatOutput(String formatOutput) {
		this.formatOutput = formatOutput;
	}

	public String getFormatSource() {
		return formatSource;
	}

	/**
	 * @ww.tagattribute required="false" type="boolean" default="true"
	 * description="The HTML shown by the editor, while switching from WYSIWYG to Source views, will be processed and formatted"
	 */
	public void setFormatSource(String formatSource) {
		this.formatSource = formatSource;
	}

	public String getFullPage() {
		return fullPage;
	}

	/**
	 * @ww.tagattribute required="false" type="boolean" default="false"
	 * description="Enables full page editing (from &lt;HTML&gt; to &lt;/HTML&gt;).
	 * It also enables the 'Page Properties' toolbar button."
	 */
	public void setFullPage(String fullPage) {
		this.fullPage = fullPage;
	}

	public String getGeckoUseSPAN() {
		return geckoUseSPAN;
	}

	/**
	 * @ww.tagattribute required="false" type="boolean" default="true"
	 * description="Tells Gecko browsers to use SPAN instead of &lt;B&gt;, &lt;I&gt; and &lt;U&gt; for bold, italic an underline"
	 */
	public void setGeckoUseSPAN(String geckoUseSPAN) {
		this.geckoUseSPAN = geckoUseSPAN;
	}

	public String getHeight() {
		return height;
	}

	/**
	 * @ww.tagattribute required="false" type="string" default="200"
	 * description="Set the height of the rich text editor"
	 */
	public void setHeight(String height) {
		this.height = height;
	}

	public String getImageBrowserURL() {
		return imageBrowserURL;
	}

	/**
	 * @ww.tagattribute required="false" type="string" default="/webwork/static/richtexteditor/editor/filemanager/browser/default/browser.html?&Type=Image&Connector=connectors/jsp/connector.action"
	 * description="Sets the URL of the page called when the user clicks the 'Browse Server' button in the 'Image' dialog window.
	 * In this way, you can create your custom Image Browser that is well integrated with your system."
	 */
	public void setImageBrowserURL(String imageBrowserURL) {
		this.imageBrowserURL = imageBrowserURL;
	}

	public String getImageUploadURL() {
		return imageUploadURL;
	}

	/**
	 * @ww.tagattribute required="false" type="string" default="/webwork/static/richtexteditor/editor/filemanager/upload/uploader.action?Type=Image"
	 * description="Sets the URL of the upload handler called when the user clicks the 'Send it to server' button in the 'Image' dialog window.
	 * In this way, you can create your custom Image Uploader that is well integrated with your system."
	 */
	public void setImageUploadURL(String imageUploadURL) {
		this.imageUploadURL = imageUploadURL;
	}

	public String getLinkBrowserURL() {
		return linkBrowserURL;
	}

	/**
	 * @ww.tagattribute required="false" type="string" default="/webwork/static/richtexteditor/editor/filemanager/browser/default/browser.html?Type=File&Connector=connectors/jsp/connector.action"
	 * description="Sets the URL of the page called when the user clicks the 'Browse Server' button in the 'Link' dialog window.
	 * In this way, you can create your custom File Browser that is well integrated with your system."
	 */
	public void setLinkBrowserURL(String linkBrowserURL) {
		this.linkBrowserURL = linkBrowserURL;
	}

	public String getLinkUploadURL() {
		return linkUploadURL;
	}

	/**
	 * @ww.tagattribute required="false" type="string" default="/webwork/static/richtexteditor/editor/filemanager/upload/uploader.action?Type=File"
	 * description="Sets the URL of the upload handler called when the user clicks the 'Send it to server' button in the 'Link' dialog window.
	 * In this way, you can create your custom Link Uploader that is well integrated with your system."
	 */
	public void setLinkUploadURL(String linkUploadURL) {
		this.linkUploadURL = linkUploadURL;
	}

	public String getPluginsPath() {
		return pluginsPath;
	}

	/**
	 * @ww.tagattribute required="false" type="string" default="/webwork/static/richtexteditor/plugins/"
	 * description="Sets the base path used when looking for registered plugins."
	 */
	public void setPluginsPath(String pluginsPath) {
		this.pluginsPath = pluginsPath;
	}

	public String getSkinPath() {
		return skinPath;
	}

	/**
	 * @ww.tagattribute required="false" type="string" default="/webwork/static/richtexteditor/skins/default"
	 * description="Sets the path to the skin (graphical interface settings) to be used by the editor."
	 */
	public void setSkinPath(String skinPath) {
		this.skinPath = skinPath;
	}

	public String getStartupFocus() {
		return startupFocus;
	}

	/**
	 * @ww.tagattribute required="false" type="boolean" default="false"
	 * description="Forces the editor to get the keyboard input focus on startup (page load)"
	 */
	public void setStartupFocus(String startupFocus) {
		this.startupFocus = startupFocus;
	}

	public String getStylesXmlPath() {
		return stylesXmlPath;
	}

	/**
	 * @ww.tagattribute required="false" type="string" default="/webwork/static/richtexteditor/fckstyles.xml"
	 * description="Sets the path to the XML file that has the definitions and rules of the styles used by the 'Style' toolbar command"
	 */
	public void setStylesXmlPath(String stylesXmlPath) {
		this.stylesXmlPath = stylesXmlPath;
	}

	public String getTabSpaces() {
		return tabSpaces;
	}

	/**
	 * @ww.tagattribute required="false" type="string" default="0"
	 * description="Set the number of spaces (&amp;nbsp;) to be inserted when the user hits the 'tab' key.
	 * This is an Internet Explorer only feature. Other browsers insert spaces automatically by default."
	 */
	public void setTabSpaces(String tabSpaces) {
		this.tabSpaces = tabSpaces;
	}

	public String getToolbarCanCollapse() {
		return toolbarCanCollapse;
	}

	/**
	 * @ww.tagattribute required="false" type="boolean" default="true"
	 * description="Tells the editor that the toolbar can be Collapsed/Expanded by the user when clicking the vertical bar placed on the left of it (on the right for 'rtl' languages)."
	 * 
	 */
	public void setToolbarCanCollapse(String toolbarCanCollapse) {
		this.toolbarCanCollapse = toolbarCanCollapse;
	}

	public String getToolbarSet() {
		return toolbarSet;
	}

	/**
	 * @ww.tagattribute required="false" type="string" default="Default"
	 * description="Set the name of the toolbar to display"
	 */
	public void setToolbarSet(String toolbarSet) {
		this.toolbarSet = toolbarSet;
	}

	public String getToolbarStartExpanded() {
		return toolbarStartExpanded;
	}

	/**
	 * @ww.tagattribute required="false" type="boolean" default="true"
	 * description="Decide if the toolbar should be expanded when the rich text editor is loaded"
	 */
	public void setToolbarStartExpanded(String toolbarStartExpanded) {
		this.toolbarStartExpanded = toolbarStartExpanded;
	}

	public String getUseBROnCarriageReturn() {
		return useBROnCarriageReturn;
	}

	/**
	 * @ww.tagattribute required="false" type="boolean" default="true"
	 * description="Decide if a &lt;br/&gt; should be used in place of the occurence of a carriage return"
	 */
	public void setUseBROnCarriageReturn(String useBROnCarriageReturn) {
		this.useBROnCarriageReturn = useBROnCarriageReturn;
	}

	public String getWidth() {
		return width;
	}

	/**
	 * @ww.tagattribute required="false" type="string" default="100%"
	 * description="set the width of the rich text editor"
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	public String getAllowFlashBrowse() {
		return allowFlashBrowse;
	}

	/**
	 * @ww.tagattribute required="false" type="boolean" default="true"
	 * description="determine if to allow flash browsing"
	 */
	public void setAllowFlashBrowse(String allowFlashBrowse) {
		this.allowFlashBrowse = allowFlashBrowse;
	}

	public String getAllowFlashUpload() {
		return allowFlashUpload;
	}

	/**
	 * @ww.tagattribute required="false" type="boolean" default="true"
	 * description="determine if to allow flash upload"
	 */
	public void setAllowFlashUpload(String allowFlashUpload) {
		this.allowFlashUpload = allowFlashUpload;
	}

	public String getAllowImageBrowse() {
		return allowImageBrowse;
	}

	/**
	 * @ww.tagattribute required="false" type="boolean" default="true"
	 * description="determine if to allow image browsing"
	 */
	public void setAllowImageBrowse(String allowImageBrowse) {
		this.allowImageBrowse = allowImageBrowse;
	}

	public String getAllowImageUpload() {
		return allowImageUpload;
	}

	/**
	 * @ww.tagattribute required="false" type="boolean" default="true"
	 * description="determine if to allow image uploading"
	 */
	public void setAllowImageUpload(String allowImageUpload) {
		this.allowImageUpload = allowImageUpload;
	}

	public String getAllowLinkBrowse() {
		return allowLinkBrowse;
	}

	/**
	 * @ww.tagattribute required="false" type="boolean" default="true"
	 * description="determine if to allow link browsing"
	 */
	public void setAllowLinkBrowse(String allowLinkBrowse) {
		this.allowLinkBrowse = allowLinkBrowse;
	}

	public String getAllowLinkUpload() {
		return allowLinkUpload;
	}

	/**
	 * @ww.tagattribute required="false" type="boolean" default="true"
	 * description="determine if to allow link uploading"
	 */
	public void setAllowLinkUpload(String allowLinkUpload) {
		this.allowLinkUpload = allowLinkUpload;
	}

	public String getFlashUploadAllowedExtension() {
		return flashUploadAllowedExtension;
	}

	/**
	 * @ww.tagattribute required="false" type="string" default=".(swf|fla)$"
	 * description="regexp for allowed flash upload file format"
	 */
	public void setFlashUploadAllowedExtension(String flashUploadAllowedExtension) {
		this.flashUploadAllowedExtension = flashUploadAllowedExtension;
	}

	public String getFlashUploadDeniedExtension() {
		return flashUploadDeniedExtension;
	}

	/**
	 * @ww.tagattribute required="false" type="string" default=""
	 * description="regexp for deinied flash upload file format"
	 */
	public void setFlashUploadDeniedExtension(String flashUploadDeniedExtension) {
		this.flashUploadDeniedExtension = flashUploadDeniedExtension;
	}

	public String getImageUploadAllowedExtension() {
		return imageUploadAllowedExtension;
	}

	/**
	 * @ww.tagattribute required="false" type="string" default=".(jpg|gif|jpeg|png)$"
	 * description="regexp for allowed image upload file format"
	 */
	public void setImageUploadAllowedExtension(String imageUploadAllowedExtension) {
		this.imageUploadAllowedExtension = imageUploadAllowedExtension;
	}

	public String getImageUploadDeniedExtension() {
		return imageUploadDeniedExtension;
	}

	/**
	 * @ww.tagattribute required="false" type="string" default=""
	 * description="regexp for denied image upload file format"
	 */
	public void setImageUploadDeniedExtension(String imageUploadDeniedExtension) {
		this.imageUploadDeniedExtension = imageUploadDeniedExtension;
	}

	public String getLinkUploadAllowedExtension() {
		return linkUploadAllowedExtension;
	}

	/**
	 * @ww.tagattribute required="false" type="string" default=""
	 * description="regexp for allowed link upload file format"
	 */
	public void setLinkUploadAllowedExtension(String linkUploadAllowedExtension) {
		this.linkUploadAllowedExtension = linkUploadAllowedExtension;
	}

	public String getLinkUploadDeniedExtension() {
		return linkUploadDeniedExtension;
	}

	/**
	 * @ww.tagattribute required="false" type="string" default=".(php|php3|php5|phtml|asp|aspx|ascx|jsp|cfm|cfc|pl|bat|exe|dll|reg|cgi)$"
	 * description="regexp for denied link upload file format"
	 */
	public void setLinkUploadDeniedExtension(String linkUploadDeniedExtension) {
		this.linkUploadDeniedExtension = linkUploadDeniedExtension;
	}

	public String getSmileyImages() {
		return smileyImages;
	}

	/**
	 * @ww.tagattribute required="false" type="string' default="['regular_smile.gif','sad_smile.gif','wink_smile.gif','teeth_smile.gif','confused_smile.gif','tounge_smile.gif','embaressed_smile.gif','omg_smile.gif','whatchutalkingabout_smile.gif','angry_smile.gif','angel_smile.gif','shades_smile.gif','devil_smile.gif','cry_smile.gif','lightbulb.gif','thumbs_down.gif','thumbs_up.gif','heart.gif','broken_heart.gif','kiss.gif','envelope.gif']"
	 * description="js array of smilies files to be included"
	 */
	public void setSmileyImages(String smileyImages) {
		this.smileyImages = smileyImages;
	}

	public String getSmileyPath() {
		return smileyPath;
	}

	/**
	 * @ww.tagattribute required="false" type="string" default="/webwork/static/richtexteditor/editor/images/smiley/msn/"
	 * description="path where smilies are located"
	 */
	public void setSmileyPath(String smileyPath) {
		this.smileyPath = smileyPath;
	}

	public String getTemplatesXmlPath() {
		return templatesXmlPath;
	}

	public void setTemplatesXmlPath(String templatesXmlPath) {
		this.templatesXmlPath = templatesXmlPath;
	}

	
}

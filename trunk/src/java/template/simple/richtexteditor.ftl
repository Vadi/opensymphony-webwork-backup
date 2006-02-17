<#if !stack.findValue('#richtexteditor_js_included')?exists><#t/>
	<script type="text/javascript" src="<@ww.url value='/webwork/richtexteditor/fckeditor.js' encode='false' />"></script>
	<#assign tmpVariable = stack.setValue('#richtexteditor_js_included', 'true') /><#t/>
</#if><#t/>
<script>
	var oFCKeditor_${parameters.id} = new FCKeditor( '${parameters.name}' ) ;
	<#-- basePath --><#t/>
	<#if parameters.basePath?exists><#t/>
		oFCKeditor_${parameters.id}.BasePath = '${parameters.basePath}' ;
	<#else><#t/>
		oFCKeditor_${parameters.id}.BasePath = '<@ww.url value="/webwork/richtexteditor/" />';
	</#if><#t/>
	<#-- height --><#t/>
	<#if parameters.height?exists><#t/>
		oFCKeditor_${parameters.id}.Height	= '${parameters.height}' ;
	</#if><#t/>
	<#-- width --><#t/>
	<#if parameters.width?exists><#lt/>
		oFCKeditor_${parameters.id}.Width = '${parameters.width}' ;
	</#if><#t/>
	<#-- toolbarSet -->
	<#if parameters.toolbarSet?exists>
		oFCKeditor_${parameters.id}.ToolbarSet = '${parameters.toolbarSet}' ;
	</#if>
	<#-- checkBrowser -->
	<#if parameters.checkBrowser?exists>
		oFCKeditor_${parameters.id}.CheckBrowser = '${parameters.checkBrowser}' ;
	</#if>
	
	<#-- displayError -->
	<#if parameters.displayError?exists>
		oFCKeditor_${parameters.id}.DisplayError = '${parameters.displayError}' ;
	</#if>
	
	<#-- value -->
	<@ww.set name="tmpVal" value="parameters.nameValue" />
	<#if (stack.findValue('#tmpVal')?has_content)>
		oFCKeditor_${parameters.id}.Value = '<@ww.property value="parameters.nameValue" />' ;
	</#if>
	
	<#-- customConfigurationsPath -->
	<#if parameters.customConfigurationsPath?exists>
		oFCKeditor_${parameters.id}.Config['CustomConfigurationsPath'] = '${parameters.customConfigurationsPath}' ;
	</#if>
	
	<#-- editorAreaCSS -->
	<#if parameters.editorAreaCss?exists>
		oFCKeditor_${parameters.id}.Config['EditorAreaCSS'] = '<@ww.url value=parameters.editorAreaCss?string />' ;
	</#if>	
	
	<#-- baseHref -->
	<#if parameters.baseHref?exists>
			oFCKeditor_${parameters.id}.Config['BaseHref'] = '${parameters.baseHref}' ;
	</#if>
	
	<#-- skinPath -->
	<#if parameters.skinPath?exists>
		oFCKeditor_${parameters.id}.Config['SkinPath'] = '${parameters.skinPath}' ;
	</#if>
	
	<#-- pluginsPath -->
	<#if parameters.pluginsPath?exists>
		oFCKeditor_${parameters.id}.Config['PluginsPath'] = '${parameters.pluginsPath}' ;
	</#if>
	
	<#-- fullPage -->
	<#if parameters.fullPage?exists>
		oFCKeditor_${parameters.id}.Config['FullPage'] = '${parameters.fullPage}' ;
	</#if>
	
	<#-- debug -->
	<#if parameters.debug?exists>
		oFCKeditor_${parameters.id}.Config['Debug'] = '${parameters.debug}' ;
	</#if>
	
	<#-- autoDetectLanguage -->
	<#if parameters.autoDetectLanguage?exists>
		oFCKeditor_${parameters.id}.Config['AutoDetectLanguage'] = '${parameters.autoDetectLanguage}' ;
	</#if>
	
	<#-- defaultLanguage -->
	<#if parameters.defaultLanguage?exists>
		oFCKeditor_${parameters.id}.Config['DefaultLanguage'] = '${parameters.defaultLanguage}' ;
	</#if>
	
	<#-- contentLanguageDirection -->
	<#if parameters.contentLangDirection?exists>
		oFCKeditor_${parameters.id}.Config['ContentLangDirection'] = '${parameters.contentLangDirection}' ;
	</#if>
	
	<#-- enableXHTML  -->
	<#if parameters.enableXHTML?exists>
		oFCKeditor_${parameters.id}.Config['EnableXHTML'] = '${parameters.enableXHTML}' ;
	</#if>
	
	<#-- enableSourceXHTML -->
	<#if parameters.enableSourceXHTML?exists>
		oFCKeditor_${parameters.id}.Config['EnableSourceXHTML'] = '${parameters.enableSourceXHTML}' ;
	</#if>
	
	<#-- fillEmptyBlocks -->
	<#if parameters.fillEmptyBlocks?exists>
		oFCKeditor_${parameters.id}.Config['FillEmptyBlocks'] = '${parameters.fillEmptyBlocks}' ;
	</#if>

	<#-- formatSource -->
	<#if parameters.formatSource?exists>
		oFCKeditor_${parameters.id}.Config['FormatSource'] = '${parameters.formatSource}' ;
	</#if>
	
	<#-- formatOutput -->
	<#if parameters.formatOutput?exists>
		oFCKeditor_${parameters.id}.Config['FormatOutput'] = '${parameters.formatOutput}' ;
	</#if>
	
	<#-- formatIndentator -->
	<#if parameters.formatIndentator?exists>
		oFCKeditor_${parameters.id}.Config['FormatIndentator'] = '${parameters.formatIndentator}' ;
	</#if>
	
	<#-- geckoUseSPAN -->
	<#if parameters.geckoUseSPAN?exists>
		oFCKeditor_${parameters.id}.Config['GeckoUseSPAN'] = '${parameters.geckoUseSPAN}' ;
	</#if>
	
	<#-- startupFocus -->
	<#if parameters.startupFocus?exists>
		oFCKeditor_${parameters.id}.Config['StartupFocus'] = '${parameters.startupFocus}' ;
	</#if>
	
	<#-- forcePasteAsPlainText -->
	<#if parameters.forcePasteAsPlainText?exists>
		oFCKeditor_${parameters.id}.Config['ForcePasteAsPlainText'] = '${parameters.forcePasteAsPlainText}' ;
	</#if>
	
	<#-- forceSimpleAmpersand -->
	<#if parameters.forceSimpleAmpersand?exists>
		oFCKeditor_${parameters.id}.Config['ForceSimpleAmpersand'] = '${parameters.forceSimpleAmpersand}' ;
	</#if>
	
	<#-- tabSpaces -->
	<#if parameters.tabSpaces?exists>
		oFCKeditor_${parameters.id}.Config['TabSpaces'] = '${parameters.tabSpaces}' ;	
	</#if>
	
	<#-- useBROnCarriageReturn -->
	<#if parameters.useBROnCarriageReturn?exists>
		oFCKeditor_${parameters.id}.Config['UseBROnCarriageReturn'] = '${parameters.useBROnCarriageReturn}' ;
	</#if>
	
	<#-- toolbarStartExpanded -->
	<#if parameters.toolbarStartExpanded?exists>
		oFCKeditor_${parameters.id}.Config['ToolbarStartExpanded'] = '${parameters.toolbarStartExpanded}' ;
	</#if>
	
	<#-- toolbarCanCollapse -->
	<#if parameters.toolbarCanCollapse?exists>
		oFCKeditor_${parameters.id}.Config['ToolbarCanCollapse'] = '${parameters.toolbarCanCollapse}' ;
	</#if>
	
	<#-- fontColors -->
	<#if parameters.fontColors?exists>
		oFCKeditor_${parameters.id}.Config['FontColors'] = '${parameters.fontColors}' ;
	</#if>
	
	<#-- fontNames -->
	<#if parameters.fontNames?exists>
		oFCKeditor_${parameters.id}.Config['FontNames'] = '${parameters.fontNames}' ;
	</#if>
	
	<#-- fontSizes -->
	<#if parameters.fontSizes?exists>
		oFCKeditor_${parameters.id}.Config['FontSizes'] = '${parameters.fontSizes}' ;
	</#if>
	
	<#-- fontFormats -->
	<#if parameters.fontFormats?exists>
		oFCKeditor_${parameters.id}.Config['FontFormats'] = '${parameters.fontFormats}' ;
	</#if>
	
	<#-- stylesXmlPath -->
	<#if parameters.stylesXmlPath?exists>
		oFCKeditor_${parameters.id}.Config['StylesXmlPath'] = '<@ww.url value=parameters.stylesXmlPath?string />' ;
	</#if>
	
	<#-- templatesXmlPath -->
	<#if parameters.templatesXmlPath?exists>
		oFCKeditor_${parameters.id}.Config['TemplatesXmlPath'] = '<@ww.url value=parameters.templatesXmlPath?string />' ;
	</#if>
	
	<#-- linkBrowserURL -->
	<#if parameters.linkBrowserURL?exists>
		oFCKeditor_${parameters.id}.Config['LinkBrowserURL'] = '<@ww.url value=parameters.linkBrowserURL?string />' ;
	</#if>
	
	<#-- imageBrowserURL -->
	<#if parameters.imageBrowserURL?exists>
		oFCKeditor_${parameters.id}.Config['ImageBrowserURL'] = '<@ww.url value=parameters.imageBrowserURL?string />' ;
	</#if>
	
	<#-- flashBrowserURL -->
	<#if parameters.flashBrowserURL?exists>
		oFCKeditor_${parameters.id}.Config['FlashBrowserURL'] = '<@ww.url value=parameters.flashBrowserURL?string />' ;
	</#if>
	
	<#-- linkUploadURL -->
	<#if parameters.linkUploadURL?exists>
		oFCKeditor_${parameters.id}.Config['LinkUploadURL'] = '<@ww.url value=parameters.linkUploadURL?string />' ;
	</#if>
	
	<#-- imageUploadURL -->
	<#if parameters.imageUploadURL?exists>
		oFCKeditor_${parameters.id}.Config['ImageUploadURL'] = '<@ww.url value=parameters.imageUploadURL?string />' ;
	</#if>
	
	<#-- flashUploadURL -->
	<#if parameters.flashUploadURL?exists>
		oFCKeditor_${parameters.id}.Config['FlashUploadURL'] = '<@ww.url value=parameters.flashUploadURL?string />' ;
	</#if>
	
	<#-- allowImageBrowse -->
	<#if parameters.allowImageBrowse?exists>
		oFCKeditor_${parameters.id}.Config['ImageBrowser'] = '<@ww.url value=parameters.allowImageBrowse?string />' ;
	</#if>
	
	<#-- allowLinkBrowse -->
	<#if parameters.allowLinkBrowse?exists>
		oFCKeditor_${parameters.id}.Config['LinkBrowser'] = '<@ww.url value=parameters.allowLinkBrowse?string />' ;
	</#if>	
	
	<#-- allowFlashBrowse -->
	<#if parameters.allowFlashBrowse?exists>
		oFCKeditor_${parameters.id}.Config['FlashBrowser'] = '${parameters.allowFlashBrowse}' ;
	</#if>
	
	<#-- allowImageUpload -->
	<#if parameters.allowImageUpload?exists>
		oFCKeditor_${parameters.id}.Config['ImageUpload'] = '${parameters.allowImageUpload}' ;
	</#if>
	
	<#-- allowLinkUpload -->
	<#if parameters.allowLinkUpload?exists>
		oFCKeditor_${parameters.id}.Config['LinkUpload'] = '${parameters.allowLinkUpload}' ;
	</#if>
	
	<#-- allowFlashUpload -->
	<#if parameters.allowFlashUpload?exists>
		oFCKeditor_${parameters.id}.Config['FlashUpload'] = '${parameters.allowFlashUpload}' ;
	</#if>
	
	<#-- linkUploadAllowedExtension -->
	<#if parameters.linkUploadAllowedExtension?exists>
		oFCKeditor_${parameters.id}.Config['LinkUploadAllowedExtensions'] = '${parameters.linkUploadAllowedExtension}' ;
	</#if>
	
	<#-- linkUploadDeniedExtension -->
	<#if parameters.linkUploadDeniedExtension?exists>
		oFCKeditor_${parameters.id}.Config['LinkUploadDeniedExtensions'] = '${parameters.linkUploadDeniedExtension}' ;
	</#if>
	
	<#-- imageUploadAllowedExtension -->
	<#if parameters.imageUploadAllowedExtension?exists>
		oFCKeditor_${parameters.id}.Config['ImageUploadAllowedExtensions'] = '${parameters.imageUploadAllowedExtension}' ;
	</#if>
	
	<#-- imageUploadDeniedExtension -->
	<#if parameters.imageUploadDeniedExtension?exists>
		oFCKeditor_${parameters.id}.Config['ImageUploadDeniedExtensions'] = '${parameters.imageUploadDeniedExtension}' ;
	</#if>
	
	<#-- flashUploadAllowedExtension -->
	<#if parameters.flashUploadAllowedExtension?exists>
		oFCKeditor_${parameters.id}.Config['FlashUploadAllowedExtensions'] = '${parameters.flashUploadAllowedExtension}' ;
	</#if>
	
	<#-- flashUploadDeniedExtension -->
	<#if parameters.flashUploadDeniedExtension?exists> 
		oFCKeditor_${parameters.id}.Config['FlashUploadDeniedExtensions'] = '${parameters.flashUploadDeniedExtension}' ;
	</#if>
	
	<#-- smileyPath -->
	<#if parameters.smileyPath?exists>
		oFCKeditor_${parameters.id}.Config['SmileyPath'] = '<@ww.url value=parameters.smileyPath?string />' ;
	</#if>
	
	<#-- smileyImages -->
	<#if parameters.smileyImages?exists>
		oFCKeditor_${parameters.id}.Config['SmileyImages'] = "${parameters.smileyImages}" ;
	</#if>
	
	
	oFCKeditor_${parameters.id}.Create() ;
</script>

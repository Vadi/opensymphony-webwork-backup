dojo.provide("webwork.widget.MyWidget");

dojo.require("dojo.widget.HtmlWidget");

dojo.widget.defineWidget(
	"webwork.widget.MyWidget",
	dojo.widget.HtmlWidget,
	{
		templatePath: dojo.uri.dojoUri('../webwork/widget/MyWidget.html'),
		templateCssPath: dojo.uri.dojoUri('../webwork/widget/MyWidget.css')
	}
);

<#assign ww=JspTaglibs["/WEB-INF/webwork.tld"]>

Hello, there

<@ww.form action='test.action'>
<@ww.textfield name="'test'" label="'test'"/>
<@ww.submit value="'Go'"/>
</@>

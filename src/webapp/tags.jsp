<%@ taglib uri="webwork" prefix="ww" %>
<%@ taglib uri="benchmark" prefix="benchmark" %>

<table border="0" align="center">
<form name="inputform">

<ww:label label="'textfield test'" value="scalar"/>
<ww:label label="'required textfield test'" required="true" value="scalar"/>
<ww:textfield label="'textfield test'" name="textfieldName" value="scalar" size="50"/>
<ww:file label="'file test'" name="uploadedFile" size="30"/>
<ww:password label="'password test'" name="passwordField" value="scalar" size="50"/>
<ww:checkbox label="'checkbox test'" value="blah" name="checkboxField1"/>
<ww:checkboxlist label="'checkboxlist test'" name="RadioField" value="list[1].value" list="list" listKey="key" listValue="value"/>
<ww:component label="'component test'" name="componentField" template="/templates/xhtml/empty.vm" />
<ww:radio label="'radio test'" name="RadioField" value="list[1].value" list="list" listKey="key" listValue="value"/>
<ww:select label="'multiple select test'" name="select1" value="multiValues" list="multiList" multiple="true" size="5"/>
<ww:select label="'select test'" name="select2" value="list[1].value" list="list" listKey="key" listValue="value" size="5"/>
<ww:select label="'pulldown test'" name="select3" value="list[1].value" list="list" listKey="key" listValue="value"/>
<ww:select label="'pulldown test (empty option)'" name="select4" value="list[1].value" list="list" listKey="key" listValue="value" />
<ww:doubleselect label="'double select test'" name="dselect1" doubleName="dselect2" value="list[1].value"
                 list="list" listKey="key" listValue="value" doubleList="children"/>
<ww:textarea rows="'10'" cols="'30'" label="'textarea test'" name="'textareaField'" value="scalar" />
<ww:hidden name="'hiddenField'" value="scalar"/>
<ww:submit value="'submit this thang!'" align="'right'"/>
</table>

<p>

textfield:
<benchmark:duration >
<ww:textfield label="textfield test" name="textfieldName" value="scalar" size="50"/>
</benchmark:duration> ms<br>

textfield:
<benchmark:duration >
<ww:file label="file test" name="uploadedFile" size="30"/>
</benchmark:duration> ms<br>

password:
<benchmark:duration >
<ww:password label="password test" name="passwordField" value="scalar" size="50"/>
</benchmark:duration> ms<br>

checkbox:
<benchmark:duration >
<ww:checkbox label="checkbox test" name="checkboxField2"/>
</benchmark:duration> ms<br>

checkboxlist:
<benchmark:duration >
<ww:checkboxlist label="checkboxlist test" name="RadioField" value="list[1].value" list="list" listKey="key" listValue="value"/>
</benchmark:duration> ms<br>

component:
<benchmark:duration >
<ww:component label="component test" name="componentField" template="/templates/xhtml/empty.vm" />
</benchmark:duration> ms<br>

radio:
<benchmark:duration >
<ww:radio label="radio test" name="RadioField" value="list[1].value" list="list" listKey="key" listValue="value"/>
</benchmark:duration> ms<br>

multiple select:
 <benchmark:duration >
<ww:select label="multiple select test" name="select1" value="list[1].value" list="list" listKey="key" listValue="value" multiple="true" size="5"/>
</benchmark:duration> ms<br>

select:
<benchmark:duration >
<ww:select label="select test" name="select2" value="list[1].value" list="list" listKey="key" listValue="value" size="5"/>
</benchmark:duration> ms<br>

pulldown:
<benchmark:duration >
<ww:select label="pulldown test" name="select3" value="list[1].value" list="list" listKey="key" listValue="value"/>
</benchmark:duration> ms<br>

pulldown (empty option):
<benchmark:duration >
<%-- todo what is emptyOption ? --%>
<ww:select label="pulldown test (empty option)" name="select4" value="list[1].value" list="list" listKey="key" listValue="value"/>
</benchmark:duration> ms<br>

double select:
<benchmark:duration >
<ww:doubleselect label="double select test" name="dselect3" doubleName="dselect4" value="list[1].value" 
                 list="list" listKey="key" listValue="value" doubleList="children"/>
</benchmark:duration> ms<br>

textarea:
<benchmark:duration >
<ww:textarea rows="10" cols="30" label="textarea test" name="textareaField" value="scalar" />
</benchmark:duration> ms<br>

hidden:
<benchmark:duration >
<ww:hidden name="hiddenField" value="scalar"/>
</benchmark:duration> ms<br>

submit:
<benchmark:duration >
<ww:submit value="submit this thang!" align="right"/>
</benchmark:duration> ms<br>

</form>
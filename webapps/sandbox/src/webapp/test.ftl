hello:
<hr/>

Hello!!!

<@ww.form action="test">
    <@ww.textfield name="foo">
        <@ww.param name="label">
            <@ww.url value="http://www.yahoo.com">
                <@ww.param name="foo">bar</@ww.param>
            </@ww.url>
        </@ww.param>
    </@ww.textfield>
    <@ww.datepicker label="Birthday" name="birthday"/>
</@ww.form>


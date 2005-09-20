hello:
<hr/>

Hello!!!

<@ww.form action="test">
    <@ww.textfield name="foo">
        <@ww.param name="label">
            LABEL: <@ww.url value="http://www.yahoo.com">
                <@ww.param name="foo">bar</@ww.param>
            </@ww.url> (END)
        </@ww.param>
    </@ww.textfield>
    <@ww.datepicker label="Birthday" name="birthday"/>
</@ww.form>


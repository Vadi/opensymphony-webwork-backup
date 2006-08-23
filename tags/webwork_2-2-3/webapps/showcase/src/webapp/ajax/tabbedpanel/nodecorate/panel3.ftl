
<div id="result">
</div>

<@ww.form action="panel3Submit" namespace="/nodecorate" theme="ajax">
	<@ww.select label="Gender" name="gender" list=r"%{#{'Male':'Male','Female':'Female'}}" theme="ajax" />
	<@ww.submit theme="ajax" resultDivId="result" />
</@ww.form>


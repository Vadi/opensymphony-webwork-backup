<#include "common.ftl"/>
<html>
	<head>
		<script type="text/javascript" src="/webwork/shared/jscalendar-1.0/calendar.js"></script>
		<script type="text/javascript" src="/webwork/shared/jscalendar-1.0/calendar-setup.js"></script>
		<script type="text/javascript" src="/webwork/shared/jscalendar-1.0/lang/calendar-en.js"></script>

		<@dojoRuntime 
			includes=[
				"webwork.widgets.Calendar"
			]
			isDebug=true
		/>
		
		<style>
			.sampleBox {
				text-align : center;
				vertical-align: middle;
				width:250px;
			}
		</style>

	</head>
	
	<body>

		<@example heading='Calendar'>
			<label for='calendar'>Date Select</label>
			<dojo:calendar id='calendar' format='%A, %B %e, %Y'></dojo:calendar>
			<input type='button' onclick='calendar.show()' value='Show Calendar'>
		</@>
		
	</body>
	
</html>


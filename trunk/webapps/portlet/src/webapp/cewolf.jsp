<html>
<head>
<title>Cewolf Taglib DEMO</title>
<%@ taglib uri="/WEB-INF/tld/cewolf.tld" prefix="cewolf"%>
<%@ taglib uri="webwork" prefix="ww"%>
</head>

<body bgcolor="#E5E5E5" text="#000000" link="#006699" vlink="#5493B4">

<%
com.opensymphony.xwork.util.OgnlValueStack stack = (com.opensymphony.xwork.util.OgnlValueStack)request.getAttribute("webwork.valueStack");
request.setAttribute("timeData", stack.findValue("timeData"));
request.setAttribute("xyData", stack.findValue("xyData"));
request.setAttribute("xyLinkGenerator", stack.findValue("xyLG"));
request.setAttribute("pieData", stack.findValue("pieData"));
request.setAttribute("categoryData", stack.findValue("categoryData"));
request.setAttribute("categoryToolTipGenerator", stack.findValue("catTG"));
request.setAttribute("highLowData", stack.findValue("hiloData"));
request.setAttribute("ganttData", stack.findValue("ganttData"));
request.setAttribute("signalsData", stack.findValue("signalsData"));

request.setAttribute("meterData", stack.findValue("valueDatasetProducer"));
request.setAttribute("meterRanges", stack.findValue("meterPP"));
 %>
<TABLE BORDER=0>
<TR>
<TD>
colorpaint<BR>
<cewolf:chart id="timeChart" title="TimeSeries" type="timeseries">
    <cewolf:colorpaint color="#EEEEFF"/>
    <cewolf:data>
        <cewolf:producer id="timeData"/>
    </cewolf:data>
</cewolf:chart>

<cewolf:img chartid="timeChart" renderer="/cewolf" width="300" height="300"/>
</TD>
<TD>
colorpaint with aplpha blending.<br> link map<br>
<cewolf:chart id="XYChart" title="XY" type="xy" xaxislabel="x-values" yaxislabel="y-values">
    <cewolf:colorpaint color="#AAAAFFEE"/>
    <cewolf:data>
        <cewolf:producer id="xyData" />
    </cewolf:data>
</cewolf:chart>
<cewolf:img chartid="XYChart" renderer="/cewolf" width="300" height="300" />
</TD>
<TD>
gradientpaint acyclic<br>
<cewolf:chart id="pieChart" title="Pie" type="pie">
    <cewolf:gradientpaint>
        <cewolf:point x="0" y="0" color="#FFFFFF" />
        <cewolf:point x="300" y="0" color="#DDDDFF" />
    </cewolf:gradientpaint>
    <cewolf:data>
        <cewolf:producer id="pieData" />
    </cewolf:data>
</cewolf:chart>
<cewolf:img chartid="pieChart" renderer="/cewolf" width="300" height="300"/>
</TD>
</TR>


<TR>
<TD>
background texture paint<br>
<cewolf:chart id="areaXYChart" title="AreaXY" type="areaxy" >
    <cewolf:texturepaint image="/images/bg.jpg" width="60" height="60" />
    <cewolf:data>
        <cewolf:producer id="xyData" />
    </cewolf:data>
</cewolf:chart>
<cewolf:img chartid="areaXYChart" renderer="/cewolf" width="300" height="300"/>
</TD>
<TD>
image map links<br>
<cewolf:chart id="scatterPlot" title="ScatterPlot" type="scatter">
    <cewolf:data>
        <cewolf:producer id="xyData" />
    </cewolf:data>
</cewolf:chart>
<cewolf:img chartid="scatterPlot" renderer="/cewolf" width="300" height="300" />
</TD>
<TD>
background image<BR>
<cewolf:chart id="areaChart" title="Area" type="area" xaxislabel="Fruit" yaxislabel="favorite"  background="/img/bg.jpg">
    <cewolf:data>
        <cewolf:producer id="categoryData" />
    </cewolf:data>
</cewolf:chart>
<cewolf:img chartid="areaChart" renderer="/cewolf" width="300" height="300"/>
</TD>

</TR>

<TR>
<TD>
vertical labels with tooltips <br>
but without image map links<BR>
<cewolf:chart id="horizontalBarChart" title="HorizontalBar" type="horizontalBar" xaxislabel="Fruit" yaxislabel="favorite">
    <cewolf:data>
        <cewolf:producer id="categoryData" />
    </cewolf:data>
</cewolf:chart>
<cewolf:img chartid="horizontalBarChart" renderer="/cewolf" width="300" height="300">
    <cewolf:map tooltipgeneratorid="categoryToolTipGenerator"/>
</cewolf:img>
</TD>
<TD>
<cewolf:chart id="horizontalBarChart3D" title="HorizontalBar3D" type="horizontalBar3D" xaxislabel="Fruit" yaxislabel="favorite">
    <cewolf:data>
        <cewolf:producer id="categoryData" />
    </cewolf:data>
</cewolf:chart>
<cewolf:img chartid="horizontalBarChart3D" renderer="/cewolf" width="300" height="300"/>
</TD>
<TD>
<cewolf:chart id="lineChart" title="LineChart" type="line" xaxislabel="Fruit" yaxislabel="favorite">
    <cewolf:data>
        <cewolf:producer id="categoryData" />
    </cewolf:data>
</cewolf:chart>
<cewolf:img chartid="lineChart" renderer="/cewolf" width="300" height="300"/>
</TD>
</TR>

<TR>
<TD>
<cewolf:chart id="stackedHorizontalBar" title="StackedHorizontalBar" type="stackedHorizontalBar" xaxislabel="Fruit" yaxislabel="favorite">
    <cewolf:data>
        <cewolf:producer id="categoryData" />
    </cewolf:data>
</cewolf:chart>
<cewolf:img chartid="stackedHorizontalBar" renderer="/cewolf" width="300" height="300"/>
</TD>
<TD>
<cewolf:chart id="stackedVerticalBar" title="StackedVerticalBar" type="stackedVerticalBar" xaxislabel="Fruit" yaxislabel="Favorite">
    <cewolf:colorpaint color="#EEEEFF" />
    <cewolf:data>
        <cewolf:producer id="categoryData" />
    </cewolf:data>
</cewolf:chart>
<cewolf:img chartid="stackedVerticalBar" renderer="/cewolf" width="300" height="300"/>
</TD>
<TD>
<cewolf:chart id="stackedVerticalBar3D" title="StackedVerticalBar3D" type="stackedVerticalBar3D" xaxislabel="Fruit" yaxislabel="favorite">
    <cewolf:data>
        <cewolf:producer id="categoryData" />
    </cewolf:data>
</cewolf:chart>
<cewolf:img chartid="stackedVerticalBar3D" renderer="/cewolf" width="300" height="300"/>
</TD>
</TR>


<TR>
<TD>
<cewolf:chart id="verticalBar" title="VerticalBarChart" type="verticalBar" xaxislabel="Fruit" yaxislabel="favorite">
    <cewolf:data>
        <cewolf:producer id="categoryData" />
    </cewolf:data>
</cewolf:chart>
<cewolf:img chartid="verticalBar" renderer="/cewolf" width="300" height="300"/>
</TD>
<TD>
<cewolf:chart id="verticalBar3D" title="VerticalBar3D" type="verticalBar3D" xaxislabel="Fruit" yaxislabel="favorite">
    <cewolf:data>
        <cewolf:producer id="categoryData" />
    </cewolf:data>
</cewolf:chart>
<cewolf:img chartid="verticalBar3D" renderer="/cewolf" width="300" height="300"/>
</TD>
<TD>
<cewolf:chart id="candleStick" title="CandleStick" type="candleStick" xaxislabel="Time">
    <cewolf:data>
        <cewolf:producer id="highLowData" />
    </cewolf:data>
</cewolf:chart>
<cewolf:img chartid="candleStick" renderer="/cewolf" width="300" height="300"/>
</TD>
</TR>

<TR>
<TD>
<cewolf:chart id="highLow" title="HighLow" type="highLow" xaxislabel="Time">
    <cewolf:data>
        <cewolf:producer id="highLowData" />
    </cewolf:data>
</cewolf:chart>
<cewolf:img chartid="highLow" renderer="/cewolf" width="300" height="300"/>
</TD>
<TD>
<cewolf:chart id="gantt" title="Gantt" type="gantt" xaxislabel="Workflow">
    <cewolf:colorpaint color="#FFEEEE"/>
    <cewolf:data>
        <cewolf:producer id="ganttData" />
    </cewolf:data>
</cewolf:chart>
<cewolf:img chartid="gantt" renderer="/cewolf" width="300" height="300"/>
</TD>
<TD>
Separate legend before chart rendering
<cewolf:chart id="verticalXY" title="VerticalXYBar" type="verticalXYBar" xaxislabel="Time" showlegend="false">
    <cewolf:data>
        <cewolf:producer id="timeData" />
    </cewolf:data>
</cewolf:chart>
<cewolf:legend id="verticalXY" renderer="/cewolf" width="300" height="40" mime="image/png" /><br>
<cewolf:img chartid="verticalXY" renderer="/cewolf" width="300" height="300" />
</TD>
</TR>

<TR>
<TD>
Pie Chart with separate legend <br>after chart rendering (tooltips)<br>
<cewolf:chart id="pie3d" title="Pie3D" type="pie3D" showlegend="false">
    <cewolf:data>
        <cewolf:producer id="pieData" />
    </cewolf:data>
</cewolf:chart>
<cewolf:img chartid="pie3d" renderer="/cewolf" width="300" height="300"   >
	<cewolf:map tooltipgeneratorid="pieToolTipGenerator"/>
</cewolf:img>
<br>
<cewolf:legend id="pie3d" renderer="/cewolf" width="300" height="40" />
</TD>
<TD>
Meter Chart<br>
<cewolf:chart id="meterChart" title="Speed" type="meter">
    <cewolf:data>
        <cewolf:producer id="meterData" />
    </cewolf:data>
   <cewolf:chartpostprocessor id="meterRanges">
   </cewolf:chartpostprocessor>
</cewolf:chart>
<cewolf:img chartid="meterChart" renderer="/cewolf" width="300" height="300" />
</TD>
<TD>
Stacked Area<br>
<cewolf:chart id="stackedArea" title="Stacked Area" type="stackedarea">
    <cewolf:data>
        <cewolf:producer id="categoryData" />
    </cewolf:data>
</cewolf:chart>
<cewolf:img chartid="stackedArea" renderer="/cewolf" width="300" height="300" />
</TR>

</TABLE>
<br>
<ww:a href="/index.vm" id="Index">Back to Index</ww:a>

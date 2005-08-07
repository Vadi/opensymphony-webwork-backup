<#assign webTable = tag/>
<#assign tableModel = webTable.model/>

<#if tableModel?exists>
<p align="center">
<table bgcolor="white" border="0" cellpadding="1" cellspacing="0" >
    <tr>
        <td>
            <table  border="0" cellpadding="2" cellspacing="1">
                <tr bgcolor="yellow">
<#list webTable.columns as curColumn>
<#if curColumn.visible>
                    <th>
<#if webTable.sortable>
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td>${curColumn.displayName}</td>
                                <td>
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td align="bottom"></td>
                                        </tr>
                                        <tr>
                                            <td align="top"></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
<#else>
                        ${curColumn.displayName}
</#if>
                    </th>
</#if>
</#list>
                </tr>
<#list webTable.rowIterator as curRow>
                <tr>
<#list curRow as curColumn>
                    <td>${curColumn}</td>
</#list>
                </tr>
</#list>
            </table>
        </td>
    </tr>
</table>
</#if>

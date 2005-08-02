<script language="JavaScript" type="text/javascript">
    var headerDiv = document.getElementById("tab_header_${parameters.id}");
    var content = "        <ul class='tab_header_main' id='tab_header_main'>\n";
    <#list parameters.tabs as tab>
        content = content + "        <li onmouseover='mouseIn(this)' onmouseout='mouseOut(this)' class ='tab_default tab_unselected'  id='tab_header_${tab.id}'>";
        content = content + "        <a href='#' onclick='dojo.event.topic.publish(\"${parameters.topicName}\", \"${tab.id}\");return false;'>${tab.tabName}</a>";
        content = content + "        </li>\n";
    </#list>
    content = content + "        </ul>\n";
    headerDiv.innerHTML = content;
</script>




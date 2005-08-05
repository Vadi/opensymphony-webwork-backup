<script language="JavaScript" type="text/javascript">
    var headerDiv_${parameters.id} = document.getElementById("tab_header_${parameters.id}");
    var content_${parameters.id} = "        <ul class='tab_header_main' id='tab_header_main'>\n";
    <#list parameters.tabs as tab>
        content_${parameters.id} = content_${parameters.id} + "        <li onmouseover='mouseIn(this)' onmouseout='mouseOut(this)' class ='tab_default tab_unselected'  id='tab_header_${tab.id}'>";
        content_${parameters.id} = content_${parameters.id} + "        <a href='#' onclick='dojo.event.topic.publish(\"${parameters.topicName}\", \"${tab.id}\");return false;'>${tab.tabName}</a>";
        content_${parameters.id} = content_${parameters.id} + "        </li>\n";
    </#list>
    content_${parameters.id} = content_${parameters.id} + "        </ul>\n";
    headerDiv_${parameters.id}.innerHTML = content_${parameters.id};
</script>




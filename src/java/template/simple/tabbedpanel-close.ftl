
<div class="tab_header" id="tab_header">
    <ul class="tab_header_main" id="tab_header_main">
    <#list tag.tabs as tab>
        <li onmouseover="mouseIn(this)"  onmouseout="mouseOut(this)" class ="tab_default tab_unselected"  id="tab_header_${tab.id}"><a href="#" onclick="dojo.event.topic.publish('${tag.topicName}', '${tab.id}');return false;">${tab.tabName}</a></li>
    </#list>
    </ul>
</div>

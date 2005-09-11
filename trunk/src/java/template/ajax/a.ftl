<a dojoType='BindAnchor'
<#if parameters.id?if_exists != "">id="${parameters.id?html}"</ #if>
    <#if parameters.href?if_exists != "">href="${parameters.href}"</ #if>
        <#if parameters.notifyTopics?exists>notifyTopics='${parameters.notifyTopics}'</ #if>
            <#if parameters.errorText?if_exists != "">errorHtml="${parameters.errorText?html}"</ #if>
                <#if parameters.showErrorTransportText>showTransportError='true'</#if>
                <#if parameters.afterLoading?exists>onLoad='${parameters.afterLoading}'</ #if>

                    <#if parameters.tabindex?exists>
                            tabindex="${parameters.tabindex?html}"< #rt/>
                </#if>
                <#if parameters.cssClass?exists>
                        class="${parameters.cssClass?html}"< #rt/>
            </#if>
            <#if parameters.cssStyle?exists>
                    style="${parameters.cssStyle?html}"< #rt/>
        </#if>
        <#include"/${parameters.templateDir}/simple/scripting-events.ftl" />
        evalResult='true'
        >
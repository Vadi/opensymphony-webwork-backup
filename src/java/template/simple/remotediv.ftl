<div id="${tag.id}">${tag.loadingText?default("")?html}</div>
<script language="JavaScript" type="text/javascript">
    <!--
        var remotediv_${tag.id} = new RemoteUpdateComponent( '${tag.url?default("")}', '${tag.id}', '${tag.reloadingText?default("")?html}', '${tag.errorText?default("")?html}', '${tag.showErrorTransportText?string}' );
        remotediv_${tag.id}.intervalRefresh(${tag.updateFreq});
    -->
</script>

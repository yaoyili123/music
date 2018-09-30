<template:frame isAdmini="true" htmlTitle="结果">
    <template:card>
    </template:card>
    <script type="text/javascript">
        $(function () {
            var hint = new Dialog(null, null, '提示');
            <c:choose>
            <c:when test="${successed}">
            hint.showSuccessed('${message}');    //显示成功提示框
            hint.createDialog();
            hint.showDialog();

            </c:when>
            <c:otherwise>
            hint.showFailed('${message}');
            hint.createDialog();
            hint.showDialog();
            </c:otherwise>
            </c:choose>

            setTimeout(function () {
                hint.closeDialog();      //关闭对话框
                if (window.location.href.indexOf('song') != -1) {
                    window.location.href = '/song?albumid=${albumid}';
                } else if (window.location.href.indexOf('album') != -1) {
                    window.location.href = '/album?singerid=${singerid}';
                } else if (window.location.href.indexOf('singer') != -1) {
                    window.location.href = '/singer';
                }
            }, 2000);
        });
    </script>
</template:frame>
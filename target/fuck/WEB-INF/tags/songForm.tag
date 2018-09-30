<%@ tag body-content="scriptless" dynamic-attributes="dynamicAttributes"
        trimDirectiveWhitespaces="true"  pageEncoding="UTF-8" %>
<%@ attribute name="action" rtexprvalue="true" required="true" %>
<%@ attribute name="data" type="java.io.Serializable" rtexprvalue="true" required="false" %>
<%@ include file="/WEB-INF/base.jspf" %>
<%
    request.setAttribute("albumid", request.getParameter("albumid"));
%>
<form
    <c:if test="${action=='insert'}">action="/song?action=insert&albumid=${albumid}"</c:if>
    <c:if test="${action=='update'}">
        action="/song?action=update&songid=${data.getSongid()}&albumid=${data.getAlbumid()}"</c:if>
      enctype="multipart/form-data" method="post">
    <h2 class="form_title">
        <c:if test="${action=='update'}">修改歌曲信息</c:if>
        <c:if test="${action=='insert'}">添加歌曲</c:if>
    </h2>
    歌曲名称<input type="text" name="name" <c:if test="${action=='update'}"> value="${data.getName()}" </c:if>>
    <br>
    歌曲时长<input type="time" name="time" <c:if test="${action=='update'}"> value="${data.getTime()}" </c:if>>
    <br>
    歌词<textarea name="lyric" rows="10" cols="30">
        <c:if test="${action=='update'}">${data.getLyric()}" </c:if>
            </textarea>
    <br>
    &nbsp;<input type="submit"
        <c:if test="${action=='insert'}"> value="添加" </c:if>
        <c:if test="${action=='update'}"> value="修改" </c:if>>
    &nbsp;&nbsp;&nbsp;<input type="reset" value="重置">
</form>
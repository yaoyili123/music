<%@ tag body-content="scriptless" dynamic-attributes="dynamicAttributes"
        trimDirectiveWhitespaces="true"  pageEncoding="UTF-8" %>
<%@ attribute name="action" rtexprvalue="true" required="true" %>
<%@ attribute name="data" type="java.io.Serializable" rtexprvalue="true" required="false" %>
<%@ include file="/WEB-INF/base.jspf" %>
<%
    request.setAttribute("singerid", request.getParameter("singerid"));
%>
<form
    <c:if test="${action=='insert'}">action="/album?action=insert&singerid=${singerid}"</c:if>
    <c:if test="${action=='update'}">action="/album?action=update&albumid=${data.getAlbumid()}"</c:if>
      enctype="multipart/form-data" method="post">
    <h2 class="form_title">
        <c:if test="${action=='update'}">修改专辑信息</c:if>
        <c:if test="${action=='insert'}">添加专辑</c:if>
    </h2>
    专辑名称<input type="text" name="name" <c:if test="${action=='update'}"> value="${data.getName()}" </c:if>>
    <br>
    发布时间<input type="date" name="publishtime" <c:if test="${action=='update'}"> value="${data.getPublishtime()}" </c:if>>
    <br>
    发行公司<input type="text" name="company" <c:if test="${action=='update'}"> value="${data.getCompany()}" </c:if>>
    <br>
    专辑类型<input type="text" name="type" <c:if test="${action=='update'}"> value="${data.getType()}" </c:if>>
    <br>
    专辑简介<textarea name="detail" rows="10" cols="30">
        <c:if test="${action=='update'}">${data.getDetail()}" </c:if>
            </textarea>
    <br>
    <img class="preview" style="height: 300px; width: 640px;"
    <c:if test="${action=='update'}"> src="<c:url value="/static/images/${data.getImage()}"></c:url>" </c:if>
    <c:if test="${action=='insert'}"> src="<c:url value="/static/images/zhihu.jpg"></c:url>"</c:if>>
    <br/> 图片(尽量上传分辨率大于640*300的图片) <br/>
    <input class="image_upload" type="file" name="image" size="23" >
    <br>
    &nbsp;<input type="submit"
        <c:if test="${action=='insert'}"> value="添加" </c:if>
        <c:if test="${action=='update'}"> value="修改" </c:if>>
    &nbsp;&nbsp;&nbsp;<input type="reset" value="重置">
</form>
<%@ tag body-content="scriptless" dynamic-attributes="dynamicAttributes"
        trimDirectiveWhitespaces="true"  pageEncoding="UTF-8" %>
<%@ attribute name="action" rtexprvalue="true" required="true" %>
<%@ attribute name="data" type="java.io.Serializable" rtexprvalue="true" required="false" %>
<%@ include file="/WEB-INF/base.jspf" %>
<form
    <c:if test="${action=='insert'}">action="/singer?action=insert"</c:if>
    <c:if test="${action=='update'}">action="/singer?action=update&singerid=${data.getSingerid()}"</c:if>
      enctype="multipart/form-data" method="post">
    <h2 class="form_title">
        <c:if test="${action=='update'}">修改歌手信息</c:if>
        <c:if test="${action=='insert'}">添加歌手</c:if>
    </h2>
    歌手名称<input type="text" name="name" <c:if test="${action=='update'}"> value="${data.getName()}" </c:if>>
    <br>
    歌手城市<input type="text" name="city" <c:if test="${action=='update'}"> value="${data.getCity()}" </c:if>>
    <br>
    歌手类型<input type="text" name="type" <c:if test="${action=='update'}"> value="${data.getType()}" </c:if>>
    <br>
    歌手简介<textarea name="detail" rows="10" cols="30">
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
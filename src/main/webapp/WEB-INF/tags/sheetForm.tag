<%@ tag body-content="scriptless" dynamic-attributes="dynamicAttributes"
        trimDirectiveWhitespaces="true"  pageEncoding="UTF-8" %>
<%@ attribute name="action" rtexprvalue="true" required="true" %>
<%@ attribute name="data" type="java.io.Serializable" rtexprvalue="true" required="false" %>
<%@ include file="/WEB-INF/base.jspf" %>
<div <c:if test="${action=='add_sheet_form'}">style="display: none"</c:if> class="form ${action}" >
    <form enctype="multipart/form-data" method="post">
        <h1 class="form_title">
            <c:if test="${action=='update_sheet_form'}">修改歌单信息</c:if>
            <c:if test="${action=='add_sheet_form'}">添加歌单</c:if>
        </h1>
        <br/> 歌单名称
        <input type="text" name="name" <c:if test="${action=='update_sheet_form'}"> value="${data.getName()}" </c:if>>
        <br/> 简介
        <textarea name="detail" rows="3" cols="30"><c:if test="${action=='update_sheet_form'}">${data.getDetail()}</c:if></textarea>
        <br/> 类型
        <input type="text" name="type" <c:if test="${action=='update_sheet_form'}"> value="${data.getType()}" </c:if>>
        <br/>
        <img class="preview" style="height: 300px; width: 640px;"
        <c:if test="${action=='update_sheet_form'}"> src="<c:url value="/static/images/${data.getImage()}"></c:url>" </c:if>
        <c:if test="${action=='add_sheet_form'}"> src="<c:url value="/static/images/zhihu.jpg"></c:url>" </c:if>>
        <br/> 图片(尽量上传分辨率大于640*300的图片) <br/>
        <input class="image_upload" type="file" name="image" size="23">
        <br/> &nbsp;
        <input type="button"
            <c:if test="${action=='add_sheet_form'}"> class="add_sheet_submit" value="添加" </c:if>
    <c:if test="${action=='update_sheet_form'}"> class="update_sheet_submit" sheetid="${data.getSheetid()}" value="修改" </c:if>/> &nbsp;&nbsp;&nbsp;
        <input type="reset"/>
    </form>
</div>
<%@ tag body-content="scriptless" dynamic-attributes="dynamicAttributes"
        trimDirectiveWhitespaces="true"  pageEncoding="UTF-8" %>
<%@ attribute name="imgPath" type="java.lang.String" rtexprvalue="true" required="true" %>
<%@ attribute name="haveButton" type="java.lang.Boolean" rtexprvalue="true" required="false" %>
<%@ attribute name="data" type="java.io.Serializable" rtexprvalue="true" required="false" %>
<%@ include file="/WEB-INF/base.jspf" %>

<div class="detail-img">
    <img src="<c:url value="/static/images/${data.getImage()}"></c:url>"/>
</div>
<div class="detail-info">
    <div class="info-title">
        <img src="<c:url value="/static/images/${imgPath}"></c:url>">
        <div><h2>${data.getName()}</h2></div>
    </div>
    <div class="info-add">
        <c:if test="${data.getBeanType() == 'album'}">
            发行公司：${data.getCompany()} <br/><br/>
            发行日期：${data.getPublishtime()} <br/><br/>
        </c:if>
        <c:if test="${data.getBeanType() == 'sheet'}">
            from：
            <a href="/userSheet?action=userPage&username=${data.getUsername()}">
                ${data.getNickname()}
            </a>
            <br/>
        </c:if>
        ${data.getDetail()}
    </div>
    <c:choose>
        <c:when test="${haveButton}"></c:when>
        <c:otherwise>
            <div class="buttons">
                <a href="#"><button><img src="<c:url value="/static/images/reply.png"></c:url>"/></button></a>
            </div>
        </c:otherwise>
    </c:choose>
</div>
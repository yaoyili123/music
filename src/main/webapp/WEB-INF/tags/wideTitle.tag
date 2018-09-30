<%@ tag body-content="scriptless" dynamic-attributes="dynamicAttributes"
              trimDirectiveWhitespaces="true"  pageEncoding="UTF-8" %>
<%@ attribute name="titleName" type="java.lang.String" rtexprvalue="true" required="true" %>
<%@ attribute name="more" fragment="true" required="false" %>
<%@ include file="/WEB-INF/base.jspf" %>

<div class="wide-title">
    <img src="<c:url value="/static/images/title.png"></c:url>"/>
    <span class="left-title">
        <c:choose>
            <c:when test="${!empty titleName}">
                ${fn:trim(titleName)}
            </c:when>
            <c:otherwise>
                全部
            </c:otherwise>
        </c:choose>
    </span>
    <span class="more"><jsp:invoke fragment="more"/></span>
</div>
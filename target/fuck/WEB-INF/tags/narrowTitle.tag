<%@ tag body-content="scriptless" dynamic-attributes="dynamicAttributes"
        trimDirectiveWhitespaces="true"  pageEncoding="UTF-8" %>
<%@ attribute name="titleName" type="java.lang.String" rtexprvalue="true" required="true" %>
<%@ attribute name="morePath" rtexprvalue="true" required="false" %>
<%@ attribute name="moreText" rtexprvalue="true" required="false" %>
<%@ attribute name="myClass" rtexprvalue="true" required="false" %>
<%@ include file="/WEB-INF/base.jspf" %>

<h3 class="narrow-title">
    <span>${fn:trim(titleName)}</span>
    <a class="${myClass} more" href="<c:url value="${morePath}"></c:url>">${moreText}</a>
</h3>
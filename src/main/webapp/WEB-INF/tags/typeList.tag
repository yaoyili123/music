<%@ tag body-content="scriptless" dynamic-attributes="dynamicAttributes"
        trimDirectiveWhitespaces="true"  pageEncoding="UTF-8" %>
<%@ attribute name="typeSet" type="java.util.List" rtexprvalue="true" required="false" %>
<%@ attribute name="dataClass" type="java.lang.String" rtexprvalue="true" required="false" %>
<%@ include file="/WEB-INF/base.jspf" %>

<ul class="type-list">
    <c:forEach items="${typeSet}" var="type">
        <li><a href="<c:url value="/data">
                <c:param name="action" value="list"></c:param>
                <c:param name="class" value="${dataClass}"></c:param>
                <c:param name="type" value="${type}"></c:param>
            </c:url>">${type}</a></li>
    </c:forEach>
</ul>
<%@ tag body-content="scriptless" dynamic-attributes="dynamicAttributes"
        trimDirectiveWhitespaces="true"  pageEncoding="UTF-8" %>
<%@ attribute name="dataset" type="java.util.List" rtexprvalue="true" required="false" %>
<%@ attribute name="isHide" type="java.lang.Boolean" rtexprvalue="true" required="false" %>
<%@ attribute name="isFull" type="java.lang.Boolean" rtexprvalue="true" required="false" %>
<%@ include file="/WEB-INF/base.jspf" %>
<ul class="block-list <c:if test="${isFull}">full-list</c:if>"
    <c:if test="${isHide}">style="display:none"</c:if>>
    <c:forEach items="${dataset}" var="data">
        <li>
            <c:choose>
                <c:when test="${data.getBeanType() == 'user'}">
                    <div class="block-item">
                    <img src="<c:url value="/static/images/head.jpg"></c:url>"/>
                    <a href="<c:url value="/userSheet">
                                <c:param name="action" value="userPage"></c:param>
                                <c:param name="username" value="${data.getUsername()}"></c:param>
                             </c:url>"></a></div>
                    <p><a href="<c:url value="/userSheet">
                            <c:param name="action" value="userPage"></c:param>
                            <c:param name="username" value="${data.getUsername()}"></c:param>
                        </c:url>">${data.getNickname()}</a></p>
                </c:when>
                <c:otherwise>
                    <div class="block-item">
                    <img src="<c:url value="/static/images/${data.getImage()}"></c:url>"/>
                    <a href="<c:url value="/data">
                            <c:param name="action" value="detail"></c:param>
                            <c:param name="class" value="${data.getBeanType()}"></c:param>
                            <c:param name="id" value="${data.getId()}"></c:param>
                         </c:url>"></a></div>
                    <p><a href="<c:url value="/data">
                        <c:param name="action" value="detail"></c:param>
                        <c:param name="class" value="${data.getBeanType()}"></c:param>
                        <c:param name="id" value="${data.getId()}"></c:param>
                    </c:url>">${data.getName()}</a></p>
                </c:otherwise>
            </c:choose>
        </li>
    </c:forEach>
</ul>

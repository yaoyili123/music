<%@ tag body-content="scriptless" dynamic-attributes="dynamicAttributes"
        trimDirectiveWhitespaces="true"  pageEncoding="UTF-8" %>
<%@ attribute name="isSheet" type="java.lang.Boolean" rtexprvalue="true" required="false" %>
<%@ attribute name="dataset" type="java.util.List" rtexprvalue="true" required="false" %>
<%@ include file="/WEB-INF/base.jspf" %>

<ul class="one-list
    <c:if test="${isSheet}">mysheet</c:if>">
    <c:forEach items="${dataset}" var="data">
        <li>
            <c:choose>
                <c:when test="${isSheet}">
                    <a href="<c:url value="/userSheet">
                            <c:param name="action" value="userMusic"></c:param>
                            <c:param name="id" value="${data.getSheetid()}"></c:param>
                         </c:url>">
                </c:when>
                <c:otherwise>
                    <a href="<c:url value="/data">
                            <c:param name="action" value="detail"></c:param>
                            <c:param name="class" value="${data.getBeanType()}"></c:param>
                            <c:param name="id" value="${data.getId()}"></c:param>
                         </c:url>">
                </c:otherwise>
            </c:choose>
                <div class="head">
                    <img src="<c:url value="/static/images/${data.getImage()}"></c:url>">
                </div>
                <div class="info">
                    <h4><span>${data.getName()}</span></h4>
                    <p>${data.getDetail()}</p>
                </div>
            </a>
            <c:if test="${isSheet}">
                <span class="oper" style="display: none;">
                    <a hidefocus="true" title="编辑" class="update_sheet"
                       href="<c:url value="/userSheet">
                            <c:param name="action" value="update"></c:param>
                            <c:param name="id" value="${data.getSheetid()}"></c:param>
                        </c:url>">
                        <img src="<c:url value="/static/images/edit.png"></c:url>"/></a>
                    <a hidefocus="true" sheetid="${data.getSheetid()}" class="delete_sheet" title="删除" href="#">
                        <img src="<c:url value="/static/images/delete.png"></c:url>"/></a>
                </span>
            </c:if>
        </li>
    </c:forEach>
</ul>
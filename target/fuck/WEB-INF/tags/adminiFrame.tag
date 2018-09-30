<%@ tag body-content="scriptless" dynamic-attributes="dynamicAttributes"
        trimDirectiveWhitespaces="true"  pageEncoding="UTF-8" %>
<%@ attribute name="picture" rtexprvalue="true" required="false" %>
<%@ include file="/WEB-INF/base.jspf" %>
<div class="admini ${picture}">
    <div class="admini_list">
        <jsp:doBody/>
    </div>
</div>
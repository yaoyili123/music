<%@ tag body-content="scriptless" dynamic-attributes="dynamicAttributes"
        trimDirectiveWhitespaces="true"  pageEncoding="UTF-8" %>
<%@ attribute name="isAdd" type="java.lang.Boolean" rtexprvalue="true" required="false" %>
<%@ attribute name="isDelete" type="java.lang.Boolean" rtexprvalue="true" required="false" %>
<%@ attribute name="sheetid" type="java.lang.Integer" rtexprvalue="true" required="false" %>
<%@ attribute name="dataset" type="java.util.List" rtexprvalue="true" required="false" %>
<%@ include file="/WEB-INF/base.jspf" %>

<table class="song-list">
    <thead>
    <tr>
        <th class="first">
            <div class="wp">歌曲标题</div></th>
        <th class="second">
            <div class="wp">时长</div></th>
        <th class="second">
            <div class="wp text">歌手</div></th>
        <th class="album">
            <div class="wp text">专辑</div></th>
    </tr></thead>
    <tbody>
    <c:forEach var="data" items="${dataset}">
        <tr>
            <td class="first">
                <div class="wp"><a href="<c:url value="#"></c:url>">${data.getName()}</a></div></th>
            <td class="second <c:if test="${isAdd || isDelete}">time</c:if>">
                <div class="wp">
                    <span>${data.getTime()}</span>
                    <c:if test="${isAdd}">
                        <span class="add_song" style="display: none">
                            <a hidefocus="true" songid="${data.getSongid()}" title="添加到歌单">
                                <img src="<c:url value="/static/images/add.png"></c:url>"/>
                            </a>
                        </span>
                    </c:if>
                    <c:if test="${isDelete}">
                        <span class="delete_song" style="display: none">
                            <a hidefocus="true" sheetid="${sheetid}" songid="${data.getSongid()}" title="从歌单中删除">
                                <img src="<c:url value="/static/images/delete.png"></c:url>"/>
                            </a>
                        </span>
                    </c:if>
                </div>
            </th>
            <td class="second">
                <div class="wp text">
                    <a href="<c:url value="/data">
                        <c:param name="action" value="detail"></c:param>
                        <c:param name="class" value="singer"></c:param>
                        <c:param name="id" value="${data.getSingerid()}"></c:param>
                    </c:url>">${data.getAuther()}</a></div></th>
            <td class="album">
                <div class="wp text">
                    <a href="<c:url value="/data">
                        <c:param name="action" value="detail"></c:param>
                        <c:param name="class" value="album"></c:param>
                        <c:param name="id" value="${data.getAlbumid()}"></c:param>
                    </c:url>">${data.getAlbum()}</a></div></th>
        </tr>
    </c:forEach>
    </tbody>
</table>
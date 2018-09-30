<%@ tag body-content="scriptless" dynamic-attributes="dynamicAttributes"
        trimDirectiveWhitespaces="true"  pageEncoding="UTF-8" %>
<%@ attribute name="htmlTitle" type="java.lang.String" rtexprvalue="true" required="true" %>
<%@ attribute name="isAdmini" type="java.lang.Boolean" rtexprvalue="true" required="false"%>
<%@ include file="/WEB-INF/base.jspf" %>

<!DOCTYPE html>
<html class="no-js">
<head>
    <meta charset="utf-8">
    <title>${fn:trim(htmlTitle)}</title>
    <link rel="stylesheet" href="<c:url value="/static/css/basic.css"></c:url>">
    <script src="<c:url value="/static/js/jquery-3.3.1.js"></c:url>"></script>
    <c:if test="${isLogined}">
        <script type="text/javascript">
            $(function(){
                showUserInfo();
            });
        </script>
    </c:if>
</head>
<body>
    <div class="nav_background">
        <nav>
            <h1 class="logo"><a href="index.jsp">
                <img src="<c:url value="/static/images/logo.png"></c:url>">
            </a></h1>
            <ul class="nav">
                <c:choose>
                    <c:when test="${isAdmini}">
                        <li><a href="<c:url value="/singer"></c:url>">歌手</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="<c:url value="/data?action=list&class=sheet"></c:url>">歌单</a></li>
                        <li><a href="<c:url value="/data?action=list&class=singer"></c:url>">歌手</a></li>
                        <li><a href="<c:url value="/data?action=list&class=album"></c:url>">专辑</a></li>
                        <li><a href="<c:url value="/userSheet?action=userMusic"></c:url>">我的音乐</a></li>
                    </c:otherwise>
                </c:choose>
            </ul>
            <div class="auth">
                <c:if test="${empty isAdmini}">
                    <form action="/data?action=search" method="post">
                        <input type="text" placeholder="请输入歌曲/专辑/歌手/歌单/用户名" name="key"/>
                        <input type="image" width="30" height="30" border="0"
                               src="<c:url value="/static/images/search.png"></c:url>">
                    </form>
                </c:if>
                <div class="user-info" style="display: inline-block;">
                    <c:choose>
                        <c:when test="${isAdmini}">
                            <div class="logined dropdown">
                                <img src="<c:url value="/static/images/head.jpg"></c:url>"/>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <a class="login" href="#">登录</a>
                            <div class="logined" style="display: none;">
                                <img src="<c:url value="/static/images/head.jpg"></c:url>"/>
                            </div>
                        </c:otherwise>
                    </c:choose>
                        <div class="dropdown-content">
                            <c:if test="${empty isAdmini}">
                                <a class="user-main" href="<c:url value="/userSheet?action=userPage"></c:url>">个人主页</a>
                                <a class="modify" href="<c:url value="#"></c:url>">修改密码</a>
                                <a class="logout" href="<c:url value="#"></c:url>">注销</a>
                            </c:if>
                        </div>
                </div>
            </div>
        </nav>
    </div>

    <div class ="dialog-interface" style="display: none">
    </div>

    <jsp:doBody/>

    <footer>
        <div class="foot-content">
            <p>
                Powered by No.5 啦啦啦德玛西亚, 组长：林润石 组员：江云翔、姚依理
            </p>
        </div>
    </footer>
    <script src="<c:url value="/static/js/nav.js"></c:url>"async defer></script>
    <script src="<c:url value="/static/js/dialog.js"></c:url>" async defer></script>
    <script src="<c:url value="/static/js/ajax.js"></c:url>" async defer></script>
    <script src="<c:url value="/static/js/slide.js"></c:url>" async defer></script>
    <script src="<c:url value="/static/js/content.js"></c:url>" async defer></script>
    <script src="<c:url value="/static/js/ready.js"></c:url>" async defer></script>
</body>
</html>
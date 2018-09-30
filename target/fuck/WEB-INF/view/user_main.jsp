<c:choose>
    <c:when test="${isLogined || isOtherPage}">
        <template:frame htmlTitle="个人主页">
            <template:card>
                <div class="wide full">
                    <div class="wide-body">
                        <div class="detail-img">
                            <img src="<c:url value="/static/images/head.jpg"></c:url>"/>
                        </div>
                        <div class="detail-info">
                            <div class="info-title">
                                <img src="<c:url value="/static/images/user.png"></c:url>">
                                <div><h2>${user.getNickname()}</h2></div>
                            </div>
                            <div class="info-add">我只是一个路人</div>
                            <div class="buttons">
                                <a href="#"><button><img src="<c:url value="/static/images/reply.png"></c:url>"/></button></a>
                            </div>
                        </div>
                        <div class="detail-list">
                            <template:wideTitle titleName="我的歌单"></template:wideTitle>
                            <template:blockList dataset="${sheets}" isFull="true"></template:blockList>
                        </div>
                    </div>
                </div>
            </template:card>
        </template:frame>
    </c:when>
    <c:otherwise>
        <template:error></template:error>
    </c:otherwise>
</c:choose>




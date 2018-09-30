<template:frame htmlTitle="主页">
    <div id="container">
        <div id="list" style="left:-1920px;">
            <img src="<c:url value="/static/images/slide3.jpg"></c:url>" alt="3"/>
            <img src="<c:url value="/static/images/slide1.jpg"></c:url>" alt="1"/>
            <img src="<c:url value="/static/images/slide2.jpg"></c:url>" alt="2"/>
            <img src="<c:url value="/static/images/slide3.jpg"></c:url>" alt="3"/>
            <img src="<c:url value="/static/images/slide1.jpg"></c:url>" alt="1"/>
        </div>
        <div id="buttons">
            <span index="1" class="on"></span>
            <span index="2"></span>
            <span index="3"></span>
        </div>
        <a href="javascript:;" id="prev" class="arrow">&lt;</a>
        <a href="javascript:;" id="next" class="arrow">&gt;</a>
    </div>

    <template:card>
        <div class="wide">
            <div class="wide-body">
                <template:wideTitle titleName="歌单推荐">
                    <jsp:attribute name="more">
                        <a href="<c:url value="#"></c:url>">
                            更多<img src="<c:url value="/static/images/arrow.png"></c:url>"/>
                        </a>
                    </jsp:attribute>
                </template:wideTitle>
                <template:blockList dataset="${sheets}"></template:blockList>
            </div>
            <div class="wide-body">
                <template:wideTitle titleName="专辑推荐">
                    <jsp:attribute name="more">
                        <a href="<c:url value="#"></c:url>">
                            更多<img src="<c:url value="/static/images/arrow.png"></c:url>"/>
                        </a>
                    </jsp:attribute>
                </template:wideTitle>
                <template:blockList dataset="${albums}"></template:blockList>
            </div>
        </div>

        <div class="narrow right-float">
            <template:narrowTitle titleName="推荐歌手" morePath="/WEB-INF/view/singer_list.jsp" moreText="查看全部">
            </template:narrowTitle>
            <template:oneList dataset="${singers}"></template:oneList>
        </div>
    </template:card>
</template:frame>

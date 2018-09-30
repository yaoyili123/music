<template:frame htmlTitle="歌手详情">
    <template:card>
        <div class="wide">
            <div class="wide-body">
                <template:wideTitle titleName="${singer.getName()}">
                </template:wideTitle>

                <div class="align-center">
                    <img src="<c:url value="/static/images/${singer.getImage()}"></c:url>"
                         style="height: 300px;width: 640px;"/>
                </div>

                <ul class="detail-ul">
                    <li><a class="l1 selected">作品</a></li>
                    <li><a class="l2">专辑</a></li>
                    <li><a class="l3">简介</a></li>
                </ul>
                <div class="detail-list">
                    <template:songList dataset="${songs}" isAdd="true"></template:songList>
                    <template:blockList dataset="${albums}" isHide="true"></template:blockList>
                    <div class="detail info-add" style="display: none">
                            ${singer.getDetail()}
                    </div>
                </div>
            </div>
        </div>

        <div class="narrow right-float">
            <template:narrowTitle titleName="其他歌手">
            </template:narrowTitle>
            <template:oneList dataset="${others}"></template:oneList>
        </div>
    </template:card>
</template:frame>
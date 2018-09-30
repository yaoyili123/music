<template:frame htmlTitle="搜索结果">
    <template:card>
        <template:wideTitle titleName="搜索结果——${key}">
        </template:wideTitle>
        <div class="result">
            <ul class="detail-ul">
                <li><a class="l1 selected">歌曲</a></li>
                <li><a class="l2">专辑</a></li>
                <li><a class="l3">歌手</a></li>
                <li><a class="l4">歌单</a></li>
                <li><a class="l5">用户</a></li>
            </ul>
            <div class="detail-list">
                <template:songList dataset="${songs}" isAdd="true"></template:songList>
                <template:blockList dataset="${albums}" isHide="true"></template:blockList>
                <template:blockList dataset="${singers}" isHide="true"></template:blockList>
                <template:blockList dataset="${sheets}" isHide="true"></template:blockList>
                <template:blockList dataset="${users}" isHide="true"></template:blockList>
            </div>
        </div>
    </template:card>
</template:frame>
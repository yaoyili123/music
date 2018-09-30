<template:frame htmlTitle="专辑详情">
    <template:card>
        <div class="wide">
            <div class="wide-body">
                <template:detailInfo data="${album}" imgPath="album.png"></template:detailInfo>
                <div class="detail-list">
                    <template:wideTitle titleName="歌曲列表"></template:wideTitle>
                    <template:songList dataset="${songs}" isAdd="true"></template:songList>
                </div>
            </div>
        </div>

        <div class="narrow right-float">
            <template:narrowTitle titleName="其他专辑"></template:narrowTitle>
            <template:oneList dataset="${others}"></template:oneList>
        </div>
    </template:card>
</template:frame>
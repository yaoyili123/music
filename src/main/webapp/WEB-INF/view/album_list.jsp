<template:frame htmlTitle="专辑列表">
    <template:card>
        <div class="narrow type roller_y">
            <template:typeList dataClass="album" typeSet="${types}"></template:typeList>
        </div>
        <div class="wide type-wide right-float roller_y" style="height: 531px;">
            <template:wideTitle titleName="${dataType}"></template:wideTitle>
            <template:blockList dataset="${albums}"></template:blockList>
        </div>
    </template:card>
</template:frame>
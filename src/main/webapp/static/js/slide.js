/*File:slide.js
/TODO:图片轮播
*/
"use strict";

function slidePicture() {
    //获取需要的DOM元素节点
    var buttons = $('#buttons span');
    var list = $('#list');
    var prev = $('#prev');
    var next = $('#next');
    var index = 1;              //按钮初始值
    var timer;  //定义计时器

    //定义需要的函数
    //给当前按钮加样式
    function buttonsShow() {
        //这里需要清除之前的样式
        for (var i = 0; i < buttons.length; i++) {
            if (buttons.eq(i).hasClass('on')) {
                buttons.eq(i).removeClass('on');
            }
        }
        //数组从0开始，故index需要-1
        buttons.eq(index-1).addClass('on');
    }

    //计算动画的目标位置
    function getNewPos(interval) {
        var newLeft = parseInt(list.css('left')) + interval;
        if (newLeft < -5760) {
            return -1920 + 'px';
        } 
        if(newLeft > -1920) {
            return -5760 + 'px';
        }
        return newLeft + 'px';
    }

    //播放动画
    function play() {
        //setInterval执行多次函数, setTimeout执行一次函数
        timer = setInterval(function () {
            next.click();
        }, 5000);
    }

    //停止动画
    function stop() {
        clearInterval(timer);
    }

    //轮播动画加载
    //添加箭头事件以及切图动画
    prev.on('click', function(){
        index -= 1;
        if (index < 1) {
            index = 3;
        }
        buttonsShow();  //设置按钮高亮
        list.animate({
            left: getNewPos(1920)
        }, 1000);
    });
    next.on('click', function(){
        index += 1;
        if (index > 3) {
            index = 1;
        }
        buttonsShow();      
        list.animate({
            left: getNewPos(-1920)
        }, 1000);
    });

    //给按钮加事件
    for (var i = 0; i < buttons.length; i++) {
        buttons.on('click', function() {
            /* 由于这里的index是自定义属性, 要用DOM2级方法*/
            var clickIndex = parseInt(this.getAttribute('index'));
            //计算动画移动距离
            var offset = 1920 * (index - clickIndex);

            //设置动画
            list.animate({
                left: getNewPos(offset)
            }, 1000); 

            //存放鼠标点击后的位置，用于小圆点的正常显示
            index = clickIndex;
            console.log("clickIndex=" + clickIndex + "  index=" + index + '\n');
            //设置按钮高亮
            buttonsShow();
        });
    }

    //轮播设置
    //获取容器DOM节点
    var container = $('#container');

    //设置鼠标悬停暂停, 松开继续
    container.mouseover(function(){ //DEBUG:有错误
        stop();
    });
    container.mouseout(function(){
        play();
    })
    //开始轮播
    play();
}

/*File:ready.js
/TODO:准备事件函数, 页面特效
*/
"use strict";

$(function(){
    //定义全局变量
    //创建所有悬浮框并添加事件
    window.login = new Dialog('.login', 'login', '登录');
    login.setClickEventHandler();
    window.modify = new Dialog('.modify', 'modify', '修改密码');
    modify.setClickEventHandler();
    window.deleteSheet = new Dialog('.delete_sheet', 'delete_sheet', "提示");
    deleteSheet.setClickEventHandler();

    //主页部分函数
    highlightPage();        //导航条当前页面高亮

    //是主页就加载图片轮播
    if (window.location.href.indexOf('main') != -1) {
        slidePicture();
    }

    //内容部分函数
    //页面已选类型列表添加高亮
    selectedHeightlight('.type-list li a', 'selected'); 
    selectedHeightlight('.narrow-left ul li a', 'selected2');
    zebraTable();                           //detail页面歌曲列表斑马线表格

    var links = $('.detail-ul li a');          //获取细节页面列表超链接
    //添加超链接点击事件，显示相应部分
    links.on('click', function (e) {
        var info;
        if (window.location.href.indexOf('singer') != -1) {
            info = ['.song-list', '.block-list', '.detail'];
        }
        if (window.location.href.indexOf('search') != -1) {
            info = ['.song-list', '.block-list:eq(0)', '.block-list:eq(1)', '.block-list:eq(2)', '.block-list:eq(3)'];
        }
        showMark(info, $(this));
    });

    //左侧列表加亮
    var over = function() {
        $(this).addClass('selected2');
    };
    var out = function() {
        $(this).removeClass('selected2');
    }
    setMouseHandle('.info', over, out);

    //显示表单操作按钮
    var over = function() {
        $(this).find('.oper').css('display', 'block');
    }
    var out = function() {
        $(this).find('.oper').hide();
    }
    setMouseHandle('.mysheet li', over, out); 
    
    //显示歌曲收藏按钮
    var out = function(){
        hideAndShow(this, 'span:first', '.add_song');
    }
    var over = function() {
        hideAndShow(this, '.add_song', 'span:first');
    }
    setMouseHandle('.time .wp', over, out);

    //显示歌曲删除按钮
    var out = function(){
        hideAndShow(this, 'span:first', '.delete_song');
    }
    var over = function() {
        hideAndShow(this, '.delete_song', 'span:first');
    }
    setMouseHandle('.time .wp', over, out);

    //注册用户歌单按钮事件
    $('.add_sheet').on('click', showAddSheetForm);
    //图片处理
    imageProcess();
    //注册创建歌单提交事件
    $('.add_sheet_submit').on('click', createSheetHandler);
    //注册收藏歌曲按钮事件
    $('.add_song>a').on('click', showAddSongDialog);
    $('.delete_song>a').on('click', deleteSongFromSheet);
});






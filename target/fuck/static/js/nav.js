/*File:nav.js
/TODO:导航栏函数
*/
"use strict";

//给当前页面的导航按钮设置为高亮
function highlightPage() {
    var navs = $('nav');
    if (navs.length == 0) return false;
    //取得导航条超链接
    var links = $('nav ul a');

    //和当前页面链接比较
    for (var i = 0; i < links.length; i++) {
        var tmp1 = links.eq(i).attr('href').split("=");
        var linkUrl = tmp1[tmp1.length - 1];
        //string.indexOf方法：寻找子串第一次出现的位置
        //若不匹配则返回-1
        if (window.location.href.indexOf(linkUrl) != -1) {
            links.eq(i).addClass('cur-index');
        }
    }
}

//显示下拉菜单
function showDropDown(){
    var navs = $('nav');
    if (navs.length == 0) return false; 
    //取得元素
    var div = $('.auth>div').eq(0);
    div.addClass('dropdown');
}

//隐藏下拉菜单
function closeDropDown() {
    var navs = $('nav');
    if (navs.length == 0) return false;
    //取得元素
    var div = $('.auth>div').eq(0);
    div.removeClass('dropdown');
}

//隐藏某个元素同时显示那个元素下面的元素
function hideAndShow(selecter, showElement, hideElement){
    $(selecter).find(showElement).show();
    $(selecter).find(hideElement).hide();
}

//登录成功后显示用户信息,且开启下拉菜单
function showUserInfo(){
    //切换显示的元素
    hideAndShow('.user-info', '.logined', '.login');
    //开启下拉菜单
    showDropDown();
    //注册下拉菜单事件
    $('.logout').on('click', logOut);
}

//注销事件
function logOut() {
    hideAndShow('.user-info', '.login', '.logined');
    closeDropDown();
    document.cookie = "music_user=;expires=-1;path=/"; //清除用户cookie
    window.location.reload();
}







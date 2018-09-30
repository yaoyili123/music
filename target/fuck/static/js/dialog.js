/*File:dialog.js
/TODO:登录框函数
*/
"use strict";

//TODO:指向并操纵对话框的对象
class Dialog {

    constructor(element, action, title) {
        this.element = element;
        this.action = action;
        this.title = title;
    }

    //根据对象信息创建对话框
    createDialog() {
        this.CreateFrame();     //设置边框
        $('.dialog-interface').html(this.frame + this.content);   //创建对话框
    }

    //显示对话框
    showDialog(){
        $('.dialog-interface').show();
    }

    //设置点击创建弹框事件
    setClickEventHandler() {
        var that = this;        //保存对象指针
        for (var i = 0; i < $(this.element).length; i++) {
            $(this.element).eq(i).on('click', function() {
                //根据框的类型设置不同的内容
                switch (that.action) {
                    case 'login':that.loginContent();
                        break;
                    case 'register':that.registerContent();
                        break;
                    case 'modify':that.mofityContent();
                        break;
                    case 'delete_sheet':that.checkDelete();
                        var sheetid = $(this)[0].getAttribute('sheetid'); //获取要删除歌单的ID
                        break;
                }

                //显示框
                $('.dialog-interface').show();
                that.createDialog();        //创建对话框
                addSupEvent();              //对话框框添加事件

                //根据表单的内容设置不同的事件处理函数
                switch (that.action) {
                    case 'login':   //登录表单需要先创建注册对话框
                        $('.button').on('click', loginHandler);
                        window.register = new Dialog('.register', 'register', '注册');
                        window.register.registerContent();
                        window.register.setClickEventHandler();
                        break;
                    case 'register':$('.button').on('click', registerHandler);
                        break;
                    case 'modify': $('.button').on('click', modifyHandler);
                        break;
                    case 'delete_sheet':$('.confirm_delete_sheet').on('click', function () {
                        deleteUserSheet(sheetid);
                    });
                        break;
                }
            });
        }
    }

    //改变内容
    changeContent() {
        $('.dialog-body').html(this.content);
    }

    //创建边框
    CreateFrame(){
        this.frame = "<div class='dialog-frame' style='top:150px; left: 500px;'>" +
            "<div class='dialog-title'> "+ this.title + "<img class='dialog-close' src='../../static/images/close.png'></div>";
    }

    //成功提示
    showSuccessed(str) {
        this.content = "<div class='dialog-body'>" +
            "<div class='dialog-main'>" +
            "<img style='vertical-align:middle' src='../../static/images/gou.jpg'/>" + str +
            "</div></div></div></div>";
    }

    //失败提示
    showFailed(str) {
        this.content = "<div class='dialog-body'>" +
            "<div class='dialog-main'>" +
            "<img style='vertical-align:middle' src='../../static/images/guo1.png'/>" + str +
            "</div></div></div></div>";
    }

    //删除确认
    checkDelete() {
        this.content = "<div id='dialog-body'>" +
            "<div class='delelt-check'>" +
            "<p style='padding-right: 30px; margin-bottom:50px;'> 确定删除歌单?</p>" +
            "<div class='button'> <a class='confirm_delete_sheet'>确认</a> <a class='cancel'>取消</a>" +
            "</div></div></div>";
    }

    //创建用户登录框
    loginContent(){
        this.content = "<div class='dialog-body'>" +
            "<div class='dialog-main'>" + "<div class='login-label'><span>用户名:</span></div>" +
            "<div class='login-input'><input type='text' name='p' placeholder='请输入用户名'></div>" +
            "<div class='login-label'><span>密码:</span></div>" +
            "<div class='login-input'>" + "<input type='password' placeholder='请输入登录密码'></div>" +
            "<div class='login-label'><label for='auto'>" +
            "<input type='checkbox' name='auto_login'/>管理员</label> " +
            "<a style='float:right' class='register'>创建新用户</a></div>" +
            "<div class='button'><a>登录</a></div></div></div></div>";
    }

    //创建注册框
    registerContent(){
        this.content = "<div class='dialog-body'>" +
            "<div class='dialog-main'>" + "<div class='login-label'><span>用户名:</span></div>" +
            "<div class='login-input'><input type='text' name='p' placeholder='设置用户名，6-16位, 仅限字母和数字'></div>" +
            "<div class='login-label'><span>密码:</span></div>" +
            "<div class='login-input'>" + "<input type='password' placeholder='设置登录密码，6-16位, 仅限字母和数字'></div>" +
            "<div class='login-label'><span>再次输入:</span></div>" +
            "<div class='login-input'>" + "<input type='password' placeholder='再次重复密码'></div>" +
            "<div class='login-label'><span>昵称:</span></div>" +
            "<div class='login-input'><input type='text' placeholder='仅限10个以下汉字或30个以下字符, 可以不写，我会给起个名字'></div>" +
            "<div class='button'><a>注册</a></div></div></div></div>";
    }

    //修改密码界面
    mofityContent() {
        this.content = "<div class='dialog-body'>" +
            "<div class='dialog-main'>" + "<div class='login-label'><span>旧密码:</span></div>" +
            "<div class='login-input'><input type='password' name='p' placeholder='填写旧密码' ></div>" +
            "<div class='login-label'><span>新密码:</span></div>" +
            "<div class='login-input'><input type='password' name='p' placeholder='设置新密码，6-16位, 仅限字母和数字'></div>" +
            "<div class='login-label'><span>再次输入:</span></div>" +
            "<div class='login-input'><input type='password' placeholder='再次确认密码'></div>" +
            "<div class='button'><a>修改密码</a></div></div></div></div>";
    }
    //创建歌单框
    sheetList(dataset){
        var content = "<div class='sheet roller_x dialog-body'>" +
            "<ul class='sheet-list one-list'>";
        for (var i = 0; i < dataset.length; i++) {
            content += "<li><a sheetid='" + dataset[i].sheetid + "' class='add_song_links'>" +
                "<div class='head'><img src='static/images/" + dataset[i].image + "'></div>" +
                "<div class='info'><h4><span>" + dataset[i].name + "</span></h4>" +
                "<p>" + dataset[i].detail + "</p></div></a></li>";
        }
        content += "</ul></div></div></div>";
        this.content = content;
    }
    //关闭对话框
    closeDialog() {
        $('.dialog-interface').html('');  //清除所有标记
        $('.dialog-interface').hide('slow');
    }

    //插入输入错误信息
    insertError(msg) {
        //先清除所有警告
        $('.err').hide();
        $(".dialog-frame .button").before("<div class='err'>" +
            "<img src='../../static/images/error.png'>" + msg +
            "</div>");
    }
}

//阻止事件冒泡到父元素
function stopPropagation(e) {
    if (e.stopPropagation)
        e.stopPropagation();  //阻止事件冒泡到父元素, stopPropagation不支持IE
    else
        e.cancelBubble = true;  //阻止事件冒泡到父元素, stopPropagation只支持IE或新版主流浏览器
}

//添加辅助事件
function addSupEvent() {
    var others = $("*:not('.login-interface *, .login-interface')");   //逆向选择
    //点击关闭登录框消失
    $('.dialog-close').on('click', Dialog.prototype.closeDialog);
    //点击取消对话框消失
    $('.cancel').on('click', Dialog.prototype.closeDialog);
    //点击控件时不让事件向上冒泡
    $('.dialog-interface').on('click', function(e) {
        stopPropagation(e);
    });
}

//确认按钮事件处理, 发送删除表单请求
function deleteUserSheet(sheetid) {
    //返回一个jqXHR对象，一个实现了Promise接口的对象
    $.get('userSheet', {
        action : 'delete',
        id : sheetid
    }).done(function (data) {
        console.log('成功，数据' + JSON.stringify(data));
        var data = JSON.parse(data);
        var hint = new Dialog(null, null, '提示');
        if (data.status == 'failed') {
            hint.showFailed('删除失败');
            hint.createDialog();
            hint.showDialog();
        } else {
            hint.showSuccessed('删除成功');    //显示成功提示框
            hint.createDialog();
            hint.showDialog();
        }

        //跳转到用户歌单列表页面
        setTimeout(function () {
            hint.closeDialog();      //关闭对话框
            window.location.href = '/userSheet?action=userMusic';
        }, 2000);
    });
}



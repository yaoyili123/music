//ajax.js 
//TODO:前端验证和AJAX
"use strict";
//点击登录按钮的事件处理函数
function loginHandler() {
    //获取输入
    var info = $('.dialog-main input');
    var username = $.trim(info.eq(0).val());
    var password = $.trim(info.eq(1).val());
    var isAdmini = info.eq(2).prop('checked');

    //验证输入是否为空
    var infos = [
        {value : username, error: '用户名不能为空'},
        {value : password, error: '密码不能为空'}
        ];
    if (isEmpty(infos)) return false;

    //验证输入格式
    if (!username.match(/[a-zA-Z0-9]{6,16}/)) {
        login.insertError('用户名格式错误');
        return false;
    }

    if (!password.match(/[a-zA-Z0-9]{6,16}/)){
        login.insertError('密码格式错误');
        return false;
    }

    //返回一个jqXHR对象，一个实现了Promise接口的对象
    $.post('user', {
        action : 'login',
        isAdmini: isAdmini,
        username : username,
        password : password
    },  'json').done(function (data) {
        console.log('成功，数据' + JSON.stringify(data));
        var data = JSON.parse(data);
        if (data.status == 'failed') {
            Dialog.prototype.insertError('用户名或密码不正确');
        } else {
            window.login.showSuccessed('登录成功');    //显示成功提示框
            window.login.createDialog();

            //显示用户登录框
            if (isAdmini) {
                setTimeout(function () {
                    window.login.closeDialog();      //关闭对话框
                    window.location.href = 'user?action=login';
                }, 2000);
            } else {
                //显示用户登录框
                setTimeout(function () {
                    window.login.closeDialog();      //关闭对话框
                    window.location.reload();//刷新页面
                }, 2000);
            }

        }
    }).fail(function (xhr, status) {
        console.log('失败 ' + xhr.status + ', 原因：' + status);
        Dialog.prototype.insertError('登录异常,请检查网络连接');
    });
}

//处理注册事件
function registerHandler() {
    //获取输入
    var info = $('.dialog-main input');
    var username = $.trim(info.eq(0).val());
    var password = $.trim(info.eq(1).val());
    var repasswd = $.trim(info.eq(2).val());
    var nickname = $.trim(info.eq(3).val());

    //验证输入是否为空
    var infos = [
        {value : username, error: '用户名不能为空'},
        {value : password, error: '密码不能为空'}
    ];
    if (isEmpty(infos)) return false;
    if(nickname == '')
        nickname = '小白';

    //验证输入格式
    if (!username.match(/[a-zA-Z0-9]{6,16}/)) {
        window.register.insertError('用户名格式错误');
        return false;
    }

    if (!password.match(/[a-zA-Z0-9]{6,16}/)){
        window.register.insertError('密码格式错误');
        return false;
    }
    if(getByteLength(nickname) > 30) {
        window.register.insertError('昵称太长');
        return false;
    }
    if(password != repasswd) {
        window.register.insertError('重复密码错误');
        return false;
    }

    //返回一个jqXHR对象，一个实现了Promise接口的对象
    $.post('user', {
        action : 'register',
        username : username,
        password : password,
        nickname : nickname
    }, 'json').done(function (data) {
        console.log('成功，数据' + JSON.stringify(data));
        var data = JSON.parse(data);
        if (data.status == 'failed') {
            Dialog.prototype.insertError('用户名已被占用');
        } else {
            window.register.showSuccessed('注册成功');    //显示成功提示框
            window.register.createDialog();

            //显示用户登录框
            setTimeout(function () {
                window.register.closeDialog();      //关闭对话框
            }, 2000);
            window.location.reload();//刷新页面
        }
    }).fail(function (xhr, status) {
        console.log('失败 ' + xhr.status + ', 原因：' + status);
        window.register.insertError('登录异常,请检查网络连接');
    });
}

//修改密码处理函数
function modifyHandler() {

    console.log("OK");
    //获取输入
    var info = $('.dialog-main input');
    var oldpd = $.trim(info.eq(0).val());
    var newpd = $.trim(info.eq(1).val());
    var repasswd = $.trim(info.eq(2).val());

    //验证输入是否为空
    var infos = [
        {value : oldpd, error: '旧密码不能为空'},
        {value : newpd, error: '新密码不能为空'},
    ];
    if (isEmpty(infos)) return false;

    //验证输入格式
    if (!newpd.match(/[a-zA-Z0-9]{6,16}/)){
        window.mofity.insertError('密码格式错误');
        return false;
    }

    if(newpd != repasswd) {
        window.mofity.insertError('重复密码错误');
        return false;
    }

    //返回一个jqXHR对象，一个实现了Promise接口的对象
    $.post('user', {
        action : 'modify',
        oldpw : oldpd,
        newpw : newpd
    }, 'json').done(function (data) {
        console.log('成功，数据' + JSON.stringify(data));
        var data = JSON.parse(data);
        if (data.status == 'failed') {
            if (data.info != undefined) {
                Dialog.prototype.insertError(data.info);
            } else {
                Dialog.prototype.insertError('修改失败');
            }
        } else {
            window.modify.showSuccessed('修改成功');    //显示成功提示框
            window.modify.createDialog();

            //显示用户登录框
            setTimeout(function () {
                window.modify.closeDialog();      //关闭对话框
            }, 2000);
        }
    }).fail(function (xhr, status) {
        console.log('失败 ' + xhr.status + ', 原因：' + status);
        window.register.insertError('登录异常,请检查网络连接');
    });
}

//判断字符串是否为空
function isEmpty(infos) {
    for (var i = 0; i < infos.length; i++) {
        if ($.trim(infos[i].value) == '') {
            Dialog.prototype.insertError(infos[i].error);
            return true;
        }
    }
    return false;
}

//返回String的字节长度
function getByteLength(str) {
    var len = 0;
    for (var i = 0; i < str.length; i++) {
        if (str[i].match(/[^\x00-\xff]/ig) != null) //判断是否是不在0-255的中文字符
            len += 3;
        else
            len += 1;
    }
    return len;
}

//显示歌单对话框
function showAddSongDialog() {
    //未登录就显示登录框
    if ($('.logined').is(':hidden')) {
        window.login.showDialog();
        return;
    }
    //获取歌单id并作为全局变量
    window.songid = $(this)[0].getAttribute("songid");

    $.get('userSheet?action=addSong').done(function (data) {
        console.log('成功，数据' + JSON.stringify(data));
        //创建对话框并显示数据
        var dataset = JSON.parse(data);
        var dialog = new Dialog(null, null, '添加歌曲');
        dialog.sheetList(dataset);
        dialog.createDialog();
        addSupEvent();
        dialog.showDialog();

        //设置事件
        $('.add_song_links').on('click', addSongToSheet);
        }).fail(function (xhr, status) {
        console.log('失败 ' + xhr.status + ', 原因：' + status);
        alert('登录异常,请检查网络连接');
    });
}

function addSongToSheet() {
    var sheetid = $(this)[0].getAttribute("sheetid");
    $.post('userSheet?action=addSong', {
        sheetid : sheetid,
        songid : window.songid
    }).done(function (data) {
        console.log('成功，数据' + JSON.stringify(data));
        //创建对话框并显示数据
        var data = JSON.parse(data);
        var hint = new Dialog(null, null, '提示');
        if (data.status == 'failed') {
            hint.showFailed('歌曲已存在');
            hint.createDialog();
            hint.showDialog();
        } else {
            hint.showSuccessed('添加成功');    //显示成功提示框
            hint.createDialog();
            hint.showDialog();
        }

        setTimeout(function () {
            hint.closeDialog();      //关闭对话框
        }, 2000);

    }).fail(function (xhr, status) {
        console.log('失败 ' + xhr.status + ', 原因：' + status);
        alert('登录异常,请检查网络连接');
    });
}
//从歌单中删除歌曲
function deleteSongFromSheet() {
    var
        sheetid = $(this)[0].getAttribute("sheetid"),
        songid = $(this)[0].getAttribute("songid");
    $.get('userSheet', {
        action: 'deleteSong',
        sheetid : sheetid,
        songid : songid
    }).done(function (data) {
        console.log('成功，数据' + JSON.stringify(data));
        //创建对话框并显示数据
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
            window.location.href = '/userSheet?action=userMusic&id=' + sheetid;
        }, 2000);
    }).fail(function (xhr, status) {
        console.log('失败 ' + xhr.status + ', 原因：' + status);
        alert('登录异常,请检查网络连接');
    });
}
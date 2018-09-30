/*content.js 
TODO:内容页函数库*/
"use strict";

//斑马线表格
function zebraTable() {
    var evens = $('.song-list tbody tr:nth-child(even)');
    if (evens.length == 0) return;
    evens.addClass('highlight');
}

//已选类型高亮
function selectedHeightlight(select, classValue){
    var links = $(select);
    if (links.length == 0) return;
    for (var i = 0; i < links.length; i++) {
        var linkUrl = links.eq(i).attr('href'),
            nowUrl = window.location.href;
        //获取最后的参数字符串
        var
            tmp1 = linkUrl.split("="),
            tmp2 = nowUrl.split("=");
        linkUrl = tmp1[tmp1.length - 1];
        nowUrl = tmp2[tmp2.length - 1];

        if (linkUrl == nowUrl) {
            links.eq(i).addClass(classValue);
        }
    }
}

//注册鼠标悬浮函数
function setMouseHandle(select, overHandle, outHandle){
    $(select).mouseover(overHandle);
    $(select).mouseout(outHandle);
}

//根据按钮填入相关标记并标记高亮
function showMark(info, element){
    var nodes = new Array();
    //元素节点并全部隐藏
    for (var i = 0; i < info.length; i++) {
        var node = $(info[i]);
        if (node == null) return;
        nodes.push(node);
        node.hide();
    }

    //消除所有链接的效果
    var $links = $('.detail-ul li a');
    $links.removeClass('selected');

    //根据触发事件的节点class决定显示的元素和高亮
    for (var i = 0; i < info.length; i++) {
        if (element.hasClass('l' + (i+1))) {
            element.addClass('selected');
            nodes[i].show();
            return;
        }
    }
}

//添加歌单事件处理函数
function showAddSheetForm() {
    //先隐藏其他所有盒子
    $('.show-sheet').hide();
    $('.update_sheet_form').hide();
    //显示添加表单
    $('.add_sheet_form').show();
}

//添加编辑事件处理函数
function showUpdateSheetForm() {
    $('.add_sheet_form').hide();
    $('.show-sheet').hide();
}

//当选择图片时, 显示相应的预览图并检查是否是图片类型
function imageProcess() {
    //获取预览和文件表单节点
    var
        fileInput = $('.image_upload'),
        preview = $('.preview');

    for (var i = 0; i < fileInput.length; i++) {
        //设置改变上传文件的事件监听
        fileInput[i].addEventListener('change', function () {
            //清楚图片
            preview.attr('src', '');
            //获取File引用：
            var file = this.files[0];
            //判断是否是图片
            if (file.type !== 'image/jpeg' && file.type !== 'image/png' && file.type !== 'image/gif') {
                alert('不是有效的图片文件!');
                return;
            }
            // 读取文件:
            var reader = new FileReader();
            reader.onload = function (e) {
                preview.attr('src', this.result);
            };

            // 以DataURL的形式读取文件:
            reader.readAsDataURL(file);
        });
    }
}

//处理创建歌单提交事件
function createSheetHandler() {
    //根据元素class获取表单元素并构建formData对象并发送Post ajax请求addSheet
    getFormData('.add_sheet_form form', 'addSheet', null, "添加成功", "添加失败");
}


//根据元素class获取表单元素并构建formData对象
//使用FormData，第一是在提交表单的时候，不需要写大量的js来获得表单数据，直接把表单对象构造就行了。
//第二就是可以直接异步上传文件，简直牛逼爆了
function getFormData(selecter, action, id, successMsg, failedMsg) {
    var form = new FormData($(selecter)[0]);
    if (id != null) {
        var url = 'userSheet?action=' + action + '&id=' + id;
    } else {
        var url = 'userSheet?action=' + action;
    }
    $.ajax({
        type: 'POST',
        url: url,
        data: form,
        processData:false,
        contentType:false,
        success:function (data) {
            console.log('成功，数据' + JSON.stringify(data));
            var data = JSON.parse(data);
            var hint = new Dialog(null, null, '提示');
            if (data.status == 'failed') {
                hint.showFailed(failedMsg);
                hint.createDialog();
                hint.showDialog();
            } else {
                hint.showSuccessed(successMsg);    //显示成功提示框
                hint.createDialog();
                hint.showDialog();
            }

            //跳转到用户歌单列表页面
            setTimeout(function () {
                hint.closeDialog();      //关闭对话框
                window.location.href = '/userSheet?action=userMusic';
            }, 2000);
        }
    });

}
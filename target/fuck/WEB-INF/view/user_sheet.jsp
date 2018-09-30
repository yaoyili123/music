<c:choose>
    <c:when test="${isLogined}">
        <template:frame htmlTitle="我的音乐">
            <template:card>
                <div class="narrow narrow-left roller_y">
                    <template:narrowTitle myClass="add_sheet" titleName="创建的歌单" morePath="#" moreText="新建歌单">
                    </template:narrowTitle>
                    <template:oneList dataset="${sheets}" isSheet="true"></template:oneList>
                </div>
                <div class="wide right-float">
                    <div class="wide-body user-sheet">
                        <template:sheetForm action="add_sheet_form"></template:sheetForm>
                        <c:if test="${action=='update'}">
                            <template:sheetForm data="${now}" action="update_sheet_form"></template:sheetForm>
                            <script type="text/javascript">
                                $(function () {
                                    //显示修改歌曲表单
                                    showUpdateSheetForm();
                                    //注册修改歌单提交事件
                                    $('.update_sheet_submit').on('click', function () {
                                        var sheetid = $(this)[0].getAttribute("sheetid"); //获取要修改歌单的ID
                                        getFormData('.update_sheet_form form', 'update', sheetid, "修改成功", "修改失败");
                                    });
                                });
                            </script>
                        </c:if>
                        <c:if test="${!empty sheets}">
                            <div class="show-sheet">
                                <template:detailInfo data="${now}" imgPath="sheet.png"></template:detailInfo>
                                <div class="detail-list">
                                    <template:wideTitle titleName="歌曲列表"></template:wideTitle>
                                    <template:songList sheetid="${now.getSheetid()}" isDelete="true" dataset="${songs}"></template:songList>
                                </div>
                            </div>
                        </c:if>
                    </div>
                </div>
            </template:card>
        </template:frame>
    </c:when>
    <c:otherwise>
        <template:error></template:error>
    </c:otherwise>
</c:choose>


<template:frame htmlTitle="专辑管理" isAdmini="true">
   <template:adminiFrame picture="lf">
       <h2>专辑列表</h2>
       <table border="1">
           <thead>
           <td>专辑名称</td>
           <td>发布时间</td>
           <td>歌手</td>
           <td>公司</td>
           <td>语言</td>
           <td>简介</td>
           </thead>
           <tbody>
           <c:forEach items="${dataset}" var="data">
               <tr>
                   <td>${data.getName()}</td>
                   <td>${data.getPublishtime()}</td>
                   <td>${data.getSinger()}</td>
                   <td>${data.getCompany()}</td>
                   <td>${data.getType()}</td>
                   <td>${data.getDetail()}</td>
                   <td class="action"><a href="/album?action=delete&albumid=${data.getAlbumid()}">删除</a></td>
                   <td class="action"><a href="/album?action=update&albumid=${data.getAlbumid()}">编辑</a></td>
                   <td class="action"><a href="/song?albumid=${data.getAlbumid()}">歌曲列表</a></td>
               </tr>
           </c:forEach>
           </tbody>
       </table>
       <br/><br/>
       第${pageNo}页&nbsp;&nbsp; 共${pageCount}页
       <c:if test="${pageNo > 1}">
           <a href="/album?singerid=${singerid}&page=${pageNo - 1}">上一页</a>
       </c:if>
       <c:if test="${pageNo < pageCount}">
           <a href="/album?singerid=${singerid}&page=${pageNo + 1}">下一页</a>
       </c:if>
       <a href="../../add_album.jsp?singerid=${singerid}"><button>添加</button></a>
   </template:adminiFrame>
</template:frame>

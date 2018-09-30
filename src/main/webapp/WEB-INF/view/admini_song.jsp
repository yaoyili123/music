<template:frame htmlTitle="歌曲管理" isAdmini="true">
   <template:adminiFrame picture="smoke">
       <h2>歌曲列表</h2>
       <table border="1">
           <thead>
           <td>歌名</td>
           <td>歌手</td>
           <td>专辑</td>
           <td>歌词</td>
           <td>时长</td>
           </thead>
           <tbody>
           <c:forEach items="${dataset}" var="data">
               <tr>
                   <td>${data.getName()}</td>
                   <td>${data.getAuther()}</td>
                   <td>${data.getAlbum()}</td>
                   <td>${data.getLyric()}</td>
                   <td>${data.getTime()}</td>
                   <td class="action"><a href="/song?action=delete&songid=${data.getSongid()}">删除</a></td>
                   <td class="action"><a href="/song?action=update&songid=${data.getSongid()}">编辑</a></td>
               </tr>
           </c:forEach>
           </tbody>
       </table>
       <br/><br/>
       第${pageNo}页&nbsp;&nbsp; 共${pageCount}页
       <c:if test="${pageNo > 1}">
           <a href="/song?albumid=${albumid}&page=${pageNo - 1}">上一页</a>
       </c:if>
       <c:if test="${pageNo < pageCount}">
           <a href="/song?albumid=${albumid}&page=${pageNo + 1}">下一页</a>
       </c:if>
       <a href="add_song.jsp?albumid=${albumid}"><button>添加</button></a>
   </template:adminiFrame>
</template:frame>

<template:frame htmlTitle="歌手管理" isAdmini="true">
   <template:adminiFrame picture="jay">
       <h2>歌手列表</h2>
       <table border="1">
           <thead>
           <td>歌手</td>
           <td>简介</td>
           <td>城市</td>
           <td>类型</td>
           </thead>
           <tbody>
           <c:forEach items="${dataset}" var="data">
               <tr>
                   <td>${data.getName()}</td>
                   <td>${data.getDetail()}</td>
                   <td>${data.getCity()}</td>
                   <td>${data.getType()}</td>
                   <td class="action"><a href="/singer?action=delete&singerid=${data.getSingerid()}">删除</a></td>
                   <td class="action"><a href="/singer?action=update&singerid=${data.getSingerid()}">编辑</a></td>
                   <td class="action"><a href="/album?singerid=${data.getSingerid()}">专辑列表</a></td>
               </tr>
           </c:forEach>
           </tbody>
       </table>
       <br/><br/>
       第${pageNo}页&nbsp;&nbsp; 共${pageCount}页
       <c:if test="${pageNo > 1}">
           <a href="/singer?page=${pageNo - 1}">上一页</a>
       </c:if>
       <c:if test="${pageNo < pageCount}">
           <a href="/singer?page=${pageNo + 1}">下一页</a>
       </c:if>
       <a href="../../add_singer.jsp"><button>添加</button></a>
   </template:adminiFrame>
</template:frame>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<script type="text/javascript">
<!--
	function changePage(page){
		if(changePage.hrefFun!=null){
			changePage.hrefFun(page);
		}
	}
//-->
</script>
<%-- 
<div class="pagination">
<span>
共${pageObj.totalItem}条记录,${pageObj.totalPage}页

</span>

<span>
<a onClick="changePage(1)" style="cursor:pointer">首页 </a>
<a onClick="changePage(${pageObj.curPage-1})" style="cursor:pointer">上一页 </a>
</span>
<c:forEach items="${pageObj.viewPages}" var="pageNum">
	<c:if test="${pageNum==pageObj.curPage}">
		<b>${pageNum}</b>
    </c:if>
   <c:if test="${pageNum!=pageObj.curPage}">
        <a onClick="changePage(${pageNum})" style="cursor:pointer">${pageNum}</a>
   </c:if>
</c:forEach>
<span>
<a onClick="changePage(${pageObj.curPage+1})" style="cursor:pointer">下一页 </a> 
<!-- <img src="../images/page/arrowR.gif" alt="" /> -->
<a onClick="changePage(${pageObj.totalPage})" style="cursor:pointer">尾页 </a>
</span>

</div>
--%>
<div class="pagination"><ul>
<li class="disabled"><a href="javascript:" onClick="changePage(${pageObj.curPage-1})">« 上一页</a></li>
<c:forEach items="${pageObj.viewPages}" var="pageNum">
	<c:if test="${pageNum==pageObj.curPage}">		
		<li class="active"><a href="javascript:">${pageNum}</a></li>
    </c:if>
   <c:if test="${pageNum!=pageObj.curPage}">        
        <li ><a  onClick="changePage(${pageNum})" style="cursor:pointer">${pageNum}</a></li>
   </c:if>
</c:forEach>
<li class="disabled"><a href="javascript:" onClick="changePage(${pageObj.curPage+1})" style="cursor:pointer">下一页 »</a></li>
<li class="disabled controls"><a href="javascript:">当前 <input type="text" onclick="this.select();" onkeypress="var e=window.event||this;var c=e.keyCode||e.which;if(c==13)page(this.value,20,'');" value="${pageObj.curPage}"> / <input type="text" onclick="this.select();" onkeypress="var e=window.event||this;var c=e.keyCode||e.which;if(c==13)page(1,this.value,'');" value="${pageObj.totalPage}"> 页，共 ${pageObj.totalItem} 条</a></li>
</ul>
<div style="clear:both;"></div></div>

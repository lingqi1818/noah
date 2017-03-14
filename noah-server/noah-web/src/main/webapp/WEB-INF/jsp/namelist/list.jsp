<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<%
	String path = request.getContextPath();

	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path ;
%>
<!DOCTYPE html>
<html lang="en">
<head>
	
	<base href="${basePath}">
	<meta charset="utf-8" />
	<title>亿方云风控管理平台</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />

	<!-- basic styles -->

	<link href="assets/css/bootstrap.min.css" rel="stylesheet" />
	<link rel="stylesheet" href="assets/css/font-awesome.min.css" />

	<!--[if IE 7]>
	<link rel="stylesheet" href="assets/css/font-awesome-ie7.min.css" />
	<![endif]-->

	<!-- page specific plugin styles -->

	<!-- fonts -->

	

	<!-- ace styles -->
	
	<link rel="stylesheet" href="assets/css/ace.min.css" />
	<link rel="stylesheet" href="assets/css/ace-rtl.min.css" />
	
	
	

	<!--[if lte IE 8]>
	<link rel="stylesheet" href="assets/css/ace-ie.min.css" />
	<![endif]-->

	<!-- inline styles related to this page -->

	<!-- ace settings handler -->

	<script src="assets/js/ace-extra.min.js"></script>

	<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

	<!--[if lt IE 9]>
	<script src="assets/js/html5shiv.js"></script>
	<script src="assets/js/respond.min.js"></script>
	<![endif]-->
	
	<link rel="stylesheet" href="assets/css/bootstrap.min.css" />
	<link rel="stylesheet" href="assets/css/page.css" />
	<script type="text/javascript" src="assets/js/jquery-1.10.2.min.js"></script>
	<link rel="stylesheet" href="assets/css/ace.min.css" />
	<script type="text/javascript">
	function goPage(page){		
		$("input[name='pageNum']").val(page);
		$("#queryForm").submit();
	}

	
	$(document).ready(function() {
		changePage.hrefFun=goPage;	
	});
	
	function editNameObj(nameId){
		location.href='namelist/toEditName?nameId='+nameId;
	}

	function updateStatus(nameId,status){

		$.ajax({
			type: "GET",
			url: "namelist/updateNameListStatus",
			data: "nameId="+nameId+"&status="+status,
			success: function(data){
				if(data.success){
					alert( "修改成功" );
				}else{
					alert("修改失败！");
				}

				location.href='namelist/query';
			}
		});
	}





	function add(){

		$.ajax({
			type: "POST",
			url: "namelist/add",
			data: $("#addForm").serialize(),
			success: function(data){
				if(data.success){
					alert( "保存成功" );
				}else{
					alert("保存失败！");
				}

				$('#myModal').modal('hide');

				location.href='namelist/query';

			}
		});
	}

	</script>
</head>

<body>
<%@ include file="/WEB-INF/jsp/head.jsp"%>

<div class="main-container" id="main-container">
	<script type="text/javascript">
		try{ace.settings.check('main-container' , 'fixed')}catch(e){}
	</script>

	<div class="main-container-inner">
		<a class="menu-toggler" id="menu-toggler" href="#">
			<span class="menu-text"></span>
		</a>

		<%@ include file="/WEB-INF/jsp/side.jsp"%>

		<div class="main-content">
			<div class="breadcrumbs" id="breadcrumbs">
				<script type="text/javascript">
					try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
				</script>

				<ul class="breadcrumb">
					<li>
						<i class="icon-home home-icon"></i>
						<a href="#">Home</a>
					</li>

					<li>
						<a href="#">名单管理</a>
					</li>
					<li class="active">黑白名单查询</li>
				</ul><!-- .breadcrumb -->

				
				
			</div>

			<div class="page-content">
				<div class="row">
					<div class="col-xs-12">
					<form action="namelist/query" method="post" id="queryForm">
						<input type="hidden" name="pageNum" value="${pageObj.curPage}">
						名单内容:<input type="tel" name="content" value="${content}"/>
						<button class="btn btn-sm btn-primary" onclick="$('#queryForm').submit()">查询</button>
						&nbsp;
						<button class="btn btn-sm btn-primary" data-toggle="modal" data-target="#myModal">
							新增
						</button>
					</form>
					</div>				
					<div class="col-xs-12">
						<!-- PAGE CONTENT BEGINS -->

						<div class="row">
							<div class="col-xs-12">
								<div class="table-header">
								</div>

								<div class="table-responsive">
									<table id="sample-table-2" class="table table-striped table-bordered table-hover">
										<thead>
										<tr>
										
											<th class="center">
												<label>
													<input type="checkbox" class="ace" />
													<span class="lbl"></span>
												</label>
											</th>
										
											<th>名单内容</th>
											<th>名单类型</th>
											<th class="hidden-480">名单等级</th>

											<th>
												<i class="icon-time bigger-110 hidden-480"></i>
												创建时间
											</th>
											<th class="hidden-480">修改时间</th>
											<th class="hidden-480">状态</th>
											<th class="hidden-480">备注</th>

											<th>操作</th>
										</tr>
										</thead>

										<tbody>
										<c:forEach items="${pageObj.datas}" var="nameObj">
										<tr>
										
											<td>
												<label>
													<input type="checkbox" class="ace" />
													<span class="lbl"></span>
												</label>
											</td>
										
											<td>${nameObj.content}</td>
											<td><c:if test="${nameObj.type==1}">手机号</c:if>
											<c:if test="${nameObj.type==2}">ip</c:if>
											<c:if test="${nameObj.type==3}">设备id</c:if>
											<c:if test="${nameObj.type==4}">beacon_id</c:if></td>
											<td><c:if test="${nameObj.grade==1}">黑名单</c:if><c:if test="${nameObj.grade==2}">白名单</c:if><c:if test="${nameObj.grade==3}">灰名单</c:if></td>
											<td>${nameObj.gmtCreated}</td>
											<td>${nameObj.gmtModified}</td>
											<td>
												<c:if test="${nameObj.status==3}">无效</c:if>
												<c:if test="${nameObj.status!=3}">有效</c:if>

											</td>
											<td>${nameObj.remark}</td>
											<td>
												<button class="btn btn-xs" onclick="editNameObj(${nameObj.id})">编辑</button>
												<c:if test="${nameObj.status==3}"><button class="btn btn-xs" onclick="updateStatus(${nameObj.id},0)">设为有效</button></c:if>
												<c:if test="${nameObj.status!=3}"><button class="btn btn-xs" onclick="updateStatus(${nameObj.id},3)">设为无效</button></c:if>

											</td>
										</tr>
										</c:forEach>

										
										</tbody>
									</table>
									<%@ include file="/WEB-INF/include/pagediv.jsp"%>
									
								</div>
							</div>
						</div>

						
						
					</div><!-- /.col -->
				</div><!-- /.row -->
			</div><!-- /.page-content -->
		</div><!-- /.main-content -->

		
	</div><!-- /.main-container-inner -->

	<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
		<i class="icon-double-angle-up icon-only bigger-110"></i>
	</a>


	<!-- 模态框（Modal） -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		 aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">×
					</button>
					<h4 class="modal-title" id="myModalLabel">
						新增名单
					</h4>
				</div>
				<div class="modal-body">

					<div style="padding: 100px 100px 10px;">
						<form role="form" id="addForm">
							<div class="form-group">
								<label for="name">名单内容</label>
								<input type="text" class="form-control" id="name" name="content"
									   placeholder="请输入名称">
							</div>
							<div class="form-group">
								<label for="name">名单类型</label>
								<select class="form-control" name="type">
									<option value="1" selected>手机号</option>
									<option value="2">IP地址</option>
									<option value="3">设备ID</option>
									<option value="4">beacon_id</option>
								</select>
							</div>
							<div class="form-group">
								<label for="name">名单等级</label>
								<select class="form-control" name="grade">
									<option value="1">黑名单</option>
									<option value="3">灰名单</option>
									<option value="2">白名单</option>
								</select>
							</div>
							<div class="form-group">
								<label for="name">备注</label>
								<textarea class="form-control" rows="3" name="remark"></textarea>
							</div>
						</form>
					</div>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default"
							data-dismiss="modal">关闭
					</button>
					<button type="button" class="btn btn-primary" onclick="add()">
						提交更改
					</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</div><!-- /.main-container -->

<!-- basic scripts -->

<!--[if !IE]> -->

<script src="assets/js/jquery-2.0.3.min.js"></script>

<!-- <![endif]-->

<!--[if IE]>
<script src="assets/js/ie/jquery-1.10.2.min.js"></script>
<![endif]-->

<!--[if !IE]> -->

<script type="text/javascript">
	window.jQuery || document.write("<script src='assets/js/jquery-2.0.3.min.js'>"+"<"+"/script>");
</script>

<!-- <![endif]-->

<!--[if IE]>
<script type="text/javascript">
	window.jQuery || document.write("<script src='assets/js/jquery-1.10.2.min.js'>"+"<"+"/script>");
</script>
<![endif]-->

<script>
	$(function () { $('#myModal').modal({
		keyboard: true,
		show:false
	})});
</script>


<script src="assets/js/bootstrap.min.js"></script>
<script src="assets/js/typeahead-bs2.min.js"></script>

<!-- page specific plugin scripts -->

<script src="assets/js/jquery.dataTables.min.js"></script>
<script src="assets/js/jquery.dataTables.bootstrap.js"></script>

<!-- ace scripts -->

<script src="assets/js/ace-elements.min.js"></script>
<script src="assets/js/ace.min.js"></script>
<div style="display:none"></div>
</body>
</html>

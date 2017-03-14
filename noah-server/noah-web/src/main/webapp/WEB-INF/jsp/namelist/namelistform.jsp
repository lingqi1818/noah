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
	<link rel="stylesheet" href="assets/css/ace.min.css" />
	<link rel="stylesheet" href="assets/css/ace-rtl.min.css" />
	<script src="assets/js/ace-extra.min.js"></script>
		
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
					<li class="active">编辑黑白名单</li>
				</ul><!-- .breadcrumb -->

				
				
			</div>

			<div class="page-content">
				<div class="row">								
					<div class="col-xs-12">
						<!-- PAGE CONTENT BEGINS -->

						<div class="row">
							<div class="col-xs-12">
								<div class="table-header">
								</div>

								<form class="form-horizontal" id="editForm" method="post" action="namelist/updateNameObj">
								<input type="hidden" name="nameId" value="${nameObj.id}"/>
									<div class="form-group">
										<label for="form-field-1" class="col-sm-3 control-label no-padding-right"> 名单内容</label>
										<div class="col-sm-9">
											${nameObj.content}
										</div>
									</div>
									<div class="space-4"></div>
									
									<div class="form-group">
										<label for="form-field-1" class="col-sm-3 control-label no-padding-right"> 名单类型</label>
										<div class="col-sm-9">
											<c:if test="${nameObj.type==1}">手机号</c:if>
											<c:if test="${nameObj.type==2}">ip</c:if>
											<c:if test="${nameObj.type==3}">设备id</c:if>
											<c:if test="${nameObj.type==4}">beacon_id</c:if>
										</div>
									</div>
									<div class="space-4"></div>
									
									<div class="form-group">
										<label for="form-field-1" class="col-sm-3 control-label no-padding-right"> 名单等级</label>
										<div class="col-sm-9">
											<select name="grade">
												<option value="1"  <c:if test="${nameObj.grade==1}">selected</c:if>>黑名单</option>
												<option value="2" <c:if test="${nameObj.grade==2}">selected</c:if>>白名单</option>
												<option value="3" <c:if test="${nameObj.grade==3}">selected</c:if>>灰名单</option>											
										   </select>
										</div>
									</div>

									<div class="space-4"></div>
									
									<div class="form-group">
										<label for="form-field-1" class="col-sm-3 control-label no-padding-right"> 备注</label>
										<div class="col-sm-9">
											<input type="text" name="remark" value="${nameObj.remark}"/>
										</div>
									</div>									
									<div class="space-4"></div>
									
									<div class="form-group">
										<label for="form-field-1" class="col-sm-3 control-label no-padding-right"></label>
										
										</lable>
										
										<div class="col-sm-9">	
											<button class="btn btn-sm btn-primary" onclick="$('#editForm').submit()" style="text-align:left">提交</button>										
										</div>
									</div>
									
								</form>
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
</div><!-- /.main-container -->


<div style="display:none"></div>
</body>
</html>

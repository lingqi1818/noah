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
						<a href="#">规则管理</a>
					</li>
					<li class="active">新增规则</li>
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

								<form class="form-horizontal" id="addForm" method="post" action="${basePath}/rulemanage/add">

									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" > 事件类型 </label>

										<div class="col-sm-9">
											<select class="form-control" name="eventType" style="width:100px;float:left;" id="form-field-select-1">
												<option value="">&nbsp;</option>
												<option value="login">登陆事件</option>
												<option value="register">注册事件</option>
												<option value="submitOrder">订单事件</option>
											</select>
										</div>


									</div>

									<div class="space-4"></div>

									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 规则编号 </label>

										<div class="col-sm-9">
											<input type="text" id="form-field-2" name="ruleCode" style="width:100px;float:left;" class="col-xs-10 col-sm-5" />
										</div>
									</div>

									<div class="space-4"></div>

									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 规则名称 </label>

										<div class="col-sm-9">
											<input type="text" id="form-field-3" name="ruleName" class="col-xs-10 col-sm-5" />
										</div>
									</div>

									<div class="space-4"></div>

									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-4"> 主属性 </label>

										<div class="col-sm-9">
											<input type="text" id="form-field-4" name="primaryField" style="width:100px;float:left;" class="col-xs-10 col-sm-5" />
										</div>
									</div>

									<div class="space-4"></div>

									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-5"> 从属性 </label>

										<div class="col-sm-9">
											<input type="text" id="form-field-5" name="secondaryField" style="width:100px;float:left;" class="col-xs-10 col-sm-5" />
										</div>
									</div>

									<div class="space-4"></div>


									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" > 计算方式 </label>

										<div class="col-sm-9">
											<select class="form-control" name="operation" style="width:200px;float:left;" >
												<option value="">&nbsp;</option>
												<option value="1">判断是否在取证库</option>
												<option value="2">求主属性出现次数</option>
												<option value="3">求主属性关联从属性个数</option>
											</select>
										</div>
									</div>

									<div class="space-4"></div>

									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-7"> 时间片 </label>

										<div class="col-sm-9">
											<input type="text" id="form-field-7" name="timeRange" style="width:100px;float:left;" class="col-xs-10 col-sm-5" />
											<select class="form-control" name="timeUnit" style="width:100px;float:left;" >
												<option value="">&nbsp;</option>
												<option value="0">天</option>
												<option value="1">小时</option>
												<option value="2">分钟</option>
											</select>
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" > 比较方式 </label>

										<div class="col-sm-9">
											<select class="form-control" name="compareType" style="width:100px;float:left;" >
												<option value="">&nbsp;</option>
												<option value="0">大于</option>
												<option value="1">大于等于</option>
												<option value="2">小于</option>
												<option value="3">小于等于</option>
											</select>
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-10"> 阈值 </label>

										<div class="col-sm-9">
											<input type="number" id="form-field-10" name="threshold" style="width:100px;float:left;" class="col-xs-10 col-sm-5" />
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-11"> 权重 </label>

										<div class="col-sm-9">
											<input type="number" id="form-field-11" name="riskScore" style="width:100px;float:left;" class="col-xs-10 col-sm-5" />
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right"> 状态 </label>

										<div class="col-sm-9">
											<select class="form-control" name="status" style="width:100px;float:left;" >
												<option value="">&nbsp;</option>
												<option value="0">启用</option>
												<option value="1">停用</option>
											</select>
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-13"> 描述 </label>

										<div class="col-sm-9">
											<input type="text" id="form-field-13" name="description" class="col-xs-10 col-sm-5" />
										</div>
									</div>

									<div class="space-4"></div>


									<div class="clearfix form-actions">
										<div class="col-md-offset-3 col-md-9">
											<button class="btn btn-info" type="button" onclick="$('#addForm').submit()">
												<i class="icon-ok bigger-110"></i>
												Submit
											</button>

											&nbsp; &nbsp; &nbsp;
											<button class="btn" type="reset">
												<i class="icon-undo bigger-110"></i>
												Reset
											</button>
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

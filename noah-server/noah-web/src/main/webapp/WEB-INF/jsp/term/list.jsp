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

	function updateStatus(id,status){

		$.ajax({
			type: "GET",
			url: "riskTerm/updateStatus",
			data: "id="+id+"&status="+status,
			success: function(data){
				if(data.success){
					alert( "修改成功" );
				}else{
					alert("修改失败！");
				}

				location.href='riskTerm/query';
			}
		});
	}





	function add(){

		var word = $("#word").val();
		var wholeWord = $("#wholeWord").val();
		var allowSkip = $("#allowSkip").val();


		$.ajax({
			type: "POST",
			url: "riskTerm/add",
			data: $("#addForm").serialize(),
			success: function(data){
				if(data.success){
					alert( "保存成功" );
				}else{
					alert("保存失败！");
				}

				$('#myModal').modal('hide');

				location.href='riskTerm/query';

			}
		});
	}

	function showUpdate(riskTermId){

		var objs = $("input[name='wordType']:checked");

		$.each(objs,function(name,value) {

			obj.prop("checked",false);
		});


		$.ajax({
			type: "POST",
			url: "riskTerm/showUpdate",
			data: "id="+riskTermId,
			success: function(data){
				if(data.success){
					var term = data.data
					$("#u_riskTermId").val(term.id);
					$("#u_word").val(term.word);
					$("#u_wholeWord option[value='"+term.wholeWord+"']").attr("selected","true");
					$("#u_allowSkip option[value='"+term.allowSkip+"']").attr("selected","true");
					$("#u_weight option[value='"+term.weight+"']").attr("selected","true");

					var wordType = term.wordType;

					if(wordType !=null && wordType.length>0){
						var wt = wordType.split(',');

						$.each(wt,function(name,value) {

							var obj=$(":checkbox[value="+value+"]");
							obj.prop("checked",true);//将得到的checkbox选中
						});
					}

					$('#updateRiskTermModal').modal('show');
					return;
				}else{
					alert(data.apiMessage);
					return;
				}
			}
		});

	}

	function updateRiskTerm(){

		$.ajax({
			type: "POST",
			url: "riskTerm/update",
			data: $("#updateRiskTermForm").serialize(),
			success: function(data){
				if(data.success){
					alert( "修改成功" );
				}else{
					alert("修改失败！");
				}

				$('#updateRiskTermModal').modal('hide');

				location.href='riskTerm/query';

			}
		});

	}


	function deleteRiskTerm(riskTermId){
		$.ajax({
			type: "POST",
			url: "riskTerm/delete",
			data: "id="+riskTermId,
			success: function(data){
				if(data.success){
					alert( "删除成功" );
				}else{
					alert("删除失败！"+data.apiMessage);
				}

				location.href='riskTerm/query';

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
						<a href="#">危险词管理</a>
					</li>
					<li class="active">危险词查询</li>
				</ul><!-- .breadcrumb -->

				
				
			</div>

			<div class="page-content">
				<div class="row">
					<div class="col-xs-12">
					<form action="riskTerm/query" method="post" id="queryForm">
						<input type="hidden" name="pageNum" value="${pageObj.curPage}">
						单词:<input type="text" name="word" value="${word}"/>
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
									<table id="sample-table-2" class="table table-striped table-bordered table-hover" style="TABLE-LAYOUT:fixed;WORD-BREAK:break-all" >
										<thead>
										<tr>
											<th>内容</th>
											<th>适用范围</th>
											<th>整词匹配</th>
											<th>跳字匹配</th>
											<th>权重</th>
											<th>
												<i class="icon-time bigger-110 hidden-480"></i>
												创建时间
											</th>
											<th>状态</th>
											<th>操作</th>
										</tr>
										</thead>

										<tbody>
										<c:forEach items="${pageObj.datas}" var="term">
										<tr>
											<td>${term.word}</td>
											<td>${term.wordTypeStr}</td>
											<td>
												<c:if test="${term.wholeWord==1}">是</c:if>
												<c:if test="${term.wholeWord==0}">否</c:if>
								            </td>
											<td>
												<c:if test="${term.allowSkip==1}">是</c:if>
												<c:if test="${term.allowSkip==0}">否</c:if>
											</td>
											<td>${term.weight}</td>
											<td>${term.createTime}</td>
											<td>
												<c:if test="${term.status==1}">有效</c:if>
												<c:if test="${term.status==0}">无效</c:if>
											</td>
											<td>

												<div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
													<a class="green" href="javascript:void(0)" onclick="showUpdate(${term.id})">
														<i class="icon-pencil bigger-130"></i>
														修改
													</a>
													<a class="yellow" href="javascript:void(0)"  onclick="updateStatus(${term.id},${term.status})">
														<i class="icon-info-sign bigger-130"></i>
														<c:if test="${term.status==0}">设为有效</c:if>
														<c:if test="${term.status==1}">设为无效</c:if>
													</a>
													<a class="red" href="javascript:void(0)" onclick="deleteRiskTerm(${term.id})">
														<i class="icon-trash bigger-130"></i>
														删除
													</a>
												</div>


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
						新增单词
					</h4>
				</div>
				<div class="modal-body">

					<div style="padding: 10px 100px 10px;">
						<form role="form" class="form-group" id="addForm">
							<div class="form-group">
								<label for="word">单词内容</label>
								<input type="text" class="form-control" id="word" name="word"
									   placeholder="请输入名称">
							</div>
							<div class="control-group ">
								<label class="control-label">应用范围</label>

								<c:forEach items="${riskwordTypes}" var="et">
									<div class="checkbox">
										<label>
											<input class="ace" type="checkbox" id="wordType" name="wordType" value="${et.code}">
											<span class="lbl"> ${et.name}</span>
										</label>
									</div>
								</c:forEach>

							</div>
							<div class="form-group">
								<label for="wholeWord">是否整词匹配</label>
								<select class="form-control" id="wholeWord" name="wholeWord">
									<option value="0" selected>否</option>
									<option value="1" >是</option>
								</select>
							</div>
							<div class="form-group">
								<label for="allowSkip">是否允许跳字匹配</label>
								<select class="form-control" id="allowSkip" name="allowSkip">
									<option value="1" selected>是</option>
									<option value="0">否</option>
								</select>
							</div>
							<div class="form-group">
								<label for="weight">权重[0-10],以5为中间值</label>
								<select class="form-control" id="weight" name="weight">
									<option value="0">0</option>
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>
									<option value="5" selected>5</option>
									<option value="6">6</option>
									<option value="7">7</option>
									<option value="8">8</option>
									<option value="9">9</option>
									<option value="10">10</option>
								</select>
							</div>
						</form>
					</div>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default"
							data-dismiss="modal">关闭
					</button>
					<button type="button" class="btn btn-primary" onclick="add()">
						提交
					</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->


	<!-- 修改modal -->
	<div class="modal fade" id="updateRiskTermModal" tabindex="-1" role="dialog"
		 aria-labelledby="updateRiskTermModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">×
					</button>
					<h4 class="modal-title" id="updateRiskTermModalLabel">
						修改单词
					</h4>
				</div>
				<div class="modal-body">

					<div style="padding: 10px 10px 10px;">
						<form role="form" id="updateRiskTermForm">
							<input type="hidden" id="u_riskTermId"  name="id"/>
							<div class="form-group">
								<label for="word">单词内容</label>
								<input type="text" class="form-control" id="u_word" name="word"
									   placeholder="请输入名称">
							</div>
							<div class="control-group ">
								<label class="control-label">应用范围</label>

								<c:forEach items="${riskwordTypes}" var="et">
									<div class="checkbox">
										<label>
											<input class="ace" type="checkbox" id="u_wordType" name="wordType" value="${et.code}">
											<span class="lbl"> ${et.name}</span>
										</label>
									</div>
								</c:forEach>

							</div>
							<div class="form-group">
								<label for="wholeWord">是否整词匹配</label>
								<select class="form-control" id="u_wholeWord" name="wholeWord">
									<option value="0" selected>否</option>
									<option value="1" >是</option>
								</select>
							</div>
							<div class="form-group">
								<label for="allowSkip">是否允许跳字匹配</label>
								<select class="form-control" id="u_allowSkip" name="allowSkip">
									<option value="1" selected>是</option>
									<option value="0">否</option>
								</select>
							</div>
							<div class="form-group">
								<label for="weight">权重[0-10],以5为中间值</label>
								<select class="form-control" id="u_weight" name="weight">
									<option value="0">0</option>
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>
									<option value="5" selected>5</option>
									<option value="6">6</option>
									<option value="7">7</option>
									<option value="8">8</option>
									<option value="9">9</option>
									<option value="10">10</option>
								</select>
							</div>
						</form>
					</div>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default"
							data-dismiss="modal">关闭
					</button>
					<button type="button" class="btn btn-primary" onclick="updateRiskTerm()">
						修改
					</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-d
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

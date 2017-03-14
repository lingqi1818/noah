<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/include/taglib.jsp" %>
<%
    String path = request.getContextPath();

    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!DOCTYPE html>
<html lang="en">
<head>

    <base href="${basePath}">
    <meta charset="utf-8"/>
    <title>亿方云风控管理平台</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link href="assets/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="assets/css/font-awesome.min.css" />

    <!--[if IE 7]>
    <link rel="stylesheet" href="assets/css/font-awesome-ie7.min.css" />
    <![endif]-->

    <!-- page specific plugin styles -->

    <!-- fonts -->

    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400,300" />

    <!-- ace styles -->

    <link rel="stylesheet" href="assets/css/ace.min.css" />
    <link rel="stylesheet" href="assets/css/ace-rtl.min.css" />
    <link rel="stylesheet" href="assets/css/ace-skins.min.css" />

    <!--[if lte IE 8]>
    <link rel="stylesheet" href="assets/css/ace-ie.min.css" />
    <![endif]-->

    <!-- inline styles related to this page -->

    <!-- ace settings handler -->

    <script src="assets/js/ace-extra.min.js"></script>

    <style type="text/css">

        table{
            min-width: 100%;
        }
        td{
            min-width: 100px;
            word-break: keep-all;
        }
        .table-container{
            overflow:auto;
            display: block;
        }
    </style>

    <script type="text/javascript">

        function showAdd() {

            $("#actionName").empty();
            $("#actionName").append("<option value=''>请选择</option>");
            $.ajax({
                type: "POST",
                url: "actionScript/loadAll",
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                success: function (data) {

                    if (data.success) {
                        $.each(data.data, function(idx,obj) {
                                    $("#actionName").append("<option value='"+obj.id+"'>"+obj.name+"</option>");
                                }
                        )
                        $('#myModal').modal('show');
                    } else {
                        alert(data.apiMessage);
                    }
                }
            });

        }


        function addRule() {

            var policyId = $("#policyId").val();
            var ruleName = $("#ruleName").val();
            var code = $("#ruleCode").val();
            var riskWeight = $("#riskWeight").val();
            var expression = $("#expression").val();
            var actionName = $("#actionName").val();
            var decisionConfig = $("#decisionConfig").val();

            var datas = {
                'name':ruleName,
                'code':code,
                'policyId':policyId,
                'riskWeight':riskWeight,
                'expression':expression,
                'actionName':actionName,
                'decisionConfig':decisionConfig
            };

            $.ajax({
                type: "POST",
                url: "rule/add",
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                data : JSON.stringify(datas),
                success: function (data) {

                    if (data.success) {
                        alert("成功");
                        $('#myModal').modal('hide');

                        location.href = 'policy/detail?policyId=' +${policyId};
                    } else {
                        alert(data.apiMessage);
                    }


                }
            });

        }


        function showUpdateRule(ruleId){
            $("#u_actionName").empty();
            $("#u_actionName").append("<option value=''>请选择</option>");

            $.ajax({
                type: "POST",
                url: "actionScript/loadAll",
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                success: function (data) {

                    if (data.success) {
                        $.each(data.data, function(idx,obj) {
                                    $("#u_actionName").append("<option value='"+obj.id+"'>"+obj.name+"</option>");
                                }
                        )
                    } else {
                        alert(data.apiMessage);
                        return;
                    }
                }
            });


            $.ajax({
                type: "POST",
                url: "rule/showUpdate",
                data: "ruleId="+ruleId,
                success: function(data){
                    if(data.success){
                        var rule = data.data
                        $("#u_ruleId").val(rule.id);
                        $("#u_policyId").val(rule.policyId);
                        $("#u_ruleName").val(rule.name);
                        $("#u_ruleCode").val(rule.code);
                        $("#u_riskWeight").val(rule.riskWeight);
                        $("#u_expression").val(rule.expression);
                        $("#u_actionName").val(rule.actionName);
                        $("#u_decisionConfig").val(rule.decisionConfig);

                        $('#updateRuleModal').modal('show');
                        return;
                    }else{
                        alert(data.apiMessage);
                        return;
                    }
                }
            });

        }

        function updateRule(){

            var ruleId = $("#u_ruleId").val();
            var policyId = $("#u_policyId").val();
            var ruleName = $("#u_ruleName").val();
            var ruleCode = $("#u_ruleCode").val();
            var riskWeight = $("#u_riskWeight").val();
            var expression = $("#u_expression").val();
            var actionName = $("#u_actionName").val();
            var decisionConfig = $("#u_decisionConfig").val();


            var datas = {
                'id':ruleId,
                'name':ruleName,
                'code':ruleCode,
                'policyId':policyId,
                'riskWeight':riskWeight,
                'expression':expression,
                'actionName':actionName,
                'decisionConfig':decisionConfig
            };

            $.ajax({
                type: "POST",
                url: "rule/update",
                type : 'POST',
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                data : JSON.stringify(datas),
                success: function(data){
                    if(data.success){
                        alert( "修改成功" );
                    }else{
                        alert("修改失败！"+data.apiMessage);
                    }

                    $('#updateRuleModal').modal('hide');

                    location.href='policy/detail?policyId='+${policyId};

                }
            });

        }


        function enableRule(ruleId){

            $.ajax({
                type: "POST",
                url: "rule/enable",
                data: "ruleId="+ruleId,
                success: function(data){
                    if(data.success){
                        alert( "成功" );
                    }else{
                        alert("失败！"+data.apiMessage);
                    }

                    location.href='policy/detail?policyId='+${policyId};

                }
            });
        }

        function deleteRule(ruleId){
            $.ajax({
                type: "POST",
                url: "rule/delete",
                data: "ruleId="+ruleId,
                success: function(data){
                    if(data.success){
                        alert( "删除成功" );
                    }else{
                        alert("删除失败！"+data.apiMessage);
                    }

                    location.href='policy/detail?policyId='+${policyId};

                }
            });
        }

    </script>

</head>

<body>
<%@ include file="/WEB-INF/jsp/head.jsp" %>

<div class="main-container" id="main-container">
    <script type="text/javascript">
        try{ace.settings.check('main-container' , 'fixed')}catch(e){}
    </script>

    <div class="main-container-inner">
        <a class="menu-toggler" id="menu-toggler" href="#">
            <span class="menu-text"></span>
        </a>

        <%@ include file="/WEB-INF/jsp/side.jsp" %>

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
                        <a href="#">规则引擎服务</a>
                    </li>
                    <li class="active">规则管理</li>
                </ul><!-- .breadcrumb -->
            </div>

            <div class="page-content">

                <div class="row">
                    <div class="col-xs-12">
                        <button class="btn btn-sm btn-primary" onclick="showAdd()">新增规则</button>
                    </div>
                    <div class="col-xs-12">
                        <div class="row">
                            <div class="col-xs-12" style="overflow-x:auto">
                                <div class="table-responsive">
                                    <table class="table table-bordered table-hover table-condensed">
                                        <thead>
                                        <tr>
                                            <th>规则ID</th>
                                            <th>规则编号</th>
                                            <th>规则名称</th>
                                            <th>风险权重</th>
                                            <th>表达式</th>
                                            <th>执行动作</th>
                                            <th>决策配置</th>
                                            <th>状态</th>
                                            <th>操作</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${ruleList}" var="r">
                                            <tr>
                                                <td>${r.id}</td>
                                                <td>${r.code}</td>
                                                <td>${r.name}</td>
                                                <td>${r.riskWeight}</td>
                                                <td>${r.expression}</td>
                                                <td>${r.actionName}</td>
                                                <td>${r.decisionConfig}</td>
                                                <c:if test="${r.status==1}">
                                                    <td style="background-color:#00FF00">已启用</td>
                                                </c:if>
                                                <c:if test="${r.status==0}">
                                                    <td style="background-color:#808080">已停用</td>
                                                </c:if>
                                                <td>
                                                    <div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
                                                        <a class="green" href="javascript:void(0)" onclick="showUpdateRule(${r.id})">
                                                            <i class="icon-pencil bigger-130"></i>
                                                            修改
                                                        </a>
                                                        <a class="yellow" href="javascript:void(0)" onclick="enableRule(${r.id})">
                                                            <i class="icon-info-sign bigger-130"></i>
                                                            启用/停用
                                                        </a>
                                                        <a class="red" href="javascript:void(0)" onclick="deleteRule(${r.id})">
                                                            <i class="icon-trash bigger-130"></i>
                                                            删除
                                                        </a>
                                                    </div>
                                                </td>
                                            </tr>

                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div><!-- /.table-responsive -->
                            </div><!-- /span -->
                        </div><!-- /row -->
                    </div><!-- /.col -->
                </div><!-- /.row -->
            </div><!-- /.page-content -->
        </div><!-- /.main-content -->

    </div><!-- /.main-container-inner -->

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
                        新增规则
                    </h4>
                </div>
                <div class="modal-body">

                    <div style="padding: 10px 10px 10px;">
                        <form role="form" id="addForm">
                            <input type="hidden" id="policyId" name="policyId" value="${policyId}">

                            <div class="form-group">
                                <label for="ruleCode">规则编号</label>
                                <input type="text" class="form-control" id="ruleCode" name="ruleCode"
                                >
                            </div>

                            <div class="form-group">
                                <label for="ruleName">规则名称</label>
                                <input type="text" class="form-control" id="ruleName" name="ruleName"
                                >
                            </div>
                            <div class="form-group">
                                <label for="riskWeight">风险权重</label>
                                <input type="text" class="form-control" id="riskWeight" name="riskWeight"
                                >
                            </div>
                            <div class="form-group">
                                <label for="expression">表达式</label>
                                <textarea class="form-control" rows="3" id="expression" name="expression"></textarea>
                            </div>

                            <div class="form-group">
                                <label for="actionName">执行动作</label>
                                <select id="actionName" name = "actionName" class="form-control">
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="decisionConfig">决策配置</label>
                                <textarea class="form-control" rows="3" id="decisionConfig" name="decisionConfig"></textarea>
                            </div>

                        </form>
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default"
                            data-dismiss="modal">关闭
                    </button>
                    <button type="button" class="btn btn-primary" onclick="addRule()">
                        保存
                    </button>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>
    <!-- /.modal -->

    <!-- /.modal-dialog -->


    <!-- 模态框（Modal） -->
    <div class="modal fade" id="updateRuleModal" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-hidden="true">×
                    </button>
                    <h4 class="modal-title" id="myModalLabel2">
                        修改规则
                    </h4>
                </div>
                <div class="modal-body">

                    <div style="padding: 10px 10px 10px;">
                        <form role="form" id="updateRuleForm">
                            <input type="hidden" id="u_ruleId" name="id" >
                            <input type="hidden" id="u_policyId" name="policyId" value="${policyId}">

                            <div class="form-group">
                                <label for="u_ruleCode">规则编号</label>
                                <input type="text" class="form-control" id="u_ruleCode" name="code"
                                >
                            </div>

                            <div class="form-group">
                                <label for="u_ruleName">规则名称</label>
                                <input type="text" class="form-control" id="u_ruleName" name="name"
                                >
                            </div>
                            <div class="form-group">
                                <label for="riskWeight">风险权重</label>
                                <input type="text" class="form-control" id="u_riskWeight" name="riskWeight"
                                >
                            </div>
                            <div class="form-group">
                                <label for="expression">表达式</label>
                                <textarea class="form-control" rows="3" id="u_expression" name="expression"></textarea>
                            </div>

                            <div class="form-group">
                                <label for="actionName">执行动作</label>
                                <select id="u_actionName" name = "actionName" class="form-control">
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="decisionConfig">决策配置</label>
                                <input type="text" class="form-control" id="u_decisionConfig" name="decisionConfig"
                                >
                            </div>
                        </form>
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default"
                            data-dismiss="modal">关闭
                    </button>
                    <button type="button" class="btn btn-primary" onclick="updateRule()">
                        修改
                    </button>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>
    <!-- /.modal -->
    <!-- /.modal -->
</div><!-- /.main-container -->

<!-- basic scripts -->

<!--[if !IE]> -->


<!-- <![endif]-->

<!--[if IE]>
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

<script type="text/javascript">
    if("ontouchend" in document) document.write("<script src='assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
</script>
<script src="assets/js/bootstrap.min.js"></script>
<script src="assets/js/typeahead-bs2.min.js"></script>

<!-- page specific plugin scripts -->

<script src="assets/js/jquery.dataTables.min.js"></script>
<script src="assets/js/jquery.dataTables.bootstrap.js"></script>

<!-- ace scripts -->

<script src="assets/js/ace-elements.min.js"></script>
<script src="assets/js/ace.min.js"></script>

<!-- inline scripts related to this page -->

<script type="text/javascript">
    jQuery(function($) {

        $('#myModal').modal({
            keyboard: true,
            show: false
        })


        $('#updateRuleModal').modal({
            keyboard: true,
            show: false
        })
    })
</script>
</body>
</html>

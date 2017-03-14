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



        function addCollectParamConfig(){

            $.ajax({
                type: "POST",
                url: "collectParamConfig/add",
                data: $("#addConfigForm").serialize(),
                success: function(data){
                    if(data.success){
                        alert( "保存成功" );
                    }else{
                        alert("保存失败！"+data.apiMessage);
                    }

                    $('#myModal').modal('hide');

                    location.href='collectParamConfig/query';

                }
            });
        }



        function viewDetail(configId){
            location.href='collectParamConfig/detail?configId='+configId;
        }

        function deleteDetail(collectParamConfig){
            $.ajax({
                type: "POST",
                url: "collectParamConfig/delete",
                data: "id="+collectParamConfig,
                success: function(data){
                    if(data.success){
                        alert( "删除成功" );
                    }else{
                        alert("删除失败！"+data.apiMessage);
                    }

                    location.href='collectParamConfig/query';

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
                        <a href="#">采集参数管理</a>
                    </li>
                    <li class="active">采集参数配置</li>
                </ul><!-- .breadcrumb -->
            </div>

            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">
                        <!-- PAGE CONTENT BEGINS -->
                        <div class="row">

                            <div class="col-xs-12">
                                <form action="collectParamConfig/query" method="post" id="queryForm">
                                    <input type="hidden" name="pageNum" value="${pageObj.curPage}">
                                    <select id="applicationName" class="form-control" name="applicationName" style="width:120px;float:left;" >
                                        <option value="">--接入应用--</option>
                                        <c:forEach items="${applicationTypes}" var="data">
                                            <option value="${data.name}" <c:if test="${data.name eq applicationName}"> selected </c:if>>${data.name}</option>
                                        </c:forEach>
                                    </select>
                                    <button class="btn btn-sm btn-primary" onclick="$('#queryForm').submit()">查询</button>

                                    <button class="btn btn-sm btn-primary" data-toggle="modal" data-target="#myModal">
                                        新增
                                    </button>
                                </form>
                            </div>

                            <div class="col-xs-12">
                                <div class="table-responsive">
                                    <table id="sample-table-1" class="table table-striped table-bordered table-hover">
                                        <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>URI</th>
                                            <th>事件类型</th>
                                            <th>所属应用</th>
                                            <th>开关状态</th>
                                            <th>操作</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${pageObj.datas}" var="data">
                                        <tr>
                                            <td>${data.id}</td>
                                            <td>${data.uri}</td>
                                            <td>${data.eventType}</td>
                                            <td>${data.applicationName}</td>
                                            <td>
                                                <c:if test="${data.switchStatus=='0'}">已关闭</c:if>
                                                <c:if test="${data.switchStatus=='1'}">已开启</c:if>
                                            </td>
                                            <td>
                                                <div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">

                                                    <a class="yellow" href="javascript:void(0)" onclick="viewDetail(${data.id})">
                                                        <i class="icon-info-sign bigger-130"></i>
                                                        详情
                                                    </a>
                                                    <a class="red" href="javascript:void(0)" onclick="deleteDetail(${data.id})">
                                                        <i class="icon-trash bigger-130"></i>
                                                        删除
                                                    </a>
                                                </div>
                                            </td>
                                        </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>

                                </div>
                                <!-- /.table-responsive -->
                            </div>
                            <!-- /span -->
                        </div>
                        <!-- /row -->
                    </div>
                    <!-- /.col -->
                </div>
                <!-- /.row -->
            </div>
        </div><!-- /.main-content -->

        <!-- 模态框（Modal） -->
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog"
             aria-labelledby="policyModals" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"
                                aria-hidden="true">×
                        </button>
                        <h4 class="modal-title" id="policyModals">
                            新增采集
                        </h4>
                    </div>
                    <div class="modal-body">

                        <div style="padding: 10px 10px 10px;">
                            <form role="form" id="addConfigForm">
                                <div class="form-group">
                                    <label for="uri">uri</label>
                                    <input type="text" class="form-control" id="uri" name="uri"
                                           placeholder="请输入名称">
                                </div>


                                <div class="form-group">
                                    <label for="applicationName1">所属应用</label>
                                    <select class="form-control" id="applicationName1" name="applicationName">
                                        <c:forEach items="${applicationTypes}" var="data">
                                            <option value="${data.name}">${data.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="form-group">
                                    <label for="eventType">事件类型</label>
                                    <select class="form-control" id="eventType" name="eventType">
                                        <c:forEach items="${eventTypes}" var="data">
                                            <option value="${data.code}">${data.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="form-group">
                                    <label for="switchStatus">开关状态</label>
                                    <select class="form-control" id="switchStatus" name="switchStatus">
                                        <option value="0">关闭</option>
                                        <option value="1">开启</option>
                                    </select>
                                </div>

                            </form>
                        </div>

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default"
                                data-dismiss="modal">关闭
                        </button>
                        <button type="button" class="btn btn-primary" onclick="addCollectParamConfig()">
                            保存
                        </button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->

        <!-- 修改策略 -->
        <div class="modal fade" id="updatePolicyModal" tabindex="-1" role="dialog"
             aria-labelledby="policyModals" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"
                                aria-hidden="true">×
                        </button>
                        <h4 class="modal-title" id="updatePolicy">
                            修改策略
                        </h4>
                    </div>
                    <div class="modal-body">

                        <div style="padding: 10px 10px 10px;">
                            <form role="form" id="updatePolicyForm">
                                <input type="hidden" id="u_policyId"  name="id"/>
                                <div class="form-group">
                                    <label for="policyName">策略名称</label>
                                    <input type="text" class="form-control" id="u_policyName" name="name"
                                           placeholder="请输入名称">
                                </div>

                                <div class="form-group">
                                    <label for="eventType">事件类型</label>
                                    <select class="form-control" id="u_eventType" name="eventType">
                                        <c:forEach items="${eventTypes}" var="data">
                                            <option value="${data.code}">${data.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="eventId">事件标示</label>
                                    <input type="text" class="form-control" id="u_eventId" name="eventId"/>
                                </div>
                                <div class="form-group">
                                    <label for="appType2">应用类型</label>
                                    <select class="form-control" id="u_appType2" name="deviceType">
                                        <option value="0">安卓</option>
                                        <option value="1">IOS</option>
                                        <option value="2">网页</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="minRisk">风险阈值min</label>
                                    <input type="text" class="form-control input-sm" id="u_minRisk" name="minRisk"
                                           placeholder="请输入数字">
                                </div>
                                <div class="form-group">
                                    <label for="maxRisk">风险阈值max</label>
                                    <input type="text" class="form-control input-sm" id="u_maxRisk" name="maxRisk"
                                           placeholder="请输入数字">
                                </div>

                                <div class="form-group">
                                    <label for="actionName">执行动作</label>
                                    <input type="text" class="form-control input-sm" id="u_actionName" name="actionName"
                                            >
                                </div>
                            </form>
                        </div>

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default"
                                data-dismiss="modal">关闭
                        </button>
                        <button type="button" class="btn btn-primary" onclick="updatePolicy()">
                            修改
                        </button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->

    </div><!-- /.main-container-inner -->

    <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
        <i class="icon-double-angle-up icon-only bigger-110"></i>
    </a>
</div><!-- /.main-container -->

<!-- basic scripts -->

<!--[if !IE]> -->

<script src="assets/js/jquery-2.0.3.min.js"></script>

<!-- <![endif]-->

<!--[if IE]>
<!--<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>-->
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


<script src="assets/js/bootstrap.min.js"></script>
<script src="assets/js/typeahead-bs2.min.js"></script>

<!-- page specific plugin scripts -->

<script src="assets/js/jquery.dataTables.min.js"></script>
<script src="assets/js/jquery.dataTables.bootstrap.js"></script>

<!-- ace scripts -->

<script src="assets/js/ace-elements.min.js"></script>
<script src="assets/js/ace.min.js"></script>
<div style="display:none"></div>


<script type="text/javascript">

    jQuery(function($) {

        $('#myModal').modal({
            keyboard: true,
            show:false
        })

        $('#updatePolicyModal').modal({
            keyboard: true,
            show:false
        })
    })
</script>
</body>
</html>

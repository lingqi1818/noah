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
    <script type="text/javascript" src="assets/js/jquery-1.10.2.min.js"></script>
    <link rel="stylesheet" href="assets/css/bootstrap.min.css" />
    <link rel="stylesheet" href="assets/css/page.css" />
    <link rel="stylesheet" href="assets/css/ace.min.css" />
    <link rel="stylesheet" href="assets/css/bootstrap-datetimepicker.css" />
    <script src="assets/js/My97DatePicker/WdatePicker.js" type="text/javascript"></script>


    <script type="text/javascript">
        function goPage(page){
            $("input[name='page']").val(page);
            $("#queryForm").submit();
        }


        $(document).ready(function() {
            changePage.hrefFun=goPage;
        });



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
                        <a href="#">规则引擎服务</a>
                    </li>
                    <li class="active">调用管理</li>
                </ul><!-- .breadcrumb -->



            </div>

            <div class="page-content">
                <div class="row">
                    <div class="well">
                        <form action="event/query" method="post" id="queryForm" class="form-horizontal" role="form">
                            <input type="hidden" name="page" value="${pageObj.curPage}">
                            <div class="form-group form-group-sm no-padding-left align-left">
                                <label for="accountLogin" class="col-sm-2 control-label no-padding-left">账户</label>
                                <div class="col-sm-4"><input  class="form-control" type="text" id="accountLogin" name="accountLogin" value="${eventQuery.accountLogin}"/></div>
                                <label for="deviceId" class="col-sm-2 control-label">设备ID</label>
                                <div class="col-sm-4"><input  class="form-control" type="text" id="deviceId" name="deviceId" value="${eventQuery.deviceId}"/></div>
                                <label for="beaconId" class="col-sm-2 control-label">beaconId</label>
                                <div class="col-sm-4"><input  class="form-control" type="text" id="beaconId" name="beaconId" value="${eventQuery.beaconId}"/></div>
                                <label for="ip" class="col-sm-2 control-label">IP</label>
                                <div class="col-sm-4"><input  class="form-control" type="text" id="ip" name="ip" value="${eventQuery.ip}"/></div>
                                <label for="eventType" class="col-sm-2 control-label">事件类型</label>
                                <div class="col-sm-4">
                                    <select class="form-control" id="eventType" name="eventType">
                                        <option value=""  ></option>
                                        <c:forEach items="${eventTypes}" var="et">
                                            <option value="${et.code}" <c:if test="${eventQuery.eventType==et.code}"> selected="true"</c:if> >${et.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <label for="decision" class="col-sm-2 control-label">审核结果</label>
                                <div class="col-sm-4">
                                    <select class="form-control" id="decision" name="decision">
                                        <option value=""></option>
                                        <option value="1" <c:if test="${eventQuery.decision=='1'}"> selected="true"</c:if>>通过</option>
                                        <option value="2" <c:if test="${eventQuery.decision=='2'}"> selected="true"</c:if>>人工审核</option>
                                        <option value="3" <c:if test="${eventQuery.decision=='3'}"> selected="true"</c:if>>拒绝</option>
                                    </select>
                                </div>

                                <label for="startTime" class="col-sm-2 control-label">开始时间</label>
                                <div class="col-sm-4">
                                    <input id="startTime" name="startTime" class="form-control" type="text" value="${eventQuery.startTime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"  readonly >
                                </div>
                                <label for="endTime" class="col-sm-2 control-label">结束时间</label>
                                <div class="col-sm-4">
                                    <input id="endTime" name="endTime" class="form-control" type="text" value="${eventQuery.endTime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" readonly >

                                </div>

                            </div>
                            <button class="btn btn-sm btn-primary" onclick="$('#queryForm').submit()">查询</button>


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
                                            <th>事件发生时间</th>
                                            <th>事件类型</th>
                                            <th>账户</th>
                                            <th>设备ID</th>
                                            <th>来源IP</th>
                                            <th>评估结果</th>
                                            <th>评分</th>
                                            <th>平台</th>
                                            <th>备注</th>
                                            <th>操作</th>
                                        </tr>
                                        </thead>

                                        <tbody>
                                        <c:forEach items="${pageObj.datas}" var="d">

                                            <tr>
                                                <td>${d.createTime}</td>
                                                <td>${d.eventType}</td>
                                                <td>${d.accountLogin}</td>
                                                <td>${d.deviceId}</td>
                                                <td>${d.ip}</td>
                                                <c:if test="${d.decision==1}">
                                                    <td style="background-color:#00FF00">ACCEPT</td>
                                                </c:if>
                                                <c:if test="${d.decision==2}">
                                                    <td style="background-color:#FFFF00">REVIEW</td>
                                                </c:if>
                                                <c:if test="${d.decision==3}">
                                                    <td style="background-color:#FF0000">REJECT</td>
                                                </c:if>

                                                <td>${d.score}</td>
                                                <td>
                                                    <c:if test="${d.deviceType==0}">Android</c:if>
                                                    <c:if test="${d.deviceType==1}">IOS</c:if>
                                                    <c:if test="${d.deviceType==2}">WEB</c:if>
                                                </td>
                                                <td>${d.remark}</td>
                                                <td>
                                                    <div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
                                                        <a class="yellow" href="event/detail?id=${d.id}" target="_blank">
                                                            <i class="icon-info-sign bigger-130"></i>
                                                            事件详情
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



</div><!-- /.main-container -->

<!-- basic scripts -->

<!--[if !IE]> -->

<script src="assets/js/jquery-2.0.3.min.js"></script>

<!-- <![endif]-->

<!--[if IE]>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
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
<script type="text/javascript" src="assets/js/date-time/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript">
//    $("#startTime").datetimepicker({format: 'yyyy-mm-dd hh:ii:ss'});
//    $("#endTime").datetimepicker({format: 'yyyy-mm-dd hh:ii:ss'});
</script>
<div style="display:none"></div>
</body>
</html>

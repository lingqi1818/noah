<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <meta charset="utf-8" />
    <title>亿方云风控管理平台</title>
    <!-- basic styles -->
    <link href="assets/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="assets/css/font-awesome.min.css" />

    <!--[if IE 7]>
    <link rel="stylesheet" href="assets/css/font-awesome-ie7.min.css" />
    <![endif]-->

    <!-- page specific plugin styles -->

    <!-- fonts -->

    <link rel="stylesheet" href="assets/css/googleapis.css" />

    <!-- ace styles -->

    <link rel="stylesheet" href="assets/css/ace.min.css" />
    <link rel="stylesheet" href="assets/css/ace-rtl.min.css" />
    <link rel="stylesheet" href="assets/css/ace-skins.min.css" />

    <!--[if lte IE 8]>
    <link rel="stylesheet" href="assets/css/ace-ie.min.css" />
    <link rel="stylesheet" href="assets/css/bootstrap-datetimepicker.css" />

    <![endif]-->

    <!-- inline styles related to this page -->

    <!-- ace settings handler -->

    <script src="assets/js/ace-extra.min.js"></script>

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

    <!--[if lt IE 9]>
    <script src="assets/js/html5shiv.js"></script>
    <script src="assets/js/respond.min.js"></script>


    <![endif]-->

    <script type="text/javascript">

        function GetPercent(num, total) {
            num = parseFloat(num);
            total = parseFloat(total);
            if (isNaN(num) || isNaN(total)) {
                return "-";
            }
            return total <= 0 ? "0%" : (Math.round(num / total * 10000) / 100.00 + "%");
        }
    </script>


</head>


<body>
<%@ include file="head.jsp"%>

<div class="main-container" id="main-container">
    <script type="text/javascript">
        try{ace.settings.check('main-container' , 'fixed')}catch(e){}
    </script>

    <div class="main-container-inner">
        <a class="menu-toggler" id="menu-toggler" href="#">
            <span class="menu-text"></span>
        </a>

        <%@ include file="side.jsp"%>

        <div class="main-content">
            <div class="breadcrumbs" id="breadcrumbs">
                <script type="text/javascript">
                    try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
                </script>

                <ul class="breadcrumb">
                    <li>
                        <i class="icon-home home-icon"></i>
                        <a href="#">首页</a>
                    </li>
                    <li class="active">风险大盘</li>
                </ul><!-- .breadcrumb -->
            </div>

            <div class="col-xs-12">
                <select id="deviceType" name = "deviceType">
                    <option value="" selected>全部应用</option>
                    <option value="0">安卓</option>
                    <option value="1">IOS</option>
                    <option value="2">网页</option>
                </select>
            </div>
            <div class="hr"></div>

            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">
                        <!-- PAGE CONTENT BEGINS -->
                        <div class="row well">
                            <div class="space-6"></div>
                            <div class="col-sm-12">
                                <div class="col-sm-3" style="background-color:#FF6C5C;height: 100px;line-height:30px">
                                    <span style="display:block">今日拒绝事件</span>
                                    <span id="todayReject" style="font-size: 32px"></span>
                                    <span id="todayRejectPercent" style="margin-left:10px;"></span>
                                </div>
                                <div class="col-sm-3" style="background-color: #F8D436;height: 100px;line-height:30px">
                                    <span style="display:block">今日人工审核事件</span>
                                    <span id="todayReview" style="font-size: 32px"></span>
                                    <span id="todayReviewPercent" style="margin-left:10px;"></span>
                                </div>
                                <div class="col-sm-3" style="background-color: #A7DB65;height: 100px;line-height:30px">
                                    <span style="display:block">今日通过事件</span>
                                    <span id="todayAccept" style="font-size: 32px"></span>
                                    <span id="todayAcceptPercent" style="margin-left:10px;"></span>
                                </div>
                                <div class="col-sm-3" style="background-color: #7a8c99;height: 100px;line-height:30px">
                                    <span style="display:block">今日事件总量</span>
                                    <span id="todayAcceptTotal" style="font-size: 36px"></span>
                                </div>
                            </div>
                        </div><!-- /row -->

                        <div class="row well">
                            <div class="col-sm-12">
                                <form class="form-inline" role="form" onsubmit="return search()">
                                    <div class = "form-group col-sm-2 padding-0" >
                                        <label for = "startTime" class="sr-only" >时间</label>
                                        <input style="width: 145px" id="startTime" name="startTime" class="form-control"   type="text" value="${startTime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"  readonly >
                                    </div>
                                    <div class = "form-group col-sm-3" >
                                        <label for = "endTime" >至</label>
                                        <input style="width: 145px" id="endTime" name="endTime" class="form-control"  type="text" value="${endTime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"  readonly >
                                    </div>

                                    <div class = "form-group  col-sm-3">
                                        <label for = "eventType" class="sr-only" >事件类型</label>
                                        <select id = "eventType" name="eventType" style="width: 145px" class = "form-control" >
                                            <option value=""  >事件类型</option>
                                            <c:forEach items="${eventTypes}" var="et">
                                                <option value="${et.code}" <c:if test="${eventQuery.eventType==et.code}"> selected="true"</c:if> >${et.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <div class = "form-group col-sm-offset-3 col-sm-1">
                                        <label for = "btn1" class="sr-only"></label>
                                        <button class="btn btn-sm" id="btn1" name="btn1" >搜索</button>
                                    </div>

                                </form>
                            </div>
                        </div>


                        <div class="row well">
                            <div class="col-sm-12">
                                <div id="bar1" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
                            </div>
                        </div>

                        <div class="row well">
                            <div class="col-sm-6">
                                <div class="widget-box">
                                    <div class="widget-header widget-header-flat widget-header-small">
                                        <h5>
                                            <i class="icon-signal"></i>
                                            风险事件下风险类型分布（2016.12.08 - 2016.12.14）
                                        </h5>

                                        <div class="widget-toolbar no-border">
                                            <button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown">
                                                本周
                                                <i class="icon-angle-down icon-on-right bigger-110"></i>
                                            </button>

                                            <ul class="dropdown-menu pull-right dropdown-125 dropdown-lighter dropdown-caret">
                                                <li class="active">
                                                    <a href="#" class="blue">
                                                        <i class="icon-caret-right bigger-110">&nbsp;</i>
                                                        本周
                                                    </a>
                                                </li>

                                                <li>
                                                    <a href="#">
                                                        <i class="icon-caret-right bigger-110 invisible">&nbsp;</i>
                                                        上周
                                                    </a>
                                                </li>

                                                <li>
                                                    <a href="#">
                                                        <i class="icon-caret-right bigger-110 invisible">&nbsp;</i>
                                                        本月
                                                    </a>
                                                </li>

                                                <li>
                                                    <a href="#">
                                                        <i class="icon-caret-right bigger-110 invisible">&nbsp;</i>
                                                        上月
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>

                                    <div class="widget-body">
                                        <div class="widget-main">
                                            <div id="piechart-placeholder" style="width: 90%; min-height: 150px; padding: 0px; position: relative;"><canvas class="flot-base" style="direction: ltr; position: absolute; left: 0px; top: 0px; width: 570px; height: 150px;" width="570" height="150"></canvas><canvas class="flot-overlay" style="direction: ltr; position: absolute; left: 0px; top: 0px; width: 570px; height: 150px;" width="570" height="150"></canvas><div class="legend"><div style="position: absolute; width: 95px; height: 110px; top: 15px; right: -30px; background-color: rgb(255, 255, 255); opacity: 0.85;"> </div><table style="position:absolute;top:15px;right:-30px;;font-size:smaller;color:#545454"><tbody><tr><td class="legendColorBox"><div style="border:1px solid null;padding:1px"><div style="width:4px;height:0;border:5px solid #68BC31;overflow:hidden"></div></div></td><td class="legendLabel">social networks</td></tr><tr><td class="legendColorBox"><div style="border:1px solid null;padding:1px"><div style="width:4px;height:0;border:5px solid #2091CF;overflow:hidden"></div></div></td><td class="legendLabel">search engines</td></tr><tr><td class="legendColorBox"><div style="border:1px solid null;padding:1px"><div style="width:4px;height:0;border:5px solid #AF4E96;overflow:hidden"></div></div></td><td class="legendLabel">ad campaigns</td></tr><tr><td class="legendColorBox"><div style="border:1px solid null;padding:1px"><div style="width:4px;height:0;border:5px solid #DA5430;overflow:hidden"></div></div></td><td class="legendLabel">direct traffic</td></tr><tr><td class="legendColorBox"><div style="border:1px solid null;padding:1px"><div style="width:4px;height:0;border:5px solid #FEE074;overflow:hidden"></div></div></td><td class="legendLabel">other</td></tr></tbody></table></div></div>

                                            <div class="hr hr8 hr-double"></div>

                                            <div class="clearfix">
                                                <div class="grid3">
															<span class="grey">
																<i class="icon-facebook-sign icon-2x blue"></i>
																&nbsp; likes
															</span>
                                                    <h4 class="bigger pull-right">1,255</h4>
                                                </div>

                                                <div class="grid3">
															<span class="grey">
																<i class="icon-twitter-sign icon-2x purple"></i>
																&nbsp; tweets
															</span>
                                                    <h4 class="bigger pull-right">941</h4>
                                                </div>

                                                <div class="grid3">
															<span class="grey">
																<i class="icon-pinterest-sign icon-2x red"></i>
																&nbsp; pins
															</span>
                                                    <h4 class="bigger pull-right">1,050</h4>
                                                </div>
                                            </div>
                                        </div><!-- /widget-main -->
                                    </div><!-- /widget-body -->
                                </div><!-- /widget-box -->
                            </div>

                            <div class="col-sm-6">
                                <div class="widget-box transparent" id="recent-box">
                                    <div class="widget-header">
                                        <h4 class="lighter smaller">
                                            <i class="icon-spinner icon-spin orange bigger-125"></i>
                                            字段命中排行
                                        </h4>

                                        <div class="widget-toolbar no-border">
                                            <ul class="nav nav-tabs" id="recent-tab">
                                                <li class="active">
                                                    <a data-toggle="tab" href="#deviceId-tab">设备ID</a>
                                                </li>

                                                <li>
                                                    <a data-toggle="tab" href="#account-tab">账号</a>
                                                </li>

                                                <li>
                                                    <a data-toggle="tab" href="#ip-tab">IP</a>
                                                </li>
                                                <li>
                                                    <a data-toggle="tab" href="#BeaconId-tab">BeaconId</a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>

                                    <div class="widget-body">
                                        <div class="widget-main padding-4">
                                            <div class="tab-content padding-8 overflow-visible">
                                                <div id="deviceId-tab" class="tab-pane active">

                                                    <ul id="tasks1" class="list-group">
                                                        <li class="list-group-item">
                                                            <span> 1</span>
                                                            <span style="padding-left: 20px"> 问fasfsdafsdadfsdafdasfdsafdsfdsfdsafsdfsadfdsfds答</span>
                                                            <span style="float: right">42</span>
                                                        </li>
                                                        <li class="list-group-item">
                                                            <span> 1</span>
                                                            <span style="padding-left: 20px"> 问fasfsdafsdadfsdafdasfdsafdsfdsfdsafsdfsadfdsfds答</span>
                                                            <span style="float: right">42</span>
                                                        </li>
                                                        <li class="list-group-item">
                                                            <span> 1</span>
                                                            <span style="padding-left: 20px"> 问fasfsdafsdadfsdafdasfdsafdsfdsfdsafsdfsadfdsfds答</span>
                                                            <span style="float: right">42</span>
                                                        </li>

                                                    </ul>
                                                </div>

                                                <div id="account-tab" class="tab-pane">
                                                    <ul id="tasks2" class="list-group">
                                                        <li class="list-group-item">
                                                            <span> 1</span>
                                                            <span style="padding-left: 20px"> 问fasfsdafsdadfsdafdasfdsafdsfdsfdsafsdfsadfdsfds答</span>
                                                            <span style="float: right">42</span>
                                                        </li>
                                                        <li class="list-group-item">
                                                            <span> 1</span>
                                                            <span style="padding-left: 20px"> 问fasfsdafsdadfsdafdasfdsafdsfdsfdsafsdfsadfdsfds答</span>
                                                            <span style="float: right">42</span>
                                                        </li>
                                                        <li class="list-group-item">
                                                            <span> 1</span>
                                                            <span style="padding-left: 20px"> 问fasfsdafsdadfsdafdasfdsafdsfdsfdsafsdfsadfdsfds答</span>
                                                            <span style="float: right">42</span>
                                                        </li>

                                                    </ul>
                                                </div><!-- member-tab -->

                                                <div id="ip-tab" class="tab-pane">
                                                    <ul id="tasks3" class="list-group">
                                                        <li class="list-group-item">
                                                            <span> 1</span>
                                                            <span style="padding-left: 20px"> 问fasfsdafsdadfsdafdasfdsafdsfdsfdsafsdfsadfdsfds答</span>
                                                            <span style="float: right">42</span>
                                                        </li>
                                                        <li class="list-group-item">
                                                            <span> 1</span>
                                                            <span style="padding-left: 20px"> 问fasfsdafsdadfsdafdasfdsafdsfdsfdsafsdfsadfdsfds答</span>
                                                            <span style="float: right">42</span>
                                                        </li>
                                                        <li class="list-group-item">
                                                            <span> 1</span>
                                                            <span style="padding-left: 20px"> 问fasfsdafsdadfsdafdasfdsafdsfdsfdsafsdfsadfdsfds答</span>
                                                            <span style="float: right">42</span>
                                                        </li>

                                                    </ul>
                                                </div>

                                                <div id="BeaconId-tab" class="tab-pane">
                                                    <ul id="tasks4" class="list-group">
                                                        <li class="list-group-item">
                                                            <span> 1</span>
                                                            <span style="padding-left: 20px"> 问fasfsdafsdadfsdafdasfdsafdsfdsfdsafsdfsadfdsfds答</span>
                                                            <span style="float: right">42</span>
                                                        </li>
                                                        <li class="list-group-item">
                                                            <span> 1</span>
                                                            <span style="padding-left: 20px"> 问fasfsdafsdadfsdafdasfdsafdsfdsfdsafsdfsadfdsfds答</span>
                                                            <span style="float: right">42</span>
                                                        </li>
                                                        <li class="list-group-item">
                                                            <span> 1</span>
                                                            <span style="padding-left: 20px"> 问fasfsdafsdadfsdafdasfdsafdsfdsfdsafsdfsadfdsfds答</span>
                                                            <span style="float: right">42</span>
                                                        </li>

                                                    </ul>
                                                </div>
                                            </div>
                                        </div><!-- /widget-main -->
                                    </div><!-- /widget-body -->
                                </div><!-- /widget-box -->
                            </div><!-- /span -->



                        </div><!-- /row -->

                        <!-- PAGE CONTENT ENDS -->
                    </div><!-- /.col -->
                </div><!-- /.row -->
            </div><!-- /.page-content -->
        </div><!-- /.main-content -->


    </div><!-- /.main-container-inner -->
</div><!-- /.main-container -->


<script type="text/javascript">
    window.jQuery || document.write("<script src='assets/js/jquery-2.0.3.min.js'>"+"<"+"script>");
</script>

<!-- <![endif]-->

<!--[if IE]>
<script type="text/javascript">
    window.jQuery || document.write("<script src='assets/js/jquery-1.10.2.min.js'>"+"<"+"script>");
</script>
<![endif]-->

<script type="text/javascript">
    if("ontouchend" in document) document.write("<script src='assets/js/jquery.mobile.custom.min.js'>"+"<"+"script>");
</script>

<script src="assets/js/bootstrap.min.js"></script>
<script src="assets/js/typeahead-bs2.min.js"></script>

<!-- page specific plugin scripts -->

<!--[if lte IE 8]>
<script src="assets/js/excanvas.min.js"></script>
<![endif]-->

<script src="assets/js/jquery-ui-1.10.3.custom.min.js"></script>
<script src="assets/js/jquery.ui.touch-punch.min.js"></script>
<script src="assets/js/jquery.slimscroll.min.js"></script>
<script src="assets/js/jquery.easy-pie-chart.min.js"></script>
<script src="assets/js/jquery.sparkline.min.js"></script>
<!--<script src="assets/js/flot/jquery.flot.min.js"></script>-->
<!--<script src="assets/js/flot/jquery.flot.pie.min.js"></script>-->
<!--<script src="assets/js/flot/jquery.flot.resize.min.js"></script>-->

<!-- ace scripts -->

<script src="assets/js/ace-elements.min.js"></script>
<script src="assets/js/ace.min.js"></script>

<script src="http://cdn.hcharts.cn/jquery/jquery-1.8.3.min.js"></script>


<script src="http://cdn.hcharts.cn/highcharts/highcharts.js"></script>

<script src="assets/js/My97DatePicker/WdatePicker.js" type="text/javascript"></script>

<!-- inline scripts related to this page -->

<script type="text/javascript">
    jQuery(function($) {

        $('#deviceType').val(${deviceType});

        todayStatics();
        chartBar1();

        $('#deviceType').change(function(){
            var deviceType = $(this).children('option:selected').val();
            todayStatics(deviceType);
            chartBar1();
        })
    })

    function todayStatics(){

        var deviceType = $("#deviceType").val();

        $.ajax({
            type: "POST",
            url: "statics/today",
            data: "deviceType="+deviceType,
            success: function(data){
                if(data.success){
                    var map = data.data;

                    $("#todayReject").html(map.todayReject);
                    $("#todayReview").html(map.todayReview);
                    $("#todayAccept").html(map.todayAccept);
                    $("#todayAcceptTotal").html(map.totayTotal);

                    $("#todayRejectPercent").html(GetPercent(map.todayReject,map.totayTotal));
                    $("#todayReviewPercent").html(GetPercent(map.todayReview,map.totayTotal));
                    $("#todayAcceptPercent").html(GetPercent(map.todayAccept,map.totayTotal));
                }
            }
        });





    }


    function chartBar1(){

        var deviceType = $("#deviceType").val();
        var eventType = $("#eventType").val();
        var startTime = $("#startTime").val();
        var endTime = $("#endTime").val();


        if(startTime ==null||startTime.length<=0){
            alert("开始时间不能为空,请选择");
            return;
        }

        if(endTime ==null||endTime.length<=0){
            alert("结束时间不能为空,请选择");
            return;
        }

        var checkR = checkdate(startTime,endTime);

        if(!checkR){
            return;
        }

        var categories = [];
        var series_accept = [];
        var series_review = [];
        var series_reject = [];
        $.ajax({
            type: "POST",
            url: "statics/query",
            data: "deviceType="+deviceType+"&eventType="+eventType+"&startTime="+startTime+"&endTime="+endTime,
            success: function(data){
                if(data.success){
                    var list = data.data
                    $.each(list,function (n,value) {
                        categories[n] = value.date;
                        series_accept[n] = value.acceptCount;
                        series_review[n] = value.reviewCount;
                        series_reject[n] = value.rejectCount;
                    });
                }

                var chart = {
                    type: 'column'
                };
                var title = {
                    text: '风险事件统计（'+startTime+' - '+endTime+'）'
                };
                var xAxis = {
                    categories:categories
                };

                var yAxis ={
                    min: 0,
                    allowDecimals:false,
                    title: {
                        text: ''
                    }
                };
                var legend = {

                };
                var tooltip = {
                    pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b>{point.y}</b> ({point.percentage:.0f}%)<br/>',
                    shared: true
                };
                var plotOptions = {
                    column: {
                        stacking: 'normal'
                    }
                };
                var credits = {
                    enabled: false
                };
                var series= [
                    {
                        name: '通过',
                        color: '#A7DB65',
                        data: series_accept
                    },
                    {
                        name: '人工审核',
                        color: '#F8D436',
                        data: series_review
                    },
                    {
                        name: '拒绝',
                        color: '#FF6C5C',
                        data: series_reject
                    }];

                var json = {};
                json.chart = chart;
                json.title = title;
                json.xAxis = xAxis;
                json.yAxis = yAxis;
                json.legend = legend;
                json.tooltip = tooltip;
                json.plotOptions = plotOptions;
                json.credits = credits;
                json.series = series;
                $('#bar1').highcharts(json);
            }
        });





    }


    function search() {
        chartBar1();
        return false;
    }

    function checkdate(startTime,endTime) {

        var start = new Date(startTime.replace("-", "/").replace("-", "/"));
        var end = new Date(endTime.replace("-", "/").replace("-", "/"));
        if (end < start) {
            alert('结束日期不能小于开始日期！');
            return false;
        }

        var dt =  (end-start)/(1000*60*60*24);


        if(dt>31){
            alert("时间范围过大，请查询31天内的数据");
            return false;
        }

        return true;
    }


</script>
</body>
</html>



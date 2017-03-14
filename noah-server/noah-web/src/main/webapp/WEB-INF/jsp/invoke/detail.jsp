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
    <base target="_blank" />
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

    <style>
        pre {outline: 1px solid #ccc; padding: 5px; margin: 5px; }
        .string { color: green; }
        .boolean { color: blue; }
        .null { color: magenta; }
        .key { color: red; }
    </style>

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
                        <a href="#">调用管理</a>
                    </li>
                    <li class="active">事件详情</li>
                </ul><!-- .breadcrumb -->

                <div class="nav-search" id="nav-search">
                    <form class="form-search">
								<span class="input-icon">
									<input type="text" placeholder="Search ..." class="nav-search-input" id="nav-search-input" autocomplete="off" />
									<i class="icon-search nav-search-icon"></i>
								</span>
                    </form>
                </div><!-- #nav-search -->
            </div>

            <div class="page-content">
                <div class="page-header">
                    <h1>
                    </h1>
                </div><!-- /.page-header -->



                <div class="row">
                    <div class="col-xs-12">
                        <!-- PAGE CONTENT BEGINS -->

                        <div class="row">
                            <ul>
                                <li>
                                    <label>事件发生时间</label>
                                    <span >${event.createTime}</span>
                                </li>
                                <li>
                                    <label>事件序列id</label>
                                    <span >${event.sequenceId}</span>
                                </li>
                                <li>
                                    <label>事件类型</label>
                                    <span >${event.eventType}</span>
                                </li>
                                <li>
                                    <label>账户</label>
                                    <span >${event.accountLogin}</span>
                                </li>
                                <li>
                                    <label>设备id</label>
                                    <span >${event.deviceId}</span>
                                </li>
                                <li>
                                    <label>beaconId</label>
                                    <span >${event.beaconId}</span>
                                </li>
                                <li>
                                    <label>来源ip</label>
                                    <span >${event.ip}</span>
                                </li>
                                <li>
                                    <label>评估结果</label>
                                    <span >
                                                            <c:if test="${event.decision==1}">ACCEPT</c:if>
                                                            <c:if test="${event.decision==2}">REVIEW</c:if>
                                                            <c:if test="${event.decision==3}">REJECT</c:if>
                                                        </span>
                                </li>
                                <li>
                                    <label>评分</label>
                                    <span >${event.score}</span>
                                </li>
                                <li>
                                    <label>平台</label>
                                    <span >
                                                            <c:if test="${event.deviceType==0}">Android</c:if>
                                                            <c:if test="${event.deviceType==1}">IOS</c:if>
                                                            <c:if test="${event.deviceType==2}">WEB</c:if>
                                                        </span>
                                </li>
                                <li>
                                    <label>事件详情</label>
                                    <span id="eventDetail"></span>


                                </li>
                                <li>
                                    <label>裁决结果</label>
                                    <span id="decisionDetail"></span>
                                </li>

                                <li>
                                    <label>UserAgent</label>
                                    <span >${event.userAgent}</span>
                                </li>
                                <li>
                                    <label>手机号城市</label>
                                    <span >${event.phoneEntity.city}</span>
                                </li>

                                <li>
                                    <label>IP城市</label>
                                    <span >${event.ipEntity.city}</span>
                                </li>

                                <li>
                                    <label>备注</label>
                                    <span >${event.remark}</span>
                                </li>

                            </ul>

                        </div>

                        <!-- PAGE CONTENT ENDS -->
                    </div><!-- /.col -->
                </div><!-- /.row -->
            </div><!-- /.page-content -->
        </div><!-- /.main-content -->

    </div><!-- /.main-container-inner -->

    <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
        <i class="icon-double-angle-up icon-only bigger-110"></i>
    </a>
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
<script src="assets/js/flot/jquery.flot.min.js"></script>
<script src="assets/js/flot/jquery.flot.pie.min.js"></script>
<script src="assets/js/flot/jquery.flot.resize.min.js"></script>

<!-- ace scripts -->

<script src="assets/js/ace-elements.min.js"></script>
<script src="assets/js/ace.min.js"></script>

<!-- inline scripts related to this page -->

<script type="text/javascript">
    jQuery(function($) {


        var eventDetail = ${event.eventDetail};
        var decisionDetail = ${event.decisionDetail};

        $('#eventDetail').html('<pre>'+syntaxHighlight(eventDetail)+'</pre>');
        $('#decisionDetail').html('<pre>'+syntaxHighlight(decisionDetail)+'</pre>');


    })


    function syntaxHighlight(json) {

        if (typeof json != 'string') {
            json = JSON.stringify(json, undefined, 4);
        }
        json = json.replace(/&/g, '&').replace(/</g, '<').replace(/>/g, '>');
        return json.replace(/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g, function(match) {
            var cls = 'number';
            if (/^"/.test(match)) {
                if (/:$/.test(match)) {
                    cls = 'key';
                } else {
                    cls = 'string';
                }
            } else if (/true|false/.test(match)) {
                cls = 'boolean';
            } else if (/null/.test(match)) {
                cls = 'null';
            }
            return '<span class="' + cls + '">' + match + '</span>';
        });
    }
</script>
</body>
</html>


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

    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400,300" />

    <!-- ace styles -->

    <link rel="stylesheet" href="assets/css/ace.min.css" />
    <link rel="stylesheet" href="assets/css/ace-rtl.min.css" />
    <link rel="stylesheet" href="assets/css/ace-skins.min.css" />

    <!--[if lte IE 8]>
    <link rel="stylesheet" href="assets/css/ace-ie.min.css" />
    <![endif]-->

    <link rel="stylesheet" href="assets/css/bootstrap-switch.css" />

    <!-- inline styles related to this page -->

    <!-- ace settings handler -->

    <script src="assets/js/ace-extra.min.js"></script>



    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

    <!--[if lt IE 9]>
    <script src="assets/js/html5shiv.js"></script>
    <script src="assets/js/respond.min.js"></script>
    <![endif]-->

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
                        <a href="#">开关管理</a>
                    </li>
                    <li class="active">开关</li>
                </ul><!-- .breadcrumb -->

            </div>

            <div class="page-content">
                <div class="page-header">
                    <h1>
                        开关
                    </h1>
                </div><!-- /.page-header -->

                <div class="row">
                    <div class="col-xs-12">
                        <!-- PAGE CONTENT BEGINS -->

                        <div class="center">


                            <div class="row">
                                <div class="col-xs-6 col-sm-6">
                                    <span>图形验证码开关</span>
                                </div>

                                <div class="col-xs-2 col-sm-2">
                                    <label>
                                        <input id="switch_code" name="switch-field-1" class="ace ace-switch ace-switch-4" type="checkbox" />
                                        <span class="lbl"></span>
                                    </label>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-6 col-sm-6">
                                    <span>风控采集开关</span>
                                </div>

                                <div class="col-xs-2 col-sm-2">
                                    <label>
                                        <input id="switch_risk" name="switch-field-1" class="ace ace-switch ace-switch-4" type="checkbox" />
                                        <span class="lbl"></span>
                                    </label>
                                </div>
                            </div>

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

<!-- basic scripts -->

<!--[if !IE]> -->

<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>

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

<script type="text/javascript">
    if("ontouchend" in document) document.write("<script src='assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
</script>

<script src="assets/js/typeahead-bs2.min.js"></script>

<!-- page specific plugin scripts -->

<script src="assets/js/jquery.dataTables.min.js"></script>
<script src="assets/js/jquery.dataTables.bootstrap.js"></script>

<!-- ace scripts -->

<script src="assets/js/bootstrap-switch.js"></script>



<script type="text/javascript">
    var JJ= jQuery.noConflict();
    JJ(function() {

        JJ("#switch_code").bootstrapSwitch("state",${switch_verfiy_code});
        JJ("#switch_risk").bootstrapSwitch("state",${switch_risk});

        JJ("#switch_code").on('switchChange.bootstrapSwitch', function(event, state) {

            if(state){
                switchState = "on";
            }else{
                switchState = "off";
            }

            JJ.ajax({
                type: "POST",
                url: "switch/switchState",
                data: "switchName=switch_verfiy_code&state="+switchState,
                success: function(data){
                    if(data.success){
                        alert( "修改成功" );
                    }else{
                        alert("修改失败！");
                    }

                }
            });
        });

        JJ("#switch_risk").on('switchChange.bootstrapSwitch', function(event, state) {

            if(state){
                switchState = "on";
            }else{
                switchState = "off";
            }
            JJ.ajax({
                type: "POST",
                url: "switch/switchState",
                data: "switchName=switch_risk&state="+switchState,
                success: function(data){
                    if(data.success){
                        alert( "修改成功" );
                    }else{
                        alert("修改失败！");
                    }

                }
            });
        });

    })


</script>


</body>
</html>

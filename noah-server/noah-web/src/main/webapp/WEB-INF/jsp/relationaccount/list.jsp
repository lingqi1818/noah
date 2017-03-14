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

        function query(){
            $("input[name='cursor']").val('0');

            if(check()){
                $("#queryForm").submit();
            }else{
                return false;
            }



        }

        function nextPage(){

            if(check()){
                $("#queryForm").submit();
            }else{
                return false;
            }
        }

        function check(){
            if($("#queryType").val()=="-1"){
                alert("请选择类型");
                return false;
            }



            if($("#queryKey").val()=="" ){
                alert("查询内容不能为空");
                return false;
            }
            return true;
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
                    <li class="active">关联账户查询</li>
                </ul><!-- .breadcrumb -->

            </div>

            <div class="page-content">




                <div class="row">
                    <div class="col-xs-12">
                        <!-- PAGE CONTENT BEGINS -->
                        <div class="well">
                            <form action="relationaccount/query" method="post" id="queryForm" class="form-horizontal" role="form">
                                <input type="hidden" name="cursor" value="${nextCursor}"/>
                                <div class="form-group">
                                    <div class="col-sm-2">
                                        <select id="queryType" name="queryType" >
                                            <option value="-1">--类型--</option>
                                            <option value="risk_ip_account" <c:if test="${queryType=='risk_ip_account'}"> selected="true"</c:if>>IP</option>
                                            <option value="risk_device_id_account" <c:if test="${queryType=='risk_device_id_account'}"> selected="true"</c:if>>设备ID</option>
                                            <option value="risk_beacon_id_account" <c:if test="${queryType=='risk_beacon_id_account'}"> selected="true"</c:if>>BEACON_ID</option>
                                        </select>
                                    </div>

                                    <div class="col-sm-6">
                                        <input type="text" id="queryKey" name="queryKey" value="${queryKey}"  />
                                    </div>

                                    <div class="col-sm-2">
                                        <select id="rangeType" name="rangeType" >
                                            <option value="1" <c:if test="${rangeType=='1'}"> selected="true"</c:if>>当天</option>
                                            <option value="-1" <c:if test="${rangeType=='-1'}"> selected="true"</c:if>>全部</option>

                                        </select>
                                    </div>
                                    <div class="col-sm-1">
                                        <input  type="submit" class="btn btn-default" onclick="return query()" value="查询"/>
                                    </div>

                                </div>

                            </form>
                        </div>

                        <div class="row">
                            <div class="col-xs-4">
                                <div class="table-responsive">
                                    <table id="sample-table-1" class="table table-striped table-bordered table-hover">
                                        <thead>
                                        <tr>

                                            <th>账户</th>
                                        </tr>
                                        </thead>

                                        <tbody>
                                            <c:forEach items="${result}" var="d">
                                                <tr>
                                                    <td>${d}</td>
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

    <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
        <i class="icon-double-angle-up icon-only bigger-110"></i>
    </a>
</div><!-- /.main-container -->

<!-- basic scripts -->

<!--[if !IE]> -->

<script src="assets/js/jquery-2.0.3.min.js"></script>

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
        var oTable1 = $('#sample-table-2').dataTable( {
            "aoColumns": [
                { "bSortable": false },
                null, null,null, null, null,
                { "bSortable": false }
            ] } );


        $('table th input:checkbox').on('click' , function(){
            var that = this;
            $(this).closest('table').find('tr > td:first-child input:checkbox')
                    .each(function(){
                        this.checked = that.checked;
                        $(this).closest('tr').toggleClass('selected');
                    });

        });


        $('[data-rel="tooltip"]').tooltip({placement: tooltip_placement});
        function tooltip_placement(context, source) {
            var $source = $(source);
            var $parent = $source.closest('table')
            var off1 = $parent.offset();
            var w1 = $parent.width();

            var off2 = $source.offset();
            var w2 = $source.width();

            if( parseInt(off2.left) < parseInt(off1.left) + parseInt(w1 / 2) ) return 'right';
            return 'left';
        }
    })
</script>
</body>
</html>

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
                    <li class="active">设备ID与账户映射</li>
                </ul><!-- .breadcrumb -->

            </div>

            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">
                        <!-- PAGE CONTENT BEGINS -->
                        <div class="well">
                            <form class="form-horizontal" role="form" id="queryForm">
                                <input type="hidden" name="pageNum" value="${pageObj.curPage}">
                                <div class="form-group">
                                    <label class="col-sm-1 control-label no-padding-right" for="form-field-1"> 账户 </label>

                                    <div class="col-sm-2">
                                        <input type="text" id="form-field-1" name="userAccount" value="${userAccount}"  />
                                    </div>

                                    <label class="col-sm-1 control-label no-padding-right" for="form-field-2"> 设备ID </label>

                                    <div class="col-sm-4">
                                        <input type="text" id="form-field-2"  name="deviceId" value="${deviceId}"/>
                                    </div>
                                    <div class="col-sm-4">
                                        <input type="submit" class="btn btn-default" onclick="$('#queryForm').submit()" value="查询"/>
                                    </div>

                                </div>

                            </form>
                        </div>

                        <div class="row">
                            <div class="col-xs-12">
                                <div class="table-responsive">
                                    <table id="sample-table-1" class="table table-striped table-bordered table-hover">
                                        <thead>
                                        <tr>

                                            <th>ID</th>
                                            <th>账户</th>
                                            <th>账户类型</th>
                                            <th>设备</th>
                                            <th>设备ID</th>
                                            <th>签名版本</th>
                                            <th>cpu_id</th>
                                            <th>cpu_mode</th>
                                            <th>是否有效</th>
                                            <th>创建时间</th>
                                            <th>修改时间</th>
                                        </tr>
                                        </thead>

                                        <tbody>
                                        <c:forEach items="${pageObj.datas}" var="data">

                                            <tr>
                                                <td>${data.id}</td>
                                                <td>${data.userAccount}</td>
                                                <td>
                                                    <c:if test="${data.userType==0}">普通用户</c:if>
                                                    <c:if test="${data.userType==1}">手艺人</c:if>
                                                </td>
                                                <td>
                                                    <c:if test="${data.deviceType==0}">安卓</c:if>
                                                    <c:if test="${data.deviceType==1}">IOS</c:if>
                                                </td>

                                                <td>${data.deviceId}</td>
                                                <td>${data.signVer}</td>
                                                <td>${data.cpuId}</td>
                                                <td>${data.cpuMode}</td>
                                                <td>
                                                    <c:if test="${data.valid==0}">无效</c:if>
                                                    <c:if test="${data.valid==1}">有效</c:if>
                                                </td>
                                                <td>${data.createTime}</td>
                                                <td>${data.updateTime}</td>


                                            </tr>

                                        </c:forEach>

                                        </tbody>
                                    </table>
                                    <%@ include file="/WEB-INF/include/pagediv.jsp"%>
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

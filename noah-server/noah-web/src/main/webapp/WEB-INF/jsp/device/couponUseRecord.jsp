
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
                        <a href="#">名单管理</a>
                    </li>

                    <li>
                        <a href="#">设备用券查询</a>
                    </li>
                    <li class="active">设备用券查询</li>
                </ul><!-- .breadcrumb -->


            </div>

            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">
                        <!-- PAGE CONTENT BEGINS -->
                        <div class="well">

                            <form action="userDevice/couponUseRecord" method="post" id="queryForm">


                                手机号：&nbsp;  <input type="text" name = "userAccount" value="${userAccount}"/>

                                <button class="btn btn-sm btn-primary" onclick="$('#queryForm').submit()">查询</button>
                                &nbsp;


                            </form>
                        </div>

                        <div class="row">
                            <div class="col-xs-12" style="overflow-x:auto">
                                <div class="table-responsive">
                                    <table class="table table-bordered table-condensed">
                                        <thead>
                                        <tr>
                                            <th>关联设备ID</th>
                                            <th>使用优惠券code</th>
                                            <th>使用优惠券名称</th>
                                            <th>使用手机</th>
                                            <th>使用时间</th>
                                        </tr>
                                        </thead>
                                        <tbody id="table_r">
                                        <c:if test="${empty result && flag == 'q'}">
                                            <tr>
                                                <td colspan="4">无用券记录</td>

                                            </tr>

                                        </c:if>
                                        <c:if test="${!empty result}">
                                            <c:forEach var="d" items="${result}">
                                                <tr>
                                                    <td><c:out value="${d.get('beacon_id')}"/></td>
                                                    <td><c:out value="${d.get('coupon_code')}"/></td>
                                                    <td><c:out value="${d.get('coupon_name')}"/></td>
                                                    <td><c:out value="${d.get('mobile')}"/></td>
                                                    <td><c:out value="${d.get('update_time')}"/></td>

                                                </tr>

                                            </c:forEach>
                                        </c:if>


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
</div><!-- /.main-container -->

<!-- basic scripts -->
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


</script>
</body>
</html>

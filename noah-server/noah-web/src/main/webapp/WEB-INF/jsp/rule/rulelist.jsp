<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<%
    String path = request.getContextPath();

    String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
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
                        <a href="#">规则管理</a>
                    </li>
                    <li class="active">规则查询</li>
                </ul><!-- .breadcrumb -->



            </div>

            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">
                        <form action="${basePath}/rulemanage/query" method="post" id="queryForm">
                            <input type="hidden" name="pageNum" value="${pageObj.curPage}">
                            <label style="width:65px; float:left;" >事件类型:</label>
                            <select class="form-control" name="eventCode" style="width:100px;float:left;" id="form-field-select-1">
                                        <option value="">&nbsp;</option>
                                        <option value="login">登陆事件</option>
                                        <option value="register">注册事件</option>
                                        <option value="submitOrder">订单事件</option>
                            </select>
                            <button class="btn btn-sm btn-primary" onclick="$('#queryForm').submit()">查询</button>
                        </form>
                        <button class="btn btn-sm btn-primary" onclick="window.location.href='${basePath}/rulemanage/toAdd'">新增规则</button>
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
                                                <th class="center">
                                                    <label>
                                                        <input type="checkbox" class="ace" />
                                                        <span class="lbl"></span>
                                                    </label>
                                                </th>

                                                <th>规则编号</th>
                                                <th>规则名称</th>
                                                <th>操作</th>
                                            </tr>
                                        </thead>

                                        <tbody>
                                        <c:forEach items="${pageObj.datas}" var="data">
                                            <tr>

                                                <td>
                                                    <label>
                                                        <input type="checkbox" class="ace" />
                                                        <span class="lbl"></span>
                                                    </label>
                                                </td>

                                                <td>${data.ruleCode}</td>
                                                <td>${data.ruleName}</td>
                                                <td><button class="btn btn-xs">启用</button> </td>
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

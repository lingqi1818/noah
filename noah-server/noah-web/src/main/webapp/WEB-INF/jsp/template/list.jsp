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

        function add(){

            $.ajax({
                type: "POST",
                url: "ruleTemplate/add",
                data: $("#addForm").serialize(),
                success: function(data){
                    if(data.success){
                        alert( "保存成功" );
                    }else{
                        alert("保存失败!"+data.apiMessage);
                    }
                    $('#myModal').modal('hide');
                    location.href='ruleTemplate/query';
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
                        <a href="#">规则管理</a>
                    </li>
                    <li class="active">规则模板</li>
                </ul><!-- .breadcrumb -->

            </div>

            <div class="page-content">

                <div class="row">
                    <div class="col-xs-12">
                        <!-- PAGE CONTENT BEGINS -->

                        <div class="row">
                            <div class="col-xs-12">
                                <div class="well">
                                    <form class="form-horizontal" role="form">
                                        <input type="hidden" name="pageNum" value="${pageObj.curPage}">
                                        <div class="form-group">
                                            <label class="col-sm-1 control-label no-padding-right" for="form-field-1"> 模板类型 </label>

                                            <div class="col-sm-2" >
                                                <select id="form-field-1" name="type" >
                                                    <c:forEach items="${ruleTemplateTypes}" var="data">
                                                        <option value="${data.code}" <c:if test="${data.code==type}"> selected="true"</c:if> >${data.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>

                                            <div class="col-sm-4">
                                                <input type="submit" class="btn btn-default" value="查询"/>
                                            </div>

                                        </div>

                                    </form>
                                </div>
                            </div><!-- /span -->
                        </div><!-- /row -->

                        <div class="row">
                            <div class="col-xs-12">

                                <button class="btn btn-sm btn-primary" data-toggle="modal" data-target="#myModal">
                                    新增
                                </button>
                            </div><!-- /span -->
                        </div><!-- /row -->

                        <br/>
                        <div class="row">
                            <div class="col-xs-12">
                                <div class="table-responsive">
                                    <table id="sample-table-1" class="table table-striped table-bordered table-hover">
                                        <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>模板类型</th>
                                            <th>名称</th>
                                            <th>VM模板名称</th>
                                            <th>关联bean</th>
                                            <th>描述</th>
                                            <th>状态</th>
                                            <th>操作</th>
                                        </tr>
                                        </thead>

                                        <tbody>

                                            <c:forEach items="${pageObj.datas}" var="data">

                                            <tr>
                                                <td>${data.id}</td>
                                                <td>
                                                    <c:forEach items="${ruleTemplateTypes}" var="t">
                                                        <c:if test="${t.code==data.type}">${t.name}</c:if>
                                                    </c:forEach>
                                                </td>
                                                <td>${data.name}</td>
                                                <td>${data.templateName}</td>
                                                <td>${data.beanName}</td>
                                                <td>${data.description}</td>
                                                <td>
                                                    <c:if test="${data.status==0}">无效</c:if>
                                                    <c:if test="${data.status==1}">有效</c:if>
                                                </td>
                                                <td>
                                                    <div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">

                                                        <a class="green" href="#">
                                                            <i class="icon-pencil bigger-130"></i>
                                                        </a>

                                                        <a class="yellow" href="#">
                                                            <i class="icon-info-sign bigger-130"></i>
                                                        </a>

                                                        <a class="red" href="#">
                                                            <i class="icon-trash bigger-130"></i>
                                                        </a>
                                                    </div>

                                                </td>
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
                        新增规则模板
                    </h4>
                </div>
                <div class="modal-body">

                    <div style="padding: 10px 10px 10px 10px;">
                        <form role="form" id="addForm">
                            <div class="form-group">
                                <label for="name">名称</label>
                                <input type="text" class="form-control" id="name" name="name"
                                       placeholder="请输入名称">
                            </div>
                            <div class="form-group">
                                <label for="type">类型</label>
                                <select class="form-control" id="type" name="type">
                                    <c:forEach items="${ruleTemplateTypes}" var="data">
                                        <option value="${data.code}">${data.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="templateName">VM模板名称</label>
                                <input type="text" class="form-control" id="templateName" name="templateName"/>
                            </div>
                            <div class="form-group">
                                <label for="beanName">关联Bean</label>
                                <input type="text" class="form-control" id="beanName" name="beanName"/>
                            </div>
                            <div class="form-group">
                                <label for="description">备注</label>
                                <textarea class="form-control" rows="3" id="description" name="description"></textarea>
                            </div>
                        </form>
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default"
                            data-dismiss="modal">关闭
                    </button>
                    <button type="button" class="btn btn-primary" onclick="add()">
                        提交更改
                    </button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
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

        $('#myModal').modal({
            keyboard: true,
            show:false
        })

    })
</script>
</body>
</html>
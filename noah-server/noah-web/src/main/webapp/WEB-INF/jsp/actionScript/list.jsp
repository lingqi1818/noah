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
                url: "actionScript/add",
                data: $("#addForm").serialize(),
                success: function(data){
                    if(data.success){
                        alert( "保存成功" );
                    }else{
                        alert("保存失败！");
                    }

                    $('#myModal').modal('hide');

                    location.href='actionScript/query';

                }
            });
        }




        function deleteActionScript(id){
            $.ajax({
                type: "POST",
                url: "actionScript/delete",
                data: "id="+id,
                success: function(data){
                    if(data.success){
                        alert( "删除成功" );
                    }else{
                        alert("删除失败！"+data.apiMessage);
                    }

                    location.href='actionScript/query';

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
                        <a href="#">Action脚本</a>
                    </li>
                    <li class="active">Action脚本</li>
                </ul><!-- .breadcrumb -->
            </div>

            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">
                        <form action="actionScript/query" method="post" id="queryForm">
                            <input type="hidden" name="pageNum" value="${pageObj.curPage}">
                            <select id="actionType" name = "type">
                                <option value="1" <c:if test="${type==1}"> selected</c:if>>RuleAction</option>
                                <option value="2" <c:if test="${type==2}"> selected</c:if>>PolicyAction</option>
                            </select>

                            <input type="text" name = "name" value="${name}"/>

                            <button class="btn btn-sm btn-primary" onclick="$('#queryForm').submit()">查询</button>
                            &nbsp;

                            <button class="btn btn-sm btn-primary" data-toggle="modal" data-target="#myModal">
                                新增
                            </button>
                        </form>
                    </div>
                    <div class="col-xs-12">
                        <!-- PAGE CONTENT BEGINS -->

                        <div class="row">
                            <div class="col-xs-12">
                                <div class="table-header">
                                </div>

                                <div class="table-responsive">
                                    <table id="sample-table-2" class="table table-striped table-bordered table-hover" style="TABLE-LAYOUT:fixed;WORD-BREAK:break-all" >
                                        <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>脚本名称</th>
                                            <th>脚本类型</th>
                                            <th>创建时间</th>
                                            <th>备注</th>
                                            <th>操作</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${pageObj.datas}" var="d">
                                            <tr>
                                                <td>${d.id}</td>
                                                <td>${d.name}</td>
                                                <td>${d.type}</td>
                                                <td>${d.createTime}</td>
                                                <td>${d.remark}</td>
                                                <td>
                                                    <div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
                                                        <a class="red" href="javascript:void(0)" onclick="deleteActionScript(${d.id})">
                                                            <i class="icon-trash bigger-130"></i>
                                                            删除
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
                        新增
                    </h4>
                </div>
                <div class="modal-body">

                    <div style="padding: 10px 100px 10px;">
                        <form role="form" class="form-group" id="addForm">
                            <div class="form-group">
                                <label for="name">脚本名称</label>
                                <input type="text" class="form-control" id="name"  name="name">
                            </div>
                            <div class="control-group ">
                                <label for="dataType1">脚本类型</label>
                                    <select id="dataType1" name = "type" class="form-control">
                                        <option value="1">RuleAction</option>
                                        <option value="2">PolicyAction</option>
                                    </select>
                            </div>
                            <div class="form-group">
                                <label for="scriptContent">脚本内容</label>
                                <textarea class="form-control" rows="5" id="scriptContent"  name="scriptContent"></textarea>
                            </div>
                            <div class="form-group">
                                <label for="remark">描述</label>
                                <input type="text" class="form-control" id="remark"  name="remark">
                            </div>
                        </form>
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default"
                            data-dismiss="modal">关闭
                    </button>
                    <button type="button" class="btn btn-primary" onclick="add()">
                        提交
                    </button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->


    </div>

        <!-- basic scripts -->

        <!--[if !IE]> -->

        <script src="assets/js/jquery-2.0.3.min.js"></script>

        <!-- <![endif]-->

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
        <div style="display:none"></div>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/include/taglib.jsp" %>
<%
    String path = request.getContextPath();

    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!DOCTYPE html>
<html lang="en">
<head>

    <base href="${basePath}">
    <meta charset="utf-8"/>
    <title>亿方云风控管理平台</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <link href="assets/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="assets/css/font-awesome.min.css"/>
    <!--[if IE 7]>
    <link rel="stylesheet" href="assets/css/font-awesome-ie7.min.css"/>
    <![endif]-->
    <!-- page specific plugin styles -->
    <link rel="stylesheet" href="assets/css/jquery-ui-1.10.3.custom.min.css"/>
    <link rel="stylesheet" href="assets/css/jquery.gritter.css"/>
    <!-- fonts -->
    <!-- ace styles -->
    <link rel="stylesheet" href="assets/css/ace.min.css"/>
    <link rel="stylesheet" href="assets/css/ace-rtl.min.css"/>
    <link rel="stylesheet" href="assets/css/ace-skins.min.css"/>
    <!--[if lte IE 8]>
    <link rel="stylesheet" href="assets/css/ace-ie.min.css"/>
    <![endif]-->
    <link rel="stylesheet" href="assets/css/BootSideMenu.css">
    <link rel="stylesheet" href="assets/css/rulepage.css">
    <script src="assets/js/ace-extra.min.js"></script>
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="assets/js/html5shiv.js"></script>
    <script src="assets/js/respond.min.js"></script>
    <![endif]-->

    <script type="text/javascript">

        function showAdd() {

            $('#myModal').modal('show');
        }


        function addDetail() {

            var collectParamConfigId = $("#collectParamConfigId").val();

            var src = $("#src").val();
            var target = $("#target").val();
            var type = $("#type").val();

            var datas = {
                'collectParamConfigId':collectParamConfigId,
                'src':src,
                'target':target,
                'type':type
            };

            $.ajax({
                type: "POST",
                url: "collectParamDetail/add",
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                data : JSON.stringify(datas),
                success: function (data) {

                    if (data.success) {
                        alert("成功");
                        $('#myModal').modal('hide');

                        location.href = 'collectParamConfig/detail?configId=' +${configId};
                    } else {
                        alert(data.apiMessage);
                    }


                }
            });

        }


        function deleteCollectDetail(id){
            $.ajax({
                type: "POST",
                url: "collectParamDetail/delete",
                data: "id="+id,
                success: function(data){
                    if(data.success){
                        alert( "删除成功" );
                    }else{
                        alert("删除失败！"+data.apiMessage);
                    }

                    location.href='collectParamConfig/detail?configId='+${configId};

                }
            });
        }

    </script>

</head>

<body>
<%@ include file="/WEB-INF/jsp/head.jsp" %>

<div class="main-container" id="main-container">
    <script type="text/javascript">
        try {
            ace.settings.check('main-container', 'fixed')
        } catch (e) {
        }
    </script>

    <div class="main-container-inner">
        <a class="menu-toggler" id="menu-toggler" href="#">
            <span class="menu-text"></span>
        </a>

        <%@ include file="/WEB-INF/jsp/side.jsp" %>
        <div class="main-content">
            <div class="breadcrumbs" id="breadcrumbs">
                <script type="text/javascript">
                    try {
                        ace.settings.check('breadcrumbs', 'fixed')
                    } catch (e) {
                    }
                </script>
                <ul class="breadcrumb">
                    <li>
                        <i class="icon-home home-icon"></i>
                        <a href="#">Home</a>
                    </li>
                    <li>
                        <a href="#">规则引擎服务</a>
                    </li>
                    <li class="active">采集参数配置</li>
                </ul>
                <!-- .breadcrumb -->
            </div>
            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">
                        <!-- PAGE CONTENT BEGINS -->
                        <div class="row">
                            <div>
                                <button class="btn btn-sm btn-primary" onclick="showAdd()">新增采集参数</button>
                            </div>

                            <div class="col-xs-12">
                                <div class="table-responsive">
                                    <table id="sample-table-1" class="table table-striped table-bordered table-hover">
                                        <thead>
                                        <tr>
                                            <th>编号</th>
                                            <th>原参数</th>
                                            <th>映射后</th>
                                            <th>类型</th>
                                            <th>操作</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${detailList}" var="r">
                                            <tr>
                                                <td>${r.id}</td>
                                                <td>${r.src}</td>
                                                <td>${r.target}</td>
                                                <td>${r.type}</td>

                                                <td>
                                                    <div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">

                                                        <a class="red" href="javascript:void(0)" onclick="deleteCollectDetail(${r.id})">
                                                            <i class="icon-trash bigger-130"></i>
                                                            删除
                                                        </a>
                                                    </div>
                                                </td>
                                            </tr>

                                        </c:forEach>


                                        </tbody>
                                    </table>

                                </div>
                                <!-- /.table-responsive -->
                            </div>
                            <!-- /span -->
                        </div>
                        <!-- /row -->
                    </div>
                    <!-- /.col -->
                </div>
                <!-- /.row -->
            </div>
            <!-- /.page-content -->
        </div>
        <!-- /.main-content -->
    </div>
    <!-- /.main-container-inner -->
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
                        新增参数
                    </h4>
                </div>
                <div class="modal-body">

                    <div style="padding: 10px 10px 10px;">
                        <form role="form" id="addForm">
                            <input type="hidden" id="collectParamConfigId" name="collectParamConfigId" value="${configId}">

                            <div class="form-group">
                                <label for="src">采集参数名</label>
                                <input type="text" class="form-control" id="src" name="src"
                                        >
                            </div>
                            <div class="form-group">
                                <label for="target">映射后参数名</label>
                                <input type="text" class="form-control" id="target" name="target"
                                        >
                            </div>
                            <div class="form-group">
                                <label for="type">类型</label>
                                <select id="type" class="form-control" name="type"  >
                                    <option value="string">string</option>
                                    <option value="int">int</option>
                                    <option value="double">double</option>
                                </select>
                            </div>

                        </form>
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default"
                            data-dismiss="modal">关闭
                    </button>
                    <button type="button" class="btn btn-primary" onclick="addDetail()">
                        保存
                    </button>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>
    <!-- /.modal -->

    <!-- /.modal-dialog -->

</div>
<!-- /.modal -->
</div><!-- /.main-container -->

<!-- basic scripts -->

<!--[if !IE]> -->


<!-- <![endif]-->

<!--[if IE]>
<![endif]-->

<!--[if !IE]> -->

<script type="text/javascript">
    window.jQuery || document.write("<script src='assets/js/jquery-2.0.3.min.js'>" + "<" + "/script>");
</script>

<!-- <![endif]-->

<!--[if IE]>
<script type="text/javascript">
    window.jQuery || document.write("<script src='assets/js/jquery-1.10.2.min.js'>" + "<" + "/script>");
</script>
<![endif]-->

<script type="text/javascript">
    if ("ontouchend" in document) document.write("<script src='assets/js/jquery.mobile.custom.min.js'>" + "<" + "/script>");
</script>
<script src="assets/js/bootstrap.min.js"></script>
<script src="assets/js/typeahead-bs2.min.js"></script>

<!-- page specific plugin scripts -->

<!--[if lte IE 8]>
<script src="assets/js/excanvas.min.js"></script>
<![endif]-->

<script src="assets/js/jquery-ui-1.10.3.custom.min.js"></script>
<script src="assets/js/jquery.ui.touch-punch.min.js"></script>
<script src="assets/js/bootbox.min.js"></script>
<script src="assets/js/jquery.easy-pie-chart.min.js"></script>
<script src="assets/js/jquery.gritter.min.js"></script>

<!-- ace scripts -->

<script src="assets/js/ace-elements.min.js"></script>
<script src="assets/js/ace.min.js"></script>
<script src="assets/js/BootSideMenu.js"></script>

<!-- inline scripts related to this page -->

<script type="text/javascript">
    jQuery(function ($) {

        $('#accordion-style').on('click', function (ev) {
            var target = $('input', ev.target);
            var which = parseInt(target.val());
            if (which == 2) $('#accordion').addClass('accordion-style2');
            else $('#accordion').removeClass('accordion-style2');
        });

        var oldie = /msie\s*(8|7|6)/.test(navigator.userAgent.toLowerCase());
        $('.easy-pie-chart.percentage').each(function () {
            $(this).easyPieChart({
                barColor: $(this).data('color'),
                trackColor: '#EEEEEE',
                scaleColor: false,
                lineCap: 'butt',
                lineWidth: 8,
                animate: oldie ? false : 1000,
                size: 75
            }).css('color', $(this).data('color'));
        });

        $('[data-rel=tooltip]').tooltip();
        $('[data-rel=popover]').popover({html: true});


        $('#gritter-regular').on(ace.click_event, function () {
            $.gritter.add({
                title: 'This is a regular notice!',
                text: 'This will fade out after a certain amount of time. Vivamus eget tincidunt velit. Cum sociis natoque penatibus et <a href="#" class="blue">magnis dis parturient</a> montes, nascetur ridiculus mus.',
                image: $path_assets + '/avatars/avatar1.png',
                sticky: false,
                time: '',
                class_name: (!$('#gritter-light').get(0).checked ? 'gritter-light' : '')
            });

            return false;
        });

        $('#gritter-sticky').on(ace.click_event, function () {
            var unique_id = $.gritter.add({
                title: 'This is a sticky notice!',
                text: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus eget tincidunt velit. Cum sociis natoque penatibus et <a href="#" class="red">magnis dis parturient</a> montes, nascetur ridiculus mus.',
                image: $path_assets + '/avatars/avatar.png',
                sticky: true,
                time: '',
                class_name: 'gritter-info' + (!$('#gritter-light').get(0).checked ? ' gritter-light' : '')
            });

            return false;
        });


        $('#gritter-without-image').on(ace.click_event, function () {
            $.gritter.add({
                // (string | mandatory) the heading of the notification
                title: 'This is a notice without an image!',
                // (string | mandatory) the text inside the notification
                text: 'This will fade out after a certain amount of time. Vivamus eget tincidunt velit. Cum sociis natoque penatibus et <a href="#" class="orange">magnis dis parturient</a> montes, nascetur ridiculus mus.',
                class_name: 'gritter-success' + (!$('#gritter-light').get(0).checked ? ' gritter-light' : '')
            });

            return false;
        });


        $('#gritter-max3').on(ace.click_event, function () {
            $.gritter.add({
                title: 'This is a notice with a max of 3 on screen at one time!',
                text: 'This will fade out after a certain amount of time. Vi<vamus eget tincidunt velit. Cum sociis natoque penatibus et <a href="#" class="green">magnis dis parturient</a> montes, nascetur ridiculus mus.',
                image: $path_assets + '/avatars/avatar3.png',
                sticky: false,
                before_open: function () {
                    if ($('.gritter-item-wrapper').length >= 3) {
                        return false;
                    }
                },
                class_name: 'gritter-warning' + (!$('#gritter-light').get(0).checked ? ' gritter-light' : '')
            });

            return false;
        });


        $('#gritter-center').on(ace.click_event, function () {
            $.gritter.add({
                title: 'This is a centered notification',
                text: 'Just add a "gritter-center" class_name to your $.gritter.add or globally to $.gritter.options.class_name',
                class_name: 'gritter-info gritter-center' + (!$('#gritter-light').get(0).checked ? ' gritter-light' : '')
            });

            return false;
        });

        $('#gritter-error').on(ace.click_event, function () {
            $.gritter.add({
                title: 'This is a warning notification',
                text: 'Just add a "gritter-light" class_name to your $.gritter.add or globally to $.gritter.options.class_name',
                class_name: 'gritter-error' + (!$('#gritter-light').get(0).checked ? ' gritter-light' : '')
            });

            return false;
        });


        $("#gritter-remove").on(ace.click_event, function () {
            $.gritter.removeAll();
            return false;
        });


        ///////


        $("#bootbox-regular").on(ace.click_event, function () {
            bootbox.prompt("What is your name?", function (result) {
                if (result === null) {
                    //Example.show("Prompt dismissed");
                } else {
                    //Example.show("Hi <b>"+result+"</b>");
                }
            });
        });

        $("#bootbox-confirm").on(ace.click_event, function () {
            bootbox.confirm("Are you sure?", function (result) {
                if (result) {
                    //
                }
            });
        });

        $("#bootbox-options").on(ace.click_event, function () {
            bootbox.dialog({
                message: "<span class='bigger-110'>I am a custom dialog with smaller buttons</span>",
                buttons: {
                    "success": {
                        "label": "<i class='icon-ok'></i> Success!",
                        "className": "btn-sm btn-success",
                        "callback": function () {
                            //Example.show("great success");
                        }
                    },
                    "danger": {
                        "label": "Danger!",
                        "className": "btn-sm btn-danger",
                        "callback": function () {
                            //Example.show("uh oh, look out!");
                        }
                    },
                    "click": {
                        "label": "Click ME!",
                        "className": "btn-sm btn-primary",
                        "callback": function () {
                            //Example.show("Primary button");
                        }
                    },
                    "button": {
                        "label": "Just a button...",
                        "className": "btn-sm"
                    }
                }
            });
        });


        $('#spinner-opts small').css({display: 'inline-block', width: '60px'})

        var slide_styles = ['', 'green', 'red', 'purple', 'orange', 'dark'];
        var ii = 0;
        $("#spinner-opts input[type=text]").each(function () {
            var $this = $(this);
            $this.hide().after('<span />');
            $this.next().addClass('ui-slider-small').
                    addClass("inline ui-slider-" + slide_styles[ii++ % slide_styles.length]).
                    css({'width': '125px'}).slider({
                        value: parseInt($this.val()),
                        range: "min",
                        animate: true,
                        min: parseInt($this.data('min')),
                        max: parseInt($this.data('max')),
                        step: parseFloat($this.data('step')),
                        slide: function (event, ui) {
                            $this.attr('value', ui.value);
                            spinner_update();
                        }
                    });
        });


        $.fn.spin = function (opts) {
            this.each(function () {
                var $this = $(this),
                        data = $this.data();

                if (data.spinner) {
                    data.spinner.stop();
                    delete data.spinner;
                }
                if (opts !== false) {
                    data.spinner = new Spinner($.extend({color: $this.css('color')}, opts)).spin(this);
                }
            });
            return this;
        };

        function spinner_update() {
            var opts = {};
            $('#spinner-opts input[type=text]').each(function () {
                opts[this.name] = parseFloat(this.value);
            });
            $('#spinner-preview').spin(opts);
        }


        $('#id-pills-stacked').removeAttr('checked').on('click', function () {
            $('.nav-pills').toggleClass('nav-stacked');
        });


        $('#myModal').modal({
            keyboard: true,
            show: false
        })




    });




</script>
</body>
</html>

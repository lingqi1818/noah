<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="sidebar" id="sidebar">
    <script type="text/javascript">
        try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
    </script>

    <div class="sidebar-shortcuts" id="sidebar-shortcuts">
        <div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
            <button class="btn btn-success">
                <i class="icon-signal"></i>
            </button>

            <button class="btn btn-info">
                <i class="icon-pencil"></i>
            </button>

            <button class="btn btn-warning">
                <i class="icon-group"></i>
            </button>

            <button class="btn btn-danger">
                <i class="icon-cogs"></i>
            </button>
        </div>

        <div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
            <span class="btn btn-success"></span>

            <span class="btn btn-info"></span>

            <span class="btn btn-warning"></span>

            <span class="btn btn-danger"></span>
        </div>
    </div><!-- #sidebar-shortcuts -->

    <ul class="nav nav-list">

        <li>
            <a href="#" class="dropdown-toggle">
                <i class="icon-dashboard"></i>
                <span class="menu-text"> 风险大盘 </span>

                <b class="arrow icon-angle-down"></b>
            </a>

            <ul class="submenu">
                <li>
                    <a href="/">
                        <i class="icon-double-angle-right"></i>
                        风险趋势
                    </a>
                </li>

                <li>
                    <a href="/">
                        <i class="icon-double-angle-right"></i>
                        风险指标
                    </a>
                </li>
            </ul>
        </li>

        <li>
            <a href="#" class="dropdown-toggle">
                <i class="icon-list"></i>
                <span class="menu-text"> 名单管理 </span>

                <b class="arrow icon-angle-down"></b>
            </a>

            <ul class="submenu">
                <li>
                    <a href="namelist/query">
                        <i class="icon-double-angle-right"></i>
                        黑白灰名单管理
                    </a>
                </li>

               <!--   <li>
                    <a href="userDevice/query">
                        <i class="icon-double-angle-right"></i>
                        账户设备映射
                    </a>
                </li>-->
                <!--  <li>
                    <a href="userDevice/couponUseRecord">
                        <i class="icon-double-angle-right"></i>
                        设备使用新客券查询
                    </a>
                </li>-->
            </ul>
        </li>

        <li>
            <a href="#" class="dropdown-toggle">
                <i class="icon-text-width"></i>
                <span class="menu-text">规则引擎服务</span>

                <b class="arrow icon-angle-down"></b>
            </a>

            <ul class="submenu">

                <li>
                    <a href="event/query">
                        <i class="icon-double-angle-right"></i>
                        调用管理
                    </a>
                </li>
                <li>
                    <a href="policy/query">
                        <i class="icon-double-angle-right"></i>
                        策略管理
                    </a>
                </li>
                <li>
                    <a href="eventType/query">
                        <i class="icon-double-angle-right"></i>
                        事件类型
                    </a>
                </li>
                <li>
                    <a href="riskTerm/query">
                        <i class="icon-double-angle-right"></i>
                        危险词管理
                    </a>
                </li>

                <li>
                    <a href="collectParamConfig/query">
                        <i class="icon-double-angle-right"></i>
                        采集参数管理
                    </a>
                </li>

                <li>
                    <a href="listData/query">
                        <i class="icon-double-angle-right"></i>
                        列表数据
                    </a>
                </li>
                <li>
                    <a href="actionScript/query">
                        <i class="icon-double-angle-right"></i>
                        Action脚本
                    </a>
                </li>
            </ul>
        </li>


     <!--     <li>
            <a href="#" class="dropdown-toggle">
                <i class="icon-list-alt"></i>
                <span class="menu-text"> 开关管理 </span>

                <b class="arrow icon-angle-down"></b>
            </a>

            <ul class="submenu">
                <li>
                    <a href="switch/query">
                        <i class="icon-double-angle-right"></i>
                        风控开关
                    </a>
                </li>
            </ul>
        </li>-->

    </ul><!-- /.nav-list -->

    <div class="sidebar-collapse" id="sidebar-collapse">
        <i class="icon-double-angle-left" data-icon1="icon-double-angle-left" data-icon2="icon-double-angle-right"></i>
    </div>

    <script type="text/javascript">
        try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
    </script>
</div>
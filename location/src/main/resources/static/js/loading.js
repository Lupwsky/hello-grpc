// 动画加载依赖Spin.js插件
var Loading = new LoadingMethod();

function LoadingMethod() {
    var show = false;

    this.startLoading = startLoading;
    this.stopLoading = stopLoading;
    this.isShow = isShow;

    // 开始加载动画, 基于Spin.js插件
    function startLoading(msg, sec) {
        // 标记动画为显示状态
        show = true;

        // 1. 创建遮罩层, 遮罩层应该覆盖整个body并且禁止滚动条
        var shadowDiv = '<div id="shadow" style="position:absolute; z-index:10000; top:0; left:0; background:rgba(0, 0, 0, 0.11); width:100%; height:100%"></div>';
        $(document.body).prepend(shadowDiv);
        // 禁止滚动条，IE8及以上
        $(document.body).css({
            "overflow-x":"hidden",
            "overflow-y":"hidden"
        });

        // 2. 创建对话框
        if (msg === null || msg === "") msg = "加载数据中，请稍等...";
        var loadingDiv =
            '<div id="loading" style="width:100%; height:100%; position:relative">' +
                '<div style="width: 200px; height: 100px; background: rgba(250, 198, 0, 0.61); border-radius:7px; position: absolute; top: 0; bottom: 0; left: 0; right: 0; margin: auto;">' +
                '<div id="spin" style="display:inline-block"></div>' +
                '</div>' +
            '</div>';
        $("#shadow").append(loadingDiv);

        // 3. 创建spin并启动动画
        var opts = {
            lines: 10, // 花瓣数目
            length: 9, // 花瓣长度
            width: 4, // 花瓣宽度
            radius: 10, // 花瓣距中心半径
            corners: 1, // 花瓣圆滑度 (0-1)
            rotate: 0, // 花瓣旋转角度
            direction: 1, // 花瓣旋转方向 1: 顺时针, -1: 逆时针
            color: '#FFFFFF', // 花瓣颜色
            speed: 1, // 花瓣旋转速度
            trail: 100, // 花瓣旋转时的拖影(百分比)
            shadow: false, // 花瓣是否显示阴影
            hwaccel: false, //spinner 是否启用硬件加速及高速旋转
            className: 'spinner', // spinner css 样式名称
            zIndex: 2e9, // spinner的z轴 (默认是2000000000)
            top: '50', // spinner 相对父容器Top定位 单位 px
            right: '0'// spinner 相对父容器Left定位 单位 px
        };
        var target = document.getElementById("spin");
        var spinner = new Spinner(opts);
        spinner.spin(target);
        if (sec !== null && sec !== 0) setTimeout(stopLoading, sec * 1000);
        return spinner;
    }

    // 停止加载动画
    function stopLoading() {
        // 标记动画为隐藏状态
        show = false;
        // 移除动画记载布局
        $("#shadow").remove();
        // 允许滚动条滚动
        $(document.body).css({
            "overflow-x":"auto",
            "overflow-y":"auto"
        });
    }

    // 返回当前动画的显示状态
    function isShow() {
        return show;
    }
}
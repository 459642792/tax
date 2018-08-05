/************************************************
 * 加载遮罩层组件
 * 使用方法
 * var loading = new formLoading(options)
 *  options参数说明：
 *      toolType:(String)[可选] 遮罩层类型,有两种选项："progress"进度条形式,"loading"无进度条形式    默认："loading"
 *      zIndex:(Number)[可选] 遮罩层的z-index属性 0 - 65536的一个值    默认：9998
 *      total:(Number)[可选] 仅在toolType为"progress"有效，初始化进度条最大的进度值
 *      onShow:(Function)[可选] 在调用show方法时触发，如果该方法返回Boolean类型的false，则会取消show方法的操作
 *      onHide:(Function)[可选] 在调用hide方法时触发，如果该方法返回Boolean类型的false，则会取消hide方法的操作
 *
 *  formLoading对象函数说明：
 *      show(toolText) 显示遮罩层
 *          参数说明：
 *              toolText:(String)[可选] 显示后在进度条下显示的文本 默认"请耐心等待..."
 *      hide() 隐藏遮罩层
 *          参数说明：无
 *      setProgress(now, total) 设置进度(仅在toolType = "progress"有效)
 *          参数说明：
 *              now:(Number)[必须] 当前的进度值（一个小于total的值，将通过此值来计算百分比，如果该值大于或等于total值，则将视为100%）
 *              total:(Number)[可选] 最大的进度值 默认值：options参数的total属性
 *      setToolText(toolText) 设置进度条下显示的文本
 *          参数说明：
 *              toolText:(String)[可选] 进度条下显示的文本值    默认：如果未传入该参数或该参数为null，则显示"请耐心等待..."
 ************************************************/
var formLoading = function (opt) {
    var sf = this;
    var options = {
        timeout: 120,
        onShow: function () {
        },
        onHide: function () {
        },
        onTimeout: function () {
        },
        zIndex: 9998,
        toolType: "loading",
        total: 100
    };
    var lElement = {
        maskBody: null,
        mask: null,
        bar: null,
        barIn: null,
        offsetParent: null,
        toolText: null,
        target: $("body")
    };

    var elementStr = {
        body: '<div style="position:fixed;top:0px;left:0px;right:0px;bottom:0px;">' +
        '<div class="modal-backdrop fade" style="z-index:inherit;"></div>' +
        '<div class="position-relative" style="width:50%; margin:0px auto;padding-top:8%;position:relative; z-index:inherit;">' +
        '<div class="position-relative" style="overflow:hidden;z-index:inherit;color:#fff;text-align:center;"></div>' +
        '</div>' +
        '</div>',
        progress: '<div class="progress progress-striped active">' +
        '<div class="progress-bar bg-color-pink" role="progressbar" style="width: 0%"></div>' +
        '</div>',

    };

    var config = {
        //是否已显示
        isShow: false,
        //是否是进度条
        isProgress: false
    }

    var handler = {
        render: function (prg) {
            lElement.mask = $(elementStr.body);
            lElement.target.append(lElement.mask);
            lElement.mask.css("z-index", options.zIndex);
            lElement.bar = $(elementStr.progress);
            lElement.bar.css("z-index", options.zIndex);
            lElement.mask.children(".position-relative").prepend(lElement.bar);
            lElement.barIn = lElement.bar.children();
            if (!config.isProgress) {
                lElement.barIn.css("width", "100%");
                lElement.bar.removeAttr("data-percent");
            }
            lElement.toolText = lElement.mask.children(".position-relative").children(".position-relative");
            lElement.maskBody = lElement.mask.children(".modal-backdrop");
            setTimeout(function () {
                lElement.maskBody.addClass("in");
            }, 0);
        },
        calcPercent: function (now, total) {
            if (typeof (total) != 'number') {
                total = options.total;
            }
            else {
                options.total = total;
            }
            if (now < total) {
                return Math.round((now / total) * 1000) / 10;
            }
            return 100;
        }
    };

    //显示
    sf.show = function (toolText) {
        if (config.isShow) {
            return;
        }
        if (options.onShow() === false) {
            return;
        }
        handler.render();
        if (!toolText) {
            toolText = '请耐心等待...';
        }
        lElement.toolText.text(toolText);
        config.isShow = true;
    };
    //隐藏
    sf.hide = function () {
        if (!config.isShow) {
            return;
        }
        if (options.onHide() === false) {
            return;
        }
        lElement.maskBody.fadeTo(200, 0, function () {
            lElement.mask.remove();
            lElement.maskBody = null;
            lElement.mask = null;
            lElement.bar = null;
            lElement.barIn = null;
            lElement.offsetParent = null;
            lElement.toolText = null;
        });
        config.isShow = false;
    };
    //设置进度(仅在进度条情况下才可使用)
    sf.setProgress = function (now, total) {
        if (!config.isShow || !config.isProgress) {
            return;
        }
        if (typeof (now) != 'number' || typeof (total) != 'number' || total <= 0 || now <= 0) {
            return;
        }
        var p = handler.calcPercent(now, total).toString() + "%";
        lElement.bar.attr("data-percent", p);
        lElement.barIn.css("width", p);
    };
    sf.setToolText = function (toolText) {
        lElement.toolText.text(typeof (toolText) != 'undefined' && toolText != null ? toolText : "请耐心等待...");
    };

    /*构造函数 开始*/
    if (opt) {
        if (opt.toolType && opt.toolType === 'progress') {
            if (opt.toolType === 'progress') {
                config.isProgress = true;
            }
        }

        if (opt.zIndex && typeof (opt.zIndex) === 'number' && opt.zIndex >= 0 && opt.zIndex < 65536) {
            options.zIndex = opt.zIndex;
        }

        if (opt.total && typeof (opt.total) === 'number' && opt.total > 0) {
            options.total = opt.total;
        }

        if ($.isFunction(opt.onShow)) {
            options.onShow = opt.onShow;
        }
        if ($.isFunction(opt.onHide)) {
            options.onHide = opt.onHide;
        }
        if ($.isFunction(opt.onTimeout)) {
            options.onTimeout = opt.onTimeout;
        }
    }

    /*构造函数 结束*/
}
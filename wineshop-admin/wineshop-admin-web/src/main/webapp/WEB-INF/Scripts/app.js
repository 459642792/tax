(function () {
    var sechemeName = "附近酒行";
    $("a.nav-wechat").each(function () {
        var $this = $(this);
        if ($this.data("wechat_isProc"))
            return;
        $this.data("wechat_isProc", true);
        var href = $this.attr("href");
        if (href == "#")
            return;
        var tit = $this.attr("title");
        if (tit) {
            tit += " - " + sechemeName;
        }
        else {
            tit = sechemeName;
        }
        $this.attr("title", tit);
        var hArr = href.split("#");
        href = hArr[0];
        if (href.indexOf("?") >= 0) {
            href += "&id=" + window.wechat36Id;
        }
        /* else {
             if (href[href.length - 1] != '/')
                 href += '/';
             href += window.wechat36Id;
         }
         if (hArr.length > 1)
             href += "#" + hArr[1];*/
        $this.attr("href", href);
    });
})();

function runAllForms() {
    $.fn.slider && $(".slider").slider(),
    $.fn.select2 && $(".select2").each(function () {
        var a = $(this),
            b = a.attr("data-select-width") || "100%";
        a.select2({
            allowClear: !0,
            width: b
        }),
            a = null
    }),
    $.fn.mask && $("[data-mask]").each(function () {
        var a = $(this), b = a.attr("data-mask") || "error...", c = a.attr("data-mask-placeholder") || "X";
        a.mask(b, {
            placeholder: c
        }), a = null
    }),
    $.fn.autocomplete && $("[data-autocomplete]").each(function () {
        var a = $(this),
            b = a.data("autocomplete") || ["The", "Quick", "Brown", "Fox", "Jumps", "Over", "Three", "Lazy", "Dogs"];
        a.autocomplete({
            source: b
        }),
            a = null
    }),
    $.fn.datepicker && $(".datepicker").each(function () {
        var a = $(this), b = a.attr("data-dateformat") || "dd.mm.yy";
        a.datepicker({
            dateFormat: b,
            prevText: '<i class="fa fa-chevron-left"></i>',
            nextText: '<i class="fa fa-chevron-right"></i>'
        }), a = null
    }),
        $("button[data-loading-text]").on("click", function () {
            var a = $(this);
            a.button("loading"),
                setTimeout(function () {
                    a.button("reset")
                }, 3e3), a = null
        })
}

function runAllCharts() {
    if ($.fn.sparkline) {
        var a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, A, B, C, D, E, F, G, H, I, J,
            K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z, _, ab, bb, cb, db, eb, fb, gb, hb, ib, jb, kb, lb, mb, nb,
            ob, pb, qb, rb, sb;
        $(".sparkline").each(function () {
            var tb = $(this),
                ub = tb.data("sparkline-type") || "bar";
            if ("bar" == ub) {
                a = tb.data("sparkline-bar-color") || tb.css("color") || "#0000f0",
                    b = tb.data("sparkline-height") || "26px", c = tb.data("sparkline-barwidth") || 5,
                    d = tb.data("sparkline-barspacing") || 2,
                    e = tb.data("sparkline-negbar-color") || "#A90329",
                    f = tb.data("sparkline-barstacked-color") || ["#A90329", "#0099c6", "#98AA56", "#da532c", "#4490B1", "#6E9461", "#990099", "#B4CAD3"],
                    tb.sparkline("html", {
                        barColor: a,
                        type: ub,
                        height: b,
                        barWidth: c,
                        barSpacing: d,
                        stackedBarColor: f,
                        negBarColor: e,
                        zeroAxis: "false"
                    }),
                    tb = null
            }
            if ("line" == ub) {
                b = tb.data("sparkline-height") || "20px",
                    ab = tb.data("sparkline-width") || "90px",
                    g = tb.data("sparkline-line-color") || tb.css("color") || "#0000f0",
                    h = tb.data("sparkline-line-width") || 1, i = tb.data("fill-color") || "#c0d0f0",
                    j = tb.data("sparkline-spot-color") || "#f08000", k = tb.data("sparkline-minspot-color") || "#ed1c24",
                    l = tb.data("sparkline-maxspot-color") || "#f08000",
                    m = tb.data("sparkline-highlightspot-color") || "#50f050",
                    n = tb.data("sparkline-highlightline-color") || "f02020",
                    o = tb.data("sparkline-spotradius") || 1.5,
                    thisChartMinYRange = tb.data("sparkline-min-y") || "undefined",
                    thisChartMaxYRange = tb.data("sparkline-max-y") || "undefined",
                    thisChartMinXRange = tb.data("sparkline-min-x") || "undefined",
                    thisChartMaxXRange = tb.data("sparkline-max-x") || "undefined",
                    thisMinNormValue = tb.data("min-val") || "undefined",
                    thisMaxNormValue = tb.data("max-val") || "undefined",
                    thisNormColor = tb.data("norm-color") || "#c0c0c0",
                    thisDrawNormalOnTop = tb.data("draw-normal") || !1,
                    tb.sparkline("html", {
                        type: "line",
                        width: ab,
                        height: b,
                        lineWidth: h,
                        lineColor: g,
                        fillColor: i,
                        spotColor: j,
                        minSpotColor: k,
                        maxSpotColor: l,
                        highlightSpotColor: m,
                        highlightLineColor: n,
                        spotRadius: o,
                        chartRangeMin: thisChartMinYRange,
                        chartRangeMax: thisChartMaxYRange,
                        chartRangeMinX: thisChartMinXRange,
                        chartRangeMaxX: thisChartMaxXRange,
                        normalRangeMin: thisMinNormValue,
                        normalRangeMax: thisMaxNormValue,
                        normalRangeColor: thisNormColor,
                        drawNormalOnTop: thisDrawNormalOnTop
                    }),
                    tb = null
            }
            if ("pie" == ub) {
                p = tb.data("sparkline-piecolor") || ["#B4CAD3", "#4490B1", "#98AA56", "#da532c", "#6E9461", "#0099c6", "#990099", "#717D8A"],
                    q = tb.data("sparkline-piesize") || 90,
                    r = tb.data("border-color") || "#45494C",
                    s = tb.data("sparkline-offset") || 0,
                    tb.sparkline("html", {
                        type: "pie",
                        width: q,
                        height: q,
                        tooltipFormat: '<span style="color: {{color}}">&#9679;</span> ({{percent.1}}%)',
                        sliceColors: p,
                        borderWidth: 1,
                        offset: s,
                        borderColor: r
                    }),
                    tb = null
            }
            if ("box" == ub) {
                t = tb.data("sparkline-width") || "auto",
                    u = tb.data("sparkline-height") || "auto",
                    v = tb.data("sparkline-boxraw") || !1,
                    w = tb.data("sparkline-targetval") || "undefined",
                    x = tb.data("sparkline-min") || "undefined",
                    y = tb.data("sparkline-max") || "undefined",
                    z = tb.data("sparkline-showoutlier") || !0,
                    A = tb.data("sparkline-outlier-iqr") || 1.5,
                    B = tb.data("sparkline-spotradius") || 1.5,
                    C = tb.css("color") || "#000000",
                    D = tb.data("fill-color") || "#c0d0f0",
                    E = tb.data("sparkline-whis-color") || "#000000",
                    F = tb.data("sparkline-outline-color") || "#303030",
                    G = tb.data("sparkline-outlinefill-color") || "#f0f0f0",
                    H = tb.data("sparkline-outlinemedian-color") || "#f00000",
                    I = tb.data("sparkline-outlinetarget-color") || "#40a020",
                    tb.sparkline("html", {
                        type: "box",
                        width: t,
                        height: u,
                        raw: v,
                        target: w,
                        minValue: x,
                        maxValue: y,
                        showOutliers: z,
                        outlierIQR: A,
                        spotRadius: B,
                        boxLineColor: C,
                        boxFillColor: D,
                        whiskerColor: E,
                        outlierLineColor: F,
                        outlierFillColor: G,
                        medianColor: H,
                        targetColor: I
                    }),
                    tb = null
            }
            if ("bullet" == ub) {
                var vb = tb.data("sparkline-height") || "auto";
                J = tb.data("sparkline-width") || 2,
                    K = tb.data("sparkline-bullet-color") || "#ed1c24",
                    L = tb.data("sparkline-performance-color") || "#3030f0",
                    M = tb.data("sparkline-bulletrange-color") || ["#d3dafe", "#a8b6ff", "#7f94ff"],
                    tb.sparkline("html", {
                        type: "bullet",
                        height: vb,
                        targetWidth: J,
                        targetColor: K,
                        performanceColor: L,
                        rangeColors: M
                    }),
                    tb = null
            }
            if ("discrete" == ub) {
                N = tb.data("sparkline-height") || 26,
                    O = tb.data("sparkline-width") || 50,
                    P = tb.css("color"),
                    Q = tb.data("sparkline-line-height") || 5,
                    R = tb.data("sparkline-threshold") || "undefined",
                    S = tb.data("sparkline-threshold-color") || "#ed1c24",
                    tb.sparkline("html", {
                        type: "discrete",
                        width: O, height: N,
                        lineColor: P,
                        lineHeight: Q,
                        thresholdValue: R,
                        thresholdColor: S
                    }),
                    tb = null
            }
            if ("tristate" == ub) {
                PT = tb.data("sparkline-height") || 26,
                    U = tb.data("sparkline-posbar-color") || "#60f060",
                    V = tb.data("sparkline-negbar-color") || "#f04040",
                    W = tb.data("sparkline-zerobar-color") || "#909090",
                    X = tb.data("sparkline-barwidth") || 5,
                    Y = tb.data("sparkline-barspacing") || 2,
                    Z = tb.data("sparkline-zeroaxis") || !1,
                    tb.sparkline("html", {
                        type: "tristate",
                        height: T,
                        posBarColor: _,
                        negBarColor: V,
                        zeroBarColor: W,
                        barWidth: X,
                        barSpacing: Y,
                        zeroAxis: Z
                    }),
                    tb = null
            }
            if ("compositebar" == ub) {
                b = tb.data("sparkline-height") || "20px",
                    ab = tb.data("sparkline-width") || "100%",
                    c = tb.data("sparkline-barwidth") || 3,
                    h = tb.data("sparkline-line-width") || 1,
                    g = tb.data("sparkline-color-top") || "#ed1c24",
                    _ = tb.data("sparkline-color-bottom") || "#333333",
                    tb.sparkline(tb.data("sparkline-bar-val"), {
                        type: "bar",
                        width: ab,
                        height: b,
                        barColor: _,
                        barWidth: c
                    }),
                    tb.sparkline(tb.data("sparkline-line-val"), {
                        width: ab,
                        height: b,
                        lineColor: g,
                        lineWidth: h,
                        composite: !0,
                        fillColor: !1
                    }),
                    tb = null
            }
            if ("compositeline" == ub) {
                b = tb.data("sparkline-height") || "20px",
                    ab = tb.data("sparkline-width") || "90px",
                    bb = tb.data("sparkline-bar-val"),
                    cb = tb.data("sparkline-bar-val-spots-top") || null,
                    db = tb.data("sparkline-bar-val-spots-bottom") || null,
                    eb = tb.data("sparkline-line-width-top") || 1,
                    fb = tb.data("sparkline-line-width-bottom") || 1,
                    gb = tb.data("sparkline-color-top") || "#333333",
                    hb = tb.data("sparkline-color-bottom") || "#ed1c24",
                    ib = tb.data("sparkline-spotradius-top") || 1.5,
                    jb = tb.data("sparkline-spotradius-bottom") || ib,
                    j = tb.data("sparkline-spot-color") || "#f08000",
                    kb = tb.data("sparkline-minspot-color-top") || "#ed1c24",
                    lb = tb.data("sparkline-maxspot-color-top") || "#f08000",
                    mb = tb.data("sparkline-minspot-color-bottom") || kb,
                    nb = tb.data("sparkline-maxspot-color-bottom") || lb,
                    ob = tb.data("sparkline-highlightspot-color-top") || "#50f050",
                    pb = tb.data("sparkline-highlightline-color-top") || "#f02020",
                    qb = tb.data("sparkline-highlightspot-color-bottom") || ob,
                    thisHighlightLineColor2 = tb.data("sparkline-highlightline-color-bottom") || pb,
                    rb = tb.data("sparkline-fillcolor-top") || "transparent",
                    sb = tb.data("sparkline-fillcolor-bottom") || "transparent",
                    tb.sparkline(bb, {
                        type: "line",
                        spotRadius: ib,
                        spotColor: j,
                        minSpotColor: kb,
                        maxSpotColor: lb,
                        highlightSpotColor: ob,
                        highlightLineColor: pb,
                        valueSpots: cb,
                        lineWidth: eb,
                        width: ab,
                        height: b,
                        lineColor: gb,
                        fillColor: rb
                    }),
                    tb.sparkline(tb.data("sparkline-line-val"), {
                        type: "line",
                        spotRadius: jb,
                        spotColor: j,
                        minSpotColor: mb,
                        maxSpotColor: nb,
                        highlightSpotColor: qb,
                        highlightLineColor: thisHighlightLineColor2,
                        valueSpots: db,
                        lineWidth: fb,
                        width: ab,
                        height: b,
                        lineColor: hb,
                        composite: !0,
                        fillColor: sb
                    }),
                    tb = null
            }
        })
    }
    $.fn.easyPieChart && $(".easy-pie-chart").each(function () {
        var a = $(this), b = a.css("color") || a.data("pie-color"), c = a.data("pie-track-color") || "#eeeeee",
            d = parseInt(a.data("pie-size")) || 25;
        a.easyPieChart({
            barColor: b,
            trackColor: c,
            scaleColor: !1,
            lineCap: "butt",
            lineWidth: parseInt(d / 8.5),
            animate: 1500,
            rotate: -90,
            size: d,
            onStep: function (a) {
                this.$el.find("span").text(~~a)
            }
        }), a = null
    })
}

function setup_widgets_desktop() {
    if ($.fn.jarvisWidgets && enableJarvisWidgets) {
        $("#widget-grid").jarvisWidgets({
            grid: "article",
            widgets: ".jarviswidget",
            localStorage: !0,
            deleteSettingsKey: "#deletesettingskey-options",
            settingsKeyLabel: "Reset settings?",
            deletePositionKey: "#deletepositionkey-options",
            positionKeyLabel: "Reset position?",
            sortable: !0,
            buttonsHidden: !1,
            toggleButton: !0,
            toggleClass: "fa fa-minus | fa fa-plus",
            toggleSpeed: 200,
            onToggle: function () {
            },
            deleteButton: !0,
            deleteClass: "fa fa-times",
            deleteSpeed: 200,
            onDelete: function () {
            },
            editButton: !0,
            editPlaceholder: ".jarviswidget-editbox",
            editClass: "fa fa-cog | fa fa-save",
            editSpeed: 200,
            onEdit: function () {
            },
            colorButton: !0,
            fullscreenButton: !0,
            fullscreenClass: "fa fa-expand | fa fa-compress",
            fullscreenDiff: 3,
            onFullscreen: function () {
            },
            customButton: !1,
            customClass: "folder-10 | next-10",
            customStart: function () {
                alert("Hello you, this is a custom button...")
            },
            customEnd: function () {
                alert("bye, till next time...")
            },
            buttonOrder: "%refresh% %custom% %edit% %toggle% %fullscreen% %delete%",
            opacity: 1,
            dragHandle: "> header",
            placeholderClass: "jarviswidget-placeholder",
            indicator: !0,
            indicatorTime: 600,
            ajax: !0,
            timestampPlaceholder: ".jarviswidget-timestamp",
            timestampFormat: "Last update: %m%/%d%/%y% %h%:%i%:%s%",
            refreshButton: !0,
            refreshButtonClass: "fa fa-refresh",
            labelError: "Sorry but there was a error:",
            labelUpdated: "Last Update:",
            labelRefresh: "Refresh",
            labelDelete: "Delete widget:",
            afterLoad: function () {
            },
            rtl: !1,
            onChange: function () {
            },
            onSave: function () {
            },
            ajaxnav: $.navAsAjax
        });
    }
}

function setup_widgets_mobile() {
    if (enableMobileWidgets && enableJarvisWidgets)
        setup_widgets_desktop();
}

function loadExtendJs(fun) {
    loadScript("Scripts/vue.min.js", function () {
        loadScript("Scripts/extend/CommonBase.min.js", function () {
            loadScript("Scripts/extend/CommonBase.min.js", function () {
                loadScript("Scripts/extend/VueExtend.min.js", function () {
                    loadScript("Scripts/extend/ListModel.min.js", function () {
                        loadScript("Scripts/extend/EditModel.min.js", function () {
                            fun();
                        });
                    });
                });
            });
        });
    });
}

function loadScript(a, b) {
    if (jsArray[a]) {
        if (b) {
            b();
        }
    }
    else {
        jsArray[a] = !0;
        var c = document.getElementsByTagName("body")[0],
            d = document.createElement("script");
        d.type = "text/javascript",
            d.src = a,
            d.onload = b,
            c.appendChild(d)
    }
}

function checkURL() {
    var a = location.href.split("#").splice(1).join("#");
    if (!a)
        try {
            var b = window.document.URL;
            if (b && b.indexOf("#", 0) > 0 && b.indexOf("#", 0) < b.length + 1) {
                a = b.substring(b.indexOf("#", 0) + 1);
            }
        }
        catch (c) {
        }
    container = $("#content")
    if (a) {
        $("nav li.active").removeClass("active"),
            $('nav li:has(a[href="' + a + '"])').addClass("active");
        var d = $.trim($('nav a[href="' + a + '"]').attr("title")) || $.trim($('nav a[href="' + a + '"]').text());
        document.title = d || document.title,
            loadURL(a + location.search, container, 1)
    }
    else {
        var e = $('nav > ul > li:first-child > a[href!="#"]');
        if (e.length)
            window.location.hash = e.attr("href");
        e = null
    }
}

function getNews(a, b) {
    var c = $("#indexUrl").attr("data-url");
    var d = $("#indexUrl").attr("data-base-url");
    console.log(d);
    $.ajax({
        type: "GET",
        url: d + a,
        dataType: "json",
        beforeSend: function (xhr) {
            xhr.setRequestHeader('access_token', $.cookie('access_token'));
        },//这里设置header
        success: function (a) {
            if (a.success) {
                var allUrl = location.href.split("#")[0] + '#' + c;
                var str = '<ul class="newsList">';
                for (var i = 0; i < a.data.length; i++) {
                    var getHour = new Date(a.data[i].sendingTime).getHours();
                    var getMinutes = new Date(a.data[i].sendingTime).getMinutes();
                    var time = getHour + ":" + getMinutes
                    switch (a.data[i].typeCode) {
                        case 'submit_discovered':
                            str += '<li><a data-toggle="modal" data-target="#remoteModal" data-backdrop="static" href="' + c + '/discover/AddOrEdit?Id=' + a.data[i].dataKey + '" onclick="readMessage(this,' + a.data[i].id + ')"><p><span class="newsTitle">' + a.data[i].title + '</span><span class="f_r">' + time + '</span></p><p>' + a.data[i].content + '</p></a></li>';
                            break;
                        case 'submit_vendor_enter':
                            str += '<li><a href="' + allUrl + '/user/vendorList" onclick="readMessage(this,' + a.data[i].id + ')"><p><span  class="newsTitle">' + a.data[i].title + '</span><span class="f_r">' + time + '</span></p><p>' + a.data[i].content + '</p></a></li>';
                            break;
                        case  'submit_vendor_authenticate':
                            str += '<li><a data-toggle="modal" data-target="#remoteModal" data-backdrop="static" href="' + c + '/vendor/auth?id=' + a.data[i].dataKey + '" onclick="readMessage(this,' + a.data[i].id + ')"><p><span  class="newsTitle">' + a.data[i].title + '</span><span class="f_r">' + time + '</span></p><p>' + a.data[i].content + '</p></a></li>';
                            break;
                        case 'wine_exchange':
                            str += '<li><a href="' + allUrl + '/goodsCashRecord/pageGoodsCashRecord" onclick="readMessage(this,' + a.data[i].id + ')"><p><span  class="newsTitle">' + a.data[i].title + '</span><span class="f_r">' + time + '</span></p><p>' + a.data[i].content + '</p></a></li>';
                            break;
                        default :
                            break;
                    }
                }
                str += "</ul>"
                b.append(str);
                $("#activityLoadTime").text("加载时间:" + (new Date()).bindToString("yyyy-MM-dd HH:mm:ss"));
            }
        },
        error: function (xhr) {
            if (xhr.status == 500) {
                b.html(xhr.responseText);
            }
            else {
                b.html('<h4 class="ajax-loading-error"><i class="fa fa-warning txt-color-orangeDark"></i> Error ' + xhr.status + '! Page ' + xhr.statusText + '.</h4>')
            }
        },
        async: !0
    })
}

function readMessage(that, id) {
    if (id) {
        var d = $("#indexUrl").attr("data-base-url")
        $.ajax({
            url: d + "/api/message/readMessageByID",
            type: "Post",
            data: {messageId: id},
            beforeSend: function (xhr) {
                xhr.setRequestHeader('access_token', $.cookie('access_token'));
            },//这里设置header
            success: function (data) {
                console.log(data);
                if (data.success) {
                    $(that).parent().remove();
                    $("#newsCount").text(parseInt($("#newsCount").text()) - 1);
                    $("#newsBadge").text(parseInt($("#newsBadge").text()) - 1);
                }
            }
        })
    }
}

function loadURL(a, b, s) {
    $.ajax({
        type: "GET",
        url: a,
        dataType: "html",
        cache: !0,
        beforeSend: function () {
            if ($.navAsAjax && $(".google_maps")[0] && b[0] == $("#content")[0]) {
                var a = $(".google_maps"),
                    c = 0;
                a.each(function () {
                    c++;
                    var b = document.getElementById(this.id);
                    c == a.length + 1 || b && b.parentNode.removeChild(b)
                })
            }
            if ($.navAsAjax && $(".dataTables_wrapper")[0] && b[0] == $("#content")[0]) {
                var d = $.fn.dataTable.fnTables(!0);
                $(d).each(function () {
                    $(this).dataTable().fnDestroy()
                })
            }
            if ($.navAsAjax && $.intervalArr.length > 0 && b[0] == $("#content")[0] && enableJarvisWidgets)
                while ($.intervalArr.length > 0)
                    clearInterval($.intervalArr.pop());
            pagefunction = null,
                b.removeData().html(""),
                b.html('<h1 class="ajax-loading-animation"><div class="url-loading"><i></i><i></i><i></i><i></i><i></i><i></i></div> 正在加载, 请稍候 ...</h1>');
            if (b[0] == $("#content")[0]) {
                $("body").find("> *").filter(":not(" + ignore_key_elms + ")").empty().remove();
                drawBreadCrumb(s);
                $("html").animate({
                    scrollTop: 0
                }, "fast")
            }
        },
        success: function (a) {
            b.css({
                opacity: "0.0"
            }).html(a).delay(50).animate({
                opacity: "1.0"
            }, 300);
            a = null, b = null;
            $("#activityLoadTime").text("加载时间:" + (new Date()).bindToString("yyyy-MM-dd HH:mm:ss"));
        },
        error: function (xhr) {
            if (xhr.status == 500) {
                b.html(xhr.responseText);
            }
            else {
                b.html('<h4 class="ajax-loading-error"><i class="fa fa-warning txt-color-orangeDark"></i> Error ' + xhr.status + '! Page ' + xhr.statusText + '.</h4>')
            }
        },
        async: !0
    })
}

function drawBreadCrumb(s) {
    var a = $("nav li.active > a"),
        b = a.length;
    bread_crumb.empty(),
        bread_crumb.append($("<li>商家后台</li>")),
        a.each(function () {
            bread_crumb.append($("<li></li>").html($.trim($(this).clone().children(".badge").remove().end().text()))),
            --b || s || (document.title = bread_crumb.find("li:last-child").text())
        });
    a = null
}

//清除弹出窗口内容
function destoryModalDialog() {
    $("div[role='dialog']").on("hidden.bs.modal", function () {
        if ($(this).attr('class') == "modal fade") {
            $(this).removeData("bs.modal");
            if ($("div[role='dialog'] .summernote").length > 0) {
                $("div[role='dialog'] .summernote").destroy();
            }
        }
    });
}

function pageSetUp() {
    if ("desktop" === thisDevice) {
        $("[rel=tooltip]").tooltip(),
            $("[rel=popover]").popover(),
            $("[rel=popover-hover]").popover({
                trigger: "hover"
            }),
            setup_widgets_desktop(),
            runAllCharts(),
            runAllForms()
    } else {
        ($("[rel=popover]").popover(),
            $("[rel=popover-hover]").popover({
                trigger: "hover"
            }),
            runAllCharts(),
            setup_widgets_mobile(),
            runAllForms())
    }
    destoryModalDialog();
    //chosen - improves select
    $('[data-rel="chosen"],[rel="chosen"]').each(function () {
        var $this = $(this);
        $this.chosen({search_contains: true, width: $this.css("width")})
    });
    //处理jarviswidget loading按钮
    $(".jarviswidget-loader").each(function (i) {
        $(this).appendTo($(this).siblings("div.sb"));
    })
}

$.root_ = $("body"),
    $.intervalArr = [];
var calc_navbar_height = function () {
        var a = null;
        if ($("#header").length) {
            a = $("#header").height()
        }
        if (null === a) {
            a = $('<div id="header"></div>').height();
        }
        return null === a ? 49 : a
    },
    navbar_height = calc_navbar_height,
    shortcut_dropdown = $("#shortcut"),
    bread_crumb = $("#ribbon ol.breadcrumb"),
    topmenu = !1,
    thisDevice = null,
    ismobile = /iphone|ipad|ipod|android|blackberry|mini|windows\sce|palm/i.test(navigator.userAgent.toLowerCase()),
    jsArray = {},
    initApp = function (a) {
        a.addDeviceType = function () {
            return ismobile ? ($.root_.addClass("mobile-detected"), thisDevice = "mobile", fastClick ? ($.root_.addClass("needsclick"), FastClick.attach(document.body), !1) : void 0) : ($.root_.addClass("desktop-detected"), thisDevice = "desktop", !1)
        };
        a.menuPos = function () {
            ($.root_.hasClass("menu-on-top") || "top" == localStorage.getItem("sm-setmenu")) && (topmenu = !0, $.root_.addClass("menu-on-top"))
        };
        a.SmartActions = function () {
            var a = {
                userLogout: function (a) {
                    function b() {
                        window.location = a.attr("href")
                    }

                    $.SmartMessageBox({
                        title: "<i class='fa fa-sign-out txt-color-orangeDark'></i> 退出 <span class='txt-color-orangeDark'><strong></strong></span> ?",
                        content: a.data("logout-msg") || "You can improve your security further after logging out by closing this opened browser",
                        buttons: "[取消][退出]"
                    }, function (a) {
                        ;
                        if (a == "退出") {
                            try {
                                appJs.qyLoginOutsideTime();
                            } catch (e) {

                            }
                        }
                        "退出" == a && ($.root_.addClass("animated fadeOutUp"), setTimeout(b, 1e3))
                    });
                },
                resetWidgets: function (a) {
                    $.widresetMSG = a.data("reset-msg");
                    $.SmartMessageBox({
                        title: "<i class='fa fa-refresh' style='color:green'></i> 清空本地缓存",
                        content: $.widresetMSG || "您是否要清除本地所有控件的缓存?",
                        buttons: "[取消][确定]"
                    }, function (a) {
                        "Yes" == a && localStorage && (localStorage.clear(), location.reload())
                    });
                },
                launchFullscreen: function (a) {
                    $.root_.hasClass("full-screen") ? ($.root_.removeClass("full-screen"), document.exitFullscreen ? document.exitFullscreen() : document.mozCancelFullScreen ? document.mozCancelFullScreen() : document.webkitExitFullscreen && document.webkitExitFullscreen()) : ($.root_.addClass("full-screen"), a.requestFullscreen ? a.requestFullscreen() : a.mozRequestFullScreen ? a.mozRequestFullScreen() : a.webkitRequestFullscreen ? a.webkitRequestFullscreen() : a.msRequestFullscreen && a.msRequestFullscreen())
                },
                minifyMenu: function (a) {
                    $.root_.hasClass("menu-on-top") || ($.root_.toggleClass("minified"), $.root_.removeClass("hidden-menu"), $("html").removeClass("hidden-menu-mobile-lock"), a.effect("highlight", {}, 500))
                },
                toggleMenu: function () {
                    $.root_.hasClass("menu-on-top") ? $.root_.hasClass("menu-on-top") && $.root_.hasClass("mobile-view-activated") && ($("html").toggleClass("hidden-menu-mobile-lock"), $.root_.toggleClass("hidden-menu"), $.root_.removeClass("minified")) : ($("html").toggleClass("hidden-menu-mobile-lock"), $.root_.toggleClass("hidden-menu"), $.root_.removeClass("minified"))
                },
                toggleShortcut: function () {
                    function a() {
                        shortcut_dropdown.animate({
                            height: "hide"
                        }, 300, "easeOutCirc");
                        $.root_.removeClass("shortcut-on")
                    }

                    function b() {
                        shortcut_dropdown.animate({
                            height: "show"
                        }, 200, "easeOutCirc");
                        $.root_.addClass("shortcut-on")
                    }

                    shortcut_dropdown.is(":visible") ? a() : b();
                    shortcut_dropdown.find("a").click(function (b) {
                        b.preventDefault(), window.location = $(this).attr("href"), setTimeout(a, 300)
                    });
                    $(document).mouseup(function (b) {
                        shortcut_dropdown.is(b.target) || 0 !== shortcut_dropdown.has(b.target).length || a()
                    });
                }
            };
            $.root_.on("click", '[data-action="userLogout"]', function (b) {
                var c = $(this);
                a.userLogout(c);
                b.preventDefault();
                c = null
            }),
                $.root_.on("click", '[data-action="resetWidgets"]', function (b) {
                    var c = $(this);
                    a.resetWidgets(c), b.preventDefault(), c = null
                }),
                $.root_.on("click", '[data-action="launchFullscreen"]', function (b) {
                    a.launchFullscreen(document.documentElement), b.preventDefault()
                }),
                $.root_.on("click", '[data-action="minifyMenu"]', function (b) {
                    var c = $(this);
                    a.minifyMenu(c), b.preventDefault(), c = null
                }),
                $.root_.on("click", '[data-action="toggleMenu"]', function (b) {
                    a.toggleMenu(), b.preventDefault()
                }),
                $.root_.on("click", '[data-action="toggleShortcut"]', function (b) {
                    a.toggleShortcut(), b.preventDefault()
                })
        };
        a.leftNav = function () {
            topmenu || $("nav ul").jarvismenu({
                accordion: !0,
                speed: menu_speed,
                closedSign: '<img src="Content/Home/image/le11.png" />',
                openedSign: '<img src="Content/Home/image/le12.png" /></em>'
            })
        };
        a.domReadyMisc = function () {
            $("[rel=tooltip]").length && $("[rel=tooltip]").tooltip(), $("#search-mobile").click(function () {
                $.root_.addClass("search-mobile")
            }), $("#cancel-search-js").click(function () {
                $.root_.removeClass("search-mobile")
            }), $("#activity").click(function (a) {
                var b = $(this);
                $(".ajax-dropdown").is(":visible") ? ($(".ajax-dropdown").fadeOut(150), b.removeClass("active")) : ($(".ajax-dropdown").fadeIn(150), b.addClass("active"), $('input[name="activity"]').first().click());
                var c = $(".ajax-dropdown").find(".btn-group > .active > input").attr("id");
                b = null, c = null, a.preventDefault()
            }), $('input[name="activity"]').change(function () {
                var a = $(this);
                url = a.attr("id"), container = $(".ajax-notifications"), getNews(url, container), a = null
            })/*, $(document).mouseup(function (a) {
            $(".ajax-dropdown").is(a.target) || 0 !== $(".ajax-dropdown").has(a.target).length || ($(".ajax-dropdown").fadeOut(150), $(".ajax-dropdown").prev().removeClass("active"))
        })*/, $("button[data-btn-loading]").on("click", function () {
                var a = $(this);
                a.button("loading"), setTimeout(function () {
                        a.button("reset")
                    }
                    , 3e3), $this = null
            }), $this = $("#activity > .badge"), parseInt($this.text()) > 0 && ($this.addClass("bg-color-red bounceIn animated"), $this = null)
        };
        //初始化首页数据
        a.initIndexData = function () {
            $.getJSON("/Home/GetIndexData", null, function (data, status) {
                if (status == "success") {
                    var yxlyCount = data.yxlyCount;
                    var systemMsgCount = data.systemMsgCount;
                    var allCount = yxlyCount + systemMsgCount;

                    if (allCount == 0) {
                        $("#activity").find(".badge").removeClassPrefix("bg-color-").text(0);
                    }
                    else {
                        $("#activity").find(".badge").addClass("bg-color-red").text(allCount);
                    }

                    $('span[data-name="activityText"]').first().text("意向留言 (" + yxlyCount + ")");
                    $('span[data-name="activityText"]').last().text("系统通知 (" + systemMsgCount + ")");

                    if ($.trim(data.factoryName) != "") {
                        $("#homeBannerFactory").text(data.factoryName);
                    }
                }
            });
        }
        return a;
    }({});
initApp.addDeviceType(),
    initApp.menuPos(),
    jQuery(document).ready(function () {
        initApp.SmartActions(),
            initApp.leftNav(),
            initApp.domReadyMisc()
        /*initApp.initIndexData()*/
    }),
    function (a, b, c) {
        function d() {
            e = b[h](function () {
                f.each(function () {
                    var b,
                        c,
                        d = a(this),
                        e = a.data(this, j);
                    try {
                        b = d.width()
                    }
                    catch (f) {
                        b = d.width
                    }
                    try {
                        c = d.height()
                    }
                    catch (f) {
                        c = d.height
                    }
                    (b !== e.w || c !== e.h) && d.trigger(i, [e.w = b, e.h = c])
                }),
                    d()
            }, g[k])
        }

        var e, f = a([]), g = a.resize = a.extend(a.resize, {}), h = "setTimeout", i = "resize",
            j = i + "-special-event", k = "delay", l = "throttleWindow";
        g[k] = throttle_delay,
            g[l] = !0, a.event.special[i] = {
            setup: function () {
                if (!g[l] && this[h])
                    return !1;
                var b = a(this);
                f = f.add(b);
                try {
                    a.data(this, j, {
                        w: b.width(),
                        h: b.height()
                    })
                }
                catch (c) {
                    a.data(this, j, {
                        w: b.width, h: b.height
                    })
                }
                if (1 === f.length) {
                    d()
                }
            },
            teardown: function () {
                if (!g[l] && this[h]) return !1;
                var b = a(this);
                f = f.not(b), b.removeData(j), f.length || clearTimeout(e)
            },
            add: function (b) {
                function d(b, d, f) {
                    var g = a(this), h = a.data(this, j);
                    h.w = d !== c ? d : g.width(), h.h = f !== c ? f : g.height(), e.apply(this, arguments)
                }

                if (!g[l] && this[h]) return !1;
                var e;
                return a.isFunction(b) ? (e = b, d) : (e = b.handler, void (b.handler = d))
            }
        }
    }(jQuery, this),
    $("#main").resize(function () {
        $(window).width() < 979 ? ($.root_.addClass("mobile-view-activated"), $.root_.removeClass("minified")) : $.root_.hasClass("mobile-view-activated") && $.root_.removeClass("mobile-view-activated")
    });
var ie = function () {
    for (var a, b = 3, c = document.createElement("div"), d = c.getElementsByTagName("i"); c.innerHTML = "<!--[if gt IE " + ++b + "]><i></i><![endif]-->", d[0];) ;
    return b > 4 ? b : a
}();
$.fn.extend({
    jarvismenu: function (a) {
        var b = {
                accordion: "true", speed: 200, closedSign: "[+]", openedSign: "[-]"
            },
            c = $.extend(b, a),
            d = $(this);
        d.find("li").each(function () {
            if (0 !== $(this).find("ul").size()) {
                $(this).find("a:first").append("<b class='collapse-sign'>" + c.closedSign + "</b>")
                if ("#" == $(this).find("a:first").attr("href")) {
                    $(this).find("a:first").click(function () {
                        return !1
                    })
                }
            }
        }),
            d.find("li.active").each(function () {
                $(this).parents("ul").slideDown(c.speed),
                    $(this).parents("ul").parent("li").find("b:first").html(c.openedSign),
                    $(this).parents("ul").parent("li").addClass("open")
            }),
            d.find("li a").click(function () {
                if (0 !== $(this).parent().find("ul").size()) {
                    if (c.accordion) {
                        if (!$(this).parent().find("ul").is(":visible")) {
                            parents = $(this).parent().parents("ul");
                            visible = d.find("ul:visible");
                            visible.each(function (a) {
                                var b = !0;
                                parents.each(function (c) {
                                    return parents[c] == visible[a] ? (b = !1, !1) : void 0
                                });
                                if (b && $(this).parent().find("ul") != visible[a]) {
                                    $(visible[a]).slideUp(c.speed, function () {
                                        $(this).parent("li").find("b:first").html(c.closedSign),
                                            $(this).parent("li").removeClass("open")
                                    })
                                }
                            });
                        }
                    }
                    if ($(this).parent().find("ul:first").is(":visible") && !$(this).parent().find("ul:first").hasClass("active")) {
                        $(this).parent().find("ul:first").slideUp(c.speed, function () {
                            $(this).parent("li").removeClass("open"), $(this).parent("li").find("b:first").delay(c.speed).html(c.closedSign)
                        });
                    }
                    else {
                        $(this).parent().find("ul:first").slideDown(c.speed, function () {
                            $(this).parent("li").addClass("open"), $(this).parent("li").find("b:first").delay(c.speed).html(c.openedSign)
                        })
                    }
                }
            })
    }
});
jQuery.fn.doesExist = function () {
    return jQuery(this).length > 0
};
if ($.navAsAjax || $(".google_maps")) {
    var gMapsLoaded = !1;
    window.gMapsCallback = function () {
        gMapsLoaded = !0,
            $(window).trigger("gMapsLoaded")
    },
        window.loadGoogleMaps = function () {
            if (gMapsLoaded) return window.gMapsCallback();
            var a = document.createElement("script");
            a.setAttribute("type", "text/javascript");
            a.setAttribute("src", "http://maps.google.com/maps/api/js?sensor=false&callback=gMapsCallback");
            (document.getElementsByTagName("head")[0] || document.documentElement).appendChild(a);
        }
}
if ($.navAsAjax) {
    if ($("nav").length)
        checkURL();
    $(document).on("click", 'nav a[href!="#"]', function (a) {
        a.preventDefault();
        var b = $(a.currentTarget);
        if (!b.parent().hasClass("active") && !b.attr("target")) {
            if ($.root_.hasClass("mobile-view-activated")) {
                $.root_.removeClass("hidden-menu"),
                    $("html").removeClass("hidden-menu-mobile-lock"),
                    window.setTimeout(function () {
                        if (window.location.search) {
                            window.location.href = window.location.href.replace(window.location.search, "").replace(window.location.hash, "") + "#" + b.attr("href")
                        }
                        else {
                            window.location.hash = b.attr("href")
                        }
                    }, 150)
            }
            else {
                if (window.location.search) {
                    window.location.href = window.location.href.replace(window.location.search, "").replace(window.location.hash, "") + "#" + b.attr("href")
                }
                else {
                    window.location.hash = b.attr("href")
                }
            }
        }
    });
    $(document).on("click", 'nav a[target="_blank"]', function (a) {
        a.preventDefault();
        var b = $(a.currentTarget);
        window.open(b.attr("href"))
    });
    $(document).on("click", 'nav a[target="_top"]', function (a) {
        a.preventDefault();
        var b = $(a.currentTarget);
        window.location = b.attr("href")
    });
    $(document).on("click", 'nav a[href="#"]', function (a) {
        a.preventDefault()
    });
    $(window).on("hashchange", function () {
        checkURL();
        $("nav").find(".nav-wechat").each(function (i, o) {
            if (!$(o).parent().hasClass("active"))
                $(o).find("span").css({color: "#504e4e"});
            else
                $(o).find("span").css({color: "#d32f2f"});
        });
    });
}
$("body").on("click", function (a) {
    $('[rel="popover"]').each(function () {
        $(this).is(a.target) || 0 !== $(this).has(a.target).length || 0 !== $(".popover").has(a.target).length || $(this).popover("hide")
    })
});

!function (e, t) {
    var a = "multiple" in document.createElement("INPUT"),
        i = "FileList" in window,
        n = "FileReader" in window,
        s = "File" in window;
    var r = function (t, a) {
        var i = this;
        this.settings = e.extend({}, e.fn.smart_file_input.defaults, a),
            this.$element = e(t),
            this.element = t,
            this.disabled = !1,
            this.can_reset = !0,
            this.$element.off("change.smart_inner_call").on("change.smart_inner_call", function (e, t) {
                return i.disabled || t === !0 ? void 0 : l.call(i)
            });
        var n = this.$element.closest("label").css({display: "block"}),
            s = 0 == n.length ? "label" : "span";
        this.$element.wrap("<" + s + ' class="smart-file-input" />'),
            this.apply_settings(),
            this.reset_input_field()
    };
    r.error = {
        FILE_LOAD_FAILED: 1, IMAGE_LOAD_FAILED: 2, THUMBNAIL_FAILED: 3
    },
        r.prototype.apply_settings = function () {
            var t = this;
            this.multi = this.$element.attr("multiple") && a,
                this.well_style = "well" == this.settings.style,
                this.well_style ? this.$element.parent().addClass("smart-file-multiple") : this.$element.parent().removeClass("smart-file-multiple"),
                this.$element.parent().find(":not(input[type=file])").remove(),
                this.$element.after('<span class="smart-file-container" data-title="' + this.settings.btn_choose + '"><span class="smart-file-name" data-title="' + this.settings.no_file + '">' + (this.settings.no_icon ? '<i class="' + this.settings.no_icon + '"></i>' : "") + "</span></span>"),
                this.$label = this.$element.next(),
                this.$container = this.$element.closest(".smart-file-input");
            var n = !!this.settings.icon_remove;
            if (n) {
                var s = e('<a class="remove" href="#"><i class="' + this.settings.icon_remove + '"></i></a>').appendTo(this.$element.parent());
                s.on("click", function (e) {
                    if (e.preventDefault(), !t.can_reset)
                        return !1;
                    var a = !0;
                    if (t.settings.before_remove && (a = t.settings.before_remove.call(t.element)), !a)
                        return !1;
                    t.reset_input();
                    return !1
                })
            }
            this.settings.droppable && i && o.call(this)
        },
        r.prototype.show_file_list = function (t) {
            var a = "undefined" == typeof t ? this.$element.data("smart_input_files") : t;
            if (a && 0 != a.length) {
                this.well_style && (this.$label.find(".smart-file-name").remove(), this.settings.btn_change || this.$label.addClass("hide-placeholder")),
                    this.$label.attr("data-title", this.settings.btn_change).addClass("file-selected");
                for (var i = 0; i < a.length; i++) {
                    var s = "string" == typeof a[i] ? a[i] : e.trim(a[i].name),
                        r = s.lastIndexOf("\\") + 1;
                    0 == r && (r = s.lastIndexOf(window.location.protocol + "//" + window.location.host) + 1),
                        s = s.substr(r);
                    var o = "fa fa-file",
                        l = "file";
                    if (/\.(jpe?g|png|gif|svg|bmp|tiff?)$/i.test(s)) {
                        (o = "fa fa-picture-o file-image", l = "image")
                    }
                    else {
                        if (/\.(mpe?g|flv|mov|avi|swf|mp4|mkv|webm|wmv|3gp)$/i.test(s)) {
                            (o = "fa fa-film file-video", l = "video")
                        }
                        else {
                            if (/\.(mp3|ogg|wav|wma|amr|aac)$/i.test(s)) {
                                o = "fa fa-music file-audio";
                                l = "audio";
                            }
                        }
                    }
                    ;
                    if (this.well_style) {
                        this.$label.append('<span class="smart-file-name" data-title="' + s + '"><i class="' + o + '"></i></span>');
                        var d = e.trim(a[i].type),
                            u = n && this.settings.thumbnail && (d.length > 0 && d.match("image") || 0 == d.length && "image" == l);
                        if (u) {
                            var h = this;
                            e.when(c.call(this, a[i])).fail(function (e) {
                                h.settings.preview_error && h.settings.preview_error.call(h, s, e.code)
                            })
                        }
                    }
                    else
                        this.$label.find(".smart-file-name").attr({"data-title": s}).find(".fa").attr("class", o)
                }
                return !0
            }
        },
        r.prototype.reset_input = function () {
            this.reset_input_ui(), this.reset_input_field()
        },
        r.prototype.reset_input_ui = function () {
            this.$label.attr({
                "data-title": this.settings.btn_choose, "class": "smart-file-container"
            }).find(".smart-file-name:first").attr({
                "data-title": this.settings.no_file, "class": "smart-file-name"
            }).find(".fa").attr("class", this.settings.no_icon).prev("img").remove(),
            this.settings.no_icon || this.$label.find(".fa").remove(),
                this.$label.find(".smart-file-name").not(":first").remove(),
                this.reset_input_data()
        },
        r.prototype.reset_input_field = function () {
            this.$element.wrap("<form>").parent().get(0).reset(), this.$element.unwrap()
        },
        r.prototype.reset_input_data = function () {
            this.$element.data("smart_input_files") && (this.$element.removeData("smart_input_files"), this.$element.removeData("smart_input_method"))
        },
        r.prototype.enable_reset = function (e) {
            this.can_reset = e
        },
        r.prototype.disable = function () {
            this.disabled = !0, this.$element.attr("disabled", "disabled").addClass("disabled")
        },
        r.prototype.enable = function () {
            this.disabled = !1, this.$element.removeAttr("disabled").removeClass("disabled")
        },
        r.prototype.files = function () {
            return e(this).data("smart_input_files") || null
        },
        r.prototype.method = function () {
            return e(this).data("smart_input_method") || ""
        },
        r.prototype.update_settings = function (t) {
            this.settings = e.extend({}, this.settings, t), this.apply_settings()
        },
        r.prototype.loading = function (t) {
            if (t === !1) {
                this.$container.find(".smart-file-overlay").remove();
                this.element.removeAttribute("readonly");
            }
            else {
                var a = "string" == typeof t ? t : '<i class="overlay-content fa fa-spin fa-spinner orange2 fa-2x"></i>',
                    i = this.$container.find(".smart-file-overlay");
                if (0 == i.length) {
                    i = e('<div class="smart-file-overlay"></div>').appendTo(this.$container);
                    i.on("click tap", function (e) {
                        e.stopImmediatePropagation();
                        e.preventDefault();
                        return !1
                    });
                    this.element.setAttribute("readonly", "true");
                }
                i.empty().append(a);
            }
        };
    var o = function () {
            var e = this,
                t = this.$element.parent();
            t.off("dragenter").on("dragenter", function (e) {
                e.preventDefault(), e.stopPropagation()
            }).off("dragover").on("dragover", function (e) {
                e.preventDefault(), e.stopPropagation()
            }).off("drop").on("drop", function (t) {
                t.preventDefault();
                t.stopPropagation();
                if (!e.disabled) {
                    var a = t.originalEvent.dataTransfer,
                        i = a.files;
                    if (!e.multi && i.length > 1) {
                        var n = [];
                        n.push(i[0]), i = n
                    }
                    jQuery(this).find("input[type='file']")[0].files = i;
                    i = u.call(e, i, !0);
                    if (i === !1)
                        return !1;
                    else {
                        e.$element.data("smart_input_method", "drop");
                        e.$element.data("smart_input_files", i);
                        e.show_file_list(i);
                        e.$element.triggerHandler("change", [!0]);
                        return !0;
                    }
                }
            })
        },
        l = function () {
            var e = this.element.files || [this.element.value];
            e = u.call(this, e, !1);
            if (e === !1)
                return !1;
            else {
                this.$element.data("smart_input_method", "select");
                this.$element.data("smart_input_files", e);
                this.show_file_list(e);
                return !0;
            }
        },
        c = function (t) {
            var a = this,
                i = a.$label.find(".smart-file-name:last"),
                n = new e.Deferred,
                s = new FileReader;
            s.onload = function (s) {
                i.prepend("<img class='middle' style='display:none;' />");
                var o = i.find("img:last").get(0);
                e(o).one("load", function () {
                    var s = 50;
                    if ("large" == a.settings.thumbnail)
                        s = 150;
                    else {
                        "fit" == a.settings.thumbnail && (s = i.width())
                    }
                    i.addClass(s > 50 ? "large" : "");
                    var l = d(o, s, t.type);
                    if (null == l) {
                        e(this).remove();
                        return void n.reject({
                            code: r.error.THUMBNAIL_FAILED
                        });
                    }
                    var c = l.w,
                        u = l.h;
                    "small" == a.settings.thumbnail && (c = u = s),
                        e(o).css({
                            "background-image": "url(" + l.src + ")",
                            width: c,
                            height: u
                        }).data("thumb", l.src).attr({
                            src: "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVQImWNgYGBgAAAABQABh6FO1AAAAABJRU5ErkJggg=="
                        }).show();
                    n.resolve()
                }).one("error", function () {
                    i.find("img").remove(),
                        n.reject({
                            code: r.error.IMAGE_LOAD_FAILED
                        });
                });
                o.src = s.target.result
            }
            s.onerror = function () {
                n.reject({code: r.error.FILE_LOAD_FAILED})
            }
            s.readAsDataURL(t);
            return n.promise()
        },
        d = function (t, a) {
            var i = t.width,
                n = t.height;
            i = i > 0 ? i : e(t).width(),
                n = n > 0 ? n : e(t).height();
            if (i > a || n > a) {
                if (i > n) {
                    n = parseInt(a / i * n);
                    i = a;
                }
                else {
                    i = parseInt(a / n * i);
                    n = a;
                }
            }
            var s;
            try {
                var r = document.createElement("canvas");
                r.width = i,
                    r.height = n;
                var o = r.getContext("2d");
                o.drawImage(t, 0, 0, t.width, t.height, 0, 0, i, n),
                    s = r.toDataURL()
            }
            catch (l) {
                s = null
            }
            return s ? (/^data\:image\/(png|jpe?g|gif);base64,[0-9A-Za-z\+\/\=]+$/.test(s) || (s = null), s ? {
                src: s,
                w: i,
                h: n
            } : null) : null
        },
        u = function (e, t) {
            var a = p.call(this, e, t);
            return -1 === a ? (this.reset_input(), !1) : a && 0 != a.length ? ((a instanceof Array || i && a instanceof FileList) && (e = a), a = !0, this.settings.before_change && (a = this.settings.before_change.call(this.element, e, t)), -1 === a ? (this.reset_input(), !1) : a && 0 != a.length ? ((a instanceof Array || i && a instanceof FileList) && (e = a), e) : (this.$element.data("smart_input_files") || this.reset_input(), !1)) : (this.$element.data("smart_input_files") || this.reset_input(), !1)
        },
        h = function (e) {
            return e ? ("string" == typeof e && (e = [e]), 0 == e.length ? null : new RegExp(".(?:" + e.join("|") + ")$", "i")) : null
        },
        f = function (e) {
            return e ? ("string" == typeof e && (e = [e]), 0 == e.length ? null : new RegExp("^(?:" + e.join("|").replace(/\//g, "\\/") + ")$", "i")) : null
        },
        p = function (t, a) {
            var i = h(this.settings.allowExt),
                n = h(this.settings.denyExt),
                r = f(this.settings.allowMime),
                o = f(this.settings.denyMime),
                l = this.settings.maxSize || !1;
            if (!(i || n || r || o || l))
                return !0;
            for (var c = [], d = {}, u = 0; u < t.length; u++) {
                var p = t[u],
                    v = s ? p.name : p;
                if (!i || i.test(v))
                    if (n && n.test(v))
                        "ext" in d || (d.ext = []), d.ext.push(v);
                    else {
                        var g;
                        if (s) {
                            if ((g = e.trim(p.type)).length > 0) {
                                if (r && !r.test(g)) {
                                    "mime" in d || (d.mime = []), d.mime.push(v);
                                    continue
                                }
                                if (o && o.test(g)) {
                                    "mime" in d || (d.mime = []), d.mime.push(v);
                                    continue
                                }
                            }
                            l && p.size > l ? ("size" in d || (d.size = []), d.size.push(v)) : c.push(p)
                        }
                        else c.push(p)
                    }
                else
                    "ext" in d || (d.ext = []), d.ext.push(v)
            }
            if (c.length == t.length)
                return t;
            var m = {ext: 0, mime: 0, size: 0};
            "ext" in d && (m.ext = d.ext.length),
            "mime" in d && (m.mime = d.mime.length),
            "size" in d && (m.size = d.size.length);
            var b;
            this.$element.trigger(b = new e.Event("file.error.smart"), {
                file_count: t.length,
                invalid_count: t.length - c.length,
                error_list: d,
                error_count: m,
                dropped: a
            });
            return b.isDefaultPrevented() ? -1 : c
        };
    e.fn.smart_file_input = function (a, i) {
        var n, s = this.each(function () {
            var t = e(this),
                s = t.data("smart_file_input"),
                o = "object" == typeof a && a;
            s || t.data("smart_file_input", s = new r(this, o)),
            "string" == typeof a && (n = s[a](i))
        });
        return n === t ? s : n
    },
        e.fn.smart_file_input.defaults = {
            style: !1,
            no_file: "请选择文件 ...",
            no_icon: "fa fa-upload",
            btn_choose: "选择",
            btn_change: "更换",
            icon_remove: "fa fa-lg fa-times",
            droppable: !1,
            thumbnail: !1,
            allowExt: null,
            denyExt: null,
            allowMime: null,
            denyMime: null,
            maxSize: !1,
            before_change: null,
            before_remove: null,
            preview_error: null
        }
}(window.jQuery);
window.simpleNotify = function (text, title, type, timeout) {
    var clr = "#296191";
    var icon = "";
    switch (type) {
        case "success":
            clr = "#356635";
            icon = "fa fa-check";
            break;
        case "error":
            clr = "#c26565";
            icon = "fa fa-times";
            break;
    }
    $.smallBox({
        title: title,
        content: text,
        color: clr,
        iconSmall: icon,
        timeout: timeout || 4000
    });
}
window.getPageIndex = function (pageData) {
    var result = 0;
    if (pageData instanceof Array && pageData.length > 0) {
        for (var i = 0; i < pageData.length; i++) {
            if (pageData[i].name == 'iDisplayStart') {
                result = parseInt(pageData[i].value / getPageSize(pageData), 10);
                break;
            }
        }
    }
    return result;
}
window.getPageSize = function (pageData) {
    var result = 0;
    if (pageData instanceof Array && pageData.length > 0) {
        for (var i = 0; i < pageData.length; i++) {
            if (pageData[i].name == 'iDisplayLength') {
                result = pageData[i].value;
                break;
            }
        }
    }
    return result;
},
// jQuery DataTable 中文
    window.gjhDataTable_CN_Lang = {
        oAria: {
            sSortAscending: ": activate to sort column ascending",
            sSortDescending: ": activate to sort column descending"
        },
        oPaginate: {
            sFirst: "首页",
            sLast: "尾页",
            sNext: "下一页",
            sPrevious: "上一页"
        },
        sEmptyTable: "暂无数据。",
        sInfo: "显示 _START_ 到 _END_ ,共 _TOTAL_ 条",
        sInfoEmpty: "显示 0 到 0 。共 0 条",
        sInfoFiltered: "(filtered from _MAX_ total entries)",
        sInfoPostFix: "",
        sDecimal: "",
        sThousands: ",",
        sLengthMenu: "分页大小 _MENU_ ",
        sLoadingRecords: "加载中...",
        sProcessing: "加载中...",
        sSearch: "Search:",
        sUrl: "",
        sZeroRecords: "暂无数据。"
    };

//自定义保存文件
window.customUploadImg = function (file, editor) {
    data = new FormData();
    data.append("file", file);
    url = "/Common/UploadEditorImg";
    $.ajax({
        data: data,
        type: "POST",
        url: url,
        cache: false,
        contentType: false,
        processData: false,
        success: function (url) {
            editor.summernote('insertImage', url);
        }
    });
}

//获取url参数
window.getQueryString = function (url, name) {
    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
    var r = url.substr(1).match(reg);
    if (r != null) {
        return unescape(r[2]);
    }
    return null;
}

//显示确认弹出框
window.showConfirm = function (title, content, yesFunction, cancelFunction) {
    $.SmartMessageBox({
        title: "<i class='fa fa-bell swing animated txt-color-orangeDark' style='margin-right:10px;'></i>" + title + "?",
        content: content,
        buttons: '[取消][确认]'
    }, function (ButtonPressed) {
        if (ButtonPressed === "确认") {
            if (yesFunction && typeof(yesFunction) == 'function') {
                yesFunction();
            }
        }
        if (ButtonPressed === "取消") {
            if (cancelFunction && typeof (cancelFunction) == 'function') {
                cancelFunction();
            }
        }
    });
    event && e.preventDefault();
}

//显示确认弹出框
window.showAlert = function (title, content, yesFunction, cancelFunction) {
    $.SmartMessageBox({
        title: "<i class='fa fa-bell swing animated txt-color-orangeDark' style='margin-right:10px;'></i>" + title,
        content: content,
        buttons: '[确认]'
    }, function (ButtonPressed) {
        if (ButtonPressed === "确认") {
            if (yesFunction && typeof (yesFunction) == 'function') {
                yesFunction();
            }
        }
    });
    e.preventDefault();
}
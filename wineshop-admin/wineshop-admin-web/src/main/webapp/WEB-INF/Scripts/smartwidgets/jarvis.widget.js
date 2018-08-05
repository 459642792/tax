/*
 * Unminified JS files are available after purchase
 */
/*! SmartAdmin - v1.4.1 - 2014-06-26 */
!function (a, b, c, d) {
    function e(b, c) {
        this.obj = a(b),
            this.o = a.extend({}, a.fn[f].defaults, c),
            this.objId = this.obj.attr("id"),
            this.pwCtrls = ".jarviswidget-ctrls",
            this.widget = this.obj.find(this.o.widgets),
            this.toggleClass = this.o.toggleClass.split("|"),
            this.editClass = this.o.editClass.split("|"),
            this.fullscreenClass = this.o.fullscreenClass.split("|"),
            this.customClass = this.o.customClass.split("|"),
            this.init()
    }

    var f = "jarvisWidgets";
    e.prototype = {
        _settings: function () {
            var a = this;
            storage = !!function () {
                var a, b = +new Date;
                try {
                    return localStorage.setItem(b, b), a = localStorage.getItem(b) == b, localStorage.removeItem(b), a
                } catch (c) {
                }
            }() && localStorage;
            if (storage && a.o.localStorage) {
                if (a.o.ajaxnav === !0) {
                    widget_url = location.hash.replace(/^#/, ""),
                        keySettings = "Plugin_settings_" + widget_url + "_" + a.objId,
                        getKeySettings = localStorage.getItem(keySettings),
                        keyPosition = "Plugin_position_" + widget_url + "_" + a.objId,
                        getKeyPosition = localStorage.getItem(keyPosition)
                }
                else {
                    keySettings = "jarvisWidgets_settings_" + location.pathname + "_" + a.objId,
                        getKeySettings = localStorage.getItem(keySettings),
                        keyPosition = "jarvisWidgets_position_" + location.pathname + "_" + a.objId,
                        getKeyPosition = localStorage.getItem(keyPosition)
                }
            }
            clickEvent = "ontouchstart" in b || b.DocumentTouch && c instanceof DocumentTouch ? "touchstart" : "click"
        },
        _runLoaderWidget: function (a) {
            var b = this;
            b.o.indicator === !0 && a.parents(b.o.widgets).find(".jarviswidget-loader").stop(!0, !0).fadeIn(100).delay(b.o.indicatorTime).fadeOut(100)
        },
        _getPastTimestamp: function (a) {
            var b = this,
                c = new Date(a),
                d = c.getMonth() + 1,
                e = c.getDate(),
                f = c.getFullYear(),
                g = c.getHours(),
                h = c.getMinutes(),
                i = c.getUTCSeconds();
            10 > d && (d = "0" + d),
            10 > e && (e = "0" + e),
            10 > g && (g = "0" + g),
            10 > h && (h = "0" + h),
            10 > i && (i = "0" + i);
            var j = b.o.timestampFormat.replace(/%d%/g, e).replace(/%m%/g, d).replace(/%y%/g, f).replace(/%h%/g, g).replace(/%i%/g, h).replace(/%s%/g, i);
            return j
        },
        _loadAjaxFile: function (b, c, d) {
            var e = this;
            b.find(".widget-body").load(c, function (c, d, f) {
                var g = a(this);
                "error" == d && g.html('<h4 class="alert alert-danger">' + e.o.labelError + "<b> " + f.status + " " + f.statusText + "</b></h4>");
                if ("success" == d) {
                    var h = b.find(e.o.timestampPlaceholder);
                    h.length && h.html(e._getPastTimestamp(new Date)),
                    "function" == typeof e.o.afterLoad && e.o.afterLoad.call(this, b)
                }
            }),
                e._runLoaderWidget(d)
        },
        _saveSettingsWidget: function () {
            var b = this;
            if (b._settings(), storage && b.o.localStorage) {
                var c = [];
                b.obj.find(b.o.widgets).each(function () {
                    var b = {};
                    b.id = a(this).attr("id"),
                        b.style = a(this).attr("data-widget-attstyle"),
                        b.title = a(this).children("header").children("h2").text(),
                        b.hidden = a(this).is(":hidden") ? 1 : 0,
                        b.collapsed = a(this).hasClass("jarviswidget-collapsed") ? 1 : 0,
                        c.push(b)
                }),
                    storeSettingsObj = JSON.stringify({widget: c}),
                getKeySettings != storeSettingsObj && localStorage.setItem(keySettings, storeSettingsObj)
            }
            "function" == typeof b.o.onSave && b.o.onSave.call(this, null, storeSettingsObj)
        },
        _savePositionWidget: function () {
            var b = this;
            if (b._settings(), storage && b.o.localStorage) {
                var c = [];
                b.obj.find(b.o.grid + ".sortable-grid").each(function () {
                    var d = [];
                    a(this).children(b.o.widgets).each(function () {
                        var b = {};
                        b.id = a(this).attr("id"),
                            d.push(b)
                    });
                    var e = {
                        section: d
                    };
                    c.push(e)
                });
                var d = JSON.stringify({grid: c});
                getKeyPosition != d && localStorage.setItem(keyPosition, d, null)
            }
            "function" == typeof b.o.onSave && b.o.onSave.call(this, d)
        },
        init: function () {
            var b = this;
            if (b._settings(), a("#" + b.objId).length || alert("It looks like your using a class instead of an ID, dont do that!"), b.o.rtl === !0 && a("body").addClass("rtl"), a(b.o.grid).each(function () {
                    a(this).find(b.o.widgets).length && a(this).addClass("sortable-grid")
                }), storage && b.o.localStorage && getKeyPosition) {
                var c = JSON.parse(getKeyPosition);
                for (var e in c.grid) {
                    var f = b.obj.find(b.o.grid + ".sortable-grid").eq(e);
                    for (var g in c.grid[e].section) f.append(a("#" + c.grid[e].section[g].id))
                }
            }
            if (storage && b.o.localStorage && getKeySettings) {
                var h = JSON.parse(getKeySettings);
                for (var e in h.widget) {
                    var i = a("#" + h.widget[e].id);
                    h.widget[e].style && i.removeClassPrefix("jarviswidget-color-").addClass(h.widget[e].style).attr("data-widget-attstyle", "" + h.widget[e].style),
                        1 == h.widget[e].hidden ? i.hide(1) : i.show(1).removeAttr("data-widget-hidden"),
                    1 == h.widget[e].collapsed && i.addClass("jarviswidget-collapsed").children("div").hide(1),
                    i.children("header").children("h2").text() != h.widget[e].title && i.children("header").children("h2").text(h.widget[e].title)
                }
            }
            b.widget.each(function () {
                var c, e, f, g, h, i, j, k, l = a(this), m = a(this).children("header");
                if (!m.parent().attr("role")) {
                    l.data("widget-hidden") === !0 && l.hide(),
                    l.data("widget-collapsed") === !0 && l.addClass("jarviswidget-collapsed").children("div").hide(),
                        c = b.o.customButton === !0 && l.data("widget-custombutton") === d && 0 !== b.customClass[0].length ? '<a href="javascript:void(0);" class="button-icon jarviswidget-custom-btn"><i class="' + b.customClass[0] + '"></i></a>' : "",
                        e = b.o.deleteButton === !0 && l.data("widget-deletebutton") === d ? '<a href="javascript:void(0);" class="button-icon jarviswidget-delete-btn" rel="tooltip" title="Delete" data-placement="bottom"><i class="' + b.o.deleteClass + '"></i></a>' : "",
                        f = b.o.editButton === !0 && l.data("widget-editbutton") === d ? '<a href="javascript:void(0);" class="button-icon jarviswidget-edit-btn" rel="tooltip" title="Edit Title" data-placement="bottom"><i class="' + b.editClass[0] + '"></i></a>' : "",
                        g = b.o.fullscreenButton === !0 && l.data("widget-fullscreenbutton") === d ? '<a href="javascript:void(0);" class="button-icon jarviswidget-fullscreen-btn" rel="tooltip" title="Fullscreen" data-placement="bottom"><i class="' + b.fullscreenClass[0] + '"></i></a>' : "",
                        b.o.colorButton === !0 && l.data("widget-colorbutton") === d ? (h = '<a data-toggle="dropdown" class="dropdown-toggle color-box selector" href="javascript:void(0);"></a><ul class="dropdown-menu arrow-box-up-right color-select pull-right"><li><span class="bg-color-green" data-widget-setstyle="jarviswidget-color-green" rel="tooltip" data-placement="left" data-original-title="Green Grass"></span></li><li><span class="bg-color-greenDark" data-widget-setstyle="jarviswidget-color-greenDark" rel="tooltip" data-placement="top" data-original-title="Dark Green"></span></li><li><span class="bg-color-greenLight" data-widget-setstyle="jarviswidget-color-greenLight" rel="tooltip" data-placement="top" data-original-title="Light Green"></span></li><li><span class="bg-color-purple" data-widget-setstyle="jarviswidget-color-purple" rel="tooltip" data-placement="top" data-original-title="Purple"></span></li><li><span class="bg-color-magenta" data-widget-setstyle="jarviswidget-color-magenta" rel="tooltip" data-placement="top" data-original-title="Magenta"></span></li><li><span class="bg-color-pink" data-widget-setstyle="jarviswidget-color-pink" rel="tooltip" data-placement="right" data-original-title="Pink"></span></li><li><span class="bg-color-pinkDark" data-widget-setstyle="jarviswidget-color-pinkDark" rel="tooltip" data-placement="left" data-original-title="Fade Pink"></span></li><li><span class="bg-color-blueLight" data-widget-setstyle="jarviswidget-color-blueLight" rel="tooltip" data-placement="top" data-original-title="Light Blue"></span></li><li><span class="bg-color-teal" data-widget-setstyle="jarviswidget-color-teal" rel="tooltip" data-placement="top" data-original-title="Teal"></span></li><li><span class="bg-color-blue" data-widget-setstyle="jarviswidget-color-blue" rel="tooltip" data-placement="top" data-original-title="Ocean Blue"></span></li><li><span class="bg-color-blueDark" data-widget-setstyle="jarviswidget-color-blueDark" rel="tooltip" data-placement="top" data-original-title="Night Sky"></span></li><li><span class="bg-color-darken" data-widget-setstyle="jarviswidget-color-darken" rel="tooltip" data-placement="right" data-original-title="Night"></span></li><li><span class="bg-color-yellow" data-widget-setstyle="jarviswidget-color-yellow" rel="tooltip" data-placement="left" data-original-title="Day Light"></span></li><li><span class="bg-color-orange" data-widget-setstyle="jarviswidget-color-orange" rel="tooltip" data-placement="bottom" data-original-title="Orange"></span></li><li><span class="bg-color-orangeDark" data-widget-setstyle="jarviswidget-color-orangeDark" rel="tooltip" data-placement="bottom" data-original-title="Dark Orange"></span></li><li><span class="bg-color-red" data-widget-setstyle="jarviswidget-color-red" rel="tooltip" data-placement="bottom" data-original-title="Red Rose"></span></li><li><span class="bg-color-redLight" data-widget-setstyle="jarviswidget-color-redLight" rel="tooltip" data-placement="bottom" data-original-title="Light Red"></span></li><li><span class="bg-color-white" data-widget-setstyle="jarviswidget-color-white" rel="tooltip" data-placement="right" data-original-title="Purity"></span></li><li><a href="javascript:void(0);" class="jarviswidget-remove-colors" data-widget-setstyle="" rel="tooltip" data-placement="bottom" data-original-title="Reset widget color to default">Remove</a></li></ul>', m.prepend('<div class="widget-toolbar">' + h + "</div>")) : h = "",
                        b.o.toggleButton === !0 && l.data("widget-togglebutton") === d ? (j = l.data("widget-collapsed") === !0 || l.hasClass("jarviswidget-collapsed") ? b.toggleClass[1] : b.toggleClass[0],
                            i = '<a href="javascript:void(0);" class="button-icon jarviswidget-toggle-btn" rel="tooltip" title="Collapse" data-placement="bottom"><i class="' + j + '"></i></a>') : i = "",
                        k = b.o.refreshButton === !0 && l.data("widget-refreshbutton") !== !1 && l.data("widget-load") ? '<a href="javascript:void(0);" class="button-icon jarviswidget-refresh-btn" data-loading-text="&nbsp;&nbsp;Loading...&nbsp;" rel="tooltip" title="Refresh" data-placement="bottom"><i class="' + b.o.refreshButtonClass + '"></i></a>' : "";
                    var n = b.o.buttonOrder.replace(/%refresh%/g, k).replace(/%delete%/g, e).replace(/%custom%/g, c).replace(/%fullscreen%/g, g).replace(/%edit%/g, f).replace(/%toggle%/g, i);
                    ("" !== k || "" !== e || "" !== c || "" !== g || "" !== f || "" !== i) && m.prepend('<div class="jarviswidget-ctrls">' + n + "</div>"),
                    b.o.sortable === !0 && l.data("widget-sortable") === d && l.addClass("jarviswidget-sortable"),
                    l.find(b.o.editPlaceholder).length && l.find(b.o.editPlaceholder).find("input").val(a.trim(m.children("h2").text())),
                        m.append('<span class="jarviswidget-loader"><i class="fa fa-refresh fa-spin"></i></span>'),
                        l.attr("role", "widget").children("div").attr("role", "content").prev("header").attr("role", "heading").children("div").attr("role", "menu")
                }
            })
            if (b.o.buttonsHidden === !0 && a(b.o.pwCtrls).hide(), a(".jarviswidget header [rel=tooltip]").tooltip(), b.obj.find("[data-widget-load]").each(function () {
                    {
                        var c = a(this), d = c.children(), e = c.data("widget-load"),
                            f = 1e3 * c.data("widget-refresh");
                        c.children()
                    }
                    c.find(".jarviswidget-ajax-placeholder").length || (c.children("widget-body").append('<div class="jarviswidget-ajax-placeholder">' + b.o.loadingLabel + "</div>"), c.data("widget-refresh") > 0 ? (b._loadAjaxFile(c, e, d), a.intervalArr.push(setInterval(function () {
                        b._loadAjaxFile(c, e, d)
                    }, f))) : b._loadAjaxFile(c, e, d))
                }), b.o.sortable === !0 && jQuery.ui) {
                var j = b.obj.find(".sortable-grid").not("[data-widget-excludegrid]");
                j.sortable({
                    items: j.find(".jarviswidget-sortable"),
                    connectWith: j,
                    placeholder: b.o.placeholderClass,
                    cursor: "move",
                    revert: !0,
                    opacity: b.o.opacity,
                    delay: 200,
                    cancel: ".button-icon, #jarviswidget-fullscreen-mode > div",
                    zIndex: 1e4,
                    handle: b.o.dragHandle,
                    forcePlaceholderSize: !0,
                    forceHelperSize: !0,
                    update: function (a, c) {
                        b._runLoaderWidget(c.item.children()),
                            b._savePositionWidget(),
                        "function" == typeof b.o.onChange && b.o.onChange.call(this, c.item)
                    }
                })
            }
            b.o.buttonsHidden === !0 && b.widget.children("header").hover(function () {
                a(this).children(b.o.pwCtrls).stop(!0, !0).fadeTo(100, 1)
            }, function () {
                a(this).children(b.o.pwCtrls).stop(!0, !0).fadeTo(100, 0)
            }),
                b._clickEvents(),
                a(b.o.deleteSettingsKey).on(clickEvent, this, function (a) {
                    if (storage && b.o.localStorage) {
                        var c = confirm(b.o.settingsKeyLabel);
                        c && localStorage.removeItem(keySettings)
                    }
                    b.stopPropagation();
                    a.preventDefault()
                }),
                a(b.o.deletePositionKey).on(clickEvent, this, function (a) {
                    if (storage && b.o.localStorage) {
                        var c = confirm(b.o.positionKeyLabel);
                        c && localStorage.removeItem(keyPosition)
                    }
                    b.stopPropagation();
                    a.preventDefault()
                });
            if (storage && b.o.localStorage) {
                (null === getKeySettings || getKeySettings.length < 1) && b._saveSettingsWidget(),
                (null === getKeyPosition || getKeyPosition.length < 1) && b._savePositionWidget()
            }
        },
        _clickEvents: function () {
            function c() {
                if (a("#jarviswidget-fullscreen-mode").length) {
                    var c = a(b).height(),
                        e = a("#jarviswidget-fullscreen-mode").find(d.o.widgets).children("header").height();
                    a("#jarviswidget-fullscreen-mode").find(d.o.widgets).children("div").height(c - e - 15)
                }
            }

            var d = this;
            d._settings(),
                d.widget.on(clickEvent, ".jarviswidget-toggle-btn", function (b) {
                    var c = a(this),
                        e = c.parents(d.o.widgets);
                    d._runLoaderWidget(c);
                    if (e.hasClass("jarviswidget-collapsed")) {
                        c.children().removeClass(d.toggleClass[1]).addClass(d.toggleClass[0]).parents(d.o.widgets).removeClass("jarviswidget-collapsed").children("[role=content]").slideDown(d.o.toggleSpeed, function () {
                            d._saveSettingsWidget()
                        })
                    }
                    else {
                        c.children().removeClass(d.toggleClass[0]).addClass(d.toggleClass[1]).parents(d.o.widgets).addClass("jarviswidget-collapsed").children("[role=content]").slideUp(d.o.toggleSpeed, function () {
                            d._saveSettingsWidget()
                        })
                    }
                    "function" == typeof d.o.onToggle && d.o.onToggle.call(this, e),
                        b.stopPropagation();
                    b.preventDefault()
                }),
                d.widget.on(clickEvent, ".jarviswidget-fullscreen-btn", function (b) {
                    var e = a(this).parents(d.o.widgets),
                        f = e.children("div");
                    d._runLoaderWidget(a(this));
                    if (a("#jarviswidget-fullscreen-mode").length) {
                        a(".nooverflow").removeClass("nooverflow"),
                            e.unwrap("<div>").children("div").removeAttr("style").end().find(".jarviswidget-fullscreen-btn").children().removeClass(d.fullscreenClass[1]).addClass(d.fullscreenClass[0]).parents(d.pwCtrls).children("a").show(),
                        f.hasClass("jarviswidget-visible") && f.hide().removeClass("jarviswidget-visible")
                    }
                    else {
                        a("body").addClass("nooverflow"), e.wrap('<div id="jarviswidget-fullscreen-mode"/>').parent().find(".jarviswidget-fullscreen-btn").children().removeClass(d.fullscreenClass[0]).addClass(d.fullscreenClass[1]).parents(d.pwCtrls).children("a:not(.jarviswidget-fullscreen-btn)").hide(),
                        f.is(":hidden") && f.show().addClass("jarviswidget-visible")
                    }
                    c();
                    "function" == typeof d.o.onFullscreen && d.o.onFullscreen.call(this, e);
                    b.stopPropagation();
                    b.preventDefault();
                }),
                a(b).resize(function () {
                    c()
                }),
                d.widget.on(clickEvent, ".jarviswidget-edit-btn", function (b) {
                    var c = a(this).parents(d.o.widgets);
                    d._runLoaderWidget(a(this));
                    if (c.find(d.o.editPlaceholder).is(":visible")) {
                        a(this).children().removeClass(d.editClass[1]).addClass(d.editClass[0]).parents(d.o.widgets).find(d.o.editPlaceholder).slideUp(d.o.editSpeed, function () {
                            d._saveSettingsWidget()
                        })
                    }
                    else {
                        a(this).children().removeClass(d.editClass[0]).addClass(d.editClass[1]).parents(d.o.widgets).find(d.o.editPlaceholder).slideDown(d.o.editSpeed)
                    }
                    "function" == typeof d.o.onEdit && d.o.onEdit.call(this, c),
                        b.stopPropagation();
                    b.preventDefault()
                }),
                a(d.o.editPlaceholder).find("input").keyup(function () {
                    a(this).parents(d.o.widgets).children("header").children("h2").text(a(this).val())
                }),
                d.widget.on(clickEvent, "[data-widget-setstyle]", function (b) {
                    var c = a(this).data("widget-setstyle"),
                        e = "";
                    a(this).parents(d.o.editPlaceholder).find("[data-widget-setstyle]").each(function () {
                        e += a(this).data("widget-setstyle") + " "
                    }),
                        a(this).parents(d.o.widgets).attr("data-widget-attstyle", "" + c).removeClassPrefix("jarviswidget-color-").addClass(c),
                        d._runLoaderWidget(a(this)),
                        d._saveSettingsWidget(),
                        b.stopPropagation();
                    b.preventDefault()
                }),
                d.widget.on(clickEvent, ".jarviswidget-custom-btn", function (b) {
                    var c = a(this).parents(d.o.widgets);
                    d._runLoaderWidget(a(this));
                    if (a(this).children("." + d.customClass[0]).length) {
                        a(this).children().removeClass(d.customClass[0]).addClass(d.customClass[1]),
                        "function" == typeof d.o.customStart && d.o.customStart.call(this, c)
                    }
                    else {
                        a(this).children().removeClass(d.customClass[1]).addClass(d.customClass[0]),
                        "function" == typeof d.o.customEnd && d.o.customEnd.call(this, c)
                    }
                    d._saveSettingsWidget(),
                        b.stopPropagation();
                    b.preventDefault()
                }),
                d.widget.on(clickEvent, ".jarviswidget-delete-btn", function (b) {
                    var c = a(this).parents(d.o.widgets),
                        e = c.attr("id"),
                        f = c.children("header").children("h2").text();
                    if (a.SmartMessageBox) {
                        a.SmartMessageBox({
                            title: "<i class='fa fa-times' style='color:#ed1c24'></i> " + d.o.labelDelete + ' "' + f + '"',
                            content: "Warning: This action cannot be undone",
                            buttons: "[No][Yes]"
                        }, function (b) {
                            "Yes" == b && (d._runLoaderWidget(a(this)),
                                a("#" + e).fadeOut(d.o.deleteSpeed, function () {
                                    a(this).remove(),
                                    "function" == typeof d.o.onDelete && d.o.onDelete.call(this, c)
                                }))
                        })
                    }
                    else {
                        a("#" + e).fadeOut(d.o.deleteSpeed, function () {
                            a(this).remove(),
                            "function" == typeof d.o.onDelete && d.o.onDelete.call(this, c)
                        })
                    }
                    b.stopPropagation();
                    b.preventDefault()
                }),
                d.widget.on(clickEvent, ".jarviswidget-refresh-btn", function (b) {
                    var c = a(this).parents(d.o.widgets),
                        e = c.data("widget-load"),
                        f = c.children(),
                        g = a(this);
                    g.button("loading"),
                        f.addClass("widget-body-ajax-loading"),
                        setTimeout(function () {
                            g.button("reset"),
                                f.removeClass("widget-body-ajax-loading"),
                                d._loadAjaxFile(c, e, f)
                        }, 1e3),
                        b.stopPropagation();
                    b.preventDefault()
                })
        },
        destroy: function () {
            var a = this;
            a.widget.off("click", a._clickEvents()),
                a.obj.removeData(f)
        }
    },
        a.fn[f] = function (b) {
            return this.each(function () {
                var c = a(this),
                    d = c.data(f),
                    g = "object" == typeof b && b;
                d || c.data(f, d = new e(this, g)),
                "string" == typeof b && d[b]()
            })
        },
        a.fn[f].defaults = {
            grid: "section",
            widgets: ".jarviswidget",
            localStorage: !0,
            deleteSettingsKey: "",
            settingsKeyLabel: "Reset settings?",
            deletePositionKey: "",
            positionKeyLabel: "Reset position?",
            sortable: !0,
            buttonsHidden: !1,
            toggleButton: !0,
            toggleClass: "min-10 | plus-10",
            toggleSpeed: 200,
            onToggle: function () {
            },
            deleteButton: !0,
            deleteClass: "trashcan-10",
            deleteSpeed: 200,
            onDelete: function () {
            },
            editButton: !0,
            editPlaceholder: ".jarviswidget-editbox",
            editClass: "pencil-10 | delete-10",
            editSpeed: 200,
            onEdit: function () {
            },
            colorButton: !0,
            fullscreenButton: !0,
            fullscreenClass: "fullscreen-10 | normalscreen-10",
            fullscreenDiff: 3,
            onFullscreen: function () {
            },
            customButton: !0,
            customClass: "",
            customStart: function () {
            },
            customEnd: function () {
            },
            buttonOrder: "%refresh% %delete% %custom% %edit% %fullscreen% %toggle%",
            opacity: 1,
            dragHandle: "> header",
            placeholderClass: "jarviswidget-placeholder",
            indicator: !0,
            indicatorTime: 600,
            ajax: !0,
            loadingLabel: "loading...",
            timestampPlaceholder: ".jarviswidget-timestamp",
            timestampFormat: "Last update: %m%/%d%/%y% %h%:%i%:%s%",
            refreshButton: !0,
            refreshButtonClass: "refresh-10",
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
            ajaxnav: !0
        },
        a.fn.removeClassPrefix = function (b) {
            this.each(function (c, d) {
                var e = d.className.split(" ").map(function (a) {
                    return 0 === a.indexOf(b) ? "" : a
                });
                d.className = a.trim(e.join(" "))
            })
            return this;
        }
}(jQuery, window, document);
/*
pageButton 为指定元素设置分页控件  示例:$("#id").pageButton({...})
    opt配置
        pageIndex 初始化页索引
        pageSize 每页显示数量 可以是一个数字,或者是一个value值为数字字符串的下拉框(select)列表
        recordCount 总条数
        maskTable 要被遮罩的元素ID  不包含#号
        numCount 显示页数字按钮数量
        showFirstLast 是否显示首页/尾页 按钮
        showPrevNext 是否显示上一页/下一页按钮
        showPoint 是否显示前一页段/后一页段的"..."按钮
        searchBtn 按钮ID 不带#号的字符串
        autoLoad 是否自动开始读取 默认为true
        btnClass 要给分页按钮添加的class样式
        onPageChange(callback) 当前页更改时触发的事件
            this指针:(object) 包含分页信息
                pageIndex:当前页索引
                pageSize:当前页大小
                recordCount:总行数
                pageCount:总页数
                numCount:显示页的个数
                triggerType:触发此事件时的对象,可能的值如下(小写):
                    pagefirst:页首
                    pageprev:上一页
                    pagenum:分页数字(点击省略号点时也是此值)
                    pagenext:下一页
                    pagelast:页尾
                    inputnum:输入框
                    pagesizechange:分页大小更改
                    searchbutton:搜索按钮
                    reload:重新读取
            callback(recordCount) 一个回调函数,此函数在数据重新加载成功后调用
                recordCount 总行数

reloadPage 为指定分页控件重新读取数据,前提,必须先为此元素调用pageButton函数,此函数才有效 示例:$("#id").reloadPage(changeRecord)
    changeRecord:可以是一个int值 总行数更改的条数 增加行数为正数 减少行数为负数  总行数未更改为0
                 也可以是一个bool值,为true则强制执行刷新,为false,完全不执行
*/
(function ($) {
    var tbMask;
    if (window.formLoading) {
        tbMask = new formLoading();
    }
    //对pageSize/pageCount/pageIndex进行计算和限制
    var _reCalcSettings = function (self, options) {
        var settings = self.data("settings");
        if (typeof (settings.pageSize) === 'undefined' && options.pageSize && typeof (options.pageSize) === 'number') {
            settings.pageSize = options.pageSize;
        }
        else if (options.pageSize && typeof (options.pageSize) === 'string' && $("#" + options.pageSize).is("select")) {
            var pgSize = $.trim($("#" + options.pageSize).eq(0).val()).replace(/^0+/, "");
            if (/^[0-9]+$/.test(pgSize)) {
                settings.pageSize = parseInt(pgSize);
            }
            else {
                settings.pageSize = 10;
            }
        }
        else if (typeof (settings.pageSize) === 'undefined') {
            settings.pageSize = 10;
        }
        if (settings.recordCount > 0) {
            settings.pageCount = Math.ceil(settings.recordCount / settings.pageSize);
            if (typeof (settings.pageIndex) === 'undefined' || settings.pageIndex < 0) {
                settings.pageIndex = 0;
            }
            else if (settings.pageIndex >= settings.pageCount) {
                settings.pageIndex = settings.pageCount - 1;
            }
        }
        else {
            settings.pageIndex = 0;
        }
        self.data("settings", settings);
    };

    var showPager = function (self, options) {
        var settings = self.data("settings");
        var ulList = self.find(".pagination");
        ulList.empty();
        if (settings.recordCount > 0) {
            if (options.pageSize && typeof (options.pageSize) === 'string') {
                $("#" + options.pageSize).show(100);
            }
            self.find(".dataTables_info").html('显示<span class="numStart"></span>-<span class="numEnd"></span>条. 第<span class="nowPage"></span>页, 共<span class="pageCount"></span>页');
            if (options.showFirstLast !== false) {
                ulList.append('<li' + (settings.pageIndex == 0 ? ' class="disabled"' : '') + '><a pType="pg_first" href="javascript:void(0)"><i class="fa fa-angle-double-left"></i></a></li>');
            }
            if (options.showPrevNext !== false) {
                ulList.append('<li' + (settings.pageIndex == 0 ? ' class="disabled"' : '') + '><a pType="pg_prev" href="javascript:void(0)"><i class="fa fa-angle-left"></i></a></li>');
            }
            var isShowPrevPoint = options.showPoint !== false;
            var isShowNextPoint = options.showPoint !== false;
            var showStartNum = (settings.pageIndex + 1) - Math.floor(settings.numCount / 2);
            if (showStartNum <= 1) {
                isShowPrevPoint = false;
                showStartNum = 1;
            }
            var showLastNum = showStartNum + settings.numCount - 1;
            if (showLastNum >= settings.pageCount) {
                showLastNum = settings.pageCount;
                var tempStart = showLastNum - settings.numCount + 1;
                if (tempStart > 0) {
                    showStartNum = tempStart;
                }
                if (showStartNum <= 1) {
                    isShowPrevPoint = false;
                    showStartNum = 1;
                }
                isShowNextPoint = false;
            }

            if (isShowPrevPoint) {
                ulList.append('<li><a pType="pg_prevpi" href="javascript:void(0)">...</a></li>');
            }

            for (var i = showStartNum; i <= showLastNum; ++i) {
                ulList.append('<li' + (settings.pageIndex + 1 == i ? ' class="active"' : '') + '><a pType="pg_num" href="javascript:void(0)">' + i.toString() + '</a></li>');
            }

            if (isShowNextPoint) {
                ulList.append('<li><a pType="pg_nextpi" href="javascript:void(0)">...</a></li>');
            }

            if (options.showPrevNext !== false) {
                ulList.append('<li' + (settings.pageIndex + 1 >= settings.pageCount ? ' class="disabled"' : '') + '><a pType="pg_next" href="javascript:void(0)"><i class="fa fa-angle-right"></i></a></li>');
            }
            if (options.showFirstLast !== false) {
                ulList.append('<li' + (settings.pageIndex + 1 >= settings.pageCount ? ' class="disabled"' : '') + '><a pType="pg_last" href="javascript:void(0)"><i class="fa fa-angle-double-right"></i></a></li>');
            }
            self.find(".numStart").text(settings.pageIndex * settings.pageSize + 1);
            var numEnd = (settings.pageIndex + 1) * settings.pageSize;
            if (numEnd > settings.recordCount) {
                numEnd = settings.recordCount;
            }
            self.find(".numEnd").text(numEnd);
            self.find(".nowPage").text(settings.pageIndex + 1);
            self.find(".pageCount").text(settings.pageCount);
            if (options.showPageInput) {
                self.find("input[type='text']").css("display", "inline-block");
            }
            if (options.btnClass) {
                ulList.find("a").addClass(options.btnClass);
            }
        }
        else {
            if (options.pageSize && typeof (options.pageSize) === 'string') {
                $("#" + options.pageSize).hide(100);
            }
            self.find(".dataTables_info").text("没有数据");
            self.find("input[type='text']").css("display", "none");
        }
    };

    var callbackPage = function (self, options) {
        if (tbMask) {
            tbMask.show("正在努力加载...");
        }
        _reCalcSettings(self, options);
        var settings = self.data("settings");
        var sts = {
            pageIndex: settings.pageIndex,
            pageSize: settings.pageSize,
            recordCount: settings.recordCount,
            pageCount: settings.pageCount,
            numCount: settings.numCount,
            triggerType: settings.triggerType
        };
        options.onPageChange.apply(sts, [function (recordCount) {
            if (tbMask) {
                tbMask.hide();
            }
            if (typeof (recordCount) === 'number' && recordCount != settings.recordCount) {
                settings.recordCount = recordCount;
                _reCalcSettings(self, options);
                showPager(self, options);
            }
        }])
    };
    $.extend($.fn, {
        pageButton: function (options) {
            var self = $(this).eq(0);
            if (self.length == 0) {
                return this;
            }
            if (!options) {
                return this;
            }
            if (typeof (options.onPageChange) !== 'function') {
                options.onPageChange = function () {
                }
            }
            if (!options.nonDataHtml) {
                options.nonDataHtml = "没有数据";
            }

            self.empty();
            self.addClass("clearfix");
            self.html('<div class="col-sm-6 col-xs-12"><div class="dataTables_info"></div></div><div class="col-xs-12 col-sm-6"><div class="dataTables_paginate paging_simple_numbers"><ul class="pagination pagination-sm"></ul></div></div>');

            var settings = {
                pageIndex: undefined,//存储当前页
                pageSize: undefined,//存储每页显示数量
                recordCount: 0,//存储总条数
                pageCount: 0,//存储总页数
                numCount: 5,//存储显示页数字的个数
                triggerType: ""//存储触发的分页刷新时的触发类型
            };

            if (options.showPageInput) {
                self.find(".dataTables_paginate").append('<input type="text" class="input-mini" maxlength="10" style="margin:-24px 0 0 10px;display:none;"/><button type="button" class="btn btn-mini" style="margin:-24px 0px 0px 8px;">GO <i class="icon-arrow-right"></i></button>');
            }
            if (options.numCount && typeof (options.numCount) === 'number') {

                settings.numCount = options.numCount;
            }

            if (options.recordCount && typeof (options.recordCount) === 'number') {
                settings.recordCount = options.recordCount;
            }

            if (options.pageIndex && typeof (options.pageIndex) === 'number') {
                settings.pageIndex = options.pageIndex;
            }
            self.data("settings", settings);
            _reCalcSettings(self, options);


            self.on('click', ".pagination a", function () {
                if ($(this).parent().hasClass("disabled") || $(this).parent().hasClass("active")) {
                    return false;
                }
                var pType = $(this).attr("pType");
                switch (pType) {
                    case "pg_first":
                        settings.pageIndex = 0;
                        settings.triggerType = "pagefirst";
                        break;
                    case "pg_prev":
                        --settings.pageIndex;
                        if (settings.pageIndex < 0) {
                            settings.pageIndex = 0;
                        }
                        settings.triggerType = "pageprev";
                        break;
                    case "pg_prevpi":
                        settings.triggerType = "pagenum";
                        settings.pageIndex -= settings.numCount;
                        break;
                    case "pg_num":
                        settings.triggerType = "pagenum";
                        settings.pageIndex = parseInt($(this).text()) - 1;
                        break;
                    case "pg_nextpi":
                        settings.triggerType = "pagenum";
                        settings.pageIndex += settings.numCount;
                        break;
                    case "pg_next":
                        settings.triggerType = "pagenext";
                        ++settings.pageIndex;
                        break;
                    case "pg_last":
                        settings.triggerType = "pagelast";
                        settings.pageIndex = settings.pageCount - 1;
                        break;
                }
                if (settings.pageIndex < 0) {
                    settings.pageIndex = 0;
                }
                else if (settings.pageIndex >= settings.pageCount) {
                    settings.pageIndex = settings.pageCount - 1;
                }
                showPager(self, options);
                callbackPage(self, options);
                return false;
            });

            self.on("click", "button", function () {
                var val = $.trim($(this).prev().val().replace(/^0+/, ""));
                if (/^[0-9]+$/.test(val)) {
                    var pageIdx = parseInt(val) - 1;
                    if (pageIdx >= settings.pageCount) {
                        pageIdx = settings.pageCount - 1;
                    }
                    if (pageIdx <= 0) {
                        pageIdx = 0;
                    }
                    if (pageIdx != settings.pageIndex) {
                        settings.pageIndex = pageIdx;
                        settings.triggerType = "inputnum";
                        showPager(self, options);
                        callbackPage(self, options);
                    }
                    $(this).prev().val("");
                }
            });

            self.on("keypress", ".pagination input[type='text']", function (e) {
                var code = e.charCode && typeof (e.charCode) === 'number' ? e.charCode : e.keyCode;
                if (code >= 48 && code <= 57) {
                    return true;
                }
                else if (code == 13) {
                    $(this).next().click();
                }
                else if (code == 8) {
                    return true;
                }
                else {
                    return false;
                }
            });

            if (options.pageSize && typeof (options.pageSize) === 'string') {
                $("#" + options.pageSize).on("change", function () {
                    settings.triggerType = "pagesizechange";
                    _reCalcSettings(self, options);
                    showPager(self, options);
                    callbackPage(self, options);
                })
            }
            showPager(self, options);
            if (options.searchBtn && typeof (options.searchBtn) === 'string' && /^[0-9a-z_]+$/i.test(options.searchBtn)) {
                $("#" + options.searchBtn).on("click", function () {
                    settings.pageIndex = 0;
                    settings.triggerType = "searchbutton";
                    _reCalcSettings(self, options);
                    showPager(self, options);
                    callbackPage(self, options);
                });
            }
            if (options.autoLoad !== false)
                callbackPage(self, options);
            self.data("options", options);
            return self;
        },
        reloadPage: function (changeRecord) {
            var self = $(this).eq(0);
            if (self.length == 0) {
                return;
            }
            var settings = self.data("settings");
            settings.triggerType = "reload";
            var options = self.data("options");
            if (settings && options) {
                if (changeRecord && typeof (changeRecord) === 'number') {
                    var newRecord = settings.recordCount + changeRecord;
                    if (newRecord != settings.recordCount) {
                        //如果不是最后一页 或者 最后一页又无数据时,执行更新
                        if (settings.pageIndex + 1 < settings.pageCount || (newRecord < settings.recordCount && newRecord - (settings.pageIndex * settings.pageSize))) {
                            settings.recordCount = newRecord;
                            self.data("settings", settings);
                            _reCalcSettings(self, options);
                            showPager(self, options);
                            callbackPage(self, options);
                        }
                        else {
                            settings.recordCount = newRecord;
                            self.data("settings", settings);
                            _reCalcSettings(self, options);
                            showPager(self, options);
                            self.find(".numStart").text(settings.pageIndex * settings.pageSize + 1);
                            var numEnd = (settings.pageIndex + 1) * settings.pageSize;
                            if (numEnd > settings.recordCount) {
                                numEnd = settings.recordCount;
                            }
                            self.find(".numEnd").text(numEnd);
                            self.find(".nowPage").text(settings.pageIndex + 1);
                            self.find(".pageCount").text(settings.pageCount);
                        }
                    }
                }
                else if (typeof (changeRecord) === 'boolean') {
                    if (changeRecord) {
                        showPager(self, options);
                        callbackPage(self, options);
                    }
                }
            }
        },
        jumpPage: function (pageIndex, isLoad) {
            var self = $(this).eq(0);
            if (self.length == 0) {
                return;
            }
            var settings = self.data("settings");
            settings.triggerType = "setpage";
            var options = self.data("options");
            settings.pageIndex = pageIndex;
            _reCalcSettings(self, options);
            showPager(self, options);
            if (isLoad === true) {
                callbackPage(self, options);
            }
        },
        clearPage: function () {
            var self = $(this).eq(0);
            if (self.length == 0) {
                return;
            }
            var settings = self.data("settings");
            settings.triggerType = "clear";
            var options = self.data("options");
            settings.pageIndex = 0;
            settings.recordCount = 0;
            _reCalcSettings(self, options);
            showPager(self, options);
        }
    })
})(jQuery)
/*!
** Unobtrusive Ajax support library for jQuery
** Copyright (C) Microsoft Corporation. All rights reserved.
*/

/*jslint white: true, browser: true, onevar: true, undef: true, nomen: true, eqeqeq: true, plusplus: true, bitwise: true, regexp: true, newcap: true, immed: true, strict: false */
/*global window: false, jQuery: false */

(function ($) {
    var data_click = "unobtrusiveAjaxClick",
        data_validation = "unobtrusiveValidation";

    function getFunction(code, argNames) {
        var fn = window, parts = (code || "").split(".");
        while (fn && parts.length) {
            fn = fn[parts.shift()];
        }
        if (typeof (fn) === "function") {
            return fn;
        }
        argNames.push(code);
        return Function.constructor.apply(null, argNames);
    }

    function isMethodProxySafe(method) {
        return method === "GET" || method === "POST";
    }

    function asyncOnBeforeSend(xhr, method) {
        if (!isMethodProxySafe(method)) {
            xhr.setRequestHeader("X-HTTP-Method-Override", method);
        }
    }

    function asyncOnSuccess(element, data, contentType) {
        var mode;

        if (contentType.indexOf("application/x-javascript") !== -1) {  // jQuery already executes JavaScript for us
            return;
        }

        mode = (element.getAttribute("data-ajax-mode") || "").toUpperCase();
        $(element.getAttribute("data-ajax-update")).each(function (i, update) {
            var top;

            switch (mode) {
                case "BEFORE":
                    top = update.firstChild;
                    $("<div />").html(data).contents().each(function () {
                        update.insertBefore(this, top);
                    });
                    break;
                case "AFTER":
                    $("<div />").html(data).contents().each(function () {
                        update.appendChild(this);
                    });
                    break;
                default:
                    $(update).html(data);
                    break;
            }
        });
    }

    function asyncRequest(element, options) {
        var confirm, loading, method, duration;

        confirm = element.getAttribute("data-ajax-confirm");
        if (confirm && !window.confirm(confirm)) {
            return;
        }

        loading = $(element.getAttribute("data-ajax-loading"));
        duration = element.getAttribute("data-ajax-loading-duration") || 0;

        var fLoading;
        $.extend(options, {
            type: element.getAttribute("data-ajax-method") || undefined,
            url: element.getAttribute("data-ajax-url") || undefined,
            beforeSend: function (xhr) {
                var result;
                asyncOnBeforeSend(xhr, method);
                result = getFunction(element.getAttribute("data-ajax-begin"), ["xhr"]).apply(this, arguments);
                if (result === false) {
                    fLoading.hide(duration);
                }
                return result;
            },
            complete: function () {
                //loading.hide(duration);
                fLoading.setProgress(1, 1);
                fLoading.setToolText("已处理完成");
                setTimeout(function () {
                    fLoading.hide();
                }, 1000);
                getFunction(element.getAttribute("data-ajax-complete"), ["xhr", "status"]).apply(this, arguments);
            },
            success: function (data, status, xhr) {
                asyncOnSuccess(element, data, xhr.getResponseHeader("Content-Type") || "text/html");
                getFunction(element.getAttribute("data-ajax-success"), ["data", "status", "xhr"]).apply(this, arguments);
            },
            error: getFunction(element.getAttribute("data-ajax-failure"), ["xhr", "status", "error"])
        });

        options.data.push({name: "X-Requested-With", value: "XMLHttpRequest"});

        method = options.type.toUpperCase();
        if (!isMethodProxySafe(method)) {
            options.type = "POST";
            options.data.push({name: "X-HTTP-Method-Override", value: method});
        }

        var $fs = $(element).find(":file:not(:disabled)");
        if (method == "POST" && $fs.length > 0 && ($(element)[0].enctype || "").toLowerCase() == 'multipart/form-data') {
            fLoading = new formLoading({toolType: "progress"});
            var xhr = new XMLHttpRequest();
            xhr.onreadystatechange = function () {
                switch (xhr.readyState) {
                    case 1:
                        fLoading.show("正在开始请求...");
                        break;
                    case 2:
                        break;
                    case 3:
                        fLoading.setToolText("正在等待服务器响应...");
                        break;
                    case 4:
                        var isSuc = false;
                        var statusText = "";
                        var response = xhr.responseText;
                        if (xhr.status == 200) {
                            isSuc = true;
                            statusText = "success";
                            try {
                                response = $.parseJSON(response);
                            }
                            catch (e) {

                            }
                        }
                        else if (xhr.status == 204) {
                            isSuc = true;
                            statusText = "nocontent";
                        }
                        else if (xhr.status == 304) {
                            isSuc = true;
                            statusText = "notmodified";
                        }
                        else {
                            isSuc = false;
                            statusText = xhr.statusText;
                            if (!statusText) {
                                statusText = "error";
                            }
                        }
                        var rst = "";
                        if (isSuc) {
                            options.success.apply(options, [response, statusText, xhr]);
                        }
                        else {
                            options.error.apply(options, [xhr, xhr.statusText, statusText]);
                        }
                        options.complete.apply(options, [xhr, statusText]);
                        break;
                }
            };
            xhr.upload.addEventListener("progress", function (evt) {
                fLoading.setProgress(evt.loaded, evt.total);
                fLoading.setToolText("正在上传...");
            }, false);
            if (options.beforeSend.apply(options, [xhr]) === false) {
                xhr.abort();
                xhr = null;
                return;
            }
            xhr.open(options.type, options.url, true);
            var fData = new FormData();
            for (var i = 0; i < options.data.length; ++i) {
                fData.append(options.data[i].name, options.data[i].value || '');
            }
            $fs.each(function () {
                var dFile = $(this)[0];
                if (dFile.multiple) {
                    for (var fIdx = 0; fIdx < dFile.files.length; ++fIdx) {
                        fData.append(dFile.name, dFile.files[fIdx] || '');
                    }
                }
                else {
                    fData.append(dFile.name, dFile.files[0] || '');
                }
            });
            xhr.send(fData);
        }
        else {
            fLoading = new formLoading();
            fLoading.show("正在提交数据...");
            $.ajax(options);
        }
    }

    function validate(form) {
        var validationInfo = $(form).data(data_validation);
        return !validationInfo || !validationInfo.validate || validationInfo.validate();
    }

    $(document).on("click", "a[data-ajax=true]", function (evt) {
        evt.preventDefault();
        asyncRequest(this, {
            url: this.href,
            type: "GET",
            data: []
        });
    });

    $(document).on("click", "form[data-ajax=true] input[type=image]", function (evt) {
        var name = evt.target.name,
            $target = $(evt.target),
            form = $target.parents("form")[0],
            offset = $target.offset();

        $(form).data(data_click, [
            {name: name + ".x", value: Math.round(evt.pageX - offset.left)},
            {name: name + ".y", value: Math.round(evt.pageY - offset.top)}
        ]);

        setTimeout(function () {
            $(form).removeData(data_click);
        }, 0);
    });

    $(document).on("click", "form[data-ajax=true] :submit", function (evt) {
        var name = evt.target.name,
            form = $(evt.target).parents("form")[0];

        $(form).data(data_click, name ? [{name: name, value: evt.target.value}] : []);

        setTimeout(function () {
            $(form).removeData(data_click);
        }, 0);
    });

    $(document).on("submit", "form[data-ajax=true]", function (evt) {
        var clickInfo = $(this).data(data_click) || [];
        evt.preventDefault();
        if (!validate(this)) {
            return;
        }
        asyncRequest(this, {
            url: this.action,
            type: this.method || "GET",
            data: clickInfo.concat($(this).serializeArray())
        });
    });
}(jQuery));
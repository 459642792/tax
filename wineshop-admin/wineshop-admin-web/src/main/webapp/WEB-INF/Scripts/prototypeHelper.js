Number.prototype.bindToString = function (format) {
    format = format ? format : "";
    var ts = this.toString();
    if (format.indexOf(".") < 0) {
        if (ts.length >= format.length) {
            return ts;
        }
        else {
            return format.substring(0, format.length - ts.length) + ts;
        }
    }
    else {
        var arrFormat = format.split(".");
        if (arrFormat.length != 2) {
            throw "Invalid format";
        }
        var prevP = Math.floor(this);
        var nextP = (this - prevP).toString().substring(2);
        return prevP.bindToString(arrFormat[0]) + "." + (arrFormat[1].length > nextP.length ? nextP + arrFormat[1].substring(nextP.length) : nextP);
    }
}

Date.prototype.bindToString = function (format) {
    if (format && typeof (format) === 'string' && format.length > 0) {
        var y = this.getFullYear();
        var M = this.getMonth() + 1;
        var d = this.getDate();
        var h = this.getHours();
        var hi = h > 12 ? h - 12 : h;
        var t = h >= 12 ? "下午" : "上午";
        var m = this.getMinutes();
        var s = this.getSeconds();
        var f = this.getMilliseconds();
        var w = this.getDay();
        var cnW = ["星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];
        var rFormat = format;
        rFormat = rFormat.replace(/yyyy/gi, y.bindToString("0000"));
        rFormat = rFormat.replace(/yy/gi, y.bindToString("0000").substring(2));
        rFormat = rFormat.replace(/MM/g, M.bindToString("00"));
        rFormat = rFormat.replace(/M/g, M.toString());
        rFormat = rFormat.replace(/dd/g, d.bindToString("00"));
        rFormat = rFormat.replace(/d/g, d.toString());
        rFormat = rFormat.replace(/HH/g, h.bindToString("00"));
        rFormat = rFormat.replace(/H/g, h.toString());
        rFormat = rFormat.replace(/hh/g, hi.bindToString("00"));
        rFormat = rFormat.replace(/h/g, hi.toString());
        rFormat = rFormat.replace(/mi|mm/gi, m.bindToString("00"));
        rFormat = rFormat.replace(/m/g, m.toString());
        rFormat = rFormat.replace(/ss/g, s.bindToString("00"));
        rFormat = rFormat.replace(/s/g, s.toString());
        rFormat = rFormat.replace(/(f){1,4}/g, function (a, b, c) {
            return c.bindToString("0000".substring(0, a.length));
        });
        rFormat = rFormat.replace(/tt|t/g, t);
        rFormat = rFormat.replace(/w/gi, cnW[w]);
        return rFormat;
    }
    else {
        return this.toString();
    }
}
String.prototype.bindToString = function (format) {
    if (format && typeof (format) === 'string' && format.length > 0) {
        var regNum = /^[0-9]+(\.[0-9]+)?$/;
        if (regNum.test(this)) {
            return parseFloat(this).bindToString(format);
        }
        var dat = this.tryParseDate();
        if (dat instanceof Date)
            return dat.bindToString(format);
    }
    return this;
}

String.prototype.tryParseDate = function () {
    var regDate = /^([0-9]{4})[-\/\.]([0-9]{1,2})[-\/\.]([0-9]{1,2})(T|GMT)([0-9]{1,2}):([0-9]{1,2}):([0-9]{1,2})(\.([0-9]{1,3}))?([0-9]+)?(\+[0-9]{1,2}:[0-9]{1,2})?$/i;
    var arrReg = regDate.exec(this);
    if (arrReg) {
        var month = arrReg[2], date = arrReg[3], hour = arrReg[5] || "0", min = arrReg[6] || "0",
            sec = arrReg[7] || "0", mil = arrReg[9] || "0";
        var dt = new Date(parseInt(arrReg[1], 10), parseInt(arrReg[2], 10) - 1, parseInt(arrReg[3], 10), parseInt(arrReg[5] || "0", 10), parseInt(arrReg[6] || "0", 10), parseInt(arrReg[7] || "0", 10), parseInt(arrReg[9] || "0", 10));
        return dt;
    }
    regDate = /Date\(([0-9]+)\)/i;
    arrReg = regDate.exec(this);
    if (arrReg) {
        return new Date(parseInt(arrReg[1], 10));
    }
    return null;
};

String.prototype.startWith = function (prefix) {
    return this.substring(0, 1) == prefix;
}

//判断指定值是否存在该数组中
Array.prototype.contains = function (data) {
    for (var i = 0; i < this.length; ++i) {
        if (this[i] == data) {
            return true;
        }
    }
    return false;
}


Date.prototype.date = function () {
    /// <summary>
    /// 获取当前日期的日期部分
    /// </summary>
    /// <returns type="Date" />
    return new Date(this.getFullYear(), this.getMonth(), this.getDate());
}
commonHelper = {
    encodeHtml: function (html) {
        if (!html)
            return "";
        return html.replace(/&/g, "&amp;").replace(/\</g, "&lt;").replace(/\>/g, "&gt;").replace(/ /, "&nbsp;").replace(/\r\n/g, "<br/>").replace(/\n/g, "<br/>");
    },
    decodeHtml: function (text) {
        if (!text)
            return "";
        return text.replace(/\<br *\/?\>/gi, "\r\n").replace(/&nbsp;/gi, " ").replace(/&gt;/gi, ">").replace(/&lt;/gi, "<").replace(/&amp;/gi, "&");
    },
    getQuery: function (query, callback) {
        /// <summary>
        /// 通用查询接口
        /// </summary>
        /// <param name="query" type="DbBuildResult">查询条件</param>
        /// <param name="callback" type="Function(bool,[resultData])">
        /// <para>回调函数</para>
        /// <para>bool:(Boolean)是否获取成功</para>
        /// <para>[resultData]:(Object)当获取成功时,此参数为获取到的数据</para>
        /// </param>
        /// <returns type="Void" />
        if (typeof (callback) != 'function')
            callback = function () {
            };
        if (query instanceof DbBuildResult) {
            $.ajax({
                type: "post",
                url: "/api/DataSearch/Search",
                dataType: "json",
                data: query,
                success: function (data) {
                    callback(true, data);
                },
                error: function () {
                    callback(false);
                }
            });
        }
        else {
            throw 'the query parameter is not instance of DbBuildResult';
        }
    },
    cloneObject: function (obj) {
        /// <summary>
        /// 克隆对象
        /// </summary>
        /// <param name="obj" type="Object">要被克隆的对象</param>
        /// <returns type="Object" />
        var o;
        switch (typeof obj) {
            case 'undefined':
                break;
            case 'string':
                o = obj + '';
                break;
            case 'number':
                o = obj - 0;
                break;
            case 'boolean':
                o = obj;
                break;
            case 'object':
                if (obj === null) {
                    o = null;
                } else {
                    if (obj instanceof Array) {
                        o = [];
                        for (var i = 0; i < obj.length; i++) {
                            o.push(commonHelper.cloneObject(obj[i]));
                        }
                    } else {
                        o = {};
                        for (var k in obj) {
                            o[k] = commonHelper.cloneObject(obj[k]);
                        }
                    }
                }
                break;
            default:
                o = obj;
                break;
        }
        return o;
    }
}
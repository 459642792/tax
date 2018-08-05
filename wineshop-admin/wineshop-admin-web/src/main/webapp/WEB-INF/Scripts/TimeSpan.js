(function () {
    var numberToString = function (num, format) {
        format = format ? format : "";
        var ts = (num || "").toString();
        if (!format)
            return num.toString();
        if (ts.length >= format.length) {
            return ts;
        }
        else {
            return format.substring(0, format.length - ts.length) + ts;
        }
    };
    window.TimeSpan = function () {
        /// <signature>
        ///     <summary>
        ///     构造一个00:00的时间间隔
        ///     </summary>
        ///     <returns type="TimeSpan" />
        /// </signature>
        /// <signature>
        ///     <summary>
        ///     以指定"dd.HH:mm:ss.ffff"格式构造一个时间间隔
        ///     </summary>
        ///     <param name="time" type="String">"dd.HH:mm:ss.ffff"格式的字符串，HH和mm为必须</param>
        ///     <returns type="TimeSpan" />
        /// </signature>
        /// <signature>
        ///     <summary>
        ///     以指定时间间隔构造一个新的时间间隔
        ///     </summary>
        ///     <param name="timeSpan" type="TimeSpan">时间间隔</param>
        ///     <returns type="TimeSpan" />
        /// </signature>
        /// <signature>
        ///     <summary>
        ///     以指定总毫秒数构造一个时间间隔
        ///     </summary>
        ///     <param name="totalMS" type="Number">总毫秒数</param>
        ///     <returns type="TimeSpan" />
        /// </signature>
        /// <signature>
        ///     <summary>
        ///     以指定时、分、秒、毫秒参数构造一个时间间隔
        ///     </summary>
        ///     <param name="days" type="Number">天</param>
        ///     <param name="hours" type="Number">时</param>
        ///     <param name="minutes" type="Number">分</param>
        ///     <param name="seconds" type="Number" optional="true">秒[可选]</param>
        ///     <param name="ms" type="Number" optional="true">毫秒[可选]</param>
        ///     <returns type="TimeSpan" />
        /// </signature>

        //这是实现的一个.Net中的TimeSpan类，一个时间间隔
        //其实现方法是这个时间间隔以最小时间“毫秒”为单位，存储在tif中
        //并在以下执行的各个函数中对tif进行操作或读取
        var tif = 0;//总毫秒
        var sf = this;

        sf.sign = function () {
            /// <summary>
            /// 获取当前时间间隔是正数、负数、零。正数返回1，负数返回-1,零返回0
            /// </summary>
            /// <returns type="Number" />
            if (tif > 0)
                return 1;
            if (tif < 0)
                return -1;
            return 0;
        }

        sf.changeSign = function () {
            /// <summary>
            /// 更改自身时间间隔的正负值
            /// </summary>
            /// <returns type="Void" />
            tif = -tif;
        }

        sf.abs = function () {
            /// <summary>
            /// 获取当前时间间隔的绝对值时间间隔
            /// </summary>
            /// <returns type="TimeSpan" />
            return new TimeSpan(Math.abs(tif));
        }

        sf.getTotalMilliseconds = function () {
            /// <summary>
            /// 获取当前时间间隔的总毫秒数
            /// </summary>
            /// <returns type="Number" />
            return tif;
        }

        sf.getTotalSeconds = function () {
            /// <summary>
            /// 获取当前时间间隔的总秒数
            /// </summary>
            /// <returns type="Number" />
            return tif / 1000;
        }

        sf.getTotalMinutes = function () {
            /// <summary>
            /// 获取当前时间间隔的总分钟数
            /// </summary>
            /// <returns type="Number" />
            return tif / 60000;
        }

        sf.getTotalHours = function () {
            /// <summary>
            /// 获取当前时间间隔的总小时数
            /// </summary>
            /// <returns type="Number" />
            return tif / 3600000;
        }

        sf.getTotalDays = function () {
            /// <summary>
            /// 获取当前时间间隔的总天数
            /// </summary>
            /// <returns type="Number" />
            return tif / 86400000;
        }

        sf.getMilliseconds = function () {
            /// <summary>
            /// 获取当前时间间隔的毫秒部分
            /// </summary>
            /// <returns type="Number" />
            return Math.abs(tif % 1000);
        }

        sf.getSeconds = function () {
            /// <summary>
            /// 获取当前时间间隔的秒部分
            /// </summary>
            /// <returns type="Number" />
            return Math.abs(Math.floor(tif / 1000) % 60);
        }

        sf.getMinutes = function () {
            /// <summary>
            /// 获取当前时间间隔的分钟部分
            /// </summary>
            /// <returns type="Number" />
            return Math.abs(Math.floor(tif / 60000) % 60);
        }

        sf.getHours = function () {
            /// <summary>
            /// 获取当前时间间隔的小时部分
            /// </summary>
            /// <returns type="Number" />
            return Math.abs(Math.floor(tif / 3600000) % 24);
        }

        sf.getDays = function () {
            /// <summary>
            /// 获取当前时间间隔的天数部分
            /// </summary>
            /// <returns type="Number" />
            return Math.abs(Math.floor(tif / 86400000));
        }

        sf.addMilliseconds = function (ms) {
            /// <summary>
            /// 为当前实例添加指定毫秒数，并返回该实例(要减去请传入负数)
            /// </summary>
            /// <returns type="TimeSpan" />
            if (typeof ms === 'number' && !isNaN(ms))
                tif += ms;
            return sf;
        }

        sf.addSeconds = function (s) {
            /// <summary>
            /// 为当前实例添加指定秒数，并返回该实例(要减去请传入负数)
            /// </summary>
            /// <returns type="TimeSpan" />
            if (typeof s === 'number' && !isNaN(s))
                tif += s * 1000;
            return sf;
        }

        sf.addMinutes = function (m) {
            /// <summary>
            /// 为当前实例添加指定分钟数，并返回该实例(要减去请传入负数)
            /// </summary>
            /// <returns type="TimeSpan" />
            if (typeof m === 'number' && !isNaN(m))
                tif += m * 60000;
            return sf;
        }

        sf.addHours = function (h) {
            /// <summary>
            /// 为当前实例添加指定小时数，并返回该实例(要减去请传入负数)
            /// </summary>
            /// <returns type="TimeSpan" />
            if (typeof h === 'number' && !isNaN(h))
                tif += h * 3600000;
            return sf;
        }

        sf.addDays = function (d) {
            /// <summary>
            /// 为当前实例添加指定天数，并返回该实例(要减去请传入负数)
            /// </summary>
            /// <returns type="TimeSpan" />
            if (typeof d === 'number' && !isNaN(d))
                tif += d * 86400000;
            return sf;
        }

        sf.compareTo = function (value) {
            /// <signature>
            ///     <summary>
            ///     将当前时间间隔与指定时间间隔进行比较，大于时为1,小于时为-1,相等为0
            ///     </summary>
            ///     <param name="value" type="TimeSpan">要进行比较的时间间隔</param>
            ///     <returns type="Number" />
            /// </signature>
            /// <signature>
            ///     <summary>
            ///     将当前时间间隔与总毫秒数进行比较，大于时为1,小于时为-1,相等为0
            ///     </summary>
            ///     <param name="value" type="Number">要进行比较的总毫秒数</param>
            ///     <returns type="Number" />
            /// </signature>
            var tf = sf.getTotalMilliseconds();
            var vf;
            if (value instanceof TimeSpan)
                vf = value.getTotalMilliseconds();
            else if (typeof value === 'number' && !isNaN(value))
                vf = value;
            else
                throw 'invalid Parameter';

            if (tf > vf)
                return 1;
            if (tf < vf)
                return -1;
            else
                return 0;
        }

        sf.add = function (value) {
            /// <signature>
            ///     <summary>
            ///     将当前时间间隔与指定时间间隔相加，并返回
            ///     &#10;不更改当前对象
            ///     </summary>
            ///     <param name="value" type="TimeSpan">要进行相加的时间间隔</param>
            ///     <returns type="TimeSpan" />
            /// </signature>
            /// <signature>
            ///     <summary>
            ///     将当前时间间隔与指定时间间隔相加，并返回
            ///     &#10;不更改当前对象
            ///     </summary>
            ///     <param name="value" type="Number">要进行相加的总毫秒数</param>
            ///     <returns type="TimeSpan" />
            /// </signature>
            var f = tif;
            if (value instanceof TimeSpan) {
                f += value.getTotalMilliseconds();
            }
            else if (typeof value === 'number' && !isNaN(value))
                f += value;
            return new TimeSpan(f);
        }

        sf.subtract = function (value) {
            /// <signature>
            ///     <summary>
            ///     将当前时间间隔与指定时间间隔相减，并返回
            ///     &#10;不更改当前对象
            ///     </summary>
            ///     <param name="value" type="TimeSpan">要进行相减的时间间隔</param>
            ///     <returns type="TimeSpan" />
            /// </signature>
            if (value instanceof TimeSpan) {
                return sf.add(-value.getTotalMilliseconds());
            }
            return new TimeSpan(sf);
        }

        sf.toString = function (format) {
            /// <signature>
            ///     <summary>
            ///     将当前时间间隔转换为dd.HH:mm:ss.fff字符串
            ///     </summary>
            ///     <returns type="Number" />
            /// </signature>
            /// <signature>
            ///     <summary>
            ///     将当前时间间隔转换为指定格式的字符串
            ///     </summary>
            ///     <param name="format" type="String">
            ///     格式字符串
            ///     &#10;dd:两位数的'日'(不足补0) 如:09
            ///     &#10;d:一位或两位数的'日' 如:9
            ///     &#10;HH:两位数的24小时制的'时'(不足补0) 如:08
            ///     &#10;H:一位或两位数的24小时制的'时' 如:8
            ///     &#10;hh:两位数的12小时制的'时'(不足补0) 如:08
            ///     &#10;h:一位或两位数的12小时制的'时' 如:8
            ///     &#10;mm:两位数的'分'(不足补0) 如:08
            ///     &#10;mi:两位数的'分'(不足补0) 如:08 与mm相同
            ///     &#10;m:一位或两位数的'分' 如:8
            ///     &#10;ss:两位数的'秒'(不足补0) 如:00
            ///     &#10;s:一位或两位数的'秒' 如:0
            ///     &#10;ffff:表示毫秒,一个1~4位长度的'f'字符串,毫秒补0长度由f字符数决定
            ///     &#10;tt或TT:中文的上午或下午
            ///     &#10;t或T:英文的上午或下午
            ///     </param>
            ///     <returns type="String" />
            /// </signature>
            if (format && typeof (format) === 'string' && format.length > 0) {
                var d = sf.getDays();
                var h = sf.getHours();
                var m = sf.getMinutes();
                var s = sf.getSeconds();
                var ms = sf.getMilliseconds();
                var hi = h > 12 ? h - 12 : h;
                var t = h >= 12 ? "PM" : "AM";
                var tt = h >= 12 ? "下午" : "上午";
                var rFormat = format;
                rFormat = rFormat.replace(/dd/g, numberToString(d, "00"));
                rFormat = rFormat.replace(/d/g, d.toString());
                rFormat = rFormat.replace(/HH/g, numberToString(h, "00"));
                rFormat = rFormat.replace(/H/g, h.toString());
                rFormat = rFormat.replace(/hh/g, numberToString(hi, "00"));
                rFormat = rFormat.replace(/h/g, hi.toString());
                rFormat = rFormat.replace(/mi|mm/gi, numberToString(m, "00"));
                rFormat = rFormat.replace(/m/g, m.toString());
                rFormat = rFormat.replace(/ss/g, numberToString(s, "00"));
                rFormat = rFormat.replace(/s/g, s.toString());
                rFormat = rFormat.replace(/(f){1,4}/g, function (a, b) {
                    return "0000".substring(0, b.length);
                });
                rFormat = rFormat.replace(/tt|TT/g, tt);
                rFormat = rFormat.replace(/t|T/g, t);
                return rFormat;
            }
            else {
                var d = sf.getDays();
                var str = (tif < 0 ? "-" : "") + (d == 0 ? "" : numberToString(sf.getDays(), "00") + ".") + numberToString(sf.getHours(), "00") + ":" + numberToString(sf.getMinutes(), "00") + ":" + numberToString(sf.getSeconds(), "00") + (sf.getMilliseconds() == 0 ? "" : "." + numberToString(sf.getMilliseconds(), "000"));
                var t = str.substring(str.lastIndexOf('.') + 1);
                if (parseInt(t) != 0)
                    return str;
                str = str.substring(0, str.lastIndexOf('.'));

                t = str.substring(str.lastIndexOf(":") + 1);
                if (parseInt(t) != 0)
                    return str;
                str = str.substring(0, str.lastIndexOf(":"));
                return str;
            }
        }

        /*构造函数 开始*/
        if (arguments.length == 1) {
            //对于构造函数参数是String的情况，则通过正则表达式进行匹配，将天 时 分 秒 毫秒匹配出来，其中时和分是必须的部分
            if (typeof (arguments[0]) === 'string') {
                //这个正则表达式匹配的结果中包括天、时、分、秒、毫秒各正则分组
                //组索引      说明
                // 1          正数或负数
                // 3          天
                // 4          时
                // 5          分
                // 7          秒
                // 9          毫秒
                var reg = /^(\+|\-)?(([0-9]+)\.)?([0-9]+):([0-9]{1,2})(:([0-9]{1,2}))?(\.([0-9]{1,3}))?$/;
                var arg1 = arguments[0];
                var regArr = reg.exec(arg1);
                if (!regArr || regArr.length == 0) {
                    throw 'invalid TimeSpan';
                }
                //接下来将匹配出的结果转换为数字并附加到tif这个总毫秒数中
                var t = null;
                if (regArr[3]) {
                    t = parseInt(regArr[2]);
                    tif += t * 86400000;
                }
                t = parseInt(regArr[4]);
                tif += t * 3600000;
                t = parseInt(regArr[5]);
                tif += t * 60000;
                if (regArr[7]) {
                    t = parseInt(regArr[7]);
                    tif += t * 1000;
                }
                if (regArr[9]) {
                    t = parseInt(regArr[8]);
                    tif += t;
                }
                if (regArr[1] === '-')
                    tif = -tif;
            }
            else if (arguments[0] instanceof TimeSpan) {
                tif = arguments[0].getTotalMilliseconds();
            }
            else if (typeof arguments[0] === 'number' && !isNaN(arguments[0])) {
                tif = arguments[0];
            }
            else
                throw 'invalid TimeSpan';
        }
        else if (arguments.length > 0) {
            var sp = [86400000, 3600000, 60000, 1000, 1];
            for (var i = 0; i < sp.length; ++i) {
                if (i >= arguments.length)
                    break;
                if (arguments[i] == null)
                    continue;
                if (typeof (arguments[i]) != 'number') {
                    throw 'invalid TimeSpan';
                }
                tif += arguments[i] * sp[i];
            }
        }
        /*构造函数 结束*/
    }
    Date.prototype.add = function (value) {
        /// <summary>
        /// 将当前Date对象添加指定时间间隔
        /// </summary>
        /// <param name="value" type="TimeSpan">
        /// 时间间隔
        /// </param>
        /// <returns type="Date" />
        var d = new Date(this.getTime());
        if (value instanceof TimeSpan) {
            d.setMilliseconds(d.getMilliseconds() + value.getTotalMilliseconds());
        }
        return d;
    }

    Date.prototype.subtract = function (value) {
        /// <signature>
        ///     <summary>
        ///     将当前日期与指定时间间隔相减，而得到一个新的日期
        ///     </summary>
        ///     <param name="value" type="TimeSpan">要进行相减的时间间隔</param>
        ///     <returns type="Date" />
        /// </signature>
        /// <signature>
        ///     <summary>
        ///     将当前日期与指定日期相减，而得到一个时间间隔
        ///     </summary>
        ///     <param name="value" type="Date">要进行相减的日期</param>
        ///     <returns type="TimeSpan" />
        /// </signature>
        if (value instanceof Date) {
            return new TimeSpan(this.getTime() - value.getTime());
        }
        else if (value instanceof TimeSpan) {
            return new Date(this.getTime() - value.getTotalMilliseconds());
        }
    }

    Date.prototype.date = function () {
        /// <summary>
        /// 获取当前日期的日期部分
        /// </summary>
        /// <returns type="Date" />
        return new Date(this.getFullYear(), this.getMonth(), this.getDate());
    }

    Date.prototype.timeOfDay = function () {
        /// <summary>
        /// 获取当前日期的时、分、秒部分
        /// </summary>
        /// <returns type="TimeSpan" />
        return new TimeSpan(this.getTime() - new Date(this.getFullYear(), this.getMonth(), this.getDate()).getTime());
    }

    Date.prototype.ToString = function (format) {
        /// <summary>
        /// 将日期格式以指定格式转换为字符串
        /// </summary>
        /// <param name="format" type="String">
        /// 要转换的日期格式
        /// &#10;yyyy:完整'年' 如:1949
        /// &#10;yy:两位数'年' 如:49
        /// &#10;MM:两位数的'月'(不足补0) 如:04
        /// &#10;M:一位或两位数的'月' 如:4
        /// &#10;dd:两位数的'日'(不足补0) 如:09
        /// &#10;d:一位或两位数的'日' 如:9
        /// &#10;HH:两位数的24小时制的'时'(不足补0) 如:08
        /// &#10;H:一位或两位数的24小时制的'时' 如:8
        /// &#10;hh:两位数的12小时制的'时'(不足补0) 如:08
        /// &#10;h:一位或两位数的12小时制的'时' 如:8
        /// &#10;mm:两位数的'分'(不足补0) 如:08
        /// &#10;mi:两位数的'分'(不足补0) 如:08 与mm相同
        /// &#10;m:一位或两位数的'分' 如:8
        /// &#10;ss:两位数的'秒'(不足补0) 如:00
        /// &#10;s:一位或两位数的'秒' 如:0
        /// &#10;ffff:表示毫秒,一个1~4位长度的'f'字符串,毫秒补0长度由f字符数决定
        /// &#10;tt或TT:中文的上午或下午
        /// &#10;t或T:英文的上午或下午
        /// &#10;ww或WW:中文的星期
        /// &#10;w或W:英文的星期
        /// </param>
        /// <returns type="String" />

        //该方法以format中的字符为中心，将上面注释中的字符串通过正则表达式的全局模式替换为Date中有意义的值，
        //如果format不存在，则直接返回Date.toString()
        if (format && typeof (format) === 'string' && format.length > 0) {
            var y = this.getFullYear();
            var M = this.getMonth() + 1;
            var d = this.getDate();
            var h = this.getHours();
            var hi = h > 12 ? h - 12 : h;
            var t = h >= 12 ? "PM" : "AM";
            var tt = h >= 12 ? "下午" : "上午";
            var m = this.getMinutes();
            var s = this.getSeconds();
            var f = this.getMilliseconds();
            var w = this.getDay();
            var enW = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
            var cnW = ["星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];
            var rFormat = format;
            rFormat = rFormat.replace(/yyyy/gi, numberToString(y, "0000"));
            rFormat = rFormat.replace(/yy/gi, numberToString(y, "0000").substring(2));
            rFormat = rFormat.replace(/MM/g, numberToString(M, "00"));
            rFormat = rFormat.replace(/M/g, M.toString());
            rFormat = rFormat.replace(/dd/g, numberToString(d, "00"));
            rFormat = rFormat.replace(/d/g, d.toString());
            rFormat = rFormat.replace(/HH/g, numberToString(h, "00"));
            rFormat = rFormat.replace(/H/g, h.toString());
            rFormat = rFormat.replace(/hh/g, numberToString(hi, "00"));
            rFormat = rFormat.replace(/h/g, hi.toString());
            rFormat = rFormat.replace(/mi|mm/gi, numberToString(m, "00"));
            rFormat = rFormat.replace(/m/g, m.toString());
            rFormat = rFormat.replace(/ss/g, numberToString(s, "00"));
            rFormat = rFormat.replace(/s/g, s.toString());
            rFormat = rFormat.replace(/(f){1,4}/g, function (a, b) {
                return "0000".substring(0, b.length);
            });
            rFormat = rFormat.replace(/tt|TT/g, tt);
            rFormat = rFormat.replace(/t|T/g, t);
            rFormat = rFormat.replace(/ww|WW/g, cnW[w]);
            rFormat = rFormat.replace(/w|W/gi, enW[w]);
            return rFormat;
        } else {
            return this.toString();
        }
    };

    Date.prototype.ticks = function () {
        /// <summary>
        /// 获取表示此实例的日期和时间的计时周期数。
        /// </summary>
        /// <returns type="Number" />
        var ms = this.getTime();
        return ms * 10000 + 621355968000000000;
    }
})();
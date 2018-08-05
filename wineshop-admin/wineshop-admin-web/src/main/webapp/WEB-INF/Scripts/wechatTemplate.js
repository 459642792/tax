window.wechatTemplate = (function () {
    var templateModel = function () {
        var sf = this;
        sf.getTemplateKeys = function (tpl) {
            var arr = [];
            var reg = /\{\{.+?(?=\.DATA\}\})/gm;
            var excs = null;
            while ((excs = reg.exec(tpl)) != null) {
                arr.push(excs[0].substring(2));
            }
            return arr;
        }
        sf.getTemplateDemo = function (tpl, keywords, valueKey) {
            valueKey = valueKey || "text";
            return commonHelper.encodeHtml(tpl).replace(/\{\{(.+?)\.DATA\}\}/g, function (b, k) {
                var v = keywords[k];
                if (typeof (v) === 'undefined' || v === null)
                    v = "";

                if (v.hasOwnProperty(valueKey)) {
                    var clr = v.color;
                    v = v[valueKey];
                    if (typeof (v) === 'undefined' || v === null)
                        v = "";
                    return '<span style="color:' + (clr || '#000') + '">' + commonHelper.encodeHtml(v) + '</span>';
                }
                else {
                    return commonHelper.encodeHtml(v.toString());
                }
            });
        }
    };
    return new templateModel();
})();
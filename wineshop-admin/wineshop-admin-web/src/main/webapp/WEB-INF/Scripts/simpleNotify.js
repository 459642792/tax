(function ($) {
    var toolTipList = [];
    var nowTooltip = null;
    var toolTipTimer = 0;

    var beginTooltip = function () {
        if (toolTipTimer)
            return;
        var nowTooltip = toolTipList.shift();
        if (!nowTooltip)
            return;
        var $body = $("body");
        var $tip = $body.children(".tooltip");
        if ($tip.length == 0) {
            $tip = $('<div class="tooltip"><span></span></div>');
            $body.append($tip);
        }
        $tip.children("span").html(nowTooltip.text);
        $tip.addClass("tooltip-show").removeClass("tooltip-hide");
        $tip.one("click", function (e) {
            $tip.addClass("tooltip-hide").removeClass("tooltip-show");
            nowTooltip.callback();
            clearTimeout(toolTipTimer);
            e.preventDefault();
            e.stopPropagation();
            setTimeout(function () {
                toolTipTimer = 0;
                beginTooltip();
            }, 500);
        });
        toolTipTimer = setTimeout(function () {
            $tip.click();
        }, nowTooltip.expire);
    };

    window.showNotify = function (text, expire, hideCallback) {
        /// <signature>
        ///     <summary>
        ///     显示一个提示文本(Android's Toast)
        ///     </summary>
        ///     <param name="text" type="String">提示文本</param>
        ///     <param name="expire=3000" type="Number" optional="true">显示时间(单位：毫秒)[默认：3000]</param>
        ///     <param name="hideCallback" type="Function" optional="true">当提示隐藏时触发的函数[可选]</param>
        ///     <returns type="void" />
        /// </signature>
        if (!hideCallback)
            hideCallback = expire;
        if (typeof (expire) != 'number' || isNaN(expire) || expire < 500)
            expire = 3000;
        if (typeof (hideCallback) != 'function') {
            hideCallback = function () {
            };
        }
        toolTipList.push({
            text: text,
            expire: expire,
            callback: hideCallback
        });
        beginTooltip();
    };

    window.hideNotify = function () {
        /// <signature>
        ///     <summary>
        ///     隐藏当前显示的提示文本(将会继续显示下一个提示文本)
        ///     </summary>
        ///     <returns type="Boolean" />
        /// </signature>
        if (!nowTooltip)
            return false;
        $("body").children(".tooltip").click();
        return true;
    };
})(jQuery)
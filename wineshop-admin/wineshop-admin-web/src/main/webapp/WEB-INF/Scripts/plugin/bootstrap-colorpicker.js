/*! SmartAdmin - v1.4.1 - 2014-06-26 */
!function (a) {
    var b = function (a) {
        this.value = {h: 1, s: 1, b: 1, a: 1}, this.setColor(a)
    };
    b.prototype = {
        constructor: b,
        setColor: function (b) {
            b = b.toLowerCase();
            var c = this;
            a.each(d.stringParsers, function (a, e) {
                var f = e.re.exec(b),
                    g = f && e.parse(f),
                    h = e.space || "rgba";
                return g ? (c.value = "hsla" === h ? d.RGBtoHSB.apply(null, d.HSLtoRGB.apply(null, g)) : d.RGBtoHSB.apply(null, g), !1) : void 0
            })
        },
        setHue: function (a) {
            this.value.h = 1 - a
        },
        setSaturation: function (a) {
            this.value.s = a
        },
        setLightness: function (a) {
            this.value.b = 1 - a
        },
        setAlpha: function (a) {
            this.value.a = parseInt(100 * (1 - a), 10) / 100
        },
        toRGB: function (a, b, c, d) {
            a || (a = this.value.h, b = this.value.s, c = this.value.b),
                a *= 360;
            var e, f, g, h, i;
            a = a % 360 / 60,
                i = c * b,
                h = i * (1 - Math.abs(a % 2 - 1)),
                e = f = g = c - i,
                a = ~~a,
                e += [i, h, 0, 0, h, i][a],
                f += [h, i, i, h, 0, 0][a],
                g += [0, 0, h, i, i, h][a];
            return {
                r: Math.round(255 * e),
                g: Math.round(255 * f),
                b: Math.round(255 * g),
                a: d || this.value.a
            }
        },
        toHex: function (a, b, c, d) {
            var e = this.toRGB(a, b, c, d);
            return "#" + (1 << 24 | parseInt(e.r) << 16 | parseInt(e.g) << 8 | parseInt(e.b)).toString(16).substr(1)
        },
        toHSL: function (a, b, c, d) {
            a || (a = this.value.h, b = this.value.s, c = this.value.b);
            var e = a,
                f = (2 - b) * c,
                g = b * c;
            g /= f > 0 && 1 >= f ? f : 2 - f, f /= 2, g > 1 && (g = 1);
            return {h: e, s: g, l: f, a: d || this.value.a}
        }
    };
    var c = function (b, c) {
        this.element = a(b);
        var e = c.format || this.element.data("color-format") || "hex";
        this.format = d.translateFormats[e],
            this.isInput = this.element.is("input"),
            this.component = this.element.is(".color") ? this.element.find(".add-on") : !1,
            this.picker = a(d.template).appendTo("body").on("mousedown", a.proxy(this.mousedown, this)),
            this.isInput ? this.element.on({
                focus: a.proxy(this.show, this),
                keyup: a.proxy(this.update, this)
            }) : this.component ? this.component.on({click: a.proxy(this.show, this)}) : this.element.on({click: a.proxy(this.show, this)}),
        ("rgba" === e || "hsla" === e) && (this.picker.addClass("alpha"), this.alpha = this.picker.find(".colorpicker-alpha")[0].style),
            this.component ? (this.picker.find(".colorpicker-color").hide(), this.preview = this.element.find("i")[0].style) : this.preview = this.picker.find("div:last")[0].style,
            this.base = this.picker.find("div:first")[0].style, this.update()
    };
    c.prototype = {
        constructor: c,
        show: function (b) {
            this.picker.show(),
                this.height = this.component ? this.component.outerHeight() : this.element.outerHeight(),
                this.place(),
                a(window).on("resize", a.proxy(this.place, this)),
            this.isInput || b && (b.stopPropagation(), b.preventDefault()),
                a(document).on({mousedown: a.proxy(this.hide, this)}),
                this.element.trigger({type: "show", color: this.color})
        },
        update: function () {
            this.color = new b(this.isInput ? this.element.prop("value") : this.element.data("color")),
                this.picker.find("i").eq(0).css({
                    left: 100 * this.color.value.s, top: 100 - 100 * this.color.value.b
                }).end().eq(1).css("top", 100 * (1 - this.color.value.h)).end().eq(2).css("top", 100 * (1 - this.color.value.a)),
                this.previewColor()
        },
        setValue: function (a) {
            this.color = new b(a),
                this.picker.find("i").eq(0).css({
                    left: 100 * this.color.value.s, top: 100 - 100 * this.color.value.b
                }).end().eq(1).css("top", 100 * (1 - this.color.value.h)).end().eq(2).css("top", 100 * (1 - this.color.value.a)),
                this.previewColor(),
                this.element.trigger({type: "changeColor", color: this.color})
        },
        hide: function () {
            if (this.picker.is(":visible")) {
                this.picker.hide(),
                    a(window).off("resize", this.place),
                    this.isInput ? this.element.prop("value", this.format.call(this)) : (a(document).off({mousedown: this.hide}), this.component && this.element.find("input").prop("value", this.format.call(this)), this.element.data("color", this.format.call(this))),
                    this.element.trigger({type: "hide", color: this.color})
            }
        },
        place: function () {
            var a = this.component ? this.component.offset() : this.element.offset();
            this.picker.css({top: a.top + this.height, left: a.left})
        },
        previewColor: function () {
            try {
                this.preview.backgroundColor = this.format.call(this)
            }
            catch (a) {
                this.preview.backgroundColor = this.color.toHex()
            }
            this.base.backgroundColor = this.color.toHex(this.color.value.h, 1, 1, 1),
            this.alpha && (this.alpha.backgroundColor = this.color.toHex())
        },
        pointer: null,
        slider: null,
        mousedown: function (b) {
            b.stopPropagation(),
                b.preventDefault();
            var c = a(b.target),
                e = c.closest("div");
            if (!e.is(".colorpicker")) {
                if (e.is(".colorpicker-saturation"))
                    this.slider = a.extend({}, d.sliders.saturation);
                else if (e.is(".colorpicker-hue"))
                    this.slider = a.extend({}, d.sliders.hue);
                else {
                    if (!e.is(".colorpicker-alpha"))
                        return !1;
                    this.slider = a.extend({}, d.sliders.alpha)
                }
                var f = e.offset();
                this.slider.knob = e.find("i")[0].style,
                    this.slider.left = b.pageX - f.left,
                    this.slider.top = b.pageY - f.top,
                    this.pointer = {
                        left: b.pageX, top: b.pageY
                    },
                    a(document).on({
                        mousemove: a.proxy(this.mousemove, this),
                        mouseup: a.proxy(this.mouseup, this)
                    }).trigger("mousemove")
            }
            return !1
        },
        mousemove: function (a) {
            a.stopPropagation(),
                a.preventDefault();
            var b = Math.max(0, Math.min(this.slider.maxLeft, this.slider.left + ((a.pageX || this.pointer.left) - this.pointer.left))),
                c = Math.max(0, Math.min(this.slider.maxTop, this.slider.top + ((a.pageY || this.pointer.top) - this.pointer.top)));
            this.slider.knob.left = b + "px",
                this.slider.knob.top = c + "px",
            this.slider.callLeft && this.color[this.slider.callLeft].call(this.color, b / 100),
            this.slider.callTop && this.color[this.slider.callTop].call(this.color, c / 100),
                this.previewColor(),
                this.element.trigger({type: "changeColor", color: this.color});
            return !1
        },
        mouseup: function (b) {
            b.stopPropagation();
            b.preventDefault();
            a(document).off({mousemove: this.mousemove, mouseup: this.mouseup});
            return !1
        }
    },
        a.fn.colorpicker = function (b, d) {
            return this.each(function () {
                var e = a(this),
                    f = e.data("colorpicker"),
                    g = "object" == typeof b && b;
                f || e.data("colorpicker", f = new c(this, a.extend({}, a.fn.colorpicker.defaults, g))),
                "string" == typeof b && f[b](d)
            })
        },
        a.fn.colorpicker.defaults = {},
        a.fn.colorpicker.Constructor = c;
    var d = {
        translateFormats: {
            rgb: function () {
                var a = this.color.toRGB();
                return "rgb(" + a.r + "," + a.g + "," + a.b + ")"
            },
            rgba: function () {
                var a = this.color.toRGB();
                return "rgba(" + a.r + "," + a.g + "," + a.b + "," + a.a + ")"
            },
            hsl: function () {
                var a = this.color.toHSL();
                return "hsl(" + Math.round(360 * a.h) + "," + Math.round(100 * a.s) + "%," + Math.round(100 * a.l) + "%)"
            },
            hsla: function () {
                var a = this.color.toHSL();
                return "hsla(" + Math.round(360 * a.h) + "," + Math.round(100 * a.s) + "%," + Math.round(100 * a.l) + "%," + a.a + ")"
            },
            hex: function () {
                return this.color.toHex()
            }
        },
        sliders: {
            saturation: {
                maxLeft: 100, maxTop: 100, callLeft: "setSaturation", callTop: "setLightness"
            },
            hue: {
                maxLeft: 0, maxTop: 100, callLeft: !1, callTop: "setHue"
            },
            alpha: {
                maxLeft: 0, maxTop: 100, callLeft: !1, callTop: "setAlpha"
            }
        },
        RGBtoHSB: function (a, b, c, d) {
            a /= 255,
                b /= 255,
                c /= 255;
            var e, f, g, h;
            g = Math.max(a, b, c),
                h = g - Math.min(a, b, c),
                e = 0 === h ? null : g == a ? (b - c) / h : g == b ? (c - a) / h + 2 : (a - b) / h + 4,
                e = (e + 360) % 6 * 60 / 360, f = 0 === h ? 0 : h / g;
            return {h: e || 1, s: f, b: g, a: d || 1}
        },
        HueToRGB: function (a, b, c) {
            return 0 > c ? c += 1 : c > 1 && (c -= 1), 1 > 6 * c ? a + (b - a) * c * 6 : 1 > 2 * c ? b : 2 > 3 * c ? a + (b - a) * (2 / 3 - c) * 6 : a
        },
        HSLtoRGB: function (a, b, c, e) {
            0 > b && (b = 0);
            var f;
            f = .5 >= c ? c * (1 + b) : c + b - c * b;
            var g = 2 * c - f,
                h = a + 1 / 3,
                i = a,
                j = a - 1 / 3,
                k = Math.round(255 * d.HueToRGB(g, f, h)),
                l = Math.round(255 * d.HueToRGB(g, f, i)),
                m = Math.round(255 * d.HueToRGB(g, f, j));
            return [k, l, m, e || 1]
        },
        stringParsers: [{
            re: /rgba?\(\s*(\d{1,3})\s*,\s*(\d{1,3})\s*,\s*(\d{1,3})\s*(?:,\s*(\d+(?:\.\d+)?)\s*)?\)/,
            parse: function (a) {
                return [a[1], a[2], a[3], a[4]]
            }
        },
            {
                re: /rgba?\(\s*(\d+(?:\.\d+)?)\%\s*,\s*(\d+(?:\.\d+)?)\%\s*,\s*(\d+(?:\.\d+)?)\%\s*(?:,\s*(\d+(?:\.\d+)?)\s*)?\)/,
                parse: function (a) {
                    return [2.55 * a[1], 2.55 * a[2], 2.55 * a[3], a[4]]
                }
            },
            {
                re: /#([a-fA-F0-9]{2})([a-fA-F0-9]{2})([a-fA-F0-9]{2})/,
                parse: function (a) {
                    return [parseInt(a[1], 16), parseInt(a[2], 16), parseInt(a[3], 16)]
                }
            },
            {
                re: /#([a-fA-F0-9])([a-fA-F0-9])([a-fA-F0-9])/,
                parse: function (a) {
                    return [parseInt(a[1] + a[1], 16), parseInt(a[2] + a[2], 16), parseInt(a[3] + a[3], 16)]
                }
            },
            {
                re: /hsla?\(\s*(\d+(?:\.\d+)?)\s*,\s*(\d+(?:\.\d+)?)\%\s*,\s*(\d+(?:\.\d+)?)\%\s*(?:,\s*(\d+(?:\.\d+)?)\s*)?\)/,
                space: "hsla",
                parse: function (a) {
                    return [a[1] / 360, a[2] / 100, a[3] / 100, a[4]]
                }
            }],
        template: '<div class="colorpicker dropdown-menu"><div class="colorpicker-saturation"><i><b></b></i></div><div class="colorpicker-hue"><i></i></div><div class="colorpicker-alpha"><i></i></div><div class="colorpicker-color"><div /></div></div>'
    }
}(window.jQuery);
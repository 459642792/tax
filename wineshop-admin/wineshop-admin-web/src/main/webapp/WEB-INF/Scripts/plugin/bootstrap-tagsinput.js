/*! SmartAdmin - v1.4.1 - 2014-06-26 */
!function (a) {
    "use strict";

    function b(b, c) {
        this.itemsArray = [];
        this.$element = a(b);
        this.$element.hide();
        this.isSelect = "SELECT" === b.tagName;
        this.multiple = this.isSelect && b.hasAttribute("multiple");
        this.objectItems = c && c.itemValue;
        this.placeholderText = b.hasAttribute("placeholder") ? this.$element.attr("placeholder") : "";
        this.inputSize = Math.max(1, this.placeholderText.length);
        this.$container = a('<div class="bootstrap-tagsinput"></div>');
        this.$input = a('<input size="' + this.inputSize + '" type="text" placeholder="' + this.placeholderText + '"/>').appendTo(this.$container);
        this.$element.after(this.$container);
        this.build(c);
    }

    function c(a, b) {
        if ("function" != typeof a[b]) {
            var c = a[b];
            a[b] = function (a) {
                return a[c]
            }
        }
    }

    function d(a, b) {
        if ("function" != typeof a[b]) {
            var c = a[b];
            a[b] = function () {
                return c
            }
        }
    }

    function e(a) {
        return a ? h.text(a).html() : ""
    }

    function f(a) {
        var b = 0;
        if (document.selection) {
            a.focus();
            var c = document.selection.createRange();
            c.moveStart("character", -a.value.length), b = c.text.length
        }
        else
            (a.selectionStart || "0" == a.selectionStart) && (b = a.selectionStart);
        return b
    }

    var g = {
        tagClass: function () {
            return "label label-info"
        },
        itemValue: function (a) {
            return a ? a.toString() : a
        },
        itemText: function (a) {
            return this.itemValue(a)
        },
        freeInput: !0,
        maxTags: void 0,
        confirmKeys: [13],
        onTagExists: function (a, b) {
            b.hide().fadeIn()
        }
    };
    b.prototype = {
        constructor: b,
        add: function (b, c) {
            var d = this;
            if (!(d.options.maxTags && d.itemsArray.length >= d.options.maxTags || b !== !1 && !b)) {
                if ("object" == typeof b && !d.objectItems)
                    throw "Can't add objects when itemValue option is not set";
                if (!b.toString().match(/^\s*$/)) {
                    if (d.isSelect && !d.multiple && d.itemsArray.length > 0)
                        d.remove(d.itemsArray[0]);
                    if ("string" == typeof b && "INPUT" === this.$element[0].tagName) {
                        var f = b.split(",");
                        if (f.length > 1) {
                            for (var g = 0; g < f.length; g++)
                                this.add(f[g], !0);
                            return void (c || d.pushVal())
                        }
                    }
                    var h = d.options.itemValue(b),
                        i = d.options.itemText(b),
                        j = d.options.tagClass(b),
                        k = a.grep(d.itemsArray, function (a) {
                            return d.options.itemValue(a) === h
                        })[0];
                    if (k) {
                        if (d.options.onTagExists) {
                            var l = a(".tag", d.$container).filter(function () {
                                return a(this).data("item") === k
                            });
                            d.options.onTagExists(b, l)
                        }
                    }
                    else {
                        d.itemsArray.push(b);
                        var m = a('<span class="tag ' + e(j) + '">' + e(i) + '<span data-role="remove"></span></span>');
                        m.data("item", b), d.findInputWrapper().before(m), m.after(" ");
                        if (d.isSelect && !a('option[value="' + escape(h) + '"]', d.$element)[0]) {
                            var n = a("<option selected>" + e(i) + "</option>");
                            n.data("item", b), n.attr("value", h), d.$element.append(n)
                        }
                        c || d.pushVal();
                        d.options.maxTags === d.itemsArray.length && d.$container.addClass("bootstrap-tagsinput-max");
                        d.$element.trigger(a.Event("itemAdded", {item: b}));
                    }
                }
            }
        },
        remove: function (b, c) {
            var d = this;
            if (d.objectItems) {
                if (b = "object" == typeof b) {
                    a.grep(d.itemsArray, function (a) {
                        return d.options.itemValue(a) == d.options.itemValue(b)
                    })[0]
                }
                else {
                    a.grep(d.itemsArray, function (a) {
                        return d.options.itemValue(a) == b
                    })[0]
                }
            }
            if (b) {
                a(".tag", d.$container).filter(function () {
                    return a(this).data("item") === b
                }).remove(),
                    a("option", d.$element).filter(function () {
                        return a(this).data("item") === b
                    }).remove(),
                    d.itemsArray.splice(a.inArray(b, d.itemsArray), 1)
            }
            c || d.pushVal(),
            d.options.maxTags > d.itemsArray.length && d.$container.removeClass("bootstrap-tagsinput-max"),
                d.$element.trigger(a.Event("itemRemoved", {item: b}))
        },
        removeAll: function (c) {
            var b = this;
            for (a(".tag", b.$container).remove(), a("option", b.$element).remove(); b.itemsArray.length > 0;)
                b.itemsArray.pop();
            c || b.pushVal();
            b.options.maxTags && !this.isEnabled() && this.enable();
        },
        refresh: function () {
            var b = this;
            a(".tag", b.$container).each(function () {
                var c = a(this),
                    d = c.data("item"),
                    f = b.options.itemValue(d),
                    g = b.options.itemText(d),
                    h = b.options.tagClass(d);
                c.attr("class", null);
                c.addClass("tag " + e(h));
                c.contents().filter(function () {
                    return 3 == this.nodeType
                })[0].nodeValue = e(g);
                if (b.isSelect) {
                    var i = a("option", b.$element).filter(function () {
                        return a(this).data("item") === d
                    });
                    i.attr("value", f)
                }
            })
        },
        refreshTags: function () {
            var b = this;
            var v = b.$element.val();
            b.removeAll(!0), b.add(v);
        },
        items: function () {
            return this.itemsArray
        },
        pushVal: function () {
            var b = this,
                c = a.map(b.items(), function (a) {
                    return b.options.itemValue(a).toString()
                });
            b.$element.val(c, !0).trigger("change")
        },
        build: function (b) {
            var e = this;
            e.options = a.extend({}, g, b);
            var h = e.options.typeahead || {};
            e.objectItems && (e.options.freeInput = !1),
                c(e.options, "itemValue"),
                c(e.options, "itemText"),
                c(e.options, "tagClass"),
            e.options.source && (h.source = e.options.source);
            if (h.source && a.fn.typeahead) {
                (d(h, "source"),
                    e.$input.typeahead({
                        source: function (b, c) {
                            function d(a) {
                                for (var b = [], d = 0; d < a.length; d++) {
                                    var g = e.options.itemText(a[d]);
                                    f[g] = a[d],
                                        b.push(g)
                                }
                                c(b)
                            }

                            this.map = {};
                            var f = this.map,
                                g = h.source(b);
                            a.isFunction(g.success) ? g.success(d) : a.when(g).then(d)
                        },
                        updater: function (a) {
                            e.add(this.map[a])
                        },
                        matcher: function (a) {
                            return -1 !== a.toLowerCase().indexOf(this.query.trim().toLowerCase())
                        },
                        sorter: function (a) {
                            return a.sort()
                        },
                        highlighter: function (a) {
                            var b = new RegExp("(" + this.query + ")", "gi");
                            return a.replace(b, "<strong>$1</strong>")
                        }
                    }))
            }
            e.$container.on("click", a.proxy(function () {
                e.$input.focus()
            }, e)),
                e.$container.on("keydown", "input", a.proxy(function (b) {
                    var c = a(b.target),
                        d = e.findInputWrapper();
                    switch (b.which) {
                        case 8:
                            if (0 === f(c[0])) {
                                var g = d.prev();
                                g && e.remove(g.data("item"))
                            }
                            break;
                        case 46:
                            if (0 === f(c[0])) {
                                var h = d.next();
                                h && e.remove(h.data("item"))
                            }
                            break;
                        case 37:
                            var i = d.prev();
                            0 === c.val().length && i[0] && (i.before(d), c.focus());
                            break;
                        case 39:
                            var j = d.next();
                            0 === c.val().length && j[0] && (j.after(d), c.focus());
                            break;
                        default:
                            if (e.options.freeInput && a.inArray(b.which, e.options.confirmKeys) >= 0) {
                                e.add(c.val()), c.val(""), b.preventDefault()
                            }
                    }
                    c.attr("size", Math.max(this.inputSize, c.val().length))
                }, e)),
                e.$container.on("click", "[data-role=remove]", a.proxy(function (b) {
                    e.remove(a(b.target).closest(".tag").data("item"))
                }, e));
            if (e.options.itemValue === g.itemValue) {
                if ("INPUT" === e.$element[0].tagName) {
                    e.add(e.$element.val())
                }
                else {
                    a("option", e.$element).each(function () {
                        e.add(a(this).attr("value"), !0)
                    })
                }
            }
        },
        destroy: function () {
            var a = this;
            a.$container.off("keypress", "input"),
                a.$container.off("click", "[role=remove]"),
                a.$container.remove(),
                a.$element.removeData("tagsinput"),
                a.$element.show()
        },
        focus: function () {
            this.$input.focus()
        },
        input: function () {
            return this.$input
        },
        findInputWrapper: function () {
            for (var b = this.$input[0], c = this.$container[0]; b && b.parentNode !== c;)
                b = b.parentNode;
            return a(b)
        }
    },
        a.fn.tagsinput = function (c, d) {
            var e = [];
            this.each(function () {
                var f = a(this).data("tagsinput");
                if (f) {
                    var g = f[c](d);
                    void 0 !== g && e.push(g)
                }
                else {
                    f = new b(this, c);
                    a(this).data("tagsinput", f);
                    e.push(f);
                    "SELECT" === this.tagName && a("option", a(this)).attr("selected", "selected");
                    a(this).val(a(this).val())
                }
            });
            return "string" == typeof c ? e.length > 1 ? e : e[0] : e
        },
        a.fn.tagsinput.Constructor = b;
    var h = a("<div />");
    a(function () {
        a("input[data-role=tagsinput], select[multiple][data-role=tagsinput]").tagsinput()
    })
}(window.jQuery);
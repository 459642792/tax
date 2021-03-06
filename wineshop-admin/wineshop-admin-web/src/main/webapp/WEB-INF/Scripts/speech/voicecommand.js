/*
 * Unminified JS files are available after purchase
 */
/*! SmartAdmin - v1.4.1 - 2014-06-26 */
var root = this,
    SpeechRecognition = root.SpeechRecognition || root.webkitSpeechRecognition || root.mozSpeechRecognition || root.msSpeechRecognition || root.oSpeechRecognition;
if (SpeechRecognition && voice_command) {
    $.root_.on("click", '[data-action="voiceCommand"]', function (a) {
        if ($.root_.hasClass("voice-command-active")) {
            $.speechApp.stop()
        }
        else {
            $.speechApp.start(), $("#speech-btn .popover").fadeIn(350)
        }
        a.preventDefault()
    });
    $(document).mouseup(function (a) {
        $("#speech-btn .popover").is(a.target) || 0 !== $("#speech-btn .popover").has(a.target).length || $("#speech-btn .popover").fadeOut(250)
    });
    var modal = $('<div class="modal fade" id="voiceModal" tabindex="-1" role="dialog" aria-labelledby="remoteModalLabel" aria-hidden="true"><div class="modal-dialog"><div class="modal-content"></div></div></div>');
    modal.appendTo("body"), $.speechApp = function (a) {
        a.start = function () {
            smartSpeechRecognition.addCommands(commands);
            if (smartSpeechRecognition) {
                smartSpeechRecognition.start(),
                    $.root_.addClass("voice-command-active"),
                    $.speechApp.playON(),
                voice_localStorage && localStorage.setItem("sm-setautovoice", "true")
            }
            else {
                alert("speech plugin not loaded")
            }
        };
        a.stop = function () {
            if (smartSpeechRecognition) {
                smartSpeechRecognition.abort(),
                    $.root_.removeClass("voice-command-active"),
                    $.speechApp.playOFF(),
                voice_localStorage && localStorage.setItem("sm-setautovoice", "false"),
                $("#speech-btn .popover").is(":visible") && $("#speech-btn .popover").fadeOut(250)
            }
        };
        a.playON = function () {
            var a = document.createElement("audio");
            navigator.userAgent.match("Firefox/") ? a.setAttribute("src", $.sound_path + "voice_on.ogg") : a.setAttribute("src", $.sound_path + "voice_on.mp3"),
                a.addEventListener("load", function () {
                    a.play()
                }, !0),
            $.sound_on && (a.pause(), a.play())
        };
        a.playOFF = function () {
            var a = document.createElement("audio");
            navigator.userAgent.match("Firefox/") ? a.setAttribute("src", $.sound_path + "voice_off.ogg") : a.setAttribute("src", $.sound_path + "voice_off.mp3"),
                $.get(),
                a.addEventListener("load", function () {
                    a.play()
                }, !0),
            $.sound_on && (a.pause(), a.play())
        };
        a.playConfirmation = function () {
            var a = document.createElement("audio");
            navigator.userAgent.match("Firefox/") ? a.setAttribute("src", $.sound_path + "voice_alert.ogg") : a.setAttribute("src", $.sound_path + "voice_alert.mp3"),
                $.get(),
                a.addEventListener("load", function () {
                    a.play()
                }, !0),
            $.sound_on && (a.pause(), a.play())
        };
        return a;
    }({})
}
else
    $("#speech-btn").addClass("display-none");
(function (a) {
    "use strict";
    if (!SpeechRecognition) {
        root.smartSpeechRecognition = null;
        return a;
    }
    var b, c, d = [];
    var e = {
        start: [],
        error: [],
        end: [],
        result: [],
        resultMatch: [],
        resultNoMatch: [],
        errorNetwork: [],
        errorPermissionBlocked: [],
        errorPermissionDenied: []
    };
    var f = 0,
        g = !1,
        h = "font-weight: bold; color: #00f;",
        i = /\s*\((.*?)\)\s*/g,
        j = /(\(\?:[^)]+\))\?/g,
        k = /(\(\?)?:\w+/g,
        l = /\*\w+/g,
        m = /[\-{}\[\]+?.,\\\^$|#]/g;
    var n = function (a) {
        return a = a.replace(m, "\\$&").replace(i, "(?:$1)?").replace(k, function (a, b) {
            return b ? a : "([^\\s]+)"
        }).replace(l, "(.*?)").replace(j, "\\s*$1?\\s*"), new RegExp("^" + a + "$", "i")
    };
    var o = function (a) {
        a.forEach(function (a) {
            a.callback.apply(a.context)
        })
    };
    var p = function () {
        q() || root.smartSpeechRecognition.init({}, !1)
    };
    var q = function () {
        return b !== a
    };
    root.smartSpeechRecognition = {
        init: function (i, j) {
            j = j === a ? !0 : !!j,
            b && b.abort && b.abort(),
                b = new SpeechRecognition,
                b.maxAlternatives = 5,
                b.continuous = !0,
                b.lang = voice_command_lang || "en-US",
                b.onstart = function () {
                    o(e.start), $.root_.removeClass("service-not-allowed"), $.root_.addClass("service-allowed")
                },
                b.onerror = function (a) {
                    switch (o(e.error), a.error) {
                        case "network":
                            o(e.errorNetwork);
                            break;
                        case "not-allowed":
                        case "service-not-allowed":
                            c = !1,
                                $.root_.removeClass("service-allowed"),
                                $.root_.addClass("service-not-allowed"),
                                o((new Date).getTime() - f < 200 ? e.errorPermissionBlocked : e.errorPermissionDenied)
                    }
                },
                b.onend = function () {
                    if (o(e.end), c) {
                        var a = (new Date).getTime() - f;
                        1e3 > a ? setTimeout(root.smartSpeechRecognition.start, 1e3 - a) : root.smartSpeechRecognition.start()
                    }
                },
                b.onresult = function (a) {
                    o(e.result);
                    for (var b, c = a.results[a.resultIndex], f = 0; f < c.length; f++) {
                        b = c[f].transcript.trim(),
                        g && root.console.log("Speech recognized: %c" + b, h);
                        for (var i = 0, j = d.length; j > i; i++) {
                            var k = d[i].command.exec(b);
                            if (k) {
                                var l = k.slice(1);
                                g && (root.console.log("command matched: %c" + d[i].originalPhrase, h), l.length && root.console.log("with parameters", l)),
                                    d[i].callback.apply(this, l),
                                    o(e.resultMatch);
                                var m = ["sound on", "mute", "stop"];
                                if (m.indexOf(d[i].originalPhrase) < 0) {
                                    $.smallBox({
                                        title: d[i].originalPhrase,
                                        content: "loading...",
                                        color: "#333",
                                        sound_file: "voice_alert",
                                        timeout: 2e3
                                    }),
                                    $("#speech-btn .popover").is(":visible") && $("#speech-btn .popover").fadeOut(250)
                                }
                                return !0
                            }
                        }
                    }
                    o(e.resultNoMatch),
                        $.smallBox({
                            title: 'Error: <strong> " ' + b + ' " </strong> no match found!',
                            content: "Please speak clearly into the microphone",
                            color: "#a90329",
                            timeout: 5e3,
                            icon: "fa fa-microphone"
                        }),
                    $("#speech-btn .popover").is(":visible") && $("#speech-btn .popover").fadeOut(250);
                    return !1
                },
            j && (d = []),
            i.length && this.addCommands(i)
        },
        start: function (d) {
            p(), d = d || {}, c = d.autoRestart !== a ? !!d.autoRestart : !0, f = (new Date).getTime(), b.start()
        },
        abort: function () {
            c = !1, q && b.abort()
        },
        debug: function (a) {
            g = arguments.length > 0 ? !!a : !0
        },
        setLanguage: function (a) {
            p(), b.lang = a
        },
        addCommands: function (a) {
            var b, c;
            p();
            for (var e in a)
                if (a.hasOwnProperty(e)) {
                    if (b = root[a[e]] || a[e], "function" != typeof b) continue;
                    c = n(e), d.push({command: c, callback: b, originalPhrase: e})
                }
            g && root.console.log("Commands successfully loaded: %c" + d.length, h)
        },
        removeCommands: function (b) {
            return b === a ? void (d = []) : (b = Array.isArray(b) ? b : [b], void (d = d.filter(function (a) {
                for (var c = 0; c < b.length; c++) if (b[c] === a.originalPhrase) return !1;
                return !0
            })))
        },
        addCallback: function (b, c, d) {
            if (e[b] !== a) {
                var f = root[c] || c;
                "function" == typeof f && e[b].push({callback: f, context: d || this})
            }
        }
    }
}).call(this);
var autoStart = function () {
    smartSpeechRecognition.addCommands(commands),
        smartSpeechRecognition ? (smartSpeechRecognition.start(), $.root_.addClass("voice-command-active"), voice_localStorage && localStorage.setItem("sm-setautovoice", "true")) : alert("������δ����")
};
SpeechRecognition && voice_command && "true" == localStorage.getItem("sm-setautovoice") && autoStart(),
SpeechRecognition && voice_command_auto && voice_command && autoStart();
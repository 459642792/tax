function iQuery(d) {
    var data = d;
    this.getData = function () {
        return data;
    };
};

Query = function () {
    var sf = this;
    sf.Null = function () {
        return new iQuery({});
    };
    sf.and = function () {
        var opt = [];
        for (var i = 0; i < arguments.length; ++i) {
            if (arguments[i] instanceof iQuery) {
                opt.push(arguments[i].getData());
            }
            else if (arguments[i] instanceof Array) {
                var inArgs = arguments[i];
                for (var j = 0; j < inArgs.length; ++j) {
                    if (inArgs[j] instanceof iQuery) {
                        opt.push(inArgs[j].getData());
                    }
                }
            }
        }
        if (opt.length == 0)
            throw {
                message: "无任何and条件",
                trace: console.trace()
            };
        return new iQuery({"$and": opt});
        //var d = {};
        //for (var i = 0; i < opt.length; ++i) {
        //    var o = opt[i];
        //    for (var t in o) {
        //        if (d[t]) {
        //            var dt = d[t];
        //            var ot = o[t];
        //            for (var otd in ot) {
        //                if (typeof (ot[otd]) === 'function')
        //                    continue;
        //                dt[otd] = ot[otd];
        //            }
        //        }
        //        else
        //            d[t] = o[t];
        //    }
        //}
        //return new iQuery(d);
    };

    sf.or = function () {
        var opt = [];
        for (var i = 0; i < arguments.length; ++i) {
            if (arguments[i] instanceof iQuery) {
                opt.push(arguments[i].getData());
            }
            else if (arguments[i] instanceof Array) {
                var inArgs = arguments[i];
                for (var j = 0; j < inArgs.length; ++j) {
                    if (inArgs[j] instanceof iQuery) {
                        opt.push(inArgs[j].getData());
                    }
                }
            }
        }
        if (opt.length == 0)
            throw {
                message: "无任何or条件",
                trace: console.trace()
            };
        return new iQuery({$or: opt});
    };

    sf.eq = function (key, value, nonQuery) {
        if (nonQuery !== undefined) {
            if (nonQuery instanceof Array) {
                for (var i = 0; i < nonQuery.length; ++i) {
                    if (nonQuery[i] == value)
                        return sf.Null();
                }
            }
            else {
                if (value == nonQuery)
                    return sf.Null();
            }
        }
        if (typeof (key) === 'string' && key.length > 0) {
            var d = {};
            d[key] = value;
            return new iQuery(d);
        }
        throw {
            message: "键不可为空",
            trace: console.trace()
        };
    }

    sf.gt = function (key, value, nonQuery) {
        if (nonQuery !== undefined) {
            if (nonQuery instanceof Array) {
                for (var i = 0; i < nonQuery.length; ++i) {
                    if (nonQuery[i] == value)
                        return sf.Null();
                }
            }
            else {
                if (value == nonQuery)
                    return sf.Null();
            }
        }
        if (typeof (key) === 'string' && key.length > 0) {
            var d = {};
            d[key] = {$gt: value};
            return new iQuery(d);
        }
        throw {
            message: "键不可为空",
            trace: console.trace()
        };
    };

    sf.gte = function (key, value, nonQuery) {
        if (nonQuery !== undefined) {
            if (nonQuery instanceof Array) {
                for (var i = 0; i < nonQuery.length; ++i) {
                    if (nonQuery[i] == value)
                        return sf.Null();
                }
            }
            else {
                if (value == nonQuery)
                    return sf.Null();
            }
        }
        if (typeof (key) === 'string' && key.length > 0) {
            var d = {};
            d[key] = {$gte: value};
            return new iQuery(d);
        }
        throw {
            message: "键不可为空",
            trace: console.trace()
        };
    };

    sf.lt = function (key, value, nonQuery) {
        if (nonQuery !== undefined) {
            if (nonQuery instanceof Array) {
                for (var i = 0; i < nonQuery.length; ++i) {
                    if (nonQuery[i] == value)
                        return sf.Null();
                }
            }
            else {
                if (value == nonQuery)
                    return sf.Null();
            }
        }
        if (typeof (key) === 'string' && key.length > 0) {
            var d = {};
            d[key] = {$lt: value};
            return new iQuery(d);
        }
        throw {
            message: "键不可为空",
            trace: console.trace()
        };
    };

    sf.lte = function (key, value, nonQuery) {
        if (nonQuery !== undefined) {
            if (nonQuery instanceof Array) {
                for (var i = 0; i < nonQuery.length; ++i) {
                    if (nonQuery[i] == value)
                        return sf.Null();
                }
            }
            else {
                if (value == nonQuery)
                    return sf.Null();
            }
        }
        if (typeof (key) === 'string' && key.length > 0) {
            var d = {};
            d[key] = {$lte: value};
            return new iQuery(d);
        }
        throw {
            message: "键不可为空",
            trace: console.trace()
        };
    };

    sf.in = function (key, values) {
        if (typeof (key) === 'string' && key.length > 0) {
            var v = null;
            if (values instanceof Array)
                v = values;
            else if (typeof (values.build) === 'function') {
                v = values.build();
            }
            else {
                v = [];
                for (var i = 1; i < arguments.length; ++i) {
                    v.push(arguments[i]);
                }
            }
            var d = {};
            d[key] = {$in: v};
            return new iQuery(d);
        }
        throw {
            message: "键不可为空",
            trace: console.trace()
        };
    };

    sf.ne = function (key, value, nonQuery) {
        if (nonQuery !== undefined) {
            if (nonQuery instanceof Array) {
                for (var i = 0; i < nonQuery.length; ++i) {
                    if (nonQuery[i] == value)
                        return sf.Null();
                }
            }
            else {
                if (value == nonQuery)
                    return sf.Null();
            }
        }
        if (typeof (key) === 'string' && key.length > 0) {
            var d = {};
            d[key] = {$ne: value};
            return new iQuery(d);
        }
        throw {
            message: "键不可为空",
            trace: console.trace()
        };
    };

    sf.like = function (key, value, nonQuery) {
        if (nonQuery !== undefined) {
            if (nonQuery instanceof Array) {
                for (var i = 0; i < nonQuery.length; ++i) {
                    if (nonQuery[i] == value)
                        return sf.Null();
                }
            }
            else {
                if (value == nonQuery)
                    return sf.Null();
            }
        }
        if (typeof (key) != 'string' || key.length == 0) {
            throw {
                message: "键不可为空",
                trace: console.trace()
            };
        }
        if (typeof (value) != 'string') {
            throw {
                message: "值只能是字符串",
                trace: console.trace()
            };
        }
        var d = {};
        d[key] = {$like: value};
        return new iQuery(d);
    };

    sf.mod = function (key, m, l) {
        if (typeof (key) != 'string' || key.length == 0) {
            throw {
                message: "键不可为空",
                trace: console.trace()
            };
        }
        if (typeof (m) != 'number' || m == 0) {
            throw {
                message: "除数不是数字或除数为0",
                trace: console.trace()
            };
        }
        if (typeof (l) != 'number') {
            throw {
                message: "余数不是数字",
                trace: console.trace()
            };
        }
        var d = {};
        d[key] = {$mod: [m, l]};
        return new iQuery(d);
    };

    sf.not = function (key, value) {
        if (typeof (key) != 'string' || key.length == 0) {
            throw {
                message: "键不可为空",
                trace: console.trace()
            };
        }
        var v;
        if (value instanceof iQuery) {
            v = value.getData();
        }
        else {
            throw {
                message: "value值不是iQuery对象",
                trace: console.trace()
            };
        }
        var d = {};
        d[key] = {$not: v};
    };

    sf.notIn = function (key, values) {
        if (typeof (key) === 'string' && key.length > 0) {
            var v = null;
            if (values instanceof Array)
                v = values;
            else if (typeof (values.build) === 'function') {
                v = values.build();
            }
            else {
                v = [];
                for (var i = 1; i < arguments.length; ++i) {
                    v.push(arguments[i]);
                }
            }
            var d = {};
            d[key] = {$nin: v};
            return new iQuery(d);
        }
        else
            throw {
                message: "键不可为空",
                trace: console.trace()
            };
    };

    sf.bitAnd = function (key, value) {
        if (typeof (value) != 'number' || isNaN(value)) {
            throw {
                message: "值仅可为数字类型",
                trace: console.trace()
            };
        }
        if (typeof (key) === 'string' && key.length > 0) {
            var d = {};
            d[key] = {$bitand: value};
            return new iQuery(d);
        }
        else
            throw {
                message: "键不可为空",
                trace: console.trace()
            };
    };

    sf.bitOr = function (key, value) {
        if (typeof (value) != 'number' || isNaN(value)) {
            throw {
                message: "值仅可为数字类型",
                trace: console.trace()
            };
        }
        if (typeof (key) === 'string' && key.length > 0) {
            var d = {};
            d[key] = {$bitor: value};
            return new iQuery(d);
        }
        else
            throw {
                message: "键不可为空",
                trace: console.trace()
            };
    };

    sf.bitXor = function (key, value) {
        if (typeof (value) != 'number' || isNaN(value)) {
            throw {
                message: "值仅可为数字类型",
                trace: console.trace()
            };
        }
        if (typeof (key) === 'string' && key.length > 0) {
            var d = {};
            d[key] = {$bitxor: value};
            return new iQuery(d);
        }
        else
            throw {
                message: "键不可为空",
                trace: console.trace()
            };
    };
};

Query.Null = function () {
    return new Query().Null();
};
Query.and = function () {
    var q = new Query();
    return q.and.apply(q, arguments);
};

Query.or = function () {
    var q = new Query();
    return q.or.apply(q, arguments);
};

Query.eq = function (key, value, nonQuery) {
    return new Query().eq(key, value, nonQuery);
};
Query.gt = function (key, value, nonQuery) {
    return new Query().gt(key, value, nonQuery);
};
Query.gte = function (key, value, nonQuery) {
    return new Query().gte(key, value, nonQuery);
};

Query.lt = function (key, value, nonQuery) {
    return new Query().lt(key, value, nonQuery);
};

Query.lte = function (key, value, nonQuery) {
    return new Query().lte(key, value, nonQuery);
};

Query.in = function (key, values) {
    return new Query().in(key, values);
};

Query.ne = function (key, value, nonQuery) {
    return new Query().ne(key, value, nonQuery);
};

Query.like = function (key, reg, nonQuery) {
    return new Query().like(key, reg, nonQuery);
};

Query.mod = function (key, m, l) {
    return new Query().mod(key, m, l);
};

Query.not = function (key, value) {
    return new Query().not(key, value);
};

Query.notIn = function () {
    var q = new Query();
    return q.notIn.apply(q, arguments);
};

Query.bitAnd = function (key, value) {
    return new Query().bitAnd(key, value);
};

Query.bitOr = function (key, value) {
    return new Query().bitOr(key, value);
};

Query.bitXor = function (key, value) {
    return new Query().bitXor(key, value);
};
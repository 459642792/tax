var DbBuildResult = function (_tb, _query, _fields, _sort, _groupBy, _pageIndex, _pageSize, _isDistinct, _other) {
    var sf = this;
    var gb = [];
    for (var k in _groupBy) {
        gb.push(k);
    }
    sf.Table = _tb;
    sf.Query = JSON.stringify(_query ? _query.getData() : {});
    sf.Sort = _sort ? _sort.getData() : [];
    sf.Fields = _fields ? _fields.getData() : [];
    sf.PageIndex = _pageIndex;
    sf.PageSize = _pageSize;
    sf.IsDistinct = _isDistinct;
    sf.GroupBy = gb;
    for (var k in _other) {
        sf[k] = _other[k];
    }
}
var DbBuilder = function () {
    var sf = this;

    var cursorBuilder = function (tableName) {
        var _tb = tableName;
        var _query = null;
        var _fields = null;
        var _sort = null;
        var _groupBy = {};
        var csf = this;
        var _pageIndex = 0;
        var _pageSize = 0;
        var _isDistinct = false;

        var _other = {};

        csf.distinct = function (isDistinct) {
            if (typeof (isDistinct) === 'undefined')
                _isDistinct = true;
            else if (typeof (isDistinct) === 'number')
                _isDistinct = isDistinct > 0;
            else
                _isDistinct = isDistinct ? true : false;
            return csf;
        }

        csf.where = function (query) {
            if (query != null && !(query instanceof iQuery)) {
                throw 'query参数必须为iQuery对象';
            }
            if (query == null)
                _query = Query.Null();
            else
                _query = query;
            return csf;
        };

        csf.sortBy = function (sort) {
            if (sort != null && !(sort instanceof Sort)) {
                throw 'sort参数必须为Sort对象'
            }
            _sort = sort;
            return csf;
        };

        csf.groupBy = function () {
            for (var i = 0; i < arguments.length; ++i) {
                if (arguments[i] instanceof Array) {
                    var inArgs = arguments[i];
                    for (var j = 0; j < inArgs.length; ++j) {
                        if (typeof (inArgs[j]) === 'string' && inArgs[j].length > 0)
                            _groupBy[inArgs[j]] = true;
                    }
                }
                else {
                    if (typeof (arguments[i]) === 'string' && arguments[i].length > 0)
                        _groupBy[arguments[i]] = true;
                }
            }
            return csf;
        };

        csf.select = function (fields) {
            if (fields != null && !(fields instanceof Fields)) {
                throw 'fields参数必须Fields对象'
            }
            _fields = fields;
            return csf;
        }

        csf.pageIndex = function (idx) {
            if (typeof (idx) === 'number' && !isNaN(idx))
                _pageIndex = idx;
            else
                throw '参数idx不是数字';
            return csf;
        }

        csf.pageSize = function (size) {
            if (typeof (size) === 'number' && !isNaN(size))
                _pageSize = size;
            else
                throw '参数size不是数字';
            return csf;
        }

        csf.setParam = function (key, value) {
            if (typeof (key) != 'string')
                throw '参数key必须为字符串';
            switch (key.toLowerCase()) {
                case "table":
                case "query":
                case "sort":
                case "fields":
                case "pageindex":
                case "pagesize":
                    throw 'key不可为Table、Query、Sort、Fields、PageIndex、PageSize';
                default:
                    break;
            }
            _other[key] = value;
            return csf;
        }

        csf.removeParam = function (key) {
            delete _other[key];
            return csf;
        }

        csf.build = function () {
            return new DbBuildResult(_tb, _query, _fields, _sort, _groupBy, _pageIndex, _pageSize, _isDistinct, _other);
        }
    }

    sf.from = function (tableName) {
        if (typeof (tableName) != 'string' || tableName.length == 0)
            throw '错误的表名格式';
        return new cursorBuilder(tableName);
    }

};

DbBuilder.from = function (tableName) {
    return new DbBuilder().from(tableName);
}
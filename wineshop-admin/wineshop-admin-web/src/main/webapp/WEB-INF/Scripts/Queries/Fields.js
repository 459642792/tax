var Fields = function () {
    var sf = this;
    var cols = [];
    var pushFields = function (key, asName, fType) {
        if (typeof (key) != 'string' || key.length == 0)
            throw {
                message: "key必须是不为空的字符串",
                trace: console.trace()
            }
        cols.push({
            Fields: key,
            AsName: asName || "",
            //FType: fType
        });
    };
    //  Fields:字段名
    //  AsName:字段别名
    //  FType:字段类型 1:获取该字段,2:sum该字段,4:count该字段
    sf.include = function (key, asName) {
        pushFields(key, asName, 1);
        return sf;
    };

    //sf.sum = function (key, asName) {
    //    pushFields(key, asName, 2);
    //};

    //sf.count = function (key, asName) {
    //    pushFields(key, asName, 4);
    //};

    //sf.exclude = function (key, asName) {
    //    if (isIn === true)
    //        throw {
    //            message: "对于include时的字段配置不再进行exclude",
    //            trace: console.trace()
    //        };
    //    isIn = false;
    //    if (typeof (key) != 'string' || key.length == 0)
    //        throw {
    //            message: "key必须是不为空的字符串",
    //            trace: console.trace()
    //        }
    //    cols[key] = asName || "";
    //    return sf;
    //}

    sf.reset = function () {
        cols = {};
        return sf;
    }

    sf.getData = function () {
        return cols.concat([]);
    }
}

Fields.include = function (key, asName) {
    return new Fields().include(key, asName);
}
//Fields.exclude = function (key, asName) {
//    return new Fields().exclude(key, asName);
//}
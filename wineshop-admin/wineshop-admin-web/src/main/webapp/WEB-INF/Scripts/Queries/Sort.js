var Sort = function () {
    var sf = this;
    var sortData = [];
    sf.getData = function () {
        return sortData.concat([]);
    }
    sf.asc = function (key) {
        if (typeof (key) === 'string' && key.length > 0) {
            sortData.push({
                Field: key,
                Sort: 1
            });
            return sf;
        }
        throw {
            message: "排序键不可为空",
            trace: console.trace()
        }
    }

    sf.desc = function (key) {
        if (typeof (key) === 'string' && key.length > 0) {
            sortData.push({
                Field: key,
                Sort: -1
            });
            return sf;
        }
        throw {
            message: "排序键不可为空",
            trace: console.trace()
        }
    }

    sf.reset = function () {
        sortData.splice(0);
        return sf;
    }

    sf.revoked = function () {
        sortData.pop();
        return sf;
    }
}
Sort.asc = function (key) {
    return new Sort().asc(key);
}

Sort.desc = function (key) {
    return new Sort().desc(key);
}
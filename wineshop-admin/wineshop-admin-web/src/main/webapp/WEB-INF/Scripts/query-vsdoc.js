Query = function () {
    var sf = this;
    sf.Null = function () {
        /// <summary>
        /// 获取一个完全查询的iQuery对象
        /// </summary>
        /// <returns type="iQuery" />
        return {};
    };
    sf.and = function () {
        /// <signature>
        ///     <summary>
        ///     获取一个And查询对象
        ///     </summary>
        ///     <param name="..." optional="true">
        ///     多个查询条件iQuery，参数个数无限制
        ///     </param>
        ///     <returns type="iQuery" />
        /// </signature>
        /// <signature>
        ///     <summary>
        ///     获取一个And查询对象
        ///     </summary>
        ///     <param name="queryArray" type="Array">一个查询条件iQuery数组</param>
        ///     <returns type="iQuery" />
        /// </signature>
        return {};
    };

    sf.or = function () {
        /// <signature>
        ///     <summary>
        ///     获取一个Or查询对象
        ///     </summary>
        ///     <param name="..." optional="true">
        ///     多个查询条件iQuery，参数个数无限制
        ///     </param>
        ///     <returns type="iQuery" />
        /// </signature>
        /// <signature>
        ///     <summary>
        ///     获取一个Or查询对象
        ///     </summary>
        ///     <param name="queryArray" type="Array">一个查询条件iQuery数组</param>
        ///     <returns type="iQuery" />
        /// </signature>
        return {};
    };

    sf.eq = function (key, value, nonQuery) {
        /// <summary>
        /// 获取指定字段的值等于指定值的iQuery对象
        /// </summary>
        /// <param name="key" type="String">
        /// 字段名
        /// </param>
        /// <param name="value" type="Object">
        /// 字段值
        /// </param>
        /// <param name="nonQuery" type="Object|Array" optional="true">
        /// 排除的查询条件，当value值为该值的时候，将被视为不添加此查询条件
        /// &#10;当该值可为数组，指示多种情况
        /// </param>
        /// <returns type="iQuery" />
        return {};
    };

    sf.gt = function (key, value, nonQuery) {
        /// <summary>
        /// 获取指定键的值大于指定值的iQuery对象
        /// </summary>
        /// <param name="key" type="String">
        /// 字段名
        /// </param>
        /// <param name="value" type="Object">
        /// 字段值
        /// </param>
        /// <param name="nonQuery" type="Object|Array" optional="true">
        /// 排除的查询条件，当value值为该值的时候，将被视为不添加此查询条件
        /// &#10;当该值可为数组，指示多种情况
        /// </param>
        /// <returns type="iQuery" />
        return {};
    };

    sf.gte = function (key, value, nonQuery) {
        /// <summary>
        /// 获取指定键的值大于或等于指定值的iQuery对象
        /// </summary>
        /// <param name="key" type="String">
        /// 字段名
        /// </param>
        /// <param name="value" type="Object">
        /// 字段值
        /// </param>
        /// <param name="nonQuery" type="Object|Array" optional="true">
        /// 排除的查询条件，当value值为该值的时候，将被视为不添加此查询条件
        /// &#10;当该值可为数组，指示多种情况
        /// </param>
        /// <returns type="iQuery" />
        return {};
    };

    sf.lt = function (key, value, nonQuery) {
        /// <summary>
        /// 获取指定键的值小于指定值的iQuery对象
        /// </summary>
        /// <param name="key" type="String">
        /// 字段名
        /// </param>
        /// <param name="value" type="Object">
        /// 字段值
        /// </param>
        /// <param name="nonQuery" type="Object|Array" optional="true">
        /// 排除的查询条件，当value值为该值的时候，将被视为不添加此查询条件
        /// &#10;当该值可为数组，指示多种情况
        /// </param>
        /// <returns type="iQuery" />
        return {};
    };
    sf.lte = function (key, value, nonQuery) {
        /// <summary>
        /// 获取指定键的值小于等于指定值的iQuery对象
        /// </summary>
        /// <param name="key" type="String">
        /// 字段名
        /// </param>
        /// <param name="value" type="Object">
        /// 字段值
        /// </param>
        /// <param name="nonQuery" type="Object|Array" optional="true">
        /// 排除的查询条件，当value值为该值的时候，将被视为不添加此查询条件
        /// &#10;当该值可为数组，指示多种情况
        /// </param>
        /// <returns type="iQuery" />
        return {};
    };

    sf.in = function (key, values) {
        /// <signature>
        ///     <summary>
        ///     获取指定键的值存在于指定数组值的iQuery对象
        ///     </summary>
        ///     <param name="key" type="String">
        ///     字段名
        ///     </param>
        ///     <param name="..." optional="true">
        ///     从第二个参数开始无限制的参数都将被作为in中的项
        ///     </param>
        ///     <returns type="iQuery" />
        /// </signature>
        /// <signature>
        ///     <summary>
        ///     获取指定键的值存在于指定数组值的iQuery对象
        ///     </summary>
        ///     <param name="key" type="String">
        ///     字段名
        ///     </param>
        ///     <param name="values" type="Array">
        ///     字段值数组
        ///     </param>
        ///     <returns type="iQuery" />
        /// </signature>
        /// <signature>
        ///     <summary>
        ///     获取指定键的值存在于指定子查询的iQuery对象
        ///     </summary>
        ///     <param name="key" type="String">
        ///     字段名
        ///     </param>
        ///     <param name="values" type="CursorBuilder">
        ///     子查询Builder
        ///     </param>
        ///     <returns type="iQuery" />
        /// </signature>
        return {};
    };

    sf.ne = function (key, value, nonQuery) {
        /// <summary>
        /// 获取指定键的值不等于指定值的iQuery对象
        /// </summary>
        /// <param name="key" type="String">
        /// 字段名
        /// </param>
        /// <param name="value" type="Object">
        /// 字段值
        /// </param>
        /// <param name="nonQuery" type="Object|Array" optional="true">
        /// 排除的查询条件，当value值为该值的时候，将被视为不添加此查询条件
        /// &#10;当该值可为数组，指示多种情况
        /// </param>
        /// <returns type="iQuery" />
        return {};
    };

    sf.like = function (key, value, nonQuery) {
        /// <summary>
        /// 获取指定键的值模糊查询的iQuery对象
        /// </summary>
        /// <param name="key" type="String">
        /// 字段名
        /// </param>
        /// <param name="reg" type="Object">
        /// 模糊查询表达式
        /// </param>
        /// <param name="nonQuery" type="Object|Array" optional="true">
        /// 排除的查询条件，当value值为该值的时候，将被视为不添加此查询条件
        /// &#10;当该值可为数组，指示多种情况
        /// </param>
        /// <returns type="iQuery" />
        return {};
    };

    sf.mod = function (key, m, l) {
        /// <summary>
        /// 获取指定键的值除以指定值(m)的余数为指定值(l)的iQuery对象
        /// </summary>
        /// <param name="key" type="String">
        /// 字段名
        /// </param>
        /// <param name="m" type="Number">
        /// 除数
        /// </param>
        /// <param name="l" type="Number">
        /// 余数
        /// </param>
        /// <returns type="iQuery" />
        return {};
    };

    sf.not = function (key, value) {
        /// <summary>
        /// 反转查询条件，即将当前查询条件返回Boolean取反
        /// </summary>
        /// <param name="key" type="String">
        /// 字段名
        /// </param>
        /// <param name="m" type="iQuery">
        /// 查询条件或正则表达式
        /// </param>
        /// <returns type="iQuery" />
        return {};
    };

    sf.notIn = function (key, values) {
        /// <signature>
        ///     <summary>
        ///     获取指定键的值不存在于指定数组值的iQuery对象
        ///     </summary>
        ///     <param name="key" type="String">
        ///     字段名
        ///     </param>
        ///     <param name="..." optional="true">
        ///     从第二个参数开始无限制的参数都将被作为in中的项
        ///     </param>
        ///     <returns type="iQuery" />
        /// </signature>
        /// <signature>
        ///     <summary>
        ///     获取指定键的值不存在于指定数组值的iQuery对象
        ///     </summary>
        ///     <param name="key" type="String">
        ///     字段名
        ///     </param>
        ///     <param name="values" type="Array">
        ///     字段值数组
        ///     </param>
        ///     <returns type="iQuery" />
        /// </signature>
        /// <signature>
        ///     <summary>
        ///     获取指定键的值不存在于指定子查询的iQuery对象
        ///     </summary>
        ///     <param name="key" type="String">
        ///     字段名
        ///     </param>
        ///     <param name="values" type="CursorBuilder">
        ///     子查询Builder
        ///     </param>
        ///     <returns type="iQuery" />
        /// </signature>
        return {};
    };

    sf.bitAnd = function (key, value) {
        /// <signature>
        ///     <summary>
        ///     获取指定键的值与指定数字按位与(&)不为0的iQuery对象
        ///     </summary>
        ///     <param name="key" type="String">
        ///     字段名
        ///     </param>
        ///     <param name="value" type="Number">
        ///     要进行按位与运算的数字
        ///     </param>
        ///     <returns type="iQuery" />
        /// </signature>
        return {};
    };

    sf.bitOr = function (key, value) {
        /// <signature>
        ///     <summary>
        ///     获取指定键的值与指定数字按位或(|)不为0的iQuery对象
        ///     </summary>
        ///     <param name="key" type="String">
        ///     字段名
        ///     </param>
        ///     <param name="value" type="Number">
        ///     要进行按位或运算的数字
        ///     </param>
        ///     <returns type="iQuery" />
        /// </signature>
        return {};
    };

    sf.bitXor = function (key, value) {
        /// <signature>
        ///     <summary>
        ///     获取指定键的值与指定数字按位异或(^)不为0的iQuery对象
        ///     </summary>
        ///     <param name="key" type="String">
        ///     字段名
        ///     </param>
        ///     <param name="value" type="Number">
        ///     要进行按位异或运算的数字
        ///     </param>
        ///     <returns type="iQuery" />
        /// </signature>
        return {};
    };
}

Query.Null = function () {
    /// <summary>
    /// 获取一个完全查询的iQuery对象
    /// </summary>
    /// <returns type="iQuery" />
    return {};
};
Query.and = function () {
    /// <signature>
    ///     <summary>
    ///     获取一个And查询对象
    ///     </summary>
    ///     <param name="..." optional="true">
    ///     多个查询条件iQuery，参数个数无限制
    ///     </param>
    ///     <returns type="iQuery" />
    /// </signature>
    /// <signature>
    ///     <summary>
    ///     获取一个And查询对象
    ///     </summary>
    ///     <param name="queryArray" type="Array">一个查询条件iQuery数组</param>
    ///     <returns type="iQuery" />
    /// </signature>
    return {};
};

Query.or = function () {
    /// <signature>
    ///     <summary>
    ///     获取一个Or查询对象
    ///     </summary>
    ///     <param name="..." optional="true">
    ///     多个查询条件iQuery，参数个数无限制
    ///     </param>
    ///     <returns type="iQuery" />
    /// </signature>
    /// <signature>
    ///     <summary>
    ///     获取一个Or查询对象
    ///     </summary>
    ///     <param name="queryArray" type="Array">一个查询条件iQuery数组</param>
    ///     <returns type="iQuery" />
    /// </signature>
    return {};
};

Query.eq = function (key, value) {
    /// <summary>
    /// 获取指定字段的值等于指定值的iQuery对象
    /// </summary>
    /// <param name="key" type="String">
    /// 字段名
    /// </param>
    /// <param name="value" type="Object">
    /// 字段值
    /// </param>
    /// <returns type="iQuery" />
    return {};
};
Query.gt = function (key, value) {
    /// <summary>
    /// 获取指定键的值大于指定值的iQuery对象
    /// </summary>
    /// <param name="key" type="String">
    /// 字段名
    /// </param>
    /// <param name="value" type="Object">
    /// 字段值
    /// </param>
    /// <returns type="iQuery" />
    return {};
};
Query.gte = function (key, value) {
    /// <summary>
    /// 获取指定键的值大于或等于指定值的iQuery对象
    /// </summary>
    /// <param name="key" type="String">
    /// 字段名
    /// </param>
    /// <param name="value" type="Object">
    /// 字段值
    /// </param>
    /// <returns type="iQuery" />
    return {};
};

Query.lt = function (key, value) {
    /// <summary>
    /// 获取指定键的值小于指定值的iQuery对象
    /// </summary>
    /// <param name="key" type="String">
    /// 字段名
    /// </param>
    /// <param name="value" type="Object">
    /// 字段值
    /// </param>
    /// <returns type="iQuery" />
    return {};
};

Query.lte = function (key, value) {
    /// <summary>
    /// 获取指定键的值小于等于指定值的iQuery对象
    /// </summary>
    /// <param name="key" type="String">
    /// 字段名
    /// </param>
    /// <param name="value" type="Object">
    /// 字段值
    /// </param>
    /// <returns type="iQuery" />
    return {};
};

Query.in = function (key, values) {
    /// <signature>
    ///     <summary>
    ///     获取指定键的值存在于指定数组值的iQuery对象
    ///     </summary>
    ///     <param name="key" type="String">
    ///     字段名
    ///     </param>
    ///     <param name="..." optional="true">
    ///     从第二个参数开始无限制的参数都将被作为in中的项
    ///     </param>
    ///     <returns type="iQuery" />
    /// </signature>
    /// <signature>
    ///     <summary>
    ///     获取指定键的值存在于指定数组值的iQuery对象
    ///     </summary>
    ///     <param name="key" type="String">
    ///     字段名
    ///     </param>
    ///     <param name="values" type="Array">
    ///     字段值数组
    ///     </param>
    ///     <returns type="iQuery" />
    /// </signature>
    /// <signature>
    ///     <summary>
    ///     获取指定键的值存在于指定子查询的iQuery对象
    ///     </summary>
    ///     <param name="key" type="String">
    ///     字段名
    ///     </param>
    ///     <param name="values" type="CursorBuilder">
    ///     子查询Builder
    ///     </param>
    ///     <returns type="iQuery" />
    /// </signature>
    return {};
};

Query.ne = function (key, value) {
    /// <summary>
    /// 获取指定键的值不等于指定值的iQuery对象
    /// </summary>
    /// <param name="key" type="String">
    /// 字段名
    /// </param>
    /// <param name="value" type="Object">
    /// 字段值
    /// </param>
    /// <returns type="iQuery" />
    return {};
};

Query.like = function (key, reg) {
    /// <summary>
    /// 获取指定键的值模糊查询的iQuery对象
    /// </summary>
    /// <param name="key" type="String">
    /// 字段名
    /// </param>
    /// <param name="reg" type="Object">
    /// 模糊查询表达式
    /// </param>
    /// <returns type="iQuery" />
    return {};
};

Query.mod = function (key, m, l) {
    /// <summary>
    /// 获取指定键的值除以指定值(m)的余数为指定值(l)的iQuery对象
    /// </summary>
    /// <param name="key" type="String">
    /// 字段名
    /// </param>
    /// <param name="m" type="Number">
    /// 除数
    /// </param>
    /// <param name="l" type="Number">
    /// 余数
    /// </param>
    /// <returns type="iQuery" />
    return {};
};

Query.not = function (key, value) {
    /// <summary>
    /// 反转查询条件，即将当前查询条件返回Boolean取反
    /// </summary>
    /// <param name="key" type="String">
    /// 字段名
    /// </param>
    /// <param name="m" type="iQuery">
    /// 查询条件或正则表达式
    /// </param>
    /// <returns type="iQuery" />
    return {};
};

Query.notIn = function () {
    /// <signature>
    ///     <summary>
    ///     获取指定键的值不存在于指定数组值的iQuery对象
    ///     </summary>
    ///     <param name="key" type="String">
    ///     字段名
    ///     </param>
    ///     <param name="..." optional="true">
    ///     从第二个参数开始无限制的参数都将被作为in中的项
    ///     </param>
    ///     <returns type="iQuery" />
    /// </signature>
    /// <signature>
    ///     <summary>
    ///     获取指定键的值不存在于指定数组值的iQuery对象
    ///     </summary>
    ///     <param name="key" type="String">
    ///     字段名
    ///     </param>
    ///     <param name="values" type="Array">
    ///     字段值数组
    ///     </param>
    ///     <returns type="iQuery" />
    /// </signature>
    /// <signature>
    ///     <summary>
    ///     获取指定键的值不存在于指定子查询的iQuery对象
    ///     </summary>
    ///     <param name="key" type="String">
    ///     字段名
    ///     </param>
    ///     <param name="values" type="CursorBuilder">
    ///     子查询Builder
    ///     </param>
    ///     <returns type="iQuery" />
    /// </signature>
    return {};
};
Query.bitAnd = function (key, value) {
    /// <signature>
    ///     <summary>
    ///     获取指定键的值与指定数字按位与(&)不为0的iQuery对象
    ///     </summary>
    ///     <param name="key" type="String">
    ///     字段名
    ///     </param>
    ///     <param name="value" type="Number">
    ///     要进行按位与运算的数字
    ///     </param>
    ///     <returns type="iQuery" />
    /// </signature>
    return {};
};

Query.bitOr = function (key, value) {
    /// <signature>
    ///     <summary>
    ///     获取指定键的值与指定数字按位或(|)不为0的iQuery对象
    ///     </summary>
    ///     <param name="key" type="String">
    ///     字段名
    ///     </param>
    ///     <param name="value" type="Number">
    ///     要进行按位或运算的数字
    ///     </param>
    ///     <returns type="iQuery" />
    /// </signature>
    return {};
};

Query.bitXor = function (key, value) {
    /// <signature>
    ///     <summary>
    ///     获取指定键的值与指定数字按位异或(^)不为0的iQuery对象
    ///     </summary>
    ///     <param name="key" type="String">
    ///     字段名
    ///     </param>
    ///     <param name="value" type="Number">
    ///     要进行按位异或运算的数字
    ///     </param>
    ///     <returns type="iQuery" />
    /// </signature>
    return {};
};


DbBuildResult = function () {
    /// <signature>
    ///     <summary>
    ///     查询编译结果
    ///     </summary>
    ///     <returns type="DbBuildResult" />
    /// </signature>
    var sf = this;

    sf.Table = "";
    sf.Query = "";
    sf.Sort = [];
    sf.Fields = [];
    sf.PageIndex = 0;
    sf.PageSize = 0;
    sf.IsDistinct = false;
    sf.GroupBy = [];
};

DbBuilder = function () {
    var sf = this;

    var cursorBuilder = function (tableName) {
        var csf = this;
        csf.distinct = function (isDistinct) {
            /// <signature>
            ///     <summary>
            ///     设置是否进行去除重复查询
            ///     </summary>
            ///     <param name="isDistinct" type="Boolean">
            ///     是否去除重复
            ///     </param>
            ///     <returns type="CursorBuilder" />
            /// </signature>
            return csf;
        };

        csf.where = function (query) {
            /// <signature>
            ///     <summary>
            ///     设置where查询条件
            ///     </summary>
            ///     <param name="query" type="Query">
            ///     查询条件
            ///     </param>
            ///     <returns type="CursorBuilder" />
            /// </signature>
            return csf;
        };

        csf.sortBy = function (sort) {
            /// <signature>
            ///     <summary>
            ///     设置排序
            ///     </summary>
            ///     <param name="sort" type="Sort">
            ///     查询条件
            ///     </param>
            ///     <returns type="CursorBuilder" />
            /// </signature>
            return csf;
        };

        csf.groupBy = function () {
            /// <signature>
            ///     <summary>
            ///     设置where查询条件
            ///     </summary>
            ///     <param name="..." optional="true">
            ///     多个group by字段名，参数个数无限制
            ///     </param>
            ///     <returns type="CursorBuilder" />
            /// </signature>
            /// <signature>
            ///     <summary>
            ///     设置where查询条件
            ///     </summary>
            ///     <param name="fieldsArray" type="Array">用于group by的字段名数组</param>
            ///     <returns type="CursorBuilder" />
            /// </signature>
            return csf;
        };

        csf.select = function (fields) {
            /// <signature>
            ///     <summary>
            ///     设置select查询字段
            ///     </summary>
            ///     <param name="fields" type="Fields">
            ///     查询条件
            ///     </param>
            ///     <returns type="CursorBuilder" />
            /// </signature>
            return csf;
        };

        csf.pageIndex = function (idx) {
            /// <signature>
            ///     <summary>
            ///     设置从0开始的页索引
            ///     </summary>
            ///     <param name="idx" type="Number">
            ///     页索引
            ///     </param>
            ///     <returns type="CursorBuilder" />
            /// </signature>
            return csf;
        };

        csf.pageSize = function (size) {
            /// <signature>
            ///     <summary>
            ///     设置每页查询条数
            ///     </summary>
            ///     <param name="size" type="Number">
            ///     每页查询条数，如果该值<=0，则被视为不分页
            ///     </param>
            ///     <returns type="CursorBuilder" />
            /// </signature>
            return csf;
        };

        csf.setParam = function (key, value) {
            /// <signature>
            ///     <summary>
            ///     设置其它post参数
            ///     </summary>
            ///     <param name="key" type="String">
            ///     参数名
            ///     </param>
            ///     <param name="value" type="Object">
            ///     参数值
            ///     </param>
            ///     <returns type="CursorBuilder" />
            /// </signature>
            return csf;
        };

        csf.removeParam = function (key) {
            /// <signature>
            ///     <summary>
            ///     删除其它参数
            ///     </summary>
            ///     <param name="key" type="String">
            ///     要删除的参数名
            ///     </param>
            ///     <returns type="CursorBuilder" />
            /// </signature>
            return csf;
        };

        csf.build = function () {
            /// <signature>
            ///     <summary>
            ///     编译该查询
            ///     </summary>
            ///     <returns type="CursorBuilder" />
            /// </signature>
            return new DbBuildResult();
        };
    };

    sf.from = function (tableName) {
        /// <signature>
        ///     <summary>
        ///     设置查询表
        ///     </summary>
        ///     <param name="tableName" type="String">
        ///     表名
        ///     </param>
        ///     <returns type="CursorBuilder" />
        /// </signature>
        return new cursorBuilder(tableName);
    };

};

DbBuilder.from = function (tableName) {
    /// <signature>
    ///     <summary>
    ///     设置查询表
    ///     </summary>
    ///     <param name="tableName" type="String">
    ///     表名
    ///     </param>
    ///     <returns type="CursorBuilder" />
    /// </signature>
    return new DbBuilder().from(tableName);
};


var Sort = function () {
    /// <signature>
    ///     <summary>
    ///     排序
    ///     </summary>
    ///     <returns type="Sort" />
    /// </signature>
    var sf = this;
    sf.asc = function (key) {
        /// <signature>
        ///     <summary>
        ///     设置设置指定字段升序排序
        ///     </summary>
        ///     <param name="key" type="String">
        ///     字段名
        ///     </param>
        ///     <returns type="Sort" />
        /// </signature>
        return sf;
    };

    sf.desc = function (key) {
        /// <signature>
        ///     <summary>
        ///     设置设置指定字段降序排序
        ///     </summary>
        ///     <param name="key" type="String">
        ///     字段名
        ///     </param>
        ///     <returns type="Sort" />
        /// </signature>
        return sf;
    };

    sf.reset = function () {
        /// <signature>
        ///     <summary>
        ///     重置整个Sort
        ///     </summary>
        ///     <returns type="Sort" />
        /// </signature>
        return sf;
    };

    sf.revoked = function () {
        /// <signature>
        ///     <summary>
        ///     撤销上一次的设置操作
        ///     </summary>
        ///     <returns type="Sort" />
        /// </signature>
        return sf;
    };
};
Sort.asc = function (key) {
    /// <signature>
    ///     <summary>
    ///     设置设置指定字段升序排序
    ///     </summary>
    ///     <param name="key" type="String">
    ///     字段名
    ///     </param>
    ///     <returns type="Sort" />
    /// </signature>
    return new Sort().asc(key);
};

Sort.desc = function (key) {
    /// <signature>
    ///     <summary>
    ///     设置设置指定字段降序排序
    ///     </summary>
    ///     <param name="key" type="String">
    ///     字段名
    ///     </param>
    ///     <returns type="Sort" />
    /// </signature>
    return new Sort().desc(key);
};


var Fields = function () {
    /// <signature>
    ///     <summary>
    ///     查询字段
    ///     </summary>
    ///     <returns type="Fields" />
    /// </signature>
    var sf = this;
    sf.include = function (key, asName) {
        /// <signature>
        ///     <summary>
        ///     设置要查询的字段
        ///     </summary>
        ///     <param name="key" type="String">
        ///     字段名
        ///     </param>
        ///     <param name="asName" type="String">
        ///     字段别名
        ///     </param>
        ///     <returns type="Fields" />
        /// </signature>
        return sf;
    };

    sf.reset = function () {
        /// <signature>
        ///     <summary>
        ///     重置整个Fields
        ///     </summary>
        ///     <returns type="Fields" />
        /// </signature>
        return sf;
    };
};

Fields.include = function (key, asName) {
    /// <signature>
    ///     <summary>
    ///     设置要查询的字段
    ///     </summary>
    ///     <param name="key" type="String">
    ///     字段名
    ///     </param>
    ///     <param name="asName" type="String">
    ///     字段别名
    ///     </param>
    ///     <returns type="Fields" />
    /// </signature>
    return new Fields().include(key, asName);
};
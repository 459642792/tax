var commonJs = {
    pageTable: function (table) {
        var initComs = new Array;
        initComs.push({"mDataProp": "Id", "sClass": "hidden"});
        $.each(table.aoColumns, function (i, colums) {
            initComs.push({mDataProp: colums});
        })
        var responsiveHelper_dt_basic = undefined;
        var breakpointDefinition = {
            tablet: 1024,
            phone: 480
        };
        var searchCount = 1;
        window.pageTable = $(table.dom).dataTable({
            "sDom": "<'dt-toolbar'<'col-xs-12 col-sm-6'f><'col-sm-6 col-xs-12 hidden-xs'l>>" +
            "tr" +
            "<'dt-toolbar-footer'<'col-sm-6 col-xs-12 hidden-xs'i><'col-xs-12 col-sm-6'p>>",
            oLanguage: window.gjhDataTable_CN_Lang,
            bFilter: false,//取消过滤功能
            "autoWidth": true,
            "preDrawCallback": function () {
                // Initialize the responsive datatables helper once.
                //if (!responsiveHelper_dt_basic) {
                //    responsiveHelper_dt_basic = new ResponsiveDatatablesHelper($(dom), breakpointDefinition);
                //}
            },
            "rowCallback": function (nRow) {
                //responsiveHelper_dt_basic.createExpandIcon(nRow);
            },
            "drawCallback": function (oSettings) {
                //responsiveHelper_dt_basic.respond();
            },
            "bProcessing": true, // 是否显示取数据时的那个等待提示
            bSort: 0,
            "bServerSide": true,//这个用来指明是通过服务端来取数据
            "sAjaxSource": table.url,//这个是请求的地址
            "fnServerData": function (queryUrl, pageData, cb) {// 获取数据的处理函数
                table.option['pageIndex'] = window.getPageIndex(pageData) + 1;
                table.option['pageSize'] = window.getPageSize(pageData);
                $.post(queryUrl, table.option, function (result, status) {
                    cb({
                        "draw": (searchCount++),
                        "recordsTotal": result.TotalCount,
                        "recordsFiltered": result.TotalCount,
                        "data": result.Data
                    });
                })
            },
            "aoColumns": initComs,
            "aoColumnDefs": [{
                "aTargets": [initComs.length],
                mData: "Id", "mRender": function (data, type, full) {
                    var links = [];
                    //禁用
                    links.push('<a class="tableBtn" href="javascript:void(TabDel(' + full.Id + '))"><i class="fa fa-times txt-color-blue hidden-md hidden-sm hidden-xs"></i>禁用</a>');
                    //启用
                    links.push('<a class="tableBtn" href="javascript:void(TabDel(' + full.Id + '))"><i class="fa fa-check txt-color-blue hidden-md hidden-sm hidden-xs"></i>启用</a>');
                    //编辑
                    links.push('<a class="tableBtn" href="' + table.editUrl + '&Id=' + full.Id + '"  data-toggle="modal" data-backdrop="static" data-target="#remoteModal"><i class="fa fa-wrench txt-color-blue hidden-md hidden-sm hidden-xs"></i>编辑</a>');
                    return links.join(" ");
                }
            }]
        });
    },
    Upload: function (upload, images, maxFiles) {
        //初始化图片上传
        loadScript("/Scripts/plugin/dropzone/dropzone.min.js", function () {
            setTimeout(function () {
                $(upload).dropzone({
                    url: " /Common/UploadImg?type=WebSite&shuiYin=false",
                    addRemoveLinks: true,
                    maxFiles: maxFiles,//最多允许上传图片个数
                    parallelUploads: 1,//图片上传并发个数。建议设置为一，批量一个一个上传
                    maxFilesize: 2,//控制单张图片大小最大为2兆
                    acceptedFiles: ".jpg,.gif,.png,.jpeg,.JPG,.GIF,.PNG,.JPEG",
                    dictFallbackMessage: "您的浏览器暂不支持拖动上传哦。",
                    dictMaxFilesExceeded: "不要太贪心了，您一共只能上传{{maxFiles}}张图片。",
                    dictRemoveFile: "移除",
                    success: function (file, response) {
                        file.previewElement.classList.add("dz-success");
                        var str = $(upload).children('.imgval').val();
                        str += '||' + file.name + '&' + response;
                        $(upload).children('.imgval').val(str);
                    },
                    error: function (file, response) {
                        file.previewElement.classList.add("dz-error");
                        window.simpleNotify("文件上传失败，可能的原因有：<br/>1. 文件类型不正确<br/>2. 文件个数超过限制", '提示', "error");
                    },
                    maxfilesexceeded: function (file) {//图片个数超过限制时功能
                        this.removeFile(file);
                    },
                    init: function () {
                        var th = this;

                        //加入图片
                        function initImage() {
                            var imgarry = images;
                            var file = {
                                name: null,
                                size: 123,
                                serverId: 1234,
                                accepted: true,
                                status: 'success',
                                upload: {
                                    progress: 100,
                                    total: 123, // to fake
                                    bytesSent: 123 // to fake
                                }
                            };
                            var str = '';
                            $.each(imgarry, function (i, item) {
                                str += '||' + item.name + '&' + item.src;
                                var mockFile = {name: item.name, size: 123, accepted: true};
                                th.emit("addedfile", mockFile);
                                th.emit("thumbnail", mockFile, item.src);
                                file.name = item.name;
                                th.files.push(file);
                                th.element.classList.add("dz-started");
                            })
                            $(upload).children('.imgval').val(str);
                            //计算允许上传图片数量
                            th.options.maxFiles = maxFiles - $(upload).children('.imgval').val().split("||").length;
                        }

                        initImage();

                        //移除图片
                        th.on("removedfile", function (file) {
                            if (file.accepted) {
                                var array = $(upload).children('.imgval').val().split("||");
                                var newArray = [];
                                for (var i = 0; i < array.length; i++) {
                                    var item = array[i].split("&")[0];
                                    if (array[i].split("&")[0] != file.name) {
                                        newArray.push(array[i].split("&")[0] + "&" + array[i].split("&")[1]);
                                    } else {
                                        $.each(th.files, function (b, item) {
                                            if (item.name == array[i].split("&")[0]) {
                                                th.files.splice(b, 1);
                                            }
                                        })
                                    }
                                }
                                $(upload).children('.imgval').val(newArray.join("||"));
                            }
                            //计算允许上传图片数量
                            th.options.maxFiles = maxFiles - $(upload).children('.imgval').val().split("||").length - 1;
                        });


                    }
                });
            }, 100)
        })
    }
}
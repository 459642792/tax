window.commonRegex = {
    phone: function () {
        return /^1[34578][0-9]{9}$|^0[0-9]{2,3}[-_/\\.]?[0-9]{7,8}$/;
    },
    email: function () {
        return /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
    }
}

//根据邮箱地址，获取邮箱服务供应商地址
window.getEmailServer = function (emailAddr) {
    if ($.trim(emailAddr) != "") {
        var emailServer = $.trim(emailAddr).split('@')[1];
        emailServer = emailServer.toLowerCase();
        if (emailServer == '163.com') {
            return 'mail.163.com';
        } else if (emailServer == 'vip.163.com') {
            return 'vip.163.com';
        } else if (emailServer == '126.com') {
            return 'mail.126.com';
        } else if (emailServer == 'qq.com' || emailServer == 'vip.qq.com' || emailServer == 'foxmail.com') {
            return 'mail.qq.com';
        } else if (emailServer == 'gmail.com') {
            return 'mail.google.com';
        } else if (emailServer == 'sohu.com') {
            return 'mail.sohu.com';
        } else if (emailServer == 'tom.com') {
            return 'mail.tom.com';
        } else if (emailServer == 'vip.sina.com') {
            return 'vip.sina.com';
        } else if (emailServer == 'sina.com.cn' || emailServer == 'sina.com') {
            return 'mail.sina.com.cn';
        } else if (emailServer == 'tom.com') {
            return 'mail.tom.com';
        } else if (emailServer == 'yahoo.com.cn' || emailServer == 'yahoo.cn') {
            return 'mail.cn.yahoo.com';
        } else if (emailServer == 'tom.com') {
            return 'mail.tom.com';
        } else if (emailServer == 'yeah.net') {
            return 'www.yeah.net';
        } else if (emailServer == '21cn.com') {
            return 'mail.21cn.com';
        } else if (emailServer == 'hotmail.com') {
            return 'www.hotmail.com';
        } else if (emailServer == 'sogou.com') {
            return 'mail.sogou.com';
        } else if (emailServer == '188.com') {
            return 'www.188.com';
        } else if (emailServer == '139.com') {
            return 'mail.10086.cn';
        } else if (emailServer == '189.cn') {
            return 'webmail15.189.cn/webmail';
        } else if (emailServer == 'wo.com.cn') {
            return 'mail.wo.com.cn/smsmail';
        } else if (emailServer == '139.com') {
            return 'mail.10086.cn';
        } else {
            return '';
        }
    }
    else {
        return "";
    }
}

/*
*   table对象
*  var table= {
*        dom: $('#wabsite_list'),//table节点
*        ajaxUrl: "/Admin/WebSite/GetList",//ajax请求地址
*        aoColumns: ["Name", "Url", "EnabledFlagStr", "CreationDateStr", "LastUpdateDateStr"],//table 要显示的列
*        primaryKey:"Id",//主键
*        jsContainer: null,//js宿主对象
*        diyColumn: [     //自定义列
*            {
*                aTargets: [6],//要显示的位置
*                mData:"Id",//主键
*                mRender: function (data, type, full) {//返回参数
*                    var str = '';
*                    str += '<a class="tableBtn hidden-mobile" href="/Admin/WebSite/Edite?id=' + data + '" data-toggle="modal" data-backdrop="static"';
*                    str += ' data-target="#remoteModal"><i class="fa fa-edit txt-color-blue hidden-md hidden-sm hidden-xs"></i>编辑</a>';
*                    return str;
*                }
*            }
*        ],
*        ajxaParam: function () {//异步请求参数
*            return {
*                Name: $('#messageName').val()
*            };
*        },
*        loadedFunction: function(){ //加载成功后执行的函数
*        }
*    }
*
*     //调用并执行  参数1：table对象
*    window.initPageTab(table)
*
*/
window.initPageTab = function (table) {
    loadScript("Scripts/plugin/datatables/jquery.dataTables.min.js", function () {
        loadScript("Scripts/plugin/datatables/dataTables.colVis.min.js", function () {
            loadScript("Scripts/plugin/datatables/dataTables.tableTools.min.js", function () {
                loadScript("Scripts/plugin/datatables/dataTables.bootstrap.min.js", function () {
                    loadScript("Scripts/plugin/datatable-responsive/datatables.responsive.min.js", function () {
                        var initComs = new Array;
                        initComs.push({"mDataProp": table.primaryKey, "sClass": "hidden"});
                        $.each(table.aoColumns, function (i, colums) {
                            //字段列表名称
                            var columsName = colums.name||colums;
                            //获取class类名
                            var classNmae = colums.sClass||'';
                            initComs.push({"mDataProp": columsName, "sClass": classNmae});
                        })
                        var responsiveHelper_dt_basic = undefined;
                        var breakpointDefinition = {
                            tablet: 1024,
                            phone: 480
                        };
                        var searchCount = 1;
                        //if (!table.jsContainer) {
                        //    window.pageTable = {};
                        //    table.jsContainer = window.pageTable;
                        //}
                        table.container = $(table.dom).dataTable({
                            "sDom": "<'dt-toolbar'<'col-xs-12 col-sm-6'f><'col-sm-6 col-xs-12 hidden'l>>" +
                            "tr" +
                            "<'dt-toolbar-footer'<'col-sm-6 col-xs-12 hidden-xs'i><'col-xs-12 col-sm-6'p>>",
                            oLanguage: window.gjhDataTable_CN_Lang,
                            bFilter: false,//取消过滤功能
                            autoWidth: table.autoWidth,
                            "preDrawCallback": function () {
                                // Initialize the responsive datatables helper once.
                                if (!responsiveHelper_dt_basic) {
                                    responsiveHelper_dt_basic = new ResponsiveDatatablesHelper($(table.dom), breakpointDefinition);
                                }
                            },
                            "rowCallback": function (nRow) {
                                responsiveHelper_dt_basic.createExpandIcon(nRow);
                            },
                            "drawCallback": function (oSettings) {
                                responsiveHelper_dt_basic.respond();
                                if(table.drawCallback && (typeof table.drawCallback=='function')){
                                    table.drawCallback(oSettings);
                                }
                            },
                            "bDestory": true,
                            destroy: true,
                            "bProcessing": true, // 是否显示取数据时的那个等待提示
                            bSort: 0,
                            "createdRow": table.crateRow,
                            "bServerSide": true,//这个用来指明是通过服务端来取数据
                            "sAjaxSource": table.ajaxUrl,//这 个是请求的地址
                            "fnServerData": function (queryUrl, pageData, cb) {// 获取数据的处理函数
                                var obj = table.ajxaParam();
                                if(obj){
                                    obj.pageIndex = window.getPageIndex(pageData) + 1;
                                    obj.pageSize = window.getPageSize(pageData);
                                }
                                $.ajax({
                                    type: table.httpMethod,
                                    url: queryUrl,
                                    dataType: 'json',
                                    data: obj,
                                    success: function (result) {
                                        if (result) {
                                            if (result.count >= 0) {
                                                if( result.data){
                                                    result.data=result.data;
                                                    //统计
                                                    if(result.data.adminTradeListVOList){
                                                        $('#adminTradeListVOList_counts').text(result.data.count)
                                                        $('#adminTradeListVOList_platformFee').text(result.data.platformFee)
                                                        $('#adminTradeListVOList_vendorFee').text(result.data.vendorFee)
                                                        $('#adminTradeListVOList_refundFee').text(result.data.refundFee)
                                                        $('#adminTradeListVOList_totalFee').text(result.data.totalFee)
                                                        $('#adminTradeListVOList_statementFee').text(result.data.statementFee)
                                                        result.count=result.data.count
                                                        result.data=result.data.adminTradeListVOList
                                                    }
                                                }else {
                                                    result.data=[];
                                                    result.count=0
                                                }
                                                for (var i = 0; i < table.aoColumns; i++) {
                                                    result.data.table.aoColumns[i] === undefined ? '-' +
                                                        '' : result.data.table.aoColumns[i]
                                                }
                                                cb({
                                                    "draw": (searchCount++),
                                                    "recordsTotal": result.count,
                                                    "recordsFiltered": result.count,
                                                    "data": result.data
                                                });
                                            }else if(result.status==203){
                                                  for (var i = 0; i < table.aoColumns; i++) {
                                                      result.data.table.aoColumns[i] === undefined ? '-' +
                                                          '' : result.data.table.aoColumns[i]
                                                  }
                                                  cb({
                                                      "draw": (searchCount++),
                                                      "recordsTotal": result.data.count,
                                                      "recordsFiltered": result.data.count,
                                                      "data": result.data.arr
                                                  });
                                            }
                                            /*       if (result.TotalNum >= 0) {
                                                       cb({
                                                           "draw": (searchCount++),
                                                           "recordsTotal": result.TotalNum,
                                                           "recordsFiltered": result.TotalNum,
                                                           "data": result.data
                                                       });
                                                   }*/
                                        }
                                        if (typeof (table.loadedFunction) == 'function') {
                                            table.loadedFunction();
                                        }
                                    }
                                });
                            },
                            "aoColumns": initComs,
                            "aoColumnDefs": table.diyColumn,
                            "fnFooterCallback": table.fnFooterCallback,
                        });
                    })
                });
            });
        });
    });
}

/*
*
*      var logo = [
                {
                   name: "logo", //图片的名称（需保持唯一性）
                   src: "http://192.168.0.111:8011/Image/WebSite/2015/12/02_031540000_1.png" //图片的地址
                }
            ];
        //上传的结果将保存到传入的对象中，--->传入logo，当修改上传的序列时，也会修改logo对象,将新的值赋值到logo中


        //调用 并初始化  参数1:dom对象  参数二:要初始化的图片数组  参数三：允许上传的最大数量
*       window.initUpload($('#imgUpload_logo'), logo, 1);
*
*/
window.initUpload = function (dom, images, maxFiles) {
    //初始化图片上传
    loadScript("Scripts/plugin/dropzone/dropzone.min.js", function () {
        Dropzone.autoDiscover = false;
        setTimeout(function () {
            $(dom).dropzone({
                url: "/Common/WcfUploadImg?type=Factory",
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
                    images.push({name: file.name, src: response});
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
                        $.each(imgarry, function (i, item) {
                            var mockFile = {name: item.name, size: 123, accepted: true};
                            th.emit("addedfile", mockFile);
                            th.emit("thumbnail", mockFile, item.src);
                            file.name = item.name;
                            th.files.push(file);
                            th.element.classList.add("dz-started");
                        })
                    }

                    initImage();

                    //移除图片
                    th.on("removedfile", function (file) {
                        if (file.accepted) {
                            var fi = [];
                            $.each(th.files, function (i, obj) {
                                if (obj.name != file.name) {
                                    fi.push(obj);
                                }
                            });
                            th.files = fi;


                            var ima = [];
                            $.each(images, function (i, obj) {
                                ima.push(obj);
                            });
                            images.splice(0, images.length);
                            $.each(ima, function (i, obj) {
                                if (obj.name != file.name) {
                                    images.push(obj);
                                }
                            });

                        }
                    });
                }
            });
        }, 100)
    })
}

window.disableBackspace = function () {
    document.getElementsByTagName("body")[0].onkeydown = function (event) {
        if (event.keyCode == 8) {//判断按键为backSpace键
            //获取事件对象
            var elem = event.relatedTarget || event.srcElement || event.target || event.currentTarget;

            //判断是否需要阻止按下键盘的事件默认传递
            var name = elem.nodeName;
            if (name != 'INPUT' && name != 'TEXTAREA') {
                if (elem.attributes.hasOwnProperty("contenteditable") && elem.attributes.getNamedItem("contenteditable").value == "true") {
                    return true;
                }
                return _stopIt(event);
            }
            var type_e = elem.type.toUpperCase();
            if (name == 'INPUT' && (type_e != 'TEXT' && type_e != 'TEXTAREA' && type_e != 'PASSWORD' && type_e != 'FILE')) {
                return _stopIt(event);
            }
            if (name == 'INPUT' && (elem.readOnly == true || elem.disabled == true)) {
                return _stopIt(event);
            }
        }
    }

    function _stopIt(e) {
        if (e.returnValue) {
            e.returnValue = false;
        }
        if (e.preventDefault) {
            e.preventDefault();
        }

        return false;
    }
}
//图片上传地址
window.ossImageUrl = 'https://toss.fjjh.shop/';
//图片上传事件
window .upLoadOssImage=function (e,fileName,fn){
    var maxsize = 2 * 1024 * 1024;//2M
    upLoadImage = event.target.files[0];
    var data=new Date().getTime()
    if (!upLoadImage) return false;
    if (!upLoadImage.type.indexOf('image') == 0 && img.type && /\.(?:jpg|png|gif)$/.test(img.name)) return false;
    if (upLoadImage.size > maxsize) {
        simpleNotify('图片上传最大不能超过2M');
        return false;
    }else{
        var client = getAliyunClient();
        var name=upLoadImage.name.substr(upLoadImage.name.indexOf("."))

        var storeAs = fileName+'/'+data+name;
        client.multipartUpload(storeAs,upLoadImage)
        .then((result)=>{
            fn(result)
        })
    }
    return false;
}

//图片上传至阿里云
function getAliyunClient(){
    var client = new OSS.Wrapper({
        accessKeyId:'rgBGhs03VupDUUGB',
        accessKeySecret:'UFZDDSBq6qCsNmZv7HXUgGyLEqkhfn',
        endpoint:'oss-cn-zhangjiakou.aliyuncs.com',
        bucket:'test-blueteam-oss'
    })
    return client;
}
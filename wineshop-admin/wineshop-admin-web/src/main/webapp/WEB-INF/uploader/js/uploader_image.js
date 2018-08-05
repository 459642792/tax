var uploader_image = function (id, url) {
    var uploader = WebUploader.create({
        auto: true, //是否自动上传
        pick: {
            id: '#' + id,
            label: '点击选择图片',
            multiple: true            //默认为true，就是可以多选
        },
        swf: BASE_URL + '/js/Uploader.swf',
        server: url,
        duplicate: true,//是否可重复选择同一文件
        resize: false,
        formData: {
            "status": "file",
            "contentsDto.contentsId": "0000004730",
            "uploadNum": "0000004730",
            "existFlg": 'false'
        },
        compress: null,//图片不压缩
        chunked: true,  //分片处理
        chunkSize: 5 * 1024 * 1024, //每片5M  
        chunkRetry: false,//如果失败，则不重试
        threads: 1,//上传并发数。允许同时最大上传进程数。
        // runtimeOrder: 'flash',  
        // 禁掉全局的拖拽功能。这样不会出现图片拖进页面的时候，把图片打开。  
        disableGlobalDnd: true
    });
    return uploader;
}
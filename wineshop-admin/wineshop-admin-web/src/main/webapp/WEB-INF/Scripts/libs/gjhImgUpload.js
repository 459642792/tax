/*
赶酒会图片上传插件
*/
window.imgUpload = function (event, isCompress, imgUploadUrl, callBack) {
    compress(event, function (base64Img) {
        $.ajax({
            'url': imgUploadUrl,
            'type': 'post',
            'data': {
                img: base64Img,
                folfer: "jmp"
            },
            'success': function (ret) {
                callBack(ret);
            }
        });
    });

    function compress(event, callback) {
        var file = event.currentTarget.files[0];
        var fileSize = file.size;
        var rate;
        if (fileSize <= 300 * 1024) {
            rate = 1
        } else if (fileSize >= 300 * 1024 && fileSize < 600 * 1024) {
            rate = 0.8
        } else if (fileSize >= 600 * 1024 && fileSize < 900 * 1024) {
            rate = 0.6
        } else if (fileSize >= 900 * 1024 && fileSize < 1024 * 1024) {
            rate = 0.4
        } else if (fileSize >= 1024 * 1024) {
            rate = 0.3
        }
        var Orien;
        //调用exif获取图片的基本信息
        EXIF.getData(file, function () {
            var a = EXIF.getAllTags(this);
            Orien = EXIF.getTag(this, 'Orientation');
        })

        var reader = new FileReader();
        reader.onload = function (e) {
            var image = $('<img/>');
            image.on('load', function () {
                var canvas = document.createElement('canvas');
                canvas.width = this.width;
                canvas.height = this.height;
                var context = canvas.getContext('2d');
                context.clearRect(0, 0, canvas.width, canvas.width);
                var imageWidth = this.width;
                var imageHeight = this.height;
                var offsetX = 0;
                var offsetY = 0;
                context.drawImage(this, offsetX, offsetY, imageWidth, imageHeight);
                var data;
                //if (navigator.userAgent.match(/iphone/i)) {
                if (Orien != "" && Orien != 1) {
                    //alert('旋转处理');
                    switch (Orien) {
                        case 6://需要顺时针（向左）90度旋转  
                            rotateImg(this, 'left', canvas);
                            break;
                        case 8://需要逆时针（向右）90度旋转  
                            rotateImg(this, 'right', canvas);
                            break;
                        case 3://需要180度旋转  
                            rotateImg(this, 'turn', canvas);
                            break;
                    }
                }
                // console.log(image)
                data = canvas.toDataURL('image/jpeg', isCompress ? 0.2 : rate);
                //} else{
                //    data = canvas.toDataURL('image/jpeg', isCompress ? 0.2 : 1);
                //}

                callback(data);
            });
            image.attr('src', e.target.result);
        };
        reader.readAsDataURL(file);
    }

    function rotateImg(img, direction, canvas) {
        var min_step = 0;
        var max_step = 3;

        if (img == null) return;

        var height = img.height;
        var width = img.width;
        var step = 2;
        if (step == null) {
            step = min_step;
        }
        if (direction == 'right') {
            step++;

            step > max_step && (step = min_step);
        } else if (direction == 'left') {
            step--;
            step < min_step && (step = max_step);
        }

        //旋转角度以弧度值为参数    
        var degree = step * 90 * Math.PI / 180;
        var ctx = canvas.getContext('2d');
        switch (step) {
            case 0:
                canvas.width = width;
                canvas.height = height;
                ctx.drawImage(img, 0, 0);
                break;
            case 1:
                canvas.width = height;
                canvas.height = width;
                ctx.rotate(degree);
                ctx.drawImage(img, 0, -height);
                break;
            case 2:
                canvas.width = width;
                canvas.height = height;
                ctx.rotate(degree);
                ctx.drawImage(img, -width, -height);
                break;
            case 3:
                canvas.width = height;
                canvas.height = width;
                ctx.rotate(degree);
                ctx.drawImage(img, -width, 0);
                break;
        }
    }
}
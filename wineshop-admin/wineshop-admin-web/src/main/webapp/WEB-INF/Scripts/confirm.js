(function ($) {
    $.confirm = function (options) {
        var defaults = {
            title: "提示",
            msg: "请您确认！",
            confirmButtonClass: "btn-minor",
            cancelButtonClass: "btn-gray",
            confirmButton: "确认",
            cancelButton: "取消",
            confirm: $.noop,
            cancel: $.noop,
        }
        var options = $.extend(defaults, options);

        var confirmHeader = '';
        if (options.title !== '') {
            confirmHeader =
                '<div class=confirm-header>' +
                '<button type="button" class="confirm-close"></button>' +
                '<h4 class="confirm-title">' + options.title + '</h4>' +
                '</div>';
        }
        var confirmHTML =
            '<div class="confirm-box">' +
            '<div class="confirm-main">' +
            '<div class="confirmation">' +
            '<div class="confirm-dialog">' +
            '<div class="confirm-content">' +
            confirmHeader +
            '<div class="confirm-body">' + options.msg + '</div>' +
            '<div class="confirm-footer">' +
            '<button class="confirm btn ' + options.confirmButtonClass + '" type="button">' +
            options.confirmButton +
            '</button>' +
            '<button class="cancel btn ' + options.cancelButtonClass + '" type="button">' +
            options.cancelButton +
            '</button>' +
            '</div>' +
            '</div>' +
            '</div>' +
            '</div>' +
            '</div>' +
            '</div>';

        var modal = $(confirmHTML);
        modal.find(".confirm").click(function () {
            options.confirm();
            modal.remove();
            $(".backdrop").remove();
        });
        modal.find(".cancel").click(function () {
            options.cancel();
            modal.remove();
            $(".backdrop").remove();
        });
        modal.find(".confirm-close").click(function () {
            modal.remove();
            $(".backdrop").remove();
        });
        /*            $(document).on('click','.backdrop',function(){
                    modal.remove();
                    $(this).remove();               ;
                })*/

        $("body").append(modal);
        $("body").append('<div class="backdrop"></div>');

    };
    $.prompt = function (options) {
        var defaults = {
            title: "提示",
            msg: "请您确认！",
            confirmButtonClass: "btn-minor",
            cancelButtonClass: "btn-gray",
            confirmButton: "确认",
            cancelButton: "取消",
            confirm: $.noop,
            cancel: $.noop,
        }
        var options = $.extend(defaults, options);

        var confirmHeader = '';
        if (options.title !== '') {
            confirmHeader =
                '<div class=confirm-header>' +
                '<button type="button" class="confirm-close"></button>' +
                '<h4 class="confirm-title">' + options.title + '</h4>' +
                '</div>';
        }
        var confirmHTML =
            '<div class="confirm-box">' +
            '<div class="confirm-main">' +
            '<div class="confirmation">' +
            '<div class="confirm-dialog">' +
            '<div class="confirm-content">' +
            confirmHeader +
            '<div class="confirm-body" id="'+options.elementID+'"><span class="labelTile">'+options.spanMsg+'</span><input type="text" class="confirmInputText"  placeholder="'+options.msg+'"/> </div>' +
            '<div class="confirm-footer">'+
            '<button class="confirm btn ' + options.confirmButtonClass + '" type="button">' +
            options.confirmButton +
            '</button>' +
            '<button class="cancel btn ' + options.cancelButtonClass + '" type="button">' +
            options.cancelButton +
            '</button>' +
            '</div>' +
            '</div>' +
            '</div>' +
            '</div>' +
            '</div>' +
            '</div>';

        var modal = $(confirmHTML);
        modal.find(".confirm").click(function () {
            options.confirm();
           // modal.remove();
            //$(".backdrop").remove();
        });
        modal.find(".cancel").click(function () {
            options.cancel();
            modal.remove();
            $(".backdrop").remove();
        });
        modal.find(".confirm-close").click(function () {
            modal.remove();
            $(".backdrop").remove();
        });
        /*            $(document).on('click','.backdrop',function(){
                    modal.remove();
                    $(this).remove();               ;
                })*/

        $("body").append(modal);
        $("body").append('<div class="backdrop"></div>');

    };
}(jQuery))












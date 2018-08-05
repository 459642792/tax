/*! SmartAdmin - v1.4.1 - 2014-06-26 */
$(document).ready(function () {
    $(".tree > ul").attr("role", "tree").find("ul").attr("role", "group"),
        $(".tree").find("li:has(ul)").addClass("parent_li").attr("role", "treeitem").find(" > span").attr("title", "点击收起")
    $(".tree").on("click", "li > span", function (a) {
        var b = $(this).parent("li.parent_li").find(" > ul > li");
        if (b.is(":visible")) {
            b.hide("fast");
            $(this).attr("title", "点击展开").find(" > i").addClass("icon-plus-sign").removeClass("icon-minus-sign")
        }
        else {
            b.show("fast");
            $(this).attr("title", "点击收起").find(" > i").addClass("icon-minus-sign").removeClass("icon-plus-sign")
        }
        a.stopPropagation();
    })
});
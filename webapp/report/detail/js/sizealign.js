/**
 * Created with JetBrains WebStorm.
 * User: YHCRetinaPro
 * Date: 13. 3. 14.
 * Time: 오후 4:03
 * To change this template use File | Settings | File Templates.
 */

var min_content_height = 568;
var min_content_width = 550;
var side_margin;
var bottom_margin;
var navbar_height;
var sidebar_width;
var min_content_height;
var title_margin;
$(function () {
    navbar_height = $(".navbar").height();
    side_margin = parseInt($("#sidebar").css("margin")) * 5;
    bottom_margin = parseInt($("#sidebar").css("margin")) * 2;
    sidebar_width = $("#sidebar").width();
    min_content_height = min_content_height - navbar_height;
    title_margin = $("#sidebar_title").height();
    resize();
});
$(window).resize(resize);
function resize(){
    var cur_content_height = $(window).height()-navbar_height - bottom_margin - title_margin;
    var calHeight = min_content_height > cur_content_height ? min_content_height : cur_content_height;
    $("#sidebar").height(calHeight);

    var cur_content_width = $(window).width()-sidebar_width - side_margin;
    var calWidth = min_content_width > cur_content_width ? min_content_width : cur_content_width;
    $("#content").height(calHeight-10).width(calWidth);

    //$("#body").width(calWidth + side_margin + sidebar_width).height($(window).height()-navbar_height);
    $("#body").width($(".navbar").width());
}
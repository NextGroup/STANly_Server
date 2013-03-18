/**
 * Created with JetBrains WebStorm.
 * User: YHCRetinaPro
 * Date: 13. 3. 14.
 * Time: 오후 4:21
 * To change this template use File | Settings | File Templates.
 */
var data;
var options;
google.load("visualization", "1", {packages:["corechart"]});
google.setOnLoadCallback(drawChart);
function drawChart() {
    $.ajax( {
        type :'GET'
        ,asyn :true
        ,url :'/report/detail/test_detail.json'
        ,dataType :"json"
        //,contentType :"application/x-www-form-urlencoded;charset=UTF-8"
        ,beforeSend : function(xhr){
        }
        ,success : function(jsonData) {
            table = JSON.stringify(jsonData);
            data = google.visualization.arrayToDataTable([
                ['ID', 'Abstractness', 'Instability', 'Distance','LOC'],
                ['',   1.0,  0.0,      120, 1],
                ['',   0.9,  0.7,      130, 2],
                ['',   0.5,  0.3,      50,  1.5],
                ['',   0.1,  0.2,      230, 2.5],
                ['',   0.3,  0.6,      210, 0.5],
                ['',   0.5,  0.5,      100, 1],
                ['',   0.4,  0.1,      80,  2]
            ]);


            options = {
                title: 'Robert C. Martin Distance',
                hAxis: {title: 'Abstractness', minValue:0, maxValue:1.0, gridlines:{count:11}},
                vAxis: {title: 'Instability', minValue:0, maxValue:1.0, gridlines:{count:11}},
                bubble: {textStyle: {fontSize: 11}},
                colorAxis: {colors: ['yellow', 'red']}

            };

            drawDistance();
        }
        ,error : function(xhr, textStatus) {
        }
        ,complete : function(xhr, textStatus) {
        }
    });
    $(window).resize(drawDistance);

        function drawDistance(){
        var chart = new google.visualization.BubbleChart(document.getElementById('distance'));
        $("#distance").empty();
        chart.draw(data, options);

        var rect = $($("#distance").find('svg').find('g rect')[2]);

        var jg = new jsGraphics(document.getElementById("distance"));

        var sx = Number(rect.attr('x'));
        var sy = Number(rect.attr('y'));
        var ex = Number(rect.attr('x')) + Number(rect.attr('width'));
        var ey = Number(rect.attr('y')) + Number(rect.attr('height'));

        jg.setColor("#CCCCCC");
        jg.drawLine(sx,sy,ex,ey);
        jg.paint();
    }
}
/**
 * Created with JetBrains WebStorm.
 * User: YHCRetinaPro
 * Date: 13. 3. 14.
 * Time: 오후 4:21
 * To change this template use File | Settings | File Templates.
 */
var arr = [
    [0.5, 0.9, 1236, "com.sourceforge.pmd"],
    [0.1, 0.3, 1067, "com.sourceforge.pmd.stanly"],
    [0.9, 0.4, 1176, "AM General"],
    [0.32, 0.56, 610, "Aston Martin Lagonda"],
    [0.75, 0.12, 539, "Audi"],
    [0.33, 0.51, 864, "BMW"],
    [0.56, 0.56, 1026, "Bugatti"]];

var option = {
    title: 'Martin.C Distance',
    seriesDefaults:{
        renderer: $.jqplot.BubbleRenderer,
        rendererOptions: {
            bubbleAlpha: 0.6,
            highlightAlpha: 0.8,
            showLabels: false
        },
        shadow: true,
        shadowAlpha: 0.05
    },
    cursor: {
        show: true,            //커서 기능 사용 유무
        zoom: true,           //줌 기능 사용 유무
        showTooltip: true   //화면 하단에 tooltip 기능 사용 유무
    },
    axesDefaults:{
        numberTicks:13,
        min: -0.1,
        max: 1.1,
        ticks:[-0.1, 0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0, 1.1]
    },
    axes:{
        xaxis: {
            label:"Abstractness"
        },
        yaxis: {
            label:"Instability",
            labelOptions:{
                angle:-90
            },
            labelRenderer: $.jqplot.CanvasAxisLabelRenderer
        }
    }
};
$(window).resize(function(){
    $('#distance').empty();
    $('#tooltip').empty();
    DrowDistanceChart();
});
$(document).ready(function(){
    $.ajax( {
        type :'GET'
        ,asyn :true
        ,url :'/Stanly/component/MartinDistance'
        ,dataType :"json"
        ,data:{Name:getProjectName(),nodeID:1}
        //,contentType :"application/x-www-form-urlencoded;charset=UTF-8"
        ,beforeSend : function(xhr){
        }
        ,success : function(jsonData) {
            arr = jsonData;
            arr= new Array();
            for(var i=0;i<jsonData.metricDistance.length;i++)
                arr.push([jsonData.metricDistance[i].abstractness,jsonData.metricDistance[i].instability,jsonData.metricDistance[i].size,jsonData.metricDistance[i].name]);
            DrowDistanceChart();
        }
        ,error : function(xhr, textStatus) {
        }
        ,complete : function(xhr, textStatus) {
        }
    });

});
function DrowDistanceChart(){
    var plot1b = $.jqplot('distance',[arr],option);

    // Legend is a simple table in the html.
    // Dynamically populate it with the labels from each data value.
    //$.each(arr, function(index, val) {
    //    $('#legend1b').append('<tr><td>'+val[3]+'</td><td>'+val[2]+'</td></tr>');
    //});

    // Now bind function to the highlight event to show the tooltip
    // and highlight the row in the legend.
    $('#distance').bind('jqplotDataHighlight',
        function (ev, seriesIndex, pointIndex, data, radius) {
            var chart_left = $('#distance').offset().left,
                chart_top = $('#distance').offset().top,
                x = plot1b.axes.xaxis.u2p(data[0]),  // convert x axis unita to pixels
                y = plot1b.axes.yaxis.u2p(data[1]);  // convert y axis units to pixels
            var color = 'rgb(50%,50%,100%)';
            $('#tooltip').css({left:chart_left+x+radius+5, top:chart_top+y});
            $('#tooltip').html('<span style="font-size:14px;font-weight:bold;color:' +
                color + ';">' + data[3] + '</span><br />' + 'Abstractness: ' + data[0] +
                '<br />' + 'Instability: ' + data[1] + '<br />' + 'Line of Code: ' + data[2]);
            $('#tooltip').show();
            //$('#legend1b tr').css('background-color', '#ffffff');
            //$('#legend1b tr').eq(pointIndex+1).css('background-color', color);
        });

    // Bind a function to the unhighlight event to clean up after highlighting.
    $('#distance').bind('jqplotDataUnhighlight',
        function (ev, seriesIndex, pointIndex, data) {
            $('#tooltip').empty();
            $('#tooltip').hide();
            //$('#legend1b tr').css('background-color', '#ffffff');
        });

    var canvas = $('#distance>.jqplot-series-shadowCanvas');
    var canvas_height = canvas.height();
    var canvas_width = canvas.width();
    canvas = canvas[0].getContext('2d');
    canvas.moveTo(0,0);
    canvas.lineTo(canvas_width,canvas_height);
    canvas.strokeStyle = '#cccccc';
    canvas.stroke();
}
/*
var data;
var options;
google.load("visualization", "1", {packages:["corechart"]});
google.setOnLoadCallback(drawChart);
function drawChart() {
    $.ajax( {
        type :'GET'
        ,asyn :true
        ,url :'/Stanly/report/codesizesample.json'
        ,dataType :"json"
        //,contentType :"application/x-www-form-urlencoded;charset=UTF-8"
        ,beforeSend : function(xhr){
        }
        ,success : function(jsonData) {
            table = jsonData;
            data = google.visualization.arrayToDataTable([
                ['ID', 'Abstractness', 'Instability', 'Distance','LOC'],
                ['com.sourceforge.pmd',   1.0,  0.0,      120, 1],
                ['com.sourceforge.pmd.name',   0.9,  0.7,      130, 2],
                ['com.sourceforge.pmd.name.com.sourceforge.pmd.name',   0.5,  0.3,      50,  1.5],
                ['ASDF',   0.1,  0.2,      230, 2.5],
                ['SGS',   0.3,  0.6,      210, 0.5],
                ['QTW',   0.5,  0.5,      100, 1],
                ['OIJ',   0.4,  0.1,      80,  2]
            ]);


            options = {
                title: 'Robert C. Martin Distance',
                hAxis: {title: 'Abstractness', minValue:0, maxValue:1.0, gridlines:{count:11}},
                vAxis: {title: 'Instability', minValue:0, maxValue:1.0, gridlines:{count:11}},
                bubble: {textStyle: {fontSize: 30}},
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
        var resizable = $($("#distance").find(".ui-resizable-handle"));
        $("#distance").empty();
        $("#distance").width($('.subtitle').width()-1);
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


        if($( "#distance" ).resizable)
        {
            $("#distance").append(resizable);
            var option={
                minWidth: $('.subtitle').width()-1
                ,maxWidth: $('.subtitle').width()-1
                ,minHeight: 300
                //,resize: function(event, ui){}
            }
            $("#distance").resizable(option);
        }
    }
}
*/
/**
 * Created with JetBrains WebStorm.
 * User: YHCRetinaPro
 * Date: 13. 3. 14.
 * Time: 오후 4:21
 * To change this template use File | Settings | File Templates.
 */
var plot1b;
var arr;
var flag_data;
var option = {
    title: 'Balancing Abstractness Chart',
    seriesColors: $.jqplot.config.defaultColors,
    seriesDefaults:{
        renderer: $.jqplot.BubbleRenderer,
        rendererOptions: {
            bubbleAlpha: 0.6,
            highlightAlpha: 0.8,
            showLabels: false,
            varyBubbleColors: true
        },
        shadow: true,
        shadowAlpha: 0.05
    },
    cursor: {
        show: true,            //커서 기능 사용 유무
        //zoom: true,           //줌 기능 사용 유무
        //looseZoom: true,
        showTooltip: true   //화면 하단에 tooltip 기능 사용 유무
    },
    axesDefaults:{
        //numberTicks:13,
        //min: -0.1,
        //max: 1.1
        //autoscale:true,
        ticks:[-0.1, 0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0, 1.1],
        tickRenderer: $.jqplot.CanvasAxisTickRenderer
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

function BuildDistanceChar(pname)
{
    id = 1;
    $('#distance').empty();
    $('#tooltip').empty();
    $.ajax( {
        type :'GET'
        ,asyn :true
        ,url :'/Stanly/component/MartinDistance'
        ,dataType :"json"
        ,data:{Name:getParameter('Project'),nodeID:id}
        //,contentType :"application/x-www-form-urlencoded;charset=UTF-8"
        ,beforeSend : function(xhr){
        }
        ,success : function(jsonData) {
            arr= new Array();
            option.seriesColors = new Array();
            for(var i=0;i<jsonData.metricDistance.length;i++)
            {
                var element = jsonData.metricDistance[i];
                arr.push([element.abstractness,element.instability,element.size,element.name,element.Distance]);
                if(element.name == pname)
                {
                    flag_data = [element.abstractness,element.instability,element.size,element.name,element.Distance];
                }
                //option.seriesColors.push('#' + Math.round(16*Math.abs(element.Distance)).toString(16) + 'D0');
                if(element.Distance < 0.5 && element.Distance>-0.5)
                    option.seriesColors.push('#00DD00');
                else
                    option.seriesColors.push('#DDDD00');
            }
            DrowDistanceChart();
            if(flag_data)
                show_tooltip(flag_data,5);
        }
        ,error : function(xhr, textStatus) {
        }
        ,complete : function(xhr, textStatus) {
        }
    });
}

function set_flag(name)
{
    for(var i=0;i<arr.length;i++)
    {
        if(arr[i][3] == name)
        {
            flag_data = arr[i];
            show_tooltip(flag_data,5);
            break;
        }
    }
}

function DrowDistanceChart(){
    plot1b = $.jqplot('distance',[arr],option);

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
                '<br />' + 'Instability: ' + data[1] + '<br />' + 'Distance: ' + data[4] + '<br />' + 'Line of Code: ' + data[2]);
            $('#tooltip').show();
            //$('#legend1b tr').css('background-color', '#ffffff');
            //$('#legend1b tr').eq(pointIndex+1).css('background-color', color);
        });

    // Bind a function to the unhighlight event to clean up after highlighting.
    $('#distance').bind('jqplotDataUnhighlight',
        function (ev, seriesIndex, pointIndex, data) {
            $('#tooltip').empty();
            $('#tooltip').hide();
            show_tooltip(flag_data,5);
            //$('#legend1b tr').css('background-color', '#ffffff');
        });
    $('.button-reset').click(function() {
        plot1b.resetZoom()
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

function show_tooltip(data, radius) {
    var chart_left = $('#distance').offset().left,
        chart_top = $('#distance').offset().top,
        x = plot1b.axes.xaxis.u2p(data[0]),  // convert x axis unita to pixels
        y = plot1b.axes.yaxis.u2p(data[1]);  // convert y axis units to pixels
    var color = 'rgb(50%,50%,100%)';
    $('#tooltip').css({left:chart_left+x+radius+5, top:chart_top+y});
    $('#tooltip').html('<span style="font-size:14px;font-weight:bold;color:' +
        color + ';">' + data[3] + '</span><br />' + 'Abstractness: ' + data[0] +
        '<br />' + 'Instability: ' + data[1] + '<br />' + 'Distance: ' + data[4] + '<br />' + 'Line of Code: ' + data[2]);
    $('#tooltip').show();
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
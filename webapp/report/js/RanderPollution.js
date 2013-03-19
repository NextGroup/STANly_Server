var colors = ["#4572A7", "#AA4643", "#89A54E", "#80699B", "#3D96AE", "#DB843D", "#92A8CD", "#A47D7C", "#B5CA92", "#4572A7", "#AA4643", "#89A54E", "#80699B", "#3D96AE", "#DB843D", "#92A8CD", "#A47D7C", "#B5CA92"];
var chart;
var categories;
var name;
var data;
var subName;
function BuildMainView(id,name){
    subName = name;
    $.ajax( {
        type :'GET'
        ,asyn :true
        ,url :'/Stanly/component/PollutionView'
        ,dataType :"json"
        ,data:{Name:getProjectName(),nodeID:id}
        //,contentType :"application/x-www-form-urlencoded;charset=UTF-8"
        ,beforeSend : function(xhr){
        }
        ,success : function(jsonData) {
            categories = new Array();;
            data = new Array();
            for(var i=0;i<jsonData.PollutionScore.length;i++)
            {
                if(Number(jsonData.PollutionScore[i]) != 0)
                {
                    categories.push(jsonData.PollutionName[i]);
                    data.push({y:Number(jsonData.PollutionScore[i]),color:colors[i]});
                }
            }
            //console.log(jsonData.PollutionScore);

            //BuildTable();
            //AdjustStyle();
            DrawPollutionChart();
        }
        ,error : function(xhr, textStatus) {
        }
        ,complete : function(xhr, textStatus) {
        }
    });
}

function DrawPollutionChart()
{
    $('pollution').empty();
    /*function setChart(name, categories, data, color) {
        chart.xAxis[0].setCategories(categories, false);
        chart.series[0].remove(false);
        chart.addSeries({
            name: name,
            data: data,
            color: color || 'white'
        }, false);
        chart.redraw();
    }*/

    chart = new Highcharts.Chart({
        chart: {
            renderTo: 'pollution',
            type: 'column'
        },
        title: {
            text: getProjectName()
        },
        subtitle: {
            text: subName
        },
        xAxis: {
            categories: categories
        },
        yAxis: {
            title: {
                text: 'Number of Pollutions'
            }
        },
        plotOptions: {
            column: {
                cursor: 'pointer',
                point: {
                    events: {
                        click: function() {
                            /*var drilldown = this.drilldown;
                             if (drilldown) { // drill down
                             setChart(drilldown.name, drilldown.categories, drilldown.data, drilldown.color);
                             } else { // restore
                             setChart(name, categories, data);
                             }*/
                        }
                    }
                },
                dataLabels: {
                    enabled: true,
                    color: colors[0],
                    style: {
                        fontWeight: 'bold'
                    },
                    formatter: function() {
                        return this.y;
                    }
                }
            }
        },
        tooltip: {
            formatter: function() {
                var point = this.point,
                    s = this.x +':<b>'+ this.y +'</b><br/>';
                /*if (point.drilldown) {
                 s += 'Click to view '+ point.category +' versions';
                 } else {
                 s += 'Click to return to browser brands';
                 }*/
                return s;
            }
        },
        series: [{
         name: name,
         data: data,
         color: 'white'
         }],
        exporting: {
            enabled: false
        }
    });
}
$(document).ready(function() {
    BuildMainView(1,getProjectName());
/*
    var name = 'Pollutions';
    var data = [{
            y: 55.11,
            color: colors[0]
        }, {
            y: 21.63,
            color: colors[1]
        }, {
            y: 11.94,
            color: colors[2]
        }, {
            y: 7.15,
            color: colors[3]
        }, {
            y: 2.14,
            color: colors[4]
        }, {
            y: 2.14,
            color: colors[5]
        }, {
            y: 2.14,
            color: colors[6]
        }, {
            y: 2.14,
            color: colors[7]
        }, {
            y: 2.14,
            color: colors[8]
        }, {
            y: 2.14,
            color: colors[0]
        }, {
            y: 2.14,
            color: colors[1]
        }];
*/
});
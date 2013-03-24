var colors = ["#59B859", "#2B8E2A", "#236123", "#0000FF", "#972D96", "#600060", "#A47D7C", "#FF0000", "#F7710D", "#92A8CD", "#FF00FF", "#89A54E", "#80699B", "#3D96AE", "#B5CA92", "#4572A7", "#AA4643", "#B5CA92"];
var chart;
var categories;
var name;
var data;
var subName;
function BuildPollutionMainView(id,name){
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
            categories = new Array();
            data = new Array();
            for(var i=0;i<jsonData.PollutionScore.length;i++)
            {
                //if(Number(jsonData.PollutionScore[i]) != 0)
                {
                    categories.push(jsonData.PollutionName[i]);
                    data.push({y:Number(jsonData.PollutionScore[i]),color:colors[i]});
                    //data.push({y:Number(10),color:colors[i]});
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
        legend: {
            enabled: false
        },
        tooltip: {
            formatter: function() {
                var point = this.point,
                    s = this.x +':<b>'+ this.y +'</b><br/>';
                return s;
            }
        },
        series: [{
            name: name,
            data: data,
            color: 'white'
        }],

        plotOptions: {
            column: {
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
        }
    });
}
$(document).ready(function() {
    BuildPollutionMainView(1,getProjectName());
});
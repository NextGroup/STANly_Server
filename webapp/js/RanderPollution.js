var colors = {
    "Number of Top Level Classes":"#59B859",
    "Number of Methods":"#2B8E2A",
    "Number of Fields":"#236123",
    "Estimated Lines of Code":"#0000FF",
    "Cyclomatic Complexity":"#972D96",
    "Fat":"#600060",
    "Component Dependency":"#A47D7C",
    "Tangled":"#FF0000",
    "Distance":"#F7710D",
    "Average Absolute Distance":"#92A8CD",
    "Weighted Methods per Class":"#FF00FF",
    "Depth of Inheritance Tree":"#89A54E",
    "Coupling between Objects":"#80699B",
    "Response for a Class":"#3D96AE"
};

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
        ,data:{Name:getParameter('Project'),nodeID:id}
        //,contentType :"application/x-www-form-urlencoded;charset=UTF-8"
        ,beforeSend : function(xhr){
        }
        ,success : function(jsonData) {
            categories = new Array();
            data = new Array();
            for(var i=0;i<jsonData.PollutionScore.length;i++)
            {
                if(Number(jsonData.PollutionScore[i]) != 0)
                {
                    categories.push(jsonData.PollutionName[i]);
                    data.push({y:Number(jsonData.PollutionScore[i]),color:colors[jsonData.PollutionName[i]]});
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
            text: getParameter('Project')
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
    BuildPollutionMainView(1,getParameter('Project'));
});
var chart;
$(document).ready(function() {

    var colors = Highcharts.getOptions().colors,
        categories = [
            'Number of Top Level Classes', 'Number of Methods', 'Number of Fields',
            'Estimated Lines of Code ', 'Cyclomatic Complexity', 'Fat',
            'Tangled', 'Component Dependency', 'Distance', 'Average Absolute Distance',
            'Weighted Methods per Class', 'Depth of Inheritance Tree'
        ],
        name = 'Pollutions',
        data = [{
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

    function setChart(name, categories, data, color) {
        chart.xAxis[0].setCategories(categories, false);
        chart.series[0].remove(false);
        chart.addSeries({
            name: name,
            data: data,
            color: color || 'white'
        }, false);
        chart.redraw();
    }

    chart = new Highcharts.Chart({
        chart: {
            renderTo: 'pollution',
            type: 'column'
        },
        title: {
            text: 'Name of Proejct'
        },
        subtitle: {
            text: 'Name of SubFolder'
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
});
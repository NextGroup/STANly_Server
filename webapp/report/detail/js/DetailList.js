/**
 * Created with JetBrains WebStorm.
 * User: YHCRetinaPro
 * Date: 13. 3. 19.
 * Time: 오전 11:33
 * To change this template use File | Settings | File Templates.
 */
function BuildTable() {
    var tableObject = function (json) {
        var headerCount = new Object();
        var createTHEAD = function (id) {
            var tr = document.createElement("thead");
            tr.ID = id;
            return tr;
        };
        var createTBODY = function (id) {
            var tr = document.createElement("tbody");
            tr.ID = id;
            return tr;
        };
        var createTR = function (id) {
            var tr = document.createElement("tr");
            tr.ID = id;
            return tr;
        };
        var createTH = function (html) {
            var th = document.createElement("th");
            th.innerHTML = html;
            return th;
        };
        var createTD = function (html) {
            var td = document.createElement("td");
            td.innerHTML = html;
            return td;
        };
        var getName = function (id) {
            for (var name in headerCount) {
                if (eval("headerCount." + name) == id) {
                    return name;
                }
            }
        };
        var pTable;
        if (json.length > 0) {
            var index = 0;
            var theadFlag = false;
            pTable = document.createElement("table");
            pTable.setAttribute("class","tablesorter tablesorter-blue");
            pTable.setAttribute("style","margin:0px;");
            var thead = createTHEAD();
            var head = createTR();
            for (var i = 0; i < json.length; i++)
                for (var item in json[i]) {
                    if (!headerCount.hasOwnProperty(item)) {
                        head.appendChild(createTH(item));
                        eval('headerCount.' + item + "=" + index);
                        index++;
                    }
                }
            thead.appendChild(head);
            pTable.appendChild(thead);

            var tbody = createTBODY();
            for (var i = 0; i < json.length; i++) {
                var row = new createTR(i);
                for (var j = 0; j < index; j++) {
                    var name = getName(j);
                    if (eval("json[" + i + "].hasOwnProperty('" + name + "')")) {
                        row.appendChild(createTD(eval('json[' + i + '].' + name)));
                    }
                    else
                        row.appendChild(createTD(''));
                }
                tbody.appendChild(row);
            }
            pTable.appendChild(tbody);
        }
        return pTable;

    };
    var list = document.getElementById("detail_list");
    var d = list.getElementsByClassName("tablesorter");
    while(d.length)
        list.removeChild(d[d.length-1]);
    var c = new tableObject(table);
    if(c.getAttribute)
        list.appendChild(c);
}
function AdjustStyle(){
    //$(function() {

    // Extend the themes to change any of the default class names ** NEW **
    $.extend($.tablesorter.themes.jui, {
        // change default jQuery uitheme icons - find the full list of icons here: http://jqueryui.com/themeroller/ (hover over them for their name)
        table      : 'ui-widget ui-widget-content ui-corner-all', // table classes
        header     : 'ui-widget-header ui-corner-all ui-state-default', // header classes
        footerRow  : '',
        footerCells: '',
        icons      : 'ui-icon', // icon class added to the <i> in the header
        sortNone   : 'ui-icon-carat-2-n-s',
        sortAsc    : 'ui-icon-carat-1-n',
        sortDesc   : 'ui-icon-carat-1-s',
        active     : 'ui-state-active', // applied when column is sorted
        hover      : 'ui-state-hover',  // hover class
        filterRow  : '',
        even       : 'ui-widget-content', // odd row zebra striping
        odd        : 'ui-state-default'   // even row zebra striping
    });

    // call the tablesorter plugin and apply the ui theme widget
    $("table").tablesorter({

        theme : 'blue', // theme "jui" and "bootstrap" override the uitheme widget option in v2.7+

        headerTemplate : '{content} {icon}', // needed to add icon for jui theme

        // widget code now contained in the jquery.tablesorter.widgets.js file
        widgets : ['uitheme', 'zebra' , 'resizable', 'stickyHeaders'],

        widgetOptions : {
            // zebra striping class names - the uitheme widget adds the class names defined in
            // $.tablesorter.themes to the zebra widget class names
            zebra   : ["even", "odd"],
            // set the uitheme widget to use the jQuery UI theme class names
            // ** this is now optional, and will be overridden if the theme name exists in $.tablesorter.themes **
            // uitheme : 'jui'
            // css class name applied to the sticky header
            resizable : false,
            // css class name applied to the sticky header
            stickyHeaders : "tablesorter-stickyHeader"
        }

    });
}
function getParameter(strParamName) {
    var arrResult = null;
    if (strParamName)
        arrResult = location.search.match(new RegExp("[&?]" + strParamName+"=(.*?)(&|$)"));
    return arrResult && arrResult[1] ? arrResult[1] : null;
}

function get_avarta(json){
    $('#userBox').append($('<div class="circle_wrapper"><img src="'+ json.avatar_url +'" alt="profile" /></div>'));
    $('#userBox').append($('<div class="userName">' + getParameter('Name') + '</div>'));
}
$.ajax({
    type: "get",
    url: "https://api.github.com/users/"+getParameter('Name'),
    success: get_avarta
});

var donut_size;
function build_pollution_donut(json)
{
    rankarr=['F: ','C: ','B: ','A: '];
    donut_data = jQuery.parseJSON(json).list;
    for(var i=0;i<4;i++)
        $($('#pollution-right>.list-item>.name')[i]).text(rankarr[i] + donut_data[i].y+' EA');
    nv.addGraph(function(){
        var chart = nv.models.pie()
            .values(function(d) { return d })
            .width(donut_size)
            .height(donut_size)
            .color([ "#EE0000", "#F77700", "#fdd04f", "#00AA00" ])
            .donut(true)
            .donutRatio(0.5);

        total = donut_data[0].y+donut_data[1].y+donut_data[2].y+donut_data[3].y;
        normal_data = donut_data;
        for(var i=0;i<4;i++)
        {
            if(normal_data[i].y == 0)   continue;
            normal_data[i].y = 360*normal_data[i].y/total;
            if(normal_data[i].y < 10) normal_data[i].y = 10;
        }

        d3.select("#project-pol")
            .datum([normal_data])
            .transition().duration(1200)
            .attr('width', donut_size)
            .attr('height', donut_size)
            .call(chart);

        return chart;
    });
}

var risk_size;
function build_critical_risk(json)
{
    risk = jQuery.parseJSON(json).list;

    for(var i=0;i<(risk.length<risk_size?risk.length:risk_size);i++)
    {
        if(risk[i].Type == 'PACKAGESET')    icon = 'tree';
        else if(risk[i].Type == 'PACKAGE')    icon = 'package';
        else if(risk[i].Type == 'CLASS')    icon = 'class';
        tbltr = $('<tr></tr>');
        $('.critical-risk>tbody').append(tbltr);
        tbltr.append($('<td class="rate-'+risk[i].Rank.toLowerCase()+'">'+risk[i].Rank+'</td>'));
        tbltr.append($('<td class="icon"><img src="../images/icon/'+icon+'.gif" style="margin-top:6px;" alt="'+icon+'" /></td>'));
        tbltr.append($('<td>'+risk[i].Name+'</td>'))
    }
}

function set_title(){
    if(getParameter('Project') != null)
    {
        $.ajax({
            type: "get",
            url: "/Stanly/common/pollution/static",
            data: {name:getParameter('Project'),mode:(getParameter('Version')=="Developer"?"0":"1")},
            success: function(json)
            {
                rate = jQuery.parseJSON(json).list;
                $('#content>#content-body>#content-position-change>#overview>#overview-content>.pollution').addClass(rate[2].rate.toLowerCase()+'-circle');
                $('#content>#content-body>#content-position-change>#overview>#overview-content>.pollution>.rotation-circle>.text').text(rate[2].rate.toUpperCase());
            }
        });
    }
    $(document).ready(function() {
        $('#content>#content-body>#content-position-change>#overview>#overview-content>.text').text(getParameter('Project'));
        $("#dashboardMenu>a").attr('href',"dashboard.html"+"?Name="+getParameter('Name')+"&Version="+getParameter('Name'));
        $("#projectMenu>a").attr('href',"project.html"+"?Project="+getParameter('Project')+"&Name="+getParameter('Name')+"&Version="+getParameter('Name'));
        $("#compositionMenu>a").attr('href',"composition.html"+"?Project="+getParameter('Project')+"&Name="+getParameter('Name')+"&Version="+getParameter('Name'));
        $("#pollutionMenu>a").attr('href',"pollution.html"+"?Project="+getParameter('Project')+"&Name="+getParameter('Name')+"&Version="+getParameter('Name'));

        if(getParameter('Version')=='Architect')
        {
            $("#advancedMenu>a").attr('href',"advanced.html"+"?Project="+getParameter('Project')+"&Name="+getParameter('Name')+"&Version="+getParameter('Name'));
            $("#developerMenu>a").attr('href',"developer.html"+"?Project="+getParameter('Project')+"&Name="+getParameter('Name')+"&Version="+getParameter('Name'));
        }
    });
}

function trim(s) {
    return s.replace(/ /gi, '-');
}


var popupmenu_info = [
    {title: "Fat", content: "Fat contains rules that find problems related to software size(Package, Class, Method, etc...)"},
    {title: "Coupling", content: "Coupling metrics measure the dependencies between a given entity and other entities the program consists of."},
    {title: "Naming", content: "Static analysis information about Nmaing rule. The Naming Ruleset contains rules regarding preferred usage of names and identifiers."},
    {title: "Basic Rule", content: " The Basic ruleset contains a collection of good practices which should be followed."},
    {title: "Change Propagation", content: "Change propagation practices explore how changes made to one version of the application are migrated to other living versions of the application."}
];

popup_hide = function() {
    $("#popup-info").stop(true, true);
    $("#popup-info").animate({
        opacity: 0.0,
    }, 250, function(){
        $(this).css("display", "none");
    });
};
popup_show = function(obj) {
    $("#popup-info").stop(true, true);
    $("#popup-info").css("display", "block");
    $("#popup-info").animate({ opacity: 0.8 }, 250, function() {} );
    $("#popup-info").css("top", obj.position().top + 45);
    $("#popup-info").css("left", obj.position().left - 100);
};

function set_tooltip_event()
{
    $("#project-list .list-item .fat").hover(function() {
        $("#popup-title").html(popupmenu_info[0].title);
        $("#popup-content").html(popupmenu_info[0].content);
        popup_show($(this));
    },popup_hide);

    $("#project-list .list-item .coupling").hover(function() {
        $("#popup-title").html(popupmenu_info[1].title);
        $("#popup-content").html(popupmenu_info[1].content);
        popup_show($(this));
    },popup_hide);

    $("#project-list .list-item .naming").hover(function() {
        $("#popup-title").html(popupmenu_info[2].title);
        $("#popup-content").html(popupmenu_info[2].content);
        popup_show($(this));
    },popup_hide);

    $("#project-list .list-item .basic").hover(function() {
        $("#popup-title").html(popupmenu_info[3].title);
        $("#popup-content").html(popupmenu_info[3].content);
        popup_show($(this));
    },popup_hide);

    $("#project-list .list-item .change-propagation").hover(function() {
        $("#popup-title").html(popupmenu_info[4].title);
        $("#popup-content").html(popupmenu_info[4].content);
        popup_show($(this));
    },popup_hide);
}
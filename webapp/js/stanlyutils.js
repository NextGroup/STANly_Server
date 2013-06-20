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
        tbltr.append($('<td class="icon"><a href="#"><img src="../images/icon/'+icon+'.gif" style="margin-top:6px;" alt="'+icon+'" /></a></td>'));
        tbltr.append($('<td><a href="#">'+risk[i].Name+'</a></td>'))
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
        $("#dashboardMenu>a").attr('href',"dashboard.html"+"?Name="+getParameter('Name')+"&Version="+getParameter('Version'));
        $("#projectMenu>a").attr('href',"project.html"+"?Project="+getParameter('Project')+"&Name="+getParameter('Name')+"&Version="+getParameter('Version'));
        $("#compositionMenu>a").attr('href',"composition.html"+"?Project="+getParameter('Project')+"&Name="+getParameter('Name')+"&Version="+getParameter('Version'));
        $("#pollutionMenu>a").attr('href',"pollution.html"+"?Project="+getParameter('Project')+"&Name="+getParameter('Name')+"&Version="+getParameter('Version'));

        if(getParameter('Version')=='Architect')
        {
            $("#advancedMenu>a").attr('onclick',"window.open('"+"advanced.html"+"?Project="+getParameter('Project')+"&Name="+getParameter('Name')+"&Version="+getParameter('Version')+"')");
            $("#developerMenu>a").attr('href',"developer.html"+"?Project="+getParameter('Project')+"&Name="+getParameter('Name')+"&Version="+getParameter('Version'));
        }
    });
}// onclick="window.open('archi-architect.html?project=stanly','architect-view','width=960,height=640')"

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
/*
var developer_img_list[]=
{
    "https://secure.gravatar.com/avatar/e5db3e98a858066748db3f817be7bc01?s=60&d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png",
    "https://secure.gravatar.com/avatar/fa5767c967d4733b5937d4e9c265ee4b?s=60&d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png",
    "https://secure.gravatar.com/avatar/956c7d246841e8507a1e1b96842994db?s=60&d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png",
    "https://secure.gravatar.com/avatar/8a1aabc40d859fcb786eb4d28b95d299?s=60&d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png",
    "https://secure.gravatar.com/avatar/b336afe083d837848d94891e59ff3e82?s=60&d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png",
    https://secure.gravatar.com/avatar/3d940f701f1bc6bf922f910258e19698?s=60&d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png
        https://secure.gravatar.com/avatar/5414357263ef617c7ab3eb067c22412d?s=60&d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png
https://secure.gravatar.com/avatar/01c4bb8048409cf0238a6518b96d8e05?s=60&d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png
    https://secure.gravatar.com/avatar/fe9bb29f576661916e29a8d327493d6b?s=60&d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png
        https://secure.gravatar.com/avatar/26073c8b2f7cbdfe3139a26bfd0c8d45?s=60&d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png
            https://secure.gravatar.com/avatar/c0de98ae17fa45d01407fcd0e77c6841?s=60&d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png
                https://secure.gravatar.com/avatar/ab6f8ad1b1b902313b2273a93efe4e70?s=60&d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png
                    https://secure.gravatar.com/avatar/a4d61b91eeb78239fbf0668518a2a125?s=60&d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png
                        https://secure.gravatar.com/avatar/d295c576018ff01dd5748b659b7564f1?s=60&d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png
                            https://secure.gravatar.com/avatar/85bc99399d2c807b788ea1279d1d3df7?s=60&d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png
                                https://secure.gravatar.com/avatar/1491b11fe484af03f7beff7f1c39bf8a?s=60&d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png
                                    https://secure.gravatar.com/avatar/6dbef86896a188f1af9e8ac33020f272?s=60&d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png
                                        https://secure.gravatar.com/avatar/c567eed39d1dbbdf8a304e52013aa621?s=60&d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png
                                            https://secure.gravatar.com/avatar/2f1e1064ae4295d285234c59f5dcb541?s=60&d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png
                                                https://secure.gravatar.com/avatar/48ad44fb319fc06a737a082820bff3ea?s=60&d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png
                                                    https://secure.gravatar.com/avatar/746344a6421f3e4b081511694085be38?s=60&d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png
                                                        https://secure.gravatar.com/avatar/f25edd44c8fe368894a97a90e6f63b82?s=60&d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png
                                                            https://secure.gravatar.com/avatar/e1fd80578839ff02a8dd25f62b244d57?s=60&d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png

}*/
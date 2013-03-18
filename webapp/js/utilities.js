/**
 * Created with JetBrains WebStorm.
 * User: YHCRetinaPro
 * Date: 13. 3. 18.
 * Time: 오후 10:21
 * To change this template use File | Settings | File Templates.
 */
function getProjectName(){
    var ProjectName;
    if(location.search){
        var str = location.search.split("?")[1];
        ProjectName = str.split("Name=")[1].split("&")[0];
    }
    //console.log(ProjectName);
    return ProjectName;
}

function requestURL(mothod,url,arg){
    // Form객체를 만들고 속성값들을 추가함
    var oForm = document.createElement('form');

    oForm.method = mothod;
    oForm.action = url;

    var equIndex = 0;

    var argArr = arg.split("&");
    for(var i=0 ; i<argArr.length; i++){
        leftStr = "";
        oInputHidden = document.createElement("input");
        oInputHidden.type		= "hidden";
        oInputHidden.name		= argArr[i].split("=")[0];
        equIndex = argArr[i].indexOf("=") + 1;
        oInputHidden.value	= argArr[i].substr(equIndex);
        oForm.appendChild(oInputHidden);
    }
    // Form안에 TextBox를 넣음
    oForm.appendChild(oInputHidden);
    // Body안에 Form을 넣음
    document.body.appendChild(oForm);
    oForm.submit();
}
//$(function(){
$('.nav > li > a').click(function(){
    if(this.parentNode.getAttribute("class"))   return false;
    var url="/Stanly/report/detail/";
    if(this.text.toLowerCase() == "dashboard")
        url="/Stanly/report/";
    requestURL('get',url + this.text.toLowerCase() + '.html','Name=' + getProjectName());
    return false;
});
//});
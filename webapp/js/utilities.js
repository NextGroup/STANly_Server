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
    console.log(ProjectName);
    return ProjectName;
}
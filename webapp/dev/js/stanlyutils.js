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
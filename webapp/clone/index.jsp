<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>STANly; JAVA Architecture Analyzer</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="STANly@Software Maestro">
    <meta name="author" content="Team STANly">

    <!-- Le styles -->
    <link href="../css/style.css" rel="stylesheet" type="text/css">
    <link href="/Stanly/clone/css/bootstrap.css" rel="stylesheet">
    <link href="/Stanly/clone/css/bootstrap-responsive.css" rel="stylesheet">

    <link href="/Stanly/clone/css/index.css" rel="stylesheet">

	<script type="text/javascript" src="http://code.jquery.com/jquery-2.0.0.min.js"></script>
    <script type="text/javascript" src="../js/stanlyutils.js"></script>
</head>


<body>
    <div id="container" class="dashboard">
    <div id="header-container">
    <div id="sliverLine"></div>
    <div id="header">
    <div id="logoBox" class="img"></div>
    <div id="rightBox">
    <div id="user-container">
    <div id="userBox">
    <!--<div class="circle_wrapper"><img src="http://profile.ak.fbcdn.net/hprofile-ak-ash3/27350_100001314106197_1542605533_q.jpg" alt="profile" /></div>
    <div class="userName">An Jeungwon</div>-->
    </div>
    <div id="userInfoIcon" class="img"></div>
    </div>
    <!--user-container fin-->
    </div>
    </div>
    <!--header fin-->
    </div>
    </div>
    <!--header-container fin-->
    <div class="container-narrow">

        <hr>

        <div class="jumbotron">
            <h1>STANly</h1>
            <p class="lead">JAVA Architecture Analyzer</p>
            <div role="main">
                <form class="form-inline input-append" method="post" action="">
                    <input name ="URL" id="URL" placeholder="Get Address" type="text">
                    <input name= "Name" id="Name" placeholder="Project Name" type="text">
                    <button class="btn"><i class="icon-bookmark"></i>Analysis NOW!</button>
                </form>
            <script>
                var turl;
                $(".form-inline").submit(function(){
                    turl = $("#URL").val();
                    console.log("url: " + turl);
                    $.ajax({
                        type: "GET",
                        dataType: "json",
                        url: "/Stanly/project/IsProject",
                        data: "URL="+turl,
                        success: reqGetResponse,
                        error: function (data) {
                            console.log("json request error");
                        }
                    });
                    return false;
                });
                function reqGetResponse(data) {
                    //data = JSON.parse(data);
                    console.log(data);
                    $("#Name").val(data.state);
                    if(data.state == "IS_GITURL")
                    {
                    }
                    else if(data.state == "IS_GITNAME")
                    {
                        sendPost( "/Stanly/project/GitClone", "URL=" + turl);
                    }
                    else if(data.state == "NONE_PROJECT")
                    {
                        sendPost( "/Stanly/project/GitClone", "URL=" + turl);
                    }
                    else if(data.state == "GIT_ERROR")
                    {
                    }
                }
                function sendPost(url,arg){
                    // Form객체를 만들고 속성값들을 추가함
                    var oForm = document.createElement('form');

                    oForm.method = "post";
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
            </script>
            </div>
        </div>
        <hr>
        <div class="footer">
            <p class="text-center">(c) Software Maestro 3rd</p>
        </div>

    </div>

<!-- Le javascript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="/Stanly/clone/js/Stanly/bootstrap.min.js"></script>
</body></html>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>STANly; JAVA Architecture Analyzer</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="STANly@Software Maestro">
    <meta name="author" content="Team STANly">

    <!-- Le styles -->
    <link href="./assets/css/bootstrap.css" rel="stylesheet">
    <link href="./assets/css/bootstrap-responsive.css" rel="stylesheet">

    <link href="./assets/css/index.css" rel="stylesheet">

    <script src="./assets/js/jquery-1.7.1.min.js"></script>
</head>

<body>

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
<script src="./assets/js/bootstrap.min.js"></script>
</body></html>
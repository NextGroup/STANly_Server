<!DOCTYPE html>
<HTML>
<HEAD>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <link href="./js/d3/src/nv.d3.css" rel="stylesheet" type="text/css">
    <link href="./css/style.css" rel="stylesheet" type="text/css">

    <script type="text/javascript" src="http://code.jquery.com/jquery-2.0.0.min.js"></script>
    <script type="text/javascript" src="./js/arcadia.js"></script>
    <script type="text/javascript" src="./js/aGraph.js"></script>
    <script type="text/javascript" src="./js/d3/lib/d3.v2.js"></script>
    <script type="text/javascript" src="./js/d3/nv.d3.js"></script>
    <script type="text/javascript" src="./js/d3/src/models/pie.js"></script>



    <script type="text/javascript" >
        <!--
        function check(){
            if (document.Loginform.client_id.value =="") {
                alert("Please Input ID");
                document.Loginform.client_id.focus();
            } else {
                githubcall();
            }
        }

        function githubcall(){
            $.ajax({
                type: "GET",
                url: "https://api.github.com/users/"+document.Loginform.client_id.value,
                success: reqGetResponse,
                error: function (data) {
                    alert("Could not Search ID");
                    reset();
                }
            });
        }

        function RadioCheck(){

           var radioobj = document.all("chk_info");
           var size = radioobj.length;



            for(var i = 0; i < size; i++) {
                if(radioobj[i].checked) {
                   return radioobj[i].value;
                }
            }
        }

        function reqGetResponse(data) {
            //data = JSON.parse(data);
            if(RadioCheck() == "Developer")
                sendGet("/Stanly/dev/dashboard.html","Name="+document.Loginform.client_id.value+"&Version="+RadioCheck());
            else
                sendGet("/Stanly/arc/dashboard.html","Name="+document.Loginform.client_id.value+"&Version="+RadioCheck());
        }
        function reset(){
            document.Loginform.client_id.value = "";
            document.Loginform.client_id.focus();
        }
        function sendGet(url,arg){
            // Form��ü�� ����� �Ӽ������� �߰���
            var oForm = document.createElement('form');

            oForm.method = "get";
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
            // Form�ȿ� TextBox�� ����
            oForm.appendChild(oInputHidden);
            // Body�ȿ� Form�� ����
            document.body.appendChild(oForm);
            oForm.submit();
        }
        //-->
    </script>
</HEAD>
<BODY>
<div id="container" class="dashboard">
    <div id="header-container">
        <div id="sliverLine"></div>
            <div id="header">
                <div id="logoBox" class="img"></div>
            </div>
    </div>

    <div id="Body-Container">
        <!-- <table style='top: 50%; left: 50%; position: absolute; height: 200px; text-align: left; margin: -100px 0pt 0pt -150px; border-color:black' cellspacing="0" cellpadding="0" border="1"> -->

        <div class="loginform cf"style="font-size: 14px">
            <form name="Loginform" METHOD="get" url = "https://github.com/login/oauth/authorize" accept-charset="utf-8">
                <ul>
                    <li>
                        <label>GitHub ID</label>
                        <input type="string" name="client_id" placeholder="GitHub ID" required>
                    </li>
                    <li>
                        <label>Password</label>
                        <input type="password" name="client_pw" placeholder="password" required>
                    </li>
                </ul>
                <ul>
                    <li>
                        <form name="formname">
                            <input type="radio" name="chk_info" value="Developer" checked="checked">Developer
                            <input type="radio" name="chk_info" value="Architect"> PM & Architect
                        </form>
                    </li>
                </ul>
                <ul>
                    <li>
                        <input type="submit" name=btn1 value="Log In" onClick="javascript:check();">
                        <input type="submit" name=btn1 value="Reset" onClick="javascript:reset();">
                    </li>

                </ul>
            </form>
        </div>

    </div>
</div>



</BODY>
</HTML>


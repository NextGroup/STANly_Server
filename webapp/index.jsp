<!DOCTYPE html>
<HTML>
<HEAD>
    <script type="text/javascript" src="http://code.jquery.com/jquery-2.0.0.min.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=euc-kr">



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
                    alert("Don't Search ID");
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
            sendGet("/Stanly/dashboard.html","Name="+data.login+"&Version=" + RadioCheck());
        }
        function reset(){
            document.Loginform.client_id.value = "";
            document.Loginform.client_id.focus();
        }
        function sendGet(url,arg){
            // Form객체를 만들고 속성값들을 추가함
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
            // Form안에 TextBox를 넣음
            oForm.appendChild(oInputHidden);
            // Body안에 Form을 넣음
            document.body.appendChild(oForm);
            oForm.submit();
        }
        //-->
    </script>
</HEAD>
<BODY>
<FORM name="Loginform" METHOD="get" url = "https://github.com/login/oauth/authorize"   >
    GitHub ID : <INPUT TYPE="string" NAME="client_id">
    <input type=button name=btn1 value="Log In" onClick="javascript:check();">
    <input type=button name=btn1 value="Reset" onClick="javascript:reset();">

    <div>
        <form name="formname">
    <input type="radio" name="chk_info" value="Developer" checked="checked"> 개발자 버전
    <input type="radio" name="chk_info" value="Architect"> 아키텍트&PM 버전
            </form>
    </div>

</FORM>
</BODY>
</HTML>


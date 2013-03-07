<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.1/jquery.min.js"></script>  
<script type="text/javascript">  
		$(document).ready(function() {  
		    
		    // Check The Status Every 2 Seconds  
		    var timer = setInterval(function() {  
		          
		        $.ajax({  
		              url: 'reportstatus.json',  
		              success: function(data) {  
		                  
		                if(data === 'COMPLETE') {  
		                    $('#reportLink').html("<a target='_target' href='report.html'>Download Report</a>");      
		                    clearInterval(timer);  
		                }  
		              }  
		        });  
		          
		    }, 2000);  
		});  
</script>
</head>
<body>
<h1>  
  Report Generator  
</h1>  
<div id="reportLink">Please Wait While Your Report Is Being Generated</div>
</body>
</html>
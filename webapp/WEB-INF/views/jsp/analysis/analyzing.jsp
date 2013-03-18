<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Analyzing...</title>
    <!-- Le styles -->
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="STANly@Software Maestro">
    <meta name="author" content="Team STANly">
    <!-- Le styles -->
    <link href="/Stanly/assets/css/bootstrap.css" rel="stylesheet">
    <link href="/Stanly/assets/css/bootstrap-responsive.css" rel="stylesheet">

    <script src="/Stanly/assets/js/jquery-1.7.1.min.js"></script>
    <script src="/Stanly/assets/js/spin.min.js"></script>

    <link href="/Stanly/assets/css/index.css" rel="stylesheet">



    <script>
        var opts = {
            lines: 10, // The number of lines to draw
            length: 10, // The length of each line
            width: 7, // The line thickness
            radius: 12, // The radius of the inner circle
            color: '#000', // #rgb or #rrggbb
            speed: 1.0, // Rounds per second
            trail: 54, // Afterglow percentage
            shadow: false // Whether to render a shadow
        };

        var spinner;

        $.fn.spin = function(opts) {
            this.each(function() {
                var $this = $(this), data = $this.data();

                if (data.spinner) {
                    data.spinner.stop();
                    delete data.spinner;
                }
                if (opts !== false) {
                    data.spinner = new Spinner($.extend({color: $this.css('color')}, opts)).spin(this);
                }
            });
            return this;
        };

        $(document).ready(function(){
            $('.spin').spin(opts);

            setInterval(function(){
                $.ajax({
                    type: "GET",
                    dataType: "json",
                    url: "/Stanly/project/Analysis/IsDone.json",
                    //분석완료 URL로 수정해야함
                    data: {},
                    success: reqGetResponse,
                    error: function (data) {
                    console.log("Error IsDone "+data);
                }
                });
            },3000);
            function reqGetResponse(data) {
                //data = JSON.parse(data);
                if(data.result)
                {
                    //분석이 완료 됬을때 여기에 넣으면 됨
                    console.log("true" + data);
                }
                else
                {
                    console.log("false" + data);
                }
            }

        });
    </script>
</head>
<body>
<div class="container-narrow">

    <hr>

    <div class="jumbotron">
        <h1>Analyzing...</h1>
        <p class="lead">Git address</p>
        <div class="spin"></div>
    </div>
    <hr>
    <div class="footer">
        <p class="text-center">© Software Maestro 3rd</p>
    </div>

</div>
<!-- Le javascript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="/Stanly/assets/js/bootstrap.min.js"></script>
</body>
</html>
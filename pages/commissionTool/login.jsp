<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<spring:url value="/resources/css/style.css" var="styleCss" />
<spring:url value="/resources/js/index.js" var="indexJs" />

<link href="${styleCss}" rel="stylesheet" />
<script src="${indexJs}"></script>


<title>Login</title>
</head>
<style>
#login_btn{
background-color: white; 
  	border-radius: 8px;
    border: 2px solid #9c4d69;;
    color: black;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 14px;
    -webkit-transition-duration: 0.4s; /* Safari */
    transition-duration: 0.4s;
    cursor: pointer;
}
#login_btn:hover {
    background-color: #9c4d69;
    color: white;
}
input[type=text]{
 	width: 80%;
    padding: 12px 20px;
    margin: 8px 0;
    display: inline-block;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
    color: black;
}
input[type=password]{
 	width: 80%;
    padding: 12px 20px;
    margin: 8px 0;
    display: inline-block;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
    color: black;
}

</style>
 <body style="background-image:url(resources/image/bg_img.jpg); background-repeat: no-repeat; background-size: 100% 100%;">

<form action="/CommissionTool/submitLogin"  method="post">


	<div class="vid-container">
		<video id="Video1" class="bgvid back" autoplay="false" muted="muted"
			preload="auto" loop>
			<source
				src="http://shortcodelic1.manuelmasiacsasi.netdna-cdn.com/themes/geode/wp-content/uploads/2014/04/milky-way-river-1280hd.mp4.mp4"
				type="video/mp4">
		</video>
		<div class="inner-container">
			<video id="Video2" class="bgvid inner" autoplay="false" muted="muted"
				preload="auto" loop>
				<source
					src="http://shortcodelic1.manuelmasiacsasi.netdna-cdn.com/themes/geode/wp-content/uploads/2014/04/milky-way-river-1280hd.mp4.mp4"
					type="video/mp4">
			</video>
			<div class="box">
				<form:form method="POST" name="loginForm"
					action="/CommissionTool/submit">
					<h1 style="font-family: serif;">Welcome!</h1>
					<h3 style="font-family: serif;" align="center">Enter your credentials</h3>
					<center><input name="userName" type="text" placeholder="Username"/></center>
					<center><input name="password" type="password" placeholder="Password" /></center>
					<center><input type="submit" value="Login" id="login_btn"></center>

					<center><div style="color: red"><strong>${error}</strong></div></center>
					
				</form:form>
			</div>
		</div>
	</div>
	<script
		src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

</form>
</body>
</html>

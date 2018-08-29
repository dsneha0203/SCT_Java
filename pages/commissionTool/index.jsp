<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html >
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
    
    <title>Welcome</title>   
  </head>
  <style>
  	.inner_box{
  		background-color: #9c4d69;
  		color: #ffffff;
  		height: 20%;
  		font-size: 170%;
  		text-align: center;
  		opacity: 0.9;
  		position: relative;
		text-align: center;
  	}
  	span{
  		display: inline-block;
  		vertical-align: middle;
  		line-height: normal;
  	}
  	.box{
  		height:250px;
  		position:absolute;
  	}
  .box button{
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
   


.btn_log:hover {
    background-color: #9c4d69;
    color: white;
}
  
  </style>

  <body style="background-image:url(resources/image/bg_img.jpg); background-repeat: no-repeat; background-size: 100% 100%;">

    <div class="vid-container">
  <video id="Video1" class="bgvid back" autoplay="false" muted="muted" preload="auto" loop>
      <source src="http://shortcodelic1.manuelmasiacsasi.netdna-cdn.com/themes/geode/wp-content/uploads/2014/04/milky-way-river-1280hd.mp4.mp4" type="video/mp4">
  </video>
  <div class="inner-container">
    <video id="Video2" class="bgvid inner" autoplay="false" muted="muted" preload="auto" loop>
      <source src="http://shortcodelic1.manuelmasiacsasi.netdna-cdn.com/themes/geode/wp-content/uploads/2014/04/milky-way-river-1280hd.mp4.mp4" type="video/mp4">
    </video>
    
    <div class="box">
      <div class="inner_box">
      <span>Sales Commission Tool</span>
      </div>
     
    
       <center><button class="button btn_log" onClick="location.href='/CommissionTool/login'">Login</button></center><br><br>    
    </div>
  </div>
</div>
    <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

       
  </body>
</html>

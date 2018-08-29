<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script type="text/javascript" language="javascript" src="http://www.technicalkeeda.com/js/javascripts/plugin/jquery.js"></script>
<script type="text/javascript" src="http://www.technicalkeeda.com/js/javascripts/plugin/json2.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Spring Jquery Ajax Demo</title>
<style>
Table.GridOne {
 padding: 3px;
 margin: 0;
 background: lightyellow;
 border-collapse: collapse; 
 width:35%;
}
Table.GridOne Td { 
 padding:2px;
 border: 1px solid #ff9900;
 border-collapse: collapse;
}
</style>
<script type="text/javascript">
function madeAjaxCall(){
 $.ajax({
  type: "post",
  url: "http://localhost:8080/CommissionTool/jsonresponse1",
  cache: false,    
  data:'name=' + $("#name").val() + "&id;=" + $("#id").val() + "&salary;=" + $("#salary").val(),
  success: function(response){
   $('#result').html("");
   var obj = JSON.parse(response);
   $('#result').html("First Name:- " + obj.name +"</br>Id:- " + obj.id  + "</br>salary:- " + obj.salary);
  },
  error: function(){      
   alert('Error while request..');
  }
 });
}
</script>
</head>
<body>
 <form name="employeeForm" method="post"> 
  <table cellpadding="0" cellspacing="0" border="1" class="GridOne">
   <tr>
    <td>Name</td>
    <td><input type="text" name="name" id="name" value=""></td>
   </tr>
   <tr>
    <td>Id</td>
    <td><input type="text" name="id" id="id" value=""></td>
   </tr>
   <tr>
    <td>Salary</td>
    <td><input type="text" name="salary" id="salary" value=""></td>
   </tr>
   <tr>
    <td colspan="2" align="center"><input type="button" value="Ajax Submit" onclick="madeAjaxCall();"></td>
   </tr>
  </table>
 </form>
  <h1>Spring Framework Jquery Ajax Demo</h1>
 <div id="result"></div>
</body>
</html>
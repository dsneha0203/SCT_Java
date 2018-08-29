 <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<head>
    <title>Add Employee</title>
</head>
<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">
 
		<head>
<title>CompPlan</title>
<link rel="stylesheet" href="../resources/css/jquery-ui.css" />
<link rel="stylesheet" href="../resources/css/style.css" />

<script src="../resources/js/jquery.1.12.1.min.js"></script>
<script src="../resources/js/jquery-ui.js"></script>

<script src="../resources/js/jquery.datePicker-2.1.1.js"></script>
		</head>
		
				<script>



 
  $(function() {
    	   $('input').filter('.datepicker').datepicker({
    	      changeMonth: true,     
    	     changeYear: true,
    	      depth: "year",
    	      showOn: 'button',      
    	      buttonImage: '../resources/image/calendar1.png',
    	      buttonImageOnly: true
    	     });
 });
    

</script>
   
           
<h2>Welcome to Sales Commission Tool Mr.${sessionScope.employee}</h2>

 <table border=1>
 <tr>
 <td>&nbsp;form&nbsp;<input  class="datepicker" type="text"
									name="" ></td>
								<td>&nbsp;to&nbsp;<input  class="datepicker" type="text"
									name="" size="11" height="0.10"></td>
 
 
 
 </tr>
 
 </table>
 
 
 
 
  </tiles:putAttribute>
</tiles:insertDefinition>
  
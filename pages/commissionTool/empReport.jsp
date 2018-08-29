<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page session="false"%>
<head>
<title>Employee Report</title>
</head>
<style>

</style>
<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body"> 
	
	
	 <div align="center"><h1>Compensation Report</h1></div>
	 <div align="center">
	 Choose Employee:&nbsp;<select onchange="getValue()" id="emp-name">
	 <option selected=selected value="">Choose an employee</option>
	 	<c:forEach items="${empList}" var="employee">
							<option value="${employee.id}">${employee.employeeName}</option>
								
		</c:forEach>
	 </select>  &nbsp;&nbsp;&nbsp; 
	 
	<script type="text/javascript">
	//window.onload = getValue();
	function getValue(){
		
		var x = document.getElementById("emp-name").selectedIndex;
		var emp_id = document.getElementsByTagName("option")[x].value;
		window.location.href = "/CommissionTool/empReportDetails/"+emp_id;
		
		
	}
	</script>
	 <br><br>
	 	
	 
	 </div>
	
</tiles:putAttribute>
</tiles:insertDefinition>
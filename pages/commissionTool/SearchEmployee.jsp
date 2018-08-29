<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page session="false"%>

<tiles:insertDefinition name="defaultTemplate">
<tiles:putAttribute name="body">

<head>
<title>search employee</title>

	<link rel="stylesheet" href="resources/css/kendo.common.min.css" />
	<link rel="stylesheet" href="resources/css/kendo.default.min.css" />

	<script src="resources/js/jquery-1.12.1.min.js"></script>
	<script src="resources/js/kendo.all.min.js"></script>

<script>
			
	
	$(document).ready(function() {
		$("#grid").kendoGrid({
			pageSize : 10,
			height : 200,
			sortable : true,
			resizable : true,
			reorderable : true,
			groupable : false,
			selectable : "multiple",
			filterable : {
				extra : false,
				operators : {
					string : {
						contains : "Contains",
						startswith : "Starts With",
						endswith : "Ends With",
					}
				}
			},

			columnMenu : true,
			pageable : {
				pageSize : 10,
				buttonCount : 10,
				pageSizes : true,
				refresh : true
			},
		});
	});

	function sendData() {
		var x = document.getElementById('select').value;
		window.opener.document.getElementById('text1').value = x;
		//window.opener.document.getElementById('text2').value = x;
		window.close();
	}

	$(function() {
		$('input').focusin(function() {
			input = $(this);
			input.data('place-holder-text', input.attr('placeholder'))
			input.attr('placeholder', '');
		});

		$('input').focusout(function() {
			input = $(this);
			input.attr('placeholder', input.data('place-holder-text'));
		});
	})

	function validate() {
		if (document.getElementById("checking").value == "") {
			alert("Please enter an Employee Name for Search");
		}
	}
</script>
</head>

<style>
#textboxid {
	background-color: #FFF;
	min-height: 80px;
	min-width: 120px;
}
</style>
		<h2 align="center">Search Employee</h2>
		<body>
		<form action="/CommissionTool/searchempimage" method="post">
				<div id="menu"></div>
				<div id="buffer" class="body"></div>
				<table border=0 align="center">
						<tr>
							<td><b>Search:&nbsp;&nbsp;</b>
							<input type="text" name="EmployeeName" style= "background-color:#ccffcc" placeholder="Enter Employee Name">&nbsp;&nbsp;</td> 
							<td><input type="image" img src="resources/image/search.png" style="height:30px;width:30px; value="" onclick="validate();"/></td>
						</tr>
					</table><br>
			</form>
			
		<div id="example">
			<c:if test="${!empty emp}">
				<table id="grid">
					<colgroup>
						<col />
						<col style="width: 110px" />
						<col style="width: 120px" />
						<col style="width: 130px" />
				   </colgroup>
				   
				   <thead>
					<tr>
						<th data-field="id">ID</th>
						<th data-field="employeeName">Employee Name </th>
						<th  data-field="startDate">Start Date </th>
						<th data-field="action">Choose Employee </th>
					</tr>
				</thead>
				
					<c:forEach items="${emp}" var="emp1">
							<tr>
								<td>${emp1.id}</td>
								<td>${emp1.employeeName}</td>
								<td>${emp1.startDate}</td>
								<td><input type="checkbox" value="${emp1.employeeName}" name="select" id="select" onclick="sendData();"></td>	  
								<!-- <td><input type="checkbox" value="${emp1.id}" name="select" id="select" onclick="sendData();"></td> -->
							</tr>						
					</c:forEach>	
				</table> 
			</c:if>
		</div>
	</body>
</tiles:putAttribute>
</tiles:insertDefinition>
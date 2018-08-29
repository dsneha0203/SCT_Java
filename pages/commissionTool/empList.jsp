<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page session="false"%>
<head>
<title>Employee List</title>
</head>
<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">

		<div id="page-wrapper">
			<div class="container-fluid">
				<style type="text/css">
.tg {
	border-collapse: collapse;
	border-spacing: 0;
	border-color: #ccc;
}

.tg td {
	font-family: Arial, sans-serif;
	font-size: 14px;
	padding: 10px 5px;
	border-style: solid;
	border-width: 1px;
	overflow: hidden;
	word-break: normal;
	border-color: #ccc;
	color: #333;
	background-color: #fff;
}

.tg th {
	font-family: Arial, sans-serif;
	font-size: 14px;
	font-weight: normal;
	padding: 10px 5px;
	border-style: solid;
	border-width: 1px;
	overflow: hidden;
	word-break: normal;
	border-color: #ccc;
	color: #333;
	background-color: #f0f0f0;
}

.tg .tg-4eph {
	background-color: #f9f9f9
}
</style>


				<h3>Employees List</h3>
				<c:if test="${!empty listEmployee}">
					<table class="tg">
						<tr align="center">
							<th width="120">ID</th>
							<th width="200">First Name</th>
							<th width="200">Last Name</th>
							<th width="150">Salary</th>
							<th width="200" align="center">Actions on Row</th>
						</tr>
						<c:forEach items="${listEmployee}" var="employee">
							<tr>
								<td>${employee.id}</td>
								<td>${employee.firstName}</td>
								<td>${employee.lastName}</td>
								<td>${employee.salary}</td>
								<td><a
									href="<c:url value='/editEmployee/${employee.id}' />">Edit</a>
									| <a href="<c:url value='/removeEmployee/${employee.id}' />">Delete</a></td>
							</tr>
						</c:forEach>
					</table>
				</c:if>
				</div>
				</div>
				<div class="well">
					<a href="<c:url value='/employee' />">Add New Employee</a>
				</div>
			
		
	</tiles:putAttribute>
</tiles:insertDefinition>
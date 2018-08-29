<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<title>Add Employee</title>
</head>
<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">

		<div id="page-wrapper">

			<div class="container-fluid">

				<!-- Page Heading -->
				<div class="row">
					<div class="col-lg-12">
						<h3 class="page-header">Employees Details</h3>
						<ol class="breadcrumb">
							<li><i class="fa fa-dashboard"></i> <a href="index.html">Master
									Data</a></li>
							<li class="active"><i class="fa fa-edit"></i> Employee</li>
						</ol>
					</div>
				</div>
				<!-- /.row -->

				<div class="row">
					<div class="col-lg-6">

						<form:form method="POST" action="/CommissionTool/submitEmployee">
							<table>
								<tr>
									<td><form:label path="firstName">Employee First Name:</form:label></td>
									<td><form:input path="firstName"
											value="${employee.firstName}" /></td>
								</tr>
								<tr>
									<td><form:label path="lastName">Employee Last Name:</form:label></td>
									<td><form:input path="lastName"
											value="${employee.lastName}" /></td>
								</tr>
								<tr>
									<td><form:label path="salary">Employee Salary:</form:label></td>
									<td><form:input path="salary" value="${employee.salary}" /></td>
								</tr>

								<tr>
									<td><input type="submit" value="Submit" /> | <a
										href="<c:url value='/employeeList' />">Cancel</a></td>
								</tr>
							</table>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>
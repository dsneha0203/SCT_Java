<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page session="false"%>
<head>
<title>Insert Role</title>
</head>
<style>
#textboxid {
	background-color: #FFF;
	min-height: 80px;
	min-width: 120px;
}
</style>
<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">
		<body>

			<form action="/CommissionTool/submitRole" method="post">

				<div id="menu"></div>
				<div id="buffer" class="body"></div>
				<div id="body" class="body" align="center">
					<h1 align="center">Role Details</h1>
					<table border=0 align="center">
						<tr>
							<td><b>Role Name:</b></td>
							<td><input type="text" name="RoleName"></td>
						</tr>
						<tr>
							<td><b>Description:</b></td>
							<td><input type="text" name="Description"></td>
						</tr>
						<tr>
							<td><b>Reports To:</b></td>
							<td><input type="text" name="ReportTo"></td>
						</tr>
						<tr>
							<td colspan="2">
								<div id="setTarget"></div>
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td><input type="submit" value="Submit"> <input
								type="submit" value="Add New Parameter"> <input
								type="submit" value="cancel"></td>
						</tr>
					</table>
				</div>

			</form>




		</body>
	</tiles:putAttribute>
</tiles:insertDefinition>
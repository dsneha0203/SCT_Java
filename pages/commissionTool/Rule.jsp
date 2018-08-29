<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page session="false"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update Compensation Rule Details</title>
</head>

<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">
		<body>
			<div align="center">

				<h1>Compensation Rule Details</h1>
				<form action="/CommissionTool/submitRule" method="post">


					<b>Rule name :</b> <input type="text" name="RuleName" /><br />
					<br /> <b>Description :</b> <input type="text" name="Description" /><br />
					<br /> <b>Rule type :</b> <input type="text" name="RuleType" /><br>
					<br /> <input type="submit" value="Update" />

				</form>
			</div>
			>
		</body>
	</tiles:putAttribute>
</tiles:insertDefinition>
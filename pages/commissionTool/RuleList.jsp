<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page session="false"%>
<head>
<title>Rule List</title>
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


				<div align="center">

					<h2>Rule List</h2>
					</br>
					<c:if test="${!empty listRule}">
						<table border="1" class="tg">
							<tr>
								<th><b>ID</b></th>
								<th><b>Rule Name</b></th>
								<th><b>Description</b></th>
								<th><b>Rule To</b></th>
								<th align="center"><b>Actions on Row</b></th>
							</tr>

							<c:forEach items="${listRule}" var="rule">
								<tr>

									<td>${rule.id}</td>
									<td>${rule.ruleName}</td>
									<td>${rule.description}</td>
									<td>${rule.ruleType}</td>
									<td><a href="<c:url value='/editRule/${rule.id}' />">Edit</a>
										| <a href="<c:url value='/deleteRule/${rule.id}' />">Delete</a></td>

								</tr>

							</c:forEach>


						</table>
					</c:if>
				</div>
			</div>
			<div class="well">
				<a href="<c:url value='/rule' />">Add Rule</a>
			</div>
		</div>



	</tiles:putAttribute>
</tiles:insertDefinition>
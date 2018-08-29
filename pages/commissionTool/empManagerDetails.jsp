<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">
		<div align="center">
			<h3>Employee Manager Details</h3>
			
		</div>

		<head>

<title>EmployeeManagerDetails</title>

<link rel="stylesheet" href="../resources/css/kendo.common.min.css" />
<link rel="stylesheet" href="../resources/css/kendo.default.min.css" />

<script src="../resources/js/jquery-1.12.1.min.js"></script>
<script src="../resources/js/kendo.all.min.js"></script>

<script>
	$(document).ready(function() {
		$("#grid").kendoGrid({
			pageSize : 10,
			height : 400,
			//scrollable: {
			//virtual: true
			//},                                                
			//scrollable: true,
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
					//eq: "Is Equal To"
					}
				}
			},

			columnMenu : true,
			pageable : {
				pageSize : 10,
				buttonCount : 10,
				//numeric: true,
				pageSizes : true,
				refresh : true
			},
		});
	});
</script>

		</head>
		<div style="height: 580px; overflow: auto;">
		<div id="example">

			<table id="grid">
				<colgroup>
					<col />
					<col />
					<col />
					<col />
				</colgroup>
				<thead>
					<tr>
						<th data-field="Id">Id</th>
						<th data-field="StartDate">Start Date</th>
						<th data-field="EndDate">End Date</th>
						<th data-field="ManagerName">Manager Name</th>
						

					</tr>
				</thead>
				
					<c:forEach items="${managers}" var="manager">
						<tr>
							<td>${manager.id}</td>
							<td>${manager.startDate}</td>
							<td>${manager.endDate}</td>
							<td>${manager.manager.employeeName}</td>
						</tr>
					
				</c:forEach>


			</table>
		</div>
		</div>
		</tiles:putAttribute>
		</tiles:insertDefinition>
	
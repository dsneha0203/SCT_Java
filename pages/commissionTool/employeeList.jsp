<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">
		<div align="center">
			<h3>Employee List</h3>
		</div>

		<head>

<title>Employee List</title>

<link rel="stylesheet" href="resources/css/kendo.common.min.css" />
<link rel="stylesheet" href="resources/css/kendo.default.min.css" />

<script src="resources/js/jquery-1.12.1.min.js"></script>
<script src="resources/js/kendo.all.min.js"></script>

<script>
			

  
     			$(document).ready(function() {
                    $("#grid").kendoGrid({
                    	 pageSize: 10,
                    	 height: 400, 
                    	 //scrollable: {
                            //virtual: true
                         //},                                                
                          //scrollable: true,
                          sortable: true,
                          resizable: true,
                          reorderable: true,
                          groupable: false,
                          selectable: "multiple",
                          filterable: {
                        	  extra: false, 
                        	   operators: { 
                        	       string: {   
                        	            contains: "Contains",
                        	            startswith: "Starts With",
                        	            endswith: "Ends With",
                        	            //eq: "Is Equal To"
                        	        }
                        	    }
                        	}, 
                        	
                          columnMenu: true,                          
                          pageable: {
                             pageSize: 10,
       						 buttonCount: 10,
       						//numeric: true,
       						pageSizes: true,
       						refresh: true
                          },                                                                                                   
                    });                       
                });
     			
     			
   			</script>

		</head>
		<div id="example">

			<table id="grid">
				<colgroup>
					<col />
					<col />
					<col style="width: 110px" />
					<col style="width: 120px" />
					<col style="width: 130px" />
				</colgroup>
				<thead>
					<tr>
						<th data-field="EmployeeId">Employee Id</th>
						<th data-field="EmployeeName">Employee Name</th>
						<th data-field="Salary">Salary</th>
						<th data-field="StartDate">Start Date</th>
						<th data-field="TerminationDate">Termination Date</th>
						<th data-field="Role">Role</th>
						<th data-field="ManagerId">Manager ID</th>
						<th data-field="ManagerName">Manager Name</th>  
					</tr>
				</thead>

				<c:forEach items="${empList}" var="emp">
					<tr>
						<td><a href="/CommissionTool/empDetails/${emp.id}"><input type="button" 
						 value="${emp.id}"></a></td>
						<td>${emp.employeeName}</td>
						<td>${emp.salary}</td>
						<td>${emp.startDate}</td>
						<td>${emp.terminationDate}</td>
						<c:set var="role" value="roleName${emp.id}"/>
						<td>${requestScope[role]}</td>
						<c:set var="managerId" value="managerId${emp.id}"/>
						<td><a href="/CommissionTool/empDetails/${requestScope[managerId]}">${requestScope[managerId]}</a></td>
						<c:set var="managerName" value="managerName${emp.id}"/>
						<td>${requestScope[managerName]}</td>
					</tr>

				</c:forEach>


			</table>
		</div>

	</tiles:putAttribute>
</tiles:insertDefinition>
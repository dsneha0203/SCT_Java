<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">
		<div align="center">
			<h3>Order Split Rule</h3>
		</div>

		<head>

<title>SplitRuleDetails</title>

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
			<table id="grid">
				<colgroup>
					<col />
					<col style="width: 130px" />
					<col />
					<col />
					<col style="width: 110px" />
					<col style="width: 120px" />
					<col style="width: 130px" />
					
				</colgroup>
				<thead>
					<tr>
						<th data-field="SplitRuleName">Split Rule Name</th>
						<th data-field="Description">Description</th>
						<th data-field="ValidFrom">Valid From</th>
						<th data-field="Valid upto">Valid upto</th>
						<th data-field="Details">Details</th>
						
					</tr>
				</thead>

				<c:forEach items="${splitRuleDetails}" var="split">
					<tr>
					
						<td>${split.splitRuleName}</td>
						<td>${split.description}</td>
						<td>${split.startDate}</td>
						<td>${split.endDate}</td>
						  		
						<td>
						<a href="/CommissionTool/splitRuleDetails/${split.id}"><input type="button" 
						 value="Details"></a>
			
						
					</tr>

				</c:forEach>


			</table>
			
			<br/>
			 <div align="center">
					<a href="/CommissionTool/newSplitRule"> <input type="button"
						value="Create Order Split Rule" /></a>
				</div>
			
	</tiles:putAttribute>
</tiles:insertDefinition>
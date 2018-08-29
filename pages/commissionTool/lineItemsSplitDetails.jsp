<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">
		<div align="center">
			<h3>Line Item Split Details</h3>
		</div>

		<head>

<title>OrderDetails</title>

<link rel="stylesheet" href="../resources/css/kendo.common.min.css" />
<link rel="stylesheet" href="../resources/css/kendo.default.min.css" />

<script src="../resources/js/jquery-1.12.1.min.js"></script>
<script src="../resources/js/kendo.all.min.js"></script>



<script>

function openWindow(){
	window.open('/CommissionTool/lineItems/' , '' , 'width=1400,height=700,scrollbars=yes');
	}


</script>

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
				
				<thead>
					<tr>
						<th data-field="lineItemId">Order Line Item Id</th>
						<th data-field="orderLineItemSplitRuleId">Order Line Item Split Id</th>
						<th data-field="splitRuleId">Split Rule Id</th>
						<th data-field="benefId">Beneficiary Id</th>
						<th data-field="benefType">Beneficiary Type</th>
						<th data-field="split_qty">Split Quantity</th>
						<th data-field="split_sub_total">Split Sub total</th>
						
						
					</tr>
				</thead>

				<c:forEach items="${splitList}" var="splitListItems">
					<tr>
					
						
						<td>${sessionScope.lineItemId}</td>
						<td>${splitListItems.id}</td>
						<td>${splitListItems.splitRule.id }</td>
						<td>${splitListItems.beneficiary.id}</td>
						<td>${splitListItems.beneficiaryType}</td>
						<td>${splitListItems.splitQuantity}</td>
						<td>${splitListItems.splitSubTotal}</td>
						
					
					</tr>
					
				</c:forEach>

			</table>
			
			<br/><div align="center">
					<a href="/CommissionTool/lineItemDetails/${sessionScope.ordDetailId }"> <input type="button"
						value="Back To Line Items" /></a>
				</div>
		
	</tiles:putAttribute>
</tiles:insertDefinition>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">
		<div align="center">
			<h3>Line Items Details</h3>
		</div>

		<head>

<title>LineItems</title>

<link rel="stylesheet" href="../resources/css/kendo.common.min.css" />
<link rel="stylesheet" href="../resources/css/kendo.default.min.css" />

<script src="../resources/js/jquery-1.12.1.min.js"></script>
<script src="../resources/js/kendo.all.min.js"></script>


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
				<!--<colgroup>
					<col />
					<col />
					<col />
					<col style="width: 110px" />
					<col style="width: 120px" />
					<col style="width: 130px" />
					
				</colgroup>-->
				<thead>
					<tr>
						<th data-field="ProductId">Product Id</th>
						<th data-field="Quantity">Quantity</th>
						<th data-field="Rate">Rate</th>
						<th data-field="DisCountPercentage">DiscountPercentage</th>
						<th data-field="DutyPercentage">DutyPercentage</th>
						<th data-field="subTotal">SubTotal</th>
						<th data-field="Details">Details</th>
						
					</tr>
				</thead>
				<c:set var="product_total" value="0" />
				<c:set var="service_total" value="0"/>
				<c:forEach items="${OrderLineItems}" var="orLineItems">
					<tr>
					
						<!-- <td><a href="/CommissionTool/splitDetails/${orLineItems.product.id}"><input type="button"
						value="${orLineItems.product.id}"></a></td> -->
						<td>${orLineItems.product.id}</td>
						<td>${orLineItems.quantity}</td>
						<td>${orLineItems.rate}</td>
						<td>${orLineItems.discountPercentage}</td>
						<td>${orLineItems.dutyPercentage}</td>
						<!--<td>${orLineItems.subtotal}</td>   -->
						<c:set var="qty" value="${orLineItems.quantity}"/>
						<c:set var="rate" value="${orLineItems.rate}"/>
						<c:set var="dis_percent" value="${orLineItems.discountPercentage}"/>
						<c:set var="dut_percent" value="${orLineItems.dutyPercentage}"/>
						<c:set var="subTotal" value="${(1+(dut_percent/100))*((1-(dis_percent/100))*(qty*rate)) }"/>
						<td>Rs.${subTotal}</td>
						<td><a href="/CommissionTool/lineItemSplitDetails/${orLineItems.id }"><input type="button" 
						 value="Details"></a></td>
					
					</tr>
					
				</c:forEach>
				

			</table>
			
			<br/><div align="center">
					<a href="/CommissionTool/orderDetails/${sessionScope.id}"> <input type="button"
						value="Back To Order Details" /></a>
				</div>
		
	</tiles:putAttribute>
</tiles:insertDefinition>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>   
<head>
    <title>Edit Rule</title>
</head>
 <tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">
 <style type="text/css">
        .tg  {border-collapse:collapse;border-spacing:0;border-color:#ccc;}
        .tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#fff;}
        .tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#f0f0f0;}
        .tg .tg-4eph{background-color:#f9f9f9}
    </style>
    <div id="menu"></div>
	<div id="buffer" class="body"></div>
	<div id="body" class="body" align="center">
				<h1 align="center">Compensation Rules</h1>
		<table border="1">
			<thead>
			<tr>
				<td>Rule Name</td>
				<td>Type</td>
				<td>Description</td>
				<td>Rule Details</td>
			</tr>
			</thead>
			<tr>
				<td><a href="<c:url value='/RuleList' />"></a>
				<td>Composite</td>
				<td>Compensation rule for high performers</td>
				<td>When any of "Top Ranks", "Sales Performance", "Order booking" is achieved, pay 10% of "Sales performance"</td>
			</tr>
			<!-- ><tr>
				<td><b><a href="#">Top Ranks</a></b></td>
				<td>Composite</td>
				<td>Compensation rule for top rankers</td>
				<td>Rule Details</td>
			</tr>
			<tr>
				 <td><a href="../../pages/compRule/CompRuleDetailsSimple.html">Sales Performance</a></td>
				<td>Simple, Individual</td>
				<td>Compensation rule for Sales performances</td>
				<td>Rule Details</td>
			</tr>
			<tr>
				<td><a href="#">Order Booking</a></li>
				<td>Simple, Individual</td>
				<td>Description</td>
				<td>Rule Details</td>
			</tr>
			<tr>
				<td><a href="../../pages/compRule/CompRuleDetailsSimpleRank.html">Max Order Booking</a></td>
				<td>Simple, Rank</td>
				<td>Compensation rule for maximum order booking</td>
				<td>When within 10% of all the employees based on max(order total)</td>
			</tr>
			<tr>
				<td><a href="#">Max Order Counts</a></td>
				<td>Simple, Rank</td>
				<td>Description</td>
				<td>Rule Details</td>
			</tr>
			<tr>
				<td><a href="#">Max Sale of Computers</a></td>
				<td>Type</td>
				<td>Description</td>
				<td>Rule Details</td>
			</tr><-->
		</table>

	</div>
</body>

</tiles:putAttribute>
</tiles:insertDefinition>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page session="false"%>
<head>
<title>Employee Report</title>
<link rel="stylesheet" href="resources/css/jquery-ui.css" />
	<link rel="stylesheet" href="resources/css/style.css" />

	<script src="resources/js/jquery.1.12.1.min.js"></script>
	<script src="resources/js/jquery-ui.js"></script>
</head>

<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body"> 
<style>
input[type=text]{
	border: none;
	font-weight: bold;
}
body {
overflow: -moz-scrollbars-vertical; 
overflow-y: auto;
}

.btn{
border: none;
background: none;
}
</style>	
	
	 <div align="center"><h1>Compensation Report</h1></div>
	 <div align="center">
	 <table>
	 <tr>
	 <td width="400px">
	 Employee name:  <input type="text" id="empName" value="${emp.employeeName}"><br><br>
	 <td>
	 <td>
	 &nbsp;&nbsp;&nbsp; 
	  Or, Change Employee:&nbsp;<select onchange="setValue()" id="emp-name">
	 <option selected=selected value="">Choose an employee</option>
	 	<c:forEach items="${empList}" var="employee">
							<option value="${employee.id},${employee.employeeName}">${employee.employeeName}</option>
								
		</c:forEach>
	 </select>
	 <script>
	   function setValue(){
		   var x = document.getElementById("emp-name").selectedIndex;
		   var value_list = document.getElementsByTagName("option")[x].value;
		   var emp_id= value_list.split(",")[0];
		   var emp_name= value_list.split(",")[1];
		   document.getElementById("empName").value=emp_name;
		   window.location.href = "/CommissionTool/empReportDetails/"+emp_id;
	   }
	 </script>
	 </td>
	 <tr>
	 <td width="400px">
	 Role: <input type="text" name="empRole" value="${role_Name}">  
	 </td>
	 </tr>
	</table>
	 <br><br>
<table border="1">
			<thead>
			<tr>
				<td>Period</td>
			<!--  	<td>Effective Rule Name</td>	-->		
				<td>Status</td>
				<td>Show Details</td>
			</tr>
			</thead>
			<tr>
				<td>01/02/2015-01/03/2015</td>
		<!-- 		<td>Sales Performance</td>	-->	
				<td>Paid on 03/02/2015</td>
				<td align="center">
				
					<button type="button" class="btn"
							onClick="toggle_visibility('foo');">
							<img src="../resources/image/show_details.png" alt="Tutorials Point"
								width="20" height="21" border="0" />
						</button>
				
						</td>
			</tr>
			<tr>
				<td>02/03/2014-01/04/2015</td>
		<!--		<td>Max Sale of Computers</td>	-->	
				<td>Paid on 03/03/2015</td>
				<td align="center">
					<button type="button" class="btn"
							onClick="toggle_visibility('foo');">
							<img src="../resources/image/show_details.png" alt="Tutorials Point"
								width="20" height="21" />
						</button>
						</td>
			</tr>
		</table>
<div id="foo" style="display: none">
						<table border=1 align="center">
			<thead>
				<tr>
					<td><a href='#'>Rule Name</a></td>
					<td><a href='#'>Calculation Star tDate</a></td>
					<td><a href='#'>Calculation End Date</a></td>
					<td><a href='#'>Qualifying Flag</a></td>
					<td><a href='#'>Split Amount</a></td>
					<td><a href='#'>Compensation Amount</a></td>
					<td><a href='#'>Show Details</a></td>	
				</tr>
			</thead>
			<tbody><tr>
					<td><a href="../../pages/compRule/CompRuleSimple.html">Sales Performance</a></td>	
					
					<td>02/02/2015</td>        		
					<td>07/02/2015</td>
					<td>true</td>
					<td>45%</td>
					<td>$56.89</td>
					<td align="center">
					<button type="button" class="btn"
							onClick="toggle_visibility('foo1');">
							<img src="../resources/image/show_details.png" alt="Tutorials Point"
								width="20" height="21" border="0" />
						</button>
						</td>
					</tr>
					<tr>
					<td><a href="../../pages/compRule/CompRuleSimple.html">Sales Performance</a></td>	
					<td>09/02/2015</td>        		
					<td>14/02/2015</td>
					<td>false</td>
					<td>45%</td>
					<td>0</td>
					<td align="center">
					<button type="button" class="btn"
							onClick="toggle_visibility('foo2');">
							<img src="../resources/image/show_details.png" alt="Tutorials Point"
								width="20" height="21" />
						</button>
						</td>
					</tr>
					<tr>
					<td><a href="../../pages/compRule/CompRuleSimple.html">Sales Performance</a></td>	
					<td>16/02/2015</td>        		
					<td>21/02/2015</td>
					<td>true</td>
					<td>45%</td>
					<td>$63.12</td>
					<td align="center">
					<button type="button" class="btn"
							onClick="toggle_visibility('foo1');">
							<img src="../resources/image/show_details.png" alt="Tutorials Point"
								width="20" height="21"  />
						</button>
						</td>
					</tr>
					<tr>
					<td><a href="../../pages/compRule/CompRuleSimple.html">Sales Performance</a></td>	
					<td>23/02/2015</td>        		
					<td>28/02/2015</td>
					<td>false</td>
					<td>45%</td>
					<td>0</td>
					<td align="center">
					<button type="button" class="btn"
							onClick="toggle_visibility('foo2');">
							<img src="../resources/image/show_details.png" alt="Tutorials Point"
								width="20" height="21" border="0" />
						</button>
						</td>
					</tr>
					<tr>
					<td><a href="../../pages/compRule/CompRuleSimpleRank.html">Max Sale of Computers</a></td>
					<td>01/02/2015</td>            		
					<td>28/02/2015</td>
					<td>true</td>
					<td></td>
					<td>$987.5</td>
					<td align="center">
					<button type="button" class="btn"
							onClick="toggle_visibility('foo3');">
							<img src="../resources/image/show_details.png" alt="Tutorials Point"
								width="20" height="21" />
						</button>
						</td>
					</tr>
					<tr>
					<td><a href="../../pages/compRule/CompRuleSimpleRank.html">Highest Sale of Hard Disk</a></td>
					<td>01/04/2015</td>            		
					<td>30/04/2015</td>
					<td>false</td>
					<td></td>
					<td>0</td>
					<td align="center">
					<button type="button" class="btn"
							onClick="toggle_visibility('foo4');">
							<img src="../resources/image/show_details.png" alt="Tutorials Point"
								width="20" height="21"/>
						</button>
						</td>
					</tr>
			</tbody>
		</table>
		</div>
	<div id="foo1" style="display: none">
						<table border=1 align="center">
			<thead>
				<tr>
					<td><a href='#'>Product ID</a></td>
					<td><a href='#'>Quantity</a></td>
					<td><a href='#'>Rate</a></td>
					<td><a href='#'>Discount Percentage</a></td>
					<td><a href='#'>Duty Percentage</a></td>
					<td><a href='#'>Split Quantity</a></td>
					<td><a href='#'>Split Amount</a></td>
				</tr>
			</thead>
			<tbody><tr>
					<td><a href="#">0987</a></td>	
					
					<td>20</td>        		
					<td>$580</td>
					<td>5%</td>
					<td>2%</td>
					<td>45%</td>
					<td>$13.98</td>
					</tr>
					<tr>
					<td><a href="#">4981</a></td>	
					<td>15</td>        		
					<td>$800</td>
					<td>10%</td>
					<td>3%</td>
					<td>60%</td>
					<td>$37</td>
					</tr>
					<tr>
					<td><a href="#">3080</a></td>	
					<td>20</td>        		
					<td>$580</td>
					<td>8%</td>
					<td>2%</td>
					<td>20%</td>
					<td>$6.12</td>
					</tr>
			</tbody>
		</table>
		</div>	
		<div id="foo2" style="display: none">
						<table border=1 align="center">
			<thead>
				<tr>
					<td><a href='#'>Product ID</a></td>
					<td><a href='#'>Quantity</a></td>
					<td><a href='#'>Rate</a></td>
					<td><a href='#'>Discount Percentage</a></td>
					<td><a href='#'>Duty Percentage</a></td>
					<td><a href='#'>Split Quantity</a></td>
					<td><a href='#'>Split Amount</a></td>		
				</tr>
			</thead>
			<tbody><tr>
					<td><a href="#">0987</a></td>	
					
					<td>20</td>        		
					<td>$580</td>
					<td>5%</td>
					<td>2%</td>
					<td>45%</td>
					<td>0</td>
					</tr>
					<tr>
					<td><a href="#">4981</a></td>	
					<td>15</td>        		
					<td>$800</td>
					<td>10%</td>
					<td>3%</td>
					<td>60%</td>
					<td>0</td>
					</tr>
					<tr>
					<td><a href="#">3080</a></td>	
					<td>20</td>        		
					<td>$580</td>
					<td>8%</td>
					<td>2%</td>
					<td>20%</td>
					<td>0</td>
					</tr>
			</tbody>
		</table>
		</div>	
		<div id="foo3" style="display: none">
						<table border=1 align="center">
			<thead>
				<tr>
					<td><a href='#'>Product ID</a></td>
					<td><a href='#'>Quantity</a></td>
					<td><a href='#'>Rate</a></td>
					<td><a href='#'>Discount Percentage</a></td>
					<td><a href='#'>Duty Percentage</a></td>
					<td><a href='#'>Rank Based Output</a></td>
				</tr>
			</thead>
			<tbody><tr>
					<td><a href="#">0945</a></td>	
					
					<td>20</td>        		
					<td>$5800</td>
					<td>5%</td>
					<td>2%</td>
					<td>eligible</td>
					</tr>

			</tbody>
		</table>
		</div>	
		<div id="foo4" style="display: none">
						<table border=1 align="center">
			<thead>
				<tr>
					<td><a href='#'>Product ID</a></td>
					<td><a href='#'>Quantity</a></td>
					<td><a href='#'>Rate</a></td>
					<td><a href='#'>Discount Percentage</a></td>
					<td><a href='#'>Duty Percentage</a></td>
					<td><a href='#'>Rank Based Output</a></td>
				</tr>
			</thead>
			<tbody><tr>
					<td><a href="#">0167</a></td>	
					
					<td>45</td>        		
					<td>$5800</td>
					<td>5%</td>
					<td>2%</td>
					<td>Not eligible</td>
					</tr>

			</tbody>
		</table>
		</div>	
		<div id="tableFooter">
		</div>
		<h1 align="center">	<script>
				$("#tableFooter").load("components/tableFooter.html");
				showCancel();
			</script> </h1>
	<script type="text/javascript">
		function toggle_visibility(id) {
			var e = document.getElementById(id);
			if (e.style.display == 'block')
				e.style.display = 'none';
			else
				e.style.display = 'block';
		}
	</script>	
	 <br><br><br>
	 </div>
	 <center><button onClick="window.location.href=''">Cancel</button></center>
	
</tiles:putAttribute>
</tiles:insertDefinition>
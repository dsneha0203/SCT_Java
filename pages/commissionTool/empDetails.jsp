
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">

<head>
<title>EmployeeDetails</title>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
</head>

<style>

table {
	width: 750px;
	border: 1px solid #AAA;
	table-layout: auto;
	border-collapse: collapse;
}

table td, table th {
	border: 1px solid #DDD;
	text-align: left;
	padding: 5px 15px 5px 15px;
	-webkit-box-sizing: border-box;
	box-sizing: border-box;
}
#btn_save_role , #btn_save_manager{
	cursor: pointer;
	
}
</style>
		<script>
		
function openWindow1(){
	window.open('/CommissionTool/selectRole' , '' , 'width=1000,height=500,scrollbars=yes');
	popup.focus();
	}
	
function openWindow2(){
	window.open('/CommissionTool/showAllRoles/${sessionScope.empDetailsId}' , '' , 'width=1400,height=700,scrollbars=yes');
	}

function openWindow3(){
	window.open('/CommissionTool/showAllManagers/${sessionScope.empDetailsId}' , '' , 'width=1400,height=700,scrollbars=yes');
	}
	
function openWindow4(){
	window.open('/CommissionTool/selectManager' , '' , 'width=1400,height=700,scrollbars=yes');
	}


function rowAdded(rowElement) {
	//clear the imput fields for the row
	$(rowElement).find("input").val('');
	//may want to reset <select> options etc

	//in fact you may want to submit the form
	saveNeeded();
}
function rowRemoved1(rowElement) {
	saveNeeded();
}

function saveNeeded() {
	$('#submit').css('color', 'red');
	$('#submit').css('font-weight', 'bold');
	if ($('#submit').val().indexOf('!') != 0) {
		$('#submit').val('!' + $('#submit').val());
	}
}

function beforeSubmit1() {
	alert('submitting....');
	return true;
}

$(document)
		.ready(
				function() {
					var config = {
						rowClass : 'person',
						addRowId : 'addPerson1',
						removeRowClass : 'removePerson1',
						formId : 'targetListForm',
						rowContainerId : 'targetListContainer',
						indexedPropertyName : 'targetList',
						indexedPropertyMemberNames : 'targetName,startDate,terminationDate,frequency,value',
						rowAddedListener : rowAdded,
						rowRemovedListener : rowRemoved1,
						beforeSubmit : beforeSubmit1
					};
					new DynamicListHelper(config);
				});



function enable_btn_role(val){
	
	if(val != ""){
	document.getElementById("btn_save_role").style.visibilty='visible';
	}
}

function enable_btn_manager(val){
	
	if(val != ""){
	document.getElementById("btn_save_manager").style.visibilty='visible';
	}
}


function checkDate(){
	var start= document.getElementById("start").value;
    var end = document.getElementById("end").value;
    if(start == ""){
    	alert("Enter the start date first!");
    	 document.getElementById("end").value="";
    }else{
    if(end<start && end!=""){
        alert("Termination date cannot be earlier than start date");
        document.getElementById("end").value="";
     }
    }
	//alert("function called! ");
}


</script>

		
			<div style="height: 580px; overflow: auto;">


				<h1 align="center">Employee Details</h1>
				

				<table>
					<tr>
						<td>Name:</td>
						<td><input type="hidden" name="empName" value="${emp.employeeName}">${emp.employeeName}</td>
					</tr>
					<tr>
						<td>Start date:</td>
						<td>${emp.startDate}</td>
					</tr>
					<tr>
						<td>Termination date:</td>
						<td>${emp.terminationDate}</td>
					</tr>
					<tr>
					<tr>
						<td><h2>Current Role:</h2></td>
						<td>${sessionScope.roleName}</td>
					</tr>
					<tr>
						<td>Change current role to</td>
						<td><form action="/CommissionTool/submitNewRole/${sessionScope.empDetailsId}"  method="post">
						<input type="text" name="selectRole" id="roleName" required>
						<button type="button" class="btn btn-default" onClick="openWindow1();" >
								<img src="../resources/image/search1.png" alt="Tutorials Point"
									width="30" height="31" border="0" />
							</button>
							&nbsp;<input type="submit" value="Save" id="btn_save_role"  >
							</form></td>
					</tr>
					<tr>
						<td>Show History</td>
						<td>
							<button type="button" class="btn btn-default" onClick="openWindow2()">
								<img src="../resources/image/backhistory.png" alt="Tutorials Point"
									width="28" height="35" border="0" />
							</button>
						</td>
					</tr>

					<tr>
						<td><h2>Current Manager:</h2></td>
						<td>${sessionScope.managerName}</td>
					</tr>
					<tr>
						<td>Change current manager to</td>
						<td><form action="/CommissionTool/submitCurrentManager/${sessionScope.empDetailsId}"  method="post">
						<input type="text" name="currentManager" id="managerId" required> 
						<button type="button" class="btn btn-default"
								onClick="openWindow4();" >
								<img src="../resources/image/search1.png" alt="Tutorials Point"
									width="30" height="31" border="0" />
							</button>
							&nbsp;<input type="submit" value="Save" id="btn_save_manager" ></form>
							</td>
					</tr>
					<tr>

						<td>Show History</td>
						<td>
							<button type="button" class="btn btn-default"
								onClick="openWindow3();">
								<img src="../resources/image/backhistory.png" alt="Tutorials Point"
									width="28" height="35" border="0" />
							</button>
						</td>

					</tr>

					<tr>
						<td colspan="2">

							<table>
								<tr>
									<td><h3>Set Targets</h3>&nbsp;&nbsp;</td>
									<td>View&nbsp;&nbsp;<a href="/CommissionTool/empDetails/${sessionScope.empDetailsId}">All</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
										href="/CommissionTool/empDetailsCurrentTargets/${sessionScope.empDetailsId}">Current</a></td>
								</tr>
							</table>
							<form:form action="/CommissionTool/submitEmpDetails/${sessionScope.empDetailsId}"
								modelAttribute="targetListContainer" method="post"
								id="targetListForm">
								<input type="hidden" name="currentRoleName" value="${sessionScope.roleName}"/>
							<table id="targetListForm">
								<thead>
									<tr>
										<td>Parameter Name</td>
										<td>Start Date</td>
										<td>End Date</td>
										<td>Repeat Frequency</td> 
										<td>Value</td>
									</tr>
								</thead>
								<tbody id="targetListContainer">

									<c:forEach items="${targetDetails}" var="target">

										<tr class="person">
										
											<td><select name="targetList[].targetName">
													<option value="${target.targetDefinition.displayName}">---${target.targetDefinition.displayName}---</option>

													<c:forEach items="${targetDefinition}" var="tar">
														<option value="${tar.displayName}">
															<c:out value="${tar.displayName}" />
														</option>
													</c:forEach>
											</select></td>

										<fmt:formatDate var="startDate" value="${target.startDate}" pattern="yyyy-MM-dd" />
								<td ><input type="date"  name="targetList[].startDate"  value="${startDate}" 
								 required></td>
								
								 <fmt:formatDate var="endDate" value="${target.terminationDate}" pattern="yyyy-MM-dd" />
								
								<td><input type="date"    name="targetList[].terminationDate" 
									 value= "${endDate}" required></td>				
													
									
										<td>
										<select name="targetList[].frequency"  required> 
											<option value="${target.frequency.frequencyName}">--${target.frequency.frequencyName}--</option>
										<c:forEach items="${listfrequency}" var="freq">
											<option value="${freq.frequencyName}" />
											<c:out value="${freq.frequencyName}" />
										</c:forEach>
									</select>
										</td>
										
											<td><input type="number" name="targetList[].value"
												value="${target.value}" size="10"></td>


											<td><a href="#" class="removePerson1">&nbsp;Remove</a></td>
											
										</tr>
									</c:forEach>

									<tr class="person defaultRow">
										<td><select name="targetList[].targetName" required>
										<option value=""><c:out value="----Select----" /></option>
												<c:forEach items="${targetDefinition}" var="tar">
													<option value="${tar.displayName}">
														<c:out value="${tar.displayName}" />
													</option>
												</c:forEach>
										</select></td>

																				
								<td><input type='date' name="targetList[].startDate" 
								id="start" oninput="checkDate()" required></td> 
								
								  
								  
								 
							 <td ><input  type="date"  name="targetList[].terminationDate" 
							 id="end" oninput="checkDate()"
							 required></td> 			
											
							 	
										<td>
										<select name="targetList[].frequency"  required> 
											<option value="">--Select--</option>
										<c:forEach items="${listfrequency}" var="freq">
											<option value="${freq.frequencyName}" />
											<c:out value="${freq.frequencyName}" />
										</c:forEach>
									</select>
										</td>
										
										<td><input type="number" name="targetList[].value"
											value="0" size="10"></td>
										<td><a href="#" class="removePerson1">Remove</a></td>

									</tr>

								</tbody>

							</table> <a href="#" id="addPerson1">Add</a>&nbsp;&nbsp; <a href="?f=">Reset
								List</a>

						</td>
					</tr>
				</table>
				<br />
				<div align="center">
					<input type="submit" value="Update">
					<a
						href="/CommissionTool/employeeList"> <input type="button"
						value="Cancel" /></a>
				</div>
</form:form>
			</div>
		


	</tiles:putAttribute>
</tiles:insertDefinition>
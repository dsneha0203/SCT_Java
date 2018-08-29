<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">
<head>
<title>RoleDetails</title>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
</head>
<style>
body{
	overflow: auto;
}
table {
	width: 700px;
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
#btn_search{
	border:none;
	background-color: white;
}
</style>




	
<script type="text/javascript">
	
/*-------------------------*/
function rowAdded(rowElement) {
				//clear the input fields for the row
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
/*-------------------------*/

			function openWindow1(){
				window.open('/CommissionTool/selectRole' , '' , 'width=1000,height=500,scrollbars=yes');
				popup.focus();
				}
	</script>

		<h1 align="center">Role Details</h1>

		
			<div align="center">
			
			
				<table>
					<tr>
						<td><b>Role Name:</b></td>
						<td><input type="hidden" name="roleName"
							value="${roleDetails.roleName}">${roleDetails.roleName}</td>
					</tr>
					<tr>
						<td><b>Description:</b></td>
						<td><textarea rows="4" cols="50" name="description">${roleDetails.description}</textarea></td>
					</tr>
					<tr>
						<td><b>Reports To:</b></td>
						<!--<td>${roleDetails.reportsTo.roleName}</td>  -->
						<td><form action="/CommissionTool/submitNewRoleReportsTo/${roleDetails.id}"  method="post">
						<input type="hidden" name="roleName"
							value="${roleDetails.roleName}">
						<input type="text" value="${roleDetails.reportsTo.roleName}" id="roleName" name="reportsToRoleName">&nbsp;&nbsp;
						<button type="button" id="btn_search" onClick="openWindow1();" >
								<img src="../resources/image/search.png" alt=""
									width="30" height="31" border="0" />
						</button>
						&nbsp;<input type="submit" value="Save" id="btn_save_role"  >
							</form>
						</td>
					</tr>
					<tr>
						<td colspan="2">

							<table >
								<tr>
									<td><h3>Set Targets</h3>&nbsp;&nbsp;</td>
									<td>View&nbsp;&nbsp;<a href="/CommissionTool/roleDetails/${roleDetails.id}">All</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
										href="/CommissionTool/roleDetailsCurrentTargets/${roleDetails.id}">Current</a></td>
								</tr>
							</table>
							<form:form action="/CommissionTool/submitRoleDetails/${roleDetails.id}"
			modelAttribute="targetListContainer" method="post"
			id="targetListForm">
			<input type="hidden" name="roleName"
							value="${roleDetails.roleName}">
							<table id="target_table">
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
								<td ><input type="date"  name="targetList[].startDate" id="start" value="${startDate}" required></td>
								
								 <fmt:formatDate var="endDate" value="${target.terminationDate}" pattern="yyyy-MM-dd" />
								
								<td><input type="date"  height="0.10"  name="targetList[].terminationDate"  id="end" value= "${endDate}" required></td>				
											
											
									 <td>
										<select name="targetList[].frequency"  required> 
											<option value="${target.frequency.frequencyName}">--${target.frequency.frequencyName}--</option>
										<c:forEach items="${listfrequency}" var="freq">
											<option value="${freq.frequencyName}" />
											<c:out value="${freq.frequencyName}" />
										</c:forEach>
									</select>
										</td>
										
										<td><input type="number"
											name="targetList[].value" value="${target.value}" size="10"></td>
										

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
											
											
											<td><input type="number"
											name="targetList[].value" value="0" size="10" required></td>
										<td><a href="#" class="removePerson1">Remove</a></td>

									</tr>

								</tbody>

							</table> <a href="#" id="addPerson1">Add</a>&nbsp;&nbsp; <a href="?f=">Reset
								List</a>
						
						</td>
					</tr>
				</table>
				
				<br /> <input type="submit" value="Update">
				</form:form>
				
			</div>
			
		
	</tiles:putAttribute>
</tiles:insertDefinition>
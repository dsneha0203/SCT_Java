<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>


<tiles:insertDefinition name="defaultTemplate">
		<tiles:putAttribute name="body">
<title>Sales Commissioning Tool</title>



<link rel="stylesheet" href="../resources/css/jquery-ui.css" />
<link rel="stylesheet" href="../resources/css/style.css" />

<script src="../resources/js/jquery.1.12.1.min.js"></script>
<script src="../resources/js/jquery-ui.js"></script>

<script src="../resources/js/jquery.datePicker-2.1.1.js"></script>

<style>
table {
	width: 750px;
	border: 1px solid #AAA;
	table-layout: auto;
	border-collapse: collapse;
}
table td, table th {
	text-align: left;
	padding: 5px 15px 5px 15px;
	-webkit-box-sizing: border-box;
	box-sizing: border-box;
}
</style>



<script type="text/javascript">
	$(function() {
		$('input').filter('.datepicker').datepicker({
			changeMonth : true,
			changeYear : true,
			depth : "year",
			showOn : 'button',
			buttonImage : '../resources/image/calendar1.png',
			buttonImageOnly : true
		});
	});
	</script>
	
	<script>
	
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
							rowClass : 'qualiClause',
							addRowId : 'addPerson1',
							removeRowClass : 'removePerson1',
							formId : 'personListForm',
							rowContainerId : 'personListContainer1',
							indexedPropertyName : 'personList',
							indexedPropertyMemberNames : 'fieldName,condition,conditionValue,value',
							rowAddedListener : rowAdded,
							rowRemovedListener : rowRemoved1,
							//beforeSubmit : beforeSubmit1
						};
						new DynamicListHelper(config);
					});
	/*-----------------------------------------------------------------------------------------*/
	
			function rowAdded(rowElement) {
				//clear the imput fields for the row
				$(rowElement).find("input").val('');
				//may want to reset <select> options etc
				//in fact you may want to submit the form
				saveNeeded();
			}
			function rowRemoved(rowElement) {
				saveNeeded();
			}
			function saveNeeded() {
				$('#submit').css('color', 'red');
				$('#submit').css('font-weight', 'bold');
				if ($('#submit').val().indexOf('!') != 0) {
					$('#submit').val('!' + $('#submit').val());
				}
			}
			function beforeSubmit() {
				alert('submitting....');
				return true;
			}
			$(document)
					.ready(
							function() {
								var config = {
									rowClass : 'beneficiary',
									addRowId : 'addPerson',
									removeRowClass : 'removePerson',
									formId : 'personListForm',
									rowContainerId : 'personListContainer',
									indexedPropertyName : 'personList1',
									indexedPropertyMemberNames : 'parameterName,parameterValue',
									rowAddedListener : rowAdded,
									rowRemovedListener : rowRemoved,
									beforeSubmit : beforeSubmit
								};
								new DynamicListHelper(config);
							});
			
	
	
</script>

<div style="height: 580px; overflow: auto;">
	

			<form:form action="/CommissionTool/updateSplitRule" method="post" id="personListForm">
				
				<h1 align="center">Split Rule Details</h1>
				<div align="center">
					<table border="1">
						<tr>
							<td>Rule name:</td>
							<td><input type="text" size=50 value="${splitRuleDetails.splitRuleName}" required name="splitRuleName"></input></td>
							
						</tr>
						<tr>
							<td valign="top">Description:</td>
							<td valign="top"><textarea rows="4" cols="50"
									style="vertical-align: top" name="splitRuleDesc">${splitRuleDetails.description}</textarea></td>
							
						</tr>
						<tr>
							<td>Split Plan validity :</td>
							<td>From&nbsp; &nbsp;
							<!--  <input class="datepicker" type="text"
								name="startDate" height="0.10" value="${splitRuleDetails.startDate}" required > -->
								<fmt:formatDate var="fmtStartDate" value="${splitRuleDetails.startDate}" pattern="yyyy-MM-dd" />
								<input type="date" name="startDate" value="${fmtStartDate}" required>
								&nbsp;&nbsp;&nbsp;
								To&nbsp;&nbsp;&nbsp;
								<!--  <input class="datepicker"
								type="text" name="endDate"  height="0.10" value="${splitRuleDetails.endDate}" required name="">-->
								<fmt:formatDate var="fmtEndDate" value="${splitRuleDetails.endDate}" pattern="yyyy-MM-dd" />
								<input type="date" name="endDate" value="${fmtEndDate}" required>
								
							</td>
						</tr>
						<tr>
							<td valign="top">Applies when :</td>
							<td>

								<table border="1">

									<tr>
									<tbody id="personListContainer1">
										

										
											<tr class="qualiClause defaultRow">
												<td>&nbsp;Field Name&nbsp;<select
													name="personList[].fieldName">
														<!--<c:forEach items="${listRule2}" var="rule">
															<option value="${rule.displayName}">
																<c:out value="${rule.displayName}" />
															</option>
														</c:forEach>-->
														<c:forEach items="${fieldNameList}" var="fieldName">
													<option value="${fieldName}">
															<c:out value="${fieldName}" />
														</option>
													</c:forEach>
												</select></td>

												<!--  <td>Not&nbsp;<input type="text"
												name="personList[].condition" value="${quali.notFlag}"
												size="2"></td>-->
												<td>&nbsp;Not&nbsp;
												<select name="personList[].condition" required>
												<option value="${quali.notFlag}">---${quali.notFlag}---</option>
												<option value="True">True</option>
												<option value="False">False</option>
												</select></td>

												<td>&nbsp;Condition&nbsp;<select
													name="personList[].conditionValue">
													<!--<c:forEach
															items="${listRule3}" var="rule">
															<option value="${rule.conditionValue}">
																<c:out value="${rule.conditionValue}" />
															</option>
														</c:forEach>-->
														<c:forEach items="${condNameList}" var="condName">
													<option value="${condName}">
															<c:out value="${condName}" />
														</option>
													</c:forEach>
														
													</select>
												<td>&nbsp;Value&nbsp;<input type="text"
													name="personList[].value"></td>
												<td><a href="#" class="removePerson1">Remove</a></td>

											</tr>
										
									</tbody>
								</table> <a href="#" id="addPerson1">Add</a>&nbsp;&nbsp; <a href="?f=">Reset
									List</a>

							</td>
						</tr>
						
						<tr>
						<td>Beneficiary roles :</td>
						<td>

							<table border="1">
								<tbody id="personListContainer">
								<tr><td>Beneficiary</td><td>Split Percentage</td></tr>
											
								
								  <tr class="beneficiary defaultRow">
										<td><select name="personList1[].parameterName" required>
									<option value="" selected>-Select-</option>
									<option value="Admin">Admin</option>
									<option value="Sales Representative" >Sales Representative</option>
									<option value="Supporting Engineer">Supporting Engineer</option>
									<option value="Manager">Manager</option>
									<option value="Second Level Manager">Second Level Manager</option>
										</select></td>
										
										
										<td><input type="number"
											name="personList1[].parameterValue" size="4" value="" required/></td>

										<td><a href="#" class="removePerson">Remove</a></td>
									</tr> 
									
								

								</tbody>
							</table> <a href="#" id="addPerson">Add</a>&nbsp;&nbsp; <a
							href="?f=">Reset List</a>

						</td>
					</tr>
					</table>
				</div><br/>
				<div align=center>
					<input type="submit" value="Create"></input>

				</div>

			</form:form>
</div>
		</tiles:putAttribute>
	</tiles:insertDefinition>
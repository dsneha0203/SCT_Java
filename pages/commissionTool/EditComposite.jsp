<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>



<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">

		<title>CompRuleDetails</title>

		<style>
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
</style>



		<script type="text/javascript">
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

			$(document).ready(function() {
				var config = {
					rowClass : 'person',
					addRowId : 'addPerson1',
					removeRowClass : 'removePerson1',
					formId : 'personListForm1',
					rowContainerId : 'personListContainer2',
					indexedPropertyName : 'personList',
					indexedPropertyMemberNames : 'simpRule',
					rowAddedListener : rowAdded,
					rowRemovedListener : rowRemoved,
					beforeSubmit : beforeSubmit
				};
				new DynamicListHelper(config);
			});

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
								/*var config = {
									rowClass : 'ruleParameter',
									addRowId : 'addPerson',
									removeRowClass : 'removePerson',
									formId : 'personListForm1',
									rowContainerId : 'personListContainer',
									indexedPropertyName : 'personList1',
									indexedPropertyMemberNames : 'parameterName,parameterValue',
									rowAddedListener : rowAdded,
									rowRemovedListener : rowRemoved,
									beforeSubmit : beforeSubmit
								};
								new DynamicListHelper(config);*/
								
								
								 var rad_fixed = document.getElementById("compensationTypeFixed");
									
									if(rad_fixed.checked){
									document.getElementById("fixedCompValue").disabled=false;
									document.getElementById("fixedCompValue").required=true;
									document.getElementById("compensationFormula").disabled=true;
									document.getElementById("compensationParameter").disabled=true;	
									
									}		
								
									var rad_variable = document.getElementById("compensationTypeVariable");
									
									
									
									if(rad_variable.checked){
										document.getElementById("fixedCompValue").disabled=true;
										document.getElementById("fixedCompValue").required=false;
										document.getElementById("compensationFormula").disabled=false;
										document.getElementById("compensationFormula").required=true;
										document.getElementById("compensationParameter").disabled=false;	
										document.getElementById("compensationParameter").required=true;
									}
							});

			var count = "1";

			function addRow(in_tbl_name) {
				var tbody = document.getElementById(in_tbl_name);

				var row = document.createElement("TR");

				var td1 = document.createElement("TD")
				var RuleArray = new Array();
				RuleArray = "${listRule}";
				var len = RuleArray.length;

				var strHtml1 = "<SELECT NAME=\"Rule\"><OPTION VALUE=\"Null\">Null<OPTION VALUE=\"Null\">Null<OPTION VALUE=\"Null\">Null</SELECT>";

				td1.innerHTML = strHtml1.replace(/!count!/g, count);

				var td2 = document.createElement("TD")
				var strHtml2 = "<INPUT TYPE=\"Button\" CLASS=\"Button\" onClick=\"delRow()\" VALUE=\"Delete Row\">";
				td2.innerHTML = strHtml2.replace(/!count!/g, count);

				row.appendChild(td1);
				row.appendChild(td2);

				count = parseInt(count) + 1;

				tbody.appendChild(row);
			}
			function delRow() {
				var current = window.event.srcElement;
				//here we will delete the line
				while ((current = current.parentElement)
						&& current.tagName != "TR")
					;
				current.parentElement.removeChild(current);
			}

			function dateGenerate() {
				var date = new Date(), dateArray = new Array(), i;
				curYear = date.getValue();
				for (i = 0; i < 5; i++) {
					dateArray[i] = curYear + i;
				}
				return dateArray;
			}

			function addSelect(divname) {
				var newDiv = document.createElement('div');
				var html = '<select>', dates = dateGenerate(), i;
				for (i = 0; i < dates.length; i++) {
					html += "<option value='"+dates[i]+"'>" + dates[i]
							+ "</option>";
				}
				html += '</select>';
				newDiv.innerHTML = html;
				document.getElementById(divname).appendChild(newDiv);
			}
		</script>

		<form:form action="/CommissionTool/submitCompRule"
			modelAttribute="personListContainer" method="post"
			id="personListForm1">
			<div style="height: 580px; overflow: auto;">

				<div align="center">
					<h1>Compensation Rule Details</h1>


					<table border="1">
						<tr>
							<td><b>Rule Name:</b></td>
							<td><input type="hidden" name="id" value="${listRule1.id}">
								<input type="text" name="ruleName" size=50
								value="${listRule1.ruleName}"></td>
						</tr>
						<tr>
							<td><b>Description:</b></td>
							<td><textarea name="description" rows="4" cols="50">${listRule1.description}</textarea></td>
						</tr>
						<tr>
							<td><b>Rule type:</b></td>
							<td>Composite<input type="hidden" name="ruleType"
								value="Composite">
						</tr>
						<tr>
							<td><b>Rules connected as: </b></td>
							<td><input type="radio" name="connectionType" id="allConType"
								${listRule1.connectionType=='All'?'checked':''}
								value="All">&nbsp;All&nbsp; <input
								type="radio" name="connectionType" id="anyConType"
								${listRule1.connectionType=='Any'?'checked':''}
								value="Any">&nbsp;Any&nbsp;</td>
						</tr>



						<tr>
							<td><b>List of Rules</b></td>
							<td>

								<table>
									<tbody id="personListContainer2">
										<c:forEach items="${rName}" var="name">

											<tr class="person">
												<td><select name="personList[].simpRule">

														<option value="${name.ruleName}">---${name.ruleName}---</option>
														<c:forEach items="${listSimpRule}" var="rule">
															<option value="${rule.ruleName}">
																<c:out value="${rule.ruleName}" />
															</option>
														</c:forEach>
												</select></td>

												<td><a href="#" class="removePerson1">&nbsp;Remove</a></td>
											</tr>
										</c:forEach>

										<c:if test="${personListContainer2.personList.size() == 0}">
											<tr class="person defaultRow">
												<td><select name="personList[].simpRule"><c:forEach
															items="${listSimpRule}" var="rule">
															<option value="${rule.ruleName}">
																<c:out value="${rule.ruleName}" />
															</option>
														</c:forEach>
												</select></td>

												<td><a href="#" class="removePerson1">&nbsp;Remove</a></td>

											</tr>
										</c:if>

									</tbody>

								</table> <a href="#" id="addPerson1">Add&nbsp;</a>&nbsp;&nbsp; <a
								href="?f=">&nbsp;Reset List</a>
							</td>
						</tr>

						<!-- <tr>
							<td><b>Rule Parameter</b></td>
							<td>
								<table>
									<tbody id="personListContainer">
										<c:forEach items="${parList}" var="par">

											<tr class="ruleParameter">
												<td>Parameter Name&nbsp;<input type="text"
													name="personList1[].parameterName"
													value="${par.parameterName}" /></td>
												<td>Parameter Value&nbsp;<input
													type="text" name="personList1[].parameterValue"
													value="${par.parameterValue}" /></td>

												<td><a href="#" class="removePerson">&nbsp;Remove</a></td>
											</tr>
										</c:forEach>

										<c:if test="${personListContainer.personList1.size() == 0}">
											<tr class="ruleParameter defaultRow">
												<td>Parameter Name&nbsp;<input type="text"
													name="personList1[].parameterName" value="" /></td>
												<td>Parameter Value&nbsp;<input type="text"
													name="personList1[].parameterValue" value="" /></td>

												<td><a href="#" class="removePerson">Remove</a></td>
											</tr>
										</c:if>

									</tbody>
								</table> <a href="#" id="addPerson">Add Parameters</a>&nbsp;&nbsp; <a
								href="?f=">Reset List</a>


							</td>

						</tr> -->

						<!-- 	
				<tr>
					<c:forEach items="${parList}"
										var="par">
											<td>Parameter Name&nbsp;<input type="text" value="${par.parameterName}"/></td>
											<td>&nbsp;Parameter Value&nbsp;<input type="text" value="${par.parameterValue}" /></td>
									
									</c:forEach>
					</tr>
			 -->
						<tr>
							<td><b>Compensation:</b></td>

							<td>
								<table>
									<tr>
										<td><input type="radio" name="compensationType"
											value="Fixed"  id="compensationTypeFixed" onchange="enableFixed()"
											${listRule1.compensationType=='Fixed'?'checked':''}>Fixed</td>
										<td><input type="number" size="20" name="fixedCompValue"
											value="${listRule1.fixedCompValue}"  id="fixedCompValue" required></input></td>
									</tr>
									<tr>
										<td valign="top"><input type="radio"
											name="compensationType" value="Variable" id="compensationTypeVariable"
											${listRule1.compensationType=='Variable'?'checked':''} onchange="enableVariable()">Variable&nbsp;
										</td>
										<td>Apply formula <input type="text" size="20"
											name="compensationFormula"
											value="${listRule1.compensationFormula}" 
											id="compensationFormula" ><br /> <br />
											&nbsp;&nbsp;&nbsp;Parameters <input type="text" size="20"
											name="compensationParameter"
											value="${listRule1.compensationParameter}" 
											id="compensationParameter" >
										</td>
									</tr>
								</table>
							</td>
						</tr>


					</table>
					
					<script>
				function enableFixed(){
					
					var rad_fixed = document.getElementById("compensationTypeFixed");
					
					if(rad_fixed.checked){
					document.getElementById("fixedCompValue").disabled=false;
					document.getElementById("fixedCompValue").required=true;
					document.getElementById("compensationFormula").disabled=true;
					document.getElementById("compensationParameter").disabled=true;	
					document.getElementById("compensationFormula").value="";
					document.getElementById("compensationParameter").value="";	
					
					}		
				}
				function enableVariable(){
					
				
					var rad_variable = document.getElementById("compensationTypeVariable");
					
				
					
					if(rad_variable.checked){
						document.getElementById("fixedCompValue").disabled=true;
						document.getElementById("fixedCompValue").value="0";
						document.getElementById("fixedCompValue").required=false;
						document.getElementById("compensationFormula").disabled=false;
						document.getElementById("compensationFormula").required=true;
						document.getElementById("compensationParameter").disabled=false;	
						document.getElementById("compensationParameter").required=true;
					}
				}
						</script>
					<br />
					<div align="center">
						<input type="submit" value="Update"> <a
							href="/CommissionTool/ruleList"> <input type="button"
							value="Cancel" /></a>
					</div>


				</div>
			</div>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>
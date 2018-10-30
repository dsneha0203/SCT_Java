<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>


<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">

		<title>SimpleRuleDetails</title>


		<style>
table {
    width: 100%;
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
			$('#vehicleChkBox').change(function() {
				if ($(this).attr('checked')) {
					$(this).val('TRUE');
				} else {
					$(this).val('FALSE');
				}
			});

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
							addRowId : 'addPersonNonAgg',
							removeRowClass : 'removePersonNonAgg',
							formId : 'personListForm',
							rowContainerId : 'personListContainer1',
							indexedPropertyName : 'personListNonAgg',
							indexedPropertyMemberNames : 'aggFuncName,fieldName,condition,conditionValue,value',
							rowAddedListener : rowAdded,
							rowRemovedListener : rowRemoved1,
							//beforeSubmit : beforeSubmit1
						};
						new DynamicListHelper(config);
					});
			$(document)
			.ready(
					function() {
						var config = {
							rowClass : 'person1',
							addRowId : 'addPerson1',
							removeRowClass : 'removePerson1',
							formId : 'personListForm',
							rowContainerId : 'personListContainer2',
							indexedPropertyName : 'personList',
							indexedPropertyMemberNames : 'aggFuncName,fieldName,condition,conditionValue,value',
							rowAddedListener : rowAdded,
							rowRemovedListener : rowRemoved1,
							//beforeSubmit : beforeSubmit1
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
								var config = {
									rowClass : 'ruleParameter',
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
								
								$("#rad_ind").click(function(){
					            	$("#row_rank").hide(1000);
					            	
					             });
					             
					             $("#rad_rank").click(function(){
					            	  $("#row_rank").show(1000);
					            	
					             });
					             
					             if(document.getElementById("rad_ind").checked){
					            	 $("#row_rank").hide();
					             }else{
					            	 $("#row_rank").show();
					             }
					             
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
									
									if(document.getElementById("rad_global").checked){
										document.getElementById("rad_global_val").disabled=false;
						             }
							});

			var count = "1";
			function addRow2(Quali_input) {
				var tbody = document.getElementById(Quali_input);
				var row = document.createElement("TR");
				var td1 = document.createElement("TD");
				var strHtml1 = "<select><option value=\"Customer Name\">Customer Name</option><option value=\"order Total\">order Total</option><option value=\"Discount%\">Discount%</option></select>&nbsp;Not&nbsp;<input type=\"checkbox\" name=\"not\" value=\"Not\">&nbsp;Condition&nbsp;<select><option value=\"Equal\">Equal</option><option value=\"Greater than\">Greater than</option><option value=\"Less than\">Less than</option></select>&nbsp;Parameter Name&nbsp;<input type=\"text\" name=\"parametername\">";
				td1.innerHTML = strHtml1.replace(/!count!/g, count);
				var td2 = document.createElement("TD")
				var strHtml2 = "&nbsp;<INPUT TYPE=\"Button\" CLASS=\"Button\" onClick=\"delRow()\" VALUE=\"Delete Row\">";
				td2.innerHTML = strHtml2.replace(/!count!/g, count);
				row.appendChild(td1);
				row.appendChild(td2);
				count = parseInt(count) + 1;
				tbody.appendChild(row);
			}
			var count = "1";
			function addRow1(Par_input) {
				var tbody = document.getElementById(Par_input);
				var row = document.createElement("TR");
				var td1 = document.createElement("TD");
				var strHtml1 = "Parameter Name&nbsp;<INPUT TYPE=\"text\" name=\"parameterName\">&nbsp;Default Value&nbsp;<INPUT TYPE=\"text\" name=\"defaultValue\">";
				td1.innerHTML = strHtml1.replace(/!count!/g, count);
				var td2 = document.createElement("TD")
				var strHtml2 = "&nbsp;<INPUT TYPE=\"Button\" CLASS=\"Button\" onClick=\"delRow()\" VALUE=\"Delete Row\">";
				td2.innerHTML = strHtml2.replace(/!count!/g, count);
				row.appendChild(td1);
				row.appendChild(td2);
				count = parseInt(count) + 1;
				tbody.appendChild(row);
			}
			function delRow() {
				var current = window.event.srcElement;
				while ((current = current.parentElement)
						&& current.tagName != "TR")
					;
				current.parentElement.removeChild(current);
			}
		</script>
		<form:form action="/CommissionTool/updateSimpRule"
			modelAttribute="personListContainer" method="post"
			id="personListForm">

			<div style="height: 580px; overflow: auto;">
				<h1 align="center">Compensation Rule Details</h1>

				<table border="1">

					<tr>
						<td><b>Rule Name:</b></td>
						<td><input type="hidden" name="id" value="${listRule4.id}">
							<input type="text" name="ruleName" size=100
							value="${listRule4.ruleName}"></td>
					</tr>
					<tr>
						<td><b>Description:</b></td>
						<td><textarea name="description" rows="4" cols="100">${listRule4.description}</textarea></td>
					</tr>
					<tr>
						<td><b>RuleDetails:</b></td>
						<td><input type="text" name="ruleDetails" size=100
							value="${listRule4.ruleDetails}"></td>
					</tr>
					<tr>
						<td><b>Rule Type:</b></td>
						<td>Simple<input type="hidden" name="ruleType" value="Simple"></td>
					</tr>

					<tr>
						<td><b>Parameters:</b></td>
						<td>

							<table>
								<tbody id="personListContainer">
								<c:if test="${personListContainer.personList1.size() != 0}">
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
								</c:if>
									
										<tr class="ruleParameter defaultRow">
											<td>Parameter Name&nbsp;<input type="text"
												name="personList1[].parameterName" value="" /></td>
											<td>&nbsp;&nbsp;Parameter Value&nbsp;<input type="text"
												name="personList1[].parameterValue" value="" /></td>

											<td><a href="#" class="removePerson">Remove</a></td>
										</tr>
									

								</tbody>
							</table> <a href="#" id="addPerson">Add Parameters</a><!-- &nbsp;&nbsp; <a
							href="?f=">Reset List</a>-->

						</TD>
					</tr>


					<tr>
						<td><b>Calculation mode:</b></td>
						<td><input type="radio" name="calculationMode" value="individual"
							${listRule4.ruleSimple.calculationMode=='individual'?'checked':''} id="rad_ind">Individual&nbsp;&nbsp;&nbsp;&nbsp;<input
							type="radio" name="calculationMode" value="rank"
							${listRule4.ruleSimple.calculationMode=='rank'?'checked':''} id="rad_rank">Rank</td>
					</tr>
						<tr id="row_rank">
					<td></td>
					<td>
					<table>
					<tr>
						<td></td>
						<td>Within <input type="number" Name="rankCount" value="${listRule4.ruleSimple.rankCount}"
							size="4">ranks in <input type="radio" Name="rankType"
							value="Number" ${listRule4.ruleSimple.rankingType=='Number'?'checked':''}>number &nbsp;&nbsp;
							<input type="radio" Name="rankType"
							value="percentage" ${listRule4.ruleSimple.rankingType=='percentage'?'checked':''}>percentage
						</td>
					</tr>
					<tr>
						<td></td>
						<td>
							<table>
								<tr>
									<td>Population</td>
									<td><input type="radio" Name="populationType"
										value="SameReportingManager" id="rad_same_mgr" ${listRule4.ruleSimple.populationType=='SameReportingManager'?'checked':''}>Under same reporting
										manager</td>
								</tr>
								<tr>
									<td></td>
									<td><input type="radio" Name="populationType"
										value="SameRole" id="rad_same_role" ${listRule4.ruleSimple.populationType=='SameRole'?'checked':''}>Same role</td>
								</tr>
								<tr>
									<td></td>
									<td><input type="radio" Name="populationType"
										value="global upto" id="rad_global" ${listRule4.ruleSimple.populationType=='global upto'?'checked':''}>Global Upto <input type="number"
										Name="populationUpto" id="rad_global_val" value="${listRule4.ruleSimple.populationUpto}" disabled></input> level up</td>
								</tr>
							</table>
						</td>
						</tr>
						</table>
						</td>
						<script>
						 $("#rad_global").click(function(){
			            	  $("#rad_global_val").prop("disabled",false);
			            	
			             });
			             
			             $("#rad_same_role").click(function(){
			            	  $("#rad_global_val").prop("disabled",true);
			            	
			             });
			             
			             $("#rad_same_mgr").click(function(){
			            	  $("#rad_global_val").prop("disabled",true);
			            	
			             });
						
						</script>
					</tr>
					
					<tr>
						<td><b>Based on: </b></td>
						<td><table>
								<tr>
									<td>Aggregate function</td>
									<td>Field</td>
								</tr>
								<tr>
									<td><select name="aggregateFunctions">
											<option
												value="${listRule4.ruleSimple.aggregateFunctions.functionName}">---${listRule4.ruleSimple.aggregateFunctions.functionName}---</option>
											
											<!--<c:forEach items="${listRule1}" var="rule">
												<option value="${rule.functionName}">
													<c:out value="${rule.functionName}" />
												</option>
											</c:forEach>-->
											<option value="sum"><c:out value="sum"></c:out></option>
											<option value="max"><c:out value="max"></c:out></option>
											<option value="min"><c:out value="min"></c:out></option>
									</select></td>
									<td><select name="Field">
											<option value="${listRule4.ruleSimple.field}">---${listRule4.ruleSimple.field}---</option>
											<option VALUE="Order Total">Order Total</option>
											<option value="Quantity">Quantity</option>
											
									</select></td>
							</table></td>
					</tr>
					<!--  
					<tr>
						<td><b>Qualifying Clause</b></td>
						<td>
							<table ID="Quali_input">
								<tr>
									<td><input type="Button" onClick="addRow2('Quali_input')"
										VALUE="Add Row"></td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;&nbsp;Field&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										Condition
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Value</td>
								</tr>
							</table>

						</td>
					</tr>
							-->
							
							
							<tr>
						<td><b>Qualifying Clause</b></td>
						<td>
						 <div>
						 <strong>Simple</strong>
							<!-- <table id="personListFormNonAgg"> -->
							<table>
								<tbody id="personListContainer1">
								<c:forEach items="${qualifyingListNonAgg}" var="quali">
										<tr class="person">
										<td>
									<input type="hidden" name="personListNonAgg[].aggFuncName" value="" >
									<br>
								&nbsp;Field Name&nbsp;<select
												name="personListNonAgg[].fieldName" required>
													<option value="${quali.fieldList.displayName}">---${quali.fieldList.displayName}---</option>
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
											</select>&nbsp;
											</td>
											
												
												<td>&nbsp;Not&nbsp;<select name="personListNonAgg[].condition" required>
												<option value="${quali.notFlag}">
															<c:out value="---${quali.notFlag}---" />
														</option>
														
														<option value="true">
															<c:out value="true" />
															<option value="false">
															<c:out value="false" />
														</option>
												
												</select>
										
										</td>

											<td>Condition&nbsp;<select
												name="personListNonAgg[].conditionValue">
													<option value="${quali.conditionList.conditionValue}">---${quali.conditionList.conditionValue}---</option>
													<!--<c:forEach items="${listRule3}" var="rule">
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
											</td>

											<td>&nbsp;Value&nbsp;<input type="text"
												name="personListNonAgg[].value" value="${quali.value}" /></td>

											<td><a href="#" class="removePersonNonAgg">Remove</a></td>
										</tr>
									</c:forEach>
									<c:if test="${personListContainer1.personList.size() == 0}">
										<tr class="person defaultRow">
										<td>
										
											<input type="hidden" name="personListNonAgg[].aggFuncName" value="" >
											&nbsp;Field Name&nbsp;<select
												name="personListNonAgg[].fieldName" required>
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
											</select>&nbsp;
											</td>

										
												
												<td>&nbsp;Not&nbsp;
								
											<select name="personListNonAgg[].condition" required>
												
														
														<option value="true">
															<c:out value="true" />
															<option value="false">
															<c:out value="false" />
														</option>
												
												</select>
										</td>

											<td>&nbsp;Condition&nbsp;<select
												name="personListNonAgg[].conditionValue">
													<!--<c:forEach items="${listRule3}" var="rule">
														<option value="${rule.conditionValue}">
															<c:out value="${rule.conditionValue}" />
														</option>
													</c:forEach>-->
													
													<c:forEach items="${condNameList}" var="condName">
													<option value="${condName}">
															<c:out value="${condName}" />
														</option>
													</c:forEach>
											</select>&nbsp;
											</td>
											<td>&nbsp;Value&nbsp;<input type="text"
												name="personListNonAgg[].value"></td>
											<td><a href="#" class="removePersonNonAgg">Remove</a></td>

										</tr>
									</c:if>
								</tbody>
							</table> <a href="#" id="addPersonNonAgg">Add</a>
							
							</div>
							<br>
							<div>
						<strong>Aggregate Qualifying Clause</strong>
							  <!--  <table id="personListForm2">-->
							  <table>
								<tbody id="personListContainer2">
									
									<c:forEach items="${qualifyingList}" var="quali">
										<tr class="person1">
										<td>&nbsp;Aggregate function&nbsp;
									<select name="personList[].aggFuncName" required>
									<option value="${quali.aggregateFunctions.functionName }">
													<c:out value="---${quali.aggregateFunctions.functionName }---" />
												</option>
												
									<c:forEach
												items="${listRule1}" var="rule">
												<option value="${rule.functionName}">
													<c:out value="${rule.functionName}" />
												</option>
											</c:forEach>
									</select></td>
									
											<td>&nbsp;Field Name&nbsp;<select
												name="personList[].fieldName">
													<option value="${quali.fieldList.displayName}">---${quali.fieldList.displayName}---</option>
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
											</select>
											</td>
										
												
												<td>&nbsp;Not&nbsp;<select name="personList[].condition" required>
												<option value="${quali.notFlag}">
															<c:out value="---${quali.notFlag}---" />
														</option>
														
														<option value="true">
															<c:out value="true" />
															<option value="false">
															<c:out value="false" />
														</option>
												
												</select>
								
											
										</td>

											<td>Condition&nbsp;<select
												name="personList[].conditionValue">
													<option value="${quali.conditionList.conditionValue}">---${quali.conditionList.conditionValue}---</option>
													<!--<c:forEach items="${listRule3}" var="rule">
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
											</td>

											<td>&nbsp;Value&nbsp;<input type="text"
												name="personList[].value" value="${quali.value}" /></td>

											<td><a href="#" class="removePerson1">Remove</a></td>
										</tr>
									</c:forEach>
									<c:if test="${personListContainer2.personList.size() == 0}">
										<tr class="person1 defaultRow">
									<td>&nbsp;Aggregate function&nbsp;
									<select name="personList[].aggFuncName" required>
									
									<c:forEach
												items="${listRule1}" var="rule">
												<option value="${rule.functionName}">
													<c:out value="${rule.functionName}" />
												</option>
											</c:forEach>
									</select></td>
									
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

										<td>&nbsp;Not&nbsp;
												<select name="personList[].condition">
															<option value="true">
															<c:out value="true" />
															<option value="false">
															<c:out value="false" />
														</option>
												
												</select>
										</td>

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
											name="personList[].value" required></td>
										<td><a href="#" class="removePerson1">Remove</a></td>

									</tr>
									</c:if>
								</tbody>
							</table> <a href="#" id="addPerson1">Add</a>
							<!-- &nbsp;&nbsp; <a href="?f=">Reset
								List</a> -->
								</div>
						</td>
					</tr>
					
					
					<tr>
						<td><b>Compensation:</b></td>

						<td>
							<table>
								<tr>
									<td><input type="radio" name="compensationType"
										value="Fixed" id="compensationTypeFixed"
										${listRule4.compensationType=='Fixed'?'checked':''} 
										onchange="enableFixed()">Fixed</td>
									<td><input type="number" size="20" name="fixedCompValue"
										value="${listRule4.fixedCompValue}" id="fixedCompValue"  required></input></td>
								</tr>
								<tr>
									<td valign="top"><input type="radio"
										name="compensationType" value="Variable"
										${listRule4.compensationType=='Variable'?'checked':''} id="compensationTypeVariable"
										onchange="enableVariable()">Variable&nbsp;
									</td>
									<td>Apply formula <input type="text" size="20"
										name="compensationFormula" id="compensationFormula"
										value="${listRule4.compensationFormula}"><br />
									<br /> &nbsp;&nbsp;&nbsp;Parameters <input type="text"
										size="20" name="compensationParameter"
										value="${listRule4.compensationParameter}" id="compensationParameter">
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

				<br/><div align="center">
					<input type="submit" value="Update"> <a
						href="/CommissionTool/ruleList"> <input type="button"
						value="Cancel" /></a>
				</div>




			</div>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>
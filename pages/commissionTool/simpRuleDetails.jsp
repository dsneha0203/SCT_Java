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
#row_rank{
display:none;
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
							formId : 'personListFormNonAgg',
							rowContainerId : 'personListContainer1',
							indexedPropertyName : 'personList',
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
							formId : 'personListForm2',
							rowContainerId : 'personListContainer2',
							indexedPropertyName : 'personList',
							indexedPropertyMemberNames : 'aggFuncName,fieldName,condition,conditionValue,value',
							rowAddedListener : rowAdded,
							rowRemovedListener : rowRemoved1,
							beforeSubmit : beforeSubmit1
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
									formId : 'personListForm1',
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
		<form:form action="/CommissionTool/submitSimpRule" modelAttribute="personListContainer" method="post"
			id="personListForm">
			<!--
<form action="/CommissionTool/submitSimpRule"  method="post">
	-->
			<div style="height: 580px; overflow: auto;">
				<h1 align="center">Compensation Rule Details</h1>

				<table border="1">

					<tr>
						<td><b>Rule Name:</b></td>
						<td><input type="text" name="ruleName" size=100 required></td>
					</tr>
					<tr>
						<td><b>Description:</b></td>
						<td><textarea name="description" rows="4" cols="100"></textarea></td>
					</tr>
					<tr>
						<td><b>RuleDetails</b></td>
						<td><input type="text" name="ruleDetails" size=100></td>
					</tr>
					<tr>
						<td><b>Rule Type:</b></td>
						<td>Simple<input type="hidden" name="ruleType" value="s"></td>
					</tr>



					<tr>
						<td><b>Parameters</b></td>
						<td>
							
							<table id="personListForm1">
								<tbody id="personListContainer">



									<tr class="ruleParameter defaultRow">
										<td>Parameter Name&nbsp;<input type="text"
											name="personList1[].parameterName" value=""  required/></td>
										<td>Parameter Value&nbsp;<input type="number"
											name="personList1[].parameterValue" value="" required/></td>

										<td><a href="#" class="removePerson">Remove</a></td>
									</tr>


								</tbody>
							</table> <a href="#" id="addPerson">Add Parameters</a><!-- &nbsp;&nbsp; <a href="?f=">Reset
								List</a> -->

						</td>
						
					</tr>

					<tr>
						<td><b>Calculation mode :</b></td>
						<td><input type="radio" name="calculationMode" value="i" id="rad_ind" checked>Individual&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="calculationMode" value="r" id="rad_rank">Rank</td>
					</tr>
					
					<tr id="row_rank">
					<td></td>
					<td>
					<table>
					<tr>
						<td></td>
						<td>Within <input type="number" Name="rankCount" value="0"
							size="4">ranks in <input type="radio" Name="rankType"
							value="Number">number &nbsp;&nbsp;
							<input type="radio" Name="rankType"
							value="percentage">percentage
						</td>
					</tr>
					<tr>
						<td></td>
						<td>
							<table>
								<tr>
									<td>Population</td>
									<td><input type="radio" Name="populationType"
										value="SameReportingManager" id="rad_same_mgr">Under same reporting
										manager</td>
								</tr>
								<tr>
									<td></td>
									<td><input type="radio" Name="populationType"
										value="SameRole" id="rad_same_role">Same role</td>
								</tr>
								<tr>
									<td></td>
									<td><input type="radio" Name="populationType"
										value="global upto" id="rad_global">Global Upto <input type="number"
										Name="populationUpto" id="rad_global_val" value="0" disabled></input> level up</td>
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
											<!--<c:forEach
												items="${listRule1}" var="rule">
												<option value="${rule.functionName}">
													<c:out value="${rule.functionName}" />
												</option>
											</c:forEach>-->
											<option value="sum"><c:out value="sum"></c:out></option>
											<option value="max"><c:out value="max"></c:out></option>
											<option value="min"><c:out value="min"></c:out></option>
									</select></td>
									<td><select name="Field">
									<!--<c:forEach
												items="${listRule2}" var="field">
												<option value="${field.displayName}">
													<c:out value="${field.displayName}" />
												</option>
											</c:forEach>-->
											<option VALUE="Order Total">Order Total</option>
											<option value="Quantity">Quantity</option>
											
									</select></td>
							</table></td>
					</tr>

					<tr>
						<td><b>Qualifying Clause</b></td>
						<td>
						<div>
						<strong>Simple</strong>
							<table id="personListFormNonAgg">
								<tbody id="personListContainer1">
									

									<tr class="person defaultRow">
									<td>
									<input type="hidden" name="personList[].aggFuncName" value="" >
									&nbsp;Field Name&nbsp;<select
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
										<!-- <input type="text"
											name="personList[].condition" value="TRUE" size="2"> -->
											<!-- <input type="checkbox" name="personList[].condition" id="notFlag"> -->
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
											<!--<c:forEach items="${listRule3}" var="rule">
														<option value="${rule.conditionValue}">
															<c:out value="${rule.conditionValue}" />
														</option>
													</c:forEach>-->
													
													<c:forEach items="${condNameList}" var="condName">
													<option value="${condName}">
															<c:out value="${condName}" />
														</option>
													</c:forEach></select>
										<td>&nbsp;Value&nbsp;<input type="text"
											name="personList[].value" required></td>
										<td><a href="#" class="removePersonNonAgg">Remove</a></td>

									</tr>

								</tbody>
							</table> <a href="#" id="addPersonNonAgg">Add</a>
							<!-- &nbsp;&nbsp; <a href="?f=">Reset
								List</a> -->
							</div>
							<br>
							<div>
						<strong>Aggregate Qualifying Clause</strong>
							  <table id="personListForm2">
								<tbody id="personListContainer2">
									

									<tr class="person1 defaultRow">
									<td>&nbsp;Aggregate function&nbsp;
									<select name="personList[].aggFuncName">
									
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
												<!--<c:forEach items="${listRule3}" var="rule">
														<option value="${rule.conditionValue}">
															<c:out value="${rule.conditionValue}" />
														</option>
													</c:forEach>-->
													
													<c:forEach items="${condNameList}" var="condName">
													<option value="${condName}">
															<c:out value="${condName}" />
														</option>
													</c:forEach></select>
										<td>&nbsp;Value&nbsp;<input type="text"
											name="personList[].value" required></td>
										<td><a href="#" class="removePerson1">Remove</a></td>

									</tr>

								</tbody>
							</table> <a href="#" id="addPerson1">Add</a>
							<!-- &nbsp;&nbsp; <a href="?f=">Reset
								List</a> -->
								</div>
						</td>
					</tr>

					<tr>
						<td><b>Compensation</b></td>

						<td>
							<table>
								<tr>
									<td><input type="radio" name="compensationType"
										id="compensationTypeFixed"
										value="Fixed" checked onchange="enableFixed()">Fixed</td>
									<td><input type="number" size="20" name="fixedCompValue"
										value="0" id="fixedCompValue" required></input></td>
								</tr>
								<tr>
									<td valign="top"><input type="radio"
										name="compensationType"
										id="compensationTypeVariable" value="Variable" 
										onchange="enableVariable()">Variable&nbsp;
									</td>
									<td>Apply formula <input type="text" size="20"
										name="compensationFormula"
										id="compensationFormula" disabled><br /> <br />
										&nbsp;&nbsp;&nbsp;Parameters <input type="text" size="20"
										name="compensationParameter"
										id="compensationParameter" disabled>
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
					<input type="submit" value="Submit"> <a
						href="/CommissionTool/ruleList"> <input type="button"
						value="Cancel" /></a>
				</div>




			</div>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>
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
				            $('#submit').css('color','red');
				            $('#submit').css('font-weight','bold');
				            if( $('#submit').val().indexOf('!') != 0 ) {
				                $('#submit').val( '!' + $('#submit').val() );
				            }
				        }

				        function beforeSubmit() {
				            alert('submitting....');
				            return true;
				        }

				        $(document).ready( function() {
				            var config = {
				                rowClass : 'person',
				                addRowId : 'addPerson1',
				                removeRowClass : 'removePerson1',
				                formId : 'personListForm2',
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
						     $('#submit').css('color','red');
						     $('#submit').css('font-weight','bold');
						     if( $('#submit').val().indexOf('!') != 0 ) {
						         $('#submit').val( '!' + $('#submit').val() );
						     }
						 }

						 function beforeSubmit() {
						     alert('submitting....');
						     return true;
						 }

						 /*$(document).ready( function() {
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
						 });*/

		     
				
 
var count = "1";

  function addRow(in_tbl_name)
  {
    var tbody = document.getElementById(in_tbl_name);
    
    var row = document.createElement("TR");

    var td1 = document.createElement("TD")
  var RuleArray=new Array();
     RuleArray= "${listRule}";
    var len=RuleArray.length;

   var strHtml1= "<SELECT NAME=\"Rule\"><OPTION VALUE=\"Null\">Null<OPTION VALUE=\"Null\">Null<OPTION VALUE=\"Null\">Null</SELECT>";
   
   
    td1.innerHTML = strHtml1.replace(/!count!/g,count);
   
    var td2 = document.createElement("TD")
    var strHtml2 = "<INPUT TYPE=\"Button\" CLASS=\"Button\" onClick=\"delRow()\" VALUE=\"Delete Row\">";
    td2.innerHTML = strHtml2.replace(/!count!/g,count);
    
    row.appendChild(td1);
    row.appendChild(td2);

    count = parseInt(count) + 1;
  
    tbody.appendChild(row);
  }
  function delRow()
  {
    var current = window.event.srcElement;
    //here we will delete the line
    while ( (current = current.parentElement)  && current.tagName !="TR");
         current.parentElement.removeChild(current);
  }
  

</script>
		<form:form action="/CommissionTool/submitCompRule"
		  method="post">
			
			<!-- 
<form action="/CommissionTool/submitCompRule" method="post">

-->
			<div style="height: 580px; overflow: auto;">
			<div align="center">
				<h1>Compensation Rule Details</h1>
			</div>


			<table border="1">
				<tr>
						<td><b>Rule Name:</b></td>
						<td><input type="text" name="ruleName" size=50 required></td>
					</tr>
					<tr>
						<td><b>Description:</b></td>
						<td><textarea name="description" rows="4" cols="50"></textarea></td>
					</tr>
					<tr>
					<td><b>Rule type:</b></td>
					<td>Composite<input type="hidden" name="ruleType" value="c">
				</tr>
				<tr>
					<td><b>Rules connected as: </b></td>
					<td><input type="Radio" name="connectionType" value="All">&nbsp;All&nbsp;
						<input type="radio" name="connectionType" value="Any" checked>&nbsp;Any&nbsp;</td>
				</tr>

			
				
				<tr>
					<td><b>List of Rules</b></td>
					<td>
					
						<table id="personListForm2">
							<tbody id="personListContainer2">
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


							</tbody>
						</table>
						 <a href="#" id="addPerson1">Add&nbsp;</a>&nbsp;&nbsp; <a
						href="?f=">&nbsp;Reset List</a>
					</td>
				</tr>
				
				<!--  <tr>
					<td><b>Rule Parameter</b></td>
					<td>
						<table id="personListForm1">
							<tbody id="personListContainer">
									<tr class="ruleParameter defaultRow">
										<td>Parameter Name&nbsp;<input type="text"
											name="personList1[].parameterName" value="" required/></td>
										<td>&nbsp;&nbsp;Parameter Value&nbsp;<input type="number"
											name="personList1[].parameterValue" value="" required/></td>

										<td><a href="#" class="removePerson">Remove</a></td>
									</tr>

							</tbody>
						</table> <a href="#" id="addPerson">Add Parameters</a>&nbsp;&nbsp; <a
						href="?f=">Reset List</a>


					</td>

				</tr>-->
				

					<tr>
				<td><b>Compensation</b></td>
			
			<td>
			<table>
			<tr>
				<td><input type="radio" name="compensationType" id="compensationTypeFixed"
				 value="Fixed" onchange="enableFixed()" checked>Fixed</td>
				<td>
					<input type="number" size="20" 
					id="fixedCompValue"
					name="fixedCompValue" value="0" required></input>
				</td>
			</tr>
			<tr>
				<td valign="top"><input type="radio" name="compensationType" 
				id="compensationTypeVariable" value="Variable" onchange="enableVariable()">Variable&nbsp;
				</td>
				<td>
					Apply formula <input type="text" size="20" name="compensationFormula"
					id="compensationFormula" disabled><br/><br/>
					&nbsp;&nbsp;&nbsp;Parameters <input type="text" size="20" name="compensationParameter"
					id="compensationParameter" disabled>
				</td>
			</tr>
			</table>
			</td></tr>
			
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
			<input type="submit" value="Submit" />
			<a href="/CommissionTool/ruleList"> <input type="button"
				value="Cancel" /></a></div>

</div>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>
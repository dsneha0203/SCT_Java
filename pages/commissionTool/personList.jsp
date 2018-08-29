<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
       
 <tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">

		

      <!--    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
        <script src="js/dynamic_list_helper.js" type="text/javascript"></script> -->
        <title>JSP Page</title>
    
    
        <h3>Parameter List</h3>
        <div style="border:1px solid #eaeaea;padding:20px;width:400px">
            ${message}
        </div>
        <form:form action="/CommissionTool/editpersonlistcontainer" modelAttribute="personListContainer1" method="post" id="personListForm1">
           <input type="text" id="txtPassportNumber" disabled="disabled">
           
           <div id="chkPassport">
    <input type="checkbox" id="chkPassport" onclick="EnableDisableTextBox(this)">
    Do you have Passport?
</div>
           
            <table>
                <thead>
                    <tr>
                        <th>Parameter Name</th>
                        <th>Parameter Val</th>
                        <th></th>
                    </tr>
                </thead>
                
  <tbody id="personListContainer1">
									<c:forEach items="${personListContainer1.personList}"
										var="Person" varStatus="i" begin="0">
										<tr class="person">
											
											<td>&nbsp;FieldName&nbsp;<form:select
													path="personList[${i.index}].fieldName"
													id="fieldName${i.index}">
													<c:forEach items="${listRule2}" var="rule">
														<option value="${rule.displayName}">
															<c:out value="${rule.displayName}" />
														</option>
													</c:forEach>
												</form:select></td>
												
										<td id="chkPassport">   
										
										<input type="checkbox" id="chkPassport" onclick="EnableDisableTextBox(this)">
										 <input type="text" id="txtPassportNumber" disabled="disabled">
										</td>
									
												<td>&nbsp;Not&nbsp;<form:checkbox
													path="personList[${i.index}].condition" id="condition${i.index}" /></td>
										
											<td>&nbsp;Condition&nbsp;<form:select
													path="personList[${i.index}].conditionValue"
													id="conditionValue${i.index}">
													<c:forEach items="${listRule3}"
											var="rule">
											<option value="${rule.conditionValue}">
												<c:out value="${rule.conditionValue}" />
											</option>
										</c:forEach></form:select></td>

											<td>&nbsp;Value&nbsp;<form:input
													path="personList[${i.index}].value" id="value${i.index}" /></td>

											<td><a href="#" class="removePerson1">Remove</a></td>
										</tr>
									</c:forEach>

									<c:if test="${personListContainer1.personList.size() == 0}">
										<tr class="person defaultRow">
											<td>&nbsp;Field Name&nbsp;<select
												name="personList[].fieldName">
													<c:forEach items="${listRule2}" var="rule">
														<option value="${rule.displayName}">
															<c:out value="${rule.displayName}" />
														</option>
													</c:forEach>
											</select></td>
											<td id="chkPassport">
											<input type="checkbox" id="chkPassport" onclick="EnableDisableTextBox(this)">
									
									
											 <input type="text" id="txtPassportNumber" disabled="disabled"></td>
											<td>&nbsp;Not&nbsp;<input type="checkbox"
												name="personList[].condition" value="12"></td>
											
											<td>&nbsp;Condition&nbsp;<select
												name="personList[].conditionValue"><c:forEach items="${listRule3}"
											var="rule">
											<option value="${rule.conditionValue}">
												<c:out value="${rule.conditionValue}" />
											</option>
										</c:forEach></select>

											<td>&nbsp;Value&nbsp;<input type="text"
												name="personList[].value"></td>
											<td><a href="#" class="removePerson1">Remove</a></td>

										</tr>
									</c:if>
								</tbody>
            </table>
            <input type="submit" value="Save" id="submit" />&nbsp;&nbsp;
            <a href="#" id="addPerson1">Add Person</a>&nbsp;&nbsp;
            <a href="?f=">Reset List</a>
            	<label for="chkPassport">	
   										 <input type="checkbox" id="chkPassport" onclick="EnableDisableTextBox(this)">
    									Do you have Passport?
											</label>
        </form:form>
 
        <script type="text/javascript">
        
        function EnableDisableTextBox(chkPassport) {
            var txtPassportNumber = document.getElementById("txtPassportNumber");
            txtPassportNumber.disabled = chkPassport.checked ? false : true;
            if (!txtPassportNumber.disabled) {
                txtPassportNumber.focus();
            }
        }
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
                    formId : 'personListForm1',
                    rowContainerId : 'personListContainer1',
                    indexedPropertyName : 'personList',
                    indexedPropertyMemberNames : 'fieldName,condition,conditionValue,value',
                    rowAddedListener : rowAdded,
                    rowRemovedListener : rowRemoved,
                    beforeSubmit : beforeSubmit
                };
                new DynamicListHelper(config);
            });
            
           
        </script>
 
    </tiles:putAttribute>
</tiles:insertDefinition>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 
 
<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">

    <head>
    <title>OrganizationStructure</title>
</head>

<style>
ul{
list-style-type: none;
}

.wrapper{ 
	overflow-y:auto; 
    position:relative; 
    height: 500px;
    padding-top: 30px;
}



</style> 
 <link rel="stylesheet" href="resources/css/kendo.common.min.css" />
    <link rel="stylesheet" href="resources/css/kendo.default.min.css" />

    <script src="resources/js/jquery-1.12.1.min.js"></script>
    <script src="resources/js/kendo.all.min.js"></script>
  
  <div align="center"><h1>Organization Structure </h1></div>
   <div class="wrapper">   
   
   <table>
   
   <tr><td align="left">
   		<c:set var="count" value="0" scope="page"/>
		<c:set var="bossListCount" value="${fn:length(bossList)}" scope="page"/>
		<c:forEach items="${roleList}" var="role">
		<ul>
		
		<c:if test="${role.reportsTo == null }">
		
			<c:set var="bossListCount" value="${bossListCount - 1}" scope="page"/>
					
			<c:set var="count" value="0"/>
		
			<li>
					<span class="Collapsable"><c:forEach begin="0" end="${count}" step="1">&nbsp;&nbsp;</c:forEach>- &nbsp;<a href='/CommissionTool/roleDetails/${role.id}'>${role.roleName}</a><br></span>
					<ul>
					<c:forEach items="${roleList}" var="role2">
					<c:if test="${role2.roleName != role.roleName}">
							<c:if test="${ role2.reportsTo.roleName == role.roleName}">
								<li>
							<span class="Collapsable"><c:forEach begin="0" end="${count}" step="1">&nbsp;&nbsp;</c:forEach>- &nbsp;<a href='/CommissionTool/roleDetails/${role2.id}'>${role2.roleName}</a></span>
								
								<c:set var="counter" value="0" scope="page"/>	
								<c:forEach items="${bossList}" var="boss">
									<c:if test="${counter eq 0 }">
										<c:if test="${role2.roleName == boss}">
											<c:set var="bossListCount" value="${bossListCount - 1}" />
											<c:set var="counter" value="${counter + 1 }"></c:set>
											<ul>
														<c:forEach items="${roleList}" var="role3">
														<c:if test="${role3.roleName != role2.roleName}">
														<c:if test="${role3.reportsTo.roleName == role2.roleName}">
														 <li>
															<span class="Collapsable"><c:forEach begin="0" end="${count}" step="1">&nbsp;&nbsp;</c:forEach>- &nbsp;<a href='/CommissionTool/roleDetails/${role3.id}'>${role3.roleName}</a></span>
														
															<c:set var="counter1" value="0" scope="page"/>
															<c:forEach items="${bossList}" var="boss">
																<c:if test="${counter1 eq 0 }">
																	<c:if test="${role3.roleName == boss}">
																		<c:set var="bossListCount" value="${bossListCount - 1}" />
																		<c:set var="counter" value="${counter1 + 1 }"></c:set>
																		<ul>
																			<c:forEach items="${roleList}" var="role4">
																				<c:if test="${role4.roleName != role3.roleName}">
																					<c:if test="${role4.reportsTo.roleName == role3.roleName}">
																						<li>
																							<span class="Collapsable"><c:forEach begin="0" end="${count}" step="1">&nbsp;&nbsp;</c:forEach>- &nbsp;<a href='/CommissionTool/roleDetails/${role4.id}'>${role4.roleName}</a></span>
																							<c:set var="counter2" value="0" scope="page"/>
																							<c:forEach items="${bossList}" var="boss">
																								<c:if test="${counter2 eq 0 }">
																									<c:if test="${role4.roleName == boss}">
																										<c:set var="bossListCount" value="${bossListCount - 1}" />
																										<c:set var="counter" value="${counter2 + 1 }"></c:set>
																										<ul>
																											<c:forEach items="${roleList}" var="role5">
																												<c:if test="${role5.roleName != role4.roleName}">
																													<c:if test="${role5.reportsTo.roleName == role4.roleName}">
																														<li>
																															<span class="Collapsable"><c:forEach begin="0" end="${count}" step="1">&nbsp;&nbsp;</c:forEach>- &nbsp;<a href='/CommissionTool/roleDetails/${role5.id}'>${role5.roleName}</a></span>
																															<c:set var="counter3" value="0" scope="page"/>
																															<c:forEach items="${bossList}" var="boss">
																																<c:if test="${counter3 eq 0 }">
																																	<c:if test="${role5.roleName == boss}">
																																		<c:set var="bossListCount" value="${bossListCount - 1}" />
																																		<c:set var="counter" value="${counter3 + 1 }"></c:set>
																																		<ul>
																																			<c:forEach items="${roleList}" var="role6">
																																				<c:if test="${role6.roleName != role5.roleName}">
																																					<c:if test="${role6.reportsTo.roleName == role5.roleName}">
																																						<li>
																																							<span class="Collapsable"><c:forEach begin="0" end="${count}" step="1">&nbsp;&nbsp;</c:forEach>- &nbsp;<a href='/CommissionTool/roleDetails/${role6.id}'>${role6.roleName}</a></span>
																																							<c:set var="counter4" value="0" scope="page"/>
																																							<c:forEach items="${bossList}" var="boss">
																																								<c:if test="${counter4 eq 0 }">
																																									<c:if test="${role6.roleName == boss}">
																																										<c:set var="bossListCount" value="${bossListCount - 1}" />
																																										<c:set var="counter" value="${counter4 + 1 }"></c:set>
																																										<ul>
																																											<c:forEach items="${roleList}" var="role7">
																																												<c:if test="${role7.roleName != role6.roleName}">
																																													<c:if test="${role7.reportsTo.roleName == role6.roleName}">
																																														<li>
																																															<span class="Collapsable"><c:forEach begin="0" end="${count}" step="1">&nbsp;&nbsp;</c:forEach>- &nbsp;<a href='/CommissionTool/roleDetails/${role7.id}'>${role7.roleName}</a></span>
																																															<c:set var="counter5" value="0" scope="page"/>
																																															<c:forEach items="${bossList}" var="boss">
																																																<c:if test="${counter5 eq 0 }">
																																																	<c:if test="${role7.roleName == boss}">
																																																		<c:set var="bossListCount" value="${bossListCount - 1}" />
																																																		<c:set var="counter" value="${counter5 + 1 }"></c:set>
																																																		<ul>
																																																			<c:forEach items="${roleList}" var="role8">
																																																			<c:if test="${role8.roleName != role7.roleName}">
																																																				<c:if test="${role8.reportsTo.roleName == role7.roleName}">
																																																					<li>
																																																						<span class="Collapsable"><c:forEach begin="0" end="${count}" step="1">&nbsp;&nbsp;</c:forEach>- &nbsp;<a href='/CommissionTool/roleDetails/${role8.id}'>${role8.roleName}</a></span>
																																																						<c:set var="counter6" value="0" scope="page"/>
																																																						<c:forEach items="${bossList}" var="boss">
																																																							<c:if test="${counter6 eq 0 }">
																																																								<c:if test="${role8.roleName == boss}">
																																																									<c:set var="bossListCount" value="${bossListCount - 1}" />
																																																									<c:set var="counter" value="${counter6 + 1 }"></c:set>
																																																									<ul>
																																																										<c:forEach items="${roleList}" var="role9">
																																																											<c:if test="${role9.roleName != role8.roleName}">
																																																												<c:if test="${role9.reportsTo.roleName == role8.roleName}">
																																																													<li>
																																																														<span class="Collapsable"><c:forEach begin="0" end="${count}" step="1">&nbsp;&nbsp;</c:forEach>- &nbsp;<a href='/CommissionTool/roleDetails/${role9.id}'>${role9.roleName}</a></span>
																																																														<c:set var="counter7" value="0" scope="page"/>
																																																														<c:forEach items="${bossList}" var="boss">
																																																															<c:if test="${counter7 eq 0 }">
																																																																<c:if test="${role9.roleName == boss}">
																																																																	<c:set var="bossListCount" value="${bossListCount - 1}" />
																																																																	<c:set var="counter" value="${counter7 + 1 }"></c:set>
																																																																	<ul>
																																																																		<c:forEach items="${roleList}" var="role10">
																																																																			<c:if test="${role10.roleName != role9.roleName}">
																																																																				<c:if test="${role10.reportsTo.roleName == role9.roleName}">
																																																																					<li>
																																																																						<span class="Collapsable"><c:forEach begin="0" end="${count}" step="1">&nbsp;&nbsp;</c:forEach>- &nbsp;<a href='/CommissionTool/roleDetails/${role10.id}'>${role10.roleName}</a></span>
																																																																						<c:set var="counter8" value="0" scope="page"/>
																																																																						<c:forEach items="${bossList}" var="boss">
																																																																							<c:if test="${counter8 eq 0 }">
																																																																								<c:if test="${role10.roleName == boss}">
																																																																									<c:set var="bossListCount" value="${bossListCount - 1}" />
																																																																									<c:set var="counter" value="${counter8 + 1 }"></c:set>
																																																																									<ul>
																																																																										<c:forEach items="${roleList}" var="role11">
																																																																											<c:if test="${role11.roleName != role10.roleName}">
																																																																												<c:if test="${role11.reportsTo.roleName == role10.roleName}">
																																																																													<li>
																																																																														<span class="Collapsable"><c:forEach begin="0" end="${count}" step="1">&nbsp;&nbsp;</c:forEach>- &nbsp;<a href='/CommissionTool/roleDetails/${role11.id}'>${role11.roleName}</a></span>
																																																																														<c:set var="counter9" value="0" scope="page"/>
																																																																														<c:forEach items="${bossList}" var="boss">
																																																																															<c:if test="${counter9 eq 0 }">
																																																																																<c:if test="${role11.roleName == boss}">
																																																																																	<c:set var="bossListCount" value="${bossListCount - 1}" />
																																																																																	<c:set var="counter" value="${counter9 + 1 }"></c:set>
																																																																																	<ul>
																																																																																		<c:forEach items="${roleList}" var="role12">
																																																																																			<c:if test="${role12.roleName != role11.roleName}">
																																																																																				<c:if test="${role12.reportsTo.roleName == role11.roleName}">
																																																																																					<li>
																																																																																						<span class="Collapsable"><c:forEach begin="0" end="${count}" step="1">&nbsp;&nbsp;</c:forEach>- &nbsp;<a href='/CommissionTool/roleDetails/${role12.id}'>${role12.roleName}</a></span>
																																																																																						<c:set var="counter10" value="0" scope="page"/>
																																																																																						<c:forEach items="${bossList}" var="boss">
																																																																																							<c:if test="${counter10 eq 0 }">
																																																																																								<c:if test="${role12.roleName == boss}">
																																																																																									<c:set var="bossListCount" value="${bossListCount - 1}" />
																																																																																									<c:set var="counter" value="${counter10 + 1 }"></c:set>
																																																																																									<ul>
																																																																																										<c:forEach items="${roleList}" var="role13">
																																																																																											<c:if test="${role13.roleName != role12.roleName}">
																																																																																												<c:if test="${role13.reportsTo.roleName == role12.roleName}">
																																																																																													<li>
																																																																																														<span class="Collapsable"><c:forEach begin="0" end="${count}" step="1">&nbsp;&nbsp;</c:forEach>- &nbsp;<a href='/CommissionTool/roleDetails/${role13.id}'>${role13.roleName}</a></span>
																																																																																													</li>
																																																																																												</c:if>
																																																																																											</c:if>
																																																																																										</c:forEach>
																																																																																									</ul>
																																																																																								</c:if>
																																																																																							</c:if>
																																																																																						</c:forEach>
																																																																																					</li>
																																																																																				</c:if>
																																																																																			</c:if>
																																																																																		</c:forEach>
																																																																																	</ul>
																																																																																</c:if>
																																																																															</c:if>
																																																																														</c:forEach>
																																																																													</li>
																																																																												</c:if>
																																																																											</c:if>
																																																																										</c:forEach>
																																																																									</ul>
																																																																								</c:if>
																																																																							</c:if>
																																																																						</c:forEach>
																																																																					</li>
																																																																				</c:if>
																																																																			</c:if>
																																																																		</c:forEach>
																																																																	</ul>
																																																																</c:if>
																																																															</c:if>
																																																														</c:forEach>																																																												
																																																													</li>
																																																												</c:if>
																																																											</c:if>
																																																										</c:forEach>
																																																									</ul>
																																																								</c:if>
																																																							</c:if>
																																																						</c:forEach>
																																																					</li>
																																																				</c:if>
																																																			</c:if>
																																																			</c:forEach>
																																																		</ul>
																																																	</c:if>
																																																</c:if>
																																															</c:forEach>
																																														</li>
																																													</c:if>
																																												</c:if>
																																											</c:forEach>
																																										</ul>
																																									</c:if>
																																								</c:if>
																																							</c:forEach>
																																						</li>
																																					</c:if>
																																				</c:if>
																																			</c:forEach>
																																		</ul>
																																	</c:if>
																																</c:if>
																															</c:forEach>	
																														
																														</li>
																													</c:if>
																												</c:if>
																											</c:forEach>
																										</ul>
																									</c:if>
																								</c:if>
																							</c:forEach>
																						
																						</li>
																					</c:if>
																				</c:if>
																			</c:forEach>
																		</ul>
																	</c:if>
																</c:if>
															</c:forEach>
														</c:if>														
														</c:if>
														</c:forEach>	
											</ul>
										</c:if>
									</c:if>
								</c:forEach>
								
								</li>
							</c:if>
					</c:if>
					
					</c:forEach>
					</ul>
			</li>
	
		</c:if>	
		
		
		</ul>
		
		
		</c:forEach>		
   </td>
    <td>&nbsp;</td>
    </tr>
  
   </table>
   
   
   
   
   
   </div>   
      
     <script>
     
	    $(".Collapsable").click(function () {
	        $(this).parent().children().toggle();
	        $(this).toggle();
	        $(this).html($(this).html().startsWith('+') ? $(this).html().replace('+','-') : $(this).html().replace('-','+'));
	    });
     </script> 
      
      
    </tiles:putAttribute>
</tiles:insertDefinition>
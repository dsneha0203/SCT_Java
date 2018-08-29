<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page session="false"%>

  <tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">
	<div align="center"><h3>Role List</h3></div>
<head>
    <title>Role list</title>
  
   
    
    <link rel="stylesheet" href="resources/css/kendo.common.min.css" />
    <link rel="stylesheet" href="resources/css/kendo.default.min.css" />

    <script src="resources/js/jquery-1.12.1.min.js"></script>
    <script src="resources/js/kendo.all.min.js"></script>
 
      <script>
                $(document).ready(function() {
                    $("#grid").kendoGrid({
                    	 pageSize: 20,
                    	 height: 500,
                          scrollable: true,
                          sortable: true,
                          resizable: true,
                          pageable: {
                        	  refresh: true,
                              pageSizes: true,
                              buttonCount: 5
                          },
                    });
                });
            </script>
</head>

  <div id="example">
            <table id="grid">
                <colgroup>
                    <col style="width:110px"/>
                    <col />
                    <col />
                    <col style="width:120px" />
                    <col style="width:130px" />
                </colgroup>
                <thead>
                    <tr>
                        <th data-field="id"><b>ID</b></th>
                        <th data-field="roleName"><b>Role Name</b></th>
                        <th data-field="description"><b>Description</b></th>
                        <th data-field="reportTo"><b>Reports To</b></th>
                         <th data-field="editdelete"><b>Edit/Delete</b></th>
                       
                    </tr>
                </thead>
                <tbody>
                   <c:forEach items="${listRole}" var="role">
							<tr>
							
								<td>${role.id}</td>
								<td>${role.roleName}</td>
								<td>${role.description}</td>
								<td>${role.reportTo}</td>
								<td><a
									href="<c:url value='/editRole/${role.id}' />">Edit</a>
									| <a href="<c:url value='/deleterole/${role.id}' />">Delete</a></td>
							
								
							</tr>
							
						</c:forEach>
						
						
					</table>
                </tbody>
       <div class="well">
					<a href="<c:url value='/role' />">Add Role</a>
				</div>
           
        </div>
	</tiles:putAttribute>
</tiles:insertDefinition>
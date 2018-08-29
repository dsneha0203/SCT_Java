<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<head>
    <title>Add Employee</title>
</head>
<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">
 
 <div id="page-wrapper">
   
            <div class="container-fluid">

                <!-- Page Heading -->
                <div class="row">
                    <div class="col-lg-12">
                        <h3 class="page-header">
                            Employees Details
                        </h3>
                        <ol class="breadcrumb">
                            <li>
                                <i class="fa fa-dashboard"></i>  <a href="index.html">Master Data</a>
                            </li>
                            <li class="active">
                                <i class="fa fa-edit"></i> Employee
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->

                <div class="row">
                    <div class="col-lg-6">

<form:form method="POST" action="/CommissionTool/submit1">
   <table>
    <tr>
        <td><form:label path="id">Id</form:label></td>
        <td><form:input path="id" /></td>
    </tr>
    <tr>
        <td><form:label path="name">Name</form:label></td>
        <td><form:input path="name" /></td>
    </tr>
      <tr>
        <td><form:label path="startDate">Start Date</form:label></td>
        <td><form:input path="startDate" /></td>
    </tr>
    <tr>
        <td><form:label path="termDate">Termination Date</form:label></td>
        <td><form:input path="termDate" /></td>
    </tr> 
    <tr>
        <td><form:label path="salary">Salary</form:label></td>
        <td><form:input path="salary" /></td>
    </tr>
    <tr>
        <td><form:label path="role">Role</form:label></td>
        <td><form:input path="role" /></td>
    </tr>
    <tr>
        <td><form:label path="managerId">Manager Id</form:label></td>
        <td><form:input path="managerId" /></td>
    </tr>  
    <tr> 
        <td colspan="2">
            <input type="submit" value="Submit"/>
        </td>
    </tr>
</table>  
</form:form>
</div>
 </div>
 </div>
 </div>
 
 
 
 
    </tiles:putAttribute>
</tiles:insertDefinition>

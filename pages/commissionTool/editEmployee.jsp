<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>   
<head>
    <title>Edit Employee</title>
</head>
<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">
 <style type="text/css">
        .tg  {border-collapse:collapse;border-spacing:0;border-color:#ccc;}
        .tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#fff;}
        .tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#f0f0f0;}
        .tg .tg-4eph{background-color:#f9f9f9}
    </style>
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
  <c:url var="addAction" value="/submitEmployee" ></c:url>
 
<form:form action="${addAction}" commandName="employee">
      <table>  
       <tr>  
           <td><form:label path="id">Employee ID:</form:label></td>  
           <td><form:input path="id" value="${employee.id}" readonly="true" /></td>    
       </tr>  
       <tr>  
           <td><form:label path="firstName">Employee First Name:</form:label></td>  
           <td><form:input path="firstName" value="${employee.firstName}"/></td>  
       </tr>  
       <tr>  
           <td><form:label path="lastName">Employee Last Name:</form:label></td>  
           <td><form:input path="lastName" value="${employee.lastName}"/></td>  
       </tr>  
       <tr>  
           <td><form:label path="salary">Employee Salary:</form:label></td>  
           <td><form:input path="salary" value="${employee.salary}"/></td>  
       </tr>  
         
          <tr>  
        <td><input type="submit" value="Submit"/>
			| <a href="<c:url value='/employeeList' />">Cancel</a></td>
        </tr>  
   </table>   
  </form:form>  
<br>
<!--  <h3>Employee List</h3>
<c:if test="${!empty listEmployee}">
    <table class="tg">
     <tr>
        <th width="80">ID</th>
        <th width="120">First Name</th>
         <th width="120">Last Name</th>
        <th width="120">Salary</th>
        <th>Actions on Row</th>  
    </tr>
    <c:forEach items="${listEmployee}" var="employee">
        <tr>
            <td>${employee.id}</td>
            <td>${employee.firstName}</td>
            <td>${employee.lastName}</td>
            <td>${employee.salary}</td>
           <td><a href="<c:url value='/editEmployee/${employee.id}' />" >Edit</a>
            | <a href="<c:url value='/removeEmployee/${employee.id}' />" >Delete</a></td>  
        </tr>
    </c:forEach>
    </table>
</c:if> -->
</div>
</div>
  </tiles:putAttribute>
</tiles:insertDefinition>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<head>
    <title>Submitted Result</title>
</head>
<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">
 
<body>

<h2>Submitted Employee Information</h2>
   <table>
    <tr>
        <td>ID</td>
        <td>${id}</td>
    </tr>
    <tr>
        <td>Name</td>
        <td>${name}</td>
    </tr>
     <tr>
        <td>Start Date</td>
        <td>${startDate}</td>
    </tr>
     <tr>
        <td>Termination Date</td>
        <td>${termDate}</td>
    </tr>
    <tr>
        <td>Salary</td>
        <td>${salary}</td>
    </tr>
    <tr>
        <td>Role</td>
        <td>${role}</td>
    </tr>
    <tr>
        <td>Manager Id</td>
        <td>${managerId}</td>
    </tr>
   
</table>  
</body>
 </tiles:putAttribute>
</tiles:insertDefinition>
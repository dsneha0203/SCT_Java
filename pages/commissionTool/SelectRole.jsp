 <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  

<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">


<head>
    <title>SelectRole</title>
</head>

 
 <link rel="stylesheet" href="resources/css/kendo.common.min.css" />
    <link rel="stylesheet" href="resources/css/kendo.default.min.css" />

    <script src="resources/js/jquery-1.12.1.min.js"></script>
    <script src="resources/js/kendo.all.min.js"></script>
  
  <div align="center"><h1>Select Role</h1></div>
   
<div align="center">
<b>Select Role:&nbsp;</b><select name="ddlNames" id="ddlNames">
    <c:forEach items="${roleList}" var="role">
    <option value="${role.roleName}">
		<c:out value="${role.roleName}" />
		</option>
    
    </c:forEach>
</select>

<br />
<br />
<input type="button" value="Select" onclick="SetName();" />
</div>
<script type="text/javascript">
    function SetName() {
        if (window.opener != null && !window.opener.closed) {
            var txtName = window.opener.document.getElementById("roleName");
            txtName.value = document.getElementById("ddlNames").value;
        }
        window.close();
    }
</script>

 
 </tiles:putAttribute>
 </tiles:insertDefinition>
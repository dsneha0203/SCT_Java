 <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<head>
    <title>Add Employee</title>
</head>
<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">
 
 <div id="page-wrapper">
   
            <div class="container-fluid">

               
                <!-- /.row -->

                <div class="row">
                    <div class="col-lg-12">
                    
<h2 align="center">Welcome to Sales Commission Tool, ${sessionScope.employee}</h2>

 
 </div>
 </div>
 </div>
 </div>
  </tiles:putAttribute>
</tiles:insertDefinition>
  
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

   <table>
    <tr>
        <td>ID</td>
        <td>${message}</td>
    </tr>
</table>  

</div>
 </div>
 </div>
 </div>
 
 
 
 
    </tiles:putAttribute>
</tiles:insertDefinition>

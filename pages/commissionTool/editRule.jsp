<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>   
<head>
    <title>Edit Rule</title>
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
                            Rule Details
                        </h3>
                        <ol class="breadcrumb">
                            <li>
                                <i class="fa fa-dashboard"></i>  <a href="index.html">Master Data</a>
                            </li>
                            <li class="active">
                                <i class="fa fa-edit"></i> Rule
                            </li>
                        </ol>
                    </div>
                </div>
  <c:url var="addAction" value="/submitRule" ></c:url>
 
<form:form action="${addAction}" commandName="rule">
      <table>  
       <tr>  
           <td><form:label path="id">Rule ID:</form:label></td>  
           <td><form:input path="id" value="${rule.id}" readonly="true" /></td>  
       </tr>  
       <tr>  
           <td><form:label path="ruleName">Compensation Rule Name:</form:label></td>  
           <td><form:input path="ruleName" value="${rule.ruleName}"/></td>  
       </tr>  
       <tr>  
           <td><form:label path="description">Compensation Rule Description:</form:label></td>  
           <td><form:input path="description" value="${rule.description}"/></td>  
       </tr>  
       <tr>  
           <td><form:label path="ruleType">Compensation Rule Types:</form:label></td>  
           <td><form:input path="ruleType" value="${rule.ruleType}"/></td>  
       </tr>  
         
          <tr>  
        <td><input type="submit" value="Submit"/>
			| <a href="<c:url value='/ruleList' />">Cancel</a></td>
        </tr>  
   </table>   
  </form:form>  
<br>

</div>
</div>
 </tiles:putAttribute>
</tiles:insertDefinition>
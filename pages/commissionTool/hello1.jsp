<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page session="false" %>
<head>
    <title>Employee Added</title>
</head>
<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">
<body>
<h2>successfully added..</h2>

<head>
<style>
table, th, td {
    border: 1px solid black;
    border-collapse: collapse;
}
th, td {
    padding: 5px;
}
</style>
</head>


<table style="width:100%">
  <tr>
  	<th>Id</th>
    <th>RoleName</th>
    <th>Description</th>		
    <th>RuleDetails</th>
    <th>type</th>
    <th>CompensationType</th>
        <th>FixedCompValue</th>
     <th>CompensationFormula</th>
      <th>CompensationtParameter</th>
      <th>CalculationMode</th>
   
  
  </tr>
  <c:forEach items="${listRule}" var="rule">
  <tr>
    <td>${rule.id}</td>
    <td>${rule.ruleName}</td>		
    <td>${rule.description}</td>
    <td>${rule.ruleDetails}</td>
     <td>${rule.ruleType}</td>
         <td>${rule.compensationType}</td>
          <td>${rule.fixedCompValue}</td>
          <td>${rule.compensationFormula}</td>
          <td>${rule.compensationParameter}</td>
          
         
    
   
  </tr>
 </c:forEach>
</table>



 </tiles:putAttribute>
</tiles:insertDefinition>
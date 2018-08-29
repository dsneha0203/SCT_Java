<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<title>SetEndDate</title>
</head>
<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">
	
	<script type="text/javascript">
	$(function() {
		$('input').filter('.datepicker').datepicker({
			changeMonth : true,
			changeYear : true,
			depth : "year",
			showOn : 'button',
			buttonImage : 'resources/image/c.png',
			buttonImageOnly : true
		});
	});

	</script>
	
	
	
	<form action="/CommissionTool/submitenddate/${sessionScope.empDetailsId}"  method="post">
	
	<br/><br/><br/><div align="center">
	
	<b>Enter End Date:&nbsp;</b><input type="text" class="datepicker" name="setDate">
	
	<br/>
	<input type="submit" value="Submit Date">
	
	
	</div>
	
	
	
	
	
	
	</form>
	
	
	
	
	
	
	
	
	
	
	</tiles:putAttribute>
	</tiles:insertDefinition>
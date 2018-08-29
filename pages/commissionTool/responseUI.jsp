<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Users using ajax</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script>
	
</script>
<script>
	$(document)
			.ready(
					function() {

						$(document).keydown(function(e) {
							if (e.keyCode == '32' && e.keyCode != '0') {
								doAjaxPost();
							}
						});
						/* $("#myName").keyup(function(){
						     $("#myName").css("background-color", "yellow");
						     doAjaxPost();
						     console.log(document.getElementById("myName").value.endsWith(" "));
						 }); */
						function doAjaxPost() {
							$
									.ajax({
										type : "GET",
										url : "http://localhost:8081/CommissionTool/jsonresponse1?name="
											+ $("#myName").val(),
										success : function(response) {
											//$('#info').html(response);
											console.log(response);
											$("#info").html('');
											var div3Content = '';
											for (var i = 0; i < response.length; i++) {

												div3Content += '<p><a href="' + response[i].url +
														'" onmouseout="clearText();" onmouseover="showDetails(\'' +
														response[i].content +
																'\');">' + response[i].title + '</a></p>'; 

											}

											$("#info").append(div3Content); 
										},
										error : function(e, x, settings,
												exception) {
											var message;
											var statusErrorMap = {
												'400' : "Server understood the request, but request content was invalid.",
												'401' : "Unauthorized access.",
												'403' : "Forbidden resource can't be accessed.",
												'500' : "Internal server error.",
												'503' : "Service unavailable."
											};
											if (x.status) {
												message = statusErrorMap[x.status];
												if (!message) {
													message = "Unknown Error \n.";
												}
											} else if (exception == 'parsererror') {
												message = "Error.\nParsing JSON Request failed.";
											} else if (exception == 'timeout') {
												message = "Request Time out.";
											} else if (exception == 'abort') {
												message = "Request was aborted by the server";
											} else {
												message = "Unknown Error \n.";
											}
											$(this).css("display", "inline");
											$(this).html(message);
										}
									});
						}

					});
	function showDetails(details) {
//		var span = document.getElementById("googleLinkDetails");
//		span.textContent = details;
		$("[id$='googleLinkDetails']").text(details);
	}

	function clearText() {
		$("[id$='googleLinkDetails']").html('');
	}
</script>
</head>
<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">

		<div id="page-wrapper">

			<div class="container-fluid">

				<!-- Page Heading -->
				<div class="row">
					<div class="col-lg-12">
						<h3 class="page-header">Ajax Result</h3>
						<ol class="breadcrumb">
							<li><i class="fa fa-dashboard"></i> <a href="#">Master
									Data</a></li>
							<li class="active"><i class="fa fa-edit"></i> Result</li>
						</ol>
					</div>
				</div>
				<!-- /.row -->

				<div class="row">
					<div class="col-lg-6">

						<form:form>
							<table>

								<tr>
									<td><form:label path="name">Enter Query</form:label></td>
									<td><form:input path="name" id="myName" /></td>
								</tr>



								<tr>
									<td colspan="2"><div id="info" style="color: green;"></div></td>
								</tr>
								<tr>
									<td colspan="2"><div id="break" style="color: green;"><br/><br/></div></td>
								</tr>
								<tr>
									<td colspan="2"><span id="googleLinkDetails" style="color: green;"></span></td>
								</tr>
							</table>
						</form:form>

					</div>
				</div>
			</div>
		</div>

	</tiles:putAttribute>
</tiles:insertDefinition>
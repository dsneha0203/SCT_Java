<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">
		<div align="center">
			<h2>Order Management</h2>
			<h4>List Of Imports</h4>
		</div>

		<head>

<title>OrderManagement</title>

<link rel="stylesheet" href="resources/css/kendo.common.min.css" />
<link rel="stylesheet" href="resources/css/kendo.default.min.css" />

<script src="resources/js/jquery-1.12.1.min.js"></script>
<script src="resources/js/kendo.all.min.js"></script>

<script>

$(document)
.ready(
        function() {
            //add more file components if Add is clicked
            $('#addFile')
                    .click(
                            function() {
                                var fileIndex = $('#fileTable tr')
                                        .children().length - 1;
                                $('#fileTable')
                                        .append(
                                                '<tr><td>'
                                                        + '   <input type="file" name="files['+ fileIndex +']" />'
                                                        + '</td></tr>');
                            });

        });



	function HandleBrowseClick() {
		var fileinput = document.getElementById("browse");
		fileinput.click();
	}
	function Handlechange() {
		var fileinput = document.getElementById("browse");
		var textinput = document.getElementById("filename");
		textinput.value = fileinput.value;
	}

	$(document).ready(function() {
		$("#grid").kendoGrid({
			pageSize : 10,
			height : 400,
			//scrollable: {
			//virtual: true
			//},                                                
			//scrollable: true,
			sortable : true,
			resizable : true,
			reorderable : true,
			groupable : false,
			selectable : "multiple",
			filterable : {
				extra : false,
				operators : {
					string : {
						contains : "Contains",
						startswith : "Starts With",
						endswith : "Ends With",
					//eq: "Is Equal To"
					}
				}
			},

			columnMenu : true,
			pageable : {
				pageSize : 10,
				buttonCount : 10,
				//numeric: true,
				pageSizes : true,
				refresh : true
			},
		});
	});
</script>
<style >
.wrapper{ 
	overflow-y:auto; 
    position:relative; 
    height: 500px;
    padding-top: 30px;
}
</style>
		</head>
		<div class="wrapper" >
		<div id="example">

			<table id="grid">
				<!--<colgroup>
					<col />
					<col />
					<col style="width: 110px" />
					<col style="width: 120px" />
					<col style="width: 130px" />
					<col />
				</colgroup>-->
				<thead>
					<tr>
						<th data-field="ImportId">Import Id</th>
						<th data-field="ImportDate">Import Date</th>
						<th data-field="CountOfOrders">Count Of Orders</th>
						<th data-field="importedBy">Imported By</th>
						<th data-field="Status">Status</th>
						<th data-field="Action">Action</th>

					</tr>
				</thead>
				
					<c:forEach items="${listOrder}" var="order">
						<tr>

						 <!-- 
						<td>
							<form action="/CommissionTool/submitId" method="POST">
							<input type=hidden name="importId" value="${order.id}">
							<input type="submit"  value="${order.id}"></form></td>
							
						   -->
							<td><a href="/CommissionTool/orderDetails/${order.id}"><input type="button"
						value="${order.id}"></a></td>
							
							<td>${order.importDate}</td>
							<td>${order.countOfOrders}</td>
							<td>${order.importedBy.employeeName}</td>
							<td>${order.status}</td>
							<td><a href="/CommissionTool/verifyOrder/${order.id}"><input type="button"
						value="Verify"></a>&nbsp;&nbsp;<a href="/CommissionTool/deleteOrder/${order.id}"><input type="button"
						value="Delete"></a></td>

						</tr>
					
				</c:forEach>


			</table>
		</div>
		<br/>
	
		
		     <form:form method="post" action="/CommissionTool/savefiles"  modelAttribute="uploadForm" enctype="multipart/form-data">
 			<div align="center">
            <table id="fileTable">
                <tr>
                <td><b>Import Files:&nbsp;</b><input name="files[0]" type="file" /></td>
                </tr>
            </table>
            </div><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
           <input type="submit" value="Upload" onClick="alert('Uploading Files.....!!!!!')" />
            <!-- <input id="addFile" type="button" value="Add More Files" />  -->
        </form:form>
        

        </div>
	</tiles:putAttribute>
</tiles:insertDefinition>
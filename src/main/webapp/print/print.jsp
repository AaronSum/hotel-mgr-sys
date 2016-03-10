<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String urlPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>print</title>
	<style type="text/css">
		.print-row{
			width: 1250px;
			margin: 0 auto;
			overflow: hidden;
		}
		.print-column{
			text-align: center;
			font-weight: bold;
		}
		.print-cell {
			float: left;
		}
		.print-cell img{
			width: 150px;
			height: 150px;
			margin: 10px;
			border: 1px solid #cccccc;
			padding: 3px;
			border-radius: 5px;
		}
		.print-bottom{
			text-align: right;
			font-weight: bold;
		}
	</style>
	</head>
	<body>
		<div id="print_container"></div>
		<script type="text/javascript" src="<%=urlPath %>/main/plug-in/jquery/jquery-1.8.2.min.js"></script>
		<script type="text/javascript" src="<%=urlPath %>/main/plug-in/underscore/underscore-min.js"></script>
		<script type="text/javascript">
			var printList = window.opener.printList;
			var thisHotelName = window.opener.hotelName || "";
			//存在数据
			if (printList && printList.length > 0) {
				//多个
				if (printList.length > 1) {
					var rows = new Array();
					for (var i = 0; i < 5; i++) {
						rows.push("<div class='print-row'>");
						//支持IE8-
						var row = new Array();
						_.map(printList, function(rowData) {
							if (i === 0) {
								row.push("<div class='print-cell'>");
								row.push("<div class='print-column'>", rowData.tag, "</div>");
								row.push("<img src='<%=urlPath %>/i2dimcodes/userI2DimCode?content=");
								row.push(encodeURIComponent(rowData.content), "'/>");
								row.push("</div>");
							} else {
								row.push("<div class='print-cell'>");
								row.push("<img src='<%=urlPath %>/i2dimcodes/userI2DimCode?content=");
								row.push(encodeURIComponent(rowData.content), "'/>");
								row.push("</div>");
							}
						});
						rows.push(row.join(""));
						rows.push("</div>");
					}
					jQuery("#print_container").append(rows.join(""));
					jQuery("#print_container").append("<div class='print-row'><div class='print-bottom'>酒店：" + thisHotelName + "</div></div>");
				//一个	
				} else {
					//支持IE8-
					var rows = new Array();
					var rowData = printList[0];
					for (var i = 0; i < 5; i++) {
						rows.push("<div class='print-row'>");
						var row = new Array();
						for (var j = 0; j < 7; j++) {
							if (i === 0) {
								row.push("<div class='print-cell'>");
								row.push("<div class='print-column'>", rowData.tag, "</div>");
								row.push("<img src='<%=urlPath %>/i2dimcodes/userI2DimCode?content=");
								row.push(encodeURIComponent(rowData.content), "'/>");
								row.push("</div>");
							} else {
								row.push("<div class='print-cell'>");
								row.push("<img src='<%=urlPath %>/i2dimcodes/userI2DimCode?content=");
								row.push(encodeURIComponent(rowData.content), "'/>");
								row.push("</div>");
							}
						}
						rows.push(row.join(""));
						rows.push("</div>");
					}
					jQuery("#print_container").append(rows.join(""));
					jQuery("#print_container").append("<div class='print-row'><div class='print-bottom'>酒店：" + thisHotelName + "</div></div>");
				}
			}
			//打印
			window.onload = function() {
				window.print();
			}
		</script>
	</body>
</html>
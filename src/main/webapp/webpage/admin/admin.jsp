<%@ page pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<%
	String urlPath = request.getContextPath();
    //   http://localhost:8080/hms/webpage/admin/admin.jsp
%>
<html ng-app="sqlApp">
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <title>该页面只能研发人员使用</title>
	    <link href="../../main/plug-in/bootstrap/bootstrap-3.3.4/css/bootstrap.min.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="../../main/plug-in/angularJs/angularJs-3.1.5/angular-1.3.5.js"></script>
		<script type="text/javascript" src="../../main/plug-in/jquery/jquery-2.1.3/jquery-2.1.3.min.js"></script>
	    <link rel="stylesheet" href="./codemirror/codemirror.css"/>
	    <script type="text/javascript" src="./codemirror/codemirror.js"></script>
	    <script type="text/javascript" src="./codemirror/mode/sql.js"></script>
	    <script type="text/javascript" src="../../js/tools.js"></script>
	    <style type="text/css">
	        .CodeMirror {
	            width: 100%;
	            height: 200px;
	            border: 1px solid black;
	            font-size: 16px;
	            border: 1px solid black;
	        }
	
	        table {
	            border-collapse: collapse;
	        }
	
	        table, th, td {
	            border: 1px solid black;
	            padding: 2px;
	        }
	
	        .tableHeader {
	            background-color: gray;
	        }
	
	        .resultTable {
	            margin: 10px 10px 10px 10px;
	        }
	
	        #resultDiv td {
	            max-width: 300px;
	            padding: 4px;
	        }
	    </style>
	</head>
	<body ng-controller="sqlController">
		
		<div style="margin-top: 20px; margin-bottom: 20px;margin-left: 50px;">
		    <button type="button" class="btn btn-primary" ng-click="submitSql();">执行SQL</button>
		    <span style="color: red;font-weight: bold;">&nbsp;&nbsp;&nbsp;目前不支持select delete 语句 </span>
		</div>
		<div>
		    <form>
		        <textarea id="code" name="code" style="height: 100px;" ng-model="obj.sqlStr"></textarea>
		        <textarea id="codeTemp" name="code" style="height: 100px;display: none" ></textarea>
		    </form>
		</div>
	</body>
		<script type="text/javascript">
			var contentPath = "<%=urlPath %>";
			angular.module("sqlApp",[]).controller("sqlController", ['$rootScope', '$scope', '$http', function($rootScope,$scope,$http){
				$scope.submitSql = function(){
					var sqlStr = window.editor.getSelection();
			        if ($.trim(sqlStr) == "") {
			        	sqlStr = window.editor.getValue();
			            if ($.trim(sqlStr) == "") {
			            	alert("sql不能为空");
			                return;
			            }
			        }
			        
			        if(sqlStr.toLowerCase().indexOf("delete_admin") == -1 && sqlStr.toLowerCase().indexOf("delete") >= 0){
			            alert('无法执行delete语句！');
			            return;
			        }
			        sqlStr = sqlStr.replace("delete_admin","delete");
			        if($("#codeTemp").val() == sqlStr){
			        	  alert('无法执行重复的sql语句！');
				          return;
			        }
					var param = {
						 sqlStr : sqlStr
					}
					var getResisUrl = contentPath + "/exeSqlController/exeSql";
					$http.post(getResisUrl, param).then(
						function(res) {
							if(res.data.success){
								alert("执行成功");
								$("#codeTemp").val(sqlStr);
							}else{
								alert("执行失败");
							}
						},
						function(error){
							alert("执行失败", "error");
						}
					);
				}
			}])
			.config(["$httpProvider", function($httpProvider) {
				// 使angular $http post提交和jQuery一致
			    $httpProvider.defaults.headers.put['Content-Type'] = 'application/x-www-form-urlencoded';
			    $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
			 
			    // Override $http service's default transformRequest
			    $httpProvider.defaults.transformRequest = [function(data) {
			        /**
			         * The workhorse; converts an object to x-www-form-urlencoded serialization.
			         * @param {Object} obj
			         * @return {String}
			         */
			        var param = function(obj) {
			            var query = '';
			            var name, value, fullSubName, subName, subValue, innerObj, i;
			 
			            for (name in obj) {
			                value = obj[name];
			 
			                if (value instanceof Array) {
			                    for (i = 0; i < value.length; ++i) {
			                        subValue = value[i];
			                        fullSubName = name + '[' + i + ']';
			                        innerObj = {};
			                        innerObj[fullSubName] = subValue;
			                        query += param(innerObj) + '&';
			                    }
			                } else if (value instanceof Object) {
			                    for (subName in value) {
			                        subValue = value[subName];
			                        fullSubName = name + '[' + subName + ']';
			                        innerObj = {};
			                        innerObj[fullSubName] = subValue;
			                        query += param(innerObj) + '&';
			                    }
			                } else if (value !== undefined && value !== null) {
			                    query += encodeURIComponent(name) + '='
			                            + encodeURIComponent(value) + '&';
			                }
			            }
			 
			            return query.length ? query.substr(0, query.length - 1) : query;
			        };
			 
			        return angular.isObject(data) && String(data) !== '[object File]'
			                ? param(data)
			                : data;
			    }];
			}]);
		
		
		
		    var mime = 'text/x-mariadb';
		    // get mime type
		    if (window.location.href.indexOf('mime=') > -1) {
		        mime = window.location.href.substr(window.location.href.indexOf('mime=') + 5);
		    }
		
		    window.editor = CodeMirror.fromTextArea(document.getElementById('code'), {
		        mode: mime,
		        indentWithTabs: true,
		        smartIndent: true,
		        lineNumbers: true,
		        matchBrackets: true,
		        autofocus: true
		    });
		
		</script>
</html>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Dashboard Manage</title>
    <link rel="stylesheet" href="js/codemirror/codemirror.css"/>
    <script src="../soneui/js/third-party/vendors.min.js"></script>
    <script src="js/third-party/backbone/json2.js"></script>
    <script type="text/javascript" src="./codemirror/codemirror.js"></script>
    <script type="text/javascript" src="js/codemirror/mode/sql.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#btnUpdateDeptId").bind("click", executeUpdate);
            $("#btnQueryCurrent").bind("click", queryCurrent);
            $("#btnHideTable").bind("click", hideTable);
            $("#btnClearUserCache").bind("click", clearUserCache);
            $("#btnClearDeptCache").bind("click", clearDeptCache);
            $("#btnClearRedisCache").bind("click", clearRedisCache);
            $("#btnInitRedisCache").bind("click", initRedisCache);
            $("#btnShowDepts").bind("click", showDepts);
            $("#btnShowSkus").bind("click", showSkus);
            $("#btnHideResult").bind("click", hideResult);
            $("#btnShowResult").bind("click", showResult);
            $("#btnShowDDL").bind("click", queryCurrentDeptIdDDL);
            $("#btnExecDDL").bind("click", execChangeDeptidDDL);
            $("#btnDDL").bind("click", execDDL);

        });

        function executeUpdate() {

            var param = {};
            var erpName = $("#txtErpUser").val();
            var dept_id = $("#txtDeptId").val();

            if ($.trim(erpName) == "" || $.trim(dept_id) == "") {
                alert("请输入Erp帐号和部门id!");
                return;
            }

            param.erpName = erpName;
            param.dept_id = dept_id;

            ajaxPost("../rest/dashboard/admin/updateUserDeptId", param, function (data) {
                if (data) {
                    for (var i = 0; i < data.length; i++) {
                        if (data[i] == -1) {
                            alert("更新失败了！！");
                            return;
                        }
                    }
                }
                queryCurrent();
            }, function () {
                alert("更新失败了！！");
            });
        }

        function queryCurrent() {

            var $tblCurrent = $("#tblCurrent");

            $tblCurrent.empty();
            $tblCurrent.show();

            ajaxGet("../rest/dashboard/admin/queryCurrent", function (data) {
                $tblCurrent.append("<tr><td class='tableHeader'><span>Erp帐号</span></td><td class='tableHeader'><span>部门id</span></td></tr>");
                if (data) {
                    for (var i = 0; i < data.length; i++) {
                        $tblCurrent.append("<tr><td><span>" + data[i].erp + "</span></td><td><span>" + data[i].dept + "</span></td></tr>");
                    }
                }
            }, function () {
                alert("查询失败了！！");
            })
        }

        function hideTable() {
            $("#tblCurrent").hide();
        }


        function clearUserCache() {

            var erpName = $("#txtErpUser").val();

            ajaxGet("../rest/dashboard/admin/clearUserCache?erpUsers=" + erpName, function () {
                alert("用户缓存更新成功！！");
            }, function () {
                alert("用户缓存更新失败！！");
            });
        }

        function clearDeptCache() {

            var dept_id = $("#txtDeptId").val();

            ajaxGet("../rest/dashboard/admin/clearDeptCache?deptId=" + dept_id, function () {
                alert("部门缓存更新成功！！");
            }, function () {
                alert("部门缓存更新失败！！");
            });
        }

        function clearRedisCache() {
            ajaxGet("../rest/dashboard/initcommoditypic/clearcache/1", function (data) {
                alert("清除redis成功！！");
            }, function () {
                alert("清除redis失败！！");
            })
        }

        function initRedisCache() {

            ajaxGet("../rest/dashboard/initcommoditypic/inituserds/1", function (data) {
                alert("初始化redis成功！！");
            }, function () {
                alert("初始化redis失败！！");
            })
        }

        function showDepts() {

            var erpName = $("#txtErpUser2").val();

            ajaxGet("../rest/dashboard/admin/showUserDepts?erpUser=" + erpName, function (result) {
                showTableResult($("#resultDiv"), result);
            }, function () {
                alert("执行失败！！");
            });
        }

        function showSkus() {
            var erpName = $("#txtErpUser2").val();
            var skuIds = $("#txtSkuId").val();

            ajaxGet("../rest/dashboard/admin/showUserSkus?erpUser=" + erpName + "&skuIds=" + skuIds, function (result) {
                showTableResult($("#resultDiv"), result);
            }, function () {
                alert("执行失败！！");
            });
        }

        function hideResult() {
            $("#resultDiv").hide();
        }

        function showResult() {
            $("#resultDiv").show();
        }

        function ajaxGet(url, successFnc, errFnc) {
            var ajaxJson = {
                type: "GET",
                url: url,
                contentType: "application/json",
                success: successFnc,
                error: errFnc
            };
            $.ajax(ajaxJson);
        }

        function ajaxPost(url, param, successFnc, errFnc) {

            var ajaxJson = {
                type: "POST",
                url: url,
                data: JSON.stringify(param),
                contentType: "application/json",
                success: successFnc,
                error: errFnc
            };
            $.ajax(ajaxJson);
        }

        function showTableResult(div, result) {

        	div.empty();
        	div.removeClass();
        	div.show();
            var headers = result.headers;
            var rowValues = result.rowValues;
            var resultTable = $("<table></table>");
            var htmlArray = new Array();
            if (headers) {
                htmlArray.push("<thead><tr>")
                for (var i = 0; i < headers.length; i++) {
                    htmlArray.push("<td style='background-color: #ccc;'>");
                    htmlArray.push("<span>");
                    htmlArray.push(headers[i]);
                    htmlArray.push("</span>");
                    htmlArray.push("</td>");
                }
                htmlArray.push("</tr></thead>");
            }
            if (rowValues) {
                htmlArray.push("<tbody>");
                for (var i = 0; i < rowValues.length; i++) {
                    htmlArray.push("<tr>");
                    for (var j = 0; j < rowValues[i].length; j++) {
                        htmlArray.push("<td>");
                        htmlArray.push("<span>");
                        htmlArray.push(rowValues[i][j]);
                        htmlArray.push("</span>");
                        htmlArray.push("</td>");
                    }

                    htmlArray.push("</tr>");
                }
                htmlArray.push("</tbody>");
            }

            resultTable.html(htmlArray.join(""));
            div.addClass("resultTable");
            div.append(resultTable);
        }
        //表格下载查看现有btn对应的点击事件函数
        function queryCurrentDeptIdDDL() {
            ajaxGet("../rest/dashboard/admin/queryDDL", function (data) {
                $("#btnShowDDL").val("部门ID: " + data[0]);
                return data;
            }, function () {
                alert("查询失败");
            })
        }

        //表格下载执行对应对应的点击事件函数
        function execChangeDeptidDDL() {
            $("#btnShowDDL").val("查看现有");
            var input = $("#deptIdDDL").val();
            input = parseInt(input);

            if (isNaN(input)) {
                alert("请检查输入");
                return;
            }
            var params = {deptid: input};
            ajaxPost("../rest/dashboard/admin/execDDL", params, function (data) {
                console.log(data);
                alert("in suc function");
            });
        }


    </script>
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

<body>
<div>
    <p>Erp帐号可以输入多个，请用半角逗号分割。部门id只能输一个（为了把更新和清除缓存放一起，谅解一下吧）。</p>

    <p>部门id信息：</p>

    <p style="color: red;">1：通讯，30：IT，33：日百，34：POP，35：家电</p>
</div>
<div>
    <span>Erp帐号：</span><input id="txtErpUser"></input>
    <span style="margin-left: 20px;">部门id：</span><input style="width: 60px;" id="txtDeptId"></input>
    <input id="btnUpdateDeptId" style="margin-left: 15px; text-align: center; width: 50px;" type="button" value="执行"></input>
    <input id="btnQueryCurrent" style="margin-left: 5px; text-align: center; width: 80px;" type="button" value="查看现有"></input>
    <input id="btnHideTable" style="margin-left: 5px; text-align: center; width: 80px;" type="button" value="隐藏表格"></input>
    <input id="btnClearUserCache" style="margin-left: 5px; text-align: center; width: 100px;" type="button" value="清除用户缓存 "></input>
    <input id="btnClearDeptCache" style="margin-left: 5px; text-align: center; width: 100px;" type="button" value="清除部门缓存"></input>
    <input id="btnClearRedisCache" style="margin-left: 5px; text-align: center; width: 100px;" type="button" value="清除redis缓存"></input>
    <input id="btnInitRedisCache" style="margin-left: 5px; text-align: center; width: 110px;" type="button" value="初始化redis缓存"></input>
</div>
<div style="margin: 10px;">
    <table id="tblCurrent"></table>
</div>
<hr style="margin-top: 20px; margin-bottom: 20px;">
<div>
    <p>Erp帐号只能输一个，如果输了sku，可以在该用户的权限下查看是否有这个sku的权限。<font color="red">sku可以输多个！</font></p>
</div>
<div>
    <span>Erp帐号：</span><input id="txtErpUser2"></input>
    <span style="margin-left: 20px;">sku编码：</span><input style="width: 240px;" id="txtSkuId">
    <input id="btnShowDepts" style="margin-left: 5px; text-align: center; width: 100px;" type="button" value="显示部门信息"></input>
    <input id="btnShowSkus" style="margin-left: 5px; text-align: center; width: 110px;" type="button" value="显示sku信息"></input>
    <input id="btnHideResult" style="margin-left: 5px; text-align: center; width: 80px;" type="button" value="隐藏表格"></input>
    <input id="btnShowResult" style="margin-left: 5px; text-align: center; width: 80px;" type="button" value="显示表格"></input>
</div>
<div id="resultDiv"></div>
<%
    String erpUser = (String) session.getAttribute("session_user");
    List<String> devUsers = new ArrayList<String>();
    devUsers.add("yinjiabin");
    devUsers.add("changwei");
    devUsers.add("wangle82");
    devUsers.add("litao26");
    devUsers.add("lixuenan");
    devUsers.add("songteng");
    devUsers.add("songquanwang");
    devUsers.add("chenwenlong6");

    if (erpUser != null && devUsers.contains(erpUser)) {
%>
<hr style="margin-top: 20px; margin-bottom: 20px;">
<div>
    <p style="color: red;">更新所有数据源的中的表数据用。Select语句请点“查询SQL”，查询只支持一个数据源（默认第一个选中的），最大限制500条记录。支持选中查询哦！！！！！</p>
</div>
<div style="margin-top: 10px; margin-bottom: 10px;">
    <input id="chkJD" type="checkbox" checked="checked"></input><label for="chkJD">家电</label>
    <input id="chkRB" type="checkbox" checked="checked"></input><label for="chkRB">日百</label>
    <input id="chkIT" type="checkbox" checked="checked"></input><label for="chkIT">IT</label>
    <input id="chkTX" type="checkbox" checked="checked"></input><label for="chkTX">通讯</label>
    <input id="chkPOP" type="checkbox" checked="checked"></input><label for="chkPOP">POP</label>
    <input id="btnExecuteSql" style="margin-left: 15px; text-align: center; width: 80px;" type="button" value="执行SQL"></input>
    <input id="btnQuerySql" style="margin-left: 15px; text-align: center; width: 80px;" type="button" value="查询SQL"></input>
    <input id="btnHideQueryResult" style="margin-left: 15px; text-align: center; width: 80px;" type="button" value="隐藏表格"></input>
    <input id="btnShowQueryResult" style="margin-left: 15px; text-align: center; width: 80px;" type="button" value="显示表格"></input>
</div>
<div>
    <form>
        <textarea id="code" name="code" style="height: 100px; display: none;"></textarea>
    </form>
</div>
<div id="queryResultDiv"></div>
<%--<div hidden="hidden">--%>
<div>
    <hr style="margin-top: 20px; margin-bottom: 20px;">

    <div>
        <p>表格下载功能,修改研发部门(25, 研发\产品)对应的部门权限.</p>
    </div>

    <div>
        <span>部门ID: </span><input style="width: 40px;" id="deptIdDDL">
        <input id="btnExecDDL" style="margin-left: 5px; text-align: center; width: 110px;" type="button" value="执行">
        <input id="btnShowDDL" style="margin-left: 5px; text-align: center; width: 110px;" type="button" value="查看现有">
    </div>

    <hr style="margin-top: 20px; margin-bottom: 20px;">
    <div>
        <p>表格下载接口,用于线上数据导入测试库.Erp账号为接受邮件的Erp账号.<font color="red">导入数据库时,字符编码请选择GBK.</font></p>
    </div>
    <span title="只能输入一个账号信息">Erp账号: </span><input id="erpUserDDL"><input id="btnDDL"
                                                                         style="margin-left: 5px; text-align: center; width: 110px;"
                                                                         type="button" value="执行sql,开始下载">
    <br>
    <br>

    <div>
        <form>
            <textarea id="sqlDDL" name="code" style="height: 100px; display: none;"></textarea>
        </form>
    </div>


</div>
<script type="text/javascript">
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

    window.ddlEditor = CodeMirror.fromTextArea(document.getElementById('sqlDDL'), {
        mode: mime,
        indentWithTabs: true,
        smartIndent: true,
        lineNumbers: true,
        matchBrackets: true,
        autofocus: false
    });

    function execDDL() {
        var sql = window.ddlEditor.getSelection();
        if ($.trim(sql) == "") {
            sql = window.ddlEditor.getValue();
            if ($.trim(sql) == "") {
                return;
            }
        }

        var erpUser = $.trim($("#erpUserDDL").val());

        if (erpUser == '') {
            alert("请输入接收邮件的Erp账号");
        }

        var pararms = {
            title: [],
            cube: "data_to_testdb",
            type: "sql",
            schema: "null",
            query: sql,
            condition:"测试库导数"
        }

        $.ajax({
            type: "post",
            url: "../rest/dashboard/download/setDDL/" + erpUser,
            contentType: "application/json",
            data: JSON.stringify(pararms),
            success: function(data){
                alert("任务提交成功,请注意查收邮箱");
            },
            error:function(){
                alert("提交失败.")
            }
    });


    }

//    $("#btnDDL").bind("click", execDDL);

    $("#btnExecuteSql").bind("click", executeSql);
    $("#btnQuerySql").bind("click", querySql);
    
    $("#btnHideQueryResult").bind("click", function () {
    	$("#queryResultDiv").hide();
    });
    $("#btnShowQueryResult").bind("click", function() {
    	$("#queryResultDiv").show();
    });

    function executeSql() {
    	
        var sql = window.editor.getSelection();
        if ($.trim(sql) == "") {
            sql = window.editor.getValue();
            if ($.trim(sql) == "") {
                return;
            }
        }

        var dsArr = new Array();
        if ($("#chkJD").is(":checked")) {
            dsArr.push("jd");
        }
        if ($("#chkRB").is(":checked")) {
            dsArr.push("rb");
        }
        if ($("#chkIT").is(":checked")) {
            dsArr.push("it");
        }
        if ($("#chkTX").is(":checked")) {
            dsArr.push("tx");
        }
        if ($("#chkPOP").is(":checked")) {
            dsArr.push("pop");
        }

        var param = {
            sql: sql,
            dsArr: dsArr
        };

        ajaxPost("../rest/dashboard/admin/executeSql", param, function (data) {
            if (data) {
                for (var i = 0; i < data.length; i++) {
                    if (data[i] == -1) {
                        alert("更新失败了！！");
                        return;
                    }
                }
            }
            alert("更新成功！");
        }, function () {
            alert("更新失败了！！");
        })
    }
    
 
	function querySql() {
		
		var sql = window.editor.getSelection();
		if ($.trim(sql) == "") {
			sql = window.editor.getValue();
			if ($.trim(sql) == "") {
				return;
			}
		}

		var dsArr = new Array();

		if ($("#chkJD").is(":checked")) {
			dsArr.push("jd");
		} else if ($("#chkRB").is(":checked")) {
			dsArr.push("rb");
		} else if ($("#chkIT").is(":checked")) {
			dsArr.push("it");
		} else if ($("#chkTX").is(":checked")) {
			dsArr.push("tx");
		} else if ($("#chkPOP").is(":checked")) {
			dsArr.push("pop");
		} else {
			dsArr.push("jd");
		}

		var param = {
			sql : sql,
			ds : dsArr[0]
		};

		ajaxPost("../rest/dashboard/admin/querySql", param, function(data) {
			showTableResult($("#queryResultDiv"), data);
		}, function() {
			alert("查询失败了！！");
		})
	}
</script>
<%
    }
%>
</body>
</html>

"use strict";
function isWebkitForHms(){
	return document.body.style.WebkitBoxShadow !== undefined;
}
/**
 * 闭包执行
 */
(function(){
	
    /**
     * 在Date对象中添加format方法
     * @param format  format 字符串
     * @returns {*}
     */
    Date.prototype.format = function(format){
        var o = {
            "M+" : this.getMonth()+1, //month
            "d+" : this.getDate(), //day
            "h+" : this.getHours(), //hour
            "m+" : this.getMinutes(), //minute
            "s+" : this.getSeconds(), //second
            "q+" : Math.floor((this.getMonth()+3)/3), //quarter
            "S" : this.getMilliseconds() //millisecond
        }
        if(/(y+)/.test(format)) {
            format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
        }
        for(var k in o) {
            if(new RegExp("("+ k +")").test(format)) {
                format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
            }
        }
        return format;
    };
    //+---------------------------------------------------  
	//| 把日期分割成数组  
	//+---------------------------------------------------  
	Date.prototype.toArray = function(){   
	    var myDate = this;  
	    var myArray = Array();  
	    myArray[0] = myDate.getFullYear();  
	    myArray[1] = myDate.getMonth();  
	    myArray[2] = myDate.getDate();  
	    myArray[3] = myDate.getHours();  
	    myArray[4] = myDate.getMinutes();  
	    myArray[5] = myDate.getSeconds();  
	    return myArray;  
	};
	//+---------------------------------------------------  
	//| 取得日期数据信息  
	//| 参数 interval 表示数据类型  
	//| y 年 m月 d日 w星期 ww周 h时 n分 s秒  
	//+---------------------------------------------------  
	Date.prototype.DatePart = function(interval){   
	    var myDate = this;  
	    var partStr='';  
	    var Week = ['日','一','二','三','四','五','六'];  
	    switch (interval)  
	    {   
	        case 'y' :partStr = myDate.getFullYear();break;  
	        case 'm' :partStr = myDate.getMonth()+1;break;  
	        case 'd' :partStr = myDate.getDate();break;  
	        case 'w' :partStr = Week[myDate.getDay()];break;  
	        case 'ww' :partStr = myDate.WeekNumOfYear();break;  
	        case 'h' :partStr = myDate.getHours();break;  
	        case 'n' :partStr = myDate.getMinutes();break;  
	        case 's' :partStr = myDate.getSeconds();break;  
	    }  
	    return partStr;  
	};
	//+---------------------------------------------------  
	//| 取得当前日期所在月的最大天数  
	//+---------------------------------------------------  
	Date.prototype.MaxDayOfDate = function(){   
	    var myDate = this;  
	    var ary = myDate.toArray();  
	    var date1 = (new Date(ary[0],ary[1]+1,1));  
	    var date2 = date1.dateAdd(1,'m',1);  
	    var result = dateDiff(date1.Format('yyyy-MM-dd'),date2.Format('yyyy-MM-dd'));  
	    return result;  
	};
	//+---------------------------------------------------  
	//| 取得当前日期所在周是一年中的第几周  
	//+---------------------------------------------------  
	Date.prototype.WeekNumOfYear = function(){   
	    var myDate = this;  
	    var ary = myDate.toArray();  
	    var year = ary[0];  
	    var month = ary[1]+1;  
	    var day = ary[2];  
	    document.write('< script language=VBScript\> \n');  
	    document.write('myDate = Datue('+month+'-'+day+'-'+year+') \n');  
	    document.write('result = DatePart("ww", myDate) \n');  
	    document.write(' \n');  
	    return result;  
	};
    //---------------------------------------------------  
	// 判断闰年  
	//---------------------------------------------------  
	Date.prototype.isLeapYear = function(){
	    return (0==this.getYear()%4&&((this.getYear()%100!=0)||(this.getYear()%400==0)));   
	};
	//---------------------------------------------------  
	// 日期格式化  
	// 格式 YYYY/yyyy/YY/yy 表示年份  
	// MM/M 月份  
	// W/w 星期  
	// dd/DD/d/D 日期  
	// hh/HH/h/H 时间  
	// mm/m 分钟  
	// ss/SS/s/S 秒  
	//---------------------------------------------------  
	Date.prototype.Format = function(formatStr){   
	    var str = formatStr;   
	    var Week = ['日','一','二','三','四','五','六'];  
	  
	    str=str.replace(/yyyy|YYYY/,this.getFullYear());   
	    str=str.replace(/yy|YY/,(this.getYear() % 100)>9?(this.getYear() % 100).toString():'0' + (this.getYear() % 100));   
	    // 月份在JS里面是从0开始的
	    str=str.replace(/MM/,this.getMonth()>=9?(this.getMonth()+1).toString():'0' + (this.getMonth()+1));   
	    str=str.replace(/M/g,this.getMonth());   
	  
	    str=str.replace(/w|W/g,Week[this.getDay()]);   
	  
	    str=str.replace(/dd|DD/,this.getDate()>9?this.getDate().toString():'0' + this.getDate());   
	    str=str.replace(/d|D/g,this.getDate());   
	  
	    str=str.replace(/hh|HH/,this.getHours()>9?this.getHours().toString():'0' + this.getHours());   
	    str=str.replace(/h|H/g,this.getHours());   
	    str=str.replace(/mm/,this.getMinutes()>9?this.getMinutes().toString():'0' + this.getMinutes());   
	    str=str.replace(/m/g,this.getMinutes());   
	  
	    str=str.replace(/ss|SS/,this.getSeconds()>9?this.getSeconds().toString():'0' + this.getSeconds());   
	    str=str.replace(/s|S/g,this.getSeconds());   
	  
	    return str;   
	};
	//+---------------------------------------------------  
	//| 日期计算  
	//+---------------------------------------------------  
	Date.prototype.DateAdd = function(strInterval, Number){   
	    var dtTmp = this;  
	    switch (strInterval) {   
	        case 's' :return new Date(Date.parse(dtTmp) + (1000 * Number));  
	        case 'n' :return new Date(Date.parse(dtTmp) + (60000 * Number));  
	        case 'h' :return new Date(Date.parse(dtTmp) + (3600000 * Number));  
	        case 'd' :return new Date(Date.parse(dtTmp) + (86400000 * Number));  
	        case 'w' :return new Date(Date.parse(dtTmp) + ((86400000 * 7) * Number));  
	        case 'q' :return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + Number*3, dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());  
	        case 'm' :return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + Number, dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());  
	        case 'y' :return new Date((dtTmp.getFullYear() + Number), dtTmp.getMonth(), dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());  
	    }  
	};
	//+---------------------------------------------------  
	//| 比较日期差 dtEnd 格式为日期型或者有效日期格式字符串  
	//+---------------------------------------------------  
	Date.prototype.DateDiff = function(strInterval, dtEnd){   
	    var dtStart = this;  
	    if (typeof dtEnd == 'string' )//如果是字符串转换为日期型  
	    {   
	        dtEnd = StringToDate(dtEnd);  
	    }  
	    switch (strInterval) {   
	        case 's' :return parseInt((dtEnd - dtStart) / 1000);  
	        case 'n' :return parseInt((dtEnd - dtStart) / 60000);  
	        case 'h' :return parseInt((dtEnd - dtStart) / 3600000);  
	        case 'd' :return parseInt((dtEnd - dtStart) / 86400000);  
	        case 'w' :return parseInt((dtEnd - dtStart) / (86400000 * 7));  
	        case 'm' :return (dtEnd.getMonth()+1)+((dtEnd.getFullYear()-dtStart.getFullYear())*12) - (dtStart.getMonth()+1);  
	        case 'y' :return dtEnd.getFullYear() - dtStart.getFullYear();  
	    }  
	}; 
	//+---------------------------------------------------  
	//| 日期输出字符串，重载了系统的toString方法  
	//+---------------------------------------------------  
	Date.prototype.toString = function(showWeek){   
	    var myDate= this;  
	    var str = myDate.toLocaleDateString();  
	    if (showWeek)  
	    {   
	        var Week = ['日','一','二','三','四','五','六'];  
	        str += ' 星期' + Week[myDate.getDay()];  
	    }  
	    return str;  
	};
    // 在数组某一位置插入元素
    Array.prototype.insert = function (index, item) {
	  this.splice(index, 0, item);
	};
	//删除数组某一项值
    Array.prototype.baoremove = function(dx) {
        if(isNaN(dx) || dx> this.length){
        	return false;
        }
        this.splice(dx,1);
    };
}());

//日期处理工具包
var DateUtils = {
		
	/**日期格式化*/
	YYYY_MM_DD_HH_MM_SS: "yyyy-MM-dd hh:mm:ss",
	YYYY_MM_DD: "yyyy-MM-dd",
	YYYY: "yyyy",
	MM_DD: "MM-dd",
	HH_MM: "hh:mm",
	MM_DD_HH_MM_SS: "MM-dd hh:mm:ss",
	
	/**
	 * 获取某个日期加上几天之后的日期字符串，支持负数
	 * @param dateParameter 某日起字符串
	 * @param num 追加天数
	 * @returns {String} 返回追加天数之后字符串
	 */
	addByTransDate : function(dateParameter, num) {
		var translateDate = "", dateString = "", monthString = "", dayString = "";
		translateDate = dateParameter.replace("-", "/").replace("-", "/");
		var newDate = new Date(translateDate);
		newDate = newDate.valueOf();
		newDate = newDate + num * 24 * 60 * 60 * 1000;
		newDate = new Date(newDate);
		// 如果月份长度少于2，则前加 0 补位
		if ((newDate.getMonth() + 1).toString().length == 1) {
			monthString = 0 + "" + (newDate.getMonth() + 1).toString();
		} else {
			monthString = (newDate.getMonth() + 1).toString();
		}
		// 如果天数长度少于2，则前加 0 补位
		if (newDate.getDate().toString().length == 1) {
			dayString = 0 + "" + newDate.getDate().toString();
		} else {
			dayString = newDate.getDate().toString();
		}
		dateString = newDate.getFullYear() + "-" + monthString + "-"
				+ dayString;
		return dateString;
	},
	
	/**
	 * 获取某日期为周几
	 * @param dateParameter 某日期字符串
	 * @returns 周几
	 */
	getWeekNum: function(dateParameter) {
		var translateDate = "", dateString = "", monthString = "", dayString = "";
		translateDate = dateParameter.replace("-", "/").replace("-", "/");
		var newDate = new Date(translateDate);
		return newDate.getDay() === 0 ? "周日"
					: newDate.getDay() === 1 ? "周一"
						: newDate.getDay() === 2 ? "周二"
							: newDate.getDay() === 3 ? " 周三"
								: newDate.getDay() === 4 ? "周四"
									: newDate.getDay() === 5 ? "周五"
										: newDate.getDay() === 6 ? "周六"
											: "周日";
	},
	
	/**
	 * 根据时间戳格式化日期
	 * @param timeVal 时间戳
	 * @param format 日期格式化类型
	 */
	formatDate4TimeVal: function(timeVal, format) {
		var date = null;
		if (typeof timeVal == "object") {
			date = new Date(timeVal.time || timeVal.getTime());
		} else {
			date = new Date(timeVal)
		}
		return date.Format(format);
	},
	/**
	 * 格式化日期字符串
	 * @param dateStr 日期字符串
	 * @param format 格式化
	 * @returns 格式化之后日期字符串
	 */
	formatDateString: function(dateStr, format) {
		var translateDate = "", dateString = "", monthString = "", dayString = "";
		translateDate = dateStr.replace("-", "/").replace("-", "/");
		var newDate = new Date(translateDate);
		return newDate.format(format);
	},
	/**
	 * 获取时间，老数据中存在时间没有添加格式，故需处理一下
	 * @param dateStr 时间字符串
	 * @returns 带格式时间字符串
	 */
	getTime4HM: function(dateStr) {
		// 如果存在不带格式的时间字符串
		var outArray = [];
		if (dateStr && dateStr.indexOf(":") <= 0) {
			var chars = dateStr.split("");
			_.map(chars, function(char, i) {
				if (i < 4) {
					if (i > 1 && i % 2 === 0) {
						outArray.push(":");
					}
					outArray.push(char);
				}
			});
		}
		return outArray.join("");
	},
	/**
	 * 获取从1970年到当前年份年份列表
	 */
	getYearList: function() {
		var firstDate = new Date(0);
		var firstYear = firstDate.getFullYear();
		var nowDate = new Date();
		var nowYear = nowDate.getFullYear();
		var yearList = [];
		for (var i = (nowYear - firstYear); i >= 0; i--) {
			yearList.push(firstYear + i);
		}
		return yearList;
	},
	/**
	 * 获取每天00:00到23:00时间列表
	 */
	getTimeList: function() {
		var timeList = [];
		for (var i = 23; i >= 0; i--) {
			timeList.push((i = (i < 10 ? "0" + i : i)) + ":00");
		}
		return timeList;
	}
};

//tip 弹出层
var Tip = {
		
		/**
		 * 提示信息
		 * @param content 提示类容
		 * @param type success、error
		 */
		message: function(content, type) {
			var extraClasses = "messenger-fixed messenger-on-bottom messenger-on-right";
			Messenger.options = {
	          extraClasses: extraClasses,
	          theme: "flat"
	        };
			Messenger().post({
				message: content,
				type: type || "success",
				showCloseButton: true,
				hideAfter:4
			});
		}
};

// 打印工具
var Print = {
		/**
		 * 获取二维码图片
		 * @param content 二维码内容
		 * @param tag 二维码tag
		 * @returns {String} 二维码流数据
		 */
		getPrintI2dimCodeUrl: function(content, tag) {
			var url = contentPath + "/i2dimcodes/userI2DimCode?content="
			+ encodeURIComponent(content);
			if(tag){
				url = url + "&tag=" + tag;
			}
			return url;
		},
		
		/**
		 * 打印二维码
		 * @param content 二维码内容
		 * @param tag 二维码tag
		 * @param printI2dimCodes 打印列表数据
		 * @param hotelName 酒店名称
		 */
		goToPrintPage: function(content, tag, hotelName, printI2dimCodes) {
			if (content && tag) {
				// 放入window下
				window.printList = [{
					content: content,
					tag: tag
				}];
			} else if (!content && !tag){
				// 放入window下
				window.printList = printI2dimCodes;
			} else if (content && !tag){
				// 放入window下
				window.printList = [{
					content: content
				}];
			}
			// 酒店名称
			window.hotelName = hotelName;
			window.open(contentPath + "/print/print.jsp", "new");
		}
};
  
//+---------------------------------------------------  
//| 求两个时间的天数差 日期格式为 YYYY-MM-dd   
//+---------------------------------------------------  
function daysBetween(DateOne,DateTwo)  
{   
    var OneMonth = DateOne.substring(5,DateOne.lastIndexOf ('-'));  
    var OneDay = DateOne.substring(DateOne.length,DateOne.lastIndexOf ('-')+1);  
    var OneYear = DateOne.substring(0,DateOne.indexOf ('-'));  
  
    var TwoMonth = DateTwo.substring(5,DateTwo.lastIndexOf ('-'));  
    var TwoDay = DateTwo.substring(DateTwo.length,DateTwo.lastIndexOf ('-')+1);  
    var TwoYear = DateTwo.substring(0,DateTwo.indexOf ('-'));  
  
    var cha=((Date.parse(OneMonth+'/'+OneDay+'/'+OneYear)- Date.parse(TwoMonth+'/'+TwoDay+'/'+TwoYear))/86400000);   
    return Math.abs(cha);  
}  
  
//+---------------------------------------------------  
//| 日期合法性验证  
//| 格式为：YYYY-MM-DD或YYYY/MM/DD  
//+---------------------------------------------------  
function IsValidDate(DateStr)   
{   
    var sDate=DateStr.replace(/(^\s+|\s+$)/g,''); //去两边空格;   
    if(sDate=='') return true;   
    //如果格式满足YYYY-(/)MM-(/)DD或YYYY-(/)M-(/)DD或YYYY-(/)M-(/)D或YYYY-(/)MM-(/)D就替换为''   
    //数据库中，合法日期可以是:YYYY-MM/DD(2003-3/21),数据库会自动转换为YYYY-MM-DD格式   
    var s = sDate.replace(/[\d]{ 4,4 }[\-/]{ 1 }[\d]{ 1,2 }[\-/]{ 1 }[\d]{ 1,2 }/g,'');   
    if (s=='') //说明格式满足YYYY-MM-DD或YYYY-M-DD或YYYY-M-D或YYYY-MM-D   
    {   
        var t=new Date(sDate.replace(/\-/g,'/'));   
        var ar = sDate.split(/[-/:]/);   
        if(ar[0] != t.getYear() || ar[1] != t.getMonth()+1 || ar[2] != t.getDate())   
        {   
            //alert('错误的日期格式！格式为：YYYY-MM-DD或YYYY/MM/DD。注意闰年。');   
            return false;   
        }   
    }   
    else   
    {   
        //alert('错误的日期格式！格式为：YYYY-MM-DD或YYYY/MM/DD。注意闰年。');   
        return false;   
    }   
    return true;   
}   
  
//+---------------------------------------------------  
//| 日期时间检查  
//| 格式为：YYYY-MM-DD HH:MM:SS  
//+---------------------------------------------------  
function CheckDateTime(str)  
{   
    var reg = /^(\d+)-(\d{ 1,2 })-(\d{ 1,2 }) (\d{ 1,2 }):(\d{ 1,2 }):(\d{ 1,2 })$/;   
    var r = str.match(reg);   
    if(r==null)return false;   
    r[2]=r[2]-1;   
    var d= new Date(r[1],r[2],r[3],r[4],r[5],r[6]);   
    if(d.getFullYear()!=r[1])return false;   
    if(d.getMonth()!=r[2])return false;   
    if(d.getDate()!=r[3])return false;   
    if(d.getHours()!=r[4])return false;   
    if(d.getMinutes()!=r[5])return false;   
    if(d.getSeconds()!=r[6])return false;   
    return true;   
}  
  
//+---------------------------------------------------  
//| 字符串转成日期类型   
//| 格式 MM/dd/YYYY MM-dd-YYYY YYYY/MM/dd YYYY-MM-dd  
//+---------------------------------------------------  
function StringToDate(DateStr)  
{   
  
    var converted = Date.parse(DateStr);  
    var myDate = new Date(converted);  
    if (isNaN(myDate))  
    {   
        //var delimCahar = DateStr.indexOf('/')!=-1?'/':'-';  
        var arys= DateStr.split('-');  
        myDate = new Date(arys[0],--arys[1],arys[2]);  
    }  
    return myDate;  
} 


/***************************************以下代码待封装*******************************************/
// 路径配置
window.require && require.config({
	paths : {
		echarts : contentPath + '/main/plug-in/echarts'
	}
});

// 通用的pie函数
function genCommonPieChart(div, title, maxPmsRevenues, pmsRevenues, unit){
	if (maxPmsRevenues == 0 && pmsRevenues == 0) {
		jQuery("#"+div).css({
			  "text-align":"center",
			  "line-height":"260px"
			  });
		return;
	}
	var labelTop = {
			normal : {
				label : {
					show : true,
					position : 'center',
					formatter : function(params) {
						return params.value + unit + "";// + params.name
					},
					textStyle : {
						align: 'center',
						baseline: 'middle',
						fontSize: 25
					}
				},
				labelLine : {
					show : false
				}
			}
		};
	var labelBottom = {
			normal : {
				color : '#ccc',
				label : {
					show : true,
					position : 'center'
				},
				labelLine : {
					show : false
				}
			},
			emphasis : {
				color : 'rgba(0,0,0,0)'
			}
		};
	var datas = [{
		name : '今日',
		value : pmsRevenues,
		itemStyle : labelTop
		}, {
		name : '历史最高',
		value : maxPmsRevenues,
		itemStyle:{
			normal:{
				color : '#ccc',
				label : {
					show : true,
					position : 'center',
					formatter : function(params) {
						return "\n今天";
					},
					textStyle : {
						align: 'center',
						baseline: 'top',
						fontSize: 15,
						color : '#FF7F50'
					}
				},
				labelLine : {
					show : false
				}
			}
		}
	} ];
	require([ 'echarts', 'echarts/chart/pie' // 使用柱状图就加载pie模块，按需加载
	], function(ec) {
		// 基于准备好的dom，初始化echarts图表
		var myChart = ec.init(document.getElementById(div));
		var radius = [ 60, 80 ];
		var labelFromatter = {
				normal : {
					label : {
						formatter : function(params) {
							return params.value
						},
						textStyle : {
							baseline : 'top'
						}
					}
				},
			};
		var option = {
			title : {
				text : title,
				x : 'left'
			},
			tooltip : {
				trigger : 'item',
				formatter : "{a} <br/>{b} : {c} ({d}%)"
			},
			series : [ {
				type : 'pie',
				center : [ '50%', '50%' ],
				radius : radius,
				x : '0%', // for funnel
				data : datas
			} ]
		};

		// 为echarts对象加载数据
		myChart.setOption(option);
	});
}

// 显示月模式图表
function showMonthCommonLineChart(divId, legendData, seriesData, unit){
	if (seriesData.length > 0 && seriesData[0]['data'] && seriesData[0]['data'].length == 0) {
		jQuery("#p_"+divId).css("display","none"); 
		return;
	}
	require([ 'echarts', 'echarts/chart/line' // 使用柱状图就加载line模块，按需加载
	          ], function(ec) {
	          	// 基于准备好的dom，初始化echarts图表
	          	var myChart = ec.init(document.getElementById(divId));
	          	var option = {
	          		    tooltip: {
	          		        trigger: 'item',
	          		        formatter: function(params) {
	          		        	var date = new Date(params.value[0]);
	          		            data = date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
	          		            return data + '<br/><span style="font-size:12px;">' + params.value[1] + unit +'</span>';
	          		        }
	          		    },
	          		    dataZoom: {
	          		        show: false,
	          		        start: 0
	          		    },
	          		    legend: {
	          		        data: legendData
	          		    },
	          		    grid: {
	          		    	x: '11%',
	          		    	y:'11%',
	          		        x2: 30,
	          		    	y2: 50
	          		    },
	          		    xAxis: [{
	          		        type: 'time',
	          		        splitNumber: 10
	          		    }],
	          		    yAxis: [{
	          		        type: 'value'
	          		    }],
	          		    series: seriesData,
	          		    noDataLoadingOption: {
		  	          	    text : '暂无数据',
			          	    effect : 'Spin',
			          	    textStyle : {
			          	        fontSize : 20
			          	    	}
	          		  	}
	          		};

	          	// 为echarts对象加载数据
	          	myChart.setOption(option);
	          });
}

// 显示周模式图表
function showWeekCommonLineChart(divId, legendData, seriesData, xAxisData){
	if (seriesData.length > 0 && seriesData[0]['data'] && seriesData[0]['data'].length == 0) {
		jQuery("#p_"+divId).css("display","none");
		return;
	}
	require([ 'echarts', 'echarts/chart/line' // 使用柱状图就加载line模块，按需加载
	          ], function(ec) {
      	// 基于准备好的dom，初始化echarts图表
      	var myChart = ec.init(document.getElementById(divId));
		var option = {
		    tooltip : {
		        trigger: 'axis'
		    },
		    legend: {
		        data:legendData
		    },
		    grid: {
  		    	x: '11%',
  		    	y:'11%',
  		        x2: 30,
  		    	y2: 50
  		    },
		    calculable : true,
		    xAxis : xAxisData,
		    yAxis : [
		        {
		            type : 'value',
		            axisLabel : {
		                formatter: '{value}'
		            }
		        }
		    ],
		    series : seriesData,
		    noDataLoadingOption: {
	          	    text : '暂无数据',
          	    effect : "dynamicLine",
          	    textStyle : {
          	        fontSize : 14
          	    	}
  		  	}
		};
		// 为echarts对象加载数据
	  	myChart.setOption(option);
  	});             
}

// 构建图标数据
function makeCommonLineChartData(divId, huanbi, type, datas, field){
	var data = [];
	var legend_data =  ['当期'];
	if (huanbi) {
		legend_data.push('上期');
	}
	// 星期模板， new Date().getDay() == 0 为周日，以此类推
	var dates = [];
	var days = -1;
	if (type == 'week') {// 周的情况
		days = 7;
	} else if (type == 'month') {
		days = 30;
	}
	var series = [];
	var xAxisData = [];
	for (var j = 0; j < legend_data.length; j++) {
		var _data = [];
		var len = 1;
		for (var a = j * days; a < datas.length; a++) {
			var his = datas[(a)];
			if (!his || _data.length == days) {
				break;
			}
			var d = new Date(his['date']);
			//console.log(Math.ceil(Math.random()*7));
			if (type=='week') {
				if (j == 0) {
					xAxisData.splice(0, 0, a == 0 ? "今天":getWeekName(d));
				}
				_data.splice(0, 0,his[field]||0);
				//_data.push(Math.ceil(Math.random()*37));// 测试数据
			} else if(type == 'month'){
				_data.splice(0, 0,[new Date(his['date']), his[field]||0]);
				//_data.push([new Date(2014, 9, 1, 0, a * 10000), Math.ceil(Math.random()*37)]);// 测试数据
			}
			
		}
		if (_data.length < days) {
			var datacount  =days - _data.length;
			var yesterday = new Date().DateAdd('d', -1);
			for(var k = 0; k < datacount; k++){
				if (type == 'week') {
					if(j == 0){
						xAxisData.splice(0, 0, getWeekName(yesterday));
					}
					_data.splice(0, 0, 0);
				} else if(type=='month'){
					_data.splice(0, 0, [yesterday, 0]);
				}
				yesterday = yesterday.DateAdd('d', -1);
			}
			
		}
		series.push({
	        name: legend_data[j],
	        type: 'line',
	        
	        data: _data
	    });
	}
	var xAxis = [ { type : 'category', boundaryGap : false, data : xAxisData } ];
	//genJdjyyj(legend_data, dates, series_data);
	if (type == 'week') {
		showWeekCommonLineChart(divId, legend_data, series, xAxis);
	} else if(type == 'month'){
		showMonthCommonLineChart(divId, legend_data, series, '元');
	}
}

// 酒店经营业绩(暂时不启用)
function genJdjyyj(legend_data, weeks, series_data){
	require([ 'echarts', 'echarts/chart/line' // 使用柱状图就加载line模块，按需加载
	], function(ec) {
		// 基于准备好的dom，初始化echarts图表
		var myChart = ec.init(document.getElementById('hotel_home_3'));
		var option = {
			tooltip : {
				trigger : 'axis'
			},
			legend : {
				x : 'right',
				y : 'bottom',
				data : legend_data
			},
			calculable : true,
			xAxis : [ {
				type : 'category',
				boundaryGap : false,
				data : weeks
			} ],
			yAxis : [ {
				type : 'value'
			} ],
			series :series_data
		};
	
		// 为echarts对象加载数据
		myChart.setOption(option);
	});
}

// 酒店房数
var roomNum = 0;
// 日期
var dateInfo = "";
// 最大价格信息
var maxPrice = {};
// 酒店的今天订单、入住信息
var curData = {};
// 酒店的历史60天的订单、入住信息
var hisDatas = [];
// 酒店前台切客收益数据
var checkerBill = [];

var JdjyyjType = "week";
var otaddsType = "week";
var otajyeType = "month";
var thisWeekOrderNum = 0;
var lastWeekOrderNum = 0;

function loadHomeDatas(successFun){
	//获取数据
	jQuery.ajax({
		type : 'get',
		cache : false,
		timeout : 30000,
		dataType : 'json',
		async : true,
		url : contentPath + '/home/homeDatas',
		success : function(res) {
			/**数据格式	
				"dateInfo":"2015-05-10 星期日",
				"curData":{},
				"hisDatas":[],
				"maxPrice":{
					"hotelId":486,
					"otaCheckinNum":0,
					"pmsRevenues":0,
					"otaRevenues":0
				},
				"checkerBill":[]
				"roomNum":0,
			*/

			//数据解析
			var data = res.attribute || {};
			dateInfo = data['dateInfo'] || "";
			curData = data["curData"] || {
				"date": new Date().Format("yyyy-MM-dd"),
				"pmsOrderNum": 0,
				"pmsCheckinNum": 0,
				"otaOrderNum": 0,
				"otaCheckinNum": 0,
				"otaRevenues": 0
			};
			hisDatas = data['hisDatas'] || [];
			maxPrice = data['maxPrice'] || {};
			// checkerBill = data['checkerBill'] || [];
			thisWeekOrderNum = data["orderNum"];
			roomNum = parseInt(curData['hotelRoomNum'] || data['roomNum']);

			//渲染数据
			genPageDefaultData();

			//布局页面
			setTimeout(function() {
				jQuery("#hotel_home_last_row_right").height(jQuery("#hotel_home_last_row_hei").height() - 13);
			}, 500);

			if (successFun && typeof successFun == "function") {
				successFun();
			}
		}
	});
}

//构建页面图表，初始化数据
function genPageDefaultData(){
	var _yesterday = hisDatas.length ? (hisDatas[1] || {}) : {};
	if (new Date().DateAdd("d", -1).Format("yyyy-MM-dd") 
		!= new Date(_yesterday['date']).Format("yyyy-MM-dd")) {
		_yesterday = {
			"pmsOrderNum": 0,
			"pmsCheckinNum": 0
		};
	}/*
	var pmsOrderNum = curData["pmsOrderNum"] || 0;// 今日pms订单间夜数
	var pmsCheckinNum = curData["pmsCheckinNum"] || 0;// 今日入住
	var his_pmsOrderNum = _yesterday['pmsOrderNum']||0;// 昨日订单间夜数
	var his_pmsCheckinNum = _yesterday['pmsCheckinNum']||0;// 昨日入住
*/    
	var pmsOrderNum = curData["otsOrderNum"] || 0;// 今日ots订单间数量 
	var pmsCheckinNum = curData["otsRentOrderNum"] || 0;// 今日ots入住数量
	var his_pmsOrderNum = _yesterday['otaOrderNum']||0;// 昨日订单间夜数
	var his_pmsCheckinNum = _yesterday['otaCheckinNum']||0;// 昨日入住

	// 1.日期信息
	jQuery('#div_dateInfo').text(dateInfo);

	// 2.预定信息
	jQuery('#div_pmsddjys').text(pmsOrderNum);
	jQuery('#i_yesterday_order_num').attr('title', "昨日"+ his_pmsOrderNum + "单");
	if (pmsOrderNum < his_pmsOrderNum) {// 页面本来是上升，如果下降了
		jQuery("#i_yesterday_order_num").removeClass("glyphicon-arrow-up hotel-home-icon-up");
		jQuery("#i_yesterday_order_num").addClass("glyphicon-arrow-down hotel-home-icon-down");
	}
	if (pmsOrderNum != his_pmsOrderNum || pmsOrderNum > his_pmsOrderNum) {// 没有变化，隐藏箭头
		jQuery("#i_yesterday_order_num").css('visibility','visible');
	}

	// 3.入住信息
	jQuery('#div_pmsjys').text(pmsCheckinNum);
	jQuery('#i_yesterday_checkin_num').attr('title', "昨日"+his_pmsCheckinNum+"单");
	if (pmsCheckinNum > his_pmsCheckinNum) {// 上升了
		jQuery("#i_yesterday_checkin_num").removeClass("glyphicon-arrow-down hotel-home-icon-down");
		jQuery("#i_yesterday_checkin_num").addClass("glyphicon-arrow-up hotel-home-icon-up");
	}
	if (pmsCheckinNum != his_pmsCheckinNum || pmsCheckinNum < his_pmsCheckinNum) {// 没有变化，隐藏箭头
		jQuery("#i_yesterday_checkin_num").css('visibility','visible');
	}

	// 4.昨日汇总
	jQuery('#b_zryd').text(_yesterday['pmsOrderNum']||0);
	jQuery('#b_zrrz').text(_yesterday['pmsCheckinNum']||0);
	jQuery('#hygxsy').text(0);// 目前设为0
	
	// 5.出租率
	showCzlChart("day");

	// 6.bill（右上角）
	window.homePortalBillWeek2(1);
	
	// hisDatas.splice(0, 0, curData);

	// 7.经营业绩
	showYysrPie();
	showJdjyyj('week');
	
	// 8.眯客经营信息
	showOtaDdsPie();
	showOtaDds('week');
	
	// 9.眯客营业额
	showOtaJyePie();
	showOtaJye('month');
}

// 出租率1：显示出租率
function showCzlChart(type){
	var all = roomNum,// 总房间数
		pms = curData['pmsRentOrderNum'],// pms
		ota = curData['otsRentOrderNum'] || 0,// 眯客
		days = -1;// 眯客

	if (type == 'week') {// 周
		days = 7
	} else if (type == 'month') {// 月
		days = 30;
	}

	if (days > -1) {
		var addCount = hisDatas.length > days ? days : hisDatas.length;
		addCount = addCount == 0 ? 1 : addCount;
		if (hisDatas.length > 1) {
			for(var i = 0; i < hisDatas.length; i++){
				var h = hisDatas[i];
				all += h['hotelRoomNum'] || 0;
				pms += h['pmsCheckinNum'];
				ota += h['otaCheckinNum'];
				if(i == days - 1){ break; }
			}
			all = parseInt(all/addCount).toFixed(2);
			ota = parseInt(pms/addCount).toFixed(2);
			pms = parseInt(ota/addCount).toFixed(2);
		}
	}
	genCzl(all, ota, pms);
}
// 出租率2：构建出租率
function genCzl(all, ota, pms){
	all = parseFloat(all);
	ota = parseFloat(ota);
	pms = parseFloat(pms);
	var allwork = ota + pms;
	var labelTop = {
		normal : {
			color:"#e1e1e1",
			label : {
				show : true,
				position : 'center',
				formatter : function(params) {
					return parseInt(allwork / all*100)+'%';// + params.name
				},
				textStyle : {
					align: 'center',
					baseline: 'middle',
					fontSize: 30,
					color:'#024857'
				}
			},
			labelLine : {
				show : false
			}
		}
	};
	var labelBottom = {
		normal:{
			color:"#FACC51",
			label : {
				show : true,
				position : 'center',
				formatter : function(params) {
					return "\n今天";
				},
				textStyle : {
					align: 'center',
					baseline: 'top',
					fontSize: 15,
					color:'#024857'
				}
			},
			labelLine : {
				show : false
			}
		}
	};
	var datas = [{
		value : all - ota - pms,
		name : '空房',
		itemStyle : labelTop
	},{
		value : ota,
		name : '眯客',
		itemStyle : labelBottom
	}, {
		value : pms,
		name : '店铺',
		itemStyle: {
			normal:{
				color:"#1EB4B1",
				label : {
					show : false,
				},
				labelLine : {
					show : false
				}
			}
		}
	}];
	// 使用
	require([ 'echarts', 'echarts/chart/pie' // 使用柱状图就加载pie模块，按需加载
	], function(ec) {
		// 基于准备好的dom，初始化echarts图表
		var myChart = ec.init(document.getElementById('hotel_home_1'));
		var option = {
			tooltip : {
				trigger : 'item',
				formatter : "{a} <br/>{b} : {c} ({d}%)"
			},
			legend : {
				orient : 'vertical',
				x : 'right',
				y : 'bottom',
				data : [ '空房', '眯客', '店铺' ]
			},
			calculable : false,
			series : [ {
				name : '出租率',
				type : 'pie',
				radius : '90%',
				center : [ '40%', '50%' ],
				data : datas
			} ]
		};
		// 为echarts对象加载数据
		myChart.setOption(option);
	});
}
window.homePortalBillWeek2 = function(thisWeekFlag){
	var sendData = {
		'ruleCode':ruleCode
	};
	var thisDayInWeek = new Date().getDay();
	thisDayInWeek = thisDayInWeek == 0 ? 7 : thisDayInWeek;
	var thisDate = new Date();
	if (thisWeekFlag) {
		sendData.beginDateStr = thisDate.DateAdd("d", -thisDayInWeek).format("yyyy-MM-dd") + " 24:00:00";
		sendData.endDateStr = thisDate.format("yyyy-MM-dd") + " 24:00:00";
	} else {
		sendData.beginDateStr = thisDate.DateAdd("d", -thisDayInWeek-7).format("yyyy-MM-dd") + " 24:00:00";
		sendData.endDateStr = thisDate.DateAdd("d", -thisDayInWeek).format("yyyy-MM-dd") + " 24:00:00";
	}
	jQuery(".lx_loading").show();
	//获取数据
	jQuery.ajax({
		type : 'get',
		cache : false,
		timeout : 3000,
		dataType : 'json',
		data: sendData,
		async : true,
		url : contentPath + '/home/checkInfo',
		success : function(res) {
			//数据解析
			var data = res.attribute || {};
			if(ruleCode == '1001' || ruleCode == 1001){
				showRuleAData(data);
			}else{
				showRuleBData(data);
			}
			if(thisWeekFlag){
				jQuery("#a_bz").css({"color": "orangered"});
				jQuery("#a_sz").css({"color": "#ffffff"});
			} else {
				jQuery("#a_bz").css({"color": "#ffffff"});
				jQuery("#a_sz").css({"color": "orangered"});
			}	
			jQuery(".lx_loading").hide();
		},
		error : function(){
			jQuery(".lx_loading").hide();
		}
	});
	//显示A规则数据
	function showRuleAData(data){
		var lxsl = 0,
			zhlxsr = 0;
		lxsl = data["orderNum"];
		zhlxsr = lxsl * 5;
		jQuery("#i_lxsl").text(lxsl);
		jQuery("#i_zhlxsr").text(zhlxsr);
	};
	//显示B规则数据
	function showRuleBData(data){
		var cgdds=data.orderNum, //全部订单
			yfbtdds=data.yfbtdds, //预付补贴订单数
			dfbtdds=data.dfbtdds,	//到付补贴订单数
			yfbtje=data.yfbtje,	//预付补贴金额
			dfbtje=data.dfbtje,	//到付补贴金额
			kjzye=data.kjzye;	//可结账余额
		jQuery("#i_cgdds").text(cgdds);
		//jQuery("#i_kjzye").text(kjzye);
		jQuery("#i_kjzye").text(0);
		jQuery("#i_yfbtdds").text(yfbtdds);
		jQuery("#i_dfbtdds").text(dfbtdds);
		jQuery("#i_yfbtje").text(yfbtje);
		jQuery("#i_dfbtje").text(dfbtje);
	};
};

// 经营业绩1：营业收入
function showYysrPie(){
	var maxPmsRevenues = maxPrice['pmsRevenues'] || 0;
	// 营业收入改成昨天的营业收入
	var his = hisDatas[0]||{};
	var pmsRevenues = his['pmsRevenues'] || 0;
	genCommonPieChart("hotel_home_2", "营业收入", maxPmsRevenues, pmsRevenues, '元');
}
// 经营业绩2：酒店经营业绩，type：周、月
function showJdjyyj(type){
	JdjyyjType = type;
	var hb = jQuery("#chk_jdjyyj").is(":checked");
	makeCommonLineChartData('hotel_home_3', hb, type, hisDatas, 'pmsRevenues');
}

// 眯客经营信息1：ota 订单数
function showOtaDdsPie(){
	var maxOtaCheckinNum = maxPrice['otaCheckinNum'] || 0;
	var otaCheckinNum = curData['otaCheckinNum'] || 0;
	genCommonPieChart("hotel_home_4", "订单数", maxOtaCheckinNum, otaCheckinNum, '单');
}
// 眯客经营信息2：眯客经营信息，type：周、月
function showOtaDds(type){
	otaddsType = type;
	var hb = jQuery("#chk_ota_dds_hb").is(":checked");
	makeCommonLineChartData('hotel_home_5', hb, type, hisDatas, 'pmsRevenues');
}

// 眯客营业额1：ota 交易额
function showOtaJyePie(){
	var maxOtaRevenues = maxPrice['otaRevenues'] || 0;
	var otaRevenues = curData['otaRevenues'] || 0;
	genCommonPieChart("hotel_home_6", "交易额", maxOtaRevenues, otaRevenues, '元');
}
// 眯客营业额2：眯客交易额，type：周、月
function showOtaJye(type){
	otajyeType = type;
	var hb = jQuery("#chk_ota_jye_hb").is(":checked");
	makeCommonLineChartData('hotel_home_7', hb, type, hisDatas, 'pmsRevenues');
}

// 获取星期名称
function getWeekName(date){
	var weekMap = ['周日', '周一', '周二', '周三', '周四', '周五', '周六' ];
	return weekMap[date.getDay()];
}

// 获取loginName
window.hotelHomeShowMy2DBarCode = function() {
	jQuery.ajax({
		url: contentPath + "/user/qrcodeByUserId",
		type: "get",
		dataType: "json",
		success: function(data) {
			var i2DimCodeImg = jQuery("#hmsHomePortalContain").find("img");
			var imgSrc = contentPath + "/images/erwei-img2.png";
			if (data) {
				if (data.content) {
					i2DimCodeImg.data("qrCodeData", data);
					imgSrc = contentPath + "/i2dimcodes/userI2DimCode?content="
						+ encodeURIComponent(data.content) + "&tag=" + data.tag;
				}
			}
			i2DimCodeImg.attr("src", imgSrc);
			jQuery("#hmsHomePortalI2DimCodesModal").modal("show");
		},
		error: function(msg) {
			Tip.message("获取用户二维码数据异常", "error");
		}
	});
};

// 打印二维码
window.hotelHomePrintMyI2DimCode = function() {
	var i2DimCodeImg = jQuery("#hmsHomePortalContain").find("img");
	var qrCodeData = i2DimCodeImg.data("qrCodeData");
	if (qrCodeData && qrCodeData.content) {
		Print.goToPrintPage(qrCodeData.content, qrCodeData.tag, thisHotel.hotelName);
	} else {
		Tip.message("暂无二维码打印数据", "error");
	}
};

// 绑定按钮事件
function bindInputs(){
	jQuery('#l_day').bind("click", function(){  
		showCzlChart("day");  
		});
	jQuery('#l_week').bind("click", function(){  
		showCzlChart("week");  
		});
	jQuery('#l_month').bind("click", function(){  
		showCzlChart("month");  
		});
	jQuery('#l_jdjyyj_week').bind("click", function(){  
		showJdjyyj("week");  
	});
	jQuery('#l_jdjyyj_month').bind("click", function(){  
		showJdjyyj("month");  
	});
	jQuery('#chk_jdjyyj').bind("click", function(){
		showJdjyyj(JdjyyjType);
	});
	// 眯客经营信息
	jQuery('#chk_ota_dds_hb').bind("click", function(){
		showOtaDds(otaddsType);
	});
	jQuery('#l_ota_dds_week').bind("click", function(){
		showOtaDds('week');
	});
	jQuery('#l_ota_dds_month').bind("click", function(){
		showOtaDds('month');
	});
	// 交易额
	jQuery('#chk_ota_jye_hb').bind("click", function(){
		showOtaJye(otajyeType);
	});
	jQuery('#l_ota_jye_week').bind("click", function(){
		showOtaJye('week');
	});
	jQuery('#l_ota_jye_month').bind("click", function(){
		showOtaJye('month');
	});
}

//绑定窗口回调方法
/*window.myErweimaCallback = function() {
	return jQuery("#hotel_home_erweima_print").data("qrcodeData");
}*/

//打印二维码
function printMyErweima() {
	var printData = {};
	printData.i2DimDataObj = jQuery("#hotel_home_erweima_print").data("qrcodeData");
	printData.hotelName = hotel_name;
	window.printData = printData;
	window.open(app + "jsp/printMyErweima.jsp", "newwindow");
}

/**comment module*/

'use strict';

//module
angular.module('commentApp', [])

//controller
.controller('CommentController', ['$rootScope','$scope','CommentService','HmsModalService', function($rootScope,$scope,CommentService,HmsModalService) {
	//标题头	
    var columns = [{   field: 'creatTime',
                displayName: '时间',
                width:'8%'
            }, {
                field: 'hotelName',
                displayName: '酒店名称',
                width:'8%'
            }, {
                field: 'mId',
                displayName: '会员号',
                width:'8%'
            }, {
                field: 'loginName',
                displayName: '提交人',
                width:'8%'
            }, {
                field: 'orderId',
                displayName: '订单号',
                width:'8%'
            },  {
                field: 'score',
                displayName: '评论内容',
                width:'20%'
            }, {
                field: 'serviceScore',
                displayName: '回复内容',
                width:'20%'
            }, {
                field: 'status',
                displayName: '状态',
                width:'8%'
            }];
    $scope.columns = columns;
    $scope.active = 0;// 默认展示全部订单
    $scope.nav = [{
        'id':'unreply',
        'displayName':'待回复的评论',
        'action':'findsUnReply'
    },{
    	'id':'replied',
    	'displayName':'已回复的评论',
    	'action':'findsReplied'
    }];
	$scope.totalItems = 0; //数据总条数
	$scope.pageNum = 1; //当前页
	$scope.pageSize = 10; //每页显示数量
	$scope.numPages = 1; //总页数
	$scope.maxSize = 5; //当总页数大于多少的时候，分页开始显示...
	//当前页发生变化时操作
	$scope.pageChanged = function(){
		loadData();
	};
	//设置选中页
	$scope.setActive = function(index){
		$scope.active = index;
        loadData();
	};
    //回复
    $scope.doReply = function(data){
        $scope.reply = {
            id:data.id
        };
        HmsModalService.show({
            contentTmpl:contentPath+"/main/comment/template/comment-reply.html",
            scope:$scope,
        });
    };
    //删除回复内容
    $scope.deleteUnReply = function(id){
        angular.forEach($scope.rowsData,function(item,i){
            if(item.id==id){
                $scope.rowsData.splice(i,1);
                return;
            }
        })
    };
    //查看评价照片
    $scope.showPics = function(id){
        $rootScope.hmsLoading.show();
        CommentService.findsPics(id)
        .success(function(result){
            if(result && result.success && result.attribute.length>0){
                $scope.commentPic = {
                    'modalConfirmClose':function(){
                            HmsModalService.hide();
                        },
                    'pics':result.attribute
                };
                HmsModalService.show({
                    contentTmpl:contentPath+"/main/comment/template/comment-pics.html",
                    scope:$scope,
                });
            }else{
                Tip.message('没有评价照片');
            }
            $rootScope.hmsLoading.hide();
        })
        .error(function(error){
            $rootScope.hmsLoading.hide();
            Tip.message('获取评价照片数据异常', 'error');
        });
    };
    //加载数据
    function loadData(){
    	$rootScope.hmsLoading.show();
        var act = $scope.nav[$scope.active]['action'],
            pageNum = $scope.pageNum,
            pageSize = $scope.pageSize;
		CommentService[act](pageNum,pageSize)
			.success(function(data) {
                $rootScope.hmsLoading.hide();
				formatData(data);
	            $scope.rowsData = data.rows; 
				$scope.totalItems = parseInt(data.total);
            })
			.error(function(msg) {
                $rootScope.hmsLoading.hide();
                $scope.rowsData = []; 
                $scope.totalItems = 0;
				Tip.message('数据加载异常', 'error');
			});
    };
	//格式化数据
	function formatData(data) {
		var rows = data.rows;
		_.map(rows, function(row) {
			row.creatTime = DateUtils.formatDate4TimeVal(row.createTime, DateUtils.YYYY_MM_DD_HH_MM_SS);
            row.hotelName = $rootScope.thisHotel.hotelName;
            row.loginName = row.loginName ? row.loginName : '--';
            row.serviceScore = row.serviceScore ? row.serviceScore : '--';
            switch(row.status){
                case 4:
                    row.status = '未回复';
                    break;
                case 5:
                    row.status = '已回复';
                    break;
                default:
                    row.status = '--';
                    break;
            }
        });
	};
	//初始化调用
    loadData();
}])
/**
 * 回复评论 属于CommentController子类
 */
.controller('CommentReplyController', ['$scope','CommentService','HmsModalService',
 function($scope,CommentService,HmsModalService){
    $scope.confirm = function(){
        reply();
    };
    $scope.cancel = function(){
        HmsModalService.hide();
    };
    //回复操作
    function reply(){
        var id = $scope.reply.id,
            content = $scope.reply.content;
        if(!content){
            Tip.message('请输入回复内容','error');
            return;
        }
        CommentService.reply(id,content)
            .success(function(result){
                if(result.success){
                    deleteUnReply(id);
                    Tip.message('回复成功');
                    $scope.cancel();
                }else{
                    Tip.message('回复失败：'+result.errorMsg,'error');                   
                }
            })
            .error(function(error){
                Tip.message('回复失败：网络超时','error');               
            });
    };
    //在未回复列表中删除此回复的内容
    function deleteUnReply(id){
        $scope.deleteUnReply(id);
    };
}])
/**
 * 评论服务类
 */
.service('CommentService', ['HmsHttp', function(HmsHttp){    
    var url = contentPath+'/comment/';
    return{
        findsUnReply:function(pageNum,pageSize){
            var params = {
                'pageNum':pageNum,
                'pageSize':pageSize
            };
            return HmsHttp.post(url+'unReply',params);
        },
        findsReplied:function(pageNum,pageSize){
            var params = {
                'pageNum':pageNum,
                'pageSize':pageSize
            };
            return HmsHttp.post(url+'replied',params);
        },
        findsUnAudit:function(pageNum,pageSize){
            var params = {
                'pageNum':pageNum,
                'pageSize':pageSize
            };
            return HmsHttp.post(url+'replied',params);
        },
        findsPics:function(id){
            var params = {
                'id':id,
            };
            return HmsHttp.post(url+'getPics',params);

        }
    };  
}])
;
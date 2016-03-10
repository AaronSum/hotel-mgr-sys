//Template Cache
angular.module("hmsAngularLib", ["angularFileUpload", "mgcrea.ngStrap"])
.run(['$templateCache', function ($templateCache) {

    //通用-modal外轮廓(空)
    $templateCache.put('modal-outer.tmpl.html',
		'<div class="modal animated" tabindex="-1" role="dialog" aria-hidden="true">' +
			'<div class="modal-dialog {{_cls}}">' +
				'<div class="modal-content" ng-include="_contentTmpl"></div>' +
			'</div>' +
		'</div>'
	);

    //通用-modal外轮廓(包括头尾但不包括内容体)
    $templateCache.put('modal-content.tmpl.html',
		'<div class="modal-header" ng-show="title">' +
			'<button type="button" class="close" aria-label="Close" ng-click="closeModal()">' +
				'<span aria-hidden="true">&times;</span>' +
			'</button>' +
			'<h4 class="modal-title" ng-bind="title"></h4>' +
		'</div>' +
		'<div class="modal-body" ng-include="_bodyTmpl"></div>' +
		'<div class="modal-footer">' +
			'<button type="button" class="btn btn-default" ng-click="closeModal()">关闭</button>' +
		'</div>'
	);

    //通用-提示modal(带确定按钮)
    $templateCache.put('modal-confirm-notice.tmpl.html',
		'<div class="modal-header">' +
			'<button type="button" class="close" aria-label="Close" ng-click="close()">' +
				'<span aria-hidden="true">&times;</span>' +
			'</button>' +
			'<h4 class="modal-title">确认提示</h4>' +
		'</div>' +
		'<div class="modal-body" ng-bind="cusContent"></div>' +
		'<div class="modal-footer">' +
			'<button type="buFtton" class="btn btn-primary" ng-click="modalConfirm()">确定</button>' +
			'<button type="button" class="btn btn-default" ng-click="modalConfirmClose()">取消</button>' +
		'</div>' +
		'<i class="confirm_loading fa fa-spinner fa-pulse fa-3x" style="position: absolute;top: 50%;left: 50%;margin-top: -20px;margin-left: -20px;display:none;"></i>'
	);

		//base 确认保存(带确定按钮)
		$templateCache.put('base-confirm-notice.tmpl.html',
			'<div class="modal-header">' +
			'<button type="button" class="close" aria-label="Close" ng-click="close()">' +
			'<span aria-hidden="true">&times;</span>' +
			'</button>' +
			'<h4 class="modal-title">确认提示</h4>' +
			'</div>' +
			'<div class="modal-body" ng-bind="cusContent"></div>' +
			'<div class="modal-footer">' +
			'<button type="buFtton" class="btn btn-primary" ng-click="baseConfirm()">确定</button>' +
			'<button type="button" class="btn btn-default" ng-click="baseConfirmClose()">取消</button>' +
			'</div>' +
			'<i class="confirm_loading fa fa-spinner fa-pulse fa-3x" style="position: absolute;top: 50%;left: 50%;margin-top: -20px;margin-left: -20px;display:none;"></i>'
		);

    //酒店讯息-保存提示modal
    $templateCache.put('modal-save-notice.tmpl.html',
		'<div class="modal-header">' +
			'<button type="button" class="close" aria-label="Close" ng-click="close()">' +
				'<span aria-hidden="true">&times;</span>' +
			'</button>' +
			'<h4 class="modal-title">保存提示</h4>' +
		'</div>' +
		'<div class="modal-body">' +
			'是否需要保存？' +
		'</div>' +
		'<div class="modal-footer">' +
			'<button type="button" class="btn btn-default" ng-click="btn.action()" ng-repeat="btn in modalBtns" ng-bind="btn.name"></button>' +
		'</div>'
	);

    //财务结算-发起审核aside
    $templateCache.put('aside-sendCheck.tpl.html',
		'<div class="aside" tabindex="-1" role="dialog" style="width:600px;">' +
			'<div class="aside-dialog">' +
				'<div class="aside-content">' +
					//<!-- 表单 -->
					'<div>' +
						'<div class="aside-header" ng-show="title">' +
							'<button type="button" class="close" ng-click="$hide()">&times;</button>' +
							'<h4 class="aside-title" ng-bind="title"></h4>' +
						'</div>' +
						'<div class="aside-body" ng-show="bill.checkstatus == 1">' +
							'<textarea ng-if="!isMgr" class="form-control" ng-model="bill.currMemo" rows="10" maxlength="2000" placeholder="如果您对该账期信息有异议，请填写发起异议原因..."></textarea>' +
							'<textarea ng-if="isMgr" class="form-control" ng-model="bill.currMemo" rows="10" maxlength="2000" placeholder="请填写回复结果..."></textarea>' +
							'<p>最多输入2000字</p>' +
							'<div class="form-inline">' +
								'<label>调整账单金额：</label><input type="number" class="form-control" ng-model="bill.currAmount" style="width:160px;" placeholder="必填"/>&nbsp;元' +
							'</div>' +
							'<h6 style="color:#f00;font-style:italic;" class="pull-right">调整后金额——负数，为眯客支付酒店金额；正数，为眯客收取酒店金额</h6>' +
						'</div>' +
						//<!-- 查看审核明细 -->
						'<div style="padding:15px;">' +
							'<i class="fa fa-spinner fa-pulse fa-3x" ng-show="bill.loading" style="position: absolute;top: 50%;left: 50%;margin-top: -20px;margin-left: -20px;"></i>' +
							'<h5>审核明细</h5>' +
							'<p>账单金额 {{bill.billcost | currency }}元</p>' +
							'<p ng-show="bill.changecost">调整金额 {{bill.changecost | currency }}元</p>' +
							'<p>调整金额后 {{(bill.changecost ? bill.changecost + bill.billcost : 0) | currency}}元</p>' +
							'<div ng-show="bill.checkDetails.length" class="panel-group" data-allow-multiple="true" role="tablist" aria-multiselectable="true">' +
							  	'<div class="panel panel-default" ng-repeat="detail in bill.checkDetails">' +
							    	'<div class="panel-heading" role="tab">' +
							      		'<span class="panel-title">' +
									        '发起时间 {{detail.checktime}}' +
							      		'</span>' +
							      		// '<span class="panel-title">' + 
									      //   '回复时间 {{detail.backtime}}' + 
							      		// '</span>' + 
							      		'&nbsp;&nbsp;&nbsp;&nbsp;<span class="panel-title">' +
									        '状态 {{detail.checkstatustext}}' +
							      		'</span>' +
							    	'</div>' +
								    '<div ng-if="detail.checkstatus==2" class="panel-collapse" role="tabpanel">' +
										'<div class="panel-body">' +
											'<p>审核原因 {{detail.checkmemo}}</p>' +
											//'<p>账单金额 {{bill.billcost}}元</p>' + 
											//'<p>调整金额 {{bill.changecost}}元</p>' + 
											//'<p ng-show="bill.changecost">调整后金额 {{bill.billcost + bill.changecost}}元</p>' + 
											// '<p>回复结果 {{detail.backmemo}}</p>' + 
											// '<p>回复人 {{detail.backusername}}</p>' + 
										'</div>' +
								    '</div>' +
						  		'</div>' +
							'</div>' +
							'<span ng-show="!bill.checkDetails.length">暂无数据</span>' +
						'</div>' +
						'<div class="aside-footer" ng-show="bill.checkstatus == 1">' +
							'<button type="button" class="btn btn-primary" ng-click="asideConfirm()">确定</button>' +
							'<button type="button" class="btn btn-default" ng-click="asideConfirmCancel()">取消</button>' +
							'<button ng-if="isMgr" type="button" class="btn btn-danger" ng-click="asideConfirmReject()">退回</button>' +
						'</div>' +
					'</div>' +
				'</div>' +
			'</div>' +
		'</div>'
	);


    //特价账单 -  有异议
    $templateCache.put('aside-sendCheck-special.tpl.html',
		'<div class="aside" tabindex="-1" role="dialog" style="width:600px;">' +
			'<div class="aside-dialog">' +
				'<div class="aside-content">' +
					//<!-- 表单 -->
					'<div>' +
						'<div class="aside-header" ng-show="title">' +
							'<button type="button" class="close" ng-click="$hide()">&times;</button>' +
							'<h4 class="aside-title" ng-bind="title"></h4>' +
						'</div>' +
						'<div class="aside-body" >' +
							'<textarea class="form-control" ng-model="bill.currMemo" rows="10" maxlength="2000" placeholder="请填申诉原因..."></textarea>' +
							'<p>最多输入2000字</p>' +
							'<div class="form-inline">' +
								'<label>调整账单金额：</label><input type="number" class="form-control" ng-model="bill.hotelchangecost" style="width:160px;" placeholder="必填"/>&nbsp;元' +
							'</div>' +
							'<h6 style="color:#f00;font-style:italic;" class="pull-right">调整金额填写该笔账单需要补贴支付的金额</h6>' +
						'</div>' +
						//<!-- 查看审核明细 -->
						'<div class="aside-footer" >' +
							'<button type="button" class="btn btn-primary" ng-click="asideConfirm()">确定</button>' +
							'<button type="button" class="btn btn-default" ng-click="asideConfirmCancel()">取消</button>' +
							'<button ng-if="isMgr" type="button" class="btn btn-danger" ng-click="asideConfirmReject()">退回</button>' +
						'</div>' +
					'</div>' +
				'</div>' +
			'</div>' +
		'</div>'
	);
	//账单申诉
	$templateCache.put('aside-sendCheck-base.tpl.html',
		'<div class="aside" tabindex="-1" role="dialog" style="width:600px;">' +
		'<div class="aside-dialog">' +
		'<div class="aside-content">' +
			//<!-- 表单 -->
		'<div>' +
		'<div class="aside-header" ng-show="title">' +
		'<button type="button" class="close" ng-click="$hide()">&times;</button>' +
		'<h4 class="aside-title" ng-bind="title"></h4>' +
		'</div>' +
		'<div class="aside-body" >' +
		'<textarea class="form-control" ng-model="bill.hotelReason" rows="10" maxlength="2000" placeholder="请填申诉原因..."></textarea>' +
		'<p>最多输入2000字</p>' +
		'<div class="form-inline">' +
		'<label>调整账单金额：</label><input type="number" class="form-control" ng-model="bill.hotelChange" style="width:160px;" placeholder="必填"/>&nbsp;元' +
		'</div>' +
		'<h6 style="color:#f00;font-style:italic;" class="text-right">调整金额填写该笔账单需要补贴支付的金额</h6>' +
		'</div>' +
		'<div class="aside-footer" >' +
		'<button type="button" class="btn btn-primary" ng-click="asideConfirm()">确定</button>' +
		'<button type="button" class="btn btn-default" ng-click="asideConfirmCancel()">取消</button>' +
		'<button ng-if="isMgr" type="button" class="btn btn-danger" ng-click="asideConfirmReject()">退回</button>' +
		'</div>' +
		'</div>' +
		'</div>' +
		'</div>' +
		'</div>'
	);

    //确认金额对话框
    $templateCache.put('confirm-bill.tmpl.html',
		'<div>' +
			'<div class="modal-header">' +
				'<button type="button" class="close" aria-label="Close" ng-click="modalConfirmClose()">' +
					'<span aria-hidden="true">&times;</span>' +
				'</button>' +
				'<h4 class="modal-title">账单信息</h4>' +
			'</div>' +
			'<div class="modal-body">' +
				'<div class="form-horizontal">' +
				  '<div class="form-group">' +
				    '<span class="col-sm-2">账单金额:</span>' +
				    '<span class="col-sm-10 text-left">{{bill.readjustcost|currency}} 元</span>' +
				  '</div>' +
				  '<div class="form-group">' +
				    '<span class="col-sm-2">备注:</span>' +
				    '<ul class="col-sm-10 text-success" style="max-height:200px;overflow:auto;">' +
				    	'<li style="list-style:none;" ng-repeat="checkDetail in checkDetailList">{{checkDetail.checkmemo}} -- {{checkDetail.checkstatustext}}</li>' +
				    '</ul>' +
				  '</div>' +
				  '<div class="form-group">' +
				    '<span class="col-sm-12 text-danger">确认金额后将不能够再更改，是否继续？</span>' +
				  '</div>' +
				'</div>' +
			'</div>' +
			'<div class="modal-footer">' +
				'<button type="button" class="btn btn-primary" ng-click="modalConfirm()">确定</button>' +
				'<button type="button" class="btn btn-default" ng-click="modalConfirmClose()">取消</button>' +
			'</div>' +
		'</div>'
	);

    //确认金额对话框 - 特价账单
    $templateCache.put('confirm-bill-special.tmpl.html',
		'<div>' +
			'<div class="modal-header">' +
				'<button type="button" class="close" aria-label="Close" ng-click="modalConfirmClose()">' +
					'<span aria-hidden="true">&times;</span>' +
				'</button>' +
				'<h4 class="modal-title">账单信息</h4>' +
			'</div>' +
			'<div class="modal-body">' +
				'<div class="form-horizontal">' +
				  '<div class="form-group">' +
				    '<span class="col-sm-2">账单金额:</span>' +
				    '<span class="col-sm-10 text-left">{{bill.billcost|currency}} 元</span>' +
				  '</div>' +
				  '<div class="form-group">' +
				    '<span class="col-sm-12 text-danger">确认金额后将不能够再更改，是否继续？</span>' +
				  '</div>' +
				'</div>' +
			'</div>' +
			'<div class="modal-footer">' +
				'<button type="button" class="btn btn-primary" ng-click="modalConfirm()">确定</button>' +
				'<button type="button" class="btn btn-default" ng-click="modalConfirmClose()">取消</button>' +
			'</div>' +
		'</div>'
	);

    //账期明细列表(弹出式)
    $templateCache.put('account-list-ctrl.tmpl.html',
		'<div class="hms-account_view clearfix">' +
			//<!-- Tabs --> 
			'<div bs-active-pane="tabs.activeTab" bs-tabs ng-controller="financeDetailListOuterController">' +
				//<!--有效切客订单-->
				'<div title="订单明细" bs-pane>' +
					'<div ng-include="\'account-list.tmpl.html\'" ng-controller="financeDetailValidListController"></div>' +
				'</div>' +
				//<!--非有效切客订单-->
				'<div ng-if="RULE_CODE==\'A\'" title="非有效切客订单" bs-pane>' +
					'<div ng-include="\'account-list.tmpl.html\'" ng-controller="financeDetailInvalidListController"></div>' +
				'</div>' +
				//<!--订单收款明细-->
				'<div title="订单收款明细" bs-pane>' +
					'<div ng-include="\'account-list.tmpl.html\'" ng-controller="financeDetailCollectListController"></div>' +
				'</div>' +
			'</div>' +
		'</div>'
	);


    //账期明细列表(弹出式) -- 特价账单
    $templateCache.put('account-list-ctrl-special.tmpl.html',
		'<div class="hms-account_view clearfix">' +
			//<!-- Tabs --> 
			'<div bs-active-pane="tabs.activeTab" bs-tabs ng-controller="financeDetailListOuterController">' +
				//<!--有效切客订单-->
				'<div title="订单明细" bs-pane>' +
					'<div ng-include="\'account-list.tmpl.html\'" ng-controller="financeDetailValidListSpecialController"></div>' +
				'</div>' +
			'</div>' +
		'</div>'
	);
   //Base账单明细列表(弹出式) -- 基础账单
		$templateCache.put('account-list-ctrl-base.tmpl.html',
			'<div class="hms-account_view clearfix">' +
				//<!-- Tabs -->
			'<div bs-active-pane="tabs.activeTab" bs-tabs ng-controller="financeDetailListOuterController">' +
				//<!--有效切客订单-->
			'<div title="订单明细" bs-pane>' +
			'<div ng-include="\'account-list.tmpl.html\'" ng-controller="baseFinanceDetailListController"></div>' +
			'</div>' +
			'</div>' +
			'</div>'
		);

    //账期明细列表
    $templateCache.put('account-list.tmpl.html',
		'<div class="table-responsive">' +
			 '<table class="table hms-table border-top">' +
			 	'<thead>' +
			 		'<tr>' +
			 			'<th class="text-center" ng-repeat="item in columns" width="{{item.width}}">{{item.displayName}}</th>' +
			 		'</tr>' +
			 	'</thead>' +
			 	'<tbody>' +
			 		'<tr ng-repeat="row in rowsData">' +
			 			'<td class="text-center" ng-repeat="item in columns" width="{{item.width}}">{{row[item.field]}}</td>' +
			 		'</tr>' +
			 		'<tr ng-if="!rowsData.length">' +
			 			'<td class="text-center" colspan="{{columns.length}}">无数据</td>' +
			 		'</tr>' +
			 	'</tbody>' +
			'</table>' +
		'</div>' +
		'<pagination class="pull-right" previous-text="前一页" next-text="后一页" first-text="首页" last-text="尾页" total-items="totalItems" ng-model="$parent.currentPage" items-per-page="pageCount" max-size="maxSize" class="pagination-md" boundary-links="true" rotate="false" num-pages="numPages" ng-change="pageChanged()"></pagination>'
	);


    //收款账号信息维护
    $templateCache.put('bank-info.tmpl.html',
		'<div ng-controller="BankInfoController">' +
			'<div class="modal-header">' +
				'<button type="button" class="close" aria-label="Close" ng-click="modalConfirmClose()">' +
					'<span aria-hidden="true">&times;</span>' +
				'</button>' +
				'<h4 class="modal-title">收款账号信息</h4>' +
			'</div>' +
			'<div class="modal-body">' +
				'<form class="form-horizontal">' +
				  '<div class="form-group">' +
				    '<label for="user" class="col-sm-2 control-label">户名:</label>' +
				    '<div class="col-sm-10">' +
				      '<input type="text" class="form-control" id="user" placeholder="户名" ng-model="bankInfo.user" >' +
				    '</div>' +
				  '</div>' +
				  '<div class="form-group">' +
				    '<label for="number" class="col-sm-2 control-label">账号:</label>' +
				    '<div class="col-sm-10">' +
				      '<input type="text" class="form-control" id="number" maxlength="19" placeholder="账号" ng-model="bankInfo.number">' +
				    '</div>' +
				  '</div>' +
				  '<div class="form-group">' +
				    '<label for="address" class="col-sm-2 control-label">开户行:</label>' +
				    '<div class="col-sm-10">' +
				      '<input type="text" class="form-control" id="address" placeholder="开户行" ng-model="bankInfo.address">' +
				    '</div>' +
				  '</div>' +
				  '<div class="form-group">' +
					  '<label for="number" class="col-sm-2 control-label">&nbsp;</label>' +
					  '<div class="col-sm-10">' +
							'<table class="table table-condensed hms-table-bankinfo">' +
								'<tr><td>标准	</td><td>开户银行信息</td><td>=</td><td>银行</td><td>+</td><td>支行</td></tr>' +
								'<tr><td>例：</td><td>中国工商银行上海宝山支行</td><td>=</td><td>中国工商银行</td><td>+</td><td>上海宝山支行</td></tr>' +
							'</table>' +
					  '</div>' +
					'</div>' +
				  /*'<div class="form-group">'+
				    '<label for="verifyCode" class="col-sm-2 control-label">验证码:</label>'+
				    '<div class="col-sm-5">'+
				      '<input type="text" class="form-control" id="verifyCode" placeholder="验证码" ng-model="bankInfo.verifyCode">'+
				    '</div>'+
				    '<div class="col-sm-4">'+
				    '<button type="button" class="btn btn-success pull-right" ng-click="verifyCode.send()" ng-disabled="verifyCode.time<verifyCode.maxTime">获取验证码({{verifyCode.time}})</button>'+			    '</div>'+
				  '</div>'+*/
				'</form>' +
			'</div>' +
			'<div class="modal-footer">' +
				'<button type="button" class="btn btn-primary" ng-click="modalConfirm()">确定</button>' +
				'<button type="button" class="btn btn-default" ng-click="modalConfirmClose()">取消</button>' +
			'</div>' +
		'</div>'
	);

    //解绑手机号
    $templateCache.put('unbind-phone.tmpl.html',
		'<div ng-controller="UnbindPhoneController">' +
			'<div class="modal-header">' +
				'<button type="button" class="close" aria-label="Close" ng-click="modalConfirmClose()">' +
					'<span aria-hidden="true">&times;</span>' +
				'</button>' +
				'<h4 class="modal-title">删除用户:{{unbind.loginname}}</h4>' +
			'</div>' +
			'<div class="modal-body">' +
				'<div class="form-horizontal">' +
				  '<div class="form-group">' +
				    '<label for="password" class="col-sm-2 control-label">登录密码:</label>' +
				    '<div class="col-sm-10">' +
				      '<input type="password" class="form-control" id="password" placeholder="密码" ng-model="unbind.password" >' +
				    '</div>' +
				  '</div>' +
				'</div>' +
			'</div>' +
			'<div class="modal-footer">' +
				'<button type="button" class="btn btn-primary" ng-click="modalConfirm()">确定</button>' +
				'<button type="button" class="btn btn-default" ng-click="modalConfirmClose()">取消</button>' +
			'</div>' +
		'</div>'
	);

    //tooltip
    $templateCache.put('tooltip-content.tmpl.html',
		'<div class="real-status" style="min-height:100px;">' +
			'<ul class="list-group">' +
				'<li class="list-group-item">在线状态：{{hotelStatus.onlineStr}}</li>' +
			'<ul>' +
		'</div>'
	);

    //工具条aside
    $templateCache.put('tool-bar.tpl.html',
		'<div class="aside tool-bar">' +
			'<div style="height:100px;"></div>' +
			'<div style="position:absolute;bottom:20px;width:100%;">' +
				'<div ng-repeat="btn in toolBar.buttons" class="pull-down tool-bar-btn-con" ng-click="btn.callBack()">' +
					// '<i class="tool-bar-btn fa fa-{{btn.icon}} fa-2x"></i>' + 
					'<div class="tool-bar-btn {{btn.icon}}"></div>' +
					'<div class="redpot" ng-class="{redpotShow:btn.redPot && btn.redPot.length}"></div>' +
					'<div class="tool-bar-btn-title" ng-bind="btn.name"></div>' +
				'</div>' +
			'</div>' +
			// '<div class="tool-bar-btn-con fix-bottom-btn" ng-click="toolBar.config($event)">' + 
			// 	'<i class="tool-bar-btn fa fa-gear fa-2x"></i>' + 
			// 	'<div class="tool-bar-btn-title">用户设置</div>' + 
			// '</div>' + 
			// '<div class="tool-bar-lock-btn" ng-click="toolBar.lock($event)" title="固定/解锁">' + 
			// 	'<i class="tool-bar-btn fa" ng-class="{\'fa-lock\':toolBar.locked,\'fa-unlock\':!toolBar.locked}"></i>' + 
			// '</div>' + 
			'<div class="tool-bar-top-btn fa fa-arrow-up" title="返回顶部" ng-show="toolBar.showTopBtn" ng-click="toolBar.backToTop()"></div>' +
		'</div>'
	);

    //工具条演示页
    $templateCache.put('preview-tool-bar.tpl.html',
		'<div class="preview-tool-bar-con">' +
			'<div class="preview-tool-bar-content">' +
				'<p>新功能上线啦！在右侧边栏，你会发现一些常用的小工具，快来体验哦！</p>' +
				'<div class="pull-right">' +
					'<input type="checkbox" ng-model="preview.shouldHide">不再提示&nbsp;&nbsp;' +
					'<button type="submit" class="btn btn-success radius-3" ng-click="preview.ok()">知道了</button>' +
				'</div>' +
			'</div>' +
			'<div class="preview-tool-bar-arrow faa-parent">' +
				'<i class="fa fa-hand-o-right faa-passing animated"></i>' +
			'</div>' +
		'</div>'
	);

    //消息中心aside
    $templateCache.put('aside-message-center.tpl.html',
		'<div class="aside" tabindex="-1" role="dialog" style="width:400px;">' +
			'<i class="aside-loading fa fa-spinner fa-pulse fa-3x" ng-show="msg.loading"></i>' +
			'<div class="aside-dialog message-dialog">' +
				'<div class="aside-content message-con">' +
					'<div class="aside-header message-head" ng-show="title">' +
						'<button type="button" class="close" ng-click="closeMessageCenter()">&times;</button>' +
						'<button type="button" class="btn btn-default pull-right" ng-click="refreshMessageList()">刷新</button>' +
						'<h4 class="aside-title" ng-bind="title"></h4>' +
					'</div>' +
					//<!-- 消息列表 -->
					'<div class="message-body">' +
						'<div ng-show="msg.list.length">' +
						  	'<div class="message-msg-con" ng-repeat="detail in msg.list" ng-click="msg.msgClick(detail)">' +
						  		'<div class="message-msg-icon {{msg[detail.directive].icon}}" ng-class={"message-msg-newtag":detail.isNew==1}>' +
						  		'</div>' +
						  		'<div class="message-msg-content" ng-bind="detail.title">' +
						  		'</div>' +
						  		'<div class="message-msg-date" ng-bind="detail.createTimeStr">' +
						  		'</div>' +
					  		'</div>' +
						'</div>' +
						'<div class="message-page" ng-hide="msg.list.length >= msg.totalItems">' +
							'<button type="button" class="btn btn-default" ng-click="getMoreMessageList()">加载更多</button>' +
						'</div>' +
					'</div>' +
				'</div>' +
			'</div>' +
		'</div>'
	);
}])
.directive('ngThumb', ['$window', function ($window) {
    var helper = {
        support: !!($window.FileReader && $window.CanvasRenderingContext2D),
        isFile: function (item) {
            return angular.isObject(item) && item instanceof $window.File;
        },
        isImage: function (file) {
            var type = '|' + file.type.slice(file.type.lastIndexOf('/') + 1) + '|';
            return '|jpg|png|jpeg|bmp|gif|'.indexOf(type) !== -1;
        }
    };

    return {
        restrict: 'A',
        template: '<canvas/>',
        link: function (scope, element, attributes) {
            if (!helper.support) return;

            var params = scope.$eval(attributes.ngThumb);

            var canvas = element.find('canvas');

            if (!helper.isFile(params.file)) return;
            if (!helper.isImage(params.file)) {
                canvas.hide();
                return;
            }
            var reader = new FileReader();

            reader.onload = onLoadFile;
            reader.readAsDataURL(params.file);

            function onLoadFile(event) {
                var img = new Image();
                img.onload = onLoadImage;
                img.src = event.target.result;
            }

            function onLoadImage() {
                var width = params.width || this.width / this.height * params.height;
                var height = params.height || this.height / this.width * params.width;
                canvas.attr({ width: width, height: height });
                canvas[0].getContext('2d').drawImage(this, 0, 0, width, height);
            }
        }
    };
}])
.directive('hmsImgload', ['$window', function ($window) {
    return {
        restrict: 'A',
        link: function (scope, element, attributes) {
            var loadIcon = jQuery("<div class='loading-img fa fa-gear faa-spin animated'></div>");

            var originScr = element[0].src;
            scope.$watch(
				function () {
				    return element[0].src;
				},
				function (newValue, oldValue) {
				    if (newValue != oldValue && newValue) {

				        element.after(loadIcon);
				        var fp = element.parent().css("position");
				        if (fp == "static" || fp == "auto") {
				            element.parent().css("position", "relative");
				        }
				        element.parent().addClass("loading-img-con");
				    }
				}
			);

            element[0].onload = onLoadImage;

            function onLoadImage() {
                loadIcon.remove();
                element.parent().removeClass("loading-img-con");
            }
        }
    };
}])
.service('HmsModalService', ['$modal', function ($modal) {
    this.modal = null;
    this.settings = {
        title: "提示",
        content: "",
        show: false,
        keyboard: false,
        animation: "am-fade-and-scale",
        backdrop: "static"
    };

    /**
	 * opt.scope
	 * opt.title
	 * opt.content
	 * opt.contentTmpl
	 * opt.bodyTmpl
	 * opt.backdrop
	 * opt.btns
	 */
    this.show = function (opt) {
        var settings = angular.copy(this.settings);
        if (!opt.scope) console.error("hms:parameter opt.scope is missed");
        opt.scope._contentTmpl = null;
        opt.scope._bodyTmpl = null;
        opt.scope._cls = null;
        // if (!opt.modal) console.error("hms:parameter opt.modal is missed");
        // if (typeof opt.modal != "object") console.error("hms:parameter opt.modal's type is illegal");
        if (!opt.content) {
            opt.template = "modal-outer.tmpl.html";
            if (!opt.contentTmpl) {
                opt.scope._contentTmpl = "modal-content.tmpl.html";
            } else {
                opt.scope._contentTmpl = opt.contentTmpl;
            }
            if (opt.bodyTmpl) {
                opt.scope._bodyTmpl = opt.bodyTmpl;
            }
            if (opt.cls) {
                opt.scope._cls = opt.cls;
            }
        }
        this.modal = $modal(angular.extend(settings, opt));
        this.modal.$promise.then(this.modal.show);
    };

    this.hide = function () {
        this.modal.hide();
        this.modal = null;
    };
}])
.service('HmsDownloadService', ['HmsHttp', '$q', function (HmsHttp, $q) {
    this.getImgUrl = function (key, mode, w, h) {
        var deferred = $q.defer();

        if (!key) {
            deferred.reject();
            return deferred.promise;
        }

        var rUrl = contentPath + "/qiniu/getDownloadUrl&key=";
        rUrl = rUrl + key;
        if (mode) {
            rUrl = rUrl + "&mode=" + mode;
        }
        if (w) {
            rUrl = rUrl + "&w=" + w;
        }
        if (h) {
            rUrl = rUrl + "&h=" + h;
        }
        return HmsHttp.get(rUrl);
    };
}])
.service('HmsUploadService', ['FileUploader', 'HmsHttp', '$timeout', '$q', 'HmsModalService', 'HmsDownloadService', function (FileUploader, HmsHttp, $timeout, $q, HmsModalService, HmsDownloadService) {
    var HmsUploadService = this;

    //token params
    this.simpleUploadToken = { "token": "" };
    this.lastTokenTime = "";
    this.retryCount = 0;
    this.RetryCountMax = 5;
    this.loadingToken = false;
    this.deferredList = [];
    this.publicDownloadUrl = qiniuDownloadAddress || "https://dn-imke-pro.qbox.me";

    this.getSimpleUploadToken = function (deferred) {
        var self = HmsUploadService;

        if (deferred) {
            if (self.verifyToken()) {
                deferred.resolve();
                return;
            }

            if (self.retryCount > self.RetryCountMax) {
                deferred.resolve();
                for (var i = 0; i < HmsUploadService.deferredList.length; i++) {
                    HmsUploadService.deferredList[i].resolve();
                }
                console.error("get token failed");
                return;
            }

            if (self.loadingToken) {
                HmsUploadService.deferredList.push(deferred);
                return;
            }

            self.retryCount++;
            self.loadingToken = true
            var url = contentPath + "/qiniu/getSimpleUploadToken";
            // console.log(new Date().valueOf());
            HmsHttp.get(url).then(function (res) {
                self.loadingToken = false;
                var data = res.data;
                if (data.errorMsg) {
                    $timeout(function () {
                        self.getSimpleUploadToken(deferred);
                    }, 1000 * self.retryCount * self.retryCount);
                } else {
                    self.simpleUploadToken["token"] = data.attribute;
                    self.lastTokenTime = new Date().valueOf();
                    self.retryCount = 0;
                    deferred.resolve();
                    for (var i = 0; i < HmsUploadService.deferredList.length; i++) {
                        HmsUploadService.deferredList[i].resolve();
                    }
                }
            }, function () {
                self.loadingToken = false;
                $timeout(function () {
                    self.getSimpleUploadToken(deferred);
                }, 1000 * self.retryCount * self.retryCount);
            });
        } else {
            deferred = $q.defer();
            self.getSimpleUploadToken(deferred);
            return deferred.promise;
        }
    };

	this.uploadSettings = {
		url: "http://up.qiniu.com", //默认
		maxItemSize: 1024*1024*10, //默认
		MaxUploadNum: 10, //默认

        headers: {},
        formData: [this.simpleUploadToken],

        isImage: function (fileItem) {
            var type = '|' + fileItem.type.slice(fileItem.type.lastIndexOf('/') + 1) + '|';
            return this.fileTypes.indexOf(type) !== -1;
        },
        isOverSize: function (fileItem) {
            var size = fileItem.size;
            return size > this.maxItemSize;
        },
        onErrorItem: function (fileItem, response, status, headers) {
            Tip.message("文件：" + fileItem.file.name + " 上传失败", "error");
        },
        onCompleteAll: function () {
            $timeout(function () {
                Tip.message("文件上传完毕");
            });
        },
        onBeforeUploadItem: function (item) {
            //
        },
        onWhenAddingFileFailed: function (item, filter, options) {
        },
        onAfterAddingAll: function (addedFileItems) {
            event.target.value = "";
            if (this.queue.length > this.MaxUploadNum) {
                Tip.message("最多支持批量上传" + this.MaxUploadNum + "个文件", "error");
            }
        },
        onAfterAddingFile: function (fileItem) {
            var scope = this.scope;
            if (!HmsModalService.modal) {
                HmsUploadService.showUploadModal(scope);
            }
        },
        onSuccessItem: function (fileItem, response, status, headers) {
            if (response && response.key) {
                var opt = fileItem.opt;
                if (opt) {
                    if (!HmsUploadService.publicDownloadUrl) {
                        HmsDownloadService.getImgUrl(response.key, opt.mode, opt.w, opt.h).then(
				    		function (res) {
				    		    if (res && res.data) {
				    		        if (opt.callBack && typeof opt.callBack == "function") {
				    		            opt.callBack(opt, response.key, res.data["attribute"]);
				    		            return;
				    		        }

				    		        opt.keys.push(response.key);
				    		        opt.previews.push(res.data["attribute"]);
				    		    }
				    		},
				    		function (err) {
				    		    //
				    		}
			    		);
	    			} else {
	    				if (opt.callBack && typeof opt.callBack == "function") {
	    					opt.callBack(opt, response.key, HmsUploadService.publicDownloadUrl + "/" + response.key);
	    					return;
	    				}
    					opt.keys.push(response.key);
    					opt.previews.push(HmsUploadService.publicDownloadUrl + "/" + response.key);
	    			}
			    }
		    }
	    },
	    formatAndSizeFilter: {
	        name: 'formatAndSizeFilter',
	        fn: function(item, options) {
	        	if (this.fileTypes && !this.isImage(item)) {
	        		Tip.message("不支持该文件格式", "error");
	        		return false;
	        	}
	        	if (this.isOverSize(item)) {
	        		Tip.message("不支持大于" +
	        		 this.maxItemSize/(1024*1024) + "M文件", "error");
	        		return false;
	        	}
	        	if ((this.queue.length + (options.opt.keys?options.opt.keys.length:0)) >= this.MaxUploadNum) {
	        		Tip.message("最多支持批量上传" + this.MaxUploadNum + "个文件", "error");
	        		return false;
	        	}
	            return true;
	        }
	    }
	}

    this.getUploader = function (options) {
        var self = this;
        var deferred = $q.defer();
        self.getSimpleUploadToken().then(function () {
            //获取uploader实例
            var uploader = new FileUploader();
            angular.extend(uploader, self.uploadSettings);
            uploader.MaxUploadNum = options.MaxUploadNum || uploader.MaxUploadNum;
            uploader.maxItemSize = options.maxItemSize || uploader.maxItemSize;
            uploader.fileTypes = options.fileTypes; //"|jpg|jpeg|png|";
            uploader.filters.push(uploader.formatAndSizeFilter);
            deferred.resolve(uploader);
        });
        return deferred.promise;
    };

    this.verifyToken = function () {
        if (!this.simpleUploadToken["token"]
			|| ((new Date().valueOf() - this.lastTokenTime) / 1000) > 1800) {
            return false;
        }
        return true;
    };

    this.showUploadModal = function (scope) {
        HmsModalService.show({
            "scope": scope,
            "title": "上传",
            "bodyTmpl": contentPath + '/main/reg/template/tmpl-upload.html'
        });
    };

    this.closeUploadModal = function (uploader) {
        uploader.clearQueue();
        HmsModalService.hide();
    };
}])
.directive('hmsUpload', ['$rootScope', 'HmsUploadService', '$timeout', function ($rootScope, HmsUploadService, $timeout) {

    var tmpl = '<div class="fileUpload btn btn-success" ng-show="!(!img.ready || (img.MaxUploadNum <= img.keys.length))">' +
					'<span ng-bind="btnName"></span>' +
					'<input ng-if="img.ready" type="file" class="upload" nv-file-select="" uploader="uploader" options="{\'opt\':img}" multiple/>' +
				'</div>';

    return {
        restrict: 'E',
        template: tmpl,
        scope: {
            img: "=image"
        },
        link: function ($scope, element, attributes) {
            $scope.uploader = {};
            $scope.btnName = $scope.img.btnName || "上传";
            HmsUploadService.getUploader($scope.img).then(
        		function (uploader) {
        		    if (!HmsUploadService.verifyToken()) {
        		        return;
        		    }
        		    uploader.scope = $scope;
        		    $scope.uploader = uploader;
        		    $scope.img.ready = true;
        		    if ($scope.img.MaxUploadNum == 1) {
        		        $timeout(function () {
        		            element.children().find("input").removeAttr("multiple");
        		        });
        		    }
        		}
    		);

            $scope.closeModal = function () {
                HmsUploadService.closeUploadModal($scope.uploader);
            };
        }
    };
}])
.directive('hmsHtml', function () {
    return function (scope, el, attr) {
        if (attr.hmsHtml) {
            scope.$watch(attr.hmsHtml, function (html) {
                el.html(html || '');//更新html内容
            });
        }
    };
})
.directive('hmsInclude', function () {
    return {
        restrict: 'AE',
        templateUrl: function (ele, attrs) {
            return attrs.templatePath;
        }
    };
})
/**
 * [省、市、县/地区数据获取服务类]
 */
.service('PreviewService', ['$timeout', function ($timeout) {
    return {
        showMask: function (tmpls) {

        }
    };
}])
/**
 * [省、市、县/地区数据获取服务类]
 */
.service('InterfaceService', ['HmsHttp', 'HmsModalService', '$timeout', function (HmsHttp, HmsModalService, $timeout) {
    return {
        /**
		 * 手动调更新房态接口
		 * @return {[type]} [description]
		 */
        refreshRoomOnlineState: function (scope, hotelId) {
            scope.cusContent = "更新房态处理过程将会持续几分钟，是否开始？";
            scope.sendRefreshRoomOnlineFlag = false;
            if (scope.sendRefreshRoomOnlineFlag) {
                Tip.message("正在更新房态，请耐心等待。。。");
                return;
            }

            scope.modalConfirm = function () {
                if (!hotelId) {
                    Tip.message("无法获取酒店ID", "error");
                    return;
                }
                jQuery(".confirm_loading").show();
                scope.sendRefreshRoomOnlineFlag = true;
                HmsHttp.post(contentPath + "/hotelmessage/refreshRoomOnlineState", {
                    hotelId: hotelId
                }).success(function (data) {
                    if (!data.success) {
                        Tip.message(data.errorMsg, "error");
                        jQuery(".confirm_loading").hide();
                        scope.sendRefreshRoomOnlineFlag = false;
                        return;
                    }
                    Tip.message("正在更新房态，请耐心等待。。。");
                    $timeout(function () {
                        jQuery(".confirm_loading").hide();
                        Tip.message("更新房态请求发送成功，反查进行中，请一分钟之后再执行反查操作");
                        scope.sendRefreshRoomOnlineFlag = false;
                        scope.modalConfirmClose();
                    }, 2000);
                }).error(function (msg) {
                    jQuery(".confirm_loading").hide();
                    scope.sendRefreshRoomOnlineFlag = false;
                    Tip.message("更新房态请求发送异常", "error");
                });
            };

            scope.close = scope.modalConfirmClose = function () {
                if (scope.sendRefreshRoomOnlineFlag) {
                    Tip.message("正在更新房态，请耐心等待。。。");
                    return;
                }
                HmsModalService && HmsModalService.hide();
            };

            HmsModalService.show({
                contentTmpl: "modal-confirm-notice.tmpl.html",
                scope: scope,
            });
        }
    };
}])
//计数器（可选可输入）
.directive('hmsFormCounter', ['$animate', function ($animate) {
    return {
        restrict: 'E',
        template: '<div class="counter_con">' +
	      			'<a ng-class="{disableCounterDecre:disableDecre}" class="counter_decre" ng-click="slider.decre()">-</a>' +
					'<input ng-show="showInput" autocomplete="off" type="text" class="counter_input" ng-model="currVal" ng-blur="change()">' +
					'<div class="counter_slider_con">' +
						'<div class="counter_slider_item {{item.pos}}" ng-repeat="item in slider.roller" ng-bind="item.num"></div>' +
					'</div>' +
					'<a class="counter_incre" ng-click="slider.incre()">+</a>' +
      			  '</div>',
        scope: {
            data: "=data",
            key: "@key",
            callback: "=callback"
        },
        link: function ($scope, element, attributes) {
            $scope.showInput = true;
            $scope.disableDecre = false;
            $scope.change = function () {
                var reg = new RegExp("^[0-9]*$");
                if (!reg.test($scope.currVal)) {
                    $scope.currVal = 1;
                }

                if ($scope.currVal == "" || $scope.currVal <= 1) {
                    $scope.currVal = 1;
                }
                $scope.slider.roller[0].num = parseInt($scope.currVal) - 1;
                $scope.slider.roller[1].num = parseInt($scope.currVal);
                $scope.slider.roller[2].num = parseInt($scope.currVal) + 1;
            };
            element.find(".counter_input")[0].addEventListener("blur", function () {
                $scope.$apply(function () {
                    $scope.showInput = true;
                    if ($scope.currVal <= 1) {
                        $scope.disableDecre = true;
                    } else {
                        $scope.disableDecre = false;
                    }
                    $scope.data[$scope.key] = $scope.currVal;
                    if ($scope.callback) {
                        $scope.callback($scope.data);
                    }
                });

                // $scope.$digest();
            });
            $scope.slider = {
                ele: element.find(".counter_slider_con"),
                active: false,
                len: 3,
                step: 1,
                roller: [],
                doneList: [],
                type: "",
                addData: null,
                ignore: null,
                stop: function () {
                    $scope.slider.doneList.splice(0);
                    $scope.slider.active = false;
                },
                decre: function () {
                    var self = $scope.slider;
                    if (!self.before(false)) {
                        return;
                    }
                    if (self.active) {
                        if (self.type != "decre") {
                            self.doneList.splice(0);
                            return;
                        }
                        self.doneList.push("decre");
                        self.type = "decre";
                        return;
                    }
                    self.type = "decre";
                    self.doneList.push(self.type);
                    self.active = true;
                    $animate.addClass(self.ele, self.type).then(self.after);
                    $scope.showInput = false;
                },
                incre: function () {
                    var self = $scope.slider;
                    if (!self.before(true)) {
                        return;
                    }
                    if (self.active) {
                        if (self.type != "incre") {
                            self.doneList.splice(0);
                            return;
                        }
                        self.doneList.push("incre");
                        self.type = "incre";
                        return;
                    }
                    self.type = "incre";
                    self.doneList.push(self.type);
                    self.active = true;
                    $animate.addClass(self.ele, self.type).then(self.after);
                    $scope.showInput = false;
                },
                before: function (isIncre) {
                    ////
                    if (isIncre) {
                        $scope.currVal++;
                    } else {
                        if ($scope.currVal <= 1) {
                            return false;
                        }
                        $scope.currVal--;
                    }
                    return true;
                },
                after: function () {
                    var self = $scope.slider;
                    var thisTask = self.doneList[0];
                    if (thisTask == "decre") {
                        var _cut = self.roller.splice(0, self.roller.length - self.step);
                        self.roller = self.roller.concat(_cut);
                        var tempRoller = angular.copy(self.roller);
                        for (var i = 0; i < self.len; i++) {
                            var newIndex = (i + self.step) % self.len;
                            self.roller[i].pos = tempRoller[newIndex].pos;
                            if (i < self.step && self.addData) {
                                self.addData(self.roller[i]);
                            }
                        }
                    } else if (thisTask == "incre") {
                        var _cut = self.roller.splice(0, self.step);
                        self.roller = self.roller.concat(_cut);
                        var tempRoller = angular.copy(self.roller);
                        for (var i = self.len - 1; i >= 0; i--) {
                            var newIndex = (i + self.len - self.step) % self.len;
                            self.roller[i].pos = tempRoller[newIndex].pos;
                            if (i >= (self.len - self.step) && self.addData) {
                                self.addData(self.roller[i]);
                            }
                        }
                    }
                    self.ele.removeClass(self.type);
                    self.doneList.shift();
                    $scope.$apply(function () {
                        if (self.doneList.length && (!self.ignore || !self.ignore())) {
                            $animate.addClass(self.ele, self.doneList[0]).then(self.after);
                            return;
                        }
                        self.doneList.splice(0);
                        self.active = false;
                        if (self.callBack) {
                            self.callBack();
                        }
                        ////
                        $scope.showInput = true;
                        $scope.data[$scope.key] = $scope.currVal;
                        if (thisTask == "decre" && $scope.currVal <= 1) {
                            $scope.disableDecre = true;
                        } else {
                            $scope.disableDecre = false;
                        }
                        if ($scope.callback) {
                            $scope.callback($scope.data);
                        }
                    });
                }
            };

            (function initSlider() {
                $scope.currVal = $scope.data[$scope.key];
                if ($scope.currVal <= 1) {
                    $scope.disableDecre = true;
                }
                $scope.slider.roller.push({ "num": parseInt($scope.currVal) - 1, "pos": "last" });
                $scope.slider.roller.push({ "num": parseInt($scope.currVal), "pos": "curr" });
                $scope.slider.roller.push({ "num": parseInt($scope.currVal) + 1, "pos": "next" });
                $scope.slider.addData = function (data) {
                    if ($scope.slider.type == "decre") {
                        data.num = data.num - $scope.slider.len;
                    } else if ($scope.slider.type == "incre") {
                        data.num = data.num + $scope.slider.len;
                    }
                };
            })();
        }
    };
}])
.directive('ngEnter', function () {
    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
            if (event.which === 13) {
                scope.$apply(function () {
                    scope.$eval(attrs.ngEnter);
                });

                event.preventDefault();
            }
        });
    };
})
.directive('hmsPosition', ['HmsHttp', 'LocaltionService', function (HmsHttp, LocaltionService) {
    return {
        restrict: 'EA',
        templateUrl: contentPath + "/main/plug-in/gaode/hms-position.tmpl.html",
        scope: {
            position: "=data"
        },
        link: function ($scope, element, attributes) {
            //
            $scope.gaodeApiHtml = contentPath + "/main/plug-in/gaode/gaodeMap.html" + hmsVersionPath;
            //
            $scope.proList = [];
            $scope.cityList = [];
            $scope.distList = [];
            $scope.areaList = [];

            // 加载省市县商圈列表信息
            LocaltionService.getPros().then(
        		function (res) {
        		    if (res.data) {
        		        $scope.proList = res.data;
        		    }
        		},
        		function (err) {

        		}
    		);
            if ($scope.position._proObj.code) {
                LocaltionService.getCitiesByCode($scope.position._proObj.code).then(
	        		function (res) {
	        		    if (res.data) {
	        		        $scope.cityList = res.data;
	        		    }
	        		},
	        		function (err) {

	        		}
	    		);
            }
            if ($scope.position._cityObj.code) {
                LocaltionService.getDistrictsByCode($scope.position._cityObj.code).then(
	        		function (res) {
	        		    if (res.data) {
	        		        $scope.distList = res.data;
	        		    }
	        		},
	        		function (err) {

	        		}
	    		);
            }

            //
            $scope.gaodeApiFrame = jQuery("iframe", element);
            $scope.gaodeApiFrame.load(function () {
                var iframe = $scope.gaodeApiFrame;
                iframe[0].contentWindow.parentH = $scope;
                iframe[0].contentWindow.initializeMap(function () {
                    if ($scope.position._disObj.code) {
                        iframe[0].contentWindow.getAreas($scope.position._disObj.code);
                    }

                    if ($scope.position.longitude && $scope.position.latitude) {
                        $scope.setMarker($scope.position.longitude, $scope.position.latitude);
                    }
                    setTimeout(function () {
                        jQuery("#map_loading_loading").hide();
                        jQuery("#map_loading_back").hide();
                    }, 1000);
                });
            });

            $scope.proChange = function () {
                $scope.position._cityObj.code = "";
                $scope.position._disObj.code = "";
                $scope.position._disObj.id = "";
                $scope.position._areaObj.areacode = "";
                $scope.position._areaObj.name = "";
                $scope.cityList = [];
                $scope.distList = [];
                $scope.areaList = [];
                if (!$scope.position.longitude && !$scope.position.latitude && !$scope.position.detailAddr) {
                    $scope.setMarker($scope.position._proObj.longitude, $scope.position._proObj.latitude);
                }
                LocaltionService.getCitiesByCode($scope.position._proObj.code).then(
	        		function (res) {
	        		    if (res.data) {
	        		        $scope.cityList = res.data;
	        		    }
	        		},
	        		function (err) {

	        		}
	    		);
            };

            $scope.cityChange = function () {
                $scope.position._disObj.code = "";
                $scope.position._disObj.id = "";
                $scope.position._areaObj.areacode = "";
                $scope.position._areaObj.name = "";
                $scope.distList = [];
                $scope.areaList = [];
                if (!$scope.position.longitude && !$scope.position.latitude && !$scope.position.detailAddr) {
                    $scope.setMarker($scope.position._cityObj.longitude, $scope.position._cityObj.latitude);
                }
                LocaltionService.getDistrictsByCode($scope.position._cityObj.code).then(
	        		function (res) {
	        		    if (res.data) {
	        		        $scope.distList = res.data;
	        		    }
	        		},
	        		function (err) {

	        		}
	    		);
            };

            $scope.disChange = function () {
                $scope.position._areaObj.areacode = "";
                $scope.position._areaObj.name = "";
                $scope.areaList = [];
                if (!$scope.position.longitude && !$scope.position.latitude && !$scope.position.detailAddr) {
                    $scope.setMarker($scope.position._disObj.longitude, $scope.position._disObj.latitude);
                }
                $scope.gaodeApiFrame[0].contentWindow.getAreas($scope.position._disObj.code);
            };

            $scope.areaChange = function () {
                if (!$scope.position.longitude && !$scope.position.latitude && !$scope.position.detailAddr) {
                    $scope.setMarker($scope.position._areaObj.location[0], $scope.position._areaObj.location[1]);
                }
            };

            $scope.setMarker = function (lng, lat) {
                $scope.gaodeApiFrame[0].contentWindow.receive(lng, lat);
            };
        }
    };
}])
/**
 * [省、市、县/地区数据获取服务类]
 */
.service('LocaltionService', ['HmsHttp', function (HmsHttp) {
    return {
        /**
		 * [获取省列表]
		 * @return {[type]} [description]
		 */
        getPros: function () {
            return HmsHttp.get(contentPath + "/hotel/provinces");
        },
        /**
		 * [获取市列表]
		 * @param  {[type]} proId [description]
		 * @return {[type]}       [description]
		 */
        getCities: function (proId) {
            return HmsHttp.get(contentPath + "/hotel/cities?proId=" + proId);
        },
        /**
		 * [获取市列表]
		 * @param  {[type]} provcode [description]
		 * @return {[type]}       [description]
		 */
        getCitiesByCode: function (provcode) {
            return HmsHttp.get(contentPath + "/hotel/citiesbycode?provcode=" + provcode);
        },
        /**
		 * [获取县/地区列表]
		 * @param  {[type]} cityId [description]
		 * @return {[type]}        [description]
		 */
        getDistricts: function (cityId) {
            return HmsHttp.post(contentPath + "/hotel/districts?cityId=" + cityId);
        },
        /**
		 * [获取县/地区列表]
		 * @param  {[type]} citycode [description]
		 * @return {[type]}        [description]
		 */
        getDistrictsByCode: function (citycode) {
            return HmsHttp.post(contentPath + "/hotel/districtsbycode?citycode=" + citycode);
        }
    };
}]);

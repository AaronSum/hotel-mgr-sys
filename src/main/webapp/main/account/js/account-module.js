/**account module*/

"use strict";
angular.module("accountApp", [])

    .controller("financeController", ['$rootScope', '$scope', '$animate', '$aside', 'HmsModalService', 'HmsHttp', '$timeout', 'BankInfoService', 'BillService', function ($rootScope, $scope, $animate, $aside, HmsModalService, HmsHttp, $timeout, BankInfoService, BillService) {

        $scope.Nav = {
            id: 'Nav',
            selected: 0,
            items: [{
                'id': 'month',
                'displayName': '月度阈值结算',
                'action': 'byHotelid',
                'loaded': false,
                'page': contentPath + '/main/account/template/finance-month.html'
            }, {
                    'id': 'week',
                    'displayName': '周结算',
                    'action': 'getWeekOrders',
                    'loaded': false,
                    'page': contentPath + '/main/account/template/finance-week.html'
                }, {
                    'id': 'special',
                    'displayName': '特价结算',
                    'action': 'getSpecialBills',
                    'loaded': false,
                    'page': contentPath + '/main/account/template/finance-special.html'
                }, {
                    'id': 'baseBills',
                    'displayName': '综合账单',
                    'action': 'getNewBills',
                    'loaded': false,
                    'page': contentPath + '/main/account/template/finance-new.html'
                }, {
                    'id': 'appeal',
                    'displayName': '我的申诉',
                    'action': 'getMyAppeal',
                    'loaded': false,
                    'page': contentPath + '/main/account/template/finance-appeal.html'
                }]
        };

        // 导航项
        $scope.isMgr = false;
        //初始化操作
        function init() {
            //加载提前结账按钮可用状态
            BillService.isFreeze()
                .success(function (result) {
                    $scope.tqjsBtn = result;
                })
                .error(function (error) {
                    console.log(error);
                });
        };
        $scope.clone = function (row) {
            return _.clone(row);
        };

        /**
         *	按钮-确认金额
         */
        $scope.confirmBill = function (row) {
            if (row.isFreeze == 'Y') {
                showFreezeDialog();
                return;
            }

            $rootScope.hmsLoading.hide();
            loadBankInfo(function (result) {
                $rootScope.hmsLoading.hide();
                if (!result) {
                    $scope.cusContent = "收款人账号信息为空，请填写收款人账号信息后，再做确认金额！";
                    $scope.modalConfirm = function () {
                        HmsModalService.hide();
                        $scope.showBankCount();
                    };
                    $scope.modalConfirmClose = function () {
                        HmsModalService.hide();
                    };
                    HmsModalService.show({
                        contentTmpl: "modal-confirm-notice.tmpl.html",
                        scope: $scope,
                    });
                    return;
                };
                var bill = row;
                $scope.bill = bill;

                var url = contentPath + "/bill/simpleCheckInfo";
                var params = {
                    billId: bill.id,
                    pageNum: 1,
                    pageSize: 1000,
                    total: 1000
                };
                HmsHttp.post(url, params).then(
                    function (res) {
                        if (res.data) {
                            $scope.checkDetailList = res.data.rows;
                        }
                    },
                    function (msg) {
                        Tip.message("获取审核明细信息失败", "error");
                    }
                    );
                $scope.modalConfirm = function () {
                    if (!bill.id) {
                        Tip.message("无法找到账单信息", "error");
                        return;
                    }
                    $rootScope.hmsLoading.show();
                    HmsHttp.post(contentPath + "/bill/simpleConfirmAmount", {
                        billId: bill.id
                    }).success(function (data) {
                        if (!data.success) {
                            Tip.message(data.errorMsg, "error");
                            return;
                        }
                        Tip.message("确认成功");
                        $scope.modalConfirmClose();
                        $scope.$broadcast("loadSubData");
                        //loadData();
                    }).error(function (msg) {
                        $rootScope.hmsLoading.hide();
                        Tip.message("确认金额异常", "error");
                    });
                };

                $scope.modalConfirmClose = function () {
                    HmsModalService.hide();
                };
                HmsModalService.show({
                    contentTmpl: "confirm-bill.tmpl.html",
                    scope: $scope,
                });

            }, function (result) {
                $rootScope.hmsLoading.hide();
                console.log(result);
            })
        };

        /**
        *	按钮特价账单-确认金额
        */
        $scope.confirmSpecialBill = function (row) {
            $rootScope.hmsLoading.hide();
            loadBankInfo(function (result) {
                $rootScope.hmsLoading.hide();
                if (!result) {
                    $scope.cusContent = "收款人账号信息为空，请填写收款人账号信息后，再做确认金额！";
                    $scope.modalConfirm = function () {
                        HmsModalService.hide();
                        $scope.showBankCount();
                    };
                    $scope.modalConfirmClose = function () {
                        HmsModalService.hide();
                    };
                    HmsModalService.show({
                        contentTmpl: "modal-confirm-notice.tmpl.html",
                        scope: $scope,
                    });
                    return;
                };
                var bill = row;
                $scope.bill = bill;

                //var url = contentPath + "/bill/simpleCheckInfo";
                //var url = contentPath + "/bill/specialCheckInfo";
                //var params = {
                //    billId: bill.id,
                //    pageNum: 1,
                //    pageSize: 1000,
                //    total: 1000
                //};
                //HmsHttp.post(url, params).then(
                //    function (res) {
                //        if (res.data) {
                //            $scope.checkDetailList = res.data.rows;
                //        }
                //    },
                //    function (msg) {
                //        Tip.message("获取审核明细信息失败", "error");
                //    }
                //);
                $scope.modalConfirm = function () {
                    if (!bill.id) {
                        Tip.message("无法找到账单信息", "error");
                        return;
                    }
                    $rootScope.hmsLoading.show();
                    HmsHttp.post(contentPath + "/bill/specialConfirmAmount", {
                        billId: bill.id
                    }).success(function (data) {
                        if (!data.success) {
                            Tip.message(data.errorMsg, "error");
                            return;
                        }
                        Tip.message("确认成功");
                        $scope.modalConfirmClose();
                        $scope.$broadcast("loadSubData");
                        //loadData();
                    }).error(function (msg) {
                        $rootScope.hmsLoading.hide();
                        Tip.message("确认金额异常", "error");
                    });
                };

                $scope.modalConfirmClose = function () {
                    HmsModalService.hide();
                };
                HmsModalService.show({
                    contentTmpl: "confirm-bill-special.tmpl.html",
                    scope: $scope,
                });

            }, function (result) {
                $rootScope.hmsLoading.hide();
                console.log(result);
            })
        };
        /**
         *	按钮-审核详情
         */
        $scope.showReadCheck = function (row) {

            //        if(row.isFreeze=='Y'){
            //            showFreezeDialog();
            //            return;
            //        }
            $scope.sendCheck(row);
        };
        /**
         *	按钮-提交审核
         */
        $scope.verifyBill = function (row) {
            if (row.isFreeze == 'Y') {
                showFreezeDialog();
                return;
            }
            $scope.sendCheck(row);
        };

        /**
         *	特价账单 - 有异议
         */
        $scope.verifySpecialBill = function (row) {
            //        if(row.isFreeze=='Y'){
            //            showFreezeDialog();
            //            return;
            //        }
            var bill = row;
            $scope.bill = bill;
            //
            var checkAside = $aside({
                title: '反馈信息',
                template: 'aside-sendCheck-special.tpl.html',
                animation: "am-slide-left",
                placement: 'left',
                show: true,
                scope: $scope
            });

            //
            $scope.asideConfirm = function () {

                $scope.cusContent = "是否确定要送出审核信息？";
                $scope.modalConfirm = function () {
                    $scope.sendSpecial(bill).then(function () {
                        HmsModalService.hide();
                        checkAside.hide();
                    });
                };

                if (!bill.id) {
                    Tip.message("无法找到账单信息", "error");
                    return;
                }

                if (!bill.hotelchangecost) {
                    Tip.message("请填写调整后账单金额", "error");
                    return;
                }
                if (!bill.currMemo) {
                    Tip.message("该账单发起审核原因不可为空", "error");
                    return;
                }

                $scope.modalConfirmClose = function () {
                    HmsModalService.hide();

                };

                HmsModalService.show({
                    contentTmpl: "modal-confirm-notice.tmpl.html",
                    scope: $scope,
                });
            };

            //
            $scope.asideConfirmCancel = function () {
                bill.currMemo = "";
                checkAside.hide();
            };
        };
        /**
         *	特价账单 - 有异议
         */
        $scope.baseVerifyBill = function (row) {
            var bill = row;
            $scope.bill = bill;
            //
            var checkAside = $aside({
                title: '账单申诉',
                template: 'aside-sendCheck-base.tpl.html',
                animation: "am-slide-left",
                placement: 'left',
                show: true,
                scope: $scope
            });

            //
            $scope.asideConfirm = function () {

                $scope.cusContent = "是否确定提交申诉信息？";
                $scope.baseConfirm = function () {
                    $scope.sendFeedback(bill).then(function () {
                        HmsModalService.hide();
                        checkAside.hide();
                    });
                };

                if (!bill.id) {
                    Tip.message("无法找到账单信息", "error");
                    return;
                }

                if (!bill.hotelChange) {
                    Tip.message("请填写调整账单金额", "error");
                    return;
                }
                if (!bill.hotelReason) {
                    Tip.message("申诉原因不可为空", "error");
                    return;
                }

                $scope.modalConfirmClose = function () {
                    HmsModalService.hide();

                };

                HmsModalService.show({
                    contentTmpl: "base-confirm-notice.tmpl.html",
                    scope: $scope,
                });
            };

            //
            $scope.asideConfirmCancel = function () {
                bill.currMemo = "";
                checkAside.hide();
            };
        };

        /**
         *	按钮-发起审核
         */
        $scope.sendCheck = function (row) {

            //        if(row.isFreeze=='Y'){
            //            showFreezeDialog();
            //            return;
            //        }
            var bill = row;
            bill.checkDetails = [];
            $scope.bill = bill;

            //
            var checkAside = $aside({
                title: '反馈信息',
                template: 'aside-sendCheck.tpl.html',
                animation: "am-slide-left",
                placement: 'left',
                show: true,
                scope: $scope
            });

            //
            $scope.getCheckDetails = function () {
                if (!bill.id) {
                    Tip.message("无法找到账单信息", "error");
                    return;
                }
                bill.loading = true;
                var url = contentPath + "/bill/simpleCheckInfo";
                var params = {
                    billId: bill.id,
                    pageNum: 1,
                    pageSize: 1000,
                    total: 1000
                };
                HmsHttp.post(url, params).then(
                    function (res) {
                        if (res.data) {
                            // bill.checkDetails = res.data.rows;
                            // _.map(bill.checkDetails, function(detail) {
                            // 	detail.checktime = new Date(detail.checktime).format("yyyy年MM月dd日 hh时mm分");
                            // });
                            var canShowMap = {
                                "2": null,
                                "3": null,
                                "6": null,
                                "7": null
                            }
                            var canShowList = [];
                            for (var i = res.data.rows.length - 1; i >= 0; i--) {
                                var detail = res.data.rows[i];
                                var status = detail.checkstatus;
                                if (!status || canShowMap[status] || typeof canShowMap[status] == "undefined") {
                                    continue;
                                }
                                canShowMap[status] = detail;
                                canShowList.push(detail);
                                detail.checktime = new Date(detail.checktime).format("yyyy年MM月dd日 hh时mm分");
                            }

                            bill.checkDetails = canShowList;
                        }
                        bill.loading = false;
                    },
                    function (msg) {
                        Tip.message("获取审核明细信息失败", "error");
                        bill.loading = false;
                    }
                    );
            };

            //
            $timeout(function () {
                $scope.getCheckDetails();
            }, 500);

            //
            $scope.asideConfirm = function () {

                $scope.cusContent = "是否确定要送出审核信息？";
                $scope.modalConfirm = function () {
                    $scope.send(bill).then(function () {
                        HmsModalService.hide();
                        checkAside.hide();
                        bill.checkDetails = [];
                    });
                };

                if (!bill.id) {
                    Tip.message("无法找到账单信息", "error");
                    return;
                }

                if (!bill.currAmount) {
                    Tip.message("请填写调整后账单金额", "error");
                    return;
                }
                bill.changecost = Math.round((bill.currAmount + bill.billcost) * 10) / 10;

                if (!bill.currMemo) {
                    Tip.message("该账单发起审核原因不可为空", "error");
                    return;
                }

                // var rate = Math.abs(bill.changecost / bill.billcost) * 100;
                // if (rate > 10) {
                // 	Tip.message("金额浮动请勿超过10%", "error");
                // 	return;
                // }

                $scope.modalConfirmClose = function () {
                    HmsModalService.hide();

                };

                HmsModalService.show({
                    contentTmpl: "modal-confirm-notice.tmpl.html",
                    scope: $scope,
                });
            };

            //
            $scope.asideConfirmCancel = function () {
                bill.currMemo = "";
                checkAside.hide();
            };
        };

        $scope.send = function (bill) {
            $rootScope.hmsLoading.show();
            return HmsHttp.post(contentPath + "/bill/simpleCheckAmount",
                {
                    billId: bill.id,
                    checkText: bill.currMemo,
                    changeCost: bill.changecost
                }
                )
                .success(function (data) {
                    if (!data.success) {
                        $scope.$broadcast("loadSubData");
                        //loadData();
                        Tip.message(data.errorMsg, "error");
                        return;
                    }
                    $scope.$broadcast("loadSubData");
                    //loadData();
                    Tip.message("发起审核成功");
                })
                .error(function (msg) {
                    $rootScope.hmsLoading.hide();
                    Tip.message("发送异常", "error");
                });
        };


        $scope.sendSpecial = function (bill) {
            $rootScope.hmsLoading.show();
            return HmsHttp.post(contentPath + "/feedback/create",
                {
                    type: 1,
                    fromBill: bill.id,
                    hotelReason: bill.currMemo,
                    hotelChange: bill.hotelchangecost
                }
                )
                .success(function (data) {
                    if (!data.success) {
                        $scope.$broadcast("loadSubData");
                        //loadData();
                        Tip.message(data.errorMsg, "error");
                        return;
                    }
                    $scope.$broadcast("loadSubData");
                    //loadData();
                    Tip.message("发起审核成功");
                })
                .error(function (msg) {
                    $rootScope.hmsLoading.hide();
                    Tip.message("发送异常", "error");
                });
        };
        //保存账单申诉信息
        $scope.sendFeedback = function (bill) {
            $rootScope.hmsLoading.show();
            return HmsHttp.post(contentPath + "/feedback/create",
                {
                    type: 2,
                    fromBill: bill.id,
                    hotelChange: bill.hotelChange,
                    hotelReason: bill.hotelReason
                }
                )
                .success(function (data) {
                    if (!data.success) {
                        $scope.$broadcast("loadSubData");
                        //loadData();
                        Tip.message(data.errorMsg, "error");
                        return;
                    }
                    $scope.$broadcast("loadSubData");
                    //loadData();
                    Tip.message("发起审核成功");
                })
                .error(function (msg) {
                    $rootScope.hmsLoading.hide();
                    Tip.message("发送异常", "error");
                });
        };


        $scope.showOrderDetail = function (row) {
            var bill = row;
            $scope.billIdForList = bill.id;
            HmsModalService.show({
                title: "账期明细列表",
                bodyTmpl: "account-list-ctrl.tmpl.html",
                scope: $scope,
                cls: "order-detail",
                backdrop: true
            });

            $scope.closeModal = function () {
                HmsModalService.hide();
            };
        };


        $scope.showSpecialBillDetail = function (row) {
            var bill = row;
            $scope.billIdForList = bill.id;
            HmsModalService.show({
                title: "账期明细列表",
                bodyTmpl: "account-list-ctrl-special.tmpl.html",
                scope: $scope,
                cls: "order-detail",
                backdrop: true
            });

            $scope.closeModal = function () {
                HmsModalService.hide();
            };
        };
        $scope.showBaseBillDetail = function (row) {
            var bill = row;
            $scope.billIdForList = bill.id;
            HmsModalService.show({
                title: "账单明细列表",
                bodyTmpl: "account-list-ctrl-base.tmpl.html",
                scope: $scope,
                cls: "order-detail",
                backdrop: true
            });

            $scope.closeModal = function () {
                HmsModalService.hide();
            };
        };
        //显示酒店银行账号
        $scope.showBankCount = function () {
            $rootScope.hmsLoading.show();
            loadBankInfo(function (result) {
                $rootScope.hmsLoading.hide();
                $scope.bankInfo = result;
                HmsModalService.show({
                    contentTmpl: contentPath + "/main/account/template/bank-info.html",
                    scope: $scope,
                });
            }, function (result) {
                $rootScope.hmsLoading.hide();
                console.log(result);
            })
        };
        //提前结算按钮
        $scope.preCalAccount = function () {
            $rootScope.hmsLoading.show();
            BillService.preCalAccount()
                .success(function (result) {
                    $rootScope.hmsLoading.hide();
                    if (result.success) {
                        //Tip.message('提前结算操作成功!');
                        layer.alert('账单后台处理中，工作日次日可查看');
                    } else {
                        if (result.errorCode == 1) {
                            layer.alert('凌晨0点至凌晨6点为账单金额生成时间，请凌晨6点以后再点击提前结算！');
                        } else {
                            Tip.message('提前结算操作失败：' + result.errorMsg, 'error');
                        }
                    }
                })
                .error(function (error) {
                    $rootScope.hmsLoading.hide();
                    console.log(error);
                })
        };
        //加载账号信息
        function loadBankInfo(success, error) {
            BankInfoService.find()
                .success(success)
                .error(error);
        };
        //显示冻结提示框
        function showFreezeDialog() {
            $scope.cusContent = "该账单为冻结状态，不可操作，请联系结算员。";
            $scope.modalConfirm = function () {
                HmsModalService.hide();
            };
            $scope.modalConfirmClose = function () {
                HmsModalService.hide();
            };
            HmsModalService.show({
                contentTmpl: "modal-confirm-notice.tmpl.html",
                scope: $scope,
            });
        }

        //初始化调用（监听当前选择项目变化）
        $scope.$watch('Nav.selected', function (newVal) {
            $scope.Nav.items[newVal].loaded = true;
        });

        init();
    }])
    .controller('FinanceMonthController', ['$scope', 'HmsHttp', '$rootScope', function ($scope, HmsHttp, $rootScope) {

        var StatusDict = { //结算状态
            0: "初始化",
            1: "待确认",
            2: "有异议",
            3: "待审核",
            4: "审核退回",
            5: "已审核",
            6: "已确认",
            7: "已结算",
            8: "已拆分或调整",
            9: "拆分或调整审核退回",
            10: "已拆分或调整",
            12: "已提交"
        }, financeStatusDict = { //订单状态
            1: "未支付",
            2: "已支付"
        }, TypeDict = {
            'Y': '阈值',
            'N': '月度'
        }, loadData = function () {
            $rootScope.hmsLoading.show();
            var nav = $scope.MonthFniance.nav,
                leftItem = nav.items[nav.selected],
                pagination = $scope.MonthFniance['pagination'], //分页
                data = $scope.MonthFniance['data'], //数据
                act = $scope.MonthFniance['action'], //操作功能名
                formatFunc = $scope.MonthFniance['formatFunc'],
                url = contentPath + "/bill/" + act,
                params = {
                    pageNum: pagination.currentPage,
                    pageSize: pagination.pageCount,
                    checkStatus: leftItem['action']
                };
            if (pagination.totalItems) {
                params.total = pagination.totalItems;
            }
            HmsHttp.post(url, params)
                .success(function (result) {
                    formatData(result.rows);
                    $scope.MonthFniance['data'] = result.rows;
                    $scope.MonthFniance['pagination'].totalItems = parseInt(result.total);
                    $scope.MonthFniance['loaded'] = true;
                    $rootScope.hmsLoading.hide();
                }).error(function (error) {
                    $rootScope.hmsLoading.hide();
                });
        }, formatData = function (rows) {
            _.map(rows, function (row) {
                var checkstatus = row.checkstatus;
                row.checkstatusTxt = StatusDict[row.checkstatus];
                row.financeStatusTxt = financeStatusDict[row.financeStatus];
                if (!row.isThreshold) {
                    row.isThreshold = 'N';
                }
                row.type = TypeDict[row.isThreshold]; //类型
                if (!row.financeStatusTxt) {
                    row.financeStatusTxt = '--';
                }
                row.begintime = DateUtils.formatDate4TimeVal(row.begintime, 'yyyy-MM-dd');
                row.endtime = DateUtils.formatDate4TimeVal(row.endtime, 'yyyy-MM-dd');
            });
        }, reset = function () {
            //重置
            var pagination = $scope.MonthFniance.pagination;
            pagination.totalItems = 0;
            pagination.currentPage = 1;
            pagination.numPages = 1;

            $scope.MonthFniance['data'] = [];
        };
        $scope.MonthFniance = {
            'action': 'byHotelid',
            'pagination': {
                'totalItems': 0,
                'currentPage': 1,
                'pageCount': 5,
                'numPages': 1,
                'maxSize': 5,
                'pageChanged': function () {
                    loadData();
                }
            },
            'nav': {
                'selected': 0,
                'items': [
                    {
                        'id': 'all',
                        'displayName': '全部',
                        'action': ""
                    },
                    {
                        'id': 'confirming',
                        'displayName': '待确认',
                        'action': "1"
                    },
                    {
                        'id': 'disagreed',
                        'displayName': '有异议',
                        'action': "2"
                    },
                    {
                        'id': 'checking',
                        'displayName': '待审核',
                        'action': "3"
                    },
                    {
                        'id': 'confirmed',
                        'displayName': '已确认',
                        'action': "6"
                    },
                    {
                        'id': 'commited',
                        'displayName': '已提交',
                        'action': "12"
                    }/*,
                {
                    'id':'finished',
                    'displayName':'已结算',
                    'action':"7"
                }*/
                ]
            },
            'columns': [
                {
                    field: "type",
                    displayName: "分类",
                    class: 'text-center',
                    width: '5%'
                },
                {
                    field: "begintime",
                    displayName: "账单开始日期",
                    class: 'text-center',
                    width: '5%'
                },
                {
                    field: "endtime",
                    displayName: "账单结束日期",
                    class: 'text-center',
                    width: '5%'
                },
                {
                    field: "prepaymentcost",
                    displayName: "预付金额",
                    class: 'text-right',
                    width: '5%'
                },
                {
                    field: "cutoffcost",
                    displayName: "切客补贴金额",
                    class: 'text-right',
                    width: '5%'
                },
                {
                    field: "otherdiscountcost",
                    displayName: "眯客优惠券金额",
                    class: 'text-right',
                    width: '5%'
                },
                {
                    field: "availablemoney",
                    displayName: "红包金额",
                    class: 'text-center',
                    width: '5%'
                },
                {
                    field: "servicecost",
                    displayName: "服务费金额",
                    class: 'text-right',
                    width: '5%'
                },
                {
                    field: "billcost",
                    displayName: "账单金额",
                    class: 'text-right',
                    width: '5%'
                },
                {
                    field: "changecost",
                    displayName: "调整金额",
                    class: 'text-center',
                    width: '5%'
                },
                {
                    field: "readjustcost",
                    displayName: "调整后金额",
                    class: 'text-center',
                    width: '5%'
                },
                {
                    field: "finalcost",
                    displayName: "商家收款金额",
                    class: 'text-right',
                    width: '5%'
                },
                {
                    field: "splitcost",
                    displayName: "销售收款金额",
                    class: 'text-right',
                    width: '5%'
                },
                {
                    field: "checkstatusTxt",
                    displayName: "账单状态",
                    class: 'text-center',
                    width: '10%'
                },
                {
                    field: "financeStatusTxt",
                    displayName: "结算状态",
                    class: 'text-center',
                    width: '10%'
                }
            ]
        };

        //初始化调用（监听当前选择项目变化）
        $scope.$watch('MonthFniance.nav.selected', function (newVal, oldVal) {
            if (newVal != oldVal) {
                reset();
                loadData();
            }
        });

        $scope.$on("loadSubData", function () {
            loadData();
        })

        loadData();
    }])
    .controller('FinanceWeekController', ['$scope', 'HmsHttp', '$rootScope', function ($scope, HmsHttp, $rootScope) {
        var loadData = function () {
            $rootScope.hmsLoading.show();
            var nav = $scope.WeekFinance.nav,
                leftItem = nav.items[nav.selected],
                pagination = $scope.WeekFinance['pagination'], //分页
                data = $scope.WeekFinance['data'], //数据
                act = $scope.WeekFinance['action'], //操作功能名
                url = contentPath + "/bill/" + act,
                params = {
                    pageNum: pagination.currentPage,
                    pageSize: pagination.pageCount,
                    checkStatus: 6 //状态为6:已确认的数据
                };
            if (pagination.totalItems) {
                params.total = pagination.totalItems;
            }
            HmsHttp.post(url, params)
                .success(function (result) {
                    formatData(result.rows);
                    $scope.WeekFinance['data'] = result.rows;
                    $scope.WeekFinance['pagination'].totalItems = parseInt(result.total);
                    $scope.WeekFinance['loaded'] = true;
                    $rootScope.hmsLoading.hide();
                }).error(function (error) {
                    $rootScope.hmsLoading.hide();
                });
        }, formatData = function (rows) {
            _.map(rows, function (row) {
                row.starttime = DateUtils.formatDate4TimeVal(row.starttime, 'yyyy-MM-dd');
                row.endtime = DateUtils.formatDate4TimeVal(row.endtime, 'yyyy-MM-dd');
            });
        };
        $scope.WeekFinance = {
            'action': 'getWeekOrders',
            'pagination': {
                'totalItems': 0,
                'currentPage': 1,
                'pageCount': 5,
                'numPages': 1,
                'maxSize': 5,
                'pageChanged': function () {
                    loadData();
                }
            },
            'nav': {
                'selected': 0,
                'items': []
            },
            'columns': [
                {
                    field: "billcost",
                    displayName: "总金额",
                    class: 'text-center',
                    width: '5%'
                },
                {
                    field: "starttime",
                    displayName: "账单开始日期",
                    class: 'text-center',
                    width: '5%'
                },
                {
                    field: "endtime",
                    displayName: "账单结束日期",
                    class: 'text-center',
                    width: '5%'
                },
                {
                    field: "cutoffcost",
                    displayName: "切客补贴金额",
                    class: 'text-center',
                    width: '5%'
                },
                {
                    field: "topaydiscount",
                    displayName: "到付补贴金额",
                    class: 'text-center',
                    width: '5%'
                },
                {
                    field: "prepaymentdiscount",
                    displayName: "预付补贴金额",
                    class: 'text-center',
                    width: '5%'
                },
                {
                    field: "deductingmoney",
                    displayName: "核单扣款",
                    class: 'text-center',
                    width: '5%'
                },
                {
                    field: "deductingservicemoney",
                    displayName: "冲抵月账单应收金额",
                    class: 'text-center',
                    width: '5%'
                },
                {
                    field: "deductingshoppingmoney",
                    displayName: "扣货款",
                    class: 'text-center',
                    width: '5%'
                },
                {
                    field: "deductingothermoney",
                    displayName: "扣其他",
                    class: 'text-center',
                    width: '5%'
                },
                {
                    field: "note",
                    displayName: "备注",
                    class: 'text-center',
                    width: '5%'
                }
            ]
        };

        loadData();

        $scope.$on("loadSubData", function () {
            loadData();
        });

    }])
    .controller('FinanceNewController', ['$scope', 'HmsHttp', '$rootScope', 'HmsModalService', '$location', function ($scope, HmsHttp, $rootScope, HmsModalService, $location) {
        var loadData = function () {
            $rootScope.hmsLoading.show();
            var nav = $scope.BaseFinance.nav,
                leftItem = nav.items[nav.selected],
                pagination = $scope.BaseFinance['pagination'], //分页
                data = $scope.BaseFinance['data'], //数据
                action = $scope.BaseFinance['action'], //操作功能名
                url = contentPath + "/billOrderWeek/" + action,
                params = {
                    pageNum: pagination.currentPage,
                    pageSize: pagination.pageCount,
                    status: leftItem['action']
                };
            if (pagination.totalItems) {
                params.total = pagination.totalItems;
            }
            HmsHttp.post(url, params)
                .success(function (result) {
                    formatData(result.rows);
                    $scope.BaseFinance['data'] = result.rows;//result.rows;
                    $scope.BaseFinance['pagination'].totalItems = parseInt(result.total);
                    $scope.BaseFinance['loaded'] = true;
                    $rootScope.hmsLoading.hide();
                }).error(function (error) {
                    $rootScope.hmsLoading.hide();
                });
            $scope.BaseFinance['pagination'].totalItems = 3;
            $scope.BaseFinance['loaded'] = true;

        }, formatData = function (rows) {
            _.map(rows, function (row) {
                if (row.beginTime) {
                    row.beginTime = DateUtils.formatDate4TimeVal(row.beginTime, 'yyyy-MM-dd');
                }
                if (row.endTime) {
                    row.endTime = DateUtils.formatDate4TimeVal(row.endTime, 'yyyy-MM-dd');
                }

            });
        }, reset = function () {
            //重置
            var pagination = $scope.BaseFinance.pagination;
            pagination.totalItems = 0;
            pagination.currentPage = 1;
            pagination.numPages = 1;

            $scope.BaseFinance['data'] = [];
        };
        $scope.BaseFinance = {
            'action': 'query',
            'pagination': {
                'totalItems': 0,
                'currentPage': 1,
                'pageCount': 5,
                'numPages': 1,
                'maxSize': 5,
                'pageChanged': function () {
                    loadData();
                }
            },
            'nav': {
                'selected': 0,
                'items': [
                    {
                        'id': 'all',
                        'displayName': '全部',
                        'action': ""
                    },
                    {
                        'id': 'checking',
                        'displayName': '付款中',
                        'action': "5"
                    },
                    {
                        'id': 'commited',
                        'displayName': '已付款',
                        'action': "6"
                    }
                ]
            },
            'columns': [
                {
                    field: "beginTime",
                    displayName: "账单开始日期",
                    class: 'text-center',
                },
                {
                    field: "endTime",
                    displayName: "账单结束日期",
                    class: 'text-center',
                }, {
                    field: "billCost",
                    displayName: "账单金额（元）",
                    class: 'text-center',
                }, {
                    field: "changeCost",
                    displayName: "历史补差",
                    class: 'text-center',
                },
                //{
                //    field: "userCost",
                //    displayName: "预付金额",
                //    class: 'text-center',
                //},
                {
                    field: "qieKeMoney",
                    displayName: "切客补贴（元）",
                    class: 'text-center',
                },
                //{
                //    field: "ticketMoney",
                //    displayName: "眯客优惠券金额",
                //    class: 'text-center',
                //},
                //{
                //    field: "availableMoney",
                //    displayName: "红包金额",
                //    class: 'text-center',
                //},
                {
                    field: "serviceCost",
                    displayName: "服务费（元）",
                    class: 'text-center',
                }, {
                    field: "hotelCost",
                    displayName: "结算金额（元）",
                    class: 'text-center',
                }, {
                    field: "settleStatusName",
                    displayName: "结算状态",
                    class: 'text-center',
                }
            ]
        };

        loadData();

        //初始化调用（监听当前选择项目变化）
        $scope.$watch('BaseFinance.nav.selected', function (newVal, oldVal) {
            if (newVal != oldVal) {
                reset();
                loadData();
            }
        });

        $scope.$on("loadSubData", function () {
            loadData();
        });
    }])
    .controller('FinanceAppealController', ['$scope', 'HmsHttp', '$rootScope', function ($scope, HmsHttp, $rootScope) {
        var loadData = function () {
            $rootScope.hmsLoading.show();
            var nav = $scope.FinanceAppeal.nav,
                leftItem = nav.items[nav.selected],
                pagination = $scope.FinanceAppeal['pagination'], //分页
                data = $scope.FinanceAppeal['data'], //数据
                action = $scope.FinanceAppeal['action'], //操作功能名
                url = contentPath + "/feedback/" + action,
                params = {
                    pageNum: pagination.currentPage,
                    pageSize: pagination.pageCount,
                    status: leftItem['action']
                };

            if (pagination.totalItems) {
                params.total = pagination.totalItems;
            }
            HmsHttp.post(url, params)
                .success(function (result) {
                    formatData(result.rows);
                    $scope.FinanceAppeal['data'] = result.rows;
                    $scope.FinanceAppeal['pagination'].totalItems = parseInt(result.total);
                    $scope.FinanceAppeal['loaded'] = true;
                    $rootScope.hmsLoading.hide();
                }).error(function (error) {
                    $rootScope.hmsLoading.hide();
                });
            $scope.FinanceAppeal['pagination'].totalItems = 3;
            $scope.FinanceAppeal['loaded'] = true;

        }, formatData = function (rows) {
            _.map(rows, function (row) {
                if (row.createTime) {
                    row.beginTime = DateUtils.formatDate4TimeVal(row.beginTime, 'yyyy-MM-dd');
                }
                if (row.endTime) {
                    row.endTime = DateUtils.formatDate4TimeVal(row.endTime, 'yyyy-MM-dd');
                }

            });
        }, reset = function () {
            //重置
            var pagination = $scope.FinanceAppeal.pagination;
            pagination.totalItems = 0;
            pagination.currentPage = 1;
            pagination.numPages = 1;

            $scope.FinanceAppeal['data'] = [];
        };
        $scope.FinanceAppeal = {
            'action': 'query',
            'pagination': {
                'totalItems': 0,
                'currentPage': 1,
                'pageCount': 10,
                'numPages': 1,
                'maxSize': 10,
                'pageChanged': function () {
                    loadData();
                }
            },
            'nav': {
                'selected': 0,
                'items': [
                    {
                        'id': 'all',
                        'displayName': '全部',
                        'action': ""
                    },
                    {
                        'id': 'checking',
                        'displayName': '未处理',
                        'action': "1"
                    },
                    {
                        'id': 'commited',
                        'displayName': '已处理',
                        'action': "2"
                    }
                ]
            },
            'columns': [
                {
                    field: "typeName",
                    displayName: "账单类型",
                    class: 'text-center',
                },
                {
                    field: "beginTime",
                    displayName: "账单开始日期",
                    class: 'text-center',
                },
                {
                    field: "endTime",
                    displayName: "账单结束日期",
                    class: 'text-center',
                }, {
                    field: "billCost",
                    displayName: "账单金额（元）",
                    class: 'text-center',
                },
                {
                    field: "hotelChange",
                    displayName: "申请补贴（元）",
                    class: 'text-center',
                },
                {
                    field: "hotelReason",
                    displayName: "申请理由",
                    class: 'text-center',
                    width: '200'
                }, {
                    field: "mkReason",
                    displayName: "处理结果",
                    class: 'text-center',
                    width: '200'
                }, {
                    field: "mkChange",
                    displayName: "实际补贴（元）",
                    class: 'text-center',
                }, {
                    field: "statusName",
                    displayName: "处理状态",
                    class: 'text-center',
                }
            ]
        };

        loadData();

        //初始化调用（监听当前选择项目变化）
        $scope.$watch('FinanceAppeal.nav.selected', function (newVal, oldVal) {
            if (newVal != oldVal) {
                reset();
                loadData();
            }
        });

        $scope.$on("loadSubData", function () {
            loadData();
        });
    }])
    .controller('FinanceSpecialController', ['$scope', 'HmsHttp', '$rootScope', function ($scope, HmsHttp, $rootScope) {
        var loadData = function () {
            $rootScope.hmsLoading.show();
            var nav = $scope.SpecialFinance.nav,
                leftItem = nav.items[nav.selected],
                pagination = $scope.SpecialFinance['pagination'], //分页
                data = $scope.SpecialFinance['data'], //数据
                act = $scope.SpecialFinance['action'], //操作功能名
                url = contentPath + "/bill/" + act,
                params = {
                    pageNum: pagination.currentPage,
                    pageSize: pagination.pageCount,
                    checkStatus: leftItem['action'] //状态为6:已确认的数据
                };

            if (pagination.totalItems) {
                params.total = pagination.totalItems;
            }
            HmsHttp.post(url, params)
                .success(function (result) {
                    formatData(result.rows);

                    $scope.SpecialFinance['data'] = result.rows;
                    $scope.SpecialFinance['pagination'].totalItems = parseInt(result.total);
                    $scope.SpecialFinance['loaded'] = true;
                    $rootScope.hmsLoading.hide();
                }).error(function (error) {
                    $rootScope.hmsLoading.hide();
                });
            $scope.SpecialFinance['pagination'].totalItems = 3;
            $scope.SpecialFinance['loaded'] = true;

        }, formatData = function (rows) {
            _.map(rows, function (row) {
                row.begintime = DateUtils.formatDate4TimeVal(row.begintime, 'yyyy-MM-dd');
                row.endtime = DateUtils.formatDate4TimeVal(row.endtime, 'yyyy-MM-dd');
            });
        }, reset = function () {
            //重置
            var pagination = $scope.SpecialFinance.pagination;
            pagination.totalItems = 0;
            pagination.currentPage = 1;
            pagination.numPages = 1;

            $scope.SpecialFinance['data'] = [];
        };
        $scope.SpecialFinance = {
            'action': 'getSpecialBills',
            'pagination': {
                'totalItems': 0,
                'currentPage': 1,
                'pageCount': 5,
                'numPages': 1,
                'maxSize': 5,
                'pageChanged': function () {
                    loadData();
                }
            },
            'nav': {
                'selected': 0,
                'items': [
                    {
                        'id': 'all',
                        'displayName': '全部',
                        'action': ""
                    },
                    {
                        'id': 'confirming',
                        'displayName': '付款中',
                        'action': "1"
                    },
                    {
                        'id': 'disagreed',
                        'displayName': '已付款',
                        'action': "2"
                    }
                    //{
                    //    'id': 'confirming',
                    //    'displayName': '待确认',
                    //    'action': "1"
                    //},
                    //{
                    //    'id': 'disagreed',
                    //    'displayName': '有异议',
                    //    'action': "2"
                    //},
                    //{
                    //    'id': 'checking',
                    //    'displayName': '待审核',
                    //    'action': "3"
                    //},
                    //{
                    //    'id': 'confirmed',
                    //    'displayName': '已确认',
                    //    'action': "6"
                    //},
                    //{
                    //    'id': 'commited',
                    //    'displayName': '已提交',
                    //    'action': "12"
                    //}
                ]
            },
            'columns': [
                {
                    field: "begintime",
                    displayName: "账单开始日期",
                    class: 'text-center',
                },
                {
                    field: "endtime",
                    displayName: "账单结束日期",
                    class: 'text-center',
                },
                {
                    field: "billcost",
                    displayName: "总金额",
                    class: 'text-center',
                },
                {
                    field: "ordernum",
                    displayName: "订单数",
                    class: 'text-center',
                },
                {
                    field: "changecost",
                    displayName: "调整金额",
                    class: 'text-center',
                },
                {
                    field: "finalcost",
                    displayName: "结算金额",
                    class: 'text-center',
                }
            ]
        };

        loadData();

        //初始化调用（监听当前选择项目变化）
        $scope.$watch('SpecialFinance.nav.selected', function (newVal, oldVal) {
            if (newVal != oldVal) {
                reset();
                loadData();
            }
        });

        $scope.$on("loadSubData", function () {
            loadData();
        });
    }])
    .controller("financeDetailListOuterController", ["$rootScope", "$scope", function ($rootScope, $scope) {
        /**TAB*/
        $scope.tabs = {};
        $scope.tabs.activeTab = 0;
    }])
    .controller("financeDetailValidListController", ["$rootScope", "$scope", "HmsHttp", "$timeout", "HmsModalService", "$location", "$log", '$aside',
        function ($rootScope, $scope, HmsHttp, $timeout, HmsModalService, $location, $log, $aside) {

            $scope.rowsData = [];
            //标题头	
            $scope.columns = [
                {
                    field: "orderId",
                    displayName: "订单号",
                    class: 'text-center',
                    width: '10%'
                },
                {
                    field: "orderType",
                    displayName: "支付类型",
                    class: 'text-center',
                    width: '10%'
                },
                {
                    field: "roomTypeName",
                    displayName: "房型",
                    class: 'text-center',
                    width: '10%'
                },
                {
                    field: "roomNo",
                    displayName: "房间",
                    class: 'text-center',
                    width: '10%'
                },
                {
                    field: "orderCreateTime",
                    displayName: "下单时间",
                    class: 'text-center',
                    width: '15%'
                },
                {
                    field: "beginTime",
                    displayName: "入住时间",
                    class: 'text-center',
                    width: '15%'
                },
                {
                    field: "endTime",
                    displayName: "离店时间",
                    class: 'text-center',
                    width: '15%'
                },
                {
                    field: "dayNumber",
                    displayName: "间夜数",
                    class: 'text-center',
                    width: '5%'
                },
                {
                    field: "allCost",
                    displayName: "订单金额(元)",
                    class: 'text-right',
                    width: '5%'
                },
                {
                    field: "cutCost",
                    displayName: "切客优惠金额(元)",
                    class: 'text-right',
                    width: '5%'
                },
                {
                    field: "otherGive",
                    displayName: "其他优惠金额(元)",
                    class: 'text-right',
                    width: '5%'
                },
                {
                    field: "serviceCost",
                    displayName: "产生服务费(元)",
                    class: 'text-right',
                    width: '5%'
                }
            ];
            $scope.totalItems = 0; //数据总条数
            $scope.currentPage = 1; //当前页
            $scope.pageCount = 20; //每页显示数量
            $scope.numPages = 1; //总页数
            $scope.maxSize = 5; //当总页数大于多少的时候，分页开始显示...
            //当前页发生变化时操作
            $scope.pageChanged = function () {
                loadData();
            };

            //初始化操作
            function init() {
                var hideCols = [];
                if ($rootScope.RULE_CODE == 'A') {
                    hideCols = [];
                } else if ($rootScope.RULE_CODE == 'B') {
                    hideCols = ['cutCost'];
                }
                angular.forEach(hideCols, function (hidekey) {
                    $scope.columns.splice(_.findLastIndex($scope.columns, { 'field': hidekey }), 1);
                });
            };
            //加载数据
            function loadData() {
                $rootScope.hmsLoading.show();
                var url = contentPath + "/bill/simpleBillInfo";
                var params = {
                    billId: $scope.$parent.billIdForList,
                    pageNum: $scope.currentPage,
                    pageSize: $scope.pageCount,
                    method: "valid"
                };
                if ($scope.totalItems) {
                    params.total = $scope.totalItems;
                }
                HmsHttp.post(url, params)
                    .success(function (data) {
                        $rootScope.hmsLoading.hide();
                        formatData(data);
                        $scope.rowsData = data.rows;
                        $scope.totalItems = parseInt(data.total);
                    })
                    .error(function (msg) {
                        $rootScope.hmsLoading.hide();
                        Tip.message("数据加载异常", "error");
                    });
            };
            //格式化数据
            function formatData(data) {
                var rows = data.rows;
                _.map(rows, function (row) {
                    var orderType = row.orderType;
                    var beginTime = row.beginTime;
                    var endTime = row.endTime;
                    var orderCreateTime = row.orderCreateTime;

                    row.dfbtje = 0;
                    row.yfbtje = 0;
                    if ((row.orderStatus == 180 || row.orderStatus == 190 || row.orderStatus == 200)
                        && row.spreaduser && !row.invalidreanson) {
                        if (row.orderType == 1) {
                            row.yfbtje = row.yfbtje;
                            row.dfbtje = 0;
                        } else {
                            row.yfbtje = 0;
                            row.dfbtje = row.dfbtje;
                        }
                    } else {
                        row.dfbtje = 0;
                        row.yfbtje = 0;
                    }

                    if (orderType) {
                        row.orderType = orderType == 1 ? "预付" : "到付";
                    }
                    if (beginTime) {
                        row.beginTime = new Date(beginTime).format('M月d日 h时m分');
                    }
                    if (endTime) {
                        row.endTime = new Date(endTime).format('M月d日 h时m分');
                    }
                    if (orderCreateTime) {
                        row.orderCreateTime = new Date(orderCreateTime).format('M月d日 h时m分');
                    }
                });
            };
            //初始化调用（监听当前选择项目变化）
            $scope.$watch('active', function (newVal) {
                init();
                loadData();
            });
        }])

    .controller("financeDetailValidListSpecialController", ["$rootScope", "$scope", "HmsHttp", "$timeout", "HmsModalService", "$location", "$log", '$aside',
        function ($rootScope, $scope, HmsHttp, $timeout, HmsModalService, $location, $log, $aside) {

            $scope.rowsData = [];
            //标题头	
            $scope.columns = [
                {
                    field: "orderid",
                    displayName: "订单号",
                    class: 'text-center',
                    width: '10%'
                },
                {
                    field: "roomtypename",
                    displayName: "房型",
                    class: 'text-center',
                    width: '10%'
                },
                {
                    field: "roomno",
                    displayName: "房间",
                    class: 'text-center',
                    width: '10%'
                },
                {
                    field: "ordertime",
                    displayName: "下单时间",
                    class: 'text-center',
                    width: '10%'
                },
                {
                    field: "checkintime",
                    displayName: "入住时间",
                    class: 'text-center',
                    width: '10%'
                },
                {
                    field: "checkouttime",
                    displayName: "离店时间",
                    class: 'text-center',
                    width: '10%'
                },
                {
                    field: "daynumber",
                    displayName: "间夜数",
                    class: 'text-center',
                    width: '5%'
                },
                {
                    field: "mikeprice",
                    displayName: "眯客价",
                    class: 'text-right',
                    width: '5%'
                },
                {
                    field: "discount",
                    displayName: "折扣",
                    class: 'text-right',
                    width: '5%'
                },
                {
                    field: "lezhucoins",
                    displayName: "结算价",
                    class: 'text-right',
                    width: '5%'
                }
            ];
            $scope.totalItems = 0; //数据总条数
            $scope.currentPage = 1; //当前页
            $scope.pageCount = 20; //每页显示数量
            $scope.numPages = 1; //总页数
            $scope.maxSize = 5; //当总页数大于多少的时候，分页开始显示...
            //当前页发生变化时操作
            $scope.pageChanged = function () {
                loadData();
            };


            //加载数据
            function loadData() {
                $rootScope.hmsLoading.show();
                var url = contentPath + "/bill/specialBillInfo";
                var params = {
                    billId: $scope.$parent.billIdForList,
                    pageNum: $scope.currentPage,
                    pageSize: $scope.pageCount
                };
                if ($scope.totalItems) {
                    params.total = $scope.totalItems;
                }
                HmsHttp.post(url, params)
                    .success(function (data) {
                        $rootScope.hmsLoading.hide();
                        formatData(data);
                        $scope.rowsData = data.rows;
                        $scope.totalItems = parseInt(data.total);
                    })
                    .error(function (msg) {
                        $rootScope.hmsLoading.hide();
                        Tip.message("数据加载异常", "error");
                    });
            };
            //格式化数据
            function formatData(data) {
                var rows = data.rows;
                _.map(rows, function (row) {
                    var checkintime = row.checkintime;
                    var checkouttime = row.checkouttime;
                    var ordertime = row.ordertime;

                    if (checkintime) {
                        row.checkintime = new Date(checkintime).format('M月d日 h时m分');
                    }
                    if (checkouttime) {
                        row.checkouttime = new Date(checkouttime).format('M月d日 h时m分');
                    }
                    if (ordertime) {
                        row.ordertime = new Date(ordertime).format('M月d日 h时m分');
                    }
                });
            };
            //初始化调用（监听当前选择项目变化）
            $scope.$watch('active', function (newVal) {
                loadData();
            });
        }])
    .controller("baseFinanceDetailListController", ["$rootScope", "$scope", "HmsHttp", "$timeout", "HmsModalService", "$location", "$log", '$aside',
        function ($rootScope, $scope, HmsHttp, $timeout, HmsModalService, $location, $log, $aside) {

            $scope.rowsData = [];
            //标题头
            $scope.columns = [
                {
                    field: "orderId",
                    displayName: "订单号",
                    class: 'text-center',
                    width: '10%'
                },
                {
                    field: "roomTypeName",
                    displayName: "房型",
                    class: 'text-center',
                    width: '10%'
                },
                {
                    field: "roomNo",
                    displayName: "房间",
                    class: 'text-center',
                    width: '10%'
                },
                {
                    field: "orderCreateTime",
                    displayName: "下单时间",
                    class: 'text-center',
                    width: '10%'
                },
                {
                    field: "checkinTime",
                    displayName: "入住时间",
                    class: 'text-center',
                    width: '10%'
                },
                {
                    field: "checkoutTime",
                    displayName: "离店时间",
                    class: 'text-center',
                    width: '10%'
                },
                {
                    field: "dayNumber",
                    displayName: "间夜数",
                    class: 'text-center',
                    width: '5%'
                },
                {
                    field: "billCost",
                    displayName: "结算金额（元）",
                    class: 'text-right',
                    width: '10%'
                }
            ];
            $scope.totalItems = 0; //数据总条数
            $scope.currentPage = 1; //当前页
            $scope.pageCount = 20; //每页显示数量
            $scope.numPages = 1; //总页数
            $scope.maxSize = 5; //当总页数大于多少的时候，分页开始显示...
            //当前页发生变化时操作
            $scope.pageChanged = function () {
                loadData();
            };


            //加载数据
            function loadData() {
                $rootScope.hmsLoading.show();
                var url = contentPath + "/billOrderWeek/queryDetail";
                var params = {
                    billOrderWeekId: $scope.$parent.billIdForList,
                    pageNum: $scope.currentPage,
                    pageSize: $scope.pageCount
                };
                if ($scope.totalItems) {
                    params.total = $scope.totalItems;
                }
                HmsHttp.post(url, params)
                    .success(function (data) {
                        $rootScope.hmsLoading.hide();
                        formatData(data);
                        $scope.rowsData = data.rows;
                        $scope.totalItems = parseInt(data.total);
                    })
                    .error(function (msg) {
                        $rootScope.hmsLoading.hide();
                        Tip.message("数据加载异常", "error");
                    });
            };
            //格式化数据
            function formatData(data) {
                var rows = data.rows;
                _.map(rows, function (row) {
                    var checkintime = row.checkinTime;
                    var checkouttime = row.checkoutTime;
                    var ordertime = row.orderCreateTime;

                    if (checkintime) {
                        row.checkinTime = new Date(checkintime).format('M月d日 h时m分');
                    }
                    if (checkouttime) {
                        row.checkoutTime = new Date(checkouttime).format('M月d日 h时m分');
                    }
                    if (ordertime) {
                        row.orderCreateTime = new Date(ordertime).format('M月d日 h时m分');
                    }
                });
            };
            //初始化调用（监听当前选择项目变化）
            $scope.$watch('active', function (newVal) {
                loadData();
            });
        }])
    .controller("financeDetailInvalidListController", ["$rootScope", "$scope", "HmsHttp", "$timeout", "HmsModalService", "$location", "$log", '$aside',
        function ($rootScope, $scope, HmsHttp, $timeout, HmsModalService, $location, $log, $aside) {

            $scope.rowsData = [];
            //标题头   
            $scope.columns = [
                {
                    field: "orderId",
                    displayName: "订单号",
                    class: 'text-center',
                    width: '10%'
                },
                {
                    field: "orderType",
                    displayName: "支付类型",
                    class: 'text-center',
                    width: '10%'
                },
                {
                    field: "roomTypeName",
                    displayName: "房型",
                    class: 'text-center',
                    width: '10%'
                },
                {
                    field: "roomNo",
                    displayName: "房间",
                    class: 'text-center',
                    width: '10%'
                },
                {
                    field: "beginTime",
                    displayName: "入住时间",
                    class: 'text-center',
                    width: '15%'
                },
                {
                    field: "endTime",
                    displayName: "离店时间",
                    class: 'text-center',
                    width: '15%'
                },
                {
                    field: "InvalidreasonStr",
                    displayName: "切客订单无效理由",
                    class: 'text-center',
                    width: '15%'
                },
                {
                    field: "dayNumber",
                    displayName: "间夜数",
                    class: 'text-center',
                    width: '5%'
                },
                {
                    field: "allCost",
                    displayName: "订单金额(元)",
                    class: 'text-right',
                    width: '5%'
                },
                {
                    field: "cutCost",
                    displayName: "切客优惠金额(元)",
                    class: 'text-right',
                    width: '5%'
                },
                {
                    field: "hotelGive",
                    displayName: "商户优惠金额(元)",
                    class: 'text-right',
                    width: '5%'
                },
                {
                    field: "otherGive",
                    displayName: "其他优惠金额(元)",
                    class: 'text-right',
                    width: '5%'
                },
                {
                    field: "serviceCost",
                    displayName: "产生服务费(元)",
                    class: 'text-right',
                    width: '5%'
                }
            ];
            $scope.totalItems = 0; //数据总条数
            $scope.currentPage = 1; //当前页
            $scope.pageCount = 20; //每页显示数量
            $scope.numPages = 1; //总页数
            $scope.maxSize = 5; //当总页数大于多少的时候，分页开始显示...
            //当前页发生变化时操作
            $scope.pageChanged = function () {
                loadData();
            };
            //加载数据
            function loadData() {
                $rootScope.hmsLoading.show();
                var url = contentPath + "/bill/simpleBillInfo";
                var params = {
                    billId: $scope.$parent.billIdForList,
                    pageNum: $scope.currentPage,
                    pageSize: $scope.pageCount,
                    method: "invalid"
                };
                if ($scope.totalItems) {
                    params.total = $scope.totalItems;
                }
                HmsHttp.post(url, params)
                    .success(function (data) {
                        $rootScope.hmsLoading.hide();
                        formatData(data);
                        $scope.rowsData = data.rows;
                        $scope.totalItems = parseInt(data.total);
                    })
                    .error(function (msg) {
                        $rootScope.hmsLoading.hide();
                        Tip.message("数据加载异常", "error");
                    });
            };
            //格式化数据
            function formatData(data) {
                var rows = data.rows;
                _.map(rows, function (row) {
                    var orderType = row.orderType;
                    var beginTime = row.beginTime;
                    var endTime = row.endTime;
                    var Invalidreason = row.invalidreason;
                    if (orderType) {
                        row.orderType = orderType == 1 ? "预付" : "到付";
                    }
                    if (beginTime) {
                        row.beginTime = new Date(beginTime).format('M月d日 h时m分');
                    }
                    if (endTime) {
                        row.endTime = new Date(endTime).format('M月d日 h时m分');
                    }
                    if (Invalidreason == 1) {
                        row.InvalidreasonStr = "入住人为常旅客";
                    }
                    if (Invalidreason == 2) {
                        row.InvalidreasonStr = "入住时间未满4小时";
                    }
                    if (Invalidreason == 3) {
                        row.InvalidreasonStr = "此用户本月超过4次切单";
                    }
                    if (Invalidreason == 4) {
                        row.InvalidreasonStr = "此用户今日不是第一次切单";
                    }
                });
            };
            //初始化调用（监听当前选择项目变化）
            $scope.$watch('active', function (newVal) {
                loadData();
            });
        }])
    .controller("financeDetailCollectListController", ["$rootScope", "$scope", "HmsHttp", "$timeout", "HmsModalService", "$location", "$log", '$aside',
        function ($rootScope, $scope, HmsHttp, $timeout, HmsModalService, $location, $log, $aside) {

            $scope.rowsData = [];
            //标题头   
            $scope.columns = [
                {
                    field: "payObjectStr",
                    displayName: "付款对象",
                    class: 'text-center',
                    width: '10%'
                },
                {
                    field: "name",
                    displayName: "收款人",
                    class: 'text-center',
                    width: '10%'
                },
                {
                    field: "moneyAmount",
                    displayName: "金额(元)",
                    class: 'text-right',
                    width: '10%'
                },
                {
                    field: "bank",
                    displayName: "开户行",
                    class: 'text-left',
                    width: '20%'
                },
                {
                    field: "bankBranch",
                    displayName: "开户支行",
                    class: 'text-left',
                    width: '20%'
                },
                {
                    field: "account",
                    displayName: "账号",
                    class: 'text-left',
                    width: '20%'
                },
                {
                    field: "transferTypeStr",
                    displayName: "转账类型",
                    class: 'text-center',
                    width: '10%'
                }
            ];
            $scope.totalItems = 0; //数据总条数
            $scope.currentPage = 1; //当前页
            $scope.pageCount = 20; //每页显示数量
            $scope.numPages = 1; //总页数
            $scope.maxSize = 5; //当总页数大于多少的时候，分页开始显示...
            //当前页发生变化时操作
            $scope.pageChanged = function () {
                loadData();
            };
            //加载数据
            function loadData() {
                $rootScope.hmsLoading.show();
                var url = contentPath + "/bill/simpleBillInfo";
                var params = {
                    billId: $scope.$parent.billIdForList,
                    pageNum: $scope.currentPage,
                    pageSize: $scope.pageCount,
                    method: "collect"
                };
                if ($scope.totalItems) {
                    params.total = $scope.totalItems;
                }
                HmsHttp.post(url, params)
                    .success(function (data) {
                        $rootScope.hmsLoading.hide();
                        formatData(data);
                        $scope.rowsData = data.rows;
                        $scope.totalItems = parseInt(data.total);
                    })
                    .error(function (msg) {
                        $rootScope.hmsLoading.hide();
                        Tip.message("数据加载异常", "error");
                    });
            };
            //格式化数据
            function formatData(data) {
                var rows = data.rows;
                _.map(rows, function (row) {
                    var orderType = row.orderType;
                    var beginTime = row.beginTime;
                    var endTime = row.endTime;
                    if (orderType) {
                        row.orderType = orderType == 1 ? "预付" : "到付";
                    }
                    if (beginTime) {
                        row.beginTime = new Date(beginTime).format('M月d日 h时m分');
                    }
                    if (endTime) {
                        row.endTime = new Date(endTime).format('M月d日 h时m分');
                    }

                    if (row.payObject == "1") {
                        row.payObjectStr = "酒店业主";
                    }
                    if (row.payObject == "2") {
                        row.payObjectStr = "公司销售";
                    }
                    if (row.transferType == "1") {
                        row.transferTypeStr = "同城";
                    }
                    if (row.transferType == "2") {
                        row.transferTypeStr = "异地";
                    }
                });
            };
            //初始化调用（监听当前选择项目变化）
            $scope.$watch('active', function (newVal) {
                loadData();
            });
        }])
    .controller('BankInfoController', ['$rootScope', '$scope', 'HmsModalService', '$timeout', 'BankInfoService',
        function ($rootScope, $scope, HmsModalService, $timeout, BankInfoService) {
            var bankInfoCopy = {};
            $scope.defaultBanks = [{
                id: 'icbc',
                name: '中国工商银行'
            }, {
                    id: 'abc',
                    name: '中国农业银行'
                }, {
                    id: 'boc',
                    name: '中国银行'
                }, {
                    id: 'ccb',
                    name: '中国建设银行股份有限公司'
                }
                // ,{
                //     id:'other',
                //     name:'其它'
                // }
            ];
            $scope.selectBankChange = function () {
                var select = $scope.bankInfo.selectBank,
                    selectName = _.findWhere($scope.defaultBanks, { id: select }).name;
                switch (select) {
                    case 'other':
                        $scope.bankInfo.bank = '';
                        break;
                    default:
                        $scope.bankInfo.bank = selectName;
                        break;
                }
            };
            $scope.modalConfirm = function () {
                saveBankInfo();
            };
            $scope.modalConfirmClose = function () {
                HmsModalService.hide();
            };

            $scope.verifyCode = {
                time: 120,
                maxTime: 120,
                send: function () {
                    var _that = this;
                    _that.time -= 1;
                    BankInfoService.sendVerifyCode()
                        .success(function (result) {
                            if (!result.success) {
                                _that.time = _that.maxTime;
                                Tip.message(result.errorMsg, 'error');
                            } else {
                                Tip.message('验证码发送成功');
                                _that.timer.run();
                            }
                        })
                        .error(function (error) {
                            Tip.message('获取验证码异常', 'error');
                            _that.time = _that.maxTime;
                        });
                },
                timer: {
                    run: function () {
                        var _that = $scope.verifyCode;
                        _that.time -= 1;
                        if (_that.time <= 0) {
                            _that.time = _that.maxTime;
                        } else {
                            $timeout(_that.timer.run, 1000);
                        }
                    }
                }
            };

            function setCopyData() {
                if (!$scope.bankInfo) {
                    $scope.bankInfo = {};
                } else {
                    var selectBank = _.findWhere($scope.defaultBanks, { name: $scope.bankInfo.bank });
                    $scope.bankInfo.selectBank = selectBank ? selectBank.id : 'other';
                    bankInfoCopy = {
                        'name': $scope.bankInfo.name,
                        'account': $scope.bankInfo.account,
                        'bank': $scope.bankInfo.bank,
                        'bankBranch': $scope.bankInfo.bank,
                        'transferType': $scope.bankInfo.bank
                    };
                }
            };
            //显示酒店银行账号
            function saveBankInfo() {
                var name = $scope.bankInfo.name,
                    account = $scope.bankInfo.account,
                    selectBank = $scope.bankInfo.selectBank,
                    bank = $scope.bankInfo.bank,
                    bankBranch = $scope.bankInfo.bankBranch,
                    transferType = $scope.bankInfo.transferType,
                    verifyCode = $scope.bankInfo.verifyCode;
                if (!name || name.length <= 0) {
                    Tip.message('请输入户名', 'error');
                    return;
                }
                if (!account || account.length <= 0) {
                    Tip.message('请输入账号', 'error');
                    return;
                } else if (isNaN(Number(account)) || Number(account) < 0) {
                    Tip.message('请输入正确的账号', 'error');
                    return;
                }
                if (!bank || bank.length <= 0) {
                    Tip.message(selectBank == 'other' ? '请输入开户行' : '请选择开户行', 'error');
                    return;
                }
                if (!bankBranch || bankBranch.length <= 0) {
                    Tip.message('请输入支行', 'error');
                    return;
                }
                if (!transferType || transferType.length <= 0) {
                    Tip.message('请选择转帐类型', 'error');
                    return;
                }
                if (!verifyCode || verifyCode.length <= 0) {
                    Tip.message('请输入验证码', 'error');
                    return;
                }
                if (bankInfoCopy.name == name
                    && bankInfoCopy.account == account
                    && bankInfoCopy.bank == bank
                    && bankInfoCopy.bankBranch == bankBranch
                    && bankInfoCopy.transferType == transferType) {
                    Tip.message('数据没有变化不需要保存', 'error');
                    return;
                }
                $rootScope.hmsLoading.show();
                BankInfoService.save(name, account, bank, bankBranch, transferType, verifyCode)
                    .success(function (result) {
                        $rootScope.hmsLoading.hide();
                        if (result.success) {
                            setCopyData();
                            Tip.message('您的银行账户信息修改已经完成。<br>户　名：' + name + '<br>账　号：' + account + '<br>开户行：' + bank + '<br>支　行：' + bankBranch);
                            HmsModalService.hide();
                        } else {
                            Tip.message(result.errorMsg, 'error');
                        }
                    })
                    .error(function (result) {
                        $rootScope.hmsLoading.hide();
                        Tip.message('保存失败！');
                    });
            };

            setCopyData();
        }])
    .service('BankInfoService', ['HmsHttp', function (HmsHttp) {
        var url = contentPath + "/bank/";
        return {
            find: function () {
                var act = 'find';
                return HmsHttp.post(url + act);
            },
            save: function (name, account, bank, bankBranch, transferType, verifyCode) {
                var act = 'save',
                    params = {
                        'name': name,
                        'account': account,
                        'bank': bank,
                        'bankBranch': bankBranch,
                        'transferType': transferType,
                        'verifyCode': verifyCode
                    };
                return HmsHttp.post(url + act, params);
            },
            sendVerifyCode: function () {
                var act = 'verifyCode';
                return HmsHttp.post(url + act);
            }
        };
    }])
/**
 * [账单服务类]
 */
    .service('BillService', ['HmsHttp', function (HmsHttp) {
        var url = contentPath + "/bill/";
        return {
            /**
             * 账单金额大于等于酒店配置的结算金额
             * 下限时，页面“提前结算”功能按钮才可
             * 正常使用，否则为disabled不可使用状态
             * @return {Boolean} 
             */
            isFreeze: function () {
                var act = 'isFreeze';
                return HmsHttp.post(url + act);
            },
            /**
             * [提前结算按钮]
             * @return {object}
             */
            preCalAccount: function () {
                var threshold = $("#threshold");
                threshold.attr("disabled", "disabled");
                var act = 'preCalAccount';
                return HmsHttp.post(url + act);
            }
        };
    }])
;
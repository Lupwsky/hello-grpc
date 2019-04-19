$(function(){
    var flag = $("body").attr("flag");
    var token = ($.cookie('token'));
    $(".hidebtn").eq(0).show();
    $(".hidebtn a[flag='"+flag+"']").addClass("on");
    $(".navbtn").eq(0).addClass("on");
    Vue.config.silent = true;

    var vm = new Vue({
        el:'#screen',
        data:{
            company:"",
            options:[],
            pickerOptions1: {
                disabledDate(time) {
                    return time.getTime() > Date.now();
                },
                shortcuts: [{
                    text: '今天',
                    onClick(picker) {
                        picker.$emit('pick', new Date());
                    }
                }, {
                    text: '昨天',
                    onClick(picker) {
                        const date = new Date();
                        date.setTime(date.getTime() - 3600 * 1000 * 24);
                        picker.$emit('pick', date);
                    }
                }, {
                    text: '一周前',
                    onClick(picker) {
                        const date = new Date();
                        date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
                        picker.$emit('pick', date);
                    }
                }],
            },
            value1: '',
            value2: '',
            tableData:'',
            totalItems:1,
            currentPage:1,
            pagesize:10,
        },
        created: function () {
            $.post("/tdx/get/company/ship/list",{token:token,type:1},function(res){
                vm.options = res.data.dataList;
            });
        },
        methods:  {
            openDetails(row) {
                var id = row.accountId;
                var formDate =  Number(vm.value1) / 1000 ;
                $.post("/tdx/get/recv/count/and/balance",{token:token,id:id,formDate:formDate},function(res){
                    var money = "当日余额:"+Number(res.data.balance) / 100 +"元"
                    var num = "收件数:"+ res.data.count + "件"
                    alert(money+"        "+num)
                })
            },
            testtest:function(){
                var formDate =  Number(vm.value1) / 1000 ;
                var strArealist = vm.company[2];
                var keyValue = $(".usersrc input").val();
                var page = vm.currentPage;
                var size = vm.pagesize;
                $.post("/tdx/bill/get/recv/bill/list",{token:token,strAreaList:strArealist,formDate:formDate,keyValue:keyValue,page:page,size:size},function(res){
                    vm.tableData = res.data.dataList;
                    vm.totalItems = res.data.totalCount;
                    for (var i in res.data.dataList){
                        vm.tableData[i].toPayAmount = Number(res.data.dataList[i].toPayAmount) / 100;
                        vm.tableData[i].shipmentAmount = Number(res.data.dataList[i].shipmentAmount) / 100;
                        vm.tableData[i].fixedAmount = Number(res.data.dataList[i].fixedAmount) / 100;
                        vm.tableData[i].chargeOffsAmount = Number(res.data.dataList[i].chargeOffsAmount) / 100;
                        vm.tableData[i].fineAmount = Number(res.data.dataList[i].fineAmount) / 100;
                        vm.tableData[i].currCostFee = Number(res.data.dataList[i].currCostFee) / 100;
                        vm.tableData[i].claimsAmount = Number(res.data.dataList[i].claimsAmount) / 100;
                        vm.tableData[i].mutualBeltAmount = Number(res.data.dataList[i].mutualBeltAmount) / 100;
                        vm.tableData[i].rechargeAmount = Number(res.data.dataList[i].rechargeAmount) / 100;
                        vm.tableData[i].claimsAmount = Number(res.data.dataList[i].claimsAmount) / 100;
                    }

                });

            },

            handleSizeChange(val) {

                var formDate =  Number(vm.value1) / 1000 ;
                var strArealist =vm.company[2];
                var keyValue = $(".usersrc input").val();
                var num = vm.currentPage;
                vm.pagesize = val;
                var page = vm.pagesize
                $.post("/tdx/bill/get/recv/bill/list",{token:token,strAreaList:strArealist,formDate:formDate,keyValue:keyValue,page:num,size:page},function(res){
                    vm.tableData = res.data.dataList;
                    for (var i in res.data.dataList){
                        vm.tableData[i].toPayAmount = Number(res.data.dataList[i].toPayAmount) / 100;
                        vm.tableData[i].shipmentAmount = Number(res.data.dataList[i].shipmentAmount) / 100;
                        vm.tableData[i].fixedAmount = Number(res.data.dataList[i].fixedAmount) / 100;
                        vm.tableData[i].chargeOffsAmount = Number(res.data.dataList[i].chargeOffsAmount) / 100;
                        vm.tableData[i].fineAmount = Number(res.data.dataList[i].fineAmount) / 100;
                        vm.tableData[i].currCostFee = Number(res.data.dataList[i].currCostFee) / 100;
                        vm.tableData[i].claimsAmount = Number(res.data.dataList[i].claimsAmount) / 100;
                        vm.tableData[i].mutualBeltAmount = Number(res.data.dataList[i].mutualBeltAmount) / 100;
                        vm.tableData[i].rechargeAmount = Number(res.data.dataList[i].rechargeAmount) / 100;
                        vm.tableData[i].claimsAmount = Number(res.data.dataList[i].claimsAmount) / 100;
                    }

                });
            },
            handleCurrentChange(val) {
                vm.currentPage = val;
                var formDate =  Number(vm.value1) / 1000 ;
                var strArealist =vm.company[2];
                var keyValue = $(".usersrc input").val();
                var num = val;
                var page = vm.pagesize;
                $.post("/tdx/bill/get/recv/bill/list",{token:token,strAreaList:strArealist,formDate:formDate,keyValue:keyValue,page:num,size:page},function(res){
                    vm.tableData = res.data.dataList;
                    for (var i in res.data.dataList){
                        vm.tableData[i].toPayAmount = Number(res.data.dataList[i].toPayAmount) / 100;
                        vm.tableData[i].shipmentAmount = Number(res.data.dataList[i].shipmentAmount) / 100;
                        vm.tableData[i].fixedAmount = Number(res.data.dataList[i].fixedAmount) / 100;
                        vm.tableData[i].chargeOffsAmount = Number(res.data.dataList[i].chargeOffsAmount) / 100;
                        vm.tableData[i].fineAmount = Number(res.data.dataList[i].fineAmount) / 100;
                        vm.tableData[i].currCostFee = Number(res.data.dataList[i].currCostFee) / 100;
                        vm.tableData[i].claimsAmount = Number(res.data.dataList[i].claimsAmount) / 100;
                        vm.tableData[i].mutualBeltAmount = Number(res.data.dataList[i].mutualBeltAmount) / 100;
                        vm.tableData[i].rechargeAmount = Number(res.data.dataList[i].rechargeAmount) / 100;
                        vm.tableData[i].claimsAmount = Number(res.data.dataList[i].claimsAmount) / 100;
                    }

                });
            }

        },


    })
})
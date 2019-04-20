$(function(){
    var flag = $("body").attr("flag");
    var token = ($.cookie('token'));
    $(".hidebtn").eq(0).show();
    $(".navbtn").eq(0).addClass("on");
    $(".hidebtn a[flag='"+flag+"']").addClass("on");
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
                    console.log(res);
                })
            },

            testtest:function(){
                var formDate =  Number(vm.value1) / 1000 ;
                var strArealist = vm.company[2];
                var keyValue = $(".usersrc input").val();
                var page = vm.currentPage;
                var size = vm.pagesize;
                $.post("/tdx/bill/get/send/bill/list",{token:token,strAreaList:strArealist,formDate:formDate,keyValue:keyValue,page:page,size:size},function(res){
                    vm.tableData = res.data.dataList;
                    vm.totalItems = res.data.totalCount;
                    for (var i in res.data.dataList){
                        vm.tableData[i].am1359Rate = Number(res.data.dataList[i].am1359Rate) / 100 .toFixed(2) + "%";
                        vm.tableData[i].am2359Rate = Number(res.data.dataList[i].am2359Rate) / 100 .toFixed(2) + "%";
                        vm.tableData[i].pm2359Rate = Number(res.data.dataList[i].pm2359Rate) / 100 .toFixed(2) + "%";
                        vm.tableData[i].distributionFee = Number(res.data.dataList[i].distributionFee) / 100;
                        vm.tableData[i].amSignDeductionFee = Number(res.data.dataList[i].amSignDeductionFee) / 100;
                        vm.tableData[i].pmSignDeductionFee = Number(res.data.dataList[i].pmSignDeductionFee) / 100;
                        vm.tableData[i].totalFee = Number(res.data.dataList[i].totalFee) / 100;
                        if(res.data.dataList[i].complainCount <= 0){
                            vm.tableData[i].complainCount = "10%"
                        }
                        else{
                            vm.tableData[i].complainCount = "0%"
                        }

                    }

                });

            },
            handleSizeChange(val) {

                var formDate =  Number(vm.value1) / 1000 ;
                var strArealist =vm.company[2];
                var keyValue = $(".usersrc input").val();
                var num = vm.currentPage;;
                vm.pagesize = val;
                var page = vm.pagesize;
                $.post("/tdx/bill/get/send/bill/list",{token:token,strAreaList:strArealist,formDate:formDate,keyValue:keyValue,page:num,size:page},function(res){
                    vm.tableData = res.data.dataList;
                    vm.currentPage = res.data.currPage;
                    for (var i in res.data.dataList){
                        vm.tableData[i].am1359Rate = Number(res.data.dataList[i].am1359Rate) / 100 .toFixed(2) + "%";
                        vm.tableData[i].am2359Rate = Number(res.data.dataList[i].am2359Rate) / 100 .toFixed(2) + "%";
                        vm.tableData[i].pm2359Rate = Number(res.data.dataList[i].pm2359Rate) / 100 .toFixed(2) + "%";
                        if(res.data.dataList[i].complainCount <= 0){
                            vm.tableData[i].complainCount = "10%"
                        }
                        else{
                            vm.tableData[i].complainCount = "0%"
                        }
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
                $.post("/tdx/bill/get/send/bill/list",{token:token,strAreaList:strArealist,formDate:formDate,keyValue:keyValue,page:num,size:page},function(res){
                    vm.tableData = res.data.dataList;
                    vm.currentPage = res.data.currPage;
                    for (var i in res.data.dataList){
                        vm.tableData[i].am1359Rate = Number(res.data.dataList[i].am1359Rate) / 100 .toFixed(2) + "%";
                        vm.tableData[i].am2359Rate = Number(res.data.dataList[i].am2359Rate) / 100 .toFixed(2) + "%";
                        vm.tableData[i].pm2359Rate = Number(res.data.dataList[i].pm2359Rate) / 100 .toFixed(2) + "%";
                        if(res.data.dataList[i].complainCount <= 0){
                            vm.tableData[i].complainCount = "10%"
                        }
                        else{
                            vm.tableData[i].complainCount = "0%"
                        }
                    }
                });
            }

        },


    })
})





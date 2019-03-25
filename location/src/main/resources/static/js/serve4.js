$(function(){
    var flag = $("body").attr("flag");
    var token = ($.cookie('token'));
    $(".hidebtn").eq(1).show();
    $(".navbtn").eq(1).addClass("on");
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

        },
        created: function () {
            $.post("/tdx/get/company/ship/list",{token:token,type:1},function(res){
                vm.options = res.data.dataList;
            });
        },
        methods:  {
            testtest:function(){
                console.log(token);
                var formDate =  Number(vm.value1) / 1000 ;
                console.log(formDate);
                var strArealist = vm.company[2];
                console.log(strArealist);
                var keyValue = $(".usersrc input").val();
                console.log(keyValue);
                $.post("/tdx/bill/get/send/bill/list",{token:token,strAreaList:strArealist,formDate:formDate,keyValue:keyValue,page:1,size:5},function(res){
                    vm.tableData = res.data.dataList;
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
            handleSizeChange(val) {

            },
            handleCurrentChange(val) {

                var formDate =  Number(vm.value1) / 1000 ;
                var strArealist =vm.company[2];
                var keyValue = $(".usersrc input").val();
                var num = val;
                $.post("/tdx/bill/get/send/bill/list",{token:token,strAreaList:strArealist,formDate:formDate,keyValue:keyValue,page:num,size:5},function(res){
                    vm.tableData = res.data.dataList;
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
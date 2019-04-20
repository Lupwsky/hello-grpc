$(function(){
    var flag = $("body").attr("flag");
    var token = ($.cookie('token'));
    $(".hidebtn").eq(1).show();
    $(".navbtn").eq(1).addClass("on");
    $(".hidebtn a[flag='"+flag+"']").addClass("on");
    Vue.config.silent = true;
    function getLocalTime(nS) {
        return new Date(parseInt(nS) * 1000).toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ");
    }

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
            testtest:function(){
                var formDate =  Number(vm.value1) / 1000 ;
                var strArealist = vm.company[2];
                var keyValue = $(".usersrc input").val();
                var page = vm.currentPage;
                var size = vm.pagesize;
                $.post("/tdx/recv/note/web/get/note/list",{token:token,strAreaList:strArealist,formDate:formDate,keyValue:keyValue,page:page,size:size},function(res){
                    vm.tableData = res.data.dataList;
                    for (var i in res.data.dataList){
                        vm.tableData[i].createDate = getLocalTime(res.data.dataList[i].createDate);
                    }
                })

            },
            handleSizeChange(val) {
                vm.pagesize = val;
                var formDate =  Number(vm.value1) / 1000 ;
                var strArealist =vm.company[2];
                var keyValue = $(".usersrc input").val();
                var num = vm.currentPage;;
                var page = vm.pagesize;
                $.post("/tdx/recv/note/web/get/note/list",{token:token,strAreaList:strArealist,formDate:formDate,keyValue:keyValue,page:num,size:page},function(res){
                    vm.tableData = res.data.dataList;
                    for (var i in res.data.dataList){
                        vm.tableData[i].createDate = getLocalTime(res.data.dataList[i].createDate);
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
                $.post("/tdx/recv/note/web/get/note/list",{token:token,strAreaList:strArealist,formDate:formDate,keyValue:keyValue,page:num,size:page},function(res){
                    vm.tableData = res.data.dataList;
                    vm.currentPage = res.data.currPage;
                    for (var i in res.data.dataList){
                        vm.tableData[i].createDate = getLocalTime(res.data.dataList[i].createDate);
                    }
                });
            }

        },


    })
})
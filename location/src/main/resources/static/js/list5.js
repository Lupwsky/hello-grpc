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
                var strArealist = vm.company[2];
                var keyValue = $(".usersrc input").val();
                var page = vm.currentPage;
                var size = vm.pagesize;
                $.post("/tdx/pieces/manager/get/pieces/fee/list",{token:token,strAreaList:strArealist,keyValue:keyValue,page:page,size:size},function(res){
                    vm.tableData = res.data.dataList;
                    vm.totalItems = res.data.totalCount;

                });

            },
            handleSizeChange(val) {
                vm.pagesize = val;
                var strArealist =vm.company[2];
                var keyValue = $(".usersrc input").val();
                var num = vm.currentPage;
                var page = vm.pagesize;
                $.post("/tdx/pieces/manager/get/pieces/fee/list",{token:token,strAreaList:strArealist,keyValue:keyValue,page:num,size:page},function(res){
                    vm.tableData = res.data.dataList;

                });
            },
            handleCurrentChange(val) {
                vm.currentPage = val;
                var strArealist =vm.company[2];
                var keyValue = $(".usersrc input").val();
                var num = val;
                var page = vm.pagesize;
                $.post("/tdx/pieces/manager/get/pieces/fee/list",{token:token,strAreaList:strArealist,keyValue:keyValue,page:num,size:page},function(res){
                    vm.tableData = res.data.dataList;

                });
            }

        },


    })
})
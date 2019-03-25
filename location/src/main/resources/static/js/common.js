/**
 * 本项目所有的网络请求
 *
 */
// 添加TOKEN参数
function addTokenParam(jsonParams) {
    if (jsonParams === null) jsonParams = {};
    jsonParams["token"] = Cookies.get("mtoken");
    return jsonParams;
}


// 发情请求
function getData(url, jsonParams, success, error) {
    if (error === null) {
        error = function () {
            content.$alert("操作失败，请重试!", "提示");
        }
    }
    jsonParams = addTokenParam(jsonParams);
    CommonMethod.obtainDataFromServer(url, jsonParams, "json", function (data) {
        var code = data["code"];
        if (code === "10000" || code === 10000) {
            success(data["data"]);
        } else if (code === "10002") {
            content.$alert("登录过期，请重新登录!", "提示");
            location.href = "/";
        } else {
            content.$alert("操作失败，请重试!", "提示");
        }
    }, null, null, error);
}


// 正则
// 验证库存数填写是否正确
var stockRule = function(rule, value, callback) {
    var regExp = /^[0-9]*$/;
    if (regExp.test(value) === false) {
        callback(new Error('请填入大于或等于0的数字'));
    } else {
        if (parseInt(value) <= 0) {
            callback(new Error('请填入大于或等于0的数字'));
        }
        callback();
    }
};


// 所有金额验证(整数或者最多两位小数)
var moneyRule = function(rule, value, callback) {
    var regExp = /^[0-9]+(.[0-9]{0,2})?$/;
    if (regExp.test(value) === false) {
        callback(new Error('请填入大于或等于0的数字'));
    } else {
        if (parseFloat(value) < 0.0) {
            callback(new Error('请填入大于或等于0的数字'));
        }
        callback();
    }
};




























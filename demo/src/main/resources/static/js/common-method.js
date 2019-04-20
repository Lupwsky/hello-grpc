//noinspection JSDuplicatedDeclaration
/**
 * 全局的CommonMethod对象
 * @type {CommonMethod}
 */
var CommonMethod = new CommonMethod();

/**
 * 公共方法类CommonMethod, 需要Jquery支持(Lpw)
 * @constructor
 */
function CommonMethod() {
    // 私有的成员变量，公有就使用this.departnent_uuid
    this.obtainDataFromServer = obtainDataFromServer;

    /**
     * 从服务器获取数据, 封装Jquery的ajax方法
     * @param url       地址
     * @param data      传递的参数参数
     * @param dataType  返回的数据格式
     * @param success   请求成功的回调方法
     * @param before    请求之前的回调方法
     * @param complete  请求完成的回调方法
     * @param error     请求出错的回调方法
     */
    function obtainDataFromServer(url, data, dataType, success, before, complete, error) {
        var params = {url: url, type: "post"};
        if (data !== null) params["data"] = data;
        if (dataType !== null) params["dataType"] = dataType;
        if (success !== null) params["success"] = success;
        if (before !== null) params["beforeSend"] = before;
        if (complete !== null) params["complete"] = complete;
        if (error !== null) params["error"] = error;
        $.ajax(params);
    }
}


// StringBuffer类，每一个对象都有自己的一份内存
function StringBuffer() {
    var strings = [];
    this.append = append;
    this.appendFormat = appendFormat;
    this.toString = toString;
    this.clear = clear;

    // 拼接字符串
    function append(str){
        if(typeof str === 'string'){
            strings.push(str);
        }
    }

    // 按照指定的格式，拼接字符串
    function appendFormat(str) {
        var args = arguments;
        str = str.replace(/\{(\d+)\}/g, function(m, i){
            return args[++i];
        });
        strings.push(str);
    }

    // 转换为String
    function toString(){
        if(typeof arguments[0] === 'string'){
            return strings.join(arguments[0]);
        }
        return strings.join("");
    }

    // 清除StringBuffer
    function clear() {
        strings = [];
    }
}





/**
 * 初始化carModel详情对话框
 */
var AutoParaTypeInfoDlg = {
    autoParaTypeInfoData : {}
};

/**
 * 清除数据
 */
AutoParaTypeInfoDlg.clearData = function() {
    this.autoParaTypeInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AutoParaTypeInfoDlg.set = function(key, val) {
    this.autoParaTypeInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AutoParaTypeInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
AutoParaTypeInfoDlg.close = function() {
    parent.layer.close(window.parent.AutoParaType.layerIndex);
}

/**
 * 收集数据
 */
AutoParaTypeInfoDlg.collectData = function() {
    this
    .set('id')
    .set('paraTypeName')
    .set('status')
    .set('tips')
    .set('createTime');
}

/**
 * 提交添加
 */
AutoParaTypeInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/autoParaType/add", function(data){
        Feng.success("添加成功!");
        window.parent.AutoParaType.table.refresh();
        AutoParaTypeInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.autoParaTypeInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
AutoParaTypeInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/autoParaType/update", function(data){
        Feng.success("修改成功!");
        window.parent.AutoParaType.table.refresh();
        AutoParaTypeInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.autoParaTypeInfoData);
    ajax.start();
}

$(function() {

});

/**
 * 初始化carModel详情对话框
 */
var AutoModelParaInfoDlg = {
    autoModelParaInfoData : {}
};

/**
 * 清除数据
 */
AutoModelParaInfoDlg.clearData = function() {
    this.autoModelParaInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AutoModelParaInfoDlg.set = function(key, val) {
    this.autoModelParaInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AutoModelParaInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
AutoModelParaInfoDlg.close = function() {
    parent.layer.close(window.parent.AutoModelPara.layerIndex);
}

/**
 * 收集数据
 */
AutoModelParaInfoDlg.collectData = function() {
    this
    .set('id')
    .set('modelId')
    .set('paraId')
    .set('paraValue')
    .set('tips')
    .set('status')
    .set('createTime')
    .set('version');
}

/**
 * 提交添加
 */
AutoModelParaInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/autoModelPara/add", function(data){
        Feng.success("添加成功!");
        window.parent.AutoModelPara.table.refresh();
        AutoModelParaInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.autoModelParaInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
AutoModelParaInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/autoModelPara/update", function(data){
        Feng.success("修改成功!");
        window.parent.AutoModelPara.table.refresh();
        AutoModelParaInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.autoModelParaInfoData);
    ajax.start();
}

$(function() {

});

/**
 * 初始化carModel详情对话框
 */
var AutoParaInfoDlg = {
    autoParaInfoData : {}
};

/**
 * 清除数据
 */
AutoParaInfoDlg.clearData = function() {
    this.autoParaInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AutoParaInfoDlg.set = function(key, val) {
    this.autoParaInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AutoParaInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
AutoParaInfoDlg.close = function() {
    parent.layer.close(window.parent.AutoPara.layerIndex);
}

/**
 * 关闭此对话框
 */
AutoParaInfoDlg.refresh = function() {
    window.location.reload();
}

/**
 * 收集数据
 */
AutoParaInfoDlg.collectData = function() {
    this
    .set('id')
    .set('paraTypeId')
    .set('paraName')
    .set('paraCode')
    .set('carPartsId')
    .set('status')
    .set('tips')
    .set('createTime');
}

/**
 * 提交添加
 */
AutoParaInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/autoPara/add", function(data){
        Feng.success("添加成功!");
        window.parent.AutoPara.table.refresh();
        AutoParaInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.autoParaInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
AutoParaInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/autoPara/update", function(data){
        Feng.success("修改成功!");
        window.parent.AutoPara.table.refresh();
        AutoParaInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.autoParaInfoData);
    ajax.start();
}

$(function() {

});

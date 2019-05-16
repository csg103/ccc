/**
 * 初始化carModel详情对话框
 */
var AutoPartsInfoDlg = {
    autoPartsInfoData : {}
};

/**
 * 清除数据
 */
AutoPartsInfoDlg.clearData = function() {
    this.autoPartsInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AutoPartsInfoDlg.set = function(key, val) {
    this.autoPartsInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AutoPartsInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
AutoPartsInfoDlg.close = function() {
    parent.layer.close(window.parent.AutoParts.layerIndex);
}

/**
 * 收集数据
 */
AutoPartsInfoDlg.collectData = function() {
    this
    .set('id')
    .set('partsName')
    .set('partsCode')
    .set('carSystemId')
    .set('pid')
    .set('status')
    .set('tips')
    .set('createTime');
}

/**
 * 提交添加
 */
AutoPartsInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/autoParts/add", function(data){
        Feng.success("添加成功!");
        window.parent.AutoParts.table.refresh();
        AutoPartsInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.autoPartsInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
AutoPartsInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/autoParts/update", function(data){
        Feng.success("修改成功!");
        window.parent.AutoParts.table.refresh();
        AutoPartsInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.autoPartsInfoData);
    ajax.start();
}

$(function() {

});

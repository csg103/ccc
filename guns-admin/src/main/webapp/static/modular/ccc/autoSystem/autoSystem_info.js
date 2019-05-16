/**
 * 初始化carModel详情对话框
 */
var AutoSystemInfoDlg = {
    autoSystemInfoData : {}
};

/**
 * 清除数据
 */
AutoSystemInfoDlg.clearData = function() {
    this.autoSystemInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AutoSystemInfoDlg.set = function(key, val) {
    this.autoSystemInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AutoSystemInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
AutoSystemInfoDlg.close = function() {
    parent.layer.close(window.parent.AutoSystem.layerIndex);
}

/**
 * 收集数据
 */
AutoSystemInfoDlg.collectData = function() {
    this
    .set('id')
    .set('carSystemName')
    .set('carSystemCode')
    .set('status')
    .set('tips')
    .set('createTime');
}

/**
 * 提交添加
 */
AutoSystemInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    var ids = [];
    $("#bootstrap-duallistbox-selected-list_doublebox option").each(function(){
        //遍历所有option
        ids.push($(this).val());
    });
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/autoSystem/add?ids="+ids, function(data){
        Feng.success("添加成功!");
        window.parent.AutoSystem.table.refresh();
        AutoSystemInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.autoSystemInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
AutoSystemInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    var ids = [];
    $("#bootstrap-duallistbox-selected-list_doublebox option").each(function(){
        //遍历所有option
        ids.push($(this).val());
    });
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/autoSystem/update?ids="+ids, function(data){
        Feng.success("修改成功!");
        window.parent.AutoSystem.table.refresh();
        AutoSystemInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.autoSystemInfoData);
    ajax.start();
}

$(function() {

});

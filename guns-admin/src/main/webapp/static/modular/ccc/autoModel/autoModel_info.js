/**
 * 初始化carModel详情对话框
 */
var AutoModelInfoDlg = {
    partZtree : null,
    autoModelInfoData : {}
};

/**
 * 清除数据
 */
AutoModelInfoDlg.clearData = function() {
    this.autoModelInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AutoModelInfoDlg.set = function(key, val) {
    this.autoModelInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AutoModelInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
AutoModelInfoDlg.close = function() {
    parent.layer.close(window.parent.AutoModel.layerIndex);
}

/**
 * 收集数据
 */
AutoModelInfoDlg.collectData = function() {
    this
    .set('id')
    .set('modelName')
    .set('modelCode')
    .set('status')
    .set('tips')
    .set('createTime');
}

/**
 * 提交添加
 */
AutoModelInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    var nodes = AutoModelInfoDlg.partZtree.getSelectedValAll();
    var id;
    var nodestring="";
    for(var i=0;i<nodes.length;i++){
        id=nodes[i].id+":"+nodes[i].level;
        if(i<nodes.length-1) {
            nodestring += id + ',';
        }
        else{
            nodestring+=id;
        }
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/autoModel/add?nodes="+nodestring, function(data){
        Feng.success("添加成功!");
        window.parent.AutoModel.table.refresh();
        AutoModelInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.autoModelInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
AutoModelInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();
    var nodes = AutoModelInfoDlg.partZtree.getChangedValAll();
    var id;
    var nodestring="";
    for(var i=0;i<nodes.length;i++){
        id=nodes[i].id+":"+nodes[i].level+":"+nodes[i].checkedOld;
        if(i<nodes.length-1) {
            nodestring += id + ',';
        }
        else{
            nodestring+=id;
        }
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/autoModel/update?nodes="+nodestring, function(data){
        Feng.success("修改成功!");
        window.parent.AutoModel.table.refresh();
        AutoModelInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.autoModelInfoData);
    ajax.start();
}

/**
 * 生成编号
 */
AutoModelInfoDlg.toExport = function() {
    //提交信息
    window.open(Feng.ctxPath + "/autoModel/toExport?modelId="+$('#id').val()+"&modelCode="+$('#modelCode').val());
}


$(function() {

});

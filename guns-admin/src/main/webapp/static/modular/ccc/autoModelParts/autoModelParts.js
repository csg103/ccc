/**
 * carModel管理初始化
 */
var AutoModelParts = {
    id: "AutoModelPartsTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
AutoModelParts.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '序号', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '车型id', field: 'modelId', visible: false, align: 'center', valign: 'middle'},
            {title: '零部件', field: 'partsName', visible: true, align: 'center', valign: 'middle'},
            {title: '零件编号', field: 'partsCode', visible: true, align: 'center', valign: 'middle'},
            {title: '所属系统', field: 'carSystemName', visible: true, align: 'center', valign: 'middle'},
            {title: '车型', field: 'modelName', visible: true, align: 'center', valign: 'middle'},
            {title: '零部件id', field: 'partsId', visible: false, align: 'center', valign: 'middle'},
            {title: '状态', field: 'status', visible: false, align: 'center', valign: 'middle'},
            {title: '状态', field: 'statusName', visible: true, align: 'center', valign: 'middle'},
            {title: '版本', field: 'version', visible: true, align: 'center', valign: 'middle'},
            {title: '备注', field: 'tips', visible: true, align: 'center', valign: 'middle'},
            {title: '创建日期', field: 'createTime', visible: false, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
AutoModelParts.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        AutoModelParts.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加carModel
 */
AutoModelParts.openAddAutoModelParts = function () {
    var index = layer.open({
        type: 2,
        title: '添加&查看数据',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/autoModelParts/autoModelParts_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看carModel详情
 */
AutoModelParts.openAutoModelPartsDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '查看数据',
            area: ['1000px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/autoModelParts/autoModelParts_update/' + AutoModelParts.seItem.id
        });
        this.layerIndex = index;
    }
};


/**
 * 删除carModel
 */
AutoModelParts.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/autoModelParts/delete", function (data) {
            Feng.success("删除成功!");
            AutoModelParts.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("autoModelPartsId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 删除carModel
 */
AutoModelParts.agree = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/autoModelParts/agree", function (data) {
            Feng.success("审批成功!");
            AutoModelParts.table.refresh();
        }, function (data) {
            Feng.error("审批失败!" + data.responseJSON.message + "!");
        });
        ajax.set("autoModelPartsId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 申请修改
 */
AutoModelParts.askForModify = function () {
    if (this.check()) {
        if(AutoModelParts.seItem.status==0){
            Feng.error("还未填写数据不能申请修改!");
            return false;
        }
        if(AutoModelParts.seItem.status==2){
            Feng.error("已经提交申请，请勿重复提交!");
            return false;
        }
        if(AutoModelParts.seItem.status==3){
            Feng.error("申请已经审批通过，请勿重复提交!");
            return false;
        }
        var ajax = new $ax(Feng.ctxPath + "/autoModelParts/askForModify", function (data) {
            Feng.success("申请成功,等待审批!");
            AutoModelParts.table.refresh();
        }, function (data) {
            Feng.error("申请失败!" + data.responseJSON.message + "!");
        });
        ajax.set("autoModelPartsId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 申请修改
 */
AutoModelParts.agree = function () {
    if (this.check()) {
        if(AutoModelParts.seItem.status==0){
            Feng.error("未申请的不能审批!");
            return false;
        }
        if(AutoModelParts.seItem.status==1){
            Feng.error("未申请的不能审批!");
            return false;
        }
        if(AutoModelParts.seItem.status==3){
            Feng.error("申请已经审批通过，请勿重复审批!");
            return false;
        }
        var ajax = new $ax(Feng.ctxPath + "/autoModelParts/agree", function (data) {
            Feng.success("审批成功!");
            AutoModelParts.table.refresh();
        }, function (data) {
            Feng.error("审批失败!" + data.responseJSON.message + "!");
        });
        ajax.set("autoModelPartsId",this.seItem.id);
        ajax.start();
    }
};


/**
 * 修改
 */
AutoModelParts.reInput = function () {
    if (this.check()) {
        if(AutoModelParts.seItem.status==0){
            Feng.error("未填写的不能修改!");
            return false;
        }
        if(AutoModelParts.seItem.status==1){
            Feng.error("未申请的不能修改!");
            return false;
        }
        if(AutoModelParts.seItem.status==2){
            Feng.error("等待审批中，不能修改!");
            return false;
        }
        var index = layer.open({
            type: 2,
            title: 'carModel详情',
            area: ['1000px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/autoModelParts/autoModelPartsReinput/' + AutoModelParts.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 查询carModel列表
 */
AutoModelParts.search = function () {
    var queryData = {};
    queryData['modelName'] = $("#modelName").val();
    queryData['partsName'] = $("#partsName").val();
    AutoModelParts.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = AutoModelParts.initColumn();
    var table = new BSTable(AutoModelParts.id, "/autoModelParts/list", defaultColunms);
    table.setPaginationType("client");
    AutoModelParts.table = table.init();
});

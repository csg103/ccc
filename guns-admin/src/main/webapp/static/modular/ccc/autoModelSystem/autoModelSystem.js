/**
 * carModel管理初始化
 */
var AutoModelSystem = {
    id: "AutoModelSystemTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
AutoModelSystem.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '车型ID', field: 'modelId', visible: true, align: 'center', valign: 'middle'},
            {title: '系统ID', field: 'systemId', visible: true, align: 'center', valign: 'middle'},
            {title: '状态', field: 'status', visible: true, align: 'center', valign: 'middle'},
            {title: '备注', field: 'tips', visible: true, align: 'center', valign: 'middle'},
            {title: '创建日期', field: 'createTime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
AutoModelSystem.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        AutoModelSystem.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加carModel
 */
AutoModelSystem.openAddAutoModelSystem = function () {
    var index = layer.open({
        type: 2,
        title: '添加carModel',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/autoModelSystem/autoModelSystem_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看carModel详情
 */
AutoModelSystem.openAutoModelSystemDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: 'carModel详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/autoModelSystem/autoModelSystem_update/' + AutoModelSystem.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除carModel
 */
AutoModelSystem.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/autoModelSystem/delete", function (data) {
            Feng.success("删除成功!");
            AutoModelSystem.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("autoModelSystemId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询carModel列表
 */
AutoModelSystem.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    AutoModelSystem.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = AutoModelSystem.initColumn();
    var table = new BSTable(AutoModelSystem.id, "/autoModelSystem/list", defaultColunms);
    table.setPaginationType("client");
    AutoModelSystem.table = table.init();
});

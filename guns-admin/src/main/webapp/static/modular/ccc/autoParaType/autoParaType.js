/**
 * carModel管理初始化
 */
var AutoParaType = {
    id: "AutoParaTypeTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
AutoParaType.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '参数类型名称', field: 'paraTypeName', visible: true, align: 'center', valign: 'middle'},
            {title: '状态', field: 'status', visible: true, align: 'center', valign: 'middle'},
            {title: '备注', field: 'tips', visible: true, align: 'center', valign: 'middle'},
            {title: '创建日期', field: 'createTime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
AutoParaType.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        AutoParaType.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加carModel
 */
AutoParaType.openAddAutoParaType = function () {
    var index = layer.open({
        type: 2,
        title: '添加carModel',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/autoParaType/autoParaType_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看carModel详情
 */
AutoParaType.openAutoParaTypeDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: 'carModel详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/autoParaType/autoParaType_update/' + AutoParaType.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除carModel
 */
AutoParaType.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/autoParaType/delete", function (data) {
            Feng.success("删除成功!");
            AutoParaType.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("autoParaTypeId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询carModel列表
 */
AutoParaType.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    AutoParaType.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = AutoParaType.initColumn();
    var table = new BSTable(AutoParaType.id, "/autoParaType/list", defaultColunms);
    table.setPaginationType("client");
    AutoParaType.table = table.init();
});

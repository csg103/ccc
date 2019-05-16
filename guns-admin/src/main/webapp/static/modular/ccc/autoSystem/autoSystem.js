/**
 * carModel管理初始化
 */
var AutoSystem = {
    id: "AutoSystemTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
AutoSystem.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '车辆系统名称', field: 'carSystemName', visible: true, align: 'center', valign: 'middle'},
            {title: '车辆系统编码', field: 'carSystemCode', visible: true, align: 'center', valign: 'middle'},
            {title: '状态', field: 'status', visible: true, align: 'center', valign: 'middle'},
            {title: '备注', field: 'tips', visible: true, align: 'center', valign: 'middle'},
            {title: '创建日期', field: 'createTime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
AutoSystem.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        AutoSystem.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加carModel
 */
AutoSystem.openAddAutoSystem = function () {
    var index = layer.open({
        type: 2,
        title: '添加系统',
        area: ['1000px', '600px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/autoSystem/autoSystem_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看carModel详情
 */
AutoSystem.openAutoSystemDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '查看系统',
            area: ['1000px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/autoSystem/autoSystem_update/' + AutoSystem.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除carModel
 */
AutoSystem.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/autoSystem/delete", function (data) {
            Feng.success("删除成功!");
            AutoSystem.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("autoSystemId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询carModel列表
 */
AutoSystem.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    AutoSystem.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = AutoSystem.initColumn();
    var table = new BSTable(AutoSystem.id, "/autoSystem/list", defaultColunms);
    table.setPaginationType("client");
    AutoSystem.table = table.init();
});

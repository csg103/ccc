/**
 * carModel管理初始化
 */
var AutoModel = {
    id: "AutoModelTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
AutoModel.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '车型名称', field: 'modelName', visible: true, align: 'center', valign: 'middle'},
            {title: '车型编码', field: 'modelCode', visible: true, align: 'center', valign: 'middle'},
            {title: '车型状态', field: 'status', visible: true, align: 'center', valign: 'middle'},
            {title: '备注', field: 'tips', visible: true, align: 'center', valign: 'middle'},
            {title: '穿件日期', field: 'createTime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
AutoModel.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        AutoModel.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加carModel
 */
AutoModel.openAddAutoModel = function () {
    var index = layer.open({
        type: 2,
        title: '添加车型',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/autoModel/autoModel_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看carModel详情
 */
AutoModel.openAutoModelDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '查看车型详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/autoModel/autoModel_update/' + AutoModel.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除carModel
 */
AutoModel.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/autoModel/delete", function (data) {
            Feng.success("删除成功!");
            AutoModel.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("autoModelId",this.seItem.id);
        ajax.start();
    }
};


/**
 * 导出车型
 */
AutoModel.exportExcel = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '导出车型详情',
            area: ['1000px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/autoModel/exportExcel/' + AutoModel.seItem.id
        });
        this.layerIndex = index;
    }
};
/**
 * 查询carModel列表
 */
AutoModel.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    AutoModel.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = AutoModel.initColumn();
    var table = new BSTable(AutoModel.id, "/autoModel/list", defaultColunms);
    table.setPaginationType("client");
    AutoModel.table = table.init();
});

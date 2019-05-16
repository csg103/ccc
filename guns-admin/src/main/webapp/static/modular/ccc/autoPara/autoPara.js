/**
 * carModel管理初始化
 */
var AutoPara = {
    id: "AutoParaTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
AutoPara.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '参数类型id', field: 'paraTypeId', visible: true, align: 'center', valign: 'middle'},
            {title: '参数名称', field: 'paraName', visible: true, align: 'center', valign: 'middle'},
            {title: '参数编码', field: 'paraCode', visible: true, align: 'center', valign: 'middle'},
            {title: '零部件id', field: 'carPartsId', visible: true, align: 'center', valign: 'middle'},
            {title: '状态', field: 'status', visible: true, align: 'center', valign: 'middle'},
            {title: '备注', field: 'tips', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
AutoPara.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        AutoPara.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加carModel
 */
AutoPara.openAddAutoPara = function () {
    var index = layer.open({
        type: 2,
        title: '添加carModel',
        area: ['1000px', '600px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/autoPara/autoPara_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看carModel详情
 */
AutoPara.openAutoParaDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: 'carModel详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/autoPara/autoPara_update/' + AutoPara.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除carModel
 */
AutoPara.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/autoPara/delete", function (data) {
            Feng.success("删除成功!");
            AutoPara.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("autoParaId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询carModel列表
 */
AutoPara.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    AutoPara.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = AutoPara.initColumn();
    var table = new BSTable(AutoPara.id, "/autoPara/list", defaultColunms);
    table.setPaginationType("client");
    AutoPara.table = table.init();
});

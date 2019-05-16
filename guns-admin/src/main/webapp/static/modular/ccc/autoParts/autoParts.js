/**
 * carModel管理初始化
 */
var AutoParts = {
    id: "AutoPartsTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
AutoParts.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '序号', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '零部件名称', field: 'partsName', visible: true, align: 'center', valign: 'middle'},
            {title: '零部件编码', field: 'partsCode', visible: true, align: 'center', valign: 'middle'},
            {title: '所属系统ID', field: 'carSystemId', visible: false, align: 'center', valign: 'middle'},
            {title: '所属系统', field: 'carSystemName', visible: true, align: 'center', valign: 'middle'},
            {title: '父ID', field: 'pid', visible: false, align: 'center', valign: 'middle'},
            {title: '上级零部件', field: 'parentPartsName', visible: true, align: 'center', valign: 'middle'},
            {title: '状态', field: 'status', visible: true, align: 'center', valign: 'middle'},
            {title: '备注', field: 'tips', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'}
        // {title: '操作', field: 'operate',width: '80px',formatter: operateFormatter}
    ];
};

/**
 * 检查是否选中
 */
AutoParts.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        AutoParts.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加carModel
 */
AutoParts.openAddAutoParts = function () {
    var index = layer.open({
        type: 2,
        title: '添加零部件&参数',
        area: ['1000px', '600px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/autoParts/autoParts_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看carModel详情
 */
AutoParts.openAutoPartsDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '修改零部件&参数',
            area: ['1000px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/autoParts/autoParts_update/' + AutoParts.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除carModel
 */
AutoParts.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/autoParts/delete", function (data) {
            Feng.success("删除成功!");
            AutoParts.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("autoPartsId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询carModel列表
 */
AutoParts.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    AutoParts.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = AutoParts.initColumn();
    var table = new BSTable(AutoParts.id, "/autoParts/list", defaultColunms);
    table.setPaginationType("client");
    AutoParts.table = table.init();
});

/**
 * 打开查看零部件实例
 */
AutoParts.viewPartsExample = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '修改零部件&参数',
            area: ['1000px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/autoParts/autoParts_update/' + AutoParts.seItem.id
        });
        this.layerIndex = index;
    }
};


/**
 * 添加零部件实例
 */
AutoParts.addPartsExample = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '修改零部件&参数',
            area: ['1000px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/autoParts/autoParts_update/' + AutoParts.seItem.id
        });
        this.layerIndex = index;
    }
};

// function operateFormatter(value, row, index) {
//     return [
//         '<button type="button" class="btn btn-primary " onclick="AutoParts.openAddAutoParts()" id=""><i class="fa fa-plus"></i>&nbsp;查看实例</button>',
//     ].join('');
// }

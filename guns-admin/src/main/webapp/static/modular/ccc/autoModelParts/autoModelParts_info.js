/**
 * 初始化carModel详情对话框
 */
var AutoModelPartsInfoDlg = {
    autoModelPartsInfoData : {}
};

/**
 * 清除数据
 */
AutoModelPartsInfoDlg.clearData = function() {
    this.autoModelPartsInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AutoModelPartsInfoDlg.set = function(key, val) {
    this.autoModelPartsInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AutoModelPartsInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
AutoModelPartsInfoDlg.close = function() {
    parent.layer.close(window.parent.AutoModelParts.layerIndex);
}

/**
 * 收集数据
 */
AutoModelPartsInfoDlg.collectData = function() {
    this
    .set('id')
    .set('modelId')
    .set('partsId')
    .set('status')
    .set('tips')
    .set('createTime');
}

/**
 * 提交添加
 */
AutoModelPartsInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/autoModelParts/add", function(data){
        Feng.success("添加成功!");
        window.parent.AutoModelParts.table.refresh();
        AutoModelPartsInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.autoModelPartsInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
AutoModelPartsInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();
    var formData = new FormData();
    var paravalues = new Array();
    var ids = $('#ids').val();
    var x;
    ids = ids.split(',');
    for (x in ids){
        var paraId = ids[x];
        var paraValueId = 'paraValue'+paraId;
        var paraValue = $('#'+paraValueId+'').val();
        var paraValueFileId = 'paraValueFile'+paraId;
        if(typeof $('#' + paraValueFileId + '').val() != "undefined" && $('#' + paraValueFileId + '').val() !=null && $('#' + paraValueFileId + '').val()!='') {
            var paraValueFile = $('#' + paraValueFileId + '')[0].files[0];;
            formData.append('file', paraValueFile);
            paravalues.push(paraId+":"+paraValue+":"+paraValueFile.name);
        }else{
            paravalues.push(paraId+":"+paraValue+":"+"undefined");
        }
        if(""==paraValue || ""==paraValueFile){
            Feng.error("请填写全部参数值后提交！");
            return false;
        }

    }
    formData.append('paravalues', paravalues);
    // formData.set('id', $('id').val());
    $.ajax({
        contentType: false,
        cache: false,
        processData: false,
        contentType : false,
        url: "/autoModelParts/update?id="+$('#id').val()+"&modelId="+$('#modelId').val(),
        type: 'POST',
        data: formData,
        success: function(data) {

            window.parent.AutoModelParts.table.refresh();
            AutoModelPartsInfoDlg.close();
        },
        error: function() {
            alert("修改失败");
        }
    });

}

function getImgUrl(inputId){
        var file = $('#' + inputId + '')[0].files[0];
        return file;
}

/**
 * 提交修改
 */
AutoModelPartsInfoDlg.reIputSubmit = function() {

    this.clearData();
    this.collectData();
    var formData = new FormData();
    var paravalues = new Array();
    var ids = $('#ids').val();
    console.log("ids="+ids);
    var x;
    ids = ids.split(',');
    for (x in ids){
        var paraId = ids[x];
        var paraValueId = 'paraValue'+paraId;
        var paraValue = $('#'+paraValueId+'').val();
        var paraValueFileId = 'paraValueFile'+paraId;
        console.log($('#' + paraValueFileId + '').val());
        if(typeof $('#' + paraValueFileId + '').val() != "undefined" && $('#' + paraValueFileId + '').val() !=null && $('#' + paraValueFileId + '').val()!='') {
            var paraValueFile = $('#' + paraValueFileId + '')[0].files[0];;
            formData.append('file', paraValueFile);
            paravalues.push(paraId+":"+paraValue+":"+paraValueFile.name);
        }else{
            paravalues.push(paraId+":"+paraValue+":"+"undefined");
        }
        if(""==paraValue || ""==paraValueFile){
            Feng.error("请填写全部参数值后提交！");
            return false;
        }

    }
    formData.append('paravalues', paravalues);
    // formData.append('id', $('#id').val());

    $.ajax({
        contentType: false,
        cache: false,
        processData: false,
        contentType : false,
        url: "/autoModelParts/reIputSubmit?id="+$('#id').val()+"&modelId="+$('#modelId').val(),
        type: 'POST',
        data: formData,
        success: function(data) {
            console.log(data);
            window.parent.AutoModelParts.table.refresh();
            AutoModelPartsInfoDlg.close();
        },
        error: function() {
            console.log('Upload file failed!');
        }
    });

}

$(function() {

});

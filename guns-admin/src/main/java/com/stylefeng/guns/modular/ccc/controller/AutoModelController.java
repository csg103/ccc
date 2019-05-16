package com.stylefeng.guns.modular.ccc.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.node.ZTreeNode;
import com.stylefeng.guns.core.util.*;
import com.stylefeng.guns.modular.ccc.service.*;
import com.stylefeng.guns.modular.system.model.*;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * carModel控制器
 *
 * @author fengshuonan
 * @Date 2019-03-12 10:11:06
 */
@Controller
@RequestMapping("/autoModel")
public class AutoModelController extends BaseController {

    private String PREFIX = "/ccc/autoModel/";

    @Autowired
    private IAutoModelService autoModelService;

    @Autowired
    private IAutoSystemService iAutoSystemService;

    @Autowired
    private IAutoPartsService iAutoPartsService;

    @Autowired
    private IAutoModelSystemService iAutoModelSystemService;

    @Autowired
    private IAutoModelPartsService iAutoModelPartsService;

    @Autowired
    private IAutoParaService iAutoParaService;

    @Autowired
    private IAutoModelParaService iAutoModelParaService;

    /**
     * 跳转到carModel首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "autoModel.html";
    }

    /**
     * 跳转到添加carModel
     */
    @RequestMapping("/autoModel_add")
    public String autoModelAdd() {
        return PREFIX + "autoModel_add.html";
    }

    /**
     * 跳转到修改carModel
     */
    @RequestMapping("/autoModel_update/{autoModelId}")
    public String autoModelUpdate(@PathVariable Integer autoModelId, Model model) {
        AutoModel autoModel = autoModelService.selectById(autoModelId);
        model.addAttribute("item",autoModel);
        LogObjectHolder.me().set(autoModel);
        return PREFIX + "autoModel_edit.html";
    }

    /**
     * 获取carModel列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return autoModelService.selectList(null);
    }

    /**
     * 新增carModel
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(AutoModel autoModel, HttpServletRequest request) {
        autoModelService.insert(autoModel);
        Integer modelId = autoModel.getId();
        String nodes = request.getParameter("nodes");
        String nodeArray[] = {};
        if(null!=nodes && !"".equals(nodes)){
            if(nodes.indexOf(",")>0) {
                nodeArray = nodes.split(",");
            }else{
                nodeArray = new String[1];
                nodeArray[0] = nodes;
            }
            for(int i=0;i<nodeArray.length;i++){
                String idAndLevel[] = nodeArray[i].split(":");
                if(idAndLevel[1].equals("0")){
                    AutoModelSystem autoModelSystem = new AutoModelSystem();
                    autoModelSystem.setModelId(modelId);
                    autoModelSystem.setSystemId(Integer.valueOf(idAndLevel[0]));
                    iAutoModelSystemService.insert(autoModelSystem);
                }else{
                    AutoModelParts autoModelParts = new AutoModelParts();
                    autoModelParts.setModelId(modelId);
                    autoModelParts.setPartsId(Integer.valueOf(idAndLevel[0]));
                    autoModelParts.setStatus("0");//初始化为未填写
                    autoModelParts.setVersion("0");//初始化数据版本为0
                    iAutoModelPartsService.insert(autoModelParts);
                    EntityWrapper<AutoPara> ew = new EntityWrapper<>();
                    ew.where("CAR_PARTS_ID={0}",idAndLevel[0]);
                    List<AutoPara> paras = iAutoParaService.selectList(ew);
                    if(null!=paras && 0<paras.size()){
                        for (int j=0;j<paras.size();j++){
                            AutoPara autoPara = paras.get(j);
                            AutoModelPara autoModelPara = new AutoModelPara();
                            autoModelPara.setModelId(modelId);
                            autoModelPara.setStatus("0");//状态待填写
                            autoModelPara.setVersion("0");//未填写版本为0
                            autoModelPara.setParaId(autoPara.getId());
                            iAutoModelParaService.insert(autoModelPara);
                        }
                    }
                }
            }

        }

        return SUCCESS_TIP;
    }

    /**
     * 删除carModel
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer autoModelId) {
        autoModelService.deleteById(autoModelId);
        return SUCCESS_TIP;
    }

    /**
     * 修改carModel
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(AutoModel autoModel,HttpServletRequest request) {
        String nodes = request.getParameter("nodes");
        String nodeSplit[] = {};
        if(null!=nodes && !"".equals(nodes)){
            //节点拼接方式{1(节点id):2(树形结构中所处层级):true(checkOld属性),"节点2",...,...}
            if(nodes.indexOf(",")>0) {
                nodeSplit = nodes.split(",");
            }else{
                nodeSplit = new String[1];
                nodeSplit[0] = nodes;
            }
            for(int i=0;i<nodeSplit.length;i++){
                String nodei[] = nodeSplit[i].split(":");
                if("0".equals(nodei[1])){
                    EntityWrapper<AutoModelSystem> systemEw = new EntityWrapper<>();
                    //处理系统
                    if("true".equals(nodei[2])){
                        //节点checkOld属性为true，代表修改前状态为选中，修改后为未选中，需要删除
                        systemEw.where("MODEL_ID={0}",autoModel.getId()).and("SYSTEM_ID={0}",nodei[0]);
                        iAutoModelSystemService.delete(systemEw);
                    }else{
                        //节点checkOld属性为false，代表修改前状态为未选中，修改后为选中，需要新增
                        AutoModelSystem autoModelSystem = new AutoModelSystem();
                        autoModelSystem.setModelId(autoModel.getId());
                        autoModelSystem.setSystemId(Integer.valueOf(nodei[0]));
                        autoModelSystem.setCreateTime(new Date());
                        iAutoModelSystemService.insert(autoModelSystem);
                    }
                }else{
                    //处理零部件
                    EntityWrapper<AutoModelParts> partsEw = new EntityWrapper<>();
                    if("true".equals(nodei[2])){
                        //节点checkOld属性为true，代表修改前状态为选中，修改后为未选中，需要删除
                        partsEw.where("MODEL_ID={0}",autoModel.getId()).and("PARTS_ID={0}",nodei[0]);
                        iAutoModelPartsService.delete(partsEw);
                        EntityWrapper<AutoPara> paraEw = new EntityWrapper<>();
                        paraEw.where("CAR_PARTS_ID={0}",nodei[0]);
                        List<AutoPara> paras = iAutoParaService.selectList(paraEw);
                        if(null!=paras && 0<paras.size()){
                            for(int j=0;j<paras.size();j++){
                                AutoPara autoPara = paras.get(j);
                                iAutoParaService.deleteById(autoPara);
                            }
                        }
                    }else{
                        //节点checkOld属性为false，代表修改前状态为未选中，修改后为选中，需要新增
                        AutoModelParts autoModelParts = new AutoModelParts();
                        autoModelParts.setModelId(autoModel.getId());
                        autoModelParts.setPartsId(Integer.valueOf(nodei[0]));
                        autoModelParts.setStatus("0");//初始化为未填写
                        autoModelParts.setVersion("0");//初始化数据版本为0
                        iAutoModelPartsService.insert(autoModelParts);
                        EntityWrapper<AutoPara> ew = new EntityWrapper<>();
                        ew.where("CAR_PARTS_ID={0}",nodei[0]);
                        List<AutoPara> paras = iAutoParaService.selectList(ew);
                        if(null!=paras && 0<paras.size()){
                            for (int j=0;j<paras.size();j++){
                                AutoPara autoPara = paras.get(j);
                                AutoModelPara autoModelPara = new AutoModelPara();
                                autoModelPara.setModelId(autoModel.getId());
                                autoModelPara.setParaId(autoPara.getId());
                                autoModelPara.setStatus("0");
                                autoModelPara.setVersion("0");
                                iAutoModelParaService.insert(autoModelPara);
                            }
                        }
                    }
                }
            }
        }
        autoModelService.updateById(autoModel);
        return SUCCESS_TIP;
    }

    /**
     * carModel详情
     */
    @RequestMapping(value = "/detail/{autoModelId}")
    @ResponseBody
    public Object detail(@PathVariable("autoModelId") Integer autoModelId) {
        return autoModelService.selectById(autoModelId);
    }


    /**
     * 获得系统零部件修改树
     */
    @RequestMapping(value = "/getEditModelTree/{modelId}")
    @ResponseBody
    public List<ZTreeNode> getEditModelTree(@PathVariable Integer modelId) {
        List<AutoSystem> systemList = iAutoSystemService.selectList(null);
        List<AutoParts> autoPartsList = iAutoPartsService.selectList(null);
        EntityWrapper<AutoModelSystem> systemew = new EntityWrapper<>();
        systemew.where("MODEL_ID={0}",modelId);
        List<AutoModelSystem> myAutoModelSystems = iAutoModelSystemService.selectList(systemew);
        EntityWrapper<AutoModelParts> partew = new EntityWrapper<>();
        partew.where("MODEL_ID={0}",modelId);
        List<AutoModelParts> myAutoModelParts = iAutoModelPartsService.selectList(partew);
        List<ZTreeNode> zTreeNodeList = new ArrayList<>();
        ZTreeNode zTreeNode;
        for(int i=0;i<systemList.size();i++) {
            boolean isSystemCheck = false;
            AutoSystem autoSystem = systemList.get(i);
            zTreeNode = new ZTreeNode();
            zTreeNode.setId(Long.valueOf(autoSystem.getId()));
            zTreeNode.setIsOpen(true);
            zTreeNode.setName(autoSystem.getCarSystemName());
            if(null!=myAutoModelSystems && 0<myAutoModelSystems.size()) {
                for (int j = 0; j < myAutoModelSystems.size(); j++) {
                    AutoModelSystem autoModelSystem = myAutoModelSystems.get(j);
                    if(autoModelSystem.getSystemId()==autoSystem.getId()) {
                        isSystemCheck = true;
                        break;
                    }
                }
                zTreeNode.setChecked(isSystemCheck);
            }else{
                zTreeNode.setChecked(false);
            }
            zTreeNode.setpId(Long.valueOf(0));
            zTreeNodeList.add(zTreeNode);
        }
        for(int i=0;i<autoPartsList.size();i++){
            boolean isPartsChecked = false;
            AutoParts autoParts = autoPartsList.get(i);
            zTreeNode = new ZTreeNode();
            zTreeNode.setId(Long.valueOf(autoParts.getId()));
            zTreeNode.setIsOpen(true);
            zTreeNode.setName(autoParts.getPartsName());
            if(null!=myAutoModelParts && 0<myAutoModelParts.size()) {
                for (int j=0;j<myAutoModelParts.size();j++) {
                    AutoModelParts autoModelParts = myAutoModelParts.get(j);
                    if(autoModelParts.getPartsId()==autoParts.getId()){
                        isPartsChecked = true;
                        break;
                    }
                }
                zTreeNode.setChecked(isPartsChecked);
            }else{
                zTreeNode.setChecked(false);
            }
            if(autoParts.getPid()!=null) {
                zTreeNode.setpId(Long.valueOf(autoParts.getPid()));
            }else if(null!=autoParts.getCarSystemId()){
                zTreeNode.setpId(Long.valueOf(autoParts.getCarSystemId()));
            }else{
                continue;
            }
            zTreeNodeList.add(zTreeNode);
        }
        return zTreeNodeList;
    }


    /**
     * 导出车型详情页面
     */
    @RequestMapping(value = "/exportExcel/{autoModelId}")
    public String exportExcel(@PathVariable("autoModelId") Integer autoModelId,Model model) {
        AutoModel autoModel = autoModelService.selectById(autoModelId);
        model.addAttribute("item",autoModel);
        return PREFIX + "autoModel_export.html";
    }

    /**
     * 获得系统零部件树
     */
    @RequestMapping(value = "/getModelExportTree/{modelId}")
    @ResponseBody
    public List<ZTreeNode> getModelExportTree(@PathVariable Integer modelId) {
        EntityWrapper<AutoModelSystem> systemew = new EntityWrapper<>();
        systemew.where("MODEL_ID={0}",modelId);
        List<AutoModelSystem> myAutoModelSystems = iAutoModelSystemService.selectList(systemew);
        EntityWrapper<AutoModelParts> partew = new EntityWrapper<>();
        partew.where("MODEL_ID={0}",modelId);
        List<AutoModelParts> myAutoModelParts = iAutoModelPartsService.selectList(partew);
        List<ZTreeNode> zTreeNodeList = new ArrayList<>();
        ZTreeNode zTreeNode;
        if(null!=myAutoModelSystems && 0<myAutoModelSystems.size()){
            for(int i=0;i<myAutoModelSystems.size();i++){
                AutoModelSystem autoModelSystem = myAutoModelSystems.get(i);
                AutoSystem autoSystem = new AutoSystem();
                autoSystem.setId(autoModelSystem.getSystemId());
                autoSystem = iAutoSystemService.selectById(autoSystem);
                zTreeNode = new ZTreeNode();
                zTreeNode.setpId(Long.valueOf(0));
                zTreeNode.setName(autoSystem.getCarSystemName());
                zTreeNode.setIsOpen(true);
                zTreeNode.setId(Long.valueOf(autoSystem.getId()));
                zTreeNodeList.add(zTreeNode);
            }
            if(null!=myAutoModelParts && 0<myAutoModelParts.size()){
                for(int i=0;i<myAutoModelParts.size();i++){
                    zTreeNode = new ZTreeNode();
                    AutoModelParts autoModelParts = myAutoModelParts.get(i);
                    AutoParts autoparts = new AutoParts();
                    autoparts.setId(autoModelParts.getPartsId());
                    autoparts = iAutoPartsService.selectById(autoparts);
                    zTreeNode.setpId(Long.valueOf(autoparts.getCarSystemId()));
                    zTreeNode.setId(Long.valueOf(autoparts.getId()));
                    zTreeNode.setIsOpen(true);
                    zTreeNode.setName(autoparts.getPartsName());
                    zTreeNodeList.add(zTreeNode);
                }
                //获得零部件对应的参数列表
                EntityWrapper<AutoModelPara> paraEw = new EntityWrapper<>();
                paraEw.where("MODEL_ID={0}",modelId);
                List<AutoModelPara> autoModelParas = iAutoModelParaService.selectList(paraEw);
                if(null!=autoModelParas && 0<autoModelParas.size()){
                    for(int j=0;j<autoModelParas.size();j++){
                        AutoModelPara autoModelPara = autoModelParas.get(j);
                        AutoPara autoPara = new AutoPara();
                        autoPara.setId(autoModelPara.getParaId());
                        autoPara = iAutoParaService.selectById(autoPara);
                        zTreeNode = new ZTreeNode();
                        zTreeNode.setId(Long.valueOf(autoPara.getId()));
                        zTreeNode.setpId(Long.valueOf(autoPara.getCarPartsId()));
                        zTreeNode.setIsOpen(true);
                        if(autoPara.getParaTypeId()==1){
                            zTreeNode.setName(autoPara.getParaName()+":"+autoModelPara.getParaValue());
                        }else if(autoPara.getParaTypeId()==2){
                            zTreeNode.setName(autoPara.getParaName()+":查看附件"+autoModelPara.getParaValue());
                            zTreeNode.setUrl(autoModelPara.getParaUrl());
                            zTreeNode.setTarget("_blank");
                        }else if(autoPara.getParaTypeId()==3){
                            zTreeNode.setName(autoPara.getParaName()+":"+autoModelPara.getParaValue()+":查看附件"+autoModelPara.getParaValue());
                            zTreeNode.setUrl(autoModelPara.getParaUrl());
                            zTreeNode.setTarget("_blank");
                        }
                        zTreeNodeList.add(zTreeNode);
                    }
                }
            }
        }
        return zTreeNodeList;
    }

    /**
     * 获得系统零部件树
     */
    @RequestMapping(value = "/getModelTree")
    @ResponseBody
    public List<ZTreeNode> getModelTree() {
        List<AutoSystem> systemList = iAutoSystemService.selectList(null);
        List<AutoParts> autoPartsList = iAutoPartsService.selectList(null);
        List<ZTreeNode> zTreeNodeList = new ArrayList<>();
        ZTreeNode zTreeNode;
        for(int i=0;i<systemList.size();i++) {
            AutoSystem autoSystem = systemList.get(i);
            zTreeNode = new ZTreeNode();
            zTreeNode.setId(Long.valueOf(autoSystem.getId()));
            zTreeNode.setIsOpen(true);
            zTreeNode.setName(autoSystem.getCarSystemName());
            zTreeNode.setChecked(false);
            zTreeNode.setpId(Long.valueOf(0));
            zTreeNodeList.add(zTreeNode);
        }
        for(int i=0;i<autoPartsList.size();i++){
            AutoParts autoParts = autoPartsList.get(i);
            zTreeNode = new ZTreeNode();
            zTreeNode.setId(Long.valueOf(autoParts.getId()));
            zTreeNode.setIsOpen(true);
            zTreeNode.setName(autoParts.getPartsName());
            zTreeNode.setChecked(false);
            if(autoParts.getPid()!=null) {
                zTreeNode.setpId(Long.valueOf(autoParts.getPid()));
            }else if(null!=autoParts.getCarSystemId()){
                zTreeNode.setpId(Long.valueOf(autoParts.getCarSystemId()));
            }else{
                continue;
            }
            zTreeNodeList.add(zTreeNode);
        }
        return zTreeNodeList;
    }

    @Value("${web.upload-path}")
    public String saveUrl;

    @ResponseBody
    @RequestMapping(value = "/toExport")
    public ResponseEntity<byte[]> getFileExport(@RequestParam(required = false) String modelId,@RequestParam(required = false) String modelCode) throws Exception {
        EntityWrapper<AutoModelParts> partEW = new EntityWrapper<>();
        partEW.where("MODEL_ID={0}",modelId);
        List<AutoModelParts> autoModelPartsList = iAutoModelPartsService.selectList(partEW);
        boolean canBeExport = true;
        if(null!=autoModelPartsList && 0<autoModelPartsList.size()){
            for(int i=0;i<autoModelPartsList.size();i++){
                AutoModelParts autoModelParts = autoModelPartsList.get(i);
                if(!autoModelParts.getStatus().equals(Constant.allStatus_inputEd)){
                    canBeExport = false;
                    break;
                }
            }
        }
        if(!canBeExport){
            throw new Exception("未完成所有零部件参数填写，不能导出！");
        }else {
            List<AutoModelPara> finalList = new ArrayList<>();
            for(int i=0;i<autoModelPartsList.size();i++){
                AutoModelParts autoModelParts = autoModelPartsList.get(i);
                AutoParts autoParts = iAutoPartsService.selectById(autoModelParts.getPartsId());
                AutoSystem autoSystem = iAutoSystemService.selectById(autoParts.getCarSystemId());
                EntityWrapper<AutoPara> paraEW = new EntityWrapper<>();
                paraEW.where("CAR_PARTS_ID={0}",autoModelParts.getPartsId());
                List<AutoPara> autoModelParas = iAutoParaService.selectList(paraEW);
                for(int j=0;j<autoModelParas.size();j++){
                    AutoModelPara finalPara = new AutoModelPara();
                    finalPara.setSystemName(autoSystem.getCarSystemName());
                    AutoPara autoPara = autoModelParas.get(j);
                    EntityWrapper<AutoModelPara> valueEW = new EntityWrapper<>();
                    valueEW.where("MODEL_ID={0}",modelId).and("PARA_ID={0}",autoPara.getId());
                    AutoModelPara autoModelPara = iAutoModelParaService.selectOne(valueEW);
                    finalPara.setParaName(autoPara.getParaName());
                    finalPara.setParaCode(autoPara.getParaCode());
//                    finalPara.setCreator(autoModelPara.getCreator());
                    if(autoPara.getParaTypeId()==1 || autoPara.getParaTypeId()==3) {
                        finalPara.setParaValue(autoModelPara.getParaValue());
                    }else if(autoPara.getParaTypeId()==2 || autoPara.getParaTypeId()==3){
                        //拷贝文件到车型文件夹下
                        finalPara.setParaUrl("HYPERLINK(\""+autoModelPara.getParaUrl().replace("/static/"+modelCode+"/","")+"\",\"查看附件\")");
                    }
                    finalList.add(finalPara);
                }
            }
            String excelPath = saveUrl+modelCode;
            String excelFileName = modelCode+".xls";
            LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
            fieldMap.put("paraCode", "参数编码");
            fieldMap.put("systemName", "所属系统");
            fieldMap.put("paraName", "参数名称");
            fieldMap.put("paraValue", "参数值");
            fieldMap.put("creator", "填报人");
            fieldMap.put("paraUrl","附件");
            File excelFile = new File(excelPath +"/"+ excelFileName);
            FileOutputStream fos2 = new FileOutputStream(excelFile);
            ExcelUtil.listToExcel(finalList, fieldMap, modelCode, fos2);
            String outputZipName = modelCode+".zip";
            ZipCompress zipCompress = new ZipCompress(saveUrl + outputZipName,saveUrl+modelCode);
            zipCompress.zip();
            File zipfile = new File(saveUrl+ outputZipName);
            HttpHeaders headers = new HttpHeaders();//设置响应头
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            //设置响应文件
            headers.setContentDispositionFormData("attachment", outputZipName);
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(zipfile), headers, HttpStatus.CREATED);
        }
    }


}

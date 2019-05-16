package com.stylefeng.guns.modular.ccc.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.shiro.ShiroUser;
import com.stylefeng.guns.core.util.Constant;
import com.stylefeng.guns.modular.ccc.service.*;
import com.stylefeng.guns.modular.system.model.*;
import com.stylefeng.guns.modular.system.warpper.AutoModelParaForInputWarpper;
import com.stylefeng.guns.modular.system.warpper.AutoModelParaWarpper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * carModel控制器
 *
 * @author fengshuonan
 * @Date 2019-03-12 10:11:33
 */
@Controller
@RequestMapping("/autoModelParts")
public class AutoModelPartsController extends BaseController {

    private String PREFIX = "/ccc/autoModelParts/";

    @Autowired
    private IAutoModelPartsService autoModelPartsService;

    @Autowired
    private IAutoModelService iAutoModelService;

    @Autowired
    private IAutoPartsService iAutoPartsService;

    @Autowired
    private IAutoModelParaService iAutoModelParaService;

    @Autowired
    private IAutoParaService iAutoParaService;


    /**
     * 跳转到carModel首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "autoModelParts.html";
    }

    /**
     * 跳转到添加carModel
     */
    @RequestMapping("/autoModelParts_add")
    public String autoModelPartsAdd() {
        return PREFIX + "autoModelParts_add.html";
    }

    /**
     * 跳转到修改carModel
     */
    @RequestMapping("/autoModelParts_update/{autoModelPartsId}")
    public String autoModelPartsUpdate(@PathVariable Integer autoModelPartsId, Model model) {
        AutoModelParts autoModelParts = autoModelPartsService.selectById(autoModelPartsId);
        AutoModel autoModel = new AutoModel();
        autoModel.setId(autoModelParts.getModelId());
        autoModel = iAutoModelService.selectById(autoModel);
        AutoParts autoParts = new AutoParts();
        autoParts.setId(autoModelParts.getPartsId());
        autoParts = iAutoPartsService.selectById(autoParts);
        autoModelParts.setModelName(autoModel.getModelName());
        autoModelParts.setPartsName(autoParts.getPartsName());
        autoModelParts.setPartsCode(autoParts.getPartsCode());
        EntityWrapper<AutoPara> paraEw = new EntityWrapper<>();
        paraEw.where("CAR_PARTS_ID={0}",autoModelParts.getPartsId());
        List<AutoPara> paraList = iAutoParaService.selectList(paraEw);
        EntityWrapper<AutoModelPara> paraEntityWrapper = new EntityWrapper<>();
        paraEntityWrapper.where("MODEL_ID={0}",autoModelParts.getModelId());
        Object ids[] = {};
        StringBuffer sb = new StringBuffer();
        if(null!=paraList && 0<paraList.size()){
            ids = new  Object[paraList.size()];
            for(int i=0;i<paraList.size();i++){
                ids[i] = paraList.get(i).getId();
            }
        }
        paraEntityWrapper.in("PARA_ID",ids);
        List<Map<String, Object>> paras = iAutoModelParaService.selectMaps(paraEntityWrapper);
        if(null!=paras && 0<paras.size()){
            for (int i=0;i<paras.size();i++){
                sb.append(paras.get(i).get("id"));
                if(i<paras.size()-1){
                    sb.append(",");
                }
            }
        }
        model.addAttribute("ids",sb.toString());
        model.addAttribute("paras", new AutoModelParaForInputWarpper(paras).warp());
        model.addAttribute("item",autoModelParts);
        LogObjectHolder.me().set(autoModelParts);
        return PREFIX + "autoModelParts_edit.html";
    }

    /**
     * 获取carModel列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String modelName,String partsName) {
        AutoModelParts autoModelParts = new AutoModelParts();
        autoModelParts.setModelName(modelName);
        autoModelParts.setPartsName(partsName);
        List<Map<String, Object>> list = autoModelPartsService.getListByNames(autoModelParts);
        return new AutoModelParaWarpper(list).warp();
    }

    /**
     * 新增carModel
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(AutoModelParts autoModelParts) {
        autoModelPartsService.insert(autoModelParts);
        return SUCCESS_TIP;
    }

    /**
     * 删除carModel
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer autoModelPartsId) {
        autoModelPartsService.deleteById(autoModelPartsId);
        return SUCCESS_TIP;
    }


    @Value("${web.upload-path}")
    public String saveUrl;
    /**
     * 修改carModel
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(AutoModelParts autoModelParts, HttpServletRequest request) throws IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
        String paravalues = request.getParameter("paravalues");
        String modelId = request.getParameter("modelId");
        AutoModel autoModel = iAutoModelService.selectById(modelId);
        List<MultipartFile> files = multipartRequest.getFiles("file");
        String subParas[] = {};
        if(paravalues.indexOf(",")>0){
            subParas=paravalues.split(",");
        }else{
            subParas=new String[1];
            subParas[0] = paravalues;
        }
        String autoParaVlue[] = {};
        Map<String,MultipartFile> map = new HashMap<>();
        AutoModelPara autoModelPara = new AutoModelPara();
        if(null!=files && 0<files.size()){
            for(int j=0;j<files.size();j++) {
                MultipartFile uploadFile = files.get(j);
                String fileName = uploadFile.getOriginalFilename();
                map.put(fileName,uploadFile);
            }
        }
        for(int i=0;i<subParas.length;i++){
            autoParaVlue = subParas[i].split(":");
            autoModelPara.setId(Integer.valueOf(autoParaVlue[0]));
            autoModelPara = iAutoModelParaService.selectById(autoModelPara);
            if(!"undefined".equals(autoParaVlue[1]) && !"".equals(autoParaVlue[1])){
                autoModelPara.setParaValue(autoParaVlue[1]);
            }
            if(!"undefined".equals(autoParaVlue[2]) && !"".equals(autoParaVlue[2])){
                String modelDir = saveUrl+autoModel.getModelCode()+"/";
                File targetFile = new File(modelDir);
                if (!targetFile.exists()) {
                    targetFile.mkdirs();
                }
                MultipartFile uploadFile = map.get(autoParaVlue[2]);
                String fileName = uploadFile.getOriginalFilename();
                String suffix = fileName.substring(fileName.lastIndexOf("."));
                fileName = UUID.randomUUID() + suffix;
                uploadFile.transferTo(new File(targetFile, fileName));
                autoModelPara.setParaUrl("/static/"+autoModel.getModelCode()+"/"+fileName);
            }
            ShiroUser shiroUser = ShiroKit.getUser();
            autoModelPara.setCreator(shiroUser.getAccount());
            autoModelPara.setStatus(Constant.allStatus_inputEd);//填写完成
            autoModelPara.setVersion("1");//初次填写版本为1
            iAutoModelParaService.updateAllColumnById(autoModelPara);
            autoModelParts = autoModelPartsService.selectById(autoModelParts);
            autoModelParts.setStatus(Constant.allStatus_inputEd);//已全部填写
            autoModelParts.setVersion("1");//初次填写版本为1
            autoModelPartsService.updateAllColumnById(autoModelParts);
        }
        return SUCCESS_TIP;
    }

    /**
     * carModel详情
     */
    @RequestMapping(value = "/detail/{autoModelPartsId}")
    @ResponseBody
    public Object detail(@PathVariable("autoModelPartsId") Integer autoModelPartsId) {
        return autoModelPartsService.selectById(autoModelPartsId);
    }

    /**
     * 申请修改
     */
    @RequestMapping(value = "/askForModify")
    @ResponseBody
    public Object askForModify(@RequestParam Integer autoModelPartsId) {
        AutoModelParts autoModelParts = autoModelPartsService.selectById(autoModelPartsId);
        EntityWrapper<AutoPara> autoParaEW = new EntityWrapper<>();
        autoParaEW.where("CAR_PARTS_ID={0}",autoModelParts.getPartsId());
        List<AutoPara> autoParas = iAutoParaService.selectList(autoParaEW);
        Object ids[] = new Object[autoParas.size()>0?autoParas.size():0];
        for(int i=0;i<autoParas.size();i++){
                ids[i] = autoParas.get(i).getId();
        }
        EntityWrapper<AutoModelPara> paraEntityWrapper = new EntityWrapper<>();
        paraEntityWrapper.where("MODEL_ID={0}",autoModelParts.getModelId());
        paraEntityWrapper.in("PARA_ID",ids);
        iAutoModelParaService.updateForSet("status="+ Constant.allStatus_askForModify,paraEntityWrapper);//批量更改参数状态为待审批
        autoModelParts.setStatus(Constant.allStatus_askForModify);
        autoModelPartsService.updateById(autoModelParts);//更新零部件状态为待审批
        return SUCCESS_TIP;
    }


    /**
     * 审批通过
     */
    @RequestMapping(value = "/agree")
    @ResponseBody
    public Object agree(@RequestParam Integer autoModelPartsId) {
        AutoModelParts autoModelParts = autoModelPartsService.selectById(autoModelPartsId);
        EntityWrapper<AutoPara> autoParaEW = new EntityWrapper<>();
        autoParaEW.where("CAR_PARTS_ID={0}",autoModelParts.getPartsId());
        List<AutoPara> autoParas = iAutoParaService.selectList(autoParaEW);
        Object ids[] = new Object[autoParas.size()>0?autoParas.size():0];
        for(int i=0;i<autoParas.size();i++){
            ids[i] = autoParas.get(i).getId();
        }
        EntityWrapper<AutoModelPara> paraEntityWrapper = new EntityWrapper<>();
        paraEntityWrapper.where("MODEL_ID={0}",autoModelParts.getModelId());
        paraEntityWrapper.in("PARA_ID",ids);
        iAutoModelParaService.updateForSet("status="+ Constant.allStatus_agree,paraEntityWrapper);//批量更改参数状态为修改
        autoModelParts.setStatus(Constant.allStatus_agree);
        autoModelPartsService.updateById(autoModelParts);//更新零部件状态为待修改
        return SUCCESS_TIP;
    }


    /**
     * 跳转到重新填写
     */
    @RequestMapping("/autoModelPartsReinput/{autoModelPartsId}")
    public String autoModelPartsReinput(@PathVariable Integer autoModelPartsId, Model model) {
        AutoModelParts autoModelParts = autoModelPartsService.selectById(autoModelPartsId);
        AutoModel autoModel = new AutoModel();
        autoModel.setId(autoModelParts.getModelId());
        autoModel = iAutoModelService.selectById(autoModel);
        AutoParts autoParts = new AutoParts();
        autoParts.setId(autoModelParts.getPartsId());
        autoParts = iAutoPartsService.selectById(autoParts);
        autoModelParts.setModelName(autoModel.getModelName());
        autoModelParts.setPartsName(autoParts.getPartsName());
        autoModelParts.setPartsCode(autoParts.getPartsCode());
        EntityWrapper<AutoPara> paraEw = new EntityWrapper<>();
        paraEw.where("CAR_PARTS_ID={0}",autoModelParts.getPartsId());
        List<AutoPara> paraList = iAutoParaService.selectList(paraEw);
        EntityWrapper<AutoModelPara> paraEntityWrapper = new EntityWrapper<>();
        paraEntityWrapper.where("MODEL_ID={0}",autoModelParts.getModelId());
        Object ids[] = {};
        StringBuffer sb = new StringBuffer();
        if(null!=paraList && 0<paraList.size()){
            ids = new  Object[paraList.size()];
            for(int i=0;i<paraList.size();i++){
                ids[i] = paraList.get(i).getId();
            }
        }
        paraEntityWrapper.in("PARA_ID",ids);
        List<Map<String, Object>> paras = iAutoModelParaService.selectMaps(paraEntityWrapper);
        if(null!=paras && 0<paras.size()){
            for (int i=0;i<paras.size();i++){
                sb.append(paras.get(i).get("id"));
                if(i<paras.size()-1){
                    sb.append(",");
                }
            }
        }
        model.addAttribute("ids",sb.toString());
        model.addAttribute("paras", new AutoModelParaForInputWarpper(paras).warp());
        model.addAttribute("item",autoModelParts);
        LogObjectHolder.me().set(autoModelParts);
        return PREFIX + "autoModelParts_reinput.html";
    }


    /**
     * 修改carModel
     */
    @RequestMapping(value = "/reIputSubmit")
    @ResponseBody
    public Object reIputSubmit(AutoModelParts autoModelParts, HttpServletRequest request) throws IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
        String paravalues = request.getParameter("paravalues");
        List<MultipartFile> files = multipartRequest.getFiles("file");
        String subParas[] = {};
        if(paravalues.indexOf(",")>0){
            subParas=paravalues.split(",");
        }else{
            subParas=new String[1];
            subParas[0] = paravalues;
        }
        String autoParaVlue[] = {};
        Map<String,MultipartFile> map = new HashMap<>();
        AutoModelPara autoModelPara = new AutoModelPara();
        String modelId = request.getParameter("modelId");
        AutoModel autoModel = iAutoModelService.selectById(modelId);
        if(null!=files && 0<files.size()){
            for(int j=0;j<files.size();j++) {
                MultipartFile uploadFile = files.get(j);
                String fileName = uploadFile.getOriginalFilename();
                map.put(fileName,uploadFile);
            }
        }
        for(int i=0;i<subParas.length;i++){
            autoParaVlue = subParas[i].split(":");
            autoModelPara.setId(Integer.valueOf(autoParaVlue[0]));
            autoModelPara = iAutoModelParaService.selectById(autoModelPara);
            if(!"undefined".equals(autoParaVlue[1]) && !"".equals(autoParaVlue[1])){
                autoModelPara.setParaValue(autoParaVlue[1]);
            }
            if(!"undefined".equals(autoParaVlue[2]) && !"".equals(autoParaVlue[2])){
                String modelDir = saveUrl+autoModel.getModelCode()+"/";
                File targetFile = new File(modelDir);
                if (!targetFile.exists()) {
                    targetFile.mkdirs();
                }
                MultipartFile uploadFile = map.get(autoParaVlue[2]);
                String fileName = uploadFile.getOriginalFilename();
                String suffix = fileName.substring(fileName.lastIndexOf("."));
                fileName = UUID.randomUUID() + suffix;
                uploadFile.transferTo(new File(targetFile, fileName));
                autoModelPara.setParaUrl("/static/"+autoModel.getModelCode()+"/"+fileName);
            }
            ShiroUser shiroUser = ShiroKit.getUser();
            autoModelPara.setCreator(shiroUser.getAccount());
            autoModelPara.setStatus(Constant.allStatus_inputEd);//填写完成
            autoModelPara.setVersion(String.valueOf(Integer.valueOf(autoModelPara.getVersion())+1));//版本号+1
            iAutoModelParaService.updateAllColumnById(autoModelPara);
            autoModelParts = autoModelPartsService.selectById(autoModelParts);
            autoModelParts.setStatus(Constant.allStatus_inputEd);//已全部填写
            autoModelParts.setVersion(String.valueOf(Integer.valueOf(autoModelParts.getVersion())+1));//版本号+1
            autoModelPartsService.updateAllColumnById(autoModelParts);
        }
        return SUCCESS_TIP;
    }
}

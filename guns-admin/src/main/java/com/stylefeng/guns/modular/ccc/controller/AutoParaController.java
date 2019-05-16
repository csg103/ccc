package com.stylefeng.guns.modular.ccc.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.ErrorTip;
import com.stylefeng.guns.core.base.tips.SuccessTip;
import com.stylefeng.guns.modular.ccc.service.*;
import com.stylefeng.guns.modular.system.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * carModel控制器
 *
 * @author fengshuonan
 * @Date 2019-03-12 10:11:51
 */
@Controller
@RequestMapping("/autoPara")
public class AutoParaController extends BaseController {

    private String PREFIX = "/ccc/autoPara/";

    @Autowired
    private IAutoPartsService autoPartsService;

    @Autowired
    private IAutoParaService autoParaService;

    @Autowired
    private IAutoSystemService autoSystemService;

    @Autowired
    private IAutoParaTypeService autoParaTypeService;

    @Autowired
    private IAutoModelPartsService iAutoModelPartsService;

    /**
     * 跳转到carModel首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "autoPara.html";
    }

    /**
     * 跳转到添加carModel
     */
    @RequestMapping("/autoPara_add")
    public String autoParaAdd(Model model) {
        List<AutoSystem>  systems = autoSystemService.selectList(null);
        List<AutoParaType> autoParaTypes = autoParaTypeService.selectList(null);
        List<AutoParts> autoParts = autoPartsService.selectList(null);
        model.addAttribute("systems",systems);
        model.addAttribute("autoParaTypes",autoParaTypes);
        model.addAttribute("autoParts",autoParts);
        return PREFIX + "autoPara_add.html";
    }

    /**
     * 跳转到修改carModel
     */
    @RequestMapping("/autoPara_update/{autoParaId}")
    public String autoParaUpdate(@PathVariable Integer autoParaId, Model model) {
        AutoPara autoPara = autoParaService.selectById(autoParaId);
        model.addAttribute("item",autoPara);
        LogObjectHolder.me().set(autoPara);
        return PREFIX + "autoPara_edit.html";
    }

    /**
     * 获取carModel列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return autoParaService.selectList(null);
    }

    /**
     * 新增carModel
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(AutoPara autoPara) {
        //验证是否已经被添加到车型当中
        EntityWrapper<AutoModelParts> autoPartsEw = new EntityWrapper<>();
        autoPartsEw.where("PARTS_ID={0}",autoPara.getCarPartsId());
        List<AutoModelParts> autoModelParts = iAutoModelPartsService.selectList(autoPartsEw);
        if(null!=autoModelParts && 0<autoModelParts.size()){
            return new ErrorTip(201,"零部件已经添加到车型当中，不能再添加新的参数！");
        }
        //验证重名
        EntityWrapper<AutoPara> ew = new EntityWrapper<>();
        ew.where("PARA_NAME={0}",autoPara.getParaName()).and("CAR_PARTS_ID={0}",autoPara.getCarPartsId());
        List<AutoPara> autoParas = autoParaService.selectList(ew);
        if(null!=autoParas && 0<autoParas.size()){
            return new ErrorTip(201,"参数名重复！");
        }else {
            autoParaService.insert(autoPara);
            return new SuccessTip(autoPara.getId());
        }
    }

    /**
     * 删除carModel
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer autoParaId) {
        autoParaService.deleteById(autoParaId);
        return SUCCESS_TIP;
    }

    /**
     * 修改carModel
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(AutoPara autoPara) {
        autoParaService.updateById(autoPara);
        return SUCCESS_TIP;
    }

    /**
     * carModel详情
     */
    @RequestMapping(value = "/detail/{autoParaId}")
    @ResponseBody
    public Object detail(@PathVariable("autoParaId") Integer autoParaId) {
        return autoParaService.selectById(autoParaId);
    }



}

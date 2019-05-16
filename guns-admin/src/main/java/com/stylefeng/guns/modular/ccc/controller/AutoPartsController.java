package com.stylefeng.guns.modular.ccc.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.ErrorTip;
import com.stylefeng.guns.core.base.tips.SuccessTip;
import com.stylefeng.guns.modular.ccc.service.IAutoParaService;
import com.stylefeng.guns.modular.ccc.service.IAutoParaTypeService;
import com.stylefeng.guns.modular.ccc.service.IAutoSystemService;
import com.stylefeng.guns.modular.system.model.AutoPara;
import com.stylefeng.guns.modular.system.model.AutoParaType;
import com.stylefeng.guns.modular.system.model.AutoSystem;
import com.stylefeng.guns.modular.system.warpper.AutoModelPartsWarpper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.guns.modular.system.model.AutoParts;
import com.stylefeng.guns.modular.ccc.service.IAutoPartsService;

import java.util.List;
import java.util.Map;

/**
 * carModel控制器
 *
 * @author fengshuonan
 * @Date 2019-03-12 10:12:08
 */
@Controller
@RequestMapping("/autoParts")
public class AutoPartsController extends BaseController {

    private String PREFIX = "/ccc/autoParts/";

    @Autowired
    private IAutoPartsService autoPartsService;

    @Autowired
    private IAutoSystemService autoSystemService;

    @Autowired
    private IAutoParaTypeService autoParaTypeService;

    @Autowired
    private IAutoParaService autoParaService;

    /**
     * 跳转到carModel首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "autoParts.html";
    }

    /**
     * 跳转到添加carModel
     */
    @RequestMapping("/autoParts_add")
    public String autoPartsAdd(Model model) {
        List<AutoSystem>  systems = autoSystemService.selectList(null);
        List<AutoParaType> autoParaTypes = autoParaTypeService.selectList(null);
        EntityWrapper<AutoParts> ewAutoParts = new EntityWrapper<>();
        ewAutoParts.where("PID=0");
        List<AutoParts> autoParts = autoPartsService.selectList(ewAutoParts);
        model.addAttribute("systems",systems);
        model.addAttribute("autoParaTypes",autoParaTypes);
        model.addAttribute("autoParts",autoParts);
        return PREFIX + "autoParts_add.html";
    }

    /**
     * 跳转到修改carModel
     */
    @RequestMapping("/autoParts_update/{autoPartsId}")
    public String autoPartsUpdate(@PathVariable Integer autoPartsId, Model model) {
        AutoParts autoParts = autoPartsService.selectById(autoPartsId);
        model.addAttribute("item",autoParts);
        EntityWrapper<AutoPara> ew = new EntityWrapper<>();
        ew.where("CAR_PARTS_ID={0}",autoPartsId);
        List<AutoPara> autoParas = autoParaService.selectList(ew);
        model.addAttribute("autoParas",autoParas);

        List<AutoSystem>  systems = autoSystemService.selectList(null);
        List<AutoParaType> autoParaTypes = autoParaTypeService.selectList(null);
        List<AutoParts> autoPartsList = autoPartsService.selectList(null);
        model.addAttribute("systems",systems);
        model.addAttribute("autoParaTypes",autoParaTypes);
        model.addAttribute("autoPartsList",autoPartsList);

        LogObjectHolder.me().set(autoParts);
        return PREFIX + "autoParts_edit.html";
    }

    /**
     * 获取carModel列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        EntityWrapper<AutoParts> ewAutoParts = new EntityWrapper<>();
        ewAutoParts.where("PID=0");
        List<Map<String, Object>> list = autoPartsService.selectMaps(ewAutoParts);
        return new AutoModelPartsWarpper(list).warp();
    }

    /**
     * 新增carModel
     */

    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(AutoParts autoParts) {
        EntityWrapper<AutoParts> ew = new EntityWrapper<>();
        ew.where("PARTS_NAME={0}",autoParts.getPartsName());
        List<AutoParts> list = autoPartsService.selectList(ew);
        if(null!=list && 0<list.size()){
            ErrorTip errorTip = new ErrorTip(404,"零部件名称重复");
            return errorTip;
        }else {
            autoPartsService.insert(autoParts);
            SuccessTip successTip = new SuccessTip(autoParts.getId());
            return successTip;
        }
    }

    /**
     * 删除carModel
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer autoPartsId) {
        autoPartsService.deleteById(autoPartsId);
        return SUCCESS_TIP;
    }

    /**
     * 修改carModel
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(AutoParts autoParts) {
        autoPartsService.updateById(autoParts);
        return new SuccessTip(autoParts.getId());
    }

    /**
     * carModel详情
     */
    @RequestMapping(value = "/detail/{autoPartsId}")
    @ResponseBody
    public Object detail(@PathVariable("autoPartsId") Integer autoPartsId) {
        return autoPartsService.selectById(autoPartsId);
    }

    /**
     * 根据系统id获取零部件列表
     */
    @RequestMapping(value = "/getpartslist/{carSystemId}")
    @ResponseBody
    public Object getparalist(@PathVariable("carSystemId") Integer carSystemId) {
        EntityWrapper<AutoParts> ew = new EntityWrapper<>();
        ew.where("CAR_SYSTEM_ID={0}",carSystemId);
        List<AutoParts> autoParts = autoPartsService.selectList(ew);
        return autoParts;
    }

    /**
     * 获取全部零部件列表
     */
    @RequestMapping(value = "/getparalistAll")
    @ResponseBody
    public Object getparalistAll() {
        EntityWrapper<AutoParts> ew = new EntityWrapper<>();
        List<AutoParts> autoParts = autoPartsService.selectList(ew);
        return autoParts;
    }
}

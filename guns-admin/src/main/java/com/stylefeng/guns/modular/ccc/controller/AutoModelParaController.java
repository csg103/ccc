package com.stylefeng.guns.modular.ccc.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.guns.modular.system.model.AutoModelPara;
import com.stylefeng.guns.modular.ccc.service.IAutoModelParaService;

/**
 * carModel控制器
 *
 * @author fengshuonan
 * @Date 2019-03-12 10:11:25
 */
@Controller
@RequestMapping("/autoModelPara")
public class AutoModelParaController extends BaseController {

    private String PREFIX = "/ccc/autoModelPara/";

    @Autowired
    private IAutoModelParaService autoModelParaService;

    /**
     * 跳转到carModel首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "autoModelPara.html";
    }

    /**
     * 跳转到添加carModel
     */
    @RequestMapping("/autoModelPara_add")
    public String autoModelParaAdd() {
        return PREFIX + "autoModelPara_add.html";
    }

    /**
     * 跳转到修改carModel
     */
    @RequestMapping("/autoModelPara_update/{autoModelParaId}")
    public String autoModelParaUpdate(@PathVariable Integer autoModelParaId, Model model) {
        AutoModelPara autoModelPara = autoModelParaService.selectById(autoModelParaId);
        model.addAttribute("item",autoModelPara);
        LogObjectHolder.me().set(autoModelPara);
        return PREFIX + "autoModelPara_edit.html";
    }

    /**
     * 获取carModel列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {

        return autoModelParaService.selectList(null);
    }

    /**
     * 新增carModel
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(AutoModelPara autoModelPara) {
        autoModelParaService.insert(autoModelPara);
        return SUCCESS_TIP;
    }

    /**
     * 删除carModel
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer autoModelParaId) {
        autoModelParaService.deleteById(autoModelParaId);
        return SUCCESS_TIP;
    }

    /**
     * 修改carModel
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(AutoModelPara autoModelPara) {
        autoModelParaService.updateById(autoModelPara);
        return SUCCESS_TIP;
    }

    /**
     * carModel详情
     */
    @RequestMapping(value = "/detail/{autoModelParaId}")
    @ResponseBody
    public Object detail(@PathVariable("autoModelParaId") Integer autoModelParaId) {
        return autoModelParaService.selectById(autoModelParaId);
    }
}

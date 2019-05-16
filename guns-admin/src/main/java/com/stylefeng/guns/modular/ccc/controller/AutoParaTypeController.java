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
import com.stylefeng.guns.modular.system.model.AutoParaType;
import com.stylefeng.guns.modular.ccc.service.IAutoParaTypeService;

/**
 * carModel控制器
 *
 * @author fengshuonan
 * @Date 2019-03-12 10:12:00
 */
@Controller
@RequestMapping("/autoParaType")
public class AutoParaTypeController extends BaseController {

    private String PREFIX = "/ccc/autoParaType/";

    @Autowired
    private IAutoParaTypeService autoParaTypeService;

    /**
     * 跳转到carModel首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "autoParaType.html";
    }

    /**
     * 跳转到添加carModel
     */
    @RequestMapping("/autoParaType_add")
    public String autoParaTypeAdd() {
        return PREFIX + "autoParaType_add.html";
    }

    /**
     * 跳转到修改carModel
     */
    @RequestMapping("/autoParaType_update/{autoParaTypeId}")
    public String autoParaTypeUpdate(@PathVariable Integer autoParaTypeId, Model model) {
        AutoParaType autoParaType = autoParaTypeService.selectById(autoParaTypeId);
        model.addAttribute("item",autoParaType);
        LogObjectHolder.me().set(autoParaType);
        return PREFIX + "autoParaType_edit.html";
    }

    /**
     * 获取carModel列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return autoParaTypeService.selectList(null);
    }

    /**
     * 新增carModel
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(AutoParaType autoParaType) {
        autoParaTypeService.insert(autoParaType);
        return SUCCESS_TIP;
    }

    /**
     * 删除carModel
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer autoParaTypeId) {
        autoParaTypeService.deleteById(autoParaTypeId);
        return SUCCESS_TIP;
    }

    /**
     * 修改carModel
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(AutoParaType autoParaType) {
        autoParaTypeService.updateById(autoParaType);
        return SUCCESS_TIP;
    }

    /**
     * carModel详情
     */
    @RequestMapping(value = "/detail/{autoParaTypeId}")
    @ResponseBody
    public Object detail(@PathVariable("autoParaTypeId") Integer autoParaTypeId) {
        return autoParaTypeService.selectById(autoParaTypeId);
    }
}

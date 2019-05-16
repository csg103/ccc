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
import com.stylefeng.guns.modular.system.model.AutoModelSystem;
import com.stylefeng.guns.modular.ccc.service.IAutoModelSystemService;

/**
 * carModel控制器
 *
 * @author fengshuonan
 * @Date 2019-03-12 10:11:43
 */
@Controller
@RequestMapping("/autoModelSystem")
public class AutoModelSystemController extends BaseController {

    private String PREFIX = "/ccc/autoModelSystem/";

    @Autowired
    private IAutoModelSystemService autoModelSystemService;

    /**
     * 跳转到carModel首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "autoModelSystem.html";
    }

    /**
     * 跳转到添加carModel
     */
    @RequestMapping("/autoModelSystem_add")
    public String autoModelSystemAdd() {
        return PREFIX + "autoModelSystem_add.html";
    }

    /**
     * 跳转到修改carModel
     */
    @RequestMapping("/autoModelSystem_update/{autoModelSystemId}")
    public String autoModelSystemUpdate(@PathVariable Integer autoModelSystemId, Model model) {
        AutoModelSystem autoModelSystem = autoModelSystemService.selectById(autoModelSystemId);
        model.addAttribute("item",autoModelSystem);
        LogObjectHolder.me().set(autoModelSystem);
        return PREFIX + "autoModelSystem_edit.html";
    }

    /**
     * 获取carModel列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return autoModelSystemService.selectList(null);
    }

    /**
     * 新增carModel
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(AutoModelSystem autoModelSystem) {
        autoModelSystemService.insert(autoModelSystem);
        return SUCCESS_TIP;
    }

    /**
     * 删除carModel
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer autoModelSystemId) {
        autoModelSystemService.deleteById(autoModelSystemId);
        return SUCCESS_TIP;
    }

    /**
     * 修改carModel
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(AutoModelSystem autoModelSystem) {
        autoModelSystemService.updateById(autoModelSystem);
        return SUCCESS_TIP;
    }

    /**
     * carModel详情
     */
    @RequestMapping(value = "/detail/{autoModelSystemId}")
    @ResponseBody
    public Object detail(@PathVariable("autoModelSystemId") Integer autoModelSystemId) {
        return autoModelSystemService.selectById(autoModelSystemId);
    }
}

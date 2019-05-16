package com.stylefeng.guns.modular.ccc.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.ErrorTip;
import com.stylefeng.guns.modular.ccc.service.IAutoPartsService;
import com.stylefeng.guns.modular.system.model.AutoParts;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.guns.modular.system.model.AutoSystem;
import com.stylefeng.guns.modular.ccc.service.IAutoSystemService;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.parser.Entity;
import java.util.List;

/**
 * carModel控制器
 *
 * @author fengshuonan
 * @Date 2019-03-12 10:12:16
 */
@Controller
@RequestMapping("/autoSystem")
public class AutoSystemController extends BaseController {

    private String PREFIX = "/ccc/autoSystem/";

    @Autowired
    private IAutoSystemService autoSystemService;

    @Autowired
    private IAutoPartsService iAutoPartsService;

    /**
     * 跳转到carModel首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "autoSystem.html";
    }

    /**
     * 跳转到添加carModel
     */
    @RequestMapping("/autoSystem_add")
    public String autoSystemAdd() {
        return PREFIX + "autoSystem_add.html";
    }

    /**
     * 跳转到修改carModel
     */
    @RequestMapping("/autoSystem_update/{autoSystemId}")
    public String autoSystemUpdate(@PathVariable Integer autoSystemId, Model model) {
        AutoSystem autoSystem = autoSystemService.selectById(autoSystemId);
        model.addAttribute("item",autoSystem);
        LogObjectHolder.me().set(autoSystem);
        return PREFIX + "autoSystem_edit.html";
    }

    /**
     * 获取carModel列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return autoSystemService.selectList(null);
    }

    /**
     * 新增carModel
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(AutoSystem autoSystem, HttpServletRequest request) throws Exception {
        AutoSystem autoSystemForSearch = new AutoSystem();
        EntityWrapper<AutoSystem> ew = new EntityWrapper<>();
        ew.where("CAR_SYSTEM_NAME={0}",autoSystem.getCarSystemName());
        autoSystemForSearch = autoSystemService.selectOne(ew);
        if(null!=autoSystemForSearch || "".equals(autoSystem.getCarSystemName())) {
            throw new Exception("车辆系统名称重复或为空！");
        }else{
            autoSystemService.insert(autoSystem);
        }
        int carSystemId = autoSystem.getId();
        String ids = request.getParameter("ids");
        String idsString[] = {};
        if(null!=ids && ids.indexOf(",")>0){
            idsString  = ids.split(",");
            AutoParts autoParts ;
            for(int i=0;i<idsString.length;i++){
                autoParts = new AutoParts();
                autoParts.setId(Integer.valueOf(idsString[i]));
                autoParts = iAutoPartsService.selectById(autoParts);
                if(null!=autoParts){
                    autoParts.setCarSystemId(carSystemId);
                    iAutoPartsService.updateAllColumnById(autoParts);
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
    public Object delete(@RequestParam Integer autoSystemId) {
        autoSystemService.deleteById(autoSystemId);
        return SUCCESS_TIP;
    }

    /**
     * 修改carModel
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(AutoSystem autoSystem,HttpServletRequest request) {
        autoSystemService.updateById(autoSystem);
        //提交上来的ids
        String ids = request.getParameter("ids");
        String idsString[] = {};
        if(null!=ids) {
            if(ids.indexOf(",")<=0) {
                idsString = new String[1] ;
                idsString[0] = ids;
            }else{
                idsString = ids.split(",");
            }
        }
        //构造查询出来的ids
        EntityWrapper<AutoParts> ew = new EntityWrapper<>();
        ew.where("CAR_SYSTEM_ID={0}",autoSystem.getId());
        List<AutoParts> autoPartsList = iAutoPartsService.selectList(ew);
        if(null!=autoPartsList && 0<autoPartsList.size()){
            AutoParts autoParts ;
            for (int i=0;i<autoPartsList.size();i++){
                autoParts = autoPartsList.get(i);
                if(idsString.length>0){
                    for(int j=0;j<idsString.length;j++){
                        if(idsString[j] == autoParts.getId().toString()){
                            //删除原系统对应零部件列表中的零部件
                            autoPartsList.remove(i);
                            //删除新提交上来的数组中存在的零部件
                            idsString = deleteArray(j,idsString);
                        }
                    }
                }
            }
        }
        //原系统中剩余的元素是需要将对应的系统删除的
        if(null!=autoPartsList && 0<autoPartsList.size()){
            AutoParts autoParts ;
            for (int i=0;i<autoPartsList.size();i++){
                autoParts = autoPartsList.get(i);
                autoParts.setCarSystemId(null);
                iAutoPartsService.updateAllColumnById(autoParts);
            }
        }
        //新数组中剩余的元素为需要更新对应的系统Id的
        if(null!=idsString && idsString.length>0){
            AutoParts autoParts ;
            for(int i=0;i<idsString.length;i++){
                autoParts = new AutoParts();
                autoParts.setId(Integer.valueOf(idsString[i]));
                autoParts = iAutoPartsService.selectById(autoParts);
                autoParts.setCarSystemId(autoSystem.getId());
                iAutoPartsService.updateAllColumnById(autoParts);
            }
        }

        return SUCCESS_TIP;
    }

    /**
     * carModel详情
     */
    @RequestMapping(value = "/detail/{autoSystemId}")
    @ResponseBody
    public Object detail(@PathVariable("autoSystemId") Integer autoSystemId) {
        return autoSystemService.selectById(autoSystemId);
    }


    public String[] deleteArray(int index, String array[]) {
        //数组的删除其实就是覆盖前一位
        String[] arrNew = new String[array.length - 1];
        for (int i = 0; i < array.length - 1; i++) {
            if (i < index) {
                arrNew[i] = array[i];
            } else {
                arrNew[i] = array[i + 1];
            }
        }
        return arrNew;
    }
}

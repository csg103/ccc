package com.stylefeng.guns.modular.system.warpper;

import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;
import com.stylefeng.guns.core.common.constant.factory.ConstantFactory;

import java.util.List;
import java.util.Map;

public class AutoModelPartsWarpper extends BaseControllerWarpper{


        public AutoModelPartsWarpper(List<Map<String, Object>> list) {
            super(list);
        }

        @Override
        public void warpTheMap(Map<String, Object> map) {
            map.put("parentPartsName", ConstantFactory.me().getPartsName((Integer) map.get("pid")));
            map.put("carSystemName", ConstantFactory.me().getCarSystemNameByPartsId((Integer) map.get("id")));
        }

}



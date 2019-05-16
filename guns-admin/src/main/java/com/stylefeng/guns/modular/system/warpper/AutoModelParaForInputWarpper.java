package com.stylefeng.guns.modular.system.warpper;

import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;
import com.stylefeng.guns.core.common.constant.factory.ConstantFactory;

import java.util.List;
import java.util.Map;

public class AutoModelParaForInputWarpper extends BaseControllerWarpper{


        public AutoModelParaForInputWarpper(List<Map<String, Object>> list) {
            super(list);
        }

        @Override
        public void warpTheMap(Map<String, Object> map) {
            map.put("paraName", ConstantFactory.me().getParaName((Integer) map.get("paraId")));
            map.put("paraTypeId", ConstantFactory.me().getParaTypeId((Integer) map.get("paraId")));
        }

}



package com.stylefeng.guns.modular.system.warpper;

import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;
import com.stylefeng.guns.core.common.constant.factory.ConstantFactory;

import java.util.List;
import java.util.Map;

public class AutoModelParaWarpper extends BaseControllerWarpper{


        public AutoModelParaWarpper(List<Map<String, Object>> list) {
            super(list);
        }

        @Override
        public void warpTheMap(Map<String, Object> map) {
            map.put("modelName", ConstantFactory.me().getModelName((Integer) map.get("modelId")));
            map.put("partsCode", ConstantFactory.me().getPartsCode((Integer) map.get("partsId")));
            map.put("partsName", ConstantFactory.me().getPartsName((Integer) map.get("partsId")));
            map.put("carSystemName", ConstantFactory.me().getCarSystemNameByPartsId((Integer) map.get("partsId")));
            map.put("statusName", ConstantFactory.me().getPartsStatusName((String)map.get("status")));
        }

}



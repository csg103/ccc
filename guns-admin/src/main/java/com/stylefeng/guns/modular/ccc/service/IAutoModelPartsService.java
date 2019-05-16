package com.stylefeng.guns.modular.ccc.service;

import com.stylefeng.guns.modular.system.model.AutoModelParts;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 车型零部件 服务类
 * </p>
 *
 * @author zhaokai
 * @since 2019-03-12
 */
public interface IAutoModelPartsService extends IService<AutoModelParts> {
    List<Map<String, Object>> getListByNames(AutoModelParts autoModelParts);
}

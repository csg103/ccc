package com.stylefeng.guns.modular.system.dao;

import com.stylefeng.guns.modular.system.model.AutoModelParts;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 车型零部件 Mapper 接口
 * </p>
 *
 * @author zhaokai
 * @since 2019-03-12
 */
public interface AutoModelPartsMapper extends BaseMapper<AutoModelParts> {
    List<Map<String, Object>> getListByNames(AutoModelParts autoModelParts);
}

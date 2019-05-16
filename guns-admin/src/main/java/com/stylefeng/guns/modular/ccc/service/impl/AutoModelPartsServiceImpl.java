package com.stylefeng.guns.modular.ccc.service.impl;

import com.stylefeng.guns.modular.system.model.AutoModelParts;
import com.stylefeng.guns.modular.system.dao.AutoModelPartsMapper;
import com.stylefeng.guns.modular.ccc.service.IAutoModelPartsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 车型零部件 服务实现类
 * </p>
 *
 * @author zhaokai
 * @since 2019-03-12
 */
@Service
public class AutoModelPartsServiceImpl extends ServiceImpl<AutoModelPartsMapper, AutoModelParts> implements IAutoModelPartsService {
    @Autowired
    private AutoModelPartsMapper autoModelPartsMapper;

    @Override
    public List<Map<String, Object>> getListByNames(AutoModelParts autoModelParts) {
        return autoModelPartsMapper.getListByNames(autoModelParts);
    }
}

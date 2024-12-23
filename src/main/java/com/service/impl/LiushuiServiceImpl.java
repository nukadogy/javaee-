package com.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dao.LiushuiDao;
import com.entity.LiushuiEntity;
import com.entity.view.LiushuiView;
import com.service.LiushuiService;
import com.utils.PageUtils;
import com.utils.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 流水表 服务实现类
 * @since 2021-03-08
 */
@Service("liushuiService")
@Transactional
public class LiushuiServiceImpl extends ServiceImpl<LiushuiDao, LiushuiEntity> implements LiushuiService {

    @Override
    public PageUtils queryPage(Map<String,Object> params) {
        if(params != null && (params.get("limit") == null || params.get("page") == null)){
            params.put("page","1");
            params.put("limit","10");
        }
        Page<LiushuiView> page =new Query<LiushuiView>(params).getPage();
        page.setRecords(baseMapper.selectListView(page,params));
        return new PageUtils(page);
    }

}

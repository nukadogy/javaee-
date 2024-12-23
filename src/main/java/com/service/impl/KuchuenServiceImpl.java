package com.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dao.KuchuenDao;
import com.entity.KuchuenEntity;
import com.entity.view.KuchuenView;
import com.service.KuchuenService;
import com.utils.PageUtils;
import com.utils.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 库存表 服务实现类
 * @since 2021-03-08
 */
@Service("kuchuenService")
@Transactional
public class KuchuenServiceImpl extends ServiceImpl<KuchuenDao, KuchuenEntity> implements KuchuenService {

    @Override
    public PageUtils queryPage(Map<String,Object> params) {
        if(params != null && (params.get("limit") == null || params.get("page") == null)){
            params.put("page","1");
            params.put("limit","10");
        }
        Page<KuchuenView> page =new Query<KuchuenView>(params).getPage();
        page.setRecords(baseMapper.selectListView(page,params));
        return new PageUtils(page);
    }

}

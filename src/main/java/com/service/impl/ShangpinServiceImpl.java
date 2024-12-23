package com.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dao.ShangpinDao;
import com.entity.ShangpinEntity;
import com.entity.view.ShangpinView;
import com.service.ShangpinService;
import com.utils.PageUtils;
import com.utils.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 商品表 服务实现类
 * @since 2021-03-08
 */
@Service("shangpinService")
@Transactional
public class ShangpinServiceImpl extends ServiceImpl<ShangpinDao, ShangpinEntity> implements ShangpinService {

    @Override
    public PageUtils queryPage(Map<String,Object> params) {
        if(params != null && (params.get("limit") == null || params.get("page") == null)){
            params.put("page","1");
            params.put("limit","10");
        }
        Page<ShangpinView> page =new Query<ShangpinView>(params).getPage();
        page.setRecords(baseMapper.selectListView(page,params));
        return new PageUtils(page);
    }

}

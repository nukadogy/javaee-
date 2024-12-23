package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.entity.KuchuenEntity;
import com.entity.view.KuchuenView;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 库存表 Dao 接口
 *
 * @since 2021-03-08
 */
public interface KuchuenDao extends BaseMapper<KuchuenEntity> {

   List<KuchuenView> selectListView(Pagination page, @Param("params") Map<String, Object> params);

}

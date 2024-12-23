package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.entity.LiushuiEntity;
import com.entity.view.LiushuiView;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 流水表 Dao 接口
 *
 * @since 2021-03-08
 */
public interface LiushuiDao extends BaseMapper<LiushuiEntity> {

   List<LiushuiView> selectListView(Pagination page, @Param("params") Map<String, Object> params);

}

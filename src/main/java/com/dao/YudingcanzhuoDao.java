package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.entity.YudingcanzhuoEntity;
import com.entity.view.YudingcanzhuoView;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 预定餐桌表 Dao 接口
 *
 * @since 2021-03-08
 */
public interface YudingcanzhuoDao extends BaseMapper<YudingcanzhuoEntity> {

   List<YudingcanzhuoView> selectListView(Pagination page, @Param("params") Map<String, Object> params);

}

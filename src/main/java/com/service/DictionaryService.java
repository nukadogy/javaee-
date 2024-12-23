package com.service;

import com.baomidou.mybatisplus.service.IService;
import com.entity.DictionaryEntity;
import com.utils.PageUtils;

import java.util.Map;

/**
 * 字典表 服务类
 * @since 2021-03-08
 */
public interface DictionaryService extends IService<DictionaryEntity> {

     PageUtils queryPage(Map<String, Object> params);

}
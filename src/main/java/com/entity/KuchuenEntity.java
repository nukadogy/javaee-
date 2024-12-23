package com.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import org.apache.commons.beanutils.BeanUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

/**
 * 库存表
 *
 * @email
 * @date 2021-03-08
 */
@TableName("kuchuen")
public class KuchuenEntity<T> implements Serializable {
    private static final long serialVersionUID = 1L;


    public KuchuenEntity() {

    }

    public KuchuenEntity(T t) {
    try {
    BeanUtils.copyProperties(this, t);
    } catch (IllegalAccessException | InvocationTargetException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
    }
    }


    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    @TableField(value = "id")

    private Integer id;


    /**
     * 商品名称
     */
    @TableField(value = "name")

    private String name;


    /**
     * 库存数量
     */
    @TableField(value = "number")
    private Integer number;


    /**
	 * 设置：主键
	 */
    public Integer getId() {
        return id;
    }


    /**
	 * 获取：主键
	 */

    public void setId(Integer id) {
        this.id = id;
    }
    /**
	 * 设置：商品名称
	 */
    public String getName() {
        return name;
    }


    /**
	 * 获取：商品名称
	 */

    public void setName(String name) {
        this.name = name;
    }
    /**
	 * 设置：库存数量
	 */
    public Integer getNumber() {
        return number;
    }


    /**
	 * 获取：库存数量
	 */

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public String toString() {
    return "Kuchuen{" +
            "id=" + id +
            ", name=" + name +
            ", number=" + number +
    "}";
    }
    }

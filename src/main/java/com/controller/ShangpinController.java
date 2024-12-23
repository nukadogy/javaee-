package com.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.*;
import com.service.*;
import com.utils.PageUtils;
import com.utils.R;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * 商品表
 * 后端接口
 * @author
 * @email
 * @date 2021-03-08
*/
@RestController
@Controller
@RequestMapping("/shangpin")
public class ShangpinController {
    private static final Logger logger = LoggerFactory.getLogger(ShangpinController.class);

    @Autowired
    private ShangpinService shangpinService;

    @Autowired
    private KuchuenService kuchuenService;

    @Autowired
    private LiushuiService liushuiService;


    @Autowired
    private YudingcanzhuoService yudingcanzhuoService;

    @Autowired
    private DingdanxiangqingService dingdanxiangqingService;

    /**
    * 后端列表
    */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params){
        logger.debug("Controller:"+this.getClass().getName()+",page方法");
        PageUtils page = shangpinService.queryPage(params);
        return R.ok().put("data", page);
    }
    /**
    * 后端详情
    */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        logger.debug("Controller:"+this.getClass().getName()+",info方法");
        ShangpinEntity shangpin = shangpinService.selectById(id);
        if(shangpin!=null){
            return R.ok().put("data", shangpin);
        }else {
            return R.error(511,"查不到数据");
        }

    }

    /**
    * 后端保存
    */
    @RequestMapping("/save")
    public R save(@RequestBody ShangpinEntity shangpin, HttpServletRequest request){
        logger.debug("Controller:"+this.getClass().getName()+",save");
        Wrapper<ShangpinEntity> queryWrapper = new EntityWrapper<ShangpinEntity>()
            .eq("hx_types", shangpin.getHxTypes())
            ;
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        ShangpinEntity shangpinEntity = shangpinService.selectOne(queryWrapper);
        if("".equals(shangpin.getImgPhoto()) || "null".equals(shangpin.getImgPhoto())){
            shangpin.setImgPhoto(null);
        }
        if(shangpinEntity==null){
            shangpinService.insert(shangpin);
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

    /**
    * 修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody ShangpinEntity shangpin, HttpServletRequest request){
        logger.debug("Controller:"+this.getClass().getName()+",update");
        //根据字段查询是否有相同数据
        Wrapper<ShangpinEntity> queryWrapper = new EntityWrapper<ShangpinEntity>()
            .notIn("id",shangpin.getId())
            .eq("hx_types", shangpin.getHxTypes())
            .eq("money", shangpin.getMoney())
            .eq("shangpin_content", shangpin.getShangpinContent())
            ;
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        ShangpinEntity shangpinEntity = shangpinService.selectOne(queryWrapper);
        if("".equals(shangpin.getImgPhoto()) || "null".equals(shangpin.getImgPhoto())){
                shangpin.setImgPhoto(null);
        }
        if(shangpinEntity==null){
            shangpinService.updateById(shangpin);//根据id更新
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }


    /**
    * 删除
    */
    @RequestMapping("/diancai")
    public R diancai(@RequestBody Map<String, Object> params, HttpServletRequest request){
        if(params.size() == 0){
            return R.error("请选择要购买的商品");
        }
        //获取系统当前时间的时间戳作为订单号
        long danhao = new Date().getTime();
        //new 一个订单详情表
        DingdanxiangqingEntity dingdanxiangqing = new DingdanxiangqingEntity();
        //new 一个流水表
        LiushuiEntity liushui = new LiushuiEntity();
        //循环前端传过来的key
            for (String id: params.keySet()) {
                //根据前端传来的key（id）查询数据
                ShangpinEntity shangpin = shangpinService.selectById(id);
                //判断查出来的数据是否为空
                if(shangpin == null){
                    //为空返回前台
                    return R.error();
                }
                //判断前端传来的商品数量是否为空或者小于0
                if(StringUtils.isBlank((String)params.get(id)) || Integer.parseInt(String.valueOf(params.get(id))) <= 0){
                    return R.error("您选择的商品数量不能小于0哦");
                }

                //根据id去库存表中查询数据
                KuchuenEntity kuchuenEntity = kuchuenService.selectById(shangpin.getHxTypes());
                //如果为空返回前台
                if(kuchuenEntity == null){
                    return R.error("这件商品商品不存在于库存");
                }
                //把map中的值转为Integger
                Integer zhi = Integer.parseInt(String.valueOf(params.get(id)));
                //根据当前登录人的id去已预约的餐桌信息表中查询数据
                YudingcanzhuoEntity yudingcanzhuo = yudingcanzhuoService.selectOne(new EntityWrapper().eq("yh_types", request.getSession().getAttribute("userId")));
                //如果为空就返回前端
                if(yudingcanzhuo == null){
                    return R.error("请先预定餐桌后再预定菜品，以免餐桌不足");
                }
                //如果为空就返回前端
                if(yudingcanzhuo.getCzTypes() == null){
                    return R.error("请先预定餐桌后再预定菜品，以免餐桌不足");
                }

                //判断库存中的商品数量是否大于用户购买的数量
                if(kuchuenEntity.getNumber() < zhi){
                    //库存小于是返回并提示
                    return R.error(kuchuenEntity.getName()+" 库存只剩："+kuchuenEntity.getNumber()+" 个，不足："+ params.get(id)+" 个！！！");
                }

                //订单详情中添加进用户购买数量
                dingdanxiangqing.setNumber(zhi);
                //在订单详情表中存入生成的订单号
                dingdanxiangqing.setOdd(String.valueOf(danhao));
                //在订单详情表中存入商品信息
                dingdanxiangqing.setHxTypes(shangpin.getHxTypes());
                //订单详情表中存入商品价格
                dingdanxiangqing.setMoney(shangpin.getMoney());
                //新增订单详情信息
                dingdanxiangqingService.insert(dingdanxiangqing);


                //给流水表中的总价赋值为0
                liushui.setMaxMoney(0.0);
                //获取系统当前时间
                liushui.setCreateTime(new Date());
                //设置上面生成的订单号
                liushui.setOdd(String.valueOf(danhao));
                //在流水表存入中查出来的餐桌信息
                liushui.setCzTypes(yudingcanzhuo.getCzTypes());
                //在流水表存入当前登录用户的信息
                liushui.setYhTypes((Integer) request.getSession().getAttribute("userId"));
                //设置为未支付
                liushui.setSfTypes(2);
                //计算总价
                liushui.setMaxMoney(liushui.getMaxMoney()+(dingdanxiangqing.getMoney()*dingdanxiangqing.getNumber()));

                //库存数量减去用户购买数量
                int i = kuchuenEntity.getNumber() - zhi;
                //将库存数量更新
                kuchuenEntity.setNumber(i);
                //修改库存数量
                kuchuenService.updateById(kuchuenEntity);
            }
        //新增流水信息
        liushuiService.insert(liushui);
        return R.ok();
    }

    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        logger.debug("Controller:"+this.getClass().getName()+",delete");
        shangpinService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
}

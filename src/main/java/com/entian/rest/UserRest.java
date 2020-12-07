package com.entian.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.entian.service.entity.UserEntity;
import com.entian.service.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author jianggangli
 * @version 1.0 2020/12/7 13:41
 * 功能:
 */
@Slf4j
@RestController
@RefreshScope
@Api(tags = "mp例子")
@Validated
public class UserRest {
    @Autowired
    private UserService userService;


    @ApiOperation("入参校验例子")
    @GetMapping("/selectList")
    public List<UserEntity> selectList() {
        List<UserEntity> userList = userService.list();
        userList.forEach(System.out::println);
        return userList;
    }

    /**
     * 根据ID获取用户信息
     * @Author jianggangli
     * @CreateTime 2019/6/8 16:34
     * @Param  userId  用户ID
     * @Return UserEntity 用户实体
     */
    @ApiOperation("根据ID获取用户信息")
    @GetMapping("/getInfo")
    public UserEntity getInfo(String userId){
        UserEntity UserEntity = userService.getById(userId);
        return UserEntity;
    }
    /**
     * 查询全部信息
     * @Author jianggangli
     * @CreateTime 2019/6/8 16:35
     * @Param  userId  用户ID
     * @Return List<UserEntity> 用户实体集合
     */
    @ApiOperation("查询全部信息")
    @GetMapping("/getList")
    public List<UserEntity> getList(){
        List<UserEntity> UserEntityList = userService.list();
        return UserEntityList;
    }
    /**
     * 分页查询全部数据
     * @Author jianggangli
     * @CreateTime 2019/6/8 16:37
     * @Return IPage<UserEntity> 分页数据
     */
    @ApiOperation("分页查询全部数据")
    @GetMapping("/getInfoListPage")
    public IPage<UserEntity> getInfoListPage(){
        //需要在Config配置类中配置分页插件
        IPage<UserEntity> page = new Page<>();
        page.setCurrent(5); //当前页
        page.setSize(1);    //每页条数
        page = userService.page(page);
        return page;
    }
    /**
     * 根据指定字段查询用户信息集合
     * @Author jianggangli
     * @CreateTime 2019/6/8 16:39
     * @Return Collection<UserEntity> 用户实体集合
     */
    @ApiOperation("根据指定字段查询用户信息集合")
    @GetMapping("/getListMap")
    public Collection<UserEntity> getListMap(){
        Map<String,Object> map = new HashMap<>();
        //kay是字段名 value是字段值
        map.put("age",20);
        Collection<UserEntity> UserEntityList = userService.listByMap(map);
        return UserEntityList;
    }
    /**
     * 新增用户信息
     * @Author jianggangli
     * @CreateTime 2019/6/8 16:40
     */
    @ApiOperation("新增用户信息")
    @GetMapping("/saveInfo")
    public void saveInfo(){
        UserEntity UserEntity = new UserEntity();
        UserEntity.setName("小龙");
        UserEntity.setSkill("JAVA");
        UserEntity.setAge(18);
        UserEntity.setFraction(59L);
        UserEntity.setEvaluate("该学生是一个在改BUG的码农");
        userService.save(UserEntity);
    }
    /**
     * 批量新增用户信息
     * @Author jianggangli
     * @CreateTime 2019/6/8 16:42
     */
    @ApiOperation("批量新增用户信息")
    @GetMapping("/saveInfoList")
    public void saveInfoList(){
        //创建对象
        UserEntity jianggangli = new UserEntity();
        jianggangli.setName("jianggangli");
        jianggangli.setSkill("睡觉");
        jianggangli.setAge(18);
        jianggangli.setFraction(60L);
        jianggangli.setEvaluate("jianggangli是一个爱睡觉,并且身材较矮骨骼巨大的骷髅小胖子");
        UserEntity papyrus = new UserEntity();
        papyrus.setName("papyrus");
        papyrus.setSkill("JAVA");
        papyrus.setAge(18);
        papyrus.setFraction(58L);
        papyrus.setEvaluate("Papyrus是一个讲话大声、个性张扬的骷髅，给人自信、有魅力的骷髅小瘦子");
        //批量保存
        List<UserEntity> list =new ArrayList<>();
        list.add(jianggangli);
        list.add(papyrus);
        userService.saveBatch(list);
    }
    /**
     * 更新用户信息
     * @Author jianggangli
     * @CreateTime 2019/6/8 16:47
     */
    @ApiOperation("更新用户信息")
    @GetMapping("/updateInfo")
    public void updateInfo(){
        //根据实体中的ID去更新,其他字段如果值为null则不会更新该字段,参考yml配置文件
        UserEntity userEntity = userService.getById("3d7a7a83e3f5e67c0089a75ce76aaae0");
        userEntity.setAge(23);
        userService.updateById(userEntity);
    }
    /**
     * 新增或者更新用户信息
     * @Author jianggangli
     * @CreateTime 2019/6/8 16:50
     */
    @ApiOperation("新增或者更新用户信息")
    @GetMapping("/saveOrUpdateInfo")
    public void saveOrUpdate(){
        //传入的实体类UserEntity中ID为null就会新增(ID自增)
        //实体类ID值存在,如果数据库存在ID就会更新,如果不存在就会新增
        UserEntity UserEntity = new UserEntity();
//        UserEntity.setId(1L);
        UserEntity.setAge(20);
        userService.saveOrUpdate(UserEntity);
    }
    /**
     * 根据ID删除用户信息
     * @Author jianggangli
     * @CreateTime 2019/6/8 16:52
     */
    @ApiOperation("根据ID删除用户信息")
    @GetMapping("/deleteInfo")
    public void deleteInfo(String userId){
        userService.removeById(userId);
    }

    /**
     * 根据ID删除用户信息
     * @Author jianggangli
     * @CreateTime 2019/6/8 16:52
     */
    @ApiOperation("根据ID删除用户信息并自动填充更新字段")
    @GetMapping("/deleteByIdWithFill")
    public void deleteByIdWithFill(String userId){
        UserEntity user = new UserEntity();
        user.setId(userId);
        //此字段没有起作用，无效果
        user.setAge(99);
        user.setEvaluate("我滴妈呀！");
        userService.deleteByIdWithFill(user);
    }
    /**
     * 根据ID批量删除用户信息
     * @Author jianggangli
     * @CreateTime 2019/6/8 16:55
     */
    @ApiOperation("根据ID批量删除用户信息")
    @GetMapping("/deleteInfoList")
    public void deleteInfoList(){
        List<String> userIdlist = new ArrayList<>();
        userIdlist.add("12");
        userIdlist.add("13");
        userService.removeByIds(userIdlist);
    }
    /**
     * 根据指定字段删除用户信息
     * @Author jianggangli
     * @CreateTime 2019/6/8 16:57
     */
    @ApiOperation("根据指定字段删除用户信息")
    @GetMapping("/deleteInfoMap")
    public void deleteInfoMap(){
        //kay是字段名 value是字段值
        Map<String,Object> map = new HashMap<>();
        map.put("skill","删除");
        map.put("fraction",10L);
        userService.removeByMap(map);
    }
}

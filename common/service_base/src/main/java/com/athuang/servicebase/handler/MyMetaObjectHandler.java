package com.athuang.servicebase.handler;/**
 * @program: guli_parent
 * @description:
 * @author: Mr.Huang
 * @create: 2022-09-04 15:40
 **/

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @program: guli_parent
 *
 * @description:
 *
 * @author: Mr.Huang
 *
 * @create: 2022-09-04 15:40
 **/
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {

//        传的属性名称，不是字段名称
        this.setFieldValByName("gmtCreate",new Date(),metaObject);
        this.setFieldValByName("gmtModified",new Date(),metaObject);

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("gmtModified",new Date(),metaObject);

    }
}

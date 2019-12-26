package com.example.jianancangku.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.List;


/**
 * @param
 * @author chenkuidou
 * @description bean转化类
 * @date 2019/1/7 20:17
 * @return
 **/
public class BeanConvertor {

    /**
     * 方法说明：将bean转化为另一种bean实体
     *
     * @param object
     * @param entityClass
     * @return
     */
    public static <T> T convertBean(Object object, Class<T> entityClass) {
        if (null == object) {
            return null;
        }
        return JSON.parseObject(JSON.toJSONString(object), entityClass);
    }

    public static <T> T convertBean(String value, Class<T> entityClass) {
        JSONObject jsonObj = (JSONObject) JSON.parse(value);
        if (null == jsonObj) {
            return null;
        }
        return JSON.parseObject(JSON.toJSONString(jsonObj), entityClass);
    }

    /**
     * 方法说明：对象转换(List)
     *
     * @param target           目标对象
     * @param ignoreProperties 排除要copy的属性
     * @return
     */
    public static <T, E> List<T> convertList(Object object, Class<T> target, String... ignoreProperties) {
        if (null == object) {
            return null;
        }
        List<T> targetList =  JSON.parseArray(JSON.toJSONString(object),target);
        return targetList;
    }


}

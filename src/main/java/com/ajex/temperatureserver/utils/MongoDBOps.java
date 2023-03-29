package com.ajex.temperatureserver.utils;

import com.ajex.temperatureserver.enums.ConstantEnum;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MongoDBOps {
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * @param object           数据
     * @param collectionName 文档集合名字
     */
    public void save(JSONObject object, String collectionName) {
        if(Strings.isBlank(collectionName)){
            log.error("collectionName is blank,save to mongodb error!");
            return;
        }
        object.put(ConstantEnum.FILL_MONGODB_CREATE_TIME_FIELD.getName(),System.currentTimeMillis());
        mongoTemplate.insert(object,collectionName);
    }
}

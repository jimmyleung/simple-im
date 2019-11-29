package com.jimmy.simpleim.protocol.serializer.impl;

import com.alibaba.fastjson.JSON;
import com.jimmy.simpleim.protocol.serializer.Serializer;
import com.jimmy.simpleim.protocol.serializer.SerializerAlgorithm;

public class JSONSerializer implements Serializer {

    @Override
    public byte getAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] encode(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T decode(Class<T> clazz, byte[] datas) {
        return JSON.parseObject(datas, clazz);
    }
}

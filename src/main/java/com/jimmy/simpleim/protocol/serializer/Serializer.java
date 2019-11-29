package com.jimmy.simpleim.protocol.serializer;

import com.jimmy.simpleim.protocol.serializer.impl.JSONSerializer;

public interface Serializer {

    Serializer DEFAULT = new JSONSerializer();

    byte getAlgorithm();

    /**
     * 编码
     * @param object
     * @return
     */
    byte[] encode(Object object);

    /**
     * 解码
     * @param clazz
     * @param datas
     * @param <T>
     * @return
     */
    <T> T decode(Class<T> clazz, byte[] datas);
}

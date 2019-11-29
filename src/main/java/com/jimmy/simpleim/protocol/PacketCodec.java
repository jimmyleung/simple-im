package com.jimmy.simpleim.protocol;

import com.jimmy.simpleim.protocol.serializer.Serializer;
import com.jimmy.simpleim.protocol.serializer.SerializerAlgorithm;
import com.jimmy.simpleim.protocol.serializer.impl.JSONSerializer;
import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

/**
 * 请求数据结构：
 * 4位 MAGIC_NUMBER -> INT
 * 1位 VERSION -> BYTE
 * 1位 SERIALIZER -> BYTE
 * 1位 COMMAND -> BYTE
 * 4位 CONTENT_LENGTH -> INT
 * 剩余内容 CONTENT
 */
public class PacketCodec {

    public static final PacketCodec INSTANCE = new PacketCodec();

    public static final int MAGIC_NUMBER = 123456;

    private Map<Byte, Serializer> serializerMap;
    private Map<Byte, Class<? extends Packet>> packetMap;

    private PacketCodec() {
        packetMap = new HashMap<>();


        serializerMap = new HashMap<>();
        serializerMap.put(SerializerAlgorithm.JSON, new JSONSerializer());
    }

    public void encode(ByteBuf byteBuf, Packet packet) {
        byte[] bytes = Serializer.DEFAULT.encode(packet);

        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

    }

    public Packet decode(ByteBuf byteBuf) {
        //ship MAGIC_NUMBER
        byteBuf.skipBytes(4);
        //skip version
        byteBuf.skipBytes(1);
        byte serializerKey = byteBuf.readByte();
        byte command = byteBuf.readByte();
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.writeBytes(bytes);

        Serializer serializer = serializerMap.get(serializerKey);
        Class<? extends Packet> packetClazz = packetMap.get(command);
        if (serializer != null) {
            Packet packet = serializer.decode(packetClazz, bytes);
            return packet;
        }
        return null;
    }

}

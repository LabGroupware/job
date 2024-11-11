package org.cresplanex.nova.job.util;

import build.buf.gen.cresplanex.nova.v1.*;

import java.util.Map;
import java.util.List;

public class FlexProtoMapper {

    /**
     * 任意のObjectをFlexにマッピング
     */
    public static Flex mapToFlex(Object obj) {
        Flex.Builder flexBuilder = Flex.newBuilder();

        switch (obj) {
            case null -> {
                // NullableFlexProtoMapperを使用して、null値を明示的に処理
                flexBuilder.setNullableFlexValue(NullableFlexProtoMapper.mapToNullableFlex(null));
            }
            case String s -> flexBuilder.setNullableStringValue(NullableString.newBuilder()
                    .setHasValue(true)
                    .setValue(s)
                    .build());
            case Integer i -> flexBuilder.setNullableIntValue(NullableInt32.newBuilder()
                    .setHasValue(true)
                    .setValue(i)
                    .build());
            case Long l -> flexBuilder.setNullableLongValue(NullableInt64.newBuilder()
                    .setHasValue(true)
                    .setValue(l)
                    .build());
            case Float v -> flexBuilder.setNullableFloatValue(NullableFloat.newBuilder()
                    .setHasValue(true)
                    .setValue(v)
                    .build());
            case Double v -> flexBuilder.setNullableDoubleValue(NullableDouble.newBuilder()
                    .setHasValue(true)
                    .setValue(v)
                    .build());
            case Boolean b -> flexBuilder.setNullableBoolValue(NullableBool.newBuilder()
                    .setHasValue(true)
                    .setValue(b)
                    .build());
            case byte[] bytes -> flexBuilder.setNullableBytesValue(NullableBytes.newBuilder()
                    .setHasValue(true)
                    .setValue(com.google.protobuf.ByteString.copyFrom(bytes))
                    .build());
            case Flex flex -> {
                // Nested FlexをNullableFlexとして処理
                flexBuilder.setNullableFlexValue(NullableFlexProtoMapper.mapToNullableFlex(flex));
            }
            case List<?> objects -> {
                // 配列やリストをNullableFlexArrayにマッピング
                FlexArray.Builder arrayBuilder = FlexArray.newBuilder();
                for (Object element : objects) {
                    arrayBuilder.addFlex(mapToFlex(element));
                }
                flexBuilder.setNullableArrayValue(NullableFlexArray.newBuilder()
                        .setHasValue(true)
                        .setValue(arrayBuilder.build())
                        .build());
            }
            case Map<?, ?> map -> {
                // MapをNullableFlexMapにマッピング
                FlexMap.Builder mapBuilder = FlexMap.newBuilder();
                for (Map.Entry<?, ?> entry : map.entrySet()) {
                    if (!(entry.getKey() instanceof String key)) {
                        throw new IllegalArgumentException("Map keys must be of type String");
                    }
                    mapBuilder.putFlex(key, mapToFlex(entry.getValue()));
                }
                flexBuilder.setNullableMapValue(NullableFlexMap.newBuilder()
                        .setHasValue(true)
                        .setValue(mapBuilder.build())
                        .build());
            }
            default -> {
                // それ以外の型をNullableFlexとして処理
                flexBuilder.setNullableFlexValue(NullableFlexProtoMapper.mapToNullableFlex(obj));
            }
        }

        return flexBuilder.build();
    }
}

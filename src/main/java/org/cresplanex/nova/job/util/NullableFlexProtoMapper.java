package org.cresplanex.nova.job.util;

import build.buf.gen.cresplanex.nova.v1.*;

public class NullableFlexProtoMapper {

    /**
     * 任意のObjectをNullableFlexにマッピング
     */
    public static NullableFlex mapToNullableFlex(Object obj) {
        NullableFlex.Builder nullableFlexBuilder = NullableFlex.newBuilder();

        if (obj == null) {
            // 値がnullの場合、has_valueをfalseに設定
            nullableFlexBuilder.setHasValue(false);
        } else {
            // 値が存在する場合はFlexにマッピングし、has_valueをtrueに設定
            nullableFlexBuilder.setHasValue(true);
            nullableFlexBuilder.setValue(FlexProtoMapper.mapToFlex(obj));
        }

        return nullableFlexBuilder.build();
    }
}

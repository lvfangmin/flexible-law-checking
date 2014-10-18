package org.coder.json;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.coder.op.Op;
import org.coder.util.OpScanner;

/**
 * Custom @{link TypeIdResolver} implementation, which support init polymorphic
 * Op classes according to the Op name in the rule.
 *
 */
public class OpTypeIdResolver implements TypeIdResolver {
    private JavaType baseType;

    @Override
    public Id getMechanism() {
        return Id.CUSTOM;
    }

    @Override
    public String idFromBaseType() {
        return idFromValueAndType(null, baseType.getRawClass());
    }

    @Override
    public String idFromValue(Object value) {
        return idFromValueAndType(value, value.getClass());
    }

    @Override
    @SuppressWarnings("unchecked")
    public String idFromValueAndType(Object value, Class<?> type) {
        return OpScanner.get((Class<? extends Op>) type);
    }

    @Override
    public void init(JavaType baseType) {
        this.baseType = baseType;
    }

    @Override
    public JavaType typeFromId(String id) {
        return TypeFactory.defaultInstance().constructSpecializedType(baseType, OpScanner.get(id));
    }

}

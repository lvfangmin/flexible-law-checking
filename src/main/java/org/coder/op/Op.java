package org.coder.op;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import org.coder.json.OpTypeIdResolver;

/**
 * The root Op interface.
 *
 * NOTE: the annotation tells Jackson to use customized @{link OpTypeIdResolver} to
 *       resolve the types in Json.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CUSTOM, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@JsonTypeIdResolver(OpTypeIdResolver.class)
public interface Op {

    public boolean apply(Map<String, String> attributes);
}

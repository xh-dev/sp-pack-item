package dev.xethh.sp;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Handler implements RequestHandler<Map<String, Object>, Map<String, Object>> {
    static ObjectMapper om = new ObjectMapper();

    static {
        SimpleModule mod = new SimpleModule();
        mod.addDeserializer(BigDecimal.class, new BigDecimalDeserializer());
        om.registerModule(mod);
    }

    @Override
    public Map<String, Object> handleRequest(Map<String, Object> stringStringMap, Context context) {
        Map<String, Object> map = new HashMap<>();
        try {
            Main.DtoReq data;
            if (Boolean.TRUE.equals(stringStringMap.get("isBase64Encoded"))) {
                throw new RuntimeException("Not support");
            } else {
                data = om.readValue(((String) stringStringMap.get("body")).replaceAll("\\n", ""), Main.DtoReq.class);
            }
            List<Box> boxes = BoxPacking.process(data.getItems(), data.getMax());
            map.put("isBase64Encoded", false);
            map.put("statusCode", 200);
            map.put("headers", new Object());
            map.put("body", om.writeValueAsString(Main.DtoResp.convert(boxes)));
            return map;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

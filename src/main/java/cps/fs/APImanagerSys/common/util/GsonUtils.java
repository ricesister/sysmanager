package cps.fs.APImanagerSys.common.util;

import com.google.gson.*;

import cps.fs.APImanagerSys.common.util.adapter.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

/**
 * json转换
 * @author fs
 * @date 2018年9月12日
 * @description
 */
public class GsonUtils {
    public static Gson gson;

    private static final JsonParser JSONP_ARSER = new JsonParser();

    static {
        if (gson == null) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.serializeNulls();
            gsonBuilder.registerTypeAdapter(Integer.class, new IntegerTypeAdapter());
            gsonBuilder.registerTypeAdapter(int.class, new IntegerTypeAdapter());
            gsonBuilder.registerTypeAdapter(Double.class, new DoubleTypeAdapter());
            gsonBuilder.registerTypeAdapter(double.class, new DoubleTypeAdapter());
            gsonBuilder.registerTypeAdapter(Float.class, new FloatTypeAdapter());
            gsonBuilder.registerTypeAdapter(float.class, new FloatTypeAdapter());
            gsonBuilder.registerTypeAdapter(Long.class, new LongTypeAdapter());
            gsonBuilder.registerTypeAdapter(long.class, new LongTypeAdapter());
            gsonBuilder.registerTypeAdapter(Short.class, new ShortTypeAdapter());
            gsonBuilder.registerTypeAdapter(short.class, new ShortTypeAdapter());
            gsonBuilder.registerTypeAdapter(Character.class, new CharTypeAdapter());
            gsonBuilder.registerTypeAdapter(char.class, new CharTypeAdapter());
            gsonBuilder.registerTypeAdapter(Byte.class, new ByteTypeAdapter());
            gsonBuilder.registerTypeAdapter(byte.class, new ByteTypeAdapter());
            gsonBuilder.registerTypeAdapter(BigDecimal.class, new BigDecimalTypeAdapter());
            gsonBuilder.registerTypeAdapter(BigInteger.class, new BigIntegerTypeAdapter());
            gsonBuilder.registerTypeAdapter(Timestamp.class, new TimestampTypeAdapter());
            gson = gsonBuilder.create();
        }
    }

    public static String toJson(Object object) {
        try {
            return gson.toJson(object);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return null;
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        if (json != null && !"".equals(json.trim())) {
            try {
                return gson.fromJson(json, classOfT);
            } catch (JsonSyntaxException e) {
            	e.printStackTrace();
            }
        }
        return null;
    }

    public static <T> List<T> toListBean(String json, Class<T[]> tClass) {
        if (json != null && !"".equals(json.trim())) {
            T[] arr = gson.fromJson(json, tClass);
            return Arrays.asList(arr);
        }
        return null;
    }

    public static <T> List<T> toListBean(JsonArray jsonArray, Class<T[]> tClass) {
        if (jsonArray != null) {
            T[] arr = gson.fromJson(jsonArray, tClass);
            return Arrays.asList(arr);
        }
        return null;
    }

    public static JsonElement getJsonElement(String json) {
        if (json != null && !"".equals(json.trim())) {
            return JSONP_ARSER.parse(json);
        }
        return JsonNull.INSTANCE;
    }

    public static JsonObject getJsonObject(String json) {
        if (json != null && !"".equals(json.trim())) {
            return JSONP_ARSER.parse(json).getAsJsonObject();
        }
        return new JsonObject();
    }

    public static JsonArray getJsonArray(String json) {
        if (json != null && !"".equals(json.trim())) {
            return JSONP_ARSER.parse(json).getAsJsonArray();
        }
        return new JsonArray();
    }
}

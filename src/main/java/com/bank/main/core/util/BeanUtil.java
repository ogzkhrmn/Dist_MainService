package com.bank.main.core.util;

import com.bank.main.core.model.AnnotatedFieldModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanUtil {

    private static Map<String, Class> serviceBeans = new HashMap<>();
    private static Map<String, List<AnnotatedFieldModel>> serviceFields = new HashMap<>();

    public static void addBean(String serviceName, Class clazz) {
        serviceBeans.put(serviceName, clazz);
    }

    public static Class getBean(String serviceName) {
        return serviceBeans.get(serviceName);
    }

    public static void addField(String serviceName, AnnotatedFieldModel field) {
        if (!serviceFields.containsKey(serviceName)) {
            List<AnnotatedFieldModel> list = new ArrayList<>();
            list.add(field);
            serviceFields.put(serviceName, list);
        } else {
            serviceFields.get(serviceName).add(field);
        }

    }

    public static List<AnnotatedFieldModel> getField(String serviceName) {
        return serviceFields.get(serviceName);
    }

    public static Map<String, List<AnnotatedFieldModel>> getFields() {
        return serviceFields;
    }
}

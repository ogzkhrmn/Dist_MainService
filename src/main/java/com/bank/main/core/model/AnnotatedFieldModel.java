package com.bank.main.core.model;

import java.lang.reflect.Field;

public class AnnotatedFieldModel {

    private Class clazz;
    private Field field;

    public AnnotatedFieldModel(Class clazz, Field field) {
        this.clazz = clazz;
        this.field = field;
    }

    public Class getClazz() {
        return clazz;
    }

    public Field getField() {
        return field;
    }

}

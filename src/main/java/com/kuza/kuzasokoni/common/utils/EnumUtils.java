package com.kuza.kuzasokoni.common.utils;


public class EnumUtils {

    public static <E extends Enum<E>> E fromString(Class<E> enumType, String value) {
        try {
            return Enum.valueOf(enumType, value.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid value for enum " + enumType.getSimpleName() + ": " + value);
        }
    }

    public static <E extends Enum<E>> boolean isValid(Class<E> enumType, String value) {
        try {
            Enum.valueOf(enumType, value.toUpperCase());
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
}

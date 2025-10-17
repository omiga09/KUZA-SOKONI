package com.kuza.kuzasokoni.common.utils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateUtils {

    public static LocalDate today() {
        return LocalDate.now();
    }

    public static boolean isPast(LocalDate date) {
        return date.isBefore(LocalDate.now());
    }

    public static boolean isFuture(LocalDate date) {
        return date.isAfter(LocalDate.now());
    }

    public static long daysBetween(LocalDate start, LocalDate end) {
        return ChronoUnit.DAYS.between(start, end);
    }

    public static LocalDate addDays(LocalDate date, int days) {
        return date.plusDays(days);
    }

    public static LocalDate subtractDays(LocalDate date, int days) {
        return date.minusDays(days);
    }
}

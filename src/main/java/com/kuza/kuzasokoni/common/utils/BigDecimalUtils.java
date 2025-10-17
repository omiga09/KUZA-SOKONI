package com.kuza.kuzasokoni.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalUtils {

        public static BigDecimal zeroIfNull(BigDecimal value) {
            return value == null ? BigDecimal.ZERO : value;
        }

        public static boolean isPositive(BigDecimal value) {
            return value != null && value.compareTo(BigDecimal.ZERO) > 0;
        }

        public static BigDecimal percentageOf(BigDecimal base, BigDecimal percent) {
            return base.multiply(percent).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        }
    }



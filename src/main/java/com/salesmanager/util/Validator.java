package com.salesmanager.util;

import com.salesmanager.exception.ValidationException;

public final class Validator {
    private Validator() {
    }

    public static void notBlank(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException(fieldName + " không được để trống");
        }
    }
}
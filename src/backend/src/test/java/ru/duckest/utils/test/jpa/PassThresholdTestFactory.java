package ru.duckest.utils.test.jpa;

import ru.duckest.entity.PassThreshold;

public class PassThresholdTestFactory {

    private PassThresholdTestFactory() {
    }

    public static PassThreshold getDummyPassThreshold() {
        return PassThreshold.builder().threshold(80).build();
    }

}

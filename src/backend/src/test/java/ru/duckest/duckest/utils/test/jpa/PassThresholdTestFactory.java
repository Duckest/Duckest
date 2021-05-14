package ru.duckest.duckest.utils.test.jpa;

import ru.duckest.duckest.entity.PassThreshold;

public class PassThresholdTestFactory {

    private PassThresholdTestFactory() {
    }

    public static PassThreshold getDummyPassThreshold() {
        return PassThreshold.builder().threshold(80).build();
    }

}

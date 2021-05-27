package ru.duckest.duckest.utils.test.jpa;

import ru.duckest.duckest.entity.TypeImageUrl;

import static ru.duckest.duckest.utils.test.Constants.DUMMY_IMAGE_URL;

public class LevelTypeImageTestFactory {

    public static TypeImageUrl getDummyImageUrl() {
        return TypeImageUrl.builder().imageUrl(DUMMY_IMAGE_URL).build();
    }

}

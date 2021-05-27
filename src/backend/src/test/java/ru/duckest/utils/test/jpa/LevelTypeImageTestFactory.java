package ru.duckest.utils.test.jpa;

import ru.duckest.entity.TypeImageUrl;
import ru.duckest.utils.test.Constants;

public class LevelTypeImageTestFactory {

    public static TypeImageUrl getDummyImageUrl() {
        return TypeImageUrl.builder().imageUrl(Constants.DUMMY_IMAGE_URL).build();
    }

}

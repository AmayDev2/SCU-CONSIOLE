package com.amay.scu.util;

import javafx.scene.image.Image;

import java.util.Objects;

public class ImageLoaderUtil {
    public static Image loadImage(String path) {
        return new Image(Objects.requireNonNull(ImageLoaderUtil.class.getResource(path)).toExternalForm());
    }
}

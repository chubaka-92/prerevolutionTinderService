package ru.liga.prerevolutionarytinderserver.api;

import java.io.InputStream;

public interface PictureWebService {

    InputStream makePicture(String text);

}

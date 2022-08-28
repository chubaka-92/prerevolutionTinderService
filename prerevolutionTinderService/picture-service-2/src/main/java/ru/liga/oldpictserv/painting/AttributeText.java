package ru.liga.oldpictserv.painting;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.font.TextAttribute;
import java.text.AttributedString;


@Service
@Slf4j
public class AttributeText {
    /**
     * Добавление атрибутов
     */
    public AttributedString getAttributedString(String text, Font mainFont, Color color) {
        log.debug("Added attrib text = {}, font = {}, color = {}", text, mainFont.toString(), color);
        AttributedString attributedText = new AttributedString(text);
        attributedText.addAttribute(TextAttribute.FONT, mainFont);
        attributedText.addAttribute(TextAttribute.FOREGROUND, color);
        return attributedText;
    }
}

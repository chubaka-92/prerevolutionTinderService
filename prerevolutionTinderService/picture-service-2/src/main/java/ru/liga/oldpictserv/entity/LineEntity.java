package ru.liga.oldpictserv.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.liga.oldpictserv.enums.TextType;

import java.awt.*;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextLayout;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Сущность текста
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LineEntity {
    /**
     * Текст
     */
    private String text;
    /**
     * Шрифт текста
     */
    private Font font;
    /**
     * Класс для работы с стилями текста
     */
    private LineBreakMeasurer lineBreakMeasurer;
    /**
     * Слои текста
     */
    private List<TextLayout> textLayoutsList = new LinkedList<>();
    /**
     * Описание текста
     */
    private TextType descriptor;
    /**
     * Высота текста
     */
    private float high;

    public void addTextLayout(TextLayout textLayouts) {
        if (this.textLayoutsList == null) {
            this.textLayoutsList = new ArrayList<>();
            this.high = 0;
        }
        this.textLayoutsList.add(textLayouts);
        this.high += textLayouts.getAscent() + textLayouts.getDescent() + textLayouts.getLeading();
    }
}

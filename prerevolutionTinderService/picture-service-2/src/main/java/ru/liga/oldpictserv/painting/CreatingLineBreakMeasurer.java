package ru.liga.oldpictserv.painting;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.oldpictserv.entity.LineEntity;

import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.text.AttributedString;
import java.util.List;

import static ru.liga.oldpictserv.constant.ConstantUtil.COLOUR_TEXT;

@Service
@Slf4j
public class CreatingLineBreakMeasurer {
    private final AttributeText attributeText;

    @Autowired
    public CreatingLineBreakMeasurer(AttributeText attributeText) {
        this.attributeText = attributeText;
    }

    /**
     * Заполняет сущность линий списком стилей налоеженных на текст
     *
     * @param lineEntityList сущность линий
     * @param frc            класс для корректного изображения
     */
    public void fillLineEntityByLineBreakMeasurer(List<LineEntity> lineEntityList, FontRenderContext frc) {
        for (LineEntity lineEntity : lineEntityList) {
            log.debug("fill line Entity by LineBreakMeasurer. LineEntity={}", lineEntityList);
            AttributedString attributed = attributeText.getAttributedString(lineEntity.getText(), lineEntity.getFont(), COLOUR_TEXT);
            LineBreakMeasurer lineMeasurer = new LineBreakMeasurer(attributed.getIterator(), frc);
            lineEntity.setLineBreakMeasurer(lineMeasurer);
        }
    }
}

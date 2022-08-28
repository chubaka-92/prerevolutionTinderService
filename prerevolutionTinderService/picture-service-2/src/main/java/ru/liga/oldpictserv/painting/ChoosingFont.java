package ru.liga.oldpictserv.painting;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.oldpictserv.entity.LineEntity;

import java.awt.*;
import java.util.List;


/**
 * Выбор шрифта
 */
@Service
@Slf4j
public class ChoosingFont {
    /**
     * Авто подбор шрифта под высоту
     *
     * @param lineEntityList список сущнсотей линий
     * @param heightImage    высота картинки
     */
    public void autoChoosingFont(List<LineEntity> lineEntityList, int heightImage) {
        log.debug("Choose font lineEntityList = {}, heightImage = {}", lineEntityList.toString(), heightImage);
        final Float heightAllText = lineEntityList.stream().map(LineEntity::getHigh).reduce(Float::sum).orElseThrow(RuntimeException::new);
        if (heightImage < heightAllText) {
            for (LineEntity lineEntity : lineEntityList) {
                final Font f = lineEntity.getFont();
                final double heightBasedFontSize = (f.getSize2D() * heightImage) / heightAllText;
                final Font newFont = f.deriveFont(f.getStyle(), (float) heightBasedFontSize);
                log.debug("Chosen font lineEntityList:{}, newFont:{}", lineEntity, newFont);
                lineEntity.setFont(newFont);
            }
        }
        log.debug("Choose font finished");
    }
}



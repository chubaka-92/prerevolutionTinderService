package ru.liga.oldpictserv.painting;

import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.oldpictserv.entity.LineEntity;
import ru.liga.oldpictserv.enums.TextType;

import java.awt.*;
import java.io.IOException;
import java.util.List;

import static ru.liga.oldpictserv.constant.ConstantUtil.OLDSTANDART_BOLD_PATH;
import static ru.liga.oldpictserv.constant.ConstantUtil.OLDSTANDART_REG_PATH;

/**
 * Создание шрифта
 */
@Service
@Slf4j
@Data
public class CreatingFont {
    private Font mainFontB;
    private Font mainFont;

    public CreatingFont() {
        try {
            this.mainFontB = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream(OLDSTANDART_BOLD_PATH));
            this.mainFontB = mainFontB.deriveFont(this.mainFontB.getStyle(), 35f);
            this.mainFontB = mainFontB.deriveFont(this.mainFontB.getStyle(), 50f);
            log.debug(getClass().getClassLoader().getResourceAsStream(OLDSTANDART_BOLD_PATH).toString());
            this.mainFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream(OLDSTANDART_REG_PATH));
            this.mainFont = mainFont.deriveFont(this.mainFont.getStyle(), 35f);
            this.mainFont = mainFont.deriveFont(this.mainFont.getStyle(), 45f);
        } catch (FontFormatException | IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Заполнение сущности линий Шрифтами
     *
     * @param lineEntityList List<LineEntity> список линий
     */
    @SneakyThrows
    public void fillLinesEntityByMainFont(List<LineEntity> lineEntityList) {
        for (LineEntity lineEntity : lineEntityList) {
            if (lineEntity.getDescriptor().equals(TextType.body)) {
                lineEntity.setFont(mainFont);
            }
            if (lineEntity.getDescriptor().equals(TextType.header)) {
                lineEntity.setFont(mainFontB);
            }
        }
    }
}

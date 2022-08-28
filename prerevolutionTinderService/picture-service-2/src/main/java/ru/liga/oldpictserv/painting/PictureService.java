package ru.liga.oldpictserv.painting;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.oldpictserv.entity.LineEntity;
import ru.liga.oldpictserv.entity.TempImageEntity;
import ru.liga.oldpictserv.exception.CreatePictureException;
import ru.liga.oldpictserv.parsetext.ParseText;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static ru.liga.oldpictserv.constant.ConstantUtil.BACKGROUND_PATH;
import static ru.liga.oldpictserv.constant.ConstantUtil.PADDING;

/**
 * Сервис по работе с картинкой
 */
@Service
@Slf4j
public class PictureService {
    private final CreatingFont creatingFont;
    private final ParseText parseText;
    private final CreatingTextLayout creatingTextLayout;
    private final CreatingLineBreakMeasurer creatingLineBreakMeasurer;
    private final ChoosingFont choosingFont;
    private final TempImageEntity tempImageEntity;

    @Autowired
    public PictureService(CreatingFont creatingFont, ParseText parseText, CreatingTextLayout creatingTextLayout, CreatingLineBreakMeasurer creatingLineBreakMeasurer, ChoosingFont choosingFont, TempImageEntity tempImageEntity) {
        this.creatingFont = creatingFont;
        this.parseText = parseText;
        this.creatingTextLayout = creatingTextLayout;
        this.creatingLineBreakMeasurer = creatingLineBreakMeasurer;
        this.choosingFont = choosingFont;
        this.tempImageEntity = tempImageEntity;
    }

    /**
     * Создать картинку
     *
     * @param text текст
     */
    public void createPicture(String text) {
        log.info("Choose background");
        try (
                InputStream inputStream = getClass().getClassLoader().getResourceAsStream(BACKGROUND_PATH);
        ) {
            BufferedImage image = ImageIO.read(inputStream);
            log.info("Add text to picture");
            addTextToPicture(text, image);
            log.info("Added picture");
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", bos);
            tempImageEntity.setBytesImage(bos.toByteArray());
        } catch (IOException | NullPointerException e) {
            log.error(e.getMessage(), e);
            throw new CreatePictureException();
        }
    }

    /**
     * Добавить текст на картинку
     *
     * @param text  текс
     * @param image картинка
     */
    private void addTextToPicture(String text, BufferedImage image) {
        log.info("add to picture where text = {}", text);
        Graphics g = image.getGraphics();
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        FontRenderContext frc = g2d.getFontRenderContext();
        final int breakWidth = image.getWidth() - PADDING;
        final int breakHeight = image.getHeight();
        List<LineEntity> lineEntityList = parseText.getLineEntityListByText(text);
        creatingFont.fillLinesEntityByMainFont(lineEntityList);
        creatingLineBreakMeasurer.fillLineEntityByLineBreakMeasurer(lineEntityList, frc);
        creatingTextLayout.fillLineEntityByTextLayout(lineEntityList, breakWidth);
        choosingFont.autoChoosingFont(lineEntityList, breakHeight);
        creatingLineBreakMeasurer.fillLineEntityByLineBreakMeasurer(lineEntityList, frc);
        creatingTextLayout.fillLineEntityByTextLayout(lineEntityList, breakWidth);
        log.info("Filling lineEntityList was successful. lineEntityList={}", lineEntityList);
        drawLines(g2d, breakHeight, lineEntityList);
        log.info("Drawing was successfully");
        g.dispose();
        g2d.dispose();
    }

    /**
     * Нарисовать линии
     *
     * @param g2d            картинка
     * @param breakHeight    высота картинки
     * @param lineEntityList список сущностей линий
     */
    private void drawLines(Graphics2D g2d, int breakHeight, List<LineEntity> lineEntityList) {
        final float heightText = lineEntityList.stream().map(LineEntity::getHigh).reduce(Float::sum).orElseThrow(RuntimeException::new);
        float y = (breakHeight - heightText) / 2;
        log.debug("Start draw lines.lineEntityList={},image height = {}, heightText={}",
                lineEntityList, breakHeight, heightText);
        for (LineEntity lineEntity : lineEntityList) {
            for (TextLayout textLayout : lineEntity.getTextLayoutsList()) {
                float x = PADDING;
                textLayout.draw(g2d, x, y + textLayout.getAscent());
                y += textLayout.getAscent() + textLayout.getDescent() + textLayout.getLeading();
            }
        }
    }
}

package ru.liga.oldpictserv.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Сущность для временного хранения картинки
 */
@AllArgsConstructor
@Data
@Component
@NoArgsConstructor
public class TempImageEntity {
    private byte[] bytesImage;
}

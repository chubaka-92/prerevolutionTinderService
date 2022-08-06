package ru.liga.prerevolutionarytinderclient.bot.keyboards;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.liga.prerevolutionarytinderclient.types.Buttons;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReplyKeyboardMaker {

    /*
    Кнопки в Любимцах
     */
    public ReplyKeyboardMarkup getFavoriteKeyboard() {
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton(Buttons.LEFT_BUTTON.getButtonName()));
        row1.add(new KeyboardButton(Buttons.MENU_BUTTON.getButtonName()));
        row1.add(new KeyboardButton(Buttons.RIGHT_BUTTON.getButtonName()));

        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(row1);

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        return replyKeyboardMarkup;
    }

    /*
Кнопки при поиске
 */
    public ReplyKeyboardMarkup getSearchKeyboard() {
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton(Buttons.SEARCH_LEFT_BUTTON.getButtonName()));
        row1.add(new KeyboardButton(Buttons.MENU_BUTTON.getButtonName()));
        row1.add(new KeyboardButton(Buttons.SEARCH_RIGHT_BUTTON.getButtonName()));

        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(row1);

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        return replyKeyboardMarkup;
    }

    /*
    Кнопки в Анкете
     */
    public ReplyKeyboardMarkup getProfileKeyboard() {
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton(Buttons.MENU_BUTTON.getButtonName()));
        row1.add(new KeyboardButton(Buttons.MY_PROFILE.getButtonName()));
        row1.add(new KeyboardButton(Buttons.CHANGE_BUTTON.getButtonName()));


        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(row1);

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        return replyKeyboardMarkup;
    }

    /*
     * Конопки выбора пола
     * */
    public ReplyKeyboardMarkup getGenderKeyboard() {
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton(Buttons.SIR.getButtonName()));
        row1.add(new KeyboardButton(Buttons.MADAM.getButtonName()));

        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(row1);

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        return replyKeyboardMarkup;
    }


    /*
     * Конопки выбора предпочтений
     * */
    public ReplyKeyboardMarkup getSearchGenderKeyboard() {
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton(Buttons.GENTLEMEN.getButtonName()));
        row1.add(new KeyboardButton(Buttons.LADIES.getButtonName()));
        row1.add(new KeyboardButton(Buttons.ALL.getButtonName()));

        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(row1);

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        return replyKeyboardMarkup;
    }

    /*
        Кнопки основного меню
     */
    public ReplyKeyboardMarkup getMainMenuKeyboard() {
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton(Buttons.SEARCH_BUTTON.getButtonName()));
        row1.add(new KeyboardButton(Buttons.MY_PROFILE.getButtonName()));
        row1.add(new KeyboardButton(Buttons.FAVORITE_BUTTON.getButtonName()));

        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(row1);

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        return replyKeyboardMarkup;
    }
}

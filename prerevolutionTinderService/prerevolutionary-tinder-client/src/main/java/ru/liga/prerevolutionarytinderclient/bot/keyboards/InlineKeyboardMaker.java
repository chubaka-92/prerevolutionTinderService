package ru.liga.prerevolutionarytinderclient.bot.keyboards;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.liga.prerevolutionarytinderclient.types.Buttons;

import java.util.ArrayList;
import java.util.List;

//
@Component
public class InlineKeyboardMaker {

    /*
     * Конопки выбора пола
     * */
    public InlineKeyboardMarkup getGenderInlineMessageButtons() {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        rowList.add(getButton(Buttons.SIR.getButtonName(), Buttons.SIR.name()));
        rowList.add(getButton(Buttons.MADAM.getButtonName(), Buttons.MADAM.name()));

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    /*
     * Конопки выбора предпочтений
     * */
    public InlineKeyboardMarkup getSearchGenderInlineMessageButtons() {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        rowList.add(getButton(Buttons.GENTLEMEN.getButtonName(), Buttons.GENTLEMEN.name()));
        rowList.add(getButton(Buttons.LADIES.getButtonName(), Buttons.LADIES.name()));
        rowList.add(getButton(Buttons.ALL.getButtonName(), Buttons.ALL.name()));

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    private List<InlineKeyboardButton> getButton(String buttonName, String buttonCallBackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(buttonName);
        button.setCallbackData(buttonCallBackData);

        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(button);
        return keyboardButtonsRow;
    }
}

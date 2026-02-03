import android.content.Context;
import android.content.SharedPreferences;

public class SaveManager {
    private static final String PREF_NAME = "ClickerSave";
    private static final String KEY_CURRENCY = "currency";

    public static void saveGame(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(KEY_CURRENCY, GameManager.getInstance().getCurrency());
        // Сохрани здесь также количество апгрейдов
        editor.apply();
    }

    public static void loadGame(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        long savedCurrency = prefs.getLong(KEY_CURRENCY, 0);
        GameManager.getInstance().setCurrency(savedCurrency);
    }
}

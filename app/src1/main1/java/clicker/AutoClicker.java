import android.os.Handler;
import android.os.Looper;

public class AutoClicker {
    private final GameManager gameManager;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable runnable;
    private final int DELAY = 1000; // 1 секунда

    public AutoClicker() {
        this.gameManager = GameManager.getInstance();
    }

    public void start(final Runnable onTickUpdateUI) {
        runnable = new Runnable() {
            @Override
            public void run() {
                // Добавляем доход от всех купленных авто-кликеров
                long income = gameManager.autoClickerUpgrade.getTotalPower();
                gameManager.addCurrency(income);
                
                // Обновляем UI через колбэк
                onTickUpdateUI.run();
                
                // Зацикливаем
                handler.postDelayed(this, DELAY);
            }
        };
        handler.post(runnable);
    }
}

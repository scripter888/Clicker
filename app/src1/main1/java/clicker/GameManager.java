public class GameManager {
    private static GameManager instance;
    private long currency = 0;
    private long clickPower = 1;
    
    // Список всех апгрейдов
    public Upgrade autoClickerUpgrade;

    private GameManager() {
        // Инициализируем первый апгрейд: имя, цена 10, множитель 1.15, дает 1 ед/сек
        autoClickerUpgrade = new Upgrade("Cursor", 10, 1.15, 1);
    }

    public static synchronized GameManager getInstance() {
        if (instance == null) instance = new GameManager();
        return instance;
    }

    public void addCurrency(long amount) {
        currency += amount;
    }

    public boolean tryBuyUpgrade(Upgrade upgrade) {
        if (currency >= upgrade.getCurrentCost()) {
            currency -= upgrade.getCurrentCost();
            upgrade.buy();
            return true;
        }
        return false;
    }

    public long getCurrency() { return currency; }
    public long getClickPower() { return clickPower; }
}

public class Upgrade {
    private String name;
    private long baseCost;
    private double costMultiplier;
    private int count;
    private long power; // Сколько дает (клика или в сек)

    public Upgrade(String name, long baseCost, double costMultiplier, long power) {
        this.name = name;
        this.baseCost = baseCost;
        this.costMultiplier = costMultiplier;
        this.power = power;
        this.count = 0;
    }

    // Формула: BaseCost * (Multiplier ^ Count)
    public long getCurrentCost() {
        return (long) (baseCost * Math.pow(costMultiplier, count));
    }

    public void buy() {
        count++;
    }

    public long getTotalPower() {
        return count * power;
    }
    
    // Геттеры для UI (названия, количества и т.д.)
}

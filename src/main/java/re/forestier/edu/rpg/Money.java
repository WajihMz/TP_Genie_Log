package re.forestier.edu.rpg;

public class Money {
    private int amount;
    
    public Money() {
        amount = 0;
    }
    
    public void addMoney(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Trying to add a negative amount of money");
        }
        this.amount += amount;
    }
    
    public int getAmount() {
        return amount;
    }
    
    public void removeMoney(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Trying to remove a negative amount of money");
        }
        if (this.amount - amount < 0) {
            throw new IllegalArgumentException("Player can't have a negative money!");
        }
        this.amount -= amount;
    }
}


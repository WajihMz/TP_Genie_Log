package re.forestier.edu;
import re.forestier.edu.classes.Dwarf;
import re.forestier.edu.rpg.AbstractPlayer;

public class Main {
    public static void main(String[] args) {
        AbstractPlayer firstPlayer = new Dwarf("Florian", "Ruzberg de Rivehaute", 200, 0);
        firstPlayer.addMoney(400);

        firstPlayer.addXp(15);
        System.out.println(firstPlayer.toString());
        System.out.println("------------------");
        firstPlayer.addXp(20);
        System.out.println(firstPlayer.toString());
    }
}
package game;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ChooseItem extends Game {
    private ArrayList<Item> items;
    private int numberOfItems;

    public ChooseItem() {
        items = new ArrayList<>();
        numberOfItems = 0;
    }

    public void addItem(String name, String imagePath) throws FileNotFoundException {
        this.items.add(new Item(name, imagePath));
        numberOfItems++;
    }

    public static ChooseItem getChooseItem() throws FileNotFoundException {
        ChooseItem chooseItem = new ChooseItem();

        chooseItem.addItem("compass", "src\\main\\resources\\images\\game\\compass.png");
        chooseItem.addItem("wheat", "src\\main\\resources\\images\\game\\wheat.png");
        chooseItem.addItem("apple", "src\\main\\resources\\images\\game\\apple.png");
        chooseItem.addItem("potato", "src\\main\\resources\\images\\game\\potato.png");
        chooseItem.addItem("book", "src\\main\\resources\\images\\game\\book.png");
        chooseItem.addItem("dog", "src\\main\\resources\\images\\game\\dog.png");
        chooseItem.addItem("pickaxe", "src\\main\\resources\\images\\game\\pickaxe.png");

        return chooseItem;
    }

    public String returnRandomQuestion() {
        int randomIndex = (int) (Math.random() * numberOfItems);
        return items.get(randomIndex).getQuestion();
    }

    public Item returnRandomItem() {
        int randomIndex = (int) (Math.random() * numberOfItems);
        return items.get(randomIndex);
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }
}

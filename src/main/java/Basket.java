import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class Basket {
    private String[] foods;
    private int[] prices;
    private int[] size;

    public Basket() {

    }

    public Basket(String[] foods, int[] prices) {
        this.foods = foods;
        this.prices = prices;
        this.size = new int[foods.length];
    }


    public void addToCart(int productNum, int amount) {
        size[productNum] += amount;
    }

    public void printCart() {
        int totalPrice = 0;
        System.out.println("Список покупок:");
        for (int i = 0; i < foods.length; i++) {
            if (size[i] > 0) {
                int currentPrice = prices[i] * size[i];
                totalPrice += currentPrice;
                System.out.println(foods[i] + " " + (size[i]) + " шт. за " + prices[i] + " руб. и в сумме " + currentPrice + " руб.");
            }
        }
        System.out.println("Итого: " + totalPrice + " руб.");
    }

    public void saveJSON(File file) {
        try (PrintWriter writer = new PrintWriter(file)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(this);
            writer.print(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static Basket loadFromJSONFile(File file) {
        Basket basket = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return basket;
    }
}


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    static File saveFile = new File("basket.json");

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);

        String[] products = {"Хлебцы", "Кофе", "Авокадо"};
        int[] prices = {40, 120, 100};
        int[] prod = new int[3];
        int productNumber = 0;
        int productCount = 0;

        Basket basket = null;
        if (saveFile.exists()) {
            basket = Basket.loadFromJSONFile(saveFile);
        } else {
            basket = new Basket(products, prices);
        }

        System.out.println("Перечень продуктов:");
        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + "." + products[i] + " " + prices[i] + " руб/шт.");
        }

        ClientLog log = new ClientLog();
        while (true) {

            System.out.println("Выберите товар и количество или введите `end`");
            String input = scanner.nextLine();

            if (input.equals("end")) {
                basket.printCart();
                log.exportAsCSV(new File("log.csv"));
                break;
            } else {
                String[] parts = input.split(" ");
                productNumber = Integer.parseInt(parts[0]) - 1; // номер продукта
                productCount = Integer.parseInt(parts[1]); //количество продукта
                prod[productNumber] += productCount;
                basket.addToCart(productNumber, productCount);
                log.log(productNumber, productCount);
                basket.saveJSON(saveFile);
            }
        }
    }
}




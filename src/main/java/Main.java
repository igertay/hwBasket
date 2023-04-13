import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static String[] products = {"Хлебцы", "Кофе", "Авокадо"};
    static int[] prices = {40, 120, 100};

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {

        int[] prod = new int[3];
        int productNumber = 0;
        int productCount = 0;

        SettingsReader settings = new SettingsReader(new File("shop.xml"));
        File loadFile = new File(settings.loadFile);
        File saveFile = new File(settings.saveFile);
        File logFile = new File(settings.logFile);


        Basket basket = createBasket(loadFile, settings.isLoad, settings.loadFormat);
        ClientLog log = new ClientLog();

        System.out.println("Перечень продуктов:");
        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + "." + products[i] + " " + prices[i] + " руб/шт.");
        }

        while (true) {

            System.out.println("Выберите товар и количество или введите `end`");
            String input = scanner.nextLine();

            if (input.equals("end")) {
                if (settings.isLog) {
                    log.exportAsCSV(logFile);
                }
                basket.printCart();
                break;
            } else {
                String[] parts = input.split(" ");
                productNumber = Integer.parseInt(parts[0]) - 1; // номер продукта
                productCount = Integer.parseInt(parts[1]); //количество продукта
                prod[productNumber] += productCount;
                basket.addToCart(productNumber, productCount);
                if (settings.isLog) {
                    log.log(productNumber, productCount);
                }
                if (settings.isSave) {
                    switch (settings.saveFormat) {
                        case "json" -> basket.saveJSON(saveFile);
                        case "txt" -> basket.saveTxt(saveFile);
                    }
                }
            }
        }
    }

    private static Basket createBasket(File loadFile, boolean isLoad, String loadFormat) {
        Basket basket;
        if (isLoad && loadFile.exists()) {
            basket = switch (loadFormat) {
                case "json" -> Basket.loadFromJSONFile(loadFile);
                case "txt" -> Basket.loadFromTxtFile(loadFile);
                default -> new Basket(products, prices);
            };
        } else {
            basket = new Basket(products, prices);
        }
        return basket;
    }
}




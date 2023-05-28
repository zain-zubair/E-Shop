import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import javafx.scene.control.Label;

import java.util.*;

public class ElectronicStoreView extends Pane {

    private ListView<String> popularItems;
    private ListView<String> storeStock;
    private ListView<String> currentCart;
    private Button b1, b2, b3, b4;
    private TextField t1, t2, t3;
    private Label l3;

    private double revenue = 0.0;

    public ListView<String> getStoreStock() {
        return storeStock;
    }
    public ListView<String> getPopularItems() { return popularItems; }
    public ListView<String> getCurrentCart() {
        return currentCart;
    }

    public Button getB1() {
        return b1;
    }

    public Button getB2() { return b2; }
    public Button getB3() { return b3; }
    public Button getB4() { return b4; }

    public Label getL3() {
        return l3;
    }

    public ElectronicStoreView() {

        // set labels
        Label l1 = new Label("Store Summary:");
        l1.relocate(50, 20);
        Label l2 = new Label("Store Stock:");
        l2.relocate(300, 20);
        l3 = new Label("Current Cart: ($" + revenue + ")");
        l3.relocate(600, 20);
        Label l4 = new Label("# Sales:");
        l4.relocate(35, 50);
        Label l5 = new Label("Revenue:");
        l5.relocate(25, 80);
        Label l6 = new Label("$ / Sales:");
        l6.relocate(30, 115);
        Label l7 = new Label("Most Popular Items:");
        l7.relocate(35,145);

        // set text boxes
        t1 = new TextField("0");
        t1.relocate(90,40); t1.setPrefSize(95,30);
        t2 = new TextField("0.00");
        t2.relocate(90,75); t2.setPrefSize(95,30);
        t3 = new TextField("N/A");
        t3.relocate(90,110); t3.setPrefSize(95,30);

        // set list views
        popularItems = new ListView<>();
        popularItems.relocate(10, 170); popularItems.setPrefSize(170, 160);
        storeStock = new ListView<>();
        storeStock.relocate(195, 40); storeStock.setPrefSize(290, 290);
        currentCart = new ListView<>();
        currentCart.relocate(495, 40); currentCart.setPrefSize(290, 290);

        // set buttons
        b1 = new Button("Reset Store");
        b1.relocate(30,340); b1.setPrefSize(145, 50);
        b2 = new Button("Add to Cart");
        b2.relocate(270,340); b2.setPrefSize(145, 50);
        b3 = new Button("Remove from Cart");
        b3.relocate(490,340); b3.setPrefSize(145, 50);
        b4 = new Button("Complete Sale");
        b4.relocate(490+145,340); b4.setPrefSize(145, 50);

        b2.setDisable(true);
        b3.setDisable(true);
        b4.setDisable(true);

        getChildren().addAll(l1, l2, l3, l4, l5, l6, l7, t1, t2, t3,
                popularItems, storeStock, currentCart, b1, b2, b3, b4);

        setPrefSize(800, 400);

    }

    // this is quite a lengthy update function, so I will add comments
    public void update(ElectronicStore store, int selectedButton) {

        // updates the figure which shows the total cost of the cart
        l3.setText("Current Cart: ($" + String.format("%.2f", store.cartValue()) + ")");

        // initialize variables
        Product[] stock = store.getStock();
        Product[] currCart = store.getCurrentCart().toArray(new Product[100]);
        List<String> productNames = new ArrayList<>();
        List<Product> popularNames = new ArrayList<>();
        List<String> cart = new ArrayList<>();

        // add items to names which are displayed and products which are compared to find most popular
        for(int i=0;i<store.getLength(stock);i++) {
            if (stock[i] != null && stock[i].getStockQuantity() != stock[i].getCartQuantity()) {
                productNames.add(stock[i].toString());
                popularNames.add(stock[i]);
            }
        }

        // adds the item to the cart view if it is not already in it along with the quantity
        for(int i=0;i<store.getLength(currCart);i++) {
            if (currCart[i] != null) {
                if (!cart.contains(currCart[i].getCartQuantity() + " x " + currCart[i].toString())){
                    cart.add(currCart[i].getCartQuantity() + " x " + currCart[i].toString());
                }
            }
        }

        // the block of code below is for comparing the products for the most popular list
        Collections.sort(popularNames, new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return Integer.compare(p2.getSoldQuantity(), p1.getSoldQuantity());
            }
        });

        List<String> top3MostPopular = new ArrayList<>();
        for (int i = 0; i < popularNames.size() && top3MostPopular.size() < 3; i++)
            top3MostPopular.add(popularNames.get(i).toString());

        // show items in their appropriate list views
        storeStock.setItems(FXCollections.observableArrayList(productNames));
        popularItems.setItems(FXCollections.observableArrayList(top3MostPopular));
        currentCart.setItems(FXCollections.observableArrayList(cart));

        t1.setText(Integer.toString(store.getSales()));
        t2.setText(Double.toString(store.getRevenue()));
        // formats sales to show only 2 decimals
        t3.setText((String.format("%.2f", store.getRevenue() / store.getSales())));

        // sets the buttons disabled
        b2.setDisable(true);
        b3.setDisable(true);
        b4.setDisable(true);

        // if the button's appropriate view is selected then show button, or if cart is empty for b3
        if (selectedButton == 2) b2.setDisable(false);
        if (selectedButton == 3) b3.setDisable(false);
        if (!cart.isEmpty()) b4.setDisable(false);
    }

}

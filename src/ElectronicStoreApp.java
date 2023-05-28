import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.Iterator;
import java.util.List;

public class ElectronicStoreApp extends Application{

    ElectronicStore store = ElectronicStore.createStore();

    public void start(Stage stage) {
        Pane pane = new Pane();


        ElectronicStoreView view = new ElectronicStoreView();
        pane.getChildren().addAll(view);
        stage.setTitle("Electronic Store Application - " + store.getName());
        stage.setResizable(false);
        stage.setScene(new Scene(pane));
        stage.show();

        view.update(store, 0);

        // updates the second button
        view.getStoreStock().setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) { view.update(store, 2); }
        });

        // updates the third button
        view.getCurrentCart().setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) { view.update(store, 3); }
        });

        view.getPopularItems().setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) { view.update(store, 0); }
        });

        view.getB2().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {

                for(Product p : store.getStock()) {
                    if(p!=null && p.toString().equals(view.getStoreStock().getSelectionModel().getSelectedItem())){
                        store.addToCart(p);
                        p.changeCartQuantity(1);
                    }
                }
                view.update(store, 0);
            }
        });

        view.getB3().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {

                // the iterator here is used to avoid the "ConcurrentModificationException"
                List<Product> currentCart = store.getCurrentCart();
                Iterator<Product> iterator = currentCart.iterator();

                while (iterator.hasNext()) {
                    Product p = iterator.next();
                    if(p!=null && (p.getCartQuantity() + " x " + p).equals(view.getCurrentCart().getSelectionModel().getSelectedItem())) {
                        p.changeCartQuantity(-1);
                    }
                    if (p!=null && p.getCartQuantity() < 1)
                        iterator.remove();
                }

                view.update(store, 0);
            }
        });

        view.getB4().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {

                // the iterator here is used to avoid the "ConcurrentModificationException"
                List<Product> currentCart = store.getCurrentCart();
                Iterator<Product> iterator = currentCart.iterator();

                while (iterator.hasNext()) {
                    Product p = iterator.next();
                    double revenue = 0;
                    revenue += p.getPrice() * p.getCartQuantity();
                    p.sellProduct();
                    store.setRevenue(revenue);

                    iterator.remove();
                }
                store.incrementSales();
                view.update(store, 0);
            }
        });

        view.getB1().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {

                store = ElectronicStore.createStore();
                view.update(store, 0);

            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

}

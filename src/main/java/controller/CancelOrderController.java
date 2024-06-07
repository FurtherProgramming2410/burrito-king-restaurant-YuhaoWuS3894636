package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Model;
import model.Order;
import model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class CancelOrderController {

    private Model model;
    private Stage stage;
    @FXML
    private MenuItem promote;
    @FXML
    private MenuItem home;
    @FXML
    private MenuItem viewProfile; // Corresponds to the Menu item "viewProfile" in HomeView.fxml
    @FXML
    private MenuItem updateProfile; // // Corresponds to the Menu item "updateProfile" in HomeView.fxml
    @FXML
    private MenuItem placeOrder;
    @FXML
    private MenuItem viewOrder;
    @FXML
    private MenuItem collectOrder;
    @FXML
    private MenuItem cancelOrder;
    @FXML
    private MenuItem exportOrder;

    @FXML
    private ListView<String> ordersListView;
    @FXML
    private Button update;


    @FXML
    private TextField orderIdTextField;

    public CancelOrderController(Stage stage, Model model) {
        this.stage = stage;
        this.model = model;
    }

    @FXML
    public void initialize() {
        menubar();
        ordersListView.getItems().clear();
        try {
            User currentUser = model.getCurrentUser();
            if (currentUser != null) {
                ArrayList<Order> orders = model.getUserDao().getOrdersByUsername(currentUser.getUsername());
                for (Order order : orders) {
                    ordersListView.getItems().add(order.toString());  // Customize toString in Order for better display
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("No user is currently logged in.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while retrieving orders: " + e.getMessage());
            alert.showAndWait();
        }
        update.setOnAction(Event -> {
            collectOrder();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/HomeView.fxml"));
                HomeController homeController = new HomeController(stage, model);

                loader.setController(homeController);
                VBox root = loader.load();

                homeController.showStage(root);
                stage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }


    @FXML
    private void collectOrder() {
        String orderIdText = orderIdTextField.getText();
        if (orderIdText.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter an Order ID.");
            alert.showAndWait();
            return;
        }

        try {
            int orderId = Integer.parseInt(orderIdText);
            Order order = model.getUserDao().getOrderById(orderId); // Add this method in your UserDao class
            if (order == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Order not found. Please check the Order ID and try again.");
                alert.showAndWait();
                return;
            }

            if ("collected".equalsIgnoreCase(order.getStatus())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("The order has been collected and cannot be calcelled.");
                alert.showAndWait();
                return;
            }

            boolean updated = model.getUserDao().updateOrderStatus(orderId, "cancelled");
            if (updated) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Order has been marked as cancelled.");
                alert.showAndWait();
                refreshOrderList();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to update order status. Please check the Order ID and try again.");
                alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid Order ID. Please enter a numeric value.");
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while updating the order status: " + e.getMessage());
            alert.showAndWait();
        }
    }


    private void refreshOrderList() {
        ordersListView.getItems().clear();
        initialize();
    }

    public void showStage(Pane root) {
        Scene scene = new Scene(root, 500, 300);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Collect Orders");
        stage.show();
    }
    public void menubar(){

        promote.setOnAction(Event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PromoteView.fxml"));
                PromoteController promoteController = new PromoteController(stage, model);
                loader.setController(promoteController);
                Pane root = loader.load();
                promoteController.initialize();
                stage.setScene(new Scene(root));
                stage.setTitle("Promote");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        viewProfile.setOnAction(event -> {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ProfileView.fxml"));
                ProfileController profileController = new ProfileController(stage, model);
                loader.setController(profileController);
                Pane root = loader.load();
                profileController.initialize();
                stage.setScene(new Scene(root));
                stage.setTitle("Profile");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        home.setOnAction(Event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/HomeView.fxml"));
                HomeController homeController = new HomeController(stage,model);
                loader.setController(homeController);
                Pane root = loader.load();
                homeController.initialize();
                stage.setScene(new Scene(root));
                stage.setTitle("Home");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        placeOrder.setOnAction(event -> {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PlaceOrderView.fxml"));
                PlaceOrderController placeOrderController = new PlaceOrderController(stage,model);
                loader.setController(placeOrderController);
                Pane root = loader.load();
                placeOrderController.initialize();
                stage.setScene(new Scene(root));
                stage.setTitle("Place Order");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        viewOrder.setOnAction(Event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ViewOrderView.fxml"));
                ViewOrderController viewOrderController = new ViewOrderController(stage,model);
                loader.setController(viewOrderController);
                Pane root = loader.load();
                viewOrderController.initialize();
                stage.setScene(new Scene(root));
                stage.setTitle("View Order");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        updateProfile.setOnAction(Event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UpdateProfileView.fxml"));
                UpdateProfileController updateProfileController = new UpdateProfileController(stage,model);
                loader.setController(updateProfileController);
                Pane root = loader.load();
                updateProfileController.initialize();
                stage.setScene(new Scene(root));
                stage.setTitle("Update Profile");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        collectOrder.setOnAction(Event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CollectOrderView.fxml"));
                CollectOrderController collectOrderController = new CollectOrderController(stage,model);
                loader.setController(collectOrderController);
                Pane root = loader.load();
                collectOrderController.initialize();
                stage.setScene(new Scene(root));
                stage.setTitle("Collect Order");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        cancelOrder.setOnAction(Event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CancelOrderView.fxml"));
                CancelOrderController cancelOrderController = new CancelOrderController(stage,model);
                loader.setController(cancelOrderController);
                Pane root = loader.load();
                cancelOrderController.initialize();
                stage.setScene(new Scene(root));
                stage.setTitle("Cancel Order");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        exportOrder.setOnAction(Event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ExportFileView.fxml"));
                ExportFileController exportFileController = new ExportFileController(stage,model);
                loader.setController(exportFileController);
                Pane root = loader.load();
                exportFileController.initialize();
                stage.setScene(new Scene(root));
                stage.setTitle("Export Order");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
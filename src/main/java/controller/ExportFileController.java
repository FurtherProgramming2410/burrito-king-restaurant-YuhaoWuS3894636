package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Model;
import model.Order;
import model.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExportFileController {
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
    private TextField orderIdsTextField;
    @FXML
    private CheckBox itemsCheckBox;
    @FXML
    private CheckBox priceCheckBox;
    @FXML
    private CheckBox timeCheckBox;
    @FXML
    private TextField fileNameTextField;
    @FXML
    private Button exportButton;

    public ExportFileController(Stage stage, Model model) {
        this.stage = stage;
        this.model = model;
    }

    @FXML
    public void initialize() {
        menubar();
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

        exportButton.setOnAction(event -> exportOrders());
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
    private void exportOrders() {
        String orderIdsInput = orderIdsTextField.getText();
        if (orderIdsInput.isEmpty()) {
            showAlert("Input Error", "Please enter order IDs.", Alert.AlertType.WARNING);
            return;
        }

        String[] orderIdsArray = orderIdsInput.split(",");
        List<Integer> orderIds = Stream.of(orderIdsArray)
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        String fileName = fileNameTextField.getText();
        if (fileName.isEmpty()) {
            showAlert("Input Error", "Please enter a file name.", Alert.AlertType.WARNING);
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName(fileName + ".csv");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.append("Order ID, Username");
                if (itemsCheckBox.isSelected()) writer.append(", Burrito Number, Fries Number, Soda Number, Meal Number");
                if (priceCheckBox.isSelected()) writer.append(", Total Price");
                if (timeCheckBox.isSelected()) writer.append(", Waiting Time, Order Time");
                writer.append(", Status\n");

                for (int orderId : orderIds) {
                    Order order = model.getUserDao().getOrderById(orderId); // Implement this method in UserDaoImpl
                    if (order != null) {
                        writer.append(String.valueOf(order.getOrderId())).append(", ").append(order.getUsername());
                        if (itemsCheckBox.isSelected()) writer.append(", ").append(String.valueOf(order.getBurritonumber())).append(", ").append(String.valueOf(order.getFriesnumber())).append(", ").append(String.valueOf(order.getSodanumber())).append(", ").append(String.valueOf(order.getMealnumber()));
                        if (priceCheckBox.isSelected()) writer.append(", ").append(String.valueOf(order.getTotalprice()));
                        if (timeCheckBox.isSelected()) writer.append(", ").append(String.valueOf(order.getWaitingTime())).append(", ").append(order.getOrderTime().toString());
                        writer.append(", ").append(order.getStatus()).append("\n");
                    }
                }

                showAlert("Success", "Orders exported successfully.", Alert.AlertType.INFORMATION);
            } catch (IOException | SQLException e) {
                e.printStackTrace();
                showAlert("Error", "An error occurred while exporting orders: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void showStage(Pane root) {
        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Export Orders");
        stage.show();
    }
}

package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Model;
import model.Order;
import model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderconfirmController {
    private Model model;
    private Stage stage;
    @FXML
    private MenuItem promote;
    @FXML
    private MenuItem home;
    @FXML
    private ListView<String> ordersListView;
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
    private Button confirmButton;

    public OrderconfirmController(Stage parentStage, Model model) {
        this.stage = new Stage();
        this.model = model;
    }

    @FXML
    public void initialize() throws SQLException {
        menubar();
        ordersListView.getItems().clear();

        try {
            User currentUser = model.getCurrentUser();
            if (currentUser != null) {
                ArrayList<User> users = model.getUserDao().getUsersByUsername(currentUser.getUsername());
                for (User user1 : users) {
                    ordersListView.getItems().add(user1.toString());  // Customize toString in Order for better display
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

        confirmButton.setOnAction(actionEvent -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PaymentView.fxml"));
                PaymentController paymentController = new PaymentController(stage,model);
                loader.setController(paymentController);
                Pane root = loader.load();
                paymentController.initialize();
                stage.setScene(new Scene(root));
                stage.setTitle("Place Order");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
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

    private double calculateWaitingTime(User user) {
        double friesAvailable = 0;
        int friesCapacity = 5;
        int waitingTime = 0;
        int friesCookingTime = 0;

        if (user.getFriesnumber() > friesAvailable) {
            friesCookingTime = (int) Math.ceil((double) (user.getFriesnumber() - friesAvailable) / 5) * 8;
            friesAvailable = friesCapacity - (user.getFriesnumber() - friesAvailable);
            while (friesAvailable < 0) {
                friesAvailable += 5;
            }
        } else {
            friesAvailable -= user.getFriesnumber();
        }

        int burritoCookingTime = (int) Math.ceil((double) user.getBurritonumber() / 2) * 9;
        waitingTime = Math.max(friesCookingTime, burritoCookingTime);

        return waitingTime;
    }

    public void showStage(Pane root) {
        Scene scene = new Scene(root, 500, 300);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Home");
        stage.show();
    }
}

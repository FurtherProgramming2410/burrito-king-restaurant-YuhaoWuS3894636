package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Model;
import model.User;
import model.UserandOrder;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class PaymentController {
    private Model model;
    private Stage stage;
    @FXML
    private MenuItem promote;
    @FXML
    private MenuItem home;
    @FXML
    private MenuItem viewProfile;
    @FXML
    private MenuItem updateProfile;
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
    private TextField cardnumber;
    @FXML
    private DatePicker expiredate;

    @FXML
    private TextField cvvnumber;

    @FXML
    private Button confirmButton;

    public PaymentController(Stage parentStage, Model model) {
        this.stage = new Stage();
        this.model = model;
    }

    @FXML
    public void initialize() {
        menubar();
        cardnumber.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 16) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Input Warning");
                alert.setHeaderText(null);
                alert.setContentText("Card number cannot be longer than 16 characters.");
                alert.showAndWait();

                cardnumber.setText(newValue.substring(0, 16));
            }
        });
        cvvnumber.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 3) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Input Warning");
                alert.setHeaderText(null);
                alert.setContentText("Card number cannot be longer than 3 characters.");
                alert.showAndWait();

                cvvnumber.setText(newValue.substring(0, 3));
            }
        });

        confirmButton.setOnAction(actionEvent -> {
            handlePaymentConfirmation();
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
    private void handlePaymentConfirmation() {
        LocalDate selectedDate = expiredate.getValue();
        LocalDate currentDate = LocalDate.now();

        if (selectedDate != null && selectedDate.isBefore(currentDate)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Expired Card");
            alert.setHeaderText(null);
            alert.setContentText("The expiration date has already passed.");
            alert.showAndWait();
        } else {
            try {

                User currentUser = model.getCurrentUser();

                if (currentUser != null) {
                    // Retrieve user data from the database
                    UserandOrder userFromDB = model.getUserDao().getorderUserByUsername(currentUser.getUsername());

                    if (userFromDB != null) {

                        System.out.println("User Details: " + userFromDB.getUsername() + ", " + userFromDB.getBurritonumber() + ", " + userFromDB.getFriesnumber() + ", " + userFromDB.getSodanumber() + ", " + userFromDB.getMealnumber() + ", " + userFromDB.getTotalprice() + ", " + userFromDB.getWaitingtime());


                        model.getUserDao().createOrder(userFromDB.getUsername(), userFromDB.getBurritonumber(), userFromDB.getFriesnumber(), userFromDB.getSodanumber(), userFromDB.getMealnumber(), userFromDB.getTotalprice(), userFromDB.getWaitingtime());

                        // Success alert
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Payment Success");
                        alert.setHeaderText(null);
                        alert.setContentText("Payment has been successfully processed and the order has been placed.");
                        alert.showAndWait();


                        currentUser.clearData();


                        model.setCurrentUser(currentUser);
                    } else {
                        throw new NullPointerException("User not found in the database.");
                    }
                } else {
                    throw new NullPointerException("Current user is not set.");
                }
            } catch (Exception e) {
                e.printStackTrace();

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Payment Error");
                alert.setHeaderText(null);
                alert.setContentText("An error occurred while processing the payment: " + e.getMessage());
                alert.showAndWait();
            }
        }
    }



    public void showStage(Pane root) {
        Scene scene = new Scene(root, 500, 300);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Payment");
        stage.show();
    }
}



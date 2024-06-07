package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Model;
import model.User;
import model.UserandOrder;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class PlaceOrderController {
    @FXML
    private MenuItem promote;
    @FXML
    private MenuItem home;
    @FXML
    private MenuItem viewProfile; // Corresponds to the Menu item "viewProfile" in HomeView.fxml
    @FXML
    private MenuItem updateProfile; // Corresponds to the Menu item "updateProfile" in HomeView.fxml
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
    private TextField burritonumber;
    @FXML
    private TextField friesnumber;
    @FXML
    private TextField sodanumber;
    @FXML
    private TextField mealnumber;
    @FXML
    private Button orderButton;

    @FXML
    private Label status;

    private Stage stage;
    private Model model;

    public PlaceOrderController(Stage stage, Model model) {
        this.stage = stage;
        this.model = model;
    }

    @FXML
    public void initialize() {
        menubar();
        orderButton.setOnAction(event -> {
            try {
                User user = model.getCurrentUser();
                if (user != null) {
                    boolean isVIP = "vip".equalsIgnoreCase(user.getStatus());
                    if (!isVIP){
                        if (!burritonumber.getText().isEmpty() && !friesnumber.getText().isEmpty() && !sodanumber.getText().isEmpty()) {
                            user = model.getUserDao().updatenonevipOrder(
                                    user.getUsername(),
                                    Double.valueOf(burritonumber.getText()) ,

                                    Double.valueOf(friesnumber.getText()),

                                    Double.valueOf(sodanumber.getText())



                            );
                            // Calculate total price
                            double totalprice = 7 * user.getBurritonumber() + 4 * user.getFriesnumber() + 2.5 * user.getSodanumber() - (3 * user.getMealnumber());


                            // Update the database with the new price and waiting time
                            double waitingTime = calculateWaitingTime(user);

                            UserandOrder userAndOrder = new UserandOrder();
                            userAndOrder.setTotalprice(totalprice);
                            userAndOrder.setWaitingTime(waitingTime);

                            user = model.getUserDao().updatePriceandTime(user.getUsername(), waitingTime, totalprice);



                            if (user != null) {
                                //model.setCurrentUser(user);
                                status.setText("Order updated for " + user.getUsername());
                                status.setTextFill(Color.GREEN);


                            } else {
                                status.setText("Cannot update order");
                                status.setTextFill(Color.RED);
                            }
                        } else {
                            status.setText("Empty burrito, fries, or soda number");
                            status.setTextFill(Color.RED);
                        }

                       // model.setCurrentUser(user);
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/OrderconfirmView.fxml"));
                            OrderconfirmController orderconfirmController = new OrderconfirmController(stage, model);

                            loader.setController(orderconfirmController);
                            VBox root = loader.load();

                            orderconfirmController.showStage(root);
                            stage.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    if (isVIP) {
                        if (!burritonumber.getText().isEmpty() && !friesnumber.getText().isEmpty() && !sodanumber.getText().isEmpty()) {
                            user = model.getUserDao().updateOrder(
                                    user.getUsername(),
                                    Double.valueOf(Double.valueOf(burritonumber.getText()) + Double.valueOf(mealnumber.getText())),

                                    Double.valueOf(Double.valueOf(friesnumber.getText()) + Double.valueOf(mealnumber.getText())),

                                    Double.valueOf(Double.valueOf(sodanumber.getText()) + Double.valueOf(mealnumber.getText())),

                                    Double.valueOf(mealnumber.getText())

                            );
                            double totalprice = 7 * user.getBurritonumber() + 4 * user.getFriesnumber() + 2.5 * user.getSodanumber() - (3 * user.getMealnumber());


                            // Update the database with the new price and waiting time
                            double waitingTime = calculateWaitingTime(user);
                            UserandOrder userAndOrder = new UserandOrder();
                            userAndOrder.setTotalprice(totalprice);
                            userAndOrder.setWaitingTime(waitingTime);

                            user = model.getUserDao().updatePriceandTime(user.getUsername(), waitingTime, totalprice);


                            if (user != null) {
                                //model.setCurrentUser(user);
                                status.setText("Order updated for " + user.getUsername());
                                status.setTextFill(Color.GREEN);

                            } else {
                                status.setText("Cannot update order");
                                status.setTextFill(Color.RED);
                            }
                        } else {
                            status.setText("Empty burrito, fries, or soda number");
                            status.setTextFill(Color.RED);
                        }
                        // Calculate total price

                        //model.setCurrentUser(user);

                        // Navigate to Order Confirm View
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/OrderconfirmView.fxml"));
                            OrderconfirmController orderconfirmController = new OrderconfirmController(stage, model);

                            loader.setController(orderconfirmController);
                            VBox root = loader.load();

                            orderconfirmController.showStage(root);
                            stage.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        status.setText("Only VIP users can order meals.");
                        status.setTextFill(Color.RED);
                    }
                } else {
                    status.setText("User not found.");
                    status.setTextFill(Color.RED);
                }


            } catch (SQLException e) {
                status.setText(e.getMessage());
                status.setTextFill(Color.RED);
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
}

package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Model;

import java.io.IOException;

public class HomeController {
	private Model model;
	private Stage stage;
	private Stage parentStage;
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
	private MenuItem logout;
	@FXML
	private MenuItem collectOrder;
	@FXML
	private MenuItem cancelOrder;
	@FXML
	private MenuItem exportOrder;

	public HomeController(Stage parentStage, Model model) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
	}

	@FXML
	public void initialize() {
		menubar();





	}

	public void showStage(Pane root) {
		Scene scene = new Scene(root, 500, 300);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Home");
		stage.show();
	}
	public void menubar(){
		logout.setOnAction(Event -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginView.fxml"));
				LoginController loginController = new LoginController(parentStage, model);
				loader.setController(loginController);
				Pane root = loader.load();
				loginController.showStage(root);
				stage.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
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

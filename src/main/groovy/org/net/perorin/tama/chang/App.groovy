package org.net.perorin.tama.chang

import org.net.perorin.tama.chang.control.MainController
import org.net.perorin.tama.chang.util.Util

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Stage

class App extends Application {

	void start(Stage stage) {

		// FXMLのレイアウトをロード
		FXMLLoader loader = new FXMLLoader(Util.getResourceURL("fxml/MainWindow.fxml"))
		Parent root = (Parent)loader.load()
		MainController controller = (MainController)loader.getController()
		stage.setOnShown({
			controller.handleWindowShowEvent()
		})

		// タイトルセット
		stage.setTitle("玉藻前 ～時間管理～")

		// アイコン設定
		stage.getIcons().add(new Image(Util.getResourceStr("img/icon/black_cat.png")))
		stage.setMinWidth(500)
		stage.setMinHeight(680)
		stage.setMaxWidth(500)
		stage.setMaxHeight(680)
		stage.setMaximized(false)
		stage.setResizable(false)

		// シーン生成
		Scene scene = new Scene(root);

		scene.getStylesheets().add(Util.getResourceStr("css/application.css"));
		stage.setScene(scene);

		stage.show()
	}

	public static void main(String[] args){
		Application.launch(this, args)
	}
}

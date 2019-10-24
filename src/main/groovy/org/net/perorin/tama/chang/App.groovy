package org.net.perorin.tama.chang

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Stage

class App extends Application {

    void start(Stage stage) {

		// FXMLのレイアウトをロード
		Parent root = FXMLLoader.load(Util.getResourceURL("fxml/MainWindow.fxml"));

		// タイトルセット
		stage.setTitle("タマちゃん ～時間管理～");

		// アイコン設定
		stage.getIcons().add(new Image(Util.getResourceStr("img/icon/black_cat.png")))

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

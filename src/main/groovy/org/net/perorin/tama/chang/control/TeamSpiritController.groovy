package org.net.perorin.tama.chang.control;

import org.net.perorin.tama.chang.part.SmoothishScrollpane
import org.net.perorin.tama.chang.util.Util

import com.jfoenix.controls.JFXPasswordField
import com.jfoenix.controls.JFXTextField

import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.scene.control.ScrollPane
import javafx.scene.control.Alert.AlertType
import javafx.stage.DirectoryChooser
import javafx.stage.StageStyle

class TeamSpiritController {

	// スクロールペーン
	@FXML ScrollPane rootScroll

	// メールアドレス
	@FXML JFXTextField mailaddress

	// パスワード
	@FXML JFXPasswordField password

	@FXML
	def initialize() {

		SmoothishScrollpane.setSmoothish(rootScroll, 2)

	}

	@FXML
	def saveAccount_OnMouceClicked() {

		Util.keyEncrypto("aasdfasdfasdfasdf", "asdfasdfasdfasd")

		// 入力チェック
		if(mailaddress.getText() == "" || password.getText() == "") {
			return
		}

		Alert alert = new Alert( AlertType.NONE, "", ButtonType.OK,ButtonType.CANCEL )
		alert.initStyle(StageStyle.UTILITY)
		alert.setTitle("確認")
		alert.getDialogPane().setContentText("パスワードの暗号鍵を作成します。\n保存先を指定してください。")
		ButtonType button = alert.showAndWait().orElse( ButtonType.CANCEL )
		if(button == ButtonType.OK) {
			DirectoryChooser fc = new DirectoryChooser();
			fc.setTitle("フォルダ選択");
			fc.setInitialDirectory(new File(System.getProperty("user.home")));
			File file = fc.showDialog(null);
			if (file != null) {
				System.out.println(file.getPath());
			}
		}
	}

}

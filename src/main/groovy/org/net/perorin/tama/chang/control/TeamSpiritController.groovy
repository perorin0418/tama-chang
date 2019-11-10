package org.net.perorin.tama.chang.control;

import java.awt.Toolkit

import org.net.perorin.tama.chang.part.SmoothishScrollpane
import org.net.perorin.tama.chang.util.SelenideUtil
import org.net.perorin.tama.chang.util.SqlUtil
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

		mailaddress.setText(SqlUtil.getCodeParameter("TeamSpiritMailAddress").Para1)
	}

	@FXML
	def saveAccount_OnMouceClicked() {

		SelenideUtil.login()
		SelenideUtil.getAllJobCode()

		// 入力チェック
		if(mailaddress.getText() == "" || password.getText() == "") {
			return
		}

		// AES暗号鍵取得
		File aesKey = new File(SqlUtil.getCodeParameter("AES-Key-Path").Para1)
		if(!aesKey.exists()) {

			// アラート表示
			Alert alert = new Alert( AlertType.NONE, "", ButtonType.OK,ButtonType.CANCEL )
			alert.initStyle(StageStyle.UTILITY)
			alert.setTitle("確認")
			alert.getDialogPane().setContentText("パスワードの暗号鍵を作成します。\n保存先を指定してください。")
			Toolkit.getDefaultToolkit().beep()
			ButtonType button = alert.showAndWait().orElse( ButtonType.CANCEL )
			if(button == ButtonType.OK) {

				// フォルダー選択表示
				DirectoryChooser fc = new DirectoryChooser();
				fc.setTitle("フォルダ選択");
				fc.setInitialDirectory(new File(System.getProperty("user.home")))
				File file = fc.showDialog(null);
				if (file != null) {
					File dir = new File(Util.execPowerShell("powershell\\FileSystemAccessRule.ps1", ["${file.getAbsolutePath()}"]))
					if(dir.exists()) {
						Util.createKey(dir)
					}
				}
			}
		}

		// メールアドレスをDBに登録
		SqlUtil.setCodeParameter("TeamSpiritMailAddress", [mailaddress.getText()])

		// パスワードを暗号化
		def encryptoPass = Util.encrypt(mailaddress.getText(), password.getText())

		// 暗号化したパスワードをDBに登録
		SqlUtil.setCodeParameter("TeamSpiritPassword", [encryptoPass])
	}

}

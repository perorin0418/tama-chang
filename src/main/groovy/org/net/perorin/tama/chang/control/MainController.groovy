package org.net.perorin.tama.chang.control

import org.net.perorin.tama.chang.Util

import com.jfoenix.controls.JFXButton

import javafx.animation.FadeTransition
import javafx.animation.TranslateTransition
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.control.Control
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.BorderPane
import javafx.scene.layout.Pane
import javafx.scene.shape.Rectangle
import javafx.util.Duration

class MainController{

	// サイドパネルのヘッダー
	@FXML Pane sideHeader

	// サイドパネル
	@FXML BorderPane sidePane

	// ボディのヘッダー
	@FXML Pane centerHeader

	// 時間管理 画像
	@FXML ImageView timeManage_img

	// チームスピリット 画像
	@FXML ImageView teamspirit_img

	// 時間管理 ボタン
	@FXML JFXButton timeManage_btn

	// チームスピリット ボタン
	@FXML JFXButton teamspirit_btn

	// 選択中パネル
	@FXML Pane selectPanel
	@FXML Rectangle selectRec
	def selectRecY

	// ボディ
	@FXML AnchorPane body

	// ボディ一覧
	def bodyMap = [:]
	def TIME_MANAGE_WINDOW = "TimeManageWindow"
	def TEAM_SPIRIT_WINDOW = "TeamSpiritWindow"

	/**
	 * 初期化処理
	 */
	@FXML
	def initialize() {

		// 時間管理
		timeManage_img.setImage(new Image(Util.getResourceStr("img/apply/Programming-Watch-icon.png")))

		// チームスピリット
		teamspirit_img.setImage(new Image(Util.getResourceStr("img/custom/teamspirit.png")))

		// 選択位置
		selectRecY = selectRec.getY();

		// 各画面読み込み
		loadFXML()

		// 時間管理ボディを選択
		changeBody(TIME_MANAGE_WINDOW)

	}

	def handleWindowShowEvent() {

		// ヘッダー情報設定
		headerInit()
	}

	def headerInit() {

		// OSのバージョン確認
		if(System.getProperty("os.name") == "Windows 10") {

			// サイドパネルのヘッダーをグラデーションにする
			sideHeader.setStyle("-fx-background-color: linear-gradient(#FFFFFF, #EFEFF1);")
			centerHeader.setStyle("-fx-background-color: linear-gradient(#FFFFFF, #FAFAFA);")
		}else {

			// サイドパネルのヘッダーを単色にする
			sideHeader.setStyle("-fx-background-color: #EFEFF1;")
			centerHeader.setStyle("-fx-background-color: #FAFAFA;")
		}

		//サイドパネルの幅を設定
		def width = sidePane.localToScene(sidePane.getBoundsInLocal()).getWidth()
		sideHeader.setMinWidth(width - 1)

	}

	def loadFXML() {
		bodyMap[TIME_MANAGE_WINDOW] = FXMLLoader.load(Util.getResourceURL("fxml/"  + TIME_MANAGE_WINDOW + ".fxml"))
		bodyMap[TEAM_SPIRIT_WINDOW] = FXMLLoader.load(Util.getResourceURL("fxml/"  + TEAM_SPIRIT_WINDOW + ".fxml"))
	}

	/**
	 * 時間管理ボタン押下
	 */
	@FXML
	def timeManage_btn_onMouseClicked(){
		selectAnimation(timeManage_btn)
		changeBody(TIME_MANAGE_WINDOW)
	}

	/**
	 * チームスピリットボタン押下
	 */
	@FXML
	def teamspirit_btn_btn_onMouseClicked(){
		selectAnimation(teamspirit_btn)
		changeBody(TEAM_SPIRIT_WINDOW)
	}

	/**
	 * ウィンドウ切り替え
	 *
	 * @param windowName
	 */
	def changeBody(def windowName) {

		headerInit()

		// 現在のウィンドウを取得
		def before = body.getChildren()[0]

		// 現在のウィンドウをフェードアウト
		FadeTransition beforeTransition = new FadeTransition()
		beforeTransition.setDuration(Duration.millis(150))
		beforeTransition.setNode(before)
		beforeTransition.setFromValue(1)
		beforeTransition.setToValue(0)

		// フェードアウト完了後の処理
		beforeTransition.setOnFinished({

			// 現在のウィンドウをクリア
			body.getChildren().clear()

			// 変更後のウィンドウ
			def after  = bodyMap[windowName]

			// 変更後のボディのサイズを最適化
			body.setTopAnchor(after, 0.0);
			body.setRightAnchor(after, 0.0);
			body.setLeftAnchor(after, 0.0);
			body.setBottomAnchor(after, 0.0);

			// 変更後のウィンドウをボディに設定
			body.getChildren().add(after)

			// 変更後のウィンドウをフェードイン
			FadeTransition afterTransition = new FadeTransition()
			afterTransition.setDuration(Duration.millis(150))
			afterTransition.setNode(after)
			afterTransition.setFromValue(0)
			afterTransition.setToValue(1)
			afterTransition.play()
		})
		beforeTransition.play()
	}

	/**
	 * 選択を動かす
	 *
	 * @param node ボタンコントロール
	 */
	def selectAnimation(Control node) {

		// ボタンの位置取得
		def nodePos = node.localToScene(node.getBoundsInLocal());

		TranslateTransition animation = new TranslateTransition(Duration.millis(100), selectRec)
		animation.setFromY(selectRecY)
		animation.setToY(nodePos.getMinY() - 20)
		animation.setFromX(0)
		animation.setToX(0)
		animation.setCycleCount(1)
		animation.setAutoReverse(false)
		animation.play()
		selectRecY = nodePos.getMinY() - 20
	}
}

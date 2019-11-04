package org.net.perorin.tama.chang.control;

import javafx.fxml.FXML
import javafx.scene.control.Label

class TimeManagePopupController {

	@FXML Label registTeamSpirit

	@FXML
	def initialize() {

		registTeamSpirit.setOnMouseClicked({println "hoge"})
	}

}

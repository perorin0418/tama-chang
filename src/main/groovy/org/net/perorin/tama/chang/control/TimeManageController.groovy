package org.net.perorin.tama.chang.control;

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

import org.net.perorin.tama.chang.part.SmoothishScrollpane
import org.net.perorin.tama.chang.util.Util

import com.calendarfx.view.DayView
import com.calendarfx.view.TimeScaleView
import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXComboBox
import com.jfoenix.controls.JFXDatePicker
import com.jfoenix.controls.JFXHamburger
import com.jfoenix.controls.JFXPopup
import com.jfoenix.controls.JFXRippler
import com.jfoenix.controls.JFXTextArea
import com.jfoenix.controls.JFXTextField
import com.jfoenix.controls.JFXTimePicker
import com.jfoenix.controls.JFXTooltip
import com.jfoenix.controls.JFXButton.ButtonType
import com.jfoenix.controls.JFXPopup.PopupHPosition
import com.jfoenix.controls.JFXPopup.PopupVPosition
import com.sun.javafx.charts.Legend

import javafx.animation.Animation
import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.geometry.Insets
import javafx.geometry.Side
import javafx.scene.chart.PieChart
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.control.TextFormatter
import javafx.scene.control.ScrollPane.ScrollBarPolicy
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.BorderPane
import javafx.scene.layout.CornerRadii
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.paint.Paint
import javafx.scene.text.Font
import javafx.util.Duration

class TimeManageController {

	@FXML BorderPane agenda
	@FXML ScrollPane agendaScroll
	@FXML PieChart pieChartContent
	@FXML PieChart pieChartLegend
	@FXML Label clockLabel_H
	@FXML Label clockLabel_C
	@FXML Label clockLabel_M
	@FXML Label dateLabel
	@FXML Label dateDow
	@FXML JFXDatePicker datePick
	@FXML ImageView windIcon
	@FXML Label windLabel
	@FXML ImageView temperatureIcon
	@FXML Label temperatureLabel
	@FXML ImageView precipitationIcon
	@FXML Label precipitationLabel
	@FXML ImageView weatherIcon1
	@FXML ImageView weatherIcon2
	@FXML JFXTimePicker workFrom
	@FXML JFXTimePicker workTo
	@FXML JFXComboBox workCode
	@FXML JFXTextField workTitle
	@FXML JFXTextArea workDetail
	@FXML JFXButton workRec
	@FXML ImageView catComment
	@FXML ImageView catView
	@FXML JFXHamburger burger
	@FXML JFXRippler rippler
	JFXPopup popup;

	@FXML
	def initialize() {

		initWork()
		initClock()
		initAgenda()
		initPieChart()
		initCat()
		initHamburger()
	}

	def initWork() {
		workCode.setItems(FXCollections.observableArrayList("Apple","Orange"))
		workRec.setButtonType(ButtonType.RAISED)
		workRec.setTooltip(new JFXTooltip("チームスピリット"))
		workDetail.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
					@Override
					public void handle(KeyEvent event) {
						if (event.getCode() == KeyCode.TAB) {
							if(!event.isShiftDown()) {
								workRec.requestFocus()
							}
						}
					}
				});
		workDetail.setTextFormatter(new TextFormatter({
			String newStr = it.getText().replace("\t", "");
			it.setText(newStr);
			return it;
		}))
	}

	def initClock() {

		clockLabel_H.setFont(Font.loadFont(Util.getResourceStr("font/Mechanoarc.otf"), 44))
		clockLabel_C.setFont(Font.loadFont(Util.getResourceStr("font/Mechanoarc.otf"), 44))
		clockLabel_M.setFont(Font.loadFont(Util.getResourceStr("font/Mechanoarc.otf"), 44))
		dateLabel.setFont(Font.loadFont(Util.getResourceStr("font/Mechanoarc.otf"), 44))
		dateDow.setFont(Font.loadFont(Util.getResourceStr("font/Mechanoarc.otf"), 24))
		windIcon.setImage(new Image(Util.getResourceStr("img/apply/Astrology-Air-Element-icon.png")))
		windLabel.setFont(Font.loadFont(Util.getResourceStr("font/Mechanoarc.otf"), 20))
		temperatureIcon.setImage(new Image(Util.getResourceStr("img/apply/Science-Temperature-icon.png")))
		temperatureLabel.setFont(Font.loadFont(Util.getResourceStr("font/Mechanoarc.otf"), 20))
		precipitationIcon.setImage(new Image(Util.getResourceStr("img/apply/Industry-Water-icon.png")))
		precipitationLabel.setFont(Font.loadFont(Util.getResourceStr("font/Mechanoarc.otf"), 20))
		weatherIcon1.setImage(new Image(Util.getResourceStr("img/weather-icons-master/wi-cloudy.png")))
		weatherIcon2.setImage(new Image(Util.getResourceStr("img/weather-icons-master/wi-day-sunny.png")))
		workFrom.setDefaultColor(Paint.valueOf("#4059a9"))
		workTo.setDefaultColor(Paint.valueOf("#4059a9"))
		datePick.setDefaultColor(Paint.valueOf("#4059a9"))
		datePick.setFocusTraversable(false)

		dateLabel.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd", new Locale("en","US"))))
		dateDow.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("E", new Locale("en","US"))))
		datePick.valueProperty().addListener(new ChangeListener<LocalDate>() {

					@Override
					public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
						dateLabel.setText(newValue.format(DateTimeFormatter.ofPattern("MM/dd", new Locale("en","US"))))
						dateDow.setText(newValue.format(DateTimeFormatter.ofPattern("E", new Locale("en","US"))))
					}
				})

		Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, {
			Calendar c = Calendar.getInstance()
			clockLabel_H.setText(new SimpleDateFormat("HH").format(c.getTime()))
			if(Integer.parseInt( new SimpleDateFormat("ss").format(c.getTime())) % 2 == 0) {
				clockLabel_C.setText("")
			}else {
				clockLabel_C.setText(":")
			}
			clockLabel_M.setText(new SimpleDateFormat("mm").format(c.getTime()))
		}),new KeyFrame(Duration.seconds(1)));
		clock.setCycleCount(Animation.INDEFINITE);
		clock.play();
	}

	def initAgenda() {

		// スクロールをなめらかに
		SmoothishScrollpane.setSmoothish(agendaScroll);

		// タイムスケール
		TimeScaleView view = new TimeScaleView()

		// 時間割
		DayView day = new DayView()

		// コンテキストメニューを削除
		day.setContextMenuCallback({})
		//		day.setPrefSize(100, 100)

		agenda.setLeft(view)
		agenda.setCenter(day)
	}

	def initPieChart() {

		ObservableList<PieChart.Data> pieChartData =
				FXCollections.observableArrayList(
				new PieChart.Data("Grapefruit", 13),
				new PieChart.Data("Oranges", 25),
				new PieChart.Data("Plums", 10),
				new PieChart.Data("Pears", 22),
				new PieChart.Data("Apple", 30)
				)
		ObservableList<PieChart.Data> pieChartData2 =
				FXCollections.observableArrayList(
				new PieChart.Data("Grapefruit", 13),
				new PieChart.Data("Oranges", 25),
				new PieChart.Data("Plums", 10),
				new PieChart.Data("Pears", 22),
				new PieChart.Data("Apple", 30),
				new PieChart.Data("Apple", 30),
				new PieChart.Data("Apple", 30),
				new PieChart.Data("Apple", 30),
				new PieChart.Data("Apple", 30),
				)
		pieChartContent.setData(pieChartData)
		pieChartContent.setLegendVisible(false)
		pieChartContent.setLabelsVisible(false)
		pieChartContent.setStartAngle(90)
		pieChartLegend.setData(pieChartData2)
		pieChartLegend.setLegendVisible(true)
		pieChartLegend.setLegendSide(Side.LEFT)
		Legend legend = pieChartLegend.getLegend()
		if(legend != null){
			legend.setPrefWidth(228)
			legend.setPrefHeight(pieChartData2.size() * 22 + 12)
			legend.setMaxWidth(228)
			legend.setFocusTraversable(false)
			ScrollPane scrollPane = new ScrollPane(legend);
			SmoothishScrollpane.setSmoothish(scrollPane, 1)
			scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
			scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
			scrollPane.setFocusTraversable(false)
			scrollPane.maxHeightProperty().bind(pieChartLegend.heightProperty());
			pieChartLegend.setLegend(scrollPane);
		}

	}

	def initHamburger(){

		popup = new JFXPopup(FXMLLoader.load(Util.getResourceURL("fxml/TimeManagePopup.fxml")));
		burger.setOnMouseClicked({popup.show(rippler, PopupVPosition.BOTTOM, PopupHPosition.LEFT)})
		burger.getChildren().forEach({
			if(it instanceof StackPane) {
				it.setBackground(new Background(new BackgroundFill(Color.valueOf("#4059a9"), new CornerRadii(5), Insets.EMPTY)))
			}
		})
	}

	def initCat() {

		catComment.setImage(new Image(Util.getResourceStr("img/fukidashi/e0055_0.png")))
		catView.setImage(new Image(Util.getResourceStr("img/icon/bastet_frame.png")))
	}

	@FXML
	def workRec_OnMouseClicked() {

	}


}

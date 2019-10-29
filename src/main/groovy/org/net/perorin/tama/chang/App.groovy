package org.net.perorin.tama.chang

import java.time.LocalDate
import java.time.LocalTime

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Calendar.Style;
import com.calendarfx.view.CalendarView;

import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage

//import javafx.fxml.FXMLLoader
//import javafx.scene.Parent
//import javafx.scene.image.Image
class App extends Application {

    void start(Stage stage) {

		CalendarView calendarView = new CalendarView(); (1)

						Calendar birthdays = new Calendar("Birthdays"); (2)
						Calendar holidays = new Calendar("Holidays");

						birthdays.setStyle(Style.STYLE1); (3)
						holidays.setStyle(Style.STYLE2);

						CalendarSource myCalendarSource = new CalendarSource("My Calendars"); (4)
						myCalendarSource.getCalendars().addAll(birthdays, holidays);

						calendarView.getCalendarSources().addAll(myCalendarSource); (5)

						calendarView.setRequestedTime(LocalTime.now());

						Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
								@Override
								public void run() {
										while (true) {
												Platform.runLater({
														calendarView.setToday(LocalDate.now());
														calendarView.setTime(LocalTime.now());
												});

												try {
														// update every 10 seconds
														sleep(10000);
												} catch (InterruptedException e) {
														e.printStackTrace();
												}

										}
								};
						};

						updateTimeThread.setPriority(Thread.MIN_PRIORITY);
						updateTimeThread.setDaemon(true);
						updateTimeThread.start();

						Scene scene = new Scene(calendarView);
						stage.setTitle("Calendar");
						stage.setScene(scene);
						stage.setWidth(1300);
						stage.setHeight(1000);
						stage.centerOnScreen();
						stage.show();

//		// FXMLのレイアウトをロード
//		FXMLLoader loader = new FXMLLoader(Util.getResourceURL("fxml/MainWindow.fxml"))
//		Parent root = (Parent)loader.load()
//		MainController controller = (MainController)loader.getController()
//		stage.setOnShown({
//			controller.handleWindowShowEvent()
//		})
//
//		// タイトルセット
//		stage.setTitle("タマちゃん ～時間管理～")
//
//		// アイコン設定
//		stage.getIcons().add(new Image(Util.getResourceStr("img/icon/black_cat.png")))
//
//		// シーン生成
//		Scene scene = new Scene(root);
//
//		scene.getStylesheets().add(Util.getResourceStr("css/application.css"));
//		stage.setScene(scene);
//
//        stage.show()
    }

    public static void main(String[] args){
        Application.launch(this, args)
    }
}

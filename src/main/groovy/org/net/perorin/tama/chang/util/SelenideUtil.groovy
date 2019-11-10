package org.net.perorin.tama.chang.util;

import static com.codeborne.selenide.Condition.*
import static com.codeborne.selenide.Selenide.*

import java.text.SimpleDateFormat

import org.net.perorin.tama.chang.data.JobData
import org.openqa.selenium.By

import com.codeborne.selenide.Configuration
import com.codeborne.selenide.SelenideElement

class SelenideUtil {

	static def config() {

		// タイムアウトの時間
		Configuration.timeout = 30000

		// テスト実行後にブラウザを開いたままにする
		Configuration.holdBrowserOpen = true

		// 使用するブラウザ
		Configuration.browser = SqlUtil.getCodeParameter("SelenideBrowser").Para1

		// 失敗時にHTMLを保存しない
		Configuration.savePageSource = false

		// 失敗時にpngを保存しない
		Configuration.screenshots = false
	}

	static def login() {
		SelenideUtil.config()
		open(SqlUtil.getCodeParameter("TeamSpititURL").Para1)

		String mailaddress = SqlUtil.getCodeParameter("TeamSpiritMailAddress").Para1
		$("#username").setValue(mailaddress)
		$("#password").setValue(Util.decrypt(mailaddress, SqlUtil.getCodeParameter("TeamSpiritPassword").Para1))
		$("#Login").click()
	}

	static def getAllJobCode() {
		SelenideUtil.config()

		// 勤務表タブタブクリック
		$(By.className("wt-勤務表")).waitUntil(visible, 10000).click()

		// 現在日付取得
		String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime())

		// 今日の工数実績画面を開く
		//		$("#daylyWorkCell" + date).waitUntil(visible, 10000).click()
		$("#dailyWorkCell2019-11-07").waitUntil(visible, 10000).click()

		// ジョブアサインをクリック
		$("#empWorkPlus").waitUntil(visible, 10000).click()

		// テーブルのtrの数を調べる
		int trSize = $("#empJobRightTable").waitUntil(visible, 10000).find(By.tagName("tbody")).findAll(By.tagName("tr")).size()

		def jobDataList = []
		for(int i = 0; i < trSize; i++) {
			SelenideElement e = $("#empJobRightTableRow" + i).waitUntil(visible, 10000)
			if(e.exists()) {
				JobData jobData = new JobData()
				jobData.date = date
				jobData.code = e.find(By.cssSelector("td :nth-child(2)")).text
				jobData.name = e.find(By.cssSelector("td :nth-child(3)")).text
				jobData.kind = e.find(By.cssSelector("td :nth-child(4)")).find(By.tagName("select")).getValue()
				jobDataList << jobData
			}
		}

		// DBに登録
		SqlUtil.addJobData(jobDataList)
	}


	static def logout() {
		close()
	}

}

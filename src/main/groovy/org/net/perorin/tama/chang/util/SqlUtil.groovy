package org.net.perorin.tama.chang.util;

import groovy.sql.Sql

class SqlUtil{

	static def sql;

	static def getInstance() {
		if(sql == null) {
			def config = Util.getConfig()
			def jdbcDriver = 'org.sqlite.JDBC'
			sql = Sql.newInstance("jdbc:sqlite:" + config.sql.database.path + "", jdbcDriver);
		}
		return sql
	}

	/**
	 * コードパラメーター取得
	 * @param id コードID
	 * @return レコード
	 */
	static def getCodeParameter(def id) {
		return SqlUtil.getInstance().firstRow("select * from CodeParameter where CodeId = ?", ["${id}"])
	}

	/**
	 * コードパラメーター設定
	 * @param id コードID
	 * @param para パラメーター（List）
	 */
	static def setCodeParameter(def id, def para) {
		String sql = "update CodeParameter set "
		for(int i = 1; i <= 8; i++) {
			if(para[i-1] != null) {
				sql += " Para${i} = '${para[i - 1]}'"
			}
		}
		sql += " where CodeId = '${id}'"
		SqlUtil.getInstance().execute(sql)
	}

	static def addJobData(def jobDataList) {
		def dataset = SqlUtil.getInstance().dataSet("JobData")
		jobDataList.each {
			dataset.add(
					Date: it.date,
					Code: it.code,
					Name: it.name,
					Kind: it.kind,
					)
		}
	}
}
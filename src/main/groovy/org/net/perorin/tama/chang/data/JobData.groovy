package org.net.perorin.tama.chang.data;

class JobData {
	String date
	String code
	String name
	String kind

	@Override
	public String toString() {
		return "[date:${date}, code:${code}, name:${name}, kind:${kind}]"
	}
}

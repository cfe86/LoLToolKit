package model.structure;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SoftwareUpdateData {

	private String updateDate;
	private String updateLink;
	private String kb;
	private String updateLinkExe;
	private String kbExe;
	private boolean showMsg;
	private String msg;

	public SoftwareUpdateData(String updateDate, String updateLink, String kb, String updateLinkExe, String kbExe, boolean showMsg, String msg) {
		this.updateDate = updateDate;
		this.updateLink = updateLink;
		this.kb = kb;
		this.updateLinkExe = updateLinkExe;
		this.kbExe = kbExe;
		this.showMsg = showMsg;
		this.msg = msg;
	}
	
	public Date getLastUpdate() throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
		return df.parse(this.updateDate);
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public String getUpdateLink() {
		return updateLink;
	}

	public String getKb() {
		return kb;
	}

	public String getUpdateLinkExe() {
		return updateLinkExe;
	}

	public String getKbExe() {
		return kbExe;
	}

	public boolean isShowMsg() {
		return showMsg;
	}

	public String getMsg() {
		return msg;
	}
}
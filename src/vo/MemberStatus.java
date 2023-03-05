package vo;

public class MemberStatus {
	private String mi_id, ms_status, ms_reason, ms_date; 
	private int ms_idx, ms_self;
	public String getMi_id() {
		return mi_id;
	}
	public void setMi_id(String mi_id) {
		this.mi_id = mi_id;
	}
	public String getMs_status() {
		return ms_status;
	}
	public void setMs_status(String ms_status) {
		this.ms_status = ms_status;
	}
	public String getMs_reason() {
		return ms_reason;
	}
	public void setMs_reason(String ms_reason) {
		this.ms_reason = ms_reason;
	}
	public String getMs_date() {
		return ms_date;
	}
	public void setMs_date(String ms_date) {
		this.ms_date = ms_date;
	}
	public int getMs_idx() {
		return ms_idx;
	}
	public void setMs_idx(int ms_idx) {
		this.ms_idx = ms_idx;
	}
	public int getMs_self() {
		return ms_self;
	}
	public void setMs_self(int ms_self) {
		this.ms_self = ms_self;
	}
	
}

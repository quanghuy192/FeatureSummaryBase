package model;

public class En_VnPOS {

	private String EN_POS;
	private String EN_mean;
	private String VN_POS;
	private String VN_mean;
	
	public En_VnPOS(String eN_POS, String eN_mean, String vN_POS, String vN_mean) {
		super();
		EN_POS = eN_POS;
		EN_mean = eN_mean;
		VN_POS = vN_POS;
		VN_mean = vN_mean;
	}

	public String getEN_POS() {
		return EN_POS;
	}

	public void setEN_POS(String eN_POS) {
		EN_POS = eN_POS;
	}

	public String getEN_mean() {
		return EN_mean;
	}

	public void setEN_mean(String eN_mean) {
		EN_mean = eN_mean;
	}

	public String getVN_POS() {
		return VN_POS;
	}

	public void setVN_POS(String vN_POS) {
		VN_POS = vN_POS;
	}

	public String getVN_mean() {
		return VN_mean;
	}

	public void setVN_mean(String vN_mean) {
		VN_mean = vN_mean;
	}

}

package model;

public class VnPOS {

	private String VN_POS;
	private String VN_mean;

	private final String SEPERATOR_CHAR = "-";

	public VnPOS(String vN_POS, String vN_mean) {
		super();
		VN_POS = vN_POS;
		VN_mean = vN_mean;
	}

	public VnPOS(String string) {
		super();
		String[] items = string.split(SEPERATOR_CHAR);
		System.out.println(items[0]);
		if (items.length >= 2) {
			VN_POS = items[0];
			VN_mean = items[1];
		}
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

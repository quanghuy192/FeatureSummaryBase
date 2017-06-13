package utils;

import java.util.List;

import model.VnPOS;

public class VnPOS_Utils {

	private static List<VnPOS> vnPOSList = new FileGeneralUtils().getVnPOS_ListData();
	private static final String ERROR_MSG = "Not found";

	private VnPOS_Utils() {
	}

	public static List<VnPOS> getVnPOSList() {
		return vnPOSList;
	}

	public static String get_VN_POS_mean(String pos) {

		for (VnPOS vnPOS : vnPOSList) {
			
			if(null == vnPOS){
				return ERROR_MSG;
			}
			
			if(null == pos){
				return ERROR_MSG;
			}
			
			if (pos.equalsIgnoreCase(vnPOS.getVN_POS())) {
				return vnPOS.getVN_mean();
			}
		}
		return ERROR_MSG;
	}
}

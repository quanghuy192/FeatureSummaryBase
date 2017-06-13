package utils;

import java.util.ArrayList;
import java.util.List;

import model.En_VnPOS;

public class En_VnPOS_Utils {

	private static List<En_VnPOS> vnPOSList;
	private static En_VnPOS_Utils posUtils = new En_VnPOS_Utils();
	private static final String ERROR_MSG = "Not found";

	private En_VnPOS_Utils() {
		vnPOSList = new ArrayList<>();
		vnPOSList.add(new En_VnPOS("CC", "(Coordinating conjunction)", "CC", "(các liên từ phối hợp)"));
		vnPOSList.add(new En_VnPOS("CD", "(Cardinal number)", "CD", "(số đếm)"));
		vnPOSList.add(new En_VnPOS("DT", "(Determiner)", "DT", "(từ xác định)"));
		vnPOSList.add(new En_VnPOS("EX", "(Existential)", "V", "(tồn tại)"));
		vnPOSList.add(new En_VnPOS("FW", "(Foreign word)", "FW", "(từ nước ngoài)"));
		vnPOSList.add(new En_VnPOS("IN", "(Preposition)", "IN", "(giới từ)"));
		vnPOSList.add(new En_VnPOS("JJ", "(Adjective)", "A", "(tính từ)"));
		vnPOSList.add(new En_VnPOS("JJR", "(Adjective, comparative)", "A", "(tính từ)"));
		vnPOSList.add(new En_VnPOS("JJS", "(Adjective, superlative)", "A", "(tính từ)"));
		vnPOSList.add(new En_VnPOS("LS", "(List item marker)", "LS", "(danh sách mục đánh dấu)"));
		vnPOSList.add(new En_VnPOS("MD", "(Modal)", "MD", "(dạng)"));
		vnPOSList.add(new En_VnPOS("NN", "(Noun, singular or mass)", "N", "(danh từ)"));
		vnPOSList.add(new En_VnPOS("NNS", "(Noun, plural)", "N", "(danh từ)"));
		vnPOSList.add(new En_VnPOS("NP", "(Proper noun, singular)", "N", "(danh từ)"));
		vnPOSList.add(new En_VnPOS("NPS", "(Proper noun, plural)", "N", "(danh từ)"));
		vnPOSList.add(new En_VnPOS("PDT", "(Predeterminer)", "DT", "(tiền xác định)"));
		vnPOSList.add(new En_VnPOS("POS", "(Possessive ending)", "của", "(hậu sở hữu)"));
		vnPOSList.add(new En_VnPOS("PP", "(Personal pronoun)", "P", "(đại từ nhân xưng)"));
		vnPOSList.add(new En_VnPOS("PP$", "(Possessive pronoun)", "của P", "(đại từ sở hữu)"));
		vnPOSList.add(new En_VnPOS("RB", "(Adverb)", "R", "(trạng từ)"));
		vnPOSList.add(new En_VnPOS("RBR", "(Adverb, comparative)", "R", "(trạng từ)"));
		vnPOSList.add(new En_VnPOS("RBS", "(Adverb, superlative)", "R", "(trạng từ)"));
		vnPOSList.add(new En_VnPOS("RP", "(Particle)", "RP", "(vật nhỏ)"));
		vnPOSList.add(new En_VnPOS("SYM", "(Symbol)", "SYM", "(ký tự)"));
		vnPOSList.add(new En_VnPOS("TO", "(''to'')", "-", "(không có loại từ tương ứng)"));
		vnPOSList.add(new En_VnPOS("UH", "(Interjection)", "UH", "(thán từ)"));
		vnPOSList.add(new En_VnPOS("VB", "(Verb, base form)", "V", "(động từ)"));
		vnPOSList.add(new En_VnPOS("VBD", "(Verb, past tense)", "V", "(động từ)"));
		vnPOSList.add(new En_VnPOS("VBG", "(Verb, gerund or present participle)", "V", "(động từ)"));
		vnPOSList.add(new En_VnPOS("VBN", "(Verb, past participle)", "V", "(động từ)"));
		vnPOSList.add(new En_VnPOS("VBP", "(Verb, non-3rd person singular present)", "V", "(động từ)"));
		vnPOSList.add(new En_VnPOS("VBZ", "(Verb, 3rd person singular present)", "V", "(động từ)"));
		vnPOSList.add(new En_VnPOS("WDT", "(Whdeterminer)", "P", "(từ xác định Wh)"));
		vnPOSList.add(new En_VnPOS("WP", "(Wh-pronoun)", "P", "(đại từ Wh)"));
		vnPOSList.add(new En_VnPOS("WP$", "(Possessive wh-pronoun)", "của P", "(đại từ sở hữu Wh)"));
		vnPOSList.add(new En_VnPOS("WRB", "(Wh-adverb)", "R", "(phó từ trạng thái Wh)"));
	}

	// Singleton Pattern
	private synchronized static En_VnPOS_Utils getInstance() {
		return posUtils;
	}

	public static List<En_VnPOS> getVnPOSList() {
		return vnPOSList;
	}

	public static String get_VN_POS_mean(String pos) {
		for (En_VnPOS vnPOS : vnPOSList) {
			if (pos.equalsIgnoreCase(vnPOS.getVN_POS())) {
				return vnPOS.getVN_mean();
			}
		}
		return ERROR_MSG;
	}

}

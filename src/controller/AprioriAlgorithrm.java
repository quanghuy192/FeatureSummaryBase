package controller;

import java.util.ArrayList;
import java.util.List;

public class AprioriAlgorithrm {

	private List<String> itemList;
	private String[][][] data = { { { "1" }, { "A", "C", "D" } },
								  { { "2" }, { "B", "C", "E" } },
								  { { "3" }, { "A", "B", "C", "E" } },
								  { { "4" }, { "B", "E" } }
								};
	
	public AprioriAlgorithrm() {
		itemList = new ArrayList<>();
	}

	private void generateK_ItemSet() {
		for (int i = 0; i < data.length; i++) {
			String[][] items = data[i];
			
			// generate item list
			generateItemList(items);
			
		}
	}

	private void generateItemList(String[][] items) {
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < items[1].length; j++) {
				if(!itemList.contains(items[1][j])){
					itemList.add(items[1][j]);
				}
			}
		}
	}
	
	public void test(){
		
		// test
		generateK_ItemSet();
		
		for (String s : itemList) {
			System.out.print(s + "-");
		}
	}

}

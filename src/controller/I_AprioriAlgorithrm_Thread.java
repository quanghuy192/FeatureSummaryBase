package controller;

import java.util.ArrayList;
import java.util.List;

import model.I_ComplexArray;

public class I_AprioriAlgorithrm_Thread extends Thread {

	// Delegate Pattern
	interface AprioriAction {
		public void action(List<I_ComplexArray> dataItemsParent);
	}

	AprioriAction apriAction;

	private long id;
	private List<I_ComplexArray> dataItemsParent;
	private int multiThread = 5;

	public I_AprioriAlgorithrm_Thread(AprioriAction apriAction, long id, List<I_ComplexArray> dataItemsParent) {
		super();
		this.apriAction = apriAction;
		this.id = id;

		List<I_ComplexArray> data = new ArrayList<I_ComplexArray>();
		int size = (int) id * (dataItemsParent.size() / 5);
		if (id == 5) {
			size = dataItemsParent.size();
		}
		for (int i = (int) id; i < size; i++) {
			data.add(dataItemsParent.get(i));
		}

		this.dataItemsParent = data;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public void run() {
		super.run();
		apriAction.action(dataItemsParent);
	}
}

package old;

import java.util.ArrayList;
import java.util.List;

import model.I_ComplexArray;

public class I_AprioriItemsChild_Thread extends Thread {

	// Delegate Pattern
	interface AprioriItemsChild {
		public void getItemsChild(List<I_ComplexArray> dataItemsParent);
	}

	AprioriItemsChild apriAction;

	private long id;
	private List<I_ComplexArray> dataItemsParent;
	public static int MULTI_THREAD = 1;

	public I_AprioriItemsChild_Thread(AprioriItemsChild apriAction, long id, List<I_ComplexArray> dataItemsParent) {
		super();
		this.apriAction = apriAction;
		this.id = id;

		List<I_ComplexArray> data = new ArrayList<I_ComplexArray>();
		int start = (int) id * (dataItemsParent.size() / MULTI_THREAD);
		int end = (int) (id + 1) * (dataItemsParent.size() / MULTI_THREAD);
		if (id == MULTI_THREAD - 1) {
			end = dataItemsParent.size();
		}
		for (int i = start; i < end; i++) {
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
		apriAction.getItemsChild(dataItemsParent);
	}
}

package controller;

import java.util.ArrayList;
import java.util.List;

import model.I_ComplexArray;

public class Apriori_FindingSubChild_Thread extends Thread {

	// Delegate Pattern
	interface AprioriFindingSubChild {
		public void findSubChild(List<I_ComplexArray> indicate, I_ComplexArray parent);
	}

	AprioriFindingSubChild apriAction;

	private long id;
	private List<I_ComplexArray> dataItemsChild;
	private I_ComplexArray parent;
	public static int MULTI_THREAD = 200;

	public Apriori_FindingSubChild_Thread(AprioriFindingSubChild apriAction, long id, List<I_ComplexArray> childs,
			I_ComplexArray parent) {
		super();
		this.apriAction = apriAction;
		this.id = id;
		
		if(childs.size() >= MULTI_THREAD) {

			List<I_ComplexArray> data = new ArrayList<I_ComplexArray>();
			int start = (int) id * (childs.size() / MULTI_THREAD);
			int end = (int) (id + 1) * (childs.size() / MULTI_THREAD);
			if (id == MULTI_THREAD - 1) {
				end = childs.size();
			}
			for (int i = start; i < end; i++) {
				data.add(childs.get(i));
			}
			this.dataItemsChild = data;
		}else {
			this.dataItemsChild = childs;
		}

		this.parent = parent;
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
		apriAction.findSubChild(dataItemsChild, parent);
	}
}

package spoon.test.processing;

public class SampleForInsertBefore {

	public SampleForInsertBefore() {
		new Thread() {
		};
	}

	public SampleForInsertBefore(int j) {
		this(j, 0);
		new Thread() {
		};
	}

	public SampleForInsertBefore(int j, int k) {
		super();
		new Thread() {
		};
	}
	
	void method() {
	}

	void method2() {
		new Thread() {
		};
	}

	Thread method3() {
		return new Thread() {
		};
	}
}

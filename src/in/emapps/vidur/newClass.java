package in.emapps.vidur;

public class newClass {

	private Triggers triggers;

	public newClass(Triggers mTriggers) {
		triggers = mTriggers;

	}

	public void calledFromMain() {
		// Do Something
		triggers.showTost("This is Do Something");
	}
}

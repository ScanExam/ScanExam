package fr.istic.tools.scanexam.presenter

class SelectionStateMachine {
	public static final int IDLE = 0
	public static final int CREATE = 1
	public static final int RESIZE = 2
	public static final int MOVE = 3
	public static final int DELETE = 4
	public static final int LOOK = 5
	static int currentState = IDLE

	def static int getState() {
		return currentState
	}

	def static void setState(int state) {
		currentState = state
	}
}

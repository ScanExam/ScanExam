package fr.istic.tools.scanexam

class Test {
	
	def static void main(String... args) {
			val method = typeof(Test).getDeclaredMethod("a", null);
			method.setAccessible(true);
			
	}
	
}
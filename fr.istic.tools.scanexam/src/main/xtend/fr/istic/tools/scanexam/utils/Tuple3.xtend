package fr.istic.tools.scanexam.utils

class Tuple3<T1, T2, T3> {

	public final T1 _1
	public final T2 _2
	public final T3 _3
	
	
	new(T1 v1, T2 v2, T3 v3) {
		_1 = v1
		_2 = v2
		_3 = v3
	}
	
	def static <T1, T2, T3> Tuple3<T1, T2, T3> of(T1 v1, T2 v2, T3 v3) {
		return new Tuple3(v1, v2, v3)
	}
	
	override equals(Object o) {
		if(!(o instanceof Tuple3))
			return false
		else {
			val tuple = o as Tuple3<?, ?, ?>
			return tuple._1 == _1 && tuple._2 == _2 && tuple._3 == _3
		}
	}
	
	override toString() {
		return String.format("Tuple3(%s, %s, %s)", _1.toString, _2.toString, _3.toString)
	}
}

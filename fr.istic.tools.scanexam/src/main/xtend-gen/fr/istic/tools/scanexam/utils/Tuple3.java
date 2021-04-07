package fr.istic.tools.scanexam.utils;

@SuppressWarnings("all")
public class Tuple3<T1 extends Object, T2 extends Object, T3 extends Object> {
  public final T1 _1;
  
  public final T2 _2;
  
  public final T3 _3;
  
  public Tuple3(final T1 v1, final T2 v2, final T3 v3) {
    this._1 = v1;
    this._2 = v2;
    this._3 = v3;
  }
  
  public static <T1 extends Object, T2 extends Object, T3 extends Object> Tuple3<T1, T2, T3> of(final T1 v1, final T2 v2, final T3 v3) {
    return new Tuple3<T1, T2, T3>(v1, v2, v3);
  }
}

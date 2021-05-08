package fr.istic.tools.scanexam.utils;

import com.google.common.base.Objects;

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
  
  @Override
  public boolean equals(final Object o) {
    if ((!(o instanceof Tuple3))) {
      return false;
    } else {
      final Tuple3<?, ?, ?> tuple = ((Tuple3<?, ?, ?>) o);
      return ((Objects.equal(tuple._1, this._1) && Objects.equal(tuple._2, this._2)) && Objects.equal(tuple._3, this._3));
    }
  }
  
  @Override
  public String toString() {
    return String.format("Tuple3(%s, %s, %s)", this._1.toString(), this._2.toString(), this._3.toString());
  }
}

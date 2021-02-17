package fr.istic.tools.scanexam.utils;

@SuppressWarnings("all")
public class Pair<T1 extends Object, T2 extends Object> {
  public final T1 _1;
  
  public final T2 _2;
  
  public Pair(final T1 v1, final T2 v2) {
    this._1 = v1;
    this._2 = v2;
  }
}

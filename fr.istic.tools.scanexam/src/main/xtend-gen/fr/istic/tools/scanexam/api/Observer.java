package fr.istic.tools.scanexam.api;

@SuppressWarnings("all")
public interface Observer<T extends Object> {
  void update(final Subject<T> subject, final T data);
}

package fr.istic.tools.scanexam.api;

import fr.istic.tools.scanexam.api.Subject;

@SuppressWarnings("all")
public interface Observer<T extends Object> {
  void update(final Subject<T> subject, final T data);
}

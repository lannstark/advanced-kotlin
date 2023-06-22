package function;

public interface Filter<T> {
  abstract public boolean predicate(T t);
}

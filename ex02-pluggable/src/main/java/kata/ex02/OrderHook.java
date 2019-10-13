package kata.ex02;

/**
 * @author hakiba
 */
public interface OrderHook<T> {
    void execute(T v);
}

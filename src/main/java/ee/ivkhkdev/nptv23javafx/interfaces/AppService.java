package ee.ivkhkdev.nptv23javafx.interfaces;

import java.util.List;
import java.util.Optional;

public interface AppService<T> {
    Optional<T> add(T t);
    List<T> getList();
}

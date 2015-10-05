package cronin.matt.moovingbits.Data;

import java.util.List;

/**
 * Created by mattcronin on 10/2/15.
 */
public interface CRUDHelper<E> {
    void add(E item);
    List<E> getAll();
    E getItem(int id);
    void delete(E item);
    void update(int id, E item);
}

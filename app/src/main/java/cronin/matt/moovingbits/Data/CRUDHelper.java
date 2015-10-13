package cronin.matt.moovingbits.Data;

import java.util.List;

/**
 * Created by mattcronin on 10/2/15.
        */
public interface CRUDHelper<E> {
    long add(E item);
    List<E> getAll();
    E getItem(long id);
    void delete(long id);
    void update(long id, E item);
}

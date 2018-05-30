import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size;

    public ArrayStorage() {
        this.size = 0;
    }

    void clear() {
        storage = new Resume[10000];
        size = 0;
    }

    void save(Resume r) {
        if (!update(r)) {
            storage[size] = r;
            size++;
        }

   }

   boolean update(Resume r) {
        if (r == null)
            return false;
       for (int i = 0; i < size; i++) {
           if (storage[i].uuid.equals(r.uuid)) {
               storage[i] = r;
               return true;
           }
       }
       return false;
   }


    Resume get(String uuid) {
        for (int j = 0; j < size; j++)
            if (storage[j].uuid.equals(uuid))
                return storage[j];
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < size; i++)
            if (storage[i].uuid.equals(uuid)) {
                System.arraycopy(storage, i+1, storage, i, size - i);
                size--;
                break;
            }
    }


    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    int size() {
        return size;
    }
}

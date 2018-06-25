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
        Arrays.fill(storage, null);
        size = 0;
    }

    void save(Resume r) {
        if (r == null)
            return;
        if (!update(r))
            if (storage.length == size)
                System.out.println("Resume did not saved! Overflow.");
            else {
                storage[size] = r;
                size++;
                return;
            }
   }

   boolean update(Resume r) {
       if (r == null)
           return false;
       for (int i = 0; i < size; i++)
           if (storage[i].uuid.equals(r.uuid)) {
               storage[i] = r;
               return true;
           }
       return false;
   }

    Resume get(String uuid) {
        for (int j = 0; j < size; j++)
            if (storage[j].uuid.equals(uuid))
                return storage[j];
        System.out.println("Resume not found.");
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < size; i++)
            if (storage[i].uuid.equals(uuid)) {
                System.arraycopy(storage, i + 1, storage, i, size - i - 1);
                size--;
                return;
            }
        System.out.println("Resume not found.");
        return;
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

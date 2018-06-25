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
        int index = getIndex(r.uuid);
        if (index != -1) {
            storage[index] = r;
            return;
        }
        if (storage.length == size) {
            System.out.println("Resume did not saved! Overflow.");
            return;
        }
        storage[size] = r;
        size++;
        return;
   }

   void update(Resume r) {
       int index = getIndex(r.uuid);
       if (index == -1) {
           System.out.println("Resume not found.");
           return;
       }
       storage[index] = r;
       return;
   }

    Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("Resume not found.");
            return null;
        }
        return storage[index];
    }

    void delete(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("Resume not found.");
            return;
        }
        storage[index] = storage[size - 1];
        storage[size - 1] = null;
        size--;
        return;
    }

    private int getIndex(String uuid) {
        for (int i = 0; i < size; i++)
            if (storage[i].uuid.equals(uuid))
                return i;
        return -1;
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

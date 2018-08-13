import model.Resume;
import storage.ArrayStorage;
import storage.SortedArrayStorage;
import storage.Storage;

/**
 * Test ru.javawebinar.basejava.storage.storage.AbstractArrayStorage
 */
public class MainTestArrayStorage {
//    static final ArrayStorage ARRAY_STORAGE = new ArrayStorage();
    static final SortedArrayStorage ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume("uuid1", "name1");
        Resume r2 = new Resume("uuid2", "name2");
        Resume r3 = new Resume("uuid3", "name3");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        try {
            System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));
        } catch (Exception e)
        {
            System.out.println("Get dummy: absent");
        };

        printAll();
        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        ARRAY_STORAGE.getAllSorted().stream().forEach(System.out::println);
    }
}
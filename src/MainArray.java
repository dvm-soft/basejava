import model.Resume;
import storage.MapUuidStorage;
import storage.Storage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Test for ru.javawebinar.basejava.storage.storage.AbstractArrayStorage
 */
public class MainArray {
//    private final static Storage ARRAY_STORAGE = new ArrayStorage();
//    private final static Storage ARRAY_STORAGE = new SortedArrayStorage();
    private final static Storage ARRAY_STORAGE = new MapUuidStorage();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Resume r;
        while (true) {
            System.out.print("Введите одну из команд - (list | save fullName | delete uuid | get uuid | update uuid fullName| clear | exit): ");
            String[] params = reader.readLine().trim().toLowerCase().split(" ");
            if (params.length < 1 || params.length > 3) {
                System.out.println("Неверная команда.");
                continue;
            }
            String uuid = null;
            String fullName = null;
            switch (params[0]) {
                case "list":
                    printAll();
                    break;
                case "size":
                    System.out.println(ARRAY_STORAGE.size());
                    break;
                case "save":
                    fullName = params[1].intern();
                    r = new Resume(fullName);
                    ARRAY_STORAGE.save(r);
                    printAll();
                    break;
                case "update":
                    uuid = params[1].intern();
                    fullName = params[2].intern();
                    r = new Resume(uuid, fullName);
                    ARRAY_STORAGE.update(r);
                    printAll();
                    break;
                case "delete":
                    uuid = params[1].intern();
                    ARRAY_STORAGE.delete(uuid);
                    printAll();
                    break;
                case "get":
                    uuid = params[1].intern();
                    System.out.println(ARRAY_STORAGE.get(uuid));
                    break;
                case "clear":
                    ARRAY_STORAGE.clear();
                    printAll();
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Неверная команда.");
                    break;
            }
        }
    }

    static void printAll() {
        List<Resume> all = ARRAY_STORAGE.getAllSorted();
        System.out.println("----------------------------");
        if (all.size() == 0) {
            System.out.println("Empty");
        } else {
            all.stream().forEach(System.out::println);
        }
        System.out.println("----------------------------");
    }
}
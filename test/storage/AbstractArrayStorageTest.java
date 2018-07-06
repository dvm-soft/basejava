package storage;

import exception.NotExistStorageException;
import exception.StorageException;
import model.Resume;
import org.jboss.shrinkwrap.api.asset.Asset;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {

    private Storage storage = new ArrayStorage();
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void size() {
        Assert.assertEquals(3,storage.size());
    }

    @Test
    public void get() {
        Assert.assertTrue(storage.get(UUID_1).equals(new Resume(UUID_1)));
        Assert.assertTrue(storage.get(UUID_2).equals(new Resume(UUID_2)));
        Assert.assertTrue(storage.get(UUID_3).equals(new Resume(UUID_3)));
    }

    @Test
    public void clear() {
        Assert.assertTrue(storage.size() == 3);
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        storage.update(new Resume(UUID_1));
        Assert.assertTrue(storage.get(UUID_1).equals(new Resume(UUID_1)));
        try {
            storage.update(new Resume("dummy"));
        } catch (NotExistStorageException e) {
            return;
        }
        Assert.fail();
    }

    @Test
    public void getAll() {
        Assert.assertEquals(3, storage.getAll().length);
    }

    @Test
    public void save() {
        storage.save(new Resume("dummy"));
        try {
            storage.get("dummy");
        } catch (NotExistStorageException e) {
            Assert.fail();
        }
        Assert.assertEquals(4, storage.size());
        storage.save(new Resume("dummy"));
        Assert.assertEquals(4, storage.size());
    }

    @Test
    public void overflow() {
        storage.clear();
        for (int i = 0; i <= AbstractArrayStorage.STORAGE_LIMIT; i++){
            try {
                storage.save(new Resume("" + i));
            } catch (StorageException e) {
                if(i == (AbstractArrayStorage.STORAGE_LIMIT))
                    Assert.assertTrue(true);
                else
                    Assert.fail();
                return;
            }
        }
        Assert.fail();
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_1);
        Assert.assertEquals(2, storage.size());
        storage.delete(UUID_1);
        Assert.fail();
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception{
        storage.get("dummy");
    }
}
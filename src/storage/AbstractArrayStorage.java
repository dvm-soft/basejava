package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import exception.StorageException;
import model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {

    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }


    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void doUpdate(Resume r, Object index) {
        storage[(Integer) index] = r;
    }

    @Override
    protected void doSave(Resume r, Object index) {
        if (size >= STORAGE_LIMIT)
            throw new StorageException("Storage overflow", r.getUuid());
        else {
            insertElement(r, (Integer) index);
            size++;
        }
    }

    @Override
    protected void doDelete(Object index) {
        fillDeletedElemet((Integer) index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected Resume doGet(Object index) {
        return storage[(Integer) index];
    }

    @Override
    protected boolean isExist(Object index) {
        return (Integer) index >= 0;
    }

    @Override
    public List<Resume> doCopyAll() {
        List<Resume> list = Arrays.asList(Arrays.copyOfRange(storage, 0, size()));
        return list;
    }

    protected abstract Integer getSearchKey(String uuid);
    protected abstract void insertElement(Resume r, int index);
    protected abstract void fillDeletedElemet(int index);

}
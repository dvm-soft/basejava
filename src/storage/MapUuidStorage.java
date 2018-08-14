/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package storage;

import model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage<String> {

    Map<String, Resume> map = new HashMap();

    @Override
    protected void doUpdate(Resume r, String uuid) {
        map.put((String) uuid, r);
    }

    @Override
    protected void doSave(Resume r, String uuid) {
        map.put((String) uuid, r);
    }

    @Override
    protected void doDelete(String uuid) {
        map.remove(uuid);
    }

    @Override
    protected Resume doGet(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected boolean isExist(String searchKey) {
        return map.containsKey(searchKey);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public List<Resume> doCopyAll() {
        return new ArrayList(map.values());
    }

    @Override
    public int size() {
        return map.size();
    }


}

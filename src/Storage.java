public interface Storage {

    public void clear();

    public void update(Resume r);

    public void save(Resume r);

    public Resume get(String uuid);

    public void delete(String uuid);

    public Resume[] getAll();

    public int size();
}

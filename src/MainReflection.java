import model.Resume;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException {
        Resume r = new Resume();
        System.out.println(r);
        Field field = r.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName() + ' ' + field.get(r));
        field.set(r, "new_UUID");
        //TODO: invoke r.toString via reflection

        System.out.println(field.getName() + ' ' + field.get(r));

//        Annotation annotation = r.getClass().getAnnotations()[0];
//        System.out.println(annotation);


    }
}

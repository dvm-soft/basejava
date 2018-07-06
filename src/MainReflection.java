import model.Resume;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume r = new Resume();
        System.out.println(r);

        Field field = r.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName() + ' ' + field.get(r));

        field.set(r, "new_UUID");
        System.out.println(field.getName() + ' ' + field.get(r));

        //TODO: invoke r.toString via reflection
        Method method = r.getClass().getDeclaredMethod("toString", null);
        System.out.println(method.invoke(r, null));
//        Annotation annotation = r.getClass().getAnnotations()[0];
//        System.out.println(annotation);


    }
}

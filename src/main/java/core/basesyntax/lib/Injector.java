package core.basesyntax.lib;

import core.basesyntax.exception.NoAnnotationsExceptions;
import core.basesyntax.factory.FactoryBet;
import core.basesyntax.factory.FactoryFruit;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class Injector {
    public static Object getInstance(Class clazz) throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException,
            InstantiationException, NoAnnotationsExceptions {
        Constructor constructor = clazz.getConstructor();
        Object instance = constructor.newInstance();

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.getAnnotation(Inject.class) != null) {
                field.setAccessible(true);
                field.set(instance, FactoryBet.getBetDao());
            } else if (field.getAnnotation(Dao.class) != null) {
                field.setAccessible(true);
                field.set(instance, FactoryFruit.getFruitDao());
            } else {
                throw new NoAnnotationsExceptions("Didn't found annotations");
            }
        }
        return instance;
    }
}

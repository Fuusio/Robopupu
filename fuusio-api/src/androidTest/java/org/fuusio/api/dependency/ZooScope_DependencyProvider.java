package org.fuusio.api.dependency;

public class ZooScope_DependencyProvider extends DependencyProvider {

    @SuppressWarnings("unchecked")
    @Override
    protected <T> T getDependency(final Class<T> dependencyType) {
        if (type(Elephant.class, dependencyType)) {
            return (T)new Elephant();
        } else if (type(Lion.class, dependencyType)) {
            return (T)new Lion();
        } else if (type(Monkey.class, dependencyType)) {
            final Banana banana = D.get(Banana.class);
            return (T)new Monkey(banana);
        }
        return null;
    }

    private final boolean type(Class<?> providedClass, final Class<?> dependencyType) {
        return dependencyType.isAssignableFrom(providedClass);
    }
}

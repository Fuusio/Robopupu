package com.robopupu.api.dependency;

public class ZooScope_DependencyProvider extends DependencyProvider {

    @SuppressWarnings("unchecked")
    @Override
    protected <T> void getDependencies(final DependencyQuery<T> query) {

        if (query.matches(Elephant.class, Elephant.class)) {
            if (query.add((T)new Elephant())) {
                return;
            }
        }

        if (query.matches(Lion.class, Lion.class)) {
            if (query.add((T)new Lion())) {
                return;
            }
        }

        if (query.matches(Monkey.class, Monkey.class)) {
            final Banana banana = D.get(Banana.class);
            if (query.add((T)new Monkey(banana))) {
                return;
            }
        }
    }

    private final boolean type(Class<?> providedClass, final Class<?> dependencyType) {
        return dependencyType.isAssignableFrom(providedClass);
    }
}

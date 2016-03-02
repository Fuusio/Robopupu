package org.fuusio.api.dependency;

public class ZooAppScope extends AppDependencyScope<ZooApp> {

    public ZooAppScope(ZooApp app) {
        super(app);
    }

    @Override
    protected <T> T getDependency() {

        if (type(ZooApp.class)) {
            return (T)getApplication();
        } else if (type(Zoo.class)) {
            return dependency(new MyLittleZoo());
        } else {
            return super.getDependency();
        }
    }
}

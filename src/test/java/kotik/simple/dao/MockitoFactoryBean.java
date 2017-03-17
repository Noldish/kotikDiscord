//package kotik.simple.dao;
//
//import org.springframework.beans.factory.FactoryBean;
//
//import static org.mockito.Mockito.mock;
//
///**
// * Created by Roman_Kuznetcov on 17.11.2016.
// */
//public class MockitoFactoryBean<T> implements FactoryBean<T> {
//    private final Class<T> clazz;
//
//    public MockitoFactoryBean(Class<T> clazz) {
//        this.clazz = clazz;
//    }
//
//    @Override public T getObject() throws Exception {
//        return mock(clazz);
//    }
//
//    @Override public Class<T> getObjectType() {
//        return clazz;
//    }
//
//    @Override public boolean isSingleton() {
//        return true;
//    }
//}

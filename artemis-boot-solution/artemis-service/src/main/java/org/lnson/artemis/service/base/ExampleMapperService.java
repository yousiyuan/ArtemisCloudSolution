package org.lnson.artemis.service.base;

import org.lnson.artemis.exception.UnimplementedException;

import java.io.Serializable;
import java.util.List;

public interface ExampleMapperService<T extends Serializable> {
    public default int deleteByExample(Class<? extends T> clazz, Object... arguments) {
        UnimplementedException unimplementedException = new UnimplementedException("an unimplemented method was invoked");
        unimplementedException.setStackTrace(Thread.currentThread().getStackTrace());
        throw unimplementedException;
    }

    public default List<T> selectByExample(Class<? extends T> clazz, Object... arguments) {
        UnimplementedException unimplementedException = new UnimplementedException("an unimplemented method was invoked");
        unimplementedException.setStackTrace(Thread.currentThread().getStackTrace());
        throw unimplementedException;
    }

    public default int selectCountByExample(Class<? extends T> clazz, Object... arguments) {
        UnimplementedException unimplementedException = new UnimplementedException("an unimplemented method was invoked");
        unimplementedException.setStackTrace(Thread.currentThread().getStackTrace());
        throw unimplementedException;
    }

    public default int updateByExample(Class<? extends T> clazz, Object... arguments) {
        UnimplementedException unimplementedException = new UnimplementedException("an unimplemented method was invoked");
        unimplementedException.setStackTrace(Thread.currentThread().getStackTrace());
        throw unimplementedException;
    }

    public default int updateByExampleSelective(Class<? extends T> clazz, Object... arguments) {
        UnimplementedException unimplementedException = new UnimplementedException("an unimplemented method was invoked");
        unimplementedException.setStackTrace(Thread.currentThread().getStackTrace());
        throw unimplementedException;
    }
}

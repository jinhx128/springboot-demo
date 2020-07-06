package com.jinhaoxun.exception.exception;

import java.util.function.Consumer;

/**
 * @version 1.0
 * @author jinhaoxun
 * @date 2019-05-09
 * @description Lambda表达式的异常处理
 */
@FunctionalInterface
public interface LambdaThrowingConsumer<T, E extends Exception> {

    void accept(T t) throws E;

    /**
     * @author jinhaoxun
     * @description 从Lambda表达式中抛出非Checked异常
     * @param lambdaThrowingConsumer lambda表达式
     * @return T
     */
    static <T> Consumer<T> throwingConsumerWrapper(
            LambdaThrowingConsumer<T, Exception> lambdaThrowingConsumer) {
        return i -> {
            try {
                lambdaThrowingConsumer.accept(i);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    /**
     * @author jinhaoxun
     * @description 从Lambda表达式中抛出Checked异常
     * @param lambdaThrowingConsumer lambda表达式
     * @param exceptionClass 具体Checked异常
     * @return T
     */
    static <T, E extends Exception> Consumer<T> handlingConsumerWrapper(
            LambdaThrowingConsumer<T, E> lambdaThrowingConsumer, Class<E> exceptionClass) {
        return i -> {
            try {
                lambdaThrowingConsumer.accept(i);
            } catch (Exception ex) {
                try {
                    E exCast = exceptionClass.cast(ex);
                    System.err.println(
                            "Exception occured : " + exCast.getMessage());
                } catch (ClassCastException ccEx) {
                    throw new RuntimeException(ex);
                }
            }
        };
    }
}

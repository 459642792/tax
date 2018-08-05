package com.blueteam.base.exception;


/**
 * 带消息的异常
 *
 * @author nastynas   2017.8.11
 */
public class MessageException extends BaseException {
    private static final long serialVersionUID = -6437547090874832816L;

    /**
     * ctor.
     *
     * @param message 异常消息
     * @param cause   异常致因
     */
    public MessageException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * ctor.
     *
     * @param message 异常消息
     */
    public MessageException(String message) {
        super(message);
    }
}

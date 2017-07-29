package balber.reactive.mongo_toturial;

import static java.lang.String.format;

public class PrintSubscriber<T> extends OperationSubscriber<T> {
    private final String message;

    /**
     * A Subscriber that outputs a message onComplete.
     *
     * @param message the message to output onComplete
     */
    public PrintSubscriber(final String message) {
        this.message = message;
    }

    @Override
    public void onComplete() {
        System.out.println(format(message, getReceived()));
        super.onComplete();
    }
}



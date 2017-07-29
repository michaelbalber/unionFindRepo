package balber.reactive.mongo_toturial;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import com.mongodb.reactivestreams.client.Success;

public  class OperationSubscriber<T> extends ObservableSubscriber<T> {

    @Override
    public void onSubscribe(final Subscription s) {
        super.onSubscribe(s);
        s.request(Integer.MAX_VALUE);
    }
}

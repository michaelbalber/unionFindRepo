package balber.reactive.mongo_toturial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import com.mongodb.reactivestreams.client.Success;

public  class OperationSubscriber<T> extends ObservableSubscriber<T> {

    @Override
    public void onSubscribe(final Subscription s) {
        super.onSubscribe(s);
        s.request(Integer.MAX_VALUE);
        
    }
    public static int getCount(String str) {
        int vowelsCount = 0;
        // your code here

       List<Character> vowels = Arrays.asList(new Character[]{'a','e','i','o'});
       vowelsCount = (int) str.chars().
    	mapToObj(c -> (char) c).
       	filter(x-> vowels.contains(x)).
       	count();
       return vowelsCount;
      }
}

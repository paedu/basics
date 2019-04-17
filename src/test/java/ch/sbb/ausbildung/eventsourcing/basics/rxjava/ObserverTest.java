package ch.sbb.ausbildung.eventsourcing.basics.rxjava;

import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.TestObserver;
import org.junit.jupiter.api.Test;

/**
 * Testing reactive operators on observables (i.e. Flux, Mono etc.)
 *
 * @see <a href="https://github.com/ReactiveX/RxJava">RxJava</a>
 * @see <a href="https://projectreactor.io/">Project Reactor (i.e. Spring 5, Webflux)</a>
 *
 */
class ObserverTest {

    @Test
    void square_lessThan50_addAllValues() {
        final TestObserver<Integer> subscriber = new TestObserver<>();

        Observable.range(0, 10)
                // TODO: square value (x^2)
                // TODO: take only values < 50
                // TODO: log the current values
                // TODO: sum up all values into a single result
                // TODO: log the result (element)
                .subscribe(subscriber);

        // assertions should be satisfied if all is right
        subscriber.assertComplete();
        subscriber.assertNoErrors();
        subscriber.assertValueCount(1);
        subscriber.assertValue(140);
    }

    @Test
    void test_02() {
        final TestObserver<Integer> subscriber = new TestObserver<>();
        final Observable<Integer> zero2Nine = Observable.range(0, 10);

        Observable.range(10, 10)
                // TODO: concat or merge the 2 observables
                // TODO: log elements
                // TODO...??
                .subscribe(subscriber);

        // TODO: at the end, the assertions should be met
        subscriber.assertComplete();
        subscriber.assertNoErrors();
        subscriber.assertValueCount(20);
        subscriber.assertValues(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19);

    }

    // Helper Lambdas (logging)
    private static Consumer<Notification> logElements = (Notification not) ->
            System.out.println("Element: " + (not.isOnComplete() ? "completed" :
                    not.isOnError() ? "Error" : not.getValue()));

    private static BiConsumer<Integer, Throwable> logElement = (Integer in, Throwable thr) -> {
        if (thr != null) {
            System.err.println("Frror at element: " + thr);
        } else {
            System.out.println("Element: " + in);
        }
    };

}

package ch.sbb.ausbildung.eventsourcing.basics.streams;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.OverflowStrategy;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Source;
import io.reactivex.Flowable;
import io.reactivex.subscribers.TestSubscriber;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

/**
 * Implementation of a <a href="">Reactive Streams</a> (Akka Streams)
 */
class StreamingTest {

    @Test
    void streaming_backpressure() {
        ActorSystem system = ActorSystem.create("testing-streams");
        final ActorMaterializer materializer = ActorMaterializer.create(system);

        Source.range(0, 10)
        // TODO: add string "Element: " in front of value
        // TODO: log elements
        // TODO: only 1 element per second allowed!
        // TODO: let the source passing via flow() (via a processor)
        // TODO: run this source by connecting it to an appropriate sink
        ; // TODO: await the result.
    }


    // flow with input buffer size of 2 and backpressure
    private Flow<String, String, NotUsed> flow() {
        return Flow.<String, String>fromFunction(in -> in)
                .buffer(2, OverflowStrategy.backpressure());
        // TODO (2): only 2 elements every 3 seconds allowed!;
    }

    // *** Publisher / Subscriber from various implementations *** //

    // publisher / subscriber from "rxjava" / "reactor"
    private static Publisher<Integer> rxjavaPublisher() {
        return Flowable.range(0, 10);
    }

    private static Publisher<Integer> reactorPublisher() {
        return Flux.range(0, 10);
    }

    // pub / sub from "reactor"
    private static Subscriber<Integer> rxjavaSubscriber() {
        return new TestSubscriber<>();
    }

    private static Subscriber<Integer> reactorSubscriber() {
        return new BaseSubscriber<Integer>() {
            private Subscription subscription;

            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                this.subscription = subscription;
                subscription.request(100); // request next 100 elements
            }
        };
    }
}

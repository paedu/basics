package ch.sbb.ausbildung.eventsourcing.basics.actors;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import scala.Immutable;

public class SumActor extends AbstractLoggingActor {

    private int counter;

    static Props props() {
        return Props.create(SumActor.class, SumActor::new);
    }

    @Override
    public void preStart() throws Exception {
        log().info("SumActor starting..");
        super.preStart();
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Integer.class, this::handleMessage)
                .match(Message.GetState.class, this::replyCurrentState)
                .build();
    }

    private void replyCurrentState(Message.GetState request) {
        sender().tell(counter, self());
    }

    private void handleMessage(Integer input) {
        counter += input;
    }

    // Message classes (sent to this actor)
    interface Message extends Immutable {
        // msg used to retrieve the current state of this actor
        class GetState implements Message {
            static final GetState INSTANCE = new GetState();
        }
    }

}

package ch.sbb.ausbildung.eventsourcing.basics.actors;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class MainApp {

    public static void main(final String[] args) {
        // bootstrapping actor system
        ActorSystem system = ActorSystem.create("actor-test");

        // create the "sum actor" and keep the actorRef in order to send messages to him
        final ActorRef sumActor = system.actorOf(SumActor.props(), "sumActor");

        // send 2 msg to "sum actor" (fire 'n' forget)
        // TODO: send a 3 to the sum-sctor
        // TODO: send a 2 to the sum actor

        // get current state of sum actor (via req / resp, use "Patterns")
        // TODO: send a "GetState" request to the sum actor


        // ** message passing via 2nd actor **

        // create calling actor
        final ActorRef callingActor = system.actorOf(CallingActor.props(sumActor), "callingActor");

        // send messages via calling actor to the sum actor
        // TODO: send a "7"
        // TODO: send a "3"

        // now, get current state (send "GetState" request and get current state back as response)
        // TODO: get current state from sum actor (via calling actor)
    }


    // another 2nd actor used to send msg to the sum actor and handle replies (ping pong)
    private static class CallingActor extends AbstractLoggingActor {

        private final ActorRef sumActor;

        private static Props props(ActorRef sumActor) {
            return Props.create(CallingActor.class, () -> new CallingActor(sumActor));
        }

        CallingActor(ActorRef sumActor) {
            this.sumActor = sumActor;
        }

        @Override
        public void preStart() throws Exception {
            log().info("CallingActor starting..");
            super.preStart();
        }

        @Override
        public Receive createReceive() {
            return receiveBuilder()
                    .match(String.class, this::forwardToSumActor)
                    .match(SumActor.Message.GetState.class, this::forwardToSumActor)
                    .match(Integer.class, this::handleReply)
                    .matchAny(this::unhandled)
                    .build();
        }

        private void forwardToSumActor(SumActor.Message.GetState request) {
            sumActor.forward(request, context());
        }

        private void forwardToSumActor(String message) {
            sumActor.forward(Integer.parseInt(message), context());
        }

        private void handleReply(Integer reply) {
            log().info("Reply from sum actor: {}", reply);
        }
    }
}

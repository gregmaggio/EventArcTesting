/**
 * 
 */
package ca.datamagic.event;

import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PubsubMessage;

/**
 * @author gregm
 *
 */
public class ReceiveJsonMessage {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String projectId = "api-project-378578942759";
			String subscriptionId = "events";
			
			MessageReceiver receiver = new MessageReceiver() {				
				@Override
				public void receiveMessage(PubsubMessage message, AckReplyConsumer consumer) {
					String json = message.getData().toStringUtf8();
					System.out.println("json: " + json);
				}
			};
			ProjectSubscriptionName projectSubscriptionName = ProjectSubscriptionName.of(projectId, subscriptionId);
			Subscriber subscriber = Subscriber.newBuilder(projectSubscriptionName, receiver).build();
			/*
			 subscriber.addListener(new Subscriber.Listener() {
			   public void failed(Subscriber.State from, Throwable failure) {
			     // Handle error.
			   }
			 }, executor);
			 */
			 subscriber.startAsync();
			 Thread.sleep(10000);
			 subscriber.stopAsync();
		} catch (Throwable t) {
			System.out.println("Throwable: " + t.getMessage());
			t.printStackTrace();
		}
	}

}

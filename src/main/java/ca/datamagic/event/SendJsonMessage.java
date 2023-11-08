/**
 * 
 */
package ca.datamagic.event;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;

import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;

/**
 * @author gregm
 *
 */
public class SendJsonMessage {

	public static void sendMessage(String projectId, String topicId, String message) throws IOException, ExecutionException, InterruptedException {
		System.out.println("projectId: " + projectId);
		System.out.println("topicId: " + topicId);
		System.out.println("message: " + message);
		TopicName topicName = TopicName.of(projectId, topicId);
		Publisher publisher = Publisher.newBuilder(topicName).build();
		try {
			ByteString data = ByteString.copyFromUtf8(message);
			PubsubMessage pubsubMessage = PubsubMessage.newBuilder()
					.setData(data)
					.build();
			ApiFuture<String> messageIdFuture = publisher.publish(pubsubMessage);
			String messageId = messageIdFuture.get();
			System.out.println("messageId: " + messageId);
		} finally {
			publisher.shutdown();
	        publisher.awaitTermination(1, TimeUnit.MINUTES);
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String projectId = "api-project-378578942759";
		    String topicId = "events";
		    JSONObject obj = new JSONObject();
		    obj.append("test", "value");
		    String json = obj.toString();
		    sendMessage(projectId, topicId, json);
		} catch (Throwable t) {
			System.out.println("Throwable: " + t.getMessage());
			t.printStackTrace();
		}
	}

}

/**
 * 
 */
package ca.datamagic.event;

import java.io.IOException;

import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.pubsub.v1.ProjectName;
import com.google.pubsub.v1.PushConfig;
import com.google.pubsub.v1.Subscription;
import com.google.pubsub.v1.SubscriptionName;
import com.google.pubsub.v1.TopicName;

/**
 * @author gregm
 *
 */
public class Subscriptions {

	public static void listSubscriptions(String projectId) throws IOException {
		System.out.println("projectId: " + projectId);
		SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create();
		try {
			ProjectName projectName = ProjectName.of(projectId);
			for (Subscription subscription : subscriptionAdminClient.listSubscriptions(projectName).iterateAll()) {
				System.out.println(subscription.getName());
			}
			System.out.println("Listed all subscriptions.");
		} finally {
			subscriptionAdminClient.close();
		}
	}
	
	public static void createSubscription(String projectId, String subscriptionId, String topicId) throws IOException {
		System.out.println("projectId: " + projectId);
		System.out.println("subscriptionId: " + subscriptionId);
		System.out.println("topicId: " + topicId);
		SubscriptionName subscriptionName = SubscriptionName.of(projectId, subscriptionId);
		TopicName topicName = TopicName.of(projectId, topicId);
		SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create();
		try {
			PushConfig pushConfig = PushConfig.newBuilder().build();
			int ackDeadlineSeconds = 10;
			subscriptionAdminClient.createSubscription(subscriptionName, topicName, pushConfig, ackDeadlineSeconds);
		} finally {
			subscriptionAdminClient.close();
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String projectId = "api-project-378578942759";
			String subscriptionId = "events";
			String topicId = "events";
			createSubscription(projectId, subscriptionId, topicId);
			listSubscriptions(projectId);
		} catch (Throwable t) {
			System.out.println("Throwable: " + t.getMessage());
			t.printStackTrace();
		}
	}

}

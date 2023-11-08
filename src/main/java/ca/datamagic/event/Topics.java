/**
 * 
 */
package ca.datamagic.event;

import java.io.IOException;

import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.pubsub.v1.ProjectName;
import com.google.pubsub.v1.Topic;
import com.google.pubsub.v1.TopicName;

/**
 * @author gregm
 *
 */
public class Topics {

	public static void listTopics(String projectId) throws IOException {
		System.out.println("projectId: " + projectId);
		TopicAdminClient topicAdminClient = TopicAdminClient.create();
		try {
			ProjectName projectName = ProjectName.of(projectId);
			for (Topic topic : topicAdminClient.listTopics(projectName).iterateAll()) {
				System.out.println(topic.getName());
			}
			System.out.println("Listed all topics.");
		} finally {
			topicAdminClient.close();
		}
	}
	
	public static void createTopic(String projectId, String topicId) throws IOException {
		System.out.println("projectId: " + projectId);
		System.out.println("topicId: " + topicId);
		TopicName topicName = TopicName.of(projectId, topicId);
		TopicAdminClient topicAdminClient = TopicAdminClient.create();
		try {
			topicAdminClient.createTopic(topicName);
		} finally {
			topicAdminClient.close();
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String projectId = "api-project-378578942759";
			String topicId = "events";
			createTopic(projectId, topicId);
			listTopics(projectId);
		} catch (Throwable t) {
			System.out.println("Throwable: " + t.getMessage());
			t.printStackTrace();
		}
	}

}

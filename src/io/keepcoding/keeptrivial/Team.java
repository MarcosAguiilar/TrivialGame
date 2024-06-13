package io.keepcoding.keeptrivial;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Team {
    private String name;
    private Map<String, Integer> pointsPerTopic;

    public Team(String name) {
        this.name = name;
        this.pointsPerTopic = new HashMap<>();
    }
    

    public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Map<String, Integer> getPointsPerTopic() {
		return pointsPerTopic;
	}


	public void setPointsPerTopic(Map<String, Integer> pointsPerTopic) {
		this.pointsPerTopic = pointsPerTopic;
	}


	public void addPoint(String topicName) {
        pointsPerTopic.put(topicName, pointsPerTopic.getOrDefault(topicName, 0) + 1);
    }
	
	public int getPointsForTopic(String topicName) {
        return pointsPerTopic.getOrDefault(topicName, 0);
    }

    public boolean hasAtLeastOnePointInEachTopic(List<Topic> topics) {
        for (Topic topic : topics) {
            if (!pointsPerTopic.containsKey(topic.getTitle()) || pointsPerTopic.get(topic.getTitle()) == 0) {
                return false;
            }
        }
        return true;
    }


}
	



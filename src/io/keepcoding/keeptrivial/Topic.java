package io.keepcoding.keeptrivial;

import java.util.ArrayList;
import java.util.List;

public class Topic {
	private String title;
	private List<Question> questions;

    public Topic(String title) {
        this.title = title;
        this.questions = new ArrayList<>();
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }
    public List<Question> getQuestions() {
        return questions;
    }
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}

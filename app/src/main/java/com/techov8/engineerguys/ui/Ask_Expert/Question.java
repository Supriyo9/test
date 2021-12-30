package com.techov8.engineerguys.ui.Ask_Expert;

public class Question {

    private String questions;
    private String Ispublicquestion;
    private String postid;
    private String publisher;

    public Question() {
    }

    public Question(String questions, String Ispublicquestion, String postid, String publisher) {
        this.questions = questions;
        this.Ispublicquestion = Ispublicquestion;
        this.postid = postid;
        this.publisher = publisher;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public String getIspublicquestion() {
        return Ispublicquestion;
    }

    public void setIspublicquestion(String ispublicquestion) {
        this.Ispublicquestion = ispublicquestion;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}

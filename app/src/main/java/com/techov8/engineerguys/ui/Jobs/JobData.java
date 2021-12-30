package com.techov8.engineerguys.ui.Jobs;

public class JobData {
    private String jobtitle, salary,post,image,additionalinfo,key,link;

    public JobData(){

    }


    public JobData(String jobtitle, String salary, String post, String image, String additionalinfo, String key, String link) {
        this.jobtitle = jobtitle;
        this.salary = salary;
        this.post = post;
        this.image = image;
        this.additionalinfo = additionalinfo;
        this.key = key;
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAdditionalinfo() {
        return additionalinfo;
    }

    public void setAdditionalinfo(String additionalinfo) {
        this.additionalinfo = additionalinfo;
    }

    public String getJobtitle() {
        return jobtitle;
    }

    public void setJobtitle(String jobtitle) {
        this.jobtitle = jobtitle;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

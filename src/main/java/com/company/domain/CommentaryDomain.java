package com.company.domain;

import com.company.entity.Commentary;

public class CommentaryDomain {

    private int id;

    private int accountId;

    private int projectId;

    private String text;

    public CommentaryDomain() {}

    public CommentaryDomain(Commentary commentary) {
        if(commentary != null) {
            this.id = commentary.getId();
            if(commentary.getAuthor() != null) {
                this.accountId = commentary.getAuthor().getId();
            }
            if(commentary.getProject() != null) {
                this.projectId = commentary.getProject().getId();
            }
            this.text = commentary.getText();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

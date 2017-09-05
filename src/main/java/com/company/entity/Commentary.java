package com.company.entity;

import com.company.domain.CommentaryDomain;

import javax.persistence.*;

@Entity
@Table(name = "commentary", schema = "public", catalog = "postgres")
public class Commentary {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Account author;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "text")
    private String text;

    public Commentary() {
    }

    public Commentary(CommentaryDomain commentaryDomain, Account author, Project project) {
        if(commentaryDomain != null) {
            this.id = commentaryDomain.getId();
            this.text = commentaryDomain.getText();
            this.author = author;
            this.project = project;
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Account getAuthor() {
        return author;
    }

    public void setAuthor(Account author) {
        this.author = author;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

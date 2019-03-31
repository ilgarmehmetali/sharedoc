package net.milgar.sharedoc.domain.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String content;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_poster_user_id", referencedColumnName = "id")
    private User posterUser;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_posted_termclass_id", referencedColumnName = "id")
    private TermClass termClass;

    @OneToMany
    private List<File> files;

    @OneToMany(mappedBy = "document")
    private List<Comment> comments;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modify_date")
    private Date modifyDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getPosterUser() {
        return posterUser;
    }

    public void setPosterUser(User posterUser) {
        this.posterUser = posterUser;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public TermClass getTermClass() {
        return termClass;
    }

    public void setTermClass(TermClass termClass) {
        this.termClass = termClass;
    }
}

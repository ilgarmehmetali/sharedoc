package net.milgar.sharedoc.domain.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String displayName;

    private String email;

    private String password;

    @Transient
    private String passwordConfirm;

    @ManyToMany
    private Set<Role> roles;

    @ManyToMany
    private List<TermClass> classes;

    @OneToMany(mappedBy = "creator")
    @OrderBy("create_date ASC")
    private List<TermClass> createdClasses;

    @OneToMany(mappedBy = "commenter")
    private Set<Comment> postedClasses;

    @OneToMany(mappedBy = "posterUser")
    private Set<Document> postedDocuments;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<TermClass> getClasses() {
        return classes;
    }

    public void setClasses(List<TermClass> classes) {
        this.classes = classes;
    }

    public List<TermClass> getCreatedClasses() {
        return createdClasses;
    }

    public void setCreatedClasses(List<TermClass> createdClasses) {
        this.createdClasses = createdClasses;
    }

    public void joinClass(TermClass termClass){
        if(this.classes == null){
            this.classes = new ArrayList<>();
        }
        this.classes.add(termClass);
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Set<Comment> getPostedClasses() {
        return postedClasses;
    }

    public void setPostedClasses(Set<Comment> postedClasses) {
        this.postedClasses = postedClasses;
    }

    public Set<Document> getPostedDocuments() {
        return postedDocuments;
    }

    public void setPostedDocuments(Set<Document> postedDocuments) {
        this.postedDocuments = postedDocuments;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

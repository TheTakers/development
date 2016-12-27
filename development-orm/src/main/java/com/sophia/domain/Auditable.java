package com.sophia.domain;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;

import org.springframework.security.core.context.SecurityContextHolder;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by Kim on 2015/9/21.
 */
@MappedSuperclass
public class Auditable implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Version
    private long version;
    private String createUser;
    private Date createTime;
    private String lastUpdateUser;
    private Date lastUpdateTime;

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getLastUpdateUser() {
        return lastUpdateUser;
    }

    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @PrePersist
    public void prePersist() {
        Date currentTime = new Date();
        this.setCreateTime(currentTime);
        this.setCreateUser(getUserName());
        this.setLastUpdateTime(currentTime);
        this.setLastUpdateUser(getUserName());
    }

    @PreUpdate
    public void preUpdate() {
        this.setLastUpdateTime(new Date());
        this.setLastUpdateUser(getUserName());
    }
    
    @JsonIgnore
    private String getUserName() {
        String username;
        try {
            username = SecurityContextHolder.getContext().getAuthentication().getName();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }
}
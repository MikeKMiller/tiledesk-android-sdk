package org.chat21.android.tilewidget.core.models;

import java.io.Serializable;

/**
 * Created by stefanodp91 on 04/05/18.
 */

// "_id":"5ae1a39b86724100146e1e4e",
// "updatedAt":"2018-04-26T10:07:01.215Z",
// "createdAt":"2018-04-26T10:02:03.179Z",
// "name":"Default Department",
// "id_project":"5ae1a39b86724100146e1e4c",
// "createdBy":"5ae1a38b86724100146e1e4b",
// "__v":0,
// "id_bot":null,
// "default":true,
// "routing":"assigned"
public class Department implements Serializable {
    private String id, name, projectId, createdBy, botId, defaultDepartment, routing;
    private long updatedAt, createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getBotId() {
        return botId;
    }

    public void setBotId(String botId) {
        this.botId = botId;
    }

    public String getDefaultDepartment() {
        return defaultDepartment;
    }

    public void setDefaultDepartment(String defaultDepartment) {
        this.defaultDepartment = defaultDepartment;
    }

    public String getRouting() {
        return routing;
    }

    public void setRouting(String routing) {
        this.routing = routing;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", projectId='" + projectId + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", botId='" + botId + '\'' +
                ", defaultDepartment='" + defaultDepartment + '\'' +
                ", routing='" + routing + '\'' +
                ", updatedAt=" + updatedAt +
                ", createdAt=" + createdAt +
                '}';
    }
}

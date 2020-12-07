package org.geekhub.crypto.web.model;

public class System {
    private String id;
    private String status;

    public System(String id, String status) {
        this.id = id;
        this.status = status;
    }

    public System() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

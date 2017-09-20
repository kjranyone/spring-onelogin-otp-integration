package com.example.demo;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by usr0200505 on 2017/09/19.
 */
public class OneLoginAttributeBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private LocalDateTime lastAuthenticatedAt;

    public LocalDateTime getLastAuthenticatedAt() {
        return lastAuthenticatedAt;
    }

    public void setLastAuthenticatedAt(LocalDateTime lastAuthenticatedAt) {
        this.lastAuthenticatedAt = lastAuthenticatedAt;
    }
}

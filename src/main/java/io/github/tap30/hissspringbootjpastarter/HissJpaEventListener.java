package io.github.tap30.hissspringbootjpastarter;

import io.github.tap30.hiss.Hiss;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Component
public class HissJpaEventListener {
    private final Hiss hiss;

    public HissJpaEventListener(Hiss hiss) {
        this.hiss = hiss;
    }

    @PrePersist
    @PreUpdate
    public void encrypt(Object entity) {
        hiss.encryptObject(entity);
    }

    @PostPersist
    @PostUpdate
    @PostLoad
    public void decrypt(Object entity) {
        hiss.decryptObject(entity);
    }

}

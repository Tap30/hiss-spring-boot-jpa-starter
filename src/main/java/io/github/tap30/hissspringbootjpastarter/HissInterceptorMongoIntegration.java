package io.github.tap30.hissspringbootjpastarter;

import io.github.tap30.hiss.Hiss;
import org.slf4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterConvertEvent;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;

@Order(1000)
public class HissInterceptorMongoIntegration extends AbstractMongoEventListener<Object> {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(HissInterceptorMongoIntegration.class);
    private final Hiss hiss;

    public HissInterceptorMongoIntegration(Hiss hiss) {
        this.hiss = hiss;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {
        log.debug("(On Before Convert) Before Encryption: {}", event.getSource());
        this.hiss.encryptObject(event.getSource());
        log.debug("(On Before Convert) After Encryption: {}", event.getSource());
    }

    @Override
    public void onAfterSave(AfterSaveEvent<Object> event) {
        log.debug("(On After Save) Before Decryption: {}", event.getSource());
        this.hiss.decryptObject(event.getSource());
        log.debug("(On After Save) After Decryption: {}", event.getSource());
    }

    @Override
    public void onAfterConvert(AfterConvertEvent<Object> event) {
        log.debug("(On After Convert) Before Decryption: {}", event.getSource());
        this.hiss.decryptObject(event.getSource());
        log.debug("(On After Convert) After Decryption: {}", event.getSource());
    }

}

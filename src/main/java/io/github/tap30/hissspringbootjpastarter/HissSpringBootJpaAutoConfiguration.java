package io.github.tap30.hissspringbootjpastarter;

import io.github.tap30.hiss.Hiss;
import io.github.tap30.hiss.HissFactory;
import io.github.tap30.hiss.HissPropertiesFromEnvProvider;
import io.github.tap30.hiss.HissPropertiesProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@AutoConfiguration
@ConditionalOnClass(Hiss.class)
public class HissSpringBootJpaAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public Hiss hiss(Optional<HissPropertiesProvider> optionalHissPropertiesProvider) {
        var hissPropertiesProvider = optionalHissPropertiesProvider
                .orElse(new HissPropertiesFromEnvProvider());
        return HissFactory.createHiss(hissPropertiesProvider);
    }

}

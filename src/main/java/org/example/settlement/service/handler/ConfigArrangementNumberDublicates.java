package org.example.settlement.service.handler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigArrangementNumberDublicates {

    @Bean(name = "instanceCreateOperations")
    public InspectorArrangementNumbersDuplicates inspectorArrangementNumbersDuplicatesNew() {
        return new InspectorArrangementNumbersDuplicates();
    }

    @Bean(name = "instanceUpdateOperations")
    public InspectorArrangementNumbersDuplicates inspectorArrangementNumbersDuplicatesUpd() {
        return new InspectorArrangementNumbersDuplicates();
    }
}

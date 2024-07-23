package org.example.settlement.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Value
@Getter @Setter
public class AdditionalPropertiesDTO {
        @RequiredFields
        String key;
        String value;
        String name;

        public AdditionalPropertiesDTO(String key, String value, String name) {
                this.key = key;
                this.value = value;
                this.name = name;
        }
}

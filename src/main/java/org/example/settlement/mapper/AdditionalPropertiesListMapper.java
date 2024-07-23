package org.example.settlement.mapper;

import org.example.settlement.DTO.AdditionalPropertiesDTO;
import org.example.settlement.DTO.request.RequestAdditionalPropertiesDTO;
import org.example.settlement.DTO.request.RequestAdditionalPropertiesDataDTO;
import org.example.settlement.service.RequiredFieldsProcessor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AdditionalPropertiesListMapper implements RequestBodyMapperInterface<RequestAdditionalPropertiesDTO, List<AdditionalPropertiesDTO>> {
    @Override
    public List<AdditionalPropertiesDTO> map(RequestAdditionalPropertiesDTO request) {
        List<AdditionalPropertiesDTO> tmp = new ArrayList<>();
        for (RequestAdditionalPropertiesDataDTO prop: request.data()) {
            AdditionalPropertiesDTO additionalPropertiesDTO = new AdditionalPropertiesDTO(prop.getKey(), prop.getValue(), prop.getName());
            //Да такое приравнивание лишнее. Но так очевиднее что дает резульата
            additionalPropertiesDTO = RequiredFieldsProcessor.process(additionalPropertiesDTO);
            tmp.add(additionalPropertiesDTO);
        }
        return tmp;
    }


}

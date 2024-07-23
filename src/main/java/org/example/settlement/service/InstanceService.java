
package org.example.settlement.service;

import org.example.settlement.DTO.InstanceBodyDTO;
import org.example.settlement.DTO.ResponseInstanceMessage;
import org.example.settlement.DTO.request.RequestInstanceBodyDTO;
import org.example.settlement.mapper.InstanceRequestBodyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InstanceService implements InstanceServiceable{
    @Autowired
    private final InstanceRequestBodyMapper instanceRequestBodyMapper; //Если здесь использовать интерфейсную ссылку, то ниже придется кастовать

    @Autowired @Qualifier("instanceCreateOperations")
    List<UnaryOperator<InstanceBodyDTO>> instanceCreateOperations;
    @Autowired @Qualifier("instanceUpdateOperations")
    List<UnaryOperator<InstanceBodyDTO>> instanceUpdateOperations;

    @Override
    public ResponseInstanceMessage process(RequestInstanceBodyDTO accMsgIn) {
        InstanceBodyDTO instanceBodyDTO = instanceRequestBodyMapper.map(accMsgIn);

        Optional<Integer> instanceId = Optional.ofNullable(instanceBodyDTO.getInstanceId());

        if(instanceId.orElse(0) != 0) {
            instanceUpdateOperations.forEach(x -> x.apply(instanceBodyDTO));
        }
        else {
            //stream()....тоже можно было бы вынести в отдельный класс/метод :)
            instanceCreateOperations.forEach(x -> x.apply(instanceBodyDTO));
        };

        List<String> tmpProductList=new ArrayList<>();
        if((instanceBodyDTO.getProductRegisterList()!=null)) {
            tmpProductList = instanceBodyDTO.getProductRegisterList().stream().map(Object::toString).collect(Collectors.toList());
        }
        return new ResponseInstanceMessage(instanceBodyDTO.getInstanceId().toString(),
                tmpProductList,
                instanceBodyDTO.getProductRegisterTypeList());
    }

}

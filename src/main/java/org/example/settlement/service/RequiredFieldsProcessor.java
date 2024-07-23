package org.example.settlement.service;

import org.example.settlement.DTO.RequiredFields;
import org.springframework.beans.BeansException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


@Service
public class RequiredFieldsProcessor<T>{
        static public <T> T process(T o) throws BeansException, ResponseStatusException {
            List<String> errorFields = new ArrayList<>();
            try {
                for (Field field : o.getClass().getDeclaredFields()) {
                    if (field.isAnnotationPresent(RequiredFields.class)){
                        field.setAccessible(true);
                        if (field.get(o) == null) {
                            errorFields.add(field.getName());
                        }}
                }
                if(errorFields.size()>0){
                    String tmp="";
                    for (String field:errorFields) {
                        tmp += "Обязательный параметр <" + field + "> не заполнен.\n";
                    }
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, tmp);
                }
                return o;
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Could not validate input object", e);
            }
        };
}

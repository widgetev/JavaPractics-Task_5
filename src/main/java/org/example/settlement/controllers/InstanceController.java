package org.example.settlement.controllers;

import org.example.settlement.DTO.request.RequestInstanceBodyDTO;
import org.example.settlement.service.InstanceServiceable;
import jakarta.servlet.http.HttpServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
@RestController
@RequestMapping("/corporate-settlement-instance")
public class InstanceController extends HttpServlet {
    @Autowired
    InstanceServiceable createInstanceService;
    @PostMapping(value = "create",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createInstance(@RequestBody RequestInstanceBodyDTO accMsgIn)
    {
        try {
            Object responce = createInstanceService.process(accMsgIn);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(responce);
        } catch (ResponseStatusException e)
        {
            return new ResponseEntity(e.getMessage(), e.getStatusCode());
        }
    }

//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    public ResponseEntity<Object> handleMissingRequestBody(HttpMessageNotReadableException ex) {
//        return new ResponseEntity<Object>("Запрос \"corporate-settlement-instance/create\" не может быть пустым ", HttpStatus.BAD_REQUEST);
//    }

}

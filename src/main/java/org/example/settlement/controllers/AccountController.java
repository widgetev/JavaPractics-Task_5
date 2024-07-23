package org.example.settlement.controllers;

import org.example.settlement.DTO.request.RequestAccountDTO;
import org.example.settlement.service.AccountServiceable;
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
@RequestMapping("/corporate-settlement-account")
public class AccountController extends HttpServlet {
    @Autowired
    AccountServiceable createAccountService;
    @PostMapping(value = "create",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> processAccount(@RequestBody RequestAccountDTO accMsgIn)
    {
       try {
           Object response = createAccountService.process(accMsgIn);
           return ResponseEntity
                   .status(HttpStatus.OK)
                   .body(response);
       }catch(ResponseStatusException e) {
           return new ResponseEntity(e.getMessage(), e.getStatusCode());
       }
    }
}

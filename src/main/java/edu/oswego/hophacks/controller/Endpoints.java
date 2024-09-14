package edu.oswego.hophacks.controller;

import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Endpoints {
    @PostMapping("/message")
    public ResponseEntity<JSONObject> sendMessage(@RequestBody JSONObject json) {
        return ResponseEntity.ok(json);
    }
}

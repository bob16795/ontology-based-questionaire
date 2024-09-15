package edu.oswego.hophacks.controller;

import edu.oswego.hophacks.object.Ontology;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class Endpoints {
    HashMap<String, String> chatHistory;

    Ontology onto = new Ontology();

    @PostMapping("/message")
    public ResponseEntity<JSONObject> sendMessage(@RequestBody JSONObject json) {
        return ResponseEntity.ok(new JSONObject());
    }


    //Question History
    //When a doctor or patient reload the page the same information is displayed

    /**
     * Requests chat history
     * @return JSON of Chat History
     */

    @GetMapping("/get_question")
    public ResponseEntity<JSONObject> getQuestion() {
        return ResponseEntity.ok(Ontology.getQuestion(Ontology.currentNode));
    }


}

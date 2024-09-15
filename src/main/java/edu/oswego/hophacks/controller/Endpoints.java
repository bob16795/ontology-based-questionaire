package edu.oswego.hophacks.controller;

//import edu.oswego.hophacks.object
import edu.oswego.hophacks.object.Deduction;
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
    JSONObject message = null;
    boolean sendMessage = false;

    public void accecptMessage(JSONObject obj){
            obj = message;
            this.sendMessage = true;
    }

    @PostMapping("/message")
    //Ontology.getMessage -> new JSONObject("new Question");
    public ResponseEntity<JSONObject> sendMessage(@RequestBody JSONObject json) {

        return ResponseEntity.ok(message);
    }


    //Question History
    //When a doctor or patient reload the page the same information is displayed

    /**
     * Requests chat history
     * @return JSON of Chat History
     */

    @GetMapping("/history")
    public ResponseEntity<JSONObject> getHistory() {
        return ResponseEntity.ok(new JSONObject()); //Ontology.getHistory()
    }


}

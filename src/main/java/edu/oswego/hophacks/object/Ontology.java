package edu.oswego.hophacks.object;

import com.sun.tools.javac.Main;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Ontology {
    static ArrayList<Node> destNodes;
    static ArrayList<Node> undetermined;
    //This is the arraylist for the condition nodes that apply to the patient's situation
    static ArrayList<Node> applicableConditions;
    //This is the arraylist for the nodes whose questions have been asked
    static ArrayList<Node> determinedConditions;
    static ArrayList<Node> allNodes;
    static ArrayList<Edge> allEdges;
    static ArrayList<Node> totalConditions;


    public static Node currentNode;

    boolean readyToSendQuestion;
    String questionToSend;
    String currentAnswer;

    public Ontology() {
        totalConditions = allNodes=destNodes = undetermined = applicableConditions = determinedConditions = new ArrayList<>();
        allEdges = new ArrayList<>();
        Node name = new Node("Name", "0");
        totalConditions.add(name);
        Node weight = new Node("Weight", "1");
        totalConditions.add(weight);
        Node height = new Node("Height", "2");
        totalConditions.add(height);
        Node age = new Node("Age", "3");
        totalConditions.add(age);

        parseJSON();
        System.out.println(allNodes);
    }


    public static JSONObject getQuestion(Node node) {
        String question = "";
        ArrayList<String> possibleResponses = new ArrayList<>();
        //Get nodes question
        for (Edge e : node.getEdges()) {
            if (e.getRelation().equals("question_for")) {
                 question = e.getDest().getID();
                //TODO: add code for asking question + getting response
            }
            else if (e.getRelation().equals("is_a")) {
                    //Get the node that e points to
                    Node possibleNode = e.getDest();
                    //Find the option node from possible node
                    for (Edge possibleE : possibleNode.getEdges()) {
                        if (possibleE.getRelation().equals("answer_for")) {
                            possibleResponses.add(possibleE.getDest().getID());
                        }
                    }
                }
            }
        JSONObject object = new JSONObject();
        object.put("question", question);
        object.put("choices", possibleResponses);

        return object;
    }

    public Node travelDown(Node currentNode) {
        //Iterate and make sure there are no "is_a" edges on the current node. If there aren't any, return the current node
        boolean bottomOfGraph = true;
        for (Edge e : currentNode.getEdges()) {
            if (e.getRelation().equals("is_a")) {
                bottomOfGraph = false;
            }
        }
        if (bottomOfGraph) {
            return currentNode;
        }
        //Iterate through the list of edges and find the questionFor edge
        String response = null;

        for (Edge e : currentNode.getEdges()) {
            if (e.getRelation().equals("question_for")) {
                this.questionToSend = e.getDest().getID();
                this.readyToSendQuestion = true;
                //TODO: add code for asking question + getting response
            }
        }
        Node nextNode = null;
        String answer_for = null;
        for (Edge e : currentNode.getEdges()) {
            if (e.getRelation().equals("is_a")) {
                //Get the node that e points to
                Node possibleNode = e.getDest();
                //Find the option node from possible node
                for (Edge possibleE : possibleNode.getEdges()) {
                    if (possibleE.getRelation().equals("answer_for")) {
                        answer_for = possibleE.getDest().getID();
                        if (answer_for.equals(response)) {
                            nextNode = possibleNode;
                        };
                    }
                }
            }
        }
        return travelDown(nextNode);
    }

    public static void parseJSON() {
        JSONParser jsonParser = new JSONParser();
        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("ontology.json");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream)))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONObject ontology = (JSONObject) obj;
            JSONArray nodes = (JSONArray) ontology.get("nodes");
            JSONArray edges = (JSONArray) ontology.get("edges");

            int idAssigner = 0;

            //Add each node to the allnodes array
            for (Object o : nodes) {
                JSONObject nodeObject = (JSONObject) o;


                String nameID = (String) nodeObject.get("content");
                String uri = (String) nodeObject.get("uri");
                Node node = new Node(nameID, uri);
                allNodes.add(node);

                if (nameID.equals("_symptom")) {
                    currentNode = node;
                }

            }

            //Add edges to alledges array
            for (Object o : edges) {
                JSONObject edgeObject = (JSONObject) o;
                String source = (String) edgeObject.get("obj");
                String dest = (String) edgeObject.get("subj");
                String relation = (String) edgeObject.get("relation");

                //Use idAssigner to get a unique id for each edge
                int id = idAssigner;
                idAssigner++;

                Node sourceNode = null;
                Node destNode = null;
                //Find the correct dest and source nodes
                for (Node n : allNodes) {
                    if (source.equals(n.getURI())) {
                        sourceNode = n;
                    }
                    if (dest.equals(n.getURI())) {
                        destNode = n;
                    }
                }
                if (sourceNode != null && destNode != null) {
                    allEdges.add(new Edge(sourceNode, destNode, relation));
                } else { throw new Exception(); }
            }
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        } catch (Exception e) {
            System.out.println("Error - edge failed to find node");
            throw new RuntimeException(e);
        }

        //NEED A SPECIFIC GRAPH WITH A "Conditions" NODE POINTING TO ALL CONDITIONS
        //This will identify all of our symptoms/conditions
        for (Edge e : allEdges) {
            if (e.getSource().getURI().equals("Conditions")) {
                totalConditions.add(e.getDest());
            }
        }
    }

    public static Node determineMostValuable(ArrayList<Node> determined, ArrayList<Node> total) {
        destNodes = new ArrayList<>();

        //Get the total list of unique destination nodes in the determined list
        for (Node n : determined) {
            for (Edge e : n.getEdges()) {
                if (!destNodes.contains(e.getDest())) { destNodes.add(e.getDest()); }
            }
        }

        //Get the nodes that aren't already determined
        undetermined = new ArrayList<>();
        for (Node n : total) {
            if (!determined.contains(n) && !undetermined.contains(n)) { undetermined.add(n); }
        }

        //Make a HashTable for the node and the number of destinations it matches
        HashMap<Node, Integer> matches = new HashMap<Node, Integer>();

        //Loop through the undetermined nodes, get their edges, and compare them to the unique determined destinations
        for (Node n : undetermined) {
            int numMatches = 0;
            for (Edge e : n.getEdges()) {
                //If the unique determined destinations contain the specific destination, increment the match total
                if (destNodes.contains(e.getDest())) { numMatches++; }
            }
            matches.put(n, numMatches);
        }

        //The node with the number of matches closest to half of the size of destNodes is the best node to determine next
        //This number can be adjusted without really breaking anything, so if my 5 am math is incorrect we can aim for more/less matches
        double target = destNodes.size();

        // Initialize variables to track the closest index and smallest difference
        double smallestDifference = Double.MAX_VALUE;
        //I need to return something if the for loop fails so it's just going to be the first undetermined node
        Node bestNode = undetermined.getFirst();

        //Have been told that this will iterate through the Hashtable
        for (Map.Entry<Node, Integer> entry : matches.entrySet()) {
            //Get the difference between our target value and the current hash match value
            double diff = Math.abs(entry.getValue() - target);

            //If it's smaller, that means it's closer
            if (diff < smallestDifference) {
                smallestDifference = diff;
                bestNode = entry.getKey();
            }
        }
        return bestNode;
    }


    public static void main(String[] args) {
        Ontology oogabooga = new Ontology();
    }

}

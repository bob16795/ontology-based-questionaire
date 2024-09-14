import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Ontology {
public static Node determineMostValuable(ArrayList<Node> determined, ArrayList<Node> total) {
    ArrayList<Node> destNodes = new ArrayList<>();

    //Get the total list of unique destination nodes in the determined list
    for (Node n : determined) {
        for (Edge e : n.getEdges()) {
            if (!destNodes.contains(e.getDest())) { destNodes.add(e.getDest()); }
        }
    }

    //Get the nodes that aren't already determined
    ArrayList<Node> undetermined = new ArrayList<>();
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
    double target = destNodes.size() / 2.0;

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


    public static void main(String[] args)  {
        //This is the arraylist for all condition nodes
        ArrayList<Node> totalConditions = new ArrayList<>();
        //This is the arraylist for the condition nodes that apply to the patient's situation
        ArrayList<Node> applicableConditions = new ArrayList<Node>();
        //This is the arraylist for the nodes whose questions have been asked
        ArrayList<Node> determinedConditions = new ArrayList<>();



        //Initialize a ton of nodes. If we're doing something simple it might be worth hard-coding this honestly
        Node conditions = new Node("Conditions", "URIPLACEHOLDER1");
        Node fever = new Node("fever", "URIPLACEHOLDER2");
        Node cough = new Node("cough", "URIPLACEHOLDER3");
        totalConditions.add(fever);
        totalConditions.add(cough);
        //Third layer node, condition -> condition1 -> condition11
        Node COVID = new Node("COVID", "URIPLACEHOLDER4");
        Node skinInfection = new Node("SkinInfection", "URIPLACEHOLDER5"); //Fever only
        Node commonCold = new Node("CommonCold", "URIPLACEHOLDER6");
        Edge condition1 = new Edge(conditions, fever, "condition", "URIPLACEHOLDER10");
        Edge condition2 = new Edge(conditions, cough, "condition", "URIPLACEHOLDER11");
        Edge feverCause1 = new Edge(fever, skinInfection, "Cause", "URIPLACEHOLDER12");
        Edge feverCause2 = new Edge(fever, COVID, "Cause", "URIPLACEHOLDER13");
        Edge coughCause1 = new Edge(cough, commonCold, "Cause", "URIPLACEHOLDER14");
        Edge coughCause2 = new Edge(cough, COVID, "Cause", "URIPLACEHOLDER15");


        //TO DO: Once nodes and edges are created, iterate through the condition nodes and ask questions to see which are applicable
        //TO DO: Add applicable nodes to arraylist. Ensure we somehow store the answers to these questions so we can transmit them to PHC

        //Always start with the same question
        int conditionIndex = 0;
        //Loop until there are no other conditions to ask, or until we've asked a specific number of questions
        for (int i = 0; i < totalConditions.size() || totalConditions.size() == 1 + determinedConditions.size(); i++) {
            boolean question = true; //We'd prompt for the question, in this case just assume true.

            //Add it to the list of determined nodes
            determinedConditions.add(totalConditions.get(i));


                //If the question did not add an applicable condition, just move to the next question
                if (applicableConditions.size() == 0 && !question) {
                    conditionIndex = 1;
                } else {
                    //Add the applicable condition
                    applicableConditions.add(totalConditions.get(conditionIndex));
                    //Make sure the determined conditions aren't the same as the total conditions otherwise will have a bad time
                    if (totalConditions.size() > determinedConditions.size()) {
                        conditionIndex = totalConditions.indexOf(determineMostValuable(determinedConditions, totalConditions)); //Find the next question to ask
                }
            }
        }


        //If there's multiple applicable conditions, find the two with the most common edges and spit out the possible causes
        if (applicableConditions.size() > 1) {
            Node[] mostCommonEdges = Node.findOptimalPair(applicableConditions);
            if (mostCommonEdges[0] == null) {
                //SHOULD NEVER HAPPEN, ONLY HERE FOR TESTING PURPOSES
                System.out.println("Too much conflicting information, please try again.");
            }

            ArrayList<Edge> commonEdges = Node.commonEdges(mostCommonEdges[0], mostCommonEdges[1]);

            System.out.println("Most likely diagnosis: ");
            for (int i = 0; i < commonEdges.size(); i++) {
                System.out.println((i + 1) + ". " + commonEdges.get(i).getDest().getID());
            }
        }
        //If there's one applicable condition, spit out all possible causes
        else if (applicableConditions.size() == 1){
            Node condition = applicableConditions.getFirst();
            System.out.println("Most likely diagnosis: ");
            for (int i = 0; i < condition.getEdges().size(); i++) {
                System.out.println(i + ". " + condition.getEdges().get(i).getDest().getID());
            }
        } else {
            System.out.println("Patient appears to have no applicable conditions.");
        }
    }
}
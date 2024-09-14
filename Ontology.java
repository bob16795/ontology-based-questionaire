import java.util.ArrayList; // import the ArrayList class

public class Ontology {
    public static void main(String[] args)  {
        ArrayList<Node> applicableConditions = new ArrayList<Node>();

        //Initialize a ton of nodes. If we're doing something simple it might be worth hard-coding this honestly
        Node conditions = new Node("Conditions", "URIPLACEHOLDER1");
        Node fever = new Node("fever", "URIPLACEHOLDER2");
        Node cough = new Node("cough", "URIPLACEHOLDER3");
        //Third layer node, condition -> condition1 -> condition11
        Node COVID = new Node("COVID", "URIPLACEHOLDER4");
        Node skinInfection = new Node("SkinInfection", "URIPLACEHOLDER5"); //Fever only
        Node commonCold = new Node("CommonCold", "URIPLACEHOLDER6");
        Edge condition1 = new Edge(conditions, fever, "condition", "URIPLACEHOLDER10");
        Edge condition2 = new Edge(conditions, cough, "condition", "URIPLACEHOLDER11");
        Edge feverCause1 = new Edge(fever, skinInfection, "cause", "URIPLACEHOLDER12");
        Edge feverCause2 = new Edge(fever, COVID, "Cause", "URIPLACEHOLDER13");
        Edge coughCause1 = new Edge(cough, commonCold, "cause", "URIPLACEHOLDER14");
        Edge coughCause2 = new Edge(cough, COVID, "Cause", "URIPLACEHOLDER15");


        //TO DO: Once nodes and edges are created, iterate through the condition nodes and ask questions to see which are applicable
        //TO DO: Add applicable nodes to arraylist. Ensure we somehow store the answers to these questions so we can transmit them to PHC

        //In this hard-coded case, assume both cases are applicable
        applicableConditions.add(cough);
        applicableConditions.add(fever);


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
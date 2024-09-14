import java.util.ArrayList; // import the ArrayList class

public class Ontology {
    public static void main(String[] args) throws InterruptedException {
        ArrayList<Node> nodes = new ArrayList<Node>();

        //Initialize a ton of nodes. If we're doing something simple it might be worth hard-coding this honestly
        Node conditions = new Node("Conditions", "URIPLACEHOLDER1");
        Node condition1 = new Node("FirstCondition", "URIPLACEHOLDER2");
        Node condition2 = new Node("SecondCondition", "URIPLACEHOLDER3");
        //Third layer node, condition -> condition1 -> condition11
        Node condition11 = new Node("FirstCondition", "URIPLACEHOLDER4");
        Edge e1 = new Edge(condition1, condition11, "Name", "URIPLACEHOLDER5");
        Edge e2 = new Edge(condition2, condition11, "Name", "URIPLACEHOLDER6");

        System.out.println("Node 1 and 2 common destination Nodes: " + Node.compareEdges(condition1, condition2));
    }
}
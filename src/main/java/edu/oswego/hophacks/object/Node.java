package edu.oswego.hophacks.object;

import java.util.ArrayList;


public class Node {
    private final String id; //Assume ID is unique
    private final String uri;
    private final ArrayList<Edge> edges = new ArrayList<>();

    public Node(String ID, String URI) {
        this.id = ID;
        this.uri = URI;
    }

    public void addEdge(Edge e) {
        this.edges.add(e);
    }

    public String getID() {
        return this.id;
    }
    public String getURI() {
        return this.uri;
    }
    public ArrayList<Edge> getEdges() {
        return this.edges;
    }

    //Compare how many edges two nodes have in common
    public static int compareEdges(Node a, Node b) {

        ArrayList<Edge> edgesA = a.getEdges();
        ArrayList<Edge> edgesB = b.getEdges();
        if (edgesA.isEmpty() || edgesB.isEmpty()) {
            return 0;
        }

        int totalMatches = 0;
        //Loop through a
        for (int i = 0; i < edgesA.size(); i++) {
            //System.out.println("Iteration " + i + "/" + edgesA.size());
            //get Edge A's destination URI
            String uriA = edgesA.get(i).getDest().getURI();
            //System.out.println("Edge A DestID: " + edgesA.get(i).getDest().getID());
            //Loop through b. If a match is found, increment b and EXIT THE LOOP. 
            boolean loopAgain = true;
            for (int j = 0; j < edgesB.size() && loopAgain; j++) {
                //System.out.println("Iteration " + j + "/" + edgesB.size());
                //get Edge B's destination
                String uriB = edgesB.get(j).getDest().getURI();
                //System.out.println("Edge B DestID: " + edgesB.get(j).getDest().getID());
                if (uriA.equals(uriB)) {
                    //Found a match, quit loop
                    loopAgain = false;
                    //Increment total matches
                    totalMatches++;
                    //System.out.println("Found a match, total matches: " + totalMatches);
                }
            }
        }
        return totalMatches;
    }

    //Same as compareEdges, except returns an ArrayList of all common edges between two Nodes.
    //RETURNS THE EDGES FROM NODE A'S PERSPECTIVE!

    public static ArrayList<Edge> commonEdges(Node a, Node b) {

        ArrayList<Edge> edgesA = a.getEdges();
        ArrayList<Edge> edgesB = b.getEdges();
        if (compareEdges(a, b) == 0) {
            return null;
        }

        ArrayList<Edge> matches = new ArrayList<Edge>();
        //Loop through a
        for (int i = 0; i < edgesA.size(); i++) {
            //get Edge A's destination URI
            String uriA = edgesA.get(i).getDest().getURI();
            //Loop through b. If a match is found, increment b and EXIT THE LOOP.
            boolean loopAgain = true;
            for (int j = 0; j < edgesB.size() && loopAgain; j++) {
                //get Edge B's destination
                String uriB = edgesB.get(j).getDest().getURI();
                if (uriA.equals(uriB)) {
                    //Found a match, quit loop
                    loopAgain = false;
                    //Add a's current edge
                    matches.add(edgesA.get(i));
                }
            }
        }
        return matches;
    }


    //Gets an ArrayList of Nodes and returns the pair of Nodes that share the most Edges
    public static Node[] findOptimalPair(ArrayList<Node> list) {
        Node[] output = new Node[2];
        int currentMax = 0;


        //Will always need a temporary arraylist without the current Node
        ArrayList<Node> tempList = list;
        //Loop through the arraylist in pairs, comparing edges for each of them. If the current pair is bigger than the currentMax,
        //add that to the array of output Nodes.
        for (int i = 0; i < list.size(); i++) {
            Node iNode = list.get(i);
            //System.out.println(iNode.getID());
            //I hope this doesn't cause any issues since I'm getting the object from list rather than tempList
            tempList.remove(iNode);
            for (int j = 0; j < tempList.size(); j++) {
                Node jNode = tempList.get(j);
                //System.out.println(jNode.getID());
                int currComp = compareEdges(iNode, jNode);
                //System.out.println(currComp);
                if (currComp > currentMax) {
                    output[0] = iNode;
                    output[1] = jNode;
                    currentMax = currComp;
                    //In the future, it may be a good idea to include numerous outcomes, like the second and third-likeliest options
                }
            }
        }


        return output;
    }

}
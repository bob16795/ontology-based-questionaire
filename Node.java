import java.util.ArrayList; // import the ArrayList class


public class Node {
    private String id;
    private String uri;
    private ArrayList<Edge> edges = new ArrayList<Edge>();

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
        if (edgesA.size() == 0 || edgesB.size() == 0) {
            return 0;
        }

        int totalMatches = 0;
        //Loop through a
        for (int i = 0; i < edgesA.size(); i++) {
            //get Edge A's destination URI
            String uriA = edgesA.get(i).getDest().getURI();
            System.out.println("Current A edge URI: " + uriA);
            //Loop through b. If a match is found, increment b and EXIT THE LOOP. 
            boolean foundMatch = false;
            for (int j = 0; j < edgesB.size() || foundMatch; j++) {
                //get Edge B's destination
                String uriB = edgesB.get(i).getDest().getURI();
                System.out.println("Current B edge URI: " + uriB);
                if (uriA.equals(uriB)) {
                    //Found a match, quit loop
                    foundMatch = true;
                    //Increment total matches
                    totalMatches++;
                    System.out.println("Found match!");
                }
            }
        }
        return totalMatches;
    }

}
import java.util.ArrayList;


public class Node {
    private final String id;
    private final String uri;
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
    public static int compareEdges(Node a, Node b) throws InterruptedException {

        ArrayList<Edge> edgesA = a.getEdges();
        ArrayList<Edge> edgesB = b.getEdges();
        if (edgesA.isEmpty() || edgesB.isEmpty()) {
            return 0;
        }

        int totalMatches = 0;
        //Loop through a
        for (int i = 0; i < edgesA.size(); i++) {
            //get Edge A's destination URI
            String uriA = edgesA.get(i).getDest().getURI();
            // System.out.println("Current A edge URI: " + uriA);
            // System.out.println("Iteration: " + i + "/" + edgesA.size());
            //Loop through b. If a match is found, increment b and EXIT THE LOOP. 
            boolean loopAgain = true;
            for (int j = 0; j < edgesB.size() || loopAgain; j++) {
                //get Edge B's destination
                String uriB = edgesB.get(i).getDest().getURI();
                // System.out.println("Current B edge URI: " + uriB);
                if (uriA.equals(uriB)) {
                    //Found a match, quit loop
                    loopAgain = false;
                    //Increment total matches
                    totalMatches++;
                    /* Uncomment if this method doesn't work
                    System.out.println("Found match!");
                    System.out.println("Looping again? " + loopAgain);
                    System.out.println("Iteration: " + j + "/" + edgesB.size());
                    System.out.println("Total Matches: " + totalMatches);
                    TimeUnit.SECONDS.sleep(1);
                     */
                }
            }
        }
        return totalMatches;
    }

}
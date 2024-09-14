import java.util.ArrayList; // import the ArrayList class


public class Edge {
    private String tag;
    private final String uri;
    private final Node source;
    private final Node dest;

    public Edge(Node source, Node destination, String tag, String URI) {
        this.source = source;
        this.dest = destination;
        source.addEdge(this);
        this.tag = tag;
        this.uri = URI;
    }

    public String getTag() {
        return this.tag;
    }
    
    public void setTag(String tag) {
        this.tag = tag;
    }

    public Node getSource() {
        return this.source;
    }

    public Node getDest() {
        return this.dest;
    }
}
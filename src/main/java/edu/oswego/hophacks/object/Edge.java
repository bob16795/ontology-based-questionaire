package edu.oswego.hophacks.object;

import java.util.ArrayList; // import the ArrayList class


public class Edge {
    private final String uri; //Relation
    private final int id; //unique
    private final Node source;
    private final Node dest;

    public Edge(Node source, Node destination, int id, String URI) {
        this.source = source;
        this.dest = destination;
        source.addEdge(this);
        this.id = id;
        this.uri = URI;
    }

    public Node getSource() {
        return this.source;
    }
    public int getId() {
        return this.id;
    }

    public Node getDest() {
        return this.dest;
    }
}
package edu.oswego.hophacks.object;

import java.util.ArrayList; // import the ArrayList class


public class Edge {
    private final String relation; //Relation
    //private final String id; //unique
    private final Node source;
    private final Node dest;

    public Edge(Node source, Node destination, String relation) {
        this.source = source;
        this.dest = destination;
        source.addEdge(this);
        this.relation = relation;
    }

    public Node getSource() {
        return this.source;
    }
    public String getRelation() {
        return this.relation;
    }

    public Node getDest() {
        return this.dest;
    }
}
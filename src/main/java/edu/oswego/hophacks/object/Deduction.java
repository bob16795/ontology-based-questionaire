package edu.oswego.hophacks.object;

import org.apache.commons.collections4.bag.HashBag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Deduction {


    static ArrayList<Node> determinedSymptoms;
    Node activeNode;

    // Subclass of
    // Description
    // Title
    // Question for
    // symptom_of
    /**
     * 1. Get all possible symptom_of edges of the symptom given
     * 2. Ask yes or no question for the node with the most edges
     * 3. recurse... /\
     * @param node
     */
    public Deduction(Node node){
        //Given a symptom, find related conditions with similar symptoms
        //and ask questions to deduce the possible conditions
        determinedSymptoms = new ArrayList<>(); //Should really be "incorrect symptoms"
        HashBag<Node> bag = possibilities(node);
        this.activeNode = findMedian(bag);
    }

    public void iterateCalc(Node node) {
        HashBag<Node> bag = possibilities(node);
        this.activeNode = findMedian(bag);
    }

    public String getNextQuestion() {
        String question = "";
        for (Edge e : this.activeNode.getEdges()) {
            if (e.getRelation().equals("bool_question")) {
                question = e.getDest().getID();
            }
        }
        return question;
    }

    public void determineAnswer(boolean answer) {
        if (!answer) {
            determinedSymptoms.add(this.activeNode);
        }
    }


    /**
     * go through all nodes connected to the node given with symptom_of (for each edge. get dest)
     * for each node left, make a count of all
     *
     * @param node
     * @return node (symptom)
     */
    public static HashBag<Node> possibilities(Node node){
        HashBag<Node> possibleSymptoms = new HashBag<>();
        for(Edge e: node.getEdges()){
            //If the node has already been determined, don't add it
            if(e.getRelation().equals("symptom_of") && !determinedSymptoms.contains(e.getDest())){
                ArrayList<Edge> sEdges = e.getDest().getEdges(); //Diagnosis
                for(Edge f:sEdges){ //Going back up the tree
                    possibleSymptoms.add(f.getSource());
                }
            }
        }
        return possibleSymptoms;
    }

    public static Node findMedian(HashBag<Node> bag){
        List<Map.Entry<Node, Integer>> objects = new ArrayList<>();

        for(Node n : bag.uniqueSet()){
            objects.add(Map.entry(n, bag.getCount(n)));
        }

        objects.sort((e1,e2)-> Integer.compare(e1.getValue(), e2.getValue()));

        int median = objects.size()/2;

        return objects.get(median).getKey();
    }




}

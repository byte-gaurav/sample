package org.sample.dsa;

import java.util.ArrayList;
import java.util.List;

public class CloneGraph {

    static class Node {
        public int val;
        public List<Node> neighbors;
        public Node() {
            val = 0;
            neighbors = new ArrayList<Node>();
        }
        public Node(int _val) {
            val = _val;
            neighbors = new ArrayList<Node>();
        }
        public Node(int _val, ArrayList<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }

    public static void main(String[] args) {
        CloneGraph cloneGraph = new CloneGraph();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        node1.neighbors.add(node2);
        node1.neighbors.add(node4);
        node2.neighbors.add(node1);
        node2.neighbors.add(node3);
        node3.neighbors.add(node2);
        node3.neighbors.add(node4);
        node4.neighbors.add(node1);
        node4.neighbors.add(node3);
        Node cloneNode = cloneGraph.cloneGraphDFS(node1, new Node[5]);
    }

    public Node cloneGraphDFS(Node node, Node[] visited) {
        if (visited[node.val] != null) {
            return visited[node.val];
        }
        Node clone = new Node(node.val);
        visited[node.val] = clone;
        for (Node neighbor : node.neighbors) {
            clone.neighbors.add(cloneGraphDFS(neighbor, visited));
        }

        return clone;
    }
}

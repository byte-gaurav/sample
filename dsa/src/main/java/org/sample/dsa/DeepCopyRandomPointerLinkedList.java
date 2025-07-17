package org.sample.dsa;

import java.util.HashSet;
import java.util.Set;

public class DeepCopyRandomPointerLinkedList {

    public static void main(String[] args) {
        NodeWithRandomPointer node1 = new NodeWithRandomPointer(1);
        NodeWithRandomPointer node2 = new NodeWithRandomPointer(2);
        NodeWithRandomPointer node3 = new NodeWithRandomPointer(3);
        NodeWithRandomPointer node4 = new NodeWithRandomPointer(4);

        node1.next = node2;
        node2.next = node3;
        node3.next = node4;

        node1.random = null;
        node2.random = node1;
        node3.random = node2;
        node4.random = node1;

        Set<NodeWithRandomPointer> originalNodes = new HashSet<>();
        NodeWithRandomPointer temp = node1;
        while (temp != null) {
            System.out.print(temp + " next=" + temp.next + " random="+temp.random);
            System.out.println();
            temp = temp.next;
        }

        temp = copyRandomList(node1);
        System.out.println("Now clone");
        while (temp != null) {
            System.out.print(temp + " next=" + temp.next + " random="+temp.random);
            System.out.println();
            temp = temp.next;
        }
    }

    public static NodeWithRandomPointer copyRandomList(NodeWithRandomPointer head) {
        NodeWithRandomPointer temp = head;
        while (temp != null) {
            NodeWithRandomPointer copyNode = new NodeWithRandomPointer(temp.val);
            NodeWithRandomPointer actualNext = temp.next;
            copyNode.next = actualNext;
            temp.next = copyNode;
            temp = actualNext;
        }
        temp = head;
        NodeWithRandomPointer dummy = new NodeWithRandomPointer(0);
        NodeWithRandomPointer res = dummy;
        while (temp != null) {
            NodeWithRandomPointer cloneNode = temp.next;
            if (temp.random != null) {
                cloneNode.random = temp.random.next;
            } else {
                cloneNode.random = null;
            }
            res.next = cloneNode;
            res = res.next;
            temp.next = cloneNode.next;
            temp = temp.next;
        }
        return dummy.next;
    }

    static class NodeWithRandomPointer {

        public int val;
        public NodeWithRandomPointer next;

        public NodeWithRandomPointer random;

        public NodeWithRandomPointer(int val) {
            this.val = val;
        }
    }

}

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class LinkedList<T extends Comparable<T>> {
    private class Node {
        private T data;
        private Node next;

        private Node(T data) {
            this.data = data;
        }
    }

    private Node head;

    public LinkedList() {
        head = null;
    }

    public LinkedList<T> add(T data) {
        Node newNode = new Node(data);
        newNode.next = head;
        head = newNode;
        return this;
    }


    public Set<T> removeLessOrEqual(boolean ordered, T upperBound) {
        Set<T> set;
        if (ordered == true){
            set = new TreeSet<>();
        }else{
            set = new HashSet<>();
        }
        return removeLessOrEqualAux(set, head, upperBound);
    }
    private Set<T> removeLessOrEqualAux(Set set ,Node node,  T upperBound){
        Node previousCurr = head;
        while(previousCurr.next != node && node != head){
            previousCurr = previousCurr.next;
        }
        if (node != null) {
            if (node.data.compareTo(upperBound) <= 0) {
                // remove and add to set
                set.add(node.data);
                previousCurr.next = node.next;
                node.next = null;
                removeLessOrEqualAux(set, previousCurr.next, upperBound);

            } else {
                // do not add to set
                removeLessOrEqualAux(set, node.next, upperBound);
            }
        }
        return set;
    }

}
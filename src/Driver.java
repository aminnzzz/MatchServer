import java.util.Set;

public class Driver {
    public static void main(String[] args){
        LinkedList<Integer> list = new LinkedList<>();
        list.add(7).add(3).add(1).add(9).add(11);
        Set<Integer> a = list.removeLessOrEqual(false, new Integer (5));
        for (Integer i : a){
            System.out.println(i);
        }
    }
}

public class Node{
    String data;
    Node left; // hace ref a hijo izq
    Node right; // hace ref a hijo der

    // constructor de la clase Node
    public Node(String data){
        this.data = data;
        this.left = null;
        this.right = null;
    }
}

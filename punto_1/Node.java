public class Node{
    int data;
    Node left; // hace ref a hijo izq
    Node right; // hace ref a hijo der
    int fe;     //factor de equilibrio
    // constructor de la clase Node
    public Node(int data){
        this.data = data;
        this.left = null;
        this.right = null;
        this.fe=0;
    }
}
import java.util.Stack;
 
class Node{
    char data;
    Node left,right;
    public Node(char data){
        this.data = data;
        left = right = null;
    }
}
 
public class Main {
    public static boolean isOperator(char ch){
        if(ch=='+' || ch=='-'|| ch=='*' || ch=='/' || ch=='^'){
            return true;
        }
        return false;
    }
    public static Node expressionTree(String postfix){
        Stack<Node> pila = new Stack<Node>();
        Node t1,t2,temp;
 
        for(int i=0;i<postfix.length();i++){
            if(!isOperator(postfix.charAt(i))){
                temp = new Node(postfix.charAt(i));
                pila.push(temp);
            }
            else{
                temp = new Node(postfix.charAt(i));
 
                t1 = pila.pop();
                t2 = pila.pop();
 
                temp.left = t2;
                temp.right = t1;
 
                pila.push(temp);
            }
 
        }
        temp = pila.pop();
        return temp;
    }
    public static void inorder(Node root){
        if(root==null) return;
 
        inorder(root.left);
        System.out.print(root.data);
        inorder(root.right);
    }
    public static void main(String[] args) {
        String postfix = "ABC*+D/";
 
        Node r = expressionTree(postfix);
        inorder(r);
    }
}
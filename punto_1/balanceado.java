import java.util.Scanner;
public class balanceado {
    public Node root;
    

    //insertar avl
    public Node InsertarAVL(Node nuevo, Node subAr){
        Node nuevoPadre = subAr; // creamos un auxiliar que mantenga el nodo que vamos a insertar
        if(nuevo.data < subAr.data){ // comparamos para saber si el elemento es menor al nodo donde estamos 
            if(subAr.left == null){ // Si es menor y la rama derecha esta vacia lo añadimos 
                subAr.left = nuevo;
            }else{ // Caso contrario 
                subAr.left = InsertarAVL(nuevo, subAr.left);
                if((obtenerFE(subAr.left) - obtenerFE(subAr.right) == 2)){ // Aqui revisamos si el arbol esta balanceado
                    if(nuevo.data < subAr.left.data){                   //revisamos cual nodo hay que girar
                        nuevoPadre = RotacionSimpleIzquierda(subAr);    //Realizamos una rotacion simple a la izquierda
                    }else{
                        nuevoPadre = RotacionDobleIzquierda(subAr); // Realizamos una rotacion doble a la izquierda
                    }
                }
            }
        }else{
            if(nuevo.data > subAr.data){       // Si el dato es mayor debemos revisar la rama derecha del nodo y
                if(subAr.right == null){       //Si es vacia entonces añadimos el nuevo nodo 
                    subAr.right = nuevo;
                }else{
                    subAr.right = InsertarAVL(nuevo, subAr.right); //Si no es vacia revisamos y buscamos donde poder añadar el nodo
                    if((obtenerFE(subAr.right) - obtenerFE(subAr.left) == 2)){ // Revisamos si esta balanceado
                        if(nuevo.data > subAr.right.data){       // Si el dato es mayor y no esta balanceado realizamos una rotacion simple a la derecha
                            nuevoPadre = RotacionSimpleDerecha(subAr);
                        }else{
                            nuevoPadre = rotacionDobleDerecha(subAr); // Si se necesita se realiza una doble a la derecha
                        }
                    }
                }
            }
        }
        //actualizar altura
        if((subAr.left == null) && (subAr.right != null)){  // aqui revisamos que las alturas no son nulas 
            subAr.fe=subAr.right.fe + 1; //Aqui actualizamos la altura dado que se añadio un nuevo nodo a la derecha 
        }else{
            if((subAr.right == null) && (subAr.left != null)){
                subAr.fe=subAr.left.fe + 1;  //Aqui actualizamos la altura dado que se añadio un nuevo nodo a la izquierda
            }else{
                subAr.fe=Math.max(obtenerFE(subAr.left), obtenerFE(subAr.right)) + 1;  //Aqui actualizamos la altura dado todo el arbol
            }
        }
        return nuevoPadre;
    }
    //insertar normal
    public void Insertar(int data){
        Node nuevo = new Node(data); 
        if (root == null){ // Si la raiz es nula , añadimos el elemento como raiz
            root = nuevo;
        }else{
            root = InsertarAVL(nuevo, root); // Si no es nula la raiz, vamos a agregarlo y balancear el arbol
        }
    }
    //obtener factor de equilibrio
    public int obtenerFE(Node x){
        if(x == null){
            return -1;
        }else{
            return x.fe;
        }
    }
    //rotacion simple izquierda
    public Node RotacionSimpleIzquierda(Node x){
        Node hijo = x.left;
        x.left = hijo.right;
        hijo.right = x;
        //nuevo factor de equilibrio de x e hijo
        x.fe = Math.max(obtenerFE(x.left), obtenerFE(x.right)) + 1;  //obtiene el maximo
        hijo.fe = Math.max(obtenerFE(hijo.left), obtenerFE(hijo.right)) + 1;
        return hijo;
    }
    //rotacion simple derecha
    public Node RotacionSimpleDerecha(Node x){
        Node hijo = x.right;
        x.right = hijo.left;
        hijo.left = x;
        //nuevo factor de equilibrio de x e hijo
        x.fe = Math.max(obtenerFE(x.left), obtenerFE(x.right)) + 1;  //obtiene el maximo
        hijo.fe = Math.max(obtenerFE(hijo.left), obtenerFE(hijo.right))+1;
        return hijo;
    }
    //rotacion doble a la der
    public Node RotacionDobleIzquierda(Node x){
        Node temp;
        x.left = RotacionSimpleDerecha(x.left);
        temp = RotacionSimpleIzquierda(x);
        return temp;
    }
    //rotacion doble a la izq
    public Node rotacionDobleDerecha(Node x){
        Node temp;
        x.right = RotacionSimpleIzquierda(x.right);
        temp = RotacionSimpleDerecha(x);
        return temp;
    }
    public boolean Buscar( Node node, int key){
        if (node == null){
            return false;
        }
        if (node.data == key){
            return true;
        }
        //Buscar en el subarbol izq
        boolean found1 = Buscar(node.left, key);
        if(found1 == true){
            return true;
        }
        //Buscar en el subarbol der
        boolean found2 = Buscar(node.right, key);
        return found2;
    }
    public void Tio(Node node, int key, int dad, int ky){
        if (node == null){
            return;
        }
        if (node.data == key){
            if(node.left.data != dad){
                System.out.println("El tio de " + ky + " es: " + node.left.data);
            }
            if(node.right.data != dad){
                System.out.println("El tio de " + ky + " es: " + node.right.data);
            }
        }else{
            Tio(node.left, key, dad, ky);
            Tio(node.right, key, dad, ky);
        }
    }   
    public boolean RecorridoNivel(Node node, int nivel){
        if (node == null){//caso base
            return false;
        }
        if (nivel == 0){
            System.out.print(node.data + " ");
            return true;
        }
        boolean left = RecorridoNivel(node.left, nivel-1);
        boolean right = RecorridoNivel(node.right, nivel-1);
        return left || right;
    }


    public void Borrar(Node node, int key,int nod){
        if (node == null){
            return;
        }
        if (node.data == key){ //Encontramos al padre del nodo a borrar
            if(node.left.data == nod){ 
                node.left = null;
            }           // Buscamos cual rama es y la  eliminamos 
            if(node.right.data == nod){
                node.right = null;
            }
        }else{
            Borrar(node.left, key,nod);
            Borrar(node.right, key,nod);
        }
    }
    public static int Nivel(Node root){
        if (root == null){      //caso base: el árbol vacío tiene una altura de 0
            return 0;
        }
        //recorre subarbol izq y der y obtiene la profundidad máxima
        int n = 1 + Math.max(Nivel(root.left), Nivel(root.right));
        return n;
    }
    public void Altura( Node node, int key){
        if (node == null){
            return;
        }
        if (node.data == key){  //hallamos el nodo
            int h = Nivel(node) - 1;
            System.out.println("La altura es: "+ h);
        }else{
            Altura(node.left, key);
            Altura(node.right,key);
        }
    }
    public int pa=0;
    public int Padre(Node node,  int val, int  parent){  
        if (node == null){
            return 0;
        }
        if (node.data == val){ //encontramos al padre del nodo
            pa=parent;
        }else{
            Padre(node.left, val, node.data); //recorremos subarbol izq
            Padre(node.right, val, node.data);  //recorremos subarbol der
              return pa; 
        }
        return pa;
    }

    
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        balanceado tree = new balanceado();
        tree.root = new Node(8);  //arbol AVL
        tree.root.left = new Node(3);
        tree.root.left.left =new Node(1);
        tree.root.left.right = new Node(6);
        tree.root.left.right.left = new Node(4);
        tree.root.left.right.right = new Node(7);
        tree.root.right = new Node(13);
        tree.root.right.right = new Node(14);
        tree.root.right.left = new Node(10);
        int op;
        int seguir = 1;
        do{
            System.out.println("El arbol  es: ");
            int nivel = 0;
            while (tree.RecorridoNivel(tree.root, nivel) == true){
                System.out.println("\n");
                nivel++;
            }
            System.out.println("\nIngrese accion a realizar: \n1. Insertar nodo");
            System.out.println("2. Eliminar nodo");
            System.out.println("3. Buscar nodo");
            System.out.println("4. Recorrido en orden por nivel");
            System.out.println("5. Altura dado un nodo");
            System.out.println("6. Encontrar el abuelo y tio de un nodo");
            op = in.nextInt();
            while (op < 1 || op > 6){
                System.out.println("Opcion invalida \nIngrese accion a realizar: \n1. Insertar nodo");
                System.out.println("2. Eliminar nodo");
                System.out.println("3. Buscar nodo");
                System.out.println("4. Recorrido en orden por nivel");
                System.out.println("5. Altura dado un nodo");
                System.out.println("6. Encontrar el abuelo y tio de un nodo");
                op = in.nextInt();
            }
            switch (op){
                case 1:
                    System.out.println("Ingrese nodo a insertar");
                    int key = in.nextInt();
                    boolean found = tree.Buscar(tree.root, key);
                    while (found == true){
                        System.out.println("Nodo existente");
                        System.out.println("Ingrese nodo a insertar");
                        key = in.nextInt();
                        found = tree.Buscar(tree.root, key);
                    }
                    System.out.println("Nodo a insertar: " + key);
                    tree.Insertar(key);
                    int nivels = 0;
                    System.out.println("El arbol actualizado es: ");
            while (tree.RecorridoNivel(tree.root, nivels) == true){
                System.out.println("\n");
                nivels++;
            }
                    break;
                case 2:
                    System.out.println("Ingrese nodo a eliminar");
                    int key2 = in.nextInt();
                    boolean found2 = tree.Buscar(tree.root, key2);
                    while (found2 == false){
                        System.out.println("Nodo inexistente");
                        System.out.println("Ingrese nodo a eliminar");
                        key2 = in.nextInt();
                        found2 = tree.Buscar(tree.root, key2);
                    }
                    System.out.println("Nodo a eliminar: " + key2);
                    if (key2 == tree.root.data){
                        tree.root = null;     
                        System.out.println("El arbol quedo vacio");
                    }else{
                        int padre = tree.root.data;
                        int dad = 1;
                        dad = tree.Padre(tree.root, key2, padre) ;
                        tree.Borrar(tree.root,dad,key2);
                        int nivelt = 0;
                    while (tree.RecorridoNivel(tree.root, nivelt) == true){
                        System.out.println("\n");
                        nivelt++;
                    }
                    }
                    break;
                case 3:
                    System.out.println("Ingrese nodo a buscar");
                    int key3 = in.nextInt();
                    boolean found3 = tree.Buscar(tree.root, key3);
                    if (found3 == true){
                        System.out.println("El nodo " + key3 + " existe en el arbol");
                    }else{
                        System.out.println("El nodo " + key3 + " no existe en el arbol");
                    }
                    break;
                case 4:
                    System.out.println("Recorrido en orden por nivel\n");
                    int nivelts = 0;
                    while (tree.RecorridoNivel(tree.root, nivelts) == true){
                        nivelts++;
                    }
                    break;
                case 5:
                    System.out.println("Ingrese nodo");
                    int key5 = in.nextInt();
                    boolean found5 = tree.Buscar(tree.root, key5);
                    while (found5 == false){
                        System.out.println("Nodo inexistente");
                        System.out.println("Ingrese nodo");
                        key5 = in.nextInt();
                        found5 = tree.Buscar(tree.root, key5);
                    }
                    tree.Altura(tree.root, key5);
                    break;
                case 6:
                    System.out.println("Ingrese nodo a buscar abuelo y tio");
                    int key6 = in.nextInt();
                    boolean found6 = tree.Buscar(tree.root, key6);
                    while (found6 == false){
                        System.out.println("Nodo inexistente");
                        System.out.println("Ingrese nodo");
                        key6 = in.nextInt();
                        found6 = tree.Buscar(tree.root, key6);
                    }
                    if (key6 == tree.root.data){
                        System.out.println("abuelo: null");
                        System.out.println("tio: null");
                    }else{
                        if (key6 == tree.root.right.data || key6 == tree.root.left.data){
                            System.out.println("abuelo: null");
                            System.out.println("tio: null");
                        }else{
                            int padre = tree.root.data;
                            int dad=1;
                            dad = tree.Padre(tree.root, key6, padre) ;
                            int abuelo=0;
                            abuelo=tree.Padre(tree.root, dad, padre) ;
                            System.out.println("El abuelo de " + key6 + " es : " + abuelo);
                            tree.Tio( tree.root,abuelo, dad,  key6) ;  
                        }
                    }
                   break;
                default:
                    System.out.println("No es opcion");
            }
            System.out.println("\nDesea realizar otra accion? 1.Si  2.No");
            seguir = in.nextInt();
            while (seguir < 1 || seguir > 2){
                System.out.println(seguir + " no es opcion");
                System.out.println("\nDesea realizar otra accion? 1.Si  2.No");
                seguir = in.nextInt();
            }
        }while (seguir == 1);
    }
}

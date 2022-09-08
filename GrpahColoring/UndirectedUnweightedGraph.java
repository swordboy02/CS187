package graph;
import java.util.ArrayList;
/**
 * This class implements general operations on a graph as specified by UndirectedGraphADT.
 * It implements a graph where data is contained in Vertex class instances.
 * Edges between verticies are unweighted and undirected.
 * A graph coloring algorithm determines the chromatic number. 
 * Colors are represented by integers. 
 * The maximum number of vertices and colors must be specified when the graph is instantiated.
 * You may implement the graph in the manner you choose. See instructions and course material for background.
 */
 
 public class UndirectedUnweightedGraph<T> implements UndirectedGraphADT<T> {
   // private class variables here.
   
   private int MAX_VERTICES;
   private int MAX_COLORS;
   private ArrayList<Vertex<T>> vertex;
   private boolean adjMatrix[][];
   private int numEdges;
   
   /**
    * Initialize all class variables and data structures. 
   */   
   public UndirectedUnweightedGraph (int maxVertices, int maxColors){
      MAX_VERTICES = maxVertices;
      MAX_COLORS = maxColors; 
      vertex = new ArrayList<Vertex<T>>();
      adjMatrix = new boolean[maxVertices][maxVertices];
      numEdges = 0;
   }

   /**
    * Add a vertex containing this data to the graph.
    * Throws Exception if trying to add more than the max number of vertices.
   */
   public void addVertex(T data) throws Exception {
     if(vertex.size() >= MAX_VERTICES) throw new Exception();
     Vertex tempAdd = new Vertex(data);
     vertex.add(tempAdd);
   }
   
   /**
    * Return true if the graph contains a vertex with this data, false otherwise.
   */   
   public boolean hasVertex(T data){
     for(int i=0; i<vertex.size(); i++){
       if(data.equals(vertex.get(i).getData())){
         return true;
       }
     }
     return false;
   } 

   /**
    * Add an edge between the vertices that contain these data.
    * Throws Exception if one or both vertices do not exist.
   */   
   public void addEdge(T data1, T data2) throws Exception {
       boolean temp1 = false;
       boolean temp2 = false;
       for (int i = 0; i < vertex.size(); i++) {
           if (data1.equals(vertex.get(i).getData())){
               temp1 = true;
           }
           if (data2.equals(vertex.get(i).getData())){
               temp2 = true;
           }
       }
       if (temp1 == false || temp2 == false) throw new Exception();
       numEdges++;
       int vertOne = -1;
       int vertTwo = -1;
       for (int i = 0; i < vertex.size(); i++) {
           if (data1.equals(vertex.get(i).getData())) {
               vertOne = i;
               break;
           }
       }
       for (int i = 0; i < vertex.size(); i++) {
           if (data2.equals(vertex.get(i).getData())) {
               vertTwo = i;
               break;
           }
       }
       adjMatrix[vertOne][vertTwo] = true;
       adjMatrix[vertTwo][vertOne] = true;
   }

   /**
    * Get an ArrayList of the data contained in all vertices adjacent to the vertex that
    * contains the data passed in. Returns an array of zero length if no adjacencies exist in the graph.
    * Throws Exception if a vertex containing the data passed in does not exist.
   */   
   public ArrayList<T> getAdjacentData(T data) throws Exception{
       ArrayList<T> output = new ArrayList<T>();
       int vert=-1;
       for (int i = 0; i < vertex.size(); i++) {
           if (data.equals(vertex.get(i).getData())) {
               vert = i;
               break;
           }
       }
       for (int i = 0; i < vertex.size(); i++) {
           if(adjMatrix[vert][i] == true){
               output.add(vertex.get(i).getData());
           }
       }
       return output;
   }
   
   /**
    * Returns the total number of vertices in the graph.
   */   
   public int getNumVertices(){
      return vertex.size();
   }

   /**
    * Returns the total number of edges in the graph.
   */   
   public int getNumEdges(){
      return numEdges;
   }

   /**
    * Returns the minimum number of colors required for this graph as 
    * determined by a graph coloring algorithm.
    * Throws an Exception if more than the maximum number of colors are required
    * to color this graph.
   */   
   public int getChromaticNumber() throws Exception{
       ArrayList<Vertex<T>> temp = new ArrayList<Vertex<T>>();
       int highestColourUsed = -1;
       int colourToUse = -1;
       for(int i = 0; i < vertex.size(); i++){
           if(vertex.get(i).getColor() == -1){
               int[] temp1 = new int[MAX_COLORS];
               for(int j =0; j<temp1.length; j++){
                   temp1[j] = 0;
               }
               temp = getAdjacentVert(vertex.get(i));
               for(int j =0; j<temp.size(); j++){
                   if(temp.get(j).getColor() != -1) {
                       temp1[temp.get(j).getColor()]++;
                   }
               }
               for(int j =0; j<temp1.length; j++){
                   if(temp1[j] == 0){
                       break;
                   }
                   if(j == temp1.length-1) throw new Exception();
               }
               for(int j =0; j<temp1.length; j++){
                   if(temp1[j] == 0){
                       vertex.get(i).setColor(j);
                       if(j > highestColourUsed) {
                            highestColourUsed = j;
                       }
                       break;
                   }
               }
           }
       }
       return highestColourUsed+1;
   }
    private ArrayList<Vertex<T>> getAdjacentVert(Vertex<T> data) throws Exception{
        ArrayList<Vertex<T>> output = new ArrayList<Vertex<T>>();
        int vert=-1;
        for (int i = 0; i < vertex.size(); i++) {
            if (data.getData().equals(vertex.get(i).getData())) {
                vert = i;
                break;
            }
        }
        for (int i = 0; i < vertex.size(); i++) {
            if(adjMatrix[vert][i] == true){
                output.add(vertex.get(i));
            }
        }
        return output;
    }
   
   
}
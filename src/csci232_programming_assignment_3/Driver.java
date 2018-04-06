
package csci232_programming_assignment_3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * Authors: Rusty Clayton, Kamran Hetke, Derek Jacobson
 * Date: 4/6/2018
 * Overview: takes an input file adjacency matrix and implements Prim's MST, Kruskals MST and Floyd Warshals algorithm
 * 
 */
public class Driver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
            
        //calls the Floyd_Worshalls function    
        Floyd_Worshalls(readFiles("input"));
        
        //calls the Prim function
        Prim(readFiles("undirectedInput"));
        
        
    }
    
    
    
    
    public static String[][] readFiles(String inputFileName){
        int count1=0;
        int count2=0;
        String inputArray[][]=null;
        Path inputFile = Paths.get(inputFileName+".txt");

            try (InputStream in = Files.newInputStream(inputFile);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
                String line = null;

                while ((line = reader.readLine()) != null) {

                    count2= line.split(",").length;
                    count1++;                    
                }
                
                inputArray= new String[count1][count2];
                count1=0;
                count2=0;
                reader.close();
            } catch (IOException x) {
                System.err.println(x);
            }
            
        
            try (InputStream in = Files.newInputStream(inputFile);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
                String line = null;
                
                while ((line = reader.readLine()) != null) {
                    String [] splitLines=line.split(",");
                    
                    inputArray[count1]= splitLines;
                    
                    count1++;
                    
                    
                    
                }
                reader.close();
                
            } catch (IOException x) {
                System.err.println(x);
            }
        return inputArray;
    }
    
  
    
    
    // Function to implement Prim's algorithm 
    public static void Prim(String[][] adjacencyMatrix)
    {
        String [][] prim_String= new String[adjacencyMatrix.length-1][adjacencyMatrix[0].length];
        int [][] prim_Alg = new int[adjacencyMatrix.length-1][adjacencyMatrix[0].length];
        
        //copies adjancey matrix
        for (int i = 1; i<adjacencyMatrix.length;i++){
            for (int j=0; j<adjacencyMatrix[1].length; j++){
                prim_String[i-1][j] = adjacencyMatrix[i][j];
            }
        }
        
        
        //For loop to turn the graph 2D array of strings into a graph 2D array of integers
        for(int i = 0; i < prim_String.length; i++)
        {
            for(int j = 0; j < prim_String.length; j++)
            {
                if(prim_String[i][j] == "∞")
                {
                    prim_Alg[i][j] = 99999;
                }
                
                else
                {
                     prim_Alg[i][j] = Integer.parseInt(prim_String[i][j]);
                }
                
            }
            
        }
        
        // Array that stores constructed Minimum spanning tree
        int parent[] = new int[prim_Alg.length];
        
        // Key values to pick the minimum weight in the edge cuts
        int key[] = new int[prim_Alg.length];
        
        // Representation of the vertices that aren't in the tree yet
        Boolean treeSet[] = new Boolean[prim_Alg.length];
        
        //Make all the keys infinite(max value)
        for(int i = 0; i < prim_Alg.length; i++)
        {
            key[i] = Integer.MAX_VALUE;
            treeSet[i] = false; 
        }
        
        key[0] = 0; 
        
        parent[0] = -1; //First node of tree is the root
        
        
        for(int i = 0; i < (prim_Alg.length - 1); i++)
        {
            int u = minimumKey(key, treeSet, prim_Alg.length); //finds minimum key value from vertices not in the tree
            
            treeSet[u] = true;  // Add that minimum key value to the tree set
            
            for(int j = 0; j < prim_Alg.length; i++)
            {
                if(prim_Alg[u][j] != 0 && treeSet[j] == false && prim_Alg[u][j] < key[j])
                {
                    parent[j] = u;
                    key[j] = prim_Alg[u][j];
                }
            }
            
            
        }
        print_Prim(parent, prim_Alg.length, prim_Alg);  //Calls the print_Prim function 
    }
    
    //Function to find the vertex that has the minimum key from vertices not in the minimum spanning tree
    static int minimumKey(int key[], Boolean treeSet[], int n)
    {
        int minimum = Integer.MAX_VALUE;
        int minimum_Index = -1;
        
        for(int i = 0; i < n; i++)
        {
            if(treeSet[i] == false && key[i] < minimum)
            {
                minimum = key[i];
                minimum_Index = i;
            }
        }
        
        return minimum_Index;
    }
    
    
    
    //Fucntion to print the edge weights from the Prim algorithm function
    public static void print_Prim(int parent[], int n, int graph[][])
    {
        System.out.println("Edge Weight");
        
        for(int i = 1 ; i < n; i++)
        {
            System.out.println(parent[i]+ " - " + i + " " + graph[i][parent[i]]);
        }
    }
    
    
    public static void Floyd_Worshalls(String [][] adjacencyMatrix){
        
        String [][] fWorshalls= new String[adjacencyMatrix.length-1][adjacencyMatrix[0].length];
        //copies adjancey matrix
        for (int i = 1; i<adjacencyMatrix.length;i++){
            for (int j=0; j<adjacencyMatrix[1].length; j++){
                fWorshalls[i-1][j] = adjacencyMatrix[i][j];
            }
        }
        
        //zeroes the center diagonal row(vertices are immediately adjacent to themselves)
        for (int i = 0; i<fWorshalls.length;i++){
            
            fWorshalls[i][i] = "0";            
        }
        //triply nested loop that calculates the shortest paths between all adjacent vertices
        
        for (int i = 0; i<fWorshalls.length;i++){          
            for (int j=0; j<fWorshalls.length; j++){
                for (int k=0; k<fWorshalls.length;k++){
                    
                    if(fWorshalls[j][i].charAt(0)=='∞'||fWorshalls[i][k].charAt(0)=='∞'){
                        //do nothing if these are infinite, there is not path
                    }                    
                    else if(fWorshalls[j][k].charAt(0)=='∞'){
                        fWorshalls[j][k]=Integer.parseInt(fWorshalls[j][i])+Integer.parseInt(fWorshalls[i][k])+"";
                    }
                    else if (Integer.parseInt(fWorshalls[j][k])>(Integer.parseInt(fWorshalls[j][i])+Integer.parseInt(fWorshalls[i][k]))){
                        fWorshalls[j][k]=Integer.parseInt(fWorshalls[j][i])+Integer.parseInt(fWorshalls[i][k])+"";
                    }
                    
                }
                printMatrix(fWorshalls, adjacencyMatrix[0]);
            }   
        }
        
    }
    
    public static void printMatrix(String [][] input, String [] header){
        // iterates through a given matrix and array and prints it with a set format
        System.out.print("  ");
        for(int l=0; l<header.length-1; l++){
                System.out.print(header[l]+",  ");
                
        }
        System.out.println(header[header.length-1]);
        for(int l=0; l<input.length; l++){
                System.out.print(header[l]+":");
                for (int m=0; m<input[0].length-1;m++){
                    System.out.print(input[l][m]+", ");
                }                
                System.out.println(input[l][input[l].length-1]);
        }
        System.out.println("\n\n");
    }

    
}

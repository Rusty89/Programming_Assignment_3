
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
 * Date: 4/5/2018
 * Overview: takes an input file adjacency matrix and implements Prim's MST, Kruskals MST and Floyd Warshals algorithm
 * 
 */
public class Driver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int count1=0;
        int count2=0;
        String inputArray[][]=null;
        Path inputFile = Paths.get("input.txt");

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
            
        //calls the Floyd_Worshalls function    
        Floyd_Worshalls(inputArray);
            
        
        
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

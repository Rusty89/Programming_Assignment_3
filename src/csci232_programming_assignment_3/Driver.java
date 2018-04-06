
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
        
        Kruskals(readFiles("undirectedInput"));
        
        
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
    
    public static void Kruskals(String [][] adjacencyMatrix) {
    	
        int [][] kruskals= new int[adjacencyMatrix.length-1][adjacencyMatrix[0].length];
    	for (int i = 1; i<adjacencyMatrix.length;i++){
            for (int j=0; j<adjacencyMatrix[1].length; j++){
            	if (adjacencyMatrix[i][j].charAt(0) == '∞') {
                	kruskals[i-1][j] = 0;
                } else {
                	kruskals[i-1][j] = Integer.parseInt(adjacencyMatrix[i][j]);
                }
            }
        }
    	
    	String [] mst = new String[kruskals.length-1];
    	int [] mst_candidate = new int[3];
    	
    	for (int i=0; i < kruskals[0].length-1; i++) {
    		mst_candidate[0] = kruskals[0][0];
    		mst_candidate[1] = 0;
    		mst_candidate[2] = 0;
	    	for (int j=0; j < kruskals.length; j++) {
	    		for (int k=0; k < kruskals[0].length; k++) {
	    			if (mst_candidate[0] == 0) {
	    				mst_candidate[0] = kruskals[j][k];
	    				mst_candidate[1] = j;
	    				mst_candidate[2] = k;
	    			} else if (kruskals[j][k] < mst_candidate[0] && kruskals[j][k] != 0) {
	    				mst_candidate[0] = kruskals[j][k];
	    				mst_candidate[1] = j;
	    				mst_candidate[2] = k;
	    			}
	    			
	    		}
	    		
	    	}
	    	
	    	mst[i] = adjacencyMatrix[0][mst_candidate[1]] + adjacencyMatrix[0][mst_candidate[2]];
	    	kruskals[mst_candidate[1]][mst_candidate[2]] = 0;
	    	kruskals[mst_candidate[2]][mst_candidate[1]] = 0;	    	
    	}
    	
    	printMST(mst);
    	
    }
    
    public static void printMST(String[] mst) {
    	
    	for(int i = 0; i < mst.length; i++) {
    		System.out.println(mst[i]);
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

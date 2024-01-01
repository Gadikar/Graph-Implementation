import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;





public class Floyd {

    public static void main(String args[]) {
        Map<String, Matrix> data = new LinkedHashMap<>();
     
        try {
		
        	File file = new File(args[0]);
        	
			Scanner sc=new Scanner(file);
			while(sc.hasNextLine()) {
				String line = sc.nextLine();
				if(line.length() == 0) continue;
				if(line.contains("Problem")) {
					String[] parts = line.split(" ");
					int matrixSize = Integer.parseInt(parts[parts.length-1]);
					int[][] matrix = new int[matrixSize][matrixSize];
					for(int r =0; r<matrixSize; r++) {
						Scanner matrixLine = new Scanner(sc.nextLine());
						int col = 0;
						while(matrixLine.hasNext()) {
							if(matrixLine.hasNextInt()) {
								matrix[r][col] = matrixLine.nextInt();
								col++;
							}
						}
					}

                    Matrix matrixData = new Matrix();
                    matrixData.setN(matrixSize); 
                    matrixData.setInput(matrix);

                    data.put(line, matrixData);
				}
			}
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } 

        for (Map.Entry<String,Matrix> entry : data.entrySet()) {
            //System.out.println(entry.getKey());
            Matrix m = entry.getValue();
            
            floyd_graph(m);
         
        }
        
        try {	
            // Create a FileWriter object to write in the file
            FileWriter fWriter = new FileWriter("output_floyd.txt");
            
            for (Map.Entry<String,Matrix> entry : data.entrySet()) {
                fWriter.write(entry.getKey());
                fWriter.write("\r\n");
                fWriter.write("P Matrix: \n");
                Matrix m = entry.getValue();
                
                for(int i=0;i<m.getOutput().length; i++) {
                    for(int j=0;j<m.getOutput().length; j++) {
                        fWriter.write(m.getOutput()[i][j] + " ");
                    }
                    fWriter.write("\n");
                }
                fWriter.write("\n");
                
               //display shortest distance of all edges
                for(int i=0;i<m.getN();i++) {
                	 fWriter.write("V"+(i+1)+"-Vj:shortest path and length");
                	 fWriter.write("\n");
                	for(int j=0;j<m.getN();j++) {
                			
                		    fWriter.write("V" + (i + 1)+" ");
                			printPath(i,j,m.getN(),m.getOutput(),fWriter);
                			 fWriter.write("V" + (j + 1)+ " : "+ m.getMat()[i][j]);
                			 fWriter.write("\n");
                	}
                	fWriter.write("\n");
               }
                fWriter.write("\n");
               
        }
            
            fWriter.close();      
            
    }catch (IOException e) {
        // Catch block to handle if exception occurs
        System.out.print(e.getMessage());
    }
        
    }

    //to print intermediate nodes
    public static void printPath(int q, int r,int n,int p[][],FileWriter fWriter) {
    	
    	if(p[q][r]!=0) {
    		printPath(q,p[q][r]-1,n,p,fWriter);
    		try {
				fWriter.write("V"+p[q][r]+" ");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		printPath(p[q][r]-1,r,n,p,fWriter);
    	}
    	return;
    }
   
   
   
private static void floyd_graph(Matrix m) {

    int n = m.getN();  
    int[][] g = m.getInput();
	int[][] p = new int[n][n];
        int i, j = 0, k;
       
		//initialise matrix  to 0
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                p[i][j] = 0;
            }
        }

        //floyd matrix evaluation
        for (k = 0; k < n; k++) { 
            for (i = 0; i < n; i++) { 
                for (j = 0; j < n; j++) { 
                	//calculating minimum distance of nodes
                    if (g[i][j] > g[i][k] + g[k][j]) {                     
                        g[i][j] = g[i][k] + g[k][j];
                        p[i][j] = k + 1;			
                    }
                }
            }
           
        }
        
       m.setMat(g);
       m.setOutput(p);
             
    }
}




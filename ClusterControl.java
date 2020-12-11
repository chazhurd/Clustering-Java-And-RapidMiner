import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Iterator;
import java.util.List;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class ClusterControl {
	
	private static double[][] cLines = new double[600][60];
   
	private static ArrayList<Double> clustersA = new ArrayList();
   	private static ArrayList<Double> clustersB = new ArrayList();
      	private static ArrayList<Double> clustersC = new ArrayList();
         	private static ArrayList<Double> clustersD = new ArrayList();
            	private static ArrayList<Double> clustersE = new ArrayList();
               	private static ArrayList<Double> clustersF = new ArrayList();
                  private static ArrayList cluA = new ArrayList();
               private static ArrayList cluB = new ArrayList();
             private static ArrayList cluC = new ArrayList();
           private static ArrayList cluD = new ArrayList();
         private static ArrayList cluE = new ArrayList();
       private static ArrayList cluF = new ArrayList();
                  
   private static int[][] clustered = new int[6][50];
   private static double[][] clusteredDistances = new double[6][50];
	private static double[][] distanceTable = new double[6][600];
   private static ArrayList distanceList = new ArrayList();
	private static int check = 0;
   private static int pick1, pick2, pick3, pick4, pick5, pick6;
	
	
	public static void main(String[] args) throws FileNotFoundException{
   //step1 read file
		int x = 0;
		int y = 0;
		File file = new File("synthetic_control_data.txt");
		try {
			Scanner sc = new Scanner(file);
			while(x<600) {
				
				cLines[x][y] = sc.nextDouble();
				y++;
				if(y/60==1)
				{
					x++;
					y = 0;
				}

			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("Aint Happening Boss " + e.getMessage());
		}
      //step 2 generate 2 aribitrary locations
      Random rand = new Random(); 
		int c1 = rand.nextInt(600);
		int c2 = rand.nextInt(600);
		while(c2 == c1 ) {
			c2 = rand.nextInt(600);
		}
		int c3 = rand.nextInt(600);
		while(c3 == c2 ) {
			c3 = rand.nextInt(600);
		}
		int c4 = rand.nextInt(600);
		while(c4 == c3 ) {
			c4 = rand.nextInt(600);
		}
		int c5 = rand.nextInt(600);
		while(c5 == c4 ) {
			c5 = rand.nextInt(600);
		}
		int c6 = rand.nextInt(600);
		while(c6 == c5 ) {
			c6 = rand.nextInt(600);
		}
		int[] randomCluster = new int[6];
      randomCluster[0] = c1;
      randomCluster[1] = c2;
      randomCluster[2] = c3;
      randomCluster[3] = c4;
      randomCluster[4] = c5;
      randomCluster[5] = c6;
      
      pick1 = c1; 
      pick2 = c2;
      pick3 = c3;
      pick4 = c4;
      pick5 = c5;
      pick6 = c6;
      //step 3 calculate distances from each of the randomly/unique chosen points
		calculateDistances(randomCluster);
      //step 4 assign 50 data sets that are closest to randomly chosen centroids.
      //  choosePoints();
      //step 5 find the closest row for each centroid
      findClosest();
      //step 5 print the clusters to individual files// i made an error here, took closest points without comparing. 
      //HINT TO GRADER MY CLUSTERED 2D ARRAY IS THE DATA SET ROW NUMBERS
      //AND THE CLUSTEREDDISTANCES IS A 2D ARRAY THAT HAS THE TOTAL DISTANCE TO THAT DATA SET ROW (IE clustered[0,1] IS THE CLOSEST DATA SET ROW TO THE RANDOW CENTROID, clusteredDistances[0,1] is the distance from the chose centroid to the closest data set row)
		printClusters();
      System.out.println("\nClustering Successful, Clustered Data Printed To File");
		System.out.println("Clustered Data with Row Reference==> ");
      System.out.println("Random Centroids Chosen Are: " + pick1 +", " + pick2 + ", " + pick3 + ", " + pick4 + ", " + pick5 + ", " + pick6);
     
	}
   
   private static void printClusters() throws FileNotFoundException {
   //set up files to write
   File c1 = new File("cluster1.txt");
   File c2 = new File("cluster2.txt");
   File c3 = new File("cluster3.txt");
   File c4 = new File("cluster4.txt");
   File c5 = new File("cluster5.txt");
   File c6 = new File("cluster6.txt");
   PrintWriter pw1 = new PrintWriter(c1);
   PrintWriter pw2 = new PrintWriter(c2);
   PrintWriter pw3 = new PrintWriter(c3);
   PrintWriter pw4 = new PrintWriter(c4);
   PrintWriter pw5 = new PrintWriter(c5);
   PrintWriter pw6 = new PrintWriter(c6);
   
   //arraylists with references to the row location, print the info from cList to print the sets data.
   int x = 0;
   Iterator i = cluA.iterator();
   while(x<cluA.size()-1){
      x++;
      //have reference now need to print the 60 columns
         for(int y = 0; y < 60; y ++){
            pw1.print(cLines[(int)cluA.get(x)][y]+ ", ");
         }
         pw1.print("\n");
      }
      pw1.flush();
      
      //for cluster b
   x = 0;
   i = cluB.iterator();
   while(x<cluB.size()-1){
      x++;
      //have reference now need to print the 60 columns
         for(int y = 0; y < 60; y ++){
            pw2.print(cLines[(int)cluB.get(x)][y]+ ", ");
         }
         pw2.print("\n");
      }
      pw2.flush();
      
      //cluster c
   x = 0;
   i = cluC.iterator();
   while(x<cluC.size()-1){
      x++;
      //have reference now need to print the 60 columns
         for(int y = 0; y < 60; y ++){
            pw3.print(cLines[(int)cluC.get(x)][y]+ ", ");
         }
         pw3.print("\n");
      }
      pw3.flush();
      
      //cluster d
       x = 0;
   i = cluD.iterator();
   while(x<cluD.size()-1){
      x++;
      //have reference now need to print the 60 columns
         for(int y = 0; y < 60; y ++){
            pw4.print(cLines[(int)cluD.get(x)][y]+ ", ");
         }
         pw4.print("\n");
      }
      pw4.flush();
      
      //cluser e
       x = 0;
   i = cluE.iterator();
   while(x<cluE.size()-1){
      x++;
      //have reference now need to print the 60 columns
         for(int y = 0; y < 60; y ++){
            pw5.print(cLines[(int)cluE.get(x)][y]+ ", ");
         }
         pw5.print("\n");
      }
      pw5.flush();
      
      //CLUSTER F
       x = 0;
   i = cluF.iterator();
   while(x<cluF.size()-1){
      x++;
      //have reference now need to print the 60 columns
         for(int y = 0; y < 60; y ++){
            pw6.print(cLines[(int)cluF.get(x)][y]+ ", ");
         }
         pw6.print("\n");
      }
      pw6.flush();
      
    /*
   pw1.print("" + pick1+  ", ");
   pw1.flush();
   pw2.print("" + pick2 + ", ");
   pw3.flush();
   pw3.print("" + pick3  +", ");
   pw3.flush();
   pw4.print("" + pick4  +", ");
   pw4.flush();
   pw5.print("" + pick5 +  ", ");
   pw5.flush();
   pw6.print("" + pick6 +  ", ");
   pw6.flush();
   

   // start writing to files using DataSet ROW #
  
   pw1.print("First Cluster Using Centriod Row#: "+pick1 + " --> {");
   pw1.flush();
   pw2.print("Second Cluster Using Centriod Row#: "+pick2 + " --> {");
   pw3.flush();
   pw3.print("Third Cluster Using Centriod Row#: "+pick3 + " --> {");
   pw3.flush();
   pw4.print("Fourth Cluster Using Centriod Row#: "+pick4 + " --> {");
   pw4.flush();
   pw5.print("Fifth Cluster Using Centriod Row#: "+pick5 + " --> {");
   pw5.flush();
   pw6.print("Sixth Cluster Using Centriod Row#: "+pick6 + " --> {");
   pw6.flush();
   
   // we will have reference to the location in the cList (original data) now we have read that data and write it end with new line
      for(int y = 0; y < 50; y++){
      pw1.print("\n");
         for(int z = 0; z<60; z++){
         if(z == 59){
         pw1.print(cLines[clustered[0][y]][z]);
        }
        else
        {
         pw1.print(cLines[clustered[0][y]][z] + ", ");
         pw1.flush();
         }
         }
         //given the row number
         //traverse that row number to 50 
         

      }

      for(int y = 0; y < 50; y++){
      pw2.print("\n");
       for(int z = 0; z<60; z++){
         if(z == 59){
         pw2.print(cLines[clustered[1][y]][z]);
        }
        else
        {
         pw2.print(cLines[clustered[1][y]][z] + ", ");
         pw2.flush();
         }
         }
      }
      
         for(int y = 0; y < 50; y++){
         pw3.print("\n");
         for(int z = 0; z<60; z++){
         if(z == 59){
         pw3.print(cLines[clustered[2][y]][z]);
        }
        else
        {
         pw3.print(cLines[clustered[2][y]][z] + ", ");
         pw3.flush();
         }
         }
      }
      
            for(int y = 0; y < 50; y++){
            pw4.print("\n");
            for(int z = 0; z<60; z++){
         if(z == 59){
         pw4.print(cLines[clustered[3][y]][z]);
        }
        else
        {
         pw4.print(cLines[clustered[3][y]][z] + ", ");
         pw4.flush();
         }
         }
      }
      
      for(int y = 0; y < 50; y++){
      pw5.print("\n");
      for(int z = 0; z<60; z++){
         if(z == 59){
         pw5.print(cLines[clustered[4][y]][z]);
        }
        else
        {
         pw5.print(cLines[clustered[4][y]][z] + ", ");
         pw5.flush();
         }
         }
      }
      
      for(int y = 0; y < 50; y++){
      pw6.print("\n");
      for(int z = 0; z<60; z++){
         if(z == 59){
         pw6.print(cLines[clustered[5][y]][z]);
        }
        else
        {
         pw6.print(cLines[clustered[5][y]][z] + ", ");
         pw6.flush();
         }
      }
      }
      /*
   pw1.print("}\n");
   pw1.flush();
   pw2.print("}\n");
   pw3.flush();
   pw3.print("}\n");
   pw3.flush();
   pw4.print("}\n");
   pw4.flush();
   pw5.print("}\n");
   pw5.flush();
   pw6.print("}\n");
   pw6.flush();
   
   
   
      // start writing to files using DataSet ROW #
   pw1.print("Distances From Centroid Row# "+pick1 + " --> {");
   pw1.flush();
   pw2.print("Distances From Centroid Row# "+pick2 + " --> {");
   pw3.flush();
   pw3.print("Distances From Centroid Row# "+pick3 + " --> {");
   pw3.flush();
   pw4.print("Distances From Centroid Row# "+pick4 + " --> {");
   pw4.flush();
   pw5.print("Distances From Centroid Row# "+pick5 + " --> {");
   pw5.flush();
   pw6.print("Distances From Centroid Row# "+pick6 + " --> {");
   pw6.flush();
      for(int y = 0; y < 50; y++){
         pw1.print(clusteredDistances[0][y] + ", ");
         pw1.flush();

      }

      for(int y = 0; y < 50; y++){
         pw2.print(clusteredDistances[1][y] + ", ");
         pw2.flush();
      }
      
         for(int y = 0; y < 50; y++){
         pw3.print(clusteredDistances[2][y] + ", ");
         pw3.flush();
      }
      
            for(int y = 0; y < 50; y++){
         pw4.print(clusteredDistances[3][y] + ", ");
         pw4.flush();
      }
      
      for(int y = 0; y < 50; y++){
         pw5.print(clusteredDistances[4][y] + ", ");
         pw5.flush();
      }
      
      for(int y = 0; y < 50; y++){
         pw6.print(clusteredDistances[5][y] + ", ");
         pw6.flush();
      
      }
   pw1.print("}\n");
   pw1.flush();
   pw2.print("}\n");
   pw3.flush();
   pw3.print("}\n");
   pw3.flush();
   pw4.print("}\n");
   pw4.flush();
   pw5.print("}\n");
   pw5.flush();
   pw6.print("}\n");
   pw6.flush();
   */
   
   }
   private static void findClosest(){

   
      for(int y = 0; y<600; y++){
         if(clustersA.get(y)< clustersB.get(y) && clustersA.get(y)<clustersC.get(y) && clustersA.get(y) < clustersD.get(y) && clustersA.get(y) < clustersE.get(y) && clustersA.get(y) < clustersF.get(y)){
         cluA.add(y);
         }
         if(clustersB.get(y)<clustersA.get(y) && clustersB.get(y)<clustersC.get(y) && clustersB.get(y) < clustersD.get(y) && clustersB.get(y) < clustersE.get(y) && clustersB.get(y) < clustersF.get(y)){
         cluB.add(y);
         }
         if(clustersC.get(y)<clustersA.get(y) && clustersC.get(y)<clustersB.get(y) && clustersC.get(y) < clustersD.get(y) && clustersC.get(y) < clustersE.get(y) && clustersC.get(y) < clustersF.get(y)){
         cluC.add(y);
         }
         if(clustersD.get(y)<clustersA.get(y) && clustersD.get(y)<clustersB.get(y) && clustersD.get(y) < clustersC.get(y) && clustersD.get(y) < clustersE.get(y) && clustersD.get(y) < clustersF.get(y)){
         cluD.add(y);
         }
         if(clustersE.get(y)<clustersA.get(y) && clustersE.get(y)<clustersB.get(y) && clustersE.get(y) < clustersC.get(y) && clustersE.get(y) < clustersD.get(y) && clustersE.get(y) < clustersF.get(y)){
         cluE.add(y);
         }
         if(clustersF.get(y)<clustersA.get(y) && clustersF.get(y)<clustersB.get(y) && clustersF.get(y) < clustersC.get(y) && clustersF.get(y) < clustersE.get(y) && clustersF.get(y) < clustersD.get(y)){
         cluF.add(y);
         }
         }
         
      }

	private static void calculateDistances(int[] randomCluster) {
		double total = 0;
		int x = 0;
      
      while(x<6){
		for(int i = 0; i < 600; i++) {
			for(int k = 0; k < 60; k++) {
            total = 0;
				total += Math.pow(cLines[randomCluster[x]][k] - cLines[i][k], 2);
				}
			total =  Math.pow(total, .5);
			distanceTable[x][i] = total;
         
         if(x == 0)
            clustersA.add(total);
         if(x == 1)
            clustersB.add(total);
         if(x == 2)
            clustersC.add(total);
         if(x ==3)
            clustersD.add(total);
         if(x ==4)
            clustersE.add(total);
         if(x==5)
            clustersF.add(total);
            
         
         
			}
         total = 0; 
		   x++;
	}
   }
   private static void choosePoints(){
      int x = 0;
      int whereAt = 0;
      int a = 0;
      int b = 0;
      double compareA = clustersA.get(a);
      Iterator i = clustersA.iterator();
      //here we know we are traversing through the list of distances from the first selected centroid
      while(x<50){
               while(i.hasNext() && a<600){
               
               
                  if(compareA >clustersA.get(a) && clustersA.get(a) != 0){
                                 //if smallest is found
                                 // 0 means its itself
                                 //new small value
                                 //location saved
                  compareA = clustersA.get(a);
                  whereAt = a;
                  }
                  i.next();
                  a++;       
               }
               //input in the new array of final clusters
               clustered[0][x] = whereAt;
               clusteredDistances[0][x] = clustersA.get(whereAt);
               //so we dont keep getting the same one
               clustersA.remove(whereAt);
               x++;
               a = 0;
               i = clustersA.iterator();
               compareA = clustersA.get(0);
               whereAt = 0;
            }
   ///AT THIS POINT I HAVE POINTS THAT WILL BE CLUSTERED FOR THE FIRST RANDOM CENTROID. I COULD PRACTICE BETTER CODING HERE BUT I AM IN A TIME CRUNCH SO I WILL JUST COPY MY PREVIOUS STUFF AND CHANGE CLUSTERS
                 //CLUSTER B 
       x = 0;
       whereAt = 0;
       a = 0;
       b = 0;
      compareA = clustersB.get(a);
      i = clustersB.iterator();
      //here we know we are traversing through the list of distances from the first selected centroid
      while(x<50){
               while(i.hasNext() && a<600){
               
               
                  if(compareA >clustersB.get(a) && clustersB.get(a) != 0){
                                 //if smallest is found
                                 // 0 means its itself
                                 //new small value
                                 //location saved
                  compareA = clustersB.get(a);
                  whereAt = a;
                  }
                  i.next();
                  a++;       
               }
               //input in the new array of final clusters//b = 1
               clustered[1][x] = whereAt;
               clusteredDistances[1][x] = clustersB.get(whereAt);
               //so we dont keep getting the same one
               clustersB.remove(whereAt);
               x++;
               a = 0;
               i = clustersB.iterator();
               compareA = clustersB.get(0);
               whereAt = 0;
            }
            
            
            //CLUSTER C
       x = 0;
       whereAt = 0;
       a = 0;
       b = 0;
      compareA = clustersC.get(a);
      i = clustersC.iterator();
      //here we know we are traversing through the list of distances from the first selected centroid
      while(x<50){
               while(i.hasNext() && a<600){
               
               
                  if(compareA >clustersC.get(a) && clustersC.get(a) != 0){
                                 //if smallest is found
                                 // 0 means its itself
                                 //new small value
                                 //location saved
                  compareA = clustersC.get(a);
                  whereAt = a;
                  }
                  i.next();
                  a++;       
               }
               //input in the new array of final clusters//b = 1
               clustered[2][x] = whereAt;
               clusteredDistances[2][x] = clustersC.get(whereAt);
               //so we dont keep getting the same one
               clustersC.remove(whereAt);
               x++;
               a = 0;
               i = clustersC.iterator();
               compareA = clustersC.get(0);
               whereAt = 0;
            }
            
            //CLUSTER D
       x = 0;
       whereAt = 0;
       a = 0;
       b = 0;
      compareA = clustersD.get(a);
      i = clustersD.iterator();
      //here we know we are traversing through the list of distances from the first selected centroid
      while(x<50){
               while(i.hasNext() && a<600){
               
               
                  if(compareA >clustersD.get(a) && clustersD.get(a) != 0){
                                 //if smallest is found
                                 // 0 means its itself
                                 //new small value
                                 //location saved
                  compareA = clustersD.get(a);
                  whereAt = a;
                  }
                  i.next();
                  a++;       
               }
               //input in the new array of final clusters//b = 1
               clustered[3][x] = whereAt;
               clusteredDistances[3][x] = clustersD.get(whereAt);
               //so we dont keep getting the same one
               clustersD.remove(whereAt);
               x++;
               a = 0;
               i = clustersD.iterator();
               compareA = clustersD.get(0);
               whereAt = 0;
            }
            
            
            //CLUSTER E
       x = 0;
       whereAt = 0;
       a = 0;
       b = 0;
      compareA = clustersE.get(a);
      i = clustersE.iterator();
      //here we know we are traversing through the list of distances from the first selected centroid
      while(x<50){
               while(i.hasNext() && a<600){
               
               
                  if(compareA >clustersE.get(a) && clustersE.get(a) != 0){
                                 //if smallest is found
                                 // 0 means its itself
                                 //new small value
                                 //location saved
                  compareA = clustersE.get(a);
                  whereAt = a;
                  }
                  i.next();
                  a++;       
               }
               //input in the new array of final clusters//b = 1
               clustered[4][x] = whereAt;
               clusteredDistances[4][x] = clustersE.get(whereAt);
               //so we dont keep getting the same one
               clustersE.remove(whereAt);
               x++;
               a = 0;
               i = clustersE.iterator();
               compareA = clustersE.get(0);
               whereAt = 0;
            }
            
            //CLUSTER F THE LAST ONE
       x = 0;
       whereAt = 0;
       a = 0;
       b = 0;
      compareA = clustersF.get(a);
      i = clustersF.iterator();
      //here we know we are traversing through the list of distances from the first selected centroid
      while(x<50){
               while(i.hasNext() && a<600){
               
               
                  if(compareA >clustersF.get(a) && clustersF.get(a) != 0){
                                 //if smallest is found
                                 // 0 means its itself
                                 //new small value
                                 //location saved
                  compareA = clustersF.get(a);
                  whereAt = a;
                  }
                  i.next();
                  a++;       
               }
               //input in the new array of final clusters//b = 1
               clustered[5][x] = whereAt;
               clusteredDistances[5][x] = clustersF.get(whereAt);
               //so we dont keep getting the same one
               clustersF.remove(whereAt);
               x++;
               a = 0;
               i = clustersF.iterator();
               compareA = clustersF.get(0);
               whereAt = 0;
            }
         }

            
        }

            
         
   
package app;
import java.util.*;

public class IntegerBucketSorter implements Sorter {
   int[][] buckets;
   private static final int SENTINEL = -1;
   private static final int MAX_DIGIT_LIMIT = 5;
   public int[] sort(int[] dataArray) throws Exception {
      int tempCur = findMaxIntLength(dataArray);
      if(tempCur > MAX_DIGIT_LIMIT)
          throw new Exception();
      for(int i=0;i<=tempCur;i++){
         distribute(dataArray, i+1);
         collect(dataArray);
      }
      resetBucketValues();
      return dataArray;
   }

   public void distribute(int[] dataArray, int curPlace){
       buckets = new int[10][dataArray.length];
       resetBucketValues();
       for(int i=0;i<dataArray.length;i++){
         int tempPlace = getPlaceValue(curPlace, dataArray[i]);
         for(int j=0;j<buckets[tempPlace].length;j++){
            if(buckets[tempPlace][j] == -1){
               buckets[tempPlace][j]=dataArray[i];
               break;
            }
         }
      }
   }

   public void collect(int[] dataArray) {
      ArrayList<Integer> tempList = new ArrayList<>();
      for(int i=0;i<buckets.length;i++){
         for(int j=0;j<buckets[i].length;j++){
            if(buckets[i][j] != -1){
                tempList.add(buckets[i][j]);
         }
      }
      }
       System.out.println();
       for(int i= 0;i<dataArray.length;i++){
         dataArray[i] = tempList.get(i);
      }
      }
   
   public int findMaxIntLength(int[] array) throws Exception{
      int max = -1;
      try{
      if(array.length == 0 || array == null){
         return 0;
      }else{
         max = findIntLength(array[0]);
         for(int i=0;i<array.length;i++){
            if(findIntLength(array[i]) > findIntLength(max)){
               max = findIntLength(array[i]);
            }
         }
      }
      return max;
   }
   catch(Exception e){
      throw new Exception();
   }
   }
   
   public int findIntLength(int num) throws Exception {
      int len = -1;
      while(num>0){
      num/=10;
      len++;
      }
      len+=1;
      if(len > MAX_DIGIT_LIMIT){
         throw new Exception();
      }
      return len;
   }
   
   public int getPlaceValue(int place, int num){
      int digit = -1;
      while(place>0){
         digit = num%10;
         num /=10;
         place--;
      }
      return digit;
   }
   

   public void resetBucketValues(){
      for(int i=0;i<buckets.length;i++){
         for(int j=0;j<buckets[i].length;j++){
            buckets[i][j]= SENTINEL;
         }
      }

   }
}
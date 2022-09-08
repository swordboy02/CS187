    package app;

    import java.util.Scanner;
    import java.io.*;

    public class RLEconverter {
       private char[] fileChars; // stores unique chars as they appear in the file.
       private final static int DEFAULT_LEN = 100; // used to create arrays.
       private int numLines; // number of lines in the file that is read.

       
       public RLEconverter(){
          fileChars = new char[2];
          numLines=0;
       }

       
      public void compressFile(String fileName) throws IOException{
        Scanner scan = new Scanner(new FileReader(fileName));
        String line = null;
        String[] decompressed = new String [DEFAULT_LEN];
        numLines=0;
        while(scan.hasNext()){
          line = scan.next();
          if(line != null && line.length()>0)
            decompressed[numLines]=line;
            numLines++;
        }
        scan.close();
        String[] compressed = compressLines(decompressed);
        writeFile(getCompressedFileStr(compressed, fileChars), "RLE_"+fileName);
      }


    
    public String compressLine(String line, char[] fileChars){
       String res = "";
       int cnt = 0;
       if(!java.util.Objects.equals(line.charAt(0), fileChars[0]) )
           res+="0";
       for(int i =0; i <line.length(); i++){
           cnt = 0;
           for (int j =0; j<fileChars.length; j++) {
               if (line.charAt(i) == fileChars[j]){
                   int temp = i;
                   while (temp!= line.length()-1){
                       if(line.charAt(temp+1) == fileChars[j]){
                           cnt++;
                           temp++;
                       }
                       else break;
                   }
                   cnt++;
                   if(res.length() == 0) {
                       res = res + "" + cnt;
                       i = temp;
                       break;
                   }
                   res = res+","+cnt;
                   i = temp;
                   break;
               }
           }
       }
       return res;
    }

      
      public String[] compressLines(String[] lines){
       String[] compressed = new String[DEFAULT_LEN];
          fileChars[0] = lines[0].charAt(0);
       int currIndex = 0;
       for(int i =0; i<lines[0].length(); i++){
           if(fileChars[currIndex] != lines[0].charAt(i)){
               fileChars[currIndex+1] = lines[0].charAt(i);
               currIndex++;
               break;
           }
       }

       int i =0;
       while (lines[i] != null){
           compressed[i] = compressLine(lines[i], fileChars);
           i +=1;
       }

        
       return compressed;
    }

    
    public String getCompressedFileStr(String[] compressed, char[] fileChars) {
       String data = "";
       for (int i =0; i<fileChars.length; i++){
           if(i == fileChars.length-1) {
               data += fileChars[i];
               break;
           }
           data+= fileChars[i]+",";
       }
       data+="\n";
        for (int i =0; i<compressed.length; i++){
            if(compressed[i] == null)
                break;
            data+=compressed[i]+"\n";
        }
        

      return data;
    }
       
      public void decompressFile(String fileName) throws IOException{
        Scanner scan = new Scanner(new FileReader(fileName));
        String line = null;
        String[] compressed = new String [DEFAULT_LEN];
        numLines=0;
        while(scan.hasNext()){
          line = scan.next();
          if(line != null && line.length()>0)
            compressed[numLines]=line;
            numLines++;
        }
        scan.close();
        String[] decompressed = decompressLines(compressed);
        writeFile(getDecompressedFileStr(decompressed), "DECOMP_"+fileName);
      }

       
       public String decompressLine(String line, char[] fileChars){
          String res = "";
          
           int charArray = 0;
           String[] lines = line.split(",");
           for (int i =0; i<lines.length; i++){
               if(Integer.parseInt(lines[i]) == 0){
                   charArray++;
                   if(charArray == fileChars.length)
                       charArray = 0;
                   continue;
               }
               for (int j = 0; j< Integer.parseInt(lines[i]); j++){
                   res+= fileChars[charArray];
               }
               charArray++;
               if(charArray == fileChars.length)
                   charArray = 0;
           }
          return res;
       }
        
      public String[] decompressLines(String[] lines){
       String[] decompressed = new String[DEFAULT_LEN];
        
        String[] strings = lines[0].split(",");
        String temp = "";
        int j =0;
        int i =1;
          while (lines[i] != null){
              decompressed[j] = decompressLine(lines[i], fileChars);
              j++;
              i++;
          }

         return decompressed;
      }

      
      public String getDecompressedFileStr(String[] decompressed){

         String data = "";
       
        for (int i=0; i<decompressed.length; i++){
            if(decompressed[i] == null)
                break;
            data+= decompressed[i]+"\n";
        }
         return data;
      }



       public void writeFile(String data, String fileName) throws IOException{
            PrintWriter pw = new PrintWriter(fileName);
          pw.print(data);
          pw.close();
       }
    }
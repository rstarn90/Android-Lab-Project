package cs490.git.labCheckUI;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.util.ByteArrayBuffer;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class LabIO{
	
	private TextView t = null;
	private final String PATH = "LabMap.htm";  //put the downloaded file here
	
	private String [] labName ;
	
	
	private class rooms {
	 	
	 	public String building;
	 	public String room;
	 	public String status;
	 	public String longtitude;
	 	public String latitude;
	 	
	 	public rooms(String b, String r, String s, String lg, String la){
	 		building = b;
	 		room = r;
	 		status = s;
	 		longtitude = lg;
	 		latitude = la;
	 		
	 		
	 	}
	 	
	 }//rooms
	
	ArrayList<rooms> roomList = new ArrayList();
	boolean newRoomFlag = true;
	String bName = null;
	String rName = null;
	String status = null;
	String currentLatitude = null;
	String currentLongtitude = null;
	
	 private void parse(String line){
			Pattern room = Pattern.compile("<br>");
			String[] result = room.split(line);
		    
			for (int i=0; i<result.length; i++){
		        	
		        	//will be no room without building
		        	if(result[i].contains("building=")){
		        		
		        		final String temp = "building=";
		        		final String temp2 = "room=";
		        		
		        		int start = result[i].indexOf(temp);
		        		int end = result[i].indexOf("&");
		        		bName = result[i].substring(start+temp.length(), end);
		        		
			        	System.out.println(bName);
			        	Log.d("roomInfo", bName);
			        	
			        	
			        	if(result[i].contains(temp2)){
			        		
			        		int start2 = result[i].indexOf(temp2);
			        		//System.out.println("start2 : " + start2);
			        		int end2 = result[i].indexOf(">"+bName);
			        		//System.out.println("end2 : " + end2);
			        		rName = result[i].substring(start2+temp2.length(),end2);
			        		System.out.println(rName);
			        		Log.d("roomInfo", rName);
			        	}
			        	
		        		newRoomFlag = true;

		        	}
		        	else if(result[i].contains("<font size=-2>")){
		        		
		        		
		        		final String temp = "<font size=-2>";
		        		final String temp2 = "</font>";
		        		
		        		int start = result[i].indexOf(temp);
		        		int end = result[i].indexOf(temp2);
		        		
		        		if(start>-1 && end>-1 && start<end){
			        		status = result[i].substring(start+temp.length(), end);
			        		System.out.println(status);
			        		Log.d("roomInfo", status);
			        		newRoomFlag = false;
		        		}
		        		else{
		        			System.out.println(result[i]);
		        			System.out.println("start : " + start);
		        			System.out.println("end : " + end);
		        		
		        		}
		        	}
			
		        	if(!newRoomFlag){
		        		roomList.add(new rooms(bName, rName, status, currentLongtitude, currentLatitude));
		        		Log.d("roomInfo","item added");
		        		newRoomFlag = true;
		        	}
		        }//for
	    	}//generate
	 
	 private void read() throws IOException {
	    	String a ;
	        Log.d("downloader", "Reading from file.");
	        File fFileName = new File("lab.htm");
	        Scanner scanner = new Scanner(new FileInputStream(fFileName));
	    
	        Pattern p = Pattern.compile("(^\\s+)?maparray\\[\\d+\\]\\[4\\]");
	        Pattern longtitude =Pattern.compile("(^\\s+)?maparray\\[\\d+\\]\\[1\\]");
	        Pattern latitude =Pattern.compile("(^\\s+)?maparray\\[\\d+\\]\\[2\\]");
	        
			//Pattern p2 = Pattern.compile("a href=LabInfo?building=\\S+&room=\\d+>");
	     
	        try {
	          while (scanner.hasNextLine()){
	            String line = scanner.nextLine();
				
	            Matcher m = p.matcher(line);
				Matcher longti = longtitude.matcher(line);
				Matcher lati = latitude.matcher(line);
				
				boolean info = m.find();
				
				
				
				if(info){
					Log.d("downloader", line);
					parse(line);
	          	}
				else if( longti.find()){
					Log.d("downloader", line);
					Pattern splitEqual = Pattern.compile("=");
					String[] result = splitEqual.split(line);
					
					currentLongtitude = result[1].substring(0, result[1].length()-1);
					Log.d("roomInfo", currentLongtitude);
				}
				else if(lati.find()){
					Log.d("downloader", line);
					Log.d("downloader", line);
					Pattern splitEqual = Pattern.compile("=");
					String[] result = splitEqual.split(line);
					
					currentLatitude = result[1].substring(0, result[1].length()-1);
					
					Log.d("roomInfo", currentLatitude);
					
				}
	          }//while
	          
	          }//try
	        
	        finally{
	        	 scanner.close();
	        }
	      }//read
	 
	 
	 
	 
	 
	  //try {
	//		read();
	//	} catch (IOException e) {
			// TODO Auto-generated catch block
	//		e.printStackTrace();
	//	}
	  //finish();
   //  String content = roomList.size() + " computer labs found"; 
    //  for(int i =0; i< roomList.size(); i++){
    //  	content += "\n" + roomList.get(i).building+" "+roomList.get(i).room+ "\n" + roomList.get(i).longtitude+ roomList.get(i).latitude+"\n" + roomList.get(i).status + "\n";
      	
      }

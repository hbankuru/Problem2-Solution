/**
 * 
 */
/**
 * @author harika
 *
 */

package com.yale.concordance;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.BreakIterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Concordance {
	
	
	private static String getLabel(int num)
	{
		// Initializing labelAlpha to label the data
		
		 StringBuffer labelAlpha = new StringBuffer();
		 
           int letter = (num) % 26;
           char character = (char) (letter + 97); //add to the unicode character set value of 'a'
           for(int i = num/26; i>=0; i--) {
           	labelAlpha.append(character); //to append the same character 
           }
           return labelAlpha.toString();
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args)throws IOException
	{

		String fileName = "C:\\Users\\harika\\workspace\\Problem2Solution\\concordance.txt";


		//Read file
		String text = new String(Files.readAllBytes(Paths.get(fileName)) );
 
		//Initializing the sentence Iterator to break the sentences
		BreakIterator sentenceBreak = BreakIterator.getSentenceInstance();

		//Setting the file input to the Iterator sentenceBreak
		sentenceBreak.setText(text);

		//Setting sentenceNumber to know the sentence end
		int sentenceIndex =0;

		// We are using tree map because it sorts the strings in order 
		TreeMap <String, LinkedList<Integer>> wordOccurence = new TreeMap();

		//Incrementing sentenceNumber
		int sentenceNumber=1;

		while(BreakIterator.DONE !=sentenceBreak.next())
		{
			//Storing each sentence to a string
			String sentence = text.substring(sentenceIndex, sentenceBreak.current());

			//Updating sentenceIndex
			sentenceIndex=sentenceBreak.current();

			//Splitting sentence into tokens(words)
			StringTokenizer splitWord = new StringTokenizer( sentence );
			while( splitWord.hasMoreTokens( ) )
			{
				String word = splitWord.nextToken( );
				
				//Storing token occurence in linked list
				LinkedList<Integer>  lines = (LinkedList) wordOccurence.get(word);
				
				//Checking if token occurred first time
				if(lines !=null)
				{
					lines.addLast(sentenceNumber);
					wordOccurence.put(word.toLowerCase(), lines);

				}
				else {
					lines = new LinkedList();
					lines.add(sentenceNumber);
					wordOccurence.put(word.toLowerCase(), lines);
				}


			}

			sentenceNumber++;


		}
		
		//Initializing index for labeling the order
		int num=0;

		for(Map.Entry<String, LinkedList<Integer>> entry : wordOccurence.entrySet())
		{
			//Initializing StringBuilder to append the output and print
			
			StringBuilder sb =  new StringBuilder();
			

			LinkedList  print = entry.getValue();
			
			for (int i=0; i<print.size(); i++)

			{   
				if(i==0)
				{
					// printed format specifier, called getLabel to print the label, the print frequency and index
					sb.append(String.format("%-30s",getLabel(num)+"  "+entry.getKey())+"{"+print.size()+":"+print.get(i));
				}
				else
				{
					sb.append("," +print.get(i));
				}

			}
			sb.append("}");

			System.out.print(sb+"\n");
			
			//incrementing num to increase the index of the alpha labeling
			num++;


		}

	}

}

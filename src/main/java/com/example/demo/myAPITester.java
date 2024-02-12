package com.example.demo;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//import net.sf.extjwnl.data.IndexWord;
//import net.sf.extjwnl.data.POS;
//import net.sf.extjwnl.dictionary.Dictionary;
import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.item.Synset;
import edu.mit.jwi.item.WordID;


import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class myAPITester {
	
	@PostMapping(value = "/data")
    public ResponseEntity<List<resultObject>> receiveData(@RequestBody  String text) {
       
        String response = "Received data: " + text;
        System.out.println(response);
        List<resultObject> lst=funParser(text,true);
        System.out.println(lst);
        return ResponseEntity.ok(lst);
    }  
	/* @PostMapping("/parseTerm")
	    public List<String> parseTerm(@RequestBody String term) {
	       System.out.println("inside parser!!"+term);
		 return funParser(term,true);
	    }*/
	/* @GetMapping("/parseTerm")
	    public List<String> parseTerm(@RequestParam String term) {
		 System.out.println("Hello inside parseTerm");
		 	
	        return funParser(term,true);
	    }
		*/	
			/*@GetMapping("/parser")
		    public String funParser(
		    		@RequestParam String name,
		    		@RequestParam (name ="status" , defaultValue="true")boolean status)*/
			//service
					//public List<List<String>> funParser(String name,boolean status)
					public List<resultObject> funParser(String name,boolean status)
		    		{
						ArrayList<String> propernouns=new ArrayList<>();
						ArrayList<String> finalKeywords=new ArrayList<>();
						List<List<String>> wordsWithSynonyms=new ArrayList<>();
						List<resultObject> relatedWordList=new ArrayList<>();
						try {
						
					       
					        for (int i = 0; i < name.length(); i++) {
					            for (int j = i + 1; j <= name.length(); j++) {
					                String keyword = name.substring(i, j);
					               System.out.println(keyword);
					                if(keyword.indexOf(" ")!=-1)
					                {
					                //System.out.println("Whiter space hai!!");
					                keyword=keyword.replaceAll("//s","");
					                }
					                
					                if(keyword.length()>2){
					                		if(checkWord(keyword))
					                			{
					                				if(!(finalKeywords.contains(keyword))){
					                					finalKeywords.add(keyword);
					                					wordsWithSynonyms.add(get_synonyms(keyword));
					                					relatedWordList.add(get_synonymsobject(keyword));
					                					}
					                			}
					                		else {
					                		propernouns.add(keyword);
					                	}
					                
					            }
					        }
					        
						}
						}
						catch (Exception e) {
				        	System.out.println("In exception!!!");
				        	e.printStackTrace();
				        	}
			       
				       if(status && !finalKeywords.isEmpty())
				       {
				    	   System.out.println("Final keywords="+finalKeywords);
				   
				       }
				       else {
				    	//   System.out.println(propernouns);
				       }
				      //System.out.println("leftout="+propernouns);
						//return "Hello Priya , REST API!"+name;
				     //  return wordsWithSynonyms;
				       return relatedWordList;
		    		}
		    		
			// function to check the given word is present in the wordnet dicitonary.
			public boolean checkWord(String word)
			{
				String wordNetPath = "D:\\TERA_projects\\stringparser-1\\lib\\dict";
				POS[] posArray= {POS.ADJECTIVE,POS.ADVERB,POS.NOUN,POS.VERB};
				IDictionary dict = new Dictionary(new File(wordNetPath));
			        try {
			            	dict.open();
			            	int flag=0;
			            	for(POS pos:posArray)
			            	{
			            		if(dict.getIndexWord(word, pos) != null)
			            		{
			            			flag=1;
			            			System.out.println(word+" "+pos);
			            			//return true;
			            		}
			            	}
			            	if(flag==1)
			            	{
			            		return true;
			            	}
			            	dict.close();
			        	} 
			        catch (Exception e) 
			        {
			            e.printStackTrace();
			        } 
			        finally 
			        {
			            // Close the dictionary
			            if (dict != null) 
			            {
			                dict.close();
			            }
			        }

			        return false;
			}
			public List<String> get_synonyms(String wordtoLookUp)
			{
				
				Set<String> synonyms = new HashSet<>();
				
				try {
					String wordNetPath = "D:\\TERA_projects\\stringparser-1\\lib\\dict";
					
					IDictionary dict = new Dictionary(new File(wordNetPath));
		            dict.open();
		            // Get all synsets for the word
		            POS[] posArray= {POS.ADJECTIVE,POS.ADVERB,POS.NOUN,POS.VERB};
		            for(POS pos:posArray)
	            	{
		            IIndexWord idxWord = dict.getIndexWord(wordtoLookUp, pos);
		            
		            if (idxWord != null) {
		            	
		                for (IWordID wordID : idxWord.getWordIDs()) {
		                    IWord word = dict.getWord(wordID);
		                    ISynset synset = word.getSynset();
		                  
		                    for (IWord synWord : synset.getWords()) {
		                        String wordstr=synWord.getLemma().toLowerCase();
		                        //!(wordstr.contains("_")||
		                        
		                        if(wordstr.contains("-"))
		                        {
		                        	String newStr = wordstr.replaceAll("-","");
		                        	
		                              synonyms.add(newStr);
		                        }
		                        else if(wordstr.contains("_"))
		                        {
		                        	String newStr = wordstr.replaceAll("_","");
		                        	
		                              synonyms.add(newStr);
		                        }
		                        else {
		                        
		                        synonyms.add(wordstr);
		                        }
		                    }
		                    
		                }
		            }
		          	}
		            dict.close();
				 } catch (Exception e) {
			            e.printStackTrace();
			        }
				List<String> myList = new ArrayList<>(synonyms);
				myList.remove(myList.indexOf(wordtoLookUp));
				myList.add(0, wordtoLookUp);
				return myList;
				//make hashmap here where keyword is key and list of synonmys and other forms is its value
			}
			
			
			public resultObject get_synonymsobject(String wordtoLookUp)
			{
				
				Set<String> synonyms = new HashSet<>();
				
				try {
					String wordNetPath = "D:\\TERA_projects\\stringparser-1\\lib\\dict";
					resultObject obj=new resultObject();
					IDictionary dict = new Dictionary(new File(wordNetPath));
		            dict.open();
		            // Get all synsets for the word
		            POS[] posArray= {POS.ADJECTIVE,POS.ADVERB,POS.NOUN,POS.VERB};
		            for(POS pos:posArray)
	            	{
		            IIndexWord idxWord = dict.getIndexWord(wordtoLookUp, pos);
		            
		            if (idxWord != null) {
		            	
		                for (IWordID wordID : idxWord.getWordIDs()) {
		                    IWord word = dict.getWord(wordID);
		                    ISynset synset = word.getSynset();
		                  
		                    for (IWord synWord : synset.getWords()) {
		                        String wordstr=synWord.getLemma().toLowerCase();
		                        //!(wordstr.contains("_")||
		                        
		                        if(wordstr.contains("-"))
		                        {
		                        	String newStr = wordstr.replaceAll("-","");
		                        	
		                              synonyms.add(newStr);
		                        }
		                        else if(wordstr.contains("_"))
		                        {
		                        	String newStr = wordstr.replaceAll("_","");
		                        	
		                              synonyms.add(newStr);
		                        }
		                        else {
		                        
		                        synonyms.add(wordstr);
		                        }
		                    }
		                    
		                }
		            }
		          	}
		            dict.close();
				 } catch (Exception e) {
			            e.printStackTrace();
			        }
				List<String> myList = new ArrayList<>(synonyms);
				myList.remove(myList.indexOf(wordtoLookUp));
				//myList.add(0, wordtoLookUp);
				resultObject obj=new resultObject();
				obj.setKeyword(wordtoLookUp);
				obj.setRelatedWords(myList);
				return obj;
				//make hashmap here where keyword is key and list of synonmys and other forms is its value
			}
			
			public  void extractWords(String input) {
		        // Remove punctuation and whitespace
		        String cleanedInput = input.replaceAll("[^\\p{Alpha}\\d']+","");

		        List<String> words = new ArrayList<>();

		        // Updated regular expression pattern to match words (including names and alphanumeric combinations)
		        Pattern pattern = Pattern.compile("\\b[\\p{Alpha}'\\d]+\\b");

		        Matcher matcher = pattern.matcher(cleanedInput);

		        while (matcher.find()) {
		            words.add(matcher.group());
		        }
		        for (String word : words) {
		            System.out.println(word);
		        }
		      // return words;
		    }
			 
	}
			


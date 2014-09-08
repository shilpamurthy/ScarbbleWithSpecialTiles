package edu.cmu.cs.cs214.hw4.core;

import java.util.*;
import java.io.*;

public class Dictionary {

	// The dictionary contains a hashset
	HashSet<String> dict;

	public Dictionary() {
		dict = new HashSet<String>();
		try {
			BufferedReader read = new BufferedReader(new FileReader(
					"assets/words.txt"));
			try {
				String word = read.readLine();

				while (word != null) {
					this.dict.add(word.toUpperCase());
					word = read.readLine();
				}
			} finally {
				read.close();
			}
		} catch (Exception e) {
			System.out.println("FILE NOT FOUND");
		}
	}

	public boolean isWord(String word) {
		return this.dict.contains(word);
	}

}

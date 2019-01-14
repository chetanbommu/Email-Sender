package com.email;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Tester {
	
    /**
     * Complete the function below.
     * DO NOT MODIFY anything outside this method. 
     */
    static boolean[] twins(String[] a, String[] b) {
        boolean[] result = new boolean[a.length];
        for(int i=0;i<a.length;i++) {
        	result[i] = checkTwin(a[i], b[i]);
        }
        return result;
    }

    /**
     * DO NOT MODIFY THIS METHOD!
     */
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        
        int n = Integer.parseInt(in.nextLine().trim());
        String[] a = new String[n];
        for(int i = 0; i < n; i++) {
            a[i] = in.nextLine();
        }
        
        int m = Integer.parseInt(in.nextLine().trim());
        String[] b = new String[m];
        for(int i = 0; i < m; i++) {
            b[i] = in.nextLine();
        }
        
        // call twins function
        boolean[] results = twins(a, b);
        
        for(int i = 0; i < results.length; i++) {
            System.out.println(results[i]? "Yes" : "No");
        }
    }
    
    public static boolean checkTwin(String a, String b) {
    	int a_len = a.length();
    	int b_len = b.length();
    	if(a_len != b_len) {
    		return false;
    	}
    	String a_odd = "",a_even = "",b_odd = "",b_even = "";
    	for(int i=0;i<a_len;i = i+2) {
    		a_odd += a.charAt(i);
    	}
    	for(int i=1;i<a_len;i = i+2) {
    		a_even += a.charAt(i);
    	}
    	for(int i=0;i<b_len;i = i+2) {
    		b_odd += b.charAt(i);
    	}
    	for(int i=1;i<b_len;i = i+2) {
    		b_even += b.charAt(i);
    	}
    	char[] a_odd_chars = a_odd.toCharArray();
    	char[] a_even_chars = a_even.toCharArray();
    	
    	char[] b_odd_chars = b_odd.toCharArray();
    	char[] b_even_chars = b_even.toCharArray();
    	
    	Arrays.sort(a_odd_chars);
    	Arrays.sort(a_even_chars);
    	Arrays.sort(b_odd_chars);
    	Arrays.sort(b_even_chars);

    	a_odd =new String(a_odd_chars);
    	a_even = new String(a_even_chars);
    	
    	b_odd =new String(b_odd_chars);
    	b_even = new String(b_even_chars);

    	return a_odd.equals(b_odd) && a_even.equals(b_even);
    }
}
package com.engie.rdf;

import java.io.UnsupportedEncodingException;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str = "@prefix seas: <https://w3id.org/seas/> .\n"+
				"@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n"+
				"@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\n"+
				"<http://www.engie.fr/laptop> a seas:System;\n"+
				"rdfs:label \"personal\" .";
		
		TurtleReader tr=null;
		
		try {
			tr = new TurtleReader(str);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(tr.parseData());
	}
}

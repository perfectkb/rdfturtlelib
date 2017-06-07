# About

RDF Turtle library that parses an RDF turtle document and returns a JSON object with detailed status of terms used in document as below;


	{ "result": "OK",
	  "statements": 
		[{ "subject": "http://www.engie.fr/resource/CA00001B3", "predicateStatus": "Verified", "objectStatus": "Verified" }, 
			{ "subject": "http://www.engie.fr/resource/CA00001B3", "predicateStatus": "Verified", "objectStatus": "Literal" }, 
			{ "subject": "http://www.engie.fr/resource/CA00001B3", "predicateStatus": "Verified", "objectStatus": "Literal" }, 
			{ "subject": "http://www.engie.fr/resource/CA00001B3", "predicateStatus": "Verified", "objectStatus": "Literal" }, 
			{ "subject": "http://www.engie.fr/resource/CA00001B3", "predicateStatus": "Verified", "objectStatus": "Object term used could not verified in prefixed IRI" }, 
			{ "subject": "http://www.engie.fr/resource/CA00001B3", "predicateStatus": "Verified", "objectStatus": "Object term used could not verified in prefixed IRI" }, 
			{ "subject": "http://www.engie.fr/resource/CA00001B3/model", "predicateStatus": "Verified", "objectStatus": "Object term used could not verified in prefixed IRI" }, 
			{ "subject": "http://www.engie.fr/resource/CA00001B3/model", "predicateStatus": "Verified", "objectStatus": "Literal" }, 
			{ "subject": "http://www.engie.fr/resource/CA00001B3/model", "predicateStatus": "Verified", "objectStatus": "Literal" }
		]}
	}

following conformance checkings are done n the document;

- Syntax Checking : If there is a syntax error in a document, its highlighted in returned JSON object. If no syntax error, result returned is 'OK'
- Verify terms in Web of Data: Predicates and objects used are verified from prefixed IRIs, appropriate message is displayed as per result accordingly 


# Usage

Once the library has been added, following code snippet can be used to invoke the TurtleReader 

	TurtleReader tr=null;
	try {
		tr = new TurtleReader(str);
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
and result returned by tr.parseData() would be in JSON format;

	String jsonResult = tr.parseData();

TurtleReader provides two constructors; first one takes turtle file as a String;

    public TurtleReader(String turtleDoc) throws UnsupportedEncodingException
    
and second constructor takes ByteArrayInputStream as input

    public TurtleReader(ByteArrayInputStream turtleDoc)



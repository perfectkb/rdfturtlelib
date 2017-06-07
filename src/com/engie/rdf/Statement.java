/*
* Copyright 2017 ENGIE 'Smart Energy Aware Systems' SEAS Project. 
* Licensed under GNU AGPL, Version 3.0 (the "License"); 
* you may not use this file except in compliance with the License. 
* You may obtain a copy of the License at 
* 
* https://www.gnu.org/licenses/agpl-3.0.en.html
* 
* Unless required by applicable law or agreed to in writing, software 
* distributed under the License is distributed on an "AS IS" BASIS, 
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
* See the License for the specific language governing permissions and 
* limitations under the License. 
*/

package com.engie.rdf;

/*
 * author: Hammad Aslam Khan
 */

import java.io.IOException;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.jena.graph.Triple;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.lang.PipedRDFIterator;
import org.apache.jena.riot.lang.PipedRDFStream;
import org.apache.jena.riot.lang.PipedTriplesStream;

// TODO: Auto-generated Javadoc
/**
 * The Class Statement.
 */
public class Statement {

	/** The triple. */
	private transient Triple triple;
	
	/** subject */
	private String subject;

	/** The predicate status. */
	private String predicateStatus;

	/** The object status. */
	private String objectStatus;

	/**
	 * Instantiates a new statement.
	 *
	 * @param t
	 *            the t
	 */
	public Statement(Triple t) {
		this.subject = t.getSubject().toString();
		PipedRDFIterator<Triple> iter = new PipedRDFIterator<>();
		PipedRDFStream<Triple> inputStream = new PipedTriplesStream(iter);

		this.triple = t;
		// Code here to verify the predicte
		try {
			RDFDataMgr.parse(inputStream, t.getPredicate().getURI());
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		boolean foundPred = false;

		while (iter.hasNext()) {
			Triple next = iter.next();
			// Do something with each triple
			if (next.getSubject().equals(this.triple.getPredicate()))
				if (next.getObject().toString().equals("http://www.w3.org/2002/07/owl#AnnotationProperty")
						|| next.getObject().toString().equals("http://www.w3.org/2002/07/owl#DatatypeProperty")
						|| next.getObject().toString().equals("http://www.w3.org/2002/07/owl#ObjectProperty")
						|| next.getObject().toString().equals("http://www.w3.org/2002/07/owl#TransitiveProperty")
						|| next.getObject().toString().equals("http://www.w3.org/2002/07/owl#SymmetricProperty")
						|| next.getObject().toString().equals("http://www.w3.org/2002/07/owl#ReflexiveProperty")
						|| next.getObject().toString().equals("http://www.w3.org/2002/07/owl#IrreflexiveProperty")
						|| next.getObject().toString().equals("http://www.w3.org/2002/07/owl#InverseFunctionalProperty")
						|| next.getObject().toString().equals("http://www.w3.org/2002/07/owl#FunctionalProperty")
						|| next.getObject().toString().equals("http://www.w3.org/2002/07/owl#DeprecatedProperty")
						|| next.getObject().toString().equals("http://www.w3.org/2002/07/owl#AsymmetricProperty")
						|| next.getObject().toString().equals("http://www.w3.org/2002/07/owl#OntologyProperty")
						|| next.getObject().toString().equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#Property"))
					foundPred = true;
		}

		if (!foundPred)
			this.predicateStatus = "Predicate term could not be verified in prefixed IRI";
		else
			this.predicateStatus = "Verified";

		if (t.getObject().isLiteral() || !t.getObject().isURI()) {

			this.objectStatus = "Literal";

		} else {

			PipedRDFIterator<Triple> iter1 = new PipedRDFIterator<>();
			PipedRDFStream<Triple> inputStream1 = new PipedTriplesStream(iter1);
			boolean foundObject = false;
			try {
				RDFDataMgr.parse(inputStream1, t.getObject().getURI());

				while (iter1.hasNext()) {
					Triple next = iter1.next();
					// Do something with each triple
					if (next.getSubject().equals(this.triple.getObject())) {
						if (next.getObject().toString().equals("http://www.w3.org/2000/01/rdf-schema#Class")
								|| next.getObject().toString().equals("http://www.w3.org/2002/07/owl#Class"))
							foundObject = true;
					}
				}
			} catch (Exception ex) {
				System.out.println("inside parser: "+ex.getMessage());
			}

			if (!foundObject)
				this.objectStatus = "Object term used could not verified in prefixed IRI";
			else
				this.objectStatus = "Verified";
		}
	}

	/**
	 * Gets the predicate status.
	 *
	 * @return the predicate status
	 */
	public String getPredicateStatus() {
		return predicateStatus;
	}

	/**
	 * Sets the predicate status.
	 *
	 * @param predicateStatus
	 *            the new predicate status
	 */
	public void setPredicateStatus(String predicateStatus) {
		this.predicateStatus = predicateStatus;
	}

	/**
	 * Gets the object status.
	 *
	 * @return the object status
	 */
	public String getObjectStatus() {
		return objectStatus;
	}

	/**
	 * Sets the object status.
	 *
	 * @param objectStatus
	 *            the new object status
	 */
	public void setObjectStatus(String objectStatus) {
		this.objectStatus = objectStatus;
	}

	/**
	 * Gets the triple.
	 *
	 * @return the triple
	 */
	public Triple getTriple() {
		return triple;
	}

	/**
	 * Sets the triple.
	 *
	 * @param triple
	 *            the new triple
	 */
	public void setTriple(Triple triple) {
		this.triple = triple;
	}

	/**
	 * Parses the N 1.
	 *
	 * @param uri
	 *            the uri
	 * @param obj
	 *            the obj
	 * @return true, if successful
	 */
	public boolean parseN1(String uri, String obj) {
		//
		ResponseHandler<String> handler = new BasicResponseHandler();
		HttpClient client = HttpClients.custom().build();
		HttpUriRequest request = RequestBuilder.get().setUri(uri)
				// .setHeader(HttpHeaders.CONTENT_TYPE, "application/rdf+xml")
				.setHeader(HttpHeaders.CONTENT_TYPE, "turtle/text").build();
		try {
			HttpResponse response = client.execute(request);
			String body = handler.handleResponse(response);
			int code = response.getStatusLine().getStatusCode();
			System.out.println(body);
			System.out.println(code);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}

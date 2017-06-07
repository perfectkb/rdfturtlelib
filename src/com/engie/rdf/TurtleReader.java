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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import org.apache.jena.graph.Triple;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.lang.PipedRDFIterator;
import org.apache.jena.riot.lang.PipedRDFStream;
import org.apache.jena.riot.lang.PipedTriplesStream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

// TODO: Auto-generated Javadoc
/**
 * The Class TurtleReader.
 */
public class TurtleReader {

	/** The iter. */
	PipedRDFIterator<Triple> iter;

	/** The input stream. */
	final PipedRDFStream<Triple> inputStream;

	/** The is. */
	InputStream is = null;

	/**
	 * Instantiates a new turtle reader.
	 *
	 * @param turtleDoc
	 *            the turtle doc
	 * @throws UnsupportedEncodingException
	 *             the unsupported encoding exception
	 */
	public TurtleReader(String turtleDoc) throws UnsupportedEncodingException {
		is = new ByteArrayInputStream(turtleDoc.getBytes("UTF-8"));
		iter = new PipedRDFIterator<>();
		inputStream = new PipedTriplesStream(iter);
	}

	/**
	 * Instantiates a new turtle reader.
	 *
	 * @param turtleDoc
	 *            the turtle doc
	 * @throws UnsupportedEncodingException
	 *             the unsupported encoding exception
	 */
	public TurtleReader(ByteArrayInputStream turtleDoc) {
		is = turtleDoc;
		iter = new PipedRDFIterator<>();
		inputStream = new PipedTriplesStream(iter);
	}

	/**
	 * Parses the data.
	 *
	 * @return the string
	 */
	public String parseData() {
		ParseResult pr = new ParseResult();

		try {
			RDFDataMgr.parse(inputStream, is, Lang.TURTLE);

			while (iter.hasNext()) {
				Triple next = iter.next();
				System.out.println("Subject:" + next.getSubject());
				System.out.println("Predicate:" + next.getPredicate());
				System.out.println("Object:" + next.getObject());
				System.out.println("");
				pr.addStatement(new Statement(next));
			}
		} catch (Exception ex) {
			// System.out.println(ex.toString());
			if (ex.toString().contains("RiotException:")) {
				pr.result = "Invalid Syntax: " + ex.toString().split("RiotException:")[1];
			} else {
				pr.result = ex.toString();
			}
		}

		String result = "";

		try {
			Gson gson = new GsonBuilder().create();
			result = gson.toJson(pr, ParseResult.class);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}

		return result;
	}
}
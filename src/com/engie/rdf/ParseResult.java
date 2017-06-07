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

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class ParseResult.
 */
public class ParseResult {

	/** The statements. */
	List<Statement> statements = new ArrayList<Statement>();
	
	/** The result. */
	String result = "";
	
	/**
	 * Instantiates a new parses the result.
	 */
	public ParseResult(){
		this.result = "OK";
	}
	
	/**
	 * Adds the statement.
	 *
	 * @param st the st
	 */
	public void addStatement(Statement st){
		this.statements.add(st);
	}
}

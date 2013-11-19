package co.bledo.mvc.response;
/*
 *
 * Copyright 2012 The ClickPro.com LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
*/


import java.io.PrintWriter;
import org.json.JSONObject;
import co.bledo.mvc.Request;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;


public class Json extends AbstractResponse
{
	private static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger(Json.class);

	JSONObject json;
	public Json(JSONObject json)
	{
		this.putHeader("Content-Type", "application/json");
		this.json = json;
	}
	
	@Override
	public void printBody(Request req, HttpServletResponse resp) throws IOException {
		log.info("");
		PrintWriter pw = resp.getWriter();
		pw.print(json);
		pw.close();
	}
}

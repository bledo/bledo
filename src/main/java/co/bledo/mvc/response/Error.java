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


import co.bledo.mvc.HttpError404;
import co.bledo.mvc.Request;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author rxr
 */
public class Error extends AbstractResponse implements Response
{
	private static final co.bledo.logger.Logger log = co.bledo.logger.Logger.getLogger(Error.class);

	protected Exception e;
	public Error(Request req , Exception ex)
	{
		e = ex;
		if (ex instanceof HttpError404)
		{
			setStatus(404);
		}
		else
		{
			setStatus(500);
		}
	}

	@Override
	public void printBody(Request req, HttpServletResponse resp) throws Exception
	{
		PrintWriter pw = resp.getWriter();
		log.debug("printingBody ... Errr");
		pw.print("Error : " + e.getMessage() + "<br/>");
		pw.print("<br>");
		e.printStackTrace( pw );
	}
}

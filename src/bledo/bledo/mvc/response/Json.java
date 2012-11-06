package bledo.mvc.response;

import java.io.OutputStream;
import java.io.PrintWriter;

import org.json.JSONObject;

import bledo.mvc.Request;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;


public class Json extends AbstractResponse
{
	private static final bledo.logger.Logger log = bledo.logger.Logger.getLogger(Json.class);

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

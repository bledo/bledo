package co.bledo;
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

import java.io.StringWriter;
import java.io.Writer;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;


public class VelocityTplParser implements TplParser
{
	private static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger(VelocityTplParser.class);
	
	private VelocityContext _context = new VelocityContext();

	@Override
	public void assign(String key, Object obj)
	{
		_context.put(key, obj);
	}
	
	/*
	 * private static int c = 1;
	 * @Override
	 * public String fetch(Request req, OutputStream os)
	 * {
	 * log.info("content view file : {}, {}",c++, view);
	 * 
	 * //log.debug("_context.get({}) = {}",_CONTENTTOKEN, _context.get(_CONTENTTOKEN));
	 * if (_context.get(_CONTENTTOKEN) == null && view != null && !view.isEmpty())
	 * {
	 * Template viewTpl = getTemplateInstance( view );
	 * StringWriter sw = new StringWriter();
	 * viewTpl.merge(_context, sw);
	 * _context.put(_CONTENTTOKEN, sw.toString());
	 * }
	 * 
	 * log.info("layout file : {}", layout);
	 * Template layoutTpl = getTemplateInstance(layout);
	 * PrintWriter pw = new PrintWriter(os);
	 * layoutTpl.merge(_context, pw);
	 * pw.close();
	 * }
	 */
	
	@Override
	public String fetch(String tpl)
	{
		StringWriter sw = new StringWriter();
		Template template = getTemplateInstance(tpl);
		template.merge(_context, sw);
		return sw.toString();
	}
	
	public static Template getTemplateInstance(String tpl)
	{
		VelocityEngine engine = _getEngine();
		Template template = engine.getTemplate(tpl);
		return template;
	}
	
	@Override
	public void evaluate(String tplName, String strTpl, Writer writer)
	{
		VelocityEngine engine = _getEngine();
		engine.evaluate(_context, writer, tplName, strTpl);
	}
	
	private static VelocityEngine veCp = null; //new VelocityEngine();
	private static VelocityEngine _getEngine()
	{
		if ( veCp == null) {
			
			veCp = new VelocityEngine();
			veCp.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
			veCp.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());

			/*
			 * veCp.setProperty("runtime.log.error.stacktrace", true);
			 * veCp.setProperty("runtime.log.info.stacktrace", true);
			 * veCp.setProperty("runtime.log.warn.stacktrace", true);
			 * veCp.setProperty("runtime.log.invalid.references", true);
			 * veCp.setProperty("runtime.references.strict", true);
			 * veCp.setProperty("velocimacro.arguments.strict", true);
			 */
			veCp.setProperty("macro.provide.scope.control", true);
			
			veCp.init();
		}
		
		return veCp;
	}
	
	/*
	 * private static boolean _veFileInit = false;
	 * private static VelocityEngine veFile = new VelocityEngine();
	 * public static Template getFileTpl(String tpl)
	 * {
	 * if (_veFileInit == false) {
	 * 
	 * veFile.setProperty(RuntimeConstants.RESOURCE_LOADER, "file");
	 * veFile.setProperty("file.resource.loader.path", "/home/ricardo/dev/newpp/src/main/webapp/WEB-INF/view");
	 * 
	 * veFile.init();
	 * _veFileInit = true;
	 * }
	 * 
	 * Template template = veFile.getTemplate(tpl);
	 * return template;
	 * }
	 */
}

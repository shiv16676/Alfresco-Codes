package org.example;

import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.extensions.webscripts.DescriptionExtension;
import org.springframework.extensions.webscripts.WebScriptException;

public class NodeWebScriptExtension implements DescriptionExtension
{
	public Map<String, Serializable> parseExtensions(String serviceDescPath,
			InputStream servicedesc)
			{
		Map<String, Serializable> extensions = new HashMap<String, Serializable>();
				SAXReader reader = new SAXReader();
				try
				{
					// extract path value from description document
					Document document = reader.read(servicedesc);
					Element rootElement = document.getRootElement();
					Element pathElement = rootElement.element("path");
					String path = pathElement.getTextTrim();
					extensions.put("path", path);
				}
				catch (DocumentException e)
				{
					throw new WebScriptException("Failed to parse", e);
				}
				return extensions;
			}
}

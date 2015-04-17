package org.example;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.alfresco.repo.model.Repository;
import org.alfresco.service.cmr.repository.NodeRef;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;

public class NodeWebScript extends DeclarativeWebScript {

	private Repository repository;

	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	protected Map<String, Object> executeImpl(WebScriptRequest req,
			Status status, Cache cache) {

		// extract node path from description extensions
		Map<String, Serializable> extensions = getDescription().getExtensions();
		String path = (String) extensions.get("path");

		// search for folder within Alfresco content repository
		String nodePath = "workspace/SpacesStore/" + path;
		NodeRef node = repository.findNodeRef("path", nodePath.split("/"));

		// validate that node has been found
		if (node == null) {
			throw new WebScriptException(Status.STATUS_NOT_FOUND, "Path "
					+ path + " not found");
		}
		// construct model for response template to render
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("node", node);
		return model;
	}
}
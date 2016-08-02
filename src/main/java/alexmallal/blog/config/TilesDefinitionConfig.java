package alexmallal.blog.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.tiles.Attribute;
import org.apache.tiles.Definition;
import org.apache.tiles.definition.DefinitionsFactory;
import org.apache.tiles.request.Request;

public final class TilesDefinitionConfig implements DefinitionsFactory {

	private static final Map<String, Definition> TILES_DEFINITIONS = new HashMap<>();

	@Override
	public Definition getDefinition(String name, Request rqst) {

		return TILES_DEFINITIONS.get(name);
	}

	public static void addDefinitions() {

		// Ensure title does not use same string as the value assigned to the view
		addDefaultLayoutDefinition("home", "My Home", "/WEB-INF/jsp/home.jsp");
		addDefaultLayoutDefinition("login", "Login Here", "/WEB-INF/jsp/login.jsp");
		addDefaultLayoutDefinition("user", "User only Page", "/WEB-INF/jsp/user.jsp");
		addDefaultLayoutDefinition("register", "Registration Page", "/WEB-INF/jsp/register.jsp");
		addDefaultLayoutDefinition("post", "Create a New Post", "/WEB-INF/jsp/post.jsp");
		addDefaultLayoutDefinition("postind", "Individual Post", "/WEB-INF/jsp/indpost.jsp");
		addDefaultLayoutDefinition("editpost", "Edit your post", "/WEB-INF/jsp/editpost.jsp");
		addCustomHeaderLayoutDefinition("editpost", "Edit your post", "/WEB-INF/jsp/editpost.jsp", "/WEB-INF/jsp/editpostheader.jsp");
	}

	private static void addDefaultLayoutDefinition(String name, String title, String body) {

		Map<String, Attribute> attributes = new HashMap<>();

		attributes.put("title", new Attribute(title));
		attributes.put("header", new Attribute("/WEB-INF/jsp/tiles/header.jsp"));
		attributes.put("menu", new Attribute("/WEB-INF/jsp/tiles/menu.jsp"));
		attributes.put("body", new Attribute(body));
		attributes.put("footer", new Attribute("/WEB-INF/jsp/tiles/footer.jsp"));

		Attribute baseTemplate = new Attribute("/WEB-INF/jsp/tiles/layout.jsp");

		TILES_DEFINITIONS.put(name, new Definition(name, baseTemplate, attributes));
	}
	
	private static void addCustomHeaderLayoutDefinition(String name, String title, String body, String header) {

		Map<String, Attribute> attributes = new HashMap<>();

		attributes.put("title", new Attribute(title));
		attributes.put("header", new Attribute(header));
		attributes.put("menu", new Attribute("/WEB-INF/jsp/tiles/menu.jsp"));
		attributes.put("body", new Attribute(body));
		attributes.put("footer", new Attribute("/WEB-INF/jsp/tiles/footer.jsp"));

		Attribute baseTemplate = new Attribute("/WEB-INF/jsp/tiles/layout.jsp");

		TILES_DEFINITIONS.put(name, new Definition(name, baseTemplate, attributes));
	}
}
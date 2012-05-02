package com.dotmarketing.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.PageContext;

import com.dotmarketing.beans.Host;
import com.dotmarketing.beans.Identifier;
import com.dotmarketing.beans.Inode;
import com.dotmarketing.beans.PermissionAsset;
import com.dotmarketing.beans.WebAsset;
import com.dotmarketing.business.APILocator;
import com.dotmarketing.business.BaseWebAssetAPI;
import com.dotmarketing.business.IdentifierAPI;
import com.dotmarketing.business.Role;
import com.dotmarketing.exception.DotDataException;
import com.dotmarketing.exception.DotSecurityException;
import com.dotmarketing.factories.InodeFactory;
import com.dotmarketing.factories.WebAssetFactory;
import com.dotmarketing.portlets.categories.business.CategoryAPI;
import com.dotmarketing.portlets.categories.model.Category;
import com.dotmarketing.portlets.containers.model.Container;
import com.dotmarketing.portlets.contentlet.business.HostAPI;
import com.dotmarketing.portlets.contentlet.model.Contentlet;
import com.dotmarketing.portlets.entities.factories.EntityFactory;
import com.dotmarketing.portlets.entities.model.Entity;
import com.dotmarketing.portlets.events.model.Event;
import com.dotmarketing.portlets.events.model.Recurance;
import com.dotmarketing.portlets.files.model.File;
import com.dotmarketing.portlets.htmlpages.model.HTMLPage;
import com.dotmarketing.portlets.languagesmanager.business.LanguageAPI;
import com.dotmarketing.portlets.languagesmanager.model.Language;
import com.dotmarketing.portlets.links.model.Link;
import com.dotmarketing.portlets.templates.factories.TemplateFactory;
import com.dotmarketing.portlets.templates.model.Template;
import com.liferay.portal.language.LanguageException;
import com.liferay.portal.language.LanguageUtil;
import com.liferay.portal.model.User;

public class UtilHTML {

	private static CategoryAPI categoryAPI = APILocator.getCategoryAPI();
	private static LanguageAPI langAPI = APILocator.getLanguageAPI();
	private static IdentifierAPI identifierAPI = APILocator.getIdentifierAPI();
	private static HostAPI hostAPI = APILocator.getHostAPI();

	public static String convertContentsToHTML(ArrayList<Contentlet> contents){
		StringBuffer buffy = new StringBuffer();
		for (Contentlet contentlet : contents) {
//			contentlet.get
		}
		return buffy.toString();
	}
	
	public static String getStatusIcons(WebAsset webasset, String COMMON_IMG, boolean showWorking) {
		return getStatusIcons(webasset.isLive(), webasset.isWorking(), webasset.isDeleted(), webasset.isLocked(),
				COMMON_IMG, showWorking,null);
	}

	public static String getStatusIcons(boolean live, boolean working, boolean deleted, boolean locked,
			String COMMON_IMG, boolean showWorking) {
		return getStatusIcons(live, working, deleted, locked, COMMON_IMG, showWorking, null);
	}

	public static String getStatusIcons(WebAsset webasset, String COMMON_IMG, boolean showWorking,PageContext pageContext) {
		return getStatusIcons(webasset.isLive(), webasset.isWorking(), webasset.isDeleted(), webasset.isLocked(),
				COMMON_IMG, showWorking,pageContext);
	}

	public static String getStatusIcons(boolean live, boolean working, boolean deleted, boolean locked,
			String COMMON_IMG, boolean showWorking,PageContext pageContext) {
		String strLive = "live";
		String strDeleted = "deleted";
		String strWorking = "working";
		String strLocked = "locked";
		if(pageContext != null){
			try {
				strLive = LanguageUtil.get(pageContext, "live");
				strDeleted = LanguageUtil.get(pageContext, "deleted");
				strWorking = LanguageUtil.get(pageContext, "working");
				strLocked = LanguageUtil.get(pageContext, "locked");
			} catch (LanguageException e) {
				
			}
		}
		
		StringBuffer strHTML = new StringBuffer();
		strHTML.append("<table style='margin-top:10px'><tr>");
		strHTML.append("<td valign=middle width=16 style='border:0px;padding:0px;margin:0px;'>");

		if (live) {
			strHTML.append("<img src='/html/images/icons/status.png' width=16 height=16 alt="+strLive+" title='"+strLive+"'>");
		} else if (deleted) {
			strHTML.append("<img src='/html/images/icons/status-busy.png' width=16 height=16  alt="+strDeleted+" title='"+strDeleted+"'>");
		} else if ((showWorking) && (working)) {
			strHTML.append("<img src='/html/images/icons/status-away.png' width=16 height=16  alt="+strWorking+" title='"+strWorking+"'>");
		} else{
			strHTML.append("<img src='/html/images/shim.gif' width=16>");
		} 

		strHTML.append("</td>");
		strHTML.append("<td valign=middle width=16 style='border:0px;padding:0px;margin:0px;'>");
		
		if (locked) {
			strHTML.append("<img src='/html/images/icons/lock.png' width=16 height=16 alt='"+strLocked+"' title='"+strLocked+"' />");
		}
		else{
			strHTML.append("<img src='/html/images/shim.gif' height=16 width=16 />");
		}


		strHTML.append("</td></tr></table>");

		return strHTML.toString();
	}
	
	public static String getAssetIcon(WebAsset webasset) {
		String imgSrc = "";

		if (webasset instanceof Container) {
			imgSrc = "/html/images/icons/layout-select-content.png";
		} else if (webasset instanceof Template) {
			imgSrc = "/html/images/icons/layout-header-3-mix.png";
		} else if (webasset instanceof Link) {
			imgSrc = "/html/images/icons/chain.png";
		} else if (webasset instanceof File) {
			imgSrc = "/icon?i=" + ((File) webasset).getFileName();
		} else if (webasset instanceof HTMLPage) {
			imgSrc = "/html/images/icons/blog-blue.png";
		}

		return "<img src=\"" + imgSrc + "\" width=16 height=16 />";
	}

	public static String getAssetIcon(Contentlet con){
		return "<img src=\"/html/images/icons/document-text-image.png\" width=16 height=16 />";
	}

	public static String getSelectCategories(Inode parent, int level, Inode current, User user, boolean respectFrontendRoles) throws DotDataException, DotSecurityException {

		level++;
		Logger.debug(UtilHTML.class, "getSelectCategories!!!");

		List<Category> categories = (parent == null) ? categoryAPI.findTopLevelCategories(user, respectFrontendRoles) : categoryAPI
				.getChildren(parent, user, respectFrontendRoles);

		StringBuffer strHTML = new StringBuffer();
		Iterator<Category> m = categories.iterator();

		while (m.hasNext()) {
			Category cat = (Category) m.next();
			
			boolean canUse = categoryAPI.canUseCategory(cat, user, respectFrontendRoles);
			if(canUse){
				if (level == 1) {
					strHTML.append("<option value=\"" + cat.getInode() + "\" class='topCat' ");
				} else {
					strHTML.append("<option value=\"" + cat.getInode() + "\"");
				}
	
				if (current != null) {
					Iterator<Category> l = categoryAPI.getParents(current, user, respectFrontendRoles).iterator();
	
					while (l.hasNext()) {
						Category disCat = (Category) l.next();
	
						if (cat.getInode().equalsIgnoreCase(disCat.getInode())) {
							strHTML.append(" selected");
						}
					}
				}
	
				strHTML.append(">");
	
				for (int k = 0; k < (level - 1); k++) {
					strHTML.append(" &nbsp; &nbsp;");
				}
	
				strHTML.append("+&nbsp;");
				strHTML.append(cat.getCategoryName() + "</option>\n");
	
				if (categoryAPI.getChildren(cat, user, respectFrontendRoles).size() > 0) {
					strHTML.append(getSelectCategories(cat, level, current, user, respectFrontendRoles));
				}
			}
		}
		return strHTML.toString();
	}



	public static String getSelectLanguages(Contentlet contentlet) {
		List<Language> languages = langAPI.getLanguages();

		StringBuffer strHTML = new StringBuffer();
		Iterator<Language> m = languages.iterator();

		while (m.hasNext()) {
			Language language = (Language) m.next();

			strHTML.append("<option value=\"" + language.getId() + "\"");

			if (language.getId() == contentlet.getLanguageId()) {
				strHTML.append(" selected");
			}

			strHTML.append(">");

			strHTML.append(language.getLanguage() + " (" + language.getCountry() + ")</option>\n");
		}

		return strHTML.toString();
	}

	public static String getSelectCategories(Inode parent, int level, String currentCats, User user, boolean respectFrontendRoles) throws DotDataException, DotSecurityException {

		CategoryAPI catAPI = APILocator.getCategoryAPI();
		
		level++;
		Logger.debug(UtilHTML.class, "getSelectCategories!!!");

		List<Category> categories = (parent == null) ? categoryAPI.findTopLevelCategories(user, respectFrontendRoles) : categoryAPI
				.getChildren(parent, user, respectFrontendRoles);

		StringBuffer strHTML = new StringBuffer();
		Iterator<Category> m = categories.iterator();

		while (m.hasNext()) {
			Category cat = (Category) m.next();

			boolean canUse = catAPI.canUseCategory(cat, user, respectFrontendRoles);
			if(canUse){				
				if (level == 1) {
					strHTML.append("<option value=\"" + cat.getInode() + "\" class='topCat' ");
				} else {
					strHTML.append("<option value=\"" + cat.getInode() + "\"");
				}
	
				if (currentCats.indexOf(cat.getInode() + ",") != -1) {
					Logger.debug(UtilHTML.class, "found the same objects!!!!");
					strHTML.append(" selected");
				}
	
				strHTML.append(">");
	
				for (int k = 0; k < (level - 1); k++) {
					strHTML.append(" &nbsp; &nbsp;");
				}
	
				if (level != 0)
					strHTML.append("+&nbsp;");
				strHTML.append(cat.getCategoryName() + "</option>\n");
	
				if (categoryAPI.getChildren(cat, user, respectFrontendRoles).size() > 0) {
					strHTML.append(getSelectCategories(cat, level, currentCats, user, respectFrontendRoles));
				}
			}
		}

		return strHTML.toString();
	}

	public static String getSelectCategoriesTextMode(Inode parent, int level, String currentCats,User user, boolean respectFrontendRoles) throws DotDataException, DotSecurityException {
		
		CategoryAPI catAPI = APILocator.getCategoryAPI();
		
		level++;
		Logger.debug(UtilHTML.class, "getSelectCategories!!!");

		List<Category> categories = (parent == null) ? categoryAPI.findTopLevelCategories(user, respectFrontendRoles) : 
			categoryAPI.getChildren(parent, user, respectFrontendRoles);

		StringBuffer strHTML = new StringBuffer();
		Iterator<Category> m = categories.iterator();

		while (m.hasNext()) {
			Category cat = (Category) m.next();
			boolean canUse = catAPI.canUseCategory(cat, user, respectFrontendRoles);
			if(canUse){
				if (currentCats.indexOf(cat.getInode() + ",") != -1) {
					Logger.debug(UtilHTML.class, "found the same objects!!!!");
					strHTML.append("<tr><td>");
					strHTML.append(cat.getCategoryName());
					strHTML.append("</td></tr>");
				}
				if (categoryAPI.getChildren(cat, user, respectFrontendRoles).size() > 0) {
					strHTML.append(getSelectCategoriesTextMode(cat, level, currentCats, user, respectFrontendRoles));
				}
			}
		}
		return strHTML.toString();
	}

	public static String getSelectCategories(Inode parent, int level, String[] currentCats, User user, boolean respectFrontendRoles) throws DotDataException, DotSecurityException {
		StringBuffer buffer = new StringBuffer();
		if (currentCats != null) {
			for (int i = 0; i < currentCats.length; i++) {
				buffer.append(currentCats[i] + ",");
			}
		}
		return getSelectCategories(parent, level, buffer.toString(), user, respectFrontendRoles);
	}

	public static String getSelectCategoriesTextMode(Inode parent, int level, String[] currentCats,User user, boolean respectFrontendRoles) throws DotDataException, DotSecurityException {
		StringBuffer buffer = new StringBuffer();
		if (currentCats != null) {
			for (int i = 0; i < currentCats.length; i++) {
				buffer.append(currentCats[i] + ",");
			}
		}
		return getSelectCategoriesTextMode(parent, level, buffer.toString(), user, respectFrontendRoles);
	}

	public static List<Template> getSelectTemplates(Host host, User user) {
		List<Template> result = new ArrayList<Template>();
		String condition = "working=" + com.dotmarketing.db.DbConnectionFactory.getDBTrue() + " and deleted=" + com.dotmarketing.db.DbConnectionFactory.getDBFalse();

		// Gets roles
		try {
			List<WebAsset> templates = WebAssetFactory.getAssetsPerConditionWithPermission(condition, Template.class, 100000, 0, "title", null, user);
			
			Iterator<WebAsset> templatesIter = templates.iterator();
			Template template;
			
			if (host == null) {
				while (templatesIter.hasNext()) {
					template = (Template) templatesIter.next();
					result.add(template);
				}
			} else {
				Identifier identifier;
				Host systemHost = hostAPI.findSystemHost(user, false);
				
				while (templatesIter.hasNext()) {
					template = (Template) templatesIter.next();
					identifier = identifierAPI.findFromInode(template.getIdentifier());
					if (identifier.getHostId().equals(host.getIdentifier()) || !UtilMethods.isSet(identifier.getHostId()) || ((systemHost != null) && identifier.getHostId().equals(systemHost.getIdentifier()))) {
						result.add(template);
					}
				}
			}
		} catch (Exception e) {
		}

		return result;
	}

	public static String getSelectCategories(Inode current, User user, boolean respectFrontendRoles) throws DotDataException, DotSecurityException {
		return getSelectCategories(null, 0, current, user, respectFrontendRoles);
	}

	public static String getSelectCategories(String x, User user, boolean respectFrontendRoles) throws DotDataException, DotSecurityException {
		Category current = categoryAPI.find(x, user, respectFrontendRoles);

		return getSelectCategories(null, 0, current, user, respectFrontendRoles);
	}

	public static String getSelectCategoriesByParent(Inode parent, User user, boolean respectFrontendRoles) throws DotDataException, DotSecurityException {
		Inode current = new Inode();
		Category parentCat = (Category) parent;
		StringBuffer strHTML = new StringBuffer();
		strHTML.append("<option value=\"" + parent.getInode() + "\" class='topCat'>");
		strHTML.append(parentCat.getCategoryName() + "</option>\n");
		strHTML.append(getSelectCategories(parent, 1, current, user, respectFrontendRoles));

		return strHTML.toString();
	}

	public static String getSelectCategories(Inode parent, int level, Inode current, int maxlevel, 
			User user, boolean respectFrontendRoles) throws DotDataException, DotSecurityException {
		ArrayList idList = new ArrayList();
		if (current instanceof Category) {
			idList.add(current.getInode());
		} else {
			List cats = InodeFactory.getParentsOfClass(current, Category.class);
			Iterator it = cats.iterator();
			while (it.hasNext()) {
				Category cat = (Category) it.next();
				idList.add(cat.getInode());
			}
		}
		String[] catsArr = (String[]) idList.toArray(new String[0]);
		return getSelectCategories(parent, level, catsArr, maxlevel, user, respectFrontendRoles);
	}

	public static String getSelectCategories(Inode parent, int level, String[] selectedCategoriesIds, int maxlevel, 
			User user, boolean respectFrontendRoles) throws DotDataException, DotSecurityException {
		level++;

		List categories = (parent == null) ? categoryAPI.findTopLevelCategories(user, respectFrontendRoles) : 
			categoryAPI.getChildren(parent, user, respectFrontendRoles);

		StringBuffer strHTML = new StringBuffer();
		Iterator m = categories.iterator();

		while (m.hasNext()) {
			Category cat = (Category) m.next();

			if (level == 1) {
				strHTML.append("<option value=\"" + cat.getInode() + "\" class=\"topCat\" ");
			} else {
				strHTML.append("<option value=\"" + cat.getInode() + "\"");
			}

			if (selectedCategoriesIds != null) {
				for (int i = 0; i < selectedCategoriesIds.length; i++) {
					String id = selectedCategoriesIds[i];
					if (cat.getInode().equalsIgnoreCase(id)) {
						strHTML.append(" selected");

					}
				}
			}

			strHTML.append(">");

			for (int k = 0; k < (level - 1); k++) {
				strHTML.append(" &nbsp; &nbsp;");
			}

			strHTML.append("+&nbsp;");
			strHTML.append(cat.getCategoryName() + "</option>");

			if (level <= maxlevel && categoryAPI.getChildren(cat, user, respectFrontendRoles).size() > 0) {
				strHTML.append(getSelectCategories(cat, level, selectedCategoriesIds, maxlevel, user, respectFrontendRoles));
			}
		}

		return strHTML.toString();
	}

	// Event recurance HTML representation
	public static String recuranceToString(com.dotmarketing.portlets.events.model.Recurance r) {
		if (r == null || (!InodeUtils.isSet(r.getInode() )))
			return "Once";

		String plural = (r.getInterval() < 2) ? r.getOccurs() : r.getInterval() + " " + r.getOccurs() + "s";

		StringBuffer days = new StringBuffer("");
		if (r.getOccurs() != null && r.getOccurs().equals("week") && r.getDaysOfWeek() != null
				&& r.getDaysOfWeek().length() > 0) {
			days.append(" - ");
			if (r.getDaysOfWeek().indexOf("0") > -1)
				days.append("Sun ");
			if (r.getDaysOfWeek().indexOf("1") > -1)
				days.append("Mon ");
			if (r.getDaysOfWeek().indexOf("2") > -1)
				days.append("Tue ");
			if (r.getDaysOfWeek().indexOf("3") > -1)
				days.append("Wed ");
			if (r.getDaysOfWeek().indexOf("4") > -1)
				days.append("Thr ");
			if (r.getDaysOfWeek().indexOf("5") > -1)
				days.append("Fri ");
			if (r.getDaysOfWeek().indexOf("6") > -1)
				days.append("Sat");

		}

		return "Every " + plural + days + " from " + UtilMethods.dateToHTMLDate(r.getStarting()) + " to "
				+ UtilMethods.dateToHTMLDate(r.getEnding());

	}

	public static String recuranceToString(Event e, Recurance r) {
		if (r == null || (!InodeUtils.isSet(r.getInode() )))
			return "Once";

		String plural = (r.getInterval() < 2) ? r.getOccurs() : r.getInterval() + " " + r.getOccurs() + "s";

		StringBuffer days = new StringBuffer("");
		if (r.getOccurs() != null && r.getOccurs().equals("week") && r.getDaysOfWeek() != null
				&& r.getDaysOfWeek().length() > 0) {
			days.append(" - ");
			if (r.getDaysOfWeek().indexOf("1") > -1)
				days.append("Sun ");
			if (r.getDaysOfWeek().indexOf("2") > -1)
				days.append("Mon ");
			if (r.getDaysOfWeek().indexOf("3") > -1)
				days.append("Tue ");
			if (r.getDaysOfWeek().indexOf("4") > -1)
				days.append("Wed ");
			if (r.getDaysOfWeek().indexOf("5") > -1)
				days.append("Thr ");
			if (r.getDaysOfWeek().indexOf("6") > -1)
				days.append("Fri ");
			if (r.getDaysOfWeek().indexOf("7") > -1)
				days.append("Sat");

		}
		if (e.isTimeTBD())
			return "Every " + plural + days + " from <br>"
					+ UtilMethods.dateToHTMLDateTimeRange(r.getStarting(), r.getEnding()) + " at time TBD";
		else
			return "Every " + plural + days + " from <br>"
					+ UtilMethods.dateToHTMLDateTimeRange(r.getStarting(), r.getEnding()) + " at "
					+ UtilMethods.dateToHTMLTimeRange(r.getStarting(), r.getEnding());
	}

	/***************************************************************************
	 * This methods return the selects required for the categories of an entity
	 * 
	 * @param parameterName
	 *            value of the "name" attribute for the <select> html tag
	 * @param entityName
	 *            name of the entity that have the categories
	 * @param selectCatsString
	 *            Selected categories inodes
	 * @param displayTitle
	 *            if do you want that the title display or not (Entity Name)
	 * @param multipleValues
	 *            if the user could select multiple values
	 * @param size
	 *            the height in entries that could be displayed
	 * @param readonly
	 *            if the select is disable
	 * @return the the HTML that render the selects
	 */
	
	/**
	 * @throws DotDataException 
	 * @throws DotSecurityException 
	 * @deprecated
	 */
	public static String getCategoriesByEntityName(String parameterName, String entityName, String[] selectCatsString,
			boolean displayTitle, boolean multipleValues, int size, boolean readonly) throws DotDataException, DotSecurityException {
		return getCategoriesByEntityName(parameterName, entityName, selectCatsString, displayTitle, multipleValues, size, readonly, null, true);
	}
	/**
	 * @throws DotDataException 
	 * @throws DotSecurityException 
	 * @deprecated
	 */
	public static String getCategoriesByEntityName(String parameterName, String entityName, String childInode,
			boolean displayTitle, boolean multipleValues, int size, boolean readonly) throws DotDataException, DotSecurityException {
		return getCategoriesByEntityName(parameterName, entityName, childInode, displayTitle, multipleValues, size, readonly, null, true);
	}
	
	public static String getCategoriesByEntityName(String parameterName, String entityName, String[] selectCatsString,
			boolean displayTitle, boolean multipleValues, int size, boolean readonly, User user, boolean respectFrontendRoles) throws DotDataException, DotSecurityException {
		StringBuffer buffer = new StringBuffer();
		// GETS THE ENTITY
		Entity entity = EntityFactory.getEntity(entityName);
		// GET ALL THE MAIN CATEGORIES FOR THIS ENTITY
		List categories = EntityFactory.getEntityCategories(entity);
		if (categories.size() > 0) {
			buffer.append("<table>");
			buffer.append("<tr>");
			buffer.append("<td align='left' colspan='2'>");
			buffer.append("<table border='0'>");
			Iterator categoriesIterator = categories.iterator();
			// /FOR EACH CATEGORY WE GET THE CHILDREN
			while (categoriesIterator.hasNext()) {
				Category category = (Category) categoriesIterator.next();
				int count = InodeFactory.countChildrenOfClass(category, Category.class);
				if (count > 1) {
					StringBuffer buffy = new StringBuffer();
					buffy.append("<tr>");
					if (displayTitle) {
						buffy.append("<td valign='top'>");
						buffy.append(category.getCategoryName() + ":");
						buffy.append("</td>");
					}
					buffy.append("<td align='left'>");
					String multiple = (multipleValues ? "multiple='multiple'" : "");
					int sizeAux = (size == -1 ? count - 1 : size);
					String disabled = (readonly ? "disabled" : "");
					buffy.append("<select id'" + parameterName + "' name='" + parameterName + "'  size='" + sizeAux
							+ "' " + multiple + " " + disabled + ">");
					String catOptions = getSelectCategories(category, -1, selectCatsString, user, respectFrontendRoles);
					buffy.append(catOptions);
					buffy.append("</select>");
					buffy.append("</td>");
					buffy.append("</tr>");
					if(catOptions.length() > 1){
						buffer.append(buffy);
					}
				}
			}
			buffer.append("</table>");
			buffer.append("</td>");
			buffer.append("</tr>");
			buffer.append("</table>");
		}
		return buffer.toString();
	}

	public static String getCategoriesByEntityName(String parameterName, String entityName, String childInode,
			boolean displayTitle, boolean multipleValues, int size, boolean readonly, User user, boolean respectFrontendRoles) throws DotDataException, DotSecurityException {
		String[] selectCatsString = new String[0];
		// Find the selected values
		if (InodeUtils.isSet(childInode)) {
			Inode inode = InodeFactory.getInode(childInode, Inode.class);
			List<Category> categories = InodeFactory.getParentsOfClass(inode, Category.class);
			selectCatsString = new String[categories.size()];
			for (int i = 0; i < categories.size(); i++) {
				selectCatsString[i] = categories.get(i).getInode();
			}
		}

		return getCategoriesByEntityName(parameterName, entityName, selectCatsString, displayTitle, multipleValues,
				size, readonly, user, respectFrontendRoles);
	}
	
	public static String htmlEncode(String data)
	{
		final StringBuffer buf = new StringBuffer();
		if(UtilMethods.isSet(data))
		{				
			final char[] chars = data.toCharArray();
			for (int i = 0; i < chars.length; i++) 
			{ 
				if (chars[i] == ';')
					buf.append(";");
				else
					buf.append("&#" + (int) chars[i]);
			}  
		}
		return buf.toString();
	}
	
	public static String escapeHTMLSpecialChars (String data){
		return UtilMethods.escapeHTMLSpecialChars (data);
	}
	
	/**
	 * This method escape the double quotes into html representacion
	 * @param fixme The text where the double quates exists
	 * @return String
	 * @author Oswaldo Gallango
	 * @version 1.0
	 */
	public static String escapeDoubleQuotesToHTMLEspecialChars(String fixme) {
		fixme = fixme.replaceAll("\"", "&quot;");
		return fixme;
	}

	public static CategoryAPI getCategoryAPI() {
		return categoryAPI;
	}

	public static void setCategoryAPI(CategoryAPI categoryAPI) {
		UtilHTML.categoryAPI = categoryAPI;
	}
}

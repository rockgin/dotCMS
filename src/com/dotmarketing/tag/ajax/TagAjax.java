package com.dotmarketing.tag.ajax;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContext;

import uk.ltd.getahead.dwr.WebContextFactory;

import com.dotmarketing.beans.Host;
import com.dotmarketing.business.APILocator;
import com.dotmarketing.business.web.UserWebAPI;
import com.dotmarketing.business.web.WebAPILocator;
import com.dotmarketing.exception.DotDataException;
import com.dotmarketing.exception.DotSecurityException;
import com.dotmarketing.portlets.categories.business.CategoryAPI;
import com.dotmarketing.portlets.usermanager.factories.UserManagerListBuilderFactory;
import com.dotmarketing.portlets.usermanager.struts.UserManagerListSearchForm;
import com.dotmarketing.tag.ajax.TagAjax;
import com.dotmarketing.tag.business.TagAPI;
import com.dotmarketing.tag.model.Tag;
import com.dotmarketing.util.Logger;
import com.dotmarketing.util.UtilMethods;
import com.dotmarketing.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.util.servlet.SessionMessages;

public class TagAjax {
	
	private static TagAPI tagAPI = APILocator.getTagAPI();

	/**
	 * Tags an object, validates the existence of a tag(s), creates it if it doesn't exists
	 * and then tags the object 
	 * @param tagName tag(s) to create
	 * @param userId owner of the tag
	 * @param inode object to tag
	 * @return a list of all tags assigned to an object
	 */
	public static Map<String,Object> addTag(String tagName, String userId, String hostId) {
		
		HttpServletRequest req = WebContextFactory.get().getHttpServletRequest();
    	List<String> saveTagErrors = new ArrayList<String>();
    	Map<String,Object> callbackData = new HashMap<String,Object>();
    	
    	Tag tag = new Tag();
    	
    	hostId=hostId.trim();
    	
        try{
        	
        	tag = tagAPI.getTag(tagName, userId, hostId);
        	String tagStorageForHost = "";
        	Host host = APILocator.getHostAPI().find(hostId, APILocator.getUserAPI().getSystemUser(),true);
        	
        	if(host.getIdentifier().equals(Host.SYSTEM_HOST))
        		tagStorageForHost = Host.SYSTEM_HOST;
        	else
        		tagStorageForHost = host.getMap().get("tagStorage").toString();
        	
        	if (UtilMethods.isSet(tagStorageForHost)){
            	if (!tag.getHostId().equals(tagStorageForHost) && tag.getHostId().equals(Host.SYSTEM_HOST)) {
                	saveTagErrors.add("Global Tag Already Exists");
                	SessionMessages.clear(req.getSession());
            	}
            	
        	}

        }catch(Exception e){
        	saveTagErrors.add("There was an error saving the tag");
        	SessionMessages.clear(req.getSession());
        }finally{
        	if(saveTagErrors != null && saveTagErrors.size() > 0){
        		callbackData.put("saveTagErrors", saveTagErrors);
        		SessionMessages.clear(req.getSession());
        	}

        }
        return callbackData;
	}
	
	/**
	 * Updates an existing tag.
	 * @param tagName tag to update
	 * @param userId owner of the tag
	 * @param hostId the storage host id
	 */
	public static Map<String,Object> updateTag(String tagId, String tagName, String hostId){
		
		HttpServletRequest req = WebContextFactory.get().getHttpServletRequest();
    	List<String> saveTagErrors = new ArrayList<String>();
    	Map<String,Object> callbackData = new HashMap<String,Object>();
    	
    	hostId=hostId.trim();
    	
        try{
        	tagAPI.updateTag(tagId, tagName, false, hostId);
        }catch(Exception e){
        	saveTagErrors.add("There was an error saving the tag.");
        	SessionMessages.clear(req.getSession());
        }finally{
        	if(saveTagErrors != null && saveTagErrors.size() > 0){
        		callbackData.put("saveTagErrors", saveTagErrors);
        		SessionMessages.clear(req.getSession());
        	}
        }
        return callbackData;
	}


	/**
	 * Tags the users in selected in the User Manager, validates the existence of a tag(s), creates it if it doesn't exists
	 * and then tags the object 
	 * @param tagName tag(s) to create
	 * @param userId owner of the tag
	 * @return a list of all tags assigned to an object
	 */
	public static void addTagFullCommand(String tagName, String userId) 
	{	
		try
		{
			HttpSession session = WebContextFactory.get().getSession();

			//Get all the user of the filter
			UserManagerListSearchForm searchFormFullCommand = (UserManagerListSearchForm) session.getAttribute(WebKeys.USERMANAGERLISTPARAMETERS);
			searchFormFullCommand.setStartRow(0);
			searchFormFullCommand.setMaxRow(0);
			List matches = UserManagerListBuilderFactory.doSearch(searchFormFullCommand);

			//Get the Iterator and the userIds
			Iterator it = matches.iterator();            
			for (int i = 0; it.hasNext(); i++) 
			{               
				String userTagId = (String) ((Map) it.next()).get("userid");
				String inode = com.dotmarketing.business.APILocator.getUserProxyAPI().getUserProxy(userTagId,APILocator.getUserAPI().getSystemUser(), false).getInode();			
				tagAPI.addTag(tagName, userId, inode);
			}
		}
		catch(Exception ex)
		{
			String message = ex.toString();
			Logger.debug(TagAjax.class,message);
		}
	}

	/**
	 * Gets all the tag created by an user
	 * @param userId id of the user
	 * @return a list of all the tags created
	 */
	public List<Tag> getTagByUser(String userId) {
		return tagAPI.getTagByUser(userId);
	}

	/**
	 * Deletes a tag
	 * @param tagName name of the tag to be deleted
	 * @param userId id of the tag owner
	 * @return list of all the tags, with the owner information and the respective permission
	 */
	public void deleteTag(String tagId) {
		tagAPI.deleteTag(tagId);
	}

	/**
	 * Gets all the tags created, with the respective owner and permission information
	 * @param userId id of the user that searches the tag
	 * @return a complete list of all the tags, with the owner information and the respective permission
	 * information
	 */
	public List getAllTag(String userId) {
		return tagAPI.getAllTag(userId);
	}
	
	/**
	 * Gets a tag with the owner information, searching by name
	 * @param name name of the tag
	 * @return the tag with the owner information 
	 */
	public static List<Tag> getTagByName(String tagName) {
		return tagAPI.getTagByName(tagName);
	}

	/**
	 * Gets a tag with the owner information, searching by name
	 * @param name name of the tag
	 * @return the tag with the owner information 
	 */
	public List getTagInfoByName(String tagName) {
		return tagAPI.getTagInfoByName(tagName);
	}

	/**
	 * Gets all tags associated to an object
	 * @param inode inode of the object tagged
	 * @return list of all the TagInode where the tags are associated to the object
	 */
	public static List getTagInodeByInode(String inode) {
		return tagAPI.getTagInodeByInode(inode);
	}

	/**
	 * Deletes an object tag assignment(s)
	 * @param tagName name(s) of the tag(s)
	 * @param inode inode of the object tagged
	 * @return a list of all tags assigned to an object
	 * @throws Exception 
	 */
	public List deleteTagInode(String tagName, String inode) throws Exception {
		return tagAPI.deleteTagInode(tagName, inode);
	}

	public static void deleteTagFullCommand(String tagName) 
	{
		try
		{
			HttpSession session = WebContextFactory.get().getSession();

			//Get all the user of the filter
			UserManagerListSearchForm searchFormFullCommand = (UserManagerListSearchForm) session.getAttribute(WebKeys.USERMANAGERLISTPARAMETERS);
			searchFormFullCommand.setStartRow(0);
			searchFormFullCommand.setMaxRow(0);
			List matches = UserManagerListBuilderFactory.doSearch(searchFormFullCommand);

			//Get the Iterator and the userIds
			Iterator it = matches.iterator();            
			for (int i = 0; it.hasNext(); i++) 
			{               
				String userTagId = (String) ((Map) it.next()).get("userid");
				String inode = com.dotmarketing.business.APILocator.getUserProxyAPI().getUserProxy(userTagId,APILocator.getUserAPI().getSystemUser(), false).getInode();			
				tagAPI.deleteTagInode(tagName, inode);
			}
		}
		catch(Exception ex)
		{
			String message = ex.toString();
			Logger.debug(TagAjax.class,message);
		}
	}

	/**
	 * Gets a suggested tag(s), by name
	 * @param name name of the tag searched
	 * @return list of suggested tags
	 */
	public List<Tag> getSuggestedTag(String tagName, String selectedHostId) {
		HttpServletRequest req = WebContextFactory.get().getHttpServletRequest();
		return tagAPI.getSuggestedTag(req, tagName, selectedHostId);
	}

	/**
	 * Get a list of all the tags created
	 * @return list of all tags created
	 */
	public static List<Tag> getAllTags() {
		return tagAPI.getAllTags();
	}
	
	
	public List<Tag> getUsersTags() {
		List<Tag> ret =  new ArrayList<Tag>();
		try
		{
			HttpSession session = WebContextFactory.get().getSession();
			HttpServletRequest req = WebContextFactory.get().getHttpServletRequest();
			User currentUser = com.liferay.portal.util.PortalUtil.getUser(req);
			List<String> userIds =  new ArrayList<String>();

			//Get all the user of the filter
			UserManagerListSearchForm searchFormFullCommand = (UserManagerListSearchForm) session.getAttribute(WebKeys.USERMANAGERLISTPARAMETERS);
			searchFormFullCommand.setStartRow(0);
			searchFormFullCommand.setMaxRow(0);
			List matches = UserManagerListBuilderFactory.doSearch(searchFormFullCommand);

			//Get the Iterator and the userIds
			Iterator it = matches.iterator();            
			for (int i = 0; it.hasNext(); i++) 
			{               
				String userTagId = (String) ((Map) it.next()).get("userid");
				userIds.add(userTagId);
				
			}
			
			ret = tagAPI.getAllTagsForUsers(userIds);
		}
		catch(Exception ex)
		{
			String message = ex.toString();
			Logger.debug(TagAjax.class,message);
		}
		return ret;
	}
	
	public static Map<String,Object> importTags(byte[] uploadFile) {
		
		Map<String,Object> callbackData = new HashMap<String,Object>();
		try {
			UserWebAPI uWebAPI = WebAPILocator.getUserWebAPI();
			WebContext ctx = WebContextFactory.get();
			HttpServletRequest request = ctx.getHttpServletRequest();
			User user = uWebAPI.getLoggedInUser(request);
			TagAPI tagAPI = APILocator.getTagAPI();
			
			String content = new String(uploadFile);
			StringReader sr = new StringReader(content);
			BufferedReader br = new BufferedReader(sr); 
			
			
			String str;
		    while ((str = br.readLine()) != null) {
		    	String[] tokens = str.split(",");
		    	String tagName = UtilMethods.isSet(tokens[0])?tokens[0]:null;
		    	String hostId = UtilMethods.isSet(tokens[1])?tokens[1]:null;
		    	if(!tagName.toLowerCase().contains("tag name") && !hostId.toLowerCase().contains("host id")){
		    		tagName = tagName.replaceAll("\'|\"", "");
		    		hostId = hostId.replaceAll("\'|\"", "");
		    		tagAPI.getTag(tagName, "", hostId);
		    	}
		    }
			
			br.close();

		} catch(Exception e) {
			e.printStackTrace();
		}
		return callbackData;
	}
	
	
}
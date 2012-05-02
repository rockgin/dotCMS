/*
 * Created on May 30, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dotmarketing.cache;

import com.dotmarketing.beans.Host;
import com.dotmarketing.beans.Identifier;
import com.dotmarketing.beans.Inode;
import com.dotmarketing.beans.WebAsset;
import com.dotmarketing.business.CacheLocator;
import com.dotmarketing.business.DotCacheAdministrator;
import com.dotmarketing.business.DotCacheException;
import com.dotmarketing.factories.IdentifierFactory;
import com.dotmarketing.factories.InodeFactory;
import com.dotmarketing.factories.PreviewFactory;
import com.dotmarketing.portlets.files.factories.FileFactory;
import com.dotmarketing.portlets.files.model.File;
import com.dotmarketing.portlets.folders.model.Folder;
import com.dotmarketing.portlets.htmlpages.model.HTMLPage;
import com.dotmarketing.portlets.links.model.Link;
import com.dotmarketing.util.Config;
import com.dotmarketing.util.InodeUtils;
import com.dotmarketing.util.Logger;
import com.dotmarketing.util.UtilMethods;

/**
 * @author David
 * @author Jason Tesser
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class WorkingCache {
    
    public static void addToWorkingAssetToCache(WebAsset asset){
    	DotCacheAdministrator cache = CacheLocator.getCacheAdministrator();
		// we use the identifier uri for our mappings.
		Identifier id = IdentifierFactory.getIdentifierByInode(asset);
		//Velocity Page Extension
		String ext = Config.getStringProperty("VELOCITY_PAGE_EXTENSION");
		//Obtain the URI
		String uri = id.getURI(); 		
		//Obtain the INODE
		String hostId = id.getHostId();

		if (UtilMethods.isSet(uri)) 
		{		    
			Logger.debug(WorkingCache.class, "Mapping Working: " + uri + " to " + uri);
			if(uri.endsWith("." + ext))
			{
			    //add it to the cache
				//for now we are adding the page URI
				cache.put(getPrimaryGroup() + hostId + "-" + uri,uri, getPrimaryGroup() + "_" + hostId);

				//if this is an index page, map its directories to it
				if(id.getURI().endsWith("/index." + ext))
				{
					Logger.debug(WorkingCache.class, "Mapping Working: " + uri.substring(0,uri.lastIndexOf("/index." + ext)) + " to " + uri);
					cache.put(getPrimaryGroup() + hostId + "-" + uri.substring(0,uri.lastIndexOf("/index." + ext)),uri, getPrimaryGroup() + "_" + hostId);
					Logger.debug(WorkingCache.class, "Mapping Working: " + id.getURI().substring(0,id.getURI().lastIndexOf("index." + ext)) + " to " + uri);
					cache.put(getPrimaryGroup() + hostId + "-" + uri.substring(0,uri.lastIndexOf("index." + ext)), uri, getPrimaryGroup() + "_" + hostId);
				}
			}
			else if (asset instanceof Link) {
				Folder parent = (Folder) InodeFactory.getParentOfClass(asset, Folder.class);
				String path = ((Link)asset).getURI(parent);
				//add the entry to the cache
				Logger.debug(WorkingCache.class, "Mapping: " + uri + " to " + path);
				cache.put(getPrimaryGroup() + hostId + "-" + uri,path, getPrimaryGroup() + "_" + hostId);
			} else {
			    //it's a file
				String path = FileFactory.getRelativeAssetPath(asset);
				//add it to the cache
				Logger.debug(WorkingCache.class, "Mapping: " + uri + " to " + path);
				cache.put(getPrimaryGroup() + hostId + "-" + uri, path, getPrimaryGroup() + "_" + hostId);
			}
		}
		
	}
    
    public static String getPathFromCache(String URI, Host host){
	    return getPathFromCache (URI, host.getIdentifier());
	}

	    //Working cache methods
	public static String getPathFromCache(String URI, String hostId){
		DotCacheAdministrator cache = CacheLocator.getCacheAdministrator();
		String _uri = null;
		try{
			_uri = (String) cache.get(getPrimaryGroup() + hostId + "-" + URI,getPrimaryGroup() + "_" + hostId);
		}catch (DotCacheException e) {
			Logger.debug(WorkingCache.class, "Cache Entry not found", e);
    	}

		if(_uri != null) return _uri;
		
		String ext = Config.getStringProperty("VELOCITY_PAGE_EXTENSION");

		if (URI.endsWith("/")) {
			//it's a folder path, so I add index.html at the end
			URI += "index." + ext;
		}

		// lets try to lazy get it.
		Identifier id = IdentifierFactory.getIdentifierByURI(URI, hostId);

		if(!InodeUtils.isSet(id.getInode())) 
		{
			//it's a folder path, so I add index.html at the end
			URI += "/index." + ext;
			id = IdentifierFactory.getIdentifierByURI(URI, hostId);
			if(!InodeUtils.isSet(id.getInode()))
			{
			    return null;
			}
		}


		WebAsset asset = null;
		if(id.getURI().endsWith("." + ext))
		{
		    asset = (WebAsset) IdentifierFactory.getWorkingChildOfClass(id, HTMLPage.class);
		}
		else
		{
		    asset = (WebAsset) IdentifierFactory.getWorkingChildOfClass(id, File.class);
		}
		// add to cache now
		if(InodeUtils.isSet(asset.getInode()))
		{
			Logger.debug(PreviewFactory.class, "Lazy Preview Mapping: " + id.getURI() + " to " + URI);
		    addToWorkingAssetToCache(asset);
		}
		try{	
			return (String) cache.get(getPrimaryGroup() + hostId + "-" + URI,getPrimaryGroup() + "_" + hostId);
		}catch (DotCacheException e) {
			Logger.debug(WorkingCache.class,"Cache Entry not found", e);
			return null; 
    	}
	}

	public static void removeURIFromCache(String URI, Host host){
	    removeURIFromCache (URI, host.getIdentifier());
	}
	
	public static void removeURIFromCache(String URI, String hostId){
		DotCacheAdministrator cache = CacheLocator.getCacheAdministrator();
			cache.remove(getPrimaryGroup() + hostId + "-" + URI,getPrimaryGroup() + "_" + hostId);	
	}

	public static void removeAssetFromCache(WebAsset asset){
		DotCacheAdministrator cache = CacheLocator.getCacheAdministrator();
		Class c = InodeUtils.getClassByDBType(asset.getType());
		Inode inode = InodeFactory.getInode((asset.getInode()),c);
		Identifier identifier = IdentifierFactory.getIdentifierByInode(inode);
		cache.remove(getPrimaryGroup() + identifier.getHostId() + "-" + identifier.getURI(),getPrimaryGroup() + "_" + identifier.getHostId());
	}
	
	public static void clearCache(String hostId){
		DotCacheAdministrator cache = CacheLocator.getCacheAdministrator();
	    //clear the cache
	    cache.flushGroup(getPrimaryGroup() + "_" + hostId);
	}
	public static String[] getGroups() {
    	String[] groups = {getPrimaryGroup()};
    	return groups;
    }
    
    public static String getPrimaryGroup() {
    	return "WorkingCache";
    }    
}

package com.dotmarketing.factories;

import com.dotmarketing.beans.Rating;
import com.dotmarketing.db.DotHibernate;

/**
 * This factory manage the search in the rating table
 * @author Armando Siem
 *
 */
public class RatingsFactory {

	/**
	 * Return the rating of the content by userid
	 * @param inode
	 * @param userId
	 * @return Rating
	 */
	public static Rating getRatingByUserId(String identifier, String userId) {
		if(identifier == null || userId == null){
			return new Rating();
		}
		DotHibernate dh = new DotHibernate();
		dh.setClass(Rating.class);
		dh.setQuery("from content_rating in class com.dotmarketing.beans.Rating where user_id = '" + userId + "' and identifier = '" + identifier +"'" );
		Rating r = (Rating) dh.load();
		return r;
	}
	
	/**
	 * Return the rating searching by Session Id
	 * @param inode
	 * @param sessionId
	 * @return
	 */
	public static Rating getRatingBySessionId(String identifier, String sessionId) {
		if(identifier == null || sessionId == null){
			return new Rating();
		}
		DotHibernate dh = new DotHibernate();
		dh.setClass(Rating.class);
		dh.setQuery("from content_rating in class com.dotmarketing.beans.Rating where session_id = '" + sessionId + "' and identifier = '" + identifier + "'");
		Rating r = (Rating) dh.load();
		return r;
	}
	
	/**
	 * Return the rating searching by the long live cookie id
	 * @param inode
	 * @param longCookieId
	 * @return Rating
	 * @author Oswaldo Gallango
	 */
	public static Rating getRatingByLongCookieId(String identifier, String longCookieId) {
		if(identifier == null || longCookieId == null){
			return new Rating();
		}
		DotHibernate dh = new DotHibernate();
		dh.setClass(Rating.class);
		dh.setQuery("from content_rating in class com.dotmarketing.beans.Rating where long_live_cookie_id = '" + longCookieId + "' and identifier = '" + identifier + "'");
		Rating r = (Rating) dh.load();
		return r;
	}

	/**
	 * Saves rating object to db
	 * @param rt
	 */
	public static void saveRating(Rating rt) {
		DotHibernate.saveOrUpdate(rt);
		
	}
	
	
}
package com.dotmarketing.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dotmarketing.business.APILocator;
import com.dotmarketing.factories.ClickstreamFactory;
import com.dotmarketing.factories.InodeFactory;
import com.dotmarketing.portlets.campaigns.factories.ClickFactory;
import com.dotmarketing.portlets.campaigns.factories.RecipientFactory;
import com.dotmarketing.portlets.campaigns.model.Campaign;
import com.dotmarketing.portlets.campaigns.model.Click;
import com.dotmarketing.portlets.campaigns.model.Recipient;
import com.dotmarketing.util.Config;
import com.dotmarketing.util.InodeUtils;
import com.dotmarketing.util.Logger;
import com.liferay.portal.model.User;

public class RedirectURLServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String query = request.getQueryString();
		String redir = null;

		try {

			redir = query.substring(query.indexOf("redir=") + 6);
		}
		catch (Exception e) {
		}

        Logger.debug(this, "redir" + redir);

		if (redir != null) {
			try {

				redir = redir.substring(0, redir.indexOf("&r="));
			}

			catch (Exception e) {
			}

			Recipient r = RecipientFactory.getRecipient(request.getParameter("r"));
			Campaign c = (Campaign) InodeFactory.getParentOfClass(r, Campaign.class);
			if (InodeUtils.isSet(r.getInode())) {

				//update recipient click through-links
				Click click = ClickFactory.getClickByLinkAndRecipient(redir, r);
				click.setClickCount((click.getClickCount() + 1));
				click.setLink(redir);
				InodeFactory.saveInode(click);
				r.addChild(click);
				InodeFactory.saveInode(r);

				//update queue clickthrough links
				click = ClickFactory.getClickByLinkAndCampaign(redir, c);
				click.setClickCount((click.getClickCount() + 1));
				click.setLink(redir);
				InodeFactory.saveInode(click);
				c.addChild(click);
				InodeFactory.saveInode(c);
				if(Config.getBooleanProperty("ENABLE_CLICKSTREAM_TRACKING", false)){
					try{
						User user = APILocator.getUserAPI().loadByUserByEmail(r.getEmail(), APILocator.getUserAPI().getSystemUser(), false);
						ClickstreamFactory.setClickStreamUser(user.getUserId(), request);
					}catch (Exception e) {
						Logger.error(this, e.getMessage(), e);
					}
				}
			}
			//do redirect first for optimal user expierience(tm)
			response.sendRedirect(redir);
			response.flushBuffer();
			
		}

	}
}

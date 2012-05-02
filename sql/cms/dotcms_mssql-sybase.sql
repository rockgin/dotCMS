create table calendar_reminder (
   user_id varchar(255) not null,
   event_id varchar(100) not null,
   send_date datetime not null,
   primary key (user_id, event_id, send_date)
);
create table analytic_summary_pages (
   id numeric(19,0) identity not null,
   summary_id numeric(19,0) not null,
   inode varchar(255) null,
   hits numeric(19,0) null,
   uri varchar(255) null,
   primary key (id)
);
create table ecom_product_price (
   inode varchar(100) not null,
   product_format_inode varchar(100) not null,
   min_qty int null,
   max_qty int null,
   retail_price float not null,
   partner_price float not null,
   primary key (inode)
);
create table tag (
   tag_id varchar(100) not null,
   tagname varchar(255) null,
   host_id varchar(255) null,
   user_id varchar(255) null,
   primary key (tag_id)
);
create table entity (
   inode varchar(100) not null,
   entity_name varchar(255) null,
   primary key (inode)
);
create table user_comments (
   inode varchar(100) not null,
   user_id varchar(255) null,
   cdate datetime null,
   comment_user_id varchar(100) null,
   type varchar(255) null,
   method varchar(255) null,
   subject varchar(255) null,
   ucomment text null,
   communication_id varchar(100) null,
   primary key (inode)
);
create table permission_reference (
   id numeric(19,0) identity not null,
   asset_id varchar(100) null,
   reference_id varchar(100) null,
   permission_type varchar(100) null,
   primary key (id),
   unique (asset_id)
);
create table fixes_audit (
   id varchar(255) not null,
   table_name varchar(255) null,
   action varchar(255) null,
   records_altered int null,
   datetime datetime null,
   primary key (id)
);
create table trackback (
   id numeric(19,0) identity not null,
   asset_identifier varchar(100) null,
   title varchar(255) null,
   excerpt varchar(255) null,
   url varchar(255) null,
   blog_name varchar(255) null,
   track_date datetime not null,
   primary key (id)
);
create table plugin (
   id varchar(255) not null,
   plugin_name varchar(255) not null,
   plugin_version varchar(255) not null,
   author varchar(255) not null,
   first_deployed_date datetime not null,
   last_deployed_date datetime not null,
   primary key (id)
);
create table recipient (
   inode varchar(100) not null,
   name varchar(255) null,
   lastname varchar(255) null,
   email varchar(255) null,
   sent datetime null,
   opened datetime null,
   last_result int null,
   last_message varchar(255) null,
   user_id varchar(100) null,
   primary key (inode)
);
create table mailing_list (
   inode varchar(100) not null,
   title varchar(255) null,
   public_list tinyint null,
   user_id varchar(255) null,
   primary key (inode)
);
create table web_form (
   web_form_id varchar(100) not null,
   form_type varchar(255) null,
   submit_date datetime null,
   prefix varchar(255) null,
   first_name varchar(255) null,
   middle_initial varchar(255) null,
   middle_name varchar(255) null,
   full_name varchar(255) null,
   organization varchar(255) null,
   title varchar(255) null,
   last_name varchar(255) null,
   address varchar(255) null,
   address1 varchar(255) null,
   address2 varchar(255) null,
   city varchar(255) null,
   state varchar(255) null,
   zip varchar(255) null,
   country varchar(255) null,
   phone varchar(255) null,
   email varchar(255) null,
   custom_fields text null,
   user_inode varchar(100) null,
   categories varchar(255) null,
   primary key (web_form_id)
);
create table virtual_link (
   inode varchar(100) not null,
   title varchar(255) null,
   url varchar(255) null,
   uri varchar(255) null,
   active tinyint null,
   primary key (inode)
);
create table analytic_summary_period (
   id numeric(19,0) identity not null,
   full_date datetime null,
   day int null,
   week int null,
   month int null,
   year varchar(255) null,
   dayname varchar(50) not null,
   monthname varchar(50) not null,
   primary key (id),
   unique (full_date)
);
create table tree (
   child varchar(100) not null,
   parent varchar(100) not null,
   relation_type varchar(64) not null,
   tree_order int null,
   primary key (child, parent, relation_type)
);
create table analytic_summary (
   id numeric(19,0) identity not null,
   summary_period_id numeric(19,0) not null,
   host_id varchar(50) not null,
   visits numeric(19,0) null,
   page_views numeric(19,0) null,
   unique_visits numeric(19,0) null,
   new_visits numeric(19,0) null,
   direct_traffic numeric(19,0) null,
   referring_sites numeric(19,0) null,
   search_engines numeric(19,0) null,
   bounce_rate int null,
   avg_time_on_site datetime null,
   primary key (id),
   unique (summary_period_id, host_id)
);
create table event (
   inode varchar(100) not null,
   title varchar(255) null,
   subtitle varchar(255) null,
   start_date datetime null,
   end_date datetime null,
   location varchar(255) null,
   address1 varchar(255) null,
   address2 varchar(255) null,
   address3 varchar(255) null,
   city varchar(255) null,
   state varchar(255) null,
   zip varchar(32) null,
   country varchar(64) null,
   email varchar(64) null,
   phone varchar(64) null,
   fax varchar(64) null,
   url varchar(255) null,
   registration tinyint null,
   include_file varchar(255) null,
   show_public tinyint null,
   contact_name varchar(255) null,
   contact_company varchar(255) null,
   contact_phone varchar(255) null,
   contact_email varchar(255) null,
   contact_fax varchar(255) null,
   directions text null,
   description text null,
   email_response text null,
   web_address varchar(255) null,
   user_id varchar(255) null,
   featured tinyint null,
   setup_date datetime null,
   break_date datetime null,
   approval_status int null,
   comments_equipment text null,
   received_adm_approval tinyint null,
   time_tbd tinyint null,
   primary key (inode)
);
create table users_cms_roles (
   id varchar(255) not null,
   user_id varchar(100) not null,
   role_id varchar(100) not null,
   primary key (id)
);
create table web_event (
   inode varchar(100) not null,
   title varchar(255) null,
   subtitle varchar(255) null,
   summary varchar(1000) null,
   description varchar(255) null,
   terms_conditions varchar(255) null,
   comments varchar(255) null,
   partners_only tinyint null,
   show_on_web tinyint null,
   sort_order int null,
   event_image_1 varchar(100) null,
   event_image_2 varchar(100) null,
   event_image_3 varchar(100) null,
   event_image_4 varchar(100) null,
   is_institute tinyint null,
   primary key (inode)
);
create table ecom_product (
   inode varchar(100) not null,
   title varchar(255) not null,
   short_description text null,
   long_description text null,
   req_shipping tinyint null,
   featured tinyint null,
   sort_order int null,
   comments text null,
   showonweb tinyint null,
   primary key (inode)
);
create table template (
   inode varchar(100) not null,
   live tinyint null,
   working tinyint null,
   deleted tinyint null,
   locked tinyint null,
   show_on_menu tinyint null,
   title varchar(255) null,
   mod_date datetime null,
   mod_user varchar(100) null,
   sort_order int null,
   friendly_name varchar(255) null,
   body text null,
   header text null,
   footer text null,
   image varchar(100) null,
   primary key (inode)
);
create table ecom_order_item (
   inode varchar(100) not null,
   order_inode varchar(100) null,
   product_inode varchar(100) null,
   item_qty int null,
   item_price float null,
   primary key (inode)
);
create table analytic_summary_content (
   id numeric(19,0) identity not null,
   summary_id numeric(19,0) not null,
   inode varchar(255) null,
   hits numeric(19,0) null,
   uri varchar(255) null,
   title varchar(255) null,
   primary key (id)
);
create table structure (
   inode varchar(100) not null,
   name varchar(255) null,
   description varchar(255) null,
   default_structure tinyint null,
   review_interval varchar(255) null,
   reviewer_role varchar(255) null,
   page_detail varchar(100) null,
   structuretype int null,
   system tinyint null,
   fixed tinyint not null,
   velocity_var_name varchar(255) null,
   url_map_pattern varchar(512) null,
   host varchar(100) not null,
   folder varchar(100) not null,
   primary key (inode)
);
create table ecom_discount_code (
   inode varchar(100) not null,
   discount_type int null,
   start_date datetime null,
   end_date datetime null,
   code_id varchar(50) null,
   code_description varchar(100) null,
   free_shipping tinyint null,
   no_bulk_disc tinyint null,
   discount_amount float null,
   min_order int null,
   primary key (inode)
);
create table cms_role (
   id varchar(100) not null,
   role_name varchar(255) not null,
   description text null,
   role_key varchar(255) null,
   db_fqn varchar(1000) not null,
   parent varchar(100) not null,
   edit_permissions tinyint null,
   edit_users tinyint null,
   edit_layouts tinyint null,
   locked tinyint null,
   system tinyint null,
   primary key (id)
);
create table web_event_registration (
   inode varchar(100) not null,
   event_inode varchar(100) null,
   event_location_inode varchar(100) null,
   user_inode varchar(100) null,
   registration_status int null,
   date_posted datetime null,
   last_mod_date datetime null,
   total_paid float null,
   total_due float null,
   total_registration float null,
   payment_type int null,
   billing_address_1 varchar(255) null,
   billing_address_2 varchar(255) null,
   billing_city varchar(255) null,
   billing_state varchar(50) null,
   billing_zip varchar(50) null,
   billing_country varchar(50) null,
   billing_contact_name varchar(255) null,
   billing_contact_phone varchar(50) null,
   billing_contact_email varchar(255) null,
   card_name varchar(255) null,
   card_type varchar(50) null,
   card_number varchar(50) null,
   card_exp_month varchar(50) null,
   card_exp_year varchar(50) null,
   card_verification_value varchar(10) null,
   check_number varchar(50) null,
   check_bank_name varchar(255) null,
   po_number varchar(50) null,
   invoice_number varchar(50) null,
   badge_printed tinyint null,
   how_did_you_hear varchar(255) null,
   ceo_name varchar(255) null,
   modified_qb tinyint null,
   reminder_email_sent tinyint null,
   post_email_sent tinyint null,
   primary key (inode)
);
create table permission (
   id numeric(19,0) identity not null,
   permission_type varchar(100) null,
   inode_id varchar(100) null,
   roleid varchar(100) null,
   permission int null,
   primary key (id),
   unique (permission_type, inode_id, roleid)
);
create table recurance (
   inode varchar(100) not null,
   recurrance_occurs varchar(255) null,
   recurrance_interval int null,
   recurrance_starting datetime null,
   recurrance_ending datetime null,
   recurrance_days_of_week varchar(255) null,
   recurrance_day_of_month int null,
   primary key (inode)
);
	create table contentlet (inode varchar(100) not null,
	live tinyint null,
	working tinyint null,
	deleted tinyint null,
	locked tinyint null,
	show_on_menu tinyint null,
	title varchar(255) null,
	mod_date datetime null,
	mod_user varchar(100) null,
	sort_order int null,
	friendly_name varchar(255) null,
	language_id numeric(19,0) null,
	structure_inode varchar(100) null,
	last_review datetime null,
	next_review datetime null,
	review_interval varchar(255) null,
	disabled_wysiwyg varchar(255) null,
	folder varchar(100) null,
	date1 datetime null,
	date2 datetime null,
	date3 datetime null,
	date4 datetime null,
	date5 datetime null,
	date6 datetime null,
	date7 datetime null,
	date8 datetime null,
	date9 datetime null,
	date10 datetime null,
	date11 datetime null,
	date12 datetime null,
	date13 datetime null,
	date14 datetime null,
	date15 datetime null,
	date16 datetime null,
	date17 datetime null,
	date18 datetime null,
	date19 datetime null,
	date20 datetime null,
	date21 datetime null,
	date22 datetime null,
	date23 datetime null,
	date24 datetime null,
	date25 datetime null,
	text1 varchar(255) null,
	text2 varchar(255) null,
	text3 varchar(255) null,
	text4 varchar(255) null,
	text5 varchar(255) null,
	text6 varchar(255) null,
	text7 varchar(255) null,
	text8 varchar(255) null,
	text9 varchar(255) null,
	text10 varchar(255) null,
	text11 varchar(255) null,
	text12 varchar(255) null,
	text13 varchar(255) null,
	text14 varchar(255) null,
	text15 varchar(255) null,
	text16 varchar(255) null,
	text17 varchar(255) null,
	text18 varchar(255) null,
	text19 varchar(255) null,
	text20 varchar(255) null,
	text21 varchar(255) null,
	text22 varchar(255) null,
	text23 varchar(255) null,
	text24 varchar(255) null,
	text25 varchar(255) null,
	text_area1 text null,
	text_area2 text null,
	text_area3 text null,
	text_area4 text null,
	text_area5 text null,
	text_area6 text null,
	text_area7 text null,
	text_area8 text null,
	text_area9 text null,
	text_area10 text null,
	text_area11 text null,
	text_area12 text null,
	text_area13 text null,
	text_area14 text null,
	text_area15 text null,
	text_area16 text null,
	text_area17 text null,
	text_area18 text null,
	text_area19 text null,
	text_area20 text null,
	text_area21 text null,
	text_area22 text null,
	text_area23 text null,
	text_area24 text null,
	text_area25 text null,
	integer1 numeric(19,0) null,
	integer2 numeric(19,0) null,
	integer3 numeric(19,0) null,
	integer4 numeric(19,0) null,
	integer5 numeric(19,0) null,
	integer6 numeric(19,0) null,
	integer7 numeric(19,0) null,
	integer8 numeric(19,0) null,
	integer9 numeric(19,0) null,
	integer10 numeric(19,0) null,
	integer11 numeric(19,0) null,
	integer12 numeric(19,0) null,
	integer13 numeric(19,0) null,
	integer14 numeric(19,0) null,
	integer15 numeric(19,0) null,
	integer16 numeric(19,0) null,
	integer17 numeric(19,0) null,
	integer18 numeric(19,0) null,
	integer19 numeric(19,0) null,
	integer20 numeric(19,0) null,
	integer21 numeric(19,0) null,
	integer22 numeric(19,0) null,
	integer23 numeric(19,0) null,
	integer24 numeric(19,0) null,
	integer25 numeric(19,0) null,
	"float1" float null,
	"float2" float null,
	"float3" float null,
	"float4" float null,
	"float5" float null,
	"float6" float null,
	"float7" float null,
	"float8" float null,
	"float9" float null,
	"float10" float null,
	"float11" float null,
	"float12" float null,
	"float13" float null,
	"float14" float null,
	"float15" float null,
	"float16" float null,
	"float17" float null,
	"float18" float null,
	"float19" float null,
	"float20" float null,
	"float21" float null,
	"float22" float null,
	"float23" float null,
	"float24" float null,
	"float25" float null,
	bool1 tinyint null,
	bool2 tinyint null,
	bool3 tinyint null,
	bool4 tinyint null,
	bool5 tinyint null,
	bool6 tinyint null,
	bool7 tinyint null,
	bool8 tinyint null,
	bool9 tinyint null,
	bool10 tinyint null,
	bool11 tinyint null,
	bool12 tinyint null,
	bool13 tinyint null,
	bool14 tinyint null,
	bool15 tinyint null,
	bool16 tinyint null,
	bool17 tinyint null,
	bool18 tinyint null,
	bool19 tinyint null,
	bool20 tinyint null,
	bool21 tinyint null,
	bool22 tinyint null,
	bool23 tinyint null,
	bool24 tinyint null,
	bool25 tinyint null,
	primary key (inode));
create table analytic_summary_404 (
   id numeric(19,0) identity not null,
   summary_period_id numeric(19,0) not null,
   host_id varchar(50) null,
   uri varchar(255) null,
   referer_uri varchar(255) null,
   primary key (id)
);
create table cms_layouts_portlets (
   id varchar(255) not null,
   layout_id varchar(100) not null,
   portlet_id varchar(100) not null,
   portlet_order int null,
   primary key (id)
);
create table workflow_comment (
   inode varchar(100) not null,
   creation_date datetime null,
   posted_by varchar(255) null,
   wf_comment text null,
   primary key (inode)
);
create table report_asset (
   inode varchar(100) not null,
   report_name varchar(255) not null,
   report_description varchar(1000) not null,
   requires_input tinyint null,
   ds varchar(100) not null,
   web_form_report tinyint null,
   primary key (inode)
);
create table category (
   inode varchar(100) not null,
   category_name varchar(255) null,
   category_key varchar(255) null,
   sort_order int null,
   active tinyint null,
   keywords text null,
   category_velocity_var_name varchar(255) null,
   primary key (inode)
);
create table htmlpage (
   inode varchar(100) not null,
   live tinyint null,
   working tinyint null,
   deleted tinyint null,
   locked tinyint null,
   show_on_menu tinyint null,
   title varchar(255) null,
   mod_date datetime null,
   mod_user varchar(100) null,
   sort_order int null,
   friendly_name varchar(255) null,
   metadata text null,
   start_date datetime null,
   end_date datetime null,
   page_url varchar(255) null,
   https_required tinyint null,
   redirect varchar(255) null,
   seo_description text null,
   seo_keywords text null,
   cache_ttl numeric(19,0) null,
   primary key (inode)
);
create table chain_link_code (
   id numeric(19,0) identity not null,
   class_name varchar(255) null unique,
   code text not null,
   last_mod_date datetime not null,
   language varchar(255) not null,
   primary key (id)
);
create table analytic_summary_visits (
   id numeric(19,0) identity not null,
   summary_period_id numeric(19,0) not null,
   host_id varchar(50) null,
   visit_time datetime null,
   visits numeric(19,0) null,
   primary key (id)
);
create table language (
   id numeric(19,0) identity not null,
   language_code varchar(5) null,
   country_code varchar(255) null,
   language varchar(255) null,
   country varchar(255) null,
   primary key (id)
);
create table user_preferences (
   id numeric(19,0) identity not null,
   user_id varchar(100) not null,
   preference varchar(255) null,
   pref_value text null,
   primary key (id)
);
create table users_to_delete (
   id numeric(19,0) identity not null,
   user_id varchar(255) null,
   primary key (id)
);
create table identifier (
   inode varchar(100) not null,
   uri varchar(255) null,
   host_inode varchar(255) null,
   primary key (inode),
   unique (uri, host_inode)
);
create table clickstream (
   clickstream_id numeric(19,0) identity not null,
   cookie_id varchar(255) null,
   user_id varchar(255) null,
   start_date datetime null,
   end_date datetime null,
   referer varchar(255) null,
   remote_address varchar(255) null,
   remote_hostname varchar(255) null,
   user_agent varchar(255) null,
   bot tinyint null,
   number_of_requests int null,
   host_id varchar(50) null,
   last_page_id varchar(50) null,
   first_page_id varchar(50) null,
   operating_system varchar(50) null,
   browser_name varchar(50) null,
   browser_version varchar(50) null,
   mobile_device tinyint null,
   primary key (clickstream_id)
);
create table web_event_location (
   inode varchar(100) not null,
   web_event_inode varchar(100) null,
   city varchar(255) null,
   state varchar(50) null,
   start_date datetime null,
   end_date datetime null,
   show_on_web tinyint null,
   web_reg_active tinyint null,
   hotel_name varchar(255) null,
   hotel_link numeric(19,0) null,
   past_event_link numeric(19,0) null,
   partner_price float null,
   non_partner_price float null,
   short_description varchar(255) null,
   text_email varchar(1000) null,
   almost_at_capacity tinyint null,
   full_capacity tinyint null,
   default_contract_partner_price tinyint null,
   primary key (inode)
);
create table multi_tree (
   child varchar(100) not null,
   parent1 varchar(100) not null,
   parent2 varchar(100) not null,
   relation_type varchar(64) null,
   tree_order int null,
   primary key (child, parent1, parent2)
);
create table tag_inode (
   tag_id varchar(100) not null,
   inode varchar(100) not null,
   primary key (tag_id, inode)
);
create table workflow_task (
   inode varchar(100) not null,
   creation_date datetime null,
   mod_date datetime null,
   due_date datetime null,
   created_by varchar(255) null,
   assigned_to varchar(255) null,
   belongs_to varchar(255) null,
   title varchar(255) null,
   description text null,
   status varchar(255) null,
   webasset varchar(255) null,
   primary key (inode)
);
create table click (
   inode varchar(100) not null,
   link varchar(255) null,
   click_count int null,
   primary key (inode)
);
create table event_registration (
   inode varchar(100) not null,
   registration_date datetime null,
   full_name varchar(255) null,
   number_attending int null,
   comments text null,
   email varchar(255) null,
   primary key (inode)
);
create table challenge_question (
   cquestionid numeric(19,0) not null,
   cqtext varchar(255) null,
   primary key (cquestionid)
);
create table file_asset (
   inode varchar(100) not null,
   file_name varchar(255) null,
   file_size int null,
   width int null,
   height int null,
   mime_type varchar(255) null,
   author varchar(255) null,
   publish_date datetime null,
   live tinyint null,
   working tinyint null,
   deleted tinyint null,
   locked tinyint null,
   show_on_menu tinyint null,
   title varchar(255) null,
   friendly_name varchar(255) null,
   mod_date datetime null,
   mod_user varchar(100) null,
   sort_order int null,
   primary key (inode)
);
create table layouts_cms_roles (
   id varchar(255) not null,
   layout_id varchar(100) not null,
   role_id varchar(100) not null,
   primary key (id)
);
create table organization (
   inode varchar(100) not null,
   title varchar(255) null,
   ceo_name varchar(255) null,
   partner_url varchar(255) null,
   partner_key varchar(255) null,
   partner_logo varchar(100) null,
   street1 varchar(255) null,
   street2 varchar(255) null,
   city varchar(255) null,
   state varchar(255) null,
   zip varchar(100) null,
   phone varchar(100) null,
   fax varchar(100) null,
   country varchar(255) null,
   is_system tinyint null,
   parent_organization varchar(100) null,
   primary key (inode)
);
create table facility (
   inode varchar(100) not null,
   facility_name varchar(255) not null,
   facility_description varchar(255) null,
   sort_order int null,
   active tinyint null,
   primary key (inode)
);
create table clickstream_request (
   clickstream_request_id numeric(19,0) identity not null,
   clickstream_id numeric(19,0) null,
   server_name varchar(255) null,
   protocol varchar(255) null,
   server_port int null,
   request_uri varchar(255) null,
   request_order int null,
   query_string text null,
   language_id numeric(19,0) null,
   timestampper datetime null,
   host_id varchar(255) null,
   associated_identifier varchar(50) null,
   primary key (clickstream_request_id)
);
create table chain_state (
   id numeric(19,0) identity not null,
   chain_id numeric(19,0) not null,
   link_code_id numeric(19,0) not null,
   state_order numeric(19,0) not null,
   primary key (id)
);
create table content_rating (
   id numeric(19,0) identity not null,
   rating float null,
   user_id varchar(255) null,
   session_id varchar(255) null,
   identifier varchar(100) null,
   rating_date datetime null,
   user_ip varchar(255) null,
   long_live_cookie_id varchar(255) null,
   primary key (id)
);
create table analytic_summary_workstream (
   id numeric(19,0) identity not null,
   inode varchar(255) null,
   asset_type varchar(255) null,
   mod_user_id varchar(255) null,
   host_id varchar(50) null,
   mod_date datetime null,
   action varchar(255) null,
   name varchar(255) null,
   primary key (id)
);
create table dashboard_user_preferences (
   id numeric(19,0) identity not null,
   summary_404_id numeric(19,0) null,
   user_id varchar(255) null,
   ignored tinyint null,
   mod_date datetime null,
   primary key (id)
);
create table campaign (
   inode varchar(100) not null,
   title varchar(255) null,
   from_email varchar(255) null,
   from_name varchar(255) null,
   subject varchar(255) null,
   message text null,
   user_id varchar(255) null,
   start_date datetime null,
   completed_date datetime null,
   active tinyint null,
   locked tinyint null,
   sends_per_hour varchar(15) null,
   sendemail tinyint null,
   communicationinode varchar(100) null,
   userfilterinode varchar(100) null,
   sendto varchar(15) null,
   isrecurrent tinyint null,
   wassent tinyint null,
   expiration_date datetime null,
   parent_campaign varchar(100) null,
   primary key (inode)
);
create table banner (
   inode varchar(100) not null,
   title varchar(255) null,
   caption text null,
   new_window tinyint null,
   link varchar(255) null,
   start_date datetime null,
   end_date datetime null,
   body varchar(255) null,
   active tinyint null,
   nmbr_views int null,
   nmbr_clicks int null,
   image varchar(100) null,
   path varchar(500) null,
   placement varchar(255) null,
   primary key (inode)
);
create table analytic_summary_referer (
   id numeric(19,0) identity not null,
   summary_id numeric(19,0) not null,
   hits numeric(19,0) null,
   uri varchar(255) null,
   primary key (id)
);
create table containers (
   inode varchar(100) not null,
   code text null,
   pre_loop text null,
   post_loop text null,
   live tinyint null,
   working tinyint null,
   deleted tinyint null,
   locked tinyint null,
   show_on_menu tinyint null,
   title varchar(255) null,
   mod_date datetime null,
   mod_user varchar(100) null,
   sort_order int null,
   friendly_name varchar(255) null,
   max_contentlets int null,
   use_div tinyint null,
   staticify tinyint null,
   sort_contentlets_by varchar(255) null,
   lucene_query text null,
   notes varchar(255) null,
   primary key (inode)
);
create table ecom_order (
   inode varchar(100) not null,
   user_inode varchar(100) null,
   order_status int null,
   payment_status int null,
   date_posted datetime null,
   last_mod_date datetime null,
   billing_address1 varchar(255) null,
   billing_address2 varchar(255) null,
   billing_city varchar(100) null,
   billing_state varchar(50) null,
   billing_zip varchar(50) null,
   billing_country varchar(50) null,
   billing_phone varchar(50) null,
   billing_fax varchar(50) null,
   billing_first_name varchar(100) null,
   billing_last_name varchar(100) null,
   billing_contact_name varchar(100) null,
   billing_contact_phone varchar(50) null,
   billing_contact_email varchar(100) null,
   shipping_address1 varchar(255) null,
   shipping_address2 varchar(255) null,
   shipping_city varchar(50) null,
   shipping_state varchar(50) null,
   shipping_zip varchar(50) null,
   shipping_country varchar(50) null,
   shipping_phone varchar(50) null,
   shipping_fax varchar(50) null,
   payment_type varchar(10) null,
   name_on_card varchar(100) null,
   card_type varchar(50) null,
   card_number varchar(50) null,
   card_exp_month int null,
   card_exp_year int null,
   card_verification_value varchar(50) null,
   order_sub_total float null,
   order_shipping float null,
   order_ship_type int null,
   order_tax float null,
   order_discount float null,
   tax_exempt_number varchar(50) null,
   discount_codes varchar(50) null,
   order_total float null,
   order_total_paid float null,
   order_total_due float null,
   invoice_number varchar(50) null,
   invoice_date datetime null,
   check_number varchar(50) null,
   check_bank_name varchar(100) null,
   po_number varchar(50) null,
   tracking_number varchar(255) null,
   modified_qb tinyint null,
   modified_fh tinyint null,
   backend_user varchar(100) null,
   shipping_label varchar(50) null,
   primary key (inode)
);
create table web_event_attendee (
   inode varchar(100) not null,
   event_registration_inode varchar(100) null,
   first_name varchar(255) null,
   last_name varchar(255) null,
   badge_name varchar(255) null,
   email varchar(255) null,
   title varchar(255) null,
   registration_price float null,
   primary key (inode)
);
create table communication (
   inode varchar(100) not null,
   title varchar(255) null,
   trackback_link_inode varchar(100) null,
   communication_type varchar(255) null,
   from_name varchar(255) null,
   from_email varchar(255) null,
   email_subject varchar(255) null,
   html_page_inode varchar(100) null,
   text_message text null,
   mod_date datetime null,
   modified_by varchar(255) null,
   ext_comm_id varchar(255) null,
   primary key (inode)
);
create table workflow_history (
   inode varchar(100) not null,
   creation_date datetime null,
   made_by varchar(255) null,
   change_desc text null,
   primary key (inode)
);
create table host_variable (
   id varchar(255) not null,
   host_id varchar(255) null,
   variable_name varchar(255) null,
   variable_key varchar(255) null,
   variable_value varchar(255) null,
   user_id varchar(255) null,
   last_mod_date datetime null,
   primary key (id)
);
create table links (
   inode varchar(100) not null,
   live tinyint null,
   working tinyint null,
   deleted tinyint null,
   locked tinyint null,
   show_on_menu tinyint null,
   title varchar(255) null,
   mod_date datetime null,
   mod_user varchar(100) null,
   sort_order int null,
   friendly_name varchar(255) null,
   protocal varchar(100) null,
   url varchar(255) null,
   target varchar(100) null,
   internal_link_identifier varchar(100) null,
   link_type varchar(255) null,
   link_code text null,
   primary key (inode)
);
create table user_proxy (
   inode varchar(100) not null,
   user_id varchar(255) null,
   prefix varchar(255) null,
   suffix varchar(255) null,
   title varchar(255) null,
   school varchar(255) null,
   how_heard varchar(255) null,
   company varchar(255) null,
   long_lived_cookie varchar(255) null,
   website varchar(255) null,
   graduation_year int null,
   organization varchar(255) null,
   mail_subscription tinyint null,
   var1 varchar(255) null,
   var2 varchar(255) null,
   var3 varchar(255) null,
   var4 varchar(255) null,
   var5 varchar(255) null,
   var6 varchar(255) null,
   var7 varchar(255) null,
   var8 varchar(255) null,
   var9 varchar(255) null,
   var10 varchar(255) null,
   var11 varchar(255) null,
   var12 varchar(255) null,
   var13 varchar(255) null,
   var14 varchar(255) null,
   var15 varchar(255) null,
   var16 varchar(255) null,
   var17 varchar(255) null,
   var18 varchar(255) null,
   var19 varchar(255) null,
   var20 varchar(255) null,
   var21 varchar(255) null,
   var22 varchar(255) null,
   var23 varchar(255) null,
   var24 varchar(255) null,
   var25 varchar(255) null,
   last_result int null,
   last_message varchar(255) null,
   no_click_tracking tinyint null,
   cquestionid varchar(255) null,
   cqanswer varchar(255) null,
   chapter_officer varchar(255) null,
   primary key (inode),
   unique (user_id)
);
create table chain_state_parameter (
   id numeric(19,0) identity not null,
   chain_state_id numeric(19,0) not null,
   name varchar(255) not null,
   value varchar(255) not null,
   primary key (id)
);
create table clickstream_404 (
   clickstream_404_id numeric(19,0) identity not null,
   referer_uri varchar(255) null,
   query_string text null,
   request_uri varchar(255) null,
   user_id varchar(255) null,
   host_id varchar(50) null,
   timestampper datetime null,
   primary key (clickstream_404_id)
);
create table folder (
   inode varchar(100) not null,
   name varchar(255) null,
   path varchar(255) not null,
   title varchar(255) not null,
   show_on_menu tinyint null,
   sort_order int null,
   host_inode varchar(100) null,
   files_masks varchar(255) null,
   primary key (inode)
);
create table relationship (
   inode varchar(100) not null,
   parent_structure_inode varchar(255) null,
   child_structure_inode varchar(255) null,
   parent_relation_name varchar(255) null,
   child_relation_name varchar(255) null,
   relation_type_value varchar(255) null,
   cardinality int null,
   parent_required tinyint null,
   child_required tinyint null,
   fixed tinyint null,
   primary key (inode)
);
create table field (
   inode varchar(100) not null,
   structure_inode varchar(255) null,
   field_name varchar(255) null,
   field_type varchar(255) null,
   field_relation_type varchar(255) null,
   field_contentlet varchar(255) null,
   required tinyint null,
   indexed tinyint null,
   listed tinyint null,
   velocity_var_name varchar(255) null,
   sort_order int null,
   field_values text null,
   regex_check varchar(255) null,
   hint varchar(255) null,
   default_value varchar(255) null,
   fixed tinyint null,
   read_only tinyint null,
   searchable tinyint null,
   unique_ tinyint null,
   primary key (inode)
);
create table cms_layout (
   id varchar(100) not null,
   layout_name varchar(255) not null,
   description varchar(255) null,
   tab_order int null,
   primary key (id)
);
create table field_variable (
   id varchar(255) not null,
   field_id varchar(255) null,
   variable_name varchar(255) null,
   variable_key varchar(255) null,
   variable_value text null,
   user_id varchar(255) null,
   last_mod_date datetime null,
   primary key (id)
);
create table ecom_product_format (
   inode varchar(100) not null,
   product_inode varchar(100) not null,
   format_name varchar(255) not null,
   item_num varchar(50) null,
   format varchar(100) not null,
   inventory_quantity int null,
   reorder_trigger int null,
   weight float null,
   width int null,
   height int null,
   depth int null,
   primary key (inode)
);
create table report_parameter (
   inode varchar(100) not null,
   report_inode varchar(100) null,
   parameter_description varchar(1000) null,
   parameter_name varchar(255) null,
   class_type varchar(250) null,
   default_value varchar(4000) null,
   primary key (inode),
   unique (report_inode, parameter_name)
);
create table chain (
   id numeric(19,0) identity not null,
   key_name varchar(255) null unique,
   name varchar(255) not null,
   success_value varchar(255) not null,
   failure_value varchar(255) not null,
   primary key (id)
);
create table inode (
   inode varchar(100) not null,
   owner varchar(255) null,
   idate datetime null,
   type varchar(64) null,
   identifier varchar(100) null,
   primary key (inode)
);
create table user_filter (
   inode varchar(100) not null,
   title varchar(255) null,
   firstname varchar(100) null,
   middlename varchar(100) null,
   lastname varchar(100) null,
   emailaddress varchar(100) null,
   birthdaytypesearch varchar(100) null,
   birthday datetime null,
   birthdayfrom datetime null,
   birthdayto datetime null,
   lastlogintypesearch varchar(100) null,
   lastloginsince varchar(100) null,
   loginfrom datetime null,
   loginto datetime null,
   createdtypesearch varchar(100) null,
   createdsince varchar(100) null,
   createdfrom datetime null,
   createdto datetime null,
   lastvisittypesearch varchar(100) null,
   lastvisitsince varchar(100) null,
   lastvisitfrom datetime null,
   lastvisitto datetime null,
   city varchar(100) null,
   state varchar(100) null,
   country varchar(100) null,
   zip varchar(100) null,
   cell varchar(100) null,
   phone varchar(100) null,
   fax varchar(100) null,
   active_ varchar(255) null,
   tagname varchar(255) null,
   var1 varchar(255) null,
   var2 varchar(255) null,
   var3 varchar(255) null,
   var4 varchar(255) null,
   var5 varchar(255) null,
   var6 varchar(255) null,
   var7 varchar(255) null,
   var8 varchar(255) null,
   var9 varchar(255) null,
   var10 varchar(255) null,
   var11 varchar(255) null,
   var12 varchar(255) null,
   var13 varchar(255) null,
   var14 varchar(255) null,
   var15 varchar(255) null,
   var16 varchar(255) null,
   var17 varchar(255) null,
   var18 varchar(255) null,
   var19 varchar(255) null,
   var20 varchar(255) null,
   var21 varchar(255) null,
   var22 varchar(255) null,
   var23 varchar(255) null,
   var24 varchar(255) null,
   var25 varchar(255) null,
   categories varchar(255) null,
   primary key (inode)
);
alter table analytic_summary_pages add constraint fka1ad33b9ed30e054 foreign key (summary_id) references analytic_summary;
alter table ecom_product_price add constraint fkf3aa85f65fb51eb foreign key (inode) references inode;
create index idx_entity_1 on entity (entity_name);
alter table entity add constraint fkb29de3e35fb51eb foreign key (inode) references inode;
create index idx_user_comments_1 on user_comments (user_id);
alter table user_comments add constraint fkdf1b37e85fb51eb foreign key (inode) references inode;
create index idx_trackback_2 on trackback (url);
create index idx_trackback_1 on trackback (asset_identifier);
create index idx_communication_user_id on recipient (user_id);
create index idx_recipiets_1 on recipient (email);
create index idx_recipiets_2 on recipient (sent);
alter table recipient add constraint fk30e172195fb51eb foreign key (inode) references inode;
create index idx_mailinglist_1 on mailing_list (user_id);
alter table mailing_list add constraint fk7bc2cd925fb51eb foreign key (inode) references inode;
create index idx_user_webform_1 on web_form (form_type);
create index idx_virtual_link_1 on virtual_link (url);
alter table virtual_link add constraint fkd844f8ae5fb51eb foreign key (inode) references inode;
create index idx_analytic_summary_period_4 on analytic_summary_period (month);
create index idx_analytic_summary_period_3 on analytic_summary_period (week);
create index idx_analytic_summary_period_2 on analytic_summary_period (day);
create index idx_analytic_summary_period_5 on analytic_summary_period (year);
create index idx_analytic_summary_1 on analytic_summary (host_id);
create index idx_analytic_summary_2 on analytic_summary (visits);
create index idx_analytic_summary_3 on analytic_summary (page_views);
alter table analytic_summary add constraint fk9e1a7f4b7b46300 foreign key (summary_period_id) references analytic_summary_period;
create index idx_event_2 on event (end_date);
create index idx_event_1 on event (start_date);
alter table event add constraint fk5c6729a5fb51eb foreign key (inode) references inode;
create index ix_web_event on web_event (title);
create index ix_web_event_1 on web_event (sort_order);
alter table web_event add constraint fkcfabd1ef5fb51eb foreign key (inode) references inode;
alter table ecom_product add constraint fk24a022ac5fb51eb foreign key (inode) references inode;
alter table template add constraint fkb13acc7a5fb51eb foreign key (inode) references inode;
create index ix_ecom_order_item on ecom_order_item (order_inode);
alter table ecom_order_item add constraint fkebb882875fb51eb foreign key (inode) references inode;
alter table analytic_summary_content add constraint fk53cb4f2eed30e054 foreign key (summary_id) references analytic_summary;
alter table structure add constraint fk89d2d735fb51eb foreign key (inode) references inode;
create index uk_discount_code_id on ecom_discount_code (code_id);
alter table ecom_discount_code add constraint fk994566285fb51eb foreign key (inode) references inode;
create index ix_web_event_registration_3 on web_event_registration (date_posted);
create index ix_web_event_registration_2 on web_event_registration (user_inode);
create index ix_web_event_registration_1 on web_event_registration (event_location_inode);
create index ix_web_event_registration on web_event_registration (event_inode);
alter table web_event_registration add constraint fk60025d095fb51eb foreign key (inode) references inode;
create index idx_permission_2 on permission (permission_type, inode_id);
create index idx_permission_3 on permission (roleid);
alter table recurance add constraint fk457445fc5fb51eb foreign key (inode) references inode;
alter table contentlet add constraint fkfc4ef025fb51eb foreign key (inode) references inode;
create index idx_analytic_summary_404_1 on analytic_summary_404 (host_id);
alter table analytic_summary_404 add constraint fk7050866db7b46300 foreign key (summary_period_id) references analytic_summary_period;
alter table workflow_comment add constraint fk94993ddf5fb51eb foreign key (inode) references inode;
alter table report_asset add constraint fk3765ec255fb51eb foreign key (inode) references inode;
create index idx_category_1 on category (category_name);
create index idx_category_2 on category (category_key);
alter table category add constraint fk302bcfe5fb51eb foreign key (inode) references inode;
alter table htmlpage add constraint fkebf39cba5fb51eb foreign key (inode) references inode;
create index idx_chain_link_code_classname on chain_link_code (class_name);
create index idx_analytic_summary_visits_2 on analytic_summary_visits (visit_time);
create index idx_analytic_summary_visits_1 on analytic_summary_visits (host_id);
alter table analytic_summary_visits add constraint fk9eac9733b7b46300 foreign key (summary_period_id) references analytic_summary_period;
create index idx_preference_1 on user_preferences (preference);
alter table identifier add constraint fk9f88aca95fb51eb foreign key (inode) references inode;
create index idx_user_clickstream11 on clickstream (host_id);
create index idx_user_clickstream12 on clickstream (last_page_id);
create index idx_user_clickstream15 on clickstream (browser_name);
create index idx_user_clickstream_2 on clickstream (user_id);
create index idx_user_clickstream16 on clickstream (browser_version);
create index idx_user_clickstream_1 on clickstream (cookie_id);
create index idx_user_clickstream13 on clickstream (first_page_id);
create index idx_user_clickstream14 on clickstream (operating_system);
create index idx_user_clickstream17 on clickstream (remote_address);
create index ix_web_event_location_1 on web_event_location (state);
create index ix_web_event_location on web_event_location (city);
create index ix_web_event_location_3 on web_event_location (end_date);
create index ix_web_event_location_2 on web_event_location (start_date);
alter table web_event_location add constraint fk1d54bc055fb51eb foreign key (inode) references inode;
create index idx_multitree_1 on multi_tree (relation_type);
create index idx_workflow_4 on workflow_task (webasset);
create index idx_workflow_5 on workflow_task (created_by);
create index idx_workflow_2 on workflow_task (belongs_to);
create index idx_workflow_3 on workflow_task (status);
create index idx_workflow_1 on workflow_task (assigned_to);
alter table workflow_task add constraint fk441116055fb51eb foreign key (inode) references inode;
create index idx_click_1 on click (link);
alter table click add constraint fk5a5c5885fb51eb foreign key (inode) references inode;
alter table event_registration add constraint fke1516a3e5fb51eb foreign key (inode) references inode;
alter table file_asset add constraint fk7ed2366d5fb51eb foreign key (inode) references inode;
alter table organization add constraint fk4644ed335fb51eb foreign key (inode) references inode;
alter table facility add constraint fk1dde6ea35fb51eb foreign key (inode) references inode;
create index idx_user_clickstream_request_2 on clickstream_request (request_uri);
create index idx_user_clickstream_request_1 on clickstream_request (clickstream_id);
create index idx_user_clickstream_request_4 on clickstream_request (timestampper);
create index idx_user_clickstream_request_3 on clickstream_request (associated_identifier);
create index idx_dashboard_workstream_2 on analytic_summary_workstream (host_id);
create index idx_dashboard_workstream_1 on analytic_summary_workstream (mod_user_id);
create index idx_dashboard_workstream_3 on analytic_summary_workstream (mod_date);
create index idx_dashboard_prefs_2 on dashboard_user_preferences (user_id);
alter table dashboard_user_preferences add constraint fk496242cfd12c0c3b foreign key (summary_404_id) references analytic_summary_404;
create index idx_campaign_4 on campaign (expiration_date);
create index idx_campaign_3 on campaign (completed_date);
create index idx_campaign_2 on campaign (start_date);
create index idx_campaign_1 on campaign (user_id);
alter table campaign add constraint fkf7a901105fb51eb foreign key (inode) references inode;
alter table banner add constraint fkacc57f2c5fb51eb foreign key (inode) references inode;
alter table analytic_summary_referer add constraint fk5bc0f3e2ed30e054 foreign key (summary_id) references analytic_summary;
alter table containers add constraint fk8a844125fb51eb foreign key (inode) references inode;
alter table ecom_order add constraint fkf088284b5fb51eb foreign key (inode) references inode;
create index ix_web_event_attendee on web_event_attendee (event_registration_inode);
create index ix_web_event_attendee_1 on web_event_attendee (first_name);
create index ix_web_event_attendee_2 on web_event_attendee (last_name);
alter table web_event_attendee add constraint fkcc5ee90a5fb51eb foreign key (inode) references inode;
alter table communication add constraint fkc24acfd65fb51eb foreign key (inode) references inode;
alter table workflow_history add constraint fk933334145fb51eb foreign key (inode) references inode;
alter table links add constraint fk6234fb95fb51eb foreign key (inode) references inode;
alter table user_proxy add constraint fk7327d4fa5fb51eb foreign key (inode) references inode;
create index idx_user_clickstream_404_2 on clickstream_404 (user_id);
create index idx_user_clickstream_404_3 on clickstream_404 (host_id);
create index idx_user_clickstream_404_1 on clickstream_404 (request_uri);
create index idx_folder_1 on folder (name);
alter table folder add constraint fkb45d1c6e5fb51eb foreign key (inode) references inode;
create index idx_relationship_1 on relationship (parent_structure_inode);
create index idx_relationship_2 on relationship (child_structure_inode);
alter table relationship add constraint fkf06476385fb51eb foreign key (inode) references inode;
create index idx_field_1 on field (structure_inode);
alter table field add constraint fk5cea0fa5fb51eb foreign key (inode) references inode;
alter table ecom_product_format add constraint fk706fb8ea5fb51eb foreign key (inode) references inode;
alter table report_parameter add constraint fk22da125e5fb51eb foreign key (inode) references inode;
create index idx_chain_key_name on chain (key_name);
create index idx_index_2 on inode (identifier);
create index idx_index_1 on inode (type);
alter table user_filter add constraint fke042126c5fb51eb foreign key (inode) references inode;

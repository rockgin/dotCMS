 /**
 *
 * This is a form dijit that can be used inside a form and it let you select a host or a folder from your dotCMS instance, the selected host/folder id will
 * be send in the form based on the name attribute you set for your dijit.
 *
 * To include the dijit into your page
 *
 * JS Side
 *
 * <script type="text/javascript">
 * 	dojo.require("dotcms.dijit.form.HostFolderFilteringSelect");
 *
 * ...
 *
 * </script>
 *
 * HTML side
 *
 * <div id="myDijitId" jsId="myJSVariable" style="cssStyleOptions" name="myNameWithinTheForm" onlySelectFolders="true" value="folder/host id" dojoType="dotcms.dijit.form.HostFolderFilteringSelect"></div>
 *
 * Properties
 *
 * id: non-required - this is the id of the widget if not specified then it will be autogenerated.
 *
 * jsId: non-required - if specified then dijit will be registered in the JS global enviroment with this name.
 *
 * name: non-required - Need to be specified if you want the selected folder/host id to be submitted with your form.
 *
 * onlySelectFolders: non-required - default false - Let you specify if you want to disable the ability to select hosts and only allow folders to be selected. The property 'hostId' must be set.
 *
 * hostId: non-required - Only show the folders associated to the specified host id. The property 'onlySelectFolders' must be true.
 *
 * value: The id of the folder or host you want the widget to preselect.
 *
 * style: non-required - modifies the html css styles of the combo box.
 *
 * Programattically
 *
 * How to retrieve values from the dijit
 *
 * To retrieve the selected folder/host id
 * dijit.byId('myDijitId').attr('value') // Will return something like '6fa459ea-ee8a-3ca4-894e-db77e160355e'
 *
 * To retrieved the name of the selected host/folder
 * dijit.byId('myDijitId').attr('displayedValue')  // Will return something like 'applications'
 *
 * To retrieved the selected host/folder js object
 * dijit.byId('myDijitId').attr('selectedItem') //Will return a js object like { id: '6fa459ea-ee8a-3ca4-894e-db77e160355e', name: 'applications', type:'folder' }
 *
 * How to set a value
 *
 * dijit.byId('myDijitId').attr('value', 'IdOfTheFolderHost')
 *
 * If the IdOfTheFolderHost doesn't exists the dijit will ignore the request
 *
 * How to reset/clear the values of the dijit
 *
 * dijit.byId('myDijitId').reset()
 *
 */

dojo.provide("dotcms.dijit.form.HostFolderFilteringSelect");

dojo.require("dijit._Widget");
dojo.require("dijit._Templated");
dojo.require("dojo.data.ItemFileReadStore")
dojo.require("dijit.tree.TreeStoreModel")
dojo.require("dijit.Tree")
dojo.require("dotcms.dijit.tree.HostFolderTreeReadStoreModel")

dojo.declare("dotcms.dijit.form.HostFolderFilteringSelect", [dijit._Widget, dijit._Templated], {

	templatePath: dojo.moduleUrl("dotcms", "dijit/form/HostFolderFilteringSelect.html"),

	name: "",
	value: "",
	displayedValue: "",
	selectedItem: "",
	onlySelectFolders: false,
	hostId: '',
	model: null,
	hosts: null,
	updatingSelectedValue: false,
	requiredPermissions:'',
	includeAll:false,
	themesOnly:false,

	constructor: function (params, srcNodeRef) {
		if ((params.onlySelectFolders != null) && (params.onlySelectFolders != '') && (params.onlySelectFolders != undefined) && (params.onlySelectFolders != null)) {
			this.onlySelectFolders = params.onlySelectFolders;
			if ((params.hostId != null) && (params.hostId != '') && (params.hostId != undefined) && (params.hostId != null)) {
				this.hostId = params.hostId;
			}
		}

		if((params.requiredPermissions != null) && (params.requiredPermissions != '') && (params.requiredPermissions != undefined) && (params.requiredPermissions != null)){
			this.requiredPermissions = params.requiredPermissions;
		}

		if ((params.includeAll != null) && (params.includeAll != '') && (params.includeAll != undefined) && (params.includeAll != null)) {
			this.includeAll = params.includeAll;
		}

		if ((params.themesOnly != null) && (params.themesOnly != '') && (params.themesOnly != undefined) && (params.themesOnly != null)) {
			this.themesOnly = params.themesOnly;
		}

		if (this.onlySelectFolders){
			this.model = new dotcms.dijit.tree.HostFolderTreeReadStoreModel( { hostId: this.hostId, requiredPermissions: this.requiredPermissions} );
		}else if(this.themesOnly){
			this.model = new dotcms.dijit.tree.HostFolderTreeReadStoreModel({ hostId: this.hostId, requiredPermissions: this.requiredPermissions, themesOnly: this.themesOnly});
		}else{
			this.model = new dotcms.dijit.tree.HostFolderTreeReadStoreModel({requiredPermissions: this.requiredPermissions, includeAll:this.includeAll});
		}
	},

	postCreate: function (elem) {

		var url = dojo.moduleUrl("dotcms", "dijit/form/HostFolderFilteringSelectTree.html")
		var templateString = dojo._getText(url);
		var html = dojo.string.substitute(templateString, { id: this.id })
        var domObj = dojo._toDom(html);
		dojo.place(domObj, dojo.body(), 'last');
		this.hostFoldersTreeWrapper = dojo.byId(this.id + '-hostFoldersTreeWrapper');
		this.hostFoldersTree = dojo.byId(this.id + '-hostFoldersTree');


		var boxDim = dojo.contentBox(this.hostFolderComboBox);
		var cWidth = (boxDim.w -37) < 200?200:(boxDim.w -37);
		dojo.style(this.hostFoldersTreeWrapper, { width: cWidth + "px" });

	   	this.tree = new dijit.Tree({

			id: this.id + "-tree",

	        model: this.model,

	        persist: false,

	        showRoot: false,

			getIconClass: (function (item, opened) {
				if(item.type == 'host') {
					if(opened) return "dijitHostOpened";
					else return "dijitHostClosed";
				} else {
					if(opened) return "dijitFolderOpened";
					else return "dijitFolderClosed";
				}
			}),

			_createTreeNode: function (args) {
				args.id = this.id + "-treeNode-" + this.model.getIdentity(args.item);
				try{
					return new dijit._TreeNode(args);
				}
				catch(err){
					var x = dijit.byId(args.id);
					if(x){
						x.destroy();
					}
					return new dijit._TreeNode(args);
				}
			}

	    }, this.hostFoldersTree);
		dojo.connect(this.tree, 'onClick', this, this._selectItem);

		this.bodyClickHandle = dojo.connect(dojo.body(), 'onclick', this, this._onBodyClick);

		dojo.connect(this.hostFolderSelectedName, 'onchange', this, function () { this._setValueAttr(this.hostFolderSelectedName.value); } );
	},

	uninitialize : function (event) {
		dojo.disconnect(this.bodyClickHandle);
	},

	showHideHostFolderList: function (event) {
		this.filter = '';
		var display = dojo.style(this.hostFoldersTreeWrapper, 'display');
		if(display == '' || display == 'block')
			this._hideHostFolderList(event);
		else {
			this._showHostFolderList(event);
		}
	},

	_showHostFolderList: function (event) {
		this._repositionTreeWrapper();
		dojo.style(this.hostFoldersTreeWrapper, { display: '' });
	},

	_hideHostFolderList: function (event) {
		dojo.style(this.hostFoldersTreeWrapper, { display: 'none' });
	},

	/**
	 * Stub method that you can use dojo.connect to catch every time a user selects a folder from the tree
	 * @param {Object} item
	 */
	itemSelected: function (item) {

	},

	_repositionTreeWrapper: function() {
		var selectCoords = dojo.coords(this.hostFileFilteringSelectWrapper, true)
		dojo.style(this.hostFoldersTreeWrapper, { left: selectCoords.x + "px", top: (selectCoords.y + selectCoords.h) + "px"})
	},

	onChange: function(newValue) { },

	_selectItem: function(item, event) {

		if(this.onlySelectFolders && item.type != 'folder')
			return false;

		var name = this.model.getLabel(item);
		var id = this.model.getIdentity(item);

		this.value = id;
		this.displayedValue = name;
		this.selectedItem = item;
		this.hostFolderSelectedName.value = name;
		this.hostFolderSelectedId.value = id;

		if (this.hostFoldersTreeWrapper != null)
			dojo.style(this.hostFoldersTreeWrapper, { display: 'none' });

		this.itemSelected(item);
		this.onChange(item);

		return true;

	},

	_onBodyClick: function (event) {
		if(!this._within(event, this.hostFoldersTreeWrapper) && !this._within(event, this.hostFileFilteringSelectWrapper))
			dojo.style(this.hostFoldersTreeWrapper, {
				display: 'none'
			});
	},

	_within: function (event, elem) {
		var x = event.clientX;
		var y = event.clientY;
		var coords = dojo.coords(elem);

		if(x < coords.x || x > coords.x + coords.w || y < coords.y || y > coords.y + coords.h) {
			return false;
		}
		return true;
	},

	_setValueAttr: function(/*String*/ value) {
		this.updatingSelectedValue = true;
		if (value == "") {
			this.reset();
		} else {
			var keywordArgs = { identity: value, onItem:dojo.hitch(this,this._setValueAttrCallback) };
			this.model.fetchItemByIdentity(keywordArgs);
		}
	},

	_resetValue: function() {
		this.value = "";
		this.displayedValue = "";
		this.selectedItem = null;
		this.hostFolderSelectedName.value = "";
		this.hostFolderSelectedId.value = "";
	},

	_setValueAttrCallback: function(/*String*/ selectedHostFolder) {
		if(selectedHostFolder)
			this._selectItem(selectedHostFolder);
		else
			this.value = "";

		this.updatingSelectedValue = false;
	},

	reset: function () {
		this.value = "";
		this.displayedValue = "";
		this.selectedItem = null;
		this.hostFolderSelectedName.value = "";
		this.hostFolderSelectedId.value = "";
		if (this.hostFoldersTreeWrapper != null)
			dojo.style(this.hostFoldersTreeWrapper, { display: 'none' });

		this.updatingSelectedValue = false;
	},

	_onHostFolderTextFieldFocus: function () {
		this.hostFolderSelectedName.value = "";
	},

	_onHostFolderTextFieldBlur: function () {
		var wrapper = dojo.hitch(this, this._onHostFolderTextFieldBlurDelayed);
		setTimeout(wrapper, 500);
	},

	_onHostFolderTextFieldBlurDelayed: function () {

		if(!this.selectedItem) {
			this.reset();
		} else {
			this._selectItem(this.selectedItem, event);
		}

		for(idx in this.hosts) {
			var host = this.hosts[idx];
			var node = dijit.byId(this.id + '-tree-treeNode-' + host.id);
			if( typeof host.hostName != "undefined"){
				dojo.style(node.domNode, { display: '' });
			}
		}

	},

	_onHostFolderTextFieldKeyDown: function () {
		var fnWrapper = dojo.hitch(this, this._onHostFolderTextFieldKeyDownDelayed);
		if(this._onHostFolderTextFieldKeyDownDelayedHandler)
			clearTimeout(this._onHostFolderTextFieldKeyDownDelayedHandler);
		this._onHostFolderTextFieldKeyDownDelayedHandler = setTimeout(fnWrapper, 500);
	},

	_onHostFolderTextFieldKeyDownDelayed: function () {
		this._showHostFolderList();
		if(!this.hosts) {
			this._loadHostsLevel(dojo.hitch(this, this._onHostFolderTextFieldKeyDown));
		} else {
			for(idx in this.hosts) {
				var filter = this.hostFolderSelectedName.value.toLowerCase();
				var host = this.hosts[idx];
				var node = dijit.byId(this.id + '-tree-treeNode-' + host.id);
				if( typeof host.hostName != "undefined"){
					if(host.hostName.toLowerCase().indexOf(filter) == 0) {
						dojo.style(node.domNode, { display: '' });
					} else {
						dojo.style(node.domNode, { display: 'none' });
					}
				}
			}
		}
	},

	_loadHostsLevel: function (callback) {
		this.model.getRoot(dojo.hitch(this, function (root) {
			this.model.getChildren(root, dojo.hitch(this, function (hosts) {
				this.hosts = hosts;
				if(callback) {
					callback.call(this);
				}
			}));
		}));
	}

})
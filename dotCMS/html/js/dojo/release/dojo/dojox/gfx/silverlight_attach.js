if(!dojo._hasResource["dojox.gfx.silverlight_attach"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojox.gfx.silverlight_attach"] = true;
dojo.provide("dojox.gfx.silverlight_attach");

dojo.require("dojox.gfx.silverlight");

dojo.experimental("dojox.gfx.silverlight_attach");

(function(){
	var g = dojox.gfx, sl = g.silverlight;
	
	sl.attachNode = function(node){
		// summary: creates a shape from a Node
		// node: Node: an Silverlight node
		return null;	// not implemented
	};

	sl.attachSurface = function(node){
		// summary: creates a surface from a Node
		// node: Node: an Silverlight node
		return null;	// dojox.gfx.Surface
	};
})();

}

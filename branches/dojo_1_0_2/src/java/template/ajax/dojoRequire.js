   dojo.require("dojo.event.*");       // sophisticated AOP event handling
   dojo.require("dojo.io.*");          // for Ajax requests
   dojo.require("dojo.storage.*");     // a persistent local data cache
   dojo.require("dojo.json");          // serialization to JSON
   dojo.require("dojo.dnd.*");         // drag-and-drop
   dojo.require("dojo.lfx.*");         // animations and eye candy
   dojo.require("dojo.widget.*");
   dojo.require("dojo.widget.Slider");
   dojo.require("dojo.widget.HtmlWidget");
   dojo.require("dojo.widget.Tree");
   dojo.require("dojo.widget.TreeNode");
   dojo.require("dojo.widget.TreeSelector");
   dojo.require("dojo.event.topic.*");
   dojo.require("dojo.widget.Editor2");// stable, portable HTML WYSIWYG
   
   dojo.registerModulePath("webwork", "../webwork");
   
   dojo.require("webwork.Util");
   dojo.require("webwork.widget.Bind");
   dojo.require("webwork.widget.BindDiv");
   dojo.require("webwork.widget.BindAnchor");
   dojo.require("webwork.widget.BindButton");

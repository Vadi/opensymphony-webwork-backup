
Demo WAR at http://pat.net/webworkxslt
Questions to Pat Niemeyer (pat@pat.net)

--------------------------------------

Changes in XSLT 2 view
----------------------

This is a partial rewrite of the XSLT view based on the 2.1.7 codebase. 
I have placed it here to allow for migration from the existing view, and
incorporation of changes made since the code fork.

The new xslt view supports an extensible Java XML adapter framework that makes
it easy to customize the XML rendering of objects and to incorporate structured
XML text and arbitarary DOM fragments into the output.

The XSLTResult class now uses an extensible adapter factory for rendering the
WebWork action Java object tree to an XML DOM for consumption by the
stylesheet.  Users can easily register their own adapters to produce custom XML
views of Java types or simply extend a default "String" adapter and return
plain or XML text to be incorporated into the DOM.  The new adapter mechanism
is capable of proxying existing DOM trees and incorporating them into the
results, so you can freely mix DOMs produced from other sources into your
result tree. 

A default java.util.Map adapter is also now provided to render Maps to XML.

Please see the javadoc on the AdapterFactory as well as ExampleAction.java, 
CustomXSLTResult.java, and CustomXSLTResult2.java in the example package for
more details.



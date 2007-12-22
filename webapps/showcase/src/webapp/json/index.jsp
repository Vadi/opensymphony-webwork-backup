<%--
  Created by IntelliJ IDEA.
  User: tmjee
  Date: Dec 16, 2007
  Time: 11:43:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="ww" uri="/webwork" %>
<html>
  <head>
      <title>Simple jsp page</title>
      <ww:head theme="ajax" />
      <script type="text/javascript">
          dojo.require("dojo.json");
          dojo.addOnLoad(function() {
              dojo.event.connect(dojo.byId("country"), "onchange", function() {
                    var selectTag = dojo.byId('country');
                    var citiesSelectTag = dojo.byId("cities");
                    //alert(selectTag.options[selectTag.selectedIndex].value);
                    dojo.io.bind({
                        method: "POST",
                        url: "<ww:url action='countryCities' namespace='/json' />",
                        content: {
                            country: selectTag.options[selectTag.selectedIndex].value    
                        },
                        load: function(type, data, event) {
                            try {
                                var jsonObj = dojo.json.evalJson(data);

                                // clear the cities select tag options
                                citiesSelectTag.options.length = 0;

                                if (jsonObj.cities) {
                                    var cities = jsonObj.cities;
                                    for (var a=0;a <cities.length; a++) {
                                        var option = document.createElement("option");
                                        option.text = cities[a];
                                        option.value = cities[a];
                                        citiesSelectTag.add(option, null);
                                    }
                                }
                            }
                            catch(e) {   // just in case there's an invalid JSON script
                                alert('error evaluating JSON '+e);
                            }
                        },
                        error: function(type, error){
                            alert( "Error : " + error.message );
                        },
                        mimetype: "text/plain"
                    });
              });
          });
      </script>
  </head>
  <body>
    <select id="country" name="country">
        <option selected="selected" value="none">Please Select</option>
        <option value="uk">United Kingdom</option>
        <option value="us">United States</option>
        <option value="aus">Australia</option>
        <option value="ger">Germany</option>
    </select>
    <br/>
    <select id="cities" name="cities">
    </select>
  </body>
</html>

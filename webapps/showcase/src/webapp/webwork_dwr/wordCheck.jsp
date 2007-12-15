<%--
  Created by IntelliJ IDEA.
  User: tmjee
  Date: Dec 15, 2007
  Time: 9:37:56 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="ww" uri="/webwork" %>
<html>
  <head>
      <title>Word Check</title>
      <ww:head theme="ajax" />

      <script src='<ww:url value="/dwr/interface/dwraction.js" />'></script>
      <script src='<ww:url value="/dwr/engine.js" />'></script>
      <script src='<ww:url value="/dwr/util.js" />'></script>

      <script type="text/javascript">
          dojo.addOnLoad(function() {
              dojo.event.connect(dojo.byId("word"), "onkeyup", function() {
                var text = dojo.byId('word').value;
                dwraction.execute(
                    {
                        action: 'checkWord',
                        namespace: '/webwork_dwr',
                        executeResult: false
                    },
                    {
                        wordToCheckFor: text
                    },
                    function(ajaxResult) {
                        var possibleWords = ajaxResult.data.possibleWords;
                        var msg;
                        if (possibleWords.length > 0) {
                            msg = "Are you looking for ";
                            for (var a=0; a<possibleWords.length; a++) {
                                msg = msg +"<br/>"+possibleWords[a];    
                            }
                        }
                        else {
                            msg = "Sorry, cannot locate your words";
                        }
                        dojo.byId("message").innerHTML = msg;
                    }
                );
              });
              dojo.event.connect(dojo.byId("wordListButton"), "onclick", function() {
                dwraction.execute(
                    {
                        action: 'wordList',
                        namespace: '/webwork_dwr',
                        executeResult: true
                    },
                    {},
                    function(ajaxResult) {
                        dojo.byId("wordList").innerHTML = ajaxResult.text;
                    }
                );
              });

              dojo.event.connect(dojo.byId("clearListButton"), "onclick", function() {
                  dojo.byId("wordList").innerHTML = "";
              });
          });
      </script>
  </head>
  <body>

     <h1>Word Check</h1>
     <input id="word" type="text" />
     <input id="wordListButton" type="button" value="List All Word" />
     <input id="clearListButton" type="button" value="Hide Word List" />
     <div>
         <div id="message" style="float:left">

         </div>
         <div id="wordList" style="float:left">

         </div>
     </div>
  </body>
</html>
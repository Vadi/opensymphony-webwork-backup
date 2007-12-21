<%--
  @author tmjee
  @version $Date$ $Id$
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="ww" uri="/webwork" %>
<html>
  <head>
      <title>IfTagJspTest</title>
      <meta content="plain" name="decorator" />
  </head>
  <body>
    <table>
        <tr>
            <td colspan="3">IfTagJspTest</td>
        </tr>
        <tr>
            <td>open</td>
            <td><ww:url namespace="/tags/non-ui/ifTag" action="testIfTagJsp" includeContext="true" includeParams="none"/></td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>1 - Foo -  Foo</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>2 - Bar -  Bar</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>3 - FooFooFoo -  Foo FooFoo</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>4 - FooBarBar -  Foo BarBar</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>5 - BarFooFoo -  Bar FooFoo</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>6 - BarBarBar -  Bar BarBar</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>7 - Foo -  Foo</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>8 - Moo -  Moo</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>9 - Bar -  Bar</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>10 - FooFooFoo -  Foo FooFoo</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>11 - FooMooMoo -  Foo MooMoo</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>12 - FooBarBar -  Foo BarBar</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>13 - MooFooFoo -  Moo FooFoo</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>14 - MooMooMoo -  Moo MooMoo</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>15 - MooBarBar -  Moo BarBar</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>16 - BarFooFoo -  Bar FooFoo</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>17 - BarMooMoo -  Bar MooMoo</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>18 - BarBarBar -  Bar BarBar</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>19 - Foo -  Foo</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>20 - ** should not display anything ** -</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>21 FooFooFoo -  Foo FooFoo</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>22 - Foo -  Foo</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>23 - BarFooFoo -  Bar FooFoo</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>24 - Bar -  Bar</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>25 - FooFooFoo  Foo FooFoo</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>26 - FooMooMoo  Foo MooMoo</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>27 - Foo -  Foo</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>28 - MooFooFoo  Moo FooFoo</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>29 - MooMooMoo  Moo MooMoo</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>30 - Moo -  Moo</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>31 - BarFooFoo -  Bar FooFoo</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>32 - BarMooMoo -  Bar MooMoo</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>33 - Bar -  Bar</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>34 - FooFooFoo -  Foo FooFoo</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>35 - Foo -  Foo</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>36 - MooFooFoo -  Moo FooFoo</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>37 - Moo -  Moo</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>38 - BarFooFoo -  Bar FooFoo</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>39 - Bar -  Bar</td>
            <td></td>
        </tr>
    </table>
  </body>
</html>
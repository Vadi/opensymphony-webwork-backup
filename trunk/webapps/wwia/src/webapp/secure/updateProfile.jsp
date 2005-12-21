<%@ page import="org.hibernate.auction.model.User,
                 org.hibernate.auction.model.Address,
                 java.util.Map,
                 java.util.Collections"%>
<%@ taglib prefix="ww" uri="webwork"%>
<html>
  <head>
    <title><ww:text name="title"/></title>
    <script language="JavaScript" src="/shared/javascript/calendar2.js"></script>
  </head>

  <body>
<ww:form action="updateProfile" method="post">
<ww:textfield label="%{getText('firstname')}"
              name="firstname"/>
<ww:textfield label="%{getText('lastname')}" name="lastname"/>
<ww:textfield label="%{getText('email')}" name="email"/>
<ww:component template="calendar" label="Birthdate" name="birthdate"/>
<ww:radio label="%{getText('gender')}" name="gender"
         list="#{0 : getText('gender.male'),
                 1 : getText('gender.female')}"/>
<ww:textfield label="%{getText('address.street')}"
              name="address.street"/>
<ww:textfield label="%{getText('address.zipcode')}"
              name="address.zipcode"/>
<ww:textfield label="%{getText('address.city')}"
              name="address.city"/>
<ww:select label="%{getText('address.state')}"
           name="address.state"
           list="{'Californa', 'Georgia', 'New York', 'Oregon'}"/>
<ww:select label="%{getText('address.country')}"
           name="address.country"
           list="{'USA', 'Canada', 'Mexico', 'Other'}"/>
<ww:checkbox label="%{getText('address.poBox')}"
             name="address.poBox"
             fieldValue="true"/>
<ww:submit value="%{getText('updateProfile')}"/>
</ww:form>

<%--
<ww:form action="updateProfile" method="post">
<ww:textfield label="First name" name="user.firstname"/>
<ww:textfield label="Last name" name="user.lastname"/>
<ww:textfield label="Email" name="user.email"/>
<ww:radio label="Gender" name="user.gender"
          list="#{0 : 'Male', 1 : 'Female'"/>
<ww:textfield label="Street" name="user.address.street"/>
<ww:textfield label="Zip Code" name="user.address.zipcode"/>
<ww:textfield label="City" name="user.address.city"/>
<ww:select label="State" name="user.address.state"
          list="{'Californa', 'Oregon'}"/>
<ww:select label="Country" name="user.address.country"
          list="{'USA', 'Canada', 'Mexico', 'Other'}"/>
<ww:checkbox label="P.O. Box" name="user.address.poBox"
             fieldValue="true"/>
<ww:submit value="Update Profile"/>
</ww:form>
--%>

<%--
<ww:set name="user" value="user" scope="request"/>
<ww:set name="fieldErrors" value="fieldErrors" scope="request"/>
<%
  User user = (User) request.getAttribute("user");
  Map fieldErrors = (Map) request.getAttribute("fieldErrors");
  if (fieldErrors == null) {
    fieldErrors = Collections.EMPTY_MAP;
  }
%>
<form action="updateProfile.action" method="post">
<table>
<%
  if (fieldErrors.containsKey("user.firstname"))  {
%>
<tr>
  <td align="center" valign="top" colspan="2">
  <span class="errorMessage">
    <%= fieldErrors.get("user.firstname")%>
  </span>
  </td>
</tr>
<%
  }
%>
<tr>
  <td align="right"><label>First name:</label></td>
  <td><input type="text" name="user.firstname"
             value="<%= user.getFirstname() %>"/></td>
</tr>
<tr>
  <td align="right"><label>Last name:</label></td>
  <td><input type="text" name="user.lastname"
             value="<%= user.getLastname() %>"/></td>
</tr>
<tr>
  <td align="right"><label>Email:</label></td>
  <td><input type="text" name="user.email"
             value="<%= user.getEmail() %>"/></td>
</tr>
<tr>
  <td align="right"><label>Gender:</label></td>
  <td>
  <input type="radio" name="user.gender" value="0" id="user.gender0"
         <% if (user.getGender() == 0) { %>
         checked="checked"
         <% } %> />
  <label for="user.gender0">Male</label>

  <input type="radio" name="user.gender" value="1" id="user.gender1"
         <% if (user.getGender() == 1) { %>
         checked="checked"
         <% } %> />
  <label for="user.gender1">Female</label>
  </td>
</tr>
<%
  Address address = user.getAddress();
  boolean nullAddress = address == null;
%>
<tr>
  <td align="right"><label>Street Address:</label></td>
  <td><input type="text" name="user.address.street"
             value="<%= !nullAddress ?
                        address.getStreet() : ""%>"/></td>
</tr>
<tr>
  <td align="right"><label>Zip Code:</label></td>
  <td><input type="text" name="user.address.zipcode"
             value="<%= !nullAddress ?
                        address.getZipcode() : ""%>"/></td>
</tr>
<tr>
  <td align="right"><label>City:</label></td>
  <td><input type="text" name="user.address.city"
             value="<%= !nullAddress ?
                        address.getCity() : ""%>"/></td>
</tr>
<tr>
  <td align="right"><label>State:</label></td>
  <td><select name="user.address.state">
        <option value="Californa"
          <% if (!nullAddress &&
                 "California".equals(address.getState())) { %>
          selected="selected"
          <% } %>>Californa</option>
        <option value="Oregon"
          <% if (!nullAddress &&
                 "Oregon".equals(address.getState())) { %>
          selected="selected"
          <% } %>>Oregon</option>
      </select></td>
</tr>
<tr>
  <td align="right"><label>Country:</label></td>
  <td><select name="user.address.country">
        <option value="USA"
          <% if (!nullAddress &&
                 "USA".equals(address.getCountry())) { %>
          selected="selected"
          <% } %>>USA</option>
        <option value="Canada"
          <% if (!nullAddress &&
                 "Canada".equals(address.getCountry())) { %>
          selected="selected"
          <% } %>>Canada</option>
        <option value="Mexico"
          <% if (!nullAddress &&
                 "Mexico".equals(address.getCountry())) { %>
          selected="selected"
          <% } %>>Mexico</option>
        <option value="Other"
          <% if (!nullAddress &&
                 "Other".equals(address.getCountry())) { %>
          selected="selected"
          <% } %>>Other</option>
      </select></td>
</tr>
<tr>
  <td colspan="2">
    <table>
    <tr>
      <td valign="middle">
        <input type="checkbox" name="user.address.poBox"
               value="true"
               <% if (!nullAddress && address.isPoBox()) { %>
               checked="checked"
               <% } %>/>
      </td>
      <td valign="middle" width="100%">
        <label class="checkboxLabel">P.O. Box</label>
      </td>
    </tr>
    </table>
  </td>
</tr>
<tr>
  <td colspan="2"><div align="'right'">
    <input value="Update Profile" type="submit"/>
  </div></td>
</tr>
</table>
</form>
--%>


  </body>
</html>
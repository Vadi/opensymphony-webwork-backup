package com.opensymphony.webwork.showcase.json;

import com.opensymphony.xwork.ActionSupport;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author tmjee
 * @version $Date$ $Id$
 */
public class CountryCitiesAction extends ActionSupport {

    private String country;
    private JSONObject jsonObject;

    private Map cities = new HashMap() {
        {
            put("uk", new ArrayList() {
                {
                    add("Manchester");
                    add("London");
                    add("Liverpool");
                    add("Notthingham");
                }
            });
            put("us", new ArrayList() {
                {
                    add("California");
                    add("New York");
                    add("Texas");
                    add("Utah");
                }
            });
            put("aus", new ArrayList() {
                {
                    add("Sydney");
                    add("Melbourne");
                    add("Adelaide");
                    add("Victoria");
                }
            });
            put("ger", new ArrayList() {
                {
                    add("Bamberg");
                    add("Berlin");
                    add("Cologne");
                    add("Dresden");
                }
            });
        }
    };


    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }


    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public String execute() throws Exception {

        jsonObject = new JSONObject();
        System.out.println("******* country="+country);
        if (cities.containsKey(country)) {
            jsonObject.put("cities", cities.get(country));
        }
        else {
            jsonObject.put("cities", Collections.EMPTY_LIST);
        }

        return SUCCESS;
    }
}

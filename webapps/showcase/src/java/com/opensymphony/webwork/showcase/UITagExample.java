package com.opensymphony.webwork.showcase;

import com.opensymphony.xwork.ActionSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.File;

/**
 * @author Patrick Lightbody (plightbo at gmail dot com)
 */
public class UITagExample extends ActionSupport {
    String name;
    Date birthday;
    String bio;
    String favoriteColor;
    List friends;
    boolean legalAge;
    String state;
    String region;
    File picture;
    String pictureContentType;
    String pictureFileName;
    String favouriteLanguage;
    
    List favouriteLanguages = new ArrayList();
    
    public UITagExample() {
    	favouriteLanguages.add(new Language("EnglishKey", "English Language"));
    	favouriteLanguages.add(new Language("FrenchKey", "French Language"));
    	favouriteLanguages.add(new Language("SpanishKey", "Spanish Language"));
    }
    
    public List getFavouriteLanguages() {
    	return favouriteLanguages;
    }

    public String execute() throws Exception {
        return SUCCESS;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getFavoriteColor() {
        return favoriteColor;
    }

    public void setFavoriteColor(String favoriteColor) {
        this.favoriteColor = favoriteColor;
    }

    public List getFriends() {
        return friends;
    }

    public void setFriends(List friends) {
        this.friends = friends;
    }

    public boolean isLegalAge() {
        return legalAge;
    }

    public void setLegalAge(boolean legalAge) {
        this.legalAge = legalAge;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setPicture(File picture) {
        this.picture = picture;
    }

    public void setPictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
    }

    public void setPictureFileName(String pictureFileName) {
        this.pictureFileName = pictureFileName;
    }
    
    public void setFavouriteLanguage(String favouriteLanguage) {
    	this.favouriteLanguage = favouriteLanguage;
    }
    
    public String getFavouriteLanguage() {
    	return favouriteLanguage;
    }
    
    public String doSubmit() {
    	return SUCCESS;
    }
    
    
    
    
    
    // === inner class 
    public static class Language {
    	String description;
    	String key;
    	
    	public Language(String key, String description) {
    		this.key = key;
    		this.description = description;
    	}
    	
    	public String getKey() { return key; }
    	public String getDescription() { return description; }
    	
    }
}

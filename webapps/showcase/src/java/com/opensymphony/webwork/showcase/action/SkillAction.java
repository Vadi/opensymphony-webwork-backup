/*
 * Copyright (c) 2002-2007 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.showcase.action;

import com.opensymphony.webwork.showcase.dao.Dao;
import com.opensymphony.webwork.showcase.dao.SkillDao;
import com.opensymphony.webwork.showcase.model.Skill;
import com.opensymphony.xwork.Preparable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * SkillAction.
 *
 * @author <a href="mailto:gielen@it-neering.net">Rene Gielen</a>
 * @author tmjee
 */

public class SkillAction extends AbstractCRUDAction implements Preparable {

    private static final Log log = LogFactory.getLog(SkillAction.class);

    private String skillName;
    protected SkillDao skillDao;
    private Skill currentSkill;

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    protected Dao getDao() {
        return skillDao;
    }

    public void setSkillDao(SkillDao skillDao) {
        if (log.isDebugEnabled()) {
            log.debug("SkillAction - [setSkillDao]: skillDao injected.");
        }
        this.skillDao = skillDao;
    }

    public Skill getCurrentSkill() {
        return currentSkill;
    }

    public void setCurrentSkill(Skill currentSkill) {
        this.currentSkill = currentSkill;
    }

    /**
     * This method is called to allow the action to prepare itself.
     *
     * @throws Exception thrown if a system level exception occurs.
     */
    public void prepare() throws Exception {
        Skill preFetched = (Skill) fetch(getSkillName(), getCurrentSkill());
        if (preFetched != null) {
            setCurrentSkill(preFetched);
        }
    }

    public String save() throws Exception {
        if (getCurrentSkill() != null) {
            setSkillName((String) skillDao.merge(getCurrentSkill()));
        }
        return SUCCESS;
    }

}

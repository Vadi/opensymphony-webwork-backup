/*
 * Copyright (c) 2002-2007 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.showcase.dao;

import com.opensymphony.webwork.showcase.model.Skill;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * SkillDao.
 *
 * @author <a href="mailto:gielen@it-neering.net">Rene Gielen</a>
 * @author tmjee
 */

public class SkillDao extends AbstractDao {

    private static final Log log = LogFactory.getLog(SkillDao.class);

    public Class getFeaturedClass() {
        return Skill.class;
    }

    public Skill getSkill( String name ) {
        return (Skill) get(name);
    }
}

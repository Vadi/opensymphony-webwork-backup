/*
 * Copyright (c) 2002-2007 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.showcase.dao;

import com.opensymphony.webwork.showcase.model.Employee;
import com.opensymphony.webwork.showcase.model.IdEntity;
import com.opensymphony.webwork.showcase.model.Skill;

import java.util.List;
import java.util.ArrayList;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

/**
 * EmployeeDao.
 *
 * @author <a href="mailto:gielen@it-neering.net">Rene Gielen</a>
 * @author tmjee
 */

public class EmployeeDao extends AbstractDao {

    private static final Log log = LogFactory.getLog(EmployeeDao.class);

    protected SkillDao skillDao;

    public void setSkillDao(SkillDao skillDao) {
        this.skillDao = skillDao;
    }

    public Class getFeaturedClass() {
        return Employee.class;
    }

    public Employee getEmployee( Long id ) {
        return (Employee) get(id);
    }

    public Employee setSkills(Employee employee, List skillNames) {
        if (employee!= null && skillNames != null) {
            employee.setOtherSkills(new ArrayList());
            for (int i = 0, j = skillNames.size(); i < j; i++) {
                Skill skill = (Skill) skillDao.get((String) skillNames.get(i));
                employee.getOtherSkills().add(skill);
            }
        }
        return employee;
    }

    public Employee setSkills(Long empId, List skillNames) {
        return setSkills((Employee) get(empId), skillNames);
    }

}

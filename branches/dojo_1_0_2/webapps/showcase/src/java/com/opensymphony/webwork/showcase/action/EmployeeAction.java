/*
 * Copyright (c) 2002-2007 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.showcase.action;

import com.opensymphony.webwork.showcase.dao.Dao;
import com.opensymphony.webwork.showcase.dao.EmployeeDao;
import com.opensymphony.webwork.showcase.model.Employee;
import com.opensymphony.webwork.showcase.model.Skill;
import com.opensymphony.webwork.showcase.application.TestDataProvider;
import com.opensymphony.xwork.Preparable;

import java.util.List;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ArrayList;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

/**
 * EmployeeAction.
 *
 * @author <a href="mailto:gielen@it-neering.net">Rene Gielen</a>
 * @author tmjee
 */

public class EmployeeAction extends AbstractCRUDAction implements Preparable {

    private static final Log log = LogFactory.getLog(EmployeeAction.class);

    private Long empId;
    protected EmployeeDao employeeDao;
    private Employee currentEmployee;
    private List selectedSkills;

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public Employee getCurrentEmployee() {
        return currentEmployee;
    }

    public void setCurrentEmployee(Employee currentEmployee) {
        this.currentEmployee = currentEmployee;
    }

    public String[] getAvailablePositions() {
        return TestDataProvider.POSITIONS;
    }

    public List getAvailableLevels() {
        return Arrays.asList(TestDataProvider.LEVELS);
    }

    public List getSelectedSkills() {
        return selectedSkills;
    }

    public void setSelectedSkills(List selectedSkills) {
        this.selectedSkills = selectedSkills;
    }

    protected Dao getDao() {
        return employeeDao;
    }

    public void setEmployeeDao(EmployeeDao employeeDao) {
        if (log.isDebugEnabled()) {
            log.debug("EmployeeAction - [setEmployeeDao]: employeeDao injected.");
        }
        this.employeeDao = employeeDao;
    }

    /**
     * This method is called to allow the action to prepare itself.
     *
     * @throws Exception thrown if a system level exception occurs.
     */
    public void prepare() throws Exception {
        Employee preFetched = (Employee) fetch(getEmpId(), getCurrentEmployee());
        if (preFetched != null) {
            setCurrentEmployee(preFetched);
        }
    }

    public String execute() throws Exception {
        if (getCurrentEmployee() != null && getCurrentEmployee().getOtherSkills()!=null) {
            setSelectedSkills(new ArrayList());
            Iterator it = getCurrentEmployee().getOtherSkills().iterator();
            while (it.hasNext()) {
                getSelectedSkills().add(((Skill) it.next()).getName());
            }
        }
        return super.execute();
    }

    public String save() throws Exception {
        if (getCurrentEmployee() != null) {
            setEmpId((Long) employeeDao.merge(getCurrentEmployee()));
            employeeDao.setSkills(getEmpId(), getSelectedSkills());
        }
        return SUCCESS;
    }

}

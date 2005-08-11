package com.opensymphony.webwork.sandbox;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * User: plightbo
 * Date: Aug 10, 2005
 * Time: 7:56:08 PM
 */
public class TableTest {
    PersonTableModel people;

    public String execute() {
        people = new PersonTableModel();
        people.addPerson(new Person());

        return "success";
    }

    public PersonTableModel getPeople() {
        return people;
    }

    static class PersonTableModel implements TableModel {
        List people = new ArrayList();

        public int getRowCount() {
            return people.size();
        }

        public int getColumnCount() {
            return 2;
        }

        public String getColumnName(int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return "ID";
                case 1:
                    return "Name";
                default:
                    return "Unknown";
            }
        }

        public Class getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return Long.class;
                case 1:
                    return String.class;
                default:
                    return null;
            }
        }

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            Person person = (Person) people.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return new Long(person.getId());
                case 1:
                    return person.getName();
                default:
                    return null;
            }
        }

        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        }

        public void addTableModelListener(TableModelListener l) {
        }

        public void removeTableModelListener(TableModelListener l) {
        }

        public void addPerson(Person person) {
            people.add(person);
        }
    }
}

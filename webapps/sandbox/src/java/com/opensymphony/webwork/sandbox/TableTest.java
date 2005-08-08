package com.opensymphony.webwork.sandbox;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * User: plightbo
 * Date: Aug 6, 2005
 * Time: 8:15:36 PM
 */
public class TableTest {
    PersonTableModel table;

    public PersonTableModel getTable() {
        return table;
    }

    public String execute() {
        System.out.println("1");

        table = new PersonTableModel();
        table.addPerson(new Person(1, "Patrick"));
        table.addPerson(new Person(2, "Jason"));
        table.addPerson(new Person(3, "Matthew"));

        return "success";
    }

    class PersonTableModel implements TableModel {
        List people = new ArrayList();

        public void addPerson(Person person) {
            people.add(person);
        }

        public int getColumnCount() {
            return 2;
        }

        public int getRowCount() {
            return people.size();
        }

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        public Class getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return String.class;
                case 1:
                    return Long.class;
                default:
                    return null;
            }
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

        public String getColumnName(int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return "id";
                case 1:
                    return "name";
                default:
                    return "n/a";
            }
        }

        public void addTableModelListener(TableModelListener l) {
        }

        public void removeTableModelListener(TableModelListener l) {
        }
    }
}

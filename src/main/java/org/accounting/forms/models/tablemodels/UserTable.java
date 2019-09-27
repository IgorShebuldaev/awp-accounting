package org.accounting.forms.models.tablemodels;

import org.accounting.database.models.User;

public class UserTable extends MainTableModel {

    public UserTable() {
        columnNames = new String[]{"Email", "Password", "Role", "Time in program"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object result = null;
        User user = (User) data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                result = user.email;
                break;
            case 1:
                result = user.password;
                break;
            case 2:
                result = user.getRole().role;
                break;
            case 3:
                result = user.timeInProgram;
                break;
        }
        return result;
    }

    @Override
    public User getRecord(int rowIndex) {
        return (User) super.getRecord(rowIndex);
    }
}

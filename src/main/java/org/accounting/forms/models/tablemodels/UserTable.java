package org.accounting.forms.models.tablemodels;

import org.accounting.database.models.User;

public class UserTable extends MainTableModel {

    public UserTable() {
        columnNames = new String[]{"Email", "Password", "Role", "Time in program"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        User user = (User) data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return user.email;
            case 1:
                return user.password;
            case 2:
                return user.getRole().role;
            case 3:
                return user.getTimeInProgram();
        }
        return "";
    }

    @Override
    public User getRecord(int rowIndex) {
        return (User) super.getRecord(rowIndex);
    }
}

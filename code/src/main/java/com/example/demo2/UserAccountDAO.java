package com.example.demo2;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.demo2.DBUtil;

public class UserAccountDAO {
    public UserAccountDAO() {
    }

    public static UserAccount searchUserAccount(String userId) throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM public.useraccounts WHERE id_useraccounts=" + userId;

        try {
            ResultSet rsUser = DBUtil.dbExecuteQuery(selectStmt);
            UserAccount userAccount = getUserAccountFromResultSet(rsUser);
            return userAccount;
        } catch (SQLException var4) {
            System.out.println("While searching a user account with " + userId + " id, an error occurred: " + var4);
            throw var4;
        }
    }

    private static UserAccount getUserAccountFromResultSet(ResultSet rs) throws SQLException {
        UserAccount userAccount = null;
        if (rs.next()) {
            userAccount = new UserAccount();
            userAccount.setIdUserAccounts(rs.getInt("id_useraccounts"));
            userAccount.setUsername(rs.getString("username"));
            userAccount.setFirstname(rs.getString("firstname"));
            userAccount.setLastname(rs.getString("lastname"));
            userAccount.setMdpPharm(rs.getString("mdp_pharm"));
            userAccount.setStatus(rs.getString("status"));

        }

        return userAccount;
    }

    public static ObservableList<UserAccount> searchUserAccounts() throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM public.useraccounts";

        try {
            ResultSet rsUserAccounts = DBUtil.dbExecuteQuery(selectStmt);
            ObservableList<UserAccount> userAccountList = getUserAccountList(rsUserAccounts);
            return userAccountList;
        } catch (SQLException var3) {
            System.out.println("SQL select operation has been failed: " + var3);
            throw var3;
        }
    }

    private static ObservableList<UserAccount> getUserAccountList(ResultSet rs) throws SQLException {
        ObservableList<UserAccount> userAccountList = FXCollections.observableArrayList();

        while(rs.next()) {
            UserAccount userAccount = new UserAccount();
            userAccount.setIdUserAccounts(rs.getInt("id_useraccounts"));
            userAccount.setUsername(rs.getString("username"));
            userAccount.setFirstname(rs.getString("firstname"));
            userAccount.setLastname(rs.getString("lastname"));
            userAccount.setMdpPharm(rs.getString("mdp_pharm"));
            userAccount.setStatus(rs.getString("status"));
            userAccountList.add(userAccount);
        }

        return userAccountList;
    }

    public static void updateUsername(String userId, String username) throws SQLException, ClassNotFoundException {
        String updateStmt = "UPDATE public.useraccounts\n" +
                "SET username = '" + username + "'\n" +
                "WHERE id_useraccounts = " + userId + ";\n";

        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException var4) {
            System.out.print("Error occurred while UPDATE Operation: " + var4);
            throw var4;
        }
    }

    public static void deleteUserWithId(String userId) throws SQLException, ClassNotFoundException {
        String updateStmt = "DELETE FROM public.useraccounts\n" +
                "WHERE id_useraccounts =" + userId + ";\n";

        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException var3) {
            System.out.print("Error occurred while DELETE Operation: " + var3);
            throw var3;
        }
    }

    public static void insertUserAccount(String username, String firstname, String lastname, String mdpPharm) throws SQLException, ClassNotFoundException {
        String updateStmt = "INSERT INTO public.useraccounts\n" +
                "(username, firstname, lastname, mdp_pharm)\n" +
                "VALUES\n('" + username + "', '" + firstname + "','" + lastname + "','" + mdpPharm + "');\n";

        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException var5) {
            System.out.print("Error occurred while INSERT Operation: " + var5);
            throw var5;
        }
    }
}

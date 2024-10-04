package lms.util;

import java.sql.*;

public class UserService {

    public static final String databaseLocation = "src/main/resources/database/data.db";
    public static final String URL = "jdbc:sqlite:" + databaseLocation;
    public static final String complaintDatabaseLocation = "src/main/resources/database/Complain.db";
    public static final String COMPLAINT_URL = "jdbc:sqlite:" + complaintDatabaseLocation;

    public static final String AnnouncementDatabaseLocation = "src/main/resources/database/Announcement.db";
    public static final String ANNOUNCEMENT_URL = "jdbc:sqlite:" + AnnouncementDatabaseLocation;

    public static final String TABLE_TEACHERS = "teachers";
    public static final String TABLE_STUDENTS = "students";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";

    private static Connection conn;
    private static Statement statement;
    private static ResultSet rs;

    /**
     * Check if a user already exists in database
     *
     * @param value     the value you want to search in the database
     * @param isStudent to know which database to check, student or teacher
     * @return true is 'value' found in database, false otherwise
     */
    public static boolean userExists(String value, UserAttribute attribute, boolean isStudent) {
        try {
            connection(value, attribute, isStudent);

            return rs.next();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            close(rs);
            close(statement);
            close(conn);
        }

        return false;
    }


    public static String getPassword(String value, UserAttribute attribute, boolean isStudent) {
        try {
            connection(value, attribute, isStudent);

            if (rs.next()) {
                return rs.getString(COLUMN_PASSWORD);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        } finally {
            close(rs);
            close(statement);
            close(conn);
        }

        return null;
    }


    /**
     * @param email_or_id the email/id for which i want to get information
     * @param table       which table i want to search
     * @param columnInfo  the information i want to get from that table
     * @return The data inside that column
     */
    public static String get(String email_or_id, UserAttribute attribute, String table, String columnInfo) {
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("Connection to database has been established.");

            statement = conn.createStatement();

            String query = "SELECT * FROM " + table + " WHERE " + attribute.getColumnName() + " = '" + email_or_id + "'";

            rs = statement.executeQuery(query);

            if (rs.next()) {
                return rs.getString(columnInfo);
            }

        } catch (SQLException e) {

            throw new RuntimeException(e);

        } finally {
            close(rs);
            close(statement);
            close(conn);
        }

        return null;
    }


    private static void connection(String searchValue, UserAttribute attribute, boolean isStudent) throws SQLException {
        conn = DriverManager.getConnection(URL);
        System.out.println("Connection to database has been established.");

        statement = conn.createStatement();

        String table = isStudent ? TABLE_STUDENTS : TABLE_TEACHERS;

        String column = switch (attribute) {
            case ID -> COLUMN_ID;
            case EMAIL -> COLUMN_EMAIL;
        };

        String query = "SELECT * FROM " + table + " WHERE " + column + " = '" + searchValue + "'";

        rs = statement.executeQuery(query);
    }


    private static void close(AutoCloseable ac) {
        if (ac != null) {
            try {
                ac.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


package lms.controller.dictionary;

import java.sql.*;

public class DictionaryModel {

    private static final String databaseLocation = "src/main/resources/database/dictionary.db";
    private static final String PATH_TO_DATABASE = "jdbc:sqlite:" + databaseLocation;
    private static final String TABLE_WORDS = "words";
    private static final String TABLE_USER_DEFINED_WORDS = "userDefinedWords";
    private static final String COLUMN_WORD = "word";
    private static final String COLUMN_MEANING = "meaning";
    private static final String COLUMN_ISBOOKMARKED = "isBookmarked";

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    public static boolean isBookmarked(Word word) {

        try {
            connection = DriverManager.getConnection(PATH_TO_DATABASE);
            statement = connection.createStatement();
            String query = "SELECT * FROM " + TABLE_WORDS + " WHERE " + COLUMN_WORD + " = '" + word.getWord() + "'";
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int isBookmarked = resultSet.getInt(COLUMN_ISBOOKMARKED);
                if (isBookmarked == 1) {
                    return true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {

            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public static boolean unBookmark(Word word) {

        try {
            connection = DriverManager.getConnection(PATH_TO_DATABASE);
            statement = connection.createStatement();
            String query = "UPDATE " + TABLE_WORDS + " SET " + COLUMN_ISBOOKMARKED
                    + " = 0 WHERE " + COLUMN_WORD + " = '" + word.getWord() + "'";

            statement.executeUpdate(query);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;

        } finally {

            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean Bookmark(Word word) {

        try {
            connection = DriverManager.getConnection(PATH_TO_DATABASE);
            statement = connection.createStatement();
            String query = "UPDATE " + TABLE_WORDS + " SET " + COLUMN_ISBOOKMARKED + " = 1 WHERE " + COLUMN_WORD + " = '" + word.getWord() + "'";

            statement.executeUpdate(query);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;

        } finally {

            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean addUserDefinedWord(UserDefinedWord word) {

        try {
            connection = DriverManager.getConnection(PATH_TO_DATABASE);
            statement = connection.createStatement();

            String query = "INSERT INTO " + TABLE_USER_DEFINED_WORDS
                    + " VALUES ('" + word.getWord() + "','" + word.getMeaning() + "')";

            statement.executeUpdate(query);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;

        } finally {

            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean deleteUserDefinedWord(UserDefinedWord word) {

        try {
            connection = DriverManager.getConnection(PATH_TO_DATABASE);
            statement = connection.createStatement();

            String query = "DELETE FROM " + TABLE_USER_DEFINED_WORDS
                    + " WHERE " + COLUMN_WORD + " = '" + word.getWord() + "'";

            statement.executeUpdate(query);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;

        } finally {

            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

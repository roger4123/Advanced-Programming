package CompulsoryL8.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryDAO {
    private Connection connection;

    public CountryDAO(Connection connection) {
        this.connection = connection;
    }

    public void addCountry(String name, String code, int continentId) throws SQLException {
        String query = "INSERT INTO countries (name, code, continent_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, code);
            stmt.setInt(3, continentId);
            stmt.executeUpdate();
        }
    }

    public String findCountryById(int id) throws SQLException {
        String query = "SELECT name FROM countries WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("name");
            }
        }
        return null;
    }

    public String findCountryByName(String name) throws SQLException {
        String query = "SELECT id, code, continent_id FROM countries WHERE name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return "ID: " + resultSet.getInt("id") +
                        ", Code: " + resultSet.getString("code") +
                        ", Continent ID: " + resultSet.getInt("continent_id");
            }
        }
        return "Country not found";
    }

    public int findCountryIdByName(String name) throws SQLException {
        String query = "SELECT id FROM countries WHERE name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        }
        return -1;
    }
}


package Compulsory.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContinentDAO {
    private final Connection connection;

    public ContinentDAO(Connection connection) {
        this.connection = connection;
    }

    public void addContinent(String name) throws SQLException {
        String query = "INSERT INTO continents (name) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.executeUpdate();
        }
    }

    public String findContinentById(int id) throws SQLException {
        String query = "SELECT name FROM continents WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("name");
            }
        }
        return null;
    }

    public String findContinentByName(String name) throws SQLException {
        String query = "SELECT id FROM continents WHERE name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("id"); // Returns the ID of the continent
            }
        }
        return null;
    }

}

package HomeworkL8.dao;

import HomeworkL8.City;
import HomeworkL8.DistanceCalculator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CityDAO {
    private Connection connection;

    public CityDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean cityExists(String name) throws SQLException {
        String query = "SELECT COUNT(*) FROM cities WHERE name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                return true;
            }
        }
        return false;
    }

    public void addCity(String name, int countryId, boolean isCapital, double latitude, double longitude) throws SQLException {
        if (cityExists(name)) {
            System.out.println("Skipping duplicate city: " + name);
            return;
        }

        String query = "INSERT INTO cities (name, country_id, capital, latitude, longitude) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setInt(2, countryId);
            stmt.setBoolean(3, isCapital);
            stmt.setDouble(4, latitude);
            stmt.setDouble(5, longitude);
            stmt.executeUpdate();
        }
    }

    public City findCityByName(String name) throws SQLException {
        String query = "SELECT * FROM cities WHERE name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new City(
                        rs.getInt("id"),
                        rs.getInt("country_id"),
                        rs.getString("name"),
                        rs.getBoolean("capital"),
                        rs.getDouble("longitude"),
                        rs.getDouble("latitude")
                );
            }
        }
        return null;
    }

    public double getDistanceBetweenCities(String city1Name, String city2Name) throws SQLException {
        City city1 = findCityByName(city1Name);
        City city2 = findCityByName(city2Name);

        if (city1 == null || city2 == null) {
            System.out.println("One or both cities not found.");
            return -1;
        }

        return DistanceCalculator.calculateDistance(
                city1.getLatitude(), city1.getLongitude(),
                city2.getLatitude(), city2.getLongitude()
        );
    }
}

package HomeworkL8;

import HomeworkL8.dao.CityDAO;
import HomeworkL8.db.DatabaseConnectionHikariCP;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            Connection connection = DatabaseConnectionHikariCP.getConnection();
            ImportTool.importData("src/main/resources/concap.csv");

            CityDAO cityDAO = new CityDAO(connection);
            City city = cityDAO.findCityByName("Paris");

            if(city != null) {
                System.out.println("\nCity " + city.getName() + " found.");
                System.out.println("Country ID: " + city.getCountry_id());
                System.out.println("Is it a capital? " + city.isCapital());
                System.out.println("Coordinates: " + city.getLatitude() + " lat. " + city.getLongitude() + " long.");
            } else {
                System.out.println("\nCity not found.");
            }

            double distance = cityDAO.getDistanceBetweenCities("Paris", "Tokyo");
            System.out.println(String.format("Distance between Paris and Tokyo: %.2f km.", distance));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

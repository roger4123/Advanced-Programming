package CompulsoryL8;

import java.sql.Connection;
import java.sql.SQLException;

import CompulsoryL8.db.DatabaseConnection;
import CompulsoryL8.dao.ContinentDAO;
import CompulsoryL8.dao.CountryDAO;


public class Main {
    public static void main(String[] args) {
        try {
            Connection connection = DatabaseConnection.getInstance();

            ContinentDAO continentDAO = new ContinentDAO(connection);
            CountryDAO countryDAO = new CountryDAO(connection);

            //continentDAO.addContinent("Europe");
            //countryDAO.addCountry("Romania", "ROU", 1);

            System.out.println("Continent with ID 1: " + continentDAO.findContinentById(1));
            System.out.println("Country with ID 1: " + countryDAO.findCountryById(1));


            System.out.println("Continent Africa ID: " + continentDAO.findContinentByName("Africa"));
            System.out.println("Country Japan Info: " + countryDAO.findCountryByName("Japan"));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}


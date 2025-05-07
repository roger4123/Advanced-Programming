package HomeworkL8;

import CompulsoryL8.dao.CountryDAO;
import HomeworkL8.dao.CityDAO;
import HomeworkL8.db.DatabaseConnectionHikariCP;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class ImportTool {
    public static void importData(String path) {
        try(Connection connection = DatabaseConnectionHikariCP.getConnection(); BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            CityDAO cityDAO = new CityDAO(connection);
            CountryDAO countryDAO = new CountryDAO(connection);

            br.readLine(); // skip header

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if(data.length < 6)
                    continue;

                String countryName = data[0].trim();
                String cityName = data[1].trim();
                double latitude = Double.parseDouble(data[2].trim());
                double longitude = Double.parseDouble(data[3].trim());
                String countryCode = data[4].trim();
                String continentName = data[5].trim();

                int countryID = countryDAO.findCountryIdByName(countryName);
                if(countryID == -1) {
                    System.out.println("Country " + countryName.toUpperCase() + " not found.");
                    continue;
                }

                cityDAO.addCity(cityName, countryID, true, latitude, longitude);

                System.out.println("Added city " + cityName + " to country " + countryName);
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}

package uz.oasis.repo;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import uz.oasis.config.DBConfig;
import uz.oasis.entity.Country;
import uz.oasis.model.CountryDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CountryRepo {

    private final DBConfig config;

    public List<Country> findAll() {
        try (
                Connection connection = config.dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM country ORDER BY name");
        ) {
            ResultSet resultSet = statement.executeQuery();
            List<Country> countries = new ArrayList<>();
            while (resultSet.next()) {
                countries.add(new Country(
                        resultSet.getObject(1, UUID.class),
                        resultSet.getString(2)
                ));
            }
            return countries;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteById(UUID id) {
        try (
                Connection connection = config.dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("DELETE FROM country WHERE id = ?");
        ) {
            statement.setObject(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Country findById(UUID id) {
        try (
                Connection connection = config.dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM country WHERE id = ?");
        ) {
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Country(
                        resultSet.getObject(1, UUID.class),
                        resultSet.getString(2)
                );
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateById(UUID id, CountryDto countryDto) {
        try (
                Connection connection = config.dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("UPDATE country SET name = ? WHERE id = ?");
        ) {
            statement.setString(1, countryDto.name());
            statement.setObject(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void save(CountryDto country) {
        try (
                Connection connection = config.dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("INSERT INTO country (name) VALUES (?)");
        ) {
            statement.setString(1, country.name());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

package uz.oasis.repo;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import uz.oasis.dbconfig.DBConfig;
import uz.oasis.entity.District;
import uz.oasis.model.request.DistrictRequestDto;
import uz.oasis.model.response.DistrictResponseDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class DistrictRepo {

    private final DBConfig config;

    public List<DistrictResponseDto> findAllResponseDto() {
        try (
                Connection connection = config.dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("""
                        SELECT d.id, d.name,r.name,c.name FROM district d
                        LEFT JOIN region r ON d.region_id = r.id
                        LEFT JOIN country c on c.id = r.country_id ORDER BY d.name""");
        ) {
            ResultSet resultSet = statement.executeQuery();
            List<DistrictResponseDto> districts = new ArrayList<>();
            while (resultSet.next()) {
                districts.add(new DistrictResponseDto(
                        resultSet.getObject(1, UUID.class),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)
                ));
            }
            return districts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteById(UUID id) {
        try (
                Connection connection = config.dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("DELETE FROM district WHERE id = ?");
        ) {
            statement.setObject(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public District findById(UUID id) {
        try (
                Connection connection = config.dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM district WHERE id = ?");
        ) {
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new District(
                        resultSet.getObject(1, UUID.class),
                        resultSet.getString(2),
                        resultSet.getObject(3, UUID.class)
                        );
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateById(UUID id, DistrictRequestDto districtRequestDto) {
        try (
                Connection connection = config.dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("UPDATE district SET name = ?, region_id = ? WHERE id = ?");
        ) {
            statement.setString(1, districtRequestDto.name());
            statement.setObject(2, districtRequestDto.regionId());
            statement.setObject(3, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void save(DistrictRequestDto districtRequestDto) {
        try (
                Connection connection = config.dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("INSERT INTO district (name, region_id) VALUES (?,?)");
        ) {
            statement.setString(1, districtRequestDto.name());
            statement.setObject(2, districtRequestDto.regionId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

package uz.oasis.repo;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import uz.oasis.config.DBConfig;
import uz.oasis.entity.Region;
import uz.oasis.model.request.RegionRequestDto;
import uz.oasis.model.response.RegionResponseDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class RegionRepo {

    private final DBConfig config;

    public List<RegionResponseDto> findAllResponseDto() {
        try (
                Connection connection = config.dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT r.id, r.name, c.name FROM region r LEFT JOIN public.country c ON c.id = r.country_id ORDER BY r.name");
        ) {
            ResultSet resultSet = statement.executeQuery();
            List<RegionResponseDto> regions = new ArrayList<>();
            while (resultSet.next()) {
                regions.add(new RegionResponseDto(
                        resultSet.getObject(1, UUID.class),
                        resultSet.getString(2),
                        resultSet.getString(3)
                ));
            }
            return regions;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteById(UUID id) {
        try (
                Connection connection = config.dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("DELETE FROM region WHERE id = ?");
        ) {
            statement.setObject(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Region findById(UUID id) {
        try (
                Connection connection = config.dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM region WHERE id = ?");
        ) {
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Region(
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

    public void updateById(UUID id, RegionRequestDto regionRequestDto) {
        try (
                Connection connection = config.dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("UPDATE region SET name = ?, country_id = ? WHERE id = ?");
        ) {
            statement.setString(1, regionRequestDto.name());
            statement.setObject(2, regionRequestDto.countryId());
            statement.setObject(3, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void save(RegionRequestDto regionRequestDto) {
        try (
                Connection connection = config.dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("INSERT INTO region (name, country_id) VALUES (?,?)");
        ) {
            statement.setString(1, regionRequestDto.name());
            statement.setObject(2, regionRequestDto.countryId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Region> findAll() {
        try (
                Connection connection = config.dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM region r ORDER BY r.name");
        ) {
            ResultSet resultSet = statement.executeQuery();
            List<Region> regions = new ArrayList<>();
            while (resultSet.next()) {
                regions.add(new Region(
                        resultSet.getObject(1, UUID.class),
                        resultSet.getString(2),
                        resultSet.getObject(3, UUID.class)
                ));
            }
            return regions;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Region> getByCountryId(UUID id) {
        try (
                Connection connection = config.dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM region r WHERE r.country_id = ? ORDER BY r.name");
        ) {
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            List<Region> regions = new ArrayList<>();
            while (resultSet.next()) {
                regions.add(new Region(
                        resultSet.getObject(1, UUID.class),
                        resultSet.getString(2),
                        resultSet.getObject(3, UUID.class)
                ));
            }
            return regions;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

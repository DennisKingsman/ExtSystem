package edu.studentpj.city.dao;

import edu.studentpj.city.domain.PersonRequest;
import edu.studentpj.city.domain.PersonResponse;
import edu.studentpj.city.exception.PersonCheckException;

import java.sql.*;

public class PersonCheckDao
{
    private static final String SQL_REQUEST =
            "select temporal from cr_address_person ap " +
            "inner join cr_person p on p.person_id = ap.person_id " +
            "inner join cr_address a on a.address_id = ap.address_id " +
            "where  " +
            "current_date >= ap.start_date and (current_date <= ap.end_date or ap.end_date is null)" +
            "and upper(p.sur_name) = upper(?)  " +
            "and upper(p.given_name) = upper(?) " +
            "and upper(p.patronymic) = upper(?)  " +
            "and p.date_of_birth = ? " +
            "and a.street_code = ?  " +
            "and upper(a.building) = upper(?)  ";

    public PersonResponse checkPerson(PersonRequest request) throws PersonCheckException
    {
        PersonResponse response = new PersonResponse();

        String  sql = SQL_REQUEST;

        if (request.getExtension() != null)
        {
            sql += "and upper(a.extension) = upper(?) ";
        }else{
            sql += "and extension is null ";
        }

        if (request.getApartment() != null)
        {
            sql += "and upper(a.apartment) = upper(?) ";
        }else {
            sql += "and apartment is null ";
        }

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql))
        {
            int counter = 1;

            stmt.setString(counter++, request.getSurName());
            stmt.setString(counter++, request.getGivenName());
            stmt.setString(counter++, request.getPatronymic());
            stmt.setDate(counter++, java.sql.Date.valueOf(request.getDateOfBirth()));
            stmt.setInt(counter++, request.getStreetCode());
            stmt.setString(counter++, request.getBuilding());

            if(request.getExtension() != null) {
                stmt.setString(counter++, request.getExtension());
            }

            if(request.getApartment() != null) {
                stmt.setString(counter++, request.getApartment());
            }

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next())
            {
                response.setRegistered(true);
                response.setTemporal(resultSet.getBoolean("temporal"));
            }
        }catch (SQLException ex) {
            throw new PersonCheckException(ex);
        }
        return response;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost/city_register", "postgres", "postgres");

    }
}

package olympic;

import olympic.business.Athlete;
import olympic.business.ReturnValue;
import olympic.business.Sport;
import olympic.data.DBConnector;
import olympic.data.PostgreSQLErrorCodes;

import java.sql.*;
import java.util.ArrayList;

import static olympic.business.ReturnValue.*;

public class Solution {
    public static void createTables() {
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Athletes\n" +
                    "(\n" +
                    "    id integer NOT NULL,\n" +
                    "    name varchar(255) NOT NULL,\n" +
                    "    country varchar(255) NOT NULL,\n" +
                    "    active boolean NOT NULL,\n" +
                    "    PRIMARY KEY (id),\n" +
                    "    CHECK (id > 0)\n" +
                    ")");
            statement.execute();

            statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Sports\n" +
                    "(\n" +
                    "    id integer NOT NULL,\n" +
                    "    name varchar(255) NOT NULL,\n" +
                    "    city varchar(255) NOT NULL,\n" +
                    "    athlete_count integer NOT NULL,\n" +
                    "    PRIMARY KEY (id),\n" +
                    "    CHECK (id > 0)\n" +
                    ")");
            statement.execute();

            statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Participates\n" +
                    "(\n" +
                    "    aid integer NOT NULL,\n" +
                    "    sid integer NOT NULL,\n" +
                    "    place integer DEFAULT NULL,\n" +
                    "    FOREIGN KEY (aid) REFERENCES Athletes(id),\n" +
                    "    FOREIGN KEY (sid) REFERENCES Sports(id)\n" +
                    ")");
            statement.execute();

            statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Observing\n" +
                    "(\n" +
                    "    aid integer NOT NULL,\n" +
                    "    sid integer NOT NULL,\n" +
                    "    payment integer DEFAULT 100 ,\n" +
                    "    FOREIGN KEY (aid) REFERENCES Athletes(id),\n" +
                    "    FOREIGN KEY (sid) REFERENCES Sports(id)\n" +
                    ")");
            statement.execute();

            statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Friends\n" +
                    "(\n" +
                    "    id_one integer NOT NULL,\n" +
                    "    id_two integer NOT NULL,\n" +
                    "    FOREIGN KEY (id_one) REFERENCES Athletes(id),\n" +
                    "    FOREIGN KEY (id_two) REFERENCES Athletes(id)\n" +
                    ")");
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            handleError(Integer.valueOf(e.getSQLState()));
        }
        finally {
            try {
                if(statement != null){
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void clearTables() {

        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("DELETE FROM Athletes");
            statement.execute();
            statement = connection.prepareStatement("DELETE FROM Sports");
            statement.execute();
//            statement = connection.prepareStatement("DELETE FROM Participates");
//            statement.execute();
//            statement = connection.prepareStatement("DELETE FROM Observing");
//            statement.execute();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        //DELETE FROM employees;
    }

    public static void dropTables() {
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("DROP TABLE IF EXISTS Participates");
            statement.execute();
            statement = connection.prepareStatement("DROP TABLE IF EXISTS Observing");
            statement.execute();
            statement = connection.prepareStatement("DROP TABLE IF EXISTS Friends");
            statement.execute();
            statement = connection.prepareStatement("DROP TABLE IF EXISTS Athletes");
            statement.execute();
            statement = connection.prepareStatement("DROP TABLE IF EXISTS Sports");
            statement.execute();

        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }

    public static ReturnValue addAthlete(Athlete athlete) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            int aid = athlete.getId();
            pstmt = connection.prepareStatement("INSERT INTO Athletes (id, name, country, active)" +
                    " VALUES ( ?, ? , ? , ? )");
            pstmt.setInt(1,aid);
            pstmt.setString(2, athlete.getName());
            pstmt.setString(3,athlete.getCountry());
            pstmt.setBoolean(4,athlete.getIsActive());
            pstmt.execute();

        } catch (SQLException e) {
            return handleError(Integer.valueOf(e.getSQLState()));
        }
        finally {
            try {
                if(pstmt != null){
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return ERROR;
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return OK;
    }

    public static Athlete getAthleteProfile(Integer athleteId) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try{
            pstmt = connection.prepareStatement("SELECT * FROM Athletes WHERE id = ?");
            pstmt.setInt(1, athleteId);
            ResultSet res = pstmt.executeQuery();
            int id = res.getInt(1);
            String name = res.getString(2);
            String country = res.getString(3);
            boolean active = res.getBoolean(4);
            Athlete athlete = new Athlete();
            athlete.setId(id);
            athlete.setName(name);
            athlete.setCountry(country);
            athlete.setIsActive(active);
            return athlete;
        }
        catch (SQLException e){
            e.printStackTrace();
            return Athlete.badAthlete();
        }
    }

    public static ReturnValue deleteAthlete(Athlete athlete) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            Integer aid = athlete.getId();
            pstmt = connection.prepareStatement("SELECT * from Athletes where id = ?");
            pstmt.setInt(1, aid);
            pstmt.execute();
            ResultSet res = pstmt.getResultSet();
            if(!res.next()){
                return NOT_EXISTS;
            }
            pstmt = connection.prepareStatement("DELETE FROM Athletes WHERE id = ?");
            pstmt.setInt(1, aid);
            pstmt.execute();
        }
        catch (SQLException e){
            return handleError(Integer.valueOf(e.getSQLState()));
        }
        return OK;
    }

    public static ReturnValue addSport(Sport sport) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            int sid = sport.getId();
            pstmt = connection.prepareStatement("INSERT INTO Sports (id, name, city, athlete_count)" +
                    " VALUES ( ?, ? , ? , ? )");
            pstmt.setInt(1,sid);
            pstmt.setString(2, sport.getName());
            pstmt.setString(3,sport.getCity());
            pstmt.setInt(4,sport.getAthletesCount());
            pstmt.execute();

        } catch (SQLException e) {
            return handleError(Integer.valueOf(e.getSQLState()));
        }
        finally {
            try {
                if(pstmt != null){
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return ERROR;
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return OK;
    }

    public static Sport getSport(Integer sportId) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try{
            pstmt = connection.prepareStatement("SELECT * FROM Sports WHERE id = ?");
            pstmt.setInt(1, sportId);
            ResultSet res = pstmt.executeQuery();
            int id = res.getInt(1);
            String name = res.getString(2);
            String city = res.getString(3);
            int athleteCount = res.getInt(4);
            Sport sport = new Sport();
            sport.setId(id);
            sport.setName(name);
            sport.setCity(city);
            sport.setAthletesCount(athleteCount);
            return sport;
        }
        catch (SQLException e){
            e.printStackTrace();
            return Sport.badSport();
        } }

    public static ReturnValue deleteSport(Sport sport) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            Integer sid = sport.getId();
            pstmt = connection.prepareStatement("SELECT * from Sports where id = ?");
            pstmt.setInt(1, sid);
            pstmt.execute();
            ResultSet res = pstmt.getResultSet();
            if(!res.next()){
                return NOT_EXISTS;
            }
            pstmt = connection.prepareStatement("DELETE FROM Sports WHERE id = ?");
            pstmt.setInt(1, sid);
            pstmt.execute();
        }
        catch (SQLException e){
            return handleError(Integer.valueOf(e.getSQLState()));
        }
        return OK;
    }

    public static ReturnValue athleteJoinSport(Integer sportId, Integer athleteId) {
        return OK;
    }

    public static ReturnValue athleteLeftSport(Integer sportId, Integer athleteId) {
        return OK;
    }

    public static ReturnValue confirmStanding(Integer sportId, Integer athleteId, Integer place) {
        return OK;
    }

    public static ReturnValue athleteDisqualified(Integer sportId, Integer athleteId) {
        return OK;
    }

    public static ReturnValue makeFriends(Integer athleteId1, Integer athleteId2) {
        return OK;
    }

    public static ReturnValue removeFriendship(Integer athleteId1, Integer athleteId2) {
        return OK;
    }

    public static ReturnValue changePayment(Integer athleteId, Integer sportId, Integer payment) {
        return OK;
    }

    public static Boolean isAthletePopular(Integer athleteId) {
        return true;
    }

    public static Integer getTotalNumberOfMedalsFromCountry(String country) {
        return 0;
    }

    public static Integer getIncomeFromSport(Integer sportId) {
        return 0;
    }

    public static String getBestCountry() {
        return "";
    }

    public static String getMostPopularCity() {
        return "";
    }

    public static ArrayList<Integer> getAthleteMedals(Integer athleteId) {
        return new ArrayList<>();
    }

    public static ArrayList<Integer> getMostRatedAthletes() {
        return new ArrayList<>();
    }

    public static ArrayList<Integer> getCloseAthletes(Integer athleteId) {
        return new ArrayList<>();
    }

    public static ArrayList<Integer> getSportsRecommendation(Integer athleteId) {
        return new ArrayList<>();
    }

    private static ReturnValue handleError(int errVal){
        if(errVal == PostgreSQLErrorCodes.UNIQUE_VIOLATION.getValue()){
            return ALREADY_EXISTS;
        }
        else if( errVal == PostgreSQLErrorCodes.NOT_NULL_VIOLATION.getValue() ||
                errVal == PostgreSQLErrorCodes.CHECK_VIOLATION.getValue())
            return BAD_PARAMS;
        return ERROR;
    }


}


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
                    "    standing integer DEFAULT NULL,\n" +
                    "    CHECK (standing BETWEEN 1 and 3),\n" +
                    "    FOREIGN KEY (aid) REFERENCES Athletes(id),\n" +
                    "    FOREIGN KEY (sid) REFERENCES Sports(id)\n" +
                    ")");
            statement.execute();

            statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Observes\n" +
                    "(\n" +
                    "    aid integer NOT NULL,\n" +
                    "    sid integer NOT NULL,\n" +
                    "    payment integer DEFAULT 100 ,\n" +
                    "    CHECK (payment > 0),\n" +
                    "    FOREIGN KEY (aid) REFERENCES Athletes(id),\n" +
                    "    FOREIGN KEY (sid) REFERENCES Sports(id)\n" +
                    ")");
            statement.execute();

            statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Friends\n" +
                    "(\n" +
                    "    id_one integer NOT NULL,\n" +
                    "    id_two integer NOT NULL,\n" +
                    "    FOREIGN KEY (id_one) REFERENCES Athletes(id),\n" +
                    "    FOREIGN KEY (id_two) REFERENCES Athletes(id),\n" +
                    "    CHECK (id_one != id_two)\n" +
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
            statement = connection.prepareStatement("DROP TABLE IF EXISTS Observes");
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
            DBConnector.printResults(res);
            res.next();
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
        // Select active from Athlete WHERE id = athleteId
        // if active
        // INSERT INTO Participates (aid, sid, standings)" +
        //                    " VALUES ( ?, ? , ? )
        // exception
        // else
        // INSERT INTO Observes (aid, sid, price)" +
        //                    " VALUES ( ?, ? , ?)
        // exception
        return OK;
    }

    public static ReturnValue athleteLeftSport(Integer sportId, Integer athleteId) {
        // Select active from Athlete WHERE id = athleteId
        // if active
        // DELETE FROM Participates WHERE aid = athleteId AND sid = sportId
        // else
        // DELETE FROM Observes WHERE aid = athleteId AND sid = sportId
        return OK;
    }

    public static ReturnValue confirmStanding(Integer sportId, Integer athleteId, Integer place) {
        // Select active from Athlete WHERE id = athleteId
        // Select * from Sports WHERE sid = sportId (for error handling) maybe throws error in update?
        // if active
        // UPDATE Participates SET standing = place WHERE aid = athleteId AND sid = sportId
        return OK;
    }

    public static ReturnValue athleteDisqualified(Integer sportId, Integer athleteId) {
        // Select active from Athlete WHERE id = athleteId
        // if active
        // UPDATE Participates SET standing = NULL WHERE aid = athleteId AND sid = sportId
        return OK;
    }

    public static ReturnValue makeFriends(Integer athleteId1, Integer athleteId2) {
        // Select * from Athlete WHERE id = athleteId1
        // Select * from Athlete WHERE id = athleteId2
        // if exist
        // Select * from Friends WHERE id_one = athleteId2 AND id_two = athleteId1
        // if we found, return OK (they are already friends)
        // INSERT INTO Friends (id_one, id_two)" +
        //                    " VALUES (?, ?)
        // exception
        return OK;
    }

    public static ReturnValue removeFriendship(Integer athleteId1, Integer athleteId2) {
        // Select * from Athlete WHERE id = athleteId1
        // Select * from Athlete WHERE id = athleteId2
        // if exist
        // Select * from Friends WHERE (id_one = athleteId2 AND id_two = athleteId1) OR (id_one = athleteId1 AND id_two = athleteId2)
        // if not exists return NOT_EXISTS
        // else
        // DELETE FROM Friends WHERE id_one = athleteId1 AND id_two = athleteId2
        // DELETE FROM Friends WHERE id_one = athleteId2 AND id_two = athleteId1
        return OK;
    }

    public static ReturnValue changePayment(Integer athleteId, Integer sportId, Integer payment) {
        // Select active from Athlete WHERE id = athleteId
        // same as standings, may need to check if sport exists
        // if !active
        // UPDATE Observes SET payment = payment WHERE aid = athleteId AND sid = sportId
        return OK;
    }

    public static Boolean isAthletePopular(Integer athleteId) {
        // Select active FROM Athletes WHERE id = athleteId
        // if active
        // may need to create view
        // SELECT sid from Participates WHERE aid = athleteId UNION SELECT sid from Observes WHERE aid = athleteId // all of athelets sports 1
        // SELECT id_two FROM Friends WHERE id_one = athleteId UNION SELECT id_one FROM Friends WHERE id_two = athleteId // friend of athelte 2
        // (SELECT sid from Participates WHERE IN (SELECT id_two FROM Friends WHERE id_one = athleteId UNION SELECT id_one FROM Friends WHERE id_two = athleteId) UNION SELECT sid from Observes WHERE IN (SELECT id_two FROM Friends WHERE id_one = athleteId UNION SELECT id_one FROM Friends WHERE id_two = athleteId)) // sports of friends of athletes 3
        // SELECT * FROM 3 WHERE NOT EXISTS (1)
        // if its not empty, return true else false
        // SELECT * FROM Friends WHERE
        return true;
    }

    public static Integer getTotalNumberOfMedalsFromCountry(String country) {
        // SELECT COUNT (SELECT standing FROM Participates WHERE EXISTS (SELECT id FROM Athletes WHERE country = country))
        return 0;
    }

    public static Integer getIncomeFromSport(Integer sportId) {
        // SELECT SUM FROM (SELECT payment FROM Observes WHERE EXISTS FROM (SELECT id FROM Sport WHERE id = sportId))
        return 0;
    }

    public static String getBestCountry() {
        //SELECT MAX FROM (SELECT COUNT FROM (SELECT standing FROM Participates WHERE EXISTS (SELECT id FROM Athletes GROUP_BY country)))
        return "";
    }

    public static String getMostPopularCity() {
        // SELECT MAX(City) FROM (SELECT AVG
        // SELECT COUNT(SELECT city from Sports GROUP_BY city) number of sport in each city
        // SELECT sid, COUNT(sid) FROM Participates number of athletes each sport
        // SELECT Orders.OrderID, Customers.CustomerName, Orders.OrderDate
        //FROM Orders
        //INNER JOIN Customers
        //ON Orders.CustomerID=Customers.CustomerID;
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


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
//            statement = connection.prepareStatement("DELETE FROM Participates");
//            statement.execute();
//            statement = connection.prepareStatement("DELETE FROM Observing");
//            statement.execute();
            statement = connection.prepareStatement("DELETE FROM Athletes");
            statement.execute();
            statement = connection.prepareStatement("DELETE FROM Sports");
            statement.execute();
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
//            statement = connection.prepareStatement("DROP VIEW IF EXISTS CountryStandings");
//            statement.execute();
//            statement = connection.prepareStatement("DROP VIEW IF EXISTS athletesfromcountry");
//            statement.execute();
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
            ReturnValue ret = handleError(Integer.valueOf(e.getSQLState()));
            return ret;
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
//            DBConnector.printResults(res);
            if(!res.next()){
                return Athlete.badAthlete();
            }
            Athlete athlete = new Athlete();
            int id = res.getInt(1);
            String name = res.getString(2);
            String country = res.getString(3);
            boolean active = res.getBoolean(4);
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
            if(!res.next()){
                return Sport.badSport();
            }
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
        Connection connection = DBConnector.getConnection();
        PreparedStatement insertStatement = null;
        PreparedStatement updateStatement = null;
        try{
            insertStatement = connection.prepareStatement("SELECT active from Athletes WHERE id = ?");
            insertStatement.setInt(1, athleteId);
            ResultSet res = insertStatement.executeQuery();
            res.next();
            boolean isActive  = res.getBoolean(1);
            if (isActive){
                insertStatement = connection.prepareStatement("INSERT INTO Participates (aid, sid) VALUES ( ?, ? )");
                updateStatement = connection.prepareStatement("UPDATE Sports SET athlete_count=athlete_count+1 WHERE id=?");
                updateStatement.setInt(1, sportId);
                updateStatement.execute();

            }
            else{
                insertStatement = connection.prepareStatement("INSERT INTO Observes (aid, sid) VALUES ( ?, ? )");
            }
            insertStatement.setInt(1, athleteId);
            insertStatement.setInt(2, sportId);

            insertStatement.execute();

        }
        catch (SQLException e){
            e.printStackTrace();
            return handleError(Integer.valueOf(e.getSQLState()));
        }
        finally {
            try {
                if(insertStatement != null){
                    insertStatement.close();
                }
                if(updateStatement != null){
                    updateStatement.close();
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

    public static ReturnValue athleteLeftSport(Integer sportId, Integer athleteId) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement insertStatement = null;
        PreparedStatement updateStatement = null;
        try{
            insertStatement = connection.prepareStatement("SELECT active from Athletes WHERE id = ?");
            insertStatement.setInt(1, athleteId);
            ResultSet res = insertStatement.executeQuery();
            res.next();
            boolean isActive  = res.getBoolean(1);
            if (isActive){
                insertStatement = connection.prepareStatement("DELETE FROM Participates WHERE aid=? AND sid=?");
                updateStatement = connection.prepareStatement("UPDATE Sports SET athlete_count=athlete_count-1 WHERE id=?");
                updateStatement.setInt(1, sportId);
                updateStatement.execute();

            }
            else{
                insertStatement = connection.prepareStatement("DELETE FROM Observes WHERE aid=? AND sid=?");
            }
            insertStatement.setInt(1, athleteId);
            insertStatement.setInt(2, sportId);

            insertStatement.execute();

        }
        catch (SQLException e){
            e.printStackTrace();
            return handleError(Integer.valueOf(e.getSQLState()));
        }
        finally {
            try {
                if(insertStatement != null){
                    insertStatement.close();
                }
                if(updateStatement != null){
                    updateStatement.close();
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

    public static ReturnValue confirmStanding(Integer sportId, Integer athleteId, Integer place) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try{
            pstmt = connection.prepareStatement("UPDATE Participates SET standing=? WHERE aid=? AND sid=?");
            pstmt.setInt(1, place);
            pstmt.setInt(2, athleteId);
            pstmt.setInt(3, sportId);
            pstmt.execute();
        }

        catch (SQLException e){
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
                return ERROR;
            }
        }
        return OK;
    }

    public static ReturnValue athleteDisqualified(Integer sportId, Integer athleteId) {
    Connection connection = DBConnector.getConnection();
    PreparedStatement pstmt = null;
        try{
            pstmt = connection.prepareStatement("UPDATE Participates SET standing=NULL WHERE aid=? AND sid=?");
            pstmt.setInt(1, athleteId);
            pstmt.setInt(2, sportId);
            pstmt.execute();
        }
        catch (SQLException e){
            e.printStackTrace();
            return handleError(Integer.valueOf(e.getSQLState()));
        }
        finally{
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

    public static ReturnValue makeFriends(Integer athleteId1, Integer athleteId2) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try{
            pstmt = connection.prepareStatement("SELECT * from Athletes WHERE id = ?");
            pstmt.setInt(1, athleteId1);
            pstmt.executeQuery();
            if(!pstmt.getResultSet().next()){
                return NOT_EXISTS;
            }
            pstmt = connection.prepareStatement("SELECT * from Athletes WHERE id = ?");
            pstmt.setInt(1, athleteId2);
            pstmt.executeQuery();
            if(!pstmt.getResultSet().next()){
                return NOT_EXISTS;
            }
            pstmt = connection.prepareStatement("SELECT * from Friends WHERE (id_one = ? AND id_two = ?) OR (id_one = ? AND id_two = ?) ");
            pstmt.setInt(1, athleteId1);
            pstmt.setInt(2, athleteId2);
            pstmt.setInt(3, athleteId2);
            pstmt.setInt(4, athleteId1);
            pstmt.executeQuery();
            if(pstmt.getResultSet().next()){
                return ALREADY_EXISTS;
            }
            pstmt = connection.prepareStatement("INSERT INTO Friends Values (? , ?)");
            pstmt.setInt(1, athleteId1);
            pstmt.setInt(2, athleteId2);
            pstmt.execute();

        }
        catch (SQLException e){
            e.printStackTrace();
            return handleError(Integer.valueOf(e.getSQLState()));
        }
        finally{
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

    public static ReturnValue removeFriendship(Integer athleteId1, Integer athleteId2) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try{
            pstmt = connection.prepareStatement("SELECT * from Athletes WHERE id = ?");
            pstmt.setInt(1, athleteId1);
            pstmt.executeQuery();
            if(!pstmt.getResultSet().next()){
                return NOT_EXISTS;
            }
            pstmt = connection.prepareStatement("SELECT * from Athletes WHERE id = ?");
            pstmt.setInt(1, athleteId2);
            pstmt.executeQuery();
            if(!pstmt.getResultSet().next()){
                return NOT_EXISTS;
            }
            pstmt = connection.prepareStatement("SELECT * from Friends WHERE (id_one = ? AND id_two = ?) OR (id_one = ? AND id_two = ?) ");
            pstmt.setInt(1, athleteId1);
            pstmt.setInt(2, athleteId2);
            pstmt.setInt(3, athleteId2);
            pstmt.setInt(4, athleteId1);
            pstmt.executeQuery();
            if(!pstmt.getResultSet().next()){
                return NOT_EXISTS;
            }
            pstmt = connection.prepareStatement("DELETE FROM Friends WHERE  (id_one = ? AND id_two=?) OR (id_one = ? AND id_two=?) ");
            pstmt.setInt(1, athleteId1);
            pstmt.setInt(2, athleteId2);
            pstmt.setInt(3, athleteId2);
            pstmt.setInt(4, athleteId1);
            pstmt.execute();

        }
        catch (SQLException e){
            e.printStackTrace();
            return handleError(Integer.valueOf(e.getSQLState()));
        }
        finally{
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

    public static ReturnValue changePayment(Integer athleteId, Integer sportId, Integer payment) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try{
            pstmt = connection.prepareStatement("SELECT * FROM Observes WHERE aid=? AND sid=?");
            pstmt.setInt(1, athleteId);
            pstmt.setInt(2, sportId);
            pstmt.executeQuery();
            if(!pstmt.getResultSet().next()){
                return NOT_EXISTS;
            }
            pstmt = connection.prepareStatement("UPDATE Observes SET payment=? WHERE aid=? AND sid=?");
            pstmt.setInt(1, payment);
            pstmt.setInt(2, athleteId);
            pstmt.setInt(3, sportId);
            pstmt.execute();

        }
        catch (SQLException e){
            return handleError(Integer.valueOf(e.getSQLState()));
        }
        finally{
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

    public static Boolean isAthletePopular(Integer athleteId) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try{
            pstmt = connection.prepareStatement("SELECT active FROM Athletes WHERE id=?");
            pstmt.setInt(1, athleteId);
            pstmt.executeQuery();
            if(!pstmt.getResultSet().next()){
                return false;
            }
            String allSports = "SELECT sid from Participates WHERE aid =" + athleteId + " UNION SELECT sid from Observes WHERE aid = " + athleteId;
            pstmt = connection.prepareStatement("CREATE OR REPLACE VIEW AllSports AS " + allSports);
            pstmt.execute();
            String allFriendsString = "SELECT id_two AS aid FROM Friends WHERE id_one = " + athleteId + " UNION SELECT id_one AS aid FROM Friends WHERE id_two = " + athleteId;
            pstmt = connection.prepareStatement("CREATE OR REPLACE VIEW AllFriends AS " + allFriendsString);
            pstmt.execute();
            pstmt = connection.prepareStatement("SELECT * FROM AllFriends");
            pstmt.executeQuery();
            System.out.println("All Friends: \n");
            DBConnector.printResults(pstmt.getResultSet());

            String sportOfFriends = "SELECT sid FROM Participates inner join AllFriends on AllFriends.aid = Participates.aid" +
                    " UNION (SELECT sid FROM Observes inner join AllFriends on AllFriends.aid = Observes.aid)";
            pstmt = connection.prepareStatement("CREATE OR REPLACE VIEW SportsOfFriends AS " + sportOfFriends);
            pstmt.execute();
            pstmt = connection.prepareStatement("SELECT * FROM SportsOfFriends");
            pstmt.executeQuery();
            System.out.println("Sports Of Friends: \n");
            DBConnector.printResults(pstmt.getResultSet());
//            String allInPart = "SELECT sid from Participates WHERE View.id_two IN ( " + allFriendsString + ")";
//            String intersect = "SELECT sid from ( "+ sportOfFriends + " ) as t WHERE NOT EXISTS ( " + allSports + " ) ";
            pstmt = connection.prepareStatement("SELECT SportsOfFriends.sid FROM SportsOfFriends full outer join AllSports on SportsOfFriends.sid = AllSports.sid" +
                    " where SportsOfFriends.sid is null or AllSports.sid is null");
            pstmt.executeQuery();
            pstmt.getResultSet().next();
            if(pstmt.getResultSet().getString(1) != null){
                return false;
            }

        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        finally{
            try {
                if(pstmt != null){
                    pstmt = connection.prepareStatement("DROP VIEW IF EXISTS SportsOfFriends");
                    pstmt.execute();
                    pstmt = connection.prepareStatement("DROP VIEW IF EXISTS AllFriends");
                    pstmt.execute();
                    pstmt = connection.prepareStatement("DROP VIEW IF EXISTS AllSports");
                    pstmt.execute();
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
                return false;
            }
        }
        return true;
    }


    public static Integer getTotalNumberOfMedalsFromCountry(String country) {
        // SELECT COUNT (SELECT standing FROM Participates WHERE EXISTS (SELECT id FROM Athletes WHERE country = country))
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        int count = 0;
        try {
            String q = "CREATE OR REPLACE VIEW AthletesFromCountry AS SELECT id From Athletes WHERE country='" + country + "'";
            pstmt = connection.prepareStatement(q);
            pstmt.execute();
            pstmt = connection.prepareStatement(" CREATE OR REPLACE VIEW CountryStandings AS SELECT aid, standing FROM Participates inner join" +
                    " AthletesFromCountry on AthletesFromCountry.id=Participates.aid");
            pstmt.execute();
            pstmt = connection.prepareStatement("SELECT COUNT(standing) FROM CountryStandings");
            //pstmt.setString(1, country);
            pstmt.executeQuery();
            pstmt.getResultSet().next();
            count = pstmt.getResultSet().getInt(1);
        }
        catch (SQLException e){
            return 0;
        }
        finally {
            try {
                pstmt = connection.prepareStatement("DROP VIEW IF EXISTS CountryStandings");
                pstmt.execute();
                pstmt = connection.prepareStatement("DROP VIEW IF EXISTS AthletesFromCountry");
                pstmt.execute();
                if(pstmt != null){
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return 0;
            }
            try {
                connection.close();
            } catch (SQLException e) {
                return 0;
            }
        }
        return count;
    }

    public static Integer getIncomeFromSport(Integer sportId) {
        // SELECT SUM FROM (SELECT payment FROM Observes WHERE EXISTS FROM (SELECT id FROM Sport WHERE id = sportId))
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        int count = 0;
        try {
            String q = "SELECT SUM(payment) FROM Observes where sid = ?";
            pstmt = connection.prepareStatement(q);
            pstmt.setInt(1, sportId);
            pstmt.executeQuery();
            pstmt.getResultSet().next();
            count = pstmt.getResultSet().getInt(1);
        }
        catch (SQLException e){
            return 0;
        }
        finally {
            try {
                if(pstmt != null){
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return 0;
            }
            try {
                connection.close();
            } catch (SQLException e) {
                return 0;
            }
        }
        return count;
    }

    public static String getBestCountry() {
        //SELECT MAX FROM (SELECT COUNT FROM (SELECT standing FROM Participates WHERE EXISTS (SELECT id FROM Athletes GROUP_BY country)))
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        int count = 0;
        try {
            String q = "CREATE OR REPLACE VIEW AthletesFromCountry AS SELECT id, country From Athletes";
            pstmt = connection.prepareStatement(q);
            pstmt.execute();
            pstmt = connection.prepareStatement(" CREATE OR REPLACE VIEW CountryStandings AS SELECT aid, standing, country FROM Participates inner join" +
                    " AthletesFromCountry on AthletesFromCountry.id=Participates.aid");
            pstmt.execute();
            pstmt = connection.prepareStatement("SELECT MAX(standing) FROM (SELECT country, COUNT(standing) from CountryStandings GROUP BY country)");
            //pstmt.setString(1, country);
            pstmt.executeQuery();
            pstmt.getResultSet().next();
            count = pstmt.getResultSet().getInt(1);
        }
        catch (SQLException e){
            return "";
        }
        finally {
            try {
                pstmt = connection.prepareStatement("DROP VIEW IF EXISTS CountryStandings");
                pstmt.execute();
                pstmt = connection.prepareStatement("DROP VIEW IF EXISTS AthletesFromCountry");
                pstmt.execute();
                if(pstmt != null){
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return "";
            }
            try {
                connection.close();
            } catch (SQLException e) {
                return "";
            }
        }

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

//    Connection connection = DBConnector.getConnection();
//    PreparedStatement pstmt = null;
//        try{
//                pstmt = connection.prepareStatement("UPDATE Participates SET standing=? WHERE aid=? AND sid=?");
//                pstmt.setInt(1, place);
//                pstmt.setInt(2, athleteId);
//                pstmt.setInt(3, sportId);
//                pstmt.execute();
//                }
//                catch (SQLException e){
//                e.printStackTrace();
//                handleError(Integer.valueOf(e.getSQLState()));
//                }
//                finally {
//                try {
//                if(pstmt != null){
//                pstmt.close();
//                }
//                } catch (SQLException e) {
//                e.printStackTrace();
//                return ERROR;
//                }
//                try {
//                connection.close();
//                } catch (SQLException e) {
//                //e.printStackTrace()();
//                }
//                }
//                return OK;
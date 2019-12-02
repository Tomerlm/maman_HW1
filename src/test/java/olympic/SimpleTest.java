package olympic;

import org.junit.Test;
import olympic.business.*;

import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static olympic.business.ReturnValue.*;
import static org.junit.Assert.assertFalse;

public class SimpleTest extends AbstractTest{

    @Test
    public void simpleTestCreateAthlete()
    {
        Athlete a = new Athlete();
        a.setId(1);
        a.setName("Artur");
        a.setCountry("Brazil");
        a.setIsActive(true);
        ReturnValue ret = Solution.addAthlete(a);
        assertEquals(OK, ret);
    }

    @Test
    public void testDeleteUserNotExist(){
        Athlete a = new Athlete();
        a.setId(10);
        a.setName("Eli");
        a.setCountry("Argentina");
        a.setIsActive(true);

        ReturnValue ret = Solution.deleteAthlete(a);
        assertEquals(NOT_EXISTS , ret);

    }

    @Test
    public void testDeleteUser(){
        Athlete a = new Athlete();
        a.setId(10);
        a.setName("Eli");
        a.setCountry("Argentina");
        a.setIsActive(true);
        ReturnValue ret = Solution.addAthlete(a);
        ret = Solution.deleteAthlete(a);
        assertEquals(OK , ret);

    }

    @Test
    public void simpleTestCreateAthleteTwice() {
        Athlete a = new Athlete();
        a.setId(1);
        a.setName("Artur");
        a.setCountry("Brazil");
        a.setIsActive(true);
        ReturnValue ret = Solution.addAthlete(a);
        assertEquals(OK, ret);
        ret = Solution.addAthlete(a);
        assertEquals(ALREADY_EXISTS, ret);
    }

    @Test
    public void getAthleteTest(){
        Athlete a = new Athlete();
        a.setId(10);
        a.setName("Eli");
        a.setCountry("Argentina");
        a.setIsActive(true);
        ReturnValue ret = Solution.addAthlete(a);
        assertEquals(OK , ret);
        Athlete b = Solution.getAthleteProfile(11);
        assertEquals(b.getId() , -1);
    }

    @Test
    public void confirmStandAndDisqua(){
        addAtheleteSport(10, true , 10); // aid = 10 sid = 10
        ReturnValue ret = Solution.confirmStanding(10 , 10, 4);
        assertEquals(BAD_PARAMS, ret);
        ret = Solution.confirmStanding(10 , 10, 3);
        assertEquals(OK, ret);
        ret = Solution.athleteDisqualified(10, 10);
        assertEquals(OK, ret);
    }

    @Test
    public void nothing(){

    }

    @Test
    public void addFriends(){
        ReturnValue ret = Solution.makeFriends(10 , 11);
        assertEquals(NOT_EXISTS, ret);
        addAtheleteSport(10, true ,10);
        ret = Solution.makeFriends(10 , 11);
        assertEquals(NOT_EXISTS, ret);
        addAtheleteSport(11, true ,11);
        ret = Solution.makeFriends(10 , 11);
        assertEquals(OK, ret);
        ret = Solution.makeFriends(10 , 11);
        assertEquals(ALREADY_EXISTS, ret);
        ret = Solution.removeFriendship(12, 13);
        assertEquals(NOT_EXISTS, ret);
        ret = Solution.removeFriendship(10, 13);
        assertEquals(NOT_EXISTS, ret);
        ret = Solution.removeFriendship(10, 11);
        assertEquals(OK, ret);
        ret = Solution.removeFriendship(10, 11);
        assertEquals(NOT_EXISTS, ret);


    }

    @Test
    public void setPayment(){
        addAtheleteSport(10, false ,10);
        ReturnValue ret = Solution.changePayment(11, 10, 101);
        assertEquals(NOT_EXISTS, ret);
        ret = Solution.changePayment(10, 11, 101);
        assertEquals(NOT_EXISTS, ret);
        ret = Solution.changePayment(10, 10, -1);
        assertEquals(BAD_PARAMS, ret);
        ret = Solution.changePayment(10, 10, 80);
        assertEquals(OK, ret);


    }

    @Test
    public void isPop(){
        addAtheleteSport(10, true ,10);
        addAtheleteSport(11, true ,11);
        addAtheleteSport(12, true ,12);
        addAtheleteSport(13, false ,13);
        addAtheleteSport(14, false ,14);
        Solution.makeFriends(11, 10);
        Solution.makeFriends(10, 12);
        Solution.makeFriends(10, 13);
        Solution.athleteJoinSport(11, 10);
        Solution.athleteJoinSport(12, 10);
        Solution.athleteJoinSport(13, 10);
        boolean ret = Solution.isAthletePopular(10);
        assertTrue(ret);
        Solution.makeFriends(10, 14);
        ret = Solution.isAthletePopular(10);
        assertFalse(ret);
        Solution.athleteJoinSport(14, 10);
        ret = Solution.isAthletePopular(10);
        assertTrue(ret);
    }

    @Test
    public void totalMedals(){
        //check for one athelte
        addAtheleteSport(10, true, 10);
        addAtheleteSport(11, true, 11);
        int ret = Solution.getTotalNumberOfMedalsFromCountry("Argentina");
        assertEquals(0, ret);
        Solution.confirmStanding(10, 10, 2);
        ret = Solution.getTotalNumberOfMedalsFromCountry("Argentina");
        assertEquals(1, ret);
        Solution.athleteDisqualified(10 , 10);
        ret = Solution.getTotalNumberOfMedalsFromCountry("Argentina");
        assertEquals(0, ret);
        Solution.confirmStanding(10, 10, 2);
        Solution.confirmStanding(11, 11, 3);
        ret = Solution.getTotalNumberOfMedalsFromCountry("Argentina");
        assertEquals(2, ret);
        Solution.athleteDisqualified(10 , 10);
        createAndAddAthlete(12, "bjorn", true, "Sweden");
        Solution.athleteJoinSport(10, 12);
        Solution.confirmStanding(10, 12, 3);
        createAndAddAthlete(13, "bjorn", true, "Sweden");
        Solution.athleteJoinSport(10, 13);
        Solution.confirmStanding(10, 13, 3);
        String retStr = Solution.getBestCountry();
        assertEquals("Sweden", retStr);



    }

    @Test
    public void totalPayment(){
        addAtheleteSport(10, false, 10);
        addAtheleteSport(11, false, 11);
        Solution.athleteJoinSport(10, 11);
        int ret = Solution.getIncomeFromSport(10);
        assertEquals(200, ret);
        Solution.changePayment(10, 10, 200);
        ret = Solution.getIncomeFromSport(10);
        assertEquals(300, ret);

    }

    @Test
    public void avgSport(){
        //check for one athelte
        String city = Solution.getMostPopularCity();
        assertEquals(city, "");
        createAndAddAthlete(1, "avi" , true, "israel");
        createAndAddAthlete(2, "moshe", true, "israel");
        createAndAddAthlete(3, "moshe", true, "israel");
        createAndAddAthlete(4, "moshe", true, "israel");
        createAndAddSport(10, "soccer", "haifa");
        createAndAddSport(11, "baseball", "telaviv");
        createAndAddSport(12, "basketbal", "haifa");
        Solution.athleteJoinSport(10, 1);
        Solution.athleteJoinSport(10, 2);
        Solution.athleteJoinSport(12, 3);
        Solution.athleteJoinSport(12, 4);
        Solution.athleteJoinSport(11, 1);
        Solution.athleteJoinSport(11, 2);
        Solution.athleteJoinSport(11, 3);
        city = Solution.getMostPopularCity();
        assertEquals(city, "telaviv");

    }

    @Test
    public void multiInserts(){
        int id = 1;
        String name = "tosi";
        String city = "haifa";
        Sport a = new Sport();
        a.setId(id);
        a.setCity(city);
        a.setName(name);
        ReturnValue val = Solution.addSport(a);
        assertEquals(OK, val);
        val = Solution.addSport(a);
        assertEquals(ALREADY_EXISTS, val);



    }

    @Test
    public void getAthleteMedals(){
        ArrayList listt = Solution.getAthleteMedals(2);
        assertEquals(0, listt.get(0));
        assertEquals(0, listt.get(1));
        assertEquals(0, listt.get(2));
        createAndAddAthlete(1, "tomer", true, "israel");
        createAndAddSport(1, "soccer", "haifa");
        createAndAddSport(2, "basket", "haifa");
        createAndAddSport(3, "cricket", "tlv");
        createAndAddSport(4, "cricket", "tlv");
        Solution.athleteJoinSport(4, 1);
        Solution.athleteJoinSport(1, 1);
        Solution.athleteJoinSport(3, 1);
        Solution.athleteJoinSport(2, 1);
        Solution.confirmStanding(1, 1, 1);
        Solution.confirmStanding(4, 1, 3);
        Solution.confirmStanding(2, 1, 2);
        Solution.confirmStanding(3, 1, 3);
        ArrayList list = Solution.getAthleteMedals(1);
        assertEquals(1, list.get(0));
        assertEquals(1, list.get(1));
        assertEquals(2, list.get(2));
        Solution.athleteDisqualified(1, 1);
        list = Solution.getAthleteMedals(1);
        assertEquals(0, list.get(0));
        assertEquals(1, list.get(1));
        assertEquals(2, list.get(2));



    }

    @Test
    public void getMostRatedAth(){
        int count = 1;
        for(int i = 1; i <= 8; i++ ){
            createAndAddAthlete(i , "tomer", true, "israel");
            createAndAddSport(i, "soccer", "haifa");
            Solution.athleteJoinSport(i, i);
            Solution.confirmStanding(i, i , count++);
            if(count == 4){
                count = 1;
            }
        }

        ArrayList list = Solution.getMostRatedAthletes();
        for(int i = 0;  i < list.size(); i++ ){
            System.out.println(list.get(i));
        }



    }

    @Test
    public void getClosest(){
        for(int i = 1; i <= 8; i++ ){
            createAndAddAthlete(i , "tomer", true, "israel");
            createAndAddSport(i, "soccer", "haifa");
            Solution.athleteJoinSport(i, i);
            Solution.athleteJoinSport(1, i);
        }
        for(int i = 9; i <= 12; i++ ){
            createAndAddAthlete(i , "tomer", false, "israel");
            createAndAddSport(i, "soccer", "haifa");
            Solution.athleteJoinSport(1, i);
        }

        ArrayList list = Solution.getCloseAthletes(1);
        for(int i = 0;  i < list.size(); i++ ){
            System.out.println(list.get(i));
        }

        Solution.athleteJoinSport(3, 1);
        list = Solution.getCloseAthletes(1);
        for(int i = 0;  i < list.size(); i++ ){
            System.out.println(list.get(i));
        }

    }

    public void addAtheleteSport(int aid, boolean isActive, int sid){
        Athlete a = new Athlete();
        a.setId(aid);
        a.setName("Eli");
        a.setCountry("Argentina");
        a.setIsActive(isActive);
        Sport s = new Sport();
        s.setId(sid);
        s.setCity("haifa");
        s.setName("soccer");
        ReturnValue ret = Solution.addAthlete(a);
        ret = Solution.addSport(s);
        Solution.athleteJoinSport(sid,aid);
    }
    public void createAndAddAthlete(int id, String name, boolean isActive, String country){
        Athlete a = new Athlete();
        a.setId(id);
        a.setIsActive(isActive);
        a.setName(name);
        a.setCountry(country);
        Solution.addAthlete(a);
    }
    public void createAndAddSport(int id, String name, String city){
        Sport a = new Sport();
        a.setId(id);
        a.setCity(city);
        a.setName(name);
        Solution.addSport(a);
    }

}
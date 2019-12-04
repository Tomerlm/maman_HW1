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
        for(int i =1; i < 100; i++) {

            Athlete a = new Athlete();
            a.setId(i);
            a.setName("Artur");
            a.setCountry("Brazil");
            a.setIsActive(true);
            ReturnValue ret = Solution.addAthlete(a);
            assertEquals(OK, ret);
        }
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
        b = Solution.getAthleteProfile(10);
        assertEquals(b.getName() , "Eli");
        Solution.deleteAthlete(a);
        b = Solution.getAthleteProfile(10);
        assertEquals(b.getId() , -1);
    }

    @Test
    public void confirmStandAndDisqua(){
        createAndAddAthlete(10,"y",true,"y");
        createAndAddSport(10,"f","f");
        ReturnValue ret;
        ret = Solution.athleteDisqualified(10, 11);
        assertEquals(NOT_EXISTS, ret);
        ret = Solution.athleteDisqualified(11, 10);
        assertEquals(NOT_EXISTS, ret);
        ret = Solution.athleteDisqualified(10, 10);
        assertEquals(NOT_EXISTS, ret);
        Solution.athleteJoinSport(10,10);
        Solution.confirmStanding(10 , 10, 1);
        ret = Solution.athleteDisqualified(10, 10);
        assertEquals(OK, ret);
    }

    @Test
    public void nothing(){

    }

    @Test
    public void addFriends(){
        ReturnValue ret = Solution.makeFriends(10 , 10);
        assertEquals(BAD_PARAMS, ret);
         ret = Solution.makeFriends(10 , 11);
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
        ret = Solution.makeFriends(10 , 11);
        assertEquals(OK, ret);
        Athlete a = new Athlete();
        a.setId(10);
        Solution.deleteAthlete(a);


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
        assertFalse(Solution.isAthletePopular(10));
        addAtheleteSport(10, true ,10);
        assertTrue(Solution.isAthletePopular(10));
        createAndAddAthlete(20, "y", true, "c");
        assertTrue(Solution.isAthletePopular(20));
        addAtheleteSport(11, true ,11);
        addAtheleteSport(12, true ,12);
        addAtheleteSport(13, false ,13);
        addAtheleteSport(14, false ,14);
        Solution.makeFriends(11, 10);
        Solution.makeFriends(10, 12);
        Solution.makeFriends(10, 13);
        Solution.makeFriends(20, 10);
        Solution.makeFriends(20, 12);
        Solution.makeFriends(20, 13);
        assertFalse(Solution.isAthletePopular(20));
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
        String str = Solution.getBestCountry();
        assertEquals("", str);
        int n = Solution.getTotalNumberOfMedalsFromCountry("Argentina");
        assertEquals(0, n);
        addAtheleteSport(10, true, 10);
        addAtheleteSport(11, true, 11);
        str = Solution.getBestCountry();
        assertEquals("", str);
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
        Solution.confirmStanding(10, 10, 3);
        retStr = Solution.getBestCountry();
        assertEquals("Argentina", retStr);




    }

    @Test
    public void totalPayment(){
        int n = Solution.getIncomeFromSport(10);
        assertEquals(0, n );
        addAtheleteSport(10, false, 10);
        addAtheleteSport(11, false, 11);
        Solution.athleteJoinSport(10, 11);
        int ret = Solution.getIncomeFromSport(10);
        assertEquals(200, ret);
        Solution.changePayment(10, 10, 200);
        ret = Solution.getIncomeFromSport(10);
        assertEquals(300, ret);
        Athlete a = new Athlete();
        a.setId(10);
        Solution.deleteAthlete(a);
        ret = Solution.getIncomeFromSport(10);
        assertEquals(100, ret);
        Sport s = new Sport();
        s.setId(11);
        Solution.deleteSport(s);
        ret = Solution.getIncomeFromSport(11);
        assertEquals(0, ret);
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
        createAndAddAthlete(5, "moshe", true, "israel");
        createAndAddAthlete(6, "moshe", true, "israel");
        createAndAddAthlete(7, "moshe", true, "israel");
        createAndAddSport(10, "soccer", "haifa");
        createAndAddSport(11, "baseball", "telaviv");
        createAndAddSport(12, "basketbal", "haifa");
        Solution.athleteJoinSport(11, 1);
        Solution.athleteJoinSport(11, 2);
        Solution.athleteJoinSport(11, 3);
        Solution.athleteJoinSport(10, 1);
        Solution.athleteJoinSport(10, 2);
        Solution.athleteJoinSport(10, 5);
        Solution.athleteJoinSport(10, 6);
        Solution.athleteJoinSport(12, 3);
        Solution.athleteJoinSport(12, 4);

        city = Solution.getMostPopularCity();
        assertEquals(city, "telaviv");
        Solution.athleteJoinSport(10, 7);
        city = Solution.getMostPopularCity();
        assertEquals(city, "haifa");
        Athlete a = new Athlete();
        a.setId(7);
        Solution.deleteAthlete(a);
        city = Solution.getMostPopularCity();
        assertEquals(city, "haifa");


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
        Athlete a = new Athlete();
        a.setId(1);
        Solution.athleteDisqualified(4, 1);
        Solution.athleteDisqualified(3, 1);
        listt = Solution.getAthleteMedals(1);
        assertEquals(0, listt.get(0));
        assertEquals(1, listt.get(1));
        assertEquals(0, listt.get(2));
        Solution.deleteAthlete(a);
        listt = Solution.getAthleteMedals(1);
        assertEquals(0, listt.get(0));
        assertEquals(0, listt.get(1));
        assertEquals(0, listt.get(2));






    }

    @Test
    public void getMostRatedAth(){
        assertEquals(0, Solution.getMostRatedAthletes().size());
        createAndAddAthlete(44 , "tomer", true, "israel");
        createAndAddSport(44, "soccer", "haifa");
        Solution.athleteJoinSport(44, 44);
        Solution.confirmStanding(44, 44 , 1);
        assertEquals(1, Solution.getMostRatedAthletes().size());
        int count = 1;
        for(int i = 1; i <= 12; i++ ){
            createAndAddAthlete(i , "tomer", true, "israel");
            createAndAddSport(i, "soccer", "haifa");
            Solution.athleteJoinSport(i, i);
            Solution.confirmStanding(i, i , count++);
            if(count == 4){
                count = 1;
            }
        }
        for(int i = 1; i <= 12; i+=2 ){
            createAndAddSport(i + 100, "soccer", "haifa");
            Solution.athleteJoinSport(i + 100, i);
            Solution.confirmStanding(i + 100, i , count++);
            if(count == 4){
                count = 1;
            }
        }

        ArrayList list = Solution.getMostRatedAthletes();
        for(int i = 0;  i < list.size(); i++ ){
            System.out.println(list.get(i));
        }

        Athlete a = new Athlete();
        a.setId(1);
        Solution.deleteAthlete(a);
        System.out.println("SHURAAAA\n");
        list = Solution.getMostRatedAthletes();
        for(int i = 0;  i < list.size(); i++ ){
            System.out.println(list.get(i));
        }




    }

    @Test
    public void getClosest(){
        assertEquals(0, Solution.getMostRatedAthletes().size());
        assertEquals(0, Solution.getSportsRecommendation(1).size());
        assertEquals(0, Solution.getCloseAthletes(200).size());
        createAndAddAthlete(200 ,"n", true, "c");
        createAndAddAthlete(100, "soccer", false, "c");
        createAndAddSport(400, "soccer", "haifa");
        createAndAddSport(500, "soccer", "haifa");
        Solution.athleteJoinSport(400, 100);
        Solution.athleteJoinSport(500, 200);
        assertEquals(0, Solution.getCloseAthletes(200).size());

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
        System.out.println("No Sport Athlete: ");
        list = Solution.getCloseAthletes(200);
        for(int i = 0;  i < list.size(); i++ ){
            System.out.println(list.get(i));
        }

        Solution.athleteJoinSport(3, 1);
        Solution.athleteJoinSport(4, 1);
        Solution.athleteJoinSport(5, 1);
        list = Solution.getCloseAthletes(1);
        for(int i = 0;  i < list.size(); i++ ){
            System.out.println(list.get(i));
        }

        Solution.athleteJoinSport(6, 3);
        Solution.athleteJoinSport(6, 4);
        Solution.athleteJoinSport(6, 5);
        Solution.athleteJoinSport(7, 4);
        list = Solution.getSportsRecommendation(1);
        for(int i = 0;  i < list.size(); i++ ){
            System.out.println(list.get(i));
        }

    }

    @Test
    public void getRec(){
        assertEquals(0, Solution.getSportsRecommendation(1).size());

        createAndAddAthlete(1 , "tomer", true, "israel");
        assertEquals(0, Solution.getSportsRecommendation(1).size());
        createAndAddSport(1, "soccer", "haifa");
        Solution.athleteJoinSport(1, 1);
        assertEquals(0, Solution.getSportsRecommendation(1).size());
        createAndAddAthlete(200 , "tomer", true, "israel");
        createAndAddAthlete(300 , "tomer", true, "israel");
        createAndAddSport(200, "soccer", "haifa");
        createAndAddSport(300, "soccer", "haifa");
        Solution.athleteJoinSport(200, 200);
        Solution.athleteJoinSport(300, 300);
        ArrayList list = Solution.getSportsRecommendation(1);
        assertEquals(list.get(0), 200);
        assertEquals(list.get(1), 300);


        for(int i = 2; i <= 8; i++ ){
            createAndAddAthlete(i , "tomer", true, "israel");
            createAndAddSport(i, "soccer", "haifa");
            Solution.athleteJoinSport(i, i);
            Solution.athleteJoinSport(4, i);
            Solution.athleteJoinSport(5, i);
            Solution.athleteJoinSport(6, i);
        }
        for(int i = 9; i <= 12; i++ ){
            createAndAddAthlete(i , "tomer", false, "israel");
            createAndAddSport(i, "soccer", "haifa");
            Solution.athleteJoinSport(1, i);
        }

        list = Solution.getSportsRecommendation(1);
        assertEquals(list.get(0), 4);
        assertEquals(list.get(1), 5);
        assertEquals(list.get(2), 6);

    }

    @Test
    public void stand(){
        ReturnValue ret = Solution.confirmStanding(10, 10, 0);
        assertEquals(NOT_EXISTS, ret);
        ret = Solution.confirmStanding(10, 10, -1);
        assertEquals(NOT_EXISTS, ret);
        createAndAddAthlete(10 , "y" , true, "y");
        createAndAddSport(10 , "Y", "Y");
        ret = Solution.confirmStanding(10, 10, -1);
        assertEquals(NOT_EXISTS, ret);
        Solution.athleteJoinSport(10, 10);
        ret = Solution.confirmStanding(10, 10, 4);
        assertEquals(BAD_PARAMS, ret);


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
package olympic;

import olympic.business.Athlete;
import olympic.business.ReturnValue;
import olympic.business.Sport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Example {

    public static void main(String[] args) {
        Solution.clearTables();
        Solution.dropTables();
        Solution.createTables();
        Test_1_Basic();
        Solution.clearTables();
        Test_2_Basic();
        Solution.clearTables();
        Test_3_getAthleteMedals();
        Solution.clearTables();
        Test_4_getMostRatedAthletes();
        Solution.clearTables();
        Test_5_getCloseAthletes();
        Solution.clearTables();
        Test_6_getSportsRecommendation();
        Solution.dropTables();
        Test_1_Basic();
        Test_2_Basic();
        Test_3_getAthleteMedals();
    }

    public static void Test_1_Basic() {
        Solution.clearTables();
        Sport s = new Sport();
        s.setId(1);
        s.setName("football");
        s.setCity("TLV");

        Athlete a = new Athlete();
        a.setId(1);
        a.setCountry("Israel");
        a.setName("gadi");

        Solution.addAthlete(a);
        Solution.addSport(s);

        if(Solution.getSport(s.getId()).equals(s) == Boolean.TRUE)
            System.out.println("1.1 OK");
        if(Solution.getAthleteProfile(a.getId()).equals(a) == Boolean.TRUE)
            System.out.println("1.2 OK");
        if(Solution.getSport(2).equals(Sport.badSport()) == Boolean.TRUE)
            System.out.println("1.3 OK");

        Solution.athleteJoinSport(s.getId(),a.getId());

        if(Solution.confirmStanding(s.getId(),a.getId(),5) == ReturnValue.NOT_EXISTS)
            System.out.println("1.4 OK");
        if (Solution.confirmStanding(2,a.getId(),5) == ReturnValue.NOT_EXISTS)
            System.out.println("1.5 OK");
        a.setId(2);
        a.setIsActive(true);
        Solution.addAthlete(a);
        Solution.athleteJoinSport(s.getId(),a.getId());
        if (Solution.confirmStanding(s.getId(),a.getId(),3) == ReturnValue.OK)
            System.out.println("1.6 OK");

        if(Solution.athleteLeftSport(s.getId(),3) == ReturnValue.NOT_EXISTS)
            System.out.println("1.7 OK");
        if(Solution.athleteLeftSport(3,a.getId()) == ReturnValue.NOT_EXISTS)
            System.out.println("1.8 OK");
        if(Solution.athleteLeftSport(s.getId(),a.getId()) == ReturnValue.OK)
            System.out.println("1.9 OK");

        Athlete b = new Athlete();
        b.setId(11);
        b.setCountry("Israel");
        b.setName("g");
        b.setIsActive(true);

        Solution.addAthlete(b);
        Solution.athleteJoinSport(s.getId(),b.getId());
        if(Solution.makeFriends(a.getId(),b.getId()) == ReturnValue.OK)
            System.out.println("1.10 OK");
        if(Solution.makeFriends(a.getId(),b.getId()) == ReturnValue.ALREADY_EXISTS)
            System.out.println("1.11 OK");
        if(Solution.makeFriends(b.getId(),a.getId()) == ReturnValue.ALREADY_EXISTS)
            System.out.println("1.12 OK");
        if(Solution.makeFriends(a.getId(),a.getId()) == ReturnValue.BAD_PARAMS)
            System.out.println("1.13 OK");

        if(Solution.removeFriendship(a.getId(),a.getId()) == ReturnValue.NOT_EXISTS)
            System.out.println("1.14 OK");
        if(Solution.removeFriendship(b.getId(),a.getId()) == ReturnValue.OK)
            System.out.println("1.15 OK");

        if(Solution.changePayment(b.getId(),s.getId(),200) == ReturnValue.NOT_EXISTS)
            System.out.println("1.16 OK");

        Athlete c = new Athlete();
        c.setId(22);
        c.setCountry("Canada");
        c.setName("g");

        Solution.addAthlete(c);
        // a && c not in Joined, b in Joined => a not popular
        if(Solution.makeFriends(b.getId(),a.getId()) == ReturnValue.OK)
            System.out.println("1.17 OK");
        if(Solution.makeFriends(a.getId(),c.getId()) == ReturnValue.OK)
            System.out.println("1.18 OK");
        if(!Solution.isAthletePopular(a.getId()))
            System.out.println("1.19 OK");

        if(Solution.athleteLeftSport(s.getId(),b.getId()) == ReturnValue.OK)
            System.out.println("1.20 OK");
        if(Solution.isAthletePopular(a.getId()))
            System.out.println("1.21 OK");
        Solution.clearTables();

        Solution.addAthlete(a);
        Solution.addAthlete(b);
        Solution.addAthlete(c);
        Solution.addSport(s);
        Solution.athleteJoinSport(s.getId(),a.getId());
        Solution.athleteJoinSport(s.getId(),b.getId());
        Solution.athleteJoinSport(s.getId(),c.getId());

        Solution.confirmStanding(s.getId(),a.getId(),1);
        Solution.confirmStanding(s.getId(),b.getId(),2);
        Solution.confirmStanding(s.getId(),c.getId(),2);

        if(Solution.getTotalNumberOfMedalsFromCountry("Israel") == 2)
            System.out.println("1.22 OK");
        if(Solution.getTotalNumberOfMedalsFromCountry("Canada") == 0)
            System.out.println("1.23 OK");
        Solution.athleteDisqualified(s.getId(),a.getId());
        if(Solution.getTotalNumberOfMedalsFromCountry("Israel") == 1)
            System.out.println("1.24 OK");
        Solution.changePayment(a.getId(),s.getId(),100);
        Solution.changePayment(b.getId(),s.getId(),100);
        Solution.changePayment(c.getId(),s.getId(),100);

        if(Solution.getIncomeFromSport(s.getId()) == 100)
            System.out.println("1.25 OK");

        Athlete d = new Athlete();
        d.setId(22);
        d.setCountry("France");
        d.setName("g");
        d.setIsActive(true);
        Solution.addAthlete(d);
        if(Solution.athleteJoinSport(s.getId(),d.getId()) == ReturnValue.ALREADY_EXISTS)
            System.out.println("1.26.0 OK");
        Solution.changePayment(d.getId(),s.getId(),300);
        if(Solution.getIncomeFromSport(s.getId()) == 100)
            System.out.println("1.26 OK");
        Solution.athleteLeftSport(s.getId(),a.getId());
        Solution.athleteJoinSport(s.getId(),a.getId());
        Solution.confirmStanding(s.getId(),d.getId(),2);
        Solution.confirmStanding(s.getId(),a.getId(),3);
        if(Solution.getBestCountry().contains("Israel"))  //  should by Israel
            System.out.println("1.27 OK");
    }
    public static void Test_2_Basic() {
        Solution.clearTables();
        Athlete a = new Athlete();
        Sport s = new Sport();
        a.setId(1);
        a.setName("yossi");
        a.setCountry("israel");
        a.setIsActive(true);
        Solution.addAthlete(a);
        a.setId(2);
        a.setName("gadi");
        a.setCountry("israel");
        a.setIsActive(false);
        Solution.addAthlete(a);
        a.setId(3);
        a.setName("moshe");
        a.setCountry("canada");
        a.setIsActive(true);
        Solution.addAthlete(a);
        s.setId(12);
        s.setName("tennis");
        s.setCity("tel-aviv");
        Solution.addSport(s);
        s.setId(1);
        s.setName("swim");
        s.setCity("tel-aviv");
        Solution.addSport(s);
        s.setId(3);
        s.setName("football");
        s.setCity("haifa");
        Solution.addSport(s);
        Solution.athleteJoinSport(12,1);
        Solution.athleteJoinSport(1,2);
        Solution.makeFriends(1,2);
        Solution.makeFriends(1,3);

        if(Solution.isAthletePopular(1))
            System.out.println("2.1 failed");
        if(Solution.isAthletePopular(2))
            System.out.println("2.2 failed");
        if(Solution.getIncomeFromSport(1) != 100)
            System.out.println("2.3 failed");
        if(Solution.confirmStanding(12,1,1) != ReturnValue.OK)
            System.out.println("2.4 failed");
        if(Solution.confirmStanding(1,2,1) != ReturnValue.NOT_EXISTS)
            System.out.println("2.5 failed");
        if(Solution.getTotalNumberOfMedalsFromCountry("israel") != 1)
            System.out.println("2.6 failed");
        if(!Solution.getBestCountry().contains("israel"))
            System.out.println("2.7 failed");
        if(Solution.athleteDisqualified(1,2)!= ReturnValue.OK)
            System.out.println("2.8 failed");
        if(!Solution.getBestCountry().contains("israel"))
            System.out.println("2.9 failed");
        if(Solution.athleteDisqualified(12,1)!= ReturnValue.OK)
            System.out.println("2.10 failed");
        if(!Solution.getBestCountry().equals(""))
            System.out.println("2.11 failed");
        if(Solution.confirmStanding(12,1,1) != ReturnValue.OK)   // disqualified athletes can get medals
            System.out.println("2.12 failed");
        Solution.athleteJoinSport(12,3);
        Solution.athleteJoinSport(2,3);
        Solution.confirmStanding(12,3,1);
        Solution.confirmStanding(2,3,2);
        if(Solution.getTotalNumberOfMedalsFromCountry("canada") != 1)
            System.out.println("2.13 failed");
        if(!Solution.getBestCountry().contains("canada"))
            System.out.println("2.14 failed");
        if(!Solution.getMostPopularCity().contains("tel-aviv"))
            System.out.println("2.15 failed");
        if(Solution.deleteAthlete(Solution.getAthleteProfile(1)) != ReturnValue.OK)
            System.out.println("2.16 failed");
        if(Solution.athleteLeftSport(12,1) != ReturnValue.NOT_EXISTS) // should have been deleted on previous test
            System.out.println("2.17 failed");
        Solution.athleteLeftSport(12,3);
        Solution.athleteLeftSport(2,3);
        Solution.athleteLeftSport(1,2);
        if(!Solution.getBestCountry().equals(""))
            System.out.println("2.16 failed");
        if(!Solution.getMostPopularCity().equals("tel-aviv"))
            System.out.println("2.17 failed");
        Solution.deleteAthlete(Solution.getAthleteProfile(2));
        Solution.deleteAthlete(Solution.getAthleteProfile(3));
        if(!Solution.getBestCountry().isEmpty())
            System.out.println("2.18 failed");
        if(!Solution.getMostPopularCity().equals("tel-aviv"))
            System.out.println("2.19 failed");
        Solution.deleteSport(Solution.getSport(12));
        Solution.deleteSport(Solution.getSport(1));
        Solution.deleteSport(Solution.getSport(3));
        if(!Solution.getMostPopularCity().isEmpty())   // no cities in DB
            System.out.println("2.19.1 failed");
        a.setId(1);
        a.setName("A");
        a.setCountry("A");
        a.setIsActive(true);
        Solution.addAthlete(a);
        a.setId(2);
        Solution.addAthlete(a);
        s.setId(11);
        s.setName("S");
        s.setCity("SA");
        Solution.addSport(s);
        s.setId(22);
        s.setName("S");
        s.setCity("SB");
        Solution.addSport(s);
        Solution.athleteJoinSport(11,1);
        Solution.athleteJoinSport(11,2);
        Solution.athleteJoinSport(22,1);
        Solution.athleteJoinSport(22,2);
        if(!Solution.getMostPopularCity().contains("SB"))   // same AVG for SA and SB. should choose by LEX sort
            System.out.println("2.20 failed");
    }
    public static void Test_3_getAthleteMedals() {
        Solution.clearTables();
        Sport s = new Sport();
        s.setId(11);
        s.setName("tennis");
        s.setCity("TLV");
        Solution.addSport(s);
        s.setId(22);
        s.setName("golf");
        s.setCity("paris");
        Solution.addSport(s);
        Athlete atl=new Athlete();
        atl.setId(1);
        atl.setIsActive(true);
        atl.setName("name");
        atl.setCountry("country");
        Solution.addAthlete(atl);
        Solution.athleteJoinSport(11,1);
        Solution.athleteJoinSport(22,1);
        Solution.confirmStanding(11,1,1);
        Solution.confirmStanding(22,1,1);
        ArrayList<Integer> expected = new ArrayList<Integer>(Arrays.asList(2,0,0));
        if(!expected.equals(Solution.getAthleteMedals(1)))
            System.out.println("3.1 failed");
        expected.clear();
        s.setId(33);
        Solution.addSport(s);
        Solution.athleteJoinSport(33,1);
        Solution.confirmStanding(33,1,2);
        expected.addAll(Arrays.asList(2,1,0));
        if(!expected.equals(Solution.getAthleteMedals(1)))
            System.out.println("3.2 failed");
        Solution.athleteDisqualified(11,1);
        expected.clear();
        expected.addAll(Arrays.asList(1,1,0));
        if(!expected.equals(Solution.getAthleteMedals(1)))
            System.out.println("3.3 failed");
        Solution.deleteAthlete(Solution.getAthleteProfile(1));
        expected.clear();
        expected.addAll(Arrays.asList(0,0,0));
        if(!expected.equals(Solution.getAthleteMedals(1)))
            System.out.println("3.4 failed");
    }
    public static void Test_4_getMostRatedAthletes() {
        Solution.clearTables();
        Sport s = new Sport();
        s.setId(11);
        s.setCity("haifa");
        s.setName("t");
        Solution.addSport(s);
        Athlete atl = new Athlete();
        atl.setIsActive(true);
        atl.setName("n");
        atl.setCountry("c");
        for (int i : IntStream.range(1, 5).toArray()) {  // 1-4
            atl.setId(i);
            Solution.addAthlete(atl);
            Solution.athleteJoinSport(11, i);
        }
        ArrayList<Integer> expected = new ArrayList<Integer>(IntStream.range(1, 5).boxed().collect(Collectors.toList()));
        if (!expected.equals(Solution.getMostRatedAthletes()))  // less than 10 elements
            System.out.println("4.1 failed");
        for (int i : IntStream.range(5, 12).toArray()) {  // 5-11
            atl.setId(i);
            Solution.addAthlete(atl);
            Solution.athleteJoinSport(11, i);
        }
        expected.clear();
        expected.addAll(IntStream.range(1, 11).boxed().collect(Collectors.toList()));
        if (!expected.equals(Solution.getMostRatedAthletes()))  // exactly 10 elements (but there're 11 that fit)
            System.out.println("4.1 failed");

        for (int i : IntStream.range(6, 12).toArray())  // 6-11
            Solution.confirmStanding(11, i, 1);
        expected.clear();
        expected.addAll(IntStream.range(6, 12).boxed().collect(Collectors.toList())); // add 6-11
        expected.addAll(IntStream.range(1, 5).boxed().collect(Collectors.toList())); // add 1-4
        if (!expected.equals(Solution.getMostRatedAthletes()))  // order has changed
            System.out.println("4.2 failed");
        for (int i : IntStream.range(1, 4).toArray()) { // 1-3
            Solution.confirmStanding(11, i, 1);
            Solution.confirmStanding(11, i, 2);
        }
        expected.clear();
        expected.addAll(IntStream.range(6, 12).boxed().collect(Collectors.toList())); // add 6-11
        expected.addAll(IntStream.range(1, 4).boxed().collect(Collectors.toList())); // add 1-3
        expected.add(4);
        if (!expected.equals(Solution.getMostRatedAthletes()))    // order has changed again
            System.out.println("4.3 failed");
    }
    public static void Test_5_getCloseAthletes() {
        Solution.clearTables();
        Sport s = new Sport();
        s.setCity("haifa");
        s.setName("t");
        for (int i : IntStream.range(11, 14).toArray()) {  // 11-13
            s.setId(i);
            Solution.addSport(s);
        }
        Athlete at = new Athlete();
        at.setName("a");
        at.setCountry("a");
        for (int i : IntStream.range(1, 13).toArray()) {  // 1-5 observers, 6-12 participants
            if(i==6)
                at.setIsActive(true);
            at.setId(i);
            Solution.addAthlete(at);
        }
        ArrayList<Integer> expected = new ArrayList<Integer>();
        expected.addAll(IntStream.range(2, 12).boxed().collect(Collectors.toList()));
        if(!expected.equals(Solution.getCloseAthletes(1)))  // no one joined any sport
            System.out.println("5.1 failed");
        Solution.athleteJoinSport(11,1);
        expected.clear();
        expected= new ArrayList<Integer>();
        if(!expected.equals(Solution.getCloseAthletes(1))) // cannot be close to himself
            System.out.println("5.2 failed");
        for (int i : IntStream.range(2,6).toArray())  // 2-5
            Solution.athleteJoinSport(11,i);
        expected.clear();
        expected.addAll(IntStream.range(2,6).boxed().collect(Collectors.toList()));
        if(!expected.equals(Solution.getCloseAthletes(1))) // 2-5 are 100% fit
            System.out.println("5.3 failed");
        Solution.athleteJoinSport(12,1);
        if(!expected.equals(Solution.getCloseAthletes(1))) // 2-5 are 50% fit
            System.out.println("5.4 failed");
        Solution.athleteJoinSport(13,1);
        expected.clear();
        if(!expected.equals(Solution.getCloseAthletes(1))) // 2-5 are only 33.3% fit (>50%)
            System.out.println("5.4 failed");
        Solution.athleteLeftSport(13,1);    // 1 is part of sportID in (11,12)
        for (int i : IntStream.range(6,13).toArray()) {
            Solution.athleteJoinSport(11, i);
            Solution.athleteJoinSport(12, i);
        }
        // now : 2-5 are 50% fit && 6-12 are 100% fit
        // notice expected list should be in ascending athleteID order
        expected.clear();
        expected.addAll(IntStream.range(2,6).boxed().collect(Collectors.toList())); // 2-5
        expected.addAll(IntStream.range(6,12).boxed().collect(Collectors.toList())); //6-11
        if(!expected.equals(Solution.getCloseAthletes(1)))
            System.out.println("5.5 failed");
        Solution.deleteAthlete(Solution.getAthleteProfile(1));
        expected.clear();
        if(!expected.equals(Solution.getCloseAthletes(1))) // athlete doesn't exist
            System.out.println("5.6 failed");
    }
    public static void Test_6_getSportsRecommendation() {
        Solution.clearTables();
        Sport s = new Sport();
        s.setCity("haifa");
        s.setName("t");
        for (int i : IntStream.range(11, 16).toArray()) {  // 11-15
            s.setId(i);
            Solution.addSport(s);
        }
        Athlete at = new Athlete();
        at.setName("a");
        at.setCountry("a");
        for (int i : IntStream.range(1, 13).toArray()) {  // 1-5 observers, 6-12 participants
            if(i==6)
                at.setIsActive(true);
            at.setId(i);
            Solution.addAthlete(at);
        }
        ArrayList<Integer> expected = new ArrayList<Integer>();
        if(!expected.equals(Solution.getSportsRecommendation(1)))  // no one joined any sport
            System.out.println("6.1 failed");
        Solution.athleteJoinSport(11,1);
        if(!expected.equals(Solution.getSportsRecommendation(1))) // cannot recommend to himself
            System.out.println("6.2 failed");
        for (int i : IntStream.range(2,6).toArray())  // now 2-5 are close to 1
            Solution.athleteJoinSport(11,i);
        if(!expected.equals(Solution.getSportsRecommendation(1))) // athleteID=1 already joined to "recommended" sports
            System.out.println("6.3 failed");
        Solution.athleteJoinSport(12,1);
        for (int i : IntStream.range(2,6).toArray()) {  // 2-5
            Solution.athleteJoinSport(12, i);
            Solution.athleteJoinSport(13, i);
            Solution.athleteJoinSport(14, i);
        }
        expected.clear();
        expected.addAll(Arrays.asList(13,14));
        if(!expected.equals(Solution.getSportsRecommendation(1)))  // 2-5 are 50% close to 1 && recommend 13,14
            System.out.println("6.4 failed");
        Solution.athleteLeftSport(11,1);
        Solution.athleteJoinSport(14,1);
        expected.clear();
        expected.addAll(Arrays.asList(11,13));
        if(!expected.equals(Solution.getSportsRecommendation(1)))   // 2-5 are 50% close to 1 && recommend 13,11
            System.out.println("6.5 failed");
        for (int i : IntStream.range(6,11).toArray()) {  // 6-10
            Solution.athleteJoinSport(12, i);
            Solution.athleteJoinSport(13, i);
            Solution.athleteJoinSport(14, i);
        }
        Solution.athleteJoinSport(14,1);
        expected.clear();
        expected.add(13);
        expected.add(11);
        if(!expected.equals(Solution.getSportsRecommendation(1)))   // only recommendation is 13
            System.out.println("6.6 failed");
        Solution.deleteAthlete(Solution.getAthleteProfile(1));
        expected.clear();
        if(!expected.equals(Solution.getSportsRecommendation(1))) // athleteID=1 doesn't exist
            System.out.println("6.7 failed");
    }
}









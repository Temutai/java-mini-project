package Core;

import Other.Main;
import java.util.*;

public class User {
    private String name, userId;
    private ArrayList<Event> schedule, pref1, pref2;
    Scanner scan = new Scanner(System.in);
    
    public User(String name) {
        this.name = name;
        userId = UUID.randomUUID().toString();
    }
    
    void checkCollision(Event e, int flag) {
        if(flag == 1) {
            return;
        }
        flag = 1;
        for(Event x:schedule) {
            if(x.getEndDate().compareTo(e.getStartDate())>=0) {
                flag = 0;
                if(x.getPriority() > 1) {
                    schedule.remove(x);
                    //WARN hope this doesn't create problems, as i'm traversing the array at x here
                }
                else {
                    x.showEvent();
                }
            }
        }
        if(flag != 1) {
            e.showEvent();
            System.out.println("These events clash. Which events would you like to remove?");
            char ch = 'y';
            while(ch == 'y') {
                System.out.print("Enter event id : ");
                String tempId = scan.next();
                for(Event a:schedule) {
                    if(a.getEventID().equals(tempId)) {
                        schedule.remove(a);
                    }
                }
                System.out.print("Remove another?(y/n) : ");
                ch = scan.next().charAt(0);
            }
        }
        Collections.sort(schedule);
        checkCollision(schedule.get(schedule.size() - 1), flag);
    }
    void setSchedule() {
        schedule = new ArrayList<>();
        ArrayList<Event> pref = new ArrayList<>();
        pref.addAll(pref1);
        pref.addAll(pref2);
        Collections.sort(pref);
        Event prev = null;
        //TODO prev is initialised to have start and end time as beginning of the day
        for(Event e:pref) {
            if(e.getStartDate().compareTo(prev.getEndDate()) > 0) {
                schedule.add(e);
            }
            else if(e.getPriority() == 1){
                checkCollision(e, 0);
            }
            prev = e;
        } 
    }
    void setPref1() {
        pref1 = new ArrayList<>();
        System.out.println("Event List");
        for(Event e:Main.events) {
            e.showEvent();
        }
        char ch = 'y';
        while(ch == 'y') {
            System.out.print("Enter ID of the event you wish to attend : ");
            String addId = scan.next();
            //adding is a pain(time complexity wise, O(n) v/s O(1) ). think of a better way to access them. either hash to add, or make id as index
            for(Event a:Main.events) {
                if(a.getEventID().equals(addId)) {
                    pref1.add(a);
                }
            }
            System.out.print("Add another?(y/n) : ");
            ch = scan.next().charAt(0);
        }
    }
    void setPref2() {
        pref2 = new ArrayList<>();
        //TODO populate pref2
    }
}

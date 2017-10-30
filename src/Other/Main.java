package Other;

import Core.*;
import java.util.*;

/**
 *
 * @author temutai
 */

public class Main {
    public static ArrayList<Event> events = new ArrayList<>();
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        
        String admin = "TechFest", password = "TechFest@12345";
        int admin_user,eventChoice = 10,create;
        
        System.out.print("1.Admin\n2.Attendee\nEnter your choice: ");
        admin_user = scan.nextInt();
        
        if(admin_user == 1){
            System.out.print("1.View Events\n2.Create Event\n3.Logout\nEnter your choice: ");
            eventChoice = scan.nextInt();
            while(eventChoice!=3){
                switch(eventChoice){
                    case 1:viewEvents();
                           break;
                    case 2:System.out.print("1.Create Hackathon\n2.Create Concert\n3.Create Workshop\nEnter your choice: ");
                    // add create generic event
                           create = scan.nextInt();
                           if(create == 1)
                               createHackathon();
                           else if(create == 2)
                               createConcert();
                           else if(create == 3)
                               createWorkshop();
                           break;
                    case 3:break;
                    default:System.out.println("Wrong choice!Please choose again.");
                            break;
                }
                System.out.print("1.View Events\n2.Create Event\n3.Logout\nEnter your choice: ");
                eventChoice = scan.nextInt();
            }
        }
        else if(admin_user == 2){
            
        }
        else
            System.out.println("Wrong choice!");
    }
    
    static void createHackathon(){
        Hackathon hackathon = new Hackathon();
        hackathon.getDetails();
        hackathon.additional();
    }
    
    static void createConcert(){
        Concert concert = new Concert();
        concert.getDetails();
        concert.additional();
    }
    
    static void createWorkshop(){
        Workshop workshop = new Workshop();
        workshop.getDetails();
        workshop.additional();
    }
    
    static void viewEvents(){
    	// fetch the events from firebase and display it

		try {

            URL url = new URL("https://java-final-proj.firebaseio.com/fests/Breeze/Events.json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            Gson sd = new Gson();

            System.out.println(conn.toString());

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            StringBuilder sb = new StringBuilder();
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                sb.append(output);
            }
            JsonObject jObj = new JsonObject();
            jObj.getAsJsonObject(sb.toString());

            if (jObj.isJsonObject()) {
                Set<Map.Entry<String, JsonElement>> ens = ((JsonObject) jObj).entrySet();
                if (ens != null) {
                    // Iterate JSON Elements with Key values
                    for (Map.Entry<String, JsonElement> en : ens) {
                        JsonObject child = new JsonObject();
                        child = en.getValue().getAsJsonObject();
                        events.add(new Core.Event(child.get("start").getAsString(), child.get("end").getAsString()));
                    }
                }
            }

            for(Event e: events){
            	e.printDates();
            }

    }
}

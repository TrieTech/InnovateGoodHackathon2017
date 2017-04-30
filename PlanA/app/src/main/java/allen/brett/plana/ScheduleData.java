import java.util.Calender;

public class ScheduleData extends Activity {

    private String [] keywords = {"Date","Due", "Test", "Exam", "Professor", "Office Hours"};
    private int numDay;
    private int tempStart;
    String input;

    ScheduleData(){
        numDay = 0;
        tempStart = 0;
    }

    locationManager = (LocationManager)
        getSystemService(LOCATION_SERVICE);

    locationlistener = new LocationListener(){
        @Override
        public void onLocationChanged(Location location){}
    }



    //String dt = df.dateformat(cal);

    public static boolean isKeyword(String input){
         String [] split = input.split("\\s+");
        for (String s : keywords){
            if(s == input){
                return true;
            }
        }
        return false;
    }

   // public void openFileChooser(ValueCallback<url>)

    String dt = df.dateformat(cal);
    SimpleDateFormat df = SimpleDateFormat("yyyy/mm/dd");
    Calender cal = Calender.getInstance();
    System.out.println(df.dateformat(cal));


    cal.setTime(df.parse(dt));
    cal.add(Calender.DATE, 1);

    dt = sdf.format(cal.getTime());


    @override
    public void setNumDay(int numDay){
        this.numDay = numDay;
    }

    @override
    public int getNumDay(){
        return numDay;
    }

    public String getDate(String input){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM d yyyy");
        LocalDate date = input.parse(input, formatter);
    }

    Event event = new Event().setSummary("Google I/O 2015").setLocation("800 Howard St., San Francisco, CA 94103").setDescription("A chance to hear more about Google's developer products.");

    DateTime startDateTime = new DateTime("2015-05-28T09:00:00-07:00");
    EventDateTime start = new EventDateTime().setDateTime(startDateTime).setTimeZone("America/Los_Angeles");
    event.setStart(start);

    DateTime endDateTime = new DateTime("2015-05-28T17:00:00-07:00");
    EventDateTime end = new EventDateTime().setDateTime(endDateTime).setTimeZone("America/Los_Angeles");
    event.setEnd(end);

    String[] recurrence = new String[] {"RRULE:FREQ=DAILY;COUNT=2"};
    event.setRecurrence(Arrays.asList(recurrence));

    EventAttendee[] attendees = new EventAttendee[] {
    new EventAttendee().setEmail("lpage@example.com"),
    new EventAttendee().setEmail("sbrin@example.com"),};
    event.setAttendees(Arrays.asList(attendees));
}

    EventReminder[] reminderOverrides = new EventReminder[] {
    new EventReminder().setMethod("email").setMinutes(24 * 60),
    new EventReminder().setMethod("popup").setMinutes(10),};

    Event.Reminders reminders = new Event.Reminders().setUseDefault(false).setOverrides(Arrays.asList(reminderOverrides));
    event.setReminders(reminders);

    String calID = "primary";
    event = service.events().insert(calID, event).execute();
    System.out.printlf("Event Established: %s\n ", event.getHtmlLink());

public static void addAttachment(Calendar calendarService, Drive driveService, String calendarId,
        String eventId, String fileId) throws IOException {
        File file = driveService.files().get(fileId).execute();
        Event event = calendarService.events().get(calendarId, eventId).execute();

        List<EventAttachment> attachments = event.getAttachments();
        if (attachments == null) {
        attachments = new ArrayList<EventAttachment>();
        }
        attachments.add(new EventAttachment().setFileUrl(file.getAlternateLink()).setMimeType(file.getMimeType()).setTitle(file.getTitle()));

        Event changes = new Event().setAttachments(attachments);
        calendarService.events().patch(calendarId, eventId, changes).setSupportsAttachments(true).execute();
        }


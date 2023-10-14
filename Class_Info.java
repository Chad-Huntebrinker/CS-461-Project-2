public class Class_Info {
    private String room;
    private String time;
    private String instructor;
    private String course;
    private String room_capacity;
    private double fitness_score = -1;

    public String get_room() {return room;}
    public String get_time() {return time;}
    public String get_instructor() {return instructor;}
    public String get_course() {return course;}
    public String get_room_capacity() {return room_capacity;}
    public double get_fitness_score() {return fitness_score;}
    public void set_room(String user_input) {room = user_input;}
    public void set_time(String user_input) {time = user_input;}
    public void set_instructor(String user_input) {instructor = user_input;}
    public void set_course(String user_input) {course = user_input;}
    public void set_room_capacity(String user_input) {room_capacity = user_input;}
    public void set_fitness_score(double user_input) {fitness_score = user_input;}
}

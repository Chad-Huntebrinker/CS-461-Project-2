//Chad Huntebrinker
//CS 461
//Project 2

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;
import java.util.Random;

public class Program2 {
    public static void main(String[] args) throws IOException {

        Vector<String> class_list = new Vector<String>();
        Vector<String> class_enrollment_size = new Vector<String>();
        Vector<String> instructor_list = new Vector<String>();
        Vector<String> room_list = new Vector<String>();
        Vector<String> room_capacity_list = new Vector<String>();
        Vector<String> class_time_list = new Vector<String>();
        Vector<Vector<Class_Info>> schedule_population = new Vector<Vector<Class_Info>>();
        Vector<Double> average_fitness = new Vector<Double>();
        Vector<Vector<Double>> checker_upper = new Vector<Vector<Double>>();
        Random rand = new Random();
        boolean exit = false;

        //Fill up the vectors with the needed information.
        class_list.add("CS101A");
        class_list.add("CS101B");
        class_list.add("CS191A");
        class_list.add("CS191B");
        class_list.add("CS201");
        class_list.add("CS291");
        class_list.add("CS303");
        class_list.add("CS304");
        class_list.add("CS394");
        class_list.add("CS449");
        class_list.add("CS451");
        class_enrollment_size.add("50");
        class_enrollment_size.add("50");
        class_enrollment_size.add("50");
        class_enrollment_size.add("50");
        class_enrollment_size.add("50");
        class_enrollment_size.add("50");
        class_enrollment_size.add("60");
        class_enrollment_size.add("25");
        class_enrollment_size.add("20");
        class_enrollment_size.add("60");
        class_enrollment_size.add("100");
        instructor_list.add("Gharibi");
        instructor_list.add("Gladbach");
        instructor_list.add("Hare");
        instructor_list.add("Nait-Abdesselam");
        instructor_list.add("Shah");
        instructor_list.add("Song");
        instructor_list.add("Uddin");
        instructor_list.add("Xu");
        instructor_list.add("Zaman");
        instructor_list.add("Zein el Din");
        room_list.add("Katz 003");
        room_list.add("FH 216");
        room_list.add("Royall 206");
        room_list.add("Royall 201");
        room_list.add("FH 310");
        room_list.add("Haag 201");
        room_list.add("Haag 301");
        room_list.add("MNLC 325");
        room_list.add("Bloch 119");
        room_capacity_list.add("45");
        room_capacity_list.add("30");
        room_capacity_list.add("75");
        room_capacity_list.add("50");
        room_capacity_list.add("108");
        room_capacity_list.add("60");
        room_capacity_list.add("75");
        room_capacity_list.add("450");
        room_capacity_list.add("60");
        class_time_list.add("10 AM");
        class_time_list.add("11 AM");
        class_time_list.add("12 PM");
        class_time_list.add("1 PM");
        class_time_list.add("2 PM");
        class_time_list.add("3 PM");

        //Randomly assign values to our schedules and create the first population.
        for (int j = 0; j < 500; ++j) {
            Vector<Class_Info> class_schedule = new Vector<Class_Info>();
            for(int class_index = 0; class_index < class_list.size(); ++class_index) {
                int random_num2 = rand.nextInt(instructor_list.size());
                int random_num3 = rand.nextInt(room_list.size());
                int random_num4 = rand.nextInt(class_time_list.size());
                Class_Info temp = new Class_Info();
                temp.set_course(class_list.get(class_index));
                temp.set_instructor(instructor_list.get(random_num2));
                temp.set_room(room_list.get(random_num3));
                temp.set_room_capacity(room_capacity_list.get(random_num3));
                temp.set_time(class_time_list.get(random_num4));
                class_schedule.add(temp);
            }
            schedule_population.add(class_schedule);
        }

        int i = -1;
        //i will keep track of how many generations we have done.
        //We now start the process of our genetic algorithm.
        do {
            ++i;
            Vector<Vector<Class_Info>> schedule_population2 = new Vector<Vector<Class_Info>>();
            Vector<Double> checker = new Vector<Double>();
            double total = 0;
            double temp_fitness = 0;

            //This loop finds the fitness score and starts finding the softmax distribution.
            for (int j = 0; j < schedule_population.size(); ++j) {
                //Find the fitness score (we'll call it y).
                schedule_population.get(j).get(0).set_fitness_score(fitness_function(schedule_population.get(j), class_time_list, room_list, room_capacity_list, class_enrollment_size, instructor_list));
                checker.add(schedule_population.get(j).get(0).get_fitness_score());
                temp_fitness = temp_fitness + schedule_population.get(j).get(0).get_fitness_score();
                //Find e^y
                schedule_population.get(j).get(0).set_fitness_score(Math.exp(schedule_population.get(j).get(0).get_fitness_score()));

                //Calculate the total (or sum) for later use.
                total = total + schedule_population.get(j).get(0).get_fitness_score();
            }
            checker_upper.add(checker);

            //Now, we'll do y / Sum
            for (int j = 0; j < schedule_population.size(); ++j) {
                schedule_population.get(j).get(0).set_fitness_score(schedule_population.get(j).get(0).get_fitness_score() / total);
            }
            average_fitness.add(temp_fitness / 500);

            //Bubble sort the population vector.
            for (int l = 0; l < schedule_population.size() - 1; l++) {
                for (int k = 0; k < schedule_population.size() - l - 1; k++) {
                    if (schedule_population.get(k).get(0).get_fitness_score() > schedule_population.get(k + 1).get(0).get_fitness_score()) {
                        Vector<Class_Info> temp1 = new Vector<Class_Info>();
                        Vector<Class_Info> temp2 = new Vector<Class_Info>();
                        for (int m = 0; m < schedule_population.get(k).size(); ++m) {
                            Class_Info temp = new Class_Info();
                            temp = schedule_population.get(k).get(m);
                            temp1.add(temp);
                        }
                        for (int m = 0; m < schedule_population.get(k + 1).size(); ++m) {
                            Class_Info temp = new Class_Info();
                            temp = schedule_population.get(k + 1).get(m);
                            schedule_population.get(k + 1).set(m, temp1.get(m));
                            temp2.add(temp);
                        }
                        for (int m = 0; m < schedule_population.get(k).size(); ++m) {
                            schedule_population.get(k).set(m, temp2.get(m));
                        }

                    }
                }
            }

            //Finish the softmax distribution by adding the previous entries to the current one.
            //Example: new_entry3 = entry1 + entry2 + entry3.
            //That way, we'll have a starting value that is extremly small and the last value will be 1 or close to 1.
            for (int j = schedule_population.size() - 1; j > 0; --j) {
                double total_score = 0;
                for (int k = j; k > 0; --k) {
                    total_score = total_score + schedule_population.get(k).get(0).get_fitness_score();
                }
                schedule_population.get(j).get(0).set_fitness_score(total_score);
            }

            //Create the next generation.
            //Find two random values between 0 and 1.  These values will determine what the parents will be.
            //Also, find a random index that will determine where our crossover for the vector will be.
            for (int j = 0; j < 250; ++j) {
                double random_value1 = rand.nextDouble(0, 1);
                double random_value2 = rand.nextDouble(0 , 1);
                int index1 = 0;
                int index2 = 0;

                //Find the two parents.
                for (int k = 0; k < schedule_population.size(); ++k) {
                    if (schedule_population.get(k).get(0).get_fitness_score() >= random_value1 && index1 == 0) {
                        index1 = k;
                    }
                    if (schedule_population.get(k).get(0).get_fitness_score() >= random_value2 && index2 == 0) {
                        index2 = k;
                    }
                    if (index1 != 0 && index2 != 0) {
                        break;
                    }
                }

                //Find the crossover index.
                int random_index1 = rand.nextInt(schedule_population.get(0).size());
                Vector<Class_Info> temp1 = new Vector<Class_Info>();
                Vector<Class_Info> temp2 = new Vector<Class_Info>();

                //Note, for our mutation rate, we keep it at a constant rate throughout the generations.
                //Then, once the program has stopped running and we have noted the fitness score and course schedule,
                //we half the mutation rate and re-run the program.  We keep doing this until the fitness score remains
                //stable.
                int mutation_rate = 800;

                //Start the crossover process.  For this, we will be creating and keeping both children.
                //So, we'll create child1 which consists of the first part of parent A and the second part of parent B
                //And child2 will consist of the first part of parent B and the second part of parent A.
                //This first for loop will fill up with the first parts of the parents.
                for (int m = 0; m < random_index1; ++m) {
                    Class_Info temp_value1 = new Class_Info();
                    Class_Info temp_value2 = new Class_Info();
                    String room = schedule_population.get(index1).get(m).get_room();
                    String time = schedule_population.get(index1).get(m).get_time();
                    String instructor = schedule_population.get(index1).get(m).get_instructor();
                    String course = schedule_population.get(index1).get(m).get_course();
                    String room_capacity = schedule_population.get(index1).get(m).get_room_capacity();
                    double fitness_score = schedule_population.get(index1).get(m).get_fitness_score();

                    //temp_value1 will be one child.
                    temp_value1.set_room(room);
                    temp_value1.set_time(time);
                    temp_value1.set_instructor(instructor);
                    temp_value1.set_course(course);
                    temp_value1.set_room_capacity(room_capacity);
                    temp_value1.set_fitness_score(fitness_score);

                    //Mutation generator
                    int temp = rand.nextInt(mutation_rate);

                    //If the course is selected for mutation, then keep the course but randomly assign
                    //the instructor, time, and room (the room capacity will be correlated to the room).
                    if (temp == 1) {
                        int random_num2 = rand.nextInt(instructor_list.size());
                        int random_num3 = rand.nextInt(room_list.size());
                        int random_num4 = rand.nextInt(class_time_list.size());
                        temp_value1.set_course(course);
                        temp_value1.set_instructor(instructor_list.get(random_num2));
                        temp_value1.set_room(room_list.get(random_num3));
                        temp_value1.set_room_capacity(room_capacity_list.get(random_num3));
                        temp_value1.set_time(class_time_list.get(random_num4));
                    }

                    room = schedule_population.get(index2).get(m).get_room();
                    time = schedule_population.get(index2).get(m).get_time();
                    instructor = schedule_population.get(index2).get(m).get_instructor();
                    course = schedule_population.get(index2).get(m).get_course();
                    room_capacity = schedule_population.get(index2).get(m).get_room_capacity();
                    fitness_score = schedule_population.get(index2).get(m).get_fitness_score();

                    //temp_value2 will be the other child.
                    temp_value2.set_room(room);
                    temp_value2.set_time(time);
                    temp_value2.set_instructor(instructor);
                    temp_value2.set_course(course);
                    temp_value2.set_room_capacity(room_capacity);
                    temp_value2.set_fitness_score(fitness_score);

                    //Mutation generator
                    temp = rand.nextInt(mutation_rate);

                    //If the course is selected for mutation, then keep the course but randomly assign
                    //the instructor, time, and room (the room capacity will be correlated to the room).
                    if (temp == 1) {
                        int random_num2 = rand.nextInt(instructor_list.size());
                        int random_num3 = rand.nextInt(room_list.size());
                        int random_num4 = rand.nextInt(class_time_list.size());
                        temp_value2.set_course(course);
                        temp_value2.set_instructor(instructor_list.get(random_num2));
                        temp_value2.set_room(room_list.get(random_num3));
                        temp_value2.set_room_capacity(room_capacity_list.get(random_num3));
                        temp_value2.set_time(class_time_list.get(random_num4));
                    }

                    temp1.add(temp_value1);
                    temp2.add(temp_value2);
                }

                //This for loop will fill up the second part of the parents.
                for (int m = random_index1; m < schedule_population.get(index2).size(); ++m) {
                    Class_Info temp_value1 = new Class_Info();
                    Class_Info temp_value2 = new Class_Info();
                    String room = schedule_population.get(index1).get(m).get_room();
                    String time = schedule_population.get(index1).get(m).get_time();
                    String instructor = schedule_population.get(index1).get(m).get_instructor();
                    String course = schedule_population.get(index1).get(m).get_course();
                    String room_capacity = schedule_population.get(index1).get(m).get_room_capacity();
                    double fitness_score = schedule_population.get(index1).get(m).get_fitness_score();

                    temp_value1.set_room(room);
                    temp_value1.set_time(time);
                    temp_value1.set_instructor(instructor);
                    temp_value1.set_course(course);
                    temp_value1.set_room_capacity(room_capacity);
                    temp_value1.set_fitness_score(fitness_score);

                    //Mutation generator
                    int temp = rand.nextInt(mutation_rate);

                    if (temp == 1) {
                        int random_num2 = rand.nextInt(instructor_list.size());
                        int random_num3 = rand.nextInt(room_list.size());
                        int random_num4 = rand.nextInt(class_time_list.size());
                        temp_value1.set_course(course);
                        temp_value1.set_instructor(instructor_list.get(random_num2));
                        temp_value1.set_room(room_list.get(random_num3));
                        temp_value1.set_room_capacity(room_capacity_list.get(random_num3));
                        temp_value1.set_time(class_time_list.get(random_num4));
                    }

                    room = schedule_population.get(index2).get(m).get_room();
                    time = schedule_population.get(index2).get(m).get_time();
                    instructor = schedule_population.get(index2).get(m).get_instructor();
                    course = schedule_population.get(index2).get(m).get_course();
                    room_capacity = schedule_population.get(index2).get(m).get_room_capacity();
                    fitness_score = schedule_population.get(index2).get(m).get_fitness_score();

                    temp_value2.set_room(room);
                    temp_value2.set_time(time);
                    temp_value2.set_instructor(instructor);
                    temp_value2.set_course(course);
                    temp_value2.set_room_capacity(room_capacity);
                    temp_value2.set_fitness_score(fitness_score);

                    //Mutation generator
                    temp = rand.nextInt(mutation_rate);

                    if (temp == 1) {
                        int random_num2 = rand.nextInt(instructor_list.size());
                        int random_num3 = rand.nextInt(room_list.size());
                        int random_num4 = rand.nextInt(class_time_list.size());
                        temp_value2.set_course(course);
                        temp_value2.set_instructor(instructor_list.get(random_num2));
                        temp_value2.set_room(room_list.get(random_num3));
                        temp_value2.set_room_capacity(room_capacity_list.get(random_num3));
                        temp_value2.set_time(class_time_list.get(random_num4));
                    }

                    temp1.add(temp_value1);
                    temp2.add(temp_value2);
                }
                schedule_population2.add(temp1);
                schedule_population2.add(temp2);

            }

            //If it's been over 100 generations and for generation
            //G is less than 1% improvement over generation G – 100.
            if(i >= 100) {
                if(average_fitness.get(i) < average_fitness.get(i - 100) + (0.01 * average_fitness.get(i - 100))) {
                    exit = true;
                }
            }
            
            //If we aren't going to be exiting out of the loop and will be creating a new generation.
            if (exit == false) {
                schedule_population = new Vector<Vector<Class_Info>>();
                for (int j = 0; j < schedule_population2.size(); ++j) {
                    Vector<Class_Info> temp = new Vector<Class_Info>();
                    for (int k = 0; k < schedule_population2.get(0).size(); ++k) {
                        Class_Info temp2 = new Class_Info();

                        String room = schedule_population2.get(j).get(k).get_room();
                        String time = schedule_population2.get(j).get(k).get_time();
                        String instructor = schedule_population2.get(j).get(k).get_instructor();
                        String course = schedule_population2.get(j).get(k).get_course();
                        String room_capacity = schedule_population2.get(j).get(k).get_room_capacity();
                        double fitness_score = schedule_population2.get(j).get(k).get_fitness_score();

                        temp2.set_room(room);
                        temp2.set_time(time);
                        temp2.set_instructor(instructor);
                        temp2.set_course(course);
                        temp2.set_room_capacity(room_capacity);
                        temp2.set_fitness_score(fitness_score);
                        temp.add(temp2);
                    }
                    schedule_population.add(temp);
                }
            }
        } while (exit == false);

        PrintWriter out = new PrintWriter("output4.txt");

        double max = 0;
        for(int j = 0; j < checker_upper.get(checker_upper.size() - 1).size(); ++j) {
            max = checker_upper.get(checker_upper.size() - 1).get(j);
            if(max <  checker_upper.get(checker_upper.size() - 1).get(j)) {
                max = checker_upper.get(checker_upper.size() - 1).get(j);
            }
        }
        int index1 = schedule_population.size() - 1;
        out.println("Mutation Rate: 0.125% \n" );
        out.println("Fitness score: " + max + "\n");
        out.println("Course Schedule: \n");
        for(int j = 0; j < schedule_population.get(schedule_population.size() - 1).size(); ++j) {
            out.println(schedule_population.get(index1).get(j).get_course());
            out.println(schedule_population.get(index1).get(j).get_instructor());
            out.println(schedule_population.get(index1).get(j).get_time());
            out.println(schedule_population.get(index1).get(j).get_room());
            out.println("\n ---------------------------- \n");
        }
        out.close();
    }

    public static double fitness_function(Vector<Class_Info> class_input, Vector<String> class_time_list, Vector<String> room_list,
                                               Vector<String> room_capacity_list, Vector<String> class_enrollment_size, Vector<String> instructor_list){
        Vector<String> temp_room = new Vector<String>();
        double fitness_score = 0;

        //Check to see if a class is scheduled at the same time in the same room as another class.
        for(int i = 0; i < class_time_list.size(); ++i) {
            for (int j = 0; j < class_input.size(); ++j) {
                if(class_input.get(j).get_time() == class_time_list.get(i)) {
                    if(temp_room.isEmpty()) {
                        temp_room.add(class_input.get(j).get_room());
                    }
                    else {
                        for(int k = 0; k < temp_room.size(); ++k) {

                            //Checks if one room is scheduled for two classes at the same time.
                            if(class_input.get(j).get_room() == temp_room.get(k)) {
                                fitness_score = fitness_score - 0.5;
                            }
                            if(k == temp_room.size() - 1) {
                                temp_room.add(class_input.get(j).get_room());
                                break;
                            }
                        }
                    }
                }
            }
            //We finished looking through all the classes at that time, so clear the vector.
            temp_room.clear();
        }

        //Check to see if the room capacity is okay
        for(int i = 0; i < class_input.size(); ++i) {
            for(int j = 0; j < room_list.size(); ++j) {
                if(class_input.get(i).get_room() == room_list.get(j)) {

                    //If class enrollment is bigger than the room.
                    if(Integer.parseInt(class_enrollment_size.get(i)) > Integer.parseInt(room_capacity_list.get(j))) {
                        fitness_score = fitness_score - 0.5;
                        break;
                    }

                    //If room is bigger than 6 times the class enrollment.
                    else if(Integer.parseInt(class_enrollment_size.get(i)) * 6 < Integer.parseInt(room_capacity_list.get(j))) {
                        fitness_score = fitness_score - 0.4;
                        break;
                    }

                    //If room is bigger than 3 times the class enrollment.
                    else if(Integer.parseInt(class_enrollment_size.get(i)) * 3 < Integer.parseInt(room_capacity_list.get(j))) {
                        fitness_score = fitness_score - 0.2;
                        break;
                    }
                    else {
                        fitness_score = fitness_score + 0.3;
                        break;
                    }
                }
            }
        }

        //Check if class is taught by preferred, listed, or other instructor.
        for(int i = 0; i < class_input.size(); ++i) {

            //Since CS101 and CS191 have the same preferred and other instructors, simplify it into one if statement.
            if(class_input.get(i).get_course().equals("CS101A") || class_input.get(i).get_course().equals("CS101B")
                || class_input.get(i).get_course().equals("CS191A") || class_input.get(i).get_course().equals("CS191B")) {

                //Preferred faculty members.
                if(class_input.get(i).get_instructor().equals("Gladbach") || class_input.get(i).get_instructor().equals("Gharibi")
                    || class_input.get(i).get_instructor().equals("Hare") || class_input.get(i).get_instructor().equals("Zein el Din")) {
                    fitness_score = fitness_score + 0.5;
                }

                //Listed faculty members.
                else if(class_input.get(i).get_instructor().equals("Zaman") || class_input.get(i).get_instructor().equals("Nait-Abdesselam")) {
                    fitness_score = fitness_score + 0.2;
                }
                else {
                    fitness_score = fitness_score - 0.1;
                }
            }
            else if(class_input.get(i).get_course().equals("CS201")) {

                //Preferred faculty members.
                if(class_input.get(i).get_instructor().equals("Gladbach") || class_input.get(i).get_instructor().equals("Shah")
                        || class_input.get(i).get_instructor().equals("Hare") || class_input.get(i).get_instructor().equals("Zein el Din")) {
                    fitness_score = fitness_score + 0.5;
                }

                //Listed faculty members.
                else if(class_input.get(i).get_instructor().equals("Zaman") || class_input.get(i).get_instructor().equals("Nait-Abdesselam")
                        || class_input.get(i).get_instructor().equals("Song")) {
                    fitness_score = fitness_score + 0.2;
                }
                else {
                    fitness_score = fitness_score - 0.1;
                }
            }
            else if(class_input.get(i).get_course().equals("CS291")) {

                //Preferred faculty members.
                if(class_input.get(i).get_instructor().equals("Song") || class_input.get(i).get_instructor().equals("Gharibi")
                        || class_input.get(i).get_instructor().equals("Hare") || class_input.get(i).get_instructor().equals("Zein el Din")) {
                    fitness_score = fitness_score + 0.5;
                }

                //Listed faculty members.
                else if(class_input.get(i).get_instructor().equals("Zaman") || class_input.get(i).get_instructor().equals("Nait-Abdesselam")
                        || class_input.get(i).get_instructor().equals("Shah") || class_input.get(i).get_instructor().equals("Xu")) {
                    fitness_score = fitness_score + 0.2;
                }
                else {
                    fitness_score = fitness_score - 0.1;
                }
            }
            else if(class_input.get(i).get_course().equals("CS303")) {

                //Preferred faculty members.
                if(class_input.get(i).get_instructor().equals("Gladbach") || class_input.get(i).get_instructor().equals("Hare")
                        || class_input.get(i).get_instructor().equals("Zein el Din")) {
                    fitness_score = fitness_score + 0.5;
                }

                //Listed faculty members.
                else if(class_input.get(i).get_instructor().equals("Zaman") || class_input.get(i).get_instructor().equals("Shah")
                        || class_input.get(i).get_instructor().equals("Song")) {
                    fitness_score = fitness_score + 0.2;
                }
                else {
                    fitness_score = fitness_score - 0.1;
                }
            }
            else if(class_input.get(i).get_course().equals("CS304")) {

                //Preferred faculty members.
                if(class_input.get(i).get_instructor().equals("Gladbach") || class_input.get(i).get_instructor().equals("Hare")
                        || class_input.get(i).get_instructor().equals("Xu")) {
                    fitness_score = fitness_score + 0.5;
                }

                //Listed faculty members.
                else if(class_input.get(i).get_instructor().equals("Zaman") || class_input.get(i).get_instructor().equals("Shah")
                        || class_input.get(i).get_instructor().equals("Song") || class_input.get(i).get_instructor().equals("Nait-Abdesselam")
                        || class_input.get(i).get_instructor().equals("Uddin") || class_input.get(i).get_instructor().equals("Zein el Din")) {
                    fitness_score = fitness_score + 0.2;
                }
                else {
                    fitness_score = fitness_score - 0.1;
                }
            }
            else if(class_input.get(i).get_course().equals("CS394")) {

                //Preferred faculty members.
                if(class_input.get(i).get_instructor().equals("Song") || class_input.get(i).get_instructor().equals("Xu")) {
                    fitness_score = fitness_score + 0.5;
                }

                //Listed faculty members.
                else if(class_input.get(i).get_instructor().equals("Nait-Abdesselam")
                        || class_input.get(i).get_instructor().equals("Zein el Din")) {
                    fitness_score = fitness_score + 0.2;
                }
                else {
                    fitness_score = fitness_score - 0.1;
                }
            }
            else if(class_input.get(i).get_course().equals("CS449")) {

                //Preferred faculty members.
                if(class_input.get(i).get_instructor().equals("Xu") || class_input.get(i).get_instructor().equals("Shah")
                        || class_input.get(i).get_instructor().equals("Song")) {
                    fitness_score = fitness_score + 0.5;
                }
                //Listed faculty members.
                else if(class_input.get(i).get_instructor().equals("Uddin") || class_input.get(i).get_instructor().equals("Zein el Din")) {
                    fitness_score = fitness_score + 0.2;
                }
                else {
                    fitness_score = fitness_score - 0.1;
                }

            }
            else {

                //Preferred faculty members.
                if(class_input.get(i).get_instructor().equals("Xu") || class_input.get(i).get_instructor().equals("Shah")
                        || class_input.get(i).get_instructor().equals("Song")) {
                    fitness_score = fitness_score + 0.5;
                }
                //Listed faculty members.
                else if(class_input.get(i).get_instructor().equals("Uddin") || class_input.get(i).get_instructor().equals("Zein el Din")
                        || class_input.get(i).get_instructor().equals("Nait-Abdesselam") || class_input.get(i).get_instructor().equals("Hare")) {
                    fitness_score = fitness_score + 0.2;
                }
                else {
                    fitness_score = fitness_score - 0.1;
                }

            }
        }

        Vector<String> temp_instructor_teach_times = new Vector<String>();
        Vector<Integer> temp_index = new Vector<Integer>();
        //Check instructor's load and location.
        for(int i = 0; i < instructor_list.size(); ++i) {
            int temp_count = 0;
            for(int j = 0; j < class_input.size(); ++j) {

                //Keep track of how many courses an instructor are teaching.
                if(class_input.get(j).get_instructor().equals(instructor_list.get(i))) {
                    ++temp_count;
                    temp_instructor_teach_times.add(class_input.get(j).get_time());
                    temp_index.add(j);
                }

                //If we are at the end of the class_input, see how many classes the instructor is teaching.
                if(j == class_input.size() - 1 && temp_count > 0) {
                    if(temp_count > 4) {
                        fitness_score = fitness_score - 0.5;
                    }
                    if(temp_count <= 2 && !(instructor_list.get(i).equals("Xu"))) {
                        fitness_score = fitness_score - 0.4;
                    }
                }
            }

            //Check if instructor is teaching two courses in the same hour.
            for(int j = 0; j < temp_instructor_teach_times.size(); ++j) {
                for(int k = 0; k < temp_instructor_teach_times.size(); ++k) {

                    //If the instructor is teaching two classes at the same time and we haven't already counted it yet.
                    if(temp_instructor_teach_times.get(j).equals(temp_instructor_teach_times.get(k))
                        && k > j) {
                        fitness_score = fitness_score - 0.2;
                        break;
                    }
                    //If the instructor is teaching two classes at the same time and we have already counted it.
                    else if(temp_instructor_teach_times.get(j).equals(temp_instructor_teach_times.get(k))
                            && k < j) {
                        break;
                    }
                    else if(k == temp_instructor_teach_times.size() - 1) {
                        fitness_score = fitness_score + 0.2;
                    }
                }
            }

            //Check if the instructor is scheduled for consecutive time slots.
            for(int j = 0; j < class_time_list.size(); ++j) {
                for(int k = j;  k < temp_instructor_teach_times.size(); ++k) {

                    //Find where the instructor's teach time in the class_time_list is and make sure it
                    //isn't the last time of the day.
                    if (temp_instructor_teach_times.get(k).equals(class_time_list.get(j)) &&
                            class_input.get(temp_index.get(k)).get_instructor().equals(instructor_list.get(i)) &&
                            k + 1 < temp_instructor_teach_times.size() && j + 1 < class_time_list.size()) {
                        //Check if the instructor is teaching a consecutive time
                        if(temp_instructor_teach_times.get(k + 1).equals(class_time_list.get(j + 1))) {
                            if(class_input.get(temp_index.get(k)).get_room().equals("Katz 003")
                                    && class_input.get(temp_index.get(k + 1)).get_room().equals("Block 119")) {
                                fitness_score = fitness_score - 0.4;
                            }
                            else if(class_input.get(temp_index.get(k)).get_room().equals("Block 119")
                                    && class_input.get(temp_index.get(k + 1)).get_room().equals("Katz 003")) {
                                fitness_score = fitness_score - 0.4;
                            }
                        }
                    }
                    //Check the opposite way if the instructor is teaching consecutive classes.
                    else if (temp_instructor_teach_times.get(k).equals(class_time_list.get(j)) &&
                            class_input.get(temp_index.get(k)).get_instructor().equals(instructor_list.get(i)) &&
                            k - 1 > 0 && j - 1 > 0) {
                        //Check if the instructor is teaching a consecutive time.
                        if(temp_instructor_teach_times.get(k - 1).equals(class_time_list.get(j - 1))) {
                            if(class_input.get(temp_index.get(k)).get_room().equals("Katz 003")
                                    && class_input.get(temp_index.get(k - 1)).get_room().equals("Block 119")) {
                                fitness_score = fitness_score - 0.4;
                            }
                            else if(class_input.get(temp_index.get(k)).get_room().equals("Block 119")
                                    && class_input.get(temp_index.get(k - 1)).get_room().equals("Katz 003")) {
                                fitness_score = fitness_score - 0.4;
                            }
                        }
                    }
                }
            }
            temp_instructor_teach_times.clear();
            temp_index.clear();
        }

        //Check rules for CS 101 and CS 191.
        if(class_input.get(0).get_time().equals("10 AM")) {
            if(class_input.get(1).get_time().equals("3 PM")) {
                fitness_score = fitness_score + 0.5;
            }
        }
        if(class_input.get(0).get_time().equals(class_input.get(1).get_time())) {
            fitness_score = fitness_score - 0.5;
        }
        if(class_input.get(2).get_time().equals("10 AM")) {
            if(class_input.get(3).get_time().equals("3 PM")) {
                fitness_score = fitness_score + 0.5;
            }
        }
        if(class_input.get(2).get_time().equals(class_input.get(3).get_time())) {
            fitness_score = fitness_score - 0.5;
        }
        for (int i = 0; i < 2; ++i) {
            if (class_input.get(i).get_time().equals(class_input.get(2).get_time())) {
                fitness_score = fitness_score - 0.25;
            }
            if (class_input.get(i).get_time().equals(class_input.get(3).get_time())) {
                fitness_score = fitness_score - 0.25;
            }
        }

        //Check if there is a section of CS 101 and CS 191 that are taught in consecutive time slots.
        //If they are, check if they are at opposite sides of campus.
        for(int i = 0; i < 2; ++i) {
            for(int j = 0; j < class_time_list.size(); ++j) {
                if (class_input.get(i).get_time().equals(class_time_list.get(j)) && j + 1 < class_time_list.size()
                    && j - 1 > -1) {
                    if(class_input.get(2).get_time().equals(class_time_list.get(j + 1))
                        || class_input.get(2).get_time().equals(class_time_list.get((j - 1)))) {
                        fitness_score = fitness_score + 0.5;

                        if(class_input.get(i).get_room().equals("Katz 003")
                                && class_input.get(2).get_room().equals("Block 119")) {
                            fitness_score = fitness_score - 0.4;
                        }
                        else if(class_input.get(i).get_room().equals("Block 119")
                                && class_input.get(2).get_room().equals("Katz 003")) {
                            fitness_score = fitness_score - 0.4;
                        }
                    }
                    if(class_input.get(3).get_time().equals(class_time_list.get(j + 1))
                            || class_input.get(3).get_time().equals(class_time_list.get((j - 1)))) {
                        fitness_score = fitness_score + 0.5;

                        if(class_input.get(i).get_room().equals("Katz 003")
                                && class_input.get(3).get_room().equals("Block 119")) {
                            fitness_score = fitness_score - 0.4;
                        }
                        else if(class_input.get(i).get_room().equals("Block 119")
                                && class_input.get(3).get_room().equals("Katz 003")) {
                            fitness_score = fitness_score - 0.4;
                        }
                    }
                }
            }

            //Check if a sectioin of CS 101 and CS 191 are separated by 1 hour (10AM and 12PM).
            for(int j = 0; j < class_time_list.size(); ++j) {
                if(class_input.get(i).get_time().equals(class_time_list.get(j)) && j + 2 < class_time_list.size()
                    && j - 2 > -1) {
                    if(class_input.get(2).get_time().equals(class_time_list.get(j + 2))
                        || class_input.get(2).get_time().equals(class_time_list.get(j - 2))) {
                        fitness_score = fitness_score + 0.25;
                    }
                    if(class_input.get(3).get_time().equals(class_time_list.get(j + 2))
                            || class_input.get(3).get_time().equals(class_time_list.get(j - 2))) {
                        fitness_score = fitness_score + 0.25;
                    }
                }
            }
        }

        return fitness_score;
    }
}

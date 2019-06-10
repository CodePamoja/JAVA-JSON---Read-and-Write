package json.tutorial;

import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;

public class Project{
    public static int students = 0; //keep count of number of students created
    public static List<Student> listOfStudents; //store all students

    //class student
    public static class Student {
        public String regno;
        public String fname;
        public String lname;
        public String age;
        public String gender;

        //student constructor
        public Student() {
            Scanner n = new Scanner(System.in);
            System.out.println("\nRegistration Details");
            regno = Integer.toString(students + 1);
            System.out.print("First Name:");
            fname = n.next();
            System.out.print("Last Name:");
            lname = n.next();
            System.out.print("Age:");
            age = n.next();
            System.out.print("Gender:");
            gender = n.next();
            System.out.println();
            students += 1;
        }

        public Student (String regno, String fname, String lname, String age, String gender) {
            this.regno = regno;
            this.fname = fname;
            this.lname = lname;
            this.age = age;
            this.gender = gender;
        }
    }

    public static void saveStudents(List<Student> listOfStudents) throws FileNotFoundException, ParseException {

        String json = new Gson().toJson(listOfStudents);
//        final JSONObject json = new JSONObject();
//        json.toJSONString((Map) listOfStudents);
        

        try {
            BufferedWriter br =new BufferedWriter(new FileWriter("C:\\Users\\Steve Karanja\\Documents\\NetBeansProjects\\json-tutorial\\src\\json\\tutorial\\student.json", false));
            br.write(json);
            br.close();
            System.out.println("Saved to File Successfully!\n");

        mainMenu();
        } catch(IOException e) {
            System.out.println("error submitted" + e);
        }
    }

    public static Student createStudent () {
        Student s1 = new Student();
        return s1;
    }

    public static List<Student> readStudents() {
        listOfStudents = new ArrayList<Student>();
        
        File file = new File("C:\\Users\\Steve Karanja\\Documents\\NetBeansProjects\\json-tutorial\\src\\json\\tutorial\\student.json");
        if(file.length()==0){
         return listOfStudents;
        }
        Reader read = null;
        try {
            read = new FileReader("C:\\Users\\Steve Karanja\\Documents\\NetBeansProjects\\json-tutorial\\src\\json\\tutorial\\student.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        JSONParser parser = new JSONParser();

        JSONArray jsonArray = null;
        try {
            jsonArray = (JSONArray) parser.parse(read);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (Object jsonObject : jsonArray) {
            JSONObject student = (JSONObject) jsonObject;
            String regno = (String) student.get("regno");
            String fname = (String) student.get("fname");
            String sname = (String) student.get("lname");
            String age = (String) student.get("age");
            String gender = (String) student.get("gender");

            Student s = new Student(regno, fname, sname, age, gender);
            listOfStudents.add(s);
        }
        
        Student s = listOfStudents.get(listOfStudents.size() - 1);
        students = Integer.parseInt(s.regno);
        return listOfStudents;
    }

    public static void displayStudents(List<Student> listOfStudents){
        for (Student s : listOfStudents ){
            System.out.println("\nReg No:\t"+s.regno+"\nF Name:\t"+s.fname+"\nS Name:\t"+s.lname+"\nAge:\t"+s.age+"\nGender:\t"+s.gender);
        }
    }

    public static void menu() throws IOException, FileNotFoundException, ParseException{
        System.out.println("1) Add New\n2) Save");
        System.out.print("Selection:");

        Scanner s = new Scanner(System.in);
        switch (s.nextInt())
        {
            case 1:
                listOfStudents.add(createStudent());
                menu();
                break;

            case 2:
                saveStudents(listOfStudents);
                mainMenu();
                break;

            default:
                System.err.println ( "Unrecognized option" );
                break;
        }
    }
    
    
    public static void mainMenu() throws IOException, FileNotFoundException, ParseException {
        System.out.println("\n1) Add student\n2) Display Students\n3)Quit");
        System.out.print("Selection:");
        Scanner s = new Scanner(System.in);
        switch (s.nextInt())
        {
            case 1:
                readStudents();
                listOfStudents.add(createStudent());
                menu();
              
                break;

            case 2:
                readStudents();
                displayStudents(listOfStudents);
                mainMenu();
                
                break;
                
            case 3:
                System.out.println("Thank you and good bye");
                
                break;

            default:
                System.err.println ( "Unrecognized option" );
                break;
        }
    }
    

    public static void main(String []args) throws IOException, FileNotFoundException, ParseException{
        mainMenu();
    }
}

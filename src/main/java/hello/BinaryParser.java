package hello;
import java.io.BufferedReader;
import java.io.FileReader;

public class BinaryParser {

    public BinarySearchTree<Student> ReadFile(String path){
        Student[] students=null;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                students=ParseLine(line);
            }
        }catch(java.io.IOException e){
            System.out.println("could not load file.");
        }finally {
            // Will get executed, even if exception occurs
            System.out.println("Finished");
        }
        return CreateTree(students);
    } 

    public BinarySearchTree<Student> GetTreeFromLine(String line){
        return CreateTree(ParseLine(line));
    }

    private Student[] ParseLine(String line){
        String[] parts=line.split(";");
        Student[] students=new Student[parts.length];
        for (int i=0; i< parts.length; i++){
            students[i]=new Student(parts[i]);
        }
        return students;
    }

    private BinarySearchTree<Student> CreateTree(Student[] students){
        BinarySearchTree<Student> tree=new BinarySearchTree<Student>();

        for(int i=0;i<students.length;i++){
            tree.Insert(students[i]);
        }
        return tree;
    }
}
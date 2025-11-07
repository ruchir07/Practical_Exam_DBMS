package myjdbc;

import java.util.*;
import java.sql.*;


public class Firstclass {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		Connection con=null;
		try {
//			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/teachers","root","Ruchir@2111");
			System.out.println("Database connected successfully !");
			
			int choice;
			do {
				System.out.println("\n--- MENU ---");
                System.out.println("1. Add Student");
                System.out.println("2. View Students");
                System.out.println("3. Update Student");
                System.out.println("4. Delete Student");
                System.out.println("5. Exit");
				System.out.print("Enter choice: ");
				choice=sc.nextInt();
				
				switch(choice){
				case 1:
					System.out.print("Enter name: ");
                    String name = sc.next();
                    System.out.print("Enter age: ");
                    int age = sc.nextInt();
                    String sql="INSERT into students(name,age) values (?,?)";
                    PreparedStatement pst=con.prepareStatement(sql);
                    pst.setString(1,name);
                    pst.setInt(2,age);
                    int rows = pst.executeUpdate();
                    if (rows > 0)
                        System.out.println("Student inserted successfully!");
                    else
                        System.out.println("Failed to insert student!");
                    break;
				case 2:
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery("Select * from students");
					
					while(rs.next()) {
						System.out.println(rs.getInt("id")+" | "+rs.getString("name")+" | "+rs.getInt("age"));
					}
					break;
				case 3:
					System.out.print("Enter student id to update: ");
					int id=sc.nextInt();
					System.out.print("Enter new name: ");
					name=sc.next();
					System.out.print("Enter new age: ");
					age=sc.nextInt();
					String updatesql="Update students set name=? , age=? where id=?";
					PreparedStatement pst2=con.prepareStatement(updatesql);
					pst2.setString(1, name);
					pst2.setInt(2,age);
					pst2.setInt(3,id);
					int updated=pst2.executeUpdate();
					if (updated > 0)
                        System.out.println("✅ Student updated successfully!");
                    else
                        System.out.println("❌ Student not found!");
                    break;
				case 4:
					System.out.print("Enter student id to delete: ");
					id=sc.nextInt();
					String deletesql="DELETE from students where id=?";
					PreparedStatement pst3=con.prepareStatement(deletesql);
					pst3.setInt(1, id);
					int deleted = pst3.executeUpdate();
                    if (deleted > 0)
                        System.out.println("✅ Student deleted successfully!");
                    else
                        System.out.println("❌ Student not found!");
                    break;
				case 5:
                    System.out.println("👋 Exiting...");
                    break;

                default:
                    System.out.println("❌ Invalid choice!");
				}
			}while(choice!=5);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
                if (con != null)
                    con.close();
                sc.close();
            } catch (Exception e) {
            }
		}
	}
}
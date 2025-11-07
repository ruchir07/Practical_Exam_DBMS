package mongojdbc;

import java.util.*;

import org.bson.Document;
import com.mongodb.client.*;


public class myClass {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Connect to MongoDB
        MongoClient mongo = MongoClients.create("mongodb://localhost:27017");
        System.out.println("✅ Connection established successfully!");

        // Use existing database and collection
        MongoDatabase db = mongo.getDatabase("xyz");
        MongoCollection<Document> collection = db.getCollection("student");
        System.out.println("📂 Connected to database: xyz, collection: student");

        int choice;
        do {
            System.out.println("\n=== MongoDB Menu ===");
            System.out.println("1. Insert Document");
            System.out.println("2. View All Documents");
            System.out.println("3. Update Document");
            System.out.println("4. Delete Document");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume leftover newline

            switch (choice) {
                case 1:
                    System.out.print("Enter ID: ");
                    String id = sc.nextLine();
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    Document doc = new Document("id", id).append("name", name);
                    collection.insertOne(doc);
                    System.out.println("✅ Document inserted successfully!");
                    break;

                case 2:
                    System.out.println("\n📜 All Documents:");
                    FindIterable<Document> docs = collection.find();
                    for (Document d : docs) {
                        System.out.println(d.toJson());
                    }
                    break;

                case 3:
                    System.out.print("Enter ID to update: ");
                    id = sc.nextLine();
                    System.out.print("Enter new Name: ");
                    String newName = sc.nextLine();

                    UpdateResult result = collection.updateOne(Filters.eq("id",id),Updates.set("name",newName));
                    if(result.getMatchedCount() > 0) {
                    	System.out.println("Document updated successfully!");
                    }
                    else {
                    	System.out.println("No document found with ID: " + id);
                    }
                    break;

                case 4:
                    System.out.print("Enter ID to delete: ");
                    id = sc.nextLine();

                    DeleteResult Delresult = collection.deleteOne(Filters.eq("id",id));
                    if(Delresult.getDeletedCount() > 0) {
                    	System.out.println("Document deleted successfully!");
                    }
                    else {
                    	System.out.println("No document found with ID: " + id);
                    }
                    break;

                case 5: 
                	System.out.println("👋 Exiting program... Goodbye!");
                	break;
                default:
                	System.out.println("⚠️ Invalid choice. Try again!");
            }

        } while (choice != 5);

        mongo.close();
        sc.close();
    }
}

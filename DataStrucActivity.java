import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataStrucActivity {

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private static final String GROCERY_FILE = "grocery.txt";
    private static final String MOVIE_FILE = "movie.txt";
    private static final String MUSIC_FILE = "music.txt";

    public static void main(String[] args) {
        int choice;
        try {
            do {
                System.out.println("\n===========================================");
                System.out.printf("%35s\n", "MAIN SERVICE CENTER MENU");
                System.out.println("===========================================");
                System.out.println("1. Grocery Store Services");
                System.out.println("2. Movie Services");
                System.out.println("3. Music Album Services");
                System.out.println("4. Exit");
                System.out.print("Enter Choice: ");

                try {
                    choice = Integer.parseInt(reader.readLine());
                } catch (NumberFormatException e) {
                    choice = 0;
                }

                switch (choice) {
                    case 1 -> serviceMenu("GROCERY", GROCERY_FILE, 3); 
                    case 2 -> serviceMenu("MOVIE", MOVIE_FILE, 7);    
                    case 3 -> serviceMenu("MUSIC", MUSIC_FILE, 5);   
                    case 4 -> System.out.println("Goodbye!");
                    default -> System.out.println("Invalid option.");
                }

            } while (choice != 4);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void serviceMenu(String serviceName, String fileName, int recordSize) throws IOException {
        int choice;
        do {
            System.out.println("\n--- " + serviceName + " MENU ---");
            System.out.println("1. Add Item");
            System.out.println("2. Search Item");
            System.out.println("3. Remove Item");
            System.out.println("4. Display All Items");
            System.out.println("5. Sort Items");
            System.out.println("6. Back to Main Menu");
            System.out.print("Select: ");

            try {
                choice = Integer.parseInt(reader.readLine());
            } catch (NumberFormatException e) {
                choice = 0;
            }

            switch (choice) {
                case 1 -> handleAdd(serviceName, fileName);
                case 2 -> handleSearch(fileName, recordSize);
                case 3 -> handleRemove(fileName, recordSize);
                case 4 -> displayAllRecords(fileName, recordSize);
                case 5 -> sortRecords(fileName, recordSize);
                case 6 -> System.out.println("Returning...");
                default -> System.out.println("Invalid.");
            }
        } while (choice != 6);
    }


    public static void handleAdd(String service, String fileName) throws IOException {
        ArrayList<String> data = new ArrayList<>();
        
        System.out.println("Adding new " + service + " entry...");

        if (service.equals("GROCERY")) {
            System.out.print("Product Name: "); data.add(reader.readLine());
            System.out.print("Price: ");        data.add(reader.readLine());
            System.out.print("Quantity: ");     data.add(reader.readLine());

        } else if (service.equals("MOVIE")) {
            System.out.print("Type (DVD/VCD): ");  data.add(reader.readLine());
            System.out.print("Movie Title: ");     data.add(reader.readLine());
            System.out.print("Category: ");        data.add(reader.readLine());
            System.out.print("Minutes: ");         data.add(reader.readLine());
            System.out.print("Setting: ");         data.add(reader.readLine());
            System.out.print("Rental/Sales: ");    data.add(reader.readLine());
            System.out.print("Price: ");           data.add(reader.readLine());

        } else if (service.equals("MUSIC")) {
            System.out.print("Album Name: ");   data.add(reader.readLine());
            System.out.print("Artist: ");       data.add(reader.readLine());
            System.out.print("Genre: ");        data.add(reader.readLine());
            System.out.print("Record Label: "); data.add(reader.readLine());
            System.out.print("Year: ");         data.add(reader.readLine());
        }

  
        saveRecord(service, fileName, data);
        System.out.println("Item Added Successfully!");
    }

    public static void handleSearch(String fileName, int blockSize) {
        String service = getServiceName(fileName);
        try {
            System.out.print("Enter keyword to search (Name/Title): ");
            String keyword = reader.readLine().toLowerCase();

            List<List<String>> records = readAllRecords(service, fileName, blockSize);
            boolean found = false;
            int receiptNo = 0;

            for (List<String> record : records) {
                boolean match = record.stream()
                    .anyMatch(field -> field.toLowerCase().contains(keyword));

                if (match) {
                    receiptNo++;
                    printReceipt(record, service, receiptNo);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No records found.");
            }

        } catch (FileNotFoundException e) {
            System.out.println("File is empty or does not exist yet.");
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
    }

    public static void handleRemove(String fileName, int blockSize) {
        String service = getServiceName(fileName);
        try {
            System.out.print("Enter Exact Name/Title to remove: ");
            String termToDelete = reader.readLine().trim().toLowerCase();

            List<List<String>> records = readAllRecords(service, fileName, blockSize);
            List<List<String>> remaining = new ArrayList<>();
            boolean deleted = false;

            for (List<String> record : records) {
                String first = record.isEmpty() ? "" : record.get(0).toLowerCase();
                if (!deleted && first.equalsIgnoreCase(termToDelete)) {
                    System.out.println("Record deleted: " + record.get(0));
                    deleted = true;
                } else {
                    remaining.add(record);
                }
            }

            writeAllRecords(service, fileName, remaining);

            if (!deleted) {
                System.out.println("Item not found.");
            }

        } catch (FileNotFoundException e) {
            System.out.println("File does not exist.");
        } catch (IOException e) {
            System.out.println("Error processing delete.");
        }
    }

    private static void saveRecord(String service, String fileName, List<String> data) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
            writeFormattedRecord(service, bw, data);
        }
    }

    private static String getFieldLabel(String service, int index) {
        return switch (service) {
            case "GROCERY" -> switch (index) {
                case 0 -> "Product Name: ";
                case 1 -> "Price: ";
                case 2 -> "Quantity: ";
                default -> "";
            };
            case "MOVIE" -> switch (index) {
                case 0 -> "Type: ";
                case 1 -> "Movie Title: ";
                case 2 -> "Category: ";
                case 3 -> "Minutes: ";
                case 4 -> "Setting: ";
                case 5 -> "Rental/Sales: ";
                case 6 -> "Price: ";
                default -> "";
            };
            case "MUSIC" -> switch (index) {
                case 0 -> "Album Name: ";
                case 1 -> "Artist: ";
                case 2 -> "Genre: ";
                case 3 -> "Record Label: ";
                case 4 -> "Year: ";
                default -> "";
            };
            default -> "";
        };
    }

    private static String extractFieldValue(String line) {
        int sep = line.indexOf(":");
        if (sep < 0) return line.trim();
        return line.substring(sep + 1).trim();
    }

    private static void displayAllRecords(String fileName, int blockSize) {
        String service = getServiceName(fileName);
        try {
            List<List<String>> records = readAllRecords(service, fileName, blockSize);
            if (records.isEmpty()) {
                System.out.println("No records found.");
                return;
            }

            int receiptNo = 0;
            for (List<String> record : records) {
                receiptNo++;
                printReceipt(record, service, receiptNo);
            }

        } catch (FileNotFoundException e) {
            System.out.println("No records found.");
        } catch (IOException e) {
            System.out.println("Error reading records.");
        }
    }

    private static void sortRecords(String fileName, int blockSize) {
        String service = getServiceName(fileName);
        try {
            List<List<String>> records = readAllRecords(service, fileName, blockSize);

            records.sort((a, b) -> {
                String av = a.isEmpty() ? "" : a.get(0); 
                String bv = b.isEmpty() ? "" : b.get(0);
                return av.compareToIgnoreCase(bv);
            });

            writeAllRecords(service, fileName, records);

        } catch (FileNotFoundException e) {
            System.out.println("No records to sort.");
        } catch (IOException e) {
            System.out.println("Error sorting records.");
        }
    }

    private static String getServiceName(String fileName) {
        if (GROCERY_FILE.equalsIgnoreCase(fileName)) return "GROCERY";
        if (MOVIE_FILE.equalsIgnoreCase(fileName)) return "MOVIE";
        if (MUSIC_FILE.equalsIgnoreCase(fileName)) return "MUSIC";
        return "";
    }

    private static List<List<String>> readAllRecords(String service, String fileName, int blockSize) throws IOException {
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            List<String> current = new ArrayList<>();
            boolean inFormattedBlock = false;

            while ((line = br.readLine()) != null) {
                String trimmed = line.trim();
                if (trimmed.isEmpty()) {
                    if (!current.isEmpty()) {
                        records.add(new ArrayList<>(current));
                        current.clear();
                    }
                    inFormattedBlock = false;
                    continue;
                }

                if (trimmed.startsWith("===")) {
                    if (!current.isEmpty()) {
                        records.add(new ArrayList<>(current));
                        current.clear();
                    }
                    inFormattedBlock = true;
                    continue;
                }

                if (trimmed.startsWith("---")) {
                    if (!current.isEmpty()) {
                        records.add(new ArrayList<>(current));
                        current.clear();
                    }
                    inFormattedBlock = false;
                    continue;
                }

                if (inFormattedBlock) {
                    current.add(extractFieldValue(trimmed));
                } else {
                    current.add(trimmed);
                }

                if (!inFormattedBlock && current.size() == blockSize) {
                    records.add(new ArrayList<>(current));
                    current.clear();
                }
            }

            if (!current.isEmpty()) {
                records.add(new ArrayList<>(current));
            }
        }
        return records;
    }

    private static void writeAllRecords(String service, String fileName, List<List<String>> records) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (List<String> record : records) {
                writeFormattedRecord(service, bw, record);
            }
        }
    }

    private static void writeFormattedRecord(String service, BufferedWriter bw, List<String> values) throws IOException {
        bw.write("========================================");
        bw.newLine();
        for (int i = 0; i < values.size(); i++) {
            String label = getFieldLabel(service, i);
            bw.write(label + values.get(i));
            bw.newLine();
        }
        bw.write("----------------------------------------");
        bw.newLine();
        bw.newLine();
    }

    private static void printReceipt(List<String> fields, String service, int receiptNumber) {
        System.out.println("\n========================================");
        System.out.printf("%20s #%d\n", "RECEIPT", receiptNumber);
        System.out.println("========================================");
        for (int i = 0; i < fields.size(); i++) {
            String label = getFieldLabel(service, i);
            System.out.println(label + fields.get(i));
        }
        System.out.println("----------------------------------------");
    }
}

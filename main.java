import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Main {

    // big -> 0; little -> 1
    public static byte endianType;
    // int -> 0; unsigned -> 1; float -> 2
    public static byte dataType;
    public static byte size;

    public static void main(String[] args) {

        // Take file input
        File file = new File("./input.txt");

        // Takes hexadecimals from input and puts them into hexArray
        short[][] hexArray = new short[3][12];
        short i = 0;
        String tempString;
        String[] tempStrings;
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                tempString = scanner.nextLine();
                tempStrings = tempString.split(" ");
                for (int j = 0; j < 12; j++) {
                    hexArray[i][j] = convertHexToShort(tempStrings[j]);
                }
                i++;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("FILENOTFOUND");
            return;
        }
        // Gets other inputs

        // Gets byte ordering
        Scanner scanner = new Scanner(System.in);
        System.out.println("Byte Ordering: ");
        tempString = scanner.nextLine();
        if (tempString.equals("b")) {
            endianType = 0;
        } else if (tempString.equals("l")) {
            endianType = 1;
        } else {
            System.out.println("Unkown Input");
            return;
        }

        // Gets data type
        System.out.println("Data Type: ");
        tempString = scanner.nextLine();
        if (tempString.equals("int")) {
            dataType = 0;
        } else if (tempString.equals("unsigned")) {
            dataType = 1;
        } else if (tempString.equals("float")) {
            dataType = 2;
        } else {
            System.out.println("Unkown Input");
            return;
        }

        // Gets Byte Size
        System.out.println("Byte Size: ");
        tempString = scanner.next();
        if (tempString.equals("1")) {
            size = 1;
        } else if (tempString.equals("2")) {
            size = 2;
        } else if (tempString.equals("3")) {
            size = 3;
        } else if (tempString.equals("4")) {
            size = 4;
        } else {
            System.out.println("Unkown Input");
            return;
        }

        String binaryNumbers[][] = new String[3][12 / size];
        long results[][] = new long[3][12 / size];

        switch (size) {
            case 1:
                for (i = 0; i < 3; i++) {
                    for (int j = 0; j < 12; j++) {
                        binaryNumbers[i][j] = convertShortToBinary(hexArray[i][j]);
                    }
                }

                if (dataType == 1) {// For unsigned
                    printShortArray2D(hexArray);
                } else if (dataType == 0) {// For Signed

                } else {// For float

                }
                printStringArray2D(binaryNumbers);
                printLongArray2D(results);

                break;

            case 2:
                for (i = 0; i < 3; i++) {
                    for (int j = 0; j < 12; j += 2) {
                        // big
                        if (endianType == 0) {
                            binaryNumbers[i][j / 2] = convertShortToBinary(hexArray[i][j])
                                    + convertShortToBinary(hexArray[i][j + 1]);
                        } else { // little
                            binaryNumbers[i][j / 2] = convertShortToBinary(hexArray[i][j + 1])
                                    + convertShortToBinary(hexArray[i][j]);
                        }
                    }
                }

                if (dataType == 1) {// If it is unsigned
                    for (i = 0; i < 3; i++) {
                        for (int j = 0; j < 12 / size; j++) {
                            results[i][j] = convertBinaryToLong(binaryNumbers[i][j]);
                        }
                    }
                } else if (dataType == 0) {

                }
                printStringArray2D(binaryNumbers);
                printLongArray2D(results);
                break;

            case 3:
                for (i = 0; i < 3; i++) {
                    for (int j = 0; j < 12; j += 3) {

                        if (endianType == 0) {
                            binaryNumbers[i][j / 3] = convertShortToBinary(hexArray[i][j])
                                    + convertShortToBinary(hexArray[i][j + 1])
                                    + convertShortToBinary(hexArray[i][j + 2]);
                        } else {
                            binaryNumbers[i][j / 3] = convertShortToBinary(hexArray[i][j + 2])
                                    + convertShortToBinary(hexArray[i][j + 1]) + convertShortToBinary(hexArray[i][j]);

                        }
                    }
                }

                if (dataType == 1) {// For unsigned
                    for (i = 0; i < 3; i++) {
                        for (int j = 0; j < 12 / size; j++) {
                            results[i][j] = convertBinaryToLong(binaryNumbers[i][j]);
                        }
                    }

                } else if (dataType == 0) {// For Signed

                } else {// For float

                }
                printStringArray2D(binaryNumbers);
                printLongArray2D(results);

                break;

            case 4:
                for (i = 0; i < 3; i++) {
                    for (int j = 0; j < 12; j += 4) {

                        if (endianType == 0) {
                            binaryNumbers[i][j / 4] = convertShortToBinary(hexArray[i][j])
                                    + convertShortToBinary(hexArray[i][j + 1])
                                    + convertShortToBinary(hexArray[i][j + 2])
                                    + convertShortToBinary(hexArray[i][j + 3]);
                        } else {
                            binaryNumbers[i][j / 4] = convertShortToBinary(hexArray[i][j + 3])
                                    + convertShortToBinary(hexArray[i][j + 2])
                                    + convertShortToBinary(hexArray[i][j + 1])
                                    + convertShortToBinary(hexArray[i][j]);

                        }
                    }
                }

                if (dataType == 1) {// For unsigned
                    for (i = 0; i < 3; i++) {
                        for (int j = 0; j < 12 / size; j++) {
                            results[i][j] = convertBinaryToLong(binaryNumbers[i][j]);
                        }
                    }

                } else if (dataType == 0) {// For Signed

                } else {// For float

                }
                printStringArray2D(binaryNumbers);
                printLongArray2D(results);

                break;

        }
    }

    public static String convertShortToBinary(short input) {
        String binary = "";
        for (int i = 7; i >= 0; i--) {
            binary += (input >> i) & 1;
        }
        return binary;
    }

    public static long convertBinaryToLong(String binaryString) {
        int decimalValue = 0;
        int power = 0;
        for (int i = binaryString.length() - 1; i >= 0; i--) {
            char binaryDigit = binaryString.charAt(i);
            if (binaryDigit == '1') {
                decimalValue += Math.pow(2, power);
            }
            power++;
        }
        return decimalValue;
    }

    // Convers a 2 byte hexadecimal into a decimal
    // For example 1a into 170
    public static short convertHexToShort(String hex) {

        // Converts first number
        char temp = hex.charAt(1);
        int x = 0;
        short sum = 0;
        if (temp >= '0' && temp <= '9') {
            x = temp - '0';
        } else {
            x = temp - 'a' + 10;
        }
        sum += x;

        // Converts second number
        x = 0;
        temp = hex.charAt(0);
        if (temp >= '0' && temp <= '9') {
            x = temp - '0';
        } else {
            x = temp - 'a' + 10;
        }

        // Adds them up and returns
        sum += x * 16;
        return sum;

    }

    // Temp func for testing delete.
    public static void printShortArray2D(short[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void printStringArray2D(String[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void printLongArray2D(long[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static int convertBinaryToInteger(String binaryString) {
        int decimalValue = 0;
        int power = 0;
        for (int i = binaryString.length() - 1; i >= 0; i--) {
            char binaryDigit = binaryString.charAt(i);
            if (binaryDigit == '1') {
                decimalValue += Math.pow(2, power);
            }
            power++;
        }
        return decimalValue;
    }

}
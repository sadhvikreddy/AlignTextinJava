/**
 * CS5001 Practical 1 - AlignText.
 * 
 * @author 210025637 (Sadhvik Reddy).
 * @version final.
 */
public class AlignText {
    /** for Sign Post board. */
    public static final int SIGN_POST_BUFFER = 4;
    /** Numbers for better readability. */
    public static final int FIVE = 5;
    /** Numbers for better readability. */
    public static final int SIX = 6;
    /** Numbers for better readability. */
    public static final int SEVEN = 7;
    /** Numbers for better readability. */
    public static final int EIGHT = 8;
    /** Numbers for better readability. */
    public static final int FIFTEEN = 15;

    /** Remove White Space at end.
     * @param word String literal to be Processed
     * @return word
     */
    public static String removeWhiteSpaces(String word) {
        int l = word.length();
        if (l <= 0) {
            return word;
        }
        char lastChar = word.charAt(l - 1);
        if (lastChar == ' ') {
            // Stripping last Character
            word = word.substring(0, l - 1);
        }
        return word;
    }

    /** Main Method.
     * @param args file_name line_length [align_mode]
     */
    public static void main(String[] args) {
        try {
        String[] paragraphs = FileUtil.readFile(args[0]);
        int lineLength = Integer.parseInt(args[1]);
        int checker = 0; //Checking the line length
        int cursor = 0; //keeps track of printing
        String stringArray = "";
        String mode = "default";
        int minSignLength = FIFTEEN; //for extending sign post to 15 characters if less than that

        try {
            mode = args[2];
        } catch (ArrayIndexOutOfBoundsException ignored) { //Ignoring if no last argument is given
    }
        if (lineLength <= 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (mode.equals("S")) {
            //Accounting space for Sign Post Border
            lineLength = lineLength - SIGN_POST_BUFFER;
        }

        int maxChecker = lineLength;

        for (int i = 0; i < paragraphs.length; i++) {
        //splitting the paragraphs using whitespaces
        String[] arr = paragraphs[i].split("\\s");

        for (int j = 0; j < arr.length; j++) {
                int len = arr[j].length();
                //Sign post specific for Words exceeding line length
                if (len > maxChecker) {
                    maxChecker = len;
                }
                //Getting Word Count "+1" being for white spaces
                checker = checker + len + 1;
                //Line length Satisfied.
                if (checker >= lineLength && cursor != 0) {
                    char nextChar = paragraphs[i].charAt(cursor - 1);
                    //When Last character of line is either Whitespace.
                    if (nextChar == ' ' && (checker == lineLength + 1 || checker == lineLength)) {
                        checker = 0;
                        //Added to String
                        stringArray = stringArray + arr[j];
                        cursor = cursor + len + 1;
                        //detemining new lines using Char: ----
                        stringArray = stringArray + "----";
                        continue;
                    }
                    else {
                        //When text needs to be wrapped
                        checker = len + 1;
                        stringArray = stringArray + "----";
                    }
                }
                //adding new lines to buffer
                cursor = cursor + len + 1;
                stringArray = stringArray + arr[j] + " ";
        }
        checker = 0;
        cursor = 0;
        //Determining End of paragraph
        stringArray = stringArray + "----";
        }

        //Spliting the String using ----
        String[] pArr = stringArray.split("\\----");

        /** LEFT ALIGN */
        if (mode.equals("L") || mode.equals("default")) {
            for (int i = 0; i < pArr.length; i++) {
                String pWord = removeWhiteSpaces(pArr[i]);
                System.out.println(pWord);
            }
        }

        /** RIGHT ALIGN */
        else if (mode.equals("R")) {
            for (int i = 0; i < pArr.length; i++) {
                String pWord = removeWhiteSpaces(pArr[i]);
                int l = pWord.length();
                int offset = lineLength - l;
                if (offset < 0) {
                    // if there is overflow
                    System.out.println(pWord);
                }
                else {
                // Adding padding on left
                String buffer = " ".repeat(offset);
                System.out.println(buffer + pWord);
                }
            }
        }

        /** CENTER ALIGN */
        else if (mode.equals("C")) {
            for (int i = 0; i < pArr.length; i++) {
                String pWord = removeWhiteSpaces(pArr[i]);
                int l = pWord.length();
                int offset = (lineLength - l);
                // Checking for odd/even spaces
                if (offset % 2 == 0) {
                    // Adding padding on both sides
                    offset = offset / 2;
                    if (offset < 0) {
                        System.out.println(pWord);
                    }
                    else {
                        String buffer = " ".repeat(offset);
                        System.out.println(buffer + pWord + buffer);
                    }
                }
                else {
                    // Odd spaces, adding whitespace before.
                    offset--;
                    offset = offset / 2;
                    if (offset < 0) {
                        System.out.println(pWord);
                    }
                    else {
                        String buffer = " ".repeat(offset);
                        System.out.println(" " + buffer + pWord + buffer);
                    }
                }
            }
        }

        /** SIGN POST */
        else if (mode.equals("S")) {
            // initializing the Shape
            if (maxChecker + lineLength <= minSignLength) {
                maxChecker = minSignLength;
            }
            String bar  = " " + "_".repeat(maxChecker + 2) + " ";
            String bar1 = "/" + " ".repeat(maxChecker + 2) + "\\";
            String bar2 = "\\" + "_".repeat(maxChecker + 2) + "/";
            String spaces = " ".repeat(EIGHT);
            String withoutHand = spaces + "|  |";
            String thumb = spaces + "L_ |";
            String thumb1 = " ".repeat(SEVEN) + "/ _)|";
            String thumb2 = " ".repeat(SIX) + "/ /__L";
            String withHand = "_".repeat(FIVE) + "/ (____)";
            String withHand1 = " ".repeat(SEVEN) + "(____)";
            String withHand2 = "_".repeat(FIVE) + "  (____)";
            String withHand3 = " ".repeat(FIVE) + "\\_(____)";
            String handleEnd = spaces + "\\__/";
            System.out.println(bar);
            System.out.println(bar1);

            // Printing Content
            for (int i = 0; i < pArr.length; i++) {
                int l = pArr[i].length();
                if (l <= 0) {
                    continue;
                }
                char lastChar = pArr[i].charAt(l - 1);
                if (lastChar == ' ') {
                    pArr[i] = pArr[i].substring(0, l - 1);
                }
                l = pArr[i].length();
                int offset = maxChecker - l;
                if (offset < 0) {
                    System.out.println("| " + pArr[i] + " |");
                }
                else {
                    String buffer = " ".repeat(offset);
                    System.out.println("| " + pArr[i] + buffer + " |");
                }
            }

            //Printing Handle
            System.out.println(bar2);
            System.out.println(withoutHand);
            System.out.println(withoutHand);
            System.out.println(thumb);
            System.out.println(thumb1);
            System.out.println(thumb2);
            System.out.println(withHand);
            System.out.println(withHand1);
            System.out.println(withHand2);
            System.out.println(withHand3);
            System.out.println(withoutHand);
            System.out.println(withoutHand);
            System.out.println(handleEnd);
        }
        else {
            throw new ArrayIndexOutOfBoundsException();
        }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("usage: java AlignText file_name line_length [align_mode]");
        } catch (NumberFormatException e) {
            System.out.println("usage: java AlignText file_name line_length [align_mode]");
        }
    }
}

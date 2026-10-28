package  org.example;

public class User {
    private String email;
    private String pass;

    public User(String email, String pass) {
        if (email.length() > 50) {  //check if the email is longer than 50 chars
            throw new IllegalArgumentException("Username is too long, try something shorter");
        }

        int counter = 0;
        for (int i = email.length() - 1; i >= 0; i--) { //check the last part of the email
            char c = email.charAt(i);
            if (Character.isLetter(c)) {
                counter++;
            } else {
                if (counter < 2 || c != '.') { //last [art has to be at least 2 chars, and must be a dot between the second and third part.
                    throw new IllegalArgumentException("Please enter a valid Email as username");
                }
                break;
            }
        }

        int index = -1;
        for (int i = 0; i < email.length(); i++) { //check the first part
            char c = email.charAt(i);
            if (Character.isLetter(c) || Character.isDigit(c) || c == '+' || c == '-' || c == '%' || c == '_' || c == '.') {
                continue;
            } else if (c == '@') {
                index = i + 1; //save the index thats after @
                break;
            } else {
                throw new IllegalArgumentException("Please enter a valid Email as username");
            }
        }

        if(index == -1 || email.charAt(0) == '@' || index >= email.length() - counter - 1){ //check if @ exists, if the fist part is empty, if the second part is empty
            throw new IllegalArgumentException("Please enter a valid Email as username");
        }

        int first = 0;
        for (int i = index; i < email.length() - counter - 1; i++) { //check the second part
            char c = email.charAt(i);
            if (Character.isLetter(c) || Character.isDigit(c) || c == '.' || c == '-') {
                if (first == 0) { //the second part must begin with a char or digit
                    if (Character.isLetter(c) || Character.isDigit(c)) {
                        first++;
                    } else {
                        throw new IllegalArgumentException("Please enter a valid Email as username");
                    }
                }
                continue;
            } else {
                throw new IllegalArgumentException("Please enter a valid Email as username");
            }
        }

        if (pass.length() < 8) {
            throw new IllegalArgumentException("Your password is too short, add more characters");
        } else if (pass.length() > 12) {
            throw new IllegalArgumentException("Your password is too long, try a shorter one");
        }

        boolean digit = false;
        boolean letter = false;
        boolean sign = false;

        for (int i = 0; i < pass.length(); i++) { //check the password
            char c = pass.charAt(i);
            if (Character.isDigit(c)) {
                digit = true;
            } else if (Character.isLetter(c)) {
                letter = true;
            } else if (c == '!' || c == '@' || c == '#' || c == '$' || c == '%' || c == '^' || c == '&' || c == '*' || c == '(' || c == ')' || c == '_' || c == '+' || c == '-' || c == '=') {
                sign = true;
            }
            else{
                throw new IllegalArgumentException("Please enter a valid password");
            }
        }

        if (!digit || !letter || !sign) {
            throw new IllegalArgumentException("Please enter a valid password");
        }

        this.email = email;
        this.pass = pass;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPass() {
        return this.pass;
    }
}
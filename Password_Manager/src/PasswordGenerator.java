import java.security.SecureRandom;

public class PasswordGenerator {
    private static final SecureRandom random = new SecureRandom();
    private static final String caps="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String smallCaps="abcdefghijklmnopqrstuvwxyz";
    private static final String Numeric="1234567890";
    private static final String specialChar="~!@#$%^&*(_+{}|:_[?]>=<";
    private static final String dic = caps + smallCaps + Numeric + specialChar;

    public String generatePassword(int length){
        StringBuilder password = new StringBuilder();
        for(int i=0; i<length;i++){
            int index = random.nextInt(dic.length());
            password.append(dic.charAt(index));
        }
        return password.toString();
    }
}

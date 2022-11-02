package Assignment7;

/**
 * Designed to be just a string with an overriden hashCode() method
 */
public class Keyword {

    private final String keyword;
    private final int mod;

    /**
     * Instantiates a Keyword
     * @param keyword word this keyword represents
     * @param mod modulus value for the hashCode() (aka- the maximum number of hash values)
     */
    public Keyword(String keyword, int mod) {
        this.keyword = keyword;
        this.mod = mod;
    }

    /**
     * Gets the word stored by this keyword
     * @return the word this Keyword represents
     */
    public String getWord() {
        return keyword;
    }

    @Override
    public int hashCode() {
        return (keyword.length() + 26 * (keyword.charAt(0) + keyword.charAt(keyword.length() - 1))) % mod;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof String) {
            return keyword.contentEquals((String) obj);
        }
        if (obj instanceof Keyword) {
            return ((Keyword) obj).keyword.contentEquals(keyword);
        }
        return false;
    }
}

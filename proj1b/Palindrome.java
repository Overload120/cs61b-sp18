public class Palindrome {
    public Deque<Character> wordToDeque(String word){
        LinkedListDeque<Character> ch=new LinkedListDeque<Character>();
        for(int i=0;i<word.length();++i){
            ch.addLast(word.charAt(i));
        }
        return ch;
    }
    public boolean isPalindrome(String word){
        return isPalindrome(wordToDeque(word));
    }
    private boolean isPalindrome(Deque<Character> word){
        if(word.size()<=1){
            return true;
        }
        if(word.removeFirst()!=word.removeLast()){
            return false;
        }
        return isPalindrome(word);
    }
    public boolean isPalindrome(String word, CharacterComparator cc){
        return isPalindrome(wordToDeque(word),cc);
    }
    private boolean isPalindrome(Deque<Character> word, CharacterComparator cc){
        if(word.size()<=1){
            return true;
        }
        if(!cc.equalChars(word.removeFirst(),word.removeLast())){
            return false;
        }
        return isPalindrome(word,cc);
    }
}

public class OffByN implements CharacterComparator{
    private int num;
    OffByN(int N){
        num=N;
    }
    @Override
    public boolean equalChars(char x, char y){
        if(x-y==num || x-y==num*-1){
            return true;
        }
        return false;
    }
}

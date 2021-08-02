
public class GuitarHero {
    private static final String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
    private static synthesizer.GuitarString[] strings = new synthesizer.GuitarString[37];

    public static void main(String[] args) {
        double frequency;
        for (int i = 0; i < 37; i += 1) {
            frequency = 440 * Math.pow(2, ((double) i - 24) / 12);
            strings[i] = new synthesizer.GuitarString(frequency);
        }
        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int pos = keyboard.indexOf(key);
                if (pos == -1) {
                    continue;
                }
                strings[pos].pluck();
            }
                double sample=0.0;
                for(int i=0;i<37;++i){
                    sample+=strings[i].sample();
                }

                StdAudio.play(sample);
                for(int i=0;i<37;++i){
                    strings[i].tic();
                }

        }
    }
}

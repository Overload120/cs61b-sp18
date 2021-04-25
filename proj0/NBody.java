import java.security.spec.PSSParameterSpec;

public class NBody {
    public static double readRadius(String name){
        In a=new In(name);
        double b=a.readDouble();
        return a.readDouble();
    }
    public static Planet[] readPlanets(String name){
        In a=new In(name);
        Planet[] b=new Planet[a.readInt()];
        double c=a.readDouble();
        for (int i=0;i<b.length;++i){
            b[i]=new Planet(a.readDouble(),a.readDouble(),a.readDouble(),a.readDouble(),a.readDouble(),a.readString());
        }
        return b;
    }

    public static void main(String[] args) {
        double T=Double.parseDouble(args[0]);
        double dt=Double.parseDouble(args[1]);
        String filename=args[2];
        double Radius=NBody.readRadius(filename);
        Planet[] Ps=NBody.readPlanets(filename);
        StdDraw.setScale(-Radius,Radius);
        StdDraw.clear();
        StdDraw.picture(-Radius,Radius,"images/starfield.jpg");
        StdDraw.picture(-Radius,0,"images/starfield.jpg");
        StdDraw.picture(0,Radius,"images/starfield.jpg");
        StdDraw.picture(0,-Radius,"images/starfield.jpg");
        for(int i=0;i<Ps.length;++i){
            Ps[i].draw();
        }
        StdDraw.show();
        StdDraw.enableDoubleBuffering();
        double t1=0;
        double[] xForces=new double[Ps.length];
        double[] yForces=new double[Ps.length];
        while(t1<T){
            for(int i=0;i<Ps.length;++i){
                xForces[i]=Ps[i].calcNetForceExertedByX(Ps);
                yForces[i]=Ps[i].calcNetForceExertedByY(Ps);
            }
            for(int i=0;i<Ps.length;++i) {
                Ps[i].update(dt, xForces[i], yForces[i]);
            }
            StdDraw.picture(-Radius,Radius,"images/starfield.jpg");
            StdDraw.picture(-Radius,0,"images/starfield.jpg");
            StdDraw.picture(0,Radius,"images/starfield.jpg");
            StdDraw.picture(0,-Radius,"images/starfield.jpg");
            for(int i=0;i<Ps.length;++i){
                Ps[i].draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
            t1+=dt;
        }
        StdOut.printf("%d\n", Ps.length);
        StdOut.printf("%.2e\n", Radius);
        for (int i = 0; i < Ps.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    Ps[i].xxPos, Ps[i].yyPos, Ps[i].xxVel,
                    Ps[i].yyVel, Ps[i].mass, Ps[i].imgFileName);
        }
    }

}

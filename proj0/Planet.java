public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    private static double G=6.67e-11;
    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }
    public double calcDistance(Planet p){
        return Math.sqrt((xxPos-p.xxPos)*(xxPos-p.xxPos)+(yyPos-p.yyPos)*(yyPos-p.yyPos));
    }
    public double calcForceExertedBy(Planet p){
        return G*mass*p.mass/((calcDistance(p))*(calcDistance(p)));
    }
    public double calcForceExertedByX(Planet p){
        return calcForceExertedBy(p)*(p.xxPos-xxPos)/calcDistance(p);
    }
    public double calcForceExertedByY(Planet p){
        return calcForceExertedBy(p)*(p.yyPos-yyPos)/calcDistance(p);
    }
    public double calcNetForceExertedByX(Planet[] p){
        double a=0;
        int i;
        for(i=0;i<p.length;++i) {
            if (calcDistance(p[i]) != 0) {
                a += calcForceExertedByX(p[i]);
            }
        }
        return a;
    }
    public double calcNetForceExertedByY(Planet[] p){
        double a=0;
        int i;
        for(i=0;i<p.length;++i){
            if (calcDistance(p[i]) != 0) {
                a += calcForceExertedByY(p[i]);
            }
        }
        return a;
    }
    public void update(double t,double fx,double fy){
        xxVel=xxVel+t*fx/mass;
        yyVel=yyVel+t*fy/mass;
        xxPos=xxPos+t*xxVel;
        yyPos=yyPos+t*yyVel;
    }
    public void draw(){
        StdDraw.picture(xxPos,yyPos,"images/"+imgFileName);
    }
}

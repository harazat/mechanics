/**
 *  Spring class that represents a 1D massless spring.
 */

public class Spring {
    private double k = 1.0;

    public Spring() {
    }


    public Spring(double k) {
        this.k = k;
    }

    public double getStiffness() {
        return k;
    }

    private void setStiffness(double k) {
        this.k = k;
    }


//-----------------------------------Task 1 START --------------------------------------------------------------
    public double[] move(double t, double dt, double x0, double v0) {
        double omega = Math.sqrt(k);
        double A = x0;
        double B = v0 / omega;
        int n = (int) (t / dt);
        double[] x = new double[n + 1];
        for (int i = 0; i <= n; i++) {
            double time = i * dt;
            x[i] = A * Math.sin(omega * time) + B * Math.cos(omega * time);
        }
        return x;
    }

    public double[] move(double t, double dt, double x0) {
        return move(t, dt, x0, 0.0);
    }


    public double[] move(double t0, double t1, double dt, double x0, double v0) {
        double omega = Math.sqrt(k);
        double A = x0;
        double B = v0 / omega;
        int n = (int) ((t1 - t0) / dt);
        double[] x = new double[n + 1];
        for (int i = 0; i <= n; i++) {
            double time = t0 + i * dt;
            x[i] = A * Math.sin(omega * time) + B * Math.cos(omega * time);
        }
        return x;
    }


    public double[] move(double t0, double t1, double dt, double x0, double v0, double m) {
        double omega = Math.sqrt(k / m);
        double A = x0;
        double B = v0 / omega;
        int n = (int) ((t1 - t0) / dt);
        double[] x = new double[n + 1];
        for (int i = 0; i <= n; i++) {
            double time = t0 + i * dt;
            x[i] = A * Math.sin(omega * time) + B * Math.cos(omega * time);
        }
        return x;
    }
    //-------------------------------Task1 END ---------------------------------------------------------------------------

    

}


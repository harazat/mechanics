///**
// *  Spring class that represents a 1D massless spring.
// */
//


public class Spring {
    private double k;

    public Spring() {
        k = 1;
    }

    public Spring(double k) {
        this.k = k;
    }

    public double getStiffness() {
        return k;
    }

    private void setK(double k) {
        this.k = k;
    }

    public double[] move(double t, double dt, double x0, double v0) {
        return move(0, t, dt, x0, v0, 1);
    }

    public double[] move(double t, double dt, double x0) {
        return move(0, t, dt, x0, 0, 1);
    }

    public double[] move(double t0, double t1, double dt, double x0, double v0) {
        return move(t0, t1, dt, x0, v0, 1);
    }

    public double[] move(double t0, double t1, double dt, double x0, double v0, double m) {
        double omega = Math.sqrt(this.k / m);
        int numOfPoints = (int) ((t1 - t0) / dt);
        double[] x = new double[numOfPoints];
        for (int i = 0; i < numOfPoints; i++) {
            double arg = omega * (t0 + i * dt);
            x[i] = x0 * Math.cos(arg) + v0 / omega * Math.sin(arg);
        }

        return x;
    }

    public Spring inSeries(Spring that) {
        double newK = (this.k * that.k) / (this.k + that.k);
        return new Spring(newK);
    }

    public Spring inParallel(Spring that) {
        double newK = this.k + that.k;
        return new Spring(newK);
    }
}

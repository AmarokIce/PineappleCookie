package club.someoneice.cookie.util.math;

import club.someoneice.cookie.util.ObjectUtil;

public final class EulerAngles {
    public double pitch;
    public double yaw;
    public double roll;

    public EulerAngles(double pitch, double yaw, double roll) {
        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;
    }

    public EulerAngles(double w, double x, double y, double z) {
        this.roll = (double) Math.atan2(2 * (w * x + y * z),  1 - 2 * (Math.pow(x, 2) + Math.pow(y, 2)));

        this.pitch = ObjectUtil.objectRun(2 * (w * y - z * x), it -> {
            if (Math.abs(it) >= 1) return Math.copySign(1.57075f, it);
            else return Math.asin(it);
        });

        this.yaw = Math.atan2(2 * (w * z + x * y), 1 - 2 * (Math.pow(y, 2) + Math.pow(z, 2)));
    }

    public Quaternion ToQuaternion() {
        double cy = Math.cos(yaw * 0.5f);
        double sy = Math.sin(yaw * 0.5f);
        double cp = Math.cos(pitch * 0.5f);
        double sp = Math.sin(pitch * 0.5f);
        double cr = Math.cos(roll * 0.5f);
        double sr = Math.sin(roll * 0.5f);

        Quaternion q = new Quaternion();
        q.w = cy * cp * cr + sy * sp * sr;
        q.x = cy * cp * sr - sy * sp * cr;
        q.y = sy * cp * sr + cy * sp * cr;
        q.z = sy * cp * cr - cy * sp * sr;
        return q;
    }
}
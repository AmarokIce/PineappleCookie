package club.someoneice.cookie.util.math;

public final class Quaternion {
    public double w;
    public double x;
    public double y;
    public double z;

    public Quaternion() {
        this.w = 0.0f;
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
    }

    public Quaternion(double w, double x, double y, double z) {
        this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    void vectorRotation(Vector3D vector) {
        Quaternion q = this.copy();
        Quaternion qv = q.multiplication(new Quaternion(0, vector.x, vector.y, vector.z)).multiplication(q.inverse());
        vector.x = qv.x;
        vector.y = qv.y;
        vector.z = qv.z;
    }

    public EulerAngles toEulerAngles() {
        return new EulerAngles(this.w,this.x,this.y,this.z);
    }

    Quaternion multiplication(Quaternion target) {
        Quaternion ret = new Quaternion();

        ret.w = this.w * target.w - this.x * target.x - this.y * target.y - this.z * target.z;
        ret.x = this.w * target.x + this.x * target.w + this.y * target.z - this.z * target.y;
        ret.y = this.w * target.y + this.y * target.w + this.z * target.x - this.x * target.z;
        ret.z = this.w * target.z + this.z * target.w + this.x * target.y - this.y * target.x;

        return ret;
    }

    public Quaternion inverse() {
        Quaternion ret = this.copy();

        ret.x *= -1;
        ret.y *= -1;
        ret.z *= -1;

        return ret;
    }

    public Quaternion copy() {
        Quaternion ret = new Quaternion();

        ret.w = this.w;
        ret.x = this.x;
        ret.y = this.y;
        ret.z = this.z;

        return ret;
    }
}
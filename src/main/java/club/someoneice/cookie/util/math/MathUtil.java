package club.someoneice.cookie.util.math;

public final class MathUtil {
    private MathUtil() {}

    public static int add(final int inputA, final int inputB) {
        int s, c, a = inputA, b = inputB;

        do {
            s = a ^ b;
            c = (a & b) << 1;

            a = s;
            b = c;
        } while (c !=0);

        return s;
    }

    public static int sub(final int inputA, final int inputB) {
        return add(inputA, add(~inputB,1));
    }
    public static int mul(final int inputA, final int inputB) {
        int res = 0, a = inputA, b = inputB;

        while (b != 0) {
            if ((b & 1) == 1) res = add(res, a);

            a <<= 1;
            b >>>= 1;
        }

        return res;
    }

    public static int div(final int inputA, final int inputB) {
        int count = 0, t, a = inputA;

        while (a >= inputB) {
            t = inputB;

            while (a >= (t << 1)) {
                t <<= 1;
                count = add(count, 1);
            }

            a = sub(a, t);
            count = add(count, 1);
        }

        return count;
    }
}

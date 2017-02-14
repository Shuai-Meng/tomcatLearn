package connector;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Created by shuaimeng2 on 2017/2/14.
 */
public class ResponseWriter extends PrintWriter{

    public ResponseWriter(OutputStreamWriter outputStreamWriter) {
        super(outputStreamWriter);
    }

    /**
     * Print a boolean value.
     *
     * @param b The value to be printed
     */
    public void print(boolean b) {

        super.print(b);
        super.flush();

    }


    /**
     * Print a character value.
     *
     * @param c The value to be printed
     */
    public void print(char c) {

        super.print(c);
        super.flush();

    }


    /**
     * Print a character array value.
     *
     * @param ca The value to be printed
     */
    public void print(char ca[]) {

        super.print(ca);
        super.flush();

    }


    /**
     * Print a double value.
     *
     * @param d The value to be printed
     */
    public void print(double d) {

        super.print(d);
        super.flush();

    }


    /**
     * Print a float value.
     *
     * @param f The value to be printed
     */
    public void print(float f) {

        super.print(f);
        super.flush();

    }


    /**
     * Print an integer value.
     *
     * @param i The value to be printed.
     */
    public void print(int i) {

        super.print(i);
        super.flush();

    }



    /**
     * Print a long value.
     *
     * @param l The value to be printed
     */
    public void print(long l) {

        super.print(l);
        super.flush();

    }


    /**
     * Print an object value.
     *
     * @param o The value to be printed
     */
    public void print(Object o) {

        super.print(o);
        super.flush();

    }


    /**
     * Print a String value.
     *
     * @param s The value to be printed
     */
    public void print(String s) {

        super.print(s);
        super.flush();

    }


    /**
     * Terminate the current line by writing the line separator string.
     */
    public void println() {

        super.println();
        super.flush();

    }


    /**
     * Print a boolean value and terminate the current line.
     *
     * @param b The value to be printed
     */
    public void println(boolean b) {

        super.println(b);
        super.flush();

    }


    /**
     * Print a character value and terminate the current line.
     *
     * @param c The value to be printed
     */
    public void println(char c) {

        super.println(c);
        super.flush();

    }


    /**
     * Print a character array value and terminate the current line.
     *
     * @param ca The value to be printed
     */
    public void println(char ca[]) {

        super.println(ca);
        super.flush();

    }


    /**
     * Print a double value and terminate the current line.
     *
     * @param d The value to be printed
     */
    public void println(double d) {

        super.println(d);
        super.flush();

    }


    /**
     * Print a float value and terminate the current line.
     *
     * @param f The value to be printed
     */
    public void println(float f) {

        super.println(f);
        super.flush();

    }


    /**
     * Print an integer value and terminate the current line.
     *
     * @param i The value to be printed.
     */
    public void println(int i) {

        super.println(i);
        super.flush();

    }



    /**
     * Print a long value and terminate the current line.
     *
     * @param l The value to be printed
     */
    public void println(long l) {

        super.println(l);
        super.flush();

    }


    /**
     * Print an object value and terminate the current line.
     *
     * @param o The value to be printed
     */
    public void println(Object o) {

        super.println(o);
        super.flush();

    }


    /**
     * Print a String value and terminate the current line.
     *
     * @param s The value to be printed
     */
    public void println(String s) {

        super.println(s);
        super.flush();

    }
}

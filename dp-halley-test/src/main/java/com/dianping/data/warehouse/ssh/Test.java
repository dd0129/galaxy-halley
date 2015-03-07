package com.dianping.data.warehouse.ssh;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongdi.tang on 14-7-31.
 */
public class Test {
    public static void main(String[] args){
        List<String> commands = new ArrayList<String>();
        commands.add("/data/deploy/dwarch/conf/ETL/bin/start_calculate.sh");
        commands.add("/data/deploy/dwarch/bin/atom/mail/main.py");
        commands.add("-d 2015-02-03 -m 517");
        Test.execute(commands);
    }

    public static Integer execute(List<String> commands) {
        ProcessBuilder builer = new ProcessBuilder(commands);
        builer.redirectErrorStream(true);
        Process process = null;
        try {
            process = builer.start();
            InputStream stdin = process.getInputStream();
            Test.StreamPrinter printer = new Test.StreamPrinter(stdin);
            printer.start();
            return process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

     static class StreamPrinter extends Thread {
        private InputStream is;
        private OutputStream os;

        public StreamPrinter(InputStream is) {
            this(is, null);
        }

        public StreamPrinter(InputStream is, OutputStream redirect) {
            this.is = is;
            this.os = redirect;
        }

        public void run() {
            try {
                PrintWriter pw = null;
                if (os != null)
                    pw = new PrintWriter(os);
                InputStreamReader isr = new InputStreamReader(is);
                Byte c  ;
                while((c=(byte)isr.read())!=null){
                    System.out.println(c);
                }
//                BufferedReader br = new BufferedReader(isr);
//                String line;
//                while ((line = br.readLine()) != null) {
//                    if (pw != null)
//                        pw.println(line);
//                    System.out.println(line);
//                }
                if (pw != null)
                    pw.flush();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

}

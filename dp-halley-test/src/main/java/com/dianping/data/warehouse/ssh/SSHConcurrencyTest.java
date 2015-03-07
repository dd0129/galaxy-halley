package com.dianping.data.warehouse.ssh;


import java.io.*;

/**
 * Created by hongdi.tang on 14-5-1.
 */
public class SSHConcurrencyTest {
    public static void main(String[] args) throws Exception{
        final String cmd = "ssh -o ConnectTimeout=3 -o ConnectionAttempts=5 -o PasswordAuthentication=no -o StrictHostKeyChecking=no -p 58422 deploy@10.1.6.49 sh /tmp/test.sh";
        String path = "/data/deploy/dwarch/log/test/test.log";

        for(int i=0;i<10000;i++){
            final String logpath = path +"." + String.valueOf(i);
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    SSHConcurrencyTest.executeCommand(cmd,logpath);
                }
            });
            thread.start();
        }
        System.out.print("Enter a Char:");

        char i = (char) System.in.read();
        System.out.println("your char is :"+i);
    }

    public static Integer executeCommand(String cmd,String path){
        try{
            Process process = Runtime.getRuntime().exec(cmd);
            File outputFile = new File(path);
            SSHConcurrencyTest.StreamWriter outPrinter = new SSHConcurrencyTest.StreamWriter(process.getInputStream(),outputFile);
            SSHConcurrencyTest.StreamWriter errPrinter = new SSHConcurrencyTest.StreamWriter(process.getErrorStream(),outputFile);
            outPrinter.start();
            errPrinter.start();
            return process.waitFor();
        }catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * StreamWriter.
     *
     */
    public static class StreamWriter extends Thread {
        InputStream is;
        FileOutputStream output;

        public StreamWriter(InputStream is, File file) throws IOException {
            this.is = is;
            output = new FileOutputStream(file, true);
        }

        @Override
        public void run() {
            BufferedReader br = null;
            try {
                InputStreamReader isr = new InputStreamReader(is,"UTF-8");
                br = new BufferedReader(isr);
                String line = null;
                while ((line = br.readLine()) != null) {
                    output.write((line + "\r\n").getBytes());
                }
                output.close();
                output = null;
                br.close();
                br=null;
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }finally{
                cleanup(output,br);
            }
        }

        public static void cleanup(Closeable... closeables) {
            for (Closeable c : closeables) {
                if (c != null) {
                    try {
                        c.close();
                    } catch(Throwable e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

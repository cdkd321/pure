package com.mygame.pure.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

class ShellUtils {
    public final static String SHELL_CMD_CHMOD = "chmod";
    public final static String SHELL_CMD_KILL = "kill -9";
    public final static String SHELL_CMD_RM = "rm";
    public final static String SHELL_CMD_PS = "ps";
    public final static String SHELL_CMD_PIDOF = "pidof";

    public final static String CHMOD_EXE_VALUE = "700";

    public static boolean isRootPossible() {
        try {

            // Check if Superuser.apk exists
            File fileSU = new File("/system/app/Superuser.apk");
            if (fileSU.exists())
                return true;

            fileSU = new File("/system/bin/su");
            if (fileSU.exists())
                return true;

            // Check for 'su' binary
            String[] cmd = { "which su" };
            int exitCode = ShellUtils.doShellCommand(null, cmd, new ShellCallback()
            {

                @Override
                public void shellOut(String msg) {
                }

                @Override
                public void processComplete(int exitValue) {
                }

            }, false, true).exitValue();

            if (exitCode == 0) {
                return true;
            }
        } catch (IOException e) {
        } catch (Exception e) {
        }
        return false;
    }

    public static Process doShellCommand(Process proc, String[] cmds, ShellCallback sc, boolean runAsRoot,
            boolean waitFor) throws Exception {

        if (proc == null) {
            if (runAsRoot)
                proc = Runtime.getRuntime().exec("su");
            else
                proc = Runtime.getRuntime().exec("sh");
        }

        OutputStreamWriter out = new OutputStreamWriter(proc.getOutputStream());

        for (int i = 0; i < cmds.length; i++) {
            out.write(cmds[i]);
            out.write("\n");
        }

        out.flush();
        out.write("exit\n");
        out.flush();

        if (waitFor) {

            final char buf[] = new char[20];

            // Consume the "stdout"
            InputStreamReader reader = new InputStreamReader(proc.getInputStream());
            @SuppressWarnings("unused")
            int read = 0;
            while ((read = reader.read(buf)) != -1) {
                if (sc != null)
                    sc.shellOut(new String(buf));
            }

            // Consume the "stderr"
            reader = new InputStreamReader(proc.getErrorStream());
            read = 0;
            while ((read = reader.read(buf)) != -1) {
                if (sc != null)
                    sc.shellOut(new String(buf));
            }

            proc.waitFor();

        }

        sc.processComplete(proc.exitValue());

        return proc;

    }

    public interface ShellCallback {
        public void shellOut(String shellLine);

        public void processComplete(int exitValue);
    }
}

package ditto;

import java.util.Arrays;
import java.io.File;

public class UnitTest {
    public static void main(String[] args) throws Exception {
        String task = args[0];
        String passSetFolder = args[1];
        String failSetFolder = args[2];

        /// Probar que los ficheros de passSetFolder pasan el test
        /// Y los de failSetFolder no
        File passSet = new File(passSetFolder);
        File failSet = new File(failSetFolder);

        if (!passSet.isDirectory()) {
            System.out.println("passSet is not a directory");
            System.exit(1);
        }

        if (!failSet.isDirectory()) {
            System.out.println("failSet is not a directory");
            System.exit(1);
        }

        int taskIndex = Arrays.asList(Test.tasks).indexOf(task);

        Test.useAsMain = false;
        /// Deberian de pasar todos los tests anteriores, salvo etapa actual
        /// Iterar primero los archivos de passSet
        String[] newArgs = new String[2];
        newArgs[0] = task;
        for (File f : passSet.listFiles()) {
            newArgs[1] = f.getAbsolutePath();

            try {
                Test.main(newArgs);
                System.out.println("✅ passed " + f.getName() + " as it should");
            } catch (Exception e) {
                System.err.println(e);
                e.printStackTrace();
                System.err.println("❌ failed " + f.getName() + " as it shouldn't");
            }
        }

        /// Iterar los archivos de failSet
        /// Deberian pasar los tests anteriores, salvo etapa actual
        System.out.println("Testing " + task + " failSet");
        for (File f : failSet.listFiles()) {
            newArgs[1] = f.getAbsolutePath();

            for (int i = 0; i < taskIndex; i++) {
                /// Estos tienen que pasar
                newArgs[0] = Test.tasks[i];
                Test.main(newArgs);
            }

            newArgs[0] = task;
            try {
                Test.main(newArgs);
                System.out.println("❌ passed " + f.getName() + " " + task + " test, but it shouldn't");
            } catch (Exception e) {
                System.out.println("✅ failed " + f.getName() + " " + task + " as it should");
            }
        }
    }
}

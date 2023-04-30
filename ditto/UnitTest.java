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

        Test.useAsMain = false;
        String[] newArgs = new String[2];
        newArgs[0] = task;

        System.out.println("Testing " + task + " passSet");

        /// Deberian de pasar todos los tests anteriores, salvo etapa actual
        /// Iterar primero los archivos de passSet
        if (passSet.isDirectory()) {
            for (File f : passSet.listFiles()) {
                System.out.println("Testing " + f.getName() + " " + task);
                newArgs[1] = f.getAbsolutePath();
                System.out.println("args = " + Arrays.toString(newArgs));

                try {
                    Test.main(newArgs);
                    System.out.println("✅ passed " + f.getName() + " as it should");
                } catch (Exception e) {
                    System.err.println(e);
                    e.printStackTrace();
                    System.err.println("❌ failed " + f.getName() + " as it shouldn't");
                    throw e;
                }
            }
        } else {
            System.out.println("passSet is not a directory");
        }

        /// Iterar los archivos de failSet
        /// Deberian pasar los tests anteriores, salvo etapa actual
        if (failSet.isDirectory()) {
            System.out.println("Testing " + task + " failSet");
            int taskIndex = Arrays.asList(Test.tasks).indexOf(task);
            for (File f : failSet.listFiles()) {
                newArgs[1] = f.getAbsolutePath();

                for (int i = 0; i < taskIndex; i++) {
                    /// Estos tienen que pasar
                    newArgs[0] = Test.tasks[i];
                    try {
                        Test.main(newArgs);
                        System.out.println("✅ passed " + f.getName() + " in " + newArgs[0] + " as it should");
                    } catch (Exception e) {
                        System.err.println(e);
                        e.printStackTrace();
                        System.err.println("❌ failed " + f.getName() + " in " + newArgs[0] + " as it shouldn't");
                        throw e;
                    }
                }

                newArgs[0] = task;
                try {
                    Test.main(newArgs);
                    System.out.println("❌ passed " + f.getName() + " " + task + " test, but it shouldn't");
                } catch (Exception e) {
                    System.out.println("✅ failed " + f.getName() + " " + task + " as it should");
                }
            }
        } else {
            System.out.println("failSet is not a directory");
        }
    }
}

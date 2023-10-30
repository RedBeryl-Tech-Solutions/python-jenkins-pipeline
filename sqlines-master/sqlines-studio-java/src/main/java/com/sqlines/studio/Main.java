/*
 * Copyright (c) 2021 SQLines
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sqlines.studio;

// Problem: LauncherHelper will check for the javafx.graphics module to be present as a named module.
// If that module is not present, the launch is aborted.
// Solution: provide a new main class that doesn't extend from Application.

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan({"com.sqlines.studio", "com.sqlines.studio.sqlconverter"})
@ComponentScan("com")
@EntityScan("com.sqlines.studio.sqlconverter")
public class Main {

    public static void main(String[] args) {


       System.setProperty("model.curr-dir", "C:\\Users\\Acer\\Desktop\\sqlines-master\\sqlines-studio-java");

       SpringApplication.run(Main.class, args);
    }
}

//public class Main {
//
//    public static void main(String[] args) {
//
//       String javafxPropValue = System.getProperty("javafx.preloader");
//     System.out.println(javafxPropValue);
//
//        Application.main(args);
//    }
//}


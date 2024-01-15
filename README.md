# Robot Controller Network Application

**PREREQUISITES**

Before running this project, make sure you have the following Java packages installed on your computer:

- openjdk 17 2021-09-14 LTS
- OpenJDK Runtime Environment Zulu17.28+13-CA (build 17+35-LTS)
- OpenJDK 64-Bit Server VM Zulu17.28+13-CA (build 17+35-LTS, mixed mode, sharing)

You can check your Java version by entering the following command in a command prompt:

**Windows OS:**
```bash
java --version
```

**Ubuntu/Debian OS:**
```bash
java -version
```

**RUNNING THE PROJECT**

1. Extract the source code archive.
2. Start up a command prompt.
3. Go to the project folder.
4. Once in the project folder, run the following commands:

    ```bash
    cd bin
    java network_application.Server
    ```

    At this step, the prompt should display the following line: ". Listening for a connection..." and a user window should start up. This means you have correctly started the server of the application.

5. Create as many robots as you want by running the following command line:

    ```bash
    java network_application.Client 127.0.0.1 robot_name
    ```

    For this project, we want to run the server and client on the same device. This is why the IP address of the server is the IP address of the localhost (127.0.0.1). You can replace the argument "robot_name" with any name you want to give to the robot.

**CLOSING THE PROJECT**

**Closing the client:**
Just close the user window, and the client should shut down automatically.

**Closing the server:**
Close the server user window and finish the application in the command prompt using the command:
```bash
CTRL + C
```
You may have to repeat the command multiple times.

**APPLICATION DESCRIPTION**

This Java-based network application allows you to create as many controllers as needed, offering seamless control over robots in a simulated playground. The application establishes a robust communication network for real-time manipulation, providing a user-friendly interface for efficient robotic operations. Enjoy the flexibility to simulate multiple robots simultaneously.
